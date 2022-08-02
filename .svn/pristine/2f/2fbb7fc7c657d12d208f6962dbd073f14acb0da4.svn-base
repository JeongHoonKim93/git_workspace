<%/*******************************************************
*  1. 분    류    명 : 정보검색 메인
*  2. 업무 시스템 명 : 정보검색
*  3. 프로그램  개요 : 사용자 환경설정
*  4. 관 련  Table명 :
*  5. 작    성    자 :
*  6. 작    성    일 : 2006.4.13
*  7. 주  의  사  항 :
*  8. 변   경   자   :
*  9. 변 경  일 자   :
* 10. 변 경  사 유   :
********************************************************/%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList,
                 risk.util.Log,
                 risk.util.StringUtil,
                 risk.util.ParseRequest,
                 risk.util.DateUtil,
                 risk.search.userEnvMgr,
                 risk.search.userEnvInfo,
                 risk.search.keywordInfo,
                 risk.search.siteGroupInfo,
                 risk.search.siteDataInfo,risk.search.MetaMgr"
%>
<%@include file="../inc/sessioncheck.jsp" %>
<%
	ArrayList arrList = null;

    userEnvMgr uem = new userEnvMgr();
    ParseRequest    pr = new ParseRequest(request);
    StringUtil      su = new StringUtil();


    String sRtnMsg  = "";


    //System.out.println( "pr.getString(JOB)      : " + pr.getString("JOB")     );
    if ( pr.getString("JOB").equals("SAVE") ) {

        String sK_Xp        = pr.getString("rdoKeyGroup");
        String sSt_interval_day    = pr.getString("sltPeriod");
        String sMd_type      = pr.getString("type");
        String sSg_seq      = pr.getString("sites");
        String sSg_seq_al      = pr.getString("solrSites");

        String sSt_reload_time   = pr.getString("sltRefresh","0");
        String sSt_list_cnt   = pr.getString("sltRowCnt");
        String sSt_menu      = pr.getString("rdoMenu");
        String sM_Seq       = SS_M_NO;
        
        if ( uem.saveUserEnv( sK_Xp       ,
                sSt_interval_day   ,
                sMd_type     ,
                sSg_seq     ,
                sSg_seq_al,
                sSt_reload_time  ,
                sSt_list_cnt  ,
                sSt_menu     ,
                sM_Seq )
)
        {
            //저장 성공이면 세션에있는 사용자 환경도 변경해준다.
            userEnvInfo ueiSession  = (userEnvInfo) session.getAttribute("ENV");

            ueiSession.setK_xp	     ( sK_Xp       );
            ueiSession.setSt_interval_day ( sSt_interval_day   );
            ueiSession.setMd_type    ( sMd_type     );
            ueiSession.setSg_seq	 ( sSg_seq     );
            ueiSession.setSg_seq_al	 ( sSg_seq_al     );
            ueiSession.setSt_reload_time( sSt_reload_time  );
            ueiSession.setSt_list_cnt ( sSt_list_cnt  );
            ueiSession.setSt_menu    ( sSt_menu     );

            session.removeAttribute("ENV");
            session.setAttribute("ENV",ueiSession);
            
            sRtnMsg = "top.bottomFrame.leftFrame.document.location.href=\"search_main_left.jsp\"; document.location.href = \"search_main_contents.jsp\";";
            out.print("<script language=\"JavaScript\" type=\"text/JavaScript\">"+sRtnMsg+"</script>");
        } else {
            sRtnMsg = "alert(\"저장 실패 하였습니다.\");";  
        }

    } else if ( pr.getString("JOB").equals("CURSAVE") ) {

        //저장 성공이면 세션에있는 사용자 환경도 변경해준다.
        userEnvInfo ueiSession  = (userEnvInfo) session.getAttribute("ENV");

        if ( uem.saveUserEnv( su.nvl(ueiSession.getK_xp(),"0")      ,
                              ueiSession.getSt_interval_day  ()            ,
                              ueiSession.getMd_type    ()            ,
                              su.nvl(ueiSession.getSg_seq(),"0" )   ,
                              su.nvl(ueiSession.getSg_seq_al(),"0" )   ,
                              ueiSession.getSt_reload_time ()            ,
                              ueiSession.getSt_list_cnt ()            ,
                              ueiSession.getSt_menu    ()            ,
                              SS_M_NO
                              )
           )
        {
            sRtnMsg = "alert(\"현재의 검색조건을 기본조건으로 저장하였습니다.\");";
        } else {
            sRtnMsg = "alert(\"저장 실패 하였습니다.\");";
        }
    }

    //사용자 기본 환경을 조회한다.
    //없으면  NCS기본 유저의 환경을 조회하여 가져온다.

    userEnvInfo uei = null;

    uei = uem.getUserEnv( SS_M_ID );

    arrList = uem.getKeywordGroup( uei.getMg_xp() );

    String sType1       = "";
    String sType2       = "";
    String sType3       = "";
    String sTypeAll     = "";

    //Uiix의 파일권한과 동일한 개념으로 간다.
    //기사 1. 게시 2. 공지 4.
    if          ( uei.getMd_type().equals("1") ) {
        sType1  = "checked";
    } else if   ( uei.getMd_type().equals("2") ) {
        sType2  = "checked";
    } else if   ( uei.getMd_type().equals("4") ) {
        sType3  = "checked";
    } else if   ( uei.getMd_type().equals("3") ) {
        sType1  = "checked";
        sType2  = "checked";
    } else if   ( uei.getMd_type().equals("5") ) {
        sType1  = "checked";
        sType3  = "checked";
    } else if   ( uei.getMd_type().equals("6") ) {
        sType2  = "checked";
        sType3  = "checked";
    } else if   ( uei.getMd_type().equals("7") ) {
        sTypeAll= "checked";
    }

    if(sTypeAll.equals("checked")){
    	sType1  = "checked";
    	sType2  = "checked";
    	sType3  = "checked";
    }


    siteDataInfo   sdi = null;
    siteGroupInfo  sgi = null;
    MetaMgr     smgr = new MetaMgr();
	
    System.out.println("uei.getMg_site():"+uei.getMg_site());
    
    ArrayList alGroup = smgr.getSiteGroup( uei.getMg_site() );

    //그룹에 등록된 사이트 리스트
    ArrayList alSite = smgr.getSiteData(uei.getSg_seq());
    
