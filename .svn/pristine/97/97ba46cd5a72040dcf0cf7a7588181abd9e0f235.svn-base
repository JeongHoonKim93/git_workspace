 package risk.tree;
 
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.util.ArrayList;
 import risk.DBconn.DBconn;
 import risk.admin.classification.clfBean;
 import risk.util.DateUtil;
 import risk.util.Log;
 import risk.util.StringUtil;
 

 
 public class TreeDao {
    private TreeBean treeBean = null;
    private DBconn dbconn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private StringBuffer sb = null;
    private StringUtil su = new StringUtil();
    private DateUtil du = new DateUtil();
    public String msMinNo = "";
    public String msMaxNo = "";
 
    public void getMaxMinNo(String psSDate, String psEdate) {
       try {
          this.dbconn = new DBconn();
          this.dbconn.getDBCPConnection();
 
          this.sb = new StringBuffer();
          this.sb.append(" SELECT (SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '" + psSDate + " 00:00:00' AND '" + psSDate + " 23:59:59' ORDER BY MD_DATE ASC LIMIT 1) MIN_NO \n");
          this.sb.append("        ,(SELECT MD_SEQ FROM META WHERE MD_DATE BETWEEN '" + psEdate + " 00:00:00' AND '" + psEdate + " 23:59:59' ORDER BY MD_DATE DESC LIMIT 1) MAX_NO \n");
 
          this.stmt = this.dbconn.createStatement();
          this.rs = this.stmt.executeQuery(this.sb.toString());
 
          if (this.rs.next()) {
             this.msMinNo = this.rs.getString("MIN_NO");
             this.msMaxNo = this.rs.getString("MAX_NO");
 
             if (this.msMinNo == null || this.msMinNo.equals("null")) {
                this.msMinNo = "0";
             }
             if (this.msMaxNo == null || this.msMaxNo.equals("null")) {
                this.msMaxNo = "999999999";
             }
          }
       } catch (SQLException var24) {
          Log.writeExpt(var24, this.sb.toString());
 
       } catch (Exception var25) {
          Log.writeExpt(var25);
       } finally {
          try {
             if (this.rs != null) {               this.rs.close();            }         } catch (SQLException var23) {            Log.writeExpt(var23);         }         try {
             if (this.stmt != null) {               this.stmt.close();            }         } catch (SQLException var22) {            Log.writeExpt(var22);         }         try {
             if (this.dbconn != null) {
                this.dbconn.close();            }         } catch (SQLException var21) {            Log.writeExpt(var21);         }      }
    }
    
    public ArrayList getKeywordTreeData(String queryId, String level, String arg1, String arg2) {
       return this.getKeywordTreeData(queryId, level, arg1, arg2, "", "");
    }
 

 
    public ArrayList getKeywordTreeData(String queryId, String level, String arg1, String arg2, String arg3, String arg4) {
       ArrayList arrList = new ArrayList();
 
       try {
          this.dbconn = new DBconn();
          this.dbconn.getDBCPConnection();
          this.sb = new StringBuffer();
 
          if (queryId.equals("KEYWORD")) {
 
             if (level.equals("1")) {
                this.sb.append("SELECT K_SEQ AS SEQ                                                                   \n");
                this.sb.append("\t   ,K_XP AS VALUE1                                                            \n");
                this.sb.append("\t   ,K_YP AS VALUE2                                                            \n");
                this.sb.append("\t   ,K_ZP AS VALUE3                                                            \n");
                this.sb.append("\t   ,K_VALUE AS NAME                                                           \n");
                this.sb.append("\t   ,K_OP AS TYPE                                                            \n");
                this.sb.append("\t   ,(SELECT COUNT(*) FROM KEYWORD WHERE K_XP =K.K_XP AND K_YP <> 0) AS CHILDCNT \n");
                this.sb.append("\t   ,0 AS PARENTSEQ                                                            \n");
                this.sb.append("FROM KEYWORD K                                                                        \n");
                this.sb.append("WHERE                                                                                 \n");
                this.sb.append("\t     K_YP = 0\t                                                              \n");
                this.sb.append("\t AND K_TYPE = 1                                                               \n");
                this.sb.append("ORDER BY K_POS                                                                        \n");
             } else if (level.equals("2")) {
                this.sb.append(" SELECT K_SEQ AS SEQ                                                                                      \n");
                this.sb.append(" \t   ,K_XP AS VALUE1                                                                               \n");
                this.sb.append(" \t   ,K_YP AS VALUE2                                                                               \n");
                this.sb.append(" \t   ,K_ZP AS VALUE3                                                                               \n");
                this.sb.append(" \t   ,K_VALUE AS NAME                                                                              \n");
                this.sb.append(" \t   ,K_OP AS TYPE                                                                               \n");
                this.sb.append(" \t   ,(SELECT COUNT(*) FROM KEYWORD WHERE K_XP =K.K_XP AND K_YP=K.K_YP AND K_TYPE =2) AS CHILDCNT  \n");
                this.sb.append(" \t   ,0 AS PARENTSEQ         \n");
                this.sb.append(" FROM KEYWORD K                                                                                           \n");
                this.sb.append(" WHERE                                                                                                    \n");
                this.sb.append(" \t K_XP IN (" + arg1 + ")                                                                                     \n");
                this.sb.append(" \t AND K_YP <> 0\t                                                                                 \n");
                this.sb.append(" \t AND K_TYPE = 1                                                                                  \n");
                this.sb.append(" ORDER BY K_VALUE ASC                                                                                     \n");
             } else if (level.equals("3")) {
                this.sb.append(" SELECT K_SEQ AS SEQ                                                                                   \n");
                this.sb.append(" \t   ,K_XP AS VALUE1                                                                            \n");
                this.sb.append(" \t   ,K_YP AS VALUE2                                                                            \n");
                this.sb.append(" \t   ,K_ZP AS VALUE3                                                                            \n");
                this.sb.append(" \t   ,K_VALUE AS NAME                                                                           \n");
                this.sb.append(" \t   ,K_OP AS TYPE                                                                            \n");
                this.sb.append(" \t   ,'0' AS CHILDCNT                                                                           \n");
                this.sb.append(" \t   ,0 AS PARENTSEQ \n");
                this.sb.append(" FROM KEYWORD K                                                                                        \n");
                this.sb.append(" WHERE                                                                                                 \n");
                this.sb.append(" \t  K_XP IN (" + arg1 + ")                                                                                  \n");
                this.sb.append(" \t AND K_YP = " + arg2 + "                                                                                 \n");
                this.sb.append(" \t AND K_TYPE = 2                                                                               \n");
                this.sb.append(" ORDER BY K_VALUE ASC                                                                                  \n");
             }
          } else if (queryId.equals("SEARCHKEYWORD")) {
 
             if (level.equals("1")) {
                this.sb.append(" SELECT B1.K_SEQ AS SEQ                                                               \n");
                this.sb.append("        ,B1.K_XP AS VALUE1                                                             \n");
                this.sb.append("        ,B1.K_YP AS VALUE2                                                             \n");
                this.sb.append("        ,B1.K_ZP AS VALUE3                                                             \n");
                this.sb.append("        ,B1.K_VALUE AS NAME                                                            \n");
                this.sb.append("        ,B1.K_OP AS TYPE                                                               \n");
                this.sb.append("        ,IFNULL(B2.CNT,0) AS VALUE4                                                    \n");
                this.sb.append("        ,(SELECT COUNT(*) FROM KEYWORD WHERE K_XP =B1.K_XP AND K_YP<>0) AS CHILDCNT    \n");
                this.sb.append("        ,0 AS PARENTSEQ                                                                \n");
                this.sb.append(" FROM                                                                                  \n");
                this.sb.append(" KEYWORD B1 LEFT OUTER JOIN                                                                 \n");
                this.sb.append(" (                                                                                     \n");
                this.sb.append(" \tSELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                       \n");
                this.sb.append(" \tFROM META A1,                                                                 \n");
                this.sb.append(" \t(                                                                             \n");
                this.sb.append(" \t\tSELECT DISTINCT K_XP, 0 AS K_YP, 0 AS K_ZP, MD_SEQ                    \n");
                this.sb.append(" \t\tFROM IDX                                                              \n");
                this.sb.append(" \t\tWHERE MD_SEQ BETWEEN  " + this.msMinNo + " AND  " + this.msMaxNo + "                             \n");
                this.sb.append(" \t\tAND I_STATUS = 'N'                                               \t\n");
                this.sb.append(" \t) A2                                                                          \n");
                this.sb.append(" \tWHERE A1.MD_SEQ=A2.MD_SEQ                                                       \n");
                this.sb.append(" \tGROUP BY K_XP                                                                 \n");
                this.sb.append(" ) B2 ON B1.K_XP=B2.K_XP AND B1.K_YP=B2.K_YP AND B1.K_ZP=B2.K_ZP                       \n");
                this.sb.append(" WHERE B1.K_TYPE = 1   AND B1.K_XP IN (" + arg1 + ") AND B1.K_YP=0  AND B1.K_USEYN = 'Y'           \n");
                this.sb.append(" ORDER BY B1.K_POS, B1.K_XP, B1.K_YP, B1.K_ZP ASC                                      \n");
             } else if (level.equals("2")) {
                this.sb.append(" SELECT B1.K_SEQ AS SEQ                                                                               \n");
                this.sb.append("        ,B1.K_XP AS VALUE1                                                                              \n");
                this.sb.append("        ,B1.K_YP AS VALUE2                                                                              \n");
                this.sb.append("        ,B1.K_ZP AS VALUE3                                                                              \n");
                this.sb.append("        ,B1.K_VALUE AS NAME                                                                             \n");
                this.sb.append("        ,B1.K_OP AS TYPE                                                                                \n");
                this.sb.append("        ,IFNULL(B2.CNT,0) AS VALUE4                                                                     \n");
                this.sb.append("        ,(SELECT COUNT(*) FROM KEYWORD WHERE K_XP =B1.K_XP AND K_YP=B1.K_YP AND K_TYPE =2) AS CHILDCNT    \n");
                this.sb.append("        ,0 AS PARENTSEQ           \n");
                this.sb.append("  FROM                                                                                                  \n");
                this.sb.append("  KEYWORD B1 LEFT OUTER JOIN                                                                                 \n");
                this.sb.append("  (                                                                                                     \n");
                this.sb.append("  \t   SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                                     \n");
                this.sb.append("      FROM META A1,                                                                                     \n");
                this.sb.append("        (                                                                                               \n");
                this.sb.append("          SELECT DISTINCT K_XP, K_YP, 0 AS K_ZP, MD_SEQ                                                 \n");
                this.sb.append("          FROM IDX                                                                                      \n");
                this.sb.append("          WHERE MD_SEQ BETWEEN  " + this.msMinNo + " AND  " + this.msMaxNo + "                                                     \n");
                this.sb.append("          \t  AND I_STATUS = 'N'                                               \t\t\t\n");
                this.sb.append("        ) A2                                                                                            \n");
                this.sb.append("      WHERE A1.MD_SEQ=A2.MD_SEQ                                                                         \n");
                this.sb.append("      GROUP BY K_XP, K_YP                                                                               \n");
                this.sb.append("  \t) B2 ON B1.K_XP=B2.K_XP AND B1.K_YP=B2.K_YP AND B1.K_ZP=B2.K_ZP                                \n");
                this.sb.append("  \tWHERE B1.K_TYPE = 1   AND B1.K_XP IN (" + arg1 + ") AND B1.K_YP >0 AND B1.K_ZP = 0   AND B1.K_USEYN = 'Y'                    \n");
                this.sb.append("  ORDER BY B1.K_VALUE ASC                                                                               \n");
             } else if (level.equals("3")) {
                this.sb.append(" SELECT B1.K_SEQ AS SEQ                                                                           \n");
                this.sb.append("        ,B1.K_XP AS VALUE1                                                                         \n");
                this.sb.append("        ,B1.K_YP AS VALUE2                                                                         \n");
                this.sb.append("        ,B1.K_ZP AS VALUE3                                                                         \n");
                this.sb.append("        ,B1.K_VALUE AS NAME                                                                        \n");
                this.sb.append("        ,B1.K_OP AS TYPE                                                                           \n");
                this.sb.append("        ,IFNULL(B2.CNT,0) AS VALUE4                                                                \n");
                this.sb.append("        ,0 AS CHILDCNT                                                                           \n");
                this.sb.append("        ,0 AS PARENTSEQ \n");
                this.sb.append("  FROM                                                                                             \n");
                this.sb.append("  KEYWORD B1 LEFT OUTER JOIN                                                                            \n");
                this.sb.append("  (                                                                                                \n");
                this.sb.append("  \t SELECT K_XP, K_YP, K_ZP, COUNT(DISTINCT MD_PSEQ) AS CNT                                \n");
                this.sb.append("      FROM META A1,                                                                                \n");
                this.sb.append("        (                                                                                          \n");
                this.sb.append("          SELECT DISTINCT K_XP, K_YP, K_ZP, MD_SEQ                                                 \n");
                this.sb.append("          FROM IDX                                                                                 \n");
                this.sb.append("          WHERE MD_SEQ BETWEEN  " + this.msMinNo + " AND  " + this.msMaxNo + "                                                \n");
                this.sb.append("          \t  AND I_STATUS = 'N'                                               \t\t\t\n");
                this.sb.append("        ) A2                                                                                       \n");
                this.sb.append("      WHERE A1.MD_SEQ=A2.MD_SEQ                                                                 \n");
                this.sb.append("      GROUP BY K_XP, K_YP, K_ZP                                                                    \n");
                this.sb.append("  \t) B2 ON B1.K_XP=B2.K_XP AND B1.K_YP=B2.K_YP AND B1.K_ZP=B2.K_ZP                           \n");
                this.sb.append("  \tWHERE B1.K_TYPE = 2   AND B1.K_XP IN (" + arg1 + ") AND B1.K_YP IN (" + arg2 + ")   AND B1.K_USEYN = 'Y'               \n");
                this.sb.append("  ORDER BY B1.K_VALUE ASC                                                                          \n");
 
             }
          }
 
          System.out.println(this.sb.toString());
          this.stmt = this.dbconn.createStatement();
          for(this.rs = this.stmt.executeQuery(this.sb.toString()); this.rs.next(); arrList.add(this.treeBean)) {
 
 
 
 
             this.treeBean = new TreeBean();
             if (queryId.equals("KEYWORD")) {
 
                this.treeBean.setSeq(this.rs.getString("SEQ"));
                this.treeBean.setValue1(this.rs.getString("VALUE1"));
                this.treeBean.setValue2(this.rs.getString("VALUE2"));
                this.treeBean.setValue3(this.rs.getString("VALUE3"));
                this.treeBean.setName(this.rs.getString("NAME"));
                this.treeBean.setType(this.rs.getString("TYPE"));
                this.treeBean.setChildCount(this.rs.getString("CHILDCNT"));
                this.treeBean.setParentSeq(this.rs.getString("PARENTSEQ"));
 
             } else if (queryId.equals("SEARCHKEYWORD")) {
 
                this.treeBean.setSeq(this.rs.getString("SEQ"));
                this.treeBean.setValue1(this.rs.getString("VALUE1"));
                this.treeBean.setValue2(this.rs.getString("VALUE2"));
                this.treeBean.setValue3(this.rs.getString("VALUE3"));
                this.treeBean.setValue4(this.rs.getString("VALUE4"));
                this.treeBean.setName(this.rs.getString("NAME"));
                this.treeBean.setType(this.rs.getString("TYPE"));
                this.treeBean.setChildCount(this.rs.getString("CHILDCNT"));
                this.treeBean.setParentSeq(this.rs.getString("PARENTSEQ"));
 
 
 
             }
          }
       } catch (SQLException var29) {
          Log.writeExpt(var29, this.sb.toString());
       } catch (Exception var30) {
          Log.writeExpt(var30);
       } finally {
          if (this.rs != null) {            try {               this.rs.close();            } catch (SQLException var28) {               ;            }         }
          if (this.stmt != null) {            try {               this.stmt.close();            } catch (SQLException var27) {               ;            }         }
          if (this.dbconn != null) {
             try {               this.dbconn.close();            } catch (SQLException var26) {               ;            }         }      }
 
       return arrList;
    }
 
 
    public ArrayList getCodeData(String type, String  code, String  piType, String  piCode, int level) {
       ArrayList clfList = null;
       StringBuffer sb = null;
 
 
       try {
          this.dbconn = new DBconn();
          this.dbconn.getDBCPConnection();
          this.stmt = this.dbconn.createStatement();
 
          sb = new StringBuffer();
 
          new StringUtil();
          sb.append("SELECT IC_SEQ,\t          \t\t\t\n");
          sb.append("\t\t  IC_NAME,           \t\t\t\n");
          sb.append("\t\t  IC_TYPE,           \t\t\t\n");
          sb.append("\t\t  IC_CODE,           \t\t\t\n");
          sb.append("\t\t  IC_PTYPE,           \t\t\t\n");
          sb.append("\t\t  IC_PCODE,           \t\t\t\n");
          sb.append("\t\t  IC_REGDATE,           \t\t\n");
          sb.append("\t\t  M_SEQ,          \t\t\t\t\n");
          sb.append("\t\t  IC_DESCRIPTION,       \t\t\t\n");
 
          if (level == 1) {
             sb.append(" (SELECT COUNT(*) FROM ISSUE_CODE WHERE IC_USEYN = 'Y' AND IC_TYPE = II.IC_TYPE AND IC_CODE <> 0 )AS CHILDCNT\t\t\n");
          } else if (level == 2) {
             sb.append(" (SELECT COUNT(*) FROM ISSUE_CODE WHERE IC_USEYN = 'Y' AND IC_PTYPE = II.IC_TYPE AND IC_PCODE = II.IC_CODE) AS CHILDCNT\t\t\n");
 
          } else if (level == 3) {
             sb.append(" (SELECT COUNT(*) FROM ISSUE_CODE WHERE IC_USEYN = 'Y' AND IC_PTYPE = II.IC_TYPE AND IC_PCODE = II.IC_CODE) AS CHILDCNT\t\t\n");
          } else if (level == 4) {
             sb.append(" (SELECT COUNT(*) FROM ISSUE_CODE WHERE IC_USEYN = 'Y' AND IC_PTYPE = II.IC_TYPE AND IC_PCODE = II.IC_CODE) AS CHILDCNT\t\t\n");
          } else if (level == 5) {
              sb.append(" (SELECT COUNT(*) FROM ISSUE_CODE WHERE IC_USEYN = 'Y' AND IC_PTYPE = II.IC_TYPE AND IC_PCODE = II.IC_CODE) AS CHILDCNT\t\t\n");
           }
 
          sb.append("FROM ISSUE_CODE II           \t\t\t\n");
          sb.append("WHERE 1=1\t\t\t   \t\t\n");
          sb.append("\t\t AND IC_USEYN='Y'        \t\t\n");
          if (level == 1) {
             sb.append(" AND IC_CODE=0 AND IC_PTYPE=0\t\t\t   \t\t\n");
          } else if (level == 2) {
             sb.append(" AND IC_TYPE=" + type + " AND IC_PTYPE = 0\tAND IC_CODE <> 0\t\t   \t\t\n");
          } else if (level == 3) {
             sb.append(" AND IC_PTYPE=" + type + " AND IC_PCODE = " + code + "  \t\t\n");
          } else if (level == 4) {
             sb.append(" AND IC_PTYPE=" + type + " AND IC_PCODE = " + code + "  \t\t\n");
          } else if (level == 5) {
              sb.append(" AND IC_PTYPE=" + type + " AND IC_PCODE = " + code + "  \t\t\n");
           }
          sb.append("ORDER BY IC_TYPE ASC, IC_ORDER ASC  \n");
 
          System.out.println("-------------" + sb.toString());
 
 
 
          this.rs = this.stmt.executeQuery(sb.toString());
          clfBean clb = null;
 
          clfList = new ArrayList();

  		
          while(this.rs.next()) {
             clfList.add(new clfBean(this.rs.getInt("IC_SEQ"), this.rs.getString("IC_NAME"), this.rs.getInt("IC_TYPE"), this.rs.getInt("IC_CODE"), this.rs.getInt("IC_PTYPE"), this.rs.getInt("IC_PCODE"), 0, this.rs.getString("IC_REGDATE").substring(0,9), this.rs.getString("IC_REGDATE").substring(10,19), this.rs.getInt("M_SEQ"), this.rs.getString("IC_DESCRIPTION"), this.rs.getInt("CHILDCNT")));
 

          }
       } catch (SQLException var30) {
          Log.writeExpt(var30);
       } catch (Exception var31) {
          Log.writeExpt(var31);
       } finally {
          try {
             if (this.rs != null) {               this.rs.close();            }         } catch (SQLException var29) {            Log.writeExpt(var29);         }         try {
             if (this.stmt != null) {               this.stmt.close();            }         } catch (SQLException var28) {            Log.writeExpt(var28);         }         try {
             if (this.dbconn != null) {
                this.dbconn.close();            }         } catch (SQLException var27) {            Log.writeExpt(var27);         }      }
       return clfList;
    }
 }