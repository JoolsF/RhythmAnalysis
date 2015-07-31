package rhythm.analysis.view;
import processing.core.*;

import org.gicentre.utils.multisketch.*;
import org.gicentre.utils.move.*;

import controlP5.ControlP5;
import controlP5.Textarea;
import controlP5.Textfield;
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
	

	/*-----------------------------------------------------------------------------------------
	 * Arc viewer fields
	 *----------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;	

	//The data being analysed
	private String stringData = "1001010101011111111110100010001000101010100001101000001100101";
	
	//Screen size variables - Immutable
	private final int screenWidth = 900;
	private final int screenHeight = 600;
	private final int screenBorder = 100;
	private final int screenMidY = screenHeight / 2;
	private final float lineLength = screenWidth - (screenBorder * 2);
	
	//Basic arc / line variables - mutable
	private float lineSubDivision = lineLength / (stringData.length() -1);
	private int arcMinimum = 1; //minimum arc size given starting value of 1
		
	//Fields for all  sliders
	//TO DO - Refactor to seperate class
	private int sliderWidth = 15;
	private int sliderRadius = sliderWidth / 2;
	
	//Slider 1
	private int slider1xPixels = 0 + screenBorder;
	private int slider1 = 0;
	private float slider1offset = (float) 0.0;
	private boolean overSlider1 = false;
	private boolean slider1locked = false;
	//Slider 2
	private int slider2xPixels = screenWidth - screenBorder;
	
	private int slider2 = 0;
	private float slider2offset = (float) 0.0;
	private boolean overSlider2 = false;
	private boolean slider2locked = false;
	
	//Processing font
	private PFont f;
	
	ControlP5 cp5;
	Textarea myTextarea;
	
	//Nested window
	private PopupWindow textViewWindow = null;
	private Text_viewer textViewer;
	
	//TO DO - insert constructor here
		
	/*-----------------------------------------------------------------------------------------
	 * Getters and setters
	 *----------------------------------------------------------------------------------------*/
	public void setArcMinimum(int min){
		 this.arcMinimum = min;
	 }
	
	public char[] getStringAsArray(){
		return this.stringData.toCharArray();
	}
	
	public void setSlider1(int pixels){
		this.slider1 = getXPosition(slider1xPixels);
	}
	
	public void setSlider2(int pixels){
		this.slider2 = getXPosition(slider2xPixels);
	}
	
	public int getSlider1(){
		return this.slider1;
	}
	
	public int getSlider2(){
		return this.slider2;
	}
	
	
	
	/*-----------------------------------------------------------------------------------------
	 * Processing setup and draw methods
	 *----------------------------------------------------------------------------------------*/	
	 /**
	  * Initialises the sketch ready to display the arc diagram
	  */
	 public void setup() {
		size(screenWidth, screenHeight);
				
		setSlider1(slider1xPixels);
		setSlider2(slider2xPixels);
		
		//Font settings
		f = createFont("Georgia",15,true);
	    textFont(f);
	    fill(0); //set colour black
	    textAlign(CENTER); // The text must be centred
	   
	    cp5 = new ControlP5(this);
	    	    
	    pushStyle();
	    myTextarea = cp5.addTextarea("txt")
	    	    .setPosition(20,25)
	    	    .setSize(200,200)
	    	    .setFont(createFont("arial",20))
	    	    .setLineHeight(20)
	    	    .setColor(color(00));	
	    popStyle();
	    
	    //setup text viewer window
	    textViewer = new Text_viewer(this);
	    textViewWindow = new PopupWindow(this, textViewer); 
	    
	    
	 }
	 
	
	 /**
	   * Draw method run in a loop - redraws the screen
	   */
	 public  void draw()  {
		super.draw();   // Should be the first line of draw().
		background(200, 255, 200); // Should be second line of draw(). 
		drawArcDiagram();
	    drawSliders();
	    this.myTextarea.setText("Slider 1: " +  slider1 +
	    						"\n" +
	                            "Slider 1: " + slider2 + 
	                            "\nSubdiv: " + lineSubDivision);
	 }
	
	 	 
	 
	 
	 /*-----------------------------------------------------------------------------------------
	  * Arc diagram methods
	  *----------------------------------------------------------------------------------------*/	
	 /**
	  * Draw arc diagram on the screen
	  */
	 public void drawArcDiagram(){ 
		 //Test data (should be from model, currently loaded from here)
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

		/*
		 *  Render characters along line
		 *  If stringdata length l < 50 then whole string is shown along line with l subdivisions 
		 *  Else line is divisied into 50 subdivisions with each subdivision's value being = to l /50
		 */
		 
		 float linePosition = screenBorder;
		 if(stringData.length() <= 50){
			 
			 for(char currentChar: stringData.toCharArray()){
			    	pushMatrix();
			    	translate(linePosition, screenMidY);
			    	linePosition += lineSubDivision;
			    	text(currentChar,0, +30); //= 30 so that characters appear below the line
			    	ellipse(0,-0,5,5);
			    	popMatrix();
			  }
		 } else {
			 int x = stringData.length() / 50;
			 for(int i = 1; i < stringData.length(); i ++){
			 	pushMatrix();
		    	translate(linePosition, screenMidY);
		    	linePosition += lineSubDivision;
		    if(i % x == 0) {
		    	ellipse(0,-0,3,3);
		    }
		    	popMatrix(); 
			 }
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
				 pushStyle(); 
				 noFill();
				 stroke(100,127); // 2nd arg is alpha value
				 strokeWeight(nodeLength);
				 strokeCap(SQUARE); // Makes ends of arc square			 
				 arc(arcMiddle, screenMidY, arcWidth, arcWidth, -PI, 0);
				 popStyle(); 
				 
			}
		 }	 
	 }
	 //Helper method for drawArcDiagram
	 private float getMidPoint(float x, float y){
		 return (x + y) / 2;
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
		 ellipse(slider1xPixels, screenMidY, sliderWidth, sliderWidth);
		 ellipse(slider2xPixels, screenMidY, sliderWidth, sliderWidth);
		 if(overSlider((int)slider1xPixels, sliderRadius)){
			 overSlider1 = true;
			 slider1locked = true;
		 } else if(overSlider((int)slider2xPixels, sliderRadius)) {
			 overSlider2 = true;
			 slider2locked = true;
		 } else {
			 overSlider1 = false;
			 overSlider2 = false;
			
		 }
		 popStyle();
		 
	 }
	 //Helper method
	 private int getXPosition(int xPixels){
		 return (int) ((xPixels - screenBorder) / lineSubDivision);		 
	 }
	 private boolean overSlider(int xPosition, int offset){
		 if(mouseY >= screenMidY - offset  && 
			mouseY <= screenMidY + offset  && 
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
		    slider1offset = mouseX - slider2xPixels;  
		  } else if (overSlider2) {
			 slider2locked = true; 
			 slider2offset = mouseX - slider2xPixels;   
		  } else {
			  slider1locked = false;
			  slider2locked = false;  
		  }
		}
	
	 //TO DO - needs refactoring and simplifying
	 
	 public void mouseClicked(){
		 
	 }
	 public void mouseDragged() {
		  if(slider1locked) {
			  if(mouseX >= screenWidth /2){
				  slider1xPixels = (screenWidth / 2) - (sliderRadius + 1);
				  
			  } else if (mouseX <= screenBorder){
				  slider1xPixels = screenBorder ;  
			  } else {
				  slider1xPixels = mouseX;  
			  }   
		    
		  } else if(slider2locked){
			  if(mouseX <= screenWidth /2){
				  slider2xPixels = (screenWidth / 2) + (sliderRadius + 1);
			  } else if (mouseX >= screenWidth - screenBorder){
				  slider2xPixels =screenWidth - screenBorder ;  
			  } else {
				  slider2xPixels = mouseX;  
			  }     
		  }
		  setSlider1(slider1xPixels);
		  setSlider2(slider2xPixels);
	}	 
	
	 public void mouseReleased() {
	  slider1locked = false;
	  slider2locked = false;
	  textViewWindow.setVisible(true);
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
