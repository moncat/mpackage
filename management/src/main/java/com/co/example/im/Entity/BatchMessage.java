package com.co.example.im.Entity;

import lombok.Data;

/**
 * @see http://dev.netease.im/docs/product/IM即时通讯/服务端API文档/消息功能?#批量发送点对点普通消息
 * @author zyl
 *
 */
@Data
public class BatchMessage {
	private String	fromAccid	;//是
	private String	toAccids    ;//是
	private int	     type	    ;//是
	private String	body	    ;//是
	private String	option	    ;//否
	private String	pushcontent ;//否
	private String	payload     ;//否
	private String	ext         ;//否
}
