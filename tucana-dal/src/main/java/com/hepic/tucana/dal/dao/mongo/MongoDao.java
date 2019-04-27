package com.hepic.tucana.dal.dao.mongo;

import com.hepic.tucana.dal.entity.mongo.SpiderResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MongoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void addData(){
        SpiderResult sr =new SpiderResult();
        sr.setSpId(1);
        sr.setKey(UUID.randomUUID().toString());
        sr.setContent("content");
        mongoTemplate.insert(sr,"spider_result");
    }

    public List<SpiderResult> getList(){
        return mongoTemplate.findAll(SpiderResult.class,"spider_result");
    }
}