%>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../../css/base.css" />
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--


    function MM_preloadImages() { //v3.0
      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }

    function checkAll(chk) {
        var f = document.fSend;
        f.chkGisa.checked   = chk;
        f.chkGesi.checked   = chk;
        f.chkGongji.checked = chk;
    }

    function checkEtc(chk) {

        var f = document.fSend;
        var chk_flg = true;
        
        if(f.chkGisa.checked == false){
        	chk_flg = false;
        }
        if(f.chkGesi.checked == false){
        	chk_flg = false;
        }
        if(f.chkGongji.checked == false){
        	chk_flg = false;
        }    
        
        if(chk_flg){
        	f.chkAll.checked = true;
	    }
        
        if(chk == false){
        	f.chkAll.checked = false;
        }

    }

    function save() {

        f = document.fSend;

        var typeValue   = 0;

        if ( f.chkGisa.checked == false &&
             f.chkGesi.checked == false &&
             f.chkGongji.checked == false &&
             f.chkAll.checked == false  ) {

             alert("정보유형을 선택하여 주십시오");
             return;
        }

        f.JOB.value = "SAVE";

        if ( f.chkGisa.checked == true      ) typeValue += 1;
        if ( f.chkGesi.checked == true      ) typeValue += 2;
        if ( f.chkGongji.checked == true    ) typeValue += 4;
        if ( f.chkAll.checked == true    )    typeValue = 7;

        f.type.value = typeValue;

        /* 검색 대상 - 키워드 검색 */
        var group = document.all.chkSite;
        var strGroup = "";
        			        
		if(f.chkSiteAll.checked == true){
			strGroup = f.chkSiteAll.value;	
		}else{
			strGroup = "";
			if(group){
				for(var i=0; i<group.length; i++){
					if(group[i].checked){
						strGroup += "," + group[i].value;
					}
				}
			}
			
			if(strGroup.length > 0){
				strGroup = strGroup.substring(1);
			} else {
				alert("검색대상을 선택하여 주십시오");
				return;
			}
		}
		f.sites.value = strGroup;


		/* 검색 대상  - 전체 검색*/
		group = null;
        group = document.all.chkSiteSolr;
        strGroup = "";
        			        
		if(f.chkSiteSolrAll.checked == true){
			strGroup = f.chkSiteSolrAll.value;	
		}else{
			strGroup = "";
			if(group){
				for(i=0; i<group.length; i++){
					if(group[i].checked){
						strGroup += "," + group[i].value;
					}
				}
			}
			
			if(strGroup.length > 0){
				strGroup = strGroup.substring(1);
			} else {
				alert("검색대상을 선택하여 주십시오");
				return;
			}
		}
		f.solrSites.value = strGroup;
		
		f.submit();
        

    }

    function cancel() {
        document.location.href = "search_main_contents.jsp";
    }

    function siteCheckAll(chk) {
		var group = document.getElementsByName('chkSite');
		if(group){
			if(group.length){
				for(var i = 0; i < group.length; i++){
					group[i].checked = chk;
				}
			}
		} 
    }
    function siteSolrCheckAll(chk) {
		var group = document.getElementsByName('chkSiteSolr');
		if(group){
			if(group.length){
				for(var i = 0; i < group.length; i++){
					group[i].checked = chk;
				}
			}
		} 
    	        
    }
    function siteCheck(checked) {        
    	var frm = document.fSend;
	   	var chk_flg = true;
	   	
	       for (var i = 0; i < frm.elements.length; i++) {
	           var e = frm.elements[i];
	           if ( e.name == "chkSite" && e.checked ==false ) {
	              chk_flg = false;
	           }
	       }
	   	
	       if(chk_flg){
	       	 document.fSend.chkSiteAll.checked = true;
	       }

	   	if(checked==false){
	            document.fSend.chkSiteAll.checked = false;
	       }            
    }
    function siteSolrCheck(checked) {        
		
    	var frm = document.fSend;
	   	var chk_flg = true;
	   	
	       for (var i = 0; i < frm.elements.length; i++) {
	           var e = frm.elements[i];
	           if ( e.name == "chkSiteSolr" && e.checked ==false ) {
	              chk_flg = false;
	           }
	       }
	   	
	       if(chk_flg){
	       	 document.fSend.chkSiteSolrAll.checked = true;
	       }

	   	if(checked==false){
	            document.fSend.chkSiteSolrAll.checked = false;
	       }       	
		
    }
    
    function showMsg() {

        if ( '<%=sRtnMsg%>' != '' ) {
        	<%=sRtnMsg%>
        }
    }

    

    



