package com.example.fivemodels.controllers;

import com.example.fivemodels.models.Brand;
import com.example.fivemodels.models.Fin;
import com.example.fivemodels.models.Leashes;
import com.example.fivemodels.models.Wax;
import com.example.fivemodels.repositories.BrandRepository;
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
public class BrandsController {

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("/brands")
    public String surfboards(Model mod, Brand brand){
        Iterable<Brand> brands = brandRepository.findAll();
        mod.addAttribute("brands", brands);

        return "views/brands";
    }

    @PostMapping("/brands")
    public String surfboards(@Valid Brand brand, BindingResult bindingResult,
                             @RequestParam String name,
                             Model mod){
        Iterable<Brand> brands = brandRepository.findAll();
        mod.addAttribute("name", name);

        if (bindingResult.hasErrors()){
            mod.addAttribute("brands", brands);
            return "views/brands";
        }
        List<Brand> res = brandRepository.findByName(name);
        if (res.size()>0){
            ObjectError error = new ObjectError("name", "Имя уже существует");
            bindingResult.addError(error);
            mod.addAttribute("brands", brands);
            return "views/brands";
        }
        else {
            brandRepository.save(brand);

            brands = brandRepository.findAll();
            mod.addAttribute("brands", brands);

            return "views/brands";
        }
    }

    @PostMapping("/brands/search")
    public String searchbrands(
            @RequestParam String search,
            Model mod){

        List<Brand> brands = brandRepository.findByNameContaining(search);
        mod.addAttribute("brands", brands);
        mod.addAttribute("search", search);
        return "views/brands";
    }

    @GetMapping("/brands/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model mod, Brand brand){

        Brand object = brandRepository.findById(id).orElseThrow();
        mod.addAttribute("object", object);
        return "details/brand";
    }

    @PostMapping("/brands/{id}/edit")
    public String blogDetails(@Valid Brand brand, BindingResult bindingResult,
                              @PathVariable(value = "id") long id,
                              @RequestParam String name,
                              Model mod){
        mod.addAttribute("object", brand);

        if (bindingResult.hasErrors()){
            return "details/brand";
        }
        List<Brand> res = brandRepository.findByName(name);
        if (res.size()>0){
            ObjectError error = new ObjectError("name", "Имя уже существует");
            bindingResult.addError(error);
            return "details/brand";
        }
        else {
            brandRepository.save(brand);
            return "redirect:/brands";
        }
    }

    @GetMapping("/brands/{id}/delete")
    public String surfboardDelete(@PathVariable(value = "id") long id, Model mod){
        Brand brand = brandRepository.findById(id).orElseThrow();
        brandRepository.delete(brand);

        return "redirect:/brands";
    }
}
