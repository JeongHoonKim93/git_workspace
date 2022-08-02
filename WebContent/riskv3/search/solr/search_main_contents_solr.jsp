<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*
                 ,java.text.SimpleDateFormat
                 ,java.util.Date
                 ,risk.util.Log
                 ,risk.util.StringUtil
                 ,risk.util.ParseRequest
                 ,risk.util.DateUtil
                 ,risk.search.MetaBean
                 ,risk.search.MetaMgr
                 ,risk.util.PageIndex
                 ,risk.search.solr.SolrSearch
                 ,risk.search.solr.SearchForm
                 ,risk.search.userEnvMgr
                 ,risk.search.userEnvInfo
                 ,risk.issue.IssueMgr
                 ,risk.issue.IssueBean
                 ,risk.issue.IssueCodeMgr
                 ,risk.issue.IssueCodeBean
                 ,risk.search.solr.SolrMgr
                 ,risk.admin.tier.TierSiteMgr
                 ,risk.admin.tier.TierSiteBean
                 "
%>

<%@include file="../../inc/sessioncheck.jsp" %>

<%
	ParseRequest pr = new ParseRequest(request);
	StringUtil su = new StringUtil();
	DateUtil du = new DateUtil();
	pr.printParams();
	
	
	userEnvInfo uei = null;
    uei     = (userEnvInfo) session.getAttribute("ENV");
	
	String startDate   = "";
	String endDate     = "";
	//String xml         = "";
	String reSearchKey = "";
	String Sort        = "";
	String sgroup      = "";
	String emphasisKey = "";
	String sort        = "";
	String sort_order  = "";

	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	String toDay = date.format(new Date());
	
	
	String selectType = pr.getString("selectType", "1"); //1:제목+내용, 2:제목
	
	String SearchKey     = (pr.getString("txtSearch","")).trim(); //기본
	String txtSubSearch1 = (pr.getString("txtSubSearch1", "")).trim();  // 기본
	String txtSubSearch2 = (pr.getString("txtSubSearch2", "")).trim();  // OR
	String txtSubSearch3 = (pr.getString("txtSubSearch3", "")).trim();  // 구문
	String txtSubSearch4 = (pr.getString("txtSubSearch4", "")).trim();  // 인접	
	String type ="1";
	String txtSubSearch5 = (pr.getString("txtSubSearch5", "")).trim();  // 제외		
	String searchDate = pr.getString("searchDate", toDay);
	
	String searchOption = pr.getString("searchOption", "AND");
	String NotText = pr.getString("NotText");
	
	String[] groups = null;	
	//String chkgroups_init = "1 OR 2 OR 3 OR 4 OR 5 OR 6";
	//String chkgroups = pr.getString("chkgroups");
	
	String chkgroups_init = uei.getSg_seq_al().replaceAll(",", " OR ");
	System.out.println("chkgroups_init : "+chkgroups_init);
	String chkgroups = pr.getString("chkgroups", chkgroups_init);
	String sg_name = pr.getString("sg_name","");
	String s_code = "N OR B OR C OR K OR T";
	 
	int nowpage = pr.getInt("nowpage", 1); 
	int grfCheck = pr.getInt("grfCheck", 1); //그래프 화면표시 유무
	int listCheck = pr.getInt("listCheck", 1); //설명 화면표시 유무
	int detailSearch = pr.getInt("detailSearch", 0); // 상세검색 표시 유무
	int searchType = pr.getInt("searchType", 1); //1:최신순, 2:정확도순, 3:유사순
	
	String listCnt = uei.getSt_list_cnt();
	int pLength = Integer.parseInt(listCnt);
	
	endDate   = du.getDate(toDay, "yyyy-MM-dd");
	startDate = du.addDay(toDay, -20, "yyyy-MM-dd");
	
	ArrayList arlist = null;
	SolrSearch solr = new SolrSearch();
	
	
	
	//티어 정보 가져오기~
	
	String tier_s_seq = "";
	
	TierSiteMgr tiermng = new TierSiteMgr();
	List sglist = tiermng.getTs_type_data();
	
    //Tier
	String[] arTier = pr.getStringArr("chktier");
	String tiers = "";
	if(arTier != null && arTier.length > 0){
		for(int i = 0; i < arTier.length; i++){
			if(tiers.equals("")){
				tiers = arTier[i]; 
			}else{
				tiers += "," + arTier[i];
			}
		}
		
		tier_s_seq = tiermng.getSolrTierS_seq(tiers);
	}
	
	

