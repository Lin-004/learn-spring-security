package com.lin.abacspringsecurity.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lin.abacspringsecurity.mapper.SysPolicyMapper;
import com.lin.abacspringsecurity.pojo.SysPolicy;
import com.lin.abacspringsecurity.pojo.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("authz")
@RequiredArgsConstructor
public class AuthorizationLogic {

    private final SpelExpressionParser parser = new SpelExpressionParser();
    private final SysPolicyMapper sysPolicyMapper;
    public boolean check(MethodSecurityExpressionOperations operations, String permission) {
        System.out.println("ABAC check invoked, permission = " + permission);
        SysUser userDetails = (SysUser) operations.getAuthentication().getPrincipal();

        // 加载策略集
        LambdaQueryWrapper<SysPolicy> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPolicy::getTargetResource, permission);
        List<SysPolicy> policies = sysPolicyMapper.selectList(queryWrapper);
        if (policies.isEmpty()) {
            return false;
        }

        // 构建评估上下文
        EvaluationContext context = new StandardEvaluationContext();
        // 将用户传入表达式上下文 如：#user.attrs['department'] == 'IT'
        // 其中user前缀就是我们传入的user
        context.setVariable("user", userDetails);

        return policies.stream().allMatch(policy ->
                parser.parseExpression(policy.getConditionExpression()).getValue(context, Boolean.class)
        );
    }
}
