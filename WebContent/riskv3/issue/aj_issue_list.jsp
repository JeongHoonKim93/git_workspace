<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="../inc/sessioncheck.jsp" %>
<%@ page import = "risk.util.ParseRequest" %>
<%@ page import = "risk.util.DateUtil" %>
<%@ page import = "risk.util.StringUtil" %>
<%@ page import = "risk.util.PageIndex" %>
<%@ page import = "risk.search.userEnvInfo" %>
<%@ page import = "risk.issue.IssueMgr" %>
<%@ page import = "risk.issue.IssueDataBean" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.net.URLDecoder" %>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	
	String sDateFrom = pr.getString("param_sDate");
	String sDateTo = pr.getString("param_eDate");
	String ir_stime = pr.getString("param_sTime", "16");
	String ir_etime = pr.getString("param_eTime", "15");
	
	String typeCodeSeries = pr.getString("typeCodeSelect");
	String cloutSeries = pr.getString("typeCodeClout");
	String sentiSeries = pr.getString("typeCodeSenti");
	String transSeries = pr.getString("typeTransyn");
	String infoSeries = pr.getString("typeCodeInfo");
	String relationKeywordSeries = pr.getString("relationKeyword");
	String register = pr.getString("register");
	String searchKeyword = URLDecoder.decode(pr.getString("param_searchKey"), "UTF-8");
	String searchKeyType = pr.getString("param_keyType");
	String sltSiteGroups = pr.getString("sltSiteGroups","");
	
	int nowpage = pr.getInt("nowpage", 1);
	int pageCnt = 20;
	String sOrder       = pr.getString("sOrder","MD_DATE");
	String sOrderAlign  = pr.getString("sOrderAlign","DESC"); 
	
	//세션정보 가져오기~~
	userEnvInfo uei = null;
	uei = (userEnvInfo) session.getAttribute("ENV");
	
	// 데이터 정렬
	uei.setOrder( sOrder );
	uei.setOrderAlign( sOrderAlign );
	
	/**
	 * 필드별 정렬
	 */
	String sOrderImg    = "";
	String sOrderMark1 = "";
	String sOrderMark2 = "";
	String sOrderMark3 = "";
	String sOrderMark4 = "";
	String sOrderMark5 = "";
	
	if ( uei.getOrderAlign().equals("ASC") ) {
	    sOrderImg = "▲";
	} else if( uei.getOrderAlign().equals("DESC" ) ) {
	    sOrderImg = "▼";
	}  
	
	if(uei.getOrder()!=null){
	    if ( uei.getOrder().equals("MD_SITE_NAME") ) {
	        sOrderMark1 = sOrderImg;
	    } else if ( uei.getOrder().equals("ID_TITLE") )     {
	        sOrderMark2 = sOrderImg;
	    } else if ( uei.getOrder().equals("MD_SAME_CT") )   {
	        sOrderMark3 = sOrderImg;
	    } else if ( uei.getOrder().equals("MD_DATE") )      {
	        sOrderMark4 = sOrderImg;
	    } else if ( uei.getOrder().equals("M_NAME") )      {
	        sOrderMark5 = sOrderImg;
	    }
	}
	
	//검색날짜 설정 : 기본 7일간 검색한다.
	DateUtil du = new DateUtil();
	String sCurrDate    = du.getCurrentDate();
	if (sDateTo.equals("")) sDateTo = du.getCurrentDate();
	if (sDateFrom.equals("")) {
		du.addDay(-1);
		sDateFrom = du.getDate();
	}	
	
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
	
	IssueMgr issueMgr = new IssueMgr();
	ArrayList issueDataList = issueMgr.searchIssueData(nowpage, pageCnt, sDateFrom, setStime, sDateTo, setEtime, typeCodeSeries, cloutSeries, sentiSeries, infoSeries, relationKeywordSeries
			, searchKeyType, searchKeyword, register, uei.getOrder(), uei.getOrderAlign(), sltSiteGroups, transSeries);
	
	// COUNT 및 Paging
	int totalCnt =  issueMgr.getTotalIssueDataCnt();
	int sameCnt = issueMgr.getTotalSameDataCnt();
	int totalPage = totalCnt / pageCnt;
	if(totalCnt % pageCnt > 0){
		totalPage += 1;
	}
	
	String pageHtml = PageIndex.getPageIndex(nowpage, totalPage,"","" ).replaceAll("'", "\\\\'");
	
	boolean ipChk = false;
	//원문보기 권한 부여
	if(SS_M_ORGVIEW_USEYN.equals("Y")){
		ipChk = true;
	} 
	
	// 사이트그룹(분류체계 기반)
	//ArrayList arrSiteGroup = issueMgr.getSiteGroup_order();
	ArrayList arrSiteGroup = issueMgr.getSiteGroup_order3();
	
	String srtMsg = "<img src=\"../../images/search/icon_search_bullet.gif\">  유사 포함 : <b>"+sameCnt+" 건</b>,  유사 미포함 : <b>"+totalCnt+" 건</b>,  "+nowpage+"/"+totalPage+" pages";
