package edu.bit.ex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.bit.ex.page.Criteria;
import edu.bit.ex.page.PageVO;
import edu.bit.ex.service.BoardService;
import edu.bit.ex.vo.BoardVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/restful/*")
public class RestBoardController {
	/* @Autowired */
	private BoardService boardService;

	// 페이징을 적용한 게시글 리스트
	@Transactional
	@GetMapping("/board")
	public ModelAndView list2(Criteria cri, ModelAndView mav) {
		log.info("list2()");
		log.info(cri.toString());
		mav.setViewName("rest/rest_list");
		mav.addObject("list", boardService.getList(cri));

		int total = boardService.getTotal(cri);
		log.info("total" + total);
		mav.addObject("pageMaker", new PageVO(cri, total));

		return mav;
	}

	// 작성글 페이지
	@GetMapping("/board/{bId}")
	public ModelAndView rest_content_view(BoardVO boardVO, ModelAndView mav) {
		log.info("rest_content_view");
		mav.setViewName("rest/content_view");
		mav.addObject("content_view", boardService.getBoard(boardVO.getbId()));
		return mav;
	}

	// 작성글 삭제
	// ResponseEntity: 개발자가 직접 결과 데이터와 HTTP 상태 코드를 제어할 수 있는 클래스
	@DeleteMapping("/board/{bId}")
	public ResponseEntity<String> rest_delete(BoardVO boardVO, Model model) {
		ResponseEntity<String> entity = null;
		log.info("rest_delete..");

		try {
			boardService.remove(boardVO.getbId());
			// 삭제가 성공하면 상태메시지 저장
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			// 댓글 삭제가 실패하면 BAD_REQUEST를 리턴
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// 삭제 처리 HTTP 상태 메시지 리턴
		return entity;
	}

	// 작성글 수정
	@PutMapping("/board/{bId}")
	public ResponseEntity<String> rest_update(@RequestBody BoardVO boardVO, ModelAndView modelAndView) {
		ResponseEntity<String> entity = null;

		log.info("rest_update..");
		try {
			boardService.modifyBoard(boardVO);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	// 답변글 페이지
	@GetMapping("/board/reply/{bId}")
	public ModelAndView rest_reply_view(BoardVO boardVO, ModelAndView mav) {
		log.info("rest_reply_view");
		mav.setViewName("rest/reply_view");
		mav.addObject("reply_view", boardService.getReply(boardVO.getbId()));
		return mav;
	}

	// 작성글 답변
	@PutMapping("/board/reply/{bId}")
	public ResponseEntity<String> rest_reply(@RequestBody BoardVO boardVO, ModelAndView modelAndView) {
		ResponseEntity<String> entity = null;

		log.info("rest_reply..");
		try {
			boardService.replyBoard(boardVO);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	// 글 작성 페이지
	@GetMapping("/board/write")
	public ModelAndView rest_write_view(ModelAndView mav) {
		log.info("rest_write_view");
		mav.setViewName("rest/write_view");
		return mav;
	}

	// 글 작성
	@PutMapping("/board/write")
	public ResponseEntity<String> write_view(@RequestBody BoardVO boardVO, ModelAndView modelAndView) {
		ResponseEntity<String> entity = null;

		log.info("write_view..");
		try {
			boardService.writeBoard(boardVO);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return entity;
	}
}