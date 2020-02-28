package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.model.dal.TorrentInfo;
import com.hepic.tucana.service.TorrentInfoService;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.web.base.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制类
 */

@RestController
@RequestMapping("/torrentInfo")
public class TorrentInfoController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(TorrentInfoController.class);

    @Autowired
    TorrentInfoService torrentInfoService;

    @GetMapping("/selectTorrentInfoListByModel")
    public String selectTorrentInfoListByModel() {
        CommonResponse<List<TorrentInfo>> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            TorrentInfo model = new TorrentInfo();
            List<TorrentInfo> list = torrentInfoService.selectTorrentInfoListByModel(model);
            responseDto.setResult(list);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("请求异常", e);
        }
        return JSON.toJSONString(responseDto);
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(TorrentInfo torrentInfo) {
        CommonResponse<Integer> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = 0;
            if (torrentInfo.getId() == null || torrentInfo.getId() == 0) {
                result = torrentInfoService.insertTorrentInfo(torrentInfo);
            } else {
                result = torrentInfoService.updateTorrentInfo(torrentInfo);
            }
            responseDto.setResult(result);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("请求异常", e);
        }
        return JSON.toJSONString(responseDto);
    }
}
