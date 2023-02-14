package kr.ac.sesac.springboot.webproject.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {
    private String userId;
    private String userPw;
    private String userNick;
    private String userEmail;
    private Date userCreateDate;
    // private int userGender;
}
