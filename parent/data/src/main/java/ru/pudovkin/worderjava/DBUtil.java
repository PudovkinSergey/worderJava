package ru.pudovkin.worderjava;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Helps working with DB objects.
 */
public class DBUtil {
    private static Logger logger= Logger.getLogger(DBUtil.class);
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
            logger.error("Исключение при закрытии statement в DBUtil",e);
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
            logger.error("Исключние при закрытии preparedStatement в DBUtil",e);
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
            logger.error("Исключение при закрытии Result set в DBUtil",e);
        }
    }
}
