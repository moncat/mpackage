package com.co.example.entity.user.aide;

import com.co.example.entity.user.TBrUserCollect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrUserCollectQuery extends TBrUserCollect {
	
	private Boolean joinFlg = false;
	
	
	
	private String userDisplayNameLike;

    /** 商品名称 或 资讯名称 （冗余字段，方便查询） */
    private String nameLike;
	
}