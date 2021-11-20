package com.example.fivemodels.repositories;

import com.example.fivemodels.models.Brand;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BrandRepository extends CrudRepository<Brand, Long>  {
    List<Brand> findByNameContaining(String name);
    List<Brand> findByName(String name);
}
