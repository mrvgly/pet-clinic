package com.javastudy.petclinic.service;

import com.javastudy.petclinic.exception.OwnerNotFoundException;
import com.javastudy.petclinic.model.Owner;
import com.javastudy.petclinic.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //spring container servis tipinde bir bean yaratacak, owner repository bean'ini runtime'da
        // enjecte edilecektir.
public class PetClinicServiceImpl implements PetClinicService{

    private OwnerRepository ownerRepository;

    @Autowired
    public void setOwnerRepository(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<Owner> findOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public List<Owner> findOwners(String lastname) throws OwnerNotFoundException {
        return ownerRepository.findByLastName(lastname);
    }

    @Override
    public Owner findOwner(Long id) {
        Owner owner = ownerRepository.findById(id);
        if(owner == null) throw  new OwnerNotFoundException("Owner is not found with id: " + id);

        return owner;
    }

    @Override
    public void createOwner(Owner owner) {
        ownerRepository.create(owner);
    }

    @Override
    public void updateOwner(Owner owner) {
        ownerRepository.update(owner);
    }

    @Override
    public void deleteOwner(Long id) {
        ownerRepository.delete(id);
    }
}
