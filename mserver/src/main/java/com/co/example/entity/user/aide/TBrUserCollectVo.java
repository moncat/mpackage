package com.co.example.entity.user.aide;

import com.co.example.entity.user.TBrUserCollect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrUserCollectVo extends TBrUserCollect {
	/** 用户展示名称 */
    private String userDisplayName;
}