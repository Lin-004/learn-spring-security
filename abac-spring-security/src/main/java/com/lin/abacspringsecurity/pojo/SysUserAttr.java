package com.lin.abacspringsecurity.pojo;

import lombok.Data;

@Data
public class SysUserAttr {

    private Long userId;
    /**
     * 属性键 如 department
     */
    private String attrKey;
    /**
     * 属性值 如 HR
     */
    private String attrValue;
}
