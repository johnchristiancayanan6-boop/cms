package com.eastwest.repository;

import com.eastwest.model.AdditionalCost;
import com.eastwest.model.SuperAccountRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface SuperAccountRelationRepository extends JpaRepository<SuperAccountRelation, Long> {
    SuperAccountRelation findBySuperUser_IdAndChildUser_Id(Long superUserId, Long childUserId);
}
