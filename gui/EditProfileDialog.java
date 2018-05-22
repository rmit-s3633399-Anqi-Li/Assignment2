package com.socialnetwork.assign2.ui;

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
import com.socialnetwork.assign2.dao.Person;
import com.socialnetwork.assign2.database.DataBase;

/**
 * edit profile dialog
 * 
 * fulfil the function that edit the profile of a person
 *
 */
public class EditProfileDialog  extends  JDialog{
	
	private Person person;
	
    /******************************* components Definition **************************/
    private JLabel nameLb=new JLabel("Name:");
    private JLabel ageLb=new JLabel("Age:");
    private JLabel genderLb=new JLabel("Gender:");
    private JLabel stateLb=new JLabel("State:");
    private JLabel statusLb=new JLabel("Status:");
    private JComboBox stateComboBox = null;
    private JButton btEdit=new JButton("Edit");
    private JButton btExit=new JButton("Close");
    private JTextField statusTF;
    
    
    public EditProfileDialog(JFrame frm,Person person) {
        /*********************** UI initialization ***************************/
        super(frm,true);
        this.person = person;
        this.setLayout(new GridLayout(6,2));
        this.add(nameLb);
        JLabel nameLb = new JLabel(person.getName());
        this.add(nameLb);
        this.add(ageLb);
        JLabel ageLb = new JLabel(""+person.getAge());
        this.add(ageLb);
        this.add(genderLb);
        JLabel genderLb = new JLabel(""+person.getGender());
        this.add(genderLb);
        this.add(stateLb);
        this.add(stateComboBox = createStateJcomboBox());
        this.add(statusLb);
        
        this.add(statusTF = createJTextField());
        statusTF.setText(person.getStatus());

        this.add(btEdit);
        this.add(btExit);
        this.setSize(240,200);
        Helper.toCenter(this);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        /******************* add action listener *******************************/
        btEdit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String state = stateComboBox.getSelectedItem().toString();
				String status = statusTF.getText();
				
				person.setState(state);
				person.setStatus(status);
				
				DataBase.getInstance().notifyDataChanged();
				
				EditProfileDialog.this.dispose();
				
			}
        	
        });
        btExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				EditProfileDialog.this.dispose();
				
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
    	for(int i = 0 ; i < stateArray.length ; i ++){

    		stateJComboBox.addItem(stateArray[i]);
    	}
    	for(int i = 0 ; i < stateArray.length ; i ++){
    		if(stateArray[i].equals(person.getState())){
    			stateJComboBox.setSelectedIndex(i);
    			break;
    		}
    	}
    	return stateJComboBox;
    }
    
    private JTextField createJTextField(){
    	
    	JTextField textField = new JTextField();
    	return textField;
    }


    
    
}

