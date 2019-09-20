package com.hepic.tucana.job.scheduler;

import com.hepic.tucana.util.datetime.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PicScheduler {

    //每天3：05执行
    @Scheduled(cron = "0 05 03 ? * *")
    public void downloadPic() {
        System.out.println("定时任务执行时间：" + DateUtil.date2String(new Date()));
    }
}
