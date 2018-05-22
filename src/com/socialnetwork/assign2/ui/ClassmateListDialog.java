package com.socialnetwork.assign2.ui;
/**
 * @author Anqi Li s3633399
 */
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.socialnetwork.assign2.Helper;
import com.socialnetwork.assign2.dao.Adult;
import com.socialnetwork.assign2.dao.Person;
import com.socialnetwork.assign2.dao.Child;
import com.socialnetwork.assign2.database.DataBase;
import com.socialnetwork.assign2.ui.SelectSinglePersonDialog.ButtonCallBack;

/**
 * Classmate list dialog
 * to show classmates of a person
 *
 */
public class ClassmateListDialog extends  JDialog {
	
	private Person person;
	
	
    /******************************* components Definition **************************/
    
    private JButton btDel=new JButton("Delete");
    private JButton btExit=new JButton("Close");
    
    private JList list;
    
    public ClassmateListDialog(JFrame frm,Person person) {
        /*********************** UI initialization ***************************/
        
	/*********************** UI initialization ***************************/
        
    	super(frm,true);
    	this.person = person;
    	UIlayout();

    }
    
    private void UIlayout(){
    	String[] dataStrArray = null;
    	if(person instanceof Adult){
    		Adult adult = (Adult)person;
    		dataStrArray = Helper.transferAdultArrayListToNameStringArray(adult.getClassmates());
    	}else{
    		Child child = (Child)person;
    		dataStrArray = Helper.transferChildArrayListToNameStringArray(child.getClassmates());
    	}

    	
        list = new JList(dataStrArray);
        list.setBorder(BorderFactory.createTitledBorder(person.getName() + "'s classmates"));
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
				
				ClassmateListDialog.this.dispose();
			} 
        	
        });
        this.setResizable(false);
        this.setVisible(true);
    	
    }

    
}
