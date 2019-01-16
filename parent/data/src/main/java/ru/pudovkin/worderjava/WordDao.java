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
     * Get translation from DB by word
     * @param word
     * @return if id is valid Optional will contain word obj, if not - empty Optional obj.
     */
    public static Optional<WordTranslation> read(String word) {

        ConnectionPool pool=ConnectionPool.getInstance();
        Connection connection=pool.getConnection();
        PreparedStatement preparedStatement = null;
        String preparedGetSQL="SELECT OriginalWord,Translation FROM wordtranslation WHERE OriginalWord =?";
        ResultSet resultSet=null;
        try{

            preparedStatement = connection.prepareStatement(preparedGetSQL);
            preparedStatement.setString(1,word);
            resultSet = preparedStatement.executeQuery();
            logger.info("Попытка чтения из базы данных.");
            if (resultSet.next()){
                WordTranslation wordTranslation =new WordTranslation(resultSet.getString(1),resultSet.getString(2));
                Optional<WordTranslation> optionalWordTranslation= Optional.ofNullable(wordTranslation);
                return optionalWordTranslation;
            }
            else {
                logger.info("Не нашлось данных по данному запросу.");
                return Optional.empty();
            }

        }catch (SQLException e){
            for (Throwable t:e){
                logger.error("Исключение при чтениии из базы данных",t);
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
            logger.error("Исключение при чтении из базы данных.",e);
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
            logger.error("Исключение создания записи: "+ wordTranslation+".",e);
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
     * @param params - word.
     */
    public static void update(WordTranslation wordTranslation, String[] params) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement=null;
        String query = "UPDATE wordtranslation SET Translation=? where OriginalWord=?;";

        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,wordTranslation.getTranslation());
            preparedStatement.setString(2,wordTranslation.getWord());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Исключение при попытке обновить запись в базе данных",e);
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
            logger.error("Исключение при попытке удалить запись из БД",e);
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
            logger.error("Исключение при проверке существования слова в базе данных",e);
            return false;
        } finally {
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeResultSet(resultSet);
            pool.freeConnection(connection);
        }

    }
}
