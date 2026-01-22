package com.lin.dbspringsecurity.contorller;

import com.lin.dbspringsecurity.mapper.SysUserMapper;
import com.lin.dbspringsecurity.pojo.SysUser;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
//    @Resource
//    private SysUserMapper userMapper;
//    @Resource
//    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<SysUser> index(Authentication authentication) {
        System.out.println(authentication);
        SysUser principal = (SysUser)authentication.getPrincipal();
        return ResponseEntity.ok(principal);
    }
    /**
     * 基于SecurityConfig配置角色访问
     * @return
     */
    @GetMapping("/admin/view")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("基于SecurityConfig配置ROLE_ADMIN角色访问ok");
    }

    /**
     * 根据角色权限访问
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/system/view")
    public ResponseEntity<String> system() {
        return ResponseEntity.ok("方法的授权hasRole ADMIN 角色访问ok");
    }

    /**
     * 根据菜单权限访问
     * @return
     */
    @PreAuthorize("hasAuthority('admin:menu:add')")
    @GetMapping("/system/add")
    public ResponseEntity<String> add() {
        return ResponseEntity.ok("方法的授权admin:menu:add,访问ok");
    }

    /**
     * 管理员角色且用户名是admim 方可访问
     * @return
     */
    @PreAuthorize("hasRole('ADMIN') and authentication.name == 'admim'")
    @GetMapping("/system/del")
    public ResponseEntity<String> del() {
        return ResponseEntity.ok("方法的授权admin:menu:add,访问ok");
    }
}
