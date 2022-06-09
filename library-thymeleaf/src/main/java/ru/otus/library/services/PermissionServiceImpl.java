package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.ApplicationEntity;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final MutableAclService aclService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void addPermissionForAuthority(ApplicationEntity targetObj, Permission permission, String authority) {
        val sid = new GrantedAuthoritySid(authority);
        val oi = new ObjectIdentityImpl(targetObj.getClass(), targetObj.getId());

        MutableAcl acl = null;
        try {
            acl = (MutableAcl) aclService.readAclById(oi);
        } catch (final NotFoundException nfe) {
            acl = aclService.createAcl(oi);
        }

        acl.insertAce(acl.getEntries().size(),permission,sid,true);
        aclService.updateAcl(acl);
    }

}
