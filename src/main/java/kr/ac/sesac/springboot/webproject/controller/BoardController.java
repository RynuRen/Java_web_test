package kr.ac.sesac.springboot.webproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ac.sesac.springboot.webproject.mapper.BoardMapper;
import kr.ac.sesac.springboot.webproject.model.Board;
import kr.ac.sesac.springboot.webproject.model.User;

@Controller
@RequestMapping("board")
public class BoardController {
    @Autowired
    BoardMapper boardMapper;

    // ========================================
    // 게시판 목록 호출
    @GetMapping("list")
    public String list(Model model, HttpSession session, @RequestParam(defaultValue = "1") int page) {
        ////수정할 것////
        int countPage = 3; // 한 화면에 출력될 페이지 수
        int countPost = 3; // 한 페이지에 출력할 게시글 수
        ////수정할 것////
        int totalCount = boardMapper.getTotal(); // BD에 등록된 총 게시글 수

        int totalPage = totalCount / countPost; // 총 페이지 수
        // 총 게시글을 한 화면에 출력될 게시글로 나눠서 나머지가 있다면 표시할 페이지를 하나 추가
        if (totalCount % countPost > 0) {
            totalPage++;
        }
        // 총 페이지 수보다 접속한 페이지가 크면 마지막 페이지로 보정한다.
        if (totalPage < page) {
            page = totalPage;
        }
        int startPage = (page - 1) / countPage * countPage + 1;
        int endPage = startPage + countPage - 1;
        // 마지막 페이지가 총 페이지 수 보다 크면 마지막 페이지로 보정한다.
        if (endPage > totalPage) {
            endPage = totalPage;
        }
        if (endPage == 0) {
            endPage = 1;
        }
        // 마지막 페이지 리스트 전의 마지막 페이지
        int preLastPage = totalPage - (totalPage%countPage==0?countPage:totalPage%countPage);
        List<Board> list = boardMapper.getList((page - 1) * countPost, countPost);
        model.addAttribute("list", list);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("preLastPage", preLastPage);
        return "board/list";
    }

    // ========================================
    // 게시글 작성 호출
    @GetMapping("create")
    public String create() {
        return "board/create";
    }
    // 게시글 작성 요청
    @PostMapping("create")
    public String create(Board board, HttpSession session) {
        User user = (User) session.getAttribute("user");
        board.setBoardWriter(user.getUserNick());
        board.setBoardWriterId(user.getUserId());
        boardMapper.putBoard(board);
        return "redirect:/board/list";
    }

    // ========================================
    // 게시글 상세보기
    @GetMapping("detail")
    public String detail(Model model, HttpSession session, @RequestParam int boardId) {
        Board board = boardMapper.selectBoard(boardId);
        boardMapper.updateViews(boardId);
        model.addAttribute("board", board);
        return "board/detail";
    }

    // ========================================
    // 게시글 수정 호출
    @GetMapping("edit")
    public String eidt(Model model, HttpSession session, @RequestParam int boardId) {
        Board board = boardMapper.selectBoard(boardId);
        model.addAttribute("board", board);
        return "board/edit";
    }
    // 게시글 수정 요청
    @PostMapping("edit")
    public String edit(Board board, HttpSession session) {
        boardMapper.updateBoard(board);
        return "redirect:/board/detail?boardId=" + board.getBoardId();
    }

    // ========================================
    // 게시글 삭제 호출
    @GetMapping("delete")
    public String delete(@RequestParam int boardId) {
        boardMapper.deleteBoard(boardId);
        return "redirect:/board/list";
    }
}