/**키워드 상세 조합(호출 할 키워드)**/

	if(searchOption.equals("AND")){
		if(!SearchKey.equals("")){
			reSearchKey += SearchKey.replaceAll(" "," AND "); 	
		}
	} 
	
	if(searchOption.equals("OR")){
		if(!SearchKey.equals("")){
			reSearchKey += SearchKey.replaceAll(" "," OR "); 	
		}
	}

	if(searchOption.equals("NOT")){
		if(!SearchKey.equals("")){
			reSearchKey += SearchKey.replaceAll(" "," AND "); 	
		}
		
		if(!NotText.equals("")){
			if(reSearchKey.equals("")){
				reSearchKey += "NOT(" + NotText.replaceAll(" "," OR ") + ")"; 	
			}else{
				reSearchKey = "(" + reSearchKey + ")";
				reSearchKey += "NOT(" + NotText.replaceAll(" "," OR ") + ")";
			}
		}
	}


	/*
	//일반
	if(!SearchKey.equals("")){
		reSearchKey += SearchKey.replaceAll(" "," AND "); 	
	}
	
	//다음 단어 모두 포함
	if(!txtSubSearch1.equals("")){ 
		if(reSearchKey.equals("")){
			reSearchKey += txtSubSearch1.replaceAll(" "," AND "); 	
		}else{
			reSearchKey += " AND " + txtSubSearch1.replaceAll(" "," AND ");
		}
	}
	
	//다음 문구 정확하게 포함   
    if(!txtSubSearch2.equals("")){
          if(reSearchKey.equals("")){
                 reSearchKey +=  "(\""+ txtSubSearch2 +"\")" ; 
          }else{
                 reSearchKey += " AND "+ "(\"" + txtSubSearch2 + "\")"  ;
          }
    }

	
	//다음 단어 적어도 하나 포함 
	if(!txtSubSearch3.equals("")){
		if(reSearchKey.equals("")){
			reSearchKey += "(" + txtSubSearch3.replaceAll(" "," OR ") + ")"; 	
		}else{
			reSearchKey += " AND " + "(" + txtSubSearch3.replaceAll(" "," OR ") + ")";
		}
	}
	
	//인접 하여 검색
	if(!txtSubSearch4.equals("")){
		if(reSearchKey.equals("")){
			reSearchKey += "\""+ txtSubSearch4 +"\"~10";  	
		}else{
			reSearchKey += " AND " + "\""+ txtSubSearch4 +"\"~10"; 
		}		
	}
	
	
	//다음 단어 제외 
	if(!txtSubSearch5.equals("")){
		if(reSearchKey.equals("")){
			reSearchKey += "NOT(" + txtSubSearch5.replaceAll(" "," OR ") + ")"; 	
		}else{
			reSearchKey = "(" + reSearchKey + ")";
			reSearchKey += "NOT(" + txtSubSearch5.replaceAll(" "," OR ") + ")";
		}
	}
	*/
	
	
	//제목인지 제목+내용인지 판별
	if(selectType.equals("2")){
		reSearchKey = "title:" + reSearchKey;
	}

	
	//화면에 보이는 키워드 강조
	if(SearchKey.trim().length() == 0){
		if(	txtSubSearch1.trim().length() > 0){ //상세 일반
			//emphasisKey = txtSubSearch1.split(" ")[0];
			emphasisKey = txtSubSearch1;
		} else if ( txtSubSearch2.trim().length() > 0){ //상세 정확
			emphasisKey = "\""+ txtSubSearch2 + "\"";
		} else if( txtSubSearch3.trim().length() > 0){ //상세 OR
			emphasisKey = txtSubSearch3;
		} else if( txtSubSearch4.trim().length() > 0){ //인접
			emphasisKey = txtSubSearch4;
		}
	}
	else{
		emphasisKey = SearchKey;
	}
	

	
	
	if(searchType == 1){ //최신순
		sort = "docid";
		sort_order = "desc";
	}else if(searchType == 2){ //옛글순
		sort = "docid";
		sort_order = "asc";
	}else if(searchType == 3){ //정확도순
		sort = "score";
		sort_order = "desc";
	}	
	
	groups = chkgroups.split("OR");
	
	if(!chkgroups.equals("")){	
		s_code = "";
		
		for(int i = 0; i < groups.length; i++)
		{
			if(groups[i].trim().equals("1")){
				s_code  += s_code.equals("") ?  "N" :" OR N";	
			}
		}
		
		for(int i = 0; i < groups.length; i++)
		{
			if(groups[i].trim().equals("2")){
				s_code  += s_code.equals("") ?  "K" : " OR K";				
			}
		}
		
		for(int i = 0; i < groups.length; i++)
		{
			if(groups[i].trim().equals("3")){
				s_code  += s_code.equals("") ?  "B" : " OR B";				
			}
		}
		
		for(int i = 0; i < groups.length; i++)
		{
			if(groups[i].trim().equals("4")){
				s_code  += s_code.equals("") ?  "C" : " OR C";		
			}
		}
		
		for(int i = 0; i < groups.length; i++)
		{
			if(groups[i].trim().equals("5")){
				s_code  += s_code.equals("") ?  "T" : " OR T";		
			}
		}
		
		for(int i = 0; i < groups.length; i++)
		{
			if(groups[i].trim().equals("6")){
				s_code  += s_code.equals("") ?  "E" : " OR E";		
			}
		}
	}
	
	
	sgroup="s_code=("+s_code+")";

	
	
	String listSgroup = "";
	if(sg_name.equals("")){
		listSgroup =sgroup;
	}else{
		listSgroup ="s_code=("+sg_name+")";
	}
	
	
	
	
	//SOLA데이터 가져오기
	arlist = solr.getDataList(reSearchKey, nowpage, pLength, searchDate.replaceAll("-",""),sgroup, sort, sort_order, tier_s_seq, SS_M_ID);
	System.out.println("#########################################");
	System.out.println("##리스트데이터 가져오기 완료##");
	System.out.println("#########################################");
	
	//리스트의 사이즈가 0보다 작거나 null이면 error;
	if(arlist != null && arlist.size() > 0) {
		
	int iTotalPage = solr.hitCnt / pLength + (solr.hitCnt % pLength > 0 ? 1 : 0);
	
	String strLength = "(총 "+ su.digitFormat(solr.hitCnt) + " 건)";
	
	
	
	//이슈데이터 등록 관련
   	IssueMgr isMgr = new IssueMgr();
   	IssueBean isBean = new IssueBean();
   	SolrMgr smgr = new SolrMgr(); 
   	   	
   	IssueCodeMgr icMgr = IssueCodeMgr.getInstance();
   	IssueCodeBean icBean = new IssueCodeBean();
   	icMgr.init(1);
   	
   	/* ArrayList arrIssueBean = new ArrayList();
   	arrIssueBean = isMgr.getIssueList(0,0,"","","","","Y");
   	 */
   	//ArrayList<String> arrIssueSolr = smgr.getIssueSolr(searchDate);
   	
   	ArrayList arrIssueSolr = smgr.getIssueSolr(searchDate);
   	
   	uei.setSt_menu( pr.getString("layerType",uei.getSt_menu()) );
   	
   	String chartType = pr.getString("chartType", "bar");
   	
   	
   	
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RISK</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script language="JavaScript" src="chart/js/FusionCharts.js"></script>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>


<script language="JavaScript" type="text/JavaScript">
<!--

function lineOver(docid) {
		
	var obj1 = document.getElementById('divDocList'+docid);
	obj1.className='line_change02';
	//var obj2 = document.getElementById('divIssueChk'+docid);
	//obj2.style.display= '';
	//var obj3 = document.getElementById('divIssueChktd'+docid);
	//obj3.style.display= '';
}
function lineOut(docid){
	var obj1 = document.getElementById("divDocList"+docid);
	obj1.className='line_change01';
	//var obj2 = document.getElementById('divIssueChk'+docid);
	//obj2.style.display= 'none';
	//var obj3 = document.getElementById('divIssueChktd'+docid);
	//obj3.style.display= 'none';

	
	//var obj = document.getElementById(objName); 
	//obj.className='line_change01';					
}

function search(){	
	var f = document.fSolr;
	
	if(f.detailSearch.value == 1){	
	}
	
	var o =document.all.chkgroup;
	var siteGroup = ''; 
	var length = 0;
	if(o != null && o.length > 0){
		for(i=0; i<o.length; i++) {
			if(o[i].checked == true)
			{
				length = length + 1;
				if(siteGroup == ''){
					siteGroup = o[i].value;		
				}else{
					siteGroup = siteGroup + ' OR ' + o[i].value;
				}					
			}
		}
	}
	
	if( length == 0){
		alert('검색대상은 최소1개 이상 입니다.'); 
		return;
	}

	f.chkgroups.value = siteGroup;
	
	f.nowpage.value = 1;
	f.searchDate.value = "";

	f.target = '';
	f.action = 'search_main_contents_solr.jsp';
	f.submit();
}

function pageClick( paramUrl ) {
	var f = document.fSolr; 
    f.action = "search_main_contents_solr.jsp" + paramUrl;
    f.submit();
}

function responseXml()
{
	var f = document.fSolr;
	var ChartXml = f.xml.value;
	
	if(ChartXml != ""){
		
	var chart = new FusionCharts("chart/charts/Column2D.swf", "ChartId", "750", "230");
		chart.setDataXML(ChartXml);
		chart.render("chartdiv");
		 
	}else{	
		var chart2 = document.getElementById("chartdiv");//첫로딩이미지
		chart2.innerHTML = "<img id=\"loadingBar\" src=\"../../../images/search/no_data.gif\">";
	}
}

function searchDataList(date) {
	var f = document.fSolr;
	f.searchDate.value = date;
	f.sg_name.value = '';
    f.submit();
}
function searchDataList_source(date, sg_name) {

	var f = document.fSolr;
	f.searchDate.value = date;
	f.sg_name.value = sg_name;
    f.submit();
}

function chartView(check){
	var f = document.fSolr;
	var Obj = document.getElementById("listChart");
	
	
	if(check == '1'){
			
		
		f.grfCheck.value = 1;
		f.chartType.value = 'bar';

	}else{
		
		f.grfCheck.value = 2;
		f.chartType.value = 'line';
	}
	f.submit();
}  

function htmlView(check){

	var f = document.fSolr;
	for(var i = 0; i < f.lineCount.value; i++)
	{
		
		var obj = document.getElementById("htmlList" + i);

		if(check){
			obj.style.display = '';	
		}else{
			obj.style.display = 'none';	
		}
		
	}

	if(check){
		f.listCheck.value = 1;
	}else{
		f.listCheck.value = 0;
	}
}
function listNum(value){
	var f = document.fSolr;
	f.submit();
}
function searchReSet(){	
	var f = document.fSolr;
	f.nowpage.value = 1;
	f.searchDate.value = "";
	f.grfCheck.value = 1;
	f.listCheck.value = 1;
	f.lineCount.value = "";
	f.searchType.value = 1;
	f.detailSearch.value = 0;
	f.chkgroups.value = "";

	f.txtSearch.value = "";
	f.txtSubSearch1.value = "";
	f.txtSubSearch2.value = "";
	f.txtSubSearch3.value = "";
	f.txtSubSearch4.value = "";
	f.submit();
}
function changeOrder(index){
	var f = document.fSolr;
	f.searchType.value = index;	
	f.submit();
}

function trim(str) {
    return str.replace(/^\s+|\s+$/g, "");
}

function DetailSearch(){
	var f = document.fSolr;

	var detail1 = document.getElementById("detail1");
    var detail2 = document.getElementById("detail2");
    var detail3 = document.getElementById("detail3");
    var detail4 = document.getElementById("detail4");

	f.txtSubSearch1.value = '';
	f.txtSubSearch2.value = '';
	f.txtSubSearch3.value = '';
	f.txtSubSearch4.value = '';
	f.txtSubSearch5.value = '';

	var o = null;
	o = document.all.chkgroup;

	f.chkgroups.value = f.chkgroups_init.value;
	var arr =  f.chkgroups.value.split("OR");
	
	if(o != null && o.length > 0){
		for(var i=0; i<o.length; i++) {
			o[i].checked = false;
			for(var j=0; j<arr.length; j++){
				if(o[i].value == trim(arr[j])){
					o[i].checked = true;
				}	
			}
		}
	}

		
	if( f.detailSearch.value == 1){
		f.detailSearch.value = 0;
		f.txtSearch.readOnly = '';
		detail1.style.display = 'none';
		detail2.style.display = 'none';
		detail3.style.display = 'none';
		detail4.style.display = 'none';
	}else{
		f.detailSearch.value = 1;
		f.txtSearch.value = '';
		f.txtSearch.readOnly = 'true';
		detail1.style.display = 'block';
		detail2.style.display = 'block';
		detail3.style.display = 'block';
		detail4.style.display = 'block';
	}
}

function getGraph(){
	var f = document.fSolr;
	f.target='listChart';
	f.action='inc_list_chart.jsp';
	f.submit();
}


//******************* 이슈 등록 관련********************************
function show_menu(md_seq, cnt){
	
	var obj = document.getElementById("issueChkImg"+md_seq); 
	var f = document.fSolr;
	
	
	if( document.all.floater.style.display == 'none' ) {		
		objdata = obj.getBoundingClientRect();
		document.all.floater.style.top = objdata.top+document.body.scrollTop+20;
		document.all.floater.style.left = objdata.left+document.body.scrollLeft;
		document.send_mb.md_seq.value = md_seq;

		document.send_mb.md_title.value = f.rowTitle[cnt].value;
		document.send_mb.md_url.value = f.rowUrl[cnt].value;
		document.send_mb.s_seq.value = f.rowGsn[cnt].value;
		document.send_mb.md_site_name.value = f.rowGsnName[cnt].value;
		document.send_mb.md_date.value = f.rowDate[cnt].value;
		
		
		document.all.floater.style.display = '';
		
	}else {
		document.all.floater.style.display = 'none';
	}

}

//타입코드 셋팅
function settingTypeCode()
{
	var form = document.send_mb;
	var result = false;

	form.typeCode.value = '';
	
	for(var i=0;i<form.typeCode1.length;i++){
		if(form.typeCode1[i].checked)
		{
			form.typeCode.value += form.typeCode.value=='' ? form.typeCode1[i].value : '@'+form.typeCode1[i].value ;				
			result = true;
		}			
	}
	if(!result)	{alert('중요를 선택해주세요.'); return result;}	
	result = false;	
	for(var i=0;i<form.typeCode4.length;i++){
		if(form.typeCode4[i].checked)
		{
			form.typeCode.value += form.typeCode.value=='' ? form.typeCode4[i].value : '@'+form.typeCode4[i].value ;
			result = true;
		}	
	}
	if(!result)	{alert('성향을 선택해주세요.'); return result;}	
	result = false;	
	for(var i=0;i<form.typeCode5.length;i++){
		if(form.typeCode5[i].checked)
		{
			form.typeCode.value += form.typeCode.value=='' ? form.typeCode5[i].value : '@'+form.typeCode5[i].value ;
			result = true;
		}	
	}
	if(!result)	{alert('구분을 선택해주세요.'); return result;}	
	result = false;		
	for(var i=0;i<form.typeCode6.length;i++){
		if(form.typeCode6[i].checked)
		{
			form.typeCode.value += form.typeCode.value=='' ? form.typeCode6[i].value : '@'+form.typeCode6[i].value ;
			result = true;
		}	
	}
	if(!result)	{alert('구분2를 선택해주세요.'); return result;}

	return true;	
}		

//이슈 타이틀 변경
function changeIssueTitle()
{    
	ajax.post('../selectbox_issue_title.jsp','send_mb','td_it_title');
}

//이슈 데이터 즉시등록
function saveIssueData()
{
	var form = document.send_mb;
	if(!settingTypeCode()) return;
	if(form.i_seq[form.i_seq.selectedIndex].value=='0') {alert('이슈를 선택해주세요.'); return;}
	if(form.it_seq[form.it_seq.selectedIndex].value=='0') {alert('주제를 선택해주세요.'); return;}		
	form.mode.value='solrsave';
	form.method = 'post';					  
	form.target='proceeFrame';
	form.action='../issue_data_prc.jsp';
	form.submit();
	close_menu();		
}

function close_menu()
{
	document.all.floater.style.display = 'none';
}

function changeIssueIcon(docid){
	var obj1 = document.getElementById("issueChkImg"+docid);
	obj1.src='images/search/yellow_star.gif';
	obj1.title='이슈로 등록된 정보 입니다.';
    obj1.onclick='';
	var obj2 = document.getElementById("issueChkText"+docid);
	obj2.href = "javascript:";
}
function send_issue(mode, subMode)
{
	close_menu();
	document.send_mb.mode.value = mode;
	document.send_mb.subMode.value = subMode;
	if(mode=='insert'){		
    	popup.openByPost('send_mb','../pop_issue_data_form.jsp',680,945,false,false,false,'send_issue');
	}else{
		document.send_mb.md_seq.value = md_seq;
		popup.openByPost('send_mb','../pop_issue_data_form.jsp',680,945,false,false,false,'send_issue');
	}	
}
//****************************************************************
	
function chkSiteAll(chk){
 	var group = document.all.chkgroup;
	if(group){
		if(group.length){
			for(var i=0; i<group.length; i++){
				group[i].checked = chk;
			}  	
		} else {
			group.checked = chk;
		}
	}
}

function chkSite(){
	var count = 0;
 	var group = document.all.chkgroup;

 	for(var i=0; i<group.length; i++){
 		if(group[i].checked == true) count++;
	}

	if(count == group.length)
		document.all.chkgroupAll.checked = true;
	else
		document.all.chkgroupAll.checked = false;
}

function actionLayer(obj){
	if(obj.src.indexOf("close") > -1){
		for(var i = 1; i < 7; i++){
			document.getElementById('hide'+i).style.display = 'none';
		}
		obj.src = '../../../images/search/btn_searchbox_open.gif';
		document.getElementById('fSolr').layerType.value = "0";
	}else{
		for(var i = 1; i < 7; i++){
			document.getElementById('hide'+i).style.display = '';
		}
		obj.src = '../../../images/search/btn_searchbox_close.gif';
		document.getElementById('fSolr').layerType.value = "1";
	}
}

function selectSearchOption(mode){
	if(mode == 'AND'){
		document.fSolr.NotText.style.display = "none";
	} else if(mode == 'OR'){
		document.fSolr.NotText.style.display = "none";
	} else if(mode == 'NOT'){
		document.fSolr.NotText.style.display = "";
	}
}

function selectRadioByText(mode){
	if(mode == 'AND'){
		document.fSolr.searchOption[0].checked = "checked";
	} else if(mode == 'OR'){
		document.fSolr.searchOption[1].checked = "checked";
	} else if(mode == 'NOT'){
		document.fSolr.searchOption[2].checked = "checked";
	}

	selectSearchOption(mode);
}


function getExcel(reSearchKey, searchDate, listSgroup, sort, sort_order){
	var f = document.fSolr;

	
	//센딩이미지 처리
	var imgObj = document.getElementById("sending");
	var sendBtn = document.getElementById("imgExcel");
	rect = sendBtn.getBoundingClientRect();
	imgObj.style.top = rect.top + document.body.scrollTop - 50;			  
	imgObj.style.left = rect.left + 100;
	imgObj.style.display = '';
	

	
	var data  = "?reSearchKey=" + reSearchKey
	          + "&searchDate=" + searchDate
	          + "&listSgroup=" + listSgroup
	          + "&sort=" + sort
	          + "&sort_order=" + sort_order;

	f.target='proceeFrame';


	
	
	 //$('#fSolr').attr({action:'excel_main_data.jsp' + data, method:'post', target:'proceeFrame'}).submit();
	 $.post('excel_main_data.jsp' + data, $('#fSolr').serialize(),
			   function(data){    
		 			//document.getElementById('ReportTable').innerHTML = data;          
				   imgObj.style.display = 'none';



				   var frm_excel = document.proceeExcel;
					frm_excel.target = 'proceeFrame';
					frm_excel.action = 'excel_empty.jsp';
				   frm_excel.dataToDisplay.value = data;
				   frm_excel.submit();					
			});
}

//-->
</script>
</head>
<body style="margin-left: 15px">
<form name="fSolr" id="fSolr" action ="search_main_contents_solr.jsp" method="post">
<input type="hidden" name="nowpage" value ="1">
<input type="hidden" name="searchDate" value="<%=searchDate%>">
<input type="hidden" name="grfCheck" value="<%=grfCheck%>">
<input type="hidden" name="listCheck" value="<%=listCheck%>">
<input type="hidden" name="lineCount" value="<%=arlist.size()%>">
<input type="hidden" name="searchType" value="<%=searchType%>">
<input type="hidden" name="layerType" value="<%=uei.getSt_menu()%>">
<input type="hidden" name="detailSearch" value="<%=detailSearch%>">
<input type="hidden" name="chkgroups" value="<%=chkgroups%>">
<input type="hidden" name="chkgroups_init" value="<%=chkgroups_init%>">
<input type="hidden" name="chartType" value="<%=chartType%>">
<input type="hidden" name="sg_name" value="<%=sg_name%>">

<!--
<input type="hidden" name="reSearchKey" id="reSearchKey">
<input type="hidden" name="searchDate" id="searchDate">
<input type="hidden" name="listSgroup" id="listSgroup">
<input type="hidden" name="sort" id="sort">
<input type="hidden" name="sort_order" id="sort_order">
  -->
<img  id="sending" src="../../../images/search/saving.gif" style="position: absolute; display: none; " >

<table style="width:100%; height:100%;" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td class="body_bg_top" valign="top">
		<table style="width:820px;" border="0" cellpadding="0" cellspacing="0">
			<!-- 타이틀 시작 -->
			<tr>
				<td class="tit_bg" style="height:67px;padding-top:20px;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><img src="../../../images/search/tit_icon.gif" /><img src="../../../images/search/tit_0101.gif" /></td>
						<td align="right">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="navi_home">HOME</td>
								<td class="navi_arrow">정보검색</td>
								<td class="navi_arrow2">전체검색</td>
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
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="search_box">
						<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td >
								<table id="search_box" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<select name="selectType" id="mySelect">
												<option value="1" <%if(selectType.equals("1")){ out.print("selected"); }%>>제목 + 내용에서 검색</option>
        										<option value="2" <%if(selectType.equals("2")){ out.print("selected"); }%>>제목에서만 검색</option>
											</select> <input style="width:460px;" name="txtSearch" class="textbox" type="text" <%if(detailSearch==1){out.print("readonly");}%> onkeypress="if(event.keyCode==13){return search();}" value="<%=SearchKey%>"><img src="../../../images/search/btn_search.gif" onclick="search();" style="cursor:pointer" align="absmiddle" /><img src="../../../images/search/btn_reset.gif" onclick="searchReSet();" style="cursor:pointer;" align="absmiddle" />
										</td>
									</tr>
								</table>
								</td>
							</tr>
							<tr id="hide1" <%if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}%>><td style="height:15px;background:url('../../../images/search/dotline.gif') 50% repeat-x;"></td></tr>
							<tr id="hide2" <%if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}%>>
								<td>
								<table id="search_box" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<th>검색어 설정</th>
										<%
											String [] checked = new String[3];
											if(searchOption.endsWith("AND")){ checked[0] = "checked"; checked[1] = ""; checked[2] = "";}
											else if(searchOption.endsWith("OR")){ checked[0] = ""; checked[1] = "checked"; checked[2] = "";}
											else if(searchOption.endsWith("NOT")){ checked[0] = ""; checked[1] = ""; checked[2] = "checked";}
										%>
										<td><input type="radio" name="searchOption" value="AND" onclick="selectSearchOption('AND')" <%=checked[0] %>><a onclick="selectRadioByText('AND')" style="cursor: pointer">단어 모두 포함 (AND)</a>&nbsp;</td>
										<td><input type="radio" name="searchOption" value="OR" onclick="selectSearchOption('OR')" <%=checked[1] %>><a onclick="selectRadioByText('OR')" style="cursor: pointer">다음 단어 적어도 하나 포함 (OR)</a>&nbsp;</td>
										<td>
											<input type="radio" name="searchOption" value="NOT" onclick="selectSearchOption('NOT')" <%=checked[2] %>><a onclick="selectRadioByText('NOT')" style="cursor: pointer">다음 단어 제외 (NOT)</a>&nbsp;
											<input type="text" name="NotText" style="width:150px; <%if(!searchOption.endsWith("NOT"))out.print("display: none;");%>" value="<%=NotText%>">
										</td>
									</tr>

									<!-- 
									<tr>
										<th>검색어 설정</th>
										<td>다음 단어 <b>모두</b> 포함</td>
										<td><input style="width:350px;" name="txtSubSearch1" onkeypress="if(event.keyCode==13){return search();}" class="textbox" type="text" value="<%=txtSubSearch1%>"></td>
									</tr>
									<tr>
										<td></td>
										<td>다음 <b>문구 정확하게</b> 포함</td>
										<td><input style="width:350px;" name="txtSubSearch2" onkeypress="if(event.keyCode==13){return search();}" class="textbox" type="text" value="<%=txtSubSearch2%>"></td>
									</tr>
									<tr>
										<td></td>
										<td>다음 단어 <b>적어도 하나</b> 포함 &nbsp; </td>
										<td><input style="width:350px;" name="txtSubSearch3" onkeypress="if(event.keyCode==13){return search();}" class="textbox" type="text" value="<%=txtSubSearch3%>"></td>
									</tr>
									<tr>
										<td></td>
										<td>다음 단어 <b>인접하여</b></td>
										<td><input style="width:350px;" name="txtSubSearch4" onkeypress="if(event.keyCode==13){return search();}" class="textbox" type="text" value="<%=txtSubSearch4%>"></td>
									</tr>
									<tr>
										<td></td>
										<td>다음 단어 <b>제외</b></td>
										<td><input style="width:350px;" name="txtSubSearch5" onkeypress="if(event.keyCode==13){return search();}" class="textbox" type="text" value="<%=txtSubSearch5%>"></td>
									</tr>
									
									 -->
								</table>
								</td>
							</tr>
							<tr id="hide3" <%if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}%>><td style="height:10px;"></td></tr>
							<tr id="hide4" <%if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}%>>
								<td>
								<table id="search_box" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<th>검 색 대 상</th>
										<td style="padding-right:30px;">
										<table style="color:#2f5065;" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td style="padding-right:10px;"><input type="checkbox" name="chkgroupAll" value="0" onclick="chkSiteAll(this.checked);">전체</td>
												<td style="padding-right:10px;"><input type="checkbox" name="chkgroup" value="1" onclick="chkSite()" <%for(int i = 0; i < groups.length; i++){if(groups[i].trim().equals("1")){out.print("checked");}}%>>언론사</td>
												<td style="padding-right:10px;"><input type="checkbox" name="chkgroup" value="2" onclick="chkSite()" <%for(int i = 0; i < groups.length; i++){if(groups[i].trim().equals("2")){out.print("checked");}}%>>커뮤니티</td>
												<td style="padding-right:10px;"><input type="checkbox" name="chkgroup" value="3" onclick="chkSite()" <%for(int i = 0; i < groups.length; i++){if(groups[i].trim().equals("3")){out.print("checked");}}%>>블로그</td>
												<td style="padding-right:10px;"><input type="checkbox" name="chkgroup" value="4" onclick="chkSite()" <%for(int i = 0; i < groups.length; i++){if(groups[i].trim().equals("4")){out.print("checked");}}%>>카페</td>
												<td style="padding-right:10px;"><input type="checkbox" name="chkgroup" value="5" onclick="chkSite()" <%for(int i = 0; i < groups.length; i++){if(groups[i].trim().equals("5")){out.print("checked");}}%>>트위터</td>
												<td style="padding-right:10px;"><input type="checkbox" name="chkgroup"value="6" onclick="chkSite()" <%for(int i = 0; i < groups.length; i++){if(groups[i].trim().equals("6")){out.print("checked");}}%>>기타</td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								</td>
							</tr>
							<tr id="hide5" <%if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}%>><td style="height:10px;"></td></tr>
							<tr id="hide6" <%if(uei.getSt_menu().equals("0")){out.print("style=\"display:none\"");}%>>
								<td>
								<table id="search_box" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<th>&nbsp;&nbsp;Tier 정보</th>
										<td style="padding-right:30px;">
										<table style="color:#2f5065;" border="0" cellpadding="0" cellspacing="0">
											<tr>
											<%
												String ts_check = "";
												for(int i=0; i < sglist.size();i++) {
													if(su.inarray(arTier, Integer.toString((Integer)sglist.get(i)))){
														ts_check = "checked";
													}else{
														ts_check = "";
													}
											%>
												<td style="padding-right:10px;"><input type="checkbox" name="chktier" value="<%=Integer.toString((Integer)sglist.get(i))%>" <%=ts_check%>>Tier<%=Integer.toString((Integer)sglist.get(i))%></td>	
											<%		
												}
											%>
											
											</tr>
										</table>
										</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td style="text-align:center;"><img src="<%if(uei.getSt_menu().equals("0")){out.print("../../../images/search/btn_searchbox_open.gif");}else{out.print("../../../images/search/btn_searchbox_close.gif");}%>" style="cursor:pointer" onclick="actionLayer(this)"/></td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 검색 끝 -->
			<tr>
				<td><table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="vertical-align:middle"><img src="../../../images/search/table_title_001.gif" width="86" height="13" style="vertical-align:middle"> (<%=startDate%> ~ <%=endDate%>)</td>
						<td align="left">
							<div id="divLoading" style="display:none; width:196px; height:14px;  background:url(images/search/load_01.gif) no-repeat;"></div>
						</td>
						<td width="*" align="right"><table width="190" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="15" align="center" background="images/search/bg_011.gif"><input name="chartchk" type="radio" onclick="chartView(this.value)" <%if(grfCheck == 1){ out.print("checked");}%> value="1"><span class="menu_gray_s"><strong>일자별추이</strong></span></td>
								<td height="15" align="center" background="images/search/bg_011.gif"><input name="chartchk" type="radio" onclick="chartView(this.value)" <%if(grfCheck == 2){ out.print("checked");}%> value="2"><span class="menu_gray_s"><strong>출처별추이</strong></span></td>
							</tr>
						</table></td>
					</tr>
					<tr>
						<td colspan="3">
							<iframe id="listChart" name="listChart" style="display:<%if(grfCheck == 0){out.print("none");}%>" src="inc_list_chart.jsp?reSearchKey=<%=solr.changeEncode(reSearchKey,"utf-8")%>&startDate=<%=startDate.replaceAll("-","")%>&endDate=<%=endDate.replaceAll("-","") %>&sgroup=<%=sgroup%>&searchDate=<%=searchDate.replaceAll("-","")%>&chartType=<%=chartType%>&sg_name=<%=sg_name%>&s_seq=<%=tier_s_seq%>" width="820" height="<%if(chartType.equals("bar")){out.print("380");}else{out.print("700");}%>" frameborder="0" scrolling="no"></iframe>
						</td>
					</tr>
				</table></td>
			</tr>
			<tr>
				<td style="height:45px;background:url('../../../images/search/list_top_line.gif') bottom repeat-x;">
				<table style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
						<span class="sort" style="cursor:pointer" onclick="changeOrder(1);"><%if(searchType == 1){out.print("<span class=\"sort_on\">최신순</span>");}else{out.print("최신순");}%></span>
						<span class="sort" style="cursor:pointer" onclick="changeOrder(2);"><%if(searchType == 2){out.print("<span class=\"sort_on\">옛글순</span>");}else{out.print("옛글순");}%></span>
						<span class="sort" style="cursor:pointer" onclick="changeOrder(3);"><%if(searchType == 3){out.print("<span class=\"sort_on\">정확도순</span>");}else{out.print("정확도순");}%></span>
						&nbsp;&nbsp;&nbsp;<img id="imgExcel" src="../../../images/search/btn_excelsave.gif" title="잠시만 기다려주세요." style="vertical-align: middle; cursor: pointer;" onclick="getExcel('<%=solr.changeEncode(reSearchKey,"utf-8")%>','<%=searchDate.replaceAll("-","")%>','<%=listSgroup%>','<%=sort%>','<%=sort_order%>');">
						
						
						
						</td>
						<td style="text-align:right;">
						
						<span class="sort"><input type="checkbox" name="listchk" onclick="htmlView(this.checked);" <%if(listCheck == 1){ out.print("checked"); }%>>주요내용보기</span><%=strLength%>
						</td>
					</tr>
				</table>	
				</td>
			</tr>
			<!-- 게시판 시작 -->
			<tr>
				<td>
				
