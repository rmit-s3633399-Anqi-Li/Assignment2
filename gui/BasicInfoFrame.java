package com.socialnetwork.assign2.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.socialnetwork.assign2.dao.Person;
import com.socialnetwork.assign2.database.DataBase;
import com.socialnetwork.assign2.database.DataBaseListener;
import com.socialnetwork.assign2.ui.SelectSinglePersonDialog.ButtonCallBack;

/**
 * basic information frame
 * showing all information of all persons in system.
 * providing some basic functions, such as add a new person,edit profile of a person ,delete a person from system.
 *
 */
public class BasicInfoFrame extends JFrame implements DataBaseListener{

	   private DefaultTableModel model = null;
	   private JTable table = null;
	   private JButton addBtn = null,showProfileBtn = null,showRelationship = null,editProfileBtn = null, delBtn = null;
	   private JPanel btnPanel;
	   private JPopupMenu popMenu = null;
	   private int selectedRow = -1;

	
	   public BasicInfoFrame()
	   {
	      super("Basic Information Page");
	      // menu bar
	      JMenuBar menuBar = new JMenuBar();
	      JMenu file = new JMenu("Menu");

	      file.setMnemonic('F');

	      JMenuItem relatioinshipMap = new JMenuItem("Relationship Map");
	      JMenuItem quit = new JMenuItem("Exit");
	      //add action listener for menu item 'relatioinshipMap'
	      relatioinshipMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				/**
				 * dispose current frame
				 * then goto relationship frame
				 */
				BasicInfoFrame.this.dispose();
				new RelationshipFrame();
			}
	    	  
	      });

	      file.add(relatioinshipMap);
	      file.addSeparator();
	      file.add(quit);

	      menuBar.add(file);

	      //set menu bar on this frame
	      setJMenuBar(menuBar);

	     //set pop menu
	      popMenu = new JPopupMenu();
	      JMenuItem popItem1 = new JMenuItem("Profile");
	      JMenuItem popItem2 = new JMenuItem("Delete");
	      popMenu.add(popItem1);
	      popMenu.add(popItem2);
	      String[][] datas = {};
	      String[] titles = { "Name","Age","Gender","State","Status"};
	      model = new DefaultTableModel(datas, titles);
	      loadData();
	      //init new table
	      table = new JTable(model){
	             @Override
	             public boolean isCellEditable(int row, int column) {
	                 return false;
	             }
	      };

	      // set table model for table 
	      
	      // as well as the list selection listener 
	      table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){  
	    	  
	            @Override  
	            public void valueChanged(ListSelectionEvent e)  
	            {  
	                int row = table.getSelectedRow();  
	                if(row != -1)  
	                {  
	                	DefaultTableModel model = (DefaultTableModel)table.getModel(); 
	                	System.out.println("selected row : " + row);
	                	selectedRow = row;
	                	
	                	
	                	showProfileBtn.setEnabled(true);
	                	editProfileBtn.setEnabled(true);
	                	delBtn.setEnabled(true);
	                }else{
	                	showProfileBtn.setEnabled(false);
	                	editProfileBtn.setEnabled(false);
	                	delBtn.setEnabled(false);
	                }
	            }  
	        }); 
	      
	      btnPanel = new JPanel();
	      
	      addBtn = new JButton("Add");
	      addBtn.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e)
	         {
	        	 new AddNewPeopleDialog(BasicInfoFrame.this);
	         }
	      });
	      btnPanel.add(addBtn);
	      
	      
	      showProfileBtn = new JButton("Show Profile");
	      showProfileBtn.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e)
	         {
	        	 if(selectedRow >=0){
	        		 Person person = DataBase.getInstance().getPersonList().get(selectedRow);
	        		 new ShowProfileDialog(BasicInfoFrame.this,person);
	        	 }
	        	 
	         }
	      });
	      showProfileBtn.setEnabled(false);
	      btnPanel.add(showProfileBtn);
	      
	      showRelationship = new JButton("Edit Profile");
	      showRelationship.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e)
	         {

	         }
	      });
	      showRelationship.setEnabled(false);
	      btnPanel.add(showRelationship);
	      
	      editProfileBtn = new JButton("Edit Profile");
	      editProfileBtn.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e)
	         {
	        	 Person person = DataBase.getInstance().getPersonList().get(selectedRow);
	        	 new EditProfileDialog(BasicInfoFrame.this,person);
	         }
	      });
	      editProfileBtn.setEnabled(false);
	      btnPanel.add(editProfileBtn);
	      
	      
	      delBtn = new JButton("Delete");
	      delBtn.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e)
	         {
	        	 Person person = DataBase.getInstance().getPersonList().get(selectedRow);
	        	 DataBase.getInstance().delPersonFromSystem(person);
	         }
	      });
	      delBtn.setEnabled(false);
	      btnPanel.add(delBtn);
	      
	      
	      
	      table.addMouseListener(new java.awt.event.MouseAdapter() {  
	            public void mouseClicked(java.awt.event.MouseEvent evt) {  
	            	mouseRightButtonClick(evt);  
	            }  
	      });  
	      
	      
	      add(btnPanel, BorderLayout.NORTH);
	      add(new JScrollPane(table));

	      setSize(1000, 600);
	      setLocationRelativeTo(null);
	      setDefaultCloseOperation(EXIT_ON_CLOSE);
	      setVisible(true);
	      DataBase.getInstance().addDBListener(this);
	   }
	   
	   private void mouseRightButtonClick(java.awt.event.MouseEvent evt) {  
	       
	       if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {  
 
	           int focusedRowIndex = table.rowAtPoint(evt.getPoint());  
	           if (focusedRowIndex == -1) {  
	               return;  
	           }  
	           table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);  
	           popMenu.show(table, evt.getX(), evt.getY());  
	       }  
	  
	   } 
	   /**
	    * load data from database, then update to table
	    */
	   private void loadData(){
		   
		  model.getDataVector().clear();
		  ArrayList<Person> personList = DataBase.getInstance().getPersonList();
		  for(Person person : personList){
			  addRow(person.toBasicInfoStringArray());
		  }
		   
	   }
	   
	   private void addRow(String[] rowData){
		   model.addRow(rowData);
	   }

	@Override
	public void onPersonListChanged() {
		
		
		loadData();
		
	}

	@Override
	public void onRelationshipChanged() {
		// TODO Auto-generated method stub
		loadData();
	}
	   

}
