package com.example.fivemodels.controllers;

import com.example.fivemodels.models.Wax;
import com.example.fivemodels.repositories.WaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WaxController {

    @Autowired
    private WaxRepository waxRepository;

    @GetMapping("/waxes")
    public String surfboards(Model mod){
        Iterable<Wax> waxes = waxRepository.findAll();
        mod.addAttribute("waxes", waxes);

        return "views/waxes";
    }

    @PostMapping("/waxes")
    public String surfboards(
            @RequestParam String name,
            @RequestParam String temperature,
            @RequestParam String weight,
            Model mod){

        double temp;
        double w;

        try{
            temp = Double.parseDouble(temperature);
            w = Double.parseDouble(weight);
        } catch (Exception exception) {
            temp = 0;
            w = 0;
        }

        Wax wax = new Wax(name, temp, w);
        waxRepository.save(wax);

        Iterable<Wax> waxes = waxRepository.findAll();
        mod.addAttribute("waxes", waxes);

        mod.addAttribute("name", name);
        mod.addAttribute("temperature", temperature);
        mod.addAttribute("weight", weight);
        return "views/waxes";
    }
}
