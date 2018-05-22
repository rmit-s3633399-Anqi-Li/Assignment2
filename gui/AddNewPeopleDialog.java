package com.socialnetwork.assign2.ui;
/**
 * @author Anqi Li s3633399
 */
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.socialnetwork.assign2.Helper;
import com.socialnetwork.assign2.dao.Adult;
import com.socialnetwork.assign2.dao.Child;
import com.socialnetwork.assign2.dao.Person;
import com.socialnetwork.assign2.database.DataBase;

public class AddNewPeopleDialog extends  JDialog {
    /******************************* components Definition **************************/
    private JLabel nameLb=new JLabel("Name:");
    private JLabel ageLb=new JLabel("Age:");
    private JLabel genderLb=new JLabel("Gender:");
    private JLabel stateLb=new JLabel("State:");
    private JLabel statusLb=new JLabel("Status:");
    
    private JTextField nameTF = null;
    private JTextField ageTF = null;
    private JComboBox genderComboBox = null;
    private JComboBox stateComboBox = null;
    private JTextField statusTF = null;
    
    private JButton btAdd=new JButton("Add");
    private JButton btExit=new JButton("Close");
    public AddNewPeopleDialog(JFrame frm) {
        /*********************** UI initialization ***************************/
        super(frm,true);
        this.setLayout(new GridLayout(6,2));
        this.add(nameLb);
        this.add(nameTF = createJTextField());
        this.add(ageLb);
        this.add(ageTF = createAgeJTextField());
        this.add(genderLb);
        this.add(genderComboBox = createGenderJcomboBox());
        this.add(stateLb);
        this.add(stateComboBox = createStateJcomboBox());
        this.add(statusLb);
        this.add(statusTF = createJTextField());

        this.add(btAdd);
        this.add(btExit);
        this.setSize(240,200);
        Helper.toCenter(this);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        /******************* add action listener *******************************/
        btAdd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				

				String name = nameTF.getText();
				String photoPath = "";
				int age = Integer.parseInt(ageTF.getText());
				String gender = genderComboBox.getSelectedItem().toString();
				String state = stateComboBox.getSelectedItem().toString();
				String status = statusTF.getText();
				
				Person newPerson = null;
            	if(age > 16){
            		newPerson = new Adult(name,photoPath,status,gender,age,state);
            	}else{
            		newPerson = new Child(name,photoPath,status,gender,age,state);
            	}
				DataBase.getInstance().addNewUser(newPerson);
				AddNewPeopleDialog.this.dispose();
			}
        	
        });
        btExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				AddNewPeopleDialog.this.dispose();
			}
        	
        });
        this.setResizable(false);
        this.setVisible(true);
    }
    
    private JTextField createAgeJTextField(){
    	
    	JTextField ageJTextField = new JTextField();
    	
    	ageJTextField.addKeyListener(new KeyListener() {  
            @Override  
            public void keyTyped(KeyEvent e) {  
                int temp = e.getKeyChar();  
                System.out.println(temp);  
                if(temp == 10){//check enter key  
                  
                }else if(temp != 46){   //check decimal point 
                    if(temp != 8){  //check the key 'backspace'
                        //check the number range from 0 ~ 9 
                        if(temp > 57){  
                            e.consume();    
                        }else if(temp < 48){  
                            e.consume();  
                        }  
                    }  
                }         
            }  
            @Override  
            public void keyReleased(KeyEvent e) {  
                // TODO Auto-generated method stub  
            }  
            @Override  
            public void keyPressed(KeyEvent e) {  
                // TODO Auto-generated method stub        
            }  
    	});  
    	
    	return ageJTextField;
    }
    
    private JComboBox createGenderJcomboBox(){
    	
    	JComboBox genderJComboBox = new JComboBox();
    	
    	genderJComboBox.addItem("M");
    	genderJComboBox.addItem("F");
    	
    	return genderJComboBox;
    }
    
    private JComboBox createStateJcomboBox(){
    	
    	JComboBox stateJComboBox = new JComboBox();
    	
    	String[] stateArray = Helper.getStateArray();
    	for(String state : stateArray){
    		stateJComboBox.addItem(state);
    	}
    	
    	return stateJComboBox;
    }
    
    private JTextField createJTextField(){
    	
    	JTextField textField = new JTextField();
    	return textField;
    } 
    
}
