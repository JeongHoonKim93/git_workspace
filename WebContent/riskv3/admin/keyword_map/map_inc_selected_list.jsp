<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.ArrayList,
                 risk.admin.keyword.KeywordBean,
                 risk.admin.keyword.KeywordMng,
                 risk.util.ParseRequest" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%
	ParseRequest pr = new ParseRequest(request);
	KeywordBean kBean = null;
	KeywordMng kmgr = new KeywordMng();
	pr.printParams();
		
	String mode = pr.getString("mode","");
	String type = pr.getString("type","");
	String kSeq = pr.getString("kSeq","");
	String XpList = pr.getString("XpList","");
	
	ArrayList ar_seq = null;
	
	if(mode.equals("mcompany")){
		if(type.equals("left")){
			ar_seq = kmgr.getComKeyword(kSeq, KeywordBean.Type.LEFT);	
		}else if(type.equals("right")){
			ar_seq = kmgr.getComMapKeyword(kSeq, KeywordBean.Type.RIGHT);	
		}
	}else if (mode.equals("mproduct")){
		if(type.equals("left")){
			ar_seq = kmgr.getProKeyword(kSeq, KeywordBean.Type.LEFT);	
		}else if(type.equals("right")){
			ar_seq = kmgr.getProMapKeyword(kSeq, KeywordBean.Type.RIGHT);	
		}
	}	if(mode.equals("company")){
		if(type.equals("left")){
			ar_seq = kmgr.getComKeyword(XpList, KeywordBean.Type.LEFT);	
		}else if(type.equals("right")){
			ar_seq = kmgr.getComMapKeyword(XpList, KeywordBean.Type.RIGHT);	
		}
	}else if (mode.equals("product")){
		if(type.equals("left")){
			ar_seq = kmgr.getProKeyword(XpList, KeywordBean.Type.LEFT);	
		}else if(type.equals("right")){
			ar_seq = kmgr.getProMapKeyword(XpList, KeywordBean.Type.RIGHT);	
		}
	}

%>

<%
if(mode.equals("mcompany")){
	if(type.equals("left")){
%>

	<select name="k_seq_L"  multiple style="width:310px;height:200px;" class="t">
	<%                        	 	
	for(int i = 0; i < ar_seq.size(); i++){
		kBean = (KeywordBean)ar_seq.get(i);
		out.println("<option value='"+ kBean.getIc_seq() +"'>"+ kBean.getCom_name() +"</option>");
	}
	%>
	</select>	
	
<%		
	}else if(type.equals("right")){
%>		
	
	<select name="k_seq_R"  multiple style="width:310px;height:200px;" class="t">
	<%                        	 	
	for(int i = 0; i < ar_seq.size(); i++){		
		kBean = (KeywordBean)ar_seq.get(i);
		out.println("<option value='"+ kBean.getIc_seq() +"'>"+ kBean.getCom_name() +"</option>");
	}
	%>
	</select>
		
<%		
	}
}else if(mode.equals("company")){
	if(type.equals("left")){
%>

	<select name="k_seq_L"  multiple style="width:310px;height:200px;" class="t">
	<%                        	 	
	for(int i = 0; i < ar_seq.size(); i++){
		kBean = (KeywordBean)ar_seq.get(i);
		out.println("<option value='"+ kBean.getIc_seq() +"'>"+ kBean.getCom_name() +"</option>");
	}
	%>
	</select>	
	
<%		
	}else if(type.equals("right")){
%>		
	
	<select name="k_seq_R"  multiple style="width:310px;height:200px;" class="t">
	<%                        	 	
	for(int i = 0; i < ar_seq.size(); i++){		
		kBean = (KeywordBean)ar_seq.get(i);
		out.println("<option value='"+ kBean.getIc_seq() +"'>"+ kBean.getCom_name() +"</option>");
	}
	%>
	</select>
		
<%		
	}
}else if (mode.equals("mproduct")){
	if(type.equals("left")){
%>	

	<select name="s_seq_L"  multiple style="width:310px;height:200px;" class="t">
	<%                        	 	
	for(int i = 0; i < ar_seq.size(); i++){		
		kBean = (KeywordBean)ar_seq.get(i);
		out.println("<option value='"+ kBean.getIc_seq() +"'>"+ kBean.getProduct_name() +"</option>");
	}
	%>
	</select>
		
<%		
	}else if(type.equals("right")){
%>		

	<select name="s_seq_R"  multiple style="width:310px;height:200px;" class="t">
	<%                        	 	
	for(int i = 0; i < ar_seq.size(); i++){		
		kBean = (KeywordBean)ar_seq.get(i);
		out.println("<option value='"+ kBean.getIc_seq() +"'>"+ kBean.getProduct_name() +"</option>");
	}
	%>
	</select>
		
<%		
	}
}else if (mode.equals("product")){
	if(type.equals("left")){
%>	

	<select name="s_seq_L"  multiple style="width:310px;height:200px;" class="t">
	<%                        	 	
	for(int i = 0; i < ar_seq.size(); i++){		
		kBean = (KeywordBean)ar_seq.get(i);
		out.println("<option value='"+ kBean.getIc_seq() +"'>"+ kBean.getProduct_name() +"</option>");
	}
	%>
	</select>
		
<%		
	}else if(type.equals("right")){
%>		

	<select name="s_seq_R"  multiple style="width:310px;height:200px;" class="t">
	<%                        	 	
	for(int i = 0; i < ar_seq.size(); i++){		
		kBean = (KeywordBean)ar_seq.get(i);
		out.println("<option value='"+ kBean.getIc_seq() +"'>"+ kBean.getProduct_name() +"</option>");
	}
	%>
	</select>
		
<%		
	}
}
%>


