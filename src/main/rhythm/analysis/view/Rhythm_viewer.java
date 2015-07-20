package rhythm.analysis.view;


import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textfield;
import processing.core.*;
import rhythm.analysis.Rhythm_controller;
import rhythm.analysis.model.Rhythm_model;

public class Rhythm_viewer extends PApplet{
	private static final long serialVersionUID = 1L;
	
	String textValue = "";
	ControlP5 cp5;
	
	Rhythm_controller controller = null;
	Rhythm_model model = null;
	
	/**
	 * setup() called first
	 */
	public void setup() {  
		//The size of UI screen
		size(900,600);
		
		//MVC system started here
		controller = new Rhythm_controller();
		controller.initAll(this);
		
		
		//ControlP5 setup code starts here
		//Fontsetup
		PFont font = createFont("arial",20);
		textFont(font);
		cp5 = new ControlP5(this);
		
		//Input textfield 1
		cp5.addTextfield("input")
			.setPosition(20,100)
		 	.setSize(200,40)
		 	.setFont(font)
		 	.setFocus(true)
		 	.setColor(color(255,0,0));
		}
		
	/**
	 * draw() called second
	 */
	public void draw() {
		background(0);
		fill(255);
		text(cp5.get(Textfield.class,"input").getText(), 360,130);
		text(textValue, 360,180);
	}

	public void setModel(Rhythm_model m) {
		this.model = m;
	}

	
	//ControlP5 code starts here
	
	/**
	 * clear()
	 * @see processing.core.PApplet#clear()
	 */
	public void clear() {
	  cp5.get(Textfield.class,"textValue").clear();
	}
		
	/**
	 * 
	 * @param theEvent
	 */
	public void controlEvent(ControlEvent theEvent) {
		if(theEvent.isAssignableFrom(Textfield.class)) {
			println("controlEvent: accessing a string from controller '"
					+theEvent.getName()+"': "
					+theEvent.getStringValue()
					);
			}
		}

	/**
	 * 
	 * 
	 * @param theText
	 */
	public void input(String theText) {
			// automatically receives results from controller input
			println("a textfield event for controller 'input' : "+theText);
		}
	
	
	
}
//https://processing.org/tutorials/text/
//http://cs.smith.edu/dftwiki/index.php/Tutorial:_A_Model-View-Controller_in_Processing

//public class Rhythm_viewer extends PApplet {
//	Rhythm_controller controller = null;
//	Rhythm_model model = null;
//	//PFont f;
//	
//	/**
//	 * The MVC system is started from here.
//	 * PApplet will call this method first
//	 */
//	public void setUp(){
//		controller = new Rhythm_controller();
//		controller.initAll(this);
//	}
//
//	public void setModel(Rhythm_model m) {
//		this.model = m;
//		
//	}
//	
////	public void setup() {
////		  
////		  controller = new Rhythm_controller();
////		  controller.createNewTree("AB11001");
////		  size(200,200);
////		  f = createFont("Arial",16,true); // STEP 3 Create Font
////		}
////
////	public void draw() {
////		
////		background(255);
////		textFont(f,16);                 // STEP 4 Specify font to be used
////		fill(0);                        // STEP 5 Specify font color 
////		
////		
////		for(String next: controller.getTreeAsList()){
////			text(next + "\n", 10, 100);
////		}
////		
////		
////		//text("Hello Strings!",10,100);  // STEP 6 Display Text
////		}
//	
////	public void setup() {
////	    size(200,200);
////	    background(0);
////	  }
////
////	  public void draw() {
////	    stroke(255);
////	    if (mousePressed) {
////	      line(mouseX,mouseY,pmouseX,pmouseY);
////	    }
////	  }
//	  
//	  
//	  
//}
