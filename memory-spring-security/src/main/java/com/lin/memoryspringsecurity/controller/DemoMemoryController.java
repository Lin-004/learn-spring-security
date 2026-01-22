package com.lin.memoryspringsecurity.controller;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class DemoMemoryController {
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> index(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String username = authentication.getName();
        List<@Nullable String> list = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(Map.of(
                "principal", Objects.requireNonNull(principal),
                "username", username,
                "authorities", list)

        );
    }

    //测试管理员权限
    @GetMapping("/admin/view")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("管理员ADMIN角色访问ok");
    }
}
