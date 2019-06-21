package com.fas.fotomania.fotomania.controllers;

import com.fas.fotomania.fotomania.entities.Tool;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fas.fotomania.fotomania.services.interfaces.IToolService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class ToolController {
	
	@Autowired
	IToolService toolService;

	@Autowired
	IUserService userService;
	
	@RequestMapping(value="/home/company/tool", method=RequestMethod.GET)
	public String listTools(Model model, Principal principal) {
		User currentUser=userService.findUserByEmail(principal.getName());
		model.addAttribute("tools",toolService.findToolsByCompany(currentUser.getId()));
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
			redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
			redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
		}else {
			User currentUser=userService.findUserByEmail(principal.getName());
			tool.setUser(currentUser);
			toolService.saveTool(tool);
			redirectAttribute.addFlashAttribute("successMessage", "Tool registered successfully");
			return "redirect:/home/company/tool";
		}
		return "redirect:/home/company/tool/add";
	}
	@RequestMapping(value = "/home/company/tool/delete/{id}", method = RequestMethod.GET)
	public String deleteTool(@PathVariable int id, RedirectAttributes redirectAttribute, Principal principal){
		Optional<Tool> currentTool= toolService.findById(id);
		User currentUser = userService.findUserByEmail(principal.getName());
		if (currentTool.get().getUser().getId()==currentUser.getId()){
			toolService.deleteTool(currentTool.get());
			redirectAttribute.addFlashAttribute("successMessage", "Delete completed");
		}else{
			redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
		}
		return "redirect:/home/company/tool";
	}

	@RequestMapping(value = "/home/company/tool/update/{id}", method = RequestMethod.GET)
	public String updateTool(@PathVariable int id, RedirectAttributes redirectAttribute, Principal principal, Model model){
		Optional<Tool> currentTool= toolService.findById(id);
		User currentUser = userService.findUserByEmail(principal.getName());
		if (currentTool.get().getUser().getId()==currentUser.getId()){
			model.addAttribute("tool", currentTool.get());
		}else{
			redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
			return "redirect:/home/company/tool";
		}
		return "toolUpdate.html";
	}
	@RequestMapping(value = "/home/company/tool/update", method = RequestMethod.POST)
	public String saveToolUpdate(@Valid Tool tool, BindingResult bindingResult, RedirectAttributes redirectAttribute, Principal principal){
		User currentUser = userService.findUserByEmail(principal.getName());
		if (bindingResult.hasErrors()){
			redirectAttribute.addFlashAttribute("errorMessage","Correct the errors in the form");
			redirectAttribute.addFlashAttribute("bindingResult", bindingResult);
		}else {
			int id = tool.getId();
			Optional<Tool> currentTool= toolService.findById(id);
			if (currentTool.get().getUser().getId()==currentUser.getId()){
				tool.setUser(currentUser);
				toolService.updateTool(tool);
				redirectAttribute.addFlashAttribute("successMessage", "Tool updated successfully");
				return "redirect:/home/company/tool";
			} else {
				redirectAttribute.addFlashAttribute("errorMessage", "We are watching you!");
			}
		}
		return String.format("redirect:/home/company/tool/update/%d",tool.getId());
	}
}
