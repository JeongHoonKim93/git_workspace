<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<title>Day</title>
<link rel="stylesheet" type="text/css" href="css/mobile/basic.css">
<style type="text/css">
<!--
.t_gray {
	font-family: Tahoma, "돋움", "돋움체";
	font-size: 11px;
	color: #9F9F9F;
}
.input {
	BORDER-RIGHT: #E1E2E1 1px solid; BORDER-TOP: #E1E2E1 1px solid; BORDER-LEFT: #E1E2E1 1px solid; BORDER-BOTTOM: #E1E2E1 1px solid; COLOR: #767676;  HEIGHT: 20px; FONT-SIZE: 12px; PADDING-TOP: 3px; BACKGROUND-COLOR: #ffffff
}
body {
	background-image: url(images/mobile/login_bg02.gif);
	background-color: #384b5a;
}
-->
</style>
<script language="JavaScript" type="text/JavaScript">
<!--
	
function setCookie (name, value, expires) {
	  document.cookie = name + "=" + escape (value) +
	    "; path=/; expires=" + expires.toGMTString();
	}

	function isValid(){
		//var f = document.LoginForm;
		//	if( ! checkFormValidate(f)  ) {
		//		return false ;
		//	}
		return true; 
	}

	function saveid() {
	  	var expdate = new Date();
	 // 기본적으로 30일동안 기억하게 함. 일수를 조절하려면 * 30에서 숫자를 조절하면 됨
	  	var f = document.LoginForm;
	  	if (f.idsaveid.checked)
	    	expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30);  // 30일
	  	else
	    	expdate.setTime(expdate.getTime() - 1); // 쿠키 삭제조건
		setCookie("saveid", f.m_id.value, expdate);
	}

	function saveid2() {
	  	var expdate = new Date();
	 // 기본적으로 30일동안 기억하게 함. 일수를 조절하려면 * 30에서 숫자를 조절하면 됨
	  	var f = document.LoginForm;
	  	if (f.idsaveid.checked)
	    	expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30);  // 30일
	  	else
	    	expdate.setTime(expdate.getTime() - 1); // 쿠키 삭제조건
		setCookie("password", f.m_pass.value, expdate);
	}

	function getCookie(Name) {
		  var search = Name + "="
		  if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면�
		    offset = document.cookie.indexOf(search)
		    if (offset != -1) { // 쿠키가 존재하면
		      offset += search.length
		      // set index of beginning of value
		      end = document.cookie.indexOf(";", offset)
		      // 쿠키 값의 마지막 위치 인덱스 번호 설정
		      if (end == -1)
		        end = document.cookie.length
		      return unescape(document.cookie.substring(offset, end))
		    }
		  }
		  return "";
		}

	function getid() {
		var f = document.LoginForm;
	  	f.idsaveid.checked = ((f.m_id.value = getCookie("saveid")) != "");  
	  	f.passsaveid.checked = ((f.m_pass.value = getCookie("password")) != "");  
	  	if(f.idsaveid.checked==true){
	  		f.m_pass.focus(); 
	  	}else{
	  		f.m_id.focus(); 
	  	}
	}

	function doSubmit(){
		var f = document.LoginForm;
		if(isValid()){
			if(f.idsaveid.checked==true){
				saveid();
		  	}
			if(f.passsaveid.checked==true){
				saveid2();
		  	}
			f.submit();
		}
	}
	window.onload = getid;
//-->
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="LoginForm" action="m_login.jsp" method="post">
<input type="hidden" name="cmd" value="mDoLogin">
<center>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align=center>
  <tr>
    <td><img src="images/mobile/login_top_img.gif"></td>
  </tr>
  <tr>
    <td style="padding:0px 15px 0px 15px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="2" background="images/mobile/login_line01.gif"></td>
      </tr>
    </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="22"></td>
        </tr>
        <tr>
          <td style="padding:0px 25px 0px 25px"><table width="250" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="63" height="30"><img src="images/mobile/login_id.gif" width="63" height="8"></td>
              <td width="187"><input name="m_id" type="text" class="input01" style="width: 150px"></td>
            </tr>
            <tr>
              <td height="30"><img src="images/mobile/login_pass.gif" width="63" height="8"></td>
              <td height="30"><input name="m_pass" type="password" class="input01" style="width: 150px"></td>
            </tr>
            <tr>
              <td height="10" colspan="2"></td>
            </tr>
            <tr>
              <td colspan="2" style="padding:0px 0px 0px 60px"><img src="images/mobile/login_btn.gif" width="159" height="53" onclick="doSubmit();" style="cursor:pointer;"></td>
            </tr>
            <tr>
              <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
              <td colspan="2"><table width="250" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td  class="t_gray">
                    <input type="checkbox" name="idsaveid" value="checkbox"></td>
                  <td class="t" style="padding:3px 0px 0px 0px">아이디 저장하기</td>
                  <td  class="t_gray">
                    <input type="checkbox" name="passsaveid" value="checkbox">
                  </td>
                  <td class="t" style="padding:3px 0px 0px 0px">패스워드 저장하기</td>
                </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="22"></td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="2" background="images/mobile/login_line02.gif"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</center>






















<!--  
<div id="login_visual"><p></p></div>
<div id="login_box">
	<div class="line">
	<fieldset>
		<dl class="login_input" style="text-align:left;">
			<dd><input type="text" title="아이디" name="m_id" class="login_inputbox" tabindex="1"></dd>
			<dd><input type="password" name="m_pass" title="비밀번호" class="login_inputbox" tabindex="2"></dd>
			<dd class="idsave"><input name="idsaveid" type="checkbox" tabindex="4"> <label for="idsave">아이디를 저장 합니다.</label></dd>
			<dd class="idsave"><input name="passsaveid" type="checkbox" tabindex="4"> <label for="idsave">패스워드를 저장 합니다.</label></dd>
		</dl>
		<dl class="btn_login">
			<dd><img src="images/mobile/btn_login.gif" tabindex="3" onclick="doSubmit();" style="cursor:pointer;"></dd>
		</dl>
	</fieldset>
	</div>
</div>
-->
</form>
</body>
</html>

