package com.co.example.entity.product.aide;

import com.co.example.entity.enterprise.TBrEnterprisePermission;
import com.co.example.entity.product.TBrEnterprise;

import lombok.Data;

@Data
public class TBrEnterprisePo {
    private Long id;

    /** 企业 */
    private TBrEnterprise enterprise;
    
    private TBrEnterprisePermission permission;
    
    private Integer productNum;
    
    private Integer curProductNum;
    
    private Integer catagoryNum;
    
}
