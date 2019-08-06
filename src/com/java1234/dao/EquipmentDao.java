package com.java1234.dao;

import java.util.List;

import com.java1234.model.Equipment;
import com.java1234.model.PageBean;

public interface EquipmentDao {

	
	public boolean existEquipmentByTypeId(int typeId);
	
	public int count(Equipment s_equipment);
	
	public void add(Equipment equipment);
	
	public void delete(Integer id);
	
	public void update(Equipment equipment);
	
	public Equipment findById(Integer id);
	
	public List<Equipment> find(PageBean pageBean,Equipment s_equipment);
	
	
	
}
