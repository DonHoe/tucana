package com.hepic.tucana.dal.dao.mongo;

import com.hepic.tucana.model.mongo.SpiderResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpiderResultRepository extends MongoRepository<SpiderResult, String> {

}
