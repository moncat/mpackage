package com.co.example.entity.log;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrLogUser extends BaseEntity {
    /** id */
    private Long id;

    /** 用户展示名 */
    private String userName;

    /** 登录ip */
    private String ip;

    /** 操作类型 1查询 2新增 3修改 4删除 */
    private String operType;

    /** 模块名称 e.g. 肤质测试模块，用户咨询模块 */
    private String module;

    /** 操作详情 */
    private String detail;

    /** 应用id */
    private Byte appId;
}