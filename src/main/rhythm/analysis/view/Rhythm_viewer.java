package rhythm.analysis.view;

/*
Referenced
http://www.gicentre.net/utils/multiwindow/
http://www.sojamo.de/libraries/controlP5/
http://cs.smith.edu/dftwiki/index.php/Tutorial:_A_Model-View-Controller_in_Processing
http://forum.processing.org/two/discussion/1368/controlp5-buttons-controls-trigger-automatically-on-sketch-start
*/
import processing.core.*;

import org.gicentre.utils.multisketch.*;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textarea;
import controlP5.Textfield;
import rhythm.analysis.control.Rhythm_controller;
import rhythm.analysis.model.Rhythm_model;

public class Rhythm_viewer extends PApplet{
	private static final long serialVersionUID = 1L;
	
	String textValue = "";
	ControlP5 cp5;
	Textarea myTextarea;
	
	Rhythm_controller controller = null;
	Rhythm_model model = null;
	
	PopupWindow arcView = null;
	
	/**
	 * setup() called first
	 */
	public void setup() {  
		//The size of UI screen
		size(900,600);
		
		//MVC system started here
		controller = new Rhythm_controller();
		controller.initAll(this);
		

		//Start second windows
		arcView = new PopupWindow(this, new Arc_viewer()); 

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
		
		//button
		cp5.addButton("print_tree")
			.setBroadcast(false)	
			.setValue(100)
			.setPosition(20,200)
			.setSize(200,19)
			.setBroadcast(true);
			
		
		cp5.addButton("showArcTree")
		.setBroadcast(false)
		.setValue(100)
		.setPosition(20,300)
		.setSize(200,19)
		.setBroadcast(true);
		
		
		
		//text area
		myTextarea = cp5.addTextarea("txt")
	    .setPosition(20,400)
	    .setSize(200,200)
	    .setFont(createFont("arial",12))
	    .setLineHeight(14)
	    .setColor(color(128))
	    .setColorBackground(color(255,100))
	    .setColorForeground(color(255,100));		
		
		
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
	 * @param theText
	 */
	public void input(String theText) {
		// automatically receives results from controller input
		println("a textfield event for controller 'input' : "+theText);
		this.controller.createNewTree(theText);
	}
	
	//BUTTONS
	
	//Takes input from print_tree button and prints some text to screen
	public void print_tree(){
		//throws java.lang.reflect.InvocationTargetException
		this.myTextarea.setText(controller.getTreeAsList().toString());	
	}
	
	public void showArcTree(){
		
		arcView.setVisible(true);
	}
	
	
}
	
