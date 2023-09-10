package com.javastudy.petclinic;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="petclinic")
public class PetClinicProperties {
    private boolean displayOwnersWithPets = false;

    public void setDisplayOwnersWithPets(boolean displayOwnersWithPets) {
        this.displayOwnersWithPets = displayOwnersWithPets;
    }

    public boolean getDisplayOwnersWithPets() {
        return displayOwnersWithPets;
    }
}
