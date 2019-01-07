package com.hws.mapper;

import java.util.List;

import com.hws.model.Instructions;

public interface InstructionsMapper {
	
	public List<Instructions> getAllInstructions();
	public List<Instructions> getInstructionsByName(String value);
}
