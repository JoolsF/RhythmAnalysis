package rhythm.analysis.view;

public class ArcSlider {
	private Arc_viewer arcViewer;
	
	private int sliderWidth;
	private int sliderRadius;
	private int sliderxPixels;// = 0 + screenBorder;
	private int slider;// = 0;
	private float slider1offset;// = (float) 0.0;
	private boolean overSlider;// = false;
	private boolean sliderlocked;// = false;
	
	public ArcSlider(Arc_viewer arcViewer, int sliderWidth, int xOffset){
		this.arcViewer = arcViewer;
		this.sliderWidth = sliderWidth;
		this.sliderRadius = sliderWidth / 2;
		this.sliderxPixels = xOffset;
		this.slider = 0;
		this.slider1offset = (float) 0.0;
		overSlider = false;
		sliderlocked = false;
	
	}
	
	public void setSlider(int position){
		this.slider = (int) ((this.sliderxPixels - arcViewer.getScreenBorder()) / arcViewer.getLineSubvision());
	}


}