//-->
</script>

<body style="margin-left: 15px" onLoad="javascript:showMsg();" >
<form name="fSend" action="search_env_setting.jsp" method="post" >
    <input type="hidden" name="type" value="" >
    <input type="hidden" name="JOB"  value="" >
    <input type="hidden" name="sites" value="">
    <input type="hidden" name="solrSites" value="">     
<table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
			<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../images/search/tit_icon.gif" /><img src="../../images/search/tit_0001.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow2">환경설정</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="15"></td>
			</tr>
			<!-- 타이틀 끝 -->
			<!-- 검색 시작 -->
			<tr>
				<td>
				<table width="770" border="0" cellspacing="0" cellpadding="0">
        <tr>
          
          <td align="right" style="padding-left:10px"><table width="750" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="40" align="left"> 주로 사용하시는 검색조건을 저장해 두시면 , 로그인 하실 때마다 기본설정으로 적용됩니다 . &#13;&#13;</td>
            </tr>
          </table>
            <table width="750"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="25" align="left" class="menu_black" ><img src="../../images/search/search_dot01.gif" width="9" height="9" hspace="2"><strong>키워드그룹</strong></td>
              </tr>
              <tr>
                <td align="left" valign="top" style="padding-left:15px" >
                  <table width="530" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td style="padding: 5px 5px 5px 5px;" style="border:1px solid; border-color:#cccccc" >
                        <table width="500" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="15" height="20"><img src="../../images/search/search_folder.gif" width="12" height="10" align="absmiddle"></td>
                            <td width="15"><input type="radio" name="rdoKeyGroup" value="0" checked ></td>
                            <td style="padding: 2px 0px 0px 0px;">전체키워드 그룹</td>
                          </tr>
                          <%
                            for (int i = 0; i < arrList.size() ; i ++ ) {
                                keywordInfo kwi = (keywordInfo)arrList.get(i);
                          %>
                          <tr>
                            <td width="15" height="20"><img src="../../images/search/search_folder.gif" width="12" height="10" align="absmiddle"></td>
                            <td width="15">
                              <input type="radio" name="rdoKeyGroup" value="<%=kwi.getK_Xp()%>" <% if ( kwi.getK_Xp().equals( uei.getK_xp() )  ) out.print("checked");  %>  >
                            </td>
                            <td style="padding: 2px 0px 0px 0px;"><%=kwi.getK_Value()%></td>
                          </tr>
                          <%
                            }
                          %>
                        </table>
                      </td>
                    </tr>
                  </table>
                <!--
                <select name="slt_grouplist">
                <option value=""></option>
                </select>
                -->
                <!--
                <iframe src="ifram_search02.htm" width="550" height="90" scrolling="auto" frameborder="0" style="border:1px solid; border-color:#cccccc"></iframe>
                -->
                </td>
              </tr>
 			 <tr>
			    <td colspan="3"><img src="../../images/search/brank.gif" width="1" height="10"></td>
 			 </tr>
 			 <tr>
			    <td colspan="3" background="../../images/search/pop_mailt_dotbg.gif"><img src="../../images/search/brank.gif" width="1" height="1"></td>
 			 </tr>
            </table>
            <table width="750"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="25" align="left" class="menu_black" ><img src="../../images/search/search_dot01.gif" width="9" height="9" hspace="2"><strong>검색기간</strong></td>
              </tr>
              <tr>
                <td align="left" valign="top" style="padding-left:15px;vertical-align: middle" > 최근
                <% System.out.println("uei.getSt_interval_day() : "  + uei.getSt_interval_day() ); %>
                  <select name="sltPeriod" class="menu_gray">
                    <option value="1" <% if ( uei.getSt_interval_day().equals("1") ) out.print("selected"); %> >1일</option>
                    <option value="3" <% if ( uei.getSt_interval_day().equals("3") ) out.print("selected"); %> >3일</option>
                    <option value="7" <% if ( uei.getSt_interval_day().equals("7") ) out.print("selected"); %> >7일</option>
                    </select>
                    일간의 정보 중에서 검색합니다 .</td>
              </tr>
              <tr>
                <td colspan="3"><img src="../../images/search/brank.gif" width="1" height="10"></td>
              </tr>
              <tr>
                <td colspan="3" background="../../images/search/pop_mailt_dotbg.gif"><img src="../../images/search/brank.gif" width="1" height="1"></td>
              </tr>
            </table>
            <table width="750"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="25" align="left" class="menu_black" ><img src="../../images/search/search_dot01.gif" width="9" height="9" hspace="2"><strong>정보유형</strong></td>
              </tr>
              <tr>
                <td align="left" valign="top" style="padding-left:15px; vertical-align: middle;">
                <input type="checkbox" name="chkAll" value="checkbox" OnClick="javascript:checkAll(this.checked);" defaultChecked  <%=sTypeAll%>  >
                <strong>전체</strong>
                <input type="checkbox" name="chkGisa" value="checkbox" OnClick="javascript:checkEtc(this.checked);" <%=sType1%>  >
                	기사
                <input type="checkbox" name="chkGesi" value="checkbox" OnClick="javascript:checkEtc(this.checked);" <%=sType2%> >
                	게시
                <input type="checkbox" name="chkGongji" value="checkbox" OnClick="javascript:checkEtc(this.checked);" <%=sType3%> >
                	공지</td>
              </tr>
              <tr>
                <td colspan="3"><img src="../../images/search/brank.gif" width="1" height="10"></td>
              </tr>
              <tr>
                <td colspan="3" background="../../images/search/pop_mailt_dotbg.gif"><img src="../../images/search/brank.gif" width="1" height="1"></td>
              </tr>
            </table>
            
            
            
		
            <table width="750"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="25" align="left" class="menu_black" ><img src="../../images/search/search_dot01.gif" width="9" height="9" hspace="2"><strong>검색대상 - 전체검색</strong></td>
              </tr>
              <tr>
                <td align="left" valign="top" style="padding-left:15px; vertical-align: middle;">
                <%
                
                String allCkeck  ="";
                String[] arrSiteAll = new String[6];
                String allvalue = "1,2,3,4,5,6";
                String[] piece = allvalue.split(",");
               	for(int i = 0; i < arrSiteAll.length; i++){
            		arrSiteAll[i] = "";
            	}
                
                if(uei.getSg_seq_al().equals("0") || uei.getSg_seq_al().equals("") || uei.getSg_seq_al().equals(allvalue)){ 	
                	allCkeck ="checked";
                	for(int i = 0; i < arrSiteAll.length; i++){
                		arrSiteAll[i] = "checked";
                	}
                }else{
                	
                	String[] arrSgSeq = uei.getSg_seq_al().split(",");
                	System.out.println("sg_seq_al: "+uei.getSg_seq_al().split(","));
                	if( arrSgSeq.length > 0){
                		for(int i = 0 ; i < arrSgSeq.length; i++){
                			arrSiteAll[Integer.parseInt(arrSgSeq[i]) - 1] = "checked";
                		}
                	}
                }
         
                
                %>
                	<input type="checkbox" name="chkSiteSolrAll" value="<%=allvalue%>" onclick="siteSolrCheckAll(this.checked);" <%=allCkeck%>><strong>전체</strong>
                	<input type="checkbox" name="chkSiteSolr" value="<%=piece[1 - 1]%>" onclick="siteSolrCheck(this.checked);" <%=arrSiteAll[1 - 1]%>>언론
                	<input type="checkbox" name="chkSiteSolr" value="<%=piece[2 - 1]%>" onclick="siteSolrCheck(this.checked);" <%=arrSiteAll[2 - 1]%>>커뮤니티
                	<input type="checkbox" name="chkSiteSolr" value="<%=piece[3 - 1]%>" onclick="siteSolrCheck(this.checked);" <%=arrSiteAll[3 - 1]%>>블로그
                	<input type="checkbox" name="chkSiteSolr" value="<%=piece[4 - 1]%>" onclick="siteSolrCheck(this.checked);" <%=arrSiteAll[4 - 1]%>>카페
                	<input type="checkbox" name="chkSiteSolr" value="<%=piece[5 - 1]%>" onclick="siteSolrCheck(this.checked);" <%=arrSiteAll[5 - 1]%>>트위터
                	<input type="checkbox" name="chkSiteSolr" value="<%=piece[6 - 1]%>" onclick="siteSolrCheck(this.checked);" <%=arrSiteAll[6 - 1]%>>기타  
                  
                </td>
              </tr>
              <tr>
                <td colspan="3"><img src="../../images/search/brank.gif" width="1" height="10"></td>
              </tr>
              <tr>
                <td colspan="3" background="../../images/search/pop_mailt_dotbg.gif"><img src="../../images/search/brank.gif" width="1" height="1"></td>
              </tr>
            </table> 
            	
            
            
            
            <table width="750"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="25" align="left" class="menu_black" ><img src="../../images/search/search_dot01.gif" width="9" height="9" hspace="2"><strong>검색대상 - 키워드검색</strong></td>
              </tr>
              <tr>
                <td align="left" valign="top" style="padding-left:15px; vertical-align: middle;">
                
             
              	<%
              	String[] arrSgSeq = uei.getSg_seq().split(",");
              	String allChecked = "";
              	
              	if(arrSgSeq.length == alGroup.size()){
              		allChecked = "checked";
              	}
              	
        		String allValue="";
              	for(int i = 0; i < alGroup.size(); i++){
              		sgi = (siteGroupInfo)alGroup.get(i);
              		allValue += ","+ Integer.toString(sgi.getSg_seq());
              	}
              	if(allValue.length() > 0){
              		allValue = allValue.substring(1);
              	}
              	%>
              	<input type="checkbox" id="chkSiteAll" name="chkSiteAll" value="<%=allValue%>" onclick="siteCheckAll(this.checked);" <%=allChecked%>><strong>전체</strong>
              	<%
              	
          		for(int i = 0; i < alGroup.size(); i++){
          			String sSelected = "";
                    sgi = (siteGroupInfo)alGroup.get(i);
                    
                    if(!allChecked.equals("checked")){
	                    for(int j= 0; j < arrSgSeq.length; j++){
	                    	if(Integer.toString(sgi.getSg_seq()).equals(arrSgSeq[j])){
	                    		sSelected = "checked";
	                    	}
	                    }
                    }else{
                    	sSelected = "checked";
                    }
              	%>
           		<input type="checkbox" id="chkSite" name="chkSite" onclick="siteCheck(this.checked);" value="<%=sgi.getSg_seq()%>" <%=sSelected%>><%=sgi.getSd_name()%>
           		<% } %>
                  
                </td>
              </tr>
              <tr>
                <td colspan="3"><img src="../../images/search/brank.gif" width="1" height="10"></td>
              </tr>
              <tr>
                <td colspan="3" background="../../images/search/pop_mailt_dotbg.gif"><img src="../../images/search/brank.gif" width="1" height="1"></td>
              </tr>
            </table>
            <table width="750"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="25" align="left" class="menu_black" ><img src="../../images/search/search_dot01.gif" width="9" height="9" hspace="2"><strong>자동 새로고침</strong></td>
              </tr>
              <tr>
                <td align="left" valign="top" style="padding-left:15px">
                  <select name="sltRefresh" class="menu_gray">
                    <option value="0" >사용안함</option>
                    <option value="5"  <% if ( uei.getSt_reload_time().equals("5" ) ) out.print("selected"); %> >5분</option>
                    <option value="10" <% if ( uei.getSt_reload_time().equals("10") ) out.print("selected"); %> >10분</option>
                    <option value="20" <% if ( uei.getSt_reload_time().equals("20") ) out.print("selected"); %> >20분</option>
                    <option value="30" <% if ( uei.getSt_reload_time().equals("30") ) out.print("selected"); %> >30분</option>
                  </select>
                  <!--
                  <img src="../../images/search/search_reset.gif" width="59" height="18" align="absmiddle">
                  -->
                </td>
              </tr>
              <tr>
                <td colspan="3"><img src="../../images/search/brank.gif" width="1" height="10"></td>
              </tr>
              <tr>
                <td colspan="3" background="../../images/search/pop_mailt_dotbg.gif"><img src="../../images/search/brank.gif" width="1" height="1"></td>
              </tr>
            </table>
            <table width="750"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="32" align="left" class="menu_black" ><img src="../../images/search/search_dot01.gif" width="9" height="9" hspace="2"><strong>화면에 출력할 기사 수 :
                    <select name="sltRowCnt" class="menu_gray">

                    <option <% if ( uei.getSt_list_cnt().equals("10") ) out.print("selected"); %> >10</option>
                    <option <% if ( uei.getSt_list_cnt().equals("20") ) out.print("selected"); %> >20</option>
                    <option <% if ( uei.getSt_list_cnt().equals("30") ) out.print("selected"); %> >30</option>
                    <option <% if ( uei.getSt_list_cnt().equals("40") ) out.print("selected"); %> >40</option>
                    <option <% if ( uei.getSt_list_cnt().equals("50") ) out.print("selected"); %> >50</option>
                    <option <% if ( uei.getSt_list_cnt().equals("100") ) out.print("selected"); %> >100</option>
                    <option <% if ( uei.getSt_list_cnt().equals("200") ) out.print("selected"); %> >200</option>
                    <option <% if ( uei.getSt_list_cnt().equals("300") ) out.print("selected"); %> >300</option>
                    <option <% if ( uei.getSt_list_cnt().equals("400") ) out.print("selected"); %> >400</option>
                    <option <% if ( uei.getSt_list_cnt().equals("500") ) out.print("selected"); %> >500</option>

                  </select>
                </strong></td>
              </tr>
              <tr>
                <td colspan="3" background="../../images/search/pop_mailt_dotbg.gif"><img src="../../images/search/brank.gif" width="1" height="1"></td>
              </tr>
            </table>
            <table width="750"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="32" align="left" class="menu_black" ><img src="../../images/search/search_dot01.gif" width="9" height="9" hspace="2"><strong>결과내 검색 메뉴 보이기</strong> :
                  <input name="rdoMenu" type="radio" value="0" <%if ( uei.getSt_menu().equals("0") ) out.print("checked"); %>><strong>보이지 않기</strong>
                  <input name="rdoMenu" type="radio" value="1" <%if ( uei.getSt_menu().equals("1") ) out.print("checked"); %>><strong>보이기</strong>
                </td>
              </tr>
              <tr>
                <td colspan="3" bgcolor="#DDDDDD" ><img src="../../images/search/brank.gif" width="1" height="1"></td>
              </tr>
            </table>
            <table width="750" height="30" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="50" align="center">
                  <a href="javascript:save();" ><img src="../../images/search/btn_save_2.gif" width="55" height="24" hspace="5" border="0" ></a>
                  <a href="javascript:cancel();" ><img src="../../images/search/btn_cancel.gif" width="55" height="24" border="0" ></a>
                </td>
              </tr>
            </table></td>
          
        </tr>
      </table>
				
				
				
				
				
				</td>
			</tr>
			</table>	
		</td>
	</tr>
</table>
</form>
</body>
</html>
