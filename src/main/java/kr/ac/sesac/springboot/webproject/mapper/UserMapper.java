package kr.ac.sesac.springboot.webproject.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.ac.sesac.springboot.webproject.model.User;

@Mapper
public interface UserMapper {
    public void join(User user);
    public String getPw(String id);
    public User selectUser(String id);
    public void userUpdate(User user);
}
