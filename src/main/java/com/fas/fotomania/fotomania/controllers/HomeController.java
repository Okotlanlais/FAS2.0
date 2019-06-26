package com.fas.fotomania.fotomania.controllers;


import com.fas.fotomania.fotomania.entities.CompanyView;
import com.fas.fotomania.fotomania.entities.Photo;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.IPhotoService;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.print.DocFlavor;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    IUserService userService;

    @Autowired
    IPhotoService photoService;

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
    public String homeCompany(){
        return "homeCompany.html";
    }

    @RequestMapping(value="/home/client", method=RequestMethod.GET)
    public String homeClient(Model model){
        List<CompanyView> viewsInfo = new ArrayList<>();
        List<User> companies = userService.findAllCompanies();

    for (User company:companies) {
            List<Photo> companyPhotos = photoService.findPhotosByCompany(company.getId());
            CompanyView companyView= new CompanyView();
            companyView.setId(company.getId());
            companyView.setName(company.getName());
            if(companyPhotos.size()>0){
                companyView.setPhotoURL("/home/client/photo/"+companyPhotos.get(0).getId());
            }else{
                companyView.setPhotoURL("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/768px-No_image_available.svg.png");
            }
            viewsInfo.add(companyView);
        }
        model.addAttribute("views", viewsInfo);


        return "homeClient.html";
    }

    @RequestMapping(value="/home/client/company/{id}", method= RequestMethod.GET)
    public String homeCompany(@PathVariable int id, Model model){
        User company=userService.findCompanyById(id).get();
        model.addAttribute("company",company);
        return "companyView.html";
    }
}
