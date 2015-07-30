package rhythm.analysis.view;

/*
Referenced
http://www.gicentre.net/utils/multiwindow/
http://www.sojamo.de/libraries/controlP5/
http://cs.smith.edu/dftwiki/index.php/Tutorial:_A_Model-View-Controller_in_Processing
http://forum.processing.org/two/discussion/1368/controlp5-buttons-controls-trigger-automatically-on-sketch-start
*/

//TO DO look into performance.  I.e when new string analysed other screen should close and their objects be destroyed
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
	
	private String textValue = "";
	private ControlP5 cp5;
	private Textarea myTextarea;
	
	private Rhythm_controller controller = null;
	private Rhythm_model model = null;
	
	private PopupWindow arcViewWindow = null;
	private Arc_viewer arcView = null;
	
	//Nested windows
	private PopupWindow cycleViewWindow = null;
	private Cycle_viewer cycleViewer;
		
	/**
	 * setup() called first
	 */
	public void setup() {  
		//The size of UI screen
		size(600,600);
		
		//MVC system started here
		controller = new Rhythm_controller();
		controller.initAll(this);
		
		//Start second windows
		arcView = new Arc_viewer();
		arcViewWindow = new PopupWindow(this, arcView); 
		
		
		//Start cycle viewer window
	    cycleViewer = new Cycle_viewer(); 
	    cycleViewWindow = new PopupWindow(this, cycleViewer);
		
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
			.setPosition(20,175)
			.setSize(200,19)
			.setBroadcast(true);
			
		
		cp5.addButton("showArcTree")
		.setBroadcast(false)
		.setValue(100)
		.setPosition(20,225)
		.setSize(200,19)
		.setBroadcast(true);
		
		cp5.addButton("showCycleViewer")
		.setBroadcast(false)
		.setValue(100)
		.setPosition(20,275)
		.setSize(200,19)
		.setBroadcast(true);
		
		
 // add a vertical slider
		cp5.addSlider("slider")
		.setBroadcast(false)
		.setPosition(20,315)
		.setSize(200,20)
		.setRange(1,10)
		.setNumberOfTickMarks(10)
		.setValue(1)
		.setBroadcast(true);
	     
	  // use Slider.FIX or Slider.FLEXIBLE to change the slider handle
	  // by default it is Slider.FIX
	    
		//text area
		myTextarea = cp5.addTextarea("txt")
	    .setPosition(20,350)
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
		arcViewWindow.setVisible(true);
	}
	
	public void showCycleViewer(){
		cycleViewer.setOnsets(new int[]{1,10,15});
		cycleViewWindow.setVisible(true);
		
	}
	
	
	public void slider(int arcMinimum) {
		println("a slider event. setting min arc to " + arcMinimum);
		arcView.setArcMinimum(arcMinimum);
		arcView.redraw();
	}
	
}
	
