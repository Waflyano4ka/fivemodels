package com.example.fivemodels.repositories;

import com.example.fivemodels.models.Surfboards;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SurfboardRepository extends CrudRepository<Surfboards, Long> {
    List<Surfboards> findByBrand(String brand);
    List<Surfboards> findByName(String name);
}
