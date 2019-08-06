package com.java1234.controller;



import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.java1234.model.Equipment;
import com.java1234.model.EquipmentType;
import com.java1234.model.PageBean;
import com.java1234.model.Repair;
import com.java1234.model.User;
import com.java1234.service.EquipmentService;
import com.java1234.service.EquipmentTypeService;
import com.java1234.service.RepairService;
import com.java1234.util.PageUtil;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/equipment")
public class EquipmentController{
	@Resource
	private EquipmentService equipmentService;
	@Autowired
	private EquipmentTypeService equipmentTypeService;
	@Autowired
	private RepairService repairService;
	
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value="page",required=false)String page,Equipment s_equipment,HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
			session.setAttribute("s_equipment",s_equipment);
		}else{
			s_equipment=(Equipment)session.getAttribute("s_equipment");
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),3);
		List<Equipment> equipmentList=equipmentService.find(pageBean,s_equipment);
		int total=equipmentService.count(s_equipment);
		String pageCode=PageUtil.getPagation(request.getContextPath()+"/equipment/list.do", total, Integer.parseInt(page), 3);
		mav.addObject("pageCode", pageCode);
		mav.addObject("modeName", "�豸����");
		mav.addObject("equipmentList", equipmentList);
		mav.addObject("mainPage", "equipment/list.jsp");
		mav.setViewName("main");
		return mav;
	}
	@RequestMapping("/userList")
	public ModelAndView userList(@RequestParam(value="page",required=false)String page,Equipment s_equipment,HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
			session.setAttribute("s_equipment",s_equipment);
		}else{
			s_equipment=(Equipment)session.getAttribute("s_equipment");
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),3);
		List<Equipment> equipmentList=equipmentService.find(pageBean,s_equipment);
		int total=equipmentService.count(s_equipment);
		String pageCode=PageUtil.getPagation(request.getContextPath()+"/equipment/userList.do", total, Integer.parseInt(page), 3);
		mav.addObject("pageCode", pageCode);
		mav.addObject("modeName", "�豸����");
		mav.addObject("equipmentList", equipmentList);
		mav.addObject("mainPage", "equipment/userList.jsp");
		mav.setViewName("main");
		return mav;
	}
	@RequestMapping("/repairList")
	public ModelAndView repairList(@RequestParam(value="page",required=false)String page,Repair  s_repair,HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
			session.setAttribute("s_equipment",s_repair);
		}else{
			s_repair=(Repair) session.getAttribute("s_repair");
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),3);
		List<Repair> RepairList=repairService.finding(pageBean, s_repair);
		int total=repairService.count(s_repair);
		String pageCode=PageUtil.getPagation(request.getContextPath()+"/equipment/repairList.do", total, Integer.parseInt(page), 3);
		mav.addObject("pageCode", pageCode);
		mav.addObject("modeName", "ά���豸����");
		mav.addObject("repairList", RepairList);
		mav.addObject("mainPage", "equipment/repairList.jsp");
		mav.setViewName("main");
		return mav;
	}
	@RequestMapping("/repairHistory")
	public ModelAndView repairHistory(@RequestParam(value="page",required=false)String page,Repair  s_repair,HttpServletRequest request){
		ModelAndView mav=new ModelAndView();
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
			session.setAttribute("s_equipment",s_repair);
		}else{
			s_repair=(Repair) session.getAttribute("s_repair");
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),3);
		List<Repair> RepairList=repairService.find(pageBean, s_repair);
		int total=repairService.count(s_repair);
		String pageCode=PageUtil.getPagation(request.getContextPath()+"/equipment/repairHistory.do", total, Integer.parseInt(page), 3);
		mav.addObject("pageCode", pageCode);
		mav.addObject("modeName", "ά���豸��ʷ");
		mav.addObject("repairList", RepairList);
		mav.addObject("mainPage", "equipment/repairHistory.jsp");
		mav.setViewName("main");
		return mav;
	}
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		JSONObject result=new JSONObject();
		equipmentService.delete(Integer.parseInt(id));
		result.put("success", true);
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/preSave")
	public ModelAndView preSave(@RequestParam(value="id",required=false)String id){
		ModelAndView mav=new ModelAndView();
		mav.addObject("mainPage", "equipment/save.jsp");
		mav.addObject("modeName", "�豸����");
		mav.setViewName("main");
		if(StringUtil.isNotEmpty(id)){
			mav.addObject("actionName", "�豸�޸�");
			Equipment equipment=equipmentService.findById(Integer.parseInt(id));
			mav.addObject("equipment", equipment);
		}else{
			mav.addObject("actionName", "�豸���");			
		}
		List<EquipmentType> equipmentTypeList=equipmentTypeService.find(null, null);
		mav.addObject("equipmentTypeList",equipmentTypeList);
		return mav;
	}
	
	@RequestMapping("/save")
	public String save(Equipment equipment){
		if(equipment.getId()==null){
			equipmentService.add(equipment);			
		}else{
			equipmentService.update(equipment);
		}
		return "redirect:/equipment/list.do";
	}
	@RequestMapping("/repair")
	public void repair(@RequestParam(value="id")String id,HttpSession session,HttpServletResponse response)throws Exception{
		String userMan=((User)session.getAttribute("currentUser")).getUserName();
		JSONObject result=new JSONObject();
		equipmentService.addRepair(Integer.parseInt(id), userMan);
		result.put("success", true);			
		ResponseUtil.write(result, response);
	}
//	@RequestMapping("/finishRepair")
//	public void finishRepair(@RequestParam(value="id")String id,@RequestParam(value="equipmentId")String equipmentId,HttpServletRequest request,HttpServletResponse response)throws Exception{
//		JSONObject result=new JSONObject();
//		HttpSession session=request.getSession();
//		User user=(User) session.getAttribute("currentUser");
//		String repairMan=user.getUserName();
//		equipmentService.completed(Integer.parseInt(equipmentId));
//		Repair repair=new  Repair();
//		repair.setRepairMan(repairMan);
//		repair.setId(Integer.parseInt(id));
//		repair.setState(1);
//		repairService.update(repair);
//		result.put("success", true);
//		ResponseUtil.write(result, response);
//	}
//	@RequestMapping("/scrap")
//	public void scrap(@RequestParam(value="id")String id,@RequestParam(value="equipmentId")String equipmentId,HttpServletRequest request,HttpServletResponse response)throws Exception{
//		JSONObject result=new JSONObject();
//		equipmentService.scrap(Integer.parseInt(equipmentId));
//		HttpSession session=request.getSession();
//		User user=(User) session.getAttribute("currentUser");
//		String repairMan=user.getUserName();
//		Repair repair=new  Repair();
//		repair.setRepairMan(repairMan);
//		repair.setState(2);
//		repair.setId(Integer.parseInt(id));
//		repairService.update(repair);
//		result.put("success", true);
//		ResponseUtil.write(result, response);
//	}
	@RequestMapping("/finishRepair")
	public void finishRepair(@RequestParam(value="id")String id,@RequestParam(value="repairId")String repairId,@RequestParam(value="success")boolean success,HttpSession session,HttpServletResponse response)throws Exception{
		String repairMan=((User)session.getAttribute("currentUser")).getUserName();
		JSONObject result=new JSONObject();
		equipmentService.updateRepair(Integer.parseInt(id), Integer.parseInt(repairId), repairMan, success);
		result.put("success", true);			
		ResponseUtil.write(result, response);
	}
}
