package com.spocbt.spocbt.service;


import com.spocbt.spocbt.controller.Page;
import com.spocbt.spocbt.dto.BoardDetail;
import com.spocbt.spocbt.dto.Comment;
import com.spocbt.spocbt.dto.Member;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final SqlSessionTemplate sql;

    @Transactional
    public String editComment(Comment form , String memberCode){
        String res = null;
        Member member = sql.selectOne("com.spocbt.spocbt.mapper.MemberMapper.findByMemberCode",memberCode);

        if(member == null){
            return "err";
        }

        Comment comment = sql.selectOne("com.spocbt.spocbt.mapper.CommentMapper.findByCommentSeq",form.getCommentSeq());

        if(!member.getMemberCode().equals(comment.getMemberCode())){
            return "notMine";
        }

        int r = -1;
        r = sql.update("com.spocbt.spocbt.mapper.CommentMapper.updateComment" , form);

        if(r <= 0){
            return "err";
        }else {
            return "success";
        }
    }

    @Transactional
    public BoardDetail getDetail(String seq){

        BoardDetail detail = sql.selectOne("com.spocbt.spocbt.mapper.BoardDetailMapper.getDetail",Integer.parseInt(seq));

        Member member = sql.selectOne("com.spocbt.spocbt.mapper.MemberMapper.findByMemberCode",detail.getMemberCode());
        detail.setMemberName(member.getName());

        return detail;
    }

    // 25-01-14 - 1차 ok
    public String editBoard(BoardDetail form){

        int res = -1;
        String r = "err";

        try{
            res = sql.update("com.spocbt.spocbt.mapper.BoardDetailMapper.updateBoard" , form);
        }catch (Exception e){
            e.printStackTrace();
            r = "err";
        }

        if(res == 1){
            r = "success";
        }
        return r;
    }




    @Transactional
    public String create(BoardDetail form){

        Member member = sql.selectOne("com.spocbt.spocbt.mapper.MemberMapper.findByMemberCode",form.getMemberCode());

        form.setMemberName(member.getName());

        BoardDetail exist = sql.selectOne("com.spocbt.spocbt.mapper.BoardDetailMapper.findByTitle",form.getTitle());
        if(exist != null){
            return "dupTitle";
        }
        //form.setMemberCode(memberCode);
        int res = sql.insert("com.spocbt.spocbt.mapper.BoardDetailMapper.create" , form);
        if(res == 1){
            return "ok";
        }else{
            return "err";
        }

    }


    // 25-01-14 - 1차 ok
    @Transactional
    public Map<String , Object> getList(BoardDetail form){

        int total = sql.selectOne("com.spocbt.spocbt.mapper.BoardDetailMapper.getTotal",form);

        Page page = new Page(Integer.parseInt(form.getPage()) , total);

        form.setStartNum(page.getStartNum());
        form.setPageElement(page.getPageElement());

        List<BoardDetail> list = sql.selectList("com.spocbt.spocbt.mapper.BoardDetailMapper.getList",form);
        
        for(BoardDetail b: list){
            if(form.getBoardType().equals("free")){
                b.setBoardType("자유게시판");
            }
            if(form.getBoardType().equals("notice")){
                b.setBoardType("공지");
            }
            if(form.getBoardType().equals("question")){
                b.setBoardType("질문게시판");
            }

            if(b.getContent().length() > 150){
                b.setContent(b.getContent().substring(0, 149) + "......................");
            }
            if(b.getTitle().length() > 52){
                b.setTitle(b.getTitle().substring(0, 51) + "...");
            }
            /*
            Member member = sql.selectOne("com.spocbt.spocbt.mapper.MemberMapper.findByMemberCode",b.getMemberCode());

            b.setMemberName(member.getName());
            */
        }

        Map<String , Object> res = new HashMap<>();
        res.put("page" , page);
        res.put("list" , list);

        return res;
    }


    @Transactional
    public String Comment(Comment form){

        Comment c = sql.selectOne("com.spocbt.spocbt.mapper.CommentMapper.dupCheck",form);

        if(c != null){
            return "dup";
        }

        int res = -1;

            res = sql.insert("com.spocbt.spocbt.mapper.CommentMapper.save",form);

        if(res <= 0){
            return "err";
        }else {
            return "success";
        }

    }

    @Transactional
    public List<Comment> getCommentList(String seq){

        List<Comment> list = new ArrayList<>();

            int boardSeq = Integer.parseInt(seq);

            list = sql.selectList("com.spocbt.spocbt.mapper.CommentMapper.getList",boardSeq);
            if(list == null){
                list = new ArrayList<>();
            }

            for(Comment co: list){
                Member member = sql.selectOne("com.spocbt.spocbt.mapper.MemberMapper.findByMemberCode",co.getMemberCode());
                co.setMemberName(member.getName());
            }


        return list;
    }


    @Transactional
    public String deleteComment(Comment form){

        Comment comment = sql.selectOne("com.spocbt.spocbt.mapper.CommentMapper.findByCommentSeq",form.getCommentSeq());

        form.setBoardSeq(comment.getBoardSeq());

        if(!comment.getMemberCode().equals(form.getMemberCode())){
            return "notMine";
        }

        int res = sql.delete("com.spocbt.spocbt.mapper.CommentMapper.deleteComment",form.getCommentSeq());

        if(res <= 0){
            return "err";
        }

        return "success";
    }


    @Transactional
    public String deleteBoard(int boardSeq , String memberCode){

        BoardDetail b = sql.selectOne("com.spocbt.spocbt.mapper.BoardDetailMapper.getDetail",boardSeq);

        if(!b.getMemberCode().equals(memberCode)){
            return "notMine";
        }

        int res = sql.delete("com.spocbt.spocbt.mapper.BoardDetailMapper.deleteBoard" , boardSeq);

        if(res <= 0){
            return "err";
        }else {
            return "success";
        }
    }



}
