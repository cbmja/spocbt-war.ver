package com.spocbt.spocbt.filter;


import com.spocbt.spocbt.service.MemberService;
import com.spocbt.spocbt.util.LoginUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class LoginCheck  implements Filter {

    private final LoginUtil loginUtil;
    private final MemberService memberService;
    private static final Logger logger = LoggerFactory.getLogger(LoginCheck.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        String reqUri = req.getRequestURI();



        try{

            Cookie[] cookies = req.getCookies();
            String idCookie = "";
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("idCookie".equals(cookie.getName())) {
                        idCookie = cookie.getValue(); // 원하는 쿠키 반환
                    }

                }
            }
            String r = !idCookie.isEmpty() ? "1":"0";
            request.setAttribute("isLogin" , r);

            if(reqUri.contains("/exam/test") || reqUri.contains("/member/history") || reqUri.contains("/board/form") || reqUri.contains("/board/comment/submit")
                    || reqUri.contains("/board/detail") || reqUri.contains("/board/comment/delete") || reqUri.contains("board/delete")
                    || reqUri.contains("/board/edit") || reqUri.contains("/board/comment/edit") || reqUri.contains("/member/myBoard")){
                if (!reqUri.endsWith(".js") && !reqUri.endsWith(".css")) {
                    logger.info("Request URI: {}", reqUri);
                }
                // 로그인 필요
                if(!idCookie.isBlank()){
                    String memberCode = loginUtil.decrypt(idCookie);
                    String mc = memberService.findByMemberCode(memberCode);

                    if(mc.isEmpty()){ // idCookie 값이 잘못된 경우 --------------------------------------------------------
                        httpResponse.sendRedirect("/member/login");
                        return;
                    }else{ // 정상 로그인일 경우 --------------------------------------------------------------------------
                        request.setAttribute("memberCode" , mc);
                        logger.info("memberCode---------------------------"+mc);
                        chain.doFilter(request, response);
                        return;
                    }

                }else if(reqUri.contains("/board/detail") || reqUri.contains("/comment/delete")){
                    chain.doFilter(request, response);
                    return;
                }else {
                    /*if(reqUri.contains("/exam/test")){
                        httpResponse.sendRedirect("/member/flogin");
                        return;
                    } else if (reqUri.contains("/member/history") || reqUri.contains("/board/form") || reqUri.contains("/board/comment/submit")) {
                        httpResponse.sendRedirect("/member/login");
                        return;
                    }*/
                    httpResponse.sendRedirect("/member/login");
                    return;
                }
                //chain.doFilter(request, response);
                //return;
            }else{
                // 로그인 불필요



                chain.doFilter(request, response);
                return;
            }

        }catch(Exception e){
            e.printStackTrace();
            logger.error("endpoint---------------------------LoginCheck",e);
            httpResponse.sendRedirect("/exam/list");
            return;
        }

    }




}
