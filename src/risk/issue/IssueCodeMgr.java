package risk.issue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import risk.DBconn.DBconn;
import risk.issue.IssueCodeBean;
import risk.util.Log;
import risk.util.StringUtil;

public class IssueCodeMgr {	

  public ArrayList arrAllType = new ArrayList();
  private static IssueCodeMgr instance = new IssueCodeMgr();

  public static IssueCodeMgr getInstance() {

    return instance;
  }

  /**
   * mode : 0: 분류항목까지 포함, 1: 분류항목은 제외
   * 
   */	
  public void init(int mode) {

    IssueCodeBean icb; 
    ArrayList arrEachType = null;
    arrEachType = new ArrayList();
    DBconn  conn  = null;
    PreparedStatement pstmt = null;
    Statement stmt  = null;
    ResultSet rs = null;
    StringBuffer sb = null;

    this.arrAllType = new ArrayList();

    int prevType = 1;
    int nowType = 1;


    try {
      conn  = new DBconn();
      conn.getDBCPConnection();
      sb = new StringBuffer();
      stmt = conn.createStatement();

      //임시로 TEMP 테이블
      if(mode != 1){
        sb.append(" SELECT * FROM ISSUE_CODE WHERE IC_USEYN='Y' ORDER BY IC_TYPE, IC_ORDER ASC \n");
      }else{
        sb.append(" SELECT * FROM ISSUE_CODE WHERE IC_CODE > 0 AND IC_USEYN='Y' ORDER BY IC_TYPE, IC_CODE ASC  \n");
      }

      Log.debug(sb.toString() );
      rs = stmt.executeQuery(sb.toString());

      while(rs.next()) {

        nowType = rs.getInt("ic_type");
        if ( prevType != nowType ) { 
          arrAllType.add(arrEachType);
          prevType = nowType;
          arrEachType = new ArrayList();
        }

        icb = new IssueCodeBean();
        icb.setIc_seq(rs.getInt(		"IC_SEQ"	));
        icb.setIc_name(rs.getString(		"IC_NAME"	));
        icb.setIc_type( rs.getInt(		"IC_TYPE"	));
        icb.setIc_code(rs.getInt(		"IC_CODE"	));
        icb.setIc_ptype(rs.getInt(		"IC_PTYPE"	));
        icb.setIc_pcode(rs.getInt(		"IC_PCODE"	));
        icb.setM_seq(rs.getString(		"M_SEQ"		));
        icb.setIc_regdate(rs.getString(		"IC_REGDATE"	));
        icb.setIc_description(rs.getString(		"IC_DESCRIPTION"	));

        arrEachType.add(icb);			    		
      } // End while
      arrAllType.add(arrEachType);
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

  /**
   * 포탈 초기화면 속성 코드 조회
   * 
   * @return
   */
  public ArrayList getPortalAttrCode() {
    ArrayList result = new ArrayList();
    IssueCodeBean icb;

    DBconn  conn  = null;
    PreparedStatement pstmt = null;
    Statement stmt  = null;
    ResultSet rs = null;
    StringBuffer sb = null;

    try {
      conn  = new DBconn();
      conn.getDBCPConnection();
      sb = new StringBuffer();
      stmt = conn.createStatement();

      sb.append("SELECT *\n");
      sb.append("  FROM ISSUE_CODE\n");
      sb.append(" WHERE IC_TYPE = 20\n");
      sb.append("   AND IC_CODE > 0\n");
      sb.append(" ORDER BY IC_ORDER\n");

      Log.debug(sb.toString() );
      rs = stmt.executeQuery(sb.toString());


      while(rs.next()) {

        icb = new IssueCodeBean();
        icb.setIc_seq(rs.getInt("IC_SEQ"));
        icb.setIc_name(rs.getString("IC_NAME"));
        icb.setIc_type( rs.getInt("IC_TYPE"));
        icb.setIc_code(rs.getInt("IC_CODE"));
        icb.setIc_ptype(rs.getInt("IC_PTYPE"));
        icb.setIc_pcode(rs.getInt("IC_PCODE"));
        icb.setM_seq(rs.getString("M_SEQ"));
        icb.setIc_regdate(rs.getString("IC_REGDATE"));
        icb.setIc_description(rs.getString("IC_DESCRIPTION"));

        result.add(icb);

      } // End while

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

  public ArrayList getSouceCodeOrder() {


    ArrayList result = new ArrayList();
    IssueCodeBean icb; 


    DBconn  conn  = null;
    PreparedStatement pstmt = null;
    Statement stmt  = null;
    ResultSet rs = null;
    StringBuffer sb = null;



    try {
      conn  = new DBconn();
      conn.getDBCPConnection();
      sb = new StringBuffer();
      stmt = conn.createStatement();

      /*
	       	 	sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE = 0 AND IC_USEYN = 'Y')UNION\n");
	        	sb.append("(SELECT A.*					\n");
	        	sb.append("  FROM ISSUE_CODE A 			\n");
	        	sb.append("     , IC_S_RELATION B		\n");
	        	sb.append(" WHERE A.IC_SEQ = B.IC_SEQ	\n");
	        	sb.append("   AND A.IC_TYPE = 6 		\n");
	        	sb.append("   AND A.IC_CODE > 0 		\n");
	        	sb.append("   AND A.IC_USEYN = 'Y'		\n");
	        	sb.append(" ORDER BY  B.IC_ORDER)		\n");
       */

      sb.append("SELECT A.* FROM\n");
      sb.append("(SELECT * FROM ISSUE_CODE WHERE IC_TYPE = 6 AND IC_CODE <> 8  AND IC_USEYN = 'Y')A\n");
      sb.append("LEFT OUTER JOIN IC_S_RELATION B ON A.IC_SEQ = B.IC_SEQ ORDER BY B.IC_ORDER\n");


      Log.debug(sb.toString() );
      rs = stmt.executeQuery(sb.toString());


      while(rs.next()) {

        icb = new IssueCodeBean();
        icb.setIc_seq(rs.getInt(		"IC_SEQ"	));
        icb.setIc_name(rs.getString(		"IC_NAME"	));
        icb.setIc_type( rs.getInt(		"IC_TYPE"	));
        icb.setIc_code(rs.getInt(		"IC_CODE"	));
        icb.setIc_ptype(rs.getInt(		"IC_PTYPE"	));
        icb.setIc_pcode(rs.getInt(		"IC_PCODE"	));
        icb.setM_seq(rs.getString(		"M_SEQ"		));
        icb.setIc_regdate(rs.getString(		"IC_REGDATE"	));
        icb.setIc_description(rs.getString(		"IC_DESCRIPTION"	));

        result.add(icb);

      } // End while

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

  /**
   *	코드 와 사이트 매핑
   * 
   */	
  public int getSiteMapCode(String s_seq) {		

    DBconn  conn  = null;
    Statement stmt  = null;
    ResultSet rs = null;
    StringBuffer sb = null;
    int ic_seq = 0;

    try {
      conn  = new DBconn();
      conn.getDBCPConnection();
      sb = new StringBuffer();
      stmt = conn.createStatement();

      sb.append(" SELECT IC_SEQ FROM IC_S_RELATION WHERE S_SEQ = "+s_seq+" \n");

      //Log.debug(sb.toString() );
      rs = stmt.executeQuery(sb.toString());      

      if(rs.next()) {	        	
        ic_seq = rs.getInt("IC_SEQ");
      } // End while

    } catch(SQLException ex) {
      Log.writeExpt(ex, sb.toString() );
    } catch(Exception ex) {
      Log.writeExpt(ex);
    } finally {
      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
      if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
      if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    }        
    return ic_seq;       
  }



  /**
   * 해당타입의 코드 어레이 반환
   * @return
   */
  public ArrayList GetType(int type) {
    ArrayList arrEachType = null;
    IssueCodeBean isb = null;
    int returnType = 0;

    if(arrAllType.size()>0) {
	    for (int i=0; i < arrAllType.size(); i++) {
	      arrEachType = (ArrayList) arrAllType.get(i);
	      isb = (IssueCodeBean) arrEachType.get(0);
	      if (isb.getIc_type() == type) {
	        returnType = i;
	        break;
	      }
	    }
    }
    if (returnType == 0) {
      return arrEachType;	
    }
    arrEachType = (ArrayList) arrAllType.get(returnType);		
    return arrEachType;
  }


  /**
   * 해당타입의 코드 어레이 반환
   * @return
   */
  public ArrayList GetType_mobile() {

    ArrayList arrEachType = null;
    IssueCodeBean isb = null;

    ArrayList result = new ArrayList();

    IssueCodeBean[] ar_type = new IssueCodeBean[8];

    for(int i =0; i < arrAllType.size(); i++){
      arrEachType = (ArrayList) arrAllType.get(i);
      isb = (IssueCodeBean) arrEachType.get(0);

      if (isb.getIc_type() == 6) {

        for(int j =0; j < arrEachType.size(); j++){
          isb = (IssueCodeBean) arrEachType.get(j);

          /*
					1	언론사
					6	포탈기사
					2	트위터
					3	블로그
					7	카페
					4	커뮤니티
           */


          if(isb.getIc_code() == 0){
            ar_type[0] = isb;  
          }
          if(isb.getIc_code() == 1){
            isb.setIc_name("언론");
            ar_type[1] = isb;
          }
          if(isb.getIc_code() == 6){
            isb.setIc_name("포탈");
            ar_type[2] = isb;
          }
          if(isb.getIc_code() == 2){
            ar_type[3] = isb;
          }
          if(isb.getIc_code() == 3){
            ar_type[4] = isb;
          }
          if(isb.getIc_code() == 7){
            ar_type[5] = isb;
          }
          if(isb.getIc_code() == 4){
            ar_type[6] = isb;
          }
        }

      }

      if (isb.getIc_type() == 8) {

        for(int j =0; j < arrEachType.size(); j++){
          isb = (IssueCodeBean) arrEachType.get(j);

          if(isb.getIc_code() == 2){

            isb.setIc_code(isb.getIc_code()+ 8000);
            isb.setIc_name("긴급");
            ar_type[7] = isb;

          }
        }
      }
    }

    for(int i =0; i < ar_type.length; i++){
      if(ar_type[i] != null){
        result.add(ar_type[i]);
      }
    }

    return result;
  }


  /**
   * @와,로 조합된 스트링으로 부터 해당 타입의 코드가 있는지 판단한다.
   * @param typeCode : @와,로 구성된 코드 리스트 스트링 ("1,2@2,3@3,4")
   * @param oneTypeCode : type, code 로 구성된 스트링 ("1,2")
   * @return boolean
   */
  public boolean isTypeCode(String typeCode, String oneTypeCode) {
    boolean isFind = false;
    if (!typeCode.equals("")) {
      String[] code = typeCode.split("@");
      for (int i=0; i<code.length; i++) {
        if (code[i].trim().equals(oneTypeCode.trim())) {
          isFind=true;
          break;
        }
      }
    }
    return isFind;
  }

  /**
   * 해당 타입으로  존재하는 코드  값반환
   * @return
   */
  public String getCodeVal(ArrayList arrType, int piType) {
    IssueCodeBean isb = null;
    String typeCode="";

    for (int i=0; i < arrType.size(); i++) {
      isb = (IssueCodeBean) arrType.get(i);
      if (isb.getIc_type() == piType) {
        typeCode = String.valueOf(isb.getIc_code());
        break;
      }
    }
    return typeCode;
  }

  /**
   * 해당 타입으로  존재하는 타입,코드  값반환
   * @return
   */
  public String getTypeCodeVal(ArrayList arrType, int piType) {
    IssueCodeBean isb = null;
    String typeCode="";

    for (int i=0; i < arrType.size(); i++) {
      isb = (IssueCodeBean) arrType.get(i);
      if (isb.getIc_type() == piType) {
        typeCode = isb.getIc_type()+","+isb.getIc_code();
        break;
      }
    }
    return typeCode;
  }
  

  /**
   * 해당 타입 및 부코타입으로  존재하는 타입,코드  값반환
   * @return
   */
  public String getTypeCodeVal_v2(ArrayList arrType, int piType, int piPType) {
    IssueCodeBean isb = null;
    String typeCode="";

    for (int i=0; i < arrType.size(); i++) {
      isb = (IssueCodeBean) arrType.get(i);
      if (isb.getIc_type() == piType && isb.getIc_ptype() == piPType) {
        typeCode = isb.getIc_type()+","+isb.getIc_code();
        break;
      }
    }
    return typeCode;
  }
  
  /**
   * 해당 타입으로  존재하는 복수의 타입,코드  값반환
   * @return
   */
  public ArrayList getTypeCodeVal_v2(ArrayList arrType, int piType) {
	ArrayList result = new ArrayList();
    IssueCodeBean isb = null;
    String typeCode="";

    for (int i=0; i < arrType.size(); i++) {
      isb = (IssueCodeBean) arrType.get(i);
      if (isb.getIc_type() == piType) {
        typeCode = isb.getIc_type()+","+isb.getIc_code();
        result.add(typeCode);
      }
    }
    return result;
  }
  
	/**
	 * 부모타입으로 자식 타입,코드  값반환
	 * @return
	 */
	public String getSubTypeCodeVal(ArrayList arrType, int piType) {
		IssueCodeBean isb = null;
		String typeCode="";
		
		for (int i=0; i < arrType.size(); i++) {
			isb = (IssueCodeBean) arrType.get(i);
			if (isb.getIc_ptype() == piType) {
				typeCode = isb.getIc_type()+","+isb.getIc_code();
				break;
			}
		}
		return typeCode;
	}
  /**
   * 해당 타입으로  존재하는 타입,코드  값반환
   * @return
   */
  public String getTypeCodeValByKey(ArrayList arrType, int key) {
    IssueCodeBean isb = null;
    String typeCode="";

    for (int i=0; i < arrType.size(); i++) {
      isb = (IssueCodeBean) arrType.get(i);
      if (isb.getIc_seq() == key) {
        typeCode = isb.getIc_type()+","+isb.getIc_code();
        break;
      }
    }
    return typeCode;
  }

  /**
   * 코드로 코드명을 반환
   * @return
   */
  public String GetCodeName(ArrayList arrType, int piCode) {
    IssueCodeBean isb = null;
    String codeName="";

    for (int i=0; i < arrType.size(); i++) {
      isb = (IssueCodeBean) arrType.get(i);
      if (isb.getIc_code() == piCode) {
        codeName = isb.getIc_name();
        break;
      }
    }
    return codeName;
  }

  /**
   * 타입으로 코드명을 반환
   * @return
   */ 
  public String GetCodeNameType(ArrayList arrType, int piType) {
    IssueCodeBean isb = null;
    String codeName="";
    if(arrType != null){ 
      for (int i=0; i < arrType.size(); i++) {
        isb = (IssueCodeBean) arrType.get(i);
        if (isb.getIc_type() == piType) {
          codeName = isb.getIc_name();				
        }
      }
    }
    return codeName;
  }

  /**
   * 타입,코드로 코드명을 반환
   * @return
   */ 
  public String getIssueCodeName(int type, int code) {
    String codeName = "";
    ArrayList eachType = null;
    IssueCodeBean icb = null;

    for(int i=0 ; i<this.arrAllType.size() ; i++){
      eachType = (ArrayList)this.arrAllType.get(i);

      for(int j=0 ; j<eachType.size() ; j++){
        icb = (IssueCodeBean)eachType.get(j);

        if(icb.getIc_type() == type && icb.getIc_code() == code){
          codeName = icb.getIc_name();
        }
      }
    }
    return codeName;
  }

  /**
   * 이슈 분류의 타이틀을 반환
   * */
  public String getCodeTitle() {
    IssueCodeBean isb = null;
    String codeName="";
    ArrayList arrlist = new ArrayList();

    for (int i=0; i < arrAllType.size(); i++) {
      arrlist = (ArrayList) arrAllType.get(i);

      if(codeName.equals("")){
        codeName = GetCodeName(arrlist,0);
      }else{
        codeName += ","+GetCodeName(arrlist,0);
      }

    }

    return codeName;
  }

  /**
   * 이슈 코드의 전체 카운트를 반환한다.
   * @return
   */
  public int getTypeCount() {	return arrAllType.size();	}


  public int getLastCodePosition(ArrayList arData, int type){
    IssueCodeBean isb = null;
    int result = 0;
    for(int i = 0; i < arData.size(); i++){
      isb = (IssueCodeBean)arData.get(i);
      if(isb.getIc_type() == type ){
        result = isb.getIc_code();
        break;
      }
    }
    return result;
  }


  //2014.02.25 추가
  public ArrayList getSearchIssueCode(String pType, String pCode) {
    return getSearchIssueCode("",pType, pCode);
  }
  //2014.02.25 추가
  public ArrayList getSearchIssueCode(String type, String pType, String pCode) {


    ArrayList result = new ArrayList();
    IssueCodeBean icb; 

    DBconn  conn  = null;
    PreparedStatement pstmt = null;
    Statement stmt  = null;
    ResultSet rs = null;
    StringBuffer sb = null;
    
    try {
      conn  = new DBconn();
      conn.getDBCPConnection();
      sb = new StringBuffer();
      stmt = conn.createStatement();

      sb.append(" SELECT IC_TYPE					\n");
      sb.append("      , IC_CODE					\n");
      sb.append("      , IC_NAME 					\n");
      sb.append("   FROM ISSUE_CODE		 			\n");
      sb.append("  WHERE IC_CODE > 0				\n");
      if(!type.equals("")){
        sb.append("    AND IC_TYPE = "+type+"		\n");
      }
      sb.append("    AND IC_PTYPE = "+pType+" 		\n");
      sb.append("    AND IC_PCODE IN ("+pCode+")	\n");
      sb.append("    AND IC_USEYN = 'Y' 			\n");
	  sb.append("    ORDER BY IC_ORDER			\n");
    
      //고객사 요청 2020.11.17 회사구분2,3(IC_TYPE - 2,3. IC_PTYPE = 1,2 )/제품군2,3 (IC_TYPE - 12,13, IC_PTYPE = 11,12)
/*    if("1".equals(pType) || "2".equals(pType) || "11".equals(pType) || "12".equals(pType)){
    	  sb.append("    ORDER BY IC_NAME, IC_ORDER			\n");
      }else{
    	  sb.append("    ORDER BY IC_ORDER			\n");
      }		*/
      /*
       	 	if(pType.equals("4") && pCode.equals("2")){
       	 		sb.append(" SELECT IC_TYPE, IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0 AND IC_PTYPE = 5	\n");
       	 	}else{
       	 		sb.append(" SELECT IC_TYPE, IC_CODE, IC_NAME FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0 AND IC_PTYPE = "+pType+" AND IC_PCODE = "+pCode+"	\n");
       	 	}
       */

      Log.debug(sb.toString() );
      rs = stmt.executeQuery(sb.toString());
      System.out.println(sb.toString());

      while(rs.next()) {

        icb = new IssueCodeBean();
        icb.setIc_type(rs.getInt("IC_TYPE"));
        icb.setIc_code(rs.getInt("IC_CODE"));
        icb.setIc_name(rs.getString("IC_NAME"));
        result.add(icb);

      } // End while

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

  public ArrayList GetTypeOrderName(int type) {
    ArrayList result = new ArrayList();
    IssueCodeBean icb; 

    DBconn  conn  = null;
    PreparedStatement pstmt = null;
    Statement stmt  = null;
    ResultSet rs = null;
    StringBuffer sb = null;

    try {
      conn  = new DBconn();
      conn.getDBCPConnection();
      sb = new StringBuffer();
      stmt = conn.createStatement();

      sb.append(" SELECT IC_TYPE, IC_CODE, IC_NAME, IC_PTYPE, IC_PCODE FROM ISSUE_CODE WHERE IC_TYPE = "+type+" AND IC_CODE > 0 ORDER BY IC_NAME	\n");


      Log.debug(sb.toString() );
      rs = stmt.executeQuery(sb.toString());

      while(rs.next()) {

        if(rs.getInt("IC_PTYPE") == 4){
          if(rs.getInt("IC_PCODE") != 3){
            icb = new IssueCodeBean();
            icb.setIc_type(rs.getInt("IC_TYPE"));
            icb.setIc_code(rs.getInt("IC_CODE"));
            icb.setIc_name(rs.getString("IC_NAME"));

            icb.setIc_ptype(rs.getInt("IC_PTYPE"));
            icb.setIc_pcode(rs.getInt("IC_PCODE"));
            result.add(icb);
          }
        } else {
          icb = new IssueCodeBean();
          icb.setIc_type(rs.getInt("IC_TYPE"));
          icb.setIc_code(rs.getInt("IC_CODE"));
          icb.setIc_name(rs.getString("IC_NAME"));

          icb.setIc_ptype(rs.getInt("IC_PTYPE"));
          icb.setIc_pcode(rs.getInt("IC_PCODE"));
          result.add(icb);
        }

      } // End while

      rs.beforeFirst();

      while(rs.next()) {

        if(rs.getInt("IC_PTYPE") == 4){
          if(rs.getInt("IC_PCODE") == 3){
            icb = new IssueCodeBean();
            icb.setIc_type(rs.getInt("IC_TYPE"));
            icb.setIc_code(rs.getInt("IC_CODE"));
            icb.setIc_name(rs.getString("IC_NAME"));

            icb.setIc_ptype(rs.getInt("IC_PTYPE"));
            icb.setIc_pcode(rs.getInt("IC_PCODE"));
            result.add(icb);
          }
        }
      } // End while

      //	        while(rs.next()) {
      //        	
      //					icb = new IssueCodeBean();
      //					icb.setIc_type(rs.getInt("IC_TYPE"));
      //					icb.setIc_code(rs.getInt("IC_CODE"));
      //					icb.setIc_name(rs.getString("IC_NAME"));
      //					
      //					icb.setIc_ptype(rs.getInt("IC_PTYPE"));
      //					icb.setIc_pcode(rs.getInt("IC_PCODE"));
      //					result.add(icb);
      //    	} // End while


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
  public ArrayList GetPCode(ArrayList arData, String[] codes) {

    ArrayList arrEachType = new ArrayList();
    IssueCodeBean icBean = null;
    String pCode = "";
    StringUtil su = new StringUtil();

    //code의 ptype을 찾는다.
    for(int i = 0; i < arData.size(); i++){
      icBean = (IssueCodeBean)arData.get(i);


      for(int j = 0; j < codes.length; j++){

        if(icBean.getIc_code() == Integer.parseInt(codes[j].split(",")[1])){

          if(pCode.equals("")){
            pCode = String.valueOf(icBean.getIc_pcode());
          }else{
            pCode += "," + String.valueOf(icBean.getIc_pcode());
          }

        }
      }

    }

    //해당 ptype만 따로 걸러낸다.
    String[] ar_pCode = null; 
    for(int i = 0; i < arData.size(); i++){
      icBean = (IssueCodeBean)arData.get(i);
      if(i == 0){
        arrEachType.add(icBean);
      }else{

        ar_pCode = pCode.split(",");


        //배열 중복제거
        ar_pCode = su.getUniqeArray(ar_pCode);

        for(int j = 0; j < ar_pCode.length; j++){

          if(icBean.getIc_pcode() == Integer.parseInt(ar_pCode[j])){
            arrEachType.add(icBean); 
          }
        }

      }

    }
    return arrEachType;
  }

  //2014.02.25 추가
  public ArrayList GetPCode(ArrayList arData, int code) {

    ArrayList arrEachType = new ArrayList();
    IssueCodeBean icBean = null;
    int pCode = 0;

    //code의 ptype을 찾는다.
    for(int i = 0; i < arData.size(); i++){
      icBean = (IssueCodeBean)arData.get(i);

      if(icBean.getIc_code() == code){
        pCode = icBean.getIc_pcode();
        break;
      }
    }


    //해당 ptype만 따로 걸러낸다.
    for(int i = 0; i < arData.size(); i++){
      icBean = (IssueCodeBean)arData.get(i);
      if(i == 0){
        arrEachType.add(icBean);
      }else{
        if(icBean.getIc_pcode() == pCode){
          arrEachType.add(icBean); 
        }
      }

    }
    return arrEachType;
  }

  public ArrayList getOptionCode(String ptype , String pcode){
	  ArrayList result = new ArrayList();
	  IssueCodeBean icb = null;
	  DBconn  conn  = null;
	  PreparedStatement pstmt = null;
	  Statement stmt  = null;
	  ResultSet rs = null;
	  StringBuffer sb = null;

	  try {
	      conn  = new DBconn();
	      conn.getDBCPConnection();
	      sb = new StringBuffer();
	      stmt = conn.createStatement();
	      sb.append(" SELECT IC_TYPE, IC_CODE, IC_NAME, IC_PTYPE, IC_PCODE FROM ISSUE_CODE WHERE IC_PTYPE = "+ptype+" AND IC_PCODE = "+pcode+" AND IC_USEYN='Y' ORDER BY IC_CODE DESC	\n");
	      Log.debug(sb.toString() );
	      rs = stmt.executeQuery(sb.toString());
	      while(rs.next()) {
	    	  icb = new IssueCodeBean();
	          icb.setIc_type(rs.getInt("IC_TYPE"));
	          icb.setIc_code(rs.getInt("IC_CODE"));
	          icb.setIc_name(rs.getString("IC_NAME"));
              icb.setIc_ptype(rs.getInt("IC_PTYPE"));
	          icb.setIc_pcode(rs.getInt("IC_PCODE"));
	          result.add(icb);
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

  public ArrayList getMainIssue(int piNowpage, int piRowCnt, String psSearchWord) {


	    ArrayList result = new ArrayList();
	    IssueCodeBean icb; 
	    
	    int liststart;
     	int listend;
     	
     	if (piNowpage == 0) {
     		piNowpage = 1;
     	}
     	
     	liststart = (piNowpage-1) * piRowCnt;
     	listend = piNowpage * piRowCnt;

	    DBconn  conn  = null;
	    PreparedStatement pstmt = null;
	    Statement stmt  = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;

	    

	    try {
	      conn  = new DBconn();
	      conn.getDBCPConnection();
	      sb = new StringBuffer();
	      stmt = conn.createStatement();

	      sb.append("SELECT IC_SEQ, IC_NAME, IC_DISPYN\n");
	      sb.append("FROM ISSUE_CODE\n");
	      sb.append("WHERE IC_TYPE = 12\n");
	      sb.append("AND   IC_CODE >  0\n");
	      if( !psSearchWord.equals("")) {
	    	  sb.append("AND IC_NAME LIKE '%"+psSearchWord+"%'\n");
	      }
	      sb.append(" LIMIT   "+liststart+","+listend+"    \n");
	      Log.debug(sb.toString() );
	      rs = stmt.executeQuery(sb.toString());


	      while(rs.next()) {

	        icb = new IssueCodeBean();
	        icb.setIc_seq(rs.getInt(		"IC_SEQ"	));
	        icb.setIc_name(rs.getString(		"IC_NAME"	));
	        icb.setIc_dispyn( rs.getString(		"IC_DISPYN"	));

	        result.add(icb);

	      } // End while

	    } catch(SQLException ex) {
	      Log.writeExpt(ex, sb.toString() );
	    } catch(Exception ex) {
	      Log.writeExpt(ex);
	    } finally {
	    	sb = null;
	      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	      if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
	      if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	    }

	    return result;

	  }
  
	public int mainIssueCount( String psSearchWord )
	{
		int reCount = 0;
		DBconn  conn  = null;
	    PreparedStatement pstmt = null;
	    Statement stmt  = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;
		try {
			conn  = new DBconn();
			conn.getDBCPConnection();
		      sb = new StringBuffer();
		      stmt = conn.createStatement();
			sb.append(" 	 SELECT COUNT(1) AS ACOUNT     \n");
			sb.append(" 	 FROM ISSUE_CODE     \n");			
			sb.append(" 	 WHERE IC_TYPE = 12     \n");
			sb.append("      AND   IC_CODE >  0\n");
			if( !psSearchWord.equals("")) {
				sb.append("  AND IC_NAME LIKE '%"+psSearchWord+"%'     \n");
			}
	        
		    rs = stmt.executeQuery(sb.toString());
	        		        
	        if(rs.next())
	        {
	        	reCount = rs.getInt("ACOUNT");
	        }
			
		}catch (SQLException ex) {
		  Log.writeExpt(ex);
		}catch (Exception ex) {
        Log.writeExpt(ex);
		} finally {
			sb = null;
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		      if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		      if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
    return reCount;
	}
	
	public boolean updateMainIssueDisplay(IssueCodeBean icBean)
	{	
		boolean result = false;
		DBconn  conn  = null;
	    PreparedStatement pstmt = null;
	    Statement stmt  = null;
	    ResultSet rs = null;
	    StringBuffer sb = null;		
		try{
			conn = new DBconn();
			conn.getDBCPConnection();
			stmt = conn.createStatement();
			
			sb = new StringBuffer();   
			sb.append("UPDATE ISSUE_CODE SET IC_DISPYN = '"+icBean.getIc_dispyn()+"'   WHERE IC_SEQ IN ("+icBean.getIc_seq()+")     \n");			
			if(stmt.executeUpdate(sb.toString())>0) result = true;																				
			
		} catch(SQLException ex) {
			Log.writeExpt(ex, sb.toString() );
		} catch(Exception ex) {
			Log.writeExpt(ex);
		} finally {
		    sb = null;
		    if (rs != null) try { rs.close(); } catch(SQLException ex) {}
		      if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
		      if (conn != null) try { conn.close(); } catch(SQLException ex) {}          
		}        
		return result;
	}	
	
	  public ArrayList getParentIssueCode(String type, String code) {


		    ArrayList result = new ArrayList();
		    IssueCodeBean icb; 

		    DBconn  conn  = null;
		    PreparedStatement pstmt = null;
		    Statement stmt  = null;
		    ResultSet rs = null;
		    StringBuffer sb = null;

		    try {
		      conn  = new DBconn();
		      conn.getDBCPConnection();
		      sb = new StringBuffer();
		      stmt = conn.createStatement();

		      sb.append(" SELECT IC_PTYPE					\n");
		      sb.append("      , IC_PCODE					\n");
		      sb.append("   FROM ISSUE_CODE		 			\n");
		      sb.append("  WHERE IC_CODE > 0				\n");
		      sb.append("    AND IC_TYPE = "+type+" 		\n");
		      sb.append("    AND IC_CODE IN ("+code+")		\n");
		      sb.append("    AND IC_USEYN = 'Y' 			\n");

		      Log.debug(sb.toString() );
		      rs = stmt.executeQuery(sb.toString());

		      while(rs.next()) {

		        icb = new IssueCodeBean();
		        icb.setIc_ptype(rs.getInt("IC_TYPE"));
		        icb.setIc_pcode(rs.getInt("IC_CODE"));
		        result.add(icb);

		      } // End while

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
	  
	  public boolean insertIssueCode(String m_seq, String keyword, String ic_type){
			boolean chk = false;
			int maxIc_code = 0;
			int ic_order = 1;
			DBconn  conn  = null;
	        Statement stmt  = null;
	        ResultSet rs = null;
	        StringBuffer sb = null;
			try{
				conn = new DBconn();
				conn.getDBCPConnection();
				stmt = conn.createStatement();
				
				sb = new StringBuffer();
				sb.append("SELECT IFNULL(MAX(IC_CODE),0) AS MAX_CODE, IFNULL(MAX(IC_ORDER),0)  AS IC_ORDER	\n");
				sb.append("		FROM ISSUE_CODE 						\n");
				sb.append("     WHERE IC_TYPE = "+ic_type+"						\n");
				
				System.out.println(sb.toString());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
				rs = stmt.executeQuery(sb.toString());
				
				if(rs.next()){
					maxIc_code = rs.getInt("MAX_CODE");
					ic_order = rs.getInt("IC_ORDER") + 1;
					chk = true;
				}
				
				sb = new StringBuffer();
				
				if(chk!=false && keyword!=null){
					sb.append("INSERT INTO ISSUE_CODE ( 																					\n");
					sb.append("		IC_NAME, IC_TYPE, IC_CODE, IC_PTYPE, IC_PCODE, IC_REGDATE, M_SEQ, IC_DESCRIPTION, IC_USEYN, IC_ORDER, IC_DISPYN ) 	\n");
					sb.append("		VALUES	(																								\n");
					sb.append("		'"+keyword+"', "+ic_type+", "+(maxIc_code+1)+", 0, 0, NOW(), "+m_seq+", '', 'Y', "+ic_order+", 'Y'								\n");
					sb.append("		)																										\n");
					System.out.println(sb.toString());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
			
				}
				                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
				if(stmt.executeUpdate(sb.toString())>0){
					chk = true;
				}else{
					chk= false;
				}
				
			}catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
				if (conn != null) try { conn.close(); } catch(SQLException ex) {}
			}
			
			return chk;
		}
	  
	  public ArrayList getIssueCode(String m_seq, int ic_type){

			int maxIc_code = 0;
			DBconn  conn  = null;
	        Statement stmt  = null;
	        ResultSet rs = null;
	        StringBuffer sb = null;

	        IssueCodeBean icBean = null;
	        ArrayList result = new ArrayList();
	        try{
				conn = new DBconn();
				conn.getDBCPConnection();
				stmt = conn.createStatement();
				
				sb = new StringBuffer();
				sb.append("SELECT IC_NAME, IC_CODE 				\n");
				sb.append("		FROM ISSUE_CODE					\n");
				sb.append("		WHERE IC_TYPE="+ic_type +"		\n");
				
				System.out.println(sb.toString());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
					icBean = new IssueCodeBean();
					icBean.setIc_name(rs.getString("IC_NAME"));
					icBean.setIc_code(rs.getInt("IC_CODE"));
					result.add(icBean);
				}
						
				
			}catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
				if (conn != null) try { conn.close(); } catch(SQLException ex) {}
			}
			
			return result;
		}
	  
	  public ArrayList getIssueCode(String ic_type, String sort){
		  return getIssueCode(ic_type, "", sort);
	  }
	  
	  public ArrayList getIssueCode(String ic_type, String ic_code, String sort){

			DBconn  conn  = null;
	        Statement stmt  = null;
	        ResultSet rs = null;
	        StringBuffer sb = null;

	        IssueCodeBean icBean = null;
	        ArrayList result = new ArrayList();
	        try{
				conn = new DBconn();
				conn.getDBCPConnection();
				stmt = conn.createStatement();
				
				sb = new StringBuffer();
				sb.append("	SELECT IC_NAME, IC_TYPE, IC_CODE, IC_REGDATE 		\n");
				sb.append("	FROM ISSUE_CODE										\n");
				sb.append("	WHERE IC_TYPE = "+ic_type+"							\n");
				if(!"".equals(ic_code)){
					sb.append("	AND IC_CODE = "+ic_code+"						\n");	
				} else {
					sb.append("	AND IC_CODE > 0									\n");
				}
				sb.append("	AND IC_USEYN = 'Y'									\n");
				if(!"".equals(sort)){
					sb.append("	ORDER BY "+sort+"								\n");	
				}
				
				System.out.println(sb.toString());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()){
					icBean = new IssueCodeBean();
					icBean.setIc_name(rs.getString("IC_NAME"));
					icBean.setIc_type(rs.getInt("IC_TYPE"));
					icBean.setIc_code(rs.getInt("IC_CODE"));
					icBean.setIc_regdate(rs.getString("IC_REGDATE"));
					result.add(icBean);
				}
				
			}catch(SQLException ex) {
				Log.writeExpt(ex,ex.getMessage());
			} catch(Exception ex) {
				Log.writeExpt(ex);
			} finally {
				if (rs != null) try { rs.close(); } catch(SQLException ex) {}
				if (stmt != null) try { stmt.close(); } catch(SQLException ex) {}
				if (conn != null) try { conn.close(); } catch(SQLException ex) {}
			}
			
			return result;
		}
}