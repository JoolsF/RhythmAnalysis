package analysis;

// TO DO LATER - Implement simple MVC pattern so its as decoupled as possible
//http://cs.smith.edu/dftwiki/index.php/Tutorial:_A_Model-View-Controller_in_Processing
//http://www.austintek.com/mvc/

import processing.core.PApplet;


public class Runner {
	public static void main(String args[]) {
	    new Runner().launchViewer();
		
	  }
	
	
	public void launchViewer(){
		//https://github.com/processing/processing/wiki/Window-Size-and-Full-Screen
		PApplet.main(new String[] { "analysis.view.Rhythm_viewer" });
	}
	

}
