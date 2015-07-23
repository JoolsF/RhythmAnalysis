package rhythm.analysis.view;
import processing.core.*;
import org.gicentre.utils.multisketch.*;

public class Arc_viewer extends EmbeddedSketch {
	private static final long serialVersionUID = 1L;
	
	

	 float textScale;  
	 
	  // Initialises the sketch ready to display some animated text.
	 public void setup() 
	  {
	    size(500, 500);
	    textFont(createFont("SansSerif", 24), 24);
	    textAlign(CENTER, CENTER);
	    fill(20, 120, 20);
	    textScale = 0;
	  }
	 
	  // Displays some text and animates a change in size.
	 public  void draw() 
	  { 
	    super.draw();   // Should be the first line of draw(). 
	    background(200, 255, 200); 
	 
	    pushMatrix(); 
	    translate(width/2, height/2); 
	    scale(5); 
	    text("Hello again", 0, 0); 
	    popMatrix(); 
	 
	    textScale += 0.02;
	  }

}
