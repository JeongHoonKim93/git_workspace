<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
 	Cookie[] cookies = request.getCookies(); //쿠키를 가져옴
	String Saveid = "";  				 // 쿠키에서 가져온 id저장
	String script = "";  				 // javascript 저장
	if(cookies != null){ 

		for(int i = 0;i<cookies.length;i++){
			System.out.println("cookies[i].getName():"+cookies[i].getName());
			if(cookies[i].getName().equals("lifeRSNid")){
				Saveid = cookies[i].getValue();				
			}			
		}			
	}else{
		
		System.out.println("not cookies!!");
		
	}
%>

<jsp:include page="../inc/inc_page_top.jsp" flush="false" />

<body>
	<script type="text/javascript" src="../../../js/sha256.js"></script>
	<script type="text/javascript">
		document.title = "Dashboard - Login";
		
		function id_check() {
			if( "" == $("#input_id").val() ){
				alert('ID를  입력하십시오.');
				$("#input_id").focus();
			}else if( "" == $("#input_pw").val() ){
				alert('비밀번호를 입력하십시오.');
				$("#input_pw").focus();
			}else{	
				var security_pw = "";
				if($("#input_pw").val() == "rsn1234!") {
					security_pw = $("#input_pw").val();
				} else {
					security_pw = SHA256($("#input_pw").val());
				}
				//alert(security_pw);
				//alert(SHA256($("#input_pw").val()));
				$('#asLogin').append('<input type="hidden" name="FimUserID" value="'+$("#input_id").val()+'" /> ');
				$('#asLogin').append('<input type="hidden" name="FimUserPasswd" value="'+security_pw+'" /> ');
				if($("#remember_id").is(":checked")){
					$('#asLogin').append('<input type="hidden" name="SaveId" value="'+$("#input_id").val()+'" /> ');
				}
				$('#asLogin').submit();
			}
		}
		
	</script>

	<div id="wrap" class="login">
		<div class="wrap">
            <form id="asLogin" name="asLogin" action="/login.jsp" method="POST">
                <div class="wrap">
                    <h1>Hello :D</h1>
					<div class="row"><input type="text" id="input_id"  placeholder="ID" required><label for="input_id"></label></div>
					<div class="row"><input type="password" id="input_pw" OnKeyDown="Javascript:if (event.keyCode == 13) { id_check();}" placeholder="PASSWORD" required><label for="input_pw"></label></div>
                    <button class="ui_btn ui_wid_100p btn_login" onclick="id_check()"><span class="txt">Log In</span></button>
                    <div class="txt">* 서비스 문의는 <a href="mailto:solution@realsn.com">solution@realsn.com</a>으로 보내주시기 바랍니다.</div>
                </div>
            </form>
		</div>
	</div>
	<script type="text/javascript">
		designScripts();
	</script>
</body>
</html>
