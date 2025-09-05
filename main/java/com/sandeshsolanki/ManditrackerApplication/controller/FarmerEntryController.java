package com.sandeshsolanki.ManditrackerApplication.controller;

import com.sandeshsolanki.ManditrackerApplication.entity.FarmerEntry;
import com.sandeshsolanki.ManditrackerApplication.service.FarmerEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class FarmerEntryController {

    @Autowired
    private FarmerEntryService service;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("entry", new FarmerEntry());
        return "form";
    }

    @PostMapping("/save")
    public String saveEntry(@ModelAttribute FarmerEntry entry) {
        service.saveEntry(entry);
        return "redirect:/summary";
    }

    @GetMapping("/summary")
    public String showSummary(Model model) {
        var todayEntries = service.getTodayEntries();

        double totalCommission = todayEntries.stream()
                .mapToDouble(FarmerEntry::getCommissionAmount)
                .sum();

        double totalAmount = todayEntries.stream()
                .mapToDouble(FarmerEntry::getTotalAmount)
                .sum();

        model.addAttribute("entries", todayEntries);
        model.addAttribute("totalCommission", totalCommission);
        model.addAttribute("totalAmount", totalAmount);
        return "summary";
    }

    @GetMapping("/history")
    public String showHistoryDates(Model model) {
        List<LocalDate> dates = service.getDistinctEntryDatesLastMonth();
        model.addAttribute("dates", dates);
        return "history_dates";
    }

    @GetMapping("/history/{date}")
    public String showHistoryByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        List<FarmerEntry> entries = service.getEntriesByDate(date);

        double totalCommission = entries.stream()
                .mapToDouble(FarmerEntry::getCommissionAmount)
                .sum();

        model.addAttribute("entries", entries);
        model.addAttribute("selectedDate", date);
        model.addAttribute("totalCommission", totalCommission);

        return "history_summary";
    }

    @PostMapping("/delete/{id}")
    public String deleteEntry(@PathVariable Long id) {
        service.deleteEntry(id);
        return "redirect:/summary";
    }

    @PostMapping("/paid/{id}")
    public String markAsPaid(@PathVariable Long id) {
        FarmerEntry entry = service.getById(id);
        entry.setPaid(true);
        service.saveEntry(entry); // corrected method name
        return "redirect:/summary";
    }
}


