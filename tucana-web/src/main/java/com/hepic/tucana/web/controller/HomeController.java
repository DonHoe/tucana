package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.util.exception.BaseException;
import com.hepic.tucana.web.base.BaseController;
import com.hepic.tucana.web.base.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/7/13.
 */
@Slf4j
@RestController
@RequestMapping("/home")
public class HomeController extends BaseController {


    @PostMapping("login")
    public String login(String user, String password, String code,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        CommonResponse<String> responseDto = new CommonResponse<>();
        try {

            Object objCode = request.getSession().getAttribute("code");
            String _code = objCode.toString().toLowerCase();
            if (!code.equals(_code)) {
                throw new BaseException(ResponseEnum.Code_1401.getCode(), ResponseEnum.Code_1401.getMessage());
            }
        } catch (BaseException e) {
            responseDto.setCode(e.getCode());
            responseDto.setMessage(e.getMessage());
        }
        return JSON.toJSONString(response);
    }


    /**
     * 获取随机验证码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getCode")
    public String getCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 设置响应的类型格式为图片格式
            response.setContentType("image/jpeg");
            //禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            HttpSession session = request.getSession();

            ValidateCode vCode = new ValidateCode(120, 40, 4, 100);
            session.setAttribute("code", vCode.getCode());
            vCode.write(response.getOutputStream());
        } catch (Exception e) {
            response.setStatus(409);
        }
        return null;
    }

}
