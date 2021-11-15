package com.example.fivemodels.controllers;

import com.example.fivemodels.models.Brand;
import com.example.fivemodels.models.Wax;
import com.example.fivemodels.repositories.BrandRepository;
import com.example.fivemodels.repositories.WaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BrandsController {

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("/brands")
    public String surfboards(Model mod){
        Iterable<Brand> brands = brandRepository.findAll();
        mod.addAttribute("brands", brands);

        return "views/brands";
    }

    @PostMapping("/brands")
    public String surfboards(
            @RequestParam String name,
            Model mod){

        Brand brand = new Brand(name);
        brandRepository.save(brand);

        Iterable<Brand> brands = brandRepository.findAll();
        mod.addAttribute("brands", brands);

        mod.addAttribute("name", name);
        return "views/brands";
    }
}
