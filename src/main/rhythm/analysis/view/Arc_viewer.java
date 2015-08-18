package rhythm.analysis.view;
import java.util.List;

import processing.core.*;
import rhythm.analysis.control.Rhythm_controller;

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
public class Arc_viewer extends EmbeddedSketch implements Observer  {
	private static final long serialVersionUID = 1L;	

	/*-----------------------------------------------------------------------------------------
	 * Fields
	 *----------------------------------------------------------------------------------------*/
	private Rhythm_controller controller = null;
		
	//Screen size variables - Immutable
	public final int screenWidth, screenHeight, screenBorder,screenMidY,screenMidX ;
	public final float lineLength;
	
	//Arc / line variables - Mutable
	private float lineSubDivision;
	
	//Processing
	private ControlP5 cp5;
	private PFont f;	
	private Textarea myTextarea;
	
	//Nested windows
	private boolean windowsOpen;
	private PopupWindow textViewWindow, cycleViewWindow;
	private Text_viewer textViewer;
	private Cycle_viewer cycleViewer;
	
	//Arc sliders
	private ArcSlider leftSlider;
	private ArcSlider rightSlider;
	
	//Ensures that arc only redrawn when model updated
	//TO DO - Look for better method so that this variable isn't continually polled
	
	private List<List<Integer>> nodePairs;
	
	/*-----------------------------------------------------------------------------------------
	 * Constructor
	 *----------------------------------------------------------------------------------------*/
			
	public Arc_viewer(Rhythm_controller controller){		
		//Initialise controller
		this.controller = controller;
		//Initialise windows
		
		this.textViewer = new Text_viewer(this, controller);
		controller.attach(textViewer);
		this.textViewWindow = new PopupWindow(this, textViewer); 
		
		this.cycleViewer = new Cycle_viewer(this, controller);
		controller.attach(cycleViewer);
		this.cycleViewWindow = new PopupWindow(this, cycleViewer);	
		
		this.windowsOpen = false;
		
		//Initialise screen dimensions
		this.screenWidth = 1000;
		this.screenHeight = 700;
		this.screenBorder = 50;
		this.screenMidY = screenHeight / 2;
		this.screenMidX = screenWidth / 2;
		this.lineLength = screenWidth - (screenBorder * 2);
		setLineSubDivision();
				
		//Initialise sliders
		//ArcSlider(Arc_viewer arcViewer, int sliderWidth, int leftMin, int rightMax, int startX){
		this.leftSlider  = new ArcSlider(this, 15, screenBorder, screenMidX, screenBorder);
		this.rightSlider = new ArcSlider(this, 15, screenMidX, screenWidth - screenBorder, screenWidth - screenBorder);
		
		
		this.nodePairs = controller.getMatchingStrings();
	}
	
	
	/*-----------------------------------------------------------------------------------------
	 * Getters and setters
	 *----------------------------------------------------------------------------------------*/
	
	public String getData(){
		return this.controller.getModelString();
	}	
	
	public char[] getStringAsArray(){
		return this.getData().toCharArray();
	}
	
	public String getString(){
		return this.getData();
	}
	
	public int getleftSlider(){
		 return this.leftSlider.getSlider();
	 }
		
	public int getRightSlider(){
		return this.rightSlider.getSlider();
	}
	
	public List<List<Integer>> getNodePairs(){
		return this.nodePairs;
	}
	
	/**
	 *  Given that first character of string will be rendered at start of line the line should be 
	 *  divided into (line width / s.length()-1 ) sections.  In this case (s.length - 1) = 8
	 *	therefore 700 / 8 = 87.5 meaning that characters should be placed along line every 87.5 characters
	 */
	private void setLineSubDivision(){
		// TO DO - handle case better where model data is empty string
		if(getData().length() == 0){
			this.lineSubDivision  = lineLength / 2;	
		} else {
			this.lineSubDivision  = lineLength / (getData().length() -1);
		}
	}
	
	public float getLineSubdivision(){
		return this.lineSubDivision;
	}
	
	
	
