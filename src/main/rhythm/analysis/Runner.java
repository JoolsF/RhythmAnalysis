package rhythm.analysis;
/*
//References
http://cs.smith.edu/dftwiki/index.php/Tutorial:_A_Model-View-Controller_in_Processing
http://www.austintek.com/mvc/
https://github.com/processing/processing/wiki/Window-Size-and-Full-Screen
*/
import processing.core.PApplet;


public class Runner {
	public static void main(String args[]) {
	    new Runner().launchViewer();	
	  }
	
	public void launchViewer(){
		PApplet.main(new String[] { "rhythm.analysis.view.MainViewer" });
	}
}