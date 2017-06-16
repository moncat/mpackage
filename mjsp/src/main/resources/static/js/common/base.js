

// common header
$(function(){
	
	//小屏导航
	 responsiveNav("Hui-navbar", {customToggle: ".nav-toggle"});
	
    var $backToTopEle=$('<a href="javascript:void(0)" class="Hui-iconfont toTop" title="返回顶部" alt="返回顶部" style="display:none">&#xe684;</a>').appendTo($("body")).click(function(){
        $("html, body").animate({ scrollTop: 0 }, 120);
    });
    var $backToTopFun = function() {
        var st = $(document).scrollTop(), winh = $(window).height();
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
