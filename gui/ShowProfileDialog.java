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
import com.socialnetwork.assign2.dao.Person;

public class ShowProfileDialog  extends  JDialog{
	
	private Person person;
	
    /******************************* components Definition **************************/
    private JLabel nameLb=new JLabel("Name:");
    private JLabel ageLb=new JLabel("Age:");
    private JLabel genderLb=new JLabel("Gender:");
    private JLabel stateLb=new JLabel("State:");
    private JLabel statusLb=new JLabel("Status:");
    private JButton btOk=new JButton("Ok");
    private JButton btExit=new JButton("Close");
    private JTextField statusTF = new JTextField();
    
    
    public ShowProfileDialog(JFrame frm,Person person) {
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
        JLabel stateLb = new JLabel(person.getState());
        this.add(stateLb);
        this.add(statusLb);
        JLabel statusLb = new JLabel(person.getStatus());
        this.add(statusLb);
        statusTF.setText(person.getStatus());

        this.add(btOk);
        this.add(btExit);
        this.setSize(240,200);
        Helper.toCenter(this);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        /******************* add action listener *******************************/
        btOk.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				ShowProfileDialog.this.dispose();
				
			}
        	
        });
        btExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				ShowProfileDialog.this.dispose();
				
			}
        	
        });
        this.setResizable(false);
        this.setVisible(true);
    }
    
}

