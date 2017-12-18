package com.co.example.entity.message.aide;

import com.co.example.entity.message.TBrMessage;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class TBrMessageVo extends TBrMessage {
	/** 用户展示名称 */
    private String userDisplayName;
    
}