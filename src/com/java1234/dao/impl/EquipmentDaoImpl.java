package com.java1234.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.java1234.dao.EquipmentDao;
import com.java1234.model.Equipment;
import com.java1234.model.PageBean;
import com.java1234.util.StringUtil;

@Repository("equipmentDao")
public class EquipmentDaoImpl implements EquipmentDao{

	@Resource
	private JdbcTemplate jdbcTemplate;
	

	@Override
	public boolean existEquipmentByTypeId(int typeId) {
		String sql="select count(*) from t_equipment where typeId=?";
		int result=jdbcTemplate.queryForObject(sql,new Object[]{typeId},Integer.class);
		if(result>0){
			return true;
		}else{
			return false;			
		}
	}
	@Override
	public int count(Equipment s_equipment) {
		StringBuffer sb=new StringBuffer("select count(*) from t_equipment t1,t_equipmenttype t2 where t1.typeId=t2.id");
		if(s_equipment!=null){
			if(StringUtil.isNotEmpty(s_equipment.getName())){
				sb.append(" and name like '%"+s_equipment.getName()+"%'");
			}
		}
		return jdbcTemplate.queryForObject(sb.toString(), Integer.class);
	}
	@Override
	public void add(Equipment equipment) {
		String sql="insert into t_equipment values(null,?,?,?,?,?)";
		jdbcTemplate.update(sql, new Object[]{equipment.getName(),equipment.getNo(),equipment.getTypeId(),equipment.getState(),equipment.getRemark()});
	}
	@Override
	public void delete(Integer id) {
		String sql="delete from t_equipment where id=?";
		jdbcTemplate.update(sql, new Object[]{id});
	}
	@Override
	public void update(Equipment equipment) {
		String sql="update t_equipment set name=?,no=?,typeId=?,state=?,remark=? where id=?";
		jdbcTemplate.update(sql, new Object[]{equipment.getName(),equipment.getNo(),equipment.getTypeId(),equipment.getState(),equipment.getRemark(),equipment.getId()});
	}
	@Override
	public Equipment findById(Integer id) {
		String sql="select * from t_equipment t1,t_equipmenttype t2 where t1.typeId=t2.id and t1.id=?";
		final Equipment equipment=new Equipment();
		jdbcTemplate.query(sql, new Object[]{id}, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				equipment.setId(rs.getInt("id"));
				equipment.setName(rs.getString("name"));
				equipment.setNo(rs.getInt("no"));
				equipment.setTypeId(rs.getInt("typeId"));
				equipment.setTypeName(rs.getString("typeName"));
				equipment.setState(rs.getInt("state"));
				equipment.setRemark(rs.getString("remark"));
				
				
				
			}
	});
		return equipment;
	}


	@Override
	public List<Equipment> find(PageBean pageBean, Equipment s_equipment) {
		StringBuffer sb=new StringBuffer("select * from t_equipment t1,t_equipmenttype t2 where t1.typeId=t2.id");
		if(s_equipment!=null){
			if(StringUtil.isNotEmpty(s_equipment.getName())){
				sb.append(" and t1.Name like '%"+s_equipment.getName()+"%'");
			}
		}
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		final List<Equipment> equipmentList=new ArrayList<Equipment>();
		jdbcTemplate.query(sb.toString(), new Object[]{}, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Equipment equipment=new Equipment();
				equipment.setId(rs.getInt("id"));
				equipment.setName(rs.getString("name"));
				equipment.setNo(rs.getInt("no"));
				equipment.setTypeId(rs.getInt("typeId"));
				equipment.setTypeName(rs.getString("typeName"));
				equipment.setState(rs.getInt("state"));
				equipment.setRemark(rs.getString("remark"));
				equipmentList.add(equipment);
			}
		});
		return equipmentList;
	}


	


	
	

}
