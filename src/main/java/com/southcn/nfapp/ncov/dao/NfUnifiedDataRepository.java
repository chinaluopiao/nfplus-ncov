package com.southcn.nfapp.ncov.dao;

import com.southcn.nfapp.ncov.entity.NfUnifiedData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NfUnifiedDataRepository extends MongoRepository<NfUnifiedData, String> {
}
