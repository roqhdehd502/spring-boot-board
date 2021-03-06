package edu.bit.ex.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.bit.ex.mapper.BoardMapper;
import edu.bit.ex.page.Criteria;
import edu.bit.ex.vo.BoardVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	private BoardMapper mapper;

	@Override
	public List<BoardVO> getList() {
		log.info("getList...");
		return mapper.getList();
	}

	// 페이징을 적용한 게시글 리스트
	@Override
	public List<BoardVO> getList(Criteria criteria) {
		log.info("getList() WITH criteria: " + criteria);
		return mapper.getListWithPaging(criteria);
	}

	// 페이징 단위에 적용되는 최대 게시글 단위
	@Override
	public int getTotal(Criteria criteria) {
		log.info("getTotal() WITH criteria: " + criteria);
		return mapper.getTotalCount(criteria);
	}

	// 글작성 페이지
	@Override
	public void writeBoard(BoardVO boardVO) {
		mapper.insert(boardVO);
		log.info("writeBoard()");
	}

	// 작성글 페이지
	@Transactional
	@Override
	public BoardVO getBoard(int bno) {
		mapper.upHit(bno);
		log.info("getBoard()");
		return mapper.read(bno);
	}

	// 작성글 삭제
	@Override
	public void deleteBoard(BoardVO boardVO) {
		mapper.delete(boardVO);
		log.info("deleteBoard()");
	}

	// AJax를 이용한 작성글 삭제
	@Override
	public int remove(int bId) {
		log.info("remove..........");
		return mapper.ajaxDelete(bId);
	}

	// 작성글 수정
	@Override
	public void modifyBoard(BoardVO boardVO) {
		mapper.modify(boardVO);
		log.info("modifyBoard()");
	}

	// 답글 페이지
	@Override
	public BoardVO getReply(int bno) {
		log.info("getBoard()");
		return mapper.readReply(bno);
	}

	// 답글 수행
	@Transactional
	@Override
	public void replyBoard(BoardVO boardVO) {
		mapper.reply(boardVO);
		mapper.replyShape(boardVO);
		log.info("replyBoard()");
	}

}