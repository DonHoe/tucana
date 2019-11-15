package com.hepic.tucana.web.base;

import com.github.pagehelper.PageHelper;
import com.hepic.tucana.util.CommonUtil;
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
}
