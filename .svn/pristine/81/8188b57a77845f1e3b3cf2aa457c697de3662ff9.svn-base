<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,
                 risk.admin.keyword.KeywordBean,
                 risk.admin.keyword.KeywordMng,                
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ParseRequest    pr = new ParseRequest(request);
	pr.printParams();
	String mName = SS_M_NAME;
	String kSeq = pr.getString("kSeq");
	String checkIds = pr.getString("checkIds");
	String mode = pr.getString("mode");
	String type2 = pr.getString("type2");
	String targetSeq = pr.getString("targetSeq");
	String XpList = pr.getString("XpList","");
	
	KeywordBean kBean = null;
	KeywordMng kmgr = new KeywordMng();
	

	
	if(mode.equals("delete")){
		if(kmgr.delKeyword(checkIds))
		{
%>
			 <script type="text/javascript">
			 	alert('삭제하였습니다.');
			 	parent.loadList();
			 	window.close();	
			 </script>
<%
		}else{
%>
			 <script type="text/javascript">
			 	alert('1000개이상 삭제 할 수 없습니다.');
			 	window.close();			 	
			 </script>
<%
		}
	}else if(mode.equals("company")){
		if(type2.equals("add"))
		{
			
			
			String[] arTarget = pr.getStringArr("k_seq_L");
			if(arTarget != null && arTarget.length > 0){
				for(int i =0; i < arTarget.length; i++){
					System.out.println(arTarget.length);
					kmgr.InsertMapkeyword(XpList, arTarget[i]);						
				}
			}
			 
%>
			 <script type="text/javascript">	
				parent.showDataList('company', "left");
			 	parent.showDataList('company', "right");
			 </script>
<%
		}else if(type2.equals("del")){
			
			String[] arTarget = pr.getStringArr("k_seq_R");
			if(arTarget != null && arTarget.length > 0){
				for(int i =0; i < arTarget.length; i++){
					kmgr.DeleteMapkeyword(XpList, arTarget[i]);						
				}
			}
%>
			 <script type="text/javascript">
			 	parent.showDataList('company', "left");
			 	parent.showDataList('company', "right");			 	
			 </script>
<%
		}
	}else if(mode.equals("mcompany")){
		if(type2.equals("add"))
		{
			
			
			String[] arTarget = pr.getStringArr("k_seq_L");
			if(arTarget != null && arTarget.length > 0){
				for(int i =0; i < arTarget.length; i++){
					System.out.println(arTarget.length);
					kmgr.InsertMapkeyword(kSeq, arTarget[i]);						
				}
			}
			 
%>
			 <script type="text/javascript">	
				parent.showDataList('mcompany', "left");
			 	parent.showDataList('mcompany', "right");
			 </script>
<%
		}else if(type2.equals("del")){
			
			String[] arTarget = pr.getStringArr("k_seq_R");
			if(arTarget != null && arTarget.length > 0){
				for(int i =0; i < arTarget.length; i++){
					kmgr.DeleteMapkeyword(kSeq, arTarget[i]);						
				}
			}
%>
			 <script type="text/javascript">
			 	parent.showDataList('mcompany', "left");
			 	parent.showDataList('mcompany', "right");			 	
			 </script>
<%
		}
	}else if(mode.equals("product")){
		if(type2.equals("add")){
			
			String[] arTarget = pr.getStringArr("s_seq_L");
			if(arTarget != null && arTarget.length > 0){
				for(int i =0; i < arTarget.length; i++){
					kmgr.InsertMapkeyword(XpList, arTarget[i]);						
				}
			}
%>
			 <script type="text/javascript">
			 	parent.showDataList('product', "left");
			 	parent.showDataList('product', "right");	
			 </script>
<%
		}else if(type2.equals("del")){
			
			
			String[] arTarget = pr.getStringArr("s_seq_R");
			if(arTarget != null && arTarget.length > 0){
				for(int i =0; i < arTarget.length; i++){
					kmgr.DeleteMapkeyword(XpList, arTarget[i]);						
				}
			}
%>
			 <script type="text/javascript">
			 	parent.showDataList('product', "left");
			 	parent.showDataList('product', "right");			 	
			 </script>
<%
		}
	}else if(mode.equals("mproduct")){
		if(type2.equals("add")){
			
			String[] arTarget = pr.getStringArr("s_seq_L");
			if(arTarget != null && arTarget.length > 0){
				for(int i =0; i < arTarget.length; i++){
					kmgr.InsertMapkeyword(kSeq, arTarget[i]);						
				}
			}
%>
			 <script type="text/javascript">
			 	parent.showDataList('mproduct', "left");
			 	parent.showDataList('mproduct', "right");	
			 </script>
<%
		}else if(type2.equals("del")){
			
			
			String[] arTarget = pr.getStringArr("s_seq_R");
			if(arTarget != null && arTarget.length > 0){
				for(int i =0; i < arTarget.length; i++){
					kmgr.DeleteMapkeyword(kSeq, arTarget[i]);						
				}
			}
%>
			 <script type="text/javascript">
			 	parent.showDataList('mproduct', "left");
			 	parent.showDataList('mproduct', "right");			 	
			 </script>
<%
		}
	}
%>