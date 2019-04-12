package com.javaex.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	/* 리스트 페이징 */
	@RequestMapping(value= {"","/list"}, method=RequestMethod.GET)
	public String list(@RequestParam(value="crtPage", required=false, defaultValue="1" ) int crtPage, 
					   @RequestParam( value="kwd", required=false, defaultValue="") String kwd,
					   Model model) {
		Map<String, Object> pMap = boardService.getList(crtPage, kwd);
		model.addAttribute("pMap", pMap);
		return "board/list";
	}
	
	
	/* 리스트 기본 */
	/*
	@RequestMapping(value= {"","/list"}, method=RequestMethod.GET)
	public String list(Model model) {
		List<BoardVo> boardList = boardService.getList();
		model.addAttribute("boardList", boardList);
		return "board/list";
	}
	*/
	
	
	/* 글쓰기폼 */
	@RequestMapping(value="/writeform", method=RequestMethod.GET)
	public String writeform() {
		return "board/writeform";
	}
	
	/* 글저장 */
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@ModelAttribute BoardVo boardVo, HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		boardService.write(boardVo);		
		
		return "redirect:/board/list";
	}
	
	/* 글읽기 */
	@RequestMapping(value="/read/{no}", method=RequestMethod.GET)
	public String read(@PathVariable("no") int no, Model model) {
		BoardVo boardVo = boardService.read(no);
		model.addAttribute("boardVo", boardVo);
		return "board/read";
	}
	
	/* 글 수정폼 */
	@RequestMapping(value="/modifyform", method=RequestMethod.GET)
	public String modifyform(@RequestParam int no, Model model) {
		BoardVo boardVo = boardService.modifyform(no);
		model.addAttribute("boardVo", boardVo);
		return "board/modifyform";
	}
	
	/* 글수정 */
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modifyform(@ModelAttribute BoardVo boardVo,
							 @RequestParam(value="crtPage", required=false, defaultValue="1" ) int crtPage, 
							 @RequestParam( value="kwd", required=false, defaultValue="") String kwd,
			                 HttpSession session, 
			                 Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		int userNo = authUser.getNo();
		boardVo.setUserNo(userNo);
		boardService.modify(boardVo);
		
		System.out.println(crtPage);
		System.out.println(kwd);
		return "redirect:/board/list?crtPage="+ crtPage + "&kwd=" + kwd;
	}
	
	/* 글삭제 */
	@RequestMapping(value="/remove", method=RequestMethod.GET)
	public String remove(@ModelAttribute BoardVo boardVo, HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		boardService.remove(boardVo);	
		return "redirect:/board/list";
	}
	
	
}
