<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList
                 ,risk.util.Log
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.search.MetaMgr
                 ,risk.search.WarningMgr
                 ,risk.search.DomainKeywordMgr
                 ,risk.search.MetaBean"
%>
<%
	//사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.

    StringUtil      su = new StringUtil();
    ParseRequest    pr = new ParseRequest(request);
    pr.printParams();

    String sMtPno    = pr.getString("md_pseq");
    String sMtno     = pr.getString("md_seq");
    String mode      = pr.getString("mode", "");
    String nowpage   = pr.getString("nowpage","1");
    String searchMode = pr.getString("searchMode");
    String subMode = pr.getString("subMode", "");
    
    MetaMgr smgr = new MetaMgr();
    WarningMgr wmgr = new WarningMgr();
    DomainKeywordMgr dkmgr = new DomainKeywordMgr();
    boolean bRtnValue = false;

    ArrayList alData = new ArrayList();
	if(subMode.equals("warning_new")) {
		alData = wmgr.getSameList(sMtPno , sMtno ) ;
	} else if(subMode.equals("domain")) {
		alData = dkmgr.getSameList(sMtPno , sMtno ) ;
	} else {
	    alData = smgr.getSameList(sMtPno , sMtno ) ;
	}
    //ArrayList alData = smgr.getSameList( sMtPno , sMtno ) ;
    
    
    String colspan = "";
    if ( searchMode.equals("ALLKEY") ) {
		colspan = "colspan=\"11\"";
		
    } else if ( searchMode.equals("ALLDB") ) {
    	colspan = "colspan=\"9\"";    	        

    }else if(searchMode.equals("DELIDX")){
    	colspan = "colspan=\"11\"";
    }
%>
<div id="zzFilter">
<%--
--%>
<div style="width:820px;padding-top:3px" align="left">
	<!--<img src="../../images/search/btn_multi.gif" onclick="send_issue('samemulti');" style="cursor:pointer"/>-->