<%
if(arlist.size() > 0){
	for(int i = 0; i < arlist.size(); i++){
		SearchForm form = (SearchForm)arlist.get(i);
%>
					<input type="hidden" id="rowTitle" name="rowTitle" value="<%=form.getTitle()%>">
					<input type="hidden" id="rowUrl" name="rowUrl" value="<%=form.getUrl()%>">
					<input type="hidden" id="rowGsn" name="rowGsn" value="<%=form.getGsn()%>">
					<input type="hidden" id="rowGsnName" name="rowGsnName" value="<%=form.getName()%>">
					<input type="hidden" id="rowDate" name="rowDate" value="<%=form.getSdate()+form.getStime()%>">
<%
		if(i == 0){
%>
					<table id="board_02" border="0" cellpadding="0" cellspacing="0">
<%
		}
		if(!form.getImg_name().equals("NON") && !form.getImg_name().equals("Y") && form.getGsn() != 10464){
%>
					<tr>
						<th rowspan="2"><a href="<%=form.getUrl()%>" target="_blank"><img src="http://image.realsn.co.kr/<%=DateUtil.addHour(form.getSdate()+form.getStime(),9,"yyyyMMddHHmmss","yyyyMMdd")%>/<%=form.getImg_name()%>"></a></th>
						<th <%if(i == 0){out.print("class=\"top\"");}%>><a href="<%=form.getUrl()%>" target="_blank"><strong class="menu_blue_b"><%=su.subStrTitle(form.getTitle(), 30)%>&nbsp;&nbsp;</strong></a><span class="source"><%=form.getName().length() > 8 ?  form.getName().substring(0,6) + "..." : form.getName()%></span><span class="date"><%=du.getDate(form.getSdate(), "MM-dd")%> <%=DateUtil.addHour(form.getSdate()+form.getStime(),9,"yyyyMMddHHmmss","MM-dd HH:mm")%></span></th>
					</tr>
					<tr>
						<td <%if(i == arlist.size()-1){out.print("class=\"end\"");}%>><div id="htmlList<%=i%>" style="display: <%if(listCheck != 1){ out.print("none"); } %>"><%=su.cutKey(su.toHtmlString(form.getHtml()), emphasisKey, 120, "0000CC")%></div></td>
					</tr>
<%
		}else{
%>
					<tr>
						<th colspan="2" <%if(i == 0){out.print("class=\"top\"");}%>><a href="<%=form.getUrl()%>" target="_blank"><strong class="menu_blue_b"><%=su.subStrTitle(form.getTitle(), 40)%>&nbsp;&nbsp;</strong></a><span class="source"><%=form.getName().length() > 8 ?  form.getName().substring(0,6) + "..." : form.getName()%></span><span class="date"><%=DateUtil.addHour(form.getSdate()+form.getStime(),9,"yyyyMMddHHmmss","MM-dd HH:mm")%></span></th>
					</tr>
					<tr>
						<td colspan="2" <%if(i == arlist.size()-1){out.print("class=\"end\"");}%>><div id="htmlList<%=i%>" style="display: <%if(listCheck != 1){ out.print("none"); } %>"><%=su.cutKey(su.toHtmlString(form.getHtml()), emphasisKey, 130, "0000CC")%></div></td>
					</tr>
<%
		}
	}
}
%>
				</table>
				</td>
			</tr>
			<!-- 게시판 끝 -->
			<!-- 페이징 -->
			<tr>
				<td style="text-align:center;width:820px;">
				<table align="center" style="padding-top:10px;" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td>
							<table id="paging" border="0" cellpadding="0" cellspacing="2">
								<tr>
									<%=PageIndex.getPageIndex(nowpage,iTotalPage,"","" )%>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!-- 페이징 -->
		</table>
		</td>
	</tr>
