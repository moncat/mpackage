//校验非零正整数
function isPlusInteger(s) {
	var pattern = /^[1-9][0-9]*$/;
	return pattern.test(s);
}

//校验数字
function isNumber(s) {
	var pattern = /^[0-9]*$/; 
	return pattern.test(s);
}

//校验浮点数
function isFloat(s) {
	var pattern = /^\d+(\.\d+)?$/; 
	return pattern.test(s);
}

//校验email
function isEmail(s) {
	var pattern =  /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
	return pattern.test(s);
}

//校验手机号
function isMobile(s) {
	var pattern = /^1\d{10}$/;
	return pattern.test(s);
}

//校验区号
function isTelAreaCode(s) {
	var pattern = /^0[1-9]\d{1,2}$/;
	return pattern.test(s);
}

//校验座机号
function isTel(s) {
	var pattern = /^\d{7,8}$/;
	return pattern.test(s);
}

//限制文本框文本长度
function checkTextAreaLength(ele, strLength){
	var intro = ele.value.replace(/^\s+|\s+$/g,"");
	if(intro.length>parseInt(strLength)){
		ele.value=intro.substr(0,strLength);
	}
}

//校验电话号
function isPhone(s) {
	var pattern = /^1\d{10}$|^\d{7,8}$|^0[1-9]\d{1,2}-\d{7,8}$|^0[1-9]\d{8,10}$/;
	return pattern.test(s);
}

//校验用户名
function isUserName(s) {
	var pattern = /^[a-zA-Z0-9_\-\u4e00-\u9fa5]+$/;
	return pattern.test(s)&&s.length>=2&&s.length<=20;
}

//获取url参数
function getUrlParam(name)
{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r!=null) return unescape(r[2]); return null; //返回参数值
} 

//检查上传文件类型，是否为图片
function checkPicType(picPath) {
    var type = picPath.substring(picPath.lastIndexOf(".") + 1, picPath.length).toLowerCase();
    if (type != "jpg" && type != "bmp" && type != "gif" && type != "png") {
        alert("请上传正确的图片格式");
        return false;
    }
    return true;
}

//校验url
function isURL(s){
  var strRegex = "^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+";
    var re=new RegExp(strRegex); 
    if (re.test(s)){
        return true; 
    }else{ 
        return false; 
    }
}

