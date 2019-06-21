package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.Specialty;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.ISpecialtyService;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class SpecialtyController {

    @Autowired
    ISpecialtyService specialtyService;

    @Autowired
    IUserService userService;

    @RequestMapping(value="/home/company/specialty", method= RequestMethod.GET)
    public String listSpecialties(Model model, Principal principal) {
        User currentUser=userService.findUserByEmail(principal.getName());
        model.addAttribute("specialties",specialtyService.findSpecialtyByCompany(currentUser.getId()));
        return "specialtyList.html";
    }

    @RequestMapping(value = "/home/company/specialty/add", method = RequestMethod.GET)
    public String createAddSpecialty(Model model){
        model.addAttribute("specialty", new Specialty());

        return "specialtyAdd.html";
    }
    @RequestMapping(value = "/home/company/specialty/add", method = RequestMethod.POST)
    public String saveSpecialty(@Valid Specialty specialty, BindingResult bindingResult, RedirectAttributes redirectAttribute, Principal principal){
        if (bindingResult.hasErrors()){
            redirectAttribute.addFlashAttribute("message","Correct the errors in the form");
            redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
        }else {
            User currentUser=userService.findUserByEmail(principal.getName());
            specialty.setUser(currentUser);
            specialtyService.saveSpecialty(specialty);
            redirectAttribute.addFlashAttribute("message", "Specialty registered successfully");
            return "redirect:/home/company/specialty";
        }
        return "redirect:/home/company/specialty/add";
    }
}
