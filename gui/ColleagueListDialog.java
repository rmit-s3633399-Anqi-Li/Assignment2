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
 * Colleague list dialog
 * to show classmates of a adult
 *
 */
public class ColleagueListDialog extends  JDialog {
	
	private Adult adult;
	
	
    /******************************* components Definition **************************/
    
    private JButton btDel=new JButton("Delete");
    private JButton btExit=new JButton("Close");
    
    private JList list;
    
    public ColleagueListDialog(JFrame frm,Adult adult) {
        /*********************** UI initialization ***************************/
        
	/*********************** UI initialization ***************************/
        
    	super(frm,true);
    	this.adult = adult;
    	UIlayout();

    }
    
    private void UIlayout(){
    	

        list = new JList(Helper.transferAdultArrayListToNameStringArray(adult.getColleagues()));
        list.setBorder(BorderFactory.createTitledBorder(adult.getName() + "'s colleagues"));
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
				
				ColleagueListDialog.this.dispose();
			} 
        	
        });
        this.setResizable(false);
        this.setVisible(true);
    	
    }

    
}
