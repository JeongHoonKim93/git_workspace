<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
				risk.admin.ex_site.ExSiteMng,
				risk.util.ParseRequest" 
%>
<% 
	System.out.println("■-------------popUpContents_ex_site_InsertUpdate.jsp----------------■");
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	String mode = pr.getString("mode");
	String s_site = pr.getString("S_SITE");
	String s_seq = pr.getString("s_seq","");
	String ex_seq = pr.getString("ex_seq","");
	String ekSeq = pr.getString("ekSeq","");
	String ex_url = pr.getString("ex_url","");
	String siteURL = pr.getString("siteURL","");
	
	int result = 0;
	String Scripture  = "";
	
	ExSiteMng eSMng = new ExSiteMng();
			
	if(s_seq.equals("")){
		s_seq = s_site;
	}
	if(mode.equals("insert")){
		//textArea의 있는 값과, s_seq, sg_seq, S_SITE번호 까지 해서 getSiteList에서 받아오낟.
		//그리고 textArea값과 getSiteList를 넘긴다. insert한다.
		result = eSMng.insertExSite(s_seq, ex_url);
		if(result > -1){
			Scripture = "alert('저장하였습니다.'); opener.loadList(); window.close();";
			//System.out.println(Scripture);
		}else{
			Scripture = "alert('실패하였습니다.'); opener.loadList(); window.close();";
		}
		System.out.println(result);
	}else if(mode.equals("update")){
		
		result = eSMng.updateExSite(ekSeq, ex_url);
		
		if(result > 0){
			Scripture = "alert('수정하였습니다.'); opener.loadList(); window.close();";
			//System.out.println(Scripture);
		}else{
			Scripture = "alert('수정에 실패하였습니다.'); opener.loadList(); window.close();";
		}
		
	}else if(mode.equals("delete")){
		String checkIds = pr.getString("checkIds");
		result = eSMng.deleteExSite(checkIds);
		if(result > -1){
			Scripture = "alert('삭제하였습니다.');parent.loadList();window.close();";
			//System.out.println(Scripture);
		}else{
			Scripture = "alert('실패하였습니다.');parent.loadList();window.close();";
		}	
	}
	
	//popup.openByPost('managerForm','admin_ex_site_edit.jsp',400,270,false,false,false,'trendPop');
%>

<script type="text/javascript">
	
	<%=Scripture%>
	
</script>
