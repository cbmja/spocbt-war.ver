package com.spocbt.spocbt.mapper;



import com.spocbt.spocbt.dto.Comment;

import java.util.List;

public interface CommentMapper {

    int save(Comment form);

    Comment dupCheck(Comment comment);

    List<Comment> getList(int boardSeq);

    Comment findByCommentSeq(int commentSeq);

    int deleteComment(int commentSeq);

    int updateComment(Comment comment);

}