	/*-----------------------------------------------------------------------------------------
	 * Processing setup and draw methods
	 *----------------------------------------------------------------------------------------*/	
	 /**
	  * Initialises the sketch ready to display the arc diagram
	  */
	 public void setup() {
		size(screenWidth, screenHeight);
		cp5 = new ControlP5(this);
		
		f = createFont("Georgia",15,true);
	    textFont(f);
	    fill(0);
	    textAlign(CENTER);
	    
	    //Text area
	    pushStyle();
	    myTextarea = cp5.addTextarea("txt")
	    	    .setPosition(20,25)
	    	    .setSize(200,200)
	    	    .setFont(createFont("arial",20))
	    	    .setLineHeight(20)
	    	    .setColor(color(00));	
	    popStyle();
	      
	 }
	 
	
	 /**
	   * Processing draw method run in a loop - redraws the screen
	   */
	 public  void draw()  {
		super.draw();   // Should be the first line of draw().
		background(200, 255, 200); // Should be second line of draw(). 
		setLineSubDivision();
		drawArcDiagram();
		drawArcXaxis();
		drawSliders();
	    updateText();  
	 }
	 
	  
	 /*-----------------------------------------------------------------------------------------
	  * Arc diagram draw methods
	  *----------------------------------------------------------------------------------------*/	
	 /**
	  * Draw arc diagram on the screen
	  * Render arcs https://processing.org/reference/arc_.html
	  * Arcs connect repetition regions in a string.  
 	  * The width of the arc is the width of one of the repetition regions (they are the same)
 	  * Each arc has four args (int regionAstart, int regionAend, int regionBstart, int regionBend)   
	  * These represent the two repetition regions A and B to be joined.
	  * 
	  * In processing an arc is defined as follows 
      * arc(x, y, width, height, start, stop);
	  *	x represents the middle of the 'ellipse' which will be nodeTo - ((nodeTo position - nodeFrom position) / 2)
	  * As we need to take into account the width of the arc using strokeWeight(), we will need to calculate the
	  *	nodeFrom and nodeTo position as being midway between regionAstart -> regionAend and regionBstart -> regionBstend respectively
	  * The width will simply be nodeTo position - nodeFrom position
      *	The strokeWeight will be the distance regionAstart -> regionAend 
      *Height can be any value for now 
	  */
	 private void drawArcDiagram(){ 
		 
		 //List<List<Integer>> nodePairs =                
		 
		 for(List<Integer> next: nodePairs){	 
			//int nodeDistance = next[1]-next[0];
			 int nodeDistance = next.get(1)- next.get(0);
			 		 
		 	if(nodeDistance >= this.controller.getArcMin() - 1){
		 	 float regionAstart = next.get(0) * lineSubDivision + screenBorder;
			 float regionAend = next.get(1) * lineSubDivision + screenBorder;
			 float regionBstart = next.get(2) * lineSubDivision + screenBorder;
			 float regionBend= next.get(3) * lineSubDivision + screenBorder;
			 float nodeLength = regionAend - regionAstart;
			 float nodeFrom = getMidPoint(regionAstart, regionAend);
			 float nodeTo = getMidPoint(regionBstart, regionBend);		 
			 float arcMiddle = nodeTo - ((nodeTo - nodeFrom) /2);
			 float arcWidth = nodeTo - nodeFrom; 		 			 
			 
			 //Deals with single character matches.
			 //TO DO  - Improve logic and build into algorithm above.
			 if(nodeDistance == 0) {
				 nodeLength = 10;
			 }
			 
			 pushStyle(); 
			 noFill();
			 stroke(100,90); // 2nd arg is alpha value
			 strokeWeight(nodeLength);
			 strokeCap(SQUARE); // Makes ends of arc square			 
			 arc(arcMiddle, screenMidY, arcWidth, arcWidth, -PI, 0); //render upper half of circle
			// arc(arcMiddle, screenMidY, arcWidth, arcWidth, 0, PI); //render lower half of circle
			 popStyle(); 		 
			}
		 }	 
	 }
	 //Helper method for drawArcDiagram
	 private float getMidPoint(float x, float y){
		 return (x + y) / 2;
	 }
		 
	
	 /*-----------------------------------------------------------------------------------------
	  * Line axis draw methods
	  *----------------------------------------------------------------------------------------*/	
	 private void drawArcXaxis(){	 
		 /*
		  * Draw line.  
	 	  *	Y position = middle of screen i.e. (height / 2)
	 	  *	X position.  Line start  at (width + 100) and ends at (width - 100).  100 represents the border value.
		  */
		 line(screenBorder,screenMidY, screenWidth - screenBorder,screenMidY);
	
		/*
		 *  Render characters along line
		 *  If string length < 50 then whole string is shown along line
		 *  If string length > 50, line is divided into 50 subdivisions with each subdivision's value being = to l /50
		 */
		 float linePosition = screenBorder;
		 if(getData().length() <= 50){
			 for(char currentChar: getData().toCharArray()){
			    	pushMatrix();
			    	translate(linePosition, screenMidY);
			    	linePosition += lineSubDivision;
			    	text(currentChar,0, +30); //= 30 so that characters appear below the line
			    	ellipse(0,-0,5,5);
			    	popMatrix();
			  }
		 } else {
			 int x = getData().length() / 25;
			 for(int i = 1; i < getData().length(); i ++){
			 	pushMatrix();
		    	translate(linePosition, screenMidY);
		    	linePosition += lineSubDivision;
			    if(i % x == 0) {
			    	pushMatrix();
			    	rotate(-HALF_PI);
			    	//TO DO - make second variable b in text(a,b,c,d) below proportional to string length so that very large strings don't overlap x axis
			    	text(i,-20, 0);
			    	ellipse(0,-0,3,3);
			    	popMatrix();
			    }
		    	popMatrix(); 
			 }
		 }
	 }
	 
