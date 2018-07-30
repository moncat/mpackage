package com.co.example.entity.user.aide;

import com.co.example.entity.user.TBrUserAddress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrUserAddressQuery extends TBrUserAddress {
	
	   /** 姓名 */
    private String nameLike;
    
    /** 联系方式 */
    private String contactLike;
    
    /** 详细地址 */
    private String detailLike;
}