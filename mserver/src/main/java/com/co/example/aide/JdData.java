package com.co.example.aide;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.co.example.common.utils.HttpUtils;
import com.co.example.entity.comment.aide.Comment;
import com.google.common.collect.Lists;

public class JdData {
	public static List<Comment> getCommentDetails(String id){
		List<Comment> commentList = Lists.newArrayList();
		Comment comment = null;
		String url="http://club.jd.com/comment/productPageComments.action?callback=&productId="+id+"&score=0&sortType=5&page=0&pageSize=10";
		String data = HttpUtils.getData(url,"gbk");
		JSONObject parseObject = JSON.parseObject(data);
		JSONArray jsonArray = parseObject.getJSONArray("comments");
		JSONObject jsonObject = null;
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			comment = new Comment(); 
			comment.setUserNickName(jsonObject.getString("nickname"));
			comment.setSource("京东");
			comment.setDatetime(jsonObject.getString("creationTime"));
			comment.setDetail(jsonObject.getString("content"));
			commentList.add(comment);
		}
		return commentList;
	}
}
