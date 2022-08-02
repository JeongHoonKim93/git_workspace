<%@ page contentType = "text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ page import="risk.admin.classification.classificationMgr,
				 risk.admin.classification.clfBean,
				 risk.util.DateUtil,
                 risk.util.ParseRequest,
                 java.util.List" 
%>
<%@ include file="../../inc/sessioncheck.jsp" %>
<%

/*     DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    
    classificationMgr cm = new classificationMgr();
    List cmList = null;
    String script_str = "";
	
	int itype = pr.getInt("itype",0);
	int icode = pr.getInt("icode",0);
	String clf_name = pr.getString("clf_name","");
	String mode = pr.getString("mode","");
	String icSeqList = pr.getString("icSeqList","");
 */	
	
    DateUtil        du = new DateUtil();
    ParseRequest    pr = new ParseRequest(request);
    
    classificationMgr cm = new classificationMgr();
    List cmList = null;
    String script_str1 = "";
    String script_str2 = "";
	
	int itype = pr.getInt("itype",0);
	int icode = pr.getInt("icode",0);
	int iptype = pr.getInt("iptype",0);
	int ipcode = pr.getInt("ipcode",0);
	int level = pr.getInt("level",0);
	
	String ic_seq = pr.getString("ic_seq","");
	String clf_name = pr.getString("clf_name","");
	String mode = pr.getString("mode","");
	String icSeqList = pr.getString("icSeqList","");
	String ic_name = pr.getString("ic_name");
	
	System.out.println("1itype = "+itype);
	System.out.println("icode = "+icode);
	System.out.println("iptype = "+iptype);
	System.out.println("ipcode = "+ipcode);
	System.out.println("ic_seq = "+ic_seq);
	System.out.println("1level = "+level);
	
	if( mode.equals("add") ) {
		
		if( cm.InsertClf_test( itype, icode, iptype, ipcode, level, clf_name, SS_M_NO ) ) {
			
			//script_str1 = "parent.frm_menu.clickItem("+ic_seq+", 'item"+ic_seq+"','img"+ic_seq+"', "+ itype +","+ icode +","+ iptype+","+ipcode+",1,"+level +")\n";
			
			script_str1 = "parent.frm_menu.clickItem("+ic_seq+", 'item"+ic_seq+"','img"+ic_seq+"', "+ itype +","+ icode +","+ iptype+","+ipcode+",1,"+level +")\n";
		 
			script_str2 = "location.href = 'frm_classification_test_detail.jsp?type="+itype+"&code="+icode+"&ptype="+iptype+"&pcode="+ipcode+"&level="+level+"'; \n";
			
			out.println("  ");
		}else {
			script_str1 = "parent.frm_menu.location = 'frm_classification_test_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			out.println(" 입력 실패 ");
		}
		
		//out.println("itype = "+itype);
		//out.println("icode = "+icode);
		//out.println("clf_name = "+clf_name);
		
/* 		if( itype > 0 ) {
			if( cm.InsertClf( itype, icode, clf_name, SS_M_NO ) ) {
				script_str = "parent.frm_menu.location = 'frm_test_classification.jsp?itype="+itype+"&icode="+icode+"';	\n"
							 +"parent.frm_detail.location = 'frm_classification_test_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
				out.println("  ");
			}else {
				script_str = "parent.frm_menu.location = 'frm_classification_test_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
				out.println(" 입력 실패 ");
			}
		}
 */	
 
 
 	}
	
/* 	else if( mode.equals("del") ) {
		//out.println("icSeqList = "+icSeqList);
		if( cm.DelClf(icSeqList) ) {
			script_str1 = "parent.frm_menu.location = 'frm_test_classification.jsp?itype="+itype+"&icode="+icode+"';	\n"
			 			 +"parent.frm_detail.location = 'frm_classification_test_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			out.println("  ");
		}else {
			out.println(" 삭제 실패 ");
		}
		
	}
 */ 
 
 else if( mode.equals("del") ) {
		if( cm.DelClf(icSeqList) ) {
			
			
			script_str1 = "parent.frm_menu.clickItem("+ic_seq+", 'item"+ic_seq+"','img"+ic_seq+"', "+ itype +","+ icode +","+ iptype+","+ipcode+",1,"+level +")\n";
			
			
//			script_str = "parent.frm_menu.location = 'frm_classification.jsp?itype="+itype+"&icode="+icode+"';	\n"
	//		 			 +"parent.frm_detail.location = 'frm_classification_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			
			out.println("  ");
		}else {
			out.println(" 삭제 실패 ");
		}
		
	}
 
	else if( mode.equals("up") ) {
		if( cm.MoveClf( mode, itype, icode) ) {
			script_str1 = "parent.frm_menu.location = 'frm_test_classification.jsp?itype="+itype+"&icode="+icode+"';	\n"
						 +"parent.frm_detail.location = 'frm_classification_test_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			out.println("  ");
			System.out.println("script_str1>>"+script_str1);
		}else {
			script_str1 = "parent.frm_menu.location = 'frm_classification_test_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			out.println(" 이동 실패 ");
		}
	}else if( mode.equals("down") ) {
		if( cm.MoveClf( mode, itype, icode) ) {
			script_str1 = "parent.frm_menu.location = 'frm_test_classification.jsp?itype="+itype+"&icode="+icode+"';	\n"
						 +"parent.frm_detail.location = 'frm_classification_test_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			out.println("  ");
		}else {
			script_str1 = "parent.frm_menu.location = 'frm_classification_test_detail.jsp?itype="+itype+"&icode="+icode+"';	\n";
			out.println(" 이동 실패 ");
		}
	}
	
/*  	else if( mode.equals("update") ) {
		cm.ModifyName_test(ic_seq, clf_name);
		
		script_str1 = "parent.opener.parent.frm_detail.location = 'frm_classification_test_detail.jsp?itype="+itype+"&icode="+icode+"';	parent.close();\n";
		
	}
 */ 	
 
 
  	else if(mode.equals("update")){
		cm.ModifyName(ic_seq, ic_name);
		script_str1 = "parent.opener.location.reload();parent.window.close();";
	}
	
	
%>
<SCRIPT LANGUAGE="JavaScript">

	<%=script_str1%>
	<%=script_str2%>
	
</SCRIPT>