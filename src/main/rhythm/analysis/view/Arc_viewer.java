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
//	    //arc
//	    noFill();
//	    stroke(100);
//	    
//	    arc(187, 300, 175, 500, -PI, 0);
//	    
//	    
//	 
//	    String str = "hello";
//	    
//	    float linePosition = 100;
//	    line(100,screenHeight /2,800,screenHeight /2);
//	    
//	    text("HI",0,0);
//	    
//	    for(char currentChar: str.toCharArray()){
//	    	pushMatrix();
//	    	translate(linePosition, (screenHeight / 2));
//	    	//float w = textWidth(currentChar);
//	    	linePosition += 175; //length of string - 1 / width of line (-1 because first character start on line
//	    	text(currentChar,0,+30);
//	    	ellipse(0,-0,5,5);
//	    	popMatrix();
//	    }
	    
	    
	    
	  }
	 
	 public void drawArcDiagram(){
		 //Test data would come from model
		 String str = "12341566"; 
		 int[][] nodePairs = new int[2][2]; // [number of pairs] [2]
		 nodePairs[0][0] = 0;
		 nodePairs[0][1] = 4;
		 nodePairs[1][0] = 6;
		 nodePairs[1][1] = 7;
		 //Test data end
		 
		 int yMid = screenHeight / 2;
		 int lineLength = screenWidth - (screenBorder * 2);
		 int lineSubDivision = lineLength / (str.length() -1);
		 
		// Draw line using processing line(x1,y1,x2,y2)
		 line(screenBorder,yMid, screenWidth - screenBorder,yMid);
		 
		 /*
		  * 
		 	 * Steps
		 	 * 1. Draw line.  
		 	 * 			Y position = middle of screen i.e. (height / 2)
		 	 * 		    X position.  Line start  at (width + 100) and ends at (width - 100).  100 represents the border value.
		 	 * 2.Render characters and corresponding character markers onto the line
		 	 * 			i)  Calculate line subdivision e.g for string s "12341566" 
		 	 * 			ii) Given that first character of string will be rendered at start of line the line should be 
		 	 *    		    divided into (line width / s.length()-1 ) sections.  In this case (s.length - 1) = 8
		 	 *              therefore 700 / 8 = 87.5 meaning that characters should be placed along line every 87.5 characters
		 */
		 
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
		 * 3.Render arcs
	 	 * 			Arcs in arc diagrams connect two nodes on a line.  An arc has two basic arguments therefore, (int nodeFrom, int nodeTo)
	 	 * 			representing the character to go from and to.
	 	 *          In processing an arc is defined as follows 
	 	 *          	arc(x, y, width, height, start, stop);
		 *				x represents the middle of the 'ellipse' which will be nodeTo - ((nodeTo position - nodeFrom position) / 2)
	 	 *          	The width will simply be nodeTo position - nodeFrom position
	 	 * 				Height can be any value for now
	 	 */                                                       
		 
		 
		 //Render arc
		 //https://processing.org/reference/arc_.html
		 for(int[] next: nodePairs){
			
			 int nodeFrom = next[0] * lineSubDivision + screenBorder;
			 int nodeTo = next[1] * lineSubDivision + screenBorder;
			
			 int arcMiddle = nodeTo - ((nodeTo - nodeFrom) /2);
			 int arcY = screenHeight / 2;
			 int arcWidth = nodeTo - nodeFrom; 
			 int height = 500; // arbitrary for now
			 noFill();
			 stroke(100);
			 
			 println(arcMiddle);
			 
			 
			 arc(arcMiddle, arcY, arcWidth, 500, -PI, 0);
			 //arc(arcMiddle, arcY, arcWidth, height, -PI, 0);
			
		 }
		 
		 	
	
		 	         
		 	
//		 	noFill();
//		    stroke(100);
//		    
//		    arc(187, 300, 175, 500, -PI, 0);
		 
	 }

}
