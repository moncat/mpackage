package com.co.example.entity.product.aide;

import com.co.example.entity.product.TBrProductImage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrProductImageVo extends TBrProductImage {
	
	/**
	 * 下载链接
	 */
	private String downloadUrl;
	/**
	 * 图片链接
	 */
	private String imageUrl;
}