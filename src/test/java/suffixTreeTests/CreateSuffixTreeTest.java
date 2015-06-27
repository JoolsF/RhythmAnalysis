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
   // @Test
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
    //@Test
    public void addSubString_childrenLengthTest_DepthGreaterThan1(){
    	String testInput = "aaa";
    	suffixTree1.addString(testInput);
    	suffixTree1.getTree().printTree();
    	//System.out.println(suffixTree1.getTree().getChildren().get(0).getChildStrings());
    	assertEquals(2 , suffixTree1.getTree().getChildren().size());
    }
    
    @Test
    public void addSubString_childrenLengthTest_INSERTNAMEHERE(){
    	String testInput = "abaa";
    	suffixTree1.addString(testInput);
    	suffixTree1.getTree().printTree();
    	//System.out.println(suffixTree1.getTree().getChildren().get(0).getChildStrings());
    	assertEquals(3 , suffixTree1.getTree().getChildren().size());
    }
    
   
}
