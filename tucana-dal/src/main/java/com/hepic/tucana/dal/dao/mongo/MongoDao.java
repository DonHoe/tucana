package com.hepic.tucana.dal.dao.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MongoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void addData(Map<String, Object> data, String collectionName) {
        mongoTemplate.insert(data, collectionName);
    }

    public List<Map<String, Object>> getListByQuery(Query query, String collectionName) {
        Class resultType = HashMap.class;
        return mongoTemplate.find(query, resultType, collectionName);
    }

    public List<Map<String, Object>> getList() {
        Class resultType = HashMap.class;

        return mongoTemplate.findAll(resultType, "spider_result");
    }
}
