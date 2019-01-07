package com.hws.mapper;

import java.util.List;

import com.hws.model.DiseaseCure;

public interface DiseaseCureMapper {
	
	/**
	 * 插入到疾病诊断类型表
	 */
	public void insertType(List<DiseaseCure> list);
	
	/**
	 * 插入到疾病诊断内容表
	 */
	public void insertContent(List<DiseaseCure> list);
	
	/**
	 * 查询类型
	 */
	public List<DiseaseCure> findALLDC();
	/**
	 * 查询内容
	 */
	public List<DiseaseCure> findALLContent();
}
