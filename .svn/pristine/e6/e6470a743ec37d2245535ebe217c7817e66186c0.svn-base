package risk.statistics;

import java.io.*;
import java.sql.*;
import java.util.*;

import risk.DBconn.DBconn;
import risk.util.Log;
import risk.util.DateUtil;
import risk.util.StringUtil;
import risk.search.MetaMgr;
import risk.statistics.StatisticsBean;
import risk.admin.site.SiteBean;
import risk.issue.IssueDataBean;

public class StatisticsMgr {
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private ResultSet rs2    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
    
    private StatisticsBean statisticsBean = null;
 	
    
    /**
     * 키워드 그룹별 차트 데이터
     */
	public HashMap getKeywordChartData(String sdate,String stime, String edate,String etime)
	{
		HashMap ChartDataHm = new HashMap();
		DateUtil du = new DateUtil();
		
		int HashKey = 0;
		long diffDay = 0;
		int intDiffDay = 0;
		
		String[] date  = null;
		
		ArrayList typeAChart = new ArrayList();
		HashMap dataA = new HashMap();
		HashMap tempHash = new HashMap();		
				
		String first = "";
		String last = "";
		
		
		try{
			dbconn = new DBconn();			
			dbconn.getDBCPConnection();
			
			first = du.getDate(sdate,"yyyyMMdd");
			last = du.getDate(edate,"yyyyMMdd");
			
			diffDay = -du.DateDiff("yyyyMMdd",first,last);
			intDiffDay = Integer.parseInt(String.valueOf(diffDay));													
	    	
	    	sb = new StringBuffer();
	    	sb.append(" SELECT DATE_FORMAT(KA_REGDATE,'%Y%m%d') KA_REGDATE                                                              \n");
	    	sb.append("        ,K_VALUE                                                                                                                                        \n");
	    	sb.append("        ,K_CNT                                                                                                                                           \n");
	    	sb.append(" FROM KEYWORD_ANALYSIS WHERE KA_REGDATE BETWEEN '"+sdate+" "+stime+"' AND '"+edate+" "+etime+"'  \n");
	    	sb.append("      AND K_YP = 0                                                                                                                                     \n");
	    	sb.append(" ORDER BY KA_REGDATE,K_XP, K_YP, K_ZP                                                                                                                   \n");
																		
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			
			if(intDiffDay>0)
			{
				date  = new String[intDiffDay+1];
				for(int i = 0; i<intDiffDay+1; i++)
				{
					date[i] = du.addDay(first, i , "yyyyMMdd");
					tempHash = new HashMap();
					tempHash.put("1", date[i]);
					
									
					HashKey = 1;
					rs.beforeFirst();
					while(rs.next())
					{						
						HashKey++;
						tempHash.put(String.valueOf(HashKey), "0");
						HashKey++;
						tempHash.put(String.valueOf(HashKey),  rs.getString("K_VALUE"));
					}
					typeAChart.add(tempHash);
				}
			}		
				    	 			    			    		
    		for(int i =0;i<typeAChart.size();i++)
    		{
    			
    			dataA = new HashMap();
    			dataA =(HashMap)typeAChart.get(i);
    			  			
    			HashKey = 1;
				rs.beforeFirst();
    			while(rs.next()){    				
	    			if((rs.getString("KA_REGDATE")).equals((String)dataA.get("1")))
	    			{	    				
	    				HashKey++;
	    				dataA.put(String.valueOf(HashKey), rs.getString("K_CNT"));
	    				HashKey++;
	    				dataA.put(String.valueOf(HashKey), rs.getString("K_VALUE"));	    			   					    				   				
	    			}
    			}
    			
    			typeAChart.set(i, dataA);	    			
    		}	    		
	    		    	
	    	ChartDataHm.put("A",typeAChart);			
		    	    		    	
	    	
	    } catch(SQLException ex) {
	    	Log.writeExpt(ex,ex.getMessage());
	    } catch(Exception ex) {
			Log.writeExpt(ex);
	    } finally {
	    	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	        if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
	    }   	
		return ChartDataHm;
	}
	
