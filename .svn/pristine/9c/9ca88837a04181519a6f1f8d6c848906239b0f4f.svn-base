<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import="java.util.ArrayList
                ,risk.util.ParseRequest
                ,risk.util.StringUtil
                ,risk.util.DateUtil
                ,risk.util.PageIndex
                ,risk.voc.VocDataMgr
				,risk.voc.VocBean
				" %>
<%
	ParseRequest pr	= new ParseRequest(request);
	pr.printParams();	
	DateUtil du	= new DateUtil();
	StringUtil	su 	= new StringUtil();
	VocDataMgr vMgr = new VocDataMgr();
	
	int nowpage = 1;	
	int pageCnt = 20;
	int totalCnt = 0;
	int totalPage = 0;
	
	nowpage = pr.getInt("nowpage",1);
	
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String ir_stime = pr.getString("ir_stime","16");
	String ir_etime = pr.getString("ir_etime","16");
	
	String state = pr.getString("state");
	String company = pr.getString("company");
	
	// 리스트부
	ArrayList issue_list = vMgr.getVocList(nowpage, pageCnt, sDateFrom, sDateTo, ir_stime+":00:00", ir_etime+":00:00", "", "", "", "", "",company,state);
	
	//페이징 처리
	totalCnt = vMgr.getTotalCnt();	
	if (totalCnt>0) {
		totalPage = totalCnt / pageCnt;
		if ((totalCnt % pageCnt) > 0 ) {
			totalPage++;
		}
	}
	
	String strMsg = "총 건수 : "+totalCnt+" 건, "+nowpage+"/"+totalPage+" pages";
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RISK</title>
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
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
       	
        f.action = 'pop_voc_data_list.jsp' + paramUrl;
        f.target='';
        f.submit();	        
	}

	//VOC 팝업
 	function PopVocForm_app(v_seq){
 		var f = document.fSend;
 		f.v_seq.value = v_seq;
 		
 		popup.openByPost('fSend','pop_voc_pro_data_form.jsp',718,700,false,true,false,'send_issue');
 	
 	}

-->
</script>
</head>
<body>
<form name="fSend" id="fSend" action="" method="post">
<input type="hidden" name="nowpage" value="<%=nowpage%>">
<input type="hidden" name="v_seq" value="">

<table width="800" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="43" background="../../images/dashboard/pop_bg.gif"><table width="780" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="145"><img src="../../images/voc/pop_title_033.gif"></td>
        <td align="right" class="white_b"><strong><%=strMsg%></strong></td>
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
		<th>회사</th>
		<th>상태</th>
	</tr>
<%
		VocBean.VocMBean idBean = null;
		String colDate ="";
		String reDate ="";
		if(issue_list.size() > 0){
			for(int i = 0; i < issue_list.size(); i++){
				idBean = (VocBean.VocMBean)issue_list.get(i);
				
				colDate = ""+idBean.getMd_date().substring(5,10)+" "+idBean.getMd_date().substring(11,16);
				reDate = ""+idBean.getI_regdate().substring(5,10)+" "+idBean.getI_regdate().substring(11,16);
				
%>
	<tr>
		<td><input type="checkbox" name="issueCheck" value="<%=idBean.getV_seq()%>" onclick=""></td>
		<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board01_category" title="<%=idBean.getMd_site_name()%>"><%=idBean.getMd_site_name()%></p></td>
		<td><p style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board_01_tit_01" title="<%=idBean.getMd_title()%>"><a href="javascript:PopVocForm_app('<%=idBean.getV_seq()%>');"><%=idBean.getMd_title()%></a></p></td>
		<td><%=reDate%></td>
		<td><%=idBean.getCategory()%></td>
		<td><strong><%=idBean.getStatus_name()%></strong></td>
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