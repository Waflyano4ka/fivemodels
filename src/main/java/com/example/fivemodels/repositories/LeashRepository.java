package com.example.fivemodels.repositories;

import com.example.fivemodels.models.Leashes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LeashRepository extends CrudRepository<Leashes, Long> {
    List<Leashes> findByBrand(String brand);
    List<Leashes> findByName(String name);
}
