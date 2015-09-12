package rhythm.suffixTreeTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.*;

import rhythm.analysis.model.suffixTree.InnerNode;
import rhythm.analysis.model.suffixTree.Node;
import rhythm.analysis.model.suffixTree.NodeNonLeaf;


public class InnerNodeTest {
	private String testNode1String;
	private NodeNonLeaf nonLeaf;
	
	
    /**
     * Sets up the test fixture. 
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
    	testNode1String = "ab11";
    	nonLeaf = new NodeNonLeaf(testNode1String, 0, null, new ArrayList<InnerNode>());
    }

    /**
     * Tears down the test fixture. 
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
    	nonLeaf = null;
    	//testNode1String = null;
    }
    
    
    /**
     * Remove the n characters from the front of the string argument n being the length of node's substring field. 
     */
    @Test
    public void inner_noderemoveNodeFromArg(){
    	String expected = "0";
    	String output = nonLeaf.removeNodeFromArg(testNode1String + expected);
    	assertEquals(expected,output);
    }
    
    @Test
    public void thisIsAPrefixOf_testPrefixArgGreaterThanField(){
    	assertTrue(nonLeaf.nodeIsAPrefixOf(testNode1String + 0));
    }
    
    @Test
    public void thisIsAPrefixOf_testPrefixArgSameAsField(){
    	assertFalse(nonLeaf.nodeIsAPrefixOf(testNode1String));
    }
    
    @Test
    public void thisIsAPrefixOf_testPrefixArgLessThanField(){
    	assertFalse(nonLeaf.nodeIsAPrefixOf(testNode1String.substring(0, testNode1String.length()-2)));
    }
    
    @Test
    public void thishasAPrefixOf_testPrefixArgLessThanField(){
    	assertTrue(nonLeaf.nodeHasAPrefixOf(testNode1String.substring(0, testNode1String.length() -2)));
    }
    
    @Test
    public void thishasAPrefixOf_testPrefixArgSameAsField(){
    	assertFalse(nonLeaf.nodeHasAPrefixOf(testNode1String));
    }
    
    @Test
    public void thishasAPrefixOf_testPrefixArgGreaterThanField(){
    	assertFalse(nonLeaf.nodeHasAPrefixOf(testNode1String + 0));
    }


}
