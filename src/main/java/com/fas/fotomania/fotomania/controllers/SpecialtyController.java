package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.Specialty;
import com.fas.fotomania.fotomania.entities.Tool;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.ISpecialtyService;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

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
            redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
            redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
        }else {
            User currentUser=userService.findUserByEmail(principal.getName());
            specialty.setUser(currentUser);
            specialtyService.saveSpecialty(specialty);
            redirectAttribute.addFlashAttribute("successMessage", "Specialty registered successfully");
            return "redirect:/home/company/specialty";
        }
        return "redirect:/home/company/specialty/add";
    }

    @RequestMapping(value = "/home/company/specialty/delete/{id}", method = RequestMethod.GET)
    public String deleteSpecialty(@PathVariable int id, RedirectAttributes redirectAttribute, Principal principal){
        Optional<Specialty> currentSpecialty= specialtyService.findById(id);
        User currentUser = userService.findUserByEmail(principal.getName());
        if (currentSpecialty.get().getUser().getId()==currentUser.getId()){
            specialtyService.deleteSpecialty(currentSpecialty.get());
            redirectAttribute.addFlashAttribute("successMessage", "Delete completed");
        }else{
            redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
        }
        return "redirect:/home/company/specialty";
    }

    @RequestMapping(value = "/home/company/specialty/update/{id}", method = RequestMethod.GET)
    public String updateSpecialty(@PathVariable int id, RedirectAttributes redirectAttribute, Principal principal, Model model){
        Optional<Specialty> currentSpecialty= specialtyService.findById(id);
        User currentUser = userService.findUserByEmail(principal.getName());
        if (currentSpecialty.get().getUser().getId()==currentUser.getId()){
            model.addAttribute("specialty", currentSpecialty.get());
        }else{
            redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
            return "redirect:/home/company/specialty";
        }
        return "specialtyUpdate.html";
    }

    @RequestMapping(value = "/home/company/specialty/update", method = RequestMethod.POST)
    public String saveSpecialtyUpdate(@Valid Specialty specialty, BindingResult bindingResult, RedirectAttributes redirectAttribute, Principal principal){
        User currentUser = userService.findUserByEmail(principal.getName());
        if (bindingResult.hasErrors()){
            redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
            redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
        }else {
            int id = specialty.getId();
            Optional<Specialty> currentSpecialty= specialtyService.findById(id);
            if (currentSpecialty.get().getUser().getId()==currentUser.getId()){
                specialty.setUser(currentUser);
                specialtyService.updateSpecialty(specialty);
                redirectAttribute.addFlashAttribute("successMessage", "Specialty updated successfully");
                return "redirect:/home/company/specialty";
            } else {
                redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
            }
        }
        return String.format("redirect:/home/company/specialty/update/%d",specialty.getId());
    }
}
