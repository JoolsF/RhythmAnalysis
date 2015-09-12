package rhythm.analysis.view;

/**
 * ArcSlider represents an arc slider to be used in the ArcViewer class in a "left, right" pair
 * @author Julian Fenner
 *
 */
public class ArcSlider {
	private ArcViewer arcViewer;
	
	private int slider;// = 0;
	private int leftMin;
	private  int rightMax;
	
	private int sliderxPixels;
	private int sliderWidth;
	private int sliderRadius;
	
	private float slider1offset;
	private boolean overSlider;
	private boolean sliderlocked;
	
	/*-----------------------------------------------------------------------------------------
	 * Constructor
	 *----------------------------------------------------------------------------------------*/
	public ArcSlider(ArcViewer arcViewer, int sliderWidth, int leftMin, int rightMax, int startX){
		this.arcViewer = arcViewer;
		
		this.leftMin = leftMin;
		this.rightMax = rightMax;
		
		this.sliderWidth = sliderWidth;
		this.sliderRadius = sliderWidth / 2;
		this.sliderxPixels = startX;
		setSlider();
		this.slider1offset = (float) 0.0;
		overSlider = false;
		sliderlocked = false;
	
	}
	/**
	 * Check if mouse is over this slider.  If it is is sets overSlider to true else it sets overSlider
	 * to false
	 * @param mouse x position in pixels
	 * @param mouse y position in pixels
	 */
	public void checkSetOverSlider(int mouseX, int mouseY){
		if(mouseY >= arcViewer.screenMidY - sliderRadius  && 
			mouseY <= arcViewer.screenMidY + sliderRadius  && 
		    mouseX >=  sliderxPixels - sliderRadius  && 
			mouseX <=  sliderxPixels + sliderRadius){
			overSlider = true;
			} else {
			overSlider = false;
		}
	 }
	

	 /**
	  * Sets slider offset if slider locked
	  * @param mouseX
	  */
	public void setOffsetIfLocked(int mouseX){
		if(overSlider){
			//System.out.println("click");
			sliderlocked = true; 
			slider1offset = mouseX - sliderxPixels;   
		 } else {
			 sliderlocked = false;
		 }
	 }
	 
	/**
	 * Sets slider position in pixels
	 * @param mouse x position
	 * @param the minimum left slider position
	 * @param the maximum right slider position
	 * 
	 */
	 public void setSliderPixels(int mouseX, int leftMin, int rightMax){
		 if(sliderlocked) {
			 if(mouseX >= rightMax){
				 sliderxPixels = rightMax - sliderWidth;
			 } else if(mouseX <= leftMin){
				 sliderxPixels = leftMin + sliderWidth;
			 } else {
				 sliderxPixels = mouseX;
			 }
			 setSlider();
		 } 
	 }
	 
	 /**
	  * Get slider.  No parameters required
	  */
	 public void setSlider(){
		 this.slider = getXPosition(sliderxPixels); 
	 }
	/*
	 * Helper method for setSlider 
	 */
	 private int getXPosition(int xPixels){
		 return (int) ((xPixels - arcViewer.screenBorder) / arcViewer.getLineSubdivision());		 
	 } 
	 /**
	  * Gets the x position of the slider in pixels
	  * @return the x position of the slider
	  */
	 public int getXPixels(){
		 return this.sliderxPixels;
	 }
	 
	 /**
	  * Get the width of the slider
	  * @return the width of the slider
	  */
	 public int getWidth(){
		 return this.sliderWidth;
	 }
	 
	 /**
	  * Get slider position
	  * @return the slider position
	  */
	 public int getSlider(){
		 return this.slider;
	 }
	 /**
	  * Locks the slider
	  */
	 public void lockSlider(){
		 this.sliderlocked = false;
	 }
	 
	 /**
	  * Gets the leftmost position that the slider can move to 
	  * @return the leftmost position that the slider can move to
	  */
	 public int getLeftMin(){
		 return this.leftMin;
	 }
	 /**
	  * Gets the rightmost position that the slider can move to 
	  * @return the rightmost position that the slider can move to
	  */
	 public int getRightMax(){
		 return this.rightMax;
	 }

}
