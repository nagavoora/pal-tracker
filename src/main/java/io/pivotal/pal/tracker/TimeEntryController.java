package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository repo;
    private final CounterService counter;
    private final GaugeService gauge;
//    public static long idval = 1L;



    public TimeEntryController(
            TimeEntryRepository timeEntriesRepo,CounterService counter, GaugeService gauge)
     {
        this.repo = timeEntriesRepo;
        this.counter = counter;
        this.gauge = gauge;
    }
   @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {

//        timeEntryToCreate.setId(idval);
       TimeEntry timeEntry = repo.create(timeEntryToCreate);
//        idval++;
       counter.increment("TimeEntry.created");
       gauge.submit("timeEntries.count", repo.list().size());
        return new ResponseEntity(timeEntry, HttpStatus.CREATED);

    }


    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry = repo.find(id);
        if (timeEntry == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        counter.increment("TimeEntry.read");
        return new ResponseEntity(timeEntry, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        List<TimeEntry> list = repo.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry updated = repo.update(id, timeEntry);

        if (updated == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        counter.increment("TimeEntry.updated");
        return new ResponseEntity(updated, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        repo.delete(id);
        counter.increment("TimeEntry.deleted");
        return new ResponseEntity(new TimeEntry(), HttpStatus.NO_CONTENT);
    }
}
