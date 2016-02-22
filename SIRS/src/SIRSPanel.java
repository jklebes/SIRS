import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class SIRSPanel extends JPanel{

	private Grid l;
	private int height;
	private int width;

	public SIRSPanel(Grid l){
		this.l=l;
		this.width = l.getwidth();
		this.height=l.getheight();
	}

	public void paintComponent(Graphics g) {
		for (int i=0; i < height; i++){
			for (int j=0; j < width; j++){
				g.setColor(getColor(i,j,l));
				g.fillRect(5+j*5, 5+ i*5, 5,5);
			}
		}
	}

	private Color getColor(int i, int j, Grid l) {
		if (l.getStatus(i,j)==0){return Color.white;}
		else if (l.getStatus(i,j)==1){return Color.black;}
		else if (l.getStatus(i,j)==2){return Color.red;}
		else{return Color.magenta;}
	}  




}
