package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.Photo;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.IPhotoService;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class PhotoController {

    @Autowired
    IPhotoService photoService;

    @Autowired
    IUserService userService;

    @RequestMapping(value="/home/company/photo", method= RequestMethod.GET)
    public String listTools(Model model, Principal principal) {
        User currentUser=userService.findUserByEmail(principal.getName());
        model.addAttribute("tools",photoService.findPhotosByCompany(currentUser.getId()));
        return "photoList.html";
    }

    @RequestMapping(value = "/home/company/photo/add", method = RequestMethod.GET)
    public String createAddSpecialty(Model model){
        model.addAttribute("photo", new Photo());

        return "photoAdd.html";
    }
}
