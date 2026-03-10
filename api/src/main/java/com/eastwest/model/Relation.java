package com.eastwest.model;

import com.eastwest.model.abstracts.CompanyAudit;
import com.eastwest.model.enums.RelationTypeInternal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Relation extends CompanyAudit {
    @NotNull
    private RelationTypeInternal relationType = RelationTypeInternal.RELATED_TO;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private WorkOrder parent;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkOrder child;


}


