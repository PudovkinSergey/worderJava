package ru.pudovkin.worderjava;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Helps working with DB objects.
 */
public class DBUtil {

    /**
     * Closes statement if opened.
     * @param statement
     */
    public static void closeStatement(Statement statement){
        try {
            if (statement!=null){
                statement.close();
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    /**
     * Closes prepared statement if opened.
     * @param statement
     */
    public static void closePreparedStatement(PreparedStatement statement){
        try {
            if (statement!=null){
                statement.close();
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    /**
     * Closes ResultSet if opened.
     * @param resultSet
     */
    public static void closeResultSet(ResultSet resultSet){
        try {
            if (resultSet!=null){
                resultSet.close();
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
}
