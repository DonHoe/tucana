package com.hepic.tucana.dal.dao;

import com.hepic.tucana.model.dal.TorrentInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TorrentInfoDao {

    List<TorrentInfo> selectTorrentInfoListByModel(TorrentInfo model);

    TorrentInfo selectTorrentInfoById(Integer id);

    int insertTorrentInfo(TorrentInfo model);

    int updateTorrentInfo(TorrentInfo model);
}
