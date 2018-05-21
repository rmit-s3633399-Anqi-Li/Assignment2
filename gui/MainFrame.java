package com.socialnetwork.assign2.ui;
/**
 * @author Anqi Li s3633399
 */
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

public class MainFrame extends JFrame implements DataBaseListener{

	   private DefaultTableModel model = null;
	   private JTable table = null;
	   private JButton addBtn = null,showProfileBtn = null,showRelationship = null,editProfileBtn = null, delBtn = null;
	   private JPanel btnPanel;
	   private JPopupMenu popMenu = null;
	   private int selectedRow = -1;

	
	   public MainFrame()
	   {
	      super("Main Frame");
	      // menu bar
	      JMenuBar menuBar = new JMenuBar();
	      JMenu file = new JMenu("Menu");
	      // Set the mnemonic to F, press ALT + F to trigger the menu
	      file.setMnemonic('F');

	      JMenuItem relatioinshipMap = new JMenuItem("Relationship Map");
	      JMenuItem quit = new JMenuItem("Exit");
	      
	      relatioinshipMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
				MainFrame.this.dispose();
				new RelationshipFrame();
			}
	    	  
	      });

	      file.add(relatioinshipMap);
	      file.addSeparator();
	      file.add(quit);

	      menuBar.add(file);

	      // Set the menu bar, use this method to set the menu bar can not occupy the layout space
	      setJMenuBar(menuBar);

	      // The above is a normal menu, there is a pop-up menu, right-click pop-up
	      popMenu = new JPopupMenu();
	      JMenuItem popItem1 = new JMenuItem("Profile");
	      JMenuItem popItem2 = new JMenuItem("Delete");
	      popMenu.add(popItem1);
	      popMenu.add(popItem2);
	      
	      String[][] datas = {};
	      String[] titles = { "Name","Age","Gender","State","Status"};
	      model = new DefaultTableModel(datas, titles);
	      loadData();
	      table = new JTable(model){
	             @Override
	             public boolean isCellEditable(int row, int column) {
	                 return false;
	             }
	      };
//	      table.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer(){
//	    	    
//	    	                 /*(non-Javadoc)
//	    	                 *This method is used to return a cell's renderer to the method caller (ie, the display data component - or control)
//	    	                 *Can be JCheckBox JComboBox JTextArea etc
//	    	                 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
//	    	                 */
//	    	                @Override
//	    	                public Component getTableCellRendererComponent(JTable table,
//                                  Object value, boolean isSelected, boolean hasFocus,
//	    	                        int row, int column) {
//	    	                    //Create a render component for return
//	    	                    JCheckBox ck = new JCheckBox();
//	    	                    // Select the checkbox corresponding to the focused row
//	    	                    ck.setSelected(isSelected);
//	    	                    // Set the radio box.setSelected(hasFocus);
//	    	                    // Center the check box in the cell
//	    	                    ck.setHorizontalAlignment((int) 0.5f);
//	    	                    return ck;
//	    	                }});
	      
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
	        	 new AddNewPeopleDialog(MainFrame.this);
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
	        		 new ShowProfileDialog(MainFrame.this,person);
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
	        	 new EditProfileDialog(MainFrame.this,person);
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
