import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import javax.swing.*;

import com.lgj.myLineBorder.MyLineBorder;

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

import java.awt.Color;  
import java.awt.Component;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.RenderingHints;

 

public class MessageWindow extends JDialog implements ActionListener,KeyListener{

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
