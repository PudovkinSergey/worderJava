package ru.pudovkin.worderjava;

import java.io.Serializable;


public class WordTranslation implements Serializable {




    private String word;



    private String translation;

    public WordTranslation(){
        this.word ="";
        this.translation="";
    }
    public WordTranslation(String word, String translation) {


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
}
