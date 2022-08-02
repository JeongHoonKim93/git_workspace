<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@ page contentType = "text/html; charset=utf-8" %>
<%@ page import = "risk.admin.member.MemberDao" %>
<%@ page import = "risk.admin.member.MemberBean" %>
<%@ page import = "risk.admin.member.MemberGroupBean" %>
<%@ page import = "risk.util.ParseRequest" %>
<%@ page import = "risk.util.ConfigUtil" %>
<%@ page import = "java.sql.Timestamp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	String id = null;
	if( request.getParameter("id") != null ) id = request.getParameter("id");
	List MGlist = null;

	ConfigUtil cu = new ConfigUtil();
	MemberDao dao = new MemberDao();
	MemberGroupBean MGinfo = new MemberGroupBean();
	MemberBean member = (MemberBean)dao.schID(id);

	
	MGlist = dao.getMGList();
	
	//이슈 1depth - 부서
	String[] tmp_info = null;	
	
	ArrayList<String[]> check_list =  dao.checkDataList(id);
	String[] checklist = null;
	String[] check_data = null;
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../../../css/base.css" type="text/css">
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script  type="text/JavaScript">

	var issueList="";
	function check(index) {
		
		var m_issuechk = document.getElementsByName("m_issuechk");
		var DATA="";
		$("input[name=m_issuechk]:checked").each(function(){
				DATA += ","+($(this).val());
	     });
		issueList = DATA.substr(1);

		}
	

	function del_user()
	{
		if( confirm("사용자를 삭제하겠습니까?") )
		{
			document.location.href = 'admin_user_prc.jsp?mode=del&m_seq='+user_add.m_seq.value;
		}
	}

	function edit_user()
	{
		if(!user_add.m_id.value)
		{
			alert('아이디를 입력하십시요.');
			user_add.m_id.focus();
		} else if(!user_add.m_name.value)
		{
			alert('이름를 입력하십시요.');
			user_add.m_name.focus();
		} else if(!user_add.m_dept.value)
		{
			alert('부서를 입력하십시요.');
			user_add.m_dept.focus();
		} else if(!user_add.m_email.value)
		{
			alert('E-mail를 입력하십시요.');
			user_add.m_email.focus();
		} else if(!user_add.m_tel.value)
		{
			alert('전화번호를 입력하십시요.');
			user_add.m_tel.focus();
		} else {

			
			//배정권한 정제
			//if(user_add.rd_ass_grade[0].checked){
			//	user_add.m_ass_grade.value = '1';
			//}else{
			//	user_add.m_ass_grade.value = '0';
			//}
			//처리권한 정제
			//if(user_add.rd_pro_grade[0].checked){
			//	user_add.m_pro_grade.value = '1';
			//}else{
			//	user_add.m_pro_grade.value = '0';
			//}
			//승인권한 정제
			//if(user_add.rd_app_grade[0].checked){
			//	user_add.m_app_grade.value = '1';
			//}else{
			//	user_add.m_app_grade.value = '0';
			//}
			
			user_add.m_ass_grade.value = '0';
			user_add.m_pro_grade.value = '0';
			user_add.m_app_grade.value = '0';
			$('#ic_code').val(issueList);		
			user_add.submit();
		}
	}

	function ins_ip()
	{
		if(!ipmng.ip.value) {
			alert('ip를 입력하십시요.');
		} else {
			ipmng.submit();
		}
	}
	
	var str = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'; // 문자열만을 포함하는 변수
	var chr = '1234567890!@#$%^&*()-_=+'; // 숫자와 특수문자열을 포함하는 변수

	function passCheck() {
	// 비밀번호 최소 길이를 설정합니다. (입력확인에 부가적으로 필요한 부분)
		var id = document.all.m_id.value;
		var pw1 = document.all.m_pass.value;
		var pw2 = document.all.m_pass2.value;

		//패스워드를 입력하지 않았다면 이전 패스워대로 저장
		if(!pw1 && !pw2){
			edit_user();
		}else{
		
		// 패스워드 입력 체크
		if(pw1.length<4 || pw2.length<4) {
			alert('비밀번호는 최소 4자리 이상 입력해주세요.');
			return false;
		}
		// 패스워드 반복입력 체크
		if(pw1!=pw2) {
		alert('입력하신 비밀번호가 일치하지 않습니다.');
		return false;
		}
	
		var res_1 = 0; var res_2 = 0; 
		// 조건1과 조건2의 결과를 저장할 변수
	
		// 조건1: 같은 문자열이 연속으로 3개 이상일 경우 제한합니다.
		/*
		for(var i=0; i<=pw1.length-3; i++) {
			if(pw1.charAt(i)==pw1.charAt(i+1) && pw1.charAt(i)==pw1.charAt(i+2)) {
				alert('같은 문자열을 3개 이상 연속으로 사용할 수 없습니다.');
				return false;
			}
		}
	
		// 조건2: 문자는 최소 2개 이상이며, 숫자 또는 특수문자가 1개 이상 포함되어야 합니다.
		for(var x=0; x<pw1.length; x++) {
			for(var y=0; y<str.length; y++) {
				if(pw1.charAt(x)==str.charAt(y)) res_1++;
			}
			for(var z=0; z<chr.length; z++) {
				if(pw1.charAt(x)==chr.charAt(z)) res_2++;
			}
		}
		// 조건1,2 결과 출력
		if(res_1<2) {
			alert('영문자는 최소 2개 이상 입력하세요.');
			return false;
		}
		if(res_2<1) {
			alert('아래와 같은 숫자 또는 특수문자를 1개 이상 입력하셔야 합니다.\n\n'+chr);
			return false;
		}
		// 패스워드에 아이디를 포함하는지 검사
		if (id.length>0) {
			if(pw1.indexOf(id) >= 0) {
				alert('아이디는 패스워드에  포함될 수 없습니다.');
				return false;
			}
		}
		*/
		
		edit_user();
		}
	}

