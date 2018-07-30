package com.co.example.service.system;

import com.co.example.entity.system.TSensitiveWord;
import com.github.moncat.common.service.BaseService;

public interface TSensitiveWordService extends BaseService<TSensitiveWord, Long> {
	
	/**
	 * 是否通过敏感词审查
	 * @param info
	 * @return
	 */
	Boolean passByWord(String info);
}