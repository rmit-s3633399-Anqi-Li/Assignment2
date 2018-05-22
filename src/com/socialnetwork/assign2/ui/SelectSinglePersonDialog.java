package com.socialnetwork.assign2.ui;
/**
 * @author Yinan Jin s3548049
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.socialnetwork.assign2.Helper;
import com.socialnetwork.assign2.dao.Person;

public class SelectSinglePersonDialog extends  JDialog {
	
	private ArrayList<Person> personList;
	
	
    /******************************* components Definition **************************/
    
	private JList list;
    private JButton btOk=new JButton("Ok");
    private JButton btExit=new JButton("Close");
    private String title;
    private String[] dataArray;
    private ButtonCallBack okBtnCallBack,exitBtnCallBack;
    
    public SelectSinglePersonDialog(JFrame frm,String title,String[] pDataArray,ButtonCallBack btn1CallBack,ButtonCallBack btn2CallBack) {
      
    	/*********************** UI initialization ***************************/
        
    	super(frm,true);
    	this.title = title;
    	this.dataArray = pDataArray;
    	this.okBtnCallBack = btn1CallBack;
    	this.exitBtnCallBack = btn2CallBack;
    	UIlayout();

    }
    
    private void UIlayout(){
    	
    	this.setLayout(new BorderLayout(5,5));
    	JPanel centerPane = new JPanel();
    	this.add(BorderLayout.CENTER,centerPane);
        list = new JList(dataArray);
        list.setBorder(BorderFactory.createTitledBorder(this.title));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        centerPane.add(new JScrollPane(this.list));
        JPanel southPanel = new JPanel();
        this.add(BorderLayout.SOUTH,southPanel);
        southPanel.setLayout(new GridLayout(1, 2));
        southPanel.add(btOk);
        southPanel.add(btExit);
        this.setSize(150, 250);
        Helper.toCenter(this);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        /******************* add action listener *******************************/
        btOk.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				okBtnCallBack.onBtnClicked(list.getSelectedIndex());
				SelectSinglePersonDialog.this.dispose();
			}
        	
        });
        btExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				exitBtnCallBack.onBtnClicked(list.getSelectedIndex());
				SelectSinglePersonDialog.this.dispose();
			}
        	
        });
        this.setResizable(false);
        this.setVisible(true);
    	
    }
    
    interface ButtonCallBack{
    	
    	public void onBtnClicked(int index);
    	
    }
    
}
