package ru.otus.library.services;

import org.springframework.security.acls.model.Permission;
import ru.otus.library.domain.ApplicationEntity;

public interface PermissionService {
    void addPermissionForAuthority(ApplicationEntity targetObj, Permission permission, String authority);
    void removePermissionForAuthority(Class<?> targetClass, long objectId);
}
