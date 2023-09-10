package com.javastudy.petclinic.service;

import com.javastudy.petclinic.exception.OwnerNotFoundException;
import com.javastudy.petclinic.model.Owner;

import java.util.List;

public interface PetClinicService {
    List<Owner> findOwners();
    List<Owner> findOwners(String lastname) throws OwnerNotFoundException;
    Owner findOwner(Long id);
    void createOwner(Owner owner);
    void updateOwner(Owner owner);

    void deleteOwner(Long id);
}