//
</script>
<body style="margin-left: 15px">
<form name="user_add" action="admin_user_prc.jsp" method="post">
<input type="hidden" name="mode" value="edit">
<input type="hidden" name="m_seq" value="<%=member.getM_seq()%>">
<input type="hidden" name="m_ass_grade">
<input type="hidden" name="m_pro_grade">
<input type="hidden" name="m_app_grade">
<input type="hidden" name="ic_code" id="ic_code">
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/admin/member/tit_icon.gif" /><img src="../../../images/admin/member/tit_0501.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">관리자</td>
								<td class="navi_arrow2">사용자관리</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="15"></td>
			</tr>
			<!-- 타이틀 끝 -->
			<!-- 게시판 시작 -->
			<tr>
				<td>
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="height:30px;"><span class="sub_tit">필수입력 항목</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">
							<tr>
								<th><span class="board_write_tit">아이디</span></th>
								<td><input style="width:30%;" name="m_id" type="text" class="textbox2" value="<%=member.getM_id()%>" readonly></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">비밀번호</span></th>
								<td><input style="width:30%;" name="m_pass" type="password" class="textbox2" value=""> 비밀번호는 8자리 이상, 아이디와 다르게 입력하여 주십시오.</td>
							</tr>
							<tr>
								<th><span class="board_write_tit">비밀번호확인</span></th>
								<td><input style="width:30%;" name="m_pass2" type="password" class="textbox2" value=""> 입력하신 비밀번호를 한번 더 입력해 주세요.</td>
							</tr>
							<tr>
								<th><span class="board_write_tit">성명</span></th>
								<td><input style="width:30%;" name="m_name" type="text" class="textbox2" value="<%=member.getM_name()%>"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">이메일</span></th>
								<td><input style="width:30%;" name="m_email" type="text" class="textbox2" value="<%=member.getM_mail()%>"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">부서</span></th>
								<td><input style="width:30%;" name="m_dept" type="text" class="textbox2" value="<%=member.getM_dept()%>"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">사용자그룹</span></th>
								<td>
									<select style="width:30%;" name="m_mg">
<%
		for(int i=0; i < MGlist.size();i++) {
			  MGinfo = new MemberGroupBean();
			  MGinfo = (MemberGroupBean)MGlist.get(i);	
			  if(!SS_MG_NO.equals("9") && !SS_MG_NO.equals("70")) {
				  if(MGinfo.getMg_seq().equals("9") || MGinfo.getMg_seq().equals("70")){
						continue;
					} 
			  } 
%>
		      <option value="<%=MGinfo.getMg_seq()%>" <%if((member.getMg_seq()).equals(MGinfo.getMg_seq())) out.print("selected");%>><%=MGinfo.getMg_name()%></option>
<%
		}
	
