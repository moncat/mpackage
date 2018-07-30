package com.co.example.entity.user.aide;

import com.co.example.entity.user.TUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TUserQuery extends TUser {
	

    /** 展示名称 */
    private String displayNameLike;
    
    /** 真实姓名 */
    private String personNameLike;

    /** 手机号 */
    private String mobilePhoneLike;
	
}