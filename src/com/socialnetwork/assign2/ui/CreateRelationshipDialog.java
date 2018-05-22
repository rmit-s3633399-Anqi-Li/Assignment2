package com.socialnetwork.assign2.ui;
/**
 * @author Yinan Jin s3548049
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

import com.socialnetwork.assign2.Helper;
import com.socialnetwork.assign2.dao.Adult;
import com.socialnetwork.assign2.dao.Person;
import com.socialnetwork.assign2.dao.Child;
import com.socialnetwork.assign2.database.DataBase;
import com.socialnetwork.assign2.ui.SelectSinglePersonDialog.ButtonCallBack;

public class CreateRelationshipDialog extends  JDialog {
	
	private ArrayList<Person> personList;
	
	
    /******************************* components Definition **************************/
    
    private JButton btOk=new JButton("Ok");
    private JButton btExit=new JButton("Close");
    
    private JList list1;
    private JList list2;
    private String title1,title2;
    private String[] dataArray1,dataArray2;
    private ButtonCallBack okBtnCallBack,exitBtnCallBack;
    
    public CreateRelationshipDialog(JFrame frm,String title1,String title2,String[] pDataArray1,String[] pDataArray2,ButtonCallBack btn1CallBack,ButtonCallBack btn2CallBack) {
        /*********************** UI initialization ***************************/
        
	/*********************** UI initialization ***************************/
        
    	super(frm,true);
    	this.title1 = title1;
    	this.title2 = title2;
    	this.dataArray1 = pDataArray1;
    	this.dataArray2 = pDataArray2;
    	this.okBtnCallBack = btn1CallBack;
    	this.exitBtnCallBack = btn2CallBack;
    	UIlayout();

    }
    
    private void UIlayout(){
    	
    	this.setLayout(new BorderLayout(5,5));
    	JPanel centerPane = new JPanel();
    	this.add(BorderLayout.CENTER,centerPane);
    	centerPane.setLayout(new GridLayout(1, 3));
        list1 = new JList(dataArray1);
        list1.setBorder(BorderFactory.createTitledBorder(this.title1));
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        centerPane.add(new JScrollPane(this.list1));
        
        list2 = new JList(dataArray2);
        list2.setBorder(BorderFactory.createTitledBorder(this.title2));
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        centerPane.add(new JScrollPane(this.list2));
        
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
				
				okBtnCallBack.onBtnClicked(list1.getSelectedIndex(),list2.getSelectedIndex());
				CreateRelationshipDialog.this.dispose();
			} 
        	
        });
        btExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				exitBtnCallBack.onBtnClicked(list1.getSelectedIndex(),list2.getSelectedIndex());
				CreateRelationshipDialog.this.dispose();
			}
        	
        });
        this.setResizable(false);
        this.setVisible(true);
    	
    }
    
    interface ButtonCallBack{
    	
    	public void onBtnClicked(int index1,int index2);
    	
    }
    
}
