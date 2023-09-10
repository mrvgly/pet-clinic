package com.javastudy.petclinic.controller;

import com.javastudy.petclinic.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller//spring container bu sınıftan controller bean'i oluşturacak,
            // bean'deki requestmapping anotasyonlarını tarayarak
            //gelen web isteklerini ilgili metodlarla eşleştirecek
public class PetClinicController {

    @Autowired // bootstrap sırasında controller bean'ine servis bean'i enjeckte edilecek
    private PetClinicService petClinicService;

    @RequestMapping("/owners")
    public ModelAndView getOwners(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("owners", petClinicService.findOwners());
        mav.setViewName("owners");
        return mav;
    }

    @RequestMapping("/psc")
    @ResponseBody //Bu anotasyon gelen web requestlerini dispatch eden
                  //dispatcher servlet, response'u view olarak render etmesin
                  //de body olarak dönsün diye eklenir.
    public String welcome(){
        return "Welcome to PetClinic World";
    }
}
