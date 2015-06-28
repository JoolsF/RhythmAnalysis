package suffixTreeTests;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.*;

import substringAlgorithms.Node;
import substringAlgorithms.NodeImpl;
import substringAlgorithms.SuffixTree;


// TO DO - Check that no node has children starting with same character

public class CreateSuffixTreeTest {
	SuffixTree suffixTree1;
	//public String input = "ab110011ab";
	public String[] input = new String[]{"a","b","1","1","0","0","1","1","a","b"};
	
    /**
     * Sets up the test fixture. 
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
    	suffixTree1 = new SuffixTree();
    }

    /**
     * Tears down the test fixture. 
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
    	suffixTree1 = null;
    }
    
    
    /**
     * The length of the children array should always be input + 1 because of terminating symbol
     * 
     * Should produce tree
     *      R
     *     /\     
     *    /  \     
     *  a(0)  $(1) 
     */
    @Test
    public void addSubString_childrenLengthTest_Depth1(){
    	String testInput = "a";
    	suffixTree1.addString(testInput);
    	assertEquals(testInput.length() +1 , suffixTree1.getTree().getChildren().size());
    }
    
    /**
     * "aaa$" should produce tree
	 * 			R					
	 *		   / \
	 *		  a	  $(3)
	 *		 /\
	 *		/  \
  	 *	   a    $(2)
	 *	  / \
	 *	a(0) $(1) 
	 * 
	 * Root should have 2 children
     */
    @Test
    public void addSubString_childrenLengthTest_DepthGreaterThan1(){
    	String testInput = "aaa";
    	suffixTree1.addString(testInput);
    	assertEquals(2 , suffixTree1.getTree().getChildren().size());
    }

 /*THIS TEST PRODUCES WRONG TREE 
  * Seems to be do with last AB
  *  Try adding all results to array
  *  NODE: a (-1)
 NODE: b (-1)
 NODE: 110011ab (0)
 NODE: $ (8)  <-----
 NODE: $ (8)  <----
 NODE: b (-1)
 NODE: 110011ab (1)
 NODE: $ (9)
 NODE: 1 (-1)
 NODE: 1 (-1)
 NODE: 0011ab (2)
 NODE: ab (6)
 NODE: 0011ab (3)
 NODE: ab (7)
 NODE: 0 (-1)
 NODE: 011ab (4)
 NODE: 11ab (5)
 NODE: $ (10)
  * 
  */
    @Test
    public void addSubString_childrenLengthTest_INSERTNAMEHERE(){
    	String testInput = "ab110011ab";
    	suffixTree1.addString(testInput);
    	//suffixTree1.getTree().printTree();
    	//TO DO - implement proper test
    	assertEquals(true,false);
    }
    
   
}
