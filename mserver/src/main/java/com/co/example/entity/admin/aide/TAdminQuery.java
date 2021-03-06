package com.co.example.entity.admin.aide;

import java.util.List;

import com.co.example.entity.admin.TAdmin;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class TAdminQuery extends TAdmin {
	
	private List<Long> roleIds;
	
	private Long roleId;
	
	private String nameLike;
	
}