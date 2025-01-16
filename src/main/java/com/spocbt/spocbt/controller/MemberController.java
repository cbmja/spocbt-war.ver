package com.spocbt.spocbt.controller;


import com.spocbt.spocbt.dto.BoardDetail;
import com.spocbt.spocbt.dto.Member;
import com.spocbt.spocbt.dto.TestRecord;
import com.spocbt.spocbt.service.MemberService;
import com.spocbt.spocbt.util.LoginDto;
import com.spocbt.spocbt.util.LoginUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final LoginUtil loginUtil;


    @Value("${kakao.restApiKey}")
    private String kakao_restApiKey;
    @Value("${kakao.redirectUri}")
    private String kakao_redirectUri;

    @Value("${naver.clientId}")
    private String naver_clientId;
    @Value("${naver.clientSecret}")
    private String naver_clientSecret;
    @Value("${naver.redirectUri}")
    private String naver_redirectUri;

    // 24-01-03 : ok--1
    // 25-01-14 - 2차 ok
    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletResponse response,Model model){
        Cookie cookie = new Cookie("idCookie", null);

        // 경로 설정 (원래 쿠키 경로와 동일해야 함)
        cookie.setPath("/"); // 필요에 따라 경로를 지정

        // 만료 시간을 0으로 설정 (즉시 삭제)
        cookie.setMaxAge(0);

        // 응답에 추가
        response.addCookie(cookie);

        model.addAttribute("isLogin" , "0");

        return "redirect:/exam/list";
    }

    // 24-01-03 : ok--1
    // 25-01-14 - 2차 ok
    // 시험이력
    @GetMapping("/history")
    public String history(Model model, ServletRequest request){

        try{
            List<TestRecord> history;
            String memberCode = (String)request.getAttribute("memberCode");
            history = memberService.myTestHistory(memberCode);

            model.addAttribute("isLogin" , (String)request.getAttribute("isLogin"));

            model.addAttribute("selectedNav" , "mypage");
            model.addAttribute("history",history);
            String nt = "";
            if(history.size() == 1 && !history.get(0).getErr().isBlank()){
                nt = "서버 에러입니다. 다시 시도해주세요.";
            }else {
                nt = history.isEmpty() ? "시험 이력이 없습니다!!":"내 시험 이력을 확인하세요!!";
            }
            model.addAttribute("navText" , nt);
            model.addAttribute("subMenu" , "history");

            return "view/mypage/history";

        }catch (Exception e){
            e.printStackTrace();

            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
            model.addAttribute("selectedNav" , "mypage");
            model.addAttribute("navText" , "server error");
            return "util/prepare";
        }

    }

    @GetMapping("/myBoard")
    public String list(Model model, ServletRequest request, @RequestParam(name = "type" , defaultValue = "free")String type
            , @RequestParam(name = "search" , defaultValue = "")String search
            , @RequestParam(name = "searchType" , defaultValue = "title")String searchType
            , @RequestParam(name = "page" , defaultValue = "1")String page
            , @RequestParam(name = "sort" , defaultValue = "desc")String sort){

        try{
            String memberCode = (String)request.getAttribute("memberCode");
            BoardDetail form = new BoardDetail();
            form.setMemberCode(memberCode);
            form.setSearch(search);
            form.setSearchType(searchType);
            form.setBoardType(type);
            form.setPage(page);
            form.setSort(sort);

            Map<String , Object> r = memberService.getMyList(form);


            model.addAttribute("list",(List<BoardDetail>)(r.get("list")));
            model.addAttribute("page",(Page)(r.get("page")));
            model.addAttribute("type" , type);
            model.addAttribute("search" , search);
            model.addAttribute("searchType" , searchType);
            model.addAttribute("sort" , sort);
            model.addAttribute("selectedNav" , "mypage");
            model.addAttribute("subMenu" , "myBoard");
            model.addAttribute("isLogin" , (String)request.getAttribute("isLogin"));
            model.addAttribute("navText" , "내가 쓴 게시물");
            return "view/mypage/myBoard";

        }catch (Exception e){
            e.printStackTrace();

            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
            model.addAttribute("selectedNav" , "mypage");
            model.addAttribute("navText" , "server error");
            return "util/prepare";
        }

    }

    // 24-01-03 : ok--1
    // 25-01-14 - 2차 ok
    // 로그인
    @GetMapping("/login")
    public String login(Model model, ServletRequest request){
        model.addAttribute("selectedNav" , "login");
        model.addAttribute("isLogin" , (String)request.getAttribute("isLogin"));
        model.addAttribute("navText" , "간편하게 로그인하세요!");

        return "view/login";
    }

    // 25-01-14 - 1차 ok
    // 소셜 인가
    @GetMapping("/auth")
    public RedirectView auth(@RequestParam(name = "type")String type){
        String url = "";
        switch(type){

            case "kakao":
            url = "https://kauth.kakao.com/oauth/authorize"
                    +"?client_id=" + kakao_restApiKey // 필수
                    +"&redirect_uri=" + kakao_redirectUri // 필수
                    +"&response_type=code" // 필수 'code' 고정
                    +"&state=1234" // 선택 csrf 방어
                    +"&scope=openid" // 선택 id_token 발급받기 위해
                    +"&nonce=1234"; // 선택 id_token 위조방지
                break;

            case "naver":
                url = "https://nid.naver.com/oauth2.0/authorize"
                        +"?response_type=code" // 필수
                        +"&client_id=" + naver_clientId // 필수
                        +"&redirect_uri=" + naver_redirectUri // 필수 'code' 고정
                        +"&state=1234";
                break;

        }

        return new RedirectView(url);
    }

    // 25-01-14 - 1차 ok
    // 콜백
    @GetMapping("/kakao/callback")
    public String kakaoAuth(Model model,@RequestParam(name = "code" , required = false , defaultValue = "")String code , @RequestParam(name = "error" , required = false , defaultValue = "")String error, HttpServletResponse response){
        try{
            if(code.isBlank()){
                model.addAttribute("isErr" , "1");
                model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
                return "util/prepare";
            }

            LoginDto callback = loginUtil.getKakaoToken(code);

            if(callback == null || !"success".equals(loginUtil.verifyToken(callback.getIdToken() , callback))){
                model.addAttribute("isErr" , "1");
                model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
                return "util/prepare";
            }

            String loginId = callback.getLoginId();

            if(memberService.loginMember("kakao",loginId,response,"callback",new Member()).equals("login")){
                model.addAttribute("isLogin","1");
                return "redirect:/exam/list";
            }else{
                model.addAttribute("isLogin","0");
                model.addAttribute("navText" , "회원가입을 환영합니다!");
                model.addAttribute("loginType" , "kakao");
                model.addAttribute("loginId" , loginId);

                return "view/join";
            }
        }catch (Exception e){
            e.printStackTrace();

            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
            model.addAttribute("selectedNav" , "mypage");
            model.addAttribute("navText" , "server error");
            return "util/prepare";
        }

    }


    // 25-01-14 - 1차 ok
    // 콜백
    @GetMapping("/naver/callback")
    public String naverAuth(Model model,@RequestParam(name = "code" , required = false , defaultValue = "")String code , @RequestParam(name = "error" , required = false , defaultValue = "")String error, HttpServletResponse response){
        try{
            if(code.isBlank()){
                model.addAttribute("isErr" , "1");
                model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
                return "util/prepare";
            }

            LoginDto callback = loginUtil.getNaverToken(code);
            String res = loginUtil.getNaverProfile(callback.getAccessToken());
            if(callback == null || "err".equals(res)){
                model.addAttribute("isErr" , "1");
                model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
                return "util/prepare";
            }

            String loginId = res;

            if(memberService.loginMember("naver",loginId,response,"callback",new Member()).equals("login")){
                model.addAttribute("isLogin","1");
                return "redirect:/exam/list";
            }else{
                model.addAttribute("isLogin","0");
                model.addAttribute("navText" , "회원가입을 환영합니다!");
                model.addAttribute("loginType" , "naver");
                model.addAttribute("loginId" , loginId);

                return "view/join";
            }
        }catch (Exception e){
            e.printStackTrace();

            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
            model.addAttribute("selectedNav" , "mypage");
            model.addAttribute("navText" , "server error");
            return "util/prepare";
        }

    }
    // 25-01-14 - 1차 ok
    // 회원가입
    @PostMapping("/join")
    @ResponseBody
    public String joinProc(@RequestBody Member form , HttpServletResponse response , Model model, ServletRequest request){

        try{
            if(memberService.loginMember(form.getLoginType(),form.getLoginId(),response,"join" ,form).equals("join")){
                return "success";
            }else {
                return "err";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "err";
        }
    }

    // 25-01-14 - 1차 ok
    // 닉네임 중복체크
    @GetMapping("/check")
    @ResponseBody
    public String join(@RequestParam(name = "name")String name){

        try {
            if(memberService.checkName(name)){
                return "ok";
            }else {
                return "no";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "err";
        }

    }


}
