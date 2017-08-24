package com.co.example.entity.article;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TArticle extends BaseEntity {
    /** id */
    private Long id;

    /** 文章标题 */
    private String title;

    /** 内容 */
    private String content;

    /** 作者 */
    private String author;

    /** 关键字 */
    private String keyWord;

    /** 分类id */
    private Integer categoryId;

    /** 浏览次数 */
    private Integer pv;

    /** 是否置顶 1是，0否 */
    private Byte isTop;
}