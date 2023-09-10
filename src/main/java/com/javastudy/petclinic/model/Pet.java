package com.javastudy.petclinic.model;

import lombok.*;

import java.util.Date;

@Data
public class Pet {
    private Long id;
    private String name;
    private Date birthDate;

    private Owner owner;
}
