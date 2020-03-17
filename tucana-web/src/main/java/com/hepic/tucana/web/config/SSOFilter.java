package com.hepic.tucana.web.config;

import com.hepic.tucana.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SSOFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (SecurityUtils.getSubject().isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = req.getParameter("token");
        if (StringUtils.isNotBlank(token)) {
            Subject currentUser = SecurityUtils.getSubject();
            UsernamePasswordToken upToken = new UsernamePasswordToken(token, StringUtils.EMPTY);
            try {
                currentUser.login(upToken);
                filterChain.doFilter(request, response);
                return;
            } catch (Exception e){
                res.sendRedirect("http://a.device.com:5566/login?callback=http://b.device.com:5555/home");
            }
        }
        //跳转至sso认证中心
        res.sendRedirect("http://a.device.com:5566/login?callback=http://b.device.com:5555/home");
    }

    @Override
    public void destroy() {

    }
}
