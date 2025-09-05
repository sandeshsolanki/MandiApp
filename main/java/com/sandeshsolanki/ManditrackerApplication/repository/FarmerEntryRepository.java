package com.sandeshsolanki.ManditrackerApplication.repository;


import com.sandeshsolanki.ManditrackerApplication.entity.FarmerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FarmerEntryRepository extends JpaRepository<FarmerEntry, Long> {
    List<FarmerEntry> findByDate(LocalDate date);

    @Query("SELECT DISTINCT f.date FROM FarmerEntry f WHERE f.date BETWEEN :start AND :end ORDER BY f.date DESC")
    List<LocalDate> findDistinctDatesBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    List<FarmerEntry> findAllByDate(LocalDate date);

    @Query("SELECT DISTINCT e.date FROM FarmerEntry e ORDER BY e.date DESC")
    List<LocalDate> findDistinctDates();
}
