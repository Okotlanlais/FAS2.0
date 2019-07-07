package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.Reservation;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.IOfferService;
import com.fas.fotomania.fotomania.services.interfaces.IReservationService;
import com.fas.fotomania.fotomania.services.interfaces.IScheduleService;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ReservationController {

    @Autowired
    IUserService userService;

    @Autowired
    IReservationService reservationService;

    @Autowired
    IScheduleService scheduleService;

    @Autowired
    IOfferService offerService;

    @RequestMapping(value="/home/client/company/reservation/add/{id}", method= RequestMethod.GET)
    public String createAddReservation(@PathVariable int id,Model model){
        User company=userService.findCompanyById(id).get();
        if(scheduleService.daysOfWork(company.getId()).size()!=0 && offerService.findOffersByCompany(company.getId()).size()!=0){
            model.addAttribute("daysWork",scheduleService.daysOfWork(company.getId()));
            model.addAttribute("schedule",scheduleService.findScheduleByCompany(company.getId()));
            model.addAttribute("offers",offerService.findOffersByCompany(company.getId()));
            model.addAttribute("currentCompany", company);
            model.addAttribute("reservation", new Reservation());

            return "reservationAdd.html";
        }
        return String.format("redirect:/home/client/company/%d",id);
    }

    @RequestMapping(value = "/home/client/company/reservation/add", method = RequestMethod.POST)
    public String saveReservation(@Valid Reservation reservation, BindingResult bindingResult, RedirectAttributes redirectAttribute, Principal principal, @ModelAttribute("companyId")int companyid){
        //System.out.println("Start hour"+ reservation.getStartHour());
        if (bindingResult.hasErrors()){
            redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
            redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
        }else {
            //System.out.println(reservation.getStartHour());
            if(companyid != 0 && reservation.getStartHour() != 0 && reservation.getEndHour() != 0){
               int startHour = scheduleService.findScheduleByCompany(companyid).getStartHour();
               int endHour = scheduleService.findScheduleByCompany(companyid).getEndHour();
               System.out.println(reservationService.checkAvailability(companyid,reservation.getStartHour(),reservation.getEndHour(),reservation.getDay()));
               boolean available=reservationService.checkAvailability(companyid,reservation.getStartHour(),reservation.getEndHour(),reservation.getDay());
               if((reservation.getStartHour()<reservation.getEndHour())&&(reservation.getStartHour()>=startHour&&reservation.getEndHour()<=endHour)){
                   if(available){
                       User currentUser=userService.findUserByEmail(principal.getName());
                       Optional<User> company=userService.findCompanyById(companyid);
                       reservation.setClient(currentUser);
                       reservation.setCompany(company.get());
                       reservation.setCompleted(false);
                       reservation.setCode(reservationService.generateRandomCode(10));
                       reservationService.saveReservation(reservation);
                       redirectAttribute.addFlashAttribute("successMessage", "Reservation registered successfully");
                       return "redirect:/home/client/company/reservation";
                   }else{
                       redirectAttribute.addFlashAttribute("errorMessage","Sorry, that hour is already taken :(");
                       return String.format("redirect:/home/client/company/reservation/add/%d",companyid);
                   }
                }else{
                   redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
               }
            }else{
                redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
            }
        }
        return String.format("redirect:/home/client/company/reservation/add/%d",companyid);
    }

    @RequestMapping(value="/home/client/company/reservation", method= RequestMethod.GET)
    public String listReservation(Principal principal, Model model){
        User client = userService.findUserByEmail(principal.getName());
        model.addAttribute("reservations", reservationService.findReservationsByClient(client.getId()));
        return "reservationList.html";
    }

    @RequestMapping(value="/home/company/client/reservation", method= RequestMethod.GET)
    public String loadEndReservation(Principal principal, Model model){
        User company = userService.findUserByEmail(principal.getName());
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("reservations", reservationService.findReservationsByCompany(company.getId()));
        return "reservationEnd.html";
    }

    @RequestMapping(value="/home/company/client/reservation/end", method= RequestMethod.POST)
    public String endReservation(RedirectAttributes redirectAttribute,Principal principal,@ModelAttribute("code")String code){
        User company = userService.findUserByEmail(principal.getName());
        for (Reservation reservation: reservationService.findReservationsByCompany(company.getId())) {
            if(reservation.getCode().equals(code)){
                reservation.setCompleted(true);
                reservationService.saveReservation(reservation);
                redirectAttribute.addFlashAttribute("successMessage", "Reservation ended");
                return "redirect:/home/company";
            }
        }
        redirectAttribute.addFlashAttribute("errorMessage", "No reservation with that code");
        return "redirect:/home/company/client/reservation";
    }
}
