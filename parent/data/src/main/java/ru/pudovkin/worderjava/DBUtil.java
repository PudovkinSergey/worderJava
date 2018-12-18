package ru.pudovkin.worderjava;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
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
