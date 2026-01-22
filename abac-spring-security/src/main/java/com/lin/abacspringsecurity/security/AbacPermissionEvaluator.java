package com.lin.abacspringsecurity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@RequiredArgsConstructor
public class AbacPermissionEvaluator implements PermissionEvaluator {

    private final AbacDecisionEngine decisionEngine;

    /**
     * ABAC权限评估
     * @param authentication 当前认证信息 spring security 自动传入
     * @param targetDomainObject 目标对象
     * @param permission 权限标识
     * @return 是否有权限
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        System.out.println("ABAC hasPermission called -> " + permission);
        return decisionEngine.check(authentication,(String)permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
