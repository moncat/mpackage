package com.co.example.entity.information.aide;

import com.co.example.entity.information.TBrInformationComment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrInformationCommentVo extends TBrInformationComment {
	
	  /** 用户展示名称 */
    private String userDisplayName;
}