<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.DateUtil
				,risk.util.StringUtil
				,risk.issue.IssueCodeMgr
				,risk.issue.IssueCodeBean
				,risk.issue.IssueBean	
				,risk.issue.IssueMgr
				,java.util.List
				,java.util.Iterator
				,risk.issue.IssueDataBean"
%>
<%
	ParseRequest pr = new ParseRequest(request);		
	DateUtil	 du = new DateUtil();
	StringUtil		su = new StringUtil();
	pr.printParams();

	String type = "13";
	String mode = pr.getString("mode");
	String ptype = pr.getString("targetPType");
	String pcode = pr.getString("targetPCode");
	
	/* if(ptype.equals("2") || ptype.equals("3") || ptype.equals("4")){
		// TM, Family, 인물
		type = "14";
	}else{
		// 그룹 및 관계사
		type = "15";
	} */
	IssueMgr issueMgr = new IssueMgr();
	IssueCodeMgr 	icm = new IssueCodeMgr();
	IssueCodeBean icb = new IssueCodeBean();
	ArrayList arrIcBean = new ArrayList();	 //분류코드 어레이
	icm.init(0);
	arrIcBean = new ArrayList();
	arrIcBean = icm.GetType(Integer.parseInt(type));
	icb = new IssueCodeBean();
%>	
	<th style="background-color: #F8F8F8;"><span id="typeTitle<%=type %>" style="padding-left:10px;"><img alt="하위분류" src="../../images/issue/icon_bot_02.gif">정보 속성*</span></th>
	<td colspan="3" id="td_typeCode<%=ptype%>_infoAttr">
		<%
			//if(mode.equals("insert") || mode.equals("multi") || mode.equals("update_multi")){		
				
				for (int i = 1; i < arrIcBean.size(); i++) {
					icb = (IssueCodeBean) arrIcBean.get(i);
					if(ptype.equals("5") || ptype.equals("7")){
						if(icb.getIc_code() != 2){
							// 네이버, 카카오는 AI(CODE=2)만 표시
							continue;
						}
					}
		%>
		<input type='checkbox' name='typeCode<%=type %>_<%=ptype %>' value='<%=ptype %>,<%=pcode%>,<%=icb.getIc_type() %>,<%=icb.getIc_code() %>' onclick='comboCheck(this);' >
		<%=icb.getIc_name() %>
		<%if(i==6 && i!=0)out.print("<br>"); %>
		<%		}
			//}
		%>
		<input type='hidden' name='focus_typeCode<%=type %>_<%=ptype%>' value=''>
	</td>