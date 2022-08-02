<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList,
                 risk.util.Log,
                 risk.util.StringUtil,
                 risk.util.ParseRequest,
                 risk.util.DateUtil,
                 java.util.HashMap,                              
                 risk.admin.site.SitegroupBean,
                 risk.search.siteDataInfo,                
                 risk.search.userEnvMgr,
                 risk.search.userEnvInfo,
                 risk.admin.keyword.*,               
                 risk.mobile.AlimiSettingBean,
				 risk.mobile.AlimiSettingDao,
				 risk.statistics.StatisticsBean,
				 risk.statistics.StatisticsMgr				 
                 "
%>
<%
	ParseRequest pr = new ParseRequest(request);
	pr.printParams();
	DateUtil du = new DateUtil();
	long diffDay = 0;
	int intDiffDay = 0;
	String first = "";
	String last = "";
	
	String[] date  = null;
	ArrayList keywordList = new ArrayList();
	ArrayList keywordData = new ArrayList();
	
	String sDateFrom = pr.getString("sDateFrom");
	String sDateTo = pr.getString("sDateTo");
	String k_xp = pr.getString("k_xp");
	String k_yp = pr.getString("k_yp");
	String k_zp = pr.getString("k_zp");
	
	first = du.getDate(sDateFrom,"yyyyMMdd");
	last = du.getDate(sDateTo,"yyyyMMdd");
	
	diffDay = -du.DateDiff("yyyyMMdd",first,last);
	intDiffDay = Integer.parseInt(String.valueOf(diffDay));
	
	if(intDiffDay>0)
	{
		date  = new String[intDiffDay+1];
		for(int i = 0; i<intDiffDay+1; i++)
		{
			date[i] = du.getDate(du.addDay(first, i , "yyyyMMdd"),"MM.dd");										
		}
	}
	
	int[] totalDateCnt = new int[date.length];
	int[] mediaDateCnt = new int[date.length];
	int[] indiDateCnt = new int[date.length];
	
	
	
	KeywordMng kMng = new KeywordMng();
	StatisticsMgr sMgr = new StatisticsMgr();
	if(!k_xp.equals("")&&!k_yp.equals("") && !k_zp.equals("")){
		//keywordList = kMng.getKeywordInfo(k_xp,k_yp,"2");
		
		keywordList = sMgr.getSiteGroup();
		keywordData = sMgr.getKeywordData(sDateFrom,sDateTo,k_xp,k_yp,k_zp);
	}
	
	
	
 %>
<table width="<%=(intDiffDay*65+200)%>" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td colspan="2" bgcolor="#7CA5DD"><img src="../../images/statistics/brank.gif" width="1" height="2"></td>
      </tr>
      <tr>
        <td colspan="2" bgcolor="#CFCFCF"><table width="<%=(intDiffDay*65+200)%>"  border="0" cellspacing="1" cellpadding="1">
            <tr>
              <td width="150" height="30" align="center" background="../../images/statistics/statis_table_bg01.gif" style="padding: 3px 0px 0px 0px;font-weight: bold;">키워드</td>
<%
			for(int i=0; i<date.length; i++){
%>            
              <td width="75" align="center" background="../../images/statistics/statis_table_bg01.gif" class="menu_black" style="padding: 3px 0px 0px 0px; font-weight: bold;"><%=date[i]%></td>
<%
			}
%>                        
            </tr>
<%
			for(int i=0;i<keywordList.size();i++){
				StatisticsBean keyInfo = new StatisticsBean();
				keyInfo = (StatisticsBean)keywordList.get(i);
				
				
%>       
            <tr>
              <td width="150" align="center" bgcolor="#FFFFFF" class="menu_black" style="padding: 3px 0px 0px 0px;"><%=keyInfo.getSg_name()%></td>
          	  
<%				
				String cnt = "0";
				int intCnt = 0;
				for(int j=0; j<date.length; j++){
				
					for(int k=0; k<keywordData.size(); k++){
						StatisticsBean sBean = new StatisticsBean();
						sBean = (StatisticsBean)keywordData.get(k);
						
						cnt = "0";
						
						//if(keyInfo.getKGxp().equals(sBean.getK_xp()) && keyInfo.getKGyp().equals(sBean.getK_yp()) && keyInfo.getKGzp().equals(sBean.getK_zp()) && date[j].equals(sBean.getDate()))
						if(keyInfo.getSg_seq().equals(sBean.getSg_seq()) && date[j].equals(sBean.getDate())){
							cnt = sBean.getS_cnt(); 
							intCnt = Integer.parseInt(sBean.getS_cnt());
							//전체 합계
							totalDateCnt[j] += intCnt;  
							
							if(keyInfo.getSg_seq().equals("3") || keyInfo.getSg_seq().equals("28")){
								//언론합계
								mediaDateCnt[j] += intCnt;  	
							}else{
								//개인합계
								indiDateCnt[j] += intCnt;  
							}
							
							break;
						}				
					}
					
					out.print("<td width=\"75\" height=\"30\" align=\"center\" bgcolor=\"#FFFFFF\" style=\"padding: 3px 0px 0px 0px;\">"+cnt+"</td>");
					
				}
%>              
	  		  <td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"></td>
            </tr>   
<%
			}
%>       
		<tr>
            <td width="150" align="center" bgcolor="#FFE4B5" class="menu_black" style="padding: 3px 0px 0px 0px;"><strong>언론 합계</strong></td>
            <%
            	for(int j=0; j<date.length; j++){
            		out.print("<td width=\"75\" height=\"30\" align=\"center\" bgcolor=\"#FFE4B5\" style=\"padding: 3px 0px 0px 0px;\"><strong>"+mediaDateCnt[j]+"</strong></td>");
            	}
            %>
	  		<td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"></td>
        </tr>
        <tr>
            <td width="150" align="center" bgcolor="#FFE4B5" class="menu_black" style="padding: 3px 0px 0px 0px;"><strong>개인 합계</strong></td>
            <%
            	for(int j=0; j<date.length; j++){
            		out.print("<td width=\"75\" height=\"30\" align=\"center\" bgcolor=\"#FFE4B5\" style=\"padding: 3px 0px 0px 0px;\"><strong>"+indiDateCnt[j]+"</strong></td>");
            	}
            %>
	  		<td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"></td>
        </tr>
		<tr>
            <td width="150" align="center" bgcolor="#FFBEBE" class="menu_black" style="padding: 3px 0px 0px 0px;"><strong>전체 합계</strong></td>
            <%
            	for(int j=0; j<date.length; j++){
            		out.print("<td width=\"75\" height=\"30\" align=\"center\" bgcolor=\"#FFBEBE\" style=\"padding: 3px 0px 0px 0px;\"><strong>"+totalDateCnt[j]+"</strong></td>");
            	}
            %>
	  		<td align="center" bgcolor="#FFFFFF" style="padding: 3px 0px 0px 0px;"></td>
        </tr>   
        </table></td>
      </tr>
</table>