	 /**
	  * Helper method for draw() that draws sliders and checks if mouse is over any sliders
	  */
	 private void drawSliders(){
		 pushStyle();
		 noFill();
		 
		 ellipse(leftSlider.getXPixels(), screenMidY, leftSlider.getWidth(), leftSlider.getWidth());
		 leftSlider.setSlider();
		 leftSlider.checkSetOverSlider(mouseX, mouseY);
		 ellipse(rightSlider.getXPixels(), screenMidY, rightSlider.getWidth(), rightSlider.getWidth());
		 rightSlider.setSlider();
		 rightSlider.checkSetOverSlider(mouseX, mouseY);
		 //updateSliders();
		 popStyle();
		 
	 } 
	 
	 
	/*-----------------------------------------------------------------------------------------
	 * Other draw methods
	 *----------------------------------------------------------------------------------------*/
	
	 
	 private void updateText(){
		 this.myTextarea.setText("Left slider: " +  this.leftSlider.getSlider() +
					"\n" +
                 "Right slider: " + this.rightSlider.getSlider() + 
                 "\nSubdiv: " + lineSubDivision);
	 }

	 
	 /*-----------------------------------------------------------------------------------------
	  * Mouse event handlers
	  *----------------------------------------------------------------------------------------*/
	 
	 public void mousePressed() {
		 leftSlider.setOffsetIfLocked(mouseX);
		 rightSlider.setOffsetIfLocked(mouseX);
		 
	 }
	
	 public void mouseDragged() {
		 //arguments ensure that sliders do not overlap
		 leftSlider.setSliderPixels(mouseX, leftSlider.getLeftMin(), rightSlider.getXPixels());
		 rightSlider.setSliderPixels(mouseX, leftSlider.getXPixels(), rightSlider.getRightMax());
		 
	 }	 
		
	 public void mouseReleased() {
	  leftSlider.lockSlider();
	  rightSlider.lockSlider();
	  //TO DO - Add button for this
	  if(this.windowsOpen == false) {
		  this.windowsOpen = true;
		  textViewWindow.setVisible(true);
		  this.textViewer.update();
		  cycleViewWindow.setVisible(true);  
	  } 
	 
	}


	@Override
	public void update() {
		//on called on model calling update method as very expensive process.
		this.nodePairs = this.controller.getMatchingStrings();	
	}	 
	 
}
