package com.eastwest.service;

import com.eastwest.dto.RelationPatchDTO;
import com.eastwest.dto.RelationPostDTO;
import com.eastwest.dto.license.LicenseEntitlement;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.RelationMapper;
import com.eastwest.model.OwnUser;
import com.eastwest.model.Relation;
import com.eastwest.model.WorkOrder;
import com.eastwest.model.enums.RelationType;
import com.eastwest.model.enums.RelationTypeInternal;
import com.eastwest.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class RelationService {
    private final RelationRepository relationRepository;
    private final WorkOrderService workOrderService;
    private final RelationMapper relationMapper;
    private final EntityManager em;
    private final LicenseService licenseService;

    public Relation create(Relation relation) {
        Relation savedRelation = relationRepository.saveAndFlush(relation);
        em.refresh(savedRelation);
        return savedRelation;
    }


    @Transactional
    public Relation update(Long id, RelationPatchDTO relation) {
        if (relationRepository.existsById(id)) {
            Relation savedRelation = relationRepository.findById(id).get();
            Relation updatedRelation = relationRepository.saveAndFlush(relationMapper.updateRelation(savedRelation,
                    relation));
            em.refresh(updatedRelation);
            return updatedRelation;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public Collection<Relation> getAll() {
        return relationRepository.findAll();
    }

    public void delete(Long id) {
        relationRepository.deleteById(id);
    }

    public Optional<Relation> findById(Long id) {
        return relationRepository.findById(id);
    }

    public Collection<Relation> findByCompany(Long id) {
        return relationRepository.findByCompany_Id(id);
    }

    public Relation createPost(RelationPostDTO relationReq, OwnUser user) {
        // License check removed - all features are now free

        WorkOrder parent = relationReq.getParent();
        WorkOrder child = relationReq.getChild();
        RelationTypeInternal relationType = getRelationTypeInternal(relationReq.getRelationType());

        Collection<RelationType> toReverse = Arrays.asList(RelationType.BLOCKED_BY, RelationType.DUPLICATED_BY,
                RelationType.SPLIT_TO);
        if (toReverse.contains(relationReq.getRelationType())) {
            WorkOrder intermediate = child;
            child = parent;
            parent = intermediate;
        }
        Relation relation = Relation.builder()
                .parent(parent)
                .child(child)
                .relationType(relationType).build();
        relation.setCompany(user.getCompany());
        return create(relation);
    }

    private RelationTypeInternal getRelationTypeInternal(RelationType relationType) {
        switch (relationType) {
            case DUPLICATE_OF:
                return RelationTypeInternal.DUPLICATE_OF;
            case RELATED_TO:
                return RelationTypeInternal.RELATED_TO;
            case SPLIT_FROM:
                return RelationTypeInternal.SPLIT_FROM;
            case BLOCKS:
                return RelationTypeInternal.BLOCKS;
            case BLOCKED_BY:
                return RelationTypeInternal.BLOCKS;
            case DUPLICATED_BY:
                return RelationTypeInternal.DUPLICATE_OF;
            case SPLIT_TO:
                return RelationTypeInternal.SPLIT_FROM;
            default:
                return RelationTypeInternal.RELATED_TO;
        }
    }

    public Collection<Relation> findByWorkOrder(Long id) {
        Collection<Relation> whereParent = relationRepository.findByParent_Id(id);
        Collection<Relation> whereChild = relationRepository.findByChild_Id(id);

        return Stream.concat(whereParent.stream(), whereChild.stream())
                .collect(Collectors.toList());
    }

    public Collection<Relation> findByParentAndChild(Long parentId, Long childId) {
        return relationRepository.findByParent_IdAndChild_Id(parentId, childId);
    }

}

