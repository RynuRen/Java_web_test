package kr.ac.sesac.springboot.webproject.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kr.ac.sesac.springboot.webproject.model.Board;

@Mapper
public interface BoardMapper {
    public ArrayList<Board> getList(int startPost, int countList);
    public int getTotal();
    public void putBoard(Board board);
    public Board selectBoard(int boardId);
    public void deleteBoard(int boardId);
    public void updateBoard(Board board);
    public void updateViews(int boardId);
}
