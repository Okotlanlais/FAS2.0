package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.Tool;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fas.fotomania.fotomania.services.interfaces.IToolService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ToolController {
	
	@Autowired
	IToolService toolservice;

	@Autowired
	IUserService userService;
	
	@RequestMapping(value="/home/company/tool", method=RequestMethod.GET)
	public String listTools(Model model, Principal principal) {
		User currentUser=userService.findUserByEmail(principal.getName());
		model.addAttribute("tools",toolservice.findToolsByCompany(currentUser.getId()));
		return "toolList.html";
	}
	@RequestMapping(value="/home/company/tool/add",method = RequestMethod.GET)
	public String createAddTool(Model model){
		model.addAttribute("tool",new Tool());
		return "toolAdd.html";
	}
	@RequestMapping(value = "/home/company/tool/add", method = RequestMethod.POST)
	public String saveTool(@Valid Tool tool, BindingResult bindingResult, RedirectAttributes redirectAttribute, Principal principal){
		if (bindingResult.hasErrors()){
			redirectAttribute.addFlashAttribute("message","Correct the errors in the form");
			redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
		}else {
			User currentUser=userService.findUserByEmail(principal.getName());
			tool.setUser(currentUser);
			toolservice.saveTool(tool);
			redirectAttribute.addFlashAttribute("message", "Tool registered successfully");
			return "redirect:/home/company/tool";
		}
		return "redirect:/home/company/tool/add";
	}
}
