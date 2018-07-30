var toast = new auiToast({});

$(function(){
	$('#mySkin').on('click',function(){
		$.post('/plan/flag',function(data){
			if(data.code ==200){
				location.href='/plan/init';
			}else{
				location.href='/question/init';				
			}
		});
	});
	
	movePic();
	
	$(window).resize(function(){
		movePic();
		alertStyle();
	});
	//div滑动bug
	$('.mine-camera').bind('touchmove', function(e) {
        e.preventDefault();
    });
	
	/******************************************/
	//替换框架cropper图片裁剪
    $('#tailoringImg').cropper({
        aspectRatio: 1/1,//默认比例
//        preview: '.previewImg',//预览视图
        guides: false,  //裁剪框的虚线(九宫格)
        autoCropArea: 0.5,  //0-1之间的数值，定义自动剪裁区域的大小，默认0.8
        movable: false, //是否允许移动图片
        dragCrop: true,  //是否允许移除当前的剪裁框，并通过拖动来新建一个剪裁框区域
        movable: true,  //是否允许移动剪裁框
        resizable: true,  //是否允许改变裁剪框的大小
        zoomable: false,  //是否允许缩放图片大小
        mouseWheelZoom: false,  //是否允许通过鼠标滚轮来缩放图片
        touchDragZoom: true,  //是否允许通过触摸移动来缩放图片
        rotatable: true,  //是否允许旋转图片
        crop: function(e) {
            // 输出结果数据裁剪图像。
        }
    });
    
    //旋转
    $(".cropper-rotate-btn").on("click",function () {
        $('#tailoringImg').cropper("rotate", 45);
    });
    //复位
    $(".cropper-reset-btn").on("click",function () {
        $('#tailoringImg').cropper("reset");
    });
    //换向
    var flagX = true;
    $(".cropper-scaleX-btn").on("click",function () {
        if(flagX){
            $('#tailoringImg').cropper("scaleX", -1);
            flagX = false;
        }else{
            $('#tailoringImg').cropper("scaleX", 1);
            flagX = true;
        }
        flagX != flagX;
    });

    //裁剪后的处理
    $("#sureCut").on("click",function () {
        if ($("#tailoringImg").attr("src") == null ){
            return false;
        }else{
            var cas = $('#tailoringImg').cropper('getCroppedCanvas');//获取被裁剪后的canvas
            var base64url = cas.toDataURL('image/png'); //转换为base64地址形式
//            $("#headImage").prop("src",base64url);//显示为图片的形式
            //上传编码数据
            uploadBase64url(base64url);
            //关闭裁剪框
            closeTailor();
        }
    });
    
    $('#cancleCut').on('click', function() {
    	closeTailor();
	});
    
    
	/******************************************/
	$('#camera').on('click',function(){
		$(':file').trigger('click');
	});
//	
	$('#headImage').on('click',function(){
		$(':file').trigger('click');
	});
	
	$(':file').on('change',function(){	
		selectImg($(this).get(0));
	});
	
	$('#nickname').on('input',function(){
		$('#nicknameBig').text($('#nickname').val());
	});
	
	
	var currYear = (new Date()).getFullYear();	
	var opt={};
	opt.date = {preset : 'date'};
	opt.datetime = {preset : 'datetime'};
	opt.time = {preset : 'time'};
	opt.default = {
		theme: 'android-ics light', //皮肤样式
        display: 'modal', //显示方式 
        mode: 'scroller', //日期选择模式
		dateFormat: 'yyyy-mm-dd',
		lang: 'zh',
		showNow: true,
		nowText: "今天",
        startYear: currYear - 100, //开始年份
        endYear: currYear  //结束年份
	};

  	$("#birthday").mobiscroll($.extend(opt['date'], opt['default']));
  	
  	
  	$("#birthday").on('change',function(){
  		var age = ages($("#birthday").val());
  		$('#age').val(age);
  	});
  	
	
	var mobile = $('#mobile').val();
    var encrypt_mobile = mobile.substr(0,4)+"****"+mobile.substr(7);
    $('#mobile').val(encrypt_mobile);
    
    
    $('.editMineInfo').on('click', function() {
    	$('#curForm').submit();
	});
    
    //手机号修改，待定
//  	$("#mobile").on('click',function(){
//  		location.href=""
//  	});
	
});

