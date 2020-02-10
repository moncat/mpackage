package com.co.example.entity.product.aide;

import lombok.Data;
import lombok.ToString;

/**
 * 该vo统计未匹配的企业数据
 * @author zyl
 *
 */
@Data
@ToString(callSuper=true)
public class BeVo {
		private String eName;
		private String eArea;
		private Integer num;
}
