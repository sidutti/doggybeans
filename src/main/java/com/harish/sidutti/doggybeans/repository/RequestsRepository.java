package com.harish.sidutti.doggybeans.repository;

import com.harish.sidutti.doggybeans.dto.Requests;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestsRepository extends ReactiveMongoRepository<Requests, String> {

}