</table>
</form>
<form name="proceeExcel" id="proceeExcel" method="post" action="excel_empty.jsp" target="_blank">
	<input type="hidden" id="dataToDisplay" name="dataToDisplay">		
</form>
<iframe id="proceeFrame" name="proceeFrame" width="0" height="0"></iframe>

</body>
</html>
<%
	}else {
		String errorCode = solr.getErrorCode();
		String errorMsg = solr.getErrorMsg();
		String errorAlertMsg = solr.getErrorAlertMsg();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RISK</title>
<link rel="stylesheet" type="text/css" href="../../../css/base.css" />
<script language="JavaScript" src="chart/js/FusionCharts.js"></script>
<script src="<%=SS_URL%>js/jquery.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/ajax.js" type="text/javascript"></script>
<script src="<%=SS_URL%>js/popup.js" type="text/javascript"></script>
	<script type="text/JavaScript">
    var code = "<%=errorCode%>";
    var msg = "<%=errorMsg%>";
    var alertMsg = "<%=errorAlertMsg%>";
	$(function(){
		alert(alertMsg);
		top.bottomFrame.leftFrame.document.location ='../../search/search_main_left.jsp';
		location.href='../../search/search_main_contents.jsp';
	});
	</script>
	
	</head>
	<body>
	</body>
</html>
<%
	}
%>
