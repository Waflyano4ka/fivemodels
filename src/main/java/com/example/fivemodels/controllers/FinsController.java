package com.example.fivemodels.controllers;

import com.example.fivemodels.models.Brand;
import com.example.fivemodels.models.Fin;
import com.example.fivemodels.repositories.FinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FinsController {

    @Autowired
    private FinRepository finRepository;

    @GetMapping("/fins")
    public String surfboards(Model mod){
        Iterable<Fin> fins = finRepository.findAll();
        mod.addAttribute("fins", fins);

        mod.addAttribute("size", "M");

        return "views/fins";
    }

    @PostMapping("/fins")
    public String surfboards(
            @RequestParam String name,
            @RequestParam String size,
            Model mod){

        Fin fin = new Fin(name, size);
        finRepository.save(fin);

        Iterable<Fin> fins = finRepository.findAll();
        mod.addAttribute("fins", fins);

        mod.addAttribute("name", name);
        mod.addAttribute("size", size);
        return "views/fins";
    }
}
