package com.javastudy.petclinic.repository;

import com.javastudy.petclinic.model.Pet;
import java.util.List;

public interface PetRepository {
    List<Pet> findAll();
    Pet findById(Long id);
    List<Pet> findByOwnerId(Long ownerId);

    void create(Pet pet);
    Pet update(Pet pet);
    void delete(Long id);
    void deleteByOwnerId(Long ownerId);
}
