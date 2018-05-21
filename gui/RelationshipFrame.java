package com.socialnetwork.assign2.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

import com.socialnetwork.assign2.Helper;
import com.socialnetwork.assign2.dao.Adult;
import com.socialnetwork.assign2.dao.Child;
import com.socialnetwork.assign2.dao.Person;
import com.socialnetwork.assign2.dao.YoungChild;
import com.socialnetwork.assign2.database.DataBase;
import com.socialnetwork.assign2.database.DataBaseListener;

public class RelationshipFrame extends JFrame implements DataBaseListener{

	   private DefaultTableModel model = null;
	   private JTable table = null;
	   private JButton friendsBtn = null,classmatesBtn = null,colleaguesBtn = null,childrenBtn = null,checkRelationShipBtn = null,buildRelationshipBtn = null;
	   private JPanel btnPanel;
	   private JPopupMenu popMenu = null;
	   private int selectedRow = -1;
	
	   public RelationshipFrame()
	   {
	      super("Relationship Frame");
	      // menu bar
	      JMenuBar menuBar = new JMenuBar();
	      JMenu file = new JMenu("Menu");
	      //Set the mnemonic to F, press ALT + F to trigger this menu
	      file.setMnemonic('F');

	      JMenuItem mainFrame = new JMenuItem("Main Frame");
	      JMenuItem quit = new JMenuItem("Exit");
	      
	      mainFrame.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				

				new BasicInfoFrame();
				RelationshipFrame.this.dispose();
				
			}
	    	  
	      });

	      file.add(mainFrame);
	      file.addSeparator();
	      file.add(quit);

	      menuBar.add(file);

	      //Set the menu bar, use this method to set the menu bar can not occupy the layout space
	      setJMenuBar(menuBar);
 
	      // The above is a normal menu, there is a pop-up menu, right-click pop-up
	      popMenu = new JPopupMenu();
	      JMenuItem popItem1 = new JMenuItem("Profile");
	      JMenuItem popItem2 = new JMenuItem("Delete");
	      popMenu.add(popItem1);
	      popMenu.add(popItem2);
	      
	      String[][] datas = {};
	      String[] titles = { "Name","Type","Father","Mother","Partner"};
	      model = new DefaultTableModel(datas, titles);
	      loadData();
	      table = new JTable(model){
	             @Override
	             public boolean isCellEditable(int row, int column) {
	                 return false;
	             }
	      };
	      
	      btnPanel = new JPanel();
	      
	      friendsBtn = new JButton("Friends");
	      friendsBtn.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e)
	         {
	        	 if(selectedRow != -1){
	        		 Person person = DataBase.getInstance().getPersonList().get(selectedRow);
	        		 new FriendListDialog(RelationshipFrame.this,person);
	        	 }
	        	 
	         }
	      });
	      btnPanel.add(friendsBtn);
	      
	      //
	      
	      classmatesBtn = new JButton("Classmates");
	      classmatesBtn.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e)
	         {
	        	 if(selectedRow != -1){
	        		 Person person = DataBase.getInstance().getPersonList().get(selectedRow);
	        		 new ClassmateListDialog(RelationshipFrame.this,person);
	        	 }
	        	 
	         }
	      });
	      btnPanel.add(classmatesBtn);
	      
	      
	      colleaguesBtn = new JButton("Colleagues");
	      colleaguesBtn.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e)
	         {
	        	 if(selectedRow != -1){
	        		 Person person = DataBase.getInstance().getPersonList().get(selectedRow);
	        		 if(person instanceof Adult){
	        			 Adult adult = (Adult)person;
	        			 new ColleagueListDialog(RelationshipFrame.this,adult);
	        		 }
	        		 
	        	 }
	        	 
	         }
	      });
	      btnPanel.add(colleaguesBtn);
	      
	      
	      
	      childrenBtn = new JButton("Children");
	      childrenBtn.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e)
	         {
	        	 if(selectedRow >=0){
	        		 Person person = DataBase.getInstance().getPersonList().get(selectedRow);
	        		 if(person instanceof Adult){
	        			 new ChildrenListDialog(RelationshipFrame.this,(Adult)person);
	        		 }
	        		 
	        	 }
	        	 
	         }
	       });
	      childrenBtn.setEnabled(false);
	      btnPanel.add(childrenBtn);
	      

	      checkRelationShipBtn = new JButton("Check Relationship");
	      checkRelationShipBtn.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e)
	         {
	        	 SelectMultiplePersonDialog.ButtonCallBack selectedBCB = new SelectMultiplePersonDialog.ButtonCallBack(){

					@Override
					public void onBtnClicked(int index1, int index2) {
						
						System.out.println("index1 = " + index1 +", index2 = "+index2);
						checkRelationship(index1,index2);
						
						
					}
	        		 
	        	 };
	        	 
	        	 SelectMultiplePersonDialog.ButtonCallBack cancelBCB = new SelectMultiplePersonDialog.ButtonCallBack(){

					@Override
					public void onBtnClicked(int index1, int index2) {
						// TODO Auto-generated method stub
						
					}
	        		 
	        	 };
	        	 
	        	String[] dataStr = Helper.transferPersonArrayListToNameStringArray(DataBase.getInstance().getPersonList()); 
	        	 new SelectMultiplePersonDialog(RelationshipFrame.this,"First Person","Second Person",dataStr,dataStr,selectedBCB,cancelBCB);
	        	 
	         }
	       });
	      btnPanel.add(checkRelationShipBtn);
	      
	      
	      buildRelationshipBtn = new JButton("Build Relationship");
	      buildRelationshipBtn.addActionListener(new ActionListener() {

	         @Override
	         public void actionPerformed(ActionEvent e)
	         {
	        	 SelectMultiplePersonDialog.ButtonCallBack selectedBCB = new SelectMultiplePersonDialog.ButtonCallBack(){

					@Override
					public void onBtnClicked(int index1, int index2) {
						
						System.out.println("index1 = " + index1 +", index2 = "+index2);
						if(index1 == index2){
							Helper.showAlertDialog(RelationshipFrame.this, "Warning!", "You can not build relationship for same person!");
						}else{
							new BuildRelationshipDialog(RelationshipFrame.this,DataBase.getInstance().getPersonList().get(index1),DataBase.getInstance().getPersonList().get(index2));
						}
						
					}
	        		 
	        	 };
	        	 
	        	 SelectMultiplePersonDialog.ButtonCallBack cancelBCB = new SelectMultiplePersonDialog.ButtonCallBack(){

					@Override
					public void onBtnClicked(int index1, int index2) {
						// TODO Auto-generated method stub
						
					}
	        		 
	        	 };
	        	 
	        	String[] dataStr = Helper.transferPersonArrayListToNameStringArray(DataBase.getInstance().getPersonList()); 
	        	 new SelectMultiplePersonDialog(RelationshipFrame.this,"First Person","Second Person",dataStr,dataStr,selectedBCB,cancelBCB);
	        	 
	         }
	       });
	      btnPanel.add(buildRelationshipBtn);
	      
	      
	      
	      table.addMouseListener(new java.awt.event.MouseAdapter() {  
	            public void mouseClicked(java.awt.event.MouseEvent evt) {  
	            	mouseRightButtonClick(evt);  
	            }  
	      });  
	      
	      
	      add(btnPanel, BorderLayout.NORTH);
	      add(new JScrollPane(table));
	      
	      table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){  
	    	  
	            @Override  
	            public void valueChanged(ListSelectionEvent e)  
	            {  
	                int row = table.getSelectedRow();  
	                if(row >= 0 && row < DataBase.getInstance().getPersonList().size())  
	                {  
	                	
	                	DefaultTableModel model = (DefaultTableModel)table.getModel(); 
	                	System.out.println("selected row : " + row);
	                	selectedRow = row;
	                	Person person = DataBase.getInstance().getPersonList().get(selectedRow);
	                	friendsBtn.setText("Friends("+person.friendList.size()+")");
	                	if(person instanceof Adult){//adult
	                		Adult adult = (Adult)person;
	                		childrenBtn.setText("Children("+adult.getChildren().size()+")");
	                		childrenBtn.setEnabled(true);
	                		colleaguesBtn.setText("Colleagues("+adult.getColleagues().size()+")");
	                		colleaguesBtn.setEnabled(true);
	                		classmatesBtn.setText("Classmates("+adult.getClassmates().size()+")");
	                		classmatesBtn.setEnabled(true);
	                	}else{

	                		if(!(person instanceof YoungChild)){//child
	                			Child child = (Child)person;
	                			classmatesBtn.setText("Classmates("+child.getClassmates().size()+")");
		                		classmatesBtn.setEnabled(true);
	                		}else{//young child
	                			classmatesBtn.setText("Classmates");
	                			classmatesBtn.setEnabled(false);
	                		}
	                		childrenBtn.setText("Children");
	                		childrenBtn.setEnabled(false);
	                		colleaguesBtn.setText("Colleagues");
	                		colleaguesBtn.setEnabled(false);
	                	}
	                	friendsBtn.setEnabled(true);
	                }else{
	                	childrenBtn.setEnabled(false);
	                	friendsBtn.setEnabled(false);
	                	colleaguesBtn.setEnabled(false);
	                	classmatesBtn.setEnabled(false);
	                }
	            }  
	        }); 

	      setSize(1000, 600);
	      setLocationRelativeTo(null);
	      setDefaultCloseOperation(EXIT_ON_CLOSE);
	      setVisible(true);
	      DataBase.getInstance().addDBListener(this);
	   }
	   
	   private void mouseRightButtonClick(java.awt.event.MouseEvent evt) {  
	       //To determine whether the mouse BUTTON3 button, BUTTON3 right mouse button
	       if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {  
	           //Find the row in the table as a click by clicking on the location
	           int focusedRowIndex = table.rowAtPoint(evt.getPoint());  
	           if (focusedRowIndex == -1) {  
	               return;  
	           }  
	           //Set the table options to the row currently right-clicked
	           table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);  
	           //Popup menu
	           popMenu.show(table, evt.getX(), evt.getY());  
	       }  
	  
	   } 
	   
	   private void loadData(){
		   
		  model.getDataVector().clear();
		  ArrayList<Person> personList = DataBase.getInstance().getPersonList();
		  for(Person person : personList){
			  addRow(person.toRelationshipStringArray());
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
	
	
	/**
	 * check two persons relationship with indexs
	 * @param index1
	 * @param index2
	 */
	public void checkRelationship(int index1, int index2){
		JDialog dialog;
		if(index1 == index2){
	        Helper.showAlertDialog(this,"Warning!","You can not choose same persons to check ralationship.");
	        return;
		}
		
		String labStr = null;
		Person first = DataBase.getInstance().getPersonList().get(index1);
		Person second = DataBase.getInstance().getPersonList().get(index2);
		int relationship = Helper.checkRelationship(first ,second);
		switch(relationship){
			case Helper.RELATIONSHIP_COUPLES:
				labStr = first.getName() + " and " + second.getName() + " are couples.";
				break;
			case Helper.RELATIONSHIP_PARENT_CHILD:
				labStr = first.getName() + " and " + second.getName() + " are lineal relationship.";
				break;
			case Helper.RELATIONSHIP_FRIENDS:
				labStr = first.getName() + " and " + second.getName() + " are friends.";
				break;
			case Helper.RELATIONSHIP_COLLEAGUES:
				labStr = first.getName() + " and " + second.getName() + " are colleagues.";
				break;
			case Helper.RELATIONSHIP_CLASSMATES:
				labStr = first.getName() + " and " + second.getName() + " are classmates.";
				break;
			case Helper.RELATIONSHIP_FRIENDS_COLLEAGUES:
				labStr = first.getName() + " and " + second.getName() + " are friends as well as colleagues.";
				break;
			case Helper.RELATIONSHIP_FRIENDS_CLASSMATES:
				labStr = first.getName() + " and " + second.getName() + " are friends as well as classmates.";
				break;
			case Helper.RELATIONSHIP_COLLEAGUES_CLASSMATES:
				labStr = first.getName() + " and " + second.getName() + " are colleagues as well as classmates.";
				break;
			case Helper.RELATIONSHIP_FRIENDS_COLLEAGUES_CLASSMATES:
				labStr = first.getName() + " and " + second.getName() + " are friends , colleagues as well as classmates..";
				break;
			default:
				break;
		}
		if(labStr == null){
			labStr = "There is no any relationship between " + first.getName() + "and " + second.getName();
		}
		
		Helper.showAlertDialog(this,"Relationship",labStr);
		
	}
	   

}
