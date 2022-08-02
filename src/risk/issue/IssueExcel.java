package risk.issue;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import risk.DBconn.DBconn;
import risk.util.DateUtil;
import risk.util.Log;
import risk.util.StringUtil;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class IssueExcel{
	
	private DBconn dbconn   = null;
	private Statement stmt  = null;
	private ResultSet rs    = null;
	private PreparedStatement pstmt = null;
	private StringBuffer sb = null;	
	private StringUtil su = new StringUtil();
	private DateUtil du = new DateUtil();
	
    public ArrayList getExcelData_B( String sDate
								     , String eDate
								     , String sTime
								     , String eTime
								     , String[][] col_typeName
								     , String searchKey
								     , String keyType
								     , String relationKey
								     , String register   
								     , String check_no
								     , String[][] typeCode
								     , String[][] typeCode_Etc
								     , String ra_same
    		                     ) {
    	ArrayList result = null;
    	DBconn dbconn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	StringBuffer sb = new StringBuffer();
    	try {
    		
    		//ArrayList arTypeCode = ReassembleTypeCode(typeCode);
    		//ArrayList arTypeCode_sum = ReassembleTypeCode_sum(col_typeName, arTypeCode);
    		String[] typeBean = null; 
    		
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		sb = new StringBuffer();
    		
    		sb.append("	SELECT 																				\n");
    		if(col_typeName != null && col_typeName.length > 0){
    			for(int i=0; i < col_typeName.length; i++){
    						
    					if(col_typeName[i][0].equals("D0")){
    						sb.append("      A.ID_SEQ AS `번호`													\n");
    					} else if(col_typeName[i][0].equals("D1")){
    						sb.append("      ,MD_DATE AS `일자`												\n");
    						
    					} else if(col_typeName[i][0].equals("D2")){
    						sb.append("      ,MD_SITE_NAME AS `사이트`											\n");
    						
    					} else if(col_typeName[i][0].equals("D3")){
    						sb.append("      ,ID_TITLE AS `제목`												\n");
    						
    					} else if(col_typeName[i][0].equals("D4")){
    						sb.append("      ,ID_URL AS `URL`												\n");
    						
    					} else if(col_typeName[i][0].equals("D5")){
    						sb.append("      ,REPLACE(REPLACE(REPLACE(ID_CONTENT,'\\t',''),'\\r',''),'\\n','') AS `본문`											\n");
    						
    					}/* else if(col_typeName[i][0].equals("D7")){
    						sb.append("      ,DATE_FORMAT(A.MD_DATE,'%y.%m') AS `일자(yy.mm)`				\n");
    						
    					} else if(col_typeName[i][0].equals("D8")){
    						sb.append("      ,DATE_FORMAT(A.MD_DATE,'%Y') AS `일자(yyyy)`					\n");
    					}*/
    					
    			}
    		}
    		if(col_typeName != null && col_typeName.length > 0){
    			for(int i=0; i < col_typeName.length; i++){
    				String[] col_code = col_typeName[i][0].split("_");
    				if(col_code.length > 1){
    					if(col_code[0].equals("T")){
    						sb.append(" , IF(C.IC_TYPE = "+col_code[1]+",C.IC_NAME,'') AS '"+col_typeName[i][1]+"' 		\n");
    					}else if(col_code[0].equals("S")){
    						sb.append(" , FN_GET_ISSUE_CODE_NAME_DETAIL(A.ID_SEQ, "+col_code[1]+", "+col_code[2]+") AS '"+col_typeName[i][1]+"' 	\n");
    					}else if(col_code[0].equals("R")){
    						sb.append(" , FN_GET_RELATION_KEYWORD_NAME(A.ID_SEQ, "+col_code[1]+") AS '"+col_typeName[i][1]+"' 						\n");
    					}else if(col_code[0].equals("E")){
    						sb.append(" , FN_GET_ISSUE_CODE_NAME(A.ID_SEQ, "+col_code[1]+") AS '"+col_typeName[i][1]+"' 							\n");
    					}else if("NEW".equals(col_code[0])){
    						String fn_name = "";
    						if("0".equals(col_code[1])) fn_name = ", IF(C.IC_TYPE IN (8, 10, 11, 12, 13),C.IC_NAME,'')	";
    						else if("9".equals(col_code[1])) fn_name = ", FN_GET_ISSUE_CODE_NAME_DETAIL_V2(A.ID_SEQ, "+col_code[1]+")";
    						else if("20".equals(col_code[1])) fn_name = ", FN_GET_ISSUE_CODE_NAME_DETAIL_V2(A.ID_SEQ, "+col_code[1]+")";
    						else if("INFO".equals(col_code[1])) fn_name = ", FN_GET_ISSUE_CODE_NAME_DETAIL_V3(A.ID_SEQ)";
    						else if("REL".equals(col_code[1])) fn_name = ", FN_GET_RELATION_KEYWORD_NAME_V2(A.ID_SEQ)";
    						sb.append(fn_name+" AS '"+col_typeName[i][1]+"' 									\n");
    					}
    				}
    				 else if(col_typeName[i][0].equals("D6")){
 						sb.append("      ,M_NAME AS `등록자`												\n");
 						
 					}
    			}
    		}
    		sb.append(" FROM (																				\n");
    		sb.append("		SELECT																			\n");
    		sb.append("			A.ID_SEQ																	\n");
    		sb.append("			,A.MD_PSEQ																	\n");
    		sb.append("			,DATE_FORMAT(A.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE						\n");
    		sb.append("			,A.MD_SITE_NAME																\n");
    		sb.append("			,A.ID_TITLE																	\n");
    		sb.append("			,A.ID_URL																	\n");
    		sb.append("			,A.ID_CONTENT																\n");
    		sb.append("			,B.M_NAME																	\n");
    		sb.append("  FROM ISSUE_DATA A																	\n");
    		sb.append("  	INNER JOIN MEMBER B ON A.M_SEQ = B.M_SEQ										\n");
    		if(!"".equals(register)){
    			sb.append("  		AND B.M_SEQ = "+ register +"											\n");
    		}
    		
    		if(!"".equals(relationKey)){
    			sb.append("  	INNER JOIN RELATION_KEYWORD_MAP C ON A.ID_SEQ = C.ID_SEQ					\n");	
    			sb.append("  		AND C.RK_SEQ = "+ relationKey +"										\n");
    		}
    		
    		if(typeCode!=null && typeCode.length > 0){
    			for(int i=0; i < typeCode.length; i++){
    				
    				if(typeCode[i].length > 0 && !"".equals(typeCode[i][0])){
    					
    					sb.append("	INNER JOIN ISSUE_DATA_CODE D"+(i+1)+" ON A.ID_SEQ = D"+(i+1)+".ID_SEQ 						\n");
    					sb.append("	AND D"+(i+1)+".IC_TYPE = "+typeCode[i][0]+" AND D"+(i+1)+".IC_CODE = "+typeCode[i][1]+" 	\n");
    					
    				}
    			}
    		}
    		if(typeCode_Etc!=null && typeCode_Etc.length > 0){
    			for(int i=0; i < typeCode_Etc.length; i++){
    				
    				if(typeCode_Etc[i].length > 0  && !"".equals(typeCode_Etc[i][0])){
    					
    					sb.append("	INNER JOIN ISSUE_DATA_CODE_DETAIL E"+(i+1)+" ON A.ID_SEQ = E"+(i+1)+".ID_SEQ 						\n");
    					sb.append("	AND E"+(i+1)+".IC_TYPE = "+typeCode_Etc[i][0]+" AND E"+(i+1)+".IC_CODE = "+typeCode_Etc[i][1]+" 	\n");
    					
    				}
    			}
    		}
    		sb.append(" WHERE MD_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"' 		\n");
    		//키워드 조건 주기(AND검색)
			if(!"".equals(searchKey)){
				String[] arKey = searchKey.split(" ");
				
				for(int i =0; i < arKey.length; i++){
					if(!arKey[i].trim().equals("")){
						if(keyType.equals("1")){
							//제목검색
							sb.append("   AND A.ID_TITLE LIKE '%"+arKey[i]+"%' \n");
							
						}else if(keyType.equals("2")){
							//제목+내용검색
							sb.append("   AND CONCAT(A.ID_TITLE,A.ID_CONTENT) LIKE '%"+arKey[i]+"%'		\n");
						}			
					}
				}
			}
			if(!"".equals(check_no)){
				sb.append(" AND A.ID_SEQ IN ("+check_no+") 												\n");
			}
			sb.append("		) A																			\n");
    		String detail_code = "";
			if(col_typeName != null && col_typeName.length > 0){
				for(int i=0; i < col_typeName.length; i++){
					String[] col_code = col_typeName[i][0].split("_");
					if(col_code.length > 1){
						if(col_code[0].equals("T")){
							if(detail_code.equals("")){
								detail_code = col_code[1];
							}else{
								detail_code += ","+col_code[1];
							}
						}else if(col_code[0].equals("NEW")){
							detail_code = "8,10,11,12,13";
							break;
						}
					}
    			}
    		}
			
			sb.append("	INNER JOIN ISSUE_DATA_CODE B ON A.ID_SEQ = B.ID_SEQ 									\n");
			sb.append("	INNER JOIN ISSUE_CODE C																	\n");
			sb.append("	ON B.IC_TYPE = C.IC_TYPE AND B.IC_CODE = C.IC_CODE AND C.IC_TYPE IN ("+detail_code+") 	\n");
			if(ra_same.equals("2")){
				sb.append("		GROUP BY A.MD_PSEQ														\n");
			}
			sb.append("		ORDER BY A.MD_DATE, A.MD_PSEQ												\n");	
			System.out.println(sb.toString());
    		stmt = dbconn.createStatement();
    		rs = stmt.executeQuery(sb.toString());
    		result = new ArrayList();
    		int colCnt = rs.getMetaData().getColumnCount();
    		String[] column = null;
    		
    		
    		while ( rs.next() ) {
    			column = new String[colCnt];
    			for(int i = 0; i < colCnt; i++){
    				column[i] = rs.getString(i+1);
    			}
    			result.add(column);
    		}
    		
    	} catch (SQLException ex) {
    		ex.printStackTrace();
			Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex) {
        	ex.printStackTrace();
        	Log.writeExpt(ex);
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn  != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    	return result; 
    }
    
    public ArrayList getExcelData_A( String sDate
								     , String eDate
								     , String sTime
								     , String eTime
								     , String[][] col_typeName
								     , String searchKey
								     , String keyType
								     , String relationKey
								     , String register   
								     , String check_no
								     , String[][] typeCode
								     , String[][] typeCode_Etc
								     , String ra_same
    							) {
    	ArrayList result = null;
    	DBconn dbconn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	StringBuffer sb = new StringBuffer();
    	try {
    		
    		//ArrayList arTypeCode = ReassembleTypeCode(typeCode);
    		//ArrayList arTypeCode_sum = ReassembleTypeCode_sum(col_typeName, arTypeCode);
    		String[] typeBean = null; 
    		
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		sb = new StringBuffer();
    		
    		sb.append("	SELECT 																				\n");
    		if(col_typeName != null && col_typeName.length > 0){
    			for(int i=0; i < col_typeName.length; i++){
    						
    					if(col_typeName[i][0].equals("D0")){
    						sb.append("      ID_SEQ AS `번호`													\n");
    					} else if(col_typeName[i][0].equals("D1")){
    						sb.append("      ,MD_DATE AS `일자`												\n");
    						
    					} else if(col_typeName[i][0].equals("D2")){
    						sb.append("      ,MD_SITE_NAME AS `사이트`											\n");
    						
    					} else if(col_typeName[i][0].equals("D3")){
    						sb.append("      ,ID_TITLE AS `제목`												\n");
    						
    					} else if(col_typeName[i][0].equals("D4")){
    						sb.append("      ,ID_URL AS `URL`												\n");
    						
    					} else if(col_typeName[i][0].equals("D5")){
    						//sb.append("      ,REPLACE(REPLACE(REPLACE(ID_CONTENT,'\\t',''),'\\r',''),'\\n','') AS `본문`											\n");
    						sb.append("      ,ID_CONTENT AS `본문`											\n");
    					}/* else if(col_typeName[i][0].equals("D7")){
    						sb.append("      ,DATE_FORMAT(A.MD_DATE,'%y.%m') AS `일자(yy.mm)`				\n");
    						
    					} else if(col_typeName[i][0].equals("D8")){
    						sb.append("      ,DATE_FORMAT(A.MD_DATE,'%Y') AS `일자(yyyy)`					\n");
    					}*/
    					
    			}
    		}
    		
    		if(col_typeName != null && col_typeName.length > 0){
    			for(int i=0; i < col_typeName.length; i++){
    				String[] col_code = col_typeName[i][0].split("_");
    				if(col_code.length > 1){
    					if(col_code[0].equals("T")){
    						sb.append(" , FN_GET_ISSUE_CODE_NAME_CONCAT(ID_SEQ, "+col_code[1]+") AS '"+col_typeName[i][1]+"' 						\n");
    					}else if(col_code[0].equals("S")){
    						sb.append(" , FN_GET_ISSUE_CODE_NAME_DETAIL(ID_SEQ, "+col_code[1]+", "+col_code[2]+") AS '"+col_typeName[i][1]+"' 	\n");
    					}else if(col_code[0].equals("R")){
    						sb.append(" , FN_GET_RELATION_KEYWORD_NAME(ID_SEQ, "+col_code[1]+") AS '"+col_typeName[i][1]+"' 						\n");
    					}else if(col_code[0].equals("E")){
    						sb.append(" , FN_GET_ISSUE_CODE_NAME(ID_SEQ, "+col_code[1]+") AS '"+col_typeName[i][1]+"' 							\n");
    					}else if("NEW".equals(col_code[0])){
    						String fn_name = "";
    						if("0".equals(col_code[1])) fn_name = ", FN_GET_ISSUE_CODE_NAME_CONCAT_V2(ID_SEQ)	";
    						else if("9".equals(col_code[1])) fn_name = ", FN_GET_ISSUE_CODE_NAME_DETAIL_V2(ID_SEQ, "+col_code[1]+")";
    						else if("20".equals(col_code[1])) fn_name = ", FN_GET_ISSUE_CODE_NAME_DETAIL_V2(ID_SEQ, "+col_code[1]+")";
    						else if("INFO".equals(col_code[1])) fn_name = ", FN_GET_ISSUE_CODE_NAME_DETAIL_V3(ID_SEQ)";
    						else if("REL".equals(col_code[1])) fn_name = ", FN_GET_RELATION_KEYWORD_NAME_V2(ID_SEQ)";
    						sb.append(fn_name+" AS '"+col_typeName[i][1]+"' 									\n");
    					}
    				}else if(col_typeName[i][0].equals("D6")){
 						sb.append("      ,M_NAME AS `등록자`												\n");
 					}
    			}
    		}
    		sb.append(" FROM (																				\n");
    		sb.append("		SELECT																			\n");
    		sb.append("			A.ID_SEQ																	\n");
    		sb.append("			,A.MD_PSEQ																	\n");
    		sb.append("			,DATE_FORMAT(A.MD_DATE,'%Y-%m-%d %H:%i:%s') AS MD_DATE								\n");
    		sb.append("			,A.MD_SITE_NAME																\n");
    		sb.append("			,A.ID_TITLE																	\n");
    		sb.append("			,A.ID_URL																	\n");
    		sb.append("			,A.ID_CONTENT																\n");
    		sb.append("			,B.M_NAME																	\n");
    		sb.append("  FROM ISSUE_DATA A																	\n");
    		sb.append("  	INNER JOIN MEMBER B ON A.M_SEQ = B.M_SEQ										\n");
    		if(!"".equals(register)){
    			sb.append("  		AND B.M_SEQ = "+ register +"											\n");
    		}
    		
    		if(!"".equals(relationKey)){
    			sb.append("  	INNER JOIN RELATION_KEYWORD_MAP C ON A.ID_SEQ = C.ID_SEQ					\n");	
    			sb.append("  		AND C.RK_SEQ = "+ relationKey +"										\n");
    		}
    		
    		if(typeCode!=null && typeCode.length > 0){
    			for(int i=0; i < typeCode.length; i++){
    				
    				if(typeCode[i].length > 0 && !"".equals(typeCode[i][0])){
    					
    					sb.append("	INNER JOIN ISSUE_DATA_CODE D"+(i+1)+" ON A.ID_SEQ = D"+(i+1)+".ID_SEQ 						\n");
    					sb.append("	AND D"+(i+1)+".IC_TYPE = "+typeCode[i][0]+" AND D"+(i+1)+".IC_CODE = "+typeCode[i][1]+" 	\n");
    					
    				}
    			}
    		}
    		if(typeCode_Etc!=null && typeCode_Etc.length > 0){
    			for(int i=0; i < typeCode_Etc.length; i++){
    				
    				if(typeCode_Etc[i].length > 0  && !"".equals(typeCode_Etc[i][0])){
    					
    					sb.append("	INNER JOIN ISSUE_DATA_CODE_DETAIL E"+(i+1)+" ON A.ID_SEQ = E"+(i+1)+".ID_SEQ 						\n");
    					sb.append("	AND E"+(i+1)+".IC_TYPE = "+typeCode_Etc[i][0]+" AND E"+(i+1)+".IC_CODE = "+typeCode_Etc[i][1]+" 	\n");
    					
    				}
    			}
    		}
    		sb.append(" WHERE MD_DATE BETWEEN '"+sDate+" "+sTime+"' AND '"+eDate+" "+eTime+"' 		\n");
    		//키워드 조건 주기(AND검색)
			if(!"".equals(searchKey)){
				String[] arKey = searchKey.split(" ");
				
				for(int i =0; i < arKey.length; i++){
					if(!arKey[i].trim().equals("")){
						if(keyType.equals("1")){
							//제목검색
							sb.append("   AND A.ID_TITLE LIKE '%"+arKey[i]+"%' \n");
							
						}else if(keyType.equals("2")){
							//제목+내용검색
							sb.append("   AND CONCAT(A.ID_TITLE,A.ID_CONTENT) LIKE '%"+arKey[i]+"%'		\n");
						}			
					}
				}
			}
			if(!"".equals(check_no)){
				sb.append(" AND A.ID_SEQ IN ("+check_no+") 												\n");
			}
			sb.append("		) A																			\n");
			if(ra_same.equals("2")){
				sb.append("		GROUP BY A.MD_PSEQ														\n");
			}
			sb.append("		ORDER BY A.MD_DATE, A.MD_PSEQ												\n");
			
			System.out.println(sb.toString());
    		stmt = dbconn.createStatement();
    		rs = stmt.executeQuery(sb.toString());
    		result = new ArrayList();
    		int colCnt = rs.getMetaData().getColumnCount();
    		String[] column = null;
    		
    		
    		while ( rs.next() ) {
    			column = new String[colCnt];
    			for(int i = 0; i < colCnt; i++){
    				String temp = rs.getString(i+1);
    				column[i] = temp;
    			}
    			result.add(column);
    		}
    		
    	} catch (SQLException ex) {
    		ex.printStackTrace();
			Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex) {
        	ex.printStackTrace();
        	Log.writeExpt(ex);
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn  != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    	return result; 
    }
    
    
    
    public ArrayList ReassembleTypeCode(String TypeCode){
		ArrayList master = new ArrayList();
		
		if(!TypeCode.equals("")) {
			String[] tempRow = TypeCode.split("@");
			String[] tempCol = null;
			
			String[] getTBean = null;
			String[] setTBean = null;
			
			boolean exChk = false;
			
			for (int i =0; i < tempRow.length; i++) {
				tempCol = tempRow[i].split(",");
				
				exChk = false;
				for (int j =0; j < master.size(); j++){
					getTBean = (String[])master.get(j);
					
					//마스터에 있으면 입장 
					if (tempCol[0].equals(getTBean[0])) {
						//기존에 CODE를 쉼표단위로 추가하여 업데이트
						setTBean = new String[2];
						setTBean[0] = getTBean[0];
						setTBean[1] = (getTBean[1] + "," + tempCol[1]);
						
						master.set(j, setTBean);
						
						exChk = true;
						break;
					}
				}
				
				//마스터어레이에 없으면 입장
				if (!exChk) {
					//새로 추가
					setTBean = new String[2];
					setTBean[0]= tempCol[0];
					if (tempCol.length > 0) {
						setTBean[1] = tempCol[1];
					}
					master.add(setTBean);
				}
			}
		}
		
		return master;
	}
    
    public ArrayList ReassembleTypeCode_sum(String[] col_arType, String[] col_arType_name, ArrayList and_arTypeCode){
    	
    	ArrayList result = new ArrayList();
    
    	String[] arTypeCode = null;
    	String[] colResult = null;
    	boolean tmpChk = false;
    	String type = "";
    	String code = "";
    	
    	//컬럼부 삽입
    	for(int i = 0; i < col_arType.length; i++ ){
    		tmpChk = false;
    		for(int j = 0; j < and_arTypeCode.size(); j++){
    			arTypeCode = (String[])and_arTypeCode.get(j);
    			
    			if(col_arType[i].equals(arTypeCode[0])){
    				type = arTypeCode[0];
    				code = arTypeCode[1];
    				tmpChk = true;
    				break;
    			}
    		}
    		
    		colResult = new String[5];
    		
    		if(tmpChk){
    			
    			colResult[0] = "column";
    			colResult[1] = col_arType_name[i];
    			colResult[2] = "inner";
    			colResult[3] = type;
    			colResult[4] = code;
    		}else{
    			
    			colResult[0] = "column";
    			colResult[1] = col_arType_name[i];
    			colResult[2] = "outer";
    			colResult[3] = col_arType[i];
    			colResult[4] = "";
    		}
    		result.add(colResult);
    	}
    	
    	
    	
    	//조건부 삽입
    	for(int i = 0; i < and_arTypeCode.size(); i++ ){
    		arTypeCode = (String[])and_arTypeCode.get(i);
    		tmpChk = false;
    		
    		for(int j = 0; j < col_arType.length; j++){
    			if(arTypeCode[0].equals(col_arType[j])){
    				tmpChk = true;
    				break;
    			}
    		}
    		
    		if(!tmpChk){
    			colResult = new String[5];
    			
    			colResult[0] = "and";
    			colResult[1] = "";
    			colResult[2] = "inner";
    			colResult[3] = arTypeCode[0];
    			colResult[4] = arTypeCode[1];
    			
    			result.add(colResult);
    		}
    		
    	}
    	
    	return result;
    }
    
    public ArrayList ReassembleTypeCode_sum(String[][] typeName, ArrayList and_arTypeCode){
    	
    	ArrayList result = new ArrayList();
    
    	String[] arTypeCode = null;
    	String[] colResult = null;
    	boolean tmpChk = false;
    	String type = "";
    	String code = "";
    	
    	//컬럼부 삽입
    	for(int i = 0; i < typeName.length; i++ ){
    		tmpChk = false;
    		colResult = new String[5];
    		
    		
    		if(typeName[i][0].substring(0, 1).equals("T")){
        		for(int j = 0; j < and_arTypeCode.size(); j++){
        			arTypeCode = (String[])and_arTypeCode.get(j);
        			
        			if(typeName[i][0].substring(1).equals(arTypeCode[0])){
        				type = arTypeCode[0];
        				code = arTypeCode[1];
        				tmpChk = true;
        				break;
        			}
        		}
        		
        		if(tmpChk){
        			
        			colResult[0] = "column";
        			colResult[1] = typeName[i][1];
        			colResult[2] = "inner";
        			colResult[3] = type;
        			colResult[4] = code;
        			
        		}else{
        			
        			colResult[0] = "column";
        			colResult[1] = typeName[i][1];
        			colResult[2] = "outer";
        			colResult[3] = typeName[i][0].substring(1);
        			colResult[4] = "";
        		}
    		}else{
    			colResult[0] = "column";
    			colResult[1] = typeName[i][1];
    			colResult[2] = "data";
    			colResult[3] = typeName[i][0];
    			colResult[4] = "";
    		}
    		
    		
    		
    		
    		result.add(colResult);
    	}
    	
    	
    	
    	//int intChk = 0;
    	//조건부 삽입
    	for(int i = 0; i < and_arTypeCode.size(); i++ ){
    		arTypeCode = (String[])and_arTypeCode.get(i);
    		tmpChk = false;
    		
    		
    		for(int j = 0; j < typeName.length; j++){
        		if(typeName[j][0].substring(0, 1).equals("T")){
        			if(arTypeCode[0].equals(typeName[j][0].substring(1))){
        				tmpChk = true;
        				break;
        			}
        		}	
    		}
    		
    		if(!tmpChk){
    			
    			colResult = new String[5];
    			
    			colResult[0] = "and";
    			colResult[1] = "";
    			colResult[2] = "inner";
    			colResult[3] = arTypeCode[0];
    			colResult[4] = arTypeCode[1];
    			
    			result.add(colResult);
    		}
    		
    	}
    	
    	return result;
    }
    
    
    
    
    public void CreateExcel(String path, String fileName, String[] arFullName, String[] arMainName, String[] arTypeName, ArrayList arData){
    	
    	try
        {
    		XSSFWorkbook workbook = new XSSFWorkbook(); // xlsx 파일 생성
    		XSSFSheet sheet = workbook.createSheet(); // sheet 생성
    		XSSFRow row = null;
    		XSSFCell cell = null;
    		XSSFFont font = null;
    		/*해더*/
    		
    		XSSFCellStyle hederStyle = workbook.createCellStyle(); //스타일
    		
    		//폰트
    		font = workbook.createFont();
    		font.setFontName("맑은 고딕");
    		//font.setColor(new XSSFColor(new Color(250,246,231)));
    		font.setFontHeightInPoints((short)10);
    		//font.setBoldweight((short)font.BOLDWEIGHT_BOLD);
    		font.setBold(true);
    		
    		hederStyle.setFont(font);
    		//정렬
    		hederStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
    		hederStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
    		
    		//색 채우기
    		hederStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
    		hederStyle.setFillForegroundColor(new XSSFColor(new Color(250,246,231)));
    		
    		row = sheet.createRow(0);   // row 생성
    		
    		// cell 생성
    		for(int i =0; i < arFullName.length; i++){
    			
    			cell = row.createCell(i);
    			cell.setCellValue(arFullName[i]);
    			cell.setCellStyle(hederStyle);

    		}
    		
    		/*리스트*/
    		XSSFCellStyle bodyStyle = workbook.createCellStyle(); //스타일
    		
    		//폰트
    		font = workbook.createFont();
    		font.setFontName("맑은 고딕");
    		//font.setColor(new XSSFColor(new Color(250,246,231)));
    		font.setFontHeightInPoints((short)10);
    		//font.setBoldweight((short)font.BOLDWEIGHT_BOLD);
    		font.setBold(false);
    		
    		bodyStyle.setFont(font);
    		//정렬
    		bodyStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
    		bodyStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
    		
    		String[] arBean = null;
    		
    		for(int i = 1; i <= arData.size(); i++){
    			arBean = (String[])arData.get(i-1);
    			
    			
    			row = sheet.createRow(i);   // row 생성
    			
    			// cell 생성
        		for(int j =0; j < arBean.length; j++){	
        			cell = row.createCell(j);
        			cell.setCellValue(arBean[j]);
        			cell.setCellStyle(bodyStyle);        			
        		}
        	
    		}
    		//String[] main_name = {"번호","일자","사이트","제목","URL"};
    		
    		//컬럼 조정
    		for(int i =0; i < arMainName.length; i++){
    			if(arMainName[i].equals("번호")){
    				sheet.setColumnWidth((short)i,(short)4000);
    			}else if(arMainName[i].equals("일자")){
    				sheet.setColumnWidth((short)i,(short)4000);
    			}else if(arMainName[i].equals("사이트")){
    				sheet.setColumnWidth((short)i,(short)5000);
    			}else if(arMainName[i].equals("제목")){
    				sheet.setColumnWidth((short)i,(short)13000);
    			}else if(arMainName[i].equals("URL")){
    				sheet.setColumnWidth((short)i,(short)13000);
    			}
    		}
    		
    		for(int i = arMainName.length; i < arFullName.length; i++){
    			//시트자동조절
        		sheet.autoSizeColumn(i);
    		}
    		
    		FileOutputStream outStream;
            try {
        	   // Write.xlsx에 대한 출력 스트림 생성
            	
            	//저장 경로 디텍토리가 없으면 생성해주고 나모 저장경로 셋팅
        	    if(!new File(path).isDirectory()) new File(path).mkdirs();
            	
        	   outStream = new FileOutputStream(path + fileName);
        	   // Write.xlsx에 위에서 입력된 데이터를 씀
        	   workbook.write(outStream);
        	   
        	   
        	   
        	   outStream.close();
        	   
        	} catch (Exception e){
        		  System.out.println(e.getMessage());
        	}
            
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            System.out.println(e);
        }
    }
    
    
    public void CreateExcel_v2(String path, String fileName, String[] arFullName, String[] arMainName, String[] arTypeName, ArrayList arData){
    	
    	try
        {
    		SXSSFWorkbook workbook = new SXSSFWorkbook(100); // xlsx 파일 생성
    		Sheet sheet = workbook.createSheet(); // sheet 생성
    		Row row = null;
    		Cell cell = null;
    		Font font = null;
    		/***해더***/
    		
    		CellStyle hederStyle = workbook.createCellStyle(); //스타일
    		
    		//폰트
    		font = workbook.createFont();
    		font.setFontName("맑은 고딕");
    		//font.setColor(new XSSFColor(new Color(250,246,231)));
    		font.setFontHeightInPoints((short)10);
    		font.setBoldweight((short)font.BOLDWEIGHT_BOLD);
    		//font.setBold(true);
    		
    		hederStyle.setFont(font);
    		//정렬
    		hederStyle.setAlignment(CellStyle.ALIGN_CENTER);
    		hederStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    		
    		//색 채우기
    		hederStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
    		hederStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    		//hederStyle.setFillForegroundColor(new XSSFColor(new Color(250,246,231)));
    		
    		
    		row = sheet.createRow(0);   // row 생성
    		
    		// cell 생성
    		for(int i =0; i < arFullName.length; i++){
    			
    			cell = row.createCell(i);
    			cell.setCellValue(arFullName[i]);
    			cell.setCellStyle(hederStyle);

    		}
    		
    		/***리스트***/
    		CellStyle bodyStyle = workbook.createCellStyle(); //스타일
    		
    		//폰트
    		font = workbook.createFont();
    		font.setFontName("맑은 고딕");
    		//font.setColor(new XSSFColor(new Color(250,246,231)));
    		font.setFontHeightInPoints((short)10);
    		//font.setBoldweight((short)font.BOLDWEIGHT_BOLD);
    		//font.setBold(false);
    		
    		bodyStyle.setFont(font);
    		//정렬
    		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
    		bodyStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    		
    		String[] arBean = null;
    		
    		for(int i = 1; i <= arData.size(); i++){
    			arBean = (String[])arData.get(i-1);
    			
    			
    			row = sheet.createRow(i);   // row 생성
    			
    			// cell 생성
        		for(int j =0; j < arBean.length; j++){	
        			cell = row.createCell(j);
        			cell.setCellValue(arBean[j]);
        			cell.setCellStyle(bodyStyle);        			
        		}
        	
    		}
    		//String[] main_name = {"번호","일자","사이트","제목","URL"};
    		
    		//컬럼 조정
    		for(int i =0; i < arMainName.length; i++){
    			if(arMainName[i].equals("번호")){
    				sheet.setColumnWidth((short)i,(short)4000);
    			}else if(arMainName[i].equals("일자")){
    				sheet.setColumnWidth((short)i,(short)4000);
    			}else if(arMainName[i].equals("사이트")){
    				sheet.setColumnWidth((short)i,(short)5000);
    			}else if(arMainName[i].equals("제목")){
    				sheet.setColumnWidth((short)i,(short)13000);
    			}else if(arMainName[i].equals("URL")){
    				sheet.setColumnWidth((short)i,(short)13000);
    			}
    		}
    		
    		for(int i = arMainName.length; i < arFullName.length; i++){
    			//시트자동조절
        		sheet.autoSizeColumn(i);
    		}
    		
    		FileOutputStream outStream;
            try {
        	   // Write.xlsx에 대한 출력 스트림 생성
            	
            	//저장 경로 디텍토리가 없으면 생성해주고 나모 저장경로 셋팅
        	    if(!new File(path).isDirectory()) new File(path).mkdirs();
            	
        	   outStream = new FileOutputStream(path + fileName);
        	   // Write.xlsx에 위에서 입력된 데이터를 씀
        	   workbook.write(outStream);
        	   
        	   outStream.close();
        	   workbook.dispose();

            } catch (Exception e){
        		  System.out.println(e.getMessage());
        	}
            
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            System.out.println(e);
        }
    }
    
    
    

   
    public void addExcel(String path, String fileName, ArrayList arData){
    	
    	try
        {
    		
    		//입력 스트림 생성
    		
    		//File file = new File(path + fileName);
    		//System.out.println("바보1.1");
    		//OPCPackage opcp = OPCPackage.open(file);
    		
            FileInputStream fileInput = new FileInputStream(path + fileName);
            //Workbook 읽기
            XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
            //XSSFWorkbook workbook = new XSSFWorkbook(opcp);
            fileInput.close();
            //opcp.close();
            
    		//XSSFWorkbook workbook = new XSSFWorkbook(); // xlsx 파일 생성
    		//XSSFSheet sheet = workbook.createSheet(); // sheet 생성
            XSSFSheet sheet = workbook.getSheetAt(0);
    		XSSFRow row = null;
    		XSSFCell cell = null;
    		XSSFFont font = null;
    		
    		/*리스트*/
    		XSSFCellStyle bodyStyle = workbook.createCellStyle(); //스타일
    		//폰트
    		font = workbook.createFont();
    		font.setFontName("맑은 고딕");
    		//font.setColor(new XSSFColor(new Color(250,246,231)));
    		font.setFontHeightInPoints((short)10);
    		//font.setBoldweight((short)font.BOLDWEIGHT_BOLD);
    		font.setBold(false);
    		
    		bodyStyle.setFont(font);
    		//정렬
    		bodyStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
    		bodyStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
    		int lastCnt = sheet.getLastRowNum()+1;
    		String[] arBean = null;
    		for(int i = 0; i < arData.size(); i++){
    			arBean = (String[])arData.get(i);
    			
    			
    			row = sheet.createRow(lastCnt + i);   // row 생성
    			
    			// cell 생성
        		for(int j =0; j < arBean.length; j++){	
        			cell = row.createCell(j);
        			cell.setCellValue(arBean[j]);
        			cell.setCellStyle(bodyStyle);        			
        		}
        		
    		}
    		FileOutputStream outStream;
    		
            try {
        	   // Write.xlsx에 대한 출력 스트림 생성
            	//저장 경로 디텍토리가 없으면 생성해주고 나모 저장경로 셋팅
        	    if(!new File(path).isDirectory()) new File(path).mkdirs();
        	   outStream = new FileOutputStream(path + fileName);
        	   // Write.xlsx에 위에서 입력된 데이터를 씀
        	   workbook.write(outStream);
        	   outStream.close();
        	} catch (Exception e){
        		  System.out.println(e.getMessage());
        	}
            
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            System.out.println(e);
        }
    }
    
    public void addExcel_v2(String path, String fileName, ArrayList arData){
    	
    	try
        {
    		
    		//입력 스트림 생성
    		
    		File file = new File(path + fileName);
    		//System.out.println("바보1.1");
    		OPCPackage opcp = OPCPackage.open(file);
    		
            //FileInputStream fileInput = new FileInputStream(path + fileName);
            //Workbook 읽기
            
            
            //XSSFWorkbook x_workbook = new XSSFWorkbook(fileInput);
            XSSFWorkbook x_workbook = new XSSFWorkbook(opcp);
            int lastCnt = x_workbook.getSheetAt(0).getLastRowNum() + 1;
            System.out.println("lastCnt:" + lastCnt);
            
            
            SXSSFWorkbook workbook = new SXSSFWorkbook(x_workbook, 100);
            
            //
            //fileInput.close();
            opcp.close();
            
    		//XSSFWorkbook workbook = new XSSFWorkbook(); // xlsx 파일 생성
    		//XSSFSheet sheet = workbook.createSheet(); // sheet 생성
            Sheet sheet = workbook.getSheetAt(0);
            
    		Row row = null;
    		Cell cell = null;
    		Font font = null;
    		
    		/*리스트*/
    		CellStyle bodyStyle = workbook.createCellStyle(); //스타일

    		//폰트
    		font = workbook.createFont();
    		font.setFontName("맑은 고딕");
    		//font.setColor(new XSSFColor(new Color(250,246,231)));
    		font.setFontHeightInPoints((short)10);
    		//font.setBoldweight((short)font.BOLDWEIGHT_BOLD);
    		//font.setBold(false);
    		
    		bodyStyle.setFont(font);
    		//정렬
    		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
    		bodyStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    		//int lastCnt = sheet.getLastRowNum()+1;
    		
    		//System.out.println("lastCnt:" + lastCnt);
    		//lastCnt = 10000;
    		
    		String[] arBean = null;
    		for(int i = 0; i < arData.size(); i++){
    			arBean = (String[])arData.get(i);
    			
    			
    			row = sheet.createRow(lastCnt + i);   // row 생성
    			
    			// cell 생성
        		for(int j =0; j < arBean.length; j++){	
        			cell = row.createCell(j);
        			cell.setCellValue(arBean[j]);
        			cell.setCellStyle(bodyStyle);        			
        		}
        		
    		}
    		FileOutputStream outStream;
    		
            try {
        	   // Write.xlsx에 대한 출력 스트림 생성
            	//저장 경로 디텍토리가 없으면 생성해주고 나모 저장경로 셋팅
        	    if(!new File(path).isDirectory()) new File(path).mkdirs();
        	   outStream = new FileOutputStream(path + fileName);
        	   // Write.xlsx에 위에서 입력된 데이터를 씀
        	   workbook.write(outStream);
        	   outStream.close();
        	   
        	   workbook.dispose();
        	   
        	} catch (Exception e){
        		e.printStackTrace();
        		  System.out.println(e.getMessage());
        	}
            
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            System.out.println(e);
        }
    }
    
    
    
    public void addExcel_v3(String path, String fileName, ArrayList pArData){
    	
    	try
        {
    		
    		/**파일 읽고 어레이에 저장**/
    		
    		File file = new File(path + fileName);
    		//System.out.println("바보1.1");
    		OPCPackage opcp = OPCPackage.open(file);
    		
            //FileInputStream fileInput = new FileInputStream(path + fileName);
            //XSSFWorkbook r_workbook = new XSSFWorkbook(fileInput);
    		XSSFWorkbook r_workbook = new XSSFWorkbook(opcp);
    		
            //fileInput.close();
    		opcp.close();
    		
    		
            XSSFSheet r_sheet = r_workbook.getSheetAt(0);
            XSSFRow r_row = null;
            XSSFCell r_cell = null;
    		String[] bean = null;
    		ArrayList arData = new ArrayList();
    		System.out.println("=================================");
            for(int i =0; i < r_sheet.getPhysicalNumberOfRows(); i++){
            	System.out.println(i);
            	r_row = r_sheet.getRow(i);
            	bean = new String[r_row.getPhysicalNumberOfCells()];
            	for(int j =0; j < r_row.getPhysicalNumberOfCells(); j++){
            		r_cell = r_row.getCell(j);
            		bean[j] = r_cell.getStringCellValue(); 
            	}
            	arData.add(bean);
            	
            }
            System.out.println("=================================");
            
            /**기존 어레이와 새로운 어레이 합체**/
            for(int i = 0; i < pArData.size(); i++){
            	arData.add(pArData.get(i));
            }
            
            /**엑셀 만들기**/
            SXSSFWorkbook w_workbook = new SXSSFWorkbook(100); // xlsx 파일 생성
            Sheet w_sheet = w_workbook.createSheet();
            Row w_row = null;
            Cell w_cell = null;
            
    		CellStyle bodyStyle = w_workbook.createCellStyle(); //스타일
    		
    		//폰트
    		Font font = w_workbook.createFont();
    		font.setFontHeightInPoints((short)10);    		
    		bodyStyle.setFont(font);
    		
    		//정렬
    		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
    		bodyStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    
    		String[] arBean = null;
    		
    		
    		
    		for(int i = 0; i < arData.size(); i++){
    			arBean = (String[])arData.get(i);
    			
    			
    			w_row = w_sheet.createRow(i);   // row 생성
    			
    			// cell 생성
        		for(int j =0; j < arBean.length; j++){	
        			w_cell = w_row.createCell(j);
        			w_cell.setCellValue(arBean[j]);
        			w_cell.setCellStyle(bodyStyle);        			
        		}
        		
    		}
    		FileOutputStream outStream;
    		
            try {
        	   // Write.xlsx에 대한 출력 스트림 생성

            	//저장 경로 디텍토리가 없으면 생성해주고 나모 저장경로 셋팅
        	    if(!new File(path).isDirectory()) new File(path).mkdirs();
        	   outStream = new FileOutputStream(path + fileName);

        	   // Write.xlsx에 위에서 입력된 데이터를 씀
        	   w_workbook.write(outStream);
        	   outStream.close();        	   
        	   w_workbook.dispose();
        	  
        	} catch (Exception e){
        		e.printStackTrace();
        		  System.out.println(e.getMessage());
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            System.out.println(e);
        }
    }
    
    
    public void addExcel_v4(String path, String fileName, ArrayList arData){
    	
    	
    	try
        {
            
            SXSSFWorkbook w_workbook = new SXSSFWorkbook(100); // xlsx 파일 생성
            Sheet w_sheet = w_workbook.createSheet();
            Row w_row = null;
            Cell w_cell = null;
            
    		CellStyle bodyStyle = w_workbook.createCellStyle(); //스타일
    		
    		//폰트
    		Font font = w_workbook.createFont();
    		font.setFontHeightInPoints((short)10);    		
    		bodyStyle.setFont(font);
    		
    		//정렬
    		bodyStyle.setAlignment(CellStyle.ALIGN_CENTER);
    		bodyStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    
    		String[] arBean = null;
    		
    		
    		
    		for(int i = 0; i < arData.size(); i++){
    			arBean = (String[])arData.get(i);
    			
    			
    			w_row = w_sheet.createRow(i);   // row 생성
    			
    			// cell 생성
        		for(int j =0; j < arBean.length; j++){	
        			w_cell = w_row.createCell(j);
    				if(arBean[j].length() > SpreadsheetVersion.EXCEL2007.getMaxTextLength()){
    					//throw new IllegalArgumentException("The maximum length of cell contents (text) is 32,767 characters");
    					arBean[j] = arBean[j].substring(0, 32767);
    				}
        			w_cell.setCellValue(arBean[j]);
        			w_cell.setCellStyle(bodyStyle);        			
        		}
        		
    		}
    		FileOutputStream outStream;
    		
            try {
        	   // Write.xlsx에 대한 출력 스트림 생성

            	//저장 경로 디텍토리가 없으면 생성해주고 나모 저장경로 셋팅
        	    if(!new File(path).isDirectory()) new File(path).mkdirs();
        	   outStream = new FileOutputStream(path + fileName);

        	   // Write.xlsx에 위에서 입력된 데이터를 씀
        	   w_workbook.write(outStream);
        	   outStream.close();        	   
        	   w_workbook.dispose();
        	  
        	} catch (Exception e){
        		e.printStackTrace();
        		  System.out.println(e.getMessage());
        	}
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            System.out.println(e);
        }
        
    }
    
    ArrayList fullarData = new ArrayList();

	public ArrayList ReadExcel_v4(String path, String fileName){
    	
    	ArrayList result = new ArrayList();
    	try
        {
    		File file = new File(path + fileName);
    		OPCPackage opcp = OPCPackage.open(file);
    		XSSFWorkbook r_workbook = new XSSFWorkbook(opcp);
    		opcp.close();
    		
            XSSFSheet r_sheet = r_workbook.getSheetAt(0);
            XSSFRow r_row = null;
            XSSFCell r_cell = null;
    		String[] bean = null;
    		
    		//System.out.println("좋아좋아");
    		//System.out.println(fileName);
    		//System.out.println(r_sheet.getPhysicalNumberOfRows());
    		//System.out.println(fullarData.size());
    		
            for(int i =0; i < r_sheet.getPhysicalNumberOfRows(); i++){
            	r_row = r_sheet.getRow(i);
            	bean = new String[r_row.getPhysicalNumberOfCells()];
            	for(int j =0; j < r_row.getPhysicalNumberOfCells(); j++){
            		r_cell = r_row.getCell(j);
            		bean[j] = r_cell.getStringCellValue(); 
            	}
            	result.add(bean);
            }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    	
    	return result;
    	
    }
    
    
    
    public ArrayList SumExcel_v4(String path, String fileName, int diffCnt){
    	
    	//ArrayList result = new ArrayList();
    	
    	fullarData = new ArrayList();
    	
    	for(int i = 1; i <= diffCnt; i++){
    		String[] arFileName = fileName.split("_");
    		String reFileName = arFileName[0] + "_" + arFileName[1] + "_" + i + "_" + arFileName[3];
    		ReadExcel_v4(path, reFileName);
    	}
    	
    	return fullarData;
    }
    
    
    public boolean CreateExcel_v4(String path, String fileName, String[][] arFullName, int diffCnt){
    	
    	boolean result = false;
    	
    	try
        {
    		System.out.println(path+fileName);
    		SXSSFWorkbook workbook = new SXSSFWorkbook(100); // xlsx 파일 생성
    		//XSSFWorkbook workbook = new XSSFWorkbook();
    		Sheet sheet = workbook.createSheet(); // sheet 생성
    		Row row = null;
    		Cell cell = null;
    		Font font = null;
    		
    		//CreationHelper createHelper = workbook.getCreationHelper();
    		//Hyperlink link = null;
    		//int linkIdx = 0;
    		

    		/***해더***/
    		CellStyle hederStyle = workbook.createCellStyle(); //스타일
    		
    		//폰트
    		font = workbook.createFont();
    		font.setFontName("맑은 고딕");
    		//font.setColor(new XSSFColor(new Color(250,246,231)));
    		font.setFontHeightInPoints((short)10);
    		font.setBoldweight((short)font.BOLDWEIGHT_BOLD);
    		//font.setBold(true);
    		
    		hederStyle.setFont(font);
    		//정렬
    		hederStyle.setAlignment(CellStyle.ALIGN_CENTER);
    		hederStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    		
    		//색 채우기
    		hederStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
    		hederStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    		//hederStyle.setFillForegroundColor(new XSSFColor(new Color(250,246,231)));
    		
    		row = sheet.createRow(0);   // row 생성
    		// cell 생성
    		for(int i =0; i < arFullName.length; i++){
    			cell = row.createCell(i);
    			cell.setCellValue(arFullName[i][1]);
    			cell.setCellStyle(hederStyle);

    		}
    		
    		/***리스트***/
    		CellStyle[] arBodyStyle = new CellStyle[arFullName.length];
    		for(int i = 0; i < arFullName.length; i++){
    			
    			
    			/***공통 스타일적용***/
    			arBodyStyle[i] = workbook.createCellStyle(); //스타일
    			
    			//폰트
        		font = workbook.createFont();
        		font.setFontName("맑은 고딕");
        		font.setFontHeightInPoints((short)10);
        		
        		arBodyStyle[i].setFont(font);
        		//정렬
        		arBodyStyle[i].setAlignment(CellStyle.ALIGN_CENTER);
        		arBodyStyle[i].setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        		
        		//자동줄바꿈
        		if(arFullName[i][0].indexOf("D") >= 0){
            		arBodyStyle[i].setWrapText(false);
        		}else{
        			arBodyStyle[i].setWrapText(true);
        		}
        		
        		
        		/***개별 스타일적용***/
        		
        		if(arFullName[i][0].equals("D4")){
        			
        			//링크걸 컬럼인덱스 구하기(URL)
        			//linkIdx = i;
        			

            		//font = workbook.createFont();
            		//font.setFontName("맑은 고딕");
            		//font.setFontHeightInPoints((short)10);
            		//font.setColor(IndexedColors.BLUE.index);
            		//font.setUnderline(Font.U_SINGLE);
            		//arBodyStyle[i].setFont(font);
        			
        			
            		arBodyStyle[i].setAlignment(CellStyle.ALIGN_LEFT);
            		
        		}else if(arFullName[i][0].equals("D3")){
        			arBodyStyle[i].setAlignment(CellStyle.ALIGN_LEFT);
        		}
        		
        		
    		}

    		String[] arBean = null;
    		
    		ArrayList arData = null;
    		int idx  = 0;
    		for(int i = 1; i <= diffCnt; i++){
        		String[] arFileName = fileName.split("_");
        		String reFileName = arFileName[0] + "_" + arFileName[1] + "_" + i + "_" + arFileName[3];
        		arData = ReadExcel_v4(path, reFileName);
        		
        		for(int j = 0; j < arData.size(); j++){
        			arBean = (String[])arData.get(j);
        			
        			idx++;
        			row = sheet.createRow(idx);   // row 생성
        			
        			for(int s = 0; s < arBean.length; s++){
        				cell = row.createCell(s);
            			cell.setCellValue(arBean[s]);
            			cell.setCellStyle(arBodyStyle[s]);
            			
            			//링크 적용하기~
            			/*
            			if(s == linkIdx){
            				link = workbook.getCreationHelper().createHyperlink(Hyperlink.LINK_URL);
            				link.setAddress(URLEncoder.encode(arBean[s], "utf-8"));
            				cell.setHyperlink(link);
            			}
            			*/
            			
            			
        			}
        			
        		}
        	}

    		//각 컬럼 크기 넓이 정하기
    		for(int i = 0; i < arFullName.length; i++){
    			if(arFullName[i][0].equals("D0")){
    				sheet.setColumnWidth((short)i,(short)3000);
    			}else if(arFullName[i][0].equals("D1")){
    				sheet.setColumnWidth((short)i,(short)4000);
    			}else if(arFullName[i][0].equals("D2")){
    				sheet.setColumnWidth((short)i,(short)5000);
    			}else if(arFullName[i][0].equals("D3")){
    				sheet.setColumnWidth((short)i,(short)17000);
    			}else if(arFullName[i][0].equals("D4")){
    				sheet.setColumnWidth((short)i,(short)13000);
    			}else if(arFullName[i][0].equals("D5")){
    				sheet.setColumnWidth((short)i,(short)30000);
    			}else{
    				sheet.setColumnWidth((short)i,(short)3000);
    			}
    			//시트자동조절
        		//sheet.autoSizeColumn(i);
    		}
    		
    		FileOutputStream outStream;
            try {
        	   // Write.xlsx에 대한 출력 스트림 생성
            	
            	//저장 경로 디텍토리가 없으면 생성해주고 나모 저장경로 셋팅
        	   if(!new File(path).isDirectory()) new File(path).mkdirs();
        	    
        	   outStream = new FileOutputStream(path + fileName);
        	   // Write.xlsx에 위에서 입력된 데이터를 씀
        	   workbook.write(outStream);
        	   
        	   outStream.close();
        	   workbook.dispose();
        	   
        	   //workbook.close();
        	   result = true;
        	   
        	} catch (Exception e){
        		  System.out.println(e.getMessage());
        	}
            
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            System.out.println(e);
        }
    	
    	
    	return result;
    }
    
    public void DelExcelFile(String path, String fileName, int diffCnt){
    	
    	String[] arFileName = fileName.split("_");
    	String reFileName = "";
    	File file = null;
    	
    	for(int i = 0; i <= diffCnt; i++){
    		reFileName = arFileName[0] + "_" + arFileName[1] + "_"+ i +"_" + arFileName[3];
    		file = new File(path + reFileName);
    		file.delete();
    	}
    }
    
    public ArrayList getTypeName(){
    	ArrayList result = null;
    	DBconn dbconn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	StringBuffer sb = new StringBuffer();
    	try {
    		
    		dbconn = new DBconn();
    		dbconn.getDBCPConnection();
    		
    		sb = new StringBuffer();
    		sb.append("SELECT IC_TYPE, IC_NAME FROM ISSUE_CODE WHERE IC_CODE = 0 AND IC_USEYN='Y' ORDER BY IC_TYPE ASC \n");
    	
    		System.out.println(sb.toString());
    		stmt = dbconn.createStatement();
    		rs = stmt.executeQuery(sb.toString());
    		result = new ArrayList();
    		
    		while ( rs.next() ) {
    			result.add(rs.getString("IC_TYPE") + "|" + rs.getString("IC_NAME"));
    		}
    		
    	} catch (SQLException ex) {
    		ex.printStackTrace();
			Log.writeExpt(ex, sb.toString() );
        } catch (Exception ex) {
        	ex.printStackTrace();
        	Log.writeExpt(ex);
		} finally {
			try { if( rs  != null) rs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
			try { if( stmt  != null) stmt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
            try { if( dbconn  != null) dbconn.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
        }
    	return result;
    }
}
