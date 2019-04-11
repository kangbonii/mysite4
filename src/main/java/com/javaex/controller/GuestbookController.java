package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@Controller
@RequestMapping(value="/gb")
public class GuestbookController {
	
	@Autowired
	private GuestbookDao dao;
	
	@RequestMapping(value="/addlist", method=RequestMethod.GET)
	public String addList(Model model) {
		List<GuestbookVo> list = dao.selectList();
		model.addAttribute("list", list);
		return "guestbook/addlist";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@ModelAttribute GuestbookVo vo) {
		dao.insert(vo);
		return "redirect:/gb/addlist";
	}
	

	@RequestMapping(value="/deleteform", method=RequestMethod.GET)
	public String deleteForm() {
		return "guestbook/deleteform";
	}
	
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo vo) {
		System.out.println(vo.toString());
		dao.delete(vo);
		return "redirect:/gb/addlist";
	}
	
}
