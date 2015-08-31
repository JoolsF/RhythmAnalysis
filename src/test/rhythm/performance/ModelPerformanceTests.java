package rhythm.performance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
	public void updateTreeRuntimeTest(){
		System.out.println("UPDATE TREE TEST");
		//for(int alphabetSize = 2; alphabetSize <= 10; alphabetSize += 2 ){
			System.out.println("****************************");
			System.out.println("alphabetSize: " + 5);
			for (Map.Entry<Integer, Long> entry :  updateTreeRuntimeTestHelper(5, 8000, 500).entrySet()){
				System.out.println(entry.getKey() + "," + entry.getValue());
			}
			System.out.println();
		//}
		//System.out.println("done");
	}
	
	private Map<Integer, Long> updateTreeRuntimeTestHelper(int strLength, int upTo, int increments){
		String testString = "abcdefghijklmnopqrstuvwxyz";
		Map<Integer, Long> results = new LinkedHashMap<Integer, Long>();
		
		for(int stringLength = 2000;  stringLength <= upTo; stringLength+= increments){
			long time = updateTree(new RhythmController(),  randomStringGenerator(stringLength, testString.substring(0, strLength)));
			results.put(stringLength,  time);
			//System.out.println(stringLength);
		}
		return results;
	}
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
	

	
	
	
	
	/*-----------------------------------------------------------------------------------------
	 * Launch and main
	 *----------------------------------------------------------------------------------------*/
	private void launchTests() {
//		updateTreeRuntimeTest();
//		getMatchingRuntimeTest();
	}
	public static void main(String[] args){
		new ModelPerformanceTests().launchTests();
		
	}

}
