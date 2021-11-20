package com.example.fivemodels.repositories;

import com.example.fivemodels.models.Wax;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WaxRepository extends CrudRepository<Wax, Long> {
    List<Wax> findByNameContaining(String name);
    List<Wax> findByName(String name);
}
