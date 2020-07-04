package com.unlimit.task.pms.controller;

import com.unlimit.task.pms.model.PersonDetails;
import com.unlimit.task.pms.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/persons")
public class PersonController {

    private String viewPath = "index";
    private String url = "persons";

    @Autowired
    private PersonRepository personRepository;

    @ModelAttribute
    public void setGlobalAttributes(Model model) {
        model.addAttribute("url", url);
    }

    @GetMapping
    public String persons(Model model, @RequestParam(value = "country", required = false) String country) {
        // records categorized by scanned by and country too
        model.addAttribute("scannedByGroupedPersons", personRepository.findDistinctScannedByAndCountry(country));
        model.addAttribute("country", country);
        return url + "/" + viewPath;
    }

    @GetMapping("/table")
    public String table(Model model, @RequestParam(value = "country", required = false) String country) {
        if (country != null) {
            model.addAttribute("persons", personRepository.findByDetailsCountry(country));
            return url + "/table";
        }
        model.addAttribute("persons", personRepository.findAll());
        return url + "/table";
    }

    @GetMapping("/table/scannedBy/{scannedBy}")
    public String tableSortScannedBy(Model model, @PathVariable("scannedBy") String scannedBy,
            @RequestParam(value = "country", required = false) String country) {
        if (scannedBy.equals("All")) {
            // removing scanned by filter if all is selected by admin
            scannedBy = null;
        }
        model.addAttribute("persons", personRepository.findByDetailsScannedByAndDetailsCountry(scannedBy, country));
        return url + "/table";
    }

    @GetMapping(value = "/details/{id}/json")
    @ResponseBody
    public PersonDetails details(@PathVariable("id") Integer personId) {
        return personRepository.findById(personId).get().getDetails();
    }
}