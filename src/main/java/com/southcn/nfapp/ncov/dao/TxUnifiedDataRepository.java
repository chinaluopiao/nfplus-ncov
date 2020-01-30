package com.southcn.nfapp.ncov.dao;

import com.southcn.nfapp.ncov.entity.TxUnifiedData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TxUnifiedDataRepository extends MongoRepository<TxUnifiedData, String> {
}
