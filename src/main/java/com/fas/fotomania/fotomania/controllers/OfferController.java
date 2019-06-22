package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.Offer;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.IOfferService;
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
public class OfferController {
    @Autowired
    IUserService userService;

    @Autowired
    IOfferService offerService;

    @RequestMapping(value="/home/company/offer", method= RequestMethod.GET)
    public String listOffers(Model model, Principal principal) {
        User currentUser=userService.findUserByEmail(principal.getName());
        model.addAttribute("offers",offerService.findOffersByCompany(currentUser.getId()));
        return "offerList.html";
    }

    @RequestMapping(value = "/home/company/offer/add", method = RequestMethod.GET)
    public String createAddOffer(Model model){
        model.addAttribute("offer", new Offer());

        return "offerAdd.html";
    }

    @RequestMapping(value = "/home/company/offer/add", method = RequestMethod.POST)
    public String saveOffer(@Valid Offer offer, BindingResult bindingResult, RedirectAttributes redirectAttribute, Principal principal){
        if (bindingResult.hasErrors()){
            redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
            redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
        }else {
            User currentUser=userService.findUserByEmail(principal.getName());
            offer.setUser(currentUser);
            offerService.saveOffer(offer);
            redirectAttribute.addFlashAttribute("successMessage", "Specialty registered successfully");
            return "redirect:/home/company/offer";
        }
        return "redirect:/home/company/offer/add";
    }

    @RequestMapping(value = "/home/company/offer/delete/{id}", method = RequestMethod.GET)
    public String deleteOffer(@PathVariable int id, RedirectAttributes redirectAttribute, Principal principal){
        Optional<Offer> currentOffer= offerService.findById(id);
        User currentUser = userService.findUserByEmail(principal.getName());
        if (currentOffer.get().getUser().getId()==currentUser.getId()){
            offerService.deleteOffer(currentOffer.get());
            redirectAttribute.addFlashAttribute("successMessage", "Delete completed");
        }else{
            redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
        }
        return "redirect:/home/company/offer";
    }

    @RequestMapping(value = "/home/company/offer/update/{id}", method = RequestMethod.GET)
    public String updateOffer(@PathVariable int id, RedirectAttributes redirectAttribute, Principal principal, Model model){
        Optional<Offer> currentOffer= offerService.findById(id);
        User currentUser = userService.findUserByEmail(principal.getName());
        if (currentOffer.get().getUser().getId()==currentUser.getId()){
            model.addAttribute("offer", currentOffer.get());
        }else{
            redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
            return "redirect:/home/company/offer";
        }
        return "offerUpdate.html";
    }

    @RequestMapping(value = "/home/company/offer/update", method = RequestMethod.POST)
    public String saveOfferUpdate(@Valid Offer offer, BindingResult bindingResult, RedirectAttributes redirectAttribute, Principal principal){
        User currentUser = userService.findUserByEmail(principal.getName());
        if (bindingResult.hasErrors()){
            redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
            redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
        }else {
            int id = offer.getId();
            Optional<Offer> currentOffer= offerService.findById(id);
            if (currentOffer.get().getUser().getId()==currentUser.getId()){
                offer.setUser(currentUser);
                offerService.updateOffer(offer);
                redirectAttribute.addFlashAttribute("successMessage", "Offer updated successfully");
                return "redirect:/home/company/offer";
            } else {
                redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
            }
        }
        return String.format("redirect:/home/company/offer/update/%d",offer.getId());
    }
}
