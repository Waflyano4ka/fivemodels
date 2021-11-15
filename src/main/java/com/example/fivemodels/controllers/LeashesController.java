package com.example.fivemodels.controllers;

import com.example.fivemodels.models.Leashes;
import com.example.fivemodels.repositories.LeashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LeashesController {

    @Autowired
    private LeashRepository leashRepository;

    @GetMapping("/leashes")
    public String surfboards(Model mod){
        Iterable<Leashes> leashes = leashRepository.findAll();
        mod.addAttribute("leashes", leashes);

        return "views/leashes";
    }

    @PostMapping("/leashes")
    public String surfboards(
            @RequestParam String name,
            @RequestParam String brand,
            @RequestParam String size,
            Model mod){

        double s;

        try{
            s = Double.parseDouble(size);
        } catch (Exception exception) {
            s = 0;
        }

        Leashes leash = new Leashes(name, brand, s);
        leashRepository.save(leash);

        Iterable<Leashes> leashes = leashRepository.findAll();
        mod.addAttribute("leashes", leashes);

        mod.addAttribute("name", name);
        mod.addAttribute("brand", brand);
        mod.addAttribute("size", size);
        return "views/leashes";
    }
}
