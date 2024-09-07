package org.example.pap.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingCriteriaRepository extends JpaRepository<MatchingCriteria, Long> {
}