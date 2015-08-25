package rhythm.analysis;

import com.sun.crypto.provider.RC2Cipher;

import rhythm.analysis.control.RhythmController;
import rhythm.analysis.view.MainViewer;

public class PerfTestTemp {
	
	
	public static void main(String[] args){
		new PerfTestTemp().launch();
	}

	private void launch() {
		RhythmController rc = new RhythmController(new MainViewer());
		String one = "abc1abc2ab";
		String x = "";
		for(int i = 0; i < 200; i++){
			x += "ab";
		}
		
		
		long start = System.currentTimeMillis();
		rc.updateTree(x);
		long elapsed = System.currentTimeMillis() - start;
		
		System.out.println(elapsed);
		
		
	}

}
