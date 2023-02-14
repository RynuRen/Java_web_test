package kr.ac.sesac.springboot.webproject.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.sesac.springboot.webproject.mapper.UserMapper;
import kr.ac.sesac.springboot.webproject.model.User;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserMapper userMapper;

    // ========================================
    // 계정 생성 호출
    @GetMapping("join")
    public String join() {
        return "user/join";
    }
    // 계정 생성 요청
    @PostMapping("join")
    public String join(User user) {
        userMapper.join(user);
        return "redirect:/";
    }

    // ========================================
    // 로그인 호출
    @GetMapping("login")
    public String login() {
        return "user/login";
    }
    // 로그인 요청
    @PostMapping("login")
    public String login(User user, HttpSession session) {
        String id = user.getUserId();
        String pw = user.getUserPw();
        String getPw = userMapper.getPw(id);
        if (getPw != null) {
            if (getPw.equals(pw)) {
                User userData = userMapper.selectUser(id);
                session.setAttribute("user", userData);
                return "redirect:/";
            }
        } else {
            session.setAttribute("user", null);
        }
        return "user/loginFail";
    }

    // ========================================
    // 로그아웃 호출
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    // ========================================
    // 계정 수정 호출
    @GetMapping("userDetail")
    public String userDetail(HttpSession session) {
        return "user/userDetail";
    }
    // 계정 수정 요청
    @PostMapping("userDetail")
    public String userDetail(String fixPw, User user, HttpSession session) {
        User curUser = (User) session.getAttribute("user");
        // 현재 세션 유저의 id로 DB에서 pw를 구해 입력받은 pw와 비교
        String getPw = userMapper.getPw(curUser.getUserId());
        if (getPw.equals(user.getUserPw())) {
            // 수정할 pw가 있다면 DB로 보내기전에 변경
            if (fixPw != null) {
                user.setUserPw(fixPw);
            }
            userMapper.userUpdate(user);
            // 현재 세션에 반영
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "user/changeFail";
    }
}
