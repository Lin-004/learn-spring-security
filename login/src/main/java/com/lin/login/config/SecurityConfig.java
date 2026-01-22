package com.lin.login.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/ajaxLogin").permitAll()
                    .anyRequest().authenticated()
            )
                .logout(withDefaults())
                .formLogin(form -> form
                    .loginPage("/login")
                    .successHandler(loginSuccessHandler())
                    .failureHandler(loginFailureHandler())
                    .permitAll()
        )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();

    }
    // 自定义登录成功处理器
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
            if (isAjaxRequest(request)) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"code\":200, \"message\":\"/认证成功\"}");
            } else {
                response.sendRedirect("/");
            }
        };
    }

    // 自定义登录失败处理器
    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
            if (isAjaxRequest(request)) {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"code\":401, \"message\":\"认证失败\"}");
            } else {
                response.sendRedirect("/login?error=true");
            }
        };
    }

    //判断是否ajax请求
    public boolean isAjaxRequest(HttpServletRequest request) {
        String xRequestedWith = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(xRequestedWith);
    }

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        UserDetails user = User.withUsername("lin")
//                .password(bCryptPasswordEncoder().encode("123456"))
//                .roles("admin")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
}
