package com.hws.mapper;

import java.util.List;

import com.hws.model.ChineseMedicine;

public interface CMMapper {
	
	public ChineseMedicine getCM(int mid);
	public ChineseMedicine getCMByName(String mcname);
	public List<ChineseMedicine> getAllCM();

}