	/**
     * 키워드별 정보 건수 TOP 10
     */
	public ArrayList getKeywordData(String sDate, String eDate)
    {
		ArrayList result = new ArrayList();
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" SELECT                                                                                                                                                 \n");
			sb.append("        K_XP                                                                                                                                        \n");
			sb.append("        ,K_YP                                                                                                                                        \n");
			sb.append("        ,K_ZP                                                                                                                                        \n");
			sb.append("        ,K_VALUE                                                                                                                                           \n");
			sb.append("        ,SUM(K_CNT) K_CNT                                                                                                                                      \n");
			sb.append(" FROM KEYWORD_ANALYSIS  \n");			
			sb.append(" WHERE KA_REGDATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'  \n");			
			sb.append(" 	  AND K_YP > 0 AND K_ZP >0  \n");			
			sb.append(" GROUP BY K_XP, K_YP, K_ZP                                                                                                                              \n");
			sb.append(" ORDER BY K_CNT DESC                                                                                                                   \n");
			sb.append(" LIMIT 10                                                                                                                   \n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				statisticsBean = new StatisticsBean();
        		statisticsBean.setK_xp(rs.getString("K_XP"));
        		statisticsBean.setK_yp(rs.getString("K_YP"));
        		statisticsBean.setK_zp(rs.getString("K_ZP"));
        		statisticsBean.setK_value(rs.getString("K_VALUE"));
        		statisticsBean.setK_cnt(rs.getString("K_CNT")); 
        		result.add(statisticsBean);	
			}
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	
	public ArrayList getKeywordData(String sDate, String eDate, String k_xp, String k_yp, String k_zp)
    {
		ArrayList result = new ArrayList();
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT (SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+sDate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO 	\n");
			sb.append("     , (SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+eDate+" 00:00:00' AND '"+eDate+" 23:59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO	\n"); 
			
			String min = "";
			String max = "";
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			if(rs.next()){
				min = rs.getString("MIN_NO");
				max = rs.getString("MAX_NO");
			}
			
			if(min.equals("") || min == null){
				min ="0";
			}
			if(max.equals("") || max == null){
				max ="999999999";
			}
			
			sb = new StringBuffer();
			sb.append("SELECT DATE_FORMAT(A.MD_DATE,'%m.%d') AS DATE		\n");
			sb.append("     , B.SG_SEQ										\n");
			sb.append("     , C.SG_NAME										\n");
			sb.append("     , COUNT(*) AS S_CNT								\n");
			sb.append("  FROM META A										\n");
			sb.append("     , IDX B											\n");
			sb.append("     , SITE_GROUP C									\n");
			sb.append(" WHERE A.MD_SEQ = B.MD_SEQ							\n"); 
			sb.append("   AND B.SG_SEQ = C.SG_SEQ							\n");
			sb.append("   AND A.MD_SEQ BETWEEN "+min+" AND "+max+"			\n");
			sb.append("   AND (B.I_STATUS = 'N' AND B.M_SEQ <> 2)			\n");
			sb.append("   AND B.K_XP = "+k_xp+"								\n");
			sb.append("   AND B.K_YP = "+k_yp+"								\n");
			sb.append("   AND B.K_ZP = "+k_zp+"								\n");
			sb.append(" GROUP BY DATE_FORMAT(A.MD_DATE,'%Y%m%d'), B.SG_SEQ	\n");
			sb.append(" ORDER BY C.SG_SEQ									\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				statisticsBean = new StatisticsBean();
				statisticsBean.setDate(rs.getString("DATE"));
				statisticsBean.setSg_seq(rs.getString("SG_SEQ"));
        		statisticsBean.setSg_name(rs.getString("SG_NAME"));
        		statisticsBean.setS_cnt(rs.getString("S_CNT")); 
        		result.add(statisticsBean);	
			}
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	

	public ArrayList getSiteGroup()
    {
		ArrayList result = new ArrayList();
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" SELECT SG_SEQ, SG_NAME FROM SITE_GROUP \n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			while(rs.next()){
				statisticsBean = new StatisticsBean();
				statisticsBean.setSg_seq(rs.getString("SG_SEQ"));
        		statisticsBean.setSg_name(rs.getString("SG_NAME"));
        		result.add(statisticsBean);
			}
		
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public ArrayList getIssueCode(String type)
    {
		ArrayList result = new ArrayList();
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.CodeNameBean childBean = null; 
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" SELECT IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_CODE > 0 AND IC_TYPE = "+type+" AND IC_USEYN = 'Y' \n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			while(rs.next()){
				
				childBean = superBean.new CodeNameBean();
				childBean.setCode(rs.getString("IC_CODE"));
				childBean.setName(rs.getString("IC_NAME"));
				
        		result.add(childBean);
			}
		
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public ArrayList getStatisticsIssue(String sDate, String eDate)
    {
		ArrayList result = new ArrayList();
		
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.CodeNameBean childBean = null;
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			/*
			sb.append("SELECT DATE_FORMAT(A.MD_DATE,'%m.%d') AS DATE		\n");
			sb.append("     , B.SG_SEQ										\n");
			sb.append("     , C.SG_NAME										\n");
			sb.append("     , COUNT(*) AS S_CNT								\n");
			sb.append("  FROM META A										\n");
			sb.append("     , IDX B											\n");
			sb.append("     , SITE_GROUP C									\n");
			sb.append(" WHERE A.MD_SEQ = B.MD_SEQ							\n"); 
			sb.append("   AND B.SG_SEQ = C.SG_SEQ							\n");
			sb.append("   AND A.MD_SEQ BETWEEN "+min+" AND "+max+"			\n");
			sb.append("   AND (B.I_STATUS = 'N' AND B.M_SEQ <> 2)			\n");
			sb.append("   AND B.K_XP = "+k_xp+"								\n");
			sb.append("   AND B.K_YP = "+k_yp+"								\n");
			sb.append("   AND B.K_ZP = "+k_zp+"								\n");
			sb.append(" GROUP BY DATE_FORMAT(A.MD_DATE,'%Y%m%d'), B.SG_SEQ	\n");
			sb.append(" ORDER BY C.SG_SEQ									\n");
			*/
			
			sb.append("SELECT DATE_FORMAT(A.MD_DATE,'%m.%d') AS MD_DATE							\n");
			sb.append("     , B.IC_CODE 														\n");
			sb.append("     , C.IC_NAME 														\n");
			sb.append("     , COUNT(*) AS CNT													\n");
			sb.append("  FROM ISSUE_DATA A														\n");
			sb.append("     , ISSUE_DATA_CODE B													\n");
			sb.append("     , ISSUE_CODE C														\n");
			sb.append(" WHERE A.ID_SEQ = B.ID_SEQ												\n");
			sb.append("   AND A.MD_DATE BETWEEN '"+sDate+" 00:00:00' AND '"+eDate+" 23:59:59'	\n");
			sb.append("   AND B.IC_TYPE = 11													\n");
			sb.append("   AND B.IC_TYPE = C.IC_TYPE												\n");
			sb.append("   AND B.IC_CODE = C.IC_CODE												\n");
			sb.append(" GROUP BY DATE_FORMAT(A.MD_DATE,'%Y%m%d'), B.IC_CODE						\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				childBean = superBean.new CodeNameBean();
				childBean.setDate(rs.getString("MD_DATE"));
				childBean.setCode(rs.getString("IC_CODE"));
				childBean.setName(rs.getString("IC_NAME"));
				childBean.setCnt(rs.getString("CNT")); 
        		result.add(childBean);	
			}
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	/**
     * 사이트 그룹별 키워드 그룹별 일일 동향 관련
     */
	public ArrayList get_Keyword_name()
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("		SELECT K_XP, K_VALUE FROM KEYWORD WHERE K_USEYN='Y' AND K_YP = 0	\n");
			rs = stmt.executeQuery( sb.toString() );
			while( rs.next() )
			{
				statisticsBean = new StatisticsBean();
				statisticsBean.setK_xp(rs.getString("K_XP"));
				statisticsBean.setK_value(rs.getString("K_VALUE"));
        		result.add(statisticsBean);	
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	/**
     * 사이트 그룹별 키워드 그룹별 일일 동향 관련
     */
	public ArrayList get_siteG_name()
	{
		ArrayList result = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("		SELECT SG_SEQ, SG_NAME FROM SITE_GROUP	\n");
			rs = stmt.executeQuery( sb.toString() );
			while( rs.next() )
			{
				statisticsBean = new StatisticsBean();
				statisticsBean.setSg_seq(rs.getString("SG_SEQ"));
				statisticsBean.setSg_name(rs.getString("SG_NAME"));
        		result.add(statisticsBean);	
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	/**
     * 사이트 그룹별 키워드 그룹별 일일 동향 관련
     */
	public ArrayList get_site_group_data( String minNo, String maxNo, String xp )
    {
		ArrayList result = new ArrayList();
		
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append(" SELECT "+xp+" AS K_XP, A.SG_SEQ, A.SG_NAME, IFNULL(B.CNT,0) CNT                                                                                        \n");
			sb.append(" FROM                                                                                                                     \n");
			sb.append("   (                                                                                                                      \n");
			sb.append("     SELECT SG_SEQ, SG_NAME FROM SITE_GROUP                                                                               \n");
			sb.append("   ) A LEFT OUTER JOIN                                                                                                    \n");
			sb.append("   (                                                                                                                      \n");
			sb.append("     SELECT SG_SEQ , COUNT(MD_PSEQ) CNT                                                                                   \n");
			sb.append("     FROM                                                                                                                 \n");
			sb.append(" 	META M, (SELECT MD_SEQ, SG_SEQ FROM IDX WHERE MD_SEQ BETWEEN "+minNo+" AND "+maxNo+" AND K_XP = "+xp+" GROUP BY MD_SEQ, SG_SEQ) I  \n");
			sb.append("     WHERE                                                                                                                \n");
			sb.append(" 	M.MD_SEQ = I.MD_SEQ                                                                                              \n");
			sb.append("     GROUP BY SG_SEQ                                                                                                      \n");
			sb.append("    )B ON A.SG_SEQ = B.SG_SEQ                                                                                             \n");
			
			rs = stmt.executeQuery( sb.toString() );
			
			while( rs.next() )
			{
				statisticsBean = new StatisticsBean();
        		statisticsBean.setK_xp(rs.getString("K_XP"));
        		statisticsBean.setSg_seq(rs.getString("SG_SEQ"));
        		statisticsBean.setSg_name(rs.getString("A.SG_NAME"));
        		statisticsBean.setK_cnt(rs.getString("CNT"));
        		result.add(statisticsBean);	
			}
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	/**
     * 사이트 그룹별 키워드 그룹별 일일 동향 관련
     */		
	public String get_site_group_data_sum( String minNo, String maxNo, String xp )
    {
		String result = "";
		    	
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			
			sb.append(" SELECT K_XP , COUNT(MD_PSEQ) CNT                                                                                                                                                               \n");
			sb.append(" FROM                                                                                                                                                                                                                 \n");
			sb.append("       META M, (SELECT MD_SEQ, K_XP FROM IDX WHERE MD_SEQ BETWEEN "+minNo+" AND "+maxNo+" AND K_XP = "+xp+" GROUP BY MD_SEQ, SG_SEQ) I \n");
			sb.append(" WHERE                                                                                                                                                                                                               \n");
			sb.append("       M.MD_SEQ = I.MD_SEQ                                                                                                                                                                                  \n");
			sb.append(" GROUP BY K_XP                                                                                                                                                                                                  \n");
			
			rs = stmt.executeQuery( sb.toString() );
			System.out.println(sb.toString());
			
			if( rs.next() )
			{
				result = rs.getString("CNT");
			}else{
				result = "0";
			}
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	/**
     * 사이트 그룹별 키워드 그룹별 일일 동향 관련
     */
	public String get_chart_legend_name( String xp )
	{
		String result = "";
		try {
			if(xp.length()>0)
			{
				dbconn = new DBconn();
				dbconn.getDBCPConnection();
				stmt = dbconn.createStatement();
				
				sb = new StringBuffer();
				
				sb.append("	SELECT K_VALUE FROM KEYWORD WHERE K_USEYN='Y' AND K_YP=0 AND K_XP IN("+xp+") ORDER BY K_XP	\n");
				//System.out.println(sb.toString());
				rs = stmt.executeQuery( sb.toString() );
				
				while( rs.next() )
				{
					if(result.equals("")){
						result = rs.getString("K_VALUE");
					}else{
						result += ","+rs.getString("K_VALUE");
					}
				}
			}
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
    
	/**
     * 사이트 그룹별 수집현황
     */
	public ArrayList getSiteGroupAnalysis(String fromDate, String toDate) {
		
		ArrayList list = new ArrayList();	//최종리스트

        try {
        	
        	sb = new StringBuffer();

        	sb.append(" SELECT SA.SG_SEQ                                                                                                                              \n");
			sb.append("         ,SA2.SITE_CNT                                                                                                                              \n");
			sb.append("         ,SA.SG_NAME                                                                                                                                \n");
			sb.append("         , SUM(SA.SA_IDX_CNT) SA_IDX_CNT, SUM(SA.SA_META_CNT) SA_META_CNT, ROUND(SUM(SA.SA_IDX_CNT)/SUM(SA_META_CNT)*100,1) AVG                  \n");
			sb.append("  FROM SITE_ANALYSIS SA INNER JOIN                                                                                                    \n");
			sb.append("       (SELECT SG_SEQ, COUNT(*) SITE_CNT                                                                                            \n");
			sb.append("        FROM SITE_ANALYSIS WHERE SA_REGDATE BETWEEN '"+fromDate+" 00:00:00' AND '"+toDate+" 23:59:59'  \n");
			sb.append("        GROUP BY SG_SEQ                                                                                                                         \n");
			sb.append("        ) SA2 ON SA.SG_SEQ = SA2.SG_SEQ                                                                                                \n");
			sb.append("  WHERE SA_REGDATE BETWEEN '"+fromDate+" 00:00:00' AND '"+toDate+" 23:59:59'                                         \n");
			sb.append("  GROUP BY SG_SEQ                                                                                                                               \n");
			sb.append("  ORDER BY SA_META_CNT DESC                                                                                                            \n");

			System.out.println(sb.toString());
            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
			
            while(rs.next()){
            		
            		
            		double avg = 0.0;
            		if(rs.getDouble("SA_IDX_CNT")!=0.0){
            			avg = (rs.getDouble("SA_IDX_CNT")/rs.getDouble("SA_META_CNT"))*100;
            		}
            		
            		statisticsBean = new StatisticsBean();
            		statisticsBean.setSg_seq(rs.getString("SG_SEQ"));
            		statisticsBean.setSg_name(rs.getString("SG_NAME"));
            		statisticsBean.setSite_ct(rs.getInt("SITE_CNT"));
            		statisticsBean.setTotal_ct(rs.getInt("SA_META_CNT"));
            		statisticsBean.setFavor_ct(rs.getInt("SA_IDX_CNT"));
            		statisticsBean.setAvg(rs.getDouble("AVG"));
					list.add(statisticsBean);
					//System.out.println("SG_NAME : "+rs.getString("SG_NAME")+"  SITE_CT : "+rs.getInt("SITE_CT")+"  FAVOR_CT : "+rs.getInt("FAVOR_CT")+"  TOTAL_CT : "+rs.getInt("TOTAL_CT")+"  평균 : "+avg);
            }

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }

		return list;
	}
	

	/**
     * 사이트별 수집현황
     */
	public ArrayList getSiteAnalysis(String fromDate, String toDate, String Sgroup) {
		
		ArrayList list = new ArrayList();	//최종리스트

        try {        	
        	
			sb = new StringBuffer();

			sb.append("SELECT S_SEQ, S_NAME, SA_IDX_CNT, SA_META_CNT                                   \n");
			sb.append("FROM SITE_ANALYSIS                                                                                    \n");
			sb.append("WHERE SA_REGDATE BETWEEN '"+fromDate+" 00:00:00' AND '"+toDate+" 23:59:59'  \n");
			sb.append("      AND SG_SEQ = "+Sgroup+"                                                                                       \n");
			sb.append("GROUP BY S_SEQ                                                                                          \n");
			sb.append("ORDER BY SA_IDX_CNT DESC                                                                     \n");
								
			System.out.println(sb.toString());
            dbconn = new DBconn();
            dbconn.getDBCPConnection();

            stmt = dbconn.createStatement();
            rs = stmt.executeQuery(sb.toString());
			
            while(rs.next()){
            		
            		double avg = 0.0;
            		
            		if(rs.getInt("SA_META_CNT")==0){
            			avg = 0.0;
            		}else{            			
            			avg = (rs.getDouble("SA_IDX_CNT")/rs.getDouble("SA_META_CNT"))*100;
            		}
            		
            		statisticsBean = new StatisticsBean();
            		statisticsBean.setS_seq(rs.getString("S_SEQ"));            		
            		statisticsBean.setS_name(rs.getString("S_NAME"));
            		statisticsBean.setTotal_ct(rs.getInt("SA_META_CNT"));
            		statisticsBean.setFavor_ct(rs.getInt("SA_IDX_CNT"));
            		statisticsBean.setAvg(avg);
    				list.add(statisticsBean);
					//System.out.println("SG_NAME : "+rs.getString("SG_NAME")+"  SITE_CT : "+rs.getInt("SITE_CT")+"  FAVOR_CT : "+rs.getInt("FAVOR_CT")+"  TOTAL_CT : "+rs.getInt("TOTAL_CT")+"  평균 : "+avg);
            }

        } catch (SQLException ex ) {
            Log.writeExpt(ex, sb.toString() );

        } catch (Exception ex ) {
            Log.writeExpt(ex);

        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }

		return list;
	}
	
	public ArrayList getDailyIssue(String sDate, String eDate, String siteGroup, String code3) {
		ArrayList arrIssueData = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT ID.IT_SEQ, ID.ID_SEQ, ID.ID_TYPE, ID.ID_DATETIME, ID.ID_SITENAME, ID.ID_TITLE, ID.ID_URL, ID.ID_SAME_CT, IC.IC_NAME, IDC3.IC_CODE                                                                                                 		\n");
			sb.append("FROM IMS_ISSUE_DATA ID, IMS_SITE_DATA SD, IMS_ISSUE_DATA_CODE IDC1, IMS_ISSUE_DATA_CODE IDC2, IMS_ISSUE_DATA_CODE IDC3, IMS_ISSUE_CODE IC                                   		\n");
			sb.append("WHERE ID_DATETIME BETWEEN  TO_DATE('"+sDate+"','YYYY-MM-DD HH24:MI:SS') AND TO_DATE('"+eDate+"','YYYY-MM-DD HH24:MI:SS')		\n");
			sb.append("	AND ID.ID_SITECODE = SD.SD_GSN                                                                                                                                                                        		\n");
			sb.append("	AND ID.ID_SEQ = IDC1.ID_SEQ                                                                                                                                                                               		\n");
			sb.append("	AND ID.ID_SEQ = IDC2.ID_SEQ                                                                                                                                                                               		\n");
			sb.append("	AND ID.ID_SEQ = IDC3.ID_SEQ                                                                                                                                                                               		\n");
			sb.append("	AND IDC2.IC_TYPE = IC.IC_TYPE AND IDC2.IC_CODE = IC.IC_CODE                                                                                                                               		\n");
			sb.append("	AND IDC1.IC_TYPE = 3 AND IDC1.IC_CODE IN ("+code3+")                                                                                                                                                          		\n");
			sb.append("	AND IDC2.IC_TYPE = 2                                                                                                                                                                                          		\n");
			sb.append("	AND IDC3.IC_TYPE = 1                                                                                                                                                                                          		\n");
			sb.append("	AND SD.SG_SEQ IN ("+siteGroup+")                                                                                                                                                                            		\n");
			sb.append("ORDER BY IDC2.IC_CODE, ID.ID_SAME_CT DESC                                                                                                                                                                            		\n");
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				IssueDataBean idBean = new IssueDataBean();
				idBean.setIt_seq(rs.getString("IT_SEQ"));
				idBean.setId_seq(rs.getString("ID_SEQ"));
				idBean.setId_regdate(rs.getString("ID_REGDATE"));
				idBean.setId_title(rs.getString("ID_TITLE"));
				idBean.setId_url(rs.getString("ID_URL"));						
        		arrIssueData.add(idBean);
			}
			sb = null;
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrIssueData;
	}
	
	public ArrayList getDailyIssueWeather(String sDate,String sTime, String eDate,String eTime, String siteGroup, String type1) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append("SELECT A1.IC_CODE, A1.IC_NAME, IFNULL(A2.CT1,0) AS CT1, IFNULL(A2.CT2,0) AS CT2, IFNULL(A2.CT3,0) AS CT3, IFNULL(CT1+CT2,0) AS TOTAL, IFNULL(CASE CT1 WHEN 0 THEN '0.00' ELSE FORMAT(CT1/(CT1+CT2)*100,'####.##') END,0) AS CT1_PER	\n");
			sb.append("FROM (	                                                                                                                                                                                                                                                                               	\n");
			sb.append("		SELECT *                                                                                                                                                                                                                                                                  	\n");
			sb.append("		FROM ISSUE_CODE IC3                                                                                                                                                                                                                                        	\n");
			sb.append("		WHERE IC3.IC_TYPE = 4 AND IC3.IC_CODE != 0                                                                                                                                                                                                               	\n");
			sb.append("	 )A1 LEFT OUTER JOIN                                                                                                                                                                                                                                                        	\n");
			sb.append("	 (                                                                                                                                                                                                                                                                                     	\n");
			sb.append("	  SELECT IFNULL(SUM(CASE IC.IC_CODE WHEN 1 THEN 1 END), 0) AS CT1, IFNULL(SUM(CASE IC.IC_CODE WHEN 2 THEN 1 END), 0) AS CT2,                        		       	\n");
			sb.append("		    IFNULL(SUM(CASE IC.IC_CODE WHEN 3 THEN 1 END), 0) AS CT3, IDC2.IC_CODE                                                                                                                  		       	\n");
			sb.append("			FROM ISSUE_DATA ID, SG_S_RELATION SD, ISSUE_DATA_CODE IDC1, ISSUE_DATA_CODE IDC2, ISSUE_CODE IC 	                               	\n");
			sb.append("			WHERE ID_REGDATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"'  \n");
			sb.append("			AND ID.S_SEQ = SD.S_SEQ                                                                                                                                                                        		                        		\n");
			sb.append("			AND ID.ID_SEQ = IDC1.ID_SEQ                                                                                                                                                                               		                                		\n");
			sb.append("			AND ID.ID_SEQ = IDC2.ID_SEQ                                                                                                                                                                                                                       	       	\n");
			sb.append("			AND IDC1.IC_TYPE = IC.IC_TYPE AND IDC1.IC_CODE = IC.IC_CODE                                                                                                                                                                      	       	\n");
			sb.append("			AND IDC1.IC_TYPE = 1 AND IDC1.IC_CODE IN ("+type1+")                                                                                                                                                                                      	       	\n");
			sb.append("			AND IDC2.IC_TYPE = 4                                                                                                                                                                                                                                 	       	\n");
			//sb.append("			AND IDC3.IC_TYPE = 5                                                                                                                          		                                                                                        		\n");
			sb.append("			AND SD.SG_SEQ IN ("+siteGroup+")                                                                                                                                                                                                                   	       	\n");
			sb.append("			GROUP BY IDC2.IC_CODE                                                                                                                                                                                                                                   	\n");
			sb.append("	 )A2 ON (A1.IC_CODE = A2.IC_CODE)                                                                                                                                                                                                                                     	\n");
			sb.append("ORDER BY A1.IC_CODE	                                                                                                                                                                                                                                                       	\n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
					String[] result = new String[5];
					result[0] = rs.getString("CT1");
					result[1] = rs.getString("CT2");
					result[2] = rs.getString("IC_NAME");
					result[3] = rs.getString("CT1_PER");
					result[4] = rs.getString("TOTAL");
					arrResult.add(result);
			}
			sb = null;
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public ArrayList getDailyIssueWeatherAll(String sDate, String siteGroup) {
		
		ArrayList arrResult = new ArrayList();
		try {
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			sb = new StringBuffer();
			sb.append(" SELECT A1.IC_CODE                                                                                                                                                                                                       \n");
			sb.append(" 	    , A1.IC_NAME                                                                                                                                                                                                     \n");
			sb.append(" 	    , IFNULL(A2.CT1,0) AS CT1                                                                                                                                                                                  \n");
			sb.append(" 	    , IFNULL(A2.CT2,0) AS CT2                                                                                                                                                                                  \n");
			sb.append(" 	    , IFNULL(A2.CT3,0) AS CT3                                                                                                                                                                                  \n");
			sb.append(" 	    , IFNULL(CT1+CT2,0) AS TOTAL                                                                                                                                                                           \n");
			sb.append(" 	    , IFNULL(CASE CT1 WHEN 0 THEN '0.00' ELSE (CT1/(CT1+CT2)*100) END,0) AS CT1_PER	                                                                                     \n");
			sb.append(" FROM (	                                                                                                                                                                                                                      \n");
			sb.append(" 		SELECT *                                                                                                                                                                                                        \n");
			sb.append(" 		FROM ISSUE_CODE IC3                                                                                                                                                                                    \n");
			sb.append(" 		WHERE IC3.IC_TYPE = 6 AND IC3.IC_CODE != 0  AND IC_USEYN = 'Y'                                                                                                                   \n");
			sb.append(" 	 )A1 LEFT OUTER JOIN                                                                                                                                                                                             \n");
			sb.append(" 	 (                                                                                                                                                                                                                            \n");
			sb.append(" 	  SELECT IFNULL(SUM(CASE IC.IC_NAME WHEN '긍정' THEN 1 END), 0) AS CT1, IFNULL(SUM(CASE IC.IC_NAME WHEN '부정' THEN 1 END), 0) AS CT2, \n");
			sb.append(" 		    IFNULL(SUM(CASE IC.IC_NAME WHEN '중립' THEN 1 END), 0) AS CT3, IDC3.IC_CODE                                                                                      \n");
			sb.append(" 			FROM ISSUE_DATA ID, SG_S_RELATION SD, ISSUE_DATA_CODE IDC1, ISSUE_DATA_CODE IDC2, ISSUE_DATA_CODE IDC3, ISSUE_CODE IC 	\n");
			sb.append(" 			WHERE ID.MD_DATE BETWEEN  '"+sDate+" 00:00:00' AND '"+sDate+" 23:59:59'		                        	                                             \n");
			sb.append(" 			AND ID.ID_USEYN = 'Y'                                                                                                                                                                            \n");
			sb.append(" 			AND ID.S_SEQ = SD.S_SEQ                                                                                                                                                                       \n");
			sb.append(" 			AND ID.ID_SEQ = IDC1.ID_SEQ                                                                                                                                                                   \n");
			sb.append(" 			AND ID.ID_SEQ = IDC2.ID_SEQ                                                                                                                                                                   \n");
			sb.append(" 			AND ID.ID_SEQ = IDC3.ID_SEQ                                                                                                                                                                   \n");
			sb.append(" 			AND IDC2.IC_TYPE = IC.IC_TYPE AND IDC2.IC_CODE = IC.IC_CODE                                                                                                              \n");
			sb.append(" 			AND IDC1.IC_TYPE = 5                                                                                                                                                                              \n");
			sb.append(" 			AND IDC2.IC_TYPE = 1                                                                                                                                                                              \n");
			sb.append(" 			AND IDC3.IC_TYPE = 6                                                                                                                          		                                      \n");
			sb.append(" 			AND SD.SG_SEQ IN ("+siteGroup+")                                                                                                                         \n");
			sb.append(" 			GROUP BY IDC3.IC_CODE                                                                                                                                                                         \n");
			sb.append(" 	 )A2 ON (A1.IC_CODE = A2.IC_CODE)                                                                                                                                                                         \n");
			sb.append(" ORDER BY A1.IC_CODE                                                                                                                                                                                                    \n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			int toTalCnt1 = 0;
			int toTalCnt2 = 0;
			int toTalCnt3 = 0;
			int toTalCnt = 0;
			double toTalPer = 0;
			
			while( rs.next() ) {
				toTalCnt1 += rs.getInt("CT1");
				toTalCnt2 += rs.getInt("CT2");
				toTalCnt3 += rs.getInt("CT3");
				toTalCnt += rs.getInt("TOTAL");
				toTalPer += rs.getDouble("CT1_PER");
				
				String[] result = new String[6];
				result[0] = rs.getString("CT1");
				result[1] = rs.getString("CT2");
				result[2] = rs.getString("IC_NAME");
				result[3] = rs.getString("CT1_PER");
				result[4] = rs.getString("TOTAL");
				result[5] = rs.getString("IC_CODE");
				arrResult.add(result);
			}
			
			if(toTalCnt>0){
				//toTalPer = toTalPer/arrResult.size();
				toTalPer = ((double)toTalCnt1/(toTalCnt1+toTalCnt2)*100);
				String[] total = new String[6];
				total[0] = Integer.toString(toTalCnt1);
				total[1] = Integer.toString(toTalCnt2);
				total[2] = Integer.toString(toTalCnt3);
				total[3] = Integer.toString(toTalCnt);
				String tPercent ="";
				
				if(toTalPer>0){
					
					tPercent = su.digitFormatDouble(Double.toString(toTalPer),"###.##");
				}
				total[4] = tPercent;
				arrResult.add(total);
			}
			
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
  
	public String[] getTwitterMinMax(String sdate, String edate) {
			
		String[] result = new String[2];
		result[0] = "";
		result[1] = "";
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT (SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+sdate+" 00:00:00' AND '"+sdate+" 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO	\n"); 
			sb.append("      ,(SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '"+edate+" 00:00:00' AND '"+edate+" 23:59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO 	\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			if( rs.next() ) {
				
				result[0] = rs.getString("MIN_NO");
				result[1] = rs.getString("MAX_NO");
				
				
				if(result[0] == null || result[0].equals("")){
					result[0] = "0";
				}
				if(result[1] == null || result[1].equals("")){
					result[1] = "9999999999";
				}
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return result;
	}
	
	public ArrayList getTwitter_follower(String min, String max) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.twitterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT T_USER_ID									\n");
			sb.append("     , MAX(T_FOLLOWERS) AS T_FOLLOWERS			\n");
			sb.append("  FROM TWEET 									\n");
			sb.append(" WHERE MD_SEQ BETWEEN "+min+" AND "+max+"		\n");
			sb.append(" GROUP BY T_USER_ID 								\n");
			sb.append(" ORDER BY T_FOLLOWERS DESC						\n");
			sb.append(" LIMIT 10										\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				childBean = superBean.new twitterBean();
				childBean.setUser(rs.getString("T_USER_ID"));
				childBean.setCnt(rs.getString("T_FOLLOWERS"));
				arrResult.add(childBean);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public ArrayList getTwitter_twit(String min, String max) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.twitterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();
			sb.append("SELECT T_USER_ID								\n");
			sb.append("     , MAX(T_TWEET) AS T_TWEET				\n");
			sb.append("  FROM TWEET 								\n");
			sb.append(" WHERE MD_SEQ BETWEEN "+min+" AND "+max+"	\n");
			sb.append(" GROUP BY T_USER_ID 							\n");
			sb.append(" ORDER BY T_TWEET DESC						\n");
			sb.append(" LIMIT 10									\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				childBean = superBean.new twitterBean();
				childBean.setUser(rs.getString("T_USER_ID"));
				childBean.setCnt(rs.getString("T_TWEET"));
				arrResult.add(childBean);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public ArrayList getTwitter_negative(String min, String max) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.twitterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			
			sb.append("SELECT A.T_USER_ID								\n");
			sb.append("     , COUNT(0) AS CNT							\n");
			sb.append("  FROM TWEET A									\n");
			sb.append("     , ISSUE_DATA B								\n");
			sb.append("     , ISSUE_DATA_CODE C							\n");
			sb.append(" WHERE A.MD_SEQ = B.MD_SEQ						\n");
			sb.append("   AND B.ID_SEQ = C.ID_SEQ						\n");
			sb.append("   AND A.MD_SEQ BETWEEN "+min+" AND "+max+"		\n");	
			sb.append("   AND C.IC_TYPE = 9								\n");
			sb.append("   AND C.IC_CODE = 2								\n");
			sb.append(" GROUP BY A.T_USER_ID   							\n");							
			sb.append(" ORDER BY COUNT(0) DESC							\n");					
			sb.append(" LIMIT 10										\n");
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				childBean = superBean.new twitterBean();
				childBean.setUser(rs.getString("T_USER_ID"));
				childBean.setCnt(rs.getString("CNT"));
				arrResult.add(childBean);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
	
	public ArrayList getTwitter_bestRr(String min, String max) {
		
		ArrayList arrResult = new ArrayList();
		StatisticsSuperBean superBean = new StatisticsSuperBean();
		StatisticsSuperBean.twitterBean childBean = null;
		
		try {
			
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			stmt = dbconn.createStatement();
			
			sb = new StringBuffer();			
			sb.append("SELECT A.P_USER																																								\n");
			sb.append("     , A.CNT																																									\n");
			sb.append("     , A.T_FOLLOWERS AS FULL_FOLLOWER																																		\n");
			sb.append("     , IFNULL((SELECT CONCAT(T_FOLLOWERS,'@',T_FOLLOWING,'@',T_TWEET) FROM TWEET WHERE T_USER_ID = A.P_USER AND MD_TITLE LIKE CONCAT('%',A.DATA,'%') LIMIT 1 ),'') AS P_INFO	\n");
			sb.append("     , A.MD_TITLE																																							\n");
			sb.append("  FROM (																																										\n");
			sb.append("        SELECT MID(A.MD_TITLE, INSTR(A.MD_TITLE, 'RT @')+4, (INSTR(A.MD_TITLE, ':') - (INSTR(A.MD_TITLE, 'RT @')+4))) AS P_USER												\n");
			sb.append("             , MID(A.MD_TITLE, INSTR(A.MD_TITLE, ': ')+2, 40) AS DATA																										\n");
			sb.append("             , COUNT(0) AS CNT																																				\n");
			sb.append("             , MIN(A.MD_TITLE) AS MD_TITLE																																	\n"); 
			sb.append("             , SUM(T_FOLLOWERS) AS T_FOLLOWERS																																\n");
			sb.append("          FROM TWEET A																																						\n");
			sb.append("         WHERE A.MD_SEQ BETWEEN "+min+" AND "+max+"																															\n");
			sb.append("           AND MID(A.MD_TITLE, INSTR(A.MD_TITLE, 'RT @'), 40) <> ''																											\n");
			sb.append("           AND A.T_IS_RT = 'R'																																				\n");
			sb.append("         GROUP BY MID(A.MD_TITLE, INSTR(A.MD_TITLE, 'RT @'), 40)																												\n");
			sb.append("         ORDER BY  COUNT(0) DESC																																				\n");
			sb.append("         LIMIT 10)A																																							\n");
			
			
			System.out.println(sb.toString());
			rs = stmt.executeQuery(sb.toString());
			
			while( rs.next() ) {
				
				childBean = superBean.new twitterBean();
				childBean.setUser(rs.getString("P_USER"));
				childBean.setCnt(rs.getString("CNT"));
				
				childBean.setFull_follower(rs.getString("FULL_FOLLOWER"));
				childBean.setInfo(rs.getString("P_INFO"));
				childBean.setMd_title(rs.getString("MD_TITLE"));
				
				arrResult.add(childBean);
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,ex.getMessage());
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
			if (dbconn != null) try { dbconn.close(); } catch(SQLException ex) {}
		}
		return arrResult;
	}
}
