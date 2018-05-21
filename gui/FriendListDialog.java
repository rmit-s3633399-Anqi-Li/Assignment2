package com.socialnetwork.assign2.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.socialnetwork.assign2.Helper;
import com.socialnetwork.assign2.dao.Person;

/**
 * friend list dialog
 * to show friend list of a person
 *
 */
public class FriendListDialog extends  JDialog {
	
	private Person person;
	
	
    /******************************* components Definition **************************/
    
    private JButton btDel=new JButton("Delete");
    private JButton btExit=new JButton("Close");
    private DefaultListModel model;
    private JList list;
    
    public FriendListDialog(JFrame frm,Person person) {
        /*********************** UI initialization ***************************/
        
	/*********************** UI initialization ***************************/
        
    	super(frm,true);
    	this.person = person;
    	UIlayout();

    }
    
    private void UIlayout(){
    	

        list = new JList(Helper.transferPersonArrayListToNameStringArray(person.friendList));
        model = new DefaultListModel();
        for(Person person : person.friendList){
        	model.addElement(person);
        }
        list.setModel(model);
        list.setBorder(BorderFactory.createTitledBorder(person.getName() + "'s friends"));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
		
				btDel.setEnabled(list.getSelectedIndex() >= 0);
			}
        });
    	this.setLayout(new BorderLayout(5,5));
    	this.add(BorderLayout.CENTER,new JScrollPane(this.list));
        
        this.add(BorderLayout.SOUTH,btDel);
        this.setSize(300, 250);
        Helper.toCenter(this);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        btDel.setEnabled(false);
        /******************* add action listener *******************************/
        btDel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				Person removedFriend = person.friendList.remove(index);
				removedFriend.friendList.remove(person);
				model.removeElement(removedFriend);
			} 
        	
        });
        this.setResizable(false);
        this.setVisible(true);
    	
    }

    
}
