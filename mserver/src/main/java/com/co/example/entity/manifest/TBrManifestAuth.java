package com.co.example.entity.manifest;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrManifestAuth extends BaseEntity {
    /** id */
    private Long id;

    /** manifest表的Id */
    private Long manifestId;

    /** 类型 */
    private Byte type;

    /** 状态：1待加载 2加载中 3已完成 */
    private Byte status;

    /** 是否放到工作台 */
    private Byte isTop;

    /** 当前拥有使用权限的用户 */
    private Long usingBy;

    /** 应用id   */
    private Byte appId;
}