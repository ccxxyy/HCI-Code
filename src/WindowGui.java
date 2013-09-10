import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class WindowGui extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

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
