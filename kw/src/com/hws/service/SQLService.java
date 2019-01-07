package com.hws.service;

import java.util.List;

import com.hws.model.CMType;
import com.hws.model.ChineseMedicine;
import com.hws.model.DiseaseCure;
import com.hws.model.Instructions;
import com.hws.model.Literature;
import com.hws.model.SRDetails;
import com.hws.model.SRType;

public interface SQLService {
	
	
	/**
	 * 查找所有的药品类型
	 * @return
	 */
	public List<CMType> findAllType();
	/**
	 * 查找所有标准来源
	 */
	public List<SRType> findAllSRType();
	
	/**
	 * 通过mid查找cm对象
	 * @param mid
	 * @return
	 */
	public ChineseMedicine getCM(int mid);
	
	/**
	 * 通过mid查找标准来源详情
	 * @param mid
	 * @return
	 */
	public SRDetails getSRDetails(int mid);
	
	/**
	 * 通过mcname查询cm对象
	 * @param mcname
	 * @return
	 */
	public ChineseMedicine getCMByName(String mcname);
	
	/**
	 * 获取所有的cm对象
	 * @return
	 */
	public List<ChineseMedicine> getAllCM();
	
	/**
	 * 获取所有的文献资料
	 * @return
	 */
	public List<Literature> getLiterature();
	
	/**
	 * 获取所有的说明书
	 * @return
	 */
	public List<Instructions> getAllInstructions();
	
	/**
	 * 通过名称获取说明书
	 * @param value
	 * @return
	 */
	public List<Instructions> getInstructionsByName(String value);
	
	/**
	 * 插入疾病诊治类型
	 * @param dcList
	 */
	public void insertType(List<DiseaseCure> dcList);
	
	/**
	 * 插入疾病诊治内容
	 * @param dcList
	 */
	public void insertContent(List<DiseaseCure> dcList);
	
	/**
	 * 查找所有疾病诊治类型
	 * @return
	 */
	public List<DiseaseCure> findAllDCType();
	/**
	 * 查找所有内容
	 * @return
	 */
	public List<DiseaseCure> findDiseaseCure();
 	
}
