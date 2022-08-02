package risk.admin.keyword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import risk.DBconn.DBconn;
import risk.admin.aekeyword.ExceptionKeywordBean;
import risk.admin.aekeyword.StorageBean;
import risk.admin.log.LogBean;
import risk.admin.log.LogConstants;
import risk.admin.log.LogMgr;
import risk.search.MetaBean;
import risk.search.MetaMgr;
import risk.issue.IssueCodeBean;
import risk.issue.IssueCodeMgr;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

public class KeywordMng {
	
	DateUtil du = new DateUtil();
	StringBuffer sb = new StringBuffer();
	StringUtil su = new StringUtil();
	DBconn  conn  = null;
    Statement stmt = null;
    ResultSet rs = null;
    String key = null;
    
    String baseURL = "";
	String baseTarget = "";
	String imagesURL = "";
    
    int selectedXP = 0;
	int selectedYP = 0;
	int selectedZP = 0;
    
	private int k_seq;
	private int k_xp;
	private int k_yp;
	private int k_zp;
	private int k_type;
	private int k_pos;
	private String tbl_name = "KEYWORD";
	private String tbl2_name = "META";
	private String tbl3_name = "IDX";
	
	public void changeTableName() {
		this.tbl_name = "PORTAL_SEARCH_KEYWORD";
		this.tbl2_name = "PORTAL_SEARCH_META";
		this.tbl3_name = "PORTAL_SEARCH_IDX";
	}
	
	public void returnTableName() {
		this.tbl_name = "KEYWORD";
		this.tbl2_name = "META";
		this.tbl3_name = "IDX";
	}
	
	public void setImagesURL(String imagesURL) {
		this.imagesURL = imagesURL;
	}
	
	public void setBaseURL(String url) {
		baseURL = url;
	}
	
	public void setBaseTarget(String target) {
		baseTarget = target;
	}
	
	public void setSelected(int xp, int yp, int zp){
		selectedXP = xp;
		selectedYP = yp;
		selectedZP = zp;
	}
	
	private static KeywordMng instance = new KeywordMng();
	
	public static KeywordMng getInstance() {
		return instance;
	}
	
	//사이트 그룹 가져오기 
    public ArrayList getSgSeq(String sg_seqs) throws ClassNotFoundException, Exception {
    	
        sb = new StringBuffer();
        ArrayList result = new ArrayList();
        String[] k_value = null;
        
        if(sg_seqs == null || sg_seqs.trim().equals("")){
        	sg_seqs = "0";
        }
        
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	sb.append("SELECT A.SG_SEQ																	\n");
       	 	sb.append("     , A.SG_NAME																	\n");
       	 	sb.append("     , IF(B.SG_NAME IS NULL , '','checked') AS CHK								\n"); 
       	 	sb.append("  FROM (SELECT SG_SEQ, SG_NAME FROM SITE_GROUP)A									\n");
       	 	sb.append("        LEFT OUTER JOIN 															\n");
       	 	sb.append("       (SELECT SG_SEQ, SG_NAME FROM SITE_GROUP WHERE SG_SEQ IN ("+sg_seqs+"))B	\n");
       	 	sb.append("    ON A.SG_SEQ = B.SG_SEQ														\n");
    
       	 	System.out.println(sb.toString());
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            
            while(rs.next()) {
            		k_value = new String[3];
					k_value[0] = rs.getString("SG_SEQ");
					k_value[1] = rs.getString("SG_NAME");
					k_value[2] = rs.getString("CHK");
					
					result.add(k_value);
					
            }
            
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
    
    
	
	//대분류명 
    public String[] getKG(String k_xp)  {
    	
        sb = new StringBuffer();
        String[] k_value = new String[2];

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();

       	 	sb.append(" SELECT K_VALUE, SG_SEQS FROM "+tbl_name+" WHERE K_XP="+k_xp+" AND K_YP=0 AND K_ZP=0");
       	 	System.out.println(sb.toString());
            stmt = conn.createStatement();
                      rs = stmt.executeQuery(sb.toString());
            if (rs.next()) {
					k_value[0] = rs.getString("K_VALUE");
					k_value[1] = rs.getString("SG_SEQS");
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return k_value;
    }

	//중분류명
    public String getKG(String k_xp, String k_yp) throws ClassNotFoundException, Exception {
    	
        sb = new StringBuffer();
        String k_value = "";

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
	       	sb = new StringBuffer();
	       	sb.append(" SELECT K_VALUE FROM "+tbl_name+" WHERE K_XP="+k_xp+" AND K_YP="+k_yp+" AND K_ZP=0");
	       	stmt = conn.createStatement();
	        rs = stmt.executeQuery( sb.toString() );
            if (rs.next()) {
					k_value = rs.getString("K_VALUE");               
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return k_value;
    }
    
	//키워드명
    public String[] getKG(String k_xp, String k_yp, String k_zp) throws ClassNotFoundException, Exception {
    	
        //String k_value = "";
        
        String[] reslut = new String[3];

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	sb = new StringBuffer();
       	 	sb.append(" SELECT K_VALUE, K_OP, K_NEAR_LEN FROM "+tbl_name+" WHERE K_XP="+k_xp+" AND K_YP="+k_yp+" AND K_ZP="+k_zp+" AND K_TYPE=2 ORDER BY K_XP,K_YP,K_ZP ASC");
       	 	stmt = conn.createStatement();
       	
            rs = stmt.executeQuery(sb.toString());
            int idx = 0;
            if (rs.next()) {
            	reslut[idx++] = rs.getString("K_VALUE");
            	reslut[idx++] = rs.getString("K_OP");
            	reslut[idx++] = rs.getString("K_NEAR_LEN");
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return reslut;
    }
    
	//제외키워드명
    public List getEXKG(String k_xp, String k_yp, String k_zp) throws Exception {
    	
       
        List exkeylist = null;

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	System.out.println("\n\n k_xp : "+k_xp);
       	 	System.out.println(" k_yp : "+k_yp);
       	 	System.out.println(" k_zp : "+k_zp);
       	 	sb = new StringBuffer();
       	 	sb.append(" SELECT K_TYPE, K_VALUE, K_OP FROM "+tbl_name+" \n");
       	 	sb.append(" WHERE K_XP="+k_xp+" AND K_YP="+k_yp+" AND K_ZP="+k_zp+" AND K_TYPE>10 ORDER BY K_TYPE ASC");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            
            exkeylist = new ArrayList();
            if(rs != null) {
	            while(rs.next()) {
	            	KeywordBean keybean = new KeywordBean();
	            	keybean.setKGtype( rs.getString("K_TYPE") );
	            	keybean.setKGvalue( rs.getString("K_VALUE") );
	            	if(rs.getString("K_OP").equals("1")){
	            		keybean.setK_op_type("AND 조건" );
	            		//keybean.setK_img("icon_a.jpg" );
	            	}else if(rs.getString("K_OP").equals("2")){     
	            		keybean.setK_op_type("인접검색" );
	            		//keybean.setK_img("icon_n.jpg" );
	            	}else if(rs.getString("K_OP").equals("3")){     
	            		keybean.setK_op_type("구문검색" );
	            		//keybean.setK_img("icon_p.jpg" );
	            	}else if(rs.getString("K_OP").equals("4")){     
	            		keybean.setK_op_type("고유검색" );
	            		//keybean.setK_img("icon_o.jpg" );
	            	}else if(rs.getString("K_OP").equals("5")){     
	            		keybean.setK_op_type("고유검색2" );
	            		//keybean.setK_img("icon_k.gif" );
	            	}else if(rs.getString("K_OP").equals("6")){     
	            		keybean.setK_op_type("구문검색2" );
	            		//keybean.setK_img("icon_p2.jpg" );
	            	}else if(rs.getString("K_OP").equals("7")){     
	            		keybean.setK_op_type("구문검색3" );
	            		//keybean.setK_img("icon_p3.jpg" );
	            	}                                             
	            	exkeylist.add(keybean);
	            }
            } else {
            	return null;
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return exkeylist;
    }
    
    //키워드 정보를 가져온다.
    public ArrayList getKeywordInfo(String k_xp, String k_yp, String k_type) throws Exception {
    	
       
    	ArrayList keywordList = new ArrayList();

        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 
       	 	sb = new StringBuffer();
       	 	if(k_type.equals("1")&&k_xp.equals("")){
	       	 	sb.append(" SELECT K_XP, K_YP, K_ZP ,K_TYPE, K_VALUE FROM KEYWORD \n");
	       	 	sb.append(" WHERE K_YP = 0 AND K_TYPE="+k_type+" ORDER BY K_TYPE ASC");
       	 	}else if(k_type.equals("1")&&!k_xp.equals("")){
	       	 	sb.append(" SELECT K_XP, K_YP, K_ZP ,K_TYPE, K_VALUE FROM KEYWORD \n");
	       	 	sb.append(" WHERE K_XP="+k_xp+" AND K_YP<>0 AND K_TYPE="+k_type+" ORDER BY K_TYPE ASC");
       	 	}else if(k_type.equals("2")){
	       	 	sb.append(" SELECT K_XP, K_YP, K_ZP ,K_TYPE, K_VALUE FROM KEYWORD \n");
	       	 	sb.append(" WHERE K_XP="+k_xp+" AND K_YP="+k_yp+" AND K_TYPE="+k_type+" ORDER BY K_TYPE ASC");
       	 	}
       	 	stmt = conn.createStatement();
       	 	System.out.println(sb.toString());
            rs = stmt.executeQuery(sb.toString());
            
           
            while(rs.next()) {
            	KeywordBean keybean = new KeywordBean();
            	keybean.setKGxp(rs.getString("K_XP") );
            	keybean.setKGyp(rs.getString("K_YP") );
            	keybean.setKGzp(rs.getString("K_ZP") );
            	keybean.setKGtype( rs.getString("K_TYPE") );
            	keybean.setKGvalue( rs.getString("K_VALUE") );
            	
            	keywordList.add(keybean);
            }
            
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return keywordList;
    }
    
    
    
	//모든 키워드의 HTML을 리턴
    public String getAllKGHtml() throws Exception {
    	
        StringBuffer returnValue = new StringBuffer();
    	
    	DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        
        int k_xp = 0;
        String k_xpnm = new String("");
        int k_yp = 0;
        String k_ypnm = new String("");
        int k_zp = 0;


        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	
       	 	
       	 	sb = new StringBuffer();
       	 	
	        sb.append("	 SELECT K_XP, K_YP, K_ZP, K_TYPE, K_VALUE               \n");
	        sb.append("	FROM KEYWORD                                            \n");
	        sb.append("	WHERE K_YP>0 AND K_ZP>0 AND K_TYPE>10 AND K_USEYN='Y'   \n");
	        sb.append("	ORDER BY K_XP, K_YP, K_ZP, K_TYPE                       \n");

    		Log.debug(sb.toString());
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sb.toString());
            
            rs.last();
            int count =  rs.getRow();
            rs.beforeFirst();

            
            String[] xp = new String[count];
            String[] yp = new String[count];
            String[] zp = new String[count];
            String[] exvalue = new String[count];
       	 	
            int  k = 0;
            while(rs.next())
            {
            	xp[k] = rs.getString("K_XP");
            	yp[k] = rs.getString("K_YP");
            	zp[k] = rs.getString("K_ZP");
            	exvalue[k] = rs.getString("K_VALUE");
                 k++;
            }
            
       	 	sb = new StringBuffer();
       	 	
       	 	//sb.append("   	 SELECT A3.K_XP, A3.K_YP, A3.K_ZP, A1.K_VALUE AS K_XP_NM, A2.K_VALUE AS K_YP_NM, A3.K_VALUE AS K_ZP_NM  \n");
       	    sb.append("      SELECT A3.K_XP						\n");
       	    sb.append("           , A3.K_YP						\n");
       	    sb.append("           , A3.K_ZP						\n");
       	    sb.append("           , A1.K_VALUE AS K_XP_NM		\n");
       	    sb.append("           , A2.K_VALUE AS K_YP_NM		\n");
       	    sb.append("           , A3.K_VALUE AS K_ZP_NM		\n");
       	 	//sb.append("	     	  ,	dbo.get_sumkey(A3.K_XP, A3.K_YP, A3.K_ZP, 11) AS EXKEY                       \n");
    		sb.append("    	 FROM              										                                                          \n");
    		sb.append(" (                           													     										  \n"); 
    		sb.append("		 SELECT K_XP, K_VALUE													                                  \n");
    		sb.append("  	 FROM "+tbl_name+"													                                              \n");
    		sb.append("  			WHERE K_YP=0 AND K_ZP=0 AND K_TYPE=1 AND K_USEYN='Y'							                  \n");
    		sb.append(" 	  ) A1,  																	                                              \n");
    		sb.append(" (                  																											   \n");
    		sb.append(" 	SELECT K_XP, K_YP, K_VALUE            																 \n");
    		sb.append(" 		FROM "+tbl_name+"                    																		 \n");
    		sb.append("  WHERE K_YP>0 AND K_ZP=0 AND K_TYPE=1  AND K_USEYN='Y'														\n");
    		sb.append(") A2,            																										   \n");
    		sb.append("(                   																										     \n");
    		sb.append("SELECT K_XP, K_YP, K_ZP, K_TYPE, K_VALUE														\n");
    		sb.append("FROM "+tbl_name+"																									\n");
    		sb.append("WHERE K_YP>0 AND K_ZP>0 AND K_TYPE=2	AND K_USEYN='Y'															\n");
    		sb.append(") A3																																\n");
    		sb.append("WHERE A1.K_XP=A2.K_XP AND A1.K_XP=A3.K_XP AND A2.K_YP=A3.K_YP						\n");
    		sb.append("ORDER BY A3.K_XP, A3.K_YP, A3.K_ZP, A3.K_VALUE														\n");

    		Log.debug(sb.toString());
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sb.toString());
  
            returnValue.append("<table border='1'>		\n");
            returnValue.append("	<tr>		\n");
            returnValue.append("		<td bgcolor='CCCCCC'><strong>대그룹코드</strong></td>		\n");
            returnValue.append("		<td bgcolor='CCCCCC'><strong>대그룹명</strong></td>		\n");
            returnValue.append("		<td bgcolor='CCCCCC'><strong>중그룹코드</strong></td>		\n");
            returnValue.append("		<td bgcolor='CCCCCC'><strong>중그룹명</strong></td>		\n");
            returnValue.append("		<td bgcolor='CCCCCC'><strong>키워드코드</strong></td>		\n");
            returnValue.append("		<td bgcolor='CCCCCC'><strong>키워드명</strong></td>		\n");
            returnValue.append("		<td bgcolor='CCCCCC'><strong>키워드 별 제외키워드 명</strong></td>		\n");
            returnValue.append("	</tr>		\n");
            
            rs.last();
            int cnt =  rs.getRow();
            rs.beforeFirst();
            
            String[][] arrRow = new String[cnt][9];
            String[] exkey = new String[cnt];
            
            
            /*
             * arrCol[0] = 대그룹 코드
             * arrCol[1] = 대그룹 명
             * arrCol[2] = 대그룹 Row 카운트
             * arrCol[3] = 중그룹 코드
             * arrCol[4] = 중그룹 명
             * arrCol[5] = 중그룹 Row 카운트
             * arrCol[6] = 키워드 코드
             * arrCol[7] = 키워드 명
             * arrCol[8] = 키워드 별 제외키워드 명
             * */
            
            int j = 1;
            int  i = 0;
            int  z = 0;            
            while(rs.next())
            {
            	
            	// 저장
            	
            	arrRow[i][0] = rs.getString("K_XP");
            	arrRow[i][1] = rs.getString("K_XP_NM");
            	arrRow[i][2] = "";
            	arrRow[i][3] = rs.getString("K_YP");
            	arrRow[i][4] = rs.getString("K_YP_NM");
            	arrRow[i][5] = "";
            	arrRow[i][6] = rs.getString("K_ZP");
            	arrRow[i][7] = rs.getString("K_ZP_NM");
            	
                for (int l = 0; l < arrRow.length; l++) {
                	if(exkey[l] == null) {
            			exkey[l] = "";
            		}
                	for (int l2 = 0; l2 < exvalue.length; l2++) {

        				if(xp[l2].equals(arrRow[l][0]) && yp[l2].equals(arrRow[l][3]) && zp[l2].equals(arrRow[l][6])) {
                    		if(exkey[l] == "") {
                    			exkey[l] = exvalue[l2];
                    		}else {
        						exkey[l] = exkey[l] +", "+ exvalue[l2];	
        						
        				}
        			  }
    				}    	
    			}                     
            	arrRow[i][8] = exkey[i];

                 i++;
            }     
                        
            String tmpXp = "";
            String tmpyp = "";
            int xpCnt = 0;
            int ypCnt = 0;
            
            for(i = 0; i < arrRow.length; i++) {
            	
            	
            	if(!tmpXp.equals(arrRow[i][0])) {
            		
            		xpCnt = 0;
            		for(j =0; j < arrRow.length; j++) {
            			if(arrRow[i][0].equals(arrRow[j][0])) {
            				xpCnt++;
            			}
            		}			 
            	}
            	
             if(!tmpyp.equals(arrRow[i][0] + "/" +arrRow[i][3])) {
                	
            		ypCnt=0;
            		for (j = 0; j < arrRow.length; j++) {
						if (arrRow[i][0].equals(arrRow[j][0]) && arrRow[i][3].equals(arrRow[j][3])) {
							ypCnt++;
						}
					}

            	}
            	arrRow[i][2] = Integer.toString(xpCnt);   
              	arrRow[i][5] = Integer.toString(ypCnt);
            	tmpXp = arrRow[i][0];
            	tmpyp = arrRow[i][0] + "/" +arrRow[i][3];	
            }
          
            
           
         /*   for(i =0; i < arrRow.length+1; i++) {
            	
            	if(arrRow[i][0] != arrRow[i+1][0]) {
            		for(int z = i+1; z==1; z--) {
            			arrRow[i][2] = arrRow[i][2]+i+1;
            		}
            			
            	}else if(arrRow[i][3] != arrRow[i+1][3] && arrRow[i][4] != arrRow[i+1][4]) {
            		for(int z = i+1; z==1; z--) {
            			arrRow[i][5] = arrRow[i][5]+i+1;
            		}

            		//비교 / 업데이트
            	
            	}

            }*/
            
            
            
            
            
            
         /*   for(i =0; i <arrRow.length; i++) {
            	
            	for(j = 0; j < arrRow[i].length; j++) {
            		
            		System.out.print(arrRow[i][j]);
            		System.out.print(" | ");
            	}
            	
            	System.out.print("\n");
            }*/
            
            
            

            k_xp = 0;
            for(i=0; i < arrRow.length; i++) {

            	
            	returnValue.append("	<tr>		\n");
            	if(k_xp != Integer.parseInt(arrRow[i][0])) {
            		returnValue.append("	<td rowspan = "+arrRow[i][2]+">"+ arrRow[i][0] +"</td> 		\n");      	
            		returnValue.append("	<td rowspan = "+arrRow[i][2]+">"+ arrRow[i][1] +"</td> 		\n");            		
            	}
            	
  
            	if(k_xp != Integer.parseInt(arrRow[i][0]) || k_yp != Integer.parseInt(arrRow[i][3]) ) {
            		returnValue.append("	<td rowspan ="+ arrRow[i][5] +">"+ arrRow[i][3] +"</td> 		\n");      	
               	    returnValue.append("	<td rowspan ="+ arrRow[i][5] +">"+ arrRow[i][4] +"</td> 		\n");	
            	}
            	
            	  
				 returnValue.append("	<td>"+ arrRow[i][6] +"</td> 		\n");
				 returnValue.append("	<td>"+ arrRow[i][7] +"</td> 		\n");
				 returnValue.append("	<td>"+ arrRow[i][8] +"</td> 		\n");
				 
            	
                

                	returnValue.append("	</tr>		\n");            		
            	
                
                k_xp = Integer.parseInt(arrRow[i][0]);
                k_xpnm = arrRow[i][1];
                k_yp = Integer.parseInt(arrRow[i][3]);
                k_ypnm = arrRow[i][4];
                k_zp = Integer.parseInt(arrRow[i][6]);
                

                //System.out.println(returnValue.toString());
            }
            
       
            
            /*
            while(rs.next())
            {
                returnValue.append("<table border='1'>		\n");
                returnValue.append("	<tr>		\n");
                
                if( k_xp == rs.getInt("K_XP") ) 
                	returnValue.append("		<td></td>		\n");
                else
                	returnValue.append("		<td>"+rs.getInt("K_XP")+"</td>		\n");
                
                
                
                
                
                if( k_xp == rs.getInt("K_XP") )
                returnValue.append("		<td>"+rs.getString(4)+"</td>		\n");
                else
                returnValue.append("		<td>"+rs.getString(4)+"</td>		\n");
                    
                if( k_xp == rs.getInt("K_XP") && k_yp == rs.getInt("K_YP") )
                	returnValue.append("		<td>"+rs.getInt("K_YP")+"</td>		\n");
                else
                	returnValue.append("		<td>"+rs.getInt("K_YP")+"</td>		\n");
                
                if( k_xp == rs.getInt("K_XP") && k_yp == rs.getInt("K_YP") )
                returnValue.append("		<td>"+rs.getString(5)+"</td>		\n");
                else
                returnValue.append("		<td>"+rs.getString(5)+"</td>		\n");

                if( k_xp == rs.getInt("K_XP") && k_yp == rs.getInt("K_YP") && k_zp == rs.getInt("K_ZP") )
                	returnValue.append("		<td bgcolor='CCCCCC'></td>		\n");
                else
                	returnValue.append("		<td>"+rs.getInt("K_ZP")+"</td>		\n");
                
                if( k_xp == rs.getInt("K_XP") && k_yp == rs.getInt("K_YP") && k_zp == rs.getInt("K_ZP") )
                returnValue.append("		<td bgcolor='CCCCCC'></td>		\n");
                else
                returnValue.append("		<td>"+rs.getString(6)+"</td>		\n");
                
                
               // returnValue.append("		<td>"+rs.getString("EXKEY")+"</td>		\n");
                returnValue.append("	</tr>		\n");
                
                k_xp = rs.getInt("K_XP");
                k_yp = rs.getInt("K_YP");
                k_zp = rs.getInt("K_ZP");
            }
            */
            
            returnValue.append("</table>		\n");

            
            } catch(SQLException ex) {
            	
            	
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }

        return returnValue.toString();
    }  
    
    //상위 키워드그룹 추가 
	public void insert(String val, String m_seq) throws SQLException, ClassNotFoundException, Exception {
		
		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_XP) MAX_XP FROM "+tbl_name+" ");			
			rs = stmt.executeQuery(sb.toString());
			if( rs.next() ) {
				k_xp = rs.getInt("MAX_XP");
			} else {
				k_xp = 0;
			}
			
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_POS) MAX_POS FROM "+tbl_name+" WHERE K_YP=0 AND K_ZP=0 ");	
			rs = stmt.executeQuery(sb.toString());
			if( rs.next() ) {
				k_pos = rs.getInt("MAX_POS");
			} else {
				k_pos = 0;
			}
			
			k_xp++;
			k_pos++;
			
			
			//대그룹만 SG_SEQ넣기~(기본은 전부다~)
			sb = new StringBuffer();
			sb.append("SELECT SG_SEQ FROM SITE_GROUP");	
			rs = stmt.executeQuery(sb.toString());
			String sg_seqs = "";
			while(rs.next()){
				if(sg_seqs.equals("")){
					sg_seqs = rs.getString("SG_SEQ");
				}else{
					sg_seqs += "," + rs.getString("SG_SEQ");
				}
			}
			
			
			sb = new StringBuffer();
			sb.append("INSERT INTO "+tbl_name+"(K_XP, K_YP, K_ZP, K_TYPE, K_POS, K_OP, K_VALUE, K_REGDATE, SG_SEQS) VALUES("+k_xp+",0,0,1,"+k_pos+",0,'"+val+"','"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"', '"+sg_seqs+"')");
									
			Log.debug(sb.toString()+"  상위 키워드그룹 추가 :["+m_seq+"]");
			stmt.execute(sb.toString());
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_SEQ)AS PKEY FROM "+tbl_name+"");
									
