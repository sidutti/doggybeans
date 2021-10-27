package com.harish.sidutti.doggybeans.repository;

import com.harish.sidutti.doggybeans.dto.Quote;
import com.harish.sidutti.doggybeans.dto.Stats;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends ReactiveMongoRepository<Stats, String> {
}
