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
	
	int nowPage = 0;	
	int pageCnt = 15;
	int totalCnt = 0;
	int totalPage = 0;
	
	String srtMsg = "";	

    
	nowPage = pr.getInt("nowPage",1);
	String date2 = du.getCurrentDate("yyyy-MM-dd");
	String date = du.addDay(date2,-6,"yyyy-MM-dd","yyyy-MM-dd");
	
	String k_xp = pr.getString("k_xp","11");
	String k_yp = pr.getString("k_yp","");
	String sg_seq = pr.getString("sg_seq","18");
    String sOrder       = pr.getString("sOrder","MD_DATE");
    String sOrderAlign  = pr.getString("sOrderAlign","DESC"); 
	
	dataList = vMgr.getKeySearchList(
									nowPage         ,   //int    piNowpage ,
						            pageCnt         ,   //int    piRowCnt  ,
						            k_xp                               ,   //String psXp      ,
						            k_yp                                ,   //String psYp      ,
						            ""                                ,   //String psZp      ,
						            sg_seq                             ,   //String psSGseq   ,
						            ""                             ,   //String psSDgsn   ,
						            date         ,   //String psDateFrom,
						            date2         ,   //String psDateTo  ,
						          	""                             ,   //String psKeyWord ,
						            ""                         ,   //String psType
						            sOrder + " " + sOrderAlign,      //String psOrder
						            "",
						            "N");
	
	
	totalCnt = vMgr.vBeanTotalCnt;	
	
	if (totalCnt>0) {
		totalPage = totalCnt / pageCnt;
		if ((totalCnt % pageCnt) > 0 ) {
			totalPage++;
		}
	}
	
	srtMsg = " <b>"+totalCnt+"건</b>, "+nowPage+"/"+totalPage+" pages";
%>				
				<table width="660"align="center" id="board" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<th class="line">매체명</th>
						<th class="line">제목</th>
						<th>작성일</th>
					</tr>
<%	
if(dataList.size()>0){
	for(int i=0;i<dataList.size();i++)
	{
		vBean = new VipBean();
		vBean = (VipBean)dataList.get(i);
%>
					<tr>
						<td width="110" align="center"><span class="cate"><%=su.cutString(vBean.getMd_site_name(),7,"...")%></span></td>
						<td width="470"><p class="subject"><a onClick="hrefPop('<%=vBean.getMd_url()%>');" href="javascript:void(0);" onmouseover="showHtml('htmlLayer<%=vBean.getMd_seq()%>');" onmouseout="showHtml('htmlLayer<%=vBean.getMd_seq()%>');"><%=su.cutString(vBean.getMd_title(),30,"...") %></a></p><%if(!vBean.getMd_same_count().equals("0")){%><a href="javascript:openSameLayer('<%=vBean.getMd_seq()%>','<%=vBean.getMd_pseq()%>','sameLayer<%=vBean.getMd_seq()%>','sameList<%=vBean.getMd_seq()%>');"><p class="similar">[유사<%=vBean.getMd_same_count()%>]</p></a><%}%></td>
						<td width="80" align="center"><span class="date"><%=vBean.getFormatMd_date("MM.dd HH:mm")%></span></td>						
					</tr>
					
					<tr id="sameLayer<%=vBean.getMd_seq()%>" style="display:none">
					  <td colspan="3"><table width="100%" align="right" id="board" border="0" cellpadding="0" cellspacing="0">
					  	<tbody id="sameList<%=vBean.getMd_seq()%>"></tbody>
					  </table></td>						
					</tr>
					
					<div id="htmlLayer<%=vBean.getMd_seq()%>" style="font-size: 12px; padding-right: 5px; display: none; padding-left: 5px; left: 140px;
                        width:500px; height:125px; padding-bottom: 5px; padding-top: 5px; position: absolute; background-color: #ffffcc; border: #ff6600 2px solid;">
					    <%=vBean.getHighrightHtml(200,"blue") %>				
					</div>
<%
	}
%>					
				</table>
				<table align="center" id="paging" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center">
						<%=PageIndex.getVipPageIndex(nowPage,totalPage,"","" )  %>
						</td>
					</tr>
				</table>
<%
}else{
%>
				<table align="center" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="20">
						&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center">
						<b>데이터가 존재 하지 않습니다.</b>
						</td>
					</tr>
					<tr>
						<td height="10">
						&nbsp;
						</td>
					</tr>
				</table>
<%
}
%>