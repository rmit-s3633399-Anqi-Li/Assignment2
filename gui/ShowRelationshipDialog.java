package com.socialnetwork.assign2.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.socialnetwork.assign2.Helper;
import com.socialnetwork.assign2.dao.Adult;
import com.socialnetwork.assign2.dao.Person;
import com.socialnetwork.assign2.dao.Child;
import com.socialnetwork.assign2.database.DataBase;
import com.socialnetwork.assign2.ui.SelectSinglePersonDialog.ButtonCallBack;

public class ShowRelationshipDialog extends  JDialog {
	
	private ArrayList<Person> personList;
	
	
    /******************************* components Definition **************************/
    
    private JButton btOk=new JButton("Ok");
    private JButton btExit=new JButton("Close");
    private Person person;
    private JList list;
    
    public ShowRelationshipDialog(JFrame frm,Person person) {
        /*********************** UI initialization ***************************/
        
	/*********************** UI initialization ***************************/
        
    	super(frm,true);
    	this.person = person;
    	UIlayout();

    }
    
    private void UIlayout(){
    	
    	this.setLayout(new GridLayout(1, 2));
    	
    	JPanel leftPane = new JPanel();
    	this.add(leftPane);
    	leftPane.setLayout(new GridLayout(1, 2));
    	
    	String[] friendnameArray = Helper.transferPersonArrayListToNameStringArray(person.friendList);
        list = new JList(friendnameArray);
        list.setBorder(BorderFactory.createTitledBorder("Friend List"));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	this.add(new JScrollPane(this.list));

        
        JPanel southPanel = new JPanel();
        this.add(BorderLayout.SOUTH,southPanel);
        southPanel.setLayout(new GridLayout(1, 2));
        southPanel.add(btOk);
        southPanel.add(btExit);
        this.setSize(300, 250);
        Helper.toCenter(this);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        /******************* add action listener *******************************/
        btOk.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				ShowRelationshipDialog.this.dispose();
			} 
        	
        });
        btExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				ShowRelationshipDialog.this.dispose();
			}
        	
        });
        this.setResizable(false);
        this.setVisible(true);
    	
    }

    
}
