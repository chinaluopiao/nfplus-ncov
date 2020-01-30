package com.southcn.nfapp.ncov.dao;

import com.southcn.nfapp.ncov.entity.DxyUnifiedData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DxyUnifiedDataRepository extends MongoRepository<DxyUnifiedData, String> {
}
