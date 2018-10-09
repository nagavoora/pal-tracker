package io.pivotal.pal.tracker;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TimeEntryController {
    private final TimeEntryRepository repo;


    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        repo = timeEntryRepository;
    }


    public ResponseEntity create(TimeEntry timeEntryToCreate) {
        repo.create(timeEntryToCreate);
        return null;
    }

    public ResponseEntity<TimeEntry> read(long id) {
        TimeEntry timeEntry = repo.find(id);
        if (timeEntry == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(timeEntry, HttpStatus.OK);
    }

    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> list = repo.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    public ResponseEntity update(long id, TimeEntry timeEntry) {
        timeEntry = repo.find(id);
       if (timeEntry == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else {
           repo.update(id, timeEntry);


           return new ResponseEntity(timeEntry, HttpStatus.OK);
       }
    }

    public ResponseEntity<TimeEntry> delete(long id) {
        return null;
    }
}
