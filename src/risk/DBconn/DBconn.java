/**
========================================================
주 시 스 템 : RSN
서브 시스템 : db 공통모듈
프로그램 ID : DBConn.class
프로그램 명 : DBConn
프로그램개요 : 데이터베이스 커넥션 연결,트랜잭션 관리
               및 Query 실행
작 성 자 : 윤석준
작 성 일 : 2006. 04. 11
========================================================
수정자/수정일:
수정사유/내역:
========================================================
*/
package risk.DBconn;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import risk.util.Log;


public class DBconn {
  
	
	
    final private String CONST_MYSQL_SUBDB_URL       = "58.180.24.48";
    //final private String CONST_MYSQL_SUBDB_URL       = "localhost";
    final private String CONST_MYSQL_SUBDB_NAME      = "HOTELSHILLA_ASP";
    final private String CONST_MYSQL_SUBDB_USER      = "hotelshilla_asp";
    final private String CONST_MYSQL_SUBDB_PASSWD    = "rsn601";
    
    final private String CONST_MYSQL_LUCY_URL       = "61.110.19.87";
    final private String CONST_MYSQL_LUCY_NAME      = "LUCY_ADMIN";
    final private String CONST_MYSQL_LUCY_USER      = "rdata";
    final private String CONST_MYSQL_LUCY_PASSWD    = "rdata!@#";
    
    public Connection  mConn = null;   
    public boolean      mbConnet = false;
    public boolean      mbTR = false;

    public boolean isConnected() {
        return mbConnet;
    }

    public boolean isTransaction() {
        return mbTR;
    }
    

    public String getCONST_MYSQL_SUBDB_URL() {
    	return CONST_MYSQL_SUBDB_URL;
    }

    /**
     * DB tomcat Connection Pool 
     * @return
     * @throws SQLException
     * @throws Exception
     * @throws ClassNotFoundException
     */
    public void getDBCPConnection() throws SQLException, NamingException{
	       String poolName = "HOTELSHILLA";
	       Context initContext = null;
	       Context envContext = null;
	       DataSource ds = null;
	       Connection conn = null;
	       
	       initContext = new InitialContext();
	       envContext  = (Context)initContext.lookup("java:/comp/env");
	       ds = (DataSource)envContext.lookup(poolName);
	       mConn = ds.getConnection();	      
   }
    
    
    /**
     * Mysql Direct Connection
     * @return
     * @throws SQLException
     * @throws Exception
     * @throws ClassNotFoundException
     */   
    public boolean getSubDirectConnection() throws SQLException,Exception, ClassNotFoundException {
        boolean bRtnValue = false;

        if ( !mbConnet )
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            mConn = DriverManager.getConnection("jdbc:mysql://" + CONST_MYSQL_SUBDB_URL + "/" + CONST_MYSQL_SUBDB_NAME
                                                                 + "?user=" + CONST_MYSQL_SUBDB_USER
                                                                 + "&password=" + CONST_MYSQL_SUBDB_PASSWD 
                                                                 + "&autoReconnect=true&connectTimeout=0&maxReconnects=3");
        }

        if ( mConn != null )
        {
            mbConnet = true;
        }

