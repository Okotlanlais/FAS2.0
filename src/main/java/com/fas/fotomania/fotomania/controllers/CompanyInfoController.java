package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.CompanyInfo;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.ICompanyInfoService;
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
public class CompanyInfoController {

    @Autowired
    IUserService userService;

    @Autowired
    ICompanyInfoService companyInfoService;

    @RequestMapping(value = "/home/company/info/add", method = RequestMethod.GET)
    public String createAddCompanyInfo(Model model){
        model.addAttribute("companyInfo", new CompanyInfo());

        return "companyInfoAdd.html";
    }

    @RequestMapping(value = "/home/company/info/add", method = RequestMethod.POST)
    public String saveCompanyInfo(@Valid CompanyInfo companyInfo, BindingResult bindingResult, RedirectAttributes redirectAttribute, Principal principal){
        User currentUser=userService.findUserByEmail(principal.getName());
        if (bindingResult.hasErrors()){
            redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
            redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
        }else {
            if(currentUser.getCompanyInfo()!=null){
                redirectAttribute.addFlashAttribute("errorMessage","You can only have one Company Info, go to update");
            }else{
                companyInfo.setUser(currentUser);
                companyInfo.setVipLevel(0);
                companyInfoService.saveCompanyInfo(companyInfo);
                redirectAttribute.addFlashAttribute("successMessage", "Company Info registered successfully");
                return "redirect:/home/company/info";
            }
        }
        return "redirect:/home/company/info/add";
    }

    @RequestMapping(value = "/home/company/info", method = RequestMethod.GET)
    public String listCompanyInfo(Model model, Principal principal){
        User currentUser=userService.findUserByEmail(principal.getName());

        model.addAttribute("companyInfo", companyInfoService.findCompanyInfosByCompany(currentUser.getId()));

        return "companyInfoList.html";
    }

    @RequestMapping(value = "/home/company/info/update/{id}", method = RequestMethod.GET)
    public String loadUpdateCompanyInfo(@PathVariable int id, RedirectAttributes redirectAttribute, Principal principal, Model model){
        Optional<CompanyInfo> currentCompanyInfo= companyInfoService.findById(id);
        User currentUser = userService.findUserByEmail(principal.getName());
        if (currentCompanyInfo.get().getUser().getId()==currentUser.getId()){
            model.addAttribute("companyInfo", currentCompanyInfo.get());
        }else{
            redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
            return "redirect:/home/company/info";
        }
        return "companyInfoUpdate.html";
    }

    @RequestMapping(value = "/home/company/info/update", method = RequestMethod.POST)
    public String updateCompanyInfo(@Valid CompanyInfo companyInfo, BindingResult bindingResult, RedirectAttributes redirectAttribute, Principal principal){
        User currentUser=userService.findUserByEmail(principal.getName());
        if (bindingResult.hasErrors()){
            redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
            redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
        }else {
                companyInfo.setUser(currentUser);
                companyInfo.setVipLevel(companyInfoService.findCompanyInfosByCompany(currentUser.getId()).getVipLevel());
                companyInfoService.saveCompanyInfo(companyInfo);
                redirectAttribute.addFlashAttribute("successMessage", "Company Info updated successfully");
                return "redirect:/home/company/info";
        }
        return String.format("redirect:/home/company/info/update/%d",companyInfo.getId());
    }

    @RequestMapping(value = "/home/company/info/delete/{id}", method = RequestMethod.GET)
    public String deleteSchedule(@PathVariable int id, RedirectAttributes redirectAttribute, Principal principal){
        Optional<CompanyInfo> companyInfo= companyInfoService.findById(id);
        User currentUser = userService.findUserByEmail(principal.getName());
        if (companyInfo.get().getUser().getId()==currentUser.getId()){
            companyInfoService.deleteCompanyInfo(companyInfo.get());
            redirectAttribute.addFlashAttribute("successMessage", "Delete completed");
        }else{
            redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
        }
        return "redirect:/home/company/info";
    }

}
