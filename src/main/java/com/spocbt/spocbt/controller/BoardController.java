package com.spocbt.spocbt.controller;


import com.spocbt.spocbt.dto.BoardDetail;
import com.spocbt.spocbt.dto.Comment;
import com.spocbt.spocbt.service.BoardService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    // 25-01-14 - 1차 ok
    // 게시판 목록
    @GetMapping("/list")
    public String list(Model model, ServletRequest request, @RequestParam(name = "type" , defaultValue = "free")String type
            , @RequestParam(name = "search" , defaultValue = "")String search
            , @RequestParam(name = "searchType" , defaultValue = "title")String searchType
            , @RequestParam(name = "page" , defaultValue = "1")String page
            , @RequestParam(name = "sort" , defaultValue = "desc")String sort){

        try{
            BoardDetail form = new BoardDetail();
            form.setSearch(search);
            form.setSearchType(searchType);
            form.setBoardType(type);
            form.setPage(page);
            form.setSort(sort);

            Map<String , Object> r = boardService.getList(form);


            model.addAttribute("list",(List<BoardDetail>)(r.get("list")));
            model.addAttribute("page",(Page)(r.get("page")));
            model.addAttribute("type" , type);
            model.addAttribute("search" , search);
            model.addAttribute("searchType" , searchType);
            model.addAttribute("sort" , sort);
            model.addAttribute("selectedNav" , "board");
            model.addAttribute("navText" , "체육인들의 커뮤니티");
            model.addAttribute("isLogin" , (String)request.getAttribute("isLogin"));

            return "view/boardList";

        }catch (Exception e){
            e.printStackTrace();

            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
            model.addAttribute("selectedNav" , "board");
            model.addAttribute("navText" , "server error");
            return "util/prepare";
        }

    }


    // 25-01-14 - 1차 ok
    // 게시판 글 작성 화면 이동
    @GetMapping("/form")
    public String form(Model model, ServletRequest request, @RequestParam(name = "type" , defaultValue = "free")String type){

        try{
            model.addAttribute("type" , type);
            String s ;
            if(type.equals("free")){
                s = "자유게시판";
            }else if(type.equals("notice")){
                s = "공지게시판";
            }else{
                s = "질문게시판";
            }

            model.addAttribute("typeTitle" , s);
            model.addAttribute("selectedNav" , "board");
            model.addAttribute("isLogin" , (String)request.getAttribute("isLogin"));
            model.addAttribute("navText" , "[ "+s+" ]");
            model.addAttribute("memberCode" , (String)request.getAttribute("memberCode"));
            return "view/boardForm";
        }catch (Exception e){
            e.printStackTrace();

            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
            model.addAttribute("selectedNav" , "board");
            model.addAttribute("navText" , "server error");
            return "util/prepare";
        }


    }

    // 25-01-14 - 1차 ok
    // 게시판 글 작성
    @PostMapping("/submit")
    @ResponseBody
    public String submit(Model model, ServletRequest request, @RequestBody BoardDetail form){
        String res;
        try{
            res = boardService.create(form);
        }catch (Exception e){
            e.printStackTrace();
            return "err";
        }

        return res;
    }

    // 25-01-14 - 1차 ok
    // 게시판 상세
    @GetMapping("/detail")
    public String detail(Model model, ServletRequest request, @RequestParam(name = "seq" , defaultValue = "")String seq){

        try{
            BoardDetail detail = boardService.getDetail(seq);
            List<Comment> comments = boardService.getCommentList(seq);
            if(comments == null){
                comments = new ArrayList<>();
            }

            String boardTitle ;
            if(detail.getBoardType().equals("free")){
                boardTitle = "자유게시판";
            }else if(detail.getBoardType().equals("notice")){
                boardTitle = "공지게시판";
            }else{
                boardTitle = "질문게시판";
            }

            model.addAttribute("memberCode" , (String)request.getAttribute("memberCode"));
            model.addAttribute("commentList" , comments);
            model.addAttribute("detail" , detail);
            model.addAttribute("type" , boardTitle);
            model.addAttribute("selectedNav" , "board");
            model.addAttribute("navText" , "[ "+boardTitle+" ]");
            return "view/boardDetail";
        }catch (Exception e){
            e.printStackTrace();
            logger.error("endpoint---------------------------/border/detail",e);
            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
            model.addAttribute("selectedNav" , "board");
            model.addAttribute("navText" , "server error");
            return "util/prepare";
        }

    }

    // 25-01-14 - 1차 ok
    // 게시판 글 삭제
    @PostMapping("/delete")
    @ResponseBody
    public String boardDelete(Model model, ServletRequest request, @RequestBody BoardDetail form){


        String res = "";
        try{
            String memberCode = (String)request.getAttribute("memberCode");
            res = boardService.deleteBoard(form.getBoardSeq() , memberCode);
        }catch (Exception e){
            e.printStackTrace();
            res = "err";
        }

        return res;
    }

    // 25-01-14 - 1차 ok
    // 게시판 글 수정 화면 이동
    @GetMapping("/edit")
    public String boardEdit(Model model, ServletRequest request, @RequestParam(name = "boardSeq" , defaultValue = "") String boardSeq){


        try{

            String memberCode = (String)request.getAttribute("memberCode");

            BoardDetail board = boardService.getDetail(boardSeq);

            model.addAttribute("type" , board.getBoardType());
            String s ;
            if(board.getBoardType().equals("free")){
                s = "자유게시판";
            }else if(board.getBoardType().equals("notice")){
                s = "공지게시판";
            }else{
                s = "질문게시판";
            }

            model.addAttribute("typeTitle" , s);
            model.addAttribute("selectedNav" , "board");
            model.addAttribute("isLogin" , (String)request.getAttribute("isLogin"));
            model.addAttribute("navText" , "[ "+s+" ]");
            model.addAttribute("memberCode" , memberCode);
            model.addAttribute("edit" , "1");
            model.addAttribute("board", board);

            return "view/boardForm";
        }catch (Exception e){
            e.printStackTrace();

            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
            model.addAttribute("selectedNav" , "board");
            model.addAttribute("navText" , "server error");
            return "util/prepare";
        }

    }

    // 25-01-14 - 1차 ok
    // 게시판 글 수정
    @PostMapping("/edit")
    @ResponseBody
    public String edit(Model model, ServletRequest request, @RequestBody BoardDetail form){



        return boardService.editBoard(form);
    }

    // 25-01-14 - 1차 ok
    // 댓글 작성
    @PostMapping("/comment/submit")
    @ResponseBody
    public Map<String , String> commentSubmit(Model model, ServletRequest request, @RequestBody Comment form){
        Map<String , String> r = new HashMap<>();
        try{
            String memberCode = (String)request.getAttribute("memberCode");
            form.setMemberCode(memberCode);

            String res;
            List<Comment> c = new ArrayList<>();
            String appendStr = "";

            res = boardService.Comment(form);

            c = boardService.getCommentList(form.getBoardSeq()+"");
            if(c == null){
                c = new ArrayList<>();
            }

            for(Comment comment : c){

                String sub = comment.getMemberCode().equals((String)request.getAttribute("memberCode")) ?
                        "<div class=\"comment-i-r\" id=\""+comment.getCommentSeq()+"-i-r\">\n" +
                        "                        <span class=\"comment-edit\" data-commentseq=\""+comment.getCommentSeq()+"\">수정</span>\n" +
                        "                        <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>\n" +
                        "                        <span class=\"comment-delete\" data-commentseq=\""+comment.getCommentSeq()+"\">삭제</span>\n" +
                        "                    </div>\n" : "";

                String str = "            <div style=\"width: 100%;\">\n" +
                        "                <div class=\"comment-info\">\n" +
                        "                    <div class=\"comment-i-l\"><span>"+comment.getCreateAt()+"&nbsp;&nbsp;|&nbsp;&nbsp;"+comment.getMemberName()+"</span></div>\n" +
                        sub
                        +
                        "                </div>\n" +
                        "                <textarea class=\"comment-ele\" placeholder=\"댓글\" wrap=\"hard\" readonly  id=\""+comment.getCommentSeq()+"\">"+comment.getContent()+"</textarea>\n" +
                        "            </div>";
                appendStr += str;
            }
            r.put("response" , res);
            r.put("appendStr" , appendStr);

            return r;
        }catch (Exception e){
            e.printStackTrace();

            r.put("response" , "err");
            r.put("appendStr" , "");

            return r;
        }

    }

    // 25-01-14 - 1차 ok
    // 댓글 수정
    @PostMapping("/comment/edit")
    @ResponseBody
    public String commentEdit(Model model, ServletRequest request, @RequestBody Comment form){

        String res = "";

        try {
            String memberCode = (String)request.getAttribute("memberCode");
            res = boardService.editComment(form , memberCode);
        }catch (Exception e){
            e.printStackTrace();
            res = "err";

        }

        return res;
    }

    // 25-01-14 - 1차 ok
    // 댓글 삭제
    @PostMapping("/comment/delete")
    @ResponseBody
    public Map<String , String> deleteComment(Model model, ServletRequest request, @RequestBody Comment form){


        Map<String , String> r = new HashMap<>();
        try{
            String res = "";
            List<Comment> c = new ArrayList<>();
            String appendStr = "";

            String memberCode = (String)request.getAttribute("memberCode");
            form.setMemberCode(memberCode);

            res = boardService.deleteComment(form);

            c = boardService.getCommentList(form.getBoardSeq()+"");
            if(c == null){
                c = new ArrayList<>();
            }

            for(Comment comment : c){

                String sub = comment.getMemberCode().equals((String)request.getAttribute("memberCode")) ?
                        "<div class=\"comment-i-r\" id=\""+comment.getCommentSeq()+"-i-r\">\n" +
                                "                        <span class=\"comment-edit\" data-commentseq=\""+comment.getCommentSeq()+"\">수정</span>\n" +
                                "                        <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>\n" +
                                "                        <span class=\"comment-delete\" data-commentseq=\""+comment.getCommentSeq()+"\">삭제</span>\n" +
                                "                    </div>\n" : "";

                String str = "            <div style=\"width: 100%;\">\n" +
                        "                <div class=\"comment-info\">\n" +
                        "                    <div class=\"comment-i-l\"><span>"+comment.getCreateAt()+"&nbsp;&nbsp;|&nbsp;&nbsp;"+comment.getMemberName()+"</span></div>\n" +
                        sub
                        +
                        "                </div>\n" +
                        "                <textarea class=\"comment-ele\" placeholder=\"댓글\" wrap=\"hard\" readonly  id=\""+comment.getCommentSeq()+"\">"+comment.getContent()+"</textarea>\n" +
                        "            </div>";
                appendStr += str;

            }

            r.put("response" , res);
            r.put("appendStr" , appendStr);

            return r;
        }catch (Exception e){
            e.printStackTrace();
            r.put("response" , "err");
            r.put("appendStr" , "");

            return r;
        }

    }






}
