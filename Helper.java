package com.socialnetwork.assign2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.socialnetwork.assign2.dao.Adult;
import com.socialnetwork.assign2.dao.Child;
import com.socialnetwork.assign2.dao.Person;

/**
 * Helper tool class
 *
 */
public class Helper {
    private static String[] stateArray = new String[]{"ACT","NSW","NT","QLD","SA","TAS","VIC","WA"};
    public static final String[] RELATIONSHIP_ARRAY = new String[]{"couples","parent-child","friends","colleagues","classmates"
    													,"friends and colleagues","friends and classmates","colleagues and classmates"
    													,"friends, colleagues and classmates"};
    public static final String[] BUILD_RELATIONSHIP_ARRAY = new String[]{"couples","parent-child","friends","colleagues","classmates"};
    
    public static final int NO_RELATIONSHIP = -1;
    public static final int RELATIONSHIP_COUPLES = 0;
    public static final int RELATIONSHIP_PARENT_CHILD = 1;
    public static final int RELATIONSHIP_FRIENDS = 2;
    public static final int RELATIONSHIP_COLLEAGUES = 3;
    public static final int RELATIONSHIP_CLASSMATES = 4;
    public static final int RELATIONSHIP_FRIENDS_COLLEAGUES = 5;
    public static final int RELATIONSHIP_FRIENDS_CLASSMATES = 6;
    public static final int RELATIONSHIP_COLLEAGUES_CLASSMATES = 7;
    public static final int RELATIONSHIP_FRIENDS_COLLEAGUES_CLASSMATES = 8;
    
    /**
     * found this useful method online, which can centralize component in our screen.
     * @param comp
     */
    public static void toCenter(Component comp) {
        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle rec=ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        comp.setLocation(((int)(rec.getWidth()-comp.getWidth())/2),
                ((int)(rec.getHeight()-comp.getHeight()))/2);

    }
    
    /**
     * show a alert dialog with title and info.
     * @param frame
     * @param title
     * @param info
     */
    public static void showAlertDialog(JFrame frame,String title,String info){
    	
    	JDialog dialog = new JDialog(frame,title,true);
		dialog.setBounds(400, 200, 350, 150);
		dialog.setLayout(new BorderLayout(5,5));
        JLabel lab = new JLabel(info);
        JButton okBtn = new JButton("Ok");
        okBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
        });
        dialog.add(BorderLayout.CENTER,lab);
        dialog.add(BorderLayout.SOUTH,okBtn);
        Helper.toCenter(dialog);
        dialog.setVisible(true);
 
    }

    /**
     * get the default state array
     * @return
     */
    public static String[] getStateArray() {
    	return stateArray;
    }
    
    /**
     * transfer PersonArrayList To NameStringArray
     * @param personList
     * @return
     */
    public static String[] transferPersonArrayListToNameStringArray(ArrayList<Person> personList){
    	

		String[] personNameArray = new String[personList.size()];
		
		for(int i = 0 ; i < personNameArray.length ; i ++){
			
			personNameArray[i] = personList.get(i).getName();
		}
		return personNameArray;
    }
    
    /**
     * transfer AdultArrayList To NameStringArray
     * @param personList
     * @return
     */
    public static String[] transferAdultArrayListToNameStringArray(ArrayList<Adult> personList){
    	

		String[] personNameArray = new String[personList.size()];
		
		for(int i = 0 ; i < personNameArray.length ; i ++){
			
			personNameArray[i] = personList.get(i).getName();
		}
		return personNameArray;
    }
    /**
     * transfer ChildArrayList To NameStringArray
     * @param teenagerList
     * @return
     */
    public static String[] transferChildArrayListToNameStringArray(ArrayList<Child> teenagerList){
    	

		String[] personNameArray = new String[teenagerList.size()];
		
		for(int i = 0 ; i < personNameArray.length ; i ++){
			
			personNameArray[i] = teenagerList.get(i).getName();
		}
		return personNameArray;
    }
    
    /**
     * check the relationships for the provided two persons
     * @param first
     * @param second
     * @return
     */
    public static int checkRelationship(Person first,Person second){
    	
    	int relationship = NO_RELATIONSHIP;
    	/**
    	 * if the two persons are all adult
    	 */
    	if(first instanceof Adult && second instanceof Adult){
    		Adult adult1 = (Adult)first;
    		Adult adult2 = (Adult)second;
    		//1. if they are couples:
    		if(adult1.getPartner() == adult2){
    			relationship = RELATIONSHIP_COUPLES;
    		}else{//2. if they are friends:
    			boolean friends = adult1.friendList.contains(adult2);
    			boolean colleagues = adult1.getColleagues().contains(adult2);
    			boolean classmates = adult1.getClassmates().contains(adult2);
    			
    			if(friends && !colleagues && !classmates){//friends
    				relationship = RELATIONSHIP_FRIENDS;
    			}else if(!friends && colleagues && !classmates){//colleagues
    				relationship = RELATIONSHIP_COLLEAGUES;
    			}else if(!friends && !colleagues && classmates){//classmates
    				relationship = RELATIONSHIP_CLASSMATES;
    			}else if(friends && colleagues && !classmates){//friends and colleagues
    				relationship = RELATIONSHIP_FRIENDS_COLLEAGUES;
    			}else if(friends && !colleagues && classmates){//friends and classmates
    				relationship = RELATIONSHIP_FRIENDS_CLASSMATES;
    			}else if(!friends && colleagues && classmates){//colleagues and classmates
    				relationship = RELATIONSHIP_COLLEAGUES_CLASSMATES;
    			}else if(friends && colleagues && classmates){//friends , colleagues and classmates
    				relationship = RELATIONSHIP_FRIENDS_COLLEAGUES_CLASSMATES;
    			}else{
    				relationship = NO_RELATIONSHIP;
    			}
    		}
    	}
    	/**
    	 * if the two persons are all children
    	 */
    	else if(first instanceof Child && second instanceof Child){
    		Child child1 = (Child)first;
    		Child child2 = (Child)second;
    		
  			boolean friends = child1.friendList.contains(child2);
			boolean classmates = child1.getClassmates().contains(child2);
    		
			if(friends && !classmates){//friends
				relationship = RELATIONSHIP_FRIENDS;
			}else if(!friends  && classmates){//classmates
				relationship = RELATIONSHIP_CLASSMATES;
			}else if(friends && classmates){//friends and colleagues
				relationship = RELATIONSHIP_FRIENDS_CLASSMATES;
			}else{
				relationship = NO_RELATIONSHIP;
			}
    	}
    	/**
    	 * if one is adult and anther one is child
    	 */
    	else{
    		Adult adult;
    		Child child;
    		if(first instanceof Adult){
    			adult = (Adult)first;
    			child = (Child)second;
    		}else{
    			adult = (Adult)second;
    			child = (Child)first;
    		}
    		if(adult.getChildren().contains(child)){
    			relationship = RELATIONSHIP_PARENT_CHILD;
    		}else{
    			relationship = NO_RELATIONSHIP;
    		}
    		
    	}
    	return relationship;
    	
    }

    
}