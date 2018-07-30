package com.co.example.entity.message.aide;

import com.co.example.entity.message.TBrMessage;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class TBrMessageQuery extends TBrMessage {
	
	private Boolean joinUserFlg = false;
	
	private String titleLike;
	
	private String detailLike;
	
	private String userDisplayNameLike;
	
}