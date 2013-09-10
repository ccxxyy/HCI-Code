import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class ImageArea extends JPanel {
	
	Vector<Integer> xpoint = new Vector<Integer>();
	Vector<Integer> ypoint = new Vector<Integer>();
	Vector<String> name = new Vector<String>();
	Vector<Vector> labelx= new Vector<Vector>();
	Vector<Vector> labely= new Vector<Vector>();
	ImageIcon icon;
	int gg =0;
	int editLabel;
	int draggedPosition1;
	int draggedPosition2;
	int signal=0;
	int draggedX=0;
	int draggedY=0;
	int deletePosition=-1;
	boolean check=true;
	boolean dragged = false;
	boolean edit = false;

	// make the area not able to edit
	public void setCheck(){
		edit=false;
	}
	// check whether confirm button can be pressed
	public boolean clickable(){
		if(xpoint.size()>2){
			return true;
		}
		else{
			return false;
		}
	}
	
	// check whether the point can be dragged
	public void setDragged(int x, int y){
		dragged=false;
		if(edit==true){
			if(editLabel>labelx.size()-1){
				dragged=false;
			}
			else{
				System.out.println("editLabel is: "+ editLabel);
				for(int j=0;j<labelx.elementAt(editLabel).size();j++){
					if((x<=(Integer)labelx.elementAt(editLabel).elementAt(j)+10&&x>=(Integer)labelx.elementAt(editLabel).elementAt(j)-10)&&
							(y<=(Integer)labely.elementAt(editLabel).elementAt(j)+10&&y>=(Integer)labely.elementAt(editLabel).elementAt(j)-10)){
						dragged=true;
						draggedPosition1=editLabel;
						draggedPosition2=j;
					}
				}
			}

		}
		
	}
	// make change to the vectors, which will be print
	public void dragged(int x, int y){
		if(dragged==true){
			draggedX=x;
			draggedY=y;
			System.out.println("fff"+draggedPosition1);
			labelx.elementAt(draggedPosition1).removeElementAt(draggedPosition2);
			labelx.elementAt(draggedPosition1).add(draggedPosition2, draggedX);
			//System.out.println(labelx);
			labely.elementAt(draggedPosition1).removeElementAt(draggedPosition2);
			labely.elementAt(draggedPosition1).add(draggedPosition2, draggedY);
			signal = 3;
		}

	}
	// delete the last point, invoked when erase button clicked
	public void deletePoint(){
		xpoint.remove(xpoint.size()-1);
		ypoint.remove(ypoint.size()-1);
	}
	
	// add the point to the vector
	public void setCoordinate(int x,int y){
		check = true;
		for(int i=0;i<xpoint.size();i++){
			if((x<=xpoint.elementAt(i)+10 && x>=xpoint.elementAt(i)-10)&&(y<=ypoint.elementAt(i)+10&&y>=ypoint.elementAt(i)-10)){
				signal = 2;
				check=false;
				gg=i;
			}
		}	
		if(check==true){
			if(x>0&&y>0){
				xpoint.add(x);
				ypoint.add(y);
				signal=0;
			}
			if(x==-1&&y==-1){
				signal=1;
			}
		}
		

	}
	
	// get the loaded image
	public void setImage(ImageIcon a){
		icon = a;
	}
	
	public void setDeletePosition(int i){
		labelx.removeElementAt(i);
		labely.removeElementAt(i);
	}
	
	public void setEditPosition(int i){
		editLabel = i;
		edit= true;
	}
	
	public Vector getLabelx(){
		return labelx;
	}
	public Vector getLabely(){
		return labely;
	}
	
	public void setLabelx(Vector a){
		labelx=a;
	}
	public void setLabely(Vector y){
		labely=y;
	}
	
	public void setxypoint(Vector a, Vector b){
		
	}
	
	public void draw(){
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		// draw the image
		g.drawImage(icon.getImage(),0,0,800,600,null);		
		// draw the past label
		if(labelx.size()>0){
			for(int i=0; i<labelx.size();i++){
				for(int j=0; j<labelx.elementAt(i).size()-1;j++){
					g.setColor(Color.yellow);
				
					g.fillOval((Integer)labelx.elementAt(i).elementAt(j)-5,(Integer)labely.elementAt(i).elementAt(j)-5, 10, 10);
					g.drawLine((Integer)labelx.elementAt(i).elementAt(j), (Integer)labely.elementAt(i).elementAt(j), (Integer)labelx.elementAt(i).elementAt(j+1), (Integer)labely.elementAt(i).elementAt(j+1));
				}
				g.fillOval((Integer)labelx.elementAt(i).lastElement()-5,(Integer)labely.elementAt(i).lastElement()-5, 10, 10);

				g.drawLine((Integer)labelx.elementAt(i).firstElement(), (Integer)labely.elementAt(i).firstElement(), (Integer)labelx.elementAt(i).lastElement(), (Integer)labely.elementAt(i).lastElement());
			}
		}
		
			//draw the current label
			if(xpoint.size()>0){
								
				g.setColor(Color.green);
				
				g.fillOval(xpoint.lastElement()-5, ypoint.lastElement()-5, 10, 10);
				
				for(int i=0; i<xpoint.size()-1;i++){
					g.setColor(Color.green);
					g.fillOval(xpoint.elementAt(i)-5, ypoint.elementAt(i)-5, 10, 10);
					g.drawLine(xpoint.elementAt(i), ypoint.elementAt(i), xpoint.elementAt(i+1), ypoint.elementAt(i+1));
				}

			}
	
	
		if(signal==2){
			g.setColor(Color.green);
			g.drawLine(xpoint.elementAt(gg), ypoint.elementAt(gg), xpoint.lastElement(), ypoint.lastElement());
			signal = 0;
			edit=false;
		}
		
		// when confirm button clicked
		if(signal==1){
			g.setColor(Color.GREEN);
			g.drawLine(xpoint.firstElement(), ypoint.firstElement(), xpoint.lastElement(), ypoint.lastElement());
			labelx.add(xpoint);
			labely.add(ypoint);
			xpoint= new Vector<Integer>();
			ypoint= new Vector<Integer>();
			signal=0;
		}
		
		// if imageArea is editable.
		if(edit==true){
				for(int j=0; j<labelx.elementAt(editLabel).size()-1;j++){
				g.setColor(Color.red);
				g.fillOval((Integer)labelx.elementAt(editLabel).elementAt(j)-5,(Integer)labely.elementAt(editLabel).elementAt(j)-5, 10, 10);
				g.drawLine((Integer)labelx.elementAt(editLabel).elementAt(j), (Integer)labely.elementAt(editLabel).elementAt(j), (Integer)labelx.elementAt(editLabel).elementAt(j+1), (Integer)labely.elementAt(editLabel).elementAt(j+1));
			}
			g.fillOval((Integer)labelx.elementAt(editLabel).lastElement()-5,(Integer)labely.elementAt(editLabel).lastElement()-5, 10, 10);

			g.drawLine((Integer)labelx.elementAt(editLabel).firstElement(), (Integer)labely.elementAt(editLabel).firstElement(), (Integer)labelx.elementAt(editLabel).lastElement(), (Integer)labely.elementAt(editLabel).lastElement());
		}
		
	}
	
}
