package rhythm.analysis.view;
import processing.core.*;

import org.gicentre.utils.multisketch.*;
import org.gicentre.utils.move.*;

import controlP5.ControlP5;
import g4p_controls.*;

/*
Referenced
https://processing.org/tutorials/text/
https://processing.org/tutorials/transform2d/
https://processing.org/tutorials/curves/
http://www.gicentre.net/utils/zoom

Processing Mouse Functions demo used for custom slider
*/
public class Arc_viewer extends EmbeddedSketch {
	private static final long serialVersionUID = 1L;	

	//The data being analysed
	private String stringData = "abcdefghijklmnopqrstuvwxyz";
	
	//Screen size variables - immutable
	private final int screenWidth = 900;
	private final int screenHeight = 600;
	private final int screenBorder = 100;
	private final int screenMidY = screenHeight / 2;
	private final float lineLength = screenWidth - (screenBorder * 2);
	
	//Basic arc / line variables - mutable
	private float lineSubDivision = lineLength / (stringData.length() -1);
	private int arcMinimum = 1; //minimum arc size given starting value of 1
		
	
	//TO DO - Move to seperate class
	//Fields for all  sliders
	private int sliderWidth = 15;
	private int sliderRadius = sliderWidth / 2;
	private boolean locked = false;
	
	//Slider 1
	private float slider1x = 0 + screenBorder;
	private float slider1Xoffset = (float) 0.0;
	private boolean overSlider1 = false;
	private boolean slider1locked = false;
	//Slider 2
	private float slider2x = screenWidth - screenBorder;
	private float slider2Xoffset = (float) 0.0;
	private boolean overSlider2 = false;
	private boolean slider2locked = false;
	//Processing font
	private PFont f;
	
	//Nested window
	private PopupWindow cycleView = null;
	private Cycle_viewer cycleViewer;
	 
	 /**
	  * Initialises the sketch ready to display the arc diagram
	  */
	 public void setup() {
		size(screenWidth, screenHeight);
		//Font settings
		f = createFont("Georgia",15,true);
	    textFont(f);
	    fill(0); //set colour black
	    textAlign(CENTER); // The text must be centred
	    smooth();
	    //Start cycle viewer window
	    cycleViewer = new Cycle_viewer();
	    cycleView = new PopupWindow(this, cycleViewer); 	 
	 }
	 
	  /**
	   * Displays some text and animates a change in size.
	   */
	 public  void draw()  {
		super.draw();   // Should be the first line of draw().
		background(200, 255, 200); // Should be second line of draw(). 
		 // add a vertical slider
		drawArcDiagram();
	    drawSliders();	    
	  }
	 
	 
	 /**
	  * Draw arc diagram on the screen
	  */
	 public void drawArcDiagram(){ 
		 //Test data (should be from model)
		 int[][] nodePairs = loadTestData();

		 /*
		  * Draw line and place string characters evenly along line
		 	 * Steps
		 	 * 1. Draw line.  
		 	 *	Y position = middle of screen i.e. (height / 2)
		 	 *	X position.  Line start  at (width + 100) and ends at (width - 100).  100 represents the border value.
		 	 * 2.Render characters and corresponding character markers onto the line
		 	 *	i)  Calculate line subdivision e.g for string s "12341566" 
		 	 *	ii) Given that first character of string will be rendered at start of line the line should be 
		 	 *  	divided into (line width / s.length()-1 ) sections.  In this case (s.length - 1) = 8
		 	 *		therefore 700 / 8 = 87.5 meaning that characters should be placed along line every 87.5 characters
		 */		 
		 line(screenBorder,screenMidY, screenWidth - screenBorder,screenMidY);

		// Render characters along line
		 float linePosition = screenBorder;
		 for(char currentChar: stringData.toCharArray()){
		    	pushMatrix();
		    	translate(linePosition, screenMidY);
		    	linePosition += lineSubDivision;
		    	text(currentChar,0, +30); //= 30 so that characters appear below the line
		    	ellipse(0,-0,5,5);
		    	popMatrix();
		  }
		 /*
		 * Render arcs https://processing.org/reference/arc_.html
	 	 *  Arcs connect repetition regions in a string.  
	 	 * 	The width of the arc is the width of one of the repetition regions (they are the same)
	 	 * 	Each arc has four args (int regionAstart, int regionAend, int regionBstart, int regionBend)   
	 	 * 	These represent the two repetition regions A and B to be joined.
	 	 *  
	 	 *  In processing an arc is defined as follows 
	 	 *  	arc(x, y, width, height, start, stop);
		 *		x represents the middle of the 'ellipse' which will be nodeTo - ((nodeTo position - nodeFrom position) / 2)
		 *		As we need to take into account the width of the arc using strokeWeight(), we will need to calculate the
		 *		nodeFrom and nodeTo position as being midway between regionAstart -> regionAend and regionBstart -> regionBstend respectively
		 *		The width will simply be nodeTo position - nodeFrom position
		 *		The strokeWeight will be the distance regionAstart -> regionAend 
		 *		Height can be any value for now       
	 	 */                                                       
		 for(int[] next: nodePairs){	 
			int nodeDistance = next[1]-next[0];
			 		 
		 	if(nodeDistance >= arcMinimum){
		 	 float regionAstart = next[0] * lineSubDivision + screenBorder;
			 float regionAend = next[1] * lineSubDivision + screenBorder;
			 float regionBstart = next[2] * lineSubDivision + screenBorder;
			 float regionBend= next[3] * lineSubDivision + screenBorder;
			 float nodeLength = regionAend - regionAstart;
			 float nodeFrom = getMidPoint(regionAstart, regionAend);
			 float nodeTo = getMidPoint(regionBstart, regionBend);		 
			 float arcMiddle = nodeTo - ((nodeTo - nodeFrom) /2);
			 float arcWidth = nodeTo - nodeFrom; 		 			 
			 // TO DO - Refactor so that not calculating previous variables		 
				 pushStyle(); //start style region
				 noFill();
				 stroke(100,127); // 2nd arg is alpha value
				 strokeWeight(nodeLength);
				 strokeCap(SQUARE); // Makes ends of arc square			 
				 arc(arcMiddle, screenMidY, arcWidth, arcWidth, -PI, 0);
				 popStyle(); //end style region
				 
			}
		 }	 
	 }
	 
	 
	 

	 
	 public float getMidPoint(float x, float y){
		 return (x + y) / 2;
	 }
	 
