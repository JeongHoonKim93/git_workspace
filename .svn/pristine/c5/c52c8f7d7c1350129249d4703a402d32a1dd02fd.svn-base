<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.DateUtil
				,risk.util.StringUtil
				,risk.voc.*
				,java.util.List
				,java.util.Iterator
				,risk.sms.AddressBookDao
				,risk.sms.AddressBookBean
				,risk.sms.AddressBookGroupBean
				,risk.issue.IssueDataBean	
				"%>
<%
		ParseRequest pr = new ParseRequest(request);		
		DateUtil	 du = new DateUtil();
		StringUtil		su = new StringUtil();
		ConfigUtil cu = new ConfigUtil();
		pr.printParams();
		
		
		
		/*
		String cate = pr.getString("cate");
		String user = pr.getString("user");
		String respon = pr.getString("respon");
		String grade_name = pr.getString("grade_name");
		String issue = pr.getString("issue");
		String memo = pr.getString("memo");
		*/
		
		
		String URL = cu.getConfig("URL");
		String imgURL = URL+"images/voc/";
		
		String v_seq = pr.getString("v_seq");
		String send    = pr.getString("send");
		String send_name    = pr.getString("send_name");
		String resend  = pr.getString("resend"); 
		String resend_name  = pr.getString("resend_name"); 
		String sDateFrom = pr.getString("sDateFrom");
		String sDateTo = pr.getString("sDateTo");
		String summary = pr.getString("summary");
		String mode = pr.getString("mode");
		
		VocDataMgr vMgr = new VocDataMgr();
		VocBean.AssignBean cbean = vMgr.getAssData(v_seq);
		
		
		ArrayList detail = vMgr.getVocProDetailList(v_seq);
		
		String topImg = "";
		if(mode.equals("reapp")){
			topImg = "mail_top_03.gif";
		}else{
			topImg = "mail_top.gif";
		}
%>

