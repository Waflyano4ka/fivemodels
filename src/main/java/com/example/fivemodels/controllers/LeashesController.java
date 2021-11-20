package com.example.fivemodels.controllers;

import com.example.fivemodels.models.Brand;
import com.example.fivemodels.models.Leashes;
import com.example.fivemodels.repositories.LeashRepository;
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
import java.util.List;

@Controller
public class LeashesController {

    @Autowired
    private LeashRepository leashRepository;

    @GetMapping("/leashes")
    public String surfboards(Model mod, Leashes leashes){
        Iterable<Leashes> leash = leashRepository.findAll();
        mod.addAttribute("leash", leash);

        return "views/leashes";
    }

    @PostMapping("/leashes")
    public String surfboards(@Valid Leashes leashes, BindingResult bindingResult,
                             @RequestParam String name,
                             @RequestParam String brand,
                             @RequestParam String size,
                             Model mod){
        Iterable<Leashes> leash = leashRepository.findAll();

        mod.addAttribute("name", name);
        mod.addAttribute("brand", brand);
        mod.addAttribute("size", size);

        if (bindingResult.hasErrors()){
            mod.addAttribute("leash", leash);
            return "views/leashes";
        }
        List<Leashes> res = leashRepository.findByName(name);
        if (res.size()>0){
            ObjectError error = new ObjectError("name", "Имя уже существует");
            bindingResult.addError(error);
            mod.addAttribute("leash", leash);
            return "views/leashes";
        }
        else {
            leashRepository.save(leashes);

            leash = leashRepository.findAll();
            mod.addAttribute("leash", leash);

            return "views/leashes";
        }
    }

    @PostMapping("/leashes/search")
    public String surfboards(
            @RequestParam String search,
            Model mod){

        List<Leashes> leashes = leashRepository.findByBrand(search);
        mod.addAttribute("leashes", leashes);
        mod.addAttribute("search", search);
        return "views/leashes";
    }

    @GetMapping("/leashes/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model mod, Leashes leashes){

        Leashes object = leashRepository.findById(id).orElseThrow();
        mod.addAttribute("object", object);
        return "details/leash";
    }

    @PostMapping("/leashes/{id}/edit")
    public String blogDetails(@Valid Leashes leashes, BindingResult bindingResult,
                              @PathVariable(value = "id") long id,
                              @RequestParam String name,
                              Model mod){
        mod.addAttribute("object", leashes);

        if (bindingResult.hasErrors()){
            return "details/leash";
        }
        List<Leashes> res = leashRepository.findByName(name);
        if (res.size()>0){
            ObjectError error = new ObjectError("name", "Имя уже существует");
            bindingResult.addError(error);
            return "details/leash";
        }
        else {
            leashRepository.save(leashes);
            return "redirect:/leashes";
        }
    }

    @GetMapping("/leashes/{id}/delete")
    public String surfboardDelete(@PathVariable(value = "id") long id, Model mod){
        Leashes leashes = leashRepository.findById(id).orElseThrow();
        leashRepository.delete(leashes);

        return "redirect:/leashes";
    }

//    public boolean eq(List<Leashes> list, Leashes el) {
//        var b = false;
//        list.forEach(i -> {
//             if (i.getId() == el.getId()){
//                 b = true;
//             }
//        });
//        return b;
//    }
}
