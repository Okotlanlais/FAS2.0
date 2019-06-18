package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthenticationController {

    @Autowired
    IUserService userService;

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String login(){
        return "login.html";
    }

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register.html";
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String registerUser(@Valid User user,  BindingResult bindingResult, RedirectAttributes redirectAttribute){
            //System.out.println("Entro al metodo post");
        if(bindingResult.hasErrors()){
            redirectAttribute.addFlashAttribute("message","Correct the errors in the form");
            redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
            //System.out.println("Encontro errores con las validaciones");
        } else if(userService.isUserAlreadyPresent(user)){
            /*
            redirectAttribute.addFlashAttribute("message", "User already exists");
            */
            //System.out.println("Encontro duplicado");
        } else {
            userService.saveUser(user);
            redirectAttribute.addFlashAttribute("message","User registered successfully");
            //System.out.println("Registro!");
            return "redirect:/login";
        }
        return "redirect:/register";
    }
}
