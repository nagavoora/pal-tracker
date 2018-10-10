package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private final TimeEntryRepository repo;
    public static long idval = 1L;

    @Autowired
    public TimeEntryRepository timeEntryRepository;


    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        repo = timeEntryRepository;
    }

   @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {

        timeEntryToCreate.setId(idval);
        repo.create(timeEntryToCreate);
        idval++;
        return new ResponseEntity(timeEntryToCreate, HttpStatus.CREATED);

    }


    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry = repo.find(id);
        if (timeEntry == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(timeEntry, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> list = repo.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry updated = repo.update(id, timeEntry);

        if (updated == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(updated, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        repo.delete(id);
        return new ResponseEntity(new TimeEntry(), HttpStatus.NO_CONTENT);
    }
}
