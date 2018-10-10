package io.pivotal.pal.tracker;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTimeEntryRepository implements TimeEntryRepository
{
    private Map<Long, TimeEntry> map = new ConcurrentHashMap<>();
    private long counter = 0;

    public TimeEntry create(TimeEntry timeEntry) {
        counter = counter + 1;
        TimeEntry savedTimeEntry = new TimeEntry(counter, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        map.put(counter, savedTimeEntry);
        return savedTimeEntry;
    }

    public TimeEntry find(Long id) {
        return map.get(id);
    }

    public List<TimeEntry> list() {
        Collection<TimeEntry> timeEntries = map.values();
        return new ArrayList<>(timeEntries);
    }

    public TimeEntry update(Long id, TimeEntry timeEntry) {
        if (map.containsKey(id)) {
            TimeEntry updated = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
            map.put(id, updated);
            return updated;
        }

        return null;
    }

    public void delete(Long id) {
        map.remove(id);
    }
}

