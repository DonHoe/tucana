package com.zk.device.web.base;

import com.github.pagehelper.PageHelper;
import com.zk.device.model.shiro.User;
import com.zk.device.util.CommonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器
 */
public class BaseController {

    public void startPage() {
        HttpServletRequest request = getRequest();
        Integer pageNum = CommonUtil.tryParseInteger(request.getParameter("pageNum"), null);
        Integer pageSize = CommonUtil.tryParseInteger(request.getParameter("pageSize"), null);
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(1, 50);
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
        Subject currentUser = SecurityUtils.getSubject();
        return (User) currentUser.getPrincipal();
    }
}
