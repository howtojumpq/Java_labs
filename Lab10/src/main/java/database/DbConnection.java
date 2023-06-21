package database;

/*
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class DbConnection {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(String.valueOf(DbConnection.class));
    public java.sql.Connection Connect() throws ClassNotFoundException, SQLException {

        Driver driver = new com.mysql.jdbc.Driver();
        DriverManager.registerDriver(driver);
        java.sql.Connection con = null;
        String userName = "root";
        String password = "root";
        String connectionUrl = "jdbc:mysql://localhost:3306/laba10java";
        Class.forName("com.mysql.jdbc.Driver");
        try{
            FileHandler fh;
            fh = new FileHandler("C:\\Users\\User\\Desktop\\TPI\\Lab10\\Log.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            con = DriverManager.getConnection(connectionUrl,userName,password);
            logger.info("Connected");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return con;
    }
}
*/

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static Connection _connection;

    private final String _url;
    private final String _user;
    private final String _password;
    ;
    public DbConnection() throws Exception {
        Driver driver = new com.mysql.jdbc.Driver();
        DriverManager.registerDriver(driver);

        _url = "jdbc:mysql://localhost:3306/laba10java";
        _user = "root";
        _password = "root";
    }

    public Connection Connect() throws SQLException {
        if(_connection == null) {
            createConnection();
        }
        return _connection;
    }
    public void createConnection() throws SQLException {
        Properties properties = new Properties();
        properties.put("user", _user);
        properties.put("password", _password);
        _connection = DriverManager.getConnection(_url, properties);
    }
}