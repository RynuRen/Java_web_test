package kr.ac.sesac.springboot.webproject.model;

import java.util.Date;

import lombok.Data;

@Data
public class Board {
    private int boardId;
    private int rnum;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private String boardWriterId;
    private Date boardCreateDate;
    private Date boardUpdateDate;
    private int boardViews;
    private int boardThumbUp;
    private int boardThumbDown;
}
