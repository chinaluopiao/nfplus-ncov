package com.southcn.nfapp.ncov.dao;

import com.southcn.nfapp.ncov.entity.NfXlsUnifiedData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NfXlsUnifiedDataRepository extends MongoRepository<NfXlsUnifiedData, String> {
}
