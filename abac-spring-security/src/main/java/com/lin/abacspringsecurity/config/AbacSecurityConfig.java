package com.lin.abacspringsecurity.config;

import com.lin.abacspringsecurity.security.AbacDecisionEngine;
import com.lin.abacspringsecurity.security.AbacPermissionEvaluator;
import com.lin.abacspringsecurity.service.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class AbacSecurityConfig {

    private final UserDetailServiceImpl userDetailsService;
//    private final AbacDecisionEngine adacEngine;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/setPassword").permitAll()
                        //配置形式ADMIN角色可以访问/admin/view
                        .requestMatchers("/admin/view").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .userDetailsService(userDetailsService)
                .formLogin(withDefaults())
                .logout(withDefaults())
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置 Method Security Expression Handler，使用自定义的 PermissionEvaluator
     */
    @Bean
    public static MethodSecurityExpressionHandler methodSecurityExpressionHandler(AbacDecisionEngine abacEngine) {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(new AbacPermissionEvaluator(abacEngine));
        return handler;
    }
}