	 public float getXPosition(int xPixels){
		 return (xPixels - screenBorder) / lineSubDivision;		 
	 }
	 
	 public void setArcMinimum(int min){
		 this.arcMinimum = min;
	 }
	 

	/*-----------------------------------------------------------------------------------------
	 * Slider methods
	 *----------------------------------------------------------------------------------------*/
	 
	 /**
	  * Helper method for draw() that draws sliders and checks if mouse is over any sliders
	  */
	 private void drawSliders(){
		 pushStyle();
		 noFill();
		 ellipse(slider1x, screenMidY, sliderWidth, sliderWidth);
		 ellipse(slider2x, screenMidY, sliderWidth, sliderWidth);
		 if(overSlider((int)slider1x, sliderRadius)){
			 overSlider1 = true;
			 slider1locked = true;
		 } else if(overSlider((int)slider2x, sliderRadius)) {
			 overSlider2 = true;
			 slider2locked = true;
		 } else {
			 overSlider1 = false;
			 overSlider2 = false;
			
		 }
		 popStyle();
		 
	 }
	 private boolean overSlider(int xPosition, int offset){
		 if(mouseY >= screenMidY - offset && 
			mouseY <= screenMidY + offset && 
		    mouseX >=  xPosition - offset  && 
			mouseX <=  xPosition + offset){
			 return true;
			} else {
				return false;
			}
	 }
	 	
	 public void mousePressed() {
		  if(overSlider1) { 
		    slider1locked = true; 
		    slider1Xoffset = mouseX - slider2x;  
		  } else if (overSlider2) {
			 slider2locked = true; 
			 slider2Xoffset = mouseX - slider2x;   
		  } else {
			  slider1locked = false;
			  slider2locked = false;  
		  }
		}
	
	 //TO DO - needs refactoring and simplifying
	 public void mouseDragged() {
		  if(slider1locked) {
			  if(mouseX >= screenWidth /2){
				  slider1x = (screenWidth / 2) - (sliderRadius + 1);
			  } else if (mouseX <= screenBorder){
				  slider1x = screenBorder ;  
			  } else {
				  slider1x = mouseX;  
			  }   
		    
		  } else if(slider2locked){
			  if(mouseX <= screenWidth /2){
				  slider2x = (screenWidth / 2) + (sliderRadius + 1);
			  } else if (mouseX >= screenWidth - screenBorder){
				  slider2x =screenWidth - screenBorder ;  
			  } else {
				  slider2x = mouseX;  
			  }     
		  }
	}	 
	
	 public void mouseReleased() {
	  slider1locked = false;
	  slider2locked = false;
	}
	 
	 
	 
	 
	 /*-----------------------------------------------------------------------------------------
	  * Test data - TO DELETE 
	  *----------------------------------------------------------------------------------------*/
	 private int[][] loadTestData() {
		 int[][] nodePairs = new int[5][4]; // [number or pairs][nodes per pair]
		 nodePairs[0][0] = 0;
		 nodePairs[0][1] = 1;
		 nodePairs[0][2] = 2;
		 nodePairs[0][3] = 3;
		 
		 nodePairs[1][0] = 4;
		 nodePairs[1][1] = 6;
		 nodePairs[1][2] = 8;
		 nodePairs[1][3] = 10;
		 
		 nodePairs[2][0] = 15;
		 nodePairs[2][1] = 18;
		 nodePairs[2][2] = 20;
		 nodePairs[2][3] = 23;
		 
		 nodePairs[3][0] = 2;
		 nodePairs[3][1] = 3;
		 nodePairs[3][2] = 4;
		 nodePairs[3][3] = 5;
		 
		 nodePairs[4][0] = 0;
		 nodePairs[4][1] = 6;
		 nodePairs[4][2] = 10;
		 nodePairs[4][3] = 16;
		 
		 return nodePairs;
		 
	}

}
