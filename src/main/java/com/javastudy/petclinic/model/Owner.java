package com.javastudy.petclinic.model;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
public class Owner {
    private Long id;
    private String firstName;
    private String lastName;

    private Set<Pet> pets = new HashSet<>();
}
