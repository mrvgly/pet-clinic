package com.javastudy.petclinic.controller;

import com.javastudy.petclinic.model.Owner;
import org.apache.tomcat.util.file.Matcher;
import org.assertj.core.api.Assert;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PetClinicRestContollerTest {
    private RestTemplate restTemplate;

    @Before
    public void setUp(){
        restTemplate = new RestTemplate();
    }

    @Test
    public void testGetOwnerById(){
        ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/5", Owner.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(),Matchers.equalTo("asya"));
    }

    @Test
    public void testGetOwnersByLastname(){
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owner?ln=gozukizil", List.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

        List<Map<String, String>> body = response.getBody();

        List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("emre", "merve", "asya", "cemre"));
    }

    @Test
    public void testGetOwners(){
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);

        List<Map<String, String>> body = response.getBody();

        List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("emre","merve","asya", "cemre"));
    }

    @Test
    public void testCreateOwner(){
        Owner owner = new Owner();
        owner.setFirstName("gonul");
        owner.setLastName("ozpulat");

        URI location = restTemplate.postForLocation("http://localhost:8080/rest/owner", owner);

        Owner owner2 = restTemplate.getForObject(location, Owner.class);

        MatcherAssert.assertThat(owner2.getFirstName(), Matchers.equalTo(owner.getFirstName()));
    }

    @Test
    public void testUpdateOwner(){
        Owner owner = restTemplate.getForObject("http://localhost:8080/rest/owner/2", Owner.class);
        MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("emre"));

        owner.setFirstName("emrem");
        restTemplate.put("http://localhost:8080/rest/owner/2", owner);

        owner = restTemplate.getForObject("http://localhost:8080/rest/owner/2", Owner.class);

        MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("emrem"));
    }

    @Test
    public void testDeleteOwner(){
        restTemplate.delete("http://localhost:8080/rest/owner/1");

        try {
            restTemplate.getForObject("http://localhost:8080/rest/owner/1", Owner.class);
           // Assert.fail()
        } catch (HttpClientErrorException ex){
            MatcherAssert.assertThat(ex.getStatusCode().value(), Matchers.equalTo(404));
        }
    }
}
