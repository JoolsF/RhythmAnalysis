package rhythm.analysis.view;

/*
Referenced
http://www.gicentre.net/utils/multiwindow/
http://www.sojamo.de/libraries/controlP5/
http://cs.smith.edu/dftwiki/index.php/Tutorial:_A_Model-View-Controller_in_Processing
http://forum.processing.org/two/discussion/1368/controlp5-buttons-controls-trigger-automatically-on-sketch-start
*/

//TO DO look into performance.  I.e when new string analysed other screen should close and their objects be destroyed
//TO DO look into slider graphics versus output when string length > 50.  Scale and ouput not matching
import processing.core.*;

import org.gicentre.utils.multisketch.*;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Slider;
import controlP5.Textarea;
import controlP5.Textfield;
import rhythm.analysis.control.Rhythm_controller;
import rhythm.analysis.model.Rhythm_model;

public class Main_viewer extends PApplet{
	private static final long serialVersionUID = 1L;
	
	private ControlP5 cp5;
	private Textarea myTextarea;
	
	private Rhythm_controller controller = null;
	
	private PopupWindow arcViewWindow = null;
	private Arc_viewer arcView = null;	
	
	Textfield textfield;
		
	public Main_viewer(){
		//MVC system started here
		controller = new Rhythm_controller(this);	
		//Start second windows
		arcView = new Arc_viewer(controller);
		arcViewWindow = new PopupWindow(this, arcView); 
	}	
	
	/**
	 * setup() called immediately after constructor
	 */
	public void setup() {  
		size(350,600);	  
		//Fontsetup
		PFont font = createFont("arial",20);
		textFont(font);
		cp5 = new ControlP5(this);
		
		//Input textfield 1
		cp5.addTextfield("input")
			.setPosition(20,50)
		 	.setSize(200,40)
		 	.setFont(font)
		 	.setFocus(true)
		 	.setColor(color(255,0,0)
		 	);
		
		//Button
		cp5.addButton("clear_data")
			.setBroadcast(false)	
			.setValue(100)
			.setPosition(20,125)
			.setSize(200,19)
			.setBroadcast(true);
			
		
		cp5.addButton("showArcTree")
		.setBroadcast(false)
		.setValue(100)
		.setPosition(20,175)
		.setSize(200,19)
		.setBroadcast(true);	
		
		// Slider
		cp5.addSlider("slider")
		.setBroadcast(false)
		.setPosition(20,225)
		.setSize(200,20)
		.setRange(1,10)
		.setNumberOfTickMarks(10)
		.setValue(1)
		.setBroadcast(true);
	     
	    
		//Text area
		myTextarea = cp5.addTextarea("txt")
	    .setPosition(20,300)
	    .setSize(275,275)
	    .setFont(createFont("arial",12))
	    .setLineHeight(14)
	    .setColor(color(128))
	    .setColorBackground(color(255,100))
	    .setColorForeground(color(255,100));				
	}
		

	public void draw() {
		background(0);
		fill(255);
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
	 * Automatically receives results from controller input
	 * @param theText
	 */
	public void input(String theText) {
		// 
		println("a textfield event for controller 'input' : "+theText);
		this.controller.updateTree(theText);
		this.myTextarea.setText(controller.getTreeAsList().toString());
		arcView.redraw();
		
	}
	
	//BUTTONS
	public void clear_data(){
		this.myTextarea.setText("Data cleared");
		this.controller.resetModel();
	}
	
	public void showArcTree(){
		arcViewWindow.setVisible(true);
	}
	
	
	public void slider(int arcMinimum) {
		println("a slider event. setting min arc to " + arcMinimum);
		arcView.setArcMinimum(arcMinimum);
		arcView.redraw();
	}
	
}
	
