package SuffixTreeTests;

import static org.junit.Assert.*;

import org.junit.*;

import substringAlgorithms.Node;
import substringAlgorithms.NodeImpl;

public class NodeTest {
	Node testNode1;
	String testString1;
	String testPrefix1;
	
	
	

    /**
     * Sets up the test fixture. 
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
    	testString1 = "ab11";
    	testPrefix1 = "ab110";
    	testNode1 = new NodeImpl(testString1, 0);
    }

    /**
     * Tears down the test fixture. 
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        //emptyList = null;
    }
    
    @Test
    /**
     * Remove the n characters from the front of the string argument n being the length of node's substring field. 
     */
    public void removePrefix_testReturn(){
    	String expected = "0";
    	String output = testNode1.removePrefix(testPrefix1);
    	assertEquals(expected,output);
    }

}
