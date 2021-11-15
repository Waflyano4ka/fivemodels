package com.example.fivemodels.controllers;

import com.example.fivemodels.models.Surfboards;
import com.example.fivemodels.repositories.SurfboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SurfboardsController {

    @Autowired
    private SurfboardRepository surfboardRepository;

    @GetMapping("/surfboard")
    public String surfboards(Model mod){
        Iterable<Surfboards> surfboards = surfboardRepository.findAll();
        mod.addAttribute("surfboards", surfboards);

        return "views/surfboards";
    }

    @PostMapping("/surfboard")
    public String surfboards(
            @RequestParam String name,
            @RequestParam String model,
            @RequestParam String brand,
            @RequestParam String size_height,
            @RequestParam String size_width,
            Model mod){

        double height;
        double width;

        try{
            height = Double.parseDouble(size_height);
            width = Double.parseDouble(size_width);
        } catch (Exception exception) {
            height = 0;
            width = 0;
        }

        Surfboards surfboard = new Surfboards(name, model, brand, height, width);
        surfboardRepository.save(surfboard);

        Iterable<Surfboards> surfboards = surfboardRepository.findAll();
        mod.addAttribute("surfboards", surfboards);

        mod.addAttribute("name", name);
        mod.addAttribute("model", model);
        mod.addAttribute("brand", brand);
        mod.addAttribute("height", height);
        mod.addAttribute("width", width);
        return "views/surfboards";
    }
}
