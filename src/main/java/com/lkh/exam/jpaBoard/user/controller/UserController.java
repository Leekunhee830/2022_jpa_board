package com.lkh.exam.jpaBoard.user.controller;

import com.lkh.exam.jpaBoard.user.dao.UserRepository;
import com.lkh.exam.jpaBoard.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/usr/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("doJoin")
    @ResponseBody
    public String doJoin(String name,String email,String password){

        if (name == null || name.trim().length() == 0) {
            return "이름을 입력해주세요.";
        }
        name = name.trim();

        if (email == null || email.trim().length() == 0) {
            return "이메일을 입력해주세요.";
        }
        email = email.trim();

        boolean existsByEmail = userRepository.existsByEmail(email);

        if ( existsByEmail ) {
            return "입력하신 이메일(%s)은 이미 사용중입니다.".formatted(email);
        }

        if (password == null || password.trim().length() == 0) {
            return "비밀번호를 입력해주세요.";
        }
        password = password.trim();


        User user=new User();
        user.setRegDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        userRepository.save(user);

        return "%d번 회원이 생성되었습니다.".formatted(user.getId());
    }

    @RequestMapping("doLogin")
    @ResponseBody
    public String doLogin(String email, String password, HttpServletRequest req, HttpServletResponse resp){
        if (email == null || email.trim().length() == 0) {
            return "이메일을 입력해주세요.";
        }
        email = email.trim();

        if (password == null || password.trim().length() == 0) {
            return "비밀번호를 입력해주세요.";
        }
        password = password.trim();

        //User user = userRepository.findByEmail(email).orElse(null); 방법1
        Optional<User> user=userRepository.findByEmail(email); //방법2

        if(user.isEmpty()){
            return "일치하는 회원이 존재하지 않습니다.";
        }
        if(user.get().getPassword().equals(password)==false){
            return "비밀번호가 일치하지 않습니다.";
        }

        HttpSession session= req.getSession();
        session.setAttribute("loginedUserId",user.get().getId());
        //Cookie cookie=new Cookie("loginedUserId",user.get().getId()+"");
        //resp.addCookie(cookie);


        return "%s님 환영합니다.".formatted(user.get().getName());
    }

    @RequestMapping("me")
    @ResponseBody
    public User showMe(HttpSession session){
        boolean isLogined =false;
        long loginedUserId=0;
        //Cookie[] cookies=req.getCookies();
        if(session.getAttribute("loginedUserId")!=null){
            isLogined=true;
            loginedUserId=(Long)session.getAttribute("loginedUserId");
        }

        Optional<User> user=userRepository.findById(loginedUserId);

//        if(cookies!=null){
//            for(Cookie cookie:cookies){
//                if(cookie.getName().equals("loginedUserId")){
//                    isLogined=true;
//                    loginedUserId = Long.parseLong(cookie.getValue());
//                }
//            }
//        }

        if(isLogined==false){
            return null;
        }


        if(user.isEmpty()){
            return null;
        }

        return user.get();
    }

    @RequestMapping("doLogout")
    @ResponseBody
    public String doLogout(HttpSession session){
        boolean isLogined =false;

        if(session.getAttribute("loginedUserId")!=null){
            isLogined=true;
        }
        if(isLogined==false){
            return "이미 로그아웃 되었습니다.";
        }

       session.removeAttribute("loginedUserId");

        return "로그아웃 되었습니다.";
    }
}
