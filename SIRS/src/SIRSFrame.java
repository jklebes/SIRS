import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class SIRSFrame extends JFrame{

	private Grid l;
	private int height;
	private int width;
	SIRSPanel pan ;

	public SIRSFrame(Grid l){
		this.l=l;
		this.width = l.getwidth();
		this.height=l.getheight();
		this.pan = new SIRSPanel(l);
		this.add(pan);
		this.setSize(5*width+10,5*height+10+ 30);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void step(){
		pan.repaint();
	}


}
