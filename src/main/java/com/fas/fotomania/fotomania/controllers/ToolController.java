package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fas.fotomania.fotomania.services.interfaces.IToolService;

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
	
}
