<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import="risk.issue.IssueDataBean
                ,risk.issue.IssueMgr
                ,java.util.ArrayList
                ,risk.util.ParseRequest
                ,risk.issue.IssueCodeMgr
                ,risk.issue.IssueCodeBean
                ,risk.issue.IssueBean                
                ,risk.search.MetaMgr
                ,risk.search.userEnvMgr
                ,risk.search.userEnvInfo
                ,risk.dashboard.dashboardMgr
                ,risk.util.StringUtil
                ,risk.util.DateUtil
                ,risk.util.PageIndex
                ,java.net.URLDecoder" %>

<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	IssueMgr issueMgr = new IssueMgr();
	MetaMgr smgr = new MetaMgr();
	dashboardMgr dMgr = new dashboardMgr();
	ArrayList arrIcBean = null;
	ArrayList arrIdBean = null;
	ArrayList arrIdcBean =  null;
	
	ArrayList arrSiteGroup = null;
	
	
	int nowpage = pr.getInt("nowpage",1);
	int pageCnt = 10;
	int totalCnt = 0;
	int totalPage = 0;	
	
	
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String ir_stime = pr.getString("ir_stime","00:00:00");
	String ir_etime = pr.getString("ir_etime","23:59:59");

	
	String srtMsg = null;
	String typeCode = pr.getString("typeCode");
	String todayKey = pr.getString("todayKey");
	
	//간판
	String menuType = pr.getString("menuType");
	String title = "";
	int tier = 0;
	if(menuType.equals("public1")){
		title = "언론 우호도 ";
		tier = 1;
	}else if(menuType.equals("media1")){
		title = "개인 우호도";
		tier = 0;
	}

	
	String beforDateFrom = du.addDay_v2(sDateFrom, -1);
	String beforDateTo = du.addDay_v2(sDateTo, -1);
	String nextDateFrom = du.addDay_v2(sDateFrom, 1);
	String nextDateTo = du.addDay_v2(sDateTo, 1);
	
	//출처 있는지 확인
	String s_seq = "";
	String issueTypeCode = typeCode;
	if(!typeCode.equals("")){
		String[] arrType = typeCode.split("&");
		if(arrType.length > 1){
			s_seq = arrType[0];
			issueTypeCode = arrType[1];
		}
	}
	
	//투데이 키워드
	String id_sqqs = "";
	if(!todayKey.equals("")){
		id_sqqs = dMgr.TodayKeywordSeq(sDateFrom,ir_stime,sDateTo,ir_etime,todayKey);
	}
	
	//IssueCodeMgr 인스턴스 생성
	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
	IssueCodeBean icBean = null;	
	icMgr.init(0);
	
	
	//관련정보 리스트
	IssueDataBean idBean = null;	
	arrIdBean = issueMgr.getIssueDataList(nowpage,pageCnt,id_sqqs,"","","","","2",sDateFrom,ir_stime,sDateTo,ir_etime,issueTypeCode,"","Y","","","","",s_seq,tier);
	totalCnt =  issueMgr.getTotalIssueDataCnt();
	totalPage = totalCnt / pageCnt;
	if(totalCnt % pageCnt > 0){
		totalPage += 1;
	}

%>

<script language="JavaScript" type="text/JavaScript">

	var chkPop = 1;
	function hrefPop(url){
		//window.open(url,'hrefPop'+chkPop,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkPop,'');
		chkPop++;
	}

	function fn_before()
	{
		var nowpage = Number(document.getElementById("nowpage").value);
				
		if( nowpage > 1)
		{
			document.getElementById("nowpage").value = nowpage - 1;
			ajax.post('aj_mobile_issue_list.jsp?nowpage='+document.getElementById("nowpage").value,'fSend','dashboard_area');
		}
	}
	function fn_next()
	{
		var nowpage = Number(document.getElementById("nowpage").value);
		var totalPage = Number(document.getElementById("totalPage").value);

		if( nowpage < totalPage)
		{
			document.getElementById("nowpage").value = nowpage + 1;
			ajax.post('aj_mobile_issue_list.jsp?nowpage='+document.getElementById("nowpage").value,'fSend','dashboard_area');
		}
	}
	
	
</script>
  
<input type="hidden" name="nowpage" id="nowpage" value="<%=nowpage%>">
<input type="hidden" name="totalPage" id="totalPage" value="<%=totalPage%>">
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="31" bgcolor="#e4e4e4" style="padding:3px 13px 0px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><strong class="menu_gray"><%=title%></strong><img src="../../images/mobile/txt_line.gif" width="9" height="13" align="absmiddle"><strong class="menu_black"> <%=totalCnt%>건</strong></td>
        <td align="right">

		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="" bgcolor="#B9B9B9"></td>
  </tr>

<%
	String sunghyang = "";
	for(int i =0; i < arrIdBean.size(); i++){
		idBean = (IssueDataBean)arrIdBean.get(i);
		arrIdcBean = (ArrayList) idBean.getArrCodeList();
		sunghyang = icMgr.GetCodeNameType(arrIdcBean,9);
%>
  
  <tr>
    <td bgcolor="#FFFFFF" style="padding:8px 13px 8px 13px"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="23" class="menu_blue"><a onClick="hrefPop('<%=idBean.getId_url()%>');" href="javascript:void(0);"><%=su.nvl(su.cutString(idBean.getId_title(),30,"..." ),"제목없음")%></a></td>
      </tr>
      <tr>
        <td class="menu_gray"><%=su.cutKey(su.toHtmlString(idBean.getId_content().replaceAll("<font color='black'>","").replaceAll("<b>","").replaceAll("</font>","").replaceAll("</b>","")), "포스코 POSCO", 50, "0000CC")%></td>
      </tr>
      <tr>
<%
	
	String type9Img = ""; 
	
	if(sunghyang.equals("긍정")){
		type9Img = "<img src='../../images/mobile/smile_icon_01.gif' width='16' height='16' align='absmiddle'></span><span class='smile'><strong>긍정</strong></span>";
	}else if(sunghyang.equals("부정")){
		type9Img = "<img src='../../images/mobile/bad_icon_01.gif' width='16' height='16' align='absmiddle'></span><span class='bad'><strong>부정</strong></span>";
	}else if(sunghyang.equals("중립")){
		type9Img = "<img src='../../images/mobile/s_icon_01.gif' width='16' height='16' align='absmiddle'></span><span class='s'><strong>중립</strong></span>";
	}
%>
      
        <td height="20"><span class="menu_red"><%=idBean.getMd_site_name()%></span> <span class="menu_gray02"><%=du.getDate(idBean.getMd_date(), "yy/MM/dd HH:mm")%> <%=type9Img%></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#e1e1e1" ></td>
  </tr>
  
<%
	}
%> 
  
  <tr>
    <td height="2" background="../../images/mobile/list_line01.gif" ></td>
  </tr>
  <tr>
    <td height="28" background="../../images/mobile/list_bg01.gif" bgcolor="#FFFFFF" ><table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="50%" align="center" class="textwhite" onclick="fn_before();" style="cursor: pointer;"><strong class="textwhite02">&lt; 이전</strong></td>
        <td width="50%" align="center" onclick="fn_next();" background="../../images/mobile/list_bar01.gif" class="textwhite02" style="background-repeat:no-repeat; font-weight: bold; cursor: pointer;">다음 &gt;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="2" background="../../images/mobile/list_line02.gif"></td>
  </tr>
  </table>
  
  
  
  