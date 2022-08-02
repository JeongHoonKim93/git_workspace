<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import="risk.issue.IssueDataBean
                ,risk.issue.IssueMgr
                ,java.util.ArrayList
                ,risk.util.ParseRequest
                ,risk.issue.IssueCodeMgr
                ,risk.issue.IssueCodeBean
                ,risk.issue.IssueBean                
                ,risk.vip.VipMgr
                ,risk.vip.VipBean
                ,risk.search.userEnvMgr
                ,risk.search.userEnvInfo
                ,risk.util.StringUtil
                ,risk.util.DateUtil
                ,risk.util.PageIndex
                ,java.net.URLDecoder" %>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	ArrayList dataList = new ArrayList();
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	VipMgr vMgr = new VipMgr();
	VipBean vBean = new VipBean();
	

	String md_seq = pr.getString("md_seq");
	String md_pseq = pr.getString("md_pseq");

	
	dataList = vMgr.getSameList(md_pseq,md_seq);
	
%>

<%	
	for(int i=0;i<dataList.size();i++)
	{
		vBean = new VipBean();
		vBean = (VipBean)dataList.get(i);
%>			
<!--					<tr>-->
<!--						<td width="130" class="td_similar"><span class="cate">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=vBean.getMd_site_name()%></span></td>-->
<!--						<td width="450" class="td_similar"><p class="subject_similar"><a onClick="hrefPop('<%=vBean.getMd_url()%>');" href="javascript:void(0);"><%=su.cutString(vBean.getMd_title(),25,"...") %></a></p></td>-->
<!--						<td width="80" class="td_similar"><span class="date"><%=vBean.getFormatMd_date("MM-dd HH:mm")%></span></td>-->
<!--					</tr>-->
					
					<tr>
						<td width="110" align="center" bgcolor="#F1ECE6"><span class="cate"><%=vBean.getMd_site_name()%></span></td>
					  <td width="470" bgcolor="#F1ECE6"><p class="subject"><a onClick="hrefPop('<%=vBean.getMd_url()%>');" href="javascript:void(0);"><%=su.cutString(vBean.getMd_title(),25,"...") %></a></p></td>
						<td width="80" align="center" bgcolor="#F1ECE6"><span class="date"><%=vBean.getFormatMd_date("MM.dd HH:mm")%></span></td>
					</tr>	
<%
	}
%>					
	