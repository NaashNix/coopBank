package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
        private static db.DbConnection dbConnection = null;
        private Connection connection;

        private DbConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/SanasaBank",
                    "naashnix",
                    "Naash@1234"
            );
        }

        public static db.DbConnection getInstance() throws ClassNotFoundException, SQLException {
            return (dbConnection==null)?(dbConnection = new db.DbConnection()) : dbConnection;
        }
        public Connection getConnection(){
            return connection;
        }

}
