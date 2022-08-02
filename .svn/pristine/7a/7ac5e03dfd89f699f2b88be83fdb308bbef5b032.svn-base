<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import=" risk.util.DateUtil
                 ,risk.util.ParseRequest
                 ,risk.util.StringUtil
                 ,risk.admin.classification.*
                 ,java.util.ArrayList
                 ,risk.tree.TreeBean
                 ,risk.tree.TreeDao
                 " 
%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();

	ArrayList arrTreeData = new ArrayList();
	StringUtil su = new StringUtil();
	DateUtil du = new DateUtil();
	TreeDao treeDao = new TreeDao();
	TreeBean treeBean = new TreeBean();
	classificationMgr  clfMgr  = new classificationMgr();
	clfBean clfbean = new  clfBean();
	
	ArrayList arrData = new ArrayList();
	
	String typeOfclass = "";
	String queryId = pr.getString("queryId");	
	
	try{
	if(queryId.equals("CLASSIFICATION")){
		
		
		String	type = pr.getString("type");
		String code = pr.getString("code");
		String ptype = pr.getString("ptype");
		String pcode = pr.getString("pcode");
		int level = pr.getInt("level",0);
			

		if(level ==0 ) level = 1;
		
		arrData = treeDao.getCodeData(type, code, ptype, pcode, level );
		
		if(level == 1){
			
		out.println("<ul class='top'> \r\n");
			for(int i=0 ; i<arrData.size(); i++){
				clfbean = (clfBean)arrData.get(i);			
%>
			<li id="item<%=clfbean.getIc_seq()%>" title="<%=clfbean.getIc_name()%>"><p id="img<%=clfbean.getIc_seq()%>" class="closedFolder" onclick="clickItem(<%=clfbean.getIc_seq()%>, 'item<%=clfbean.getIc_seq()%>','img<%=clfbean.getIc_seq()%>',<%=clfbean.getIc_type()%>,<%=clfbean.getIc_code()%>,<%=clfbean.getIc_ptype()%>,<%=clfbean.getIc_pcode()%>, <%=clfbean.getChildCnt()%>,2)"><%=clfbean.getIc_name()%></p></li>
<%		
			}	
		out.println("</ul> \r\n");
		
		}else if(level == 2){
			
		out.println("<ul>");
			for(int i=0 ; i<arrData.size(); i++){
				clfbean = (clfBean)arrData.get(i);			
%>
			<li id="item<%=clfbean.getIc_seq()%>" title="<%=clfbean.getIc_name()%>"><p id="img<%=clfbean.getIc_seq()%>" class="closedFolder" onclick="clickItem(<%=clfbean.getIc_seq()%>, 'item<%=clfbean.getIc_seq()%>','img<%=clfbean.getIc_seq()%>',<%=clfbean.getIc_type()%>,<%=clfbean.getIc_code()%>,<%=clfbean.getIc_ptype()%>,<%=clfbean.getIc_pcode()%>, <%=clfbean.getChildCnt()%>,3)"><%=clfbean.getIc_name()%></p></li>
<%		
			}	
		out.println("</ul> \r\n");
		
		}else if(level == 3){
			
			out.println("<ul> \r\n");
				for(int i=0 ; i<arrData.size(); i++){
					clfbean = (clfBean)arrData.get(i);
					
					
					
				%>
				<li id="item<%=clfbean.getIc_seq()%>" title="<%=clfbean.getIc_name()%>">
					<p id="img<%=clfbean.getIc_seq()%>" class="closedFolder" onclick="clickItem(<%=clfbean.getIc_seq()%>, 'item<%=clfbean.getIc_seq()%>','img<%=clfbean.getIc_seq()%>',<%=clfbean.getIc_type()%>,<%=clfbean.getIc_code()%>,<%=clfbean.getIc_ptype()%>,<%=clfbean.getIc_pcode()%>, <%=clfbean.getChildCnt()%>,4)"><%=clfbean.getIc_name()%></p></li>
				<%		
				}	
			out.println("</ul> \r\n");
			
		}else if(level == 4){
			
			out.println("<ul> \r\n");
				for(int i=0 ; i<arrData.size(); i++){
					clfbean = (clfBean)arrData.get(i);
					
					
					
				%>
				<li id="item<%=clfbean.getIc_seq()%>" title="<%=clfbean.getIc_name()%>">
					<p id="img<%=clfbean.getIc_seq()%>" class="closedFolder" onclick="clickItem(<%=clfbean.getIc_seq()%>, 'item<%=clfbean.getIc_seq()%>','img<%=clfbean.getIc_seq()%>',<%=clfbean.getIc_type()%>,<%=clfbean.getIc_code()%>,<%=clfbean.getIc_ptype()%>,<%=clfbean.getIc_pcode()%>, <%=clfbean.getChildCnt()%>,5)"><%=clfbean.getIc_name()%></p></li>
				<%		
				}	
			out.println("</ul> \r\n");
			
		}else if(level == 5){
			
		out.println("<ul> \r\n");
			for(int i=0 ; i<arrData.size(); i++){
				clfbean = (clfBean)arrData.get(i);			
			%>
			<li id="item<%=clfbean.getIc_seq()%>" title="<%=clfbean.getIc_name()%>">
				<p id="img<%=clfbean.getIc_seq()%>" class="closedFolder" onclick="clickItem(<%=clfbean.getIc_seq()%>, 'item<%=clfbean.getIc_seq()%>','img<%=clfbean.getIc_seq()%>',<%=clfbean.getIc_type()%>,<%=clfbean.getIc_code()%>,<%=clfbean.getIc_ptype()%>,<%=clfbean.getIc_pcode()%>, <%=clfbean.getChildCnt()%>,6)"><%=clfbean.getIc_name()%></p></li>
			<%		
			}	
		out.println("</ul> \r\n");
		
		}else if(level == 6){
			
		out.println("<ul> \r\n");
			for(int i=0 ; i<arrData.size(); i++){
				clfbean = (clfBean)arrData.get(i);			
			%>
			<li id="item<%=clfbean.getIc_seq()%>" title="<%=clfbean.getIc_name()%>">
				<p id="img<%=clfbean.getIc_seq()%>" class="leaf" onclick="clickItem(<%=clfbean.getIc_seq()%>, 'item<%=clfbean.getIc_seq()%>','img<%=clfbean.getIc_seq()%>',<%=clfbean.getIc_type()%>,<%=clfbean.getIc_code()%>,<%=clfbean.getIc_ptype()%>,<%=clfbean.getIc_pcode()%>, <%=clfbean.getChildCnt()%>,7)"><%=clfbean.getIc_name()%></p></li>
			<%		
			}	
		out.println("</ul> \r\n");
		
		}
	}if(queryId.equals("KEYWORD")){
	
		String level = pr.getString("level");	
		String xp = pr.getString("xp");
		String yp = pr.getString("yp");
		
		arrTreeData = treeDao.getKeywordTreeData(queryId,level,xp,yp);
		
		if(level.equals("1")){
%>
		<ul>
<%
			for(int i=0;i<arrTreeData.size();i++)
			{
				treeBean = new TreeBean();
				treeBean = (TreeBean)arrTreeData.get(i);
%>
			<li id="item<%=treeBean.getSeq()%>" title="<%=treeBean.getName()%>"><p id="img<%=treeBean.getSeq()%>" class="closedFolder" onclick="clickItem('2','item<%=treeBean.getSeq()%>','img<%=treeBean.getSeq()%>','<%=treeBean.getValue1()%>','<%=treeBean.getValue2()%>','<%=treeBean.getValue3()%>',<%=treeBean.getChildCount()%>);"><%=su.cutString(treeBean.getName(),12,"...")%></p></li>
<%
			}
%>
		</ul>	
<%
		}else if(level.equals("2")){
%>		
		<ul>
<%
			for(int i=0;i<arrTreeData.size();i++)
			{
				treeBean = new TreeBean();
				treeBean = (TreeBean)arrTreeData.get(i);
%>
			<li id="item<%=treeBean.getSeq()%>" title="<%=treeBean.getName()%>"><p id="img<%=treeBean.getSeq()%>" class="closedFolder" onclick="clickItem('3','item<%=treeBean.getSeq()%>','img<%=treeBean.getSeq()%>','<%=treeBean.getValue1()%>','<%=treeBean.getValue2()%>','<%=treeBean.getValue3()%>',<%=treeBean.getChildCount()%>);"><%=su.cutString(treeBean.getName(),10,"...")%></p></li>
<%
			}
%>
		</ul>	
<%
		}else if(level.equals("3")){
%>
		<ul>
<%
			for(int i=0;i<arrTreeData.size();i++)
			{
				treeBean = new TreeBean();
				treeBean = (TreeBean)arrTreeData.get(i);
				
				if(treeBean.getType().equals("1")){
					typeOfclass = "fileA";	
				}else if(treeBean.getType().equals("2")){
					typeOfclass = "fileN";	
				}else if(treeBean.getType().equals("3")){
					typeOfclass = "fileP";	
				}else if(treeBean.getType().equals("4")){
					typeOfclass = "fileI";	
				}else if(treeBean.getType().equals("6")){
					typeOfclass = "fileP2";	
				}else if(treeBean.getType().equals("7")){
					typeOfclass = "fileP3";	
				}			
				
			
%>
			<li id="item<%=treeBean.getSeq()%>" title="<%=treeBean.getName()%>"><p id="img<%=treeBean.getSeq()%>" class="<%=typeOfclass%>" onclick="clickKeyword('item<%=treeBean.getSeq()%>','img<%=treeBean.getSeq()%>','<%=treeBean.getValue1()%>','<%=treeBean.getValue2()%>','<%=treeBean.getValue3()%>');"><%=su.cutString(treeBean.getName(),9,"...")%></p></li>
<%
			}
%>
		</ul>	
<%
		}
	}else if(queryId.equals("SEARCHKEYWORD")){
		
		String level = pr.getString("level");	
		String xp = pr.getString("xp");
		String yp = pr.getString("yp");
		String fromDate =  pr.getString("fromDate");
		String toDate = pr.getString("toDate");
	
		treeDao.getMaxMinNo(fromDate,toDate);
		arrTreeData = treeDao.getKeywordTreeData(queryId,level,xp,yp,fromDate,toDate);
				
		if(level.equals("1")){
%>
		<ul class="top">
<%
			for(int i=0;i<arrTreeData.size();i++)
			{
				treeBean = new TreeBean();
				treeBean = (TreeBean)arrTreeData.get(i);
%>
			<li id="item<%=treeBean.getSeq()%>" title="<%=treeBean.getName()%>">
				<p id="img<%=treeBean.getSeq()%>" class="closedFolder" onclick="clickItem('2','item<%=treeBean.getSeq()%>','img<%=treeBean.getSeq()%>','<%=treeBean.getValue1()%>','<%=treeBean.getValue2()%>','<%=treeBean.getValue3()%>',<%=treeBean.getChildCount()%>);"><%=su.cutString(treeBean.getName(),10,"...")%>&nbsp;&nbsp;<font color="0078FF">[<%=treeBean.getValue4()%>]</font></p>
			</li>
<%
			}
%>
		</ul>	
<%
		}else if(level.equals("2")){
%>
		<ul>
<%
			for(int i=0;i<arrTreeData.size();i++)
			{
				treeBean = new TreeBean();
				treeBean = (TreeBean)arrTreeData.get(i);
%>
			<li id="item<%=treeBean.getSeq()%>" title="<%=treeBean.getName()%>">
				<p id="img<%=treeBean.getSeq()%>" class="closedFolder" onclick="clickItem('3','item<%=treeBean.getSeq()%>','img<%=treeBean.getSeq()%>','<%=treeBean.getValue1()%>','<%=treeBean.getValue2()%>','<%=treeBean.getValue3()%>',<%=treeBean.getChildCount()%>);"><%=su.cutString(treeBean.getName(),8,"...")%>&nbsp;&nbsp;<font color="0078FF">[<%=treeBean.getValue4()%>]</font></p>
			</li>
<%
			}
%>
		</ul>	
<%
		}else if(level.equals("3")){
%>
		<ul>
<%
			for(int i=0;i<arrTreeData.size();i++)
			{
				treeBean = new TreeBean();
				treeBean = (TreeBean)arrTreeData.get(i);
				
				if(treeBean.getType().equals("1")){
					typeOfclass = "fileA";	
				}else if(treeBean.getType().equals("2")){
					typeOfclass = "fileN";	
				}else if(treeBean.getType().equals("3")){
					typeOfclass = "fileP";	
				}else if(treeBean.getType().equals("4")){
					typeOfclass = "fileI";	
				}else if(treeBean.getType().equals("6")){
					typeOfclass = "fileP2";	
				}else if(treeBean.getType().equals("7")){
					typeOfclass = "fileP3";	
				}	
%>
			<li id="item<%=treeBean.getSeq()%>" title="<%=treeBean.getName()%>">
				<p id="img<%=treeBean.getSeq()%>" class="<%=typeOfclass%>" onclick="clickKeyword('item<%=treeBean.getSeq()%>','img<%=treeBean.getSeq()%>','<%=treeBean.getValue1()%>','<%=treeBean.getValue2()%>','<%=treeBean.getValue3()%>');"><%=su.cutString(treeBean.getName(),6,"...")%>&nbsp;&nbsp;<font color="0078FF">[<%=treeBean.getValue4()%>]</font></p>
			</li>
<%
			}
%>
		</ul>	
<%
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
%>

