package com.co.example.entity.user;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class TOrg {
    /** id */
    private Integer id;

    /** 组织机构名称 */
    private String name;

    /** 层级 */
    private Integer level;

    /** 上级id */
    private Integer parentId;

    /** 是否根节点 1是 0不是 */
    private Byte isRoot;

    /** id层级串 */
    private String idBunch;

    /**  */
    @DateTimeFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private Date createTime;

    /**  */
    private Integer createBy;

    /**  */
    @DateTimeFormat(pattern="yyyy-MM-dd HH-mm-ss")
    private Date updateTime;

    /**  */
    private Integer updateBy;

    /** 是否删除  1已删除  0未删除 */
    private Byte delFlg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Byte getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Byte isRoot) {
        this.isRoot = isRoot;
    }

    public String getIdBunch() {
        return idBunch;
    }

    public void setIdBunch(String idBunch) {
        this.idBunch = idBunch == null ? null : idBunch.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Byte getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Byte delFlg) {
        this.delFlg = delFlg;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TOrg other = (TOrg) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getIsRoot() == null ? other.getIsRoot() == null : this.getIsRoot().equals(other.getIsRoot()))
            && (this.getIdBunch() == null ? other.getIdBunch() == null : this.getIdBunch().equals(other.getIdBunch()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getDelFlg() == null ? other.getDelFlg() == null : this.getDelFlg().equals(other.getDelFlg()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getIsRoot() == null) ? 0 : getIsRoot().hashCode());
        result = prime * result + ((getIdBunch() == null) ? 0 : getIdBunch().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getDelFlg() == null) ? 0 : getDelFlg().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", level=").append(level);
        sb.append(", parentId=").append(parentId);
        sb.append(", isRoot=").append(isRoot);
        sb.append(", idBunch=").append(idBunch);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", delFlg=").append(delFlg);
        sb.append("]");
        return sb.toString();
    }
}