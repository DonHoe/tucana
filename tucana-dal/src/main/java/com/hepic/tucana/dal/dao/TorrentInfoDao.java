package com.hepic.tucana.dal.dao;

import com.hepic.tucana.model.dal.TorrentInfo;

import java.util.List;

public interface TorrentInfoDao {

    List<TorrentInfo> selectTorrentInfoListByModel(TorrentInfo model);

    TorrentInfo selectTorrentInfoById(Integer id);

    int insertTorrentInfo(TorrentInfo model);

    int updateTorrentInfo(TorrentInfo model);
}
