package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReservationController {


    @RequestMapping(value="/home/client/company/reservation/{id}", method= RequestMethod.GET)
    public String homeCompany(@PathVariable int id, Model model){
        //User company=userService.findCompanyById(id).get();
        //model.addAttribute("company",company);
        return "reservationAdd.html";
    }
}
