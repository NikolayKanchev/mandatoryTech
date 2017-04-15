package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Didi on 04/13/2017.
 */
public class DBConn {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://sql11.freemysqlhosting.net:3306/";
    private static final String DB_NAME = "sql11168846";
    private static final String USER = "sql11168846";
    private static final String PASS = "ceKMrkK2jA";

    public static Connection getConn(){
        Connection conn = null;
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL + DB_NAME, USER, PASS);
            return conn;
        }catch (Exception e){
            e.printStackTrace();
         return null;
        }
    }
}
