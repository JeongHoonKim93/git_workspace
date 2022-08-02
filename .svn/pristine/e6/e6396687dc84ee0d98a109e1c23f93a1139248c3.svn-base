<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "risk.admin.member.MemberDao" %>
<%@ page import = "risk.admin.member.MemberBean" %>
<%@ page import = "risk.util.ParseRequest" %>
<%@ page import = "risk.sms.AddressBookDao" %>
<%@ page import = "risk.sms.AddressBookBean" %>
<%@page import="risk.util.*"%>


<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	int i;

	String mode = null;
	String m_id = null;
	String m_pass = null;
	String m_name = null;
	String m_dept = null;
	String m_dpos = null;
	String m_email = null;
	String m_tel = null;
	String m_hp = null;
	String m_seq = null;
	String m_ip = null;
	String m_mg = null;
	String m_sms = null;
	String m_date = null;
	String m_ass_grade = "0";
	String m_pro_grade = "0";
	String m_app_grade = "0";
	String ag_seq = "";
	String ic_type = "";
//	String ic_code = "";
	int type =0;
	String script = "";
	String[] multy_m_seq = null;

	StringBuffer sb = new StringBuffer();
	DateUtil du = new DateUtil();

	MemberDao dao = new MemberDao();
	MemberBean member = new MemberBean();
	SecurityUtil security = new SecurityUtil();
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();


	if( pr.getString("mode") != null ) mode = pr.getString("mode");
	//if( request.getParameter("mode") != null ) mode = request.getParameter("mode");
	

	if( mode != null )
	{
		if( mode.equals("ins") ) {													
			if( pr.getString("m_id") != null ) m_id = pr.getString("m_id");
				if(!dao.checkId(m_id)) {		
					if( pr.getString("m_pass") != null ) m_pass = pr.getString("m_pass");
					if( pr.getString("m_name") != null ) m_name = pr.getString("m_name");
					if( pr.getString("m_dept") != null ) m_dept = pr.getString("m_dept");
					if( pr.getString("m_dpos") != null ) m_dpos = pr.getString("m_dpos");
					if( pr.getString("m_email") != null ) m_email = pr.getString("m_email");
					if( pr.getString("m_tel") != null ) m_tel = pr.getString("m_tel");
					if( pr.getString("m_hp") != null ) m_hp = pr.getString("m_hp","");
					if( pr.getString("m_mg") != null ) m_mg = pr.getString("m_mg");
					
					if( pr.getString("m_ass_grade") != null ) m_ass_grade = pr.getString("m_ass_grade","0");
					if( pr.getString("m_pro_grade") != null ) m_pro_grade = pr.getString("m_pro_grade","0");
					if( pr.getString("m_app_grade") != null ) m_app_grade = pr.getString("m_app_grade","0");
					
					if( pr.getString("ag_seq") != null ) ag_seq = pr.getString("ag_seq","");

					if( pr.getString("ic_type") != null )ic_type = pr.getString("ic_type","16");
					//if( pr.getString("ic_code") != null )ic_code = pr.getString("ic_code","");
					
					member.setM_id(m_id);
					//member.setM_pass(m_pass);
					//암호화
					member.setM_pass(security.encryptSHA256(m_pass));
					//System.out.println(security.encryptSHA256(m_pass) + "security");
					member.setM_name(m_name);
					member.setM_dept(m_dept);
					member.setM_position(m_dpos);
					member.setM_mail(m_email);
					member.setM_tel(m_tel);
					member.setM_hp(m_hp);
					member.setMg_seq(m_mg);
					member.setM_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
					member.setM_ass_grade(m_ass_grade);
					member.setM_pro_grade(m_pro_grade);
					member.setM_app_grade(m_app_grade);
					member.setAg_seq(ag_seq);
					member.setIc_type(ic_type);
					//member.setIc_code(ic_code);
					dao.insertMember(member);
					/*
					member.setIc_type(ic_type);
					member.setIc_code(ic_code);
					dao.insertMember_IssueCheck(member);
					*/
/* 					//정보수신자 저장하기~
					AddressBookBean addBean = new AddressBookBean();
					addBean.setMab_seq(0);
					addBean.setMag_seq(ag_seq);	
					addBean.setMab_name(m_name);	
					addBean.setMab_dept(m_dept);
					addBean.setMab_mobile(m_hp);
					addBean.setMab_pos(m_dpos);
					addBean.setMab_mail(m_email);
					addBean.setMab_issue_receivechk("1");
					addBean.setMab_report_day_chk("1");
					addBean.setMab_report_week_chk("1");
					addBean.setMab_app_chk("0");
					addBean.setMab_sms_chk("0");
					
					AddressBookDao AddDao = new AddressBookDao();
					AddDao.insertAddressBook(addBean); */
					
				}else {				
					%>
					<SCRIPT LANGUAGE="JavaScript">
					<!--
					alert('중복되는 id가 있습니다! ');
					history.go(-1);
					//location.href = 'admin_user_list.jsp';
					//-->
					</SCRIPT>
					<%	
				}
		} else if( mode.equals("edit") ) {
			if( request.getParameter("m_seq") != null ) m_seq = request.getParameter("m_seq");
			if( pr.getString("m_id") != null ) m_id = pr.getString("m_id");
			if( pr.getString("m_pass") != null ) m_pass = pr.getString("m_pass");
			if( pr.getString("m_name") != null ) m_name = pr.getString("m_name");
			if( pr.getString("m_dept") != null ) m_dept = pr.getString("m_dept");
			if( pr.getString("m_dpos") != null ) m_dpos = pr.getString("m_dpos");
			if( pr.getString("m_email") != null ) m_email = pr.getString("m_email");
			if( pr.getString("m_tel") != null ) m_tel = pr.getString("m_tel");
			if( pr.getString("m_hp") != null ) m_hp = pr.getString("m_hp","");
			if( pr.getString("m_mg") != null ) m_mg = pr.getString("m_mg");
			if( pr.getString("m_date") != null ) m_date = pr.getString("m_date");
			
			if( pr.getString("m_ass_grade") != null ) m_ass_grade = pr.getString("m_ass_grade");
			if( pr.getString("m_pro_grade") != null ) m_pro_grade = pr.getString("m_pro_grade");
			if( pr.getString("m_app_grade") != null ) m_app_grade = pr.getString("m_app_grade");
			
			if( pr.getString("ic_type") != null )ic_type = pr.getString("ic_type","16");
			//if( pr.getString("ic_code") != null )ic_code = pr.getString("ic_code","");
			
			MemberBean mb = new MemberBean();
			member.setM_seq(m_seq);
			member.setM_id(m_id);
			//암호화
			if(m_pass != "") {
				member.setM_pass(security.encryptSHA256(m_pass));
			} else {
				member.setM_pass(m_pass);
			}
			//member.setM_pass(m_pass);
			//System.out.println(security.encryptSHA256(m_pass) + "security");
			member.setM_name(m_name);
			member.setM_dept(m_dept);
			member.setM_position(m_dpos);
			member.setM_mail(m_email);
			member.setM_tel(m_tel);
			member.setM_hp(m_hp);
		    member.setMg_seq(m_mg);
		    member.setM_regdate(du.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		    member.setM_ass_grade(m_ass_grade);
			member.setM_pro_grade(m_pro_grade);
			member.setM_app_grade(m_app_grade);
			member.setIc_type(ic_type);
			//member.setIc_code(ic_code);
			dao.updateMember(member);
			
			if( pr.getString("from").equals("login") ){
				
			%>
			<SCRIPT LANGUAGE="JavaScript">
			<!--
				location.href = '../../logout.jsp';
//			-->
			</SCRIPT>
				
				
				<%
			}
		
			
		}else if( mode.equals("del") ) {

			if( request.getParameter("m_seq") != null ) multy_m_seq = request.getParameterValues("m_seq");

			for(i=0; i< multy_m_seq.length;i++)
			{
				if( i != 0) sb.append(",");
				sb.append(multy_m_seq[i]);
				dao.deleteMember(multy_m_seq[i]);
			}
		}
	}	
	

%>
<SCRIPT LANGUAGE="JavaScript">
<!--	
	location.href = 'admin_user_list.jsp';
//-->
</SCRIPT>