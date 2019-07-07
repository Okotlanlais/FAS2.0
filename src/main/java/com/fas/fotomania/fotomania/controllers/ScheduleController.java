package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.Schedule;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.IScheduleService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ScheduleController {

    @Autowired
    IScheduleService scheduleService;

    @Autowired
    IUserService userService;

    @RequestMapping(value="/home/company/schedule", method= RequestMethod.GET)
    public String listSchedule(Model model, Principal principal) {
        User currentUser=userService.findUserByEmail(principal.getName());
        model.addAttribute("schedule",scheduleService.findScheduleByCompany(currentUser.getId()));
        //System.out.println(scheduleService.findScheduleByCompany(currentUser.getId()));
        List<Integer> hours = new ArrayList<>();
        for( int i=7;i<=21;i++){
            hours.add(i);
        }
        model.addAttribute("hours",hours);
        return "scheduleList.html";
    }

    @RequestMapping(value="/home/company/schedule/add",method = RequestMethod.GET)
    public String createAddSchedule(Model model){
        model.addAttribute("schedule",new Schedule());
        return "scheduleAdd.html";
    }

    @RequestMapping(value = "/home/company/schedule/add", method = RequestMethod.POST)
    public String saveSchedule(@Valid Schedule schedule, BindingResult bindingResult, RedirectAttributes redirectAttribute, Principal principal){
        if (bindingResult.hasErrors()){
            redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
            redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
        }else {
            User currentUser=userService.findUserByEmail(principal.getName());
            if(currentUser.getSchedule()!=null){
                redirectAttribute.addFlashAttribute("errorMessage","You can only have one schedule, go to update");
            }else{
                if(schedule.isFriday()||schedule.isMonday()||schedule.isSaturday()||schedule.isSunday()||schedule.isThursday()||schedule.isTuesday()||schedule.isWednesday()){
                    if(schedule.getStartHour()<schedule.getEndHour()){
                        schedule.setUser(currentUser);
                        scheduleService.saveSchedule(schedule);
                        redirectAttribute.addFlashAttribute("successMessage", "Schedule registered successfully");
                        return "redirect:/home/company/schedule";
                    }else{
                        redirectAttribute.addFlashAttribute("errorMessage","Start hour must be less than end hour");
                    }
                }else{
                    redirectAttribute.addFlashAttribute("errorMessage","Select at least one day");
                }
            }
        }
        return "redirect:/home/company/schedule/add";
    }

    @RequestMapping(value = "/home/company/schedule/delete/{id}", method = RequestMethod.GET)
    public String deleteSchedule(@PathVariable int id, RedirectAttributes redirectAttribute, Principal principal){
        Optional<Schedule> currentSchedule= scheduleService.findById(id);
        User currentUser = userService.findUserByEmail(principal.getName());
        if (currentSchedule.get().getUser().getId()==currentUser.getId()){
            scheduleService.deleteSchedule(currentSchedule.get());
            redirectAttribute.addFlashAttribute("successMessage", "Delete completed");
        }else{
            redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
        }
        return "redirect:/home/company/schedule";
    }

    @RequestMapping(value = "/home/company/schedule/update/{id}", method = RequestMethod.GET)
    public String updateSchedule(@PathVariable int id, RedirectAttributes redirectAttribute, Principal principal, Model model){
        Optional<Schedule> currentSchedule= scheduleService.findById(id);
        User currentUser = userService.findUserByEmail(principal.getName());
        if (currentSchedule.get().getUser().getId()==currentUser.getId()){
            model.addAttribute("schedule", currentSchedule.get());
        }else{
            redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
            return "redirect:/home/company/schedule";
        }
        return "scheduleUpdate.html";
    }

    @RequestMapping(value = "/home/company/schedule/update", method = RequestMethod.POST)
    public String saveScheduleUpdate(@Valid Schedule schedule, BindingResult bindingResult, RedirectAttributes redirectAttribute, Principal principal){
        User currentUser = userService.findUserByEmail(principal.getName());
        if (bindingResult.hasErrors()){
            redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
            redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
        }else {
            int id = schedule.getId();
            Optional<Schedule> currentSchedule= scheduleService.findById(id);
            if (currentSchedule.get().getUser().getId()==currentUser.getId()){
                if(schedule.isFriday()||schedule.isMonday()||schedule.isSaturday()||schedule.isSunday()||schedule.isThursday()||schedule.isTuesday()||schedule.isWednesday()){
                    if(schedule.getStartHour()<schedule.getEndHour()){
                        schedule.setUser(currentUser);
                        scheduleService.updateSchedule(schedule);
                        redirectAttribute.addFlashAttribute("successMessage", "Schedule updated successfully");
                        return "redirect:/home/company/schedule";
                    }else{
                        redirectAttribute.addFlashAttribute("errorMessage","Start hour must be less than end hour");
                    }
                }else{
                    redirectAttribute.addFlashAttribute("errorMessage","Select at least one day");
                }
            } else {
                redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
            }
        }
        return String.format("redirect:/home/company/schedule/update/%d",schedule.getId());
    }
}
