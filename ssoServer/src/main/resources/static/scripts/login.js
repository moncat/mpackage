$(function() {
	$(":text,:password").keyup(function(e){
		var keyCode= e.keyCode;
		if(keyCode!=13){
			return false;
		}
		var thisName=this.name;
		if(thisName=="username"){
			$("[name='password']").focus();
		}
		if(thisName=="pwd"){
			$("[name='checkCode']").focus();
		}
		if(thisName=="checkCode"){
			verSubmit();
		}
	});


    $('.check-box span').click(function () {
        var checkVal = $('#remember-me') , val = checkVal.val() , $this = $(this);
        if (val == 1) {
            if ($this.hasClass('is-check')) {
                $this.removeClass('is-check');
            }
            $this.addClass('no-check');
            checkVal.val(0);
            $('#remember-me').removeAttr("checked");
        } else {
            if ($this.hasClass('no-check')) {
                $this.removeClass('no-check');
            }
            $this.addClass('is-check');
            checkVal.val(1);
            $('#remember-me').attr("checked", "checked");
        }
    });

    $('.close').click(function(){
        $(this).parent('li').find('input').val('').focus();
    });

    if ($("#errorMsg").val() != '') {
        alert($("#errorMsg").val());
    }

    $(".username").focus();

});


function verSubmit()
{
    var uname = $.trim($("#username").val());
    var passwd = $.trim($("#password").val());
    if(uname == "" || uname == null || uname == "您的用户名")
    {

        alert("用户名不能为空,请输入用户名!");
        document.getElementById("username").focus();
        return false;
    }
    if(passwd == "" || passwd == null || passwd == "登录密码")
    {
        alert("密码不能为空,请输入密码!");
        $("#password").focus();
        return false;
    }

   

    checkcode("");

}

function reloadImg(){
    $("#validateImg").attr("src",$("#validateImg").attr("src").split("?")[0]+"?"+new Date().getTime())
}



function checkcode(vilidcode){
	$("#loginForm").submit();
}
