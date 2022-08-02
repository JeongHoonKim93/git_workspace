<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
				 risk.util.*,
                 risk.util.ParseRequest,
                 risk.admin.backup.BackupListBean,
                 risk.admin.backup.BackupListMgr,
                 risk.admin.member.MemberDao"  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	MemberDao mdao = new MemberDao();	
	SecurityUtil security = new SecurityUtil();
	
	String m_seq = (String)session.getAttribute("SS_M_NO");
	
	String[] result = null;
	result = mdao.checkPasswordUpdateDate(m_seq).split("/");
	
	String present_pw = pr.getString("present_pw");
	String update_pw = pr.getString("update_pw");
	String update_pw_check = pr.getString("update_pw_check");
	//비밀번호 암호화 - SHA256
	String encryption_pw = security.encryptSHA256(update_pw_check);
	
	String mode = pr.getString("mode");

	if(mode.equals("delay")) {
		mdao.updatePassword(m_seq, result[0]);
%>
		<script type="text/javascript">
	 	alert('현재비밀번호 사용기한이 3개월 연장되었습니다.');
	 	//parent.reload();
	 	window.close();
		</script>
<% 
	} else {
	
			//새 비밀번호 업데이트 메서드
			mdao.updatePassword(m_seq, encryption_pw);
%>
			<script type="text/javascript">
			 	alert('비밀번호가 변경되었습니다.');
			 	//parent.reload();
			 	window.close();
			</script>
<%
	}
%>