package com.javastudy.petclinic.controller;

import com.javastudy.petclinic.exception.InternalServerException;
import com.javastudy.petclinic.exception.OwnerNotFoundException;
import com.javastudy.petclinic.model.Owner;
import com.javastudy.petclinic.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.hateoas.EntityModel;

import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class PetClinicRestController {
    @Autowired
    private PetClinicService petClinicService;

    @RequestMapping(method = RequestMethod.POST, value="/owner")
    public ResponseEntity<URI> createOwner(@RequestBody Owner owner){
        try {
            petClinicService.createOwner(owner);
            Long id = owner.getId();
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/owner/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable("id") Long id, @RequestBody Owner owner){
        try{
            Owner owner2 = petClinicService.findOwner(id);
            owner2.setLastName(owner.getLastName());
            owner2.setFirstName(owner.getFirstName());
            petClinicService.updateOwner(owner2);
            return ResponseEntity.ok().build();
        }catch (OwnerNotFoundException ex){
            return ResponseEntity.notFound().build();
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/owner/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id){
        try{
            petClinicService.deleteOwner(id);
            return ResponseEntity.ok().build();
        } catch (OwnerNotFoundException ex){
            throw ex;
        } catch (Exception ex) {
            throw new InternalServerException(ex);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/owners")
    public ResponseEntity<List<Owner>> getOwners() {
      List<Owner> owners = petClinicService.findOwners();
      return ResponseEntity.ok(owners);
    }

    @RequestMapping(method = RequestMethod.GET, value="/owner")
    public ResponseEntity<List<Owner>> getOwnerByLastname(@RequestParam("ln") String lastname){
        List<Owner> owners = petClinicService.findOwners(lastname);
        return ResponseEntity.ok(owners);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/owner/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable("id") Long id){
        try {
            Owner owner = petClinicService.findOwner(id);
            return ResponseEntity.ok(owner);
        }
        catch (OwnerNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/owner2/{id}", produces = "application/json")
    public EntityModel<?> getOwnerAsHateoasResource(@PathVariable("id") Long id){
        try {
            Owner owner = petClinicService.findOwner(id);

            EntityModel<Owner> entityModel = EntityModel.of(owner);


            Link self = WebMvcLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner/" + id).withSelfRel();
            Link create = WebMvcLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner").withRel("create");
            Link update = WebMvcLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner/" + id).withRel("update");
            Link delete = WebMvcLinkBuilder.linkTo(PetClinicRestController.class).slash("/owner/" + id).withRel("delete");

            entityModel.add(self,create,update,delete);

            return entityModel;
        }
        catch (OwnerNotFoundException ex){
            throw ex;
        }
    }
}
