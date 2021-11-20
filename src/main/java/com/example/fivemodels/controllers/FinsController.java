package com.example.fivemodels.controllers;

import com.example.fivemodels.models.Brand;
import com.example.fivemodels.models.Fin;
import com.example.fivemodels.repositories.FinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class FinsController {

    @Autowired
    private FinRepository finRepository;

    @GetMapping("/fins")
    public String surfboards(Model mod, Fin fin){
        Iterable<Fin> fins = finRepository.findAll();
        mod.addAttribute("fins", fins);

        mod.addAttribute("size", "M");

        return "views/fins";
    }

    @PostMapping("/fins")
    public String surfboards(@Valid Fin fin, BindingResult bindingResult,
                             @RequestParam String name,
                             @RequestParam String size,
                             Model mod){
        Iterable<Fin> fins = finRepository.findAll();

        mod.addAttribute("name", name);
        mod.addAttribute("size", size);

        if (bindingResult.hasErrors()){
            mod.addAttribute("fins", fins);
            return "views/fins";
        }
        List<Fin> res = finRepository.findByName(name);
        if (res.size()>0){
            ObjectError error = new ObjectError("name", "Имя уже существует");
            bindingResult.addError(error);
            mod.addAttribute("fins", fins);
            return "views/fins";
        }
        else {
            finRepository.save(fin);

            fins = finRepository.findAll();
            mod.addAttribute("fins", fins);

            return "views/fins";
        }
    }

    @PostMapping("/fins/search")
    public String surfboards(
            @RequestParam String search,
            Model mod){

        List<Fin> fins = finRepository.findByNameContaining(search);
        mod.addAttribute("fins", fins);
        mod.addAttribute("search", search);

        mod.addAttribute("size", "M");

        return "views/fins";
    }

    @GetMapping("/fins/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model mod, Fin fin){

        Fin object = finRepository.findById(id).orElseThrow();
        mod.addAttribute("object", object);
        return "details/fin";
    }

    @PostMapping("/fins/{id}/edit")
    public String blogDetails(@Valid Fin fin, BindingResult bindingResult,
                              @PathVariable(value = "id") long id,
                              @RequestParam String name,
                              Model mod){
        mod.addAttribute("object", fin);

        if (bindingResult.hasErrors()){
            return "details/fin";
        }
        List<Fin> res = finRepository.findByName(name);
        if (res.size()>0){
            ObjectError error = new ObjectError("name", "Имя уже существует");
            bindingResult.addError(error);
            return "details/fin";
        }
        else {
            finRepository.save(fin);
            return "redirect:/fins";
        }
    }

    @GetMapping("/fins/{id}/delete")
    public String surfboardDelete(@PathVariable(value = "id") long id, Model mod){
        Fin fin = finRepository.findById(id).orElseThrow();
        finRepository.delete(fin);

        return "redirect:/fins";
    }
}