        bRtnValue = true;
        return bRtnValue;
    }
    
    
    
    /**
     * Mysql Direct Connection
     * @return
     * @throws SQLException
     * @throws Exception
     * @throws ClassNotFoundException
     */   
    public boolean getLucyDirectConnection() throws SQLException,Exception, ClassNotFoundException {
        boolean bRtnValue = false;

        if ( !mbConnet )
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            mConn = DriverManager.getConnection("jdbc:mysql://" + CONST_MYSQL_LUCY_URL + "/" + CONST_MYSQL_LUCY_NAME
                                                                 + "?user=" + CONST_MYSQL_LUCY_USER
                                                                 + "&password=" + CONST_MYSQL_LUCY_PASSWD 
                                                                 + "&autoReconnect=true&connectTimeout=0&maxReconnects=3");
        }

        if ( mConn != null )
        {
            mbConnet = true;
        }

        bRtnValue = true;
        return bRtnValue;
    }

  
    public void close() throws SQLException {

        if ( mConn != null )
        {          
            mConn.close();
            mbConnet = false;
            mConn = null;           
        }
    }

    /**
     * 자료존재여부를 파악하는 쿼리 실행시 사용
     * @param psQuery
     * @return
     * @throws SQLException
     */
    public boolean  exists( String psQuery ) throws SQLException {
        boolean bRtnValue = false;

        Statement stmt = null;
        ResultSet rs = null;

        stmt = mConn.createStatement();
        rs = stmt.executeQuery(psQuery);
        
        if ( rs.next() ) {
            bRtnValue = true;
        } else {
            bRtnValue = false;
        }

        stmt.close();
        rs.close();

        stmt    = null;
        rs  = null;
        return bRtnValue;

    }

    /**
     * 자료존재여부를 파악하는 쿼리 실행시 사용
     * @param popstmt
     * @return
     * @throws SQLException
     */
    public boolean  exists( PreparedStatement popstmt ) throws SQLException {
        boolean bRtnValue = false;

        Statement stmt = null;
        ResultSet rs = null;

        rs = popstmt.executeQuery();

        if ( rs.next() ) {
            bRtnValue = true;
        } else {
            bRtnValue = false;
        }

        rs.close();
        rs  = null;
        return bRtnValue;

    }


    /**
     * 트랜젝션 시작
     * @throws SQLException
     */
    public void TransactionStart() throws SQLException {

        mConn.setAutoCommit( false );
        mbTR = true;   //Tranjaction Start

    }


    /**
     * 트랜젝션 커밋
     * @throws SQLException
     */
    public void Commit() throws SQLException {
        mConn.commit();
        mConn.setAutoCommit( true );
        mbTR = false;  //Tranjaction End
    }

    /**
     *  트랜젝션 종료
     * @throws SQLException
     */
    public void Rollback() throws SQLException {
        mConn.rollback();
        mConn.setAutoCommit( true );
        mbTR = false;  //Tranjaction End
    }


    /**
     *  Statement 생성
     * @return
     * @throws SQLException
     */
    public Statement createStatement() throws SQLException {
        return mConn.createStatement();
    }

    /**
     *  TYPE_SCROLL_INSENSITIVE의 Statement 생성
     * @return
     * @throws SQLException
     */
    public Statement createScrollStatement() throws SQLException {
        return mConn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
    }

    /**
     * PreparedStatement 생성
     * @param psQuery
     * @return
     * @throws SQLException
     */
    public PreparedStatement createPStatement(String psQuery) throws SQLException {
        return mConn.prepareStatement( psQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );
    }

    /**
     * PreparedStatement 생성
     * @param psQuery
     * @return
     * @throws SQLException
     */
    public PreparedStatement createPStatement_return(String psQuery) throws SQLException {
        return mConn.prepareStatement( psQuery, PreparedStatement.RETURN_GENERATED_KEYS );
    }


    /**
     * PreparedStatement로 조회하여 ResultSet  리턴
     * @param pPstmt
     * @return
     * @throws SQLException
     */
    public ResultSet executeQuery( PreparedStatement pPstmt ) throws SQLException {

        ResultSet rs        = null;
        rs = pPstmt.executeQuery();
        return rs;
    }

    /**
     * IUD Query 실행
     * @param psQuery
     * @return
     * @throws SQLException
     */
    public int executeUpdate(String psQuery) throws SQLException {
        int irtnValue = 0;
        Statement stmt = null;
        stmt = mConn.createStatement();
        irtnValue = stmt.executeUpdate(psQuery);
        if (stmt != null) stmt.close();
        return irtnValue;
    }

    /**
     * IUD Query 실행
     * @param pPstmt
     * @return
     * @throws SQLException
     */
    public int executeUpdate(PreparedStatement pPstmt) throws SQLException {
        int irtnValue = 0;
        Statement stmt = null;

        irtnValue = pPstmt.executeUpdate();
        if (stmt != null) stmt.close();
        return irtnValue;
    }

    /**
     * Stored Procedure 실행
     * @param psQuery
     * @return
     * @throws SQLException
     */
    public CallableStatement createCallableStatement(String psQuery ) throws SQLException {
        return mConn.prepareCall(psQuery);
    }

    /**
     * Statement 연결해제
     * @param poSt
     */
    public void realeseStatment( Statement poSt ) {
        try { if( poSt != null)  poSt.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
    }

    /**
     * PreparedStatement 연결해제
     * @param poPst
     */
    public void realesePStatment( PreparedStatement poPst ) {
        try { if( poPst != null)  poPst.close(); } catch(SQLException ex) { Log.writeExpt(ex); }
    }

    /**
     * ResultSet 연결해제
     * @param poRs
     */
    public void realeseResultSet( ResultSet poRs ) {
        try { if( poRs != null)  poRs.close(); } catch(SQLException ex) { Log.writeExpt(ex); }

    }

}
