package com.co.example.entity.product.aide;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper=true)
public class ConfirmVo {
		private String confirmDate;
		private Integer num;
}