%>
<colgroup>
	<col width="5%"><col width="14%"><col width= "40%"><col width="5%"><col width="5%"><col width="5%"><col width="5%"><col width="13%"><col width="7%"><!-- <col width="11%"> --><col width="10%">
</colgroup>
<tr>
	<th><input type="checkbox" name="id_seqs_all" id="id_seqs_all" onclick="checkSeq(this, 2);" value=""></th>
	<th style="cursor:pointer" onclick="setOrder('MD_SITE_NAME');">출처<%=sOrderMark1%></th>
	<th style="cursor:pointer" onclick="setOrder('ID_TITLE');">제목<%=sOrderMark2%></th>
	<th> </th>
	<th> </th>
	<th style="cursor:pointer" onclick="setOrder('MD_SAME_CT');" title="">유사<%=sOrderMark3%></th>
	<th>성향</th>
	<th style="cursor:pointer" onclick="setOrder('MD_DATE');">수집일시<%=sOrderMark4%></th>
	<th>긴급<br>보고서</th>
	<th style="cursor:pointer" onclick="setOrder('M_NAME');">등록자<%=sOrderMark5%></th>
	<!-- <th>중요도</th> -->
</tr>
<%
	IssueDataBean idBean = null;
	int SitesValue_sum = 0; //2020.08 수정
	for(int i=0; i  < issueDataList.size(); i++){
		idBean = (IssueDataBean)issueDataList.get(i);
		
		// 사이트별 유사 건수
		String strSites = "";
		String strSitesValue = "";
		SitesValue_sum = 0; //2020.08 수정
		String[] ar_SiteCnt = idBean.getMd_same_ct().split(",");
		String[] ar_SiteCnt_check = idBean.getMd_same_ct_check().split(",");
		String siteCheck = "";
		
		if(arrSiteGroup.size() == ar_SiteCnt.length){
			for(int j = 0; j < arrSiteGroup.size(); j++){
				
				if(ar_SiteCnt_check[j].equals("0")){
		 			siteCheck = ar_SiteCnt[j];
 				}else{
 					siteCheck = "<font color='#F41B2F'/>"+ar_SiteCnt[j]+"</font>";
 				}
				SitesValue_sum += Integer.parseInt(ar_SiteCnt[j]);
				 if(strSites.equals("")){
		 			strSites = ((String[])arrSiteGroup.get(j))[1] + " : " +  ar_SiteCnt[j];
		 			/* if(ar_SiteCnt[j].equals("0")){
		 				strSitesValue = ar_SiteCnt[j];
		 			}else{
		 				strSitesValue = "<a href=\"javascript:getSourceData('"+ idBean.getMd_pseq() +"','"+((String[])arrSiteGroup.get(j))[0]+"','"+ ((String[])arrSiteGroup.get(j))[1] +"')\">"+ siteCheck + "</a>";
		 			}  */
		 			
		 		}else{
		 			strSites += ", " + ((String[])arrSiteGroup.get(j))[1] + " : " +  ar_SiteCnt[j];
		 			/* if(ar_SiteCnt[j].equals("0")){
		 				strSitesValue += "," + ar_SiteCnt[j];
		 			}else{
		 				strSitesValue += "," + "<a href=\"javascript:getSourceData('"+ idBean.getMd_pseq() +"','"+((String[])arrSiteGroup.get(j))[0]+"','"+ ((String[])arrSiteGroup.get(j))[1] +"')\">" + siteCheck +"</a>";
		 			} */
		 		} 
			}
		
			strSitesValue = "<a href=\"javascript:getSourceData('"+ idBean.getMd_pseq() +"')\">"+ SitesValue_sum + "</a>"; 
		}
		
		
		String linkIssueReport = "";
		String linkOnlineIssueReport = "";
		
		if(idBean.getTemp1().equals("1")){
			linkIssueReport = "icon_issue_report_download_on.gif";
		}else{
			linkIssueReport = "icon_issue_report_download_off.gif";
		}
		if(idBean.getTemp2().equals("1")){
			linkOnlineIssueReport = "icon_online_issue_report_download_on.gif";
		}else{
			linkOnlineIssueReport = "icon_online_issue_report_download_off.gif";
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
<tr>
	<td><input name="id_seqs" type="checkbox" value="<%=idBean.getId_seq()%>" onclick="checkSeq(this)"/></td>
	<td><span style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board01_category" title="<%=idBean.getMd_site_name()%>"><%=idBean.getMd_site_name()%></span></td>
	<td style="text-align: left;"><div style="width:310px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" class="board_01_tit_0<%=idBean.getMd_type()%>" title="<%=idBean.getId_title()%>">
		<a href="javascript:PopIssueDataForm('<%=idBean.getMd_seq() %>','Y');"><%=idBean.getId_title()%></a>
	</div></td>
	<%-- <td><%if(ipChk){%><a onClick="originalView('<%=idBean.getId_seq()%>');" href="javascript:void(0);" ><img src="../../images/issue/icon00.png" id="img_future<%=i %>" align="absmiddle" /></a><%}%></td> --%>
	 <td id="checkImg"><%if(idBean.getId_trans_useyn() != null && idBean.getId_trans_useyn().equals("Y")){%><img src="../../images/issue/icon_c.gif"><%}%></td> 
	<td><a onClick="hrefPop('<%=idBean.getId_url()%>');" href="javascript:void(0);" ><img src="../../images/issue/ico_original.gif" /></a></td>
	<td title="<%=strSites%>"> <%if(0==SitesValue_sum){out.println(SitesValue_sum);}else{out.println(strSitesValue);}%> </td>
	<td><%=senti%></td>
	<td><%=idBean.getFormatMd_date("yyyy-MM-dd")%></td>
	<td >
	<%
		if(idBean.getTemp1().equals("1")){
	%>
		<img src="../../images/issue/icon_issue_report_download_on.gif" title="이미 긴급 보고서에 저장되었습니다."/>
	
	<%		
		}else {
	%>
		<a onClick="report_save('U','<%=idBean.getId_seq()%>','<%=idBean.getId_title_replace()%>');" title="긴급 보고서 저장" style="cursor:pointer;">
			<img src="../../images/issue/icon_issue_report_download_off.gif" />
		</a>
	<% 
		}
	%>
	</td>
	<%-- <td><img src="../../images/issue/ico_Telegram.gif" title="텔레그램 전송" style="cursor:pointer;" onclick="pop_send_telegram('<%=idBean.getId_seq()%>');"></td> --%>
	<td><%=idBean.getM_name() %></td>
</tr>
<tr>
	<td class="same" colspan="10">
		<div id="SameList_<%=idBean.getMd_pseq()%>" style="display:none;"></div>
	</td>
</tr>
<%
	}
%>

<script type="text/javascript">
	$("#count_td").html('<%=srtMsg%>');
	$("#paging tr:first").html('<%=pageHtml%>');
	$("#nowpage").val('<%=nowpage%>');
	
	function checkSeq(obj, mode){
		
		var check_status;
		if($(obj).attr("id") == "id_seqs_all"){
			
			if($(obj).attr("checked") == true){
				check_status = true;
			} else {
				check_status = false;
			}
			
			// 전체선택 버튼
			if(mode == 1){
				check_status = !check_status;
				$(obj).attr("checked", check_status);
			}
			
			$("[name=id_seqs]").each(function(){
				$(this).attr("checked", check_status);
			});
		} else {
			if($(obj).attr("checked") == false){
				$("#id_seqs_all").attr("checked", false);
			} else {
				check_status = false;
				$("[name=id_seqs]").each(function(){
					if($(this).attr("checked") == false){
						check_status = false;
						return false;
					} else {
						check_status = true;
					}
				});
				
				$("#id_seqs_all").attr("checked", check_status);
			}
		}
	}

</script>