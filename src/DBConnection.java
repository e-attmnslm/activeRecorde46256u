import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static String userName = "root";
    private static String password = "";
    private static String serverName = "127.0.0.1";
    private static String portNumber = "3306";
    private static String dbName = "testpersonne";

    private static Connection instance;

    private DBConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName;
        instance = DriverManager.getConnection(urlDB, connectionProps);
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (instance == null) {
             new DBConnection();
        }
        return instance;
    }

    public static synchronized void setNomDB(String nomDB) throws SQLException {
        if (nomDB!=null && nomDB!=dbName){
            dbName=nomDB;
            instance=null;

        }
    }
}
