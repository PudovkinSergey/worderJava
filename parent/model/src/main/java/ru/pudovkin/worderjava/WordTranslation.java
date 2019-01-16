package ru.pudovkin.worderjava;

import org.apache.log4j.Logger;


import java.io.Serializable;

/**
 * Java Bean class for storing words and translations.
 */
public class WordTranslation implements Serializable {
    private static final Logger logger=Logger.getLogger(WordTranslation.class);

    private String word;
    private String translation;

    public WordTranslation(){
        logger.info("Создан пустой перевод");
        this.word ="";
        this.translation="";
    }
    public WordTranslation(String word, String translation) {
        logger.info("Добавлен перевод: "+ word+" - "+translation);
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setWord(String word){
        this.word =word;
    }
    public void setTranslation(String translation) {
        this.translation = translation;
    }
    @Override
    public String toString(){
        return this.getWord()+" "+this.getTranslation();
    }
    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WordTranslation that = (WordTranslation) o;

        if (word.equals(that.word)&&translation.equals(that.translation)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash=hash*31+this.getWord().hashCode();
        hash= hash*31+this.getTranslation().hashCode();
        return hash;
    }
}
