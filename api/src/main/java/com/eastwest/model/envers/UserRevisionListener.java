package com.eastwest.model.envers;

import com.eastwest.model.OwnUser;
import com.eastwest.model.envers.RevInfo;
import com.eastwest.security.CustomUserDetail;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

public class UserRevisionListener implements RevisionListener
{
    public void newRevision(Object revisionEntity)
    {
        RevInfo revision = (RevInfo) revisionEntity;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() instanceof String) return;
        OwnUser user = ((CustomUserDetail) authentication.getPrincipal()).getUser();

        revision.setUser(user);
    }
}