package com.harish.sidutti.doggybeans.repository;

import com.harish.sidutti.doggybeans.dto.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends ReactiveMongoRepository<Document, String> {
}
