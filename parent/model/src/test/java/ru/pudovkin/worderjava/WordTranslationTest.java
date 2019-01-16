package ru.pudovkin.worderjava;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.EqualsMethodTester;
import org.meanbean.test.HashCodeMethodTester;

import static org.junit.Assert.assertEquals;

public class WordTranslationTest {
    private WordTranslation wordTranslation;

    @Before
    public void setUp(){
        wordTranslation=new WordTranslation();
        wordTranslation.setWord("Test");
        wordTranslation.setTranslation("Тест");
    }


    @Test
    public void wordTranslationIsSerializable(){
        final byte[] serializedWord= SerializationUtils.serialize(wordTranslation);
        final WordTranslation deserializedWord=(WordTranslation)SerializationUtils.deserialize(serializedWord);
        assertEquals(wordTranslation,deserializedWord);
    }
    @Test
    public void getterAndSetterCorrectness() throws Exception {
        new BeanTester().testBean(WordTranslation.class);
    }

    @Test
    public void equalsTest(){
        EqualsMethodTester tester = new EqualsMethodTester();
        tester.testEqualsMethod(WordTranslation.class);
    }

    @Test
    public void hashCodeTest(){
        HashCodeMethodTester tester = new HashCodeMethodTester();
        tester.testHashCodeMethod(WordTranslation.class);
    }
}
