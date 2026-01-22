package com.lin.abacspringsecurity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_policy")
public class SysPolicy {

    @TableId(type = IdType.AUTO)
    private Long policyId;
    /**
     * 策略名称
     */
    private String policyName;
    /**
     * 目标资源标识，例如：admin:menu
     */
    private String targetResource;
    /**
     * #user.attrs['country'] == 'zh' el表达式示例
     */
    private String conditionExpression;
}
