import java.awt.Color;  
import java.awt.Component;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.RenderingHints;  
import javax.swing.border.LineBorder;  
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.Border;
  
  
  
class MyLineBorder extends LineBorder{  
  
  
    private static final long serialVersionUID = 1L;  
      
    public MyLineBorder(Color color, int thickness, boolean roundedCorners) {  
        super(color, thickness, roundedCorners);  
    }  
  
  
  
     public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {  
           
         RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,  
                                                RenderingHints.VALUE_ANTIALIAS_ON);   
         Color oldColor = g.getColor();  
         Graphics2D g2 = (Graphics2D)g;  
         int i;  
         g2.setRenderingHints(rh);  
         g2.setColor(lineColor);  
         for(i = 0; i < thickness; i++)  {  
         if(!roundedCorners)  
             g2.drawRect(x+i, y+i, width-i-i-1, height-i-i-1);  
         else  
             g2.drawRoundRect(x+i, y+i, width-i-i-1, height-i-i-1, 10, 10);   
         }  
         g2.setColor(oldColor);  
    }  
}  


class ImageArea extends JPanel {
	
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




 

class MessageWindow extends JDialog implements ActionListener,KeyListener{

	Container cp = getContentPane();
	JButton ok = new JButton("OK");
	JButton ok1 = new JButton("OK");
	JButton delete = new JButton("Delete");
	JButton edit = new JButton("Edit");
	JButton cancel = new JButton("Cancel");
	JTextArea jta1=new JTextArea();
	String name;
	String LabelName;
	String LabelName1;
	String LabelName2;
	JLabel jl1= new JLabel();
	//String LabelName3;
	boolean editLabel=false;
	boolean cancel1=false;
	boolean goOn = false;
	
	Vector<String> lname = new Vector<String>();
	
	public void setEdit(){
		//System.out.println("111");
		editLabel=true;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String a){
		LabelName=a;
	}
	
	public void setLname(Vector a){
		lname =a;
	}
	
	public String getDeleteLabelName(){
		return LabelName1;
	}
	public String getEditLabelName(){
		return LabelName2;
	}
	
	public MessageWindow(JFrame parent){
		super(parent,"Message Window",true);
	}
	public boolean getCancel(){
		return cancel1;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==cancel){
			dispose();
			cancel1=true;	
		}
		if(e.getSource()==ok1){
			dispose();
		}
		
		if(e.getSource()==ok){
			goOn=true;

			if(lname.size()>0){
				
				System.out.println("LNAME "+lname.elementAt(0));
				System.out.println("jta1: "+ jta1.getText());
				for(int i=0; i<lname.size();i++){
					if(jta1.getText().equals(lname.elementAt(i))){
						System.out.println("ffffff");
						jl1.setText("<HTML>Same Name!</HTML>");
						jl1.setFont(new java.awt.Font("Dialog",   0,   10));
						jl1.setForeground(Color.red);
						goOn=false;
					}
				}
				
			}
			
			if(jta1.getText().equals("")){
				jl1.setText("Can't be empty!");
				jl1.setFont(new java.awt.Font("Dialog",   0,   10));
				jl1.setForeground(Color.red);
				goOn=false;
			}
			if(goOn==true){
				//System.out.println("aa"+jta1.getText());
				name=jta1.getText();
				dispose();
			}
			
		}
		
		if(e.getActionCommand().equals("Delete")){
			System.out.println("Delete");
			LabelName1=LabelName;
			dispose();
		}
		if(e.getActionCommand().equals("Edit")){
			LabelName2=LabelName;
			dispose();
		}
	}

