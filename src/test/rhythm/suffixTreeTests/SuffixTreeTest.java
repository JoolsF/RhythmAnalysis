package rhythm.suffixTreeTests;

import rhythm.analysis.model.suffixTree.SuffixTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.junit.*;

import static org.junit.Assert.*;

import org.apache.commons.lang3.RandomStringUtils;


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
     * Root node of a tree representing a string with 1 unique character should have 2 children
     * Test case aa
     */
    @Test
    public void numberChildrenRoot_aa_2() {
    	suffixTree1.addString("aa");
    	int expected = 2;
    	int output =  suffixTree1.getTree().getChildren().size();
    	assertEquals(expected, output);
    	
    }
    
    /**
     * Root node of a tree representing a string with 1 unique character should have 2 children
     * Test case aaaaa
     */
    @Test
    public void numberChildrenRoot_aaaaa() {
    	suffixTree1.addString("aaaaa");
    	int expected = 2;
    	int output =  suffixTree1.getTree().getChildren().size();
    	assertEquals(expected, output);
    	
    }
    
    /**
     * Root node of a tree representing a string with 2 unique character should have 3 children
     * Test case ab
     */
    @Test
    public void numberChildrenRoot_ab_3() {
    	suffixTree1.addString("ab");
    	int expected = 3;
    	int output =  suffixTree1.getTree().getChildren().size();
    	assertEquals(expected, output);
    	
    }
    
    /**
     * Root node of a tree representing a string with 2 unique character should have 3 children
     * Test case aaaab
     */
    @Test
    public void numberChildrenRoot_aaaab_4() {
    	suffixTree1.addString("aaaab");
    	int expected = 3;
    	int output =  suffixTree1.getTree().getChildren().size();
    	assertEquals(expected, output);
    	
    }
    
    /**
     * Root node of a tree representing a string with 10 unique character should have 11 children
     * Test case abcdefghij
     */
    @Test
    public void numberChildrenRoot_abcdefghij() {
    	suffixTree1.addString("abcdefghij");
    	int expected = 11;
    	int output =  suffixTree1.getTree().getChildren().size();
    	assertEquals(expected, output);
    	
    }

    /**
     * The number of leaf nodes in the tree minus 1 should equal the string length represented by the tree.
     * A string of length n should have n  nodes (taking in account $ terminal symbol).
     * Random string generated from alphabet {a-z} used as input.
     * Ensures a wider variety of test cases can be run reducing chance that only particular substrings return true
     */
    @Test
    public void numberLeafNodes_equalCharacterLength(){
    	StringBuffer sb = new StringBuffer();
    	int cumulativeLength = 0;
    	for(int i = 1; i <= 30 ; i ++){
    		sb.append(randomStringGenerator(1, "abcde"));
    		cumulativeLength += sb.length();
    		suffixTree1.addString(sb.toString());
    		assertEquals("Error with string " + sb.toString(),cumulativeLength, getTreeIndices().size() -1);
    	}	
    }
   
 
    

    /**
     * An n length string's leaf node indices should be the range 0 to n (taking in account $ terminal symbol).
     * Random string generated from alphabet {a-z} used as input.
     * Ensures a wider variety of test cases can be run reducing chance that only particular substrings return true
     */
    @Test
    public void leafNodeIndices_Unique(){
    	StringBuffer sb = new StringBuffer();
    	for(int i = 1; i <= 30 ; i ++){
    		sb.append(randomStringGenerator(1, "abcde"));
    		suffixTree1.addString(sb.toString());
    		List<Integer> sortedList = getTreeIndices();
    		Collections.sort(sortedList);
    		
    		for(int j = 0; j <= sortedList.size() -1 ; j++){
    			assertTrue("Error with string " + sb.toString(), sortedList.get(j) == j);
    		}
    	}	
    }
   
   
   
    /*----------------------------------------------------------
     * Helper methods for tests 
     * --------------------------------------------------------*/
    
    private String randomStringGenerator(int length, String alphabet){
		return RandomStringUtils.random(length, alphabet.toCharArray());
	}
    
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
    
    private List<String> getSubstringIndices(){
    	List<String> lst = new ArrayList<String>();
    	
    	for(Entry<String, List<Integer>> entry: getSubstringMap().entrySet()){	
    		lst.add(entry.getKey());
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
