package com.hws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hws.mapper.CMMapper;
import com.hws.mapper.DiseaseCureMapper;
import com.hws.mapper.InstructionsMapper;
import com.hws.mapper.LiteratureMapper;
import com.hws.mapper.SRDetailsMapper;
import com.hws.mapper.SRTMapper;
import com.hws.mapper.TypeMapper;
import com.hws.model.CMType;
import com.hws.model.ChineseMedicine;
import com.hws.model.DiseaseCure;
import com.hws.model.Instructions;
import com.hws.model.Literature;
import com.hws.model.SRDetails;
import com.hws.model.SRType;
import com.hws.service.SQLService;

@Service
public class SQLServiceImpl implements SQLService {

	/**
	 * 查找所有药品的类型
	 */
	@Autowired
	TypeMapper typeMapper;

	public List<CMType> findAllType() {

		return typeMapper.findAllType();

	}

	/**
	 * 查找所有标准来源类型
	 */
	@Autowired
	SRTMapper srtMapper;

	@Override
	public List<SRType> findAllSRType() {
		
		return srtMapper.findAllSRType();
	}

	/**
	 * 通过mid查找cm对象
	 */
	@Autowired
	CMMapper cmMapper;
	
	@Override
	public ChineseMedicine getCM(int mid) {
		
		return cmMapper.getCM(mid);
	}
	
	/**
	 * 通过mcname查询cm对象
	 * @param mcname
	 * @return
	 */
	@Override
	public ChineseMedicine getCMByName(String mcname) {
		
		return cmMapper.getCMByName(mcname);
	}

	/**
	 * 通过mid查找标准来源详情
	 * @param mid
	 * @return
	 */
	@Autowired
	SRDetailsMapper srdMapper;
	
	@Override
	public SRDetails getSRDetails(int mid) {
		
		return srdMapper.findSRDatails(mid);
	}

	/**
	 * 获取所有的cm对象
	 */
	@Override
	public List<ChineseMedicine> getAllCM() {
		return cmMapper.getAllCM();
	}

	/**
	 * 获取所有的参考文献
	 */
	@Autowired
	LiteratureMapper literatureMapper;
	@Override
	public List<Literature> getLiterature() {
		return literatureMapper.getLiterature();
	}

	@Autowired
	InstructionsMapper iMapper;
	@Override
	public List<Instructions> getAllInstructions() {
		return iMapper.getAllInstructions();
	}

	@Override
	public List<Instructions> getInstructionsByName(String value) {
		return iMapper.getInstructionsByName(value);
	}

	@Autowired
	DiseaseCureMapper dcMapper;
	
	/**
	 * 插入疾病诊治类型
	 * @param dcList
	 */
	@Override
	public void insertType(List<DiseaseCure> dcList) {
		dcMapper.insertType(dcList);
	}

	/**
	 * 插入疾病诊治内容
	 * @param dcList
	 */
	@Override
	public void insertContent(List<DiseaseCure> dcList) {

		dcMapper.insertContent(dcList);
	}

	/**
	 * 查找所有疾病诊治类型
	 * @return
	 */
	@Override
	public List<DiseaseCure> findAllDCType() {

		return dcMapper.findALLDC();
	}

	/**
	 * 查找所有内容
	 * @return
	 */
	@Override
	public List<DiseaseCure> findDiseaseCure() {
		
		return dcMapper.findALLContent();
	}

	

}