	public void LabelName(){
		if(editLabel==false){
			//System.out.println("create");
			cp.setLayout(new FlowLayout(FlowLayout.CENTER));
			cp.setLayout(new GridLayout(2,1));
			
			JPanel panel1 = new JPanel(new GridLayout(1,2));
			panel1.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
			panel1.add(new JLabel("Name of object:"));
			 MyLineBorder myLineBorder = new MyLineBorder(new Color(192, 192, 192), 1 , true); 
		     jta1.setBorder(myLineBorder); 
			panel1.add(jta1);
			cp.add(panel1);
			JPanel panel2 = new JPanel(new GridLayout(1,3));
			panel2.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
			panel2.add(new JPanel());
			panel2.add(ok);
			JPanel panel3 = new JPanel(new GridLayout(2,1));
			
			panel3.add(jl1);
			panel3.add(new JPanel());
			//panel3.add(new JPanel());
			panel2.add(panel3);
			cp.add(panel2);
			jta1.addKeyListener(this);
			ok.addActionListener(this);
			setSize(300,120);
		}
		
		if(editLabel==true){
			//System.out.println("edit");
			//System.out.println("create");
			cp.setLayout(new FlowLayout(FlowLayout.CENTER));
			cp.setLayout(new GridLayout(2,1));
			JPanel panel1 = new JPanel(new GridLayout(1,2));
			panel1.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
			panel1.add(new JLabel("Name of object:"));
			MyLineBorder myLineBorder = new MyLineBorder(new Color(192, 192, 192), 1 , true); 
		    jta1.setBorder(myLineBorder); 
		    jta1.setText(LabelName);
			panel1.add(jta1);
			cp.add(panel1);
			JPanel panel2 = new JPanel(new GridLayout(1,3));
			panel2.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
			panel2.add(new JPanel());
			panel2.add(ok);
			JPanel panel3 = new JPanel(new GridLayout(2,1));
			
			panel3.add(jl1);
			panel3.add(new JPanel());
//			panel3.add(new JPanel());
			panel2.add(panel3);
			cp.add(panel2);
			jta1.addKeyListener(this);
			ok.addActionListener(this);
			setSize(300,120);
			editLabel=false;
		}
	}
	
	public void editWindow(){
		cp.setLayout(new FlowLayout(FlowLayout.CENTER));
		cp.setLayout(new GridLayout(2,1));
		JPanel panel1 = new JPanel(new GridLayout(1,1));
		panel1.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 30));
		
		JLabel label1= new JLabel("Object: "+ LabelName);
		label1.setFont(new Font("Thoma", Font.BOLD,14));
		
		panel1.add(label1);
		cp.add(panel1);
		JPanel panel2 = new JPanel(new GridLayout(1,3));
		panel2.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		panel2.add(delete);
		panel2.add(edit);
		panel2.add(cancel);
		cp.add(panel2);
		edit.addActionListener(this);
		delete.addActionListener(this);
		cancel.addActionListener(this);
		setSize(300,125);
	}
	
	public void ClickWindow(){
		cp.setLayout(new FlowLayout(FlowLayout.CENTER));
		cp.setLayout(new GridLayout(2,1));
		cp.add(new JLabel("<HTML>At least 3 points <br>are needed to create an object</HTML>",JLabel.CENTER));
		JPanel panel = new JPanel(new GridLayout(1,3));
		panel.add(new JLabel(""));
		panel.add(ok1);
		panel.add(new JLabel(""));
		ok1.addActionListener(this);
		cp.add(panel);
		setSize(250,90);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		  if (e.getKeyChar() == KeyEvent.VK_ENTER){
			  
			  goOn=true;

				if(lname.size()>0){
					
					System.out.println("LNAME "+lname.elementAt(0));
					System.out.println("jta1: "+ jta1.getText());
					for(int i=0; i<lname.size();i++){
						if(jta1.getText().equals(lname.elementAt(i))){
							System.out.println("ffffff");
							jl1.setText("<HTML>Same Name!</HTML>");
							jl1.setFont(new java.awt.Font("Dialog",   0,   10));
							jl1.setForeground(Color.red);
							goOn=false;
						}
					}
					
				}
			  
			  if(jta1.getText().equals("")){
					jl1.setText("Can't be empty!");
					jl1.setFont(new java.awt.Font("Dialog",   0,   10));
					jl1.setForeground(Color.red);
					goOn=false;
					
					
				}
				if(goOn==true){
					name=jta1.getText();
					dispose();
				}

			  
		  }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyChar() == KeyEvent.VK_ENTER){
			jta1.setText("");
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}




