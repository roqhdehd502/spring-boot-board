package edu.bit.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.bit.ex.page.Criteria;
import edu.bit.ex.page.PageVO;
import edu.bit.ex.service.BoardService;
import edu.bit.ex.vo.BoardVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class BoardController {
	/* @Autowired */
	private BoardService boardService;

	// 게시글 리스트
	@GetMapping("/list")
	public String list(Model model) {
		log.debug("list");
		log.info("list..");
		model.addAttribute("list", boardService.getList());
		return "list";
	}

	// 페이징을 적용한 게시글 리스트
	@Transactional
	@GetMapping("/list2")
	public String list2(Criteria cri, Model model) {
		log.info("list2()");
		log.info(cri.toString());
		model.addAttribute("list", boardService.getList(cri));

		int total = boardService.getTotal(cri);
		log.info("total" + total);
		model.addAttribute("pageMaker", new PageVO(cri, total));

		return "list2";
	}

	// 글작성 페이지
	@GetMapping("/write_view")
	public String write_view(Model model) {
		log.info("write_view()");
		return "write_view";
	}

	// 글작성
	@PostMapping("/write")
	public String write(BoardVO boardVO, Model model) throws Exception {
		log.info("write()");
		boardService.writeBoard(boardVO);
		return "redirect:list2";
	}

	// 작성글 페이지
	@GetMapping("/content_view")
	public String content_view(BoardVO boardVO, Model model) throws Exception {
		log.info("content_view()");
		model.addAttribute("content_view", boardService.getBoard(boardVO.getbId()));
		return "content_view";
	}

	// 작성글 수정
	@PostMapping("/modify")
	public String modify(BoardVO boardVO, Model model) throws Exception {
		log.info("modify()");
		boardService.modifyBoard(boardVO);
		return "redirect:list2";
	}

	// 작성글 삭제
	@GetMapping("/delete")
	public String delete(BoardVO boardVO, Model model) throws Exception {
		log.info("delete()");
		boardService.deleteBoard(boardVO);
		return "redirect:list2";
	}

	// 작성글 답변 페이지
	@GetMapping("/reply_view")
	public String reply_view(BoardVO boardVO, Model model) throws Exception {
		log.info("reply_view()");
		model.addAttribute("reply_view", boardService.getReply(boardVO.getbId()));
		return "/reply_view";
	}

	// 작성글 답변
	@PostMapping("/reply")
	public String reply(BoardVO boardVO, Model model) throws Exception {
		log.info("reply()");
		boardService.replyBoard(boardVO);
		return "redirect:list2";
	}

}