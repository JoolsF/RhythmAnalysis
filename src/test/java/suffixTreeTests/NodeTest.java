package suffixTreeTests;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.*;

import substringAlgorithms.Node;
import substringAlgorithms.NodeImpl;

public class NodeTest {
	private Node node1;
	private Node leafNodeNoString;
	private String testNode1String;
	
    /**
     * Sets up the test fixture. 
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
    	testNode1String = "ab11";
    	node1 = new NodeImpl(testNode1String, 0);
    	leafNodeNoString = new NodeImpl("$",1);
    }

    /**
     * Tears down the test fixture. 
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
    	node1 = null;
    	testNode1String = null;
    }
    
    
    /**
     * Remove the n characters from the front of the string argument n being the length of node's substring field. 
     */
    @Test
    public void removePrefix_testReturn(){
    	String expected = "0";
    	String output = node1.removePrefix(testNode1String + expected);
    	assertEquals(expected,output);
    }
    
    //thisIsAPrefixOf tests
    @Test
    public void thisIsAPrefixOf_testPrefixArgGreaterThanField(){
    	assertTrue(node1.thisIsAPrefixOf(testNode1String + 0));
    }
    
    @Test
    public void thisIsAPrefixOf_testPrefixArgSameAsField(){
    	assertFalse(node1.thisIsAPrefixOf(testNode1String));
    }
    
    @Test
    public void thisIsAPrefixOf_testPrefixArgLessThanField(){
    	assertFalse(node1.thisIsAPrefixOf(testNode1String.substring(0, testNode1String.length()-2)));
    }
    
    
    //thisHasAPrefixOf tests
    @Test
    public void thishasAPrefixOf_testPrefixArgLessThanField(){
    	assertTrue(node1.thisHasAPrefixOf(testNode1String.substring(0, testNode1String.length() -2)));
    }
    
    @Test
    public void thishasAPrefixOf_testPrefixArgSameAsField(){
    	assertFalse(node1.thisHasAPrefixOf(testNode1String));
    }
    
    @Test
    public void thishasAPrefixOf_testPrefixArgGreaterThanField(){
    	assertFalse(node1.thisHasAPrefixOf(testNode1String + 0));
    }

    
    //updateSubString tests
    
    @Test
    public void updateSubString_testWithNormalString(){
    	String newString = "x";
    	String expectedString = testNode1String + newString;
    	int newIndex = 9;
    	node1.updateSubString(newString, newIndex);
    	String outputStr = node1.getSubString();
    	int outputIndex = node1.getSubStringIndex();
    	assertEquals(expectedString, outputStr); 
    	assertEquals(newIndex, outputIndex); 
    }
    
    @Test
    public void updateSubString_atLeafNodeWithNoValue(){
    	String newString = "x";
    	int newIndex = 2;
    	leafNodeNoString.updateSubString(newString, 2);
    	String outputStr = leafNodeNoString.getSubString();
    	int outputIndex = leafNodeNoString.getSubStringIndex();
    	assertEquals(newString, outputStr); 
    	assertEquals(newIndex, outputIndex); 
    }
    
    
    //addChild tests
    //TO DO basic add children test, check size etc
    
    @Test
    /*
     * tests that terminating symbol "$" is always at end of list regardless of order children are entered.
     */
    public void addChild_addChildrenAndEnsureSortOrderCorrect(){
    	node1.addChild(new NodeImpl("1",1));
    	node1.addChild(new NodeImpl("$",2));
    	node1.addChild(new NodeImpl("0",0));
    	node1.getChildren();
//    	for(Node next: node1.getChildren()){
//    		System.out.println(next.getSubString());
//    	}
    	assertEquals("$",node1.getChildren().get(node1.getChildren().size()-1).getSubString());
    }
}
