<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@ page contentType = "text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import = "risk.admin.member.MemberDao" %>
<%@ page import = "risk.admin.member.MemberGroupBean" %>
<%@ page import = "risk.util.ConfigUtil" %>
<%@ page import = "java.sql.Timestamp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>

<%@ page import = "risk.sms.AddressBookDao
					,risk.sms.AddressBookBean
					,risk.sms.AddressBookGroupBean
				"%>

<%@ include file="../../inc/sessioncheck.jsp" %>
<%

	List MGlist = null;
	ConfigUtil cu = new ConfigUtil();
	MemberDao dao = new MemberDao();
	MGlist = dao.getMGList();
	//이슈 1depth - 부서
//	String issue_type = "16"; 
//	ArrayList<String[]> issue_list =  mgr.getIssueCodeList(issue_type);
	String[] tmp_info = null;	
	
	int userCnt = 0;
	int userLimit = 0;
	String script="";
	// 프로퍼티에서 유저 제한 정보를 가져옴
	userLimit = Integer.parseInt(cu.getConfig("USERLIMIT"));
	if(userLimit>0){
		dao.getList("1","","");
		userCnt = dao.totalct;
		
		System.out.println("userLimit : "+userLimit);
		System.out.println("userCnt : "+userCnt);
		
		if(userCnt >= userLimit  ){
	
			script = "alert('시스템 사용자는 50명까지 등록이 가능합니다. \\n\\n사용자를 50명이상 추가하시려면 영업담당에게 연락하여 주십시요. ');  ";
			script +="\n  user_add.action='admin_user_list.jsp' ";
			script +="\n  user_add.target='' ";
			script +="\n  user_add.submit(); ";
		}
	}
	
	//정보수신자 리스트 가져오기~
	AddressBookDao AddDao = new AddressBookDao();
	AddressBookBean AddBean;	
	List AbGroupList = new ArrayList();
	AbGroupList = AddDao.getAdressBookGroup();
	AddressBookGroupBean abgBean = null;
	Iterator it = null;
	if(AbGroupList!=null)
	{
		it = AbGroupList.iterator();
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RIS-K</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script>
//	var issueList="";
/* 	function check(index) {
		
		var m_issuechk = document.getElementsByName("m_issuechk");
		var DATA="";
		$("input[name=m_issuechk]:checked").each(function(i){
				DATA += "|"+($(this).val());
	     });
		//체크박스 체크할때
		if (m_issuechk[index].checked == true) {
			//reportList에 체크한 이슈의 idseq값 저장
			 if(issueList == ''){
				 issueList = m_issuechk[index].value;
	          }else{
	        	  issueList += "," + m_issuechk[index].value;
	          }
		
		//체크박스 체크 해제할때	
		} else {	
			//temp list
			var result = ""; 
			var tmp_report_list = issueList.split(',');
			
			//선택해제한 값들 빼고 tmp배열에 저장
			for(var i = 0; i < tmp_report_list.length; i++){
			 	if(m_issuechk[index].value != tmp_report_list[i]){
					if(result == ''){
						result = tmp_report_list[i];
				 	}else{
				 		result += "," + tmp_report_list[i];
				 	}	
			 	}
			}
			//reportList에 다시 tmp배열 저장
			issueList = result;

		}
	} 
 */	

	function init()
	{
		user_add.m_id.focus();
	}
	function user_sub()
	{
		if(!user_add.m_id.value)
		{
			alert('아이디를 입력하십시요.');
			user_add.m_id.focus();
		} else if(!user_add.m_pass.value)
		{
			alert('패스워드를 입력하십시요.');
			user_add.m_pass.focus();
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
		}
/* 		else if(!$('#m_issuechk_list').val(issueList))
		{
			alert('현안이슈관리를 선택하십시요.');
			user_add.m_issuechk_list.focus();
		}
 */		else {

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
		//	$('#ic_code').val(issueList);
			user_add.submit();
		}
	}
	
	var str = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'; // 문자열만을 포함하는 변수
	var chr = '1234567890'; // 숫자를 포함하는 변수
	var schr = '!@#$%^&*()-_=+'; // 특수문자열을 포함하는 변수
	var str1 = 'qwertyuiop';
	var str2 = 'asdfghjkl';
	var str3 = 'zxcvbnm';
	
	function passCheck() {
	// 비밀번호 최소 길이를 설정합니다. (입력확인에 부가적으로 필요한 부분)
		var id = document.all.m_id.value;
		var pw1 = document.all.m_pass.value;
		var pw2 = document.all.m_pass2.value;
	
		/*
		// 패스워드 입력 체크
  		if(pw1.length<8 || pw2.length<8) {
			alert('비밀번호는 최소 8자리 이상 입력해주세요.');
			return false;
		}
		*/

		// 패스워드 반복입력 체크
 		if(pw1!=pw2) {
		alert('입력하신 비밀번호가 일치하지 않습니다.');
		return false;
		}
		// id와 패스워드 동일하면 false
		if(id==pw1) {
		alert('아이디와 비밀번호는 다르게 설정해주세요.');
		return false;		
		}
 		
		/*
		for(var i=0; i<id.length-3; i++){
			for(var x=0; x<pw1.length-3; x++) {
				if(id.charAt(i)==pw1.charAt(x) && id.charAt(i+1)==pw1.charAt(x+1) && id.charAt(i+2)==pw1.charAt(x+2) && id.charAt(i+3)==pw1.charAt(x+3)) {
					alert('비밀번호는 아이디의 연속된 4글자를 포함할 수 없습니다.');
					return false;							
				}
			}
		}
 
		for(var i=0; i<pw1.length-2; i++){
			for(var x=0; x<str1.length-2; x++) {
				if(pw1.charAt(i)==str1.charAt(x) && pw1.charAt(i+1)==str1.charAt(x+1) && pw1.charAt(i+2)==str1.charAt(x+2)) {
					alert('3자리 이상의 연속된 키보드 자판으로 비밀번호를 설정하실 수 없습니다.');
					return false;							
				}
			}
			for(var y=0; y<str2.length-2; y++) {
				if(pw1.charAt(i)==str2.charAt(y) && pw1.charAt(i+1)==str2.charAt(y+1) && pw1.charAt(i+2)==str2.charAt(y+2)) {
					alert('3자리 이상의 연속된 키보드 자판으로 비밀번호를 설정하실 수 없습니다.');
					return false;							
				}
			}
			for(var z=0; z<str3.length-2; z++) {
				if(pw1.charAt(i)==str3.charAt(z) && pw1.charAt(i+1)==str3.charAt(z+1) && pw1.charAt(i+2)==str3.charAt(z+2)) {
					alert('3자리 이상의 연속된 키보드 자판으로 비밀번호를 설정하실 수 없습니다.');
					return false;							
				}
			}		
			for(var k=0; k<chr.length-2; k++) {
				if(pw1.charAt(i)==chr.charAt(k) && pw1.charAt(i+1)==chr.charAt(k+1) && pw1.charAt(i+2)==chr.charAt(k+2)) {
					alert('3자리 이상의 연속된 키보드 자판으로 비밀번호를 설정하실 수 없습니다.');
					return false;							
				}
			}			
		}

		var res_1 = 0; var res_2 = 0; var res_3 = 0; // 조건1과 조건2와 조건3의 결과를 저장할 변수
	
		// 조건1: 같은 문자열이 연속으로 3개 이상일 경우 제한합니다.
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
			for(var k=0; k<schr.length; k++) {
				if(pw1.charAt(x)==schr.charAt(k)) res_3++;
			}
		}
		
 		if(res_1 == 0 || res_2 == 0 || res_3 == 0){
			alert('비밀번호는 영문자,숫자,특수문자 혼용으로 설정해야 합니다.');
			return false;
		}
  		*/
		user_sub();
	}


</script>
</head>
<body style="margin-left: 15px">
<form name="user_add" action="admin_user_prc.jsp" method="post">
<input type="hidden" name="mode" value="ins">
<input type="hidden" name="m_ass_grade">
<input type="hidden" name="m_pro_grade">
<input type="hidden" name="m_app_grade">
<!-- <input type="hidden" name="ic_code" id="ic_code"> -->
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
						<td style="height:30px;"><span class="sub_tit"> 입력 항목</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">
							<tr>
								<th><span class="board_write_tit">아이디</span></th>
								<td><input style="width:30%;" name="m_id" type="text" class="textbox2"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">비밀번호</span></th>
								<td><input style="width:30%;" name="m_pass" type="password" class="textbox2"> 비밀번호는 8자리 이상, 아이디와 다르게 입력하여 주십시오.</td>
							</tr>
							<tr>
								<th><span class="board_write_tit">비밀번호확인</span></th>
								<td><input style="width:30%;" name="m_pass2" type="password" class="textbox2"> 입력하신 비밀번호를 한번 더 입력해 주세요.</td>
							</tr>
							<tr>
								<th><span class="board_write_tit">성명</span></th>
								<td><input style="width:30%;" name="m_name" type="text" class="textbox2"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">이메일</span></th>
								<td><input style="width:30%;" name="m_email" type="text" class="textbox2"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">부서</span></th>
								<td><input style="width:30%;" name="m_dept" type="text" class="textbox2"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">사용자그룹</span></th>
								<td>
									<select style="width:30%;" name="m_mg">

<%-- <%	
	
	if(!SS_M_ID.equals(cu.getConfig("ADMIN"))){
		
	

		for(int i=1; i < MGlist.size();i++) {
			MemberGroupBean MGinfo = (MemberGroupBean)MGlist.get(i);
			if(MGinfo.getMg_seq().equals("9")){
				continue;
			}
%>		
	          <option value="<%=MGinfo.getMg_seq()%>"><%=MGinfo.getMg_name()%></option>
<%
		}
	}else{
		for(int i=0; i < MGlist.size();i++) {
			MemberGroupBean MGinfo = (MemberGroupBean)MGlist.get(i);
%>
			<option value="<%=MGinfo.getMg_seq()%>"><%=MGinfo.getMg_name()%></option>
<%
	
		}
	}
%> --%>
	<%
	for(int i=0; i < MGlist.size();i++) {
		MemberGroupBean MGinfo = (MemberGroupBean)MGlist.get(i);
		
	%>
		<option value="<%=MGinfo.getMg_seq()%>"><%=MGinfo.getMg_name()%></option>
	<%
	}
	%>
									</select>
									 사용자그룹별로 이용가능한 메뉴의 제한이 따릅니다.
								</td>
							</tr>
							
							
							
							<tr>
								<th><span class="board_write_tit">수신자그룹</span></th>
								<td>
									<select style="width:30%;" name="ag_seq">
							<%
								if(it!=null){
									String selected = "";
									while(it.hasNext()){
										selected = "";
										abgBean = new AddressBookGroupBean();
										abgBean = (AddressBookGroupBean)it.next();
									
							%>        	
															<option value="<%=abgBean.getAg_seq()%>" ><%=abgBean.getAg_name()%></option>
							<%
									}
								}
							%>
									</select>
								</td>
							</tr>
							
							
				<!-- 		</table>
						</td>
					</tr>
					<tr>
						<td style="padding-top:15px;height:45px;"><span class="sub_tit">추가입력 항목</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">  -->
						
							<tr>
								<th><span class="board_write_tit">직급</span></th>
								<td><input style="width:30%;" name="m_dpos" type="text" class="textbox2"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">전화번호</span></th>
								<td><input style="width:30%;" name="m_tel" type="text" class="textbox2"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">핸드폰</span></th>
								<td><input style="width:30%;" name="m_hp" type="text" class="textbox2"></td>
							</tr>
<%-- 							<tr>
								<th><span class="board_write_tit">현안이슈관리</span></th>
								<td>
										<%
                                    		for(int i =0 ;i <issue_list.size(); i++){
                                    			tmp_info = issue_list.get(i);
                                    			System.out.println(tmp_info[3]);
                                   
										%>
										 		<option value="<%=tmp_info[2]%>,<%=tmp_info[3]%>" <%=check%>><%=tmp_info[1]%></option>
										 		<option value="<%=tmp_info[0]%>" <%=check%>><%=tmp_info[1]%></option>
												<input type="checkbox" id="m_issuechk<%=i%>" name="m_issuechk" onclick="check(<%=i%>);" value="<%=tmp_info[3]%>"><%=tmp_info[1]%>
                                        <%
                                            } 
                                        %>
								</td>
							</tr> --%>
						</table>
						</td>
					</tr>
					<!-- <tr>
						<td style="padding-top:15px;height:45px;"><span class="sub_tit">VOC권한</span></td>
					</tr>
					<tr>
						<td>
						<table id="board_write" border="0" cellspacing="0">
							<tr>
								<th><span class="board_write_tit">배정권한</span></th>
								<td>YES&nbsp;<input name="rd_ass_grade" type="radio" size="30">&nbsp;&nbsp;NO&nbsp;<input name="rd_ass_grade" type="radio" size="30" checked="checked"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">처리권한</span></th>
								<td>YES&nbsp;<input name="rd_pro_grade" type="radio" size="30">&nbsp;&nbsp;NO&nbsp;<input name="rd_pro_grade" type="radio" size="30" checked="checked"></td>
							</tr>
							<tr>
								<th><span class="board_write_tit">승인권한</span></th>
								<td>YES&nbsp;<input name="rd_app_grade" type="radio" size="30">&nbsp;&nbsp;NO&nbsp;<input name="rd_app_grade" type="radio" size="30" checked="checked"></td>
							</tr>
						</table>
						</td>
					</tr> -->
				</table>
				</td>
			</tr>
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