			Log.debug(sb.toString()+"  상위 키워드그룹 추가 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.insertTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	//키워드그룹 추가
    public void insert( int xp, String val, String m_seq )
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		
		String nowDate = du.getCurrentDate("yyyyMMdd");
	    String nowTime = du.getCurrentDate("HHmmss");

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			
			sb.append("INSERT INTO "+tbl_name+"( K_XP, K_YP, K_ZP, K_TYPE, K_POS, K_OP, K_VALUE, K_REGDATE) \n");
			sb.append("   SELECT   K_XP, K_YP + 1, 0, 1, K_POS, 0, '"+val+"','"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"' \n");
			sb.append("   FROM (SELECT  K_XP, K_YP, K_POS \n");
			sb.append("         FROM "+tbl_name+" \n");
			sb.append("         WHERE K_XP = "+xp+" \n");
			sb.append("         	  \n");
			sb.append("   ORDER BY K_YP DESC LIMIT 1)m \n");
					
			Log.debug(sb.toString()+"  키워드그룹 추가 :["+m_seq+"]");
			stmt.execute( sb.toString() );
			
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_SEQ)AS PKEY FROM "+tbl_name+"");
									
			Log.debug(sb.toString()+"  키워드그룹 추가 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.insertTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    }
	
	//키워드 추가
    public void insert( int xp, int yp, String val, int op, String m_seq )
    throws ClassNotFoundException, Exception {
    	DBconn  conn  = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();

			sb.append("INSERT INTO "+tbl_name+"( K_XP, K_YP, K_ZP, K_TYPE, K_POS, K_OP, K_VALUE, K_REGDATE) \n");
			sb.append("   SELECT K_XP, K_YP, K_ZP + 1, 2, K_POS, "+op+", '"+val+"','"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"' \n");
			sb.append("   FROM (SELECT  K_XP, K_YP, K_ZP, K_POS \n");
			sb.append("         FROM "+tbl_name+" \n");
			sb.append("         WHERE K_XP = "+xp+" AND K_YP = "+yp+"  \n");
			sb.append("         ORDER BY K_ZP DESC LIMIT 1)m \n");
						
			Log.debug(sb.toString()+"  키워드 추가 :["+m_seq+"]");
			stmt.execute( sb.toString() );
			
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_SEQ)AS PKEY FROM "+tbl_name+"");
									
			Log.debug(sb.toString()+"  키워드 추가 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.insertTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    } 

	//제외키워드 추가
    public void insert( int xp, int yp, int zp, String val, int op, String m_seq )
    throws ClassNotFoundException, Exception {
    	
		int Ktype = 11;
		String k_pos = "";

        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			
			sb.append("SELECT MAX(K_TYPE)+1 AS NEXT_TYPE \n");
			sb.append("FROM "+tbl_name+" \n");
			sb.append("WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP="+zp+" AND K_TYPE>10 \n");
			Log.debug(sb.toString());
			rs = stmt.executeQuery( sb.toString() );			
			if( rs.next() ) {
				Ktype = rs.getInt("NEXT_TYPE");
			}

			if( Ktype < 11 ) Ktype = 11;
			
			// k_pos 검색
			sb = new StringBuffer();			
			sb.append("SELECT MAX(K_POS) AS K_POS \n");
			sb.append("FROM "+tbl_name+" \n");
			sb.append("WHERE K_XP="+xp+" \n");
			Log.debug(sb.toString());
			rs = stmt.executeQuery( sb.toString() );			
			if( rs.next() ) {
				k_pos = rs.getString("K_POS");
			}
						
			sb = new StringBuffer();			
			sb.append("INSERT INTO "+tbl_name+"( K_XP, K_YP, K_ZP, K_TYPE, K_POS, K_OP, K_VALUE, K_REGDATE) \n");
			sb.append("VALUES("+xp+", "+yp+", "+zp+", "+Ktype+", "+k_pos+", "+op+", '"+val+"','"+du.getCurrentDate("yyyy-MM-dd HH:mm:ss")+"') \n");
			Log.debug(sb.toString()+"  제외 키워드 추가 :["+m_seq+"]");
			//rs.close();
			stmt.execute( sb.toString() );
			
			//rs.close();
			sb = new StringBuffer();
			sb.append("SELECT MAX(K_SEQ) AS PKEY FROM "+tbl_name+"");
									
			Log.debug(sb.toString()+"  키워드 추가 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			/*
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.insertTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			*/
	
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
    } 
    
    
	//대분류명 수정
	public void update( int xp, String val, String m_seq ) throws SQLException, ClassNotFoundException, Exception {
	
		try {
			
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();	
			sb.append(" UPDATE "+tbl_name+" SET K_VALUE='"+val+"' WHERE K_XP="+xp+" AND K_YP=0 AND K_ZP=0 ");			
			Log.debug(sb.toString()+"   대분류명 수정 :["+m_seq+"]");
			stmt.executeUpdate(sb.toString());
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ AS PKEY FROM "+tbl_name+" WHERE K_XP="+xp+" AND K_YP=0 AND K_ZP=0");
									
			Log.debug(sb.toString()+"  대분류명 수정 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.updateTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	//소분류명 수정
	public void update( int xp, int yp, String val, String m_seq ) throws SQLException, ClassNotFoundException, Exception {
		DBconn  conn  = null;     //DB 연결을 위한 Object 선언
		Statement stmt  = null;
		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			sb = new StringBuffer();
			sb.append("UPDATE "+tbl_name+" SET K_VALUE='"+val+"' WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP=0 ");
			Log.debug(sb.toString()+"   소분류명 수정 :["+m_seq+"]");
			stmt.executeUpdate(sb.toString());
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ AS PKEY FROM "+tbl_name+" WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP=0 ");
									
			Log.debug(sb.toString()+"  소분류명 수정 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.updateTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	//제외키워드  삭제
	public void delete( int xp, int yp, int zp, String type, String m_seq ) throws SQLException, ClassNotFoundException, Exception {
		
		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();		
				
			sb = new StringBuffer();
			sb.append(" DELETE FROM "+tbl_name+" WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP="+zp+" AND K_TYPE IN ("+type+") ");
			Log.debug(sb.toString()+"   제외키워드  삭제 :["+m_seq+"]");
			stmt.executeUpdate(sb.toString());
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ AS PKEY FROM "+tbl_name+" WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP="+zp+" AND K_TYPE IN ("+type+") ");
			Log.debug(sb.toString()+"  제외키워드  삭제 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.deleteTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	//키워드 삭제
	public void delete( int xp, int yp, int zp, String m_seq ) throws SQLException, ClassNotFoundException, Exception {
		
		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			sb.append(" DELETE FROM "+tbl_name+" WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP="+zp);
			Log.debug(sb.toString()+"   키워드  삭제 :["+m_seq+"]");
			stmt.execute(sb.toString());
			
			if(tbl_name.equals("KEYWORD")){
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM IDX WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP="+zp);			
				Log.debug(sb.toString()+"   키워드  삭제 :["+m_seq+"]");
				stmt.execute(sb.toString());
				
			}
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ AS PKEY FROM "+tbl_name+" WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_ZP="+zp+" AND K_TYPE=2 ");
									
			Log.debug(sb.toString()+"  키워드 삭제 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.deleteTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}            
        }
	}
	
	//키워드그룹 삭제
	public void delete( int xp, int yp, String m_seq ) throws SQLException, ClassNotFoundException, Exception {
		
		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();			
				
			sb = new StringBuffer();
			sb.append(" DELETE FROM "+tbl_name+" WHERE K_XP="+xp+" AND K_YP="+yp);			
			Log.debug(sb.toString()+"   키워드그룹  삭제 :["+m_seq+"]");
			stmt.execute(sb.toString());
				
			if(tbl_name.equals("KEYWORD")){
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM IDX WHERE K_XP="+xp+" AND K_YP="+yp);			
				Log.debug(sb.toString()+"   키워드그룹  삭제 :["+m_seq+"]");
				stmt.execute(sb.toString());
				
			}
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ AS PKEY FROM "+tbl_name+" WHERE K_XP="+xp+" AND K_YP="+yp+" AND K_TYPE=0 ");
									
			Log.debug(sb.toString()+"  키워드그룹  삭제 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.deleteTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
			
		} catch(SQLException ex) {
			Log.writeExpt(ex,sb.toString());
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	//상위키워드그룹 삭제
	public void delete( int xp, String m_seq ) throws SQLException, ClassNotFoundException, Exception {

		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();			
				
			sb = new StringBuffer();
			sb.append(" DELETE FROM "+tbl_name+" WHERE K_XP="+xp) ;			
			Log.debug(sb.toString()+"   상위 키워드그룹  삭제 :["+m_seq+"]");
			stmt.execute(sb.toString());
				
			if(tbl_name.equals("KEYWORD")){
				
				sb = new StringBuffer();
				sb.append(" DELETE FROM IDX WHERE K_XP="+xp) ;
				Log.debug(sb.toString()+"   상위 키워드그룹  삭제 :["+m_seq+"]");			
				stmt.execute(sb.toString());
				
			}
			
			rs.close();
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ AS PKEY FROM "+tbl_name+" WHERE K_XP="+xp+" AND K_TYPE=0 ");
									
			Log.debug(sb.toString()+"  상위 키워드그룹  삭제 :["+m_seq+"]");
			rs = stmt.executeQuery(sb.toString());
			
			if(rs.next())
			{
				key = rs.getString("PKEY");
			}
			
			//로그 관련
			LogMgr logMgr = new LogMgr();
			LogBean logBean = new LogBean();		
			logBean.setKey(key);
			logBean.setL_kinds(LogConstants.keywordKindsVal);
			logBean.setL_type(LogConstants.deleteTypeVal);
			logBean.setL_ip("");
			logBean.setM_seq(m_seq);
			
			//로그 저장
			logMgr.insertLog(logBean);
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
	}
	
	
	//키워드 on/off	
    public int keywordOnOff( int xp, int yp, int zp, int mode) {
    	DBconn  conn  = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		int result = 0;
		
        try {
        	conn  = new DBconn();
        	conn.getDBCPConnection();        	
			sb = new StringBuffer();
			
			System.out.println("mode:"+mode+"/xp:"+xp+"/yp:"+yp+"/zp"+zp);
			
			if(mode == 1){
				sb.append("UPDATE "+tbl_name+" SET K_USEYN ='Y' \n");			
				sb.append(" WHERE K_XP = ? AND K_YP = ? AND K_ZP = ? \n");
			}else if(mode == 2){
				sb.append("UPDATE "+tbl_name+" SET K_USEYN ='N' \n");			
				sb.append(" WHERE K_XP = ? AND K_YP = ? AND K_ZP = ? \n");
			}
			System.out.println(sb.toString());
			
			pstmt = conn.createPStatement(sb.toString());					
			int idx = 0;
			pstmt.clearParameters();
			pstmt.setInt(++idx, xp);
			pstmt.setInt(++idx, yp);
			pstmt.setInt(++idx, zp);
			result = pstmt.executeUpdate();
									
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return result;
    }

	//상위키워드그룹 위로 이동
	public void moveUP( int xp, String m_name ) throws SQLException, ClassNotFoundException, Exception {
	   	DBconn  conn  = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		String pos = null;
		String tgxp = null;
		String tgpos = null;
		
        try {
			conn  = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
				
			rs = stmt.executeQuery( "SELECT K_POS FROM "+tbl_name+" WHERE K_XP="+xp+" AND K_YP=0 AND K_ZP=0 " );
			if (rs.next()) {
				pos = rs.getString("K_POS");				
				sb.append("SELECT K_XP, K_POS \n");
				sb.append("FROM (SELECT K_POS, K_XP  \n");
				sb.append("        FROM "+tbl_name+" \n");
				sb.append("        WHERE K_YP = 0 AND K_ZP = 0 AND K_POS < "+pos+" AND K_USEYN='Y' \n");
				sb.append("ORDER BY K_POS DESC LIMIT 2)M \n");
				sb.append("LIMIT 1  \n");															
				Log.debug(sb.toString()+"   상위 키워드그룹  위로이동 :["+m_name+"]");
				rs = stmt.executeQuery( sb.toString() );
				if (rs.next()) {
					tgxp = rs.getString("K_XP");
					tgpos = rs.getString("K_POS");
					stmt.executeUpdate( "UPDATE "+tbl_name+" SET K_POS="+pos+" WHERE K_XP="+tgxp);
					stmt.executeUpdate( "UPDATE "+tbl_name+" SET K_POS="+tgpos+" WHERE K_XP="+xp );
				}
			}			
			
       } catch(SQLException ex) {
           System.out.println("SQLException"+ex);
       } finally {
       	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		   if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
           if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
	}
	
	//상위키워드그룹 아래로 이동
	public void moveDOWN( int xp, String m_name ) throws SQLException, ClassNotFoundException, Exception {
	  	String pos = "0";
		String tgxp = "0";
		String tgpos = "0";
		
        try {
	       	conn  = new DBconn();
	       	conn.getDBCPConnection();
			stmt = conn.createStatement();			
			rs = stmt.executeQuery( "SELECT K_POS FROM "+tbl_name+" WHERE K_XP="+xp+" AND K_YP=0 AND K_ZP=0 " );
			if (rs.next()) {
				pos = rs.getString("K_POS");
			sb = new StringBuffer();
			sb.append("SELECT K_XP, K_POS \n");
			sb.append("FROM (SELECT K_POS, K_XP \n");
			sb.append("            FROM "+tbl_name+" \n");
			sb.append("           WHERE K_YP = 0 AND K_ZP = 0 AND K_POS > "+pos+" \n");
			sb.append("        ORDER BY K_POS ASC LIMIT 2 )m \n");
			sb.append("LIMIT 1 \n");
							
			Log.debug(sb.toString()+"   상위 키워드그룹  아래로이동 :["+m_name+"]");
			rs = stmt.executeQuery( sb.toString() );
			if (rs.next()) {
				tgxp = rs.getString("K_XP");
				tgpos = rs.getString("K_POS");
							
				stmt.executeUpdate( "UPDATE "+tbl_name+" SET K_POS="+pos+" WHERE K_XP="+tgxp);
				stmt.executeUpdate( "UPDATE "+tbl_name+" SET K_POS="+tgpos+" WHERE K_XP="+xp );						
				}
			}
			
       } catch(SQLException ex) {
           //throw new MemberException("query error", ex);
    	   System.out.println("SQLException"+ex);
       } finally {
       		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
	}
	
	//사이트그룹 저장
	public void setSiteGroup( String xp, String sg_seqs ) throws SQLException, ClassNotFoundException, Exception {
	  	String pos = "0";
		String tgxp = "0";
		String tgpos = "0";
		
        try {
	       	conn  = new DBconn();
	       	conn.getDBCPConnection();
			stmt = conn.createStatement();			
			stmt.executeUpdate("UPDATE "+tbl_name+" SET SG_SEQS = '"+sg_seqs+"' WHERE K_XP = "+xp+" AND K_YP = 0 AND K_ZP = 0" );
			
       } catch(SQLException ex) {
           //throw new MemberException("query error", ex);
    	   System.out.println("SQLException"+ex);
       } finally {
       		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
	}
	
	//  키워드 트리
	public String GetHtml( String psDateFrom,String psDateTo,String MGxp, String showCount ) {
				
		StringBuffer sbhtml = null;
		String frontIcon    = "";
		String trID         = "";
		String spanID       = "";
		String imgID		= "";
		String trStyle      = "";
		String kgName       = "";
		String linkURL      = "";
		String imgAction    = "";
		String frontSpace   = "";
		String spanClass    = "";
		String sCount       = "";
		
        StringBuffer sb = null;

        String MinMtno      = "";
        String MaxMtno      = "";

		int xp=0;
		int yp=0;
		int zp=0;
		int onOffMode = 0;
		String onOffText = "";
		int prevxp = 0;
		int prevyp = 0;
		int prevzp = 0;			

		try {

		    MetaMgr metaMgr = new MetaMgr();
		    
		    if(tbl_name.equals("KEYWORD")){
		    	metaMgr.getMaxMinNo(psDateFrom, psDateTo);
			}else if(tbl_name.equals("PORTAL_SEARCH_KEYWORD")){
				metaMgr.getMaxMinNo(psDateFrom, psDateTo,"00","23","PORTAL_SEARCH_");
			}

            MinMtno= metaMgr.msMinNo;
            MaxMtno= metaMgr.msMaxNo;
            
            System.out.println("msMinNo = "+MinMtno);
            System.out.println("msMaxNo = "+MaxMtno);

            conn  = new DBconn();
            conn.getDBCPConnection();
            stmt    = conn.createStatement();

            sb = new StringBuffer();
            if( showCount.equals("false") ){
                
                sb.append(" SELECT K_XP, K_YP, K_ZP,K_OP, K_VALUE, K_OP, K_USEYN  	\n");
                sb.append(" FROM "+tbl_name+" 					\n");
                sb.append(" WHERE K_TYPE < 3 					\n");
                if( MGxp.length() > 0 )
                sb.append("   AND K_XP IN ("+MGxp+") 			\n");               
                sb.append(" ORDER BY K_POS, K_XP, K_YP, K_ZP 	\n");
            }else {   
            	String tempStr = null;
            	
                if( MGxp.length() > 0 ) tempStr = " AND B1.K_XP IN ("+MGxp+") ";
                else tempStr = "";
                sb.append(" SELECT B1.K_VALUE, B1.K_XP, B1.K_YP, B1.K_ZP, B1.K_USEYN, IFNULL(B2.CNT,0) AS CNT                                \n");
                sb.append(" FROM                                                                                                 \n");
                sb.append(" "+tbl_name+" B1 LEFT OUTER JOIN                                                                           \n");
                sb.append(" (                                                                                                    \n");
                sb.append("     SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                              \n");
                sb.append("     FROM "+tbl2_name+" A1,                                                                                    \n");
                sb.append("       (                                                                                              \n");
                sb.append("         SELECT DISTINCT K_XP, 0 AS K_YP, 0 AS K_ZP, MD_SEQ                                           \n");
                sb.append("         FROM "+tbl3_name+"                                                                                     \n");
                sb.append("         WHERE MD_SEQ BETWEEN  "+MinMtno+" AND "+MaxMtno+"                                                \n");
                sb.append("       ) A2                                                                                           \n");
                sb.append("     WHERE A1.MD_SEQ=A2.MD_SEQ                                                                        \n");
                sb.append("     GROUP BY K_XP                                                                                    \n");
                sb.append("     UNION                                                                                \n");
                sb.append(" 	SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                              \n");
                sb.append("     FROM "+tbl2_name+" A1,                                                                                    \n");
                sb.append("       (                                                                                              \n");
                sb.append("         SELECT DISTINCT K_XP, K_YP, 0 AS K_ZP, MD_SEQ                                                \n");
                sb.append("         FROM "+tbl3_name+"                                                                                     \n");
                sb.append("         WHERE MD_SEQ BETWEEN  "+MinMtno+" AND "+MaxMtno+"                                                \n");
                sb.append("       ) A2                                                                                           \n");
                sb.append("     WHERE A1.MD_SEQ=A2.MD_SEQ                                                                        \n");
                sb.append("     GROUP BY K_XP, K_YP                                                                              \n");
                sb.append(" 	UNION                                                                                \n");
                sb.append(" 	SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                              \n");
                sb.append("     FROM "+tbl2_name+" A1,                                                                                    \n");
                sb.append("       (                                                                                              \n");
                sb.append("         SELECT DISTINCT K_XP, K_YP, K_ZP, MD_SEQ                                                     \n");
                sb.append("         FROM "+tbl3_name+"                                                                                     \n");
                sb.append("         WHERE MD_SEQ BETWEEN  "+MinMtno+" AND "+MaxMtno+"                                                \n");
                sb.append("       ) A2                                                                                           \n");
                sb.append("     WHERE A1.MD_SEQ=A2.MD_SEQ                                                                        \n");
                sb.append("     GROUP BY K_XP, K_YP, K_ZP                                                                        \n");
                sb.append(" 	) B2 ON B1.K_XP=B2.K_XP AND B1.K_YP=B2.K_YP AND B1.K_ZP=B2.K_ZP                              \n");
                sb.append(" 	WHERE B1.K_TYPE<3  "+tempStr+"                              \n");
                sb.append(" ORDER BY B1.K_POS, B1.K_XP, B1.K_YP, B1.K_ZP ASC                                                     \n");
            }
            
            	

            Log.debug(sb.toString());
            System.out.println(sb.toString());
            rs = stmt.executeQuery( sb.toString() );
          
			spanID = "spanID_"+selectedXP+"_"+selectedYP+"_"+selectedZP;			
			
			sbhtml = new StringBuffer();
			sbhtml.append("<span id='tmpspan' style='display:none' class=''></span>");
			sbhtml.append("<input type=\"hidden\" name=\"kgmenu_id\" value=\""+spanID+"\">");
			sbhtml.append("<input type=\"hidden\" name=\"onoffmode\" value=\"\">");
									
			while(rs.next()){
				
            	kgName = rs.getString("k_value");            	
            	System.out.println();
            	xp = rs.getInt("k_xp");
            	yp = rs.getInt("k_yp");
            	zp = rs.getInt("k_zp");
            	
            	if(rs.getString("K_USEYN").equals("Y")){
            		onOffMode = 2;
            		onOffText = "<img src=\""+imagesURL+"on_btn.gif\" border=\"0\" style=display:none;>";
            	}else{
            		onOffMode = 1;
            		onOffText = "<img src=\""+imagesURL+"off_btn.gif\" border=\"0\" style=display:none;>";
            	}            	
            	
            	linkURL = baseTarget+".location.href='"+baseURL+"&xp="+xp+"&yp="+yp+"&zp="+zp+"'";
				spanID = "spanID_"+xp+"_"+yp+"_"+zp;
				imgID = "imgID_"+xp+"_"+yp+"_"+zp;
				
            	if (yp==0) {
					frontSpace="";
					frontIcon = "folder01_close.gif";
					if (selectedXP==xp) { trStyle = "display:"; frontIcon = "folder01_open.gif";}
					else  { trStyle = "display:none"; }
					trID = "trID_"+xp;
					imgAction = " toggleme('"+imgID+"','trID_"+xp+"'); ";
					kgName = "<b>" + kgName + "</b>";
				}
			
            	else if (zp==0) {
					frontSpace="&nbsp;&nbsp;";
					frontIcon = "folder02_close.gif";
					if (selectedXP==xp && selectedYP==yp) { trStyle = "display:"; frontIcon = "folder02_open.gif";}
					else  { trStyle = "display:none"; }
					trID = "trID_"+xp+"_"+yp;
					imgAction = " toggleme('"+imgID+"', 'trID_"+xp+"_"+yp+"'); ";
					kgName = "<b>" + kgName + "</b>";
				}			
            	else {
            		
            		if(rs.getString("K_OP").equals("1"))
            		{
            			frontIcon = "icon_a.jpg";
            		}else if(rs.getString("K_OP").equals("2")){
            			frontIcon = "icon_n.jpg";
            		}else if(rs.getString("K_OP").equals("3")){
            			frontIcon = "icon_p.jpg";
            		}else if(rs.getString("K_OP").equals("4")){
            			frontIcon = "icon_o.jpg";
            		}else if(rs.getString("K_OP").equals("5")){
            			frontIcon = "icon_k.gif";
            		}else if(rs.getString("K_OP").equals("6")){
            			frontIcon = "icon_p2.jpg";
            		}else if(rs.getString("K_OP").equals("7")){
            			frontIcon = "icon_p3.jpg";
            		}
            		
            		
            		
					frontSpace="&nbsp;&nbsp;&nbsp;&nbsp;";					
					trStyle = "";
					trID = "";
					imgAction = " ";																			
				}
				
				if (selectedXP==xp && selectedYP==yp && selectedZP==zp) {
					spanClass=" class=\"kgmenu_selected\" ";
				}
				else {
					spanClass=" class=\"kgmenu\" ";
				}
				
				if (prevyp!=0 && yp==0)
				{
					sbhtml.append("</div>\n");
				} 
				else if ( prevxp != 0 && prevxp != xp && prevyp==0 && yp==0)
				{
					sbhtml.append("</div>\n");
				}
				if (prevzp!=0 && zp==0)
				{
					sbhtml.append("  </div>\n");
				} 
				else if ( prevyp !=0 && prevyp != yp && prevzp==0 && zp==0)
				{
					sbhtml.append("  </div>\n");
				}

				if (showCount.equals("true")){
					
					sCount = "<span class=\"kgmenu_cnt\">[" + rs.getString("cnt") + "]</span>";
				}	
				
				if (zp==0) { 
					sbhtml.append("    "+frontSpace+"\n"+
						"      <img id=\""+imgID+"\" class=\"kgmenu_img\" src=\""+imagesURL+frontIcon+"\" onClick=\""+imgAction+"\" border=\"0\" align=absmiddle>"+
						" <span id=\""+spanID+"\" "+spanClass+" onclick=\""+linkURL+"; kg_click(this); "+imgAction+" \" onmouseover=\"kg_over(this);\""+
						"onmouseout=\"kg_out(this);\">" 
						+ kgName + " " + sCount +
						"</span></br>\n");
				}
				else { 
					sbhtml.append("    "+frontSpace+"\n"+
						"      <img id=\""+imgID+"\" class=\"kgmenu_img\" src=\""+imagesURL+frontIcon+"\" onClick=\""+imgAction+"\" border=\"0\" align=absmiddle>"+
						" <span id=\""+spanID+"\" "+spanClass+" onclick=\""+linkURL+"; kg_click(this); "+imgAction+" \" onmouseover=\"kg_over(this);\""+
						"onmouseout=\"kg_out(this);\">");
						
					if (showCount.equals("false")){
						sbhtml.append("<nobr style =\"text-overflow:ellipsis; overflow:hidden; width:110px; \" title=\" " +rs.getString("k_value").replaceAll("\"","")+ " \">");
					}else{ 
						sbhtml.append("<nobr style =\"text-overflow:ellipsis; overflow:hidden; width:70px; \" title=\" " +rs.getString("k_value").replaceAll("\"","")+ " \">");
					}
						
						sbhtml.append(kgName + "</nobr> " + sCount + "</span><a href=\"javascript:keywordOnOff("+xp+","+yp+","+zp+","+onOffMode+");\" style=\"cursor:hand;\"><span class=\"kgmenu_cnt\">"+onOffText+"</span></a></br>\n");					
				}
				
				// block start 
				if (yp==0)
				{
					sbhtml.append("<div id=\""+trID+"\" style=\""+trStyle+"\">\n");
				}
				else if (zp==0)
				{
					sbhtml.append("  <div id=\""+trID+"\" style=\""+trStyle+"\">\n");
				}
				
				prevxp=xp;
				prevyp=yp;
				prevzp=zp;
            }
		
		sbhtml.append("  </div>		\n");
		sbhtml.append("</div>		\n");
		
		
        } catch (SQLException ex) {
            Log.writeExpt(ex);
        } catch (Exception ex) {
            Log.writeExpt(ex);          
        } finally {
            try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( conn != null) conn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
		return sbhtml.toString();
	}
	
	public String GetScript() {
		String kgScript = ""+
			"<SCRIPT LANGUAGE=\"JavaScript\">					\n"+
			"<!--												\n"+
			"function toggleme(object, subo) {					\n"+
			"	var obj = document.getElementById(object);		\n"+
			"	var subObj = eval(\"document.all.\"+subo);		\n"+
			"	var i = 0;										\n"+
			"	var subcnt = subObj.length;						\n"+
			"	if (obj.src.indexOf('folder01_close')>0) {		  \n"+
			"		if (subObj)	{								  \n"+
			"			obj.src = '"+imagesURL+"folder01_open.gif'; \n"+
			"			subObj.style.display='';				  \n"+
			"		}											  \n"+
			"	}else if(obj.src.indexOf('folder02_close')>0){										  \n"+
			"		if (subObj)	{								  \n"+
			"			obj.src = '"+imagesURL+"folder02_open.gif';  \n"+
			"			subObj.style.display='';			\n"+
			"		}											\n"+
			"	}else if(obj.src.indexOf('folder01_open')>0){										  \n"+
			"		if (subObj)	{								  \n"+
			"			obj.src = '"+imagesURL+"folder01_close.gif';  \n"+
			"			subObj.style.display='none';			\n"+
			"		}											\n"+
			"	}else if(obj.src.indexOf('folder02_open')>0){										  \n"+
			"		if (subObj)	{								  \n"+
			"			obj.src = '"+imagesURL+"folder02_close.gif';  \n"+
			"			subObj.style.display='none';			\n"+
			"		}											\n"+
			"	}												\n"+
			"}													\n"+
			"function kg_over(obj) {							\n"+
			"	tmpspan.className=obj.className;				\n"+
			"	obj.className='kgmenu_over';					\n"+
			"}													\n"+
			"function kg_out(obj){								\n"+
			"	obj.className=tmpspan.className;				\n"+
			"}													\n"+
			"function kg_click(obj){							\n"+
			"	var prvObj = eval('document.all.'+document.all.kgmenu_id.value);	\n"+
			"	if (prvObj)										\n"+
			"	{												\n"+
			"		prvObj.className = 'kgmenu';				\n"+
			"	}												\n"+
			"	document.all.kgmenu_id.value = obj.id;			\n"+
			"	obj.className='kgmenu_selected';				\n"+
			"	tmpspan.className=obj.className;				\n"+
			"}													\n"+
			"function keywordOnOff(xp,yp,zp,mode)        {    	\n"+
			"	document.leftmenu.xp.value = xp;				\n"+
			"	document.leftmenu.yp.value = yp;				\n"+
			"	document.leftmenu.zp.value = zp;			               \n"+
			"	document.leftmenu.onoffmode.value = mode;                  \n"+
			"	document.leftmenu.target = '';  \n"+
			"	document.leftmenu.action = 'admin_keyword_onoff_prc.jsp';   \n"+
			"	document.leftmenu.submit();							       \n"+
			"}													\n"+			
			"//-->												\n"+
			"</SCRIPT>											\n";		
		return kgScript;
	}
	
	public String GetStyle() {
		StringBuffer sb = new StringBuffer();
		sb.append("																														\n");
		sb.append("<style>																												\n");
		sb.append("																													\n");
		sb.append(".kgmenu { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; cursor:hand; border:1px solid #EAF5F9; }		\n");
		sb.append(".kgmenu_cnt { FONT-SIZE: 10px; COLOR: navy; FONT-FAMILY: \"tahoma\"; padding:0 0 0 3 }								\n");
		sb.append(".kgmenu_selected { font-size:12px; height:18px; color:#7F7F7F; padding:3 3 1 3; border:1px solid #999999; 			\n");
		sb.append("                  background-color:#EAF5F9; cursor:hand;}																				\n");
		sb.append(".kgmenu_over { font-size:12px; height:18px; color:#333333; padding:3 3 1 3; cursor:hand; border:1px solid #999999; }	\n");
		sb.append(".kgmenu_img { cursor:hand; }																							\n");
		sb.append("																												\n");
		sb.append("</style>																												\n");
		return sb.toString();
	}
	
	public ArrayList getXpList(){
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ, K_XP, K_YP, K_ZP, K_VALUE\n");
			sb.append(", K_POS, K_TYPE, K_REGDATE, K_USEYN, K_OP, SG_SEQS\n");
			sb.append("FROM KEYWORD\n");
			sb.append("WHERE K_YP = 0\n");
			sb.append("AND K_USEYN = 'Y'\n");
			sb.append("ORDER BY K_XP\n");
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				KeywordBean kb = new KeywordBean();
				kb.setK_seq(rs.getString("K_SEQ"));
				kb.setK_xp(rs.getString("K_XP"));
				kb.setK_yp(rs.getString("K_YP"));
				kb.setK_zp(rs.getString("K_ZP"));
				kb.setK_value(rs.getString("K_VALUE"));
				kb.setK_pos(rs.getString("K_POS"));
				kb.setK_type(rs.getString("K_TYPE"));
				kb.setK_regdate(rs.getString("K_REGDATE"));
				kb.setK_useyn(rs.getString("K_USEYN"));
				kb.setK_op(rs.getString("K_OP"));
				kb.setSg_seqs(rs.getString("SG_SEQS"));
				result.add(kb);
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
	}
	
	//키워드 대그룹명을 가져온다.
	public ArrayList getXpgroupList()
	{
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ, K_XP, K_YP, K_ZP, K_VALUE\n");      
			sb.append(", K_POS, K_TYPE, K_REGDATE, K_USEYN, K_OP, SG_SEQS\n");
			sb.append("FROM KEYWORD\n");                                 
			sb.append("WHERE K_YP = 0\n");                               
			sb.append("AND K_USEYN = 'Y'\n");                            
			sb.append("ORDER BY K_XP ASC\n");                            
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				KeywordBean kb = new KeywordBean();
				kb.setK_seq(rs.getString("K_SEQ"));
				kb.setK_xp(rs.getString("K_XP"));
				kb.setK_yp(rs.getString("K_YP"));
				kb.setK_zp(rs.getString("K_ZP"));
				kb.setK_value(rs.getString("K_VALUE"));
				kb.setK_pos(rs.getString("K_POS"));
				kb.setK_type(rs.getString("K_TYPE"));
				kb.setK_regdate(rs.getString("K_REGDATE"));
				kb.setK_useyn(rs.getString("K_USEYN"));
				kb.setK_op(rs.getString("K_OP"));
				kb.setSg_seqs(rs.getString("SG_SEQS"));
				result.add(kb);
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
    }
	
	//매핑 안된 키워드 대그룹명을 가져온다.
	public ArrayList XpgroupList()
	{
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
                      
			sb.append("#매핑 안된 키워드 목록                                                                                                                           															\n");
			sb.append("SELECT A.K_SEQ                                                                                                                                   \n");
			sb.append("      ,A.K_XP                                                                                                                                    \n");
			sb.append("      ,A.K_YP                                                                                                                                    \n");
			sb.append("      ,B.K_VALUE AS KEYWORD_1DEPS                                                                                                                \n");
			sb.append("      ,A.K_VALUE AS KEYWORD_2DEPS                                                                                                                \n");
			sb.append("  FROM KEYWORD A                                                                                                                                 \n");
			sb.append("      ,KEYWORD B                                                                                                                                 \n");
			sb.append("    WHERE A.K_SEQ NOT IN(SELECT K_SEQ FROM KEYWORD A, MAP_KEYWORD_ISSUE_CODE B WHERE A.K_XP=B.K_XP AND A.K_YP=B.K_YP AND A.K_ZP=0)               \n");
			sb.append("          AND A.K_ZP=0                                                                                                                           \n");
			sb.append("          AND A.K_YP<>0                                                                                                                          \n");
			sb.append("          AND A.K_XP=B.K_XP                                                                                                                      \n");
			sb.append("          AND B.K_YP=0                                                                                                                           \n");
			sb.append("     ORDER BY A.K_XP,A.K_YP ASC                                                                                                                  \n");
			                                                                                                                                                        
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				KeywordBean kb = new KeywordBean();
				kb.setK_seq(rs.getString("A.K_SEQ"));
				kb.setK_xp(rs.getString("A.K_XP"));
				kb.setK_value(rs.getString("KEYWORD_1DEPS")+" > "+rs.getString("KEYWORD_2DEPS"));			
				result.add(kb);
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
    }
	
	//매핑된 키워드 대그룹명을 가져온다.
	public ArrayList getMapXpgroupList()
	{
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
                      
			sb.append("	 SELECT DISTINCT B.K_SEQ            \n");
			sb.append("      , B.K_XP                       \n");
			sb.append("      , B.K_VALUE                    \n");
			sb.append("   FROM MAP_KEYWORD_ISSUE_CODE A     \n");
			sb.append("      , KEYWORD B                    \n");
			sb.append("  WHERE A.K_XP = B.K_XP              \n");
			sb.append("    AND B.K_YP = 0                   \n");
			sb.append("    AND B.K_USEYN = 'Y'              \n");
			sb.append("    ORDER BY B.K_XP ASC              \n");
			
			System.out.println(sb.toString());
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				KeywordBean kb = new KeywordBean();
				kb.setK_seq(rs.getString("K_SEQ"));
				kb.setK_xp(rs.getString("K_XP"));
				kb.setK_value(rs.getString("K_VALUE"));
				result.add(kb);
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
    }
	
	public ArrayList getYpList(String xp){
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ, K_XP, K_YP, K_ZP, K_VALUE\n");
			sb.append(", K_POS, K_TYPE, K_REGDATE, K_USEYN, K_OP, SG_SEQS\n");
			sb.append("FROM KEYWORD\n");
			sb.append("WHERE K_XP = "+xp+" AND K_YP != 0 AND K_ZP = 0\n");
			sb.append("AND K_USEYN = 'Y'\n");
			sb.append("ORDER BY K_YP\n");
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				KeywordBean kb = new KeywordBean();
				kb.setK_seq(rs.getString("K_SEQ"));
				kb.setK_xp(rs.getString("K_XP"));
				kb.setK_yp(rs.getString("K_YP"));
				kb.setK_zp(rs.getString("K_ZP"));
				kb.setK_value(rs.getString("K_VALUE"));
				kb.setK_pos(rs.getString("K_POS"));
				kb.setK_type(rs.getString("K_TYPE"));
				kb.setK_regdate(rs.getString("K_REGDATE"));
				kb.setK_useyn(rs.getString("K_USEYN"));
				kb.setK_op(rs.getString("K_OP"));
				kb.setSg_seqs(rs.getString("SG_SEQS"));
				result.add(kb);
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
	}
	
	
	public ArrayList getZpList(String xp, String yp){
		ArrayList result = new ArrayList();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT K_SEQ, K_XP, K_YP, K_ZP, K_VALUE\n");
			sb.append(", K_POS, K_TYPE, K_REGDATE, K_USEYN, K_OP, SG_SEQS\n");
			sb.append("FROM KEYWORD\n");
			sb.append("WHERE K_XP = "+xp+" AND K_YP = "+yp+"\n");
			sb.append("AND K_USEYN = 'Y' AND K_TYPE = 2\n");
			sb.append("ORDER BY K_XP, K_YP, K_ZP\n");
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()){
				KeywordBean kb = new KeywordBean();
				kb.setK_seq(rs.getString("K_SEQ"));
				kb.setK_xp(rs.getString("K_XP"));
				kb.setK_yp(rs.getString("K_YP"));
				kb.setK_zp(rs.getString("K_ZP"));
				kb.setK_value(rs.getString("K_VALUE"));
				kb.setK_pos(rs.getString("K_POS"));
				kb.setK_type(rs.getString("K_TYPE"));
				kb.setK_regdate(rs.getString("K_REGDATE"));
				kb.setK_useyn(rs.getString("K_USEYN"));
				kb.setK_op(rs.getString("K_OP"));
				kb.setSg_seqs(rs.getString("SG_SEQS"));
				result.add(kb);
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
	}
	
	public KeywordBean getKeywordInfo(String k_seq){
		KeywordBean kb = null;
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT K_XP, K_YP, K_ZP, K_VALUE\n");
			sb.append("FROM KEYWORD\n");
			sb.append("WHERE K_SEQ = "+k_seq+"\n");
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			if(rs.next()){
				kb = new KeywordBean();
				kb.setK_xp(rs.getString("K_XP"));
				kb.setK_yp(rs.getString("K_YP"));
				kb.setK_zp(rs.getString("K_ZP"));
				kb.setK_value(rs.getString("K_VALUE"));
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return kb;
	}
	
	//키워드 삭제
	public void updateNearLen(String nearLen ) throws SQLException, ClassNotFoundException, Exception {
		updateNearLen(0,0,0,nearLen);
	}
	
	public ArrayList<HashMap<String, String>> getRateOfDecrease(String today, String yesterday, String current_hour, int row_limit){
		ArrayList<HashMap<String, String>>  result = new ArrayList<HashMap<String, String>>();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT A.K_VALUE                                                                                                               \n");
			sb.append("   , IFNULL(B.MAK_CNT,0) AS P_CNT                                                                                              \n");
			sb.append("   , IFNULL(A.MAK_CNT,0) AS C_CNT                                                                                              \n");
			sb.append("   , IF((IFNULL(A.MAK_CNT,0)-IFNULL(B.MAK_CNT,0)) = 0, 0, IF((IFNULL(A.MAK_CNT,0)-IFNULL(B.MAK_CNT,0)) > 0, 1, -1)) AS POINTER \n");  
			sb.append("   , ROUND(IFNULL((ABS(IFNULL(A.MAK_CNT,0)-IFNULL(B.MAK_CNT,0)) / IFNULL(B.MAK_CNT,0)* 100),0)) AS GAP                       \n");
			sb.append("   , A.K_XP											                                                                          \n");
			sb.append("   , A.K_YP                                                                                                                    \n");
			sb.append("   , A.K_ZP                                                                                                                    \n");
			sb.append("FROM (                                                                                                                         \n");
			sb.append("    SELECT MAK_DATE, MAK_HOUR, K_XP, K_YP, K_ZP, K_VALUE, MAK_CNT                                                              \n");
			sb.append("       FROM META_ANA_KEYWORD_HOUR                                                                                              \n");
			sb.append("      WHERE MAK_DATE = '"+today+"'                                                                                              \n");
			sb.append("         AND MAK_HOUR = '"+current_hour+"'                                                                                                   \n");
			sb.append(")A LEFT OUTER JOIN  (                                                                                                          \n");
			sb.append("  SELECT MAK_DATE, MAK_HOUR, K_XP, K_YP, K_ZP, K_VALUE, MAK_CNT                                                                \n");
			sb.append("       FROM META_ANA_KEYWORD_HOUR                                                                                              \n");
			sb.append("      WHERE MAK_DATE = '"+yesterday+"'                                                                                              \n");
			sb.append("         AND MAK_HOUR = '"+current_hour+"'                                                                                                   \n");
			sb.append(")B ON A.K_XP = B.K_XP AND A.K_YP = B.K_YP AND A.K_ZP = B.K_ZP AND A.K_VALUE = B.K_VALUE                                        \n");
			sb.append("ORDER  BY GAP DESC                                                                                                             \n");
			sb.append("LIMIT "+row_limit+"                                                                                                                       \n");
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			HashMap<String, String> item = new HashMap<String, String>();
			while(rs.next()){
				item = new HashMap<String, String>();
				item.put("K_VALUE", rs.getString("K_VALUE"));
				item.put("P_CNT", su.digitFormat(rs.getString("P_CNT")));
				item.put("C_CNT", su.digitFormat(rs.getString("C_CNT")));
				item.put("POINTER", rs.getString("POINTER"));
				item.put("GAP", rs.getString("GAP")+"%");
				item.put("K_XP", rs.getString("K_XP"));
				item.put("K_YP", rs.getString("K_YP"));
				item.put("K_ZP", rs.getString("K_ZP"));
				result.add(item);
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
	}
	
	public boolean delKeyword(String delList )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        boolean rebln = false;
        
        try {       	      	
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();	      
        	
   	 		sb = new StringBuffer();
		   	sb.append("SELECT DISTINCT K_XP,K_YP from KEYWORD   \n");
		   	sb.append("		  WHERE K_SEQ IN("+delList+")       \n");
		   	
       	 	System.out.println(sb.toString());
       	 	rs = stmt.executeQuery(sb.toString());    
       	 	
       	 	String[] kseqs = delList.split(",");
			String[] k_xp = new String[kseqs.length];
			String[] k_yp = new String[kseqs.length];
			
            int cnt = 0;
       	 	while(rs.next()){
       	 	k_xp[cnt] = rs.getString("K_XP");
       	 	k_yp[cnt] = rs.getString("K_YP");				
       	 	cnt++;
    	 	}

        	if(delList.length()>1000)
        	{
        		rebln = false;
        	}else{
        		
        		for (int i = 0; i < kseqs.length; i++) {
					
				
	        	sb = new StringBuffer();
	            
	        	sb.append("DELETE FROM MAP_KEYWORD_ISSUE_CODE		\n");
			 	sb.append("WHERE K_XP="+k_xp[i]+" AND K_YP="+k_yp[i]+"    		\n");
	   	 	
			 	Log.debug(sb.toString());
			 	stmt.executeUpdate(sb.toString());
			 	rebln = true;
        		}
        	}
	        
       	 	
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return rebln;
	
	}
	
	public KeywordBean getKeyword( String k_seq )
	{
		DBconn  conn  = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        
        KeywordBean Mapkeword = null;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       	 	
		    sb.append("SELECT A.K_VALUE AS KEY_2DEPS       \n");
		    sb.append("      ,B.K_VALUE AS KEY_1DEPS       \n");
		    sb.append(" FROM KEYWORD A             		   \n");
		    sb.append("     ,KEYWORD B              	   \n");
		    sb.append("   WHERE A.K_XP=B.K_XP       	   \n");
		    sb.append("     AND A.K_SEQ="+k_seq+"    	   \n");
		    sb.append("     AND B.K_YP=0    	 	 	   \n");
       	 	
       	 	//Log.debug(sb.toString());
       	 	
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	Mapkeword = new KeywordBean();
            	Mapkeword.setK_value(rs.getString("KEY_1DEPS")+" > "+rs.getString("KEY_2DEPS"));
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return Mapkeword;
    }
	
	public KeywordBean[] getMapList(String SearchKey, String MapxpList, String sGroupKind)
	{
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
        Statement stmt = null;
        KeywordBean[] arrMapkeword= null;
        KeywordBean Mapkeword = null;
        int rowCnt = 0;
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	
       	 	sb = new StringBuffer();
       	 	 	 	
	       	sb.append("SELECT *                                                                                                                                                                                                               \n");
	        sb.append("FROM (                                                                                                                                                                                                                 \n");
	        sb.append("      SELECT A.K_XP                                                                                                                                                                                                    \n");
	        sb.append("           , A.K_YP                                                                                                                                                                                                    \n");
            sb.append("			  , (SELECT CONCAT(C.K_SEQ)                                                                                                                                                                                   \n");
            sb.append("                 FROM KEYWORD B                                                                                                                                                                                        \n");
            sb.append("                    , KEYWORD C                                                                                                                                                                                        \n");
            sb.append("                WHERE B.K_XP = C.K_XP                                                                                                                                                                                  \n");
            sb.append("                  AND B.K_XP = A.K_XP                                                                                                                                                                                  \n");
            sb.append("                  AND B.K_YP = A.K_YP                                                                                                                                                                                  \n");
            sb.append("                  AND B.K_ZP = 0                                                                                                                                                                                       \n");
            sb.append("                  AND C.K_YP = 0                                                                                                                                                                                       \n");
            sb.append("                  AND C.K_ZP = 0) AS KEYWORD_1DEPS_SEQ                                                                                                                                                                 \n");
	        sb.append("			  , (SELECT CONCAT(B.K_SEQ)                                                                                                                                                                                   \n");
            sb.append("                 FROM KEYWORD B                                                                                                                                                                                        \n");
            sb.append("                    , KEYWORD C                                                                                                                                                                                        \n");
            sb.append("                WHERE B.K_XP = C.K_XP                                                                                                                                                                                  \n");
            sb.append("                  AND B.K_XP = A.K_XP                                                                                                                                                                                  \n");
            sb.append("                  AND B.K_YP = A.K_YP                                                                                                                                                                                  \n");
            sb.append("                  AND B.K_ZP = 0                                                                                                                                                                                       \n");
            sb.append("                  AND C.K_YP = 0                                                                                                                                                                                       \n");
            sb.append("                  AND C.K_ZP = 0) AS KEYWORD_2DEPS_SEQ                                                                                                                                                                 \n");
	        sb.append("           , (SELECT CONCAT(C.K_VALUE,' > ', B.K_VALUE)                                                                                                                                                                \n");
	        sb.append("                 FROM KEYWORD B                                                                                                                                                                                        \n");
	        sb.append("                    , KEYWORD C                                                                                                                                                                                        \n");
	        sb.append("                WHERE B.K_XP = C.K_XP                                                                                                                                                                                  \n");
	        sb.append("                  AND B.K_XP = A.K_XP                                                                                                                                                                                  \n");
	        sb.append("                  AND B.K_YP = A.K_YP                                                                                                                                                                                  \n");
	        sb.append("                  AND B.K_ZP = 0                                                                                                                                                                                       \n");
	        sb.append("                  AND C.K_YP = 0                                                                                                                                                                                       \n");
	        sb.append("                  AND C.K_ZP = 0) AS KEYWORD                                                                                                                                                                           \n");
	        sb.append("           , IFNULL(( SELECT IF(COUNT(*) > 1, CONCAT(CONCAT_WS(' > ', E.IC_NAME, D.IC_NAME, C.IC_NAME), ' 외 ',  COUNT(*))  , CONCAT_WS(' > ', E.IC_NAME, D.IC_NAME, C.IC_NAME))  AS PRODUCT                            \n");
	        sb.append("                 FROM MAP_KEYWORD_ISSUE_CODE B                                                                                                                                                                         \n");
	        sb.append("                    , ISSUE_CODE C                                                                                                                                                                                     \n");
	        sb.append("                    , ISSUE_CODE D                                                                                                                                                                                     \n");
	        sb.append("                    , ISSUE_CODE E                                                                                                                                                                                     \n");
	        sb.append("                WHERE B.IC_TYPE = C.IC_TYPE                                                                                                                                                                            \n");
	        sb.append("                  AND B.IC_CODE = C.IC_CODE                                                                                                                                                                            \n");
	        sb.append("                  AND D.IC_TYPE = C.IC_PTYPE                                                                                                                                                                           \n");
	        sb.append("                  AND D.IC_CODE = C.IC_PCODE                                                                                                                                                                           \n");
	        sb.append("                  AND E.IC_TYPE = D.IC_PTYPE                                                                                                                                                                           \n");
	        sb.append("                  AND E.IC_CODE = D.IC_PCODE                                                                                                                                                                           \n");
	        sb.append("                  AND B.K_XP = A.K_XP                                                                                                                                                                                  \n");
	        sb.append("                  AND B.K_YP = A.K_YP                                                                                                                                                                                  \n");
	        sb.append("                  AND B.IC_TYPE = 3                                                                                                                                                                                    \n");
	        sb.append("                GROUP BY B.IC_TYPE),' - ') AS COMPANY                                                                                                                                                                     \n");
	        sb.append("           , IFNULL(( SELECT IF(COUNT(*) > 1, CONCAT(CONCAT_WS(' > ', F.IC_NAME,  E.IC_NAME, D.IC_NAME, C.IC_NAME), ' 외 ',  COUNT(*))  , CONCAT_WS(' > ', F.IC_NAME,  E.IC_NAME, D.IC_NAME, C.IC_NAME))  AS PRODUCT    \n");
	        sb.append("                 FROM MAP_KEYWORD_ISSUE_CODE B                                                                                                                                                                         \n");
	        sb.append("                    , ISSUE_CODE C                                                                                                                                                                                     \n");
	        sb.append("                    , ISSUE_CODE D                                                                                                                                                                                     \n");
	        sb.append("                    , ISSUE_CODE E                                                                                                                                                                                     \n");
	        sb.append("                    , ISSUE_CODE F                                                                                                                                                                                     \n");
	        sb.append("                WHERE B.IC_TYPE = C.IC_TYPE                                                                                                                                                                            \n");
	        sb.append("                  AND B.IC_CODE = C.IC_CODE                                                                                                                                                                            \n");
	        sb.append("                  AND D.IC_TYPE = C.IC_PTYPE                                                                                                                                                                           \n");
	        sb.append("                  AND D.IC_CODE = C.IC_PCODE                                                                                                                                                                           \n");
	        sb.append("                  AND E.IC_TYPE = D.IC_PTYPE                                                                                                                                                                           \n");
	        sb.append("                  AND E.IC_CODE = D.IC_PCODE                                                                                                                                                                           \n");
	        sb.append("                  AND F.IC_TYPE = E.IC_PTYPE                                                                                                                                                                           \n");
	        sb.append("                  AND F.IC_CODE = E.IC_PCODE                                                                                                                                                                           \n");
	        sb.append("                  AND B.K_XP = A.K_XP                                                                                                                                                                                  \n");
	        sb.append("                  AND B.K_YP = A.K_YP                                                                                                                                                                                  \n");
	        sb.append("                  AND B.IC_TYPE = 14                                                                                                                                                                                   \n");
	        sb.append("                GROUP BY B.IC_TYPE),' - ') AS PRODUCT                                                                                                                                                                     \n");
	        sb.append("        FROM MAP_KEYWORD_ISSUE_CODE A                                                                                                                                                                                  \n");
	        sb.append("       GROUP BY A.K_XP, A.K_YP                                                                                                                                                                                         \n");
	        sb.append("     )A                                                                                                                                                                                                                \n");
	        sb.append("WHERE 1=1                                                                                                                                                                                                              \n");
	        //sb.append("  #AND A.K_XP = "+MapxpList+" /*키워드 대그룹*/                                                                                                                                                                                       \n");
	        //sb.append("  #AND CONCAT(A.KEYWORD, COMPANY, PRODUCT) LIKE '%"+SearchKey+"%' /*검색단어 (전체)*/                                                                                                                                                \n");
	        //sb.append("  #AND A.KEYWORD LIKE '%"+SearchKey+"%' 						 /*검색단어 (키워드 그룹)*/                                                                                                                                                                     \n");
	        //sb.append("  #AND A.COMPANY LIKE '%"+SearchKey+"%' 						 /*검색단어 (회사)*/                                                                                                                                                                          \n");
	        //sb.append("  #AND A.PRODUCT LIKE '%"+SearchKey+"%' 						 /*검색단어 (제품군)*/                                                                                                                                                                        \n");

			  if( SearchKey.length() > 0 ) {
			  //{sb.append(" AND A.KEY_1DEPS LIKE '%"+SearchKey+"%' OR A.KEY_2DEPS LIKE '%"+SearchKey+"%' \n");}
			  // 전체 검색
			  if("1".equals(sGroupKind)) {sb.append("  AND CONCAT(A.KEYWORD, COMPANY, PRODUCT) LIKE '%"+SearchKey+"%' /*검색단어 (전체)*/  \n");}
			  // 키워드 그룹 검색
			   else if("2".equals(sGroupKind)) {sb.append("  AND A.KEYWORD LIKE '%"+SearchKey+"%' 						 /*검색단어 (키워드 그룹)*/  \n");}	  
			  // 회사 검색
			   else if("3".equals(sGroupKind)) {sb.append("  AND A.COMPANY LIKE '%"+SearchKey+"%' 						 /*검색단어 (회사)*/  \n");} 
			  // 제품군 검색
			   else if("4".equals(sGroupKind)) {sb.append("  AND A.PRODUCT LIKE '%"+SearchKey+"%' 						 /*검색단어 (제품군)*/  \n");} 
			  	}
			  
			  // 키워드 대그룹 검색
			  if(MapxpList.length() > 0 ) 
			  {sb.append("  AND A.KEYWORD_1DEPS_SEQ = "+MapxpList+" /*키워드 대그룹*/  		 \n");}
			  
	        sb.append("	   ORDER BY A.K_XP,A.K_YP ASC									 \n");
			  
       	 	//if( !Ordered.equals("") )
			//sb.append(" ORDER BY "+Ordered+" 			  \n");
       	 	
       	 	Log.debug(sb.toString());
       	 	
       	 	pstmt = conn.createPStatement( sb.toString() );
       	 	rs = pstmt.executeQuery();
       	 	
       	 	rs.last();
       	 	if(rs.getRow()>0) arrMapkeword= new KeywordBean[rs.getRow()];
       	 	rs.beforeFirst();
            
            while (rs.next()) {
            	Mapkeword = new KeywordBean();
				/*
				 * Mapkeword.setIc_type(rs.getInt("A.IC_TYPE"));
				 * Mapkeword.setIc_code(rs.getInt("A.IC_CODE"));
				 * Mapkeword.setIc_name(rs.getString("B.IC_NAME"));
				 * Mapkeword.setK_xp(rs.getString("A.K_XP"));
				 * Mapkeword.setK_yp(rs.getString("A.K_YP"));
				 * Mapkeword.setK_value(rs.getString("C.K_VALUE"));
				 */      
            	
            	//Mapkeword.setIc_type(rs.getInt("A.IC_TYPE"));
            	//Mapkeword.setCom_type(rs.getInt("COMPANY"));
            	//Mapkeword.setProduct_type(rs.getInt("PRODUCT"));
            	//Mapkeword.setIc_code(rs.getInt("A.IC_CODE"));
            	
            	Mapkeword.setCom_name(rs.getString("COMPANY"));
            	Mapkeword.setProduct_name(rs.getString("PRODUCT"));
            	Mapkeword.setK_xp(rs.getString("K_XP"));
            	Mapkeword.setK_yp(rs.getString("K_YP"));
            	Mapkeword.setK_value(rs.getString("KEYWORD"));
            	Mapkeword.setK_seq(rs.getString("KEYWORD_2DEPS_SEQ"));
            	arrMapkeword[rowCnt] =	Mapkeword;    		
            	rowCnt++;
            }
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return arrMapkeword;
    }
	
	public ArrayList getComMapKeyword(String k_seq, KeywordBean.Type type)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        //PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        ArrayList result = new ArrayList();
        KeywordBean Mapkeword = null;
        
        
        String not  = "";
        
        if(type == KeywordBean.Type.LEFT){
        	not = "NOT";
        }else if(type == KeywordBean.Type.RIGHT){
        	not = "";
        }
        

        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();
       	 	if(!k_seq.contentEquals("")){

       	 		sb = new StringBuffer();
       	 			
       	 		sb.append("SELECT K_XP                       \n");
       	 		sb.append("	  ,K_YP                          \n");
       	 		sb.append("   FROM KEYWORD                   \n");
       	 		sb.append(" WHERE K_SEQ = "+k_seq+"          \n");
           	 	System.out.println(sb.toString());
           	 	rs = stmt.executeQuery(sb.toString());
                String k_xp = "";
                String k_yp = "";
                
           	 	if(rs.next()){
           	 		k_xp = rs.getString("K_XP");
           	 		k_yp = rs.getString("K_YP");
        	 	}

       	 		sb = new StringBuffer();       	 		
		       	sb.append("	  #회사(오른쪽)               	  	     				   \n");
		       	sb.append("  SELECT D.IC_NAME AS COMPANY_1DEPS                     \n");
		       	sb.append("       , C.IC_NAME AS COMPANY_2DEPS                     \n");
		       	sb.append("       , B.IC_NAME AS COMPANY_3DEPS                     \n");
		       	sb.append("       , B.IC_SEQ AS COMPANY_3DEPS_SEQ                  \n");
		       	sb.append("    FROM MAP_KEYWORD_ISSUE_CODE A    				   \n");
		       	sb.append("       , ISSUE_CODE B              				       \n");
		       	sb.append("       , ISSUE_CODE C              			    	   \n");
		       	sb.append("       , ISSUE_CODE D             				       \n");
		       	sb.append("   WHERE A.IC_TYPE = B.IC_TYPE         				   \n");
		       	sb.append("     AND A.IC_CODE = B.IC_CODE     				       \n");
		       	sb.append("     AND B.IC_PTYPE = C.IC_TYPE   				       \n");
		       	sb.append("     AND B.IC_PCODE = C.IC_CODE   				       \n");
		       	sb.append("     AND C.IC_PTYPE = D.IC_TYPE    				       \n");
		       	sb.append("     AND C.IC_PCODE = D.IC_CODE   				       \n");
		       	sb.append("     AND A.K_XP = "+k_xp+"     		                   \n");
		       	sb.append("     AND A.K_YP = "+k_yp+"    		                   \n");
		       	sb.append("     AND B.IC_TYPE = 3								   \n");
		       	sb.append("     AND B.IC_USEYN = 'Y'							   \n");
		       	sb.append("     AND C.IC_USEYN = 'Y'							   \n");
		       	sb.append("     AND D.IC_USEYN = 'Y'							   \n");
		       	sb.append("   ORDER BY COMPANY_3DEPS_SEQ ASC					   \n");
		       		
				System.out.println(sb.toString());
	       	 	rs = stmt.executeQuery(sb.toString());
	       	 		       	 	
	            	
	       	 	while(rs.next()){
	       	 	Mapkeword = new KeywordBean();
	       	 		
	       	 	Mapkeword.setIc_seq(rs.getString("COMPANY_3DEPS_SEQ"));
	       	 	Mapkeword.setCom_name(rs.getString("COMPANY_1DEPS")+" > "+rs.getString("COMPANY_2DEPS")+" > "+rs.getString("COMPANY_3DEPS"));
	       	 	result.add(Mapkeword);
	       	 	}
       	 	}
       	
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public ArrayList getComKeyword(String k_seq, KeywordBean.Type type)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        //PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        ArrayList result = new ArrayList();
        KeywordBean Mapkeword = null;
        
        
        String not  = "";
        
        if(type == KeywordBean.Type.LEFT){
        	not = "NOT";
        }else if(type == KeywordBean.Type.RIGHT){
        	not = "";
        }
        

        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();
       	 	
       	 	if(!k_seq.contentEquals("")){
       	 		sb = new StringBuffer();
       	 			
       	 		sb.append("SELECT K_XP                       \n");
       	 		sb.append("	  ,K_YP                          \n");
       	 		sb.append("   FROM KEYWORD                   \n");
       	 		sb.append(" WHERE K_SEQ = "+k_seq+"          \n");
           	 	System.out.println(sb.toString());
           	 	rs = stmt.executeQuery(sb.toString());
                String k_xp = "";
                String k_yp = "";
                
           	 	if(rs.next()){
           	 		k_xp = rs.getString("K_XP");
           	 		k_yp = rs.getString("K_YP");
        	 	}

       	 		sb = new StringBuffer();       	 		
		       	sb.append("	  #회사(왼쪽)               	  	     				  														 \n");
			    sb.append("  	SELECT A.IC_SEQ AS COMPANY_3DEPS_SEQ                                                                     \n");
			    sb.append("   ,A.IC_TYPE AS COMPANY_TYPE 							                                                     \n");
			   	sb.append("  ,A.IC_CODE AS COMPANY_CODE				                                                            	     \n");
			    sb.append("   ,C.IC_NAME AS COMPANY_1DEPS                                                                                \n");
			    sb.append("   ,B.IC_NAME AS COMPANY_2DEPS                                                                                \n");
			    sb.append("   ,A.IC_NAME AS COMPANY_3DEPS                                                                                \n");
			    sb.append(" FROM                                                                                                         \n");
			    sb.append("    ISSUE_CODE A			                                                                                     \n");
			    sb.append("    INNER JOIN ISSUE_CODE B ON A.IC_PTYPE=B.IC_TYPE AND A.IC_PCODE=B.IC_CODE 			                     \n");       						                       
			    sb.append("    INNER JOIN ISSUE_CODE C  ON B.IC_PTYPE=C.IC_TYPE AND B.IC_PCODE=C.IC_CODE   		                  	     \n");	    
			    sb.append("WHERE 1=1   																									 \n");
		    	sb.append(" AND  A.IC_CODE NOT IN (SELECT IC_CODE FROM MAP_KEYWORD_ISSUE_CODE WHERE K_XP= "+k_xp+" AND K_YP= "+k_yp+")   \n");	 
			    sb.append(" AND A.IC_TYPE = 3                                                                                            \n");
			    sb.append(" AND A.IC_CODE !=0                                                                                            \n");
			    sb.append(" AND A.IC_USEYN='Y'                                                                 			                 \n");                                                                     
			    sb.append("ORDER BY A.IC_CODE ASC;                                                                                       \n");
		       	
				System.out.println(sb.toString());
	       	 	rs = stmt.executeQuery(sb.toString());
	       	 		       	 	
	            	
	       	 	while(rs.next()){
	       	 	Mapkeword = new KeywordBean();
	       	 		
	       	 	Mapkeword.setIc_seq(rs.getString("COMPANY_3DEPS_SEQ"));
	       	 	Mapkeword.setCom_name(rs.getString("COMPANY_1DEPS")+" > "+rs.getString("COMPANY_2DEPS")+" > "+rs.getString("COMPANY_3DEPS"));
	       	 	result.add(Mapkeword);
	       	 	}}
       	 	
       	
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public ArrayList getProMapKeyword(String k_seq, KeywordBean.Type type)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        //PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        ArrayList result = new ArrayList();
        KeywordBean Mapkeword = null;
        
        String not  = "";
        
        if(type == KeywordBean.Type.LEFT){
        	not = "NOT";
        }else if(type == KeywordBean.Type.RIGHT){
        	not = "";
        }
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();

       	 	if(!k_seq.contentEquals("")){
   	 		sb = new StringBuffer();
	 			
   	 		sb.append("SELECT K_XP                       \n");
   	 		sb.append("	  ,K_YP                          \n");
   	 		sb.append("   FROM KEYWORD                   \n");
   	 		sb.append(" WHERE K_SEQ = "+k_seq+"          \n");
       	 	System.out.println(sb.toString());
       	 	rs = stmt.executeQuery(sb.toString());
            String k_xp = "";
            String k_yp = "";
            
       	 	if(rs.next()){
       	 		k_xp = rs.getString("K_XP");
       	 		k_yp = rs.getString("K_YP");
    	 	}		  
       	 		
       	 	   sb = new StringBuffer();
   	 		sb = new StringBuffer();       	 		
	       	sb.append("	  #제품군(오른쪽)               	  	     			   \n");
	       	sb.append("  SELECT E.IC_NAME AS PRODUCT_1DEPS                     \n");
	       	sb.append("       , D.IC_NAME AS PRODUCT_2DEPS                     \n");
	       	sb.append("       , C.IC_NAME AS PRODUCT_3DEPS                     \n");
	       	sb.append("       , B.IC_NAME AS PRODUCT_4DEPS                     \n");
	       	sb.append("       , B.IC_SEQ AS PRODUCT_4DEPS_SEQ                  \n");
	       	sb.append("    FROM MAP_KEYWORD_ISSUE_CODE A    				   \n");
	       	sb.append("       , ISSUE_CODE B              				       \n");
	       	sb.append("       , ISSUE_CODE C              			    	   \n");
	       	sb.append("       , ISSUE_CODE D             				       \n");
	       	sb.append("       , ISSUE_CODE E             				       \n");
	       	sb.append("   WHERE A.IC_TYPE = B.IC_TYPE         				   \n");
	       	sb.append("     AND A.IC_CODE = B.IC_CODE     				       \n");
	       	sb.append("     AND B.IC_PTYPE = C.IC_TYPE   				       \n");
	       	sb.append("     AND B.IC_PCODE = C.IC_CODE   				       \n");
	       	sb.append("     AND C.IC_PTYPE = D.IC_TYPE    				       \n");
	       	sb.append("     AND C.IC_PCODE = D.IC_CODE   				       \n");
	       	sb.append("     AND D.IC_PTYPE = E.IC_TYPE    				       \n");
	       	sb.append("     AND D.IC_PCODE = E.IC_CODE   				       \n");
	       	sb.append("     AND A.K_XP = "+k_xp+"     		                   \n");
	       	sb.append("     AND A.K_YP = "+k_yp+"    		                   \n");
	       	sb.append("     AND B.IC_TYPE = 14								   \n");
	       	sb.append("     AND B.IC_USEYN = 'Y'							   \n");
	       	sb.append("     AND C.IC_USEYN = 'Y'							   \n");
	       	sb.append("     AND D.IC_USEYN = 'Y'							   \n");
	       	sb.append("   ORDER BY PRODUCT_4DEPS_SEQ ASC					   \n");	
				
				System.out.println(sb.toString());
	       	 	rs = stmt.executeQuery(sb.toString());
	       	 	
	       	 	while(rs.next()){
	       	 	Mapkeword = new KeywordBean();
	       	 		
	       	 	Mapkeword.setIc_seq(rs.getString("PRODUCT_4DEPS_SEQ"));
	       	 	Mapkeword.setProduct_name(rs.getString("PRODUCT_1DEPS")+" > "+rs.getString("PRODUCT_2DEPS")+" > "+rs.getString("PRODUCT_3DEPS")+" > "+rs.getString("PRODUCT_4DEPS"));
	       	 	result.add(Mapkeword);
	       	 	}
       	 	}
       	
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public ArrayList getProKeyword(String k_seq, KeywordBean.Type type)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        //PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        ArrayList result = new ArrayList();
        KeywordBean Mapkeword = null;
        
        
        String not  = "";
        
        if(type == KeywordBean.Type.LEFT){
        	not = "NOT";
        }else if(type == KeywordBean.Type.RIGHT){
        	not = "";
        }
        

        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();
       	 	
       	 	if(!k_seq.contentEquals("")){
       	 		sb = new StringBuffer();
       	 			
       	 		sb.append("SELECT K_XP                       \n");
       	 		sb.append("	  ,K_YP                          \n");
       	 		sb.append("   FROM KEYWORD                   \n");
       	 		sb.append(" WHERE K_SEQ = "+k_seq+"          \n");
           	 	System.out.println(sb.toString());
           	 	rs = stmt.executeQuery(sb.toString());
                String k_xp = "";
                String k_yp = "";
                
           	 	if(rs.next()){
           	 		k_xp = rs.getString("K_XP");
           	 		k_yp = rs.getString("K_YP");
        	 	}

       	 		sb = new StringBuffer();       	 		
		       	sb.append("	  #제품군(왼쪽)               	  	     				  														 \n");
			    sb.append("  SELECT A.IC_SEQ AS PRODUCT_4DEPS_SEQ                                                           	         \n");
			    sb.append("   ,A.IC_TYPE AS PRODUCT_TYPE 							                                                     \n");
			   	sb.append("   ,A.IC_CODE AS PRODUCT_CODE				                                                            	 \n");
			    sb.append("   ,D.IC_NAME AS PRODUCT_1DEPS                                                                                \n");
			    sb.append("   ,C.IC_NAME AS PRODUCT_2DEPS                                                                                \n");
			    sb.append("   ,B.IC_NAME AS PRODUCT_3DEPS                                                                                \n");
			    sb.append("   ,A.IC_NAME AS PRODUCT_4DEPS                                                                                \n");
			    sb.append(" FROM                                                                                                         \n");
			    sb.append("    ISSUE_CODE A			                                                                                     \n");
			    sb.append("    INNER JOIN ISSUE_CODE B ON A.IC_PTYPE=B.IC_TYPE AND A.IC_PCODE=B.IC_CODE 			                     \n");       						                       
			    sb.append("    INNER JOIN ISSUE_CODE C  ON B.IC_PTYPE=C.IC_TYPE AND B.IC_PCODE=C.IC_CODE   		                  	     \n");			                                                                      
			    sb.append("    INNER JOIN ISSUE_CODE D  ON C.IC_PTYPE=D.IC_TYPE AND C.IC_PCODE=D.IC_CODE   		                  	     \n");			                                                                      
			    sb.append("WHERE A.IC_CODE NOT IN (SELECT IC_CODE FROM MAP_KEYWORD_ISSUE_CODE WHERE K_XP= "+k_xp+" AND K_YP= "+k_yp+")   \n");		                          
			    sb.append(" AND A.IC_TYPE = 14                                                                                           \n");
			    sb.append(" AND A.IC_CODE !=0                                                                                            \n");
			    sb.append(" AND A.IC_USEYN='Y'                                                                 			                 \n");                                                                     
			    sb.append("ORDER BY A.IC_CODE ASC;                                                                                       \n");
		       	
				System.out.println(sb.toString());
	       	 	rs = stmt.executeQuery(sb.toString());
	       	 		       	 	
	            	
	       	 	while(rs.next()){
	       	 	Mapkeword = new KeywordBean();
	       	 		
	       	 	Mapkeword.setIc_seq(rs.getString("PRODUCT_4DEPS_SEQ"));
	       	 	Mapkeword.setProduct_name(rs.getString("PRODUCT_1DEPS")+" > "+rs.getString("PRODUCT_2DEPS")+" > "+rs.getString("PRODUCT_3DEPS")+" > "+rs.getString("PRODUCT_4DEPS"));
	       	 	result.add(Mapkeword);
	       	 	}
       	 	}
       	
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	
	public ArrayList InsertMapkeyword(String k_seq , String targetSeq)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        //PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        KeywordBean Mapkeyword = null;
        
        ArrayList result = new ArrayList();
        ArrayList<String> newData = new ArrayList<String>();        
        
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();
       	 	
       	 	sb = new StringBuffer();
       	 	sb.append("SELECT IC_TYPE, IC_CODE FROM ISSUE_CODE WHERE IC_SEQ = "+targetSeq+"  	\n");
			System.out.println(sb.toString());
       	 	rs = stmt.executeQuery(sb.toString());
       	 	
            String itype = "";
            String icode = "";
            
       	 	if(rs.next()){
       	 		itype = rs.getString("IC_TYPE");
       	 		icode = rs.getString("IC_CODE");
    	 	}
       	 	
       	 	sb = new StringBuffer();
       	 	sb.append("SELECT K_XP, K_YP FROM KEYWORD WHERE K_SEQ = "+k_seq+"  	\n");
			System.out.println(sb.toString());
       	 	rs = stmt.executeQuery(sb.toString());
       	 	
            String k_xp = "";
            String k_yp = "";
            
       	 	if(rs.next()){
       	 		k_xp = rs.getString("K_XP");
       	 		k_yp = rs.getString("K_YP");
    	 	}
      	 	
       	 	sb = new StringBuffer();
	       	sb.append("INSERT INTO MAP_KEYWORD_ISSUE_CODE (K_XP, K_YP, IC_TYPE, IC_CODE)    									   \n");
	        sb.append("VALUES ("+k_xp+","+k_yp+","+itype+","+icode+")                                                              \n");

    	 	System.out.println(sb.toString());
       	 	stmt.executeUpdate(sb.toString());
       	
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return result;
    }

	public ArrayList DeleteMapkeyword(String k_seq , String targetSeq)
	{
		DBconn  conn  = null;
        Statement stmt = null;
        //PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = null;
        ArrayList arrList = new ArrayList();
        KeywordBean Mapkeyword = null;
        
        ArrayList result = new ArrayList();
        ArrayList<String> newData = new ArrayList<String>();        
        
        
        try {
        	conn  = new DBconn();
       	 	conn.getDBCPConnection();
       	 	stmt = conn.createStatement();
       	 	
       	 	sb = new StringBuffer();
       	 	sb.append("SELECT IC_TYPE, IC_CODE FROM ISSUE_CODE WHERE IC_SEQ = "+targetSeq+"  	\n");
			System.out.println(sb.toString());
       	 	rs = stmt.executeQuery(sb.toString());
       	 	
            String itype = "";
            String icode = "";
            
       	 	if(rs.next()){
       	 		itype = rs.getString("IC_TYPE");
       	 		icode = rs.getString("IC_CODE");
    	 	}
       	 	
       	 	sb = new StringBuffer();
       	 	sb.append("SELECT K_XP, K_YP FROM KEYWORD WHERE K_SEQ = "+k_seq+"  	\n");
			System.out.println(sb.toString());
       	 	rs = stmt.executeQuery(sb.toString());
       	 	
            String k_xp = "";
            String k_yp = "";
            
       	 	if(rs.next()){
       	 		k_xp = rs.getString("K_XP");
       	 		k_yp = rs.getString("K_YP");
    	 	}
      	 	
       	 	sb = new StringBuffer();
	       	sb.append("DELETE FROM MAP_KEYWORD_ISSUE_CODE    									 		  \n");
	        sb.append("WHERE K_XP="+k_xp+" AND K_YP="+k_yp+" AND IC_TYPE="+itype+" AND IC_CODE="+icode+"  \n");

    	 	System.out.println(sb.toString());
       	 	stmt.executeUpdate(sb.toString());
       	
        } catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
        }
        return result;
    }
	
	public ArrayList<HashMap<String, String>> getCntOfIncrease(String today, String yesterday, String current_hour, int row_limit){
		ArrayList<HashMap<String, String>>  result = new ArrayList<HashMap<String, String>>();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT A.K_VALUE                                                                                                               \n");
			sb.append("   , IFNULL(B.MAK_CNT,0) AS P_CNT                                                                                              \n");
			sb.append("   , IFNULL(A.MAK_CNT,0) AS C_CNT                                                                                              \n");
			sb.append("   , IF((IFNULL(A.MAK_CNT,0)-IFNULL(B.MAK_CNT,0)) = 0, 0, IF((IFNULL(A.MAK_CNT,0)-IFNULL(B.MAK_CNT,0)) > 0, 1, -1)) AS POINTER \n");  
			sb.append("   , (IFNULL(A.MAK_CNT,0)-IFNULL(B.MAK_CNT,0)) AS GAP                       													\n");
			sb.append("   , A.K_XP											                                                                          \n");
			sb.append("   , A.K_YP                                                                                                                    \n");
			sb.append("   , A.K_ZP                                                                                                                    \n");
			sb.append("FROM (                                                                                                                         \n");
			sb.append("    SELECT MAK_DATE, MAK_HOUR, K_XP, K_YP, K_ZP, K_VALUE, MAK_CNT                                                              \n");
			sb.append("       FROM META_ANA_KEYWORD_HOUR                                                                                              \n");
			sb.append("      WHERE MAK_DATE = '"+today+"'                                                                                              \n");
			sb.append("         AND MAK_HOUR = '"+current_hour+"'                                                                                                   \n");
			sb.append(")A LEFT OUTER JOIN  (                                                                                                          \n");
			sb.append("  SELECT MAK_DATE, MAK_HOUR, K_XP, K_YP, K_ZP, K_VALUE, MAK_CNT                                                                \n");
			sb.append("       FROM META_ANA_KEYWORD_HOUR                                                                                              \n");
			sb.append("      WHERE MAK_DATE = '"+yesterday+"'                                                                                              \n");
			sb.append("         AND MAK_HOUR = '"+current_hour+"'                                                                                                   \n");
			sb.append(")B ON A.K_XP = B.K_XP AND A.K_YP = B.K_YP AND A.K_ZP = B.K_ZP AND A.K_VALUE = B.K_VALUE                                        \n");
			sb.append("ORDER  BY GAP DESC                                                                                                             \n");
			sb.append("LIMIT "+row_limit+"                                                                                                                       \n");
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			HashMap<String, String> item = new HashMap<String, String>();
			while(rs.next()){
				item = new HashMap<String, String>();
				item.put("K_VALUE", rs.getString("K_VALUE"));
				item.put("P_CNT", su.digitFormat(rs.getString("P_CNT")));
				item.put("C_CNT", su.digitFormat(rs.getString("C_CNT")));
				item.put("POINTER", rs.getString("POINTER"));
				item.put("GAP", su.digitFormat(rs.getString("GAP")));
				item.put("K_XP", rs.getString("K_XP"));
				item.put("K_YP", rs.getString("K_YP"));
				item.put("K_ZP", rs.getString("K_ZP"));
				result.add(item);
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
	}
	

	
	public ArrayList<HashMap<String, String>> getCntOfDecrease(String today, String yesterday, String current_hour, int row_limit){
		ArrayList<HashMap<String, String>>  result = new ArrayList<HashMap<String, String>>();
		
		DBconn dbconn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = null;
		try{
			dbconn = new DBconn();
			dbconn.getDBCPConnection();
			
			sb = new StringBuffer();
			sb.append("SELECT A.K_VALUE                                                                                                               \n");
			sb.append("   , IFNULL(B.MAK_CNT,0) AS P_CNT                                                                                              \n");
			sb.append("   , IFNULL(A.MAK_CNT,0) AS C_CNT                                                                                              \n");
			sb.append("   , IF((IFNULL(A.MAK_CNT,0)-IFNULL(B.MAK_CNT,0)) = 0, 0, IF((IFNULL(A.MAK_CNT,0)-IFNULL(B.MAK_CNT,0)) > 0, 1, -1)) AS POINTER \n");
			sb.append("   , ABS(IFNULL(A.MAK_CNT,0)-IFNULL(B.MAK_CNT,0)) AS GAP                                                                          \n");
			sb.append("   , A.K_XP											                                                                          \n");
			sb.append("   , A.K_YP                                                                          \n");
			sb.append("   , A.K_ZP                                                                          \n");
			sb.append("FROM (                                                                                                                         \n");
			sb.append("    SELECT MAK_DATE, MAK_HOUR, K_XP, K_YP, K_ZP, K_VALUE, MAK_CNT                                                              \n");
			sb.append("       FROM META_ANA_KEYWORD_HOUR                                                                                              \n");
			sb.append("      WHERE MAK_DATE = '"+today+"'                                                                                              \n");
			sb.append("         AND MAK_HOUR = '"+current_hour+"'                                                                                                   \n");
			sb.append("       ORDER BY MAK_CNT DESC                                                                                                   \n");
			sb.append("        LIMIT "+row_limit+"                                                                                                               \n");
			sb.append(")A LEFT OUTER JOIN                                                                                                             \n");
			sb.append("    META_ANA_KEYWORD_HOUR B  ON A.K_XP = B.K_XP AND A.K_YP = B.K_YP AND A.K_ZP = B.K_ZP AND A.K_VALUE = B.K_VALUE              \n");
			sb.append("      AND A.MAK_HOUR = B.MAK_HOUR                                                                                              \n");
			sb.append("      AND B.MAK_DATE ='"+yesterday+"'                                                                                               \n");
			System.out.println(sb.toString());
			
			pstmt = dbconn.createPStatement(sb.toString());
			rs = pstmt.executeQuery();
			HashMap<String, String> item = new HashMap<String, String>();
			while(rs.next()){
				item = new HashMap<String, String>();
				item.put("K_VALUE", rs.getString("K_VALUE"));
				item.put("P_CNT", su.digitFormat(rs.getString("P_CNT")));
				item.put("C_CNT", su.digitFormat(rs.getString("C_CNT")));
				item.put("POINTER", rs.getString("POINTER"));
				item.put("GAP", su.digitFormat(rs.getString("GAP")));
				item.put("K_XP", rs.getString("K_XP"));
				item.put("K_YP", rs.getString("K_YP"));
				item.put("K_ZP", rs.getString("K_ZP"));
				result.add(item);
			}
			
		}catch(SQLException e){
			Log.writeExpt(e, sb.toString());
		}catch(Exception e){
			Log.writeExpt(e);
		}finally{
			sb = null;
			try{if(dbconn != null)dbconn.close();}catch(Exception e){}
			try{if(rs != null)rs.close();}catch(Exception e){}
			try{if(pstmt != null)pstmt.close();}catch(Exception e){}
		}
		return result;
	}
	
	
	
	public void updateNearLen( int xp, int yp, int zp, String nearLen ) throws SQLException, ClassNotFoundException, Exception {
		
		try {
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();
			if(xp > 0 && yp > 0 && zp > 0){
				//일반저장
				sb.append("UPDATE "+tbl_name+" SET K_NEAR_LEN = "+nearLen+" WHERE K_XP = "+xp+" AND K_YP = "+yp+" AND K_ZP = "+zp+" \n");
				stmt.executeUpdate(sb.toString());
			}else if(xp == 0 && yp == 0 && zp == 0){
				//전체저장
				sb.append("UPDATE "+tbl_name+" SET K_NEAR_LEN = "+nearLen+"\n");
				stmt.executeUpdate(sb.toString());
			}
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
        } catch(Exception ex) {
			Log.writeExpt(ex);
        } finally {
        	if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}            
        }
	}
}
