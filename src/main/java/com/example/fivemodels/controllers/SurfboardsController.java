package com.example.fivemodels.controllers;

import com.example.fivemodels.models.Brand;
import com.example.fivemodels.models.Surfboards;
import com.example.fivemodels.repositories.SurfboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SurfboardsController {

    @Autowired
    private SurfboardRepository surfboardRepository;

    @GetMapping("/surfboards")
    public String surfboards(Model mod, Surfboards surfboards){
        Iterable<Surfboards> surfboard = surfboardRepository.findAll();
        mod.addAttribute("surfboard", surfboard);

        return "views/surfboards";
    }

    @PostMapping("/surfboards")
    public String surfboards(@Valid Surfboards surfboards, BindingResult bindingResult,
                             @RequestParam String name,
                             @RequestParam String model,
                             @RequestParam String brand,
                             @RequestParam String size_height,
                             @RequestParam String size_width,
                             Model mod){
        Iterable<Surfboards> surfboard = surfboardRepository.findAll();

        mod.addAttribute("name", name);
        mod.addAttribute("model", model);
        mod.addAttribute("brand", brand);
        mod.addAttribute("height", size_height);
        mod.addAttribute("width", size_width);

        if (bindingResult.hasErrors()){
            mod.addAttribute("surfboard", surfboard);
            return "views/surfboards";
        }
        List<Surfboards> res = surfboardRepository.findByName(name);
        if (res.size()>0){
            ObjectError error = new ObjectError("name", "Имя уже существует");
            bindingResult.addError(error);
            mod.addAttribute("surfboard", surfboard);
            return "views/surfboards";
        }
        else {
            surfboardRepository.save(surfboards);

            surfboard = surfboardRepository.findAll();
            mod.addAttribute("surfboard", surfboard);

            return "views/surfboards";
        }
    }

    @PostMapping("/surfboards/search")
    public String surfboards(
            @RequestParam String search,
            Model mod){

        List<Surfboards> surfboards = surfboardRepository.findByBrand(search);
        mod.addAttribute("surfboards", surfboards);
        mod.addAttribute("search", search);
        return "views/surfboards";
    }

    @GetMapping("/surfboards/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model mod, Surfboards surfboards){

        Surfboards object = surfboardRepository.findById(id).orElseThrow();
        mod.addAttribute("object", object);
        return "details/surfboard";
    }

    @PostMapping("/surfboards/{id}/edit")
    public String blogDetails(@Valid Surfboards surfboards, BindingResult bindingResult,
                              @PathVariable(value = "id") long id,
                              @RequestParam String name,
                              Model mod){
        mod.addAttribute("object", surfboards);

        if (bindingResult.hasErrors()){
            return "details/surfboard";
        }
        List<Surfboards> res = surfboardRepository.findByName(name);
        if (res.size()>0){
            ObjectError error = new ObjectError("name", "Имя уже существует");
            bindingResult.addError(error);
            return "details/surfboard";
        }
        else {
            surfboardRepository.save(surfboards);
            return "redirect:/surfboards";
        }
    }

    @GetMapping("/surfboards/{id}/delete")
    public String surfboardDelete(@PathVariable(value = "id") long id, Model mod){
        Surfboards surfboard = surfboardRepository.findById(id).orElseThrow();
        surfboardRepository.delete(surfboard);

        return "redirect:/surfboards";
    }
}
