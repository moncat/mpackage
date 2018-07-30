

// common header
$(function(){

   
    //处理 spring security post请求不了的问题
	var token = $('meta[name="_csrf"]').attr("content");
    var header = $('meta[name="_csrf_header"]').attr("content");
    $(document).ajaxSend(function(e,xhr,opt){
        xhr.setRequestHeader(header,token);
    });
    
    
}); 

//js获取项目根路径，如： http://localhost:8080/ems
function getRootPath() {
    //获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPath = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/ems
   // var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return(localhostPath);
}


function getUrlParam(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)
    	 return  unescape(decodeURI(r[2]));
     return null;
}


function ecUrl(url){
	url=encodeURI(url);
	url=url.replace('#','%23');
	url=url.replace('+','%2B');
	return url;
}

//检测手机号
function checkPhone(phone) {
	var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
	return reg.test(phone);
}

// 检测密码是否为6-20位密码(支持数字，字母)
function checkPassword(pwd) {
	var reg = /^[0-9A-Za-z]{6,20}$/;
	return reg.test(pwd);
}


function getJSONP(url, callback) {
    if (!url) {
        return;
    }
    var a = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j']; //定义一个数组以便产生随机函数名
    var r1 = Math.floor(Math.random() * 10);
    var r2 = Math.floor(Math.random() * 10);
    var r3 = Math.floor(Math.random() * 10);
    var name = 'getJSONP' + a[r1] + a[r2] + a[r3];
    var cbname = 'getJSONP.' + name; //作为jsonp函数的属性
    if (url.indexOf('?') === -1) {
        url += '?jsonp=' + cbname;
    } else {
        url += '&jsonp=' + cbname;
    }
    var script = document.createElement('script');
    //定义被脚本执行的回调函数
    getJSONP[name] = function (e) {
        try {
            //alert(e.name);
　　　　　　　　callback && callback(e);
        } catch (e) {
            //
        }
        finally {
            //最后删除该函数与script元素
            delete getJSOP[name];
            script.parentNode.removeChild(script);
        }

    }
    script.src = url;
    document.getElementsByTagName('head')[0].appendChild(script);
}

function ms2time(data){
	var date = new Date(data);
	return date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();;
}

/*getJSONP('http://localhost:8888/',function(response){
　　alert(response.name);
});*/