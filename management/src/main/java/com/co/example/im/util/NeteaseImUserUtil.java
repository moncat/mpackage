package com.co.example.im.util;

import com.co.example.im.Entity.BatchMessage;
import com.co.example.im.Entity.Friend;
import com.co.example.im.Entity.FriendSetting;
import com.co.example.im.Entity.ImUser;
import com.co.example.im.Entity.ImUserInfo;
import com.co.example.im.Entity.ImUserSetting;
import com.co.example.im.Entity.Message;

@SuppressWarnings("deprecation")
public class NeteaseImUserUtil {
	
    public static void main(String[] args) throws Exception{
    	ImUser imUser = new ImUser();
    	imUser.setAccid("helloworld2");
    	imUser.setName("zzz333");
//    	String info = add(imUser);
//    	String info = updateByAccId(imUser);
//    	String info = refreshTokenByAccId(imUser);
    	String info = blockByAccId(imUser);
    	System.out.println(info);
    	
    }
    
    public static final String BASE_URL="https://api.netease.im/nimserver/";
    

    //***************用户基本状态******************
    //{"code":200,"info":{"token":"db915242217e9ea0c8c8412da949dc26","accid":"helloworld2","name":""}}
    public static String add(ImUser imUser) throws Exception{
    	String url = BASE_URL+"user/create.action";
        return ImHttpUtil.post(url,imUser);
    }
    
    public static  String updateByAccId(ImUser imUser) throws Exception{
    	String url = BASE_URL+"user/update.action";
    	return ImHttpUtil.post(url,imUser);
    }
    
    public static  String refreshTokenByAccId(ImUser imUser) throws Exception{
    	String url = BASE_URL+"user/refreshToken.action";
    	return ImHttpUtil.post(url,imUser);
    }
    
    public static  String blockByAccId(ImUser imUser) throws Exception{
    	String url = BASE_URL+"user/block.action";
    	return ImHttpUtil.post(url,imUser);
    }
    
    public static  String unblockByAccId(ImUser imUser) throws Exception{
    	String url = BASE_URL+"user/unblock.action";
    	return ImHttpUtil.post(url,imUser);
    }
    
    //***************用户信息******************
    public static  String addInfo(ImUserInfo imUserInfo) throws Exception{
    	String url = BASE_URL+"user/updateUinfo.action";
    	return ImHttpUtil.post(url,imUserInfo);
    }
    public static  String getInfo(ImUserInfo imUserInfo) throws Exception{
    	String url = BASE_URL+"user/getUinfos.action";
    	return ImHttpUtil.post(url,imUserInfo);
    }
    
    //***************用户设置******************
    public static  String settingUser(ImUserSetting imUserSetting) throws Exception{
    	String url = BASE_URL+"user/setDonnop.action";
    	return ImHttpUtil.post(url,imUserSetting);
    }
    
    //***************用户好友操作******************
    public static  String addFriend(Friend friend) throws Exception{
    	String url = BASE_URL+"friend/add.action";
    	return ImHttpUtil.post(url,friend);
    }
    
    public static  String updateFriendInfo(Friend friend) throws Exception{
    	String url = BASE_URL+"friend/update.action";
    	return ImHttpUtil.post(url,friend);
    }
    
    public static  String deleteFriendInfo(Friend friend) throws Exception{
    	String url = BASE_URL+"friend/delete.action";
    	return ImHttpUtil.post(url,friend);
    }
    
    public static  String getFriends(Friend friend) throws Exception{
    	String url = BASE_URL+"friend/get.action";
    	return ImHttpUtil.post(url,friend);
    }
    
    public static  String addFriendSetting(FriendSetting friendSetting) throws Exception{
    	String url = BASE_URL+"user/setSpecialRelation.action";
    	return ImHttpUtil.post(url,friendSetting);
    }
    
    public static  String getFriendSetting(FriendSetting friendSetting) throws Exception{
    	String url = BASE_URL+"user/listBlackAndMuteList.action";
    	return ImHttpUtil.post(url,friendSetting);
    }
    
    //***************消息功能******************
    public static  String sendMsg(Message message) throws Exception{
    	String url = BASE_URL+"msg/sendMsg.action";
    	return ImHttpUtil.post(url,message);
    }
    
    public static  String sendBatchMsg(BatchMessage batchmessage) throws Exception{
    	String url = BASE_URL+"msg/sendMsg.action";
    	return ImHttpUtil.post(url,batchmessage);
    }
	
	
}