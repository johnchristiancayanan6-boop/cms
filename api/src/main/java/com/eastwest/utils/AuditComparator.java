package com.eastwest.utils;

import com.eastwest.model.abstracts.Audit;

import java.util.Comparator;

public class AuditComparator implements Comparator<Audit> {
    @Override
    public int compare(Audit o1, Audit o2) {
        return o1.getCreatedAt().compareTo(o2.getCreatedAt());
    }
}
