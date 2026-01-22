package com.lin.abacspringsecurity.controller;

import com.lin.abacspringsecurity.pojo.SysUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdminController {

    @GetMapping("/")
    public ResponseEntity<SysUser> index(Authentication authentication) {
        System.out.println(authentication);
        SysUser principal = (SysUser)authentication.getPrincipal();
        return ResponseEntity.ok(principal);
    }

    /**
     * MethodSecurityExpressionHandler方式
     *
     * @return
     */
    @PreAuthorize("hasPermission(null, 'admin:menu')")
    @GetMapping("/admin")
    public ResponseEntity<?> getAdminData() {
        return ResponseEntity.ok("MethodSecurityExpressionHandler方式");
    }


    /**
     * 自定义注解的方式
     * @return
     */
    @PreAuthorize("@authz.check(#root, 'admin:menu')")
    @GetMapping("/authz")
    public ResponseEntity<?> authz() {
        return ResponseEntity.ok("自定义注解的方式");
    }

    /**
     * 以下RBAC角色 + ABAC属性的混合校验 可以复制测试
     * @PreAuthorize("hasAuthority('admin:menu') and @abacDecisionEngine.check(authentication, 'admin:menu')")
     * 
     * @PreAuthorize("hasRole('ADMIN') and @authz.check(#root, 'admin:menu')")
     *
     * @return
     */
    @PreAuthorize("hasRole('ADMIN') and @abacDecisionEngine.check(authentication, 'admin:menu')")
    @GetMapping("/admin/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("RBAC角色 + ABAC属性的混合校验");
    }
}
