package com.spocbt.spocbt.mapper;


import com.spocbt.spocbt.dto.BoardDetail;

import java.util.List;

public interface BoardDetailMapper {

    int create(BoardDetail form);

    BoardDetail findByTitle(String title);

    int getTotal(BoardDetail search);

    BoardDetail getDetail(int boardSeq);

    List<BoardDetail> getList(BoardDetail form);

    int deleteBoard(int boardSeq);

    int updateBoard(BoardDetail form);
}
