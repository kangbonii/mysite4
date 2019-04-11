package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	/* 리스트 기본 */
	public List<BoardVo> getList(){
		List<BoardVo> boardList = boardDao.selectAll(); 
		return boardList;
		
	}
	
	/* 글저장 */
	public int write(BoardVo boardVo) {
		
		return boardDao.insert(boardVo);
	}
	
	/* 글읽기 */
	public BoardVo read(int no) {
		boardDao.updateHit(no);
		BoardVo boardVo = boardDao.selectBoard(no);
		return boardVo;
	}
	
	/* 글 수정폼 */
	public BoardVo modifyform(int no) {
		BoardVo boardVo = boardDao.selectBoard(no);
		return boardVo;
	}
	
	/* 글 수정 */
	public int modify(BoardVo boardVo) {
		return boardDao.update(boardVo);
	}
	
	/* 글 삭제 */
	public int remove(BoardVo boardVo) {
		return boardDao.delete(boardVo);
	}
}
