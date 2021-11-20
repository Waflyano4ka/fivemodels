package com.example.fivemodels.repositories;

import com.example.fivemodels.models.Fin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FinRepository extends CrudRepository<Fin, Long> {
    List<Fin> findByNameContaining(String name);
    List<Fin> findByName(String name);
}
