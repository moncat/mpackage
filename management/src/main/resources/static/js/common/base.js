

// common header
$(function(){
	
    var $backToTopEle=$('<a href="javascript:void(0)" class="Hui-iconfont toTop" title="返回顶部" alt="返回顶部" style="display:none">&#xe684;</a>').appendTo($("body")).click(function(){
        $("html, body").animate({ scrollTop: 0 }, 120);
    });
    var $backToTopFun = function() {
        var st = $(document).scrollTop(); 
        var winh = $(window).height();
        (st > 0)? $backToTopEle.show(): $backToTopEle.hide();
        /*IE6下的定位*/
        if(!window.XMLHttpRequest){
            $backToTopEle.css("top", st + winh - 166);
        }
    };
    $(window).on("scroll",$backToTopFun);
    
    jQuery.cookie = function(name, value, options) {   
        if (typeof value != 'undefined') { // name and value given, set cookie  
            options = options || {};  
            if (value === null) {  
                value = '';  
                options.expires = -1;  
            }  
            var expires = '';  
            if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {  
                var date;  
                if (typeof options.expires == 'number') {  
                    date = new Date();  
                    date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));  
                } else {  
                    date = options.expires;  
                }  
                expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE  
            }  
            // CAUTION: Needed to parenthesize options.path and options.domain  
            // in the following expressions, otherwise they evaluate to undefined  
            // in the packed version for some reason...  
            var path = options.path ? '; path=' + (options.path) : '';  
            var domain = options.domain ? '; domain=' + (options.domain) : '';  
            var secure = options.secure ? '; secure' : '';    
            document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');  
        } else { // only name given, get cookie  
            var cookieValue = null;  
            if (document.cookie && document.cookie != '') {  
                var cookies = document.cookie.split(';');  
                for (var i = 0; i < cookies.length; i++) {  
                    var cookie = jQuery.trim(cookies[i]);  
                    // Does this cookie string begin with the name we want?  
                    if (cookie.substring(0, name.length + 1) == (name + '=')) {  
                        cookieValue = decodeURIComponent(cookie.substring(name.length + 1));  
                        break;  
                    }  
                }  
            }  
            return cookieValue;  
        }  
    }; 
    
    $('#showErr').on('click',function(){
    	$('.error-info').show();
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
    	 return  unescape(r[2]);
     return null;
}


function ecUrl(url){
	url=encodeURI(url);
	url=url.replace('#','%23');
	url=url.replace('+','%2B');
	return url;
}


/*个人信息*/
function myselfinfo(){
	layer.open({
		type: 1,
		area: ['300px','200px'],
		fix: false, //不固定
		maxmin: true,
		shade:0.4,
		title: '查看信息',
		content: '<div>管理员信息</div>'
	});
}

function l0(info){
	layer.alert(info);
}

function l2(url,width,height){
	var title = '信息';
	if(url.indexOf('add')>0){
		title='新增';
	}
	if(url.indexOf('edit')>0){
		title='编辑';
	}
	if(url.indexOf('show')>0){
		title='查看';
	}
	if(width == null || height == null){
		width = '800px';
		height = '495px';
	}
	
	var index = layer.open({
		type: 2,
		offset: 't',
		area: [width,height],
		fix: false, //不固定
		maxmin: true,
		shade:0.4,
		title: title,
		content: url
	});
	return index;
	//layer.full(index);
}
function lf2(url,width,height){
	var index = l2(url,width,height);
	layer.full(index);
}


function lc(){
	parent.location.reload();
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
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
/*getJSONP('http://localhost:8888/',function(response){
　　alert(response.name);
});*/