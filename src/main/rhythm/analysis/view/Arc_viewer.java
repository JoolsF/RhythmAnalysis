package rhythm.analysis.view;
import processing.core.*;
import org.gicentre.utils.multisketch.*;
/*
Referenced
https://processing.org/tutorials/text/
https://processing.org/tutorials/transform2d/
https://processing.org/tutorials/curves/
*/
public class Arc_viewer extends EmbeddedSketch {
	private static final long serialVersionUID = 1L;
	
	 private float textScale;  
	 private final int screenWidth = 900;
	 private final int screenHeight = 600;
	 private final int screenBorder = 100;
	 
	 PFont f;
	 
	 
	  // Initialises the sketch ready to display some animated text.
	 public void setup() {
	    size(screenWidth, screenHeight);
	    f = createFont("Georgia",15,true);
	    textFont(f);
	    fill(0); //set colour black
	 // The text must be centred
	    textAlign(CENTER);
	    smooth();
	    
	    }
	 
	  // Displays some text and animates a change in size.
	 public  void draw()  { 
	    super.draw();   // Should be the first line of draw(). 
	    background(200, 255, 200); // Should be second line of draw(). 
	    
	    drawArcDiagram();  
	    
	  }
	 /**
	  * Draw arc diagram on the screen
	  */
	 public void drawArcDiagram(){
		 //Test data would come from model
		 String str = "abcdefghijklmnopqrstuvwxyz"; 
		 int[][] nodePairs = new int[2][4]; // [number or pairs][nodes per pair]
		 nodePairs[0][0] = 0;
		 nodePairs[0][1] = 3;
		 nodePairs[0][2] = 9;
		 nodePairs[0][3] = 12;
		 nodePairs[1][0] = 11;
		 nodePairs[1][1] = 12;
		 nodePairs[1][2] = 24;
		 nodePairs[1][3] = 25;
		 //Test data end
		 
		 
		 /*
		  * 
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
		 
		 
		 
		 float yMid = screenHeight / 2;
		 float lineLength = screenWidth - (screenBorder * 2);
		 float lineSubDivision = lineLength / (str.length() -1);
		 
		 println("lineSubDivision: " + lineSubDivision);
		 
		// Draw line using processing line(x1,y1,x2,y2)
		 line(screenBorder,yMid, screenWidth - screenBorder,yMid);
		 
		
		 
		// Render characters along line
		 float linePosition = screenBorder;
		 for(char currentChar: str.toCharArray()){
		    	pushMatrix();
		    	translate(linePosition, (screenHeight / 2));
		    	linePosition += lineSubDivision;
		    	text(currentChar,0, +30); //= 30 so that characters appear below the line
		    	ellipse(0,-0,5,5);
		    	popMatrix();
		  }
		 
		 
		 /*
		 * 3.Render arcs https://processing.org/reference/arc_.html
	 	 *  Arcs connect two repetition regions in a string.  
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
		 		 
		 //Render arc 
		 for(int[] next: nodePairs){	 
			 float regionAstart = next[0] * lineSubDivision + screenBorder;
			 float regionAend = next[1] * lineSubDivision + screenBorder;
			 float regionBstart = next[2] * lineSubDivision + screenBorder;
			 float regionBend= next[3] * lineSubDivision + screenBorder;
			
			 float nodeLength = regionAend - regionAstart;
			 float nodeFrom = getMidPoint(regionAstart, regionAend);
			 float nodeTo = getMidPoint(regionBstart, regionBend);
			 		 
			 float arcMiddle = nodeTo - ((nodeTo - nodeFrom) /2);
			 float arcY = screenHeight / 2;
			 float arcWidth = nodeTo - nodeFrom; 
			 float height = 300; // arbitrary for now
				 
			 pushStyle(); //start style region
			 noFill();
			 stroke(100,127); // 2nd arg is alpha value
			 strokeWeight(nodeLength);
			 strokeCap(SQUARE); // Makes ends of arc square			 
			 arc(arcMiddle, arcY, arcWidth, height, -PI, 0);
			 popStyle(); //end style region
		 }	 
		 
//		 //Render arc
//		 //https://processing.org/reference/arc_.html
//		 for(int[] next: nodePairs){
//			
//			 int nodeFrom = next[0] * lineSubDivision + screenBorder;
//			 int nodeTo = next[1] * lineSubDivision + screenBorder;
//			
//			 int arcMiddle = nodeTo - ((nodeTo - nodeFrom) /2);
//			 int arcY = screenHeight / 2;
//			 int arcWidth = nodeTo - nodeFrom; 
//			 int height = 500; // arbitrary for now
//			
//			 
//			 pushStyle(); //start style region
//			 noFill();
//			 stroke(100,127); // 2nd arg is alpha value
//			 strokeWeight(120);
//			 strokeCap(SQUARE); // Makes ends of arc square			 
//			 arc(arcMiddle, arcY, arcWidth, height, -PI, 0);
//			 popStyle(); //end style region
//		 }	 
		 
		 
		 
	 }
	 
	 public float getMidPoint(float x, float y){
		 return (x + y) / 2;
	 }

}
