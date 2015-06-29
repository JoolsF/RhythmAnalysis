package suffixTreeTests;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.*;

import suffixTree.SuffixTree;
import static org.junit.Assert.*;



// TO DO - Check that no node has children starting with same character

public class SuffixTreeTest {
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
//    @Test
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
//    @Test
    public void addSubString_childrenLengthTest_DepthGreaterThan1(){
    	String testInput = "aaa";
    	suffixTree1.addString(testInput);
    	assertEquals(2 , suffixTree1.getTree().getChildren().size());
    }

 /*THIS TEST PRODUCES WRONG TREE 
  * Input "abab"
  * Seems to be do with 2nd AB
  * 
  * $: [2, 2, 3, 4] (extra 2 wrong)
  *	a: [-1]
  *	ab: [0, 1]
  *	b: [-1, -1]
  *
  *
  *	 Should produce tree
  *	 	     R_ _ 
  *		   / |    \
  *	 	  /  |     \
  *		 /   |      \
  *		/    |       \
  *	$(4)   ab(-1)      b(-1)
  *		   /   \          | \
  *		  /     \         |  \
  *		 /       \        |   \
  *		$(2)      ab(0)  $(3)  ab(1)
  * 
  */
    @Test
    public void addSubString_matchingTwoCharPair_differentChars(){
    	String testInput = "ababa";
    	//String testInput = "aba";
    	suffixTree1.addString(testInput);
    	
    	Map<String, List<Integer>> nodeMap = suffixTree1.getTree().nodesToMap();
    	suffixTree1.getTree().printTree();
    	System.out.println();
    	for(Entry<String, List<Integer>> value: nodeMap.entrySet()){	
    		System.out.println(value.getKey() + ": " + value.getValue());
    	}
    }
   
      
    
//    /*
//     *  return type Map<String, List<Integer>>
//     */
// 
//    public void nodesToMap_testReturnValid(){
//    	String testInput = "1010";
//    	suffixTree1.addString(testInput);
//    	
//    }
    
}
