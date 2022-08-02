<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList
                ,risk.util.ParseRequest
                ,risk.admin.site.SiteMng  
                ,risk.admin.keyword.*
                ,risk.search.MetaMgr
                ,risk.search.userEnvMgr
                ,risk.search.userEnvInfo
                ,risk.util.StringUtil
                ,risk.util.DateUtil
                ,risk.util.PageIndex
                ,java.net.URLDecoder
                ,risk.admin.site.SiteMng
                ,risk.admin.site.SitegroupBean
                ,java.util.List 
                ,risk.vip.VipMgr"%>              
<%
	String remoteAddress = "";
	boolean ipCheck = false;
	VipMgr vMgr = new VipMgr();
	
	if(request.getRemoteAddr()!=null){
		//ipCheck  = vMgr.getAuthorityIp(request);
		;
	}
	ipCheck = true;
	if(ipCheck){

		ParseRequest pr	= new ParseRequest(request);
		pr.printParams();	
		DateUtil du	= new DateUtil();
		StringUtil	su 	= new StringUtil();
		MetaMgr smgr = new MetaMgr();		
		SiteMng siteMng = new SiteMng();
		SitegroupBean sgBean = new SitegroupBean();
		KeywordMng keywordMng = new KeywordMng();
		KeywordBean kBean = new KeywordBean();
		List arrSiteBean = new ArrayList();
		List arrKeywordBean = new ArrayList();
		
		arrSiteBean = siteMng.getSGList();
		arrKeywordBean = keywordMng.getKeywordInfo("11","0","1");
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
body {
	font-size:12px;
	font-family:dotum, 돋움;
	background:#85b9d4;
	background-color: #CEC7BD;
}
th,td {font-size:12px;}
hr {width:0px;list-style-type:none;border:0px;margin:0px;padding:0px;height:5px;}

#tab td {height:33px; background:url('images/tab_bg_off.gif') no-repeat; line-height:33px; text-align:center; font-size:12px; font-weight:bold; color:#FFFFFF;}
#tab th {height:33px; background:url('images/tab_bg_on.gif') no-repeat; line-height:33px; font-size:12px; font-weight:bold; color:#000000;}
#tab .selected {height:33px; background:url('images/tab_bg_on.gif') -1px no-repeat; line-height:33px; font-size:12px; font-weight:bold; color:#000000;}
#tab A:link    {color:#ffffff;text-decoration:none;}
#tab A:visited {color:#ffffff;text-decoration:none;}
#tab A:hover   {color:#F9B868;text-decoration:underline;}

#tab_sub {height:31px;background:url('images/tab_bg_sub.gif');}
#tab_sub p {float:left; margin:0px; padding-left:15px; padding-right:10px; background:url('images/tab_bg_sub_line.gif') right 50% no-repeat; font-size:12px; color:#3b7da0;}
#tab_sub A:link    {color:#6C6357; text-decoration:none;}
#tab_sub A:visited {color:#6C6357; text-decoration:none;}
#tab_sub A:hover   {color:#6C6357; font-weight:bold; text-decoration:none;}

.mid_bg {background:#FFFFFF; border-left:1px #dbdbdb solid; border-right:1px #dbdbdb solid; padding-top:10px; padding-bottom:10px; text-align:center;}

#board {width:629px; background:url('images/board_bg_top.gif') no-repeat;}
#board th {height:25px; color:#FFFFFF; font-size:12px;}
#board .line {background:url('images/board_bg_top_line.gif') right no-repeat;}
#board td {height:26px; border-bottom:1px #c1c1c1 dotted; font-size:12px; color:#767676;line-height: 16px;}
#board .td_similar {background:#EEEEEE; border-bottom:1px #edf2f6 solid;}
#board .td_similar_end {background:#EEEEEE; border-bottom:1px #cccccc solid;}
#board .cate {color:#816F58;}
#board .date {font-size:11px;}
#board .subject_similar {float:left; margin:0px; background:url('images/icon_similar.gif') 10px 50% no-repeat; text-indent:20px;}
#board .subject {float:left; margin:0px;}
#board .similar {float:right; margin:0px; font-size:11px; color:#bb2525; padding-right:10px;}
#board A:link    {color:#333333; text-decoration:none;}
#board A:visited {color:#333333; text-decoration:none;}
#board A:hover   {color:#AB7834; text-decoration:none;}

#paging {width:629px; border:1px #e8e8e8 solid; padding:5px;}
#paging p {float:left; margin:0px; padding-left:10px; padding-right:10px; border-right:1px #e9e9e9 solid; font-size:11px; color:#3b7da0;}
#paging A:link    {color:#777777; text-decoration:none;}
#paging A:visited {color:#777777; text-decoration:none;}
#paging A:hover   {color:#ff4200; font-weight:bold; text-decoration:none;}
.style1 {
	color: #C87609;
	font-weight: bold;
}
</style>

<script src="./timer.js" type="text/javascript"></script>
<script src="/js/jquery.js" type="text/javascript"></script>
<script src="/js/ajax.js" type="text/javascript"></script>
<script src="/js/popup.js" type="text/javascript"></script>

<script language="JavaScript" type="text/JavaScript">

	$(document).ready(init);
	
	//검색 텍스트 활성화
	function init(){				
		listLoad();	
		setTimer('5');
	}
	
	function listLoad(){
		ajax.post2('aj_data_list.jsp','vipMain','list','ajax-loader2.gif');
	}
	
	var chkPop = 0;
	function hrefPop(url){
		//window.open(url,'hrefPop'+chkPop,'');
		window.open('http://hub.buzzms.co.kr?url=' + encodeURIComponent(url),'hrefPop'+chkPop,'');
		chkPop++;
	}

	//페이징
	function pageClick(pageNum) {		
		$('#nowPage').val(pageNum);	
		listLoad();
	}
		

	function changeSite(tabid, key, tabsize)
	{
		$('#nowPage').val('1');	//페이지 초기화
		setSiteTabColor(tabid,tabsize);
		$('#sg_seq').val(key);	
		listLoad();
	}

	function setSiteTabColor(tabid, tabsize)
	{
		for(var i=0;i<tabsize;i++){
			$('#sitetab'+String(i)).attr('class','');
		}
		$('#'+tabid).attr('class','selected');
	}

	function changeKeyword(tabid, key, tabsize)
	{
		$('#nowPage').val('1');	//페이지 초기화
		setKeywordTabColor(tabid,tabsize);
		$('#k_yp').val(key);	
		listLoad();
	}

	function setKeywordTabColor(tabid, tabsize)
	{
		for(var i=0;i<tabsize;i++){
			$('#keywordtab'+String(i)).attr('class','');
		}
		$('#'+tabid).attr('class','selected');
	}

	function openSameLayer(md_seq, md_pseq, layerId, listId)
	{
		if($('#'+layerId).css('display')=='none'){
			$('#md_seq').val(md_seq);	
			$('#md_pseq').val(md_pseq);		
			$('#'+layerId).show();
			ajax.post('aj_data_same_list.jsp','vipMain',listId);	
		}else{
			$('#'+layerId).hide();		
		}
	}
	
	function showHtml(id)
	{		
		if($('#'+id).css('display')!='none'){				
			$('#'+id).hide();
		}else{		
			$('#'+id).css('top', event.y+document.body.scrollTop + 15);
			$('#'+id).show();			
		}
	}

	function changeDate()
	{
		$('#nowPage').val('1');	//페이지 초기화
		listLoad();
	}
	
	
</script>
</head>
<body leftmargin="6">
<form id="vipMain" name="vipMain">
<input type="hidden" id="nowPage" name="nowPage" value="1">
<input type="hidden" id="sg_seq" name="sg_seq" value="3">
<input type="hidden" id="k_xp" name="k_xp" value="11">
<input type="hidden" id="k_yp" name="k_yp" value="">
<input type="hidden" id="md_seq" name="md_seq" value="">
<input type="hidden" id="md_pseq" name="md_pseq" value="">
<input type="hidden" id="timer" name="timer" value="">
<input type="hidden" id="interval" name="interval" value="">

<table style="width:670px;" border="0" cellpadding="0" cellspacing="0">
	<!-- 탭 시작 -->
	<tr>
		<td>
		<div style="float:left;">		
		<table id="tab" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td id="sitetab0" class="selected" style="width:85px;cursor:pointer" onclick="changeSite('sitetab0','18',5);">기사</td>
				<td id="sitetab1" style="width:85px;cursor:pointer" onclick="changeSite('sitetab1','17',5);">SNS</td>
				<td id="sitetab2" style="width:85px;cursor:pointer" onclick="changeSite('sitetab2','18',5);">블로그</td>
				<td id="sitetab3" style="width:85px;cursor:pointer" onclick="changeSite('sitetab3','25',5);">카페</td>
				<td id="sitetab4" style="width:86px;cursor:pointer" onclick="changeSite('sitetab4','19',5);">커뮤니티</td>
			</tr>
		</table>

		</div>
		<!-- div style="float:right;">
		    <table border="0" cellspacing="0" cellpadding="0">
		      <tr>
				<td width="16"><img src="images/icon_search.gif" width="11" height="12"></td>
		        <td style="padding: 3px 0px 0px 0px;font-size:12px"><strong>검색기간 :</strong></td>
		        <td><input type="text" id="date" name="date" size="10"  maxlength="10" class="calendar" onchange="changeDate();" onFocus="this.select();" onClick="this.select();"
                           value="<%=du.getCurrentDate("yyyy-MM-dd")%>"></td>
			  </tr>      
		    </table>
		</div-->
		
		</td>
	</tr>
	<tr>
		<td id="tab_sub">
				<p><a id="keywordtab0" class="selected" href="javascript:changeKeyword('keywordtab0','',<%=arrKeywordBean.size()+1%>);">전체보기</a></p>
<%
				String className = "";

				for(int i=0;i<arrKeywordBean.size();i++){					
					kBean = new KeywordBean();					
					kBean = (KeywordBean)arrKeywordBean.get(i);
					
					className = "";
					if(i==0)className = "";
%>		
				<p><a id="keywordtab<%=i+1%>" class="<%=className%>" href="javascript:changeKeyword('keywordtab<%=i+1%>','<%=kBean.getKGyp()%>',<%=arrKeywordBean.size()+1%>);"><%=kBean.getKGvalue()%></a></p>
<%
				}
%>
		</td>
	</tr>
	<!--tr>
		<td align="left">
			<table border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td style="padding-top:5px;" id="watch"><strong><font style="font-size:12px">새로고침 중지중..</font></strong></td>
				<td style="padding-top:5px;">		
				<select name="slttimer" class="t" OnChange="setTimer( this.value );" >
		              <option value="0"> :::::중지:::::</option>
		              <option value="5"> 5분마다</option>
		              <option value="10">10분마다</option>
		              <option value="20">20분마다</option>
		              <option value="30">30분마다</option>
		        </select>
				</td>
			</tr>
			</table>
		</td>
	</tr-->	
	<tr>
		<td style="padding-top:5px;">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr><td><img src="images/mid_bg_top.gif" /></td></tr>
			<tr>
				<td id="list" class="mid_bg" align="center">			
			
				
				</td>
			</tr>
			<tr><td><img src="images/mid_bg_btm.gif" /></td></tr>
		</table>
		</td>
	</tr>	
</table>
</form>
</body>
</html>
<%
	}else{
%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head></head>
<body leftmargin="6">
<table width="100%" border=0 cellpadding="0" cellspacing="0">
	<tr height="300" valign="middle"><td align="left"><font style="font-size:14px;font-family:돋움;font-weight:bold">해당 <font color="red">접근 IP</font>로는 사용 할 수 없습니다. 시스템 담당자에게 문의 하십시오.</font></td></tr>	
</table>		
</body>
</html>
<%	
	}
%>