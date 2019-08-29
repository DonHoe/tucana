package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.model.spider.MovieRate;
import com.hepic.tucana.job.PageInfoSpider;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.impl.DoubanMovieService;
import com.hepic.tucana.util.exception.BaseException;
import com.hepic.tucana.web.base.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/7/13.
 */
@Slf4j
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    PageInfoSpider pageInfoSpider;

    @Autowired
    DoubanMovieService doubanMovieService;

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

    /**
     * 抓取
     *
     * @param url
     * @return
     */
    @RequestMapping("/startSpider")
    public String startSpider(String url) {
        CommonResponse<String> responseDto = new CommonResponse<>();
        try {
            Spider spider = pageInfoSpider.getSpider(url);
            if (spider == null) {
                throw new BaseException(2000, "build failed");
            }
            spider.start();
        } catch (BaseException e) {
            responseDto.setCode(e.getCode());
            responseDto.setMessage(e.getMessage());
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("JOB启动异常", e);
        }
        return JSON.toJSONString(responseDto);
    }

    @RequestMapping("/getRatePercent")
    public String getRatePercent(String url) {
        CommonResponse<List<MovieRate>> responseDto = new CommonResponse<>();
        try {
            List<MovieRate> movieRates = doubanMovieService.getRatePercent();
            responseDto.setResult(movieRates);
        } catch (BaseException e) {
            responseDto.setCode(e.getCode());
            responseDto.setMessage(e.getMessage());
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("", e);
        }
        return JSON.toJSONString(responseDto);
    }

}
