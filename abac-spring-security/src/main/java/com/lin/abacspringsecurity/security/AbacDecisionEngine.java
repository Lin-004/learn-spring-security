package com.lin.abacspringsecurity.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lin.abacspringsecurity.mapper.SysPolicyMapper;
import com.lin.abacspringsecurity.pojo.SysPolicy;
import com.lin.abacspringsecurity.pojo.SysUser;
import jakarta.annotation.Resource;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AbacDecisionEngine {
    private final SpelExpressionParser expressionParser = new SpelExpressionParser();

    @Resource
    private SysPolicyMapper sysPolicyMapper;

    /**
     * 请求某个方法除了要验证用户角色或菜单资源，我还要判断用户属性部门=IT，国家是ZH
     */
    public boolean check(Authentication authentication,String resource) {
        SysUser userDetails = (SysUser) authentication.getPrincipal();

        //加载策略集
        LambdaQueryWrapper<SysPolicy> queryWrapper = new LambdaQueryWrapper<SysPolicy>();
        queryWrapper.eq(SysPolicy::getTargetResource, resource);
        List<SysPolicy> policies = sysPolicyMapper.selectList(queryWrapper);
        if (policies.isEmpty()) return false;

        //构建评估上下文
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("user", userDetails);
        System.out.println("attrs = " + userDetails.getAttrs());
        boolean result = policies.stream().allMatch(sysPolicy ->
                expressionParser.parseExpression(sysPolicy.getConditionExpression()).getValue(context, Boolean.class));
        System.out.println("ABAC check for resource=" + resource + " user=" +
                authentication.getName() + " -> " + result);
        return result;

    }
}