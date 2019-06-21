package com.fas.fotomania.fotomania.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fas.fotomania.fotomania.entities.Tool;

@Service
public interface IToolService {
	public boolean saveTool(Tool tool);
	public boolean updateTool(Tool tool);
	public boolean deleteTool(Tool tool);
	public List<Tool> listTools();
	public List<Tool> findToolsByCompany(int companyId);
	public Optional<Tool> findById(int id);
}
