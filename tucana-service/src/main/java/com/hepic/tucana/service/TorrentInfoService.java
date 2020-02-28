package com.hepic.tucana.service;
import com.hepic.tucana.model.dal.TorrentInfo;

import java.util.List;

/**
 * 服务接口
 */
public interface TorrentInfoService {

    List<TorrentInfo> selectTorrentInfoListByModel(TorrentInfo model);

    TorrentInfo selectTorrentInfoById(Integer id);

    int insertTorrentInfo(TorrentInfo model);

    int updateTorrentInfo(TorrentInfo model);
}
