package ru.pudovkin.worderjava;




import org.apache.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Class with CRUD operations for DB.
 */
public class WordDao {


    private static Logger logger= Logger.getLogger(WordDao.class);
    /**
     * Get element from DB by id
     * @param id
     * @return if id is valid Optional will contain word obj, if not - empty Optional obj.
     */
    public static Optional<WordTranslation> read(int id) {

        ConnectionPool pool=ConnectionPool.getInstance();
        Connection connection=pool.getConnection();
        PreparedStatement preparedStatement = null;
        String preparedGetSQL="SELECT OriginalWord,Translation FROM wordtranslation WHERE WordID =?";
        ResultSet resultSet=null;
        try{

            preparedStatement = connection.prepareStatement(preparedGetSQL);
            preparedStatement.setString(1,String.valueOf(id));
            resultSet = preparedStatement.executeQuery();
            WordTranslation wordTranslation =new WordTranslation(resultSet.getString(1),resultSet.getString(2));
            Optional<WordTranslation> optionalWordTranslation= Optional.ofNullable(wordTranslation);
            return optionalWordTranslation;
        }catch (SQLException e){
            for (Throwable t:e){
                t.printStackTrace();
            }
            Optional<WordTranslation> optionalWordTranslation= Optional.ofNullable(null);
            return optionalWordTranslation;
        }
        finally {
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeResultSet(resultSet);
            pool.freeConnection(connection);
        }
    }

    /**
     * Gets all words from DB.
     * @return List of WordTranslation objects.
     */
    public static List<WordTranslation> readAll() {
        ConnectionPool pool=ConnectionPool.getInstance();
        Connection connection=pool.getConnection();
        Statement statement = null;
        ResultSet words=null;
        logger.info("Попытка считать получить все переводы из базы данных.");
        try{
            statement=connection.createStatement();
            words=statement.executeQuery("SELECT * FROM wordtranslation");
            List<WordTranslation> wordList = new ArrayList<>();
            while (words.next()){
                wordList.add(new WordTranslation(words.getString("OriginalWord"),
                        words.getString("Translation")));
            }
            logger.info("Успешно получили все переводы из базы данных.");
            return wordList;

        }catch (SQLException e){
            logger.info("Произошла ошибка чтения данных из базы данных.");
            for (Throwable t:e){
                t.printStackTrace();
            }
            return null;
        }
        finally {
            DBUtil.closeStatement(statement);
            DBUtil.closeResultSet(words);
            pool.freeConnection(connection);
        }

    }

    /**
     *  Saves word to DB.
     * @param wordTranslation
     */
    public static void create(WordTranslation wordTranslation) {
       ConnectionPool pool= ConnectionPool.getInstance();
       Connection connection = pool.getConnection();
       PreparedStatement preparedStatement = null;

       String query= "INSERT INTO wordtranslation (OriginalWord,Translation) VALUES (?,?)";

        try {
            logger.info("Пытаемся создать запись с данными: "+wordTranslation+" в базе данных.");
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,wordTranslation.getWord());
            preparedStatement.setString(2,wordTranslation.getTranslation());
            preparedStatement.executeUpdate();
            logger.info("Запись: "+wordTranslation+" успешно создана.");
        } catch (SQLException e) {
            logger.info("Ошбика создания записи: "+ wordTranslation+".");
            e.printStackTrace();
        }
        finally {
            DBUtil.closePreparedStatement(preparedStatement);
            pool.freeConnection(connection);
        }
    }

    /**
     * Update wordTranslation with given id.
     * @param wordTranslation
     * @param params - WordId.
     */
    public static void update(WordTranslation wordTranslation, String[] params) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement=null;
        String query = "UPDATE wordtranslation SET OriginalWord=?, Translation=? where WordID=?;";

        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,wordTranslation.getWord());
            preparedStatement.setString(2,wordTranslation.getTranslation());
            preparedStatement.setString(3,params[0]);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePreparedStatement(preparedStatement);
            pool.freeConnection(connection);
        }
    }

    /**
     * Delete word from DB.
     * @param wordTranslation
     */
    public static void delete(WordTranslation wordTranslation) {
       ConnectionPool pool= ConnectionPool.getInstance();
       Connection connection= pool.getConnection();
       PreparedStatement preparedStatement=null;
       String query= "DELETE from wordtranslation where OriginalWord =?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,wordTranslation.getWord());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        DBUtil.closePreparedStatement(preparedStatement);
        pool.freeConnection(connection);
        }
    }

    /**
     * Check the existence of given word in DB.
     * @param word
     * @return
     */
    public static boolean wordExists(String word){
        ConnectionPool pool= ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;

        String query="SELECT OriginalWord from wordtranslation where OriginalWord=?";

        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,word);
            resultSet=preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeResultSet(resultSet);
            pool.freeConnection(connection);
        }

    }
}
