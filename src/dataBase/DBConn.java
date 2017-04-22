package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Didi on 04/13/2017.
 */
public class DBConn {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://35.161.40.26:3306/";
    private static final String DB_NAME = "foosball_management";
    private static final String USER = "Nikolay";
    private static final String PASS = "1234";

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
