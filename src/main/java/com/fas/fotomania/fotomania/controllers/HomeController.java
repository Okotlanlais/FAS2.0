package com.fas.fotomania.fotomania.controllers;


import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.print.DocFlavor;
import java.security.Principal;


@Controller
public class HomeController {

    @Autowired
    IUserService userService;

    @RequestMapping(value="/home", method= RequestMethod.GET)
    public String home(Principal principal){
        final User currentUser= userService.findUserByEmail(principal.getName());

        if (currentUser.getCompany().equals("true")){
            return "redirect:/home/company";
        } else {
            return "redirect:/home/client";
        }
    }

    @RequestMapping(value="/home/company", method= RequestMethod.GET)
    public String homeCompany(Principal principal){
        return "homeCompany.html";
    }

    @RequestMapping(value="/home/client", method=RequestMethod.GET)
    public String homeClient(Principal principal){
        return "homeClient.html";
    }
}
