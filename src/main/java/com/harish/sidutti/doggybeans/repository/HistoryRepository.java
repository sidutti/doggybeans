package com.harish.sidutti.doggybeans.repository;

import com.harish.sidutti.doggybeans.dto.History;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends ReactiveMongoRepository<History, String> {
}
