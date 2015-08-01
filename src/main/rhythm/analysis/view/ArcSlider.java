package rhythm.analysis.view;

public class ArcSlider {
	private Arc_viewer arcViewer;
	
	private int slider;// = 0;
	private final int leftMin;
	private final int rightMax;
	
	private int sliderxPixels;
	private int sliderWidth;
	private int sliderRadius;
	
	private float slider1offset;// = (float) 0.0;
	private boolean overSlider;// = false;
	private boolean sliderlocked;// = false;
	
	public ArcSlider(Arc_viewer arcViewer, int sliderWidth, int leftMin, int rightMax, int startX){
		this.arcViewer = arcViewer;
		
		this.leftMin = leftMin;
		this.rightMax = rightMax;
		
		this.sliderWidth = sliderWidth;
		this.sliderRadius = sliderWidth / 2;
		this.sliderxPixels = startX;
		this.slider = 0;
		this.slider1offset = (float) 0.0;
		overSlider = false;
		sliderlocked = false;
	
	}
	
	public void setSlider(int position){
		this.slider = (int) ((this.sliderxPixels - arcViewer.screenBorder) / arcViewer.getLineSubdivision());
	}
	
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
	 
	 public void setOffsetIfLocked(int mouseX){
		 if(overSlider){
			 //System.out.println("click");
			 sliderlocked = true; 
			 slider1offset = mouseX - sliderxPixels;   
		 } else {
			 sliderlocked = false;
		 }
	 }
	 
	 public void setSliderPixels(int mouseX){
		 if(sliderlocked) {
			 if(mouseX >= this.rightMax){
				 sliderxPixels = this.rightMax;
			 } else if(mouseX <= this.leftMin){
				 sliderxPixels = this.leftMin;
			 } else {
				 sliderxPixels = mouseX;
			 }
			 this.slider = getXPosition(sliderxPixels);
		 } 
	 }
	 
	 private int getXPosition(int xPixels){
		 return (int) ((xPixels - arcViewer.screenBorder) / arcViewer.getLineSubdivision());		 
	 }
	 
	 public int getXPixels(){
		 return this.sliderxPixels;
	 }
	 
	 public int getWidth(){
		 return this.sliderWidth;
	 }
	 
	 public int getSlider(){
		 return this.slider;
	 }
	 
	 public void lockSlider(){
		 this.sliderlocked = false;
	 }
	 

}
