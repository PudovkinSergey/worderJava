package ru.pudovkin.worderjava;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class WordDao implements Dao<WordTranslation> {


    /**
     * Get element from DB by id
     * @param id
     * @return if id is valid Optional will contain word obj, if not - empty Optional obj.
     */
    @Override
    public Optional<WordTranslation> get(int id) {
        ConnectionPool pool=ConnectionPool.getInstance();
        Connection connection=pool.getConnection();
        PreparedStatement preparedStatement = null;
        String preparedGetSQL="SELECT OriginalWord,Translation FROM wordtranslation WHERE WordID =?";

        try{

            preparedStatement = connection.prepareStatement(preparedGetSQL);
            preparedStatement.setString(1,String.valueOf(id));
            ResultSet wordSet = preparedStatement.executeQuery();
            WordTranslation wordTranslation =new WordTranslation(wordSet.getString(1),wordSet.getString(2));
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
            pool.freeConnection(connection);
        }
    }

    /**
     * Gets all words from DB.
     * @return List of WordTranslation objects
     */
    @Override
    public List<WordTranslation> getAll() {
        ConnectionPool pool=ConnectionPool.getInstance();
        Connection connection=pool.getConnection();
        Statement statement = null;
        ResultSet words=null;
        try{
            statement=connection.createStatement();
            words=statement.executeQuery("SELECT * FROM wordtranslation");
            List<WordTranslation> wordList = new ArrayList<>();
            while (words.next()){
                wordList.add(new WordTranslation(words.getString("OriginalWord"),
                        words.getString("Translation")));
            }
            return wordList;

        }catch (SQLException e){
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

    @Override
    public void save(WordTranslation wordTranslation) {
       // words.add(wordTranslation);
    }

    @Override
    public void update(WordTranslation wordTranslation, String[] params) {
        wordTranslation.setWord(Objects.requireNonNull(params[0]));
        wordTranslation.setTranslation(Objects.requireNonNull(params[1]));
    }

    @Override
    public void delete(WordTranslation wordTranslation) {
       // words.remove(wordTranslation);
    }
}