%>
									</select>
									 사용자그룹별로 이용가능한 메뉴의 제한이 따릅니다.
								</td>
							</tr>
						<!-- </table>
						</td>
					</tr>
					<tr>
						<td style="padding-top:15px;height:45px;"><span class="sub_tit">추가입력 항목</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0"> -->
							<tr>
								<th><span class="board_write_tit">직급</span></th>
								<td><input style="width:30%;" name="m_dpos" type="text" class="textbox2" value="<%=member.getM_position()%>"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">전화번호</span></th>
								<td><input style="width:30%;" name="m_tel" type="text" class="textbox2" value="<%=member.getM_tel()%>"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">핸드폰</span></th>
								<td><input style="width:30%;" name="m_hp" type="text" class="textbox2" value="<%=member.getM_hp()%>"></td>
							</tr>
<%-- 							<tr>
								<th><span class="board_write_tit">현안이슈관리</span></th>
								<td>
										<%
											String checked = "";
										
												for(int i =0 ;i <check_list.size(); i++){
													checklist = check_list.get(i);
														check_data = checklist[2].split(",");
												}
												String[] chk_value = chkVal.split(",");
												String[] confirm = new String[4];
												
												for(int i = 0;i < chk_value.length; i++) {
													for(int j = 0;j < check_data.length; j++) {
														if(chk_value[i].equals(check_data[j])) {
															confirm[i] = check_data[j];
															break;
														} else {
															confirm[i] = "0";
														}
													}
												}
	                                    		for(int i =0 ;i <issue_list.size(); i++){
	                                    				tmp_info = issue_list.get(i);
	                                    				if(tmp_info[3].equals(confirm[i])) {
	                                    					checked = "checked";
	                                    				} else {
	                                    					checked = "";
	                                    				}
	                                    		
										%>
										 		<option value="<%=tmp_info[2]%>,<%=tmp_info[3]%>" <%=check%>><%=tmp_info[1]%></option>
										 		<option value="<%=tmp_info[0]%>" <%=check%>><%=tmp_info[1]%></option>
												<input type="checkbox" id="m_issuechk<%=i%>" name="m_issuechk"  onclick="check(<%=i%>);" value="<%=tmp_info[3]%>" <%=checked %>><%=tmp_info[1]%>
                                        <%
                                    			} 
											
                                        %>
								</td>
							</tr>
 --%>						</table>
						</td>
					</tr>
					<%-- <tr>
						<td style="padding-top:15px;height:45px;"><span class="sub_tit">VOC권한</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">
							<tr>
								<th><span class="board_write_tit">배정권한</span></th>
								<%
						        	String yesChk = "";
						        	String noChk = "";
						        	
						        	if(member.getM_ass_grade().equals("1")){
						        		yesChk = "checked";
						            	noChk = "";	
						        	}else{
						        		yesChk = "";
						            	noChk = "checked";
						        	}
						        	System.out.println(yesChk);
						        	System.out.println(noChk);
						        
						        %>
								
								<td>YES&nbsp;<input name="rd_ass_grade" type="radio" size="30" <%=yesChk%> >&nbsp;&nbsp;NO&nbsp;<input name="rd_ass_grade" type="radio" size="30" <%=noChk%>></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">처리권한</span></th>
								<%
						        	yesChk = "";
						        	noChk = "";
						        	
						        	if(member.getM_pro_grade().equals("1")){
						        		yesChk = "checked";
						            	noChk = "";	
						        	}else{
						        		yesChk = "";
						            	noChk = "checked";
						        	}
						        	
						        	System.out.println(yesChk);
						        	System.out.println(noChk);
						        
						        %>
								<td>YES&nbsp;<input name="rd_pro_grade" type="radio" size="30" <%=yesChk%> >&nbsp;&nbsp;NO&nbsp;<input name="rd_pro_grade" type="radio" size="30" <%=noChk%>></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">승인권한</span></th>
								<%
						        	yesChk = "";
						        	noChk = "";
						        	
						        	if(member.getM_app_grade().equals("1")){
						        		yesChk = "checked";
						            	noChk = "";	
						        	}else{
						        		yesChk = "";
						            	noChk = "checked";
						        	}
						        	System.out.println(yesChk);
						        	System.out.println(noChk);
						        
						        %>
								<td>YES&nbsp;<input name="rd_app_grade" type="radio" size="30" <%=yesChk%> >&nbsp;&nbsp;NO&nbsp;<input name="rd_app_grade" type="radio" size="30" <%=noChk%>></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr> --%>
			<!-- 게시판 끝 -->
			<tr>
				<td style="height:40px;" align="center"><img src="../../../images/admin/member/btn_save2.gif" onclick="passCheck();" style="cursor:pointer;"/><img src="../../../images/admin/member/btn_cancel.gif" onclick="window.history.back();" style="cursor:pointer;"/></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>