function upload(){	
		var token = $('meta[name="_csrf"]').attr("content");
		var header = $('meta[name="_csrf_header"]').attr("content");
		var data4csrf={
				"_csrf":token,
				"_csrf_header":header
		};
		$.ajaxFileUpload({
			url:'upload',
			type:'post',
			data:data4csrf,
			secureuri:false,
			fileElementId:'file',
			dataType: 'json',//返回值类型 一般设置为json
			success: function (data){ 
				if(data.code==200){
					$('#image').val(data.picUrl);
					$('#headImage').prop('src',data.picUrl);
					$(':file').on('change',function(){
						upload();
					});
				}
			},
			error: function (data, status, e){
			}
		});
	
}


/* 检查是否为图片 */
function image_check(feid) { //自己添加的文件后缀名的验证
    var img = document.getElementById(feid);
    return /.(JPEG|JPG|PNG|GIF|BMP|jpeg|jpg|png|gif|bmp)$/.test(img.value)?true:(function() {
        toast.fail({title:'图片格式仅支持jpg、png、gif、bmp格式。',duration:2000});	
        return false;
    })();
}


/* 检查是否为图片 */
function isImage(filepath) {
    var extStart = filepath.lastIndexOf(".");
    var ext = filepath.substring(extStart, filepath.length).toUpperCase();
    if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
        alert("头像只能是bmp,png,gif,jpeg,jpg格式");
        return false;
    }
    return true;
}


/* 检查图片大小，不能超过500k,支持IE、filefox、chrome */
function checkFileSize(feid) {
	var img = document.getElementById(feid)
	filepath = img.value
    var maxsize =  500 * 1024;//500k
    var errMsg = "上传的头像文件不能超过500k。";
    var tipMsg = "您的浏览器暂不支持上传头像，确保上传文件不要超过2M，建议使用IE、FireFox、Chrome浏览器。";

    try {
        var filesize = 0;
        var ua = window.navigator.userAgent;
        if (ua.indexOf("MSIE") >= 1) {
            //IE
            var img = new Image();
            img.src = filepath;
            filesize = img.fileSize;
        } else {
            filesize = $("#"+feid)[0].files[0].size; //byte
        }

        if (filesize > 0 && filesize > maxsize) {
//            alert(errMsg);
            toast.fail({title:errMsg,duration:2000});	
            return false;
        } else if (filesize == -1) {
//            alert(tipMsg);
            return false;
        }
    } catch (e) {
        alert("头像上传失败，请重试");
        return false;
    }
    return true;
}


function ages(str)   
{   
      var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);     
      if(r==null){
    	  return false; 
      }     
      var d= new Date(r[1],r[3]-1,r[4]);     
      if(d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4])   
      {   
         var Y =new Date().getFullYear();   
         return Y-r[1];   
      }   
      return("输入的日期格式错误！");   
}   
  

function movePic(){
	var X = $('#headImage').offset().top;
	var Y = $('#headImage').offset().left;
	$('#camera').css({'top':X,'left':Y});
};	
	
//弹出框水平垂直居中
function alertStyle() {
    var win_height = $(window).height();
    var win_width = $(window).width();
    if (win_width <= 768){
        $(".tailoring-content").css({
            "top": (win_height - $(".tailoring-content").outerHeight())/2,
            "left": 0
        });
    }else{
        $(".tailoring-content").css({
            "top": (win_height - $(".tailoring-content").outerHeight())/2,
            "left": (win_width - $(".tailoring-content").outerWidth())/2
        });
    }
};
/******************************************/
//图像上传到本地
function selectImg(file) {
    if (!file.files || !file.files[0]){
        return;
    }
    var reader = new FileReader();
    reader.onload = function (evt) {
        var replaceSrc = evt.target.result;
        //更换cropper的图片
        $('#tailoringImg').cropper('replace', replaceSrc,false);//默认false，适应高度，不失真
    }
    reader.readAsDataURL(file.files[0]);
    //显示弹出框
    $(".tailoring-container").toggle();
}
//图片上传到服务器
function uploadBase64url(imgSrc){
	$.post('/center/upload',{'imgSrc':imgSrc},function(data){
		if(data.code==200){
			$('#image').val(data.picUrl);
			$('#headImage').prop('src',data.picUrl);
		}
	});
}


//关闭裁剪框
function closeTailor() {
    $(".tailoring-container").toggle();
}
