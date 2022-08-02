<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList
				,risk.util.ParseRequest
				,risk.util.DateUtil
				,risk.util.StringUtil
				,risk.issue.IssueCodeMgr
				,risk.issue.IssueCodeBean
				,risk.issue.IssueBean	
				,risk.issue.IssueMgr
				,java.util.List
				,java.util.Iterator
				,risk.issue.IssueDataBean"
%>
<%
	ParseRequest pr = new ParseRequest(request);		
	DateUtil	 du = new DateUtil();
	StringUtil		su = new StringUtil();
	pr.printParams();

	String type = pr.getString("targetType");
	String code = pr.getString("targetCode");

	String ptype = pr.getString("targetPType");
	String pcode = pr.getString("targetPCode");
	String id_seq = pr.getString("id_seq");
	IssueMgr issueMgr = new IssueMgr();
	IssueCodeMgr 	icm = new IssueCodeMgr();
	IssueCodeBean icb = new IssueCodeBean();
	ArrayList arrIcBean = new ArrayList();	 //분류코드 어레이
	/**********연관키워드*********/
	ArrayList relationKey =  new ArrayList();
	ArrayList relationKeyAll =  new ArrayList();
	icm.init(0);
	
	arrIcBean = new ArrayList();
	arrIcBean = icm.getSearchIssueCode(ptype, pcode);
	String title = icm.getIssueCodeName(Integer.parseInt(ptype), Integer.parseInt(pcode));
	icb = new IssueCodeBean();
	icb = (IssueCodeBean) arrIcBean.get(0);
	//자동롤링용 연관키워드 한줄로 만들기
   	relationKeyAll = issueMgr.getRelationKey();

   	String streamKey = "";
   	String checked1 = ""; //과거 성향
   	String checked2 = ""; //과거 성향
   	String checked3 = ""; //과거 성향
   	if(relationKeyAll.size() > 0){
   		for(int i =0; i < relationKeyAll.size(); i++){
	   		
	   		if(streamKey.equals("")){
	   			streamKey = (String)relationKeyAll.get(i);
	   		}else{
	   			streamKey += "," + (String)relationKeyAll.get(i);
	   		}
	   	}	
   	}
   	
   	ArrayList arrIcBean2  = new ArrayList();
   	if(!id_seq.equals("")){
   		arrIcBean2 = issueMgr.getCodeDeatil(id_seq);
   	}

	arrIcBean = icm.GetType(9);
	String selected = icm.getTypeCodeVal_v2(arrIcBean2, 20, Integer.parseInt(ptype));
	System.out.println("selected: "+selected);
	for(int i=1; i<arrIcBean.size(); i++){
		
		icb = (IssueCodeBean) arrIcBean.get(i);
		if(selected.equals("20,"+icb.getIc_code())){
			if(icb.getIc_code() == 1) checked1 = "checked";
			else if(icb.getIc_code() == 2) checked2 = "checked";
			else if(icb.getIc_code() == 3) checked3 = "checked";
			
			break;
		}
	}
   	
%>	
 		<th style="background-color: #F8F8F8;"><span id="typeTitle<%=ptype %>" style="padding-left:10px;"><img alt="하위분류" src="../../images/issue/icon_bot_02.gif">성향* 및 연관키워드</span></th>
		<td class="content" width="155px" id="td_typeCode<%=ptype %>_trend">
			<div class='radioChk' id='typeCode9_<%=ptype %>' style='margin-left:5px;display:block;'>
                 <div class="dcp"><input id="input_trend<%=ptype %>_1" type="radio" name="input_trend<%=ptype %>" value="<%=ptype %>,9,1" class="ui_hidden" ><label for="input_trend<%=ptype %>_1"><span class="radio_00"></span></label>
                 <input id="input_pastTrend<%=ptype %>_1" type="checkbox" name="input_pastTrend<%=ptype %>" value="<%=ptype %>,20,1" class="ui_hidden" <%=checked1 %> disabled><label for="input_pastTrend<%=type %>_1"></label> 
                 </div>
                 &nbsp;
                 <div class="dcp"><input id="input_trend<%=ptype %>_2" type="radio" name="input_trend<%=ptype %>" value="<%=ptype %>,9,2" class="ui_hidden" ><label for="input_trend<%=ptype %>_2"><span class="radio_01"></span></label>
                 <input id="input_pastTrend<%=ptype %>_2" type="checkbox" name="input_pastTrend<%=ptype %>" value="<%=ptype %>,20,2" class="ui_hidden" <%=checked2 %> disabled><label for="input_pastTrend<%=type %>_2"></label>
                 </div>
                 &nbsp;
                 <div class="dcp"><input id="input_trend<%=ptype %>_3" type="radio" name="input_trend<%=ptype %>" value="<%=ptype %>,9,3" class="ui_hidden" checked><label for="input_trend<%=ptype %>_3"><span class="radio_02"></span></label>   
                 <input id="input_pastTrend<%=ptype %>_3" type="checkbox" name="input_pastTrend<%=ptype %>" value="<%=ptype %>,20,3" class="ui_hidden" <%=checked3 %> disabled><label for="input_pastTrend<%=type %>_3"></label>  
				 </div> 
				 &nbsp;                                                   
			</div>
		</td>
		<td id="relKeyList_<%=ptype%>" colspan="2" valign="top" align="left">
			<input type="text" class="input_keyword" id="txt_relationkey_<%=ptype %>" name="txt_relationkey" onkeypress="javascript:if(event.keyCode == 13){addKeyword(this,'<%=ptype %>');}" placeholder="연관키워드 입력" value="">
            <img src="../../images/issue/plus_btn.gif" onclick="addKeyword(this,'<%=ptype %>');" style="vertical-align: bottom">
		</td> 
<script>
var streamKey = '<%=streamKey%>';
var ptype = '<%=ptype%>';
$(document).ready(function() {		
	 $('#txt_relationkey_'+ptype).autocomplete(streamKey.split(','));
});
</script>
		