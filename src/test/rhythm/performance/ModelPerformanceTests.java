package rhythm.performance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.instrument.Instrumentation;

import org.apache.commons.lang3.RandomStringUtils;

import rhythm.analysis.control.RhythmController;
import rhythm.analysis.model.stringHierachyAnalysis.StringHierarchyAnalyser;
import rhythm.analysis.model.suffixTree.SuffixTree;
/**
 * Following references used in this class
 * https://dzone.com/articles/how-generate-random-string
 * https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/RandomStringUtils.html
 * http://www.vogella.com/tutorials/JavaPerformance/article.html#runtimeinfo_memory 
 *
 */



public class ModelPerformanceTests {
	
	StringHierarchyAnalyser sa = new StringHierarchyAnalyser();
	Runtime runtime = Runtime.getRuntime();
	
	/*-----------------------------------------------------------------------------------------
	 * Tests
	 *----------------------------------------------------------------------------------------*/
	private long updateTree(RhythmController rc, String str){
		long start = System.currentTimeMillis();
		rc.updateTree(str);
		return System.currentTimeMillis() - start;
	}
	
	private long getMatchingStrings(RhythmController rc){
		long start = System.currentTimeMillis();
		this.sa.getStringCoordinatesExactMatch(rc.getTreeAsMap(), false);
		return System.currentTimeMillis() - start;
	}
	
	private long getSimilarStrings(RhythmController rc){
		long start = System.currentTimeMillis();
		this.sa.getStringCoordinatesInexactMatch(rc.getTreeAsMap(), false);
		return System.currentTimeMillis() - start;
	}
	
	private long getSubStringsNaive(String str){
		long start = System.currentTimeMillis();
		NaiveSubstringFinder.enumerateSubstrings(str);
		return System.currentTimeMillis() - start;
	}
	
	
	
	/*-----------------------------------------------------------------------------------------
	 * String generators
	 *----------------------------------------------------------------------------------------*/
	private String randomStringGenerator(int length, String alphabet){
		return RandomStringUtils.random(length, alphabet.toCharArray());
	}
	
	private String stringConcat(int repetitions, String string){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i <= repetitions; i++ ){
			sb.append(string);
		}
		
		return sb.toString();
	}
	
	
	
			
	/*-----------------------------------------------------------------------------------------
	 * Test runners
	 *----------------------------------------------------------------------------------------*/
	
	/**
	 * updateTree test runner
	 */
	public void updateTreeRuntimeTest(){
		System.out.println("UPDATE TREE TEST");
		for(int alphabetSize = 2; alphabetSize <= 10; alphabetSize += 2 ){
			System.out.println("****************************");
			System.out.println("alphabetSize: " + 5);
			for (Map.Entry<Integer, Long> entry :  updateTreeRuntimeTestHelper(5, 2000, 500).entrySet()){
				System.out.println(entry.getKey() + "," + entry.getValue());
			}
			System.out.println();
		}

	}
	
	private Map<Integer, Long> updateTreeRuntimeTestHelper(int strLength, int upTo, int increments){
		String testString = "abcdefghijklmnopqrstuvwxyz";
		Map<Integer, Long> results = new LinkedHashMap<Integer, Long>();
		
		for(int stringLength = 0;  stringLength <= upTo; stringLength+= increments){
			long time = updateTree(new RhythmController(),  randomStringGenerator(stringLength, testString.substring(0, strLength)));
			results.put(stringLength,  time);
		}
		return results;
	}
	
	
	/**
	 * getMatchingStrings test runner
	 */
	public void getMatchingRuntimeTest(){
		System.out.println("GET MATCHING STRING TEST");
			System.out.println("****************************");
			System.out.println("alphabetSize: " + 5);
			for (Map.Entry<Integer, Long> entry :  getMatchingRuntimeTestHelper(5, 6000, 500).entrySet()){
				System.out.println(entry.getKey() + "|" + entry.getValue());
			}
			System.out.println();
		
	}
	private Map<Integer, Long> getMatchingRuntimeTestHelper(int strLength, int upTo, int increments){
		String testString = "abcdefghijklmnopqrstuvwxyz";
		Map<Integer, Long> results = new LinkedHashMap<Integer, Long>();
		
		for(int stringLength = 0;  stringLength <= upTo; stringLength+= increments){
			RhythmController rc = new RhythmController();
			rc.updateTree(randomStringGenerator(stringLength, testString.substring(0, strLength)));
			long time = getMatchingStrings(rc);
			results.put(stringLength,  time);
			System.out.println("!");
		}
		return results;
	}
	

	/**
	 * findSubstringNaive test runner
	 */
	
	
	
	
	public void getSubStringsNaiveRunner(int from, int to, int increments){	
		System.out.println("Get SUBSTRINGS NAIVE METHOD TEST");
		String testString = "abcdefghijk";
		Map<Integer, Long> results = new LinkedHashMap<Integer, Long>();
		
		for(int stringLength = from;  stringLength <= to; stringLength+= increments){
			long time = getSubStringsNaive(randomStringGenerator(stringLength, testString));
			System.out.println(stringLength + "->" + time);
			results.put(stringLength,  time);
		}
		for(Map.Entry<Integer, Long> entry: results.entrySet()){	
			System.out.println("****************************");
			System.out.println("String: " + entry.getKey());
			System.out.println("Time: " + entry.getValue());
			System.out.println();
			
		}
	
	}
	
	
	
	/*-----------------------------------------------------------------------------------------
	 * Launch and main
	 *----------------------------------------------------------------------------------------*/
	private void launchTests() {
		updateTreeRuntimeTest();
//		getMatchingRuntimeTest();
//		getSubStringsNaiveRunner(0, 4000, 100);
	}
	public static void main(String[] args){
		new ModelPerformanceTests().launchTests();
		
	}

}
