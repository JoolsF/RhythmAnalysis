package rhythm.suffixTreeTests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.junit.*;

import rhythm.analysis.model.suffixTree.SuffixTree;
import static org.junit.Assert.*;



// TO DO - Check that no node has children starting with same character

public class SuffixTreeTest {
	SuffixTree suffixTree1;
	
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

    /**
     * The number of nodes in the tree minus 1 should equal the string length represented by the tree.
     * A String of length n should have n - 1 nodes.
     */
    @Test
    public void nodes_equal_characterLength(){
    	StringBuffer sb = new StringBuffer();
    	int cumulativeLength = 0;
    	for(int i = 1; i <= 20 ; i ++){
    		sb.append(i);
    		cumulativeLength += sb.length();
    		suffixTree1.addString(sb.toString());
    		assertEquals(cumulativeLength, getTreeIndices().size() -1);
    	}	
    }
    
    @Test
    public void nodes_unique(){
    	StringBuffer sb = new StringBuffer();
    	int cumulativeLength = 0;
    	for(int i = 1; i <= 20 ; i ++){
    		sb.append(i);
    		cumulativeLength += sb.length();
    		suffixTree1.addString(sb.toString());
    		
    		assertEquals(cumulativeLength, getTreeIndices().size() -1);
    	}	
    }
   
   
    /*----------------------------------------------------------
     * Helper methods for tests 
     * --------------------------------------------------------*/
    
    private Map<String, List<Integer>> getSubstringMap() {
    	return this.suffixTree1.getTree().nodesToMap(new TreeMap<String, List<Integer>>(getComparator()));
    }
    
    private List<Integer> getTreeIndices(){
    	List<Integer> lst = new ArrayList<Integer>();
    	
    	for(Entry<String, List<Integer>> entry: getSubstringMap().entrySet()){	
    		lst.addAll(entry.getValue());
    	}
    	
    	return lst;
    	
    }
    
    private Comparator<String> getComparator(){
		Comparator<String> comp = new Comparator<String>(){
			@Override
			public int compare(String s1, String s2){
				if(s1.length() > s2.length()){
					return -1;
				} else if(s1.length() < s2.length()){
					return 1;
				} else {
					return s1.compareTo(s2);
				}	
			}
		};
		return comp;	
	}
}
