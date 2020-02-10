package com.co.example.entity.manifest.aide;

import java.util.List;

import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.common.KvBean;
import com.co.example.entity.manifest.TBrManifestAuth;
import com.co.example.entity.manifest.TBrManifestResult;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrManifestAuthVo extends TBrManifestAuth {
	
	private String  manifestName;
	//读取主表的状态，授权表的状态暂时废弃
	private Byte  manifestStatus;
	
	private TBrManifestResult  result;
	private List<KvBean>  resultJson;
	
	private String  adminName;
}