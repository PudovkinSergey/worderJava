package ru.pudovkin.worderjava;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for creating Connection Pool to make DB connections.
 */
public class ConnectionPool {
    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;

    /**
     * Constructor of connection pool for given DataSource.
     */
    private ConnectionPool(){
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/pudov");
        }
        catch (NamingException e){
            System.out.println(e);
        }
    }

    public static synchronized ConnectionPool getInstance(){
        if (pool==null){
            pool=new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection(){
        try {
         return dataSource.getConnection();
        }
        catch (SQLException e){
            System.out.println(e);
            return null;
        }
    }
    public void freeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
