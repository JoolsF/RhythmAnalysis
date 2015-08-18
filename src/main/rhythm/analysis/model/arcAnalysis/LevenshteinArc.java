package rhythm.analysis.model.arcAnalysis;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Utility class for Arc Analysis containing one public static method getDifference. 
 * 
 *
 */
public class LevenshteinArc {

	
	
	
	
	public static double getDifference(String src, String arc){
		double difference = StringUtils.getLevenshteinDistance(src, arc);
		return (difference / src.length());
	}
	
	
	
	
	
	
}
