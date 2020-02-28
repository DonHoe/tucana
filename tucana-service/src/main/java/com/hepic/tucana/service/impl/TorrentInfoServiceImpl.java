package com.hepic.tucana.service.impl;

import com.hepic.tucana.dal.dao.TorrentInfoDao;
import com.hepic.tucana.model.dal.TorrentInfo;
import com.hepic.tucana.service.TorrentInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 服务实现
 */

@Service
public class TorrentInfoServiceImpl implements TorrentInfoService {

    private static final Logger log = LoggerFactory.getLogger(TorrentInfoService.class);

    @Autowired
    TorrentInfoDao torrentInfoDao;

    @Override
    public List<TorrentInfo> selectTorrentInfoListByModel(TorrentInfo model){
        return torrentInfoDao.selectTorrentInfoListByModel(model);
    }

    @Override
    public TorrentInfo selectTorrentInfoById(Integer id){
        return torrentInfoDao.selectTorrentInfoById(id);
    }

    @Override
    public int insertTorrentInfo(TorrentInfo model){
        return torrentInfoDao.insertTorrentInfo(model);
    }

    @Override
    public int updateTorrentInfo(TorrentInfo model){
        return torrentInfoDao.updateTorrentInfo(model);
    }
}
