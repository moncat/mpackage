package com.co.example.entity.system;

import com.co.example.common.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@ApiModel(value = "菜单对象",description="包含菜单的全部信息") 
public class TMenu extends BaseEntity {
    /** id */
	@ApiModelProperty(value = "菜单主键" ,required=false) 
    private Long id;

    /** 菜单名称 */
	@ApiModelProperty(value = "菜单名称" ,required=true) 
    private String name;

    /** 链接地址 */
    private String url;

    /** 字体图标 */
    private String icon;

    /** 层级 */
    private Integer level;

    /** 上级id */
    private Long parentId;

    /** 是否根节点 1是 0不是 */
    private Byte isRoot;




}