class WindowGui extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

	Container cp = getContentPane();
	
	JMenuBar menuBar = new JMenuBar(); 
	JMenu fileMenu = new JMenu("File"); 
	JButton open = new JButton("Open"); 
	JButton save = new JButton("Save");
	JButton delete = new JButton("Erase"); 
	JButton confirmLable= new JButton("Confirm");
	JButton load = new JButton("load");
	
	JPanel panel1 = new JPanel(){  
        private static final long serialVersionUID = 1L;  
        
        protected void paintComponent(Graphics g) {  
            Graphics2D g2 = (Graphics2D) g;  
            super.paintComponent(g);  
            // === 
            g2.setPaint(new GradientPaint(0, 0, new Color(255,240,245), getWidth(),  getHeight(), new Color(245, 245, 245)));  
            g2.fillRect(0, 0, getWidth(), getHeight());  
        }  
    }; 
	
	JPanel panel2 = new JPanel(){  
        private static final long serialVersionUID = 1L;  

        protected void paintComponent(Graphics g) {  
            Graphics2D g2 = (Graphics2D) g;  
            super.paintComponent(g);  
            // === 
            g2.setPaint(new GradientPaint(0, 0, new Color(255,228,181), getWidth(),  getHeight(), new Color(250, 240, 230)));  
            g2.fillRect(0, 0, getWidth(), getHeight());  
        }  
    }; 
	JPanel panel3 = new JPanel(){  
        private static final long serialVersionUID = 1L;  

        protected void paintComponent(Graphics g) {  
            Graphics2D g2 = (Graphics2D) g;  
            super.paintComponent(g);  
            // === 
            g2.setPaint(new GradientPaint(0, 0, new Color(255,255,240), getWidth(),  getHeight(), new Color(205, 205, 193)));  

            // g2.setPaint(new GradientPaint(0, 0, new Color(135, 206, 235), getWidth(),  getHeight(), new Color(135, 206, 250)));  
            g2.fillRect(0, 0, getWidth(), getHeight());  
        }  
    }; 
	JPanel panel4 = new JPanel(){  
        private static final long serialVersionUID = 1L;  

        protected void paintComponent(Graphics g) {  
            Graphics2D g2 = (Graphics2D) g;  
            super.paintComponent(g);  
            // === 
            g2.setPaint(new GradientPaint(0, 0, new Color(255,255,240), getWidth(),  getHeight(), new Color(205, 205, 193)));  
            g2.fillRect(0, 0, getWidth(), getHeight());  
        }  
    }; 
	JLabel label1 = new JLabel();
	JLabel label2 = new JLabel();
	JLabel label3 = new JLabel();
	ImageArea ia = new ImageArea();
	JFileChooser chooser = new JFileChooser("."); 
	String s = "jpg,bmp,tga,vst,pcd,pct,gif,ai,fpx,img,cal,wi,png"; 
	ImageIcon icon; 
	FileInputStream readfile; 
	File file;
	String recordName;
	
	Vector<String> lname = new Vector<String>();
	int number=100;
	int labelNo=0;
	JLabel[] jl = new JLabel[number];
	
	String textx[];
	String texty[];
	String tx[];
	String ty[];
	String name[];
	String labelName;
	Vector<Vector> historyx=new Vector<Vector>();
	Vector<Vector> historyy=new Vector<Vector>();
	
	boolean isEntered;
	boolean Edit=false;
	
	// constructor
	public WindowGui(String name){
		super(name);
	}
	

	
	// create the window
	public void CreateWindow(){
		
		//set the whole window
		this.setSize(1000, 700); 
		cp.setLayout(null);
		cp.addMouseListener(this);
		this.setJMenuBar(menuBar); 
		this.setResizable(false);
		
		// the operating area
		panel1.setBounds(0, 0, 800, 600);
		panel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		label1= new JLabel("<HTML><br><font size=20 face=Thoma>Welcome to Image Labeling Application!</font><br><br></HTML>", JLabel.CENTER );
		label1.setPreferredSize(new Dimension(800,100));
		label2.setPreferredSize(new Dimension(700,400));
		label2.setText("<HTML>1. Button Open is used to open any image from your local directory.<br><br>" +
				"2. Button Save is used to save the polygons with annotations you added.<br><br>" +
				"3. Button Erase is used to erase the former operaton.<br><br>" +
				"4. Button Confirm is used to confirm object you labeled on the image.<br><br>" +
				"5. The annotation of Objects which you have labeled present on the right side, you can click them to delete or edit label</HTML>");
		label2.setFont(new   java.awt.Font("Dialog",   0,   20));   
		panel1.add(label1);
		panel1.add(label2);
		cp.add(panel1);
		
		// panel at the bottom
		panel2.setBounds(0, 600, 800, 100);
        panel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        label3.setText("Click button Open to start!!");
		label3.setFont(new   java.awt.Font("Dialog",   0,   20));   
        panel2.add(label3);
		cp.add(panel2);
		
		// panel at the right top includes the button
		panel3.setBounds(800, 0, 200, 150);
		panel3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		confirmLable.setPreferredSize(new Dimension(150,30));
		save.setPreferredSize(new Dimension(150,30));
		open.setPreferredSize(new Dimension(150,30));
		delete.setPreferredSize(new Dimension(150,30));
		panel3.add(open);
		panel3.add(save);
		panel3.add(delete);
		panel3.add(confirmLable);
		cp.add(panel3);	
		
		// panel at the right bottom includes the labels' names
		panel4.setBounds(800, 150, 200, 550);
		panel4.setLayout(new GridLayout(20,1));
		panel4.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		cp.add(panel4);

		// add action listener
		load.addActionListener(this);
		confirmLable.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		delete.addActionListener(this);
		// add mouse listener
		ia.addMouseListener(this);
		ia.addMouseMotionListener(this);
	}
	
	// void to check if there is saved data about the chosen picture.
	public void checkHistory(String a,String b, String c){
		String url = a;
		String url1= b;
		String url2 =c;
	    
	    try {
	    	FileReader read = new FileReader(new File(url));
	    	FileReader read1 = new FileReader(new File(url1));
	    	FileReader read2 = new FileReader(new File(url2));

	    StringBuffer sb = new StringBuffer();
	    StringBuffer sb1 = new StringBuffer();
	    StringBuffer sb2 = new StringBuffer();
	    
	  	char ch[] = new char[1024];
	    char ch1[] = new char[1024];
	    char ch2[] = new char[1024];
	    
	    int d = read.read(ch);
	    int d1 = read1.read(ch1);
	    int d2 = read2.read(ch2);

	    while(d!=-1){
	    	String str = new String(ch,0,d);
	   
	    	textx = str.split("\\\n");
	    
	    	sb.append(str);
	    
	    
	    	d = read.read(ch);
	    
	    
	    }
	    
	    while(d1!=-1){
	    	 String str1 = new String(ch1,0,d1);
	    	 texty = str1.split("\\\n");
	    	 sb1.append(str1);
	    	 d1=read1.read(ch1);
	    }
	    
	    while(d2!=-1){
	    	String str2 = new String(ch2,0,d2);
	   
	    	name = str2.split("\\\n");
	    
	    	sb2.append(str2);
	    
	    
	    	d2 = read2.read(ch2);
	    
	    
	    }
	    
	    for(int i=0;i<name.length;i++){
	    	lname.add(name[i]);
	    }
	    
	    for(int i=0;i<textx.length;i++){
	    	String tr= textx[i].substring(1, textx[i].length()-1);
	    	
	    	tx=tr.split("\\, ");
	    	String tr1= texty[i].substring(1, texty[i].length()-1);
	    	ty=tr1.split("\\, ");
	    	
	    	Vector<Integer> aa = new Vector<Integer>();
	    	Vector<Integer> bb = new Vector<Integer>();
	    	for(int j=0; j<tx.length;j++){

	    		aa.add(Integer.parseInt(tx[j]));
	    		bb.add(Integer.parseInt(ty[j]));
	    	}
	    	historyx.add(aa);
	    	historyy.add(bb);
	    	

	    }
	    
	    ia.setLabelx(historyx);
	    ia.setLabely(historyy);
	    printLabel();
	    	    
	    } catch (FileNotFoundException e){
	    
			ia.setLabelx(new Vector<Vector>());
			ia.setLabely(new Vector<Vector>());
			lname.removeAllElements();
			//ia.removeAll();
			printLabel();
	    
			e.printStackTrace();
	    
	    } catch (IOException e) {
	    
	    e.printStackTrace();
	    
	    }
	}
	
	public void addImageArea(){
		//the image area on which we can draw labels
		
		ia.setBounds(0, 0, 800, 600);
		cp.add(ia);
		ia.draw();
	}
	// when user want to add object
	public void addLabel(String a){
			lname.addElement(a);
			printLabel();
	}
	public void editLabel(int i, String a){
		lname.add(i, a);
		printLabel();
		Edit=false;
	}
	//repaint the label;
	public void printLabel(){

		panel4.removeAll();
		panel4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		for(int j=0;j<lname.size();j++){
			jl[j] = new JLabel(lname.elementAt(j), JLabel.CENTER);
			panel4.add(jl[j]);
			jl[j].addMouseListener(this);

		}
		cp.add(panel4);
		panel4.validate();
		panel4.repaint();
	}
	
	// delete the object name on the panel4
	public void deleteLabel(int i){
		lname.removeElementAt(i);
	
		printLabel();
	}
	
	
	//the main method
	public static void main(String args[]) throws Exception {
		WindowGui screen = new WindowGui("Image Labeling");
		screen.CreateWindow();
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setVisible(true);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// click the image area
		if(e.getSource()==ia){
			if(Edit==false){
				int x = e.getX();
				int y = e.getY();	
				ia.setCoordinate(x, y);
				addImageArea();
				label3.setText("<HTML>The coordinate you clicked: ("+x+","+y+").<br> If you compeleted, please press confirm button</HTML>");
			}
			
		}

		// when customer click the label 
		
		for(int i=0;i<lname.size();i++){
			if(e.getSource()==jl[i]){
		
				ia.setEditPosition(i);
				addImageArea();
				System.out.println("Clicked: "+ i);
				
				label3.setText("You can either delete or edit the labeled object.");
				MessageWindow editWindow = new MessageWindow(null);
				editWindow.setName(lname.elementAt(i));
				labelName=lname.elementAt(i);
				editWindow.editWindow();
				editWindow.setLocation(500, 250);

				editWindow.show();
				
				if(editWindow.getCancel()==true){
					ia.setCheck();
					addImageArea();
				}
				

				for(int j=0;j<lname.size();j++){
					// if user want to delete the label
					if((lname.elementAt(j)).equals(editWindow.getDeleteLabelName()) ){
						ia.setCheck();
						ia.setDeletePosition(j);
						addImageArea();
						deleteLabel(j);
						
						label3.setText("Delete Successfully! Start to label a new Object.");
						break;
					}
					if((lname.elementAt(j)).equals(editWindow.getEditLabelName()) ){
						Edit=true;
						// if user want to edit label
						ia.setEditPosition(j);
						addImageArea();
						label3.setText("You can drag the point to edit.Click confirm when you finish modification");
						break;
					}
				}
					
			}
		}		
	}
	
    // change the cunsor when mouse in different area.
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

		for(int i=0;i<lname.size();i++){
			if(e.getSource()==jl[i]){
				
				this.setCursor(new   Cursor(Cursor.HAND_CURSOR)); 
				jl[i].setText("<HTML><U><b>"+jl[i].getText()+"</b></U></HTML>");
			}
		}
		if(e.getSource()==ia){
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		for(int i=0;i<lname.size();i++){
			if(e.getSource()==jl[i]){
				jl[i].setText(lname.elementAt(i));
			}
		}	
	}

	
	// invoked when dragge the label
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int x=e.getX();
		int y = e.getY();
		ia.setDragged(x, y);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int x=0;
		// when confirm button clicked
		if(e.getSource()==confirmLable){
	
			if(ia.clickable()==true||Edit==true){
				MessageWindow LabelName = new MessageWindow(null);
				LabelName.setLname(lname);
				if(Edit==true){
					LabelName.setName(labelName);
					LabelName.setEdit();
					for(int i=0;i<lname.size();i++){
						if(lname.elementAt(i).equals(labelName)){
							lname.removeElementAt(i);
							x=i;
						}
					}
				}
				LabelName.LabelName();
				LabelName.setLocation(500, 250);
				LabelName.show();
				if(Edit==false){
					ia.setCoordinate(-1, -1);
					addImageArea();
					label3.setText("The label is stored. You can start to label a new object.");
					addLabel(LabelName.getName());
				}
				if(Edit==true){
					ia.setCheck();
					editLabel(x,LabelName.getName());
					label3.setText("Modify Successfully! Start to label a new Object.");

				}
			}
			// when there are less than three points, confirm label can not be pressed
			else{
				MessageWindow notClickable = new MessageWindow(null);
				notClickable.ClickWindow();
				notClickable.setLocation(500, 200);
				notClickable.show();
			}
		
		}
		
		// erase button is used to delete the operation in last step
		if(e.getSource()==delete){
			ia.deletePoint();
			addImageArea();
			label3.setText("You can restart now!");
		}
		
		// when you click open button, open any file in the computer.
		if(e.getSource()==open){
			int state = chooser.showOpenDialog(null); 
			file = chooser.getSelectedFile(); 
			if (file != null && state == JFileChooser.APPROVE_OPTION) { 
			   try { 
			    String fileName = file.getName(); 
			    String a[] = fileName.split("\\.");
			    recordName=a[0];
			    if(s.indexOf(fileName.substring
			    		(fileName.lastIndexOf(".") + 1).toLowerCase()) != -1) { 
			     icon = new ImageIcon(file.getAbsolutePath());
			     icon.setImage(icon.getImage().getScaledInstance(800,600,Image.SCALE_AREA_AVERAGING));	
			     ia.setImage(icon);			     			     
			    } 
			    setTitle(chooser.getSelectedFile().getName()); 
			    checkHistory(recordName+"x.txt",recordName+"y.txt",recordName+".txt");

			   } catch (Exception e1) { 
			   e1.printStackTrace(); 
			   } 
			} 

			addImageArea();
			
			label3.setText("You can start to label the object!");
		}
		
		// when press the save item in file menu
		if(e.getSource()==save){
			
	    	String a= recordName+"x.txt";
	        String b = recordName+"y.txt";
	        String c = recordName+".txt";
	        
	        try{
	   
	        File file = new File(a);
	        File file1 = new File(b);
	        File file2 = new File(c);
	        
	        
	        if (file.exists()) {
	        
	        file.delete();
	        
	        }
	        if (file1.exists()) {
		        
		        file1.delete();
		    }
	        if (file2.exists()) {
		        
		        file2.delete();
		    }
	        
	        file.createNewFile();
	        file1.createNewFile();
	        file2.createNewFile();
	        
	        BufferedWriter output = new BufferedWriter(new FileWriter(file));
	        BufferedWriter output1 = new BufferedWriter(new FileWriter(file1));
	        BufferedWriter output2 = new BufferedWriter(new FileWriter(file2));
	        
	        for (int i=0 ;i<ia.getLabelx().size(); i++) {
	        
	        output.write(String.valueOf(ia.getLabelx().get(i)) + "\n");
	        output1.write(String.valueOf(ia.getLabely().get(i)) + "\n");
	        
	        }
	        
	        for(int j=0; j<lname.size();j++){
		        output2.write(String.valueOf(lname.get(j)) + "\n");
	        }
	        
	        output2.close();
	        output1.close();
	        output.close();
	        
			label3.setText("Saved successfully!");

	        
	        } catch (Exception ex) {
	        
	        System.out.println(ex);
	        
	        }
		}
		
	}
	//dragge the point
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int x=e.getX();
		int y = e.getY();
		ia.dragged(x, y);
//		//ia.setDragged(x, y);
		addImageArea();
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