</div>
<table width="820" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">
<!--<table width="1000" border="1" cellspacing="0" cellpadding="0">-->
<!--<col width="5%"><col width="20%"><col width="50%"><col width="5%"><col width="5%"><col width="15%">-->
<!-- <col width="5%"><col width="15%"><col width="51%"><col width="4%"><col width="5%"><col width="5%"><col width="15%"> -->
<col width="3%"><col width="8%"><col width="38%"><col width="2%"><col width="2%"><!-- <col width="7%"> --><!-- <col width="11%"> --><!-- <col width="3%"> --><!-- <col width="12%"> -->
<col width="3%"><col width="4%"><col width="7%">
<%
if(alData.size() > 0){
	
	String star = "";
	 
	for(int i = 0; i < alData.size(); i++){
		MetaBean mBean = (MetaBean) alData.get(i);
		
	   	//내용 강조
	   	String Html = "";	   	
	   	Html=mBean.getHighrightHtml(200,"0000CC");
	   	
	   	//관리버튼
	   	String managerButton = "";
	   	String freeButton = "";
		if(searchMode.equals("ALLKEY")){
			
			if(mBean.getIssue_check()!=null){
				
				
				star = "";
	        	if(mBean.getS_seq().equals("3555") || mBean.getS_seq().equals("4943")
	        	  || mBean.getMd_site_name().contains("네이버카페") || mBean.getMd_site_name().contains("다음카페")
	        	){
	        		star = "<img src='../../images/search/yellow_star.gif' style='cursor:pointer' onclick=portalSearch('"+mBean.getS_seq()+"','"+java.net.URLEncoder.encode(mBean.getMd_title(),"utf-8")+"','"+mBean.getMd_site_name()+"')>";
	        	}
				
				managerButton ="<img id=\"issue_menu_icon2"+mBean.getMd_seq()+"\" width=\"18\" height=\"17\" ";
				if(mBean.getMd_seq().equals(sMtno)){
					managerButton += "src=\"../../images/search/up_btn_on.gif\">";
				}else{
					//managerButton += "src=\"../../images/search/up_btn_off.gif\">";
					
					
					managerButton ="<img id=\"issue_menu_icon"+mBean.getMd_seq()+"\" width=\"18\" height=\"17\" style=\"cursor:hand;\" ";
					if(mBean.getIssue_check().equals("Y")){
						//managerButton += "src=\"../../images/search/btn_manage_on.gif\" title=\"이슈로 등록된 정보입니다.\">";
						managerButton += "src=\"../../images/search/btn_manage_on.gif\" title=\"이슈로 등록된 정보입니다.\" onclick=\"send_issue('update','"+mBean.getMd_seq()+"');\">";
					}else{
						managerButton += "src=\"../../images/search/btn_manage_off.gif\" title=\"이슈 미등록 정보입니다.\" onclick=\"send_issue('insert','"+mBean.getMd_seq()+"');\">";
					}
					
					
				}	
				
				
				freeButton ="<img id=\"issue_menu_icon3"+mBean.getMd_seq()+"\" width=\"18\" height=\"17\" ";
				if(mBean.getMd_seq().equals(sMtno)){
					freeButton += "src=\"../../images/search/free_btn_off.gif\">";
				}else{
					freeButton += "src=\"../../images/search/free_btn.gif\"  onclick=\"listAlter('" + mBean.getMd_seq() + "', '" + sMtPno + "');\" style=\"cursor:hand;\">";	
				}
				
				
				
				
			}
			
		}
		
%>
					<tr bgcolor="F2F7FB">
						<!--↓↓체크박스-->
						<%--
						<td>&nbsp;</td>
						--%>
						<td>
							<%if(mBean.getIssue_check()!=null && !sMtno.equals(mBean.getMd_seq())){ %><input type="checkbox" name="sameChk" value="<%=mBean.getMd_seq()%>"><%}%>
						</td>
						<!--↓↓출처-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><font style="font-size:11px;" color="#225bd1"><%=mBean.getMd_site_name() + "(" + mBean.getSg_name() + ")"%></font></td>
						<!--↓↓제목-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<%
								String isStorage = mBean.getIs_storage();
								if(isStorage.equals("N")) {
									isStorage = "";
								} else {
									isStorage = "<img style='vertical-align: middle;' src='../../images/search/icon_folder.gif' style='cursor:pointer'>";
								}
							%>
							<p class="board_01_tit_0<%=mBean.getMd_type()%>"><a><%=star%></a><a onClick="hrefPop('<%=mBean.getMd_url()%>');" onfocus="this.blur();" href="javascript:void(0);" onmouseover="showSamelist('<%=mBean.getMd_seq()%>')" onmouseout="closeSamelist('<%=i%>')"><%=mBean.getHtmlMd_title()%></a>
								<% 
									String replyCnt =  mBean.getMd_reply_count();
									 if( "0".equals(replyCnt)) {
										replyCnt = "";
									} else {
										replyCnt = "[" + mBean.getMd_reply_count() + "]";
									} 
								%>
								<strong><%=replyCnt%></strong>
							</p>
							<div id=sameContent<%=mBean.getMd_seq()%> style="display:none;width:200px;height:80px;">
								<%=Html%> 
								      
							</div>
						</td>
						<!--↓↓보관홤저장여부버튼-->
						<td>
							<%=isStorage %>
						</td> 
						<!--↓↓원문보기버튼-->
						<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">
							<img src="../../images/search/icon00.png" style="cursor:pointer" onclick="originalView('<%=mBean.getMd_seq()%>');">
						</td>
						<!--↓↓관리버튼-->
						<td><%=managerButton%></td>
						<!--↓↓유사-->
						<td><%=freeButton%></td>
						<!--↓↓수집시간-->
						<td><%=mBean.getFormatMd_date("MM-dd HH:mm")%></td>
					</tr>
					<%--
					<tr>
						<td class="same" colspan="6">
							<div id="SameList_<%=mBean.getMd_pseq()%>" style="display:none; padding:5px; border-top:1px solid #CCCCCC;"></div>
						</td>
					</tr>
					--%>
<%
	}
}
%>
</table>
</div>
<script language="javascript">
<!--	
	parent.fillSameList('<%=sMtPno%>');
//-->
</script>