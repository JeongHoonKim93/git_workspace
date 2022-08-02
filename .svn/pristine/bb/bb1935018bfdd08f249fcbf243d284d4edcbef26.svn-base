<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../../inc/sessioncheck.jsp" %>
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
                ,risk.statistics.StatisticsTwitterMgr
				,risk.statistics.StatisticsTwitterSuperBean
                ,risk.util.PageIndex" %>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	StatisticsTwitterMgr sMgr = new StatisticsTwitterMgr();
	
	ArrayList arrIcBean = null;
	ArrayList arrIdBean = null;
	ArrayList arrIdcBean =  null;
	
	int chk_cnt = 0;
	int nowpage = 1;	
	int pageCnt = 10;
	int totalCnt = 0;
	int totalPage = 0;	
	
	String searchKey = pr.getString("searchKey","");
	String tendency = pr.getString("tendency","");
	String getDate = pr.getString("getDate","");
	String mode2 = pr.getString("mode2","writer");
	String createType = pr.getString("createType");
	
	String typeCode = pr.getString("typeCode");
	String user_id = pr.getString("user_id");
	
	String html = pr.getString("html");
	
	String srtMsg = null;

	
	String sDateFrom = pr.getString("sDateFrom","");
	String sDateTo = pr.getString("sDateTo","");
	String stime = pr.getString("stime");
	String etime = pr.getString("etime");
	
	String setStime= stime.equals("0") ? "00:00:00" : stime + "00:00";
	String setEtime= etime.equals("24") ? "23:59:59" : stime + "00:00";
	
	
	//관련정보 리스트
	StatisticsTwitterSuperBean.TwitterList idBean = null;
	nowpage = pr.getInt("nowpage",1);
	if(mode2.equals("writer")){
		arrIdBean = sMgr.getTwitterList(nowpage,pageCnt,sDateFrom,sDateTo,setStime,setEtime,typeCode,searchKey,user_id, tendency,getDate, createType);
	}else if(mode2.equals("RT")){
		arrIdBean = sMgr.getTwitterList_RT(nowpage,pageCnt,sDateFrom,sDateTo,setStime,setEtime,typeCode,searchKey,user_id, tendency,getDate, createType, html);
	}
	totalCnt =  sMgr.getTotalCnt();
	
	totalPage = totalCnt / pageCnt;
	if(totalCnt % pageCnt > 0){
		totalPage += 1;
	}
	srtMsg = "수량 <b>"+totalCnt+"건</b>, "+nowpage+"/"+totalPage+" pages";
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RISK</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<style>
iframe.hide { border:0px solid red; position:absolute; top:0px; left:0px; z-index:-99; width:148px; height:150px; filter: alpha(opacity=0);}
</style>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/Calendar.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--

	//Url 링크
	var chkPop = 1;
	function hrefPop(url){
		//window.open(url,'hrefPop'+chkPop,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkPop,'');
		chkPop++;
	}

	//페이징
	function pageClick( paramUrl ) {	
       	var f = document.fSend; 
       	
        f.action = 'pop_twitter_list.jsp' + paramUrl;
        f.target='';
        f.submit();	        
	}

-->
</script>
</head>
<body>
<form name="fSend" id="fSend" action="" method="post">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<input type="hidden" name="typeCode" value="<%=typeCode%>">
<input type="hidden" name="sDateFrom" value="<%=sDateFrom%>">
<input type="hidden" name="sDateTo" value="<%=sDateTo%>">
<input type="hidden" name="stime" value="<%=stime%>">
<input type="hidden" name="etime" value="<%=etime%>">
<input type="hidden" name="user_id" value="<%=user_id%>">
<input type="hidden" name="mode2" value="<%=mode2%>">
<input type="hidden" name="searchKey" value="<%=searchKey%>">
<input type="hidden" name="tendency" value="<%=tendency%>">
<input type="hidden" name="getDate" value="<%=getDate%>">
<input type="hidden" name="createType" value="<%=createType%>">
<input type="hidden" name="html" value="<%=html%>">

<table width="800" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="43" background="../../../images/statistics/pop_bg.gif"><table width="780" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="145"><img src="../../../images/statistics/pop_title_0001.gif"></td>
        <td align="right" class="white_b"><strong><%=srtMsg%></strong></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="800" border="0" cellpadding="0" cellspacing="2" bgcolor="EBEBEB">
      <tr>
        <td bgcolor="#FFFFFF" style="padding:10px 0px 10px 0px"><table width="775" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center">
            
            
            
            
<table id="board_01" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
<col width="5%"><col width="15%"><col width="50%"><col width="16%"><col width="6%"><col width="8%">
	<tr>
		<th><input type="checkbox" name="checkall" value="" id="tt" onclick="checkAll(this);"></th>
		<th>출처</th>
		<th>제목</th>
		<th>수집일시</th>
		<th>성향</th>
		<th>중요도</th>
	</tr>
<%
		if(arrIdBean.size() > 0){
			for(int i = 0; i < arrIdBean.size(); i++){
				idBean = (StatisticsTwitterSuperBean.TwitterList)arrIdBean.get(i);
%>
	<tr>
		<td><input type="checkbox" name="issueCheck" value="<%=idBean.getId_seq()%>" onclick=""></td>
		<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board01_category" title="<%=idBean.getT_user_id()%>"><%=idBean.getT_user_id()%></p></td>
		<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board_01_tit_01" title="<%=su.toHtmlString(idBean.getId_title())%>"><a href="javascript:hrefPop('<%=idBean.getId_url()%>');"><%=su.toHtmlString(idBean.getId_title())%></a></p></td>
		<td><%=du.getDate(idBean.getMd_date(),"yyyy-MM-dd")%></td>
		<td><p class="tendency_0<%if(idBean.getType1().equals("긍정")){out.print("1");}else if(idBean.getType1().equals("중립")){out.print("2");}else{out.print("3");}%>"><%=idBean.getType1()%></p></td>
		<td>
			<dl>
				<dt>
<%
				if(idBean.getType2().equals("상")){
%>
					<dd><img src="../../../images/statistics/statusbar_03.gif" /></dd><dd><img src="../../../images/statistics/statusbar_03.gif" /></dd><dd><img src="../../../images/statistics/statusbar_03.gif" /></dd>
<%					
				}else if(idBean.getType2().equals("중")){
%>
					<dd><img src="../../../images/statistics/statusbar_03.gif" /></dd><dd><img src="../../../images/statistics/statusbar_03.gif" /></dd><dd><img src="../../../images/statistics/statusbar_01.gif" /></dd>
<%					
				}else{
%>
					<dd><img src="../../../images/statistics/statusbar_03.gif" /></dd><dd><img src="../../../images/statistics/statusbar_01.gif" /></dd><dd><img src="../../../images/statistics/statusbar_01.gif" /></dd>
<%					
				}
%>
				</dt>
			</dl>
		</td>
	</tr>
<%
			}
		}
%>
</table>


			</td>
          </tr>
          <!-- 페이징 -->
			<tr>
				<td>
				<table align="center" style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td>
							<table id="paging" border="0" cellpadding="0" cellspacing="2">
								<tr>
									<%=PageIndex.getPageIndex(nowpage, totalPage,"","" )%>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 페이징 -->
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>

</form>
</body>
</html>