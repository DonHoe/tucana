package com.hepic.tucana.job;

import com.hepic.tucana.dal.dao.mysql.CursorUrlDao;
import com.hepic.tucana.dal.entity.mysql.CursorUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/21.
 */
@Service
public class MySqlScheduler extends DuplicateRemovedScheduler implements MonitorableScheduler {
    public MySqlScheduler() {
        initDuplicateRemover();
    }

    @Autowired
    private CursorUrlDao cursorUrlDao;

    private void initDuplicateRemover() {
        setDuplicateRemover(new DuplicateRemover() {
            @Override
            public boolean isDuplicate(Request request, Task task) {

                return cursorUrlDao.selectByUrl(request.getUrl()) != null;
            }

            @Override
            public void resetDuplicateCheck(Task task) {

            }

            @Override
            public int getTotalRequestsCount(Task task) {
                return cursorUrlDao.selectCount(1);
            }
        });
    }

    @Override
    public int getLeftRequestsCount(Task task) {
        return cursorUrlDao.selectCount(0);
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return getDuplicateRemover().getTotalRequestsCount(task);
    }

    @Override
    public Request poll(Task task) {
        CursorUrl cursorUrl = cursorUrlDao.selectTop();
        if (cursorUrl != null) {
            cursorUrl.setCrawl((byte) 1);
            cursorUrlDao.updateByPrimaryKeySelective(cursorUrl);
            return new Request(cursorUrl.getUrl());
        }
        return null;
    }

    @Override
    public void pushWhenNoDuplicate(Request request, Task task) {
        CursorUrl cursorUrl = new CursorUrl();
        cursorUrl.setCrawl((byte) 0);
        cursorUrl.setUrl(request.getUrl());
        cursorUrlDao.insertSelective(cursorUrl);
    }
}
