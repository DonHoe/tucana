package com.hepic.tucana.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Slf4j
@Service
public class MutualAidPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {


        } catch (Exception e) {
            log.error("数据处理失败", e);
        }
    }
}
