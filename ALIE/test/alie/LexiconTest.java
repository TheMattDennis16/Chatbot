/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alie;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matt
 */
public class LexiconTest {
    
    public LexiconTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isPlural method, of class Lexicon.
     */
    @Test
    public void testIsPlural() {
        System.out.println("isPlural");
        String word = "";
        Lexicon instance = new Lexicon();
        boolean expResult = false;
        boolean result = instance.isPlural(word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of parseSentence method, of class Lexicon.
     */
    @Test
    public void testParseSentence() {
        System.out.println("parseSentence");
        String sentence = "Hello ALIE";
        Lexicon instance = new Lexicon();
        String expResult = "Hello user.";
        String result = instance.parseSentence(sentence);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
