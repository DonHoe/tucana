package com.hepic.tucana.web.base;

import com.github.pagehelper.PageHelper;
import com.hepic.tucana.model.shiro.User;
import com.hepic.tucana.util.CommonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器
 */
public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    public void startPage() {
        HttpServletRequest request = getRequest();
        Integer pageNum = CommonUtil.tryParseInteger(request.getParameter("pageNum"), null);
        Integer pageSize = CommonUtil.tryParseInteger(request.getParameter("pageSize"), null);
        if (pageNum == null || pageSize != null) {
            PageHelper.startPage(1, 50);
        } else {
            PageHelper.startPage(pageNum, pageSize);
        }
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public User getCurrentUser() {
        User user = new User();
        try {
            Subject currentUser = SecurityUtils.getSubject();
            user = (User) currentUser.getPrincipal();
        } catch (Exception e) {
            log.error("获取当前用户失败", e);
        }
        return user;
    }
}
