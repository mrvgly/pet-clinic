package com.javastudy.petclinic.repository.mem;

import com.javastudy.petclinic.model.Owner;
import com.javastudy.petclinic.repository.OwnerRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository //çalışma zamanında bir bean yaratılacak, bu bean servis bean'e enjekte edilecek
public class OwnerRepositoryInMemoryImpl implements OwnerRepository {
    private Map<Long, Owner> ownersMap = new HashMap<>();

    public OwnerRepositoryInMemoryImpl(){
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("merve");
        owner1.setLastName("gozukizil");

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("emre");
        owner2.setLastName("gozukizil");

        Owner owner3 = new Owner();
        owner3.setId(3L);
        owner3.setFirstName("cemre");
        owner3.setLastName("gozukizil");

        Owner owner4 = new Owner();
        owner4.setId(5L);
        owner4.setFirstName("asya");
        owner4.setLastName("gozukizil");

        ownersMap.put(owner1.getId(), owner1);
        ownersMap.put(owner2.getId(), owner2);
        ownersMap.put(owner3.getId(), owner3);
        ownersMap.put(owner4.getId(), owner4);
    }

    @Override
    public List<Owner> findAll() {
        return new ArrayList<>(ownersMap.values());
    }

    @Override
    public Owner findById(Long id) {
        return ownersMap.get(id);
    }

    @Override
    public List<Owner> findByLastName(String lastname) {
        return ownersMap.values().stream().filter(x->x.getLastName().equals(lastname)).collect(Collectors.toList());
    }

    @Override
    public void create(Owner owner) {
        owner.setId(new Date().getTime());
        ownersMap.put(owner.getId(), owner);
    }

    @Override
    public Owner update(Owner owner) {
        ownersMap.replace(owner.getId(), owner);
        return owner;
    }

    @Override
    public void delete(Long id) {
        ownersMap.remove(id);
    }
}
