package com.co.example.entity.comment.aide;

import lombok.Data;

@Data
public class Comment  {

    /** 评论来源 */
    private String source;

    /** 用户昵称 */
    private String userNickName;
    
    /** 评论时间 */
    private String datetime;

    /** 评论详情 */
    private String detail;
}