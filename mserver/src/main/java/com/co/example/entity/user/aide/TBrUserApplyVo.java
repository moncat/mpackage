package com.co.example.entity.user.aide;

import com.co.example.entity.user.TBrUserApply;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrUserApplyVo extends TBrUserApply {
	
	/** 活动标题 */
    private String title;
    
    /** 管理员展示名称 */
    private String adminDisplayName;
    
    /** 用户展示名称 */
    private String userDisplayName;
    
    /** 商品图片链接 */
    private String productImage;
    
}