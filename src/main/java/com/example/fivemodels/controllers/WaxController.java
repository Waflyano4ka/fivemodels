package com.example.fivemodels.controllers;

import com.example.fivemodels.models.Brand;
import com.example.fivemodels.models.Surfboards;
import com.example.fivemodels.models.Wax;
import com.example.fivemodels.repositories.WaxRepository;
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
public class WaxController {

    @Autowired
    private WaxRepository waxRepository;

    @GetMapping("/waxes")
    public String surfboards(Model mod, Wax wax){
        Iterable<Wax> waxes = waxRepository.findAll();
        mod.addAttribute("waxes", waxes);

        return "views/waxes";
    }

    @PostMapping("/waxes")
    public String surfboards(@Valid Wax wax, BindingResult bindingResult,
                             @RequestParam String name,
                             @RequestParam String temperature,
                             @RequestParam String weight,
                             Model mod){
        Iterable<Wax> waxes = waxRepository.findAll();

        mod.addAttribute("name", name);
        mod.addAttribute("temperature", temperature);
        mod.addAttribute("weight", weight);

        if (bindingResult.hasErrors()){
            mod.addAttribute("waxes", waxes);
            return "views/waxes";
        }
        List<Wax> res = waxRepository.findByName(name);
        if (res.size()>0){
            ObjectError error = new ObjectError("name", "Имя уже существует");
            bindingResult.addError(error);
            mod.addAttribute("waxes", waxes);
            return "views/waxes";
        }
        else {
            waxRepository.save(wax);

            waxes = waxRepository.findAll();
            mod.addAttribute("waxes", waxes);

            return "views/waxes";
        }
    }

    @PostMapping("/waxes/search")
    public String surfboards(
            @RequestParam String search,
            Model mod){

        List<Wax> waxes = waxRepository.findByNameContaining(search);
        mod.addAttribute("waxes", waxes);
        mod.addAttribute("search", search);
        return "views/waxes";
    }

    @GetMapping("/waxes/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model mod, Wax wax){

        Wax object = waxRepository.findById(id).orElseThrow();
        mod.addAttribute("object", object);
        return "details/wax";
    }

    @PostMapping("/waxes/{id}/edit")
    public String blogDetails(@Valid Wax wax, BindingResult bindingResult,
                              @PathVariable(value = "id") long id,
                              @RequestParam String name,
                              @RequestParam String temperature,
                              @RequestParam String weight,
                              Model mod){
        mod.addAttribute("object", wax);

        if (bindingResult.hasErrors()){
            return "details/wax";
        }
        List<Wax> res = waxRepository.findByName(name);
        if (res.size()>0){
            ObjectError error = new ObjectError("name", "Имя уже существует");
            bindingResult.addError(error);
            return "details/wax";
        }
        else {
            waxRepository.save(wax);
            return "redirect:/waxes";
        }
    }

    @GetMapping("/waxes/{id}/delete")
    public String surfboardDelete(@PathVariable(value = "id") long id, Model mod){
        Wax wax = waxRepository.findById(id).orElseThrow();
        waxRepository.delete(wax);

        return "redirect:/waxes";
    }
}
