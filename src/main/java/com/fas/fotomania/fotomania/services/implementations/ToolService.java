package com.fas.fotomania.fotomania.services.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fas.fotomania.fotomania.entities.Tool;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.repository.IToolRepository;
import com.fas.fotomania.fotomania.repository.IUserRepository;
import com.fas.fotomania.fotomania.services.interfaces.IToolService;
import org.springframework.stereotype.Service;

@Service
public class ToolService implements IToolService {

	@Autowired
	IToolRepository toolRepository;
	
	@Autowired
	IUserRepository userRepository;
	
	@Override
	public boolean saveTool(Tool tool) {
		boolean good=false;
		try {
			toolRepository.save(tool);
			good=true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return good;
	}

	@Override
	public boolean updateTool(Tool tool) {
		boolean good = false;
		try {
			toolRepository.save(tool);
			good = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return good;
	}

	@Override
	public boolean deleteTool(Tool tool) {
		boolean good=false;
		try {
			toolRepository.delete(tool);
			good = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return good;
	}

	@Override
	public List<Tool> listTools() {
		return toolRepository.findAll();
	}

	@Override
	public List<Tool> findToolsByCompany(int companyId) {
		Optional<User> currentCompany=userRepository.findById(companyId);
		return toolRepository.findByUser(currentCompany);
	}
	@Override
	public Optional<Tool> findById(int id){
		return toolRepository.findById(id);
	}
}