<%@page import="risk.util.ConfigUtil"%><html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--<link rel="stylesheet" href="../css/basic.css" type="text/css">-->
<style type="text/css">
table, td, input, select {font-size:12px; font-family:Dotum;line-height: 16px; color: #666666}
image {border:0px;}
.gray_s {
	font-size: 11px;
	color: #666666;
	line-height: normal;
	font-family: Dotum;
}
.orange_s {
	font-size: 11px;
	color: #DD6414;
	line-height: normal;
	font-family: Dotum;
}
.input_text {height:19px; border-top: 1px #CFCFCF solid; border-left: 1px #CFCFCF solid; border-right: 1px #CFCFCF solid; border-bottom: 1px #CFCFCF solid; background:#FFFFFF; font-size:12px; color:#666666;}
.input_text01 {height:16px; border-top: 1px #CFCFCF solid; border-left: 1px #CFCFCF solid; border-right: 1px #CFCFCF solid; border-bottom: 1px #CFCFCF solid; background:#FFFFFF; font-size:12px; color:#666666;}
.input_text02 {height:100px; border-top: 1px #CFCFCF solid; border-left: 1px #CFCFCF solid; border-right: 1px #CFCFCF solid; border-bottom: 1px #CFCFCF solid; background:#FFFFFF; font-size:12px; color:#666666;}table, td, input, select {font-size:12px; font-family:Dotum;line-height: 16px; color: #666666}
image {border:0px;}
.gray_s {
	font-size: 11px;
	color: #666666;
	line-height: normal;
	font-family: Dotum;
}
.orange_s {
	font-size: 11px;
	color: #DD6414;
	line-height: normal;
	font-family: Dotum;
}
.input_text {height:19px; border-top: 1px #CFCFCF solid; border-left: 1px #CFCFCF solid; border-right: 1px #CFCFCF solid; border-bottom: 1px #CFCFCF solid; background:#FFFFFF; font-size:12px; color:#666666;}
.input_text01 {height:16px; border-top: 1px #CFCFCF solid; border-left: 1px #CFCFCF solid; border-right: 1px #CFCFCF solid; border-bottom: 1px #CFCFCF solid; background:#FFFFFF; font-size:12px; color:#666666;}
.input_text02 {height:100px; border-top: 1px #CFCFCF solid; border-left: 1px #CFCFCF solid; border-right: 1px #CFCFCF solid; border-bottom: 1px #CFCFCF solid; background:#FFFFFF; font-size:12px; color:#666666;}
</style>
</head>
<body >






<table width="700" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="<%=imgURL%><%=topImg%>" width="700" height="59" /></td>
  </tr>
  <tr>
    <td><table width="700" border="0" cellpadding="0" cellspacing="1" bgcolor="E5E5E5">
      <tr>
        <td bgcolor="#FFFFFF"><table width="670" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
          	<td>
          		<table width="670" border="0" cellspacing="0" cellpadding="0"  style="display:">
          		<tr>	
          			<td height="35" style="padding:0px 0px 0px 3px"><img src="<%=imgURL%>arrow.gif" width="7" height="10" /><span class="gray_s"> CJ 시스템에서 </span><span class="orange_s"><strong>VOC 처리 요청이 접수</strong></span><span class="gray_s">되었습니다. </span></td>
          			<td align="right"><a href="<%=cu.getConfig("URL")%>" target="_blank"><img src="<%=imgURL%>btn_go_home.gif" style="cursor: pointer;" ></a></td>
          		</tr>
          		</table>
          	</td>
          	
          
          </tr>
          <tr>
            <td><table width="670" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="2" bgcolor="A09375"></td>
              </tr>
              <tr>
                <td><table width="670" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>제목</strong></td>
                    <td colspan="3" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><a href="<%=cbean.getMd_url()%>" target="_blank"><%=su.ChangeString(su.cutString(cbean.getMd_title(), 45, "..."))%></a></td>
                    </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>출처</strong></td>
                    <td width="235" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=cbean.getMd_site_name()%></td>
                    <td width="100" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>수집시간</strong></td>
                    <td width="235" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=du.getDate(cbean.getMd_date(),"yyyy-MM-dd HH:mm")%></td>
                  </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>등록자</strong></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=cbean.getM_name()%></td>
                    <td bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>등록시간</strong></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=du.getDate(cbean.getI_regdate(),"yyyy-MM-dd HH:mm")%></td>
                  </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>요청자</strong></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=send_name%></td>
                    <td bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>요청시간</strong></td>
                    <td bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=du.getDate("yyyy-MM-dd HH:mm")%></td>
                  </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>처리자</strong></td>
                    <td colspan="3" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=resend_name%></td>
                    </tr>
                  <tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong>처리기간</strong></td>
                    <td colspan="3" bgcolor="#FFFFFF" style="padding:0px 0px 0px 5px"><%=sDateFrom + " ~ " + sDateTo%></td>
                    </tr>
                    
                    
                    <%
					VocBean.VocProDetailBean sbean = null; 
					String title = "";
					for(int i = 0; i < detail.size(); i++){
						sbean = (VocBean.VocProDetailBean) detail.get(i);
						
						
						
						
						if(i == 0){
							title = "요청 내용";
						}else {
							
							if(Integer.parseInt(sbean.getVs_seq()) % 2 == 0){
								title = "처리내용";
							}else{
								title = "재요청내용";
							}
										
						}
						
				%>		
						
					<tr>
                    <td width="100" height="30" bgcolor="#F5F5F5" style="padding:0px 0px 0px 10px"><strong><%=title%></strong></td>
                    <td colspan="3" bgcolor="#FFFFFF" style="padding:5px 5px 5px 5px"><%=sbean.getSummary().replaceAll("\n","<br>")%></td>
                    </tr>
						
				<%		
					}	
                 %> 
                    
                  
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td height="15"></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>

</body>
</html>
