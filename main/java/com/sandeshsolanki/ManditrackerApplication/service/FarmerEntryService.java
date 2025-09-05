package com.sandeshsolanki.ManditrackerApplication.service;


import com.sandeshsolanki.ManditrackerApplication.entity.FarmerEntry;
import com.sandeshsolanki.ManditrackerApplication.repository.FarmerEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FarmerEntryService {

    @Autowired
    private FarmerEntryRepository repository;

    public void saveEntry(FarmerEntry entry) {
        if (entry.getDate() == null) {
            entry.setDate(LocalDate.now());
        }
        repository.save(entry);
    }

    public List<FarmerEntry> getTodayEntries() {
        return repository.findByDate(LocalDate.now());
    }

    public List<LocalDate> getDistinctEntryDatesLastMonth() {
        LocalDate today = LocalDate.now();
        LocalDate oneMonthAgo = today.minusMonths(1);
        return repository.findDistinctDatesBetween(oneMonthAgo, today);
    }
    public List<FarmerEntry> getEntriesByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public void deleteEntry(Long id) {
        repository.deleteById(id);
    }

    public FarmerEntry getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid entry ID: " + id));
    }

    public void save(FarmerEntry entry) {
        repository.save(entry);
    }

    public List<LocalDate> getDistinctEntryDates() {
        return repository.findDistinctDates();
    }



}

