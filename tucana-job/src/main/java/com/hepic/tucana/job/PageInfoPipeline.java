package com.hepic.tucana.job;

import com.hepic.tucana.dal.dao.mysql.PageInfoDao;
import com.hepic.tucana.dal.entity.mysql.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/21.
 */
@Slf4j
@Service
public class PageInfoPipeline implements Pipeline {

    @Autowired
    PageInfoDao pageInfoDao;

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            if (StringUtils.isNotBlank(resultItems.get("code"))) {
                return;
            }
            PageInfo query = new PageInfo();
            query.setCode(resultItems.get("code"));
            List<PageInfo> list = pageInfoDao.selectListByModel(query);
            if (CollectionUtils.isEmpty(list)) {
                query.setTitle(resultItems.get("title"));
                query.setContent(resultItems.get("content"));
                query.setImgList(resultItems.get("imgList"));
                pageInfoDao.insert(query);
            }
        } catch (Exception e) {
            log.error("数据处理失败", e);
        }
    }
}
