package com.hepic.tucana.dal.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document(collection = "spider_result")
public class SpiderResult extends HashMap<String, Object> {
}
