<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "risk.util.ParseRequest" %>
<%@ page import = "risk.issue.IssueMgr" %>
<%@ page import = "risk.issue.IssueDataBean" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ include file="../inc/sessioncheck.jsp" %>
<%
	String ipAddress = request.getRemoteAddr();
	boolean ipChk = false;
	
	ParseRequest pr = new ParseRequest(request);
    pr.printParams();
    
    String md_pseq    = pr.getString("md_pseq");
    String ic_seq    = pr.getString("ic_seq");
    //String ic_name    = new String(pr.getString("ic_name").getBytes("8859_1"), "UTF-8");
  	//String ic_name    = pr.getString("ic_name");
    String sDateFrom   = pr.getString("sDateFrom");
    String sDateTo     = pr.getString("sDateTo");
    String ir_stime    = pr.getString("ir_stime","16");
    String ir_etime    = pr.getString("ir_etime","16");
    
    String setStime = "";
	String setEtime = "";
	if(ir_stime.equals("24")){
		setStime = "23:59:59";	
	}else{
		setStime = ir_stime + ":00:00";
	}
	if(ir_etime.equals("24")){
		setEtime = "23:59:59";	
	}else{
		setEtime = ir_etime + ":59:59";
	}
	
	IssueMgr imgr = new IssueMgr();
	//ArrayList alData = imgr.getSourceData(md_pseq, ic_seq, sDateFrom, setStime, sDateTo, setEtime);
	ArrayList alData = imgr.getSourceData(md_pseq, sDateFrom, setStime, sDateTo, setEtime);
	
	//원문보기 권한 부여
	if(SS_M_ORGVIEW_USEYN.equals("Y")){
		ipChk = true;
	} 
%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<div id="zzFilter">
<!-- <table width="820px" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;"> -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
	<colgroup>
	<!-- 	<col width="1%" />
		<col width="3%" />선택박스
		<col width="17%" />출처 
		<col width="44%" />제목					
		<col width="5%" />원문
		<col width="14%" />출처구분					
		<col width="12%" />수집일시
		<col width="5%" />돋보기  -->
	<col width="5%"><col width="14%"><col width="40%"><col width="5%"><col width="5%"><col width="7%"><col width="5%"><col width="13%"><col width="7%"><col width="10%">
	
	</colgroup>
<%
if(alData.size() > 0){
	for(int i = 0; i < alData.size(); i++){
		IssueDataBean idBean = (IssueDataBean)alData.get(i);
		
		//제목 색갈변경하기
		String titleColor = "";
		if(idBean.getMd_seq().equals(idBean.getMd_pseq())){
			titleColor = "<font color='0000CC'/>";
		}else{
			titleColor = "";
		}
		//출처 색갈변경하기
		String sourchColor = "";
		if(idBean.getF_news() != null && idBean.getF_news().equals("Y")){
			sourchColor = "<font color='F41B2F'/>";
		}else{
			sourchColor = "";
		}
		
		String senti = "";
		if(idBean.getId_senti().equals("1")) {
			senti = "긍정"; 
		} else if(idBean.getId_senti().equals("2")) {
			senti = "부정";
		} else {
			senti = "중립";
		}
%>
	<tr bgcolor="F2F7FB">
		<%-- <td><input name="sameChk" type="checkbox" value="<%=idBean.getId_seq()%>" onclick=""/></td> --%>
		<td><img src="../../images/issue/icon.gif"></td>
		<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board01_category" title="<%=idBean.getMd_site_name()%>"><%=idBean.getMd_site_name()%></p></td>
		<td><p style="width:300px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board_01_tit_0<%=idBean.getMd_type()%>" title="<%=idBean.getId_title()%>"><a href="javascript:PopIssueDataForm('<%=idBean.getMd_seq()%>','N');"><%=titleColor%><%=idBean.getId_title()%></a></p></td>
		<%-- <td>
			<%if(ipChk){%>
				<a onClick="originalView('<%=idBean.getId_seq()%>');" href="javascript:void(0);" ><img src="../../images/issue/icon00.png" id="same_img_future<%=i%>" align="absmiddle" /></a>						
			<%} %>
		</td> --%>
		<td></td>
		<td>
			<a onClick="hrefPop('<%=idBean.getId_url()%>');" href="javascript:void(0);" ><img src="../../images/issue/ico_original.gif" align="absmiddle" /></a>
		</td>
		<td><%=sourchColor%><%=idBean.getSg_name()%></td>
		<td><%=senti%></td>
		<td><%=idBean.getFormatMd_date("yyyy-MM-dd")%></td>
		<td>
			<a onClick="report_save('E','<%=idBean.getId_seq()%>','<%=idBean.getId_title_replace()%>');" title="긴급 보고서 저장" style="margin-right: 10px;cursor:pointer;">
				<img src="../../images/issue/icon_issue_report_download_off.gif" />	
			</a>
		</td>
<%--	<td><img src="../../images/issue/ico_Telegram.gif" title="텔레그램 전송" style="cursor:pointer;" onclick="pop_send_telegram('<%=idBean.getMd_seq()%>');"></td> --%>
		<td><%=idBean.getM_name() %></td>
	</tr>
<%
	}
}
%>
</table>
</div>
<script type="text/javascript" >
	parent.fillSameList('<%=md_pseq%>');
</script>