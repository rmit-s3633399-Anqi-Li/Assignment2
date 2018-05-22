package com.socialnetwork.assign2.ui;
/**
 * @author Yinan Jin s3548049
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.socialnetwork.assign2.Helper;
import com.socialnetwork.assign2.dao.Adult;
import com.socialnetwork.assign2.dao.Child;
import com.socialnetwork.assign2.dao.Person;
import com.socialnetwork.assign2.dao.YoungChild;
import com.socialnetwork.assign2.database.DataBase;
import com.socialnetwork.assign2.exceptions.AllAdultsOrAllChildrenException;
import com.socialnetwork.assign2.exceptions.AlreadyColleaguesException;
import com.socialnetwork.assign2.exceptions.AlreadyFriendsException;
import com.socialnetwork.assign2.exceptions.DifferentIdentitiesException;
import com.socialnetwork.assign2.exceptions.HasParentsException;
import com.socialnetwork.assign2.exceptions.NoAvailableException;
import com.socialnetwork.assign2.exceptions.NoPartnerException;
import com.socialnetwork.assign2.exceptions.NotAllAdultsException;
import com.socialnetwork.assign2.exceptions.NotToBeCoupledException;
import com.socialnetwork.assign2.exceptions.SameGenderException;
import com.socialnetwork.assign2.exceptions.YoungChildClassmatesException;
import com.socialnetwork.assign2.exceptions.YoungChildFriendException;

public class BuildRelationshipDialog extends  JDialog {
	
	private Person firstPerson,secondPerson;
	
    /******************************* components Definition **************************/
    
	private JLabel firstPersonNameLb=new JLabel("First person's name:");
	private JLabel secondPersonNameLb=new JLabel("Second person's name:");
	private JLabel relationLb=new JLabel("Choose a relationship:");
	private JComboBox relationJComboBox = null;
    private JButton btOk=new JButton("Build");
    private JButton btExit=new JButton("Close");
    private JFrame frm;

    
    public BuildRelationshipDialog(JFrame frm,Person person1,Person person2) {
    /*********************** UI initialization ***************************/

    	
    	
	/*********************** UI initialization ***************************/
        
    	super(frm,true);
    	this.frm = frm;
    	firstPerson = person1;
    	secondPerson = person2;
    	UIlayout();

    }
    
    private void UIlayout(){
    	
    	
    	
    	this.setLayout(new BorderLayout(5,5));
    	JPanel centerPanel = new JPanel(new GridLayout(3, 2));
    	this.add(BorderLayout.CENTER,centerPanel);
    	
    	centerPanel.add(firstPersonNameLb);
    	centerPanel.add(new JLabel(this.firstPerson.getName()));
    	centerPanel.add(secondPersonNameLb);
    	centerPanel.add(new JLabel(this.secondPerson.getName()));
    	centerPanel.add(relationLb);
    	relationJComboBox = new JComboBox(Helper.BUILD_RELATIONSHIP_ARRAY);
    	centerPanel.add(relationJComboBox);
    	
    	JPanel southPanel = new JPanel(new GridLayout(1, 2));
    	this.add(BorderLayout.SOUTH,southPanel);
    	
    	southPanel.add(btOk);
    	southPanel.add(btExit);
        this.setSize(400, 400);
        Helper.toCenter(this);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        /******************* add action listener *******************************/
        btOk.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				buildRelationship();
				BuildRelationshipDialog.this.dispose();
			} 
        	
        });
        btExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				BuildRelationshipDialog.this.dispose();
			}
        	
        });
        this.setResizable(false);
        this.setVisible(true);
    	
    }
    
    private String[] createRelationArray(){
    	String[] relationArray;
    	int relationship = Helper.checkRelationship(firstPerson, secondPerson);
		switch(relationship){
		case Helper.NO_RELATIONSHIP:
			if(firstPerson instanceof Adult && secondPerson instanceof Adult){//if they are all adults
				if(!firstPerson.getGender().equals(secondPerson.getGender())){// if their genders are different
					relationArray = new String[]{Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_COUPLES]
							,Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_FRIENDS]
							,Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_CLASSMATES]
							,Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_COLLEAGUES]};
				}else{
					relationArray = new String[]{Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_FRIENDS]
							,Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_CLASSMATES]
							,Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_COLLEAGUES]};
				}
			}else if(firstPerson instanceof Child && secondPerson instanceof Child){// if they are all kids
				if(!(firstPerson instanceof YoungChild) && !(secondPerson instanceof YoungChild) ){
					relationArray = new String[]{Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_FRIENDS]
							,Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_CLASSMATES]};
				}else{
					relationArray = new String[]{"No relationship to build!"};
				}
			}else{// if one is adult and another is child
				Adult adult;
				Child child;
				if(firstPerson instanceof Adult){
					adult = (Adult)firstPerson;
					child = (Child)secondPerson;
				}else{
					adult = (Adult)secondPerson;
					child = (Child)firstPerson;
				}
				if(adult.getPartner() != null && child.getFather() == null){//if the adult has married and the child has no parents
					relationArray = new String[]{Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_PARENT_CHILD]};
				}else{
					relationArray = new String[]{"No relationship to build!"};
				}
				
			}

			break;
			case Helper.RELATIONSHIP_FRIENDS:
				if(firstPerson instanceof Adult && secondPerson instanceof Adult){//if they are all adults
					relationArray = new String[]{Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_CLASSMATES]
							,Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_COLLEAGUES]};
				}else{ // if they are all kids
					relationArray = new String[]{Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_CLASSMATES]};
				}

				break;
			case Helper.RELATIONSHIP_COLLEAGUES:
				relationArray = new String[]{Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_FRIENDS]
						,Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_CLASSMATES]};
				break;
			case Helper.RELATIONSHIP_CLASSMATES:
				
				if(firstPerson instanceof Adult && secondPerson instanceof Adult){//if they are all adults
					relationArray = new String[]{Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_FRIENDS]
							,Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_COLLEAGUES]};
				}else{ // if they are all kids
					relationArray = new String[]{Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_FRIENDS]};
				}
	
				break;
			case Helper.RELATIONSHIP_FRIENDS_COLLEAGUES:
				relationArray = new String[]{Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_CLASSMATES]};
				break;
			case Helper.RELATIONSHIP_FRIENDS_CLASSMATES:
				if(firstPerson instanceof Adult && secondPerson instanceof Adult){//if they are all adults
					relationArray = new String[]{Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_COLLEAGUES]};
				}else{ // if they are all kids
					relationArray = new String[]{"No relationship to build!"};
				}
				break;
			case Helper.RELATIONSHIP_COLLEAGUES_CLASSMATES:
				relationArray = new String[]{Helper.RELATIONSHIP_ARRAY[Helper.RELATIONSHIP_FRIENDS]};
				break;
			case Helper.RELATIONSHIP_COUPLES:
			case Helper.RELATIONSHIP_PARENT_CHILD:
			case Helper.RELATIONSHIP_FRIENDS_COLLEAGUES_CLASSMATES:
			default:
				relationArray = new String[]{"No relationship to build!"};
				break;
		}
		return relationArray;
    }
    
    
    private void buildRelationship(){
    	String relation = relationJComboBox.getSelectedItem().toString();
    	int relationIndex = Helper.NO_RELATIONSHIP;
    	for(int i = 0 ; i < Helper.BUILD_RELATIONSHIP_ARRAY.length ; i ++){
    		if(relation.equals(Helper.BUILD_RELATIONSHIP_ARRAY[i])){
    			relationIndex = i;
    			break;
    		}
    	}
    	try{
    		switch(relationIndex){
    		
				case Helper.RELATIONSHIP_COUPLES:
					if(firstPerson instanceof Adult && secondPerson instanceof Adult){
						Adult firstAdult = (Adult)firstPerson;
						Adult secondAdult = (Adult)secondPerson;
						if(firstAdult.getPartner() == null && secondAdult.getPartner() == null){
							if(!firstAdult.getGender().equals(secondAdult.getGender())){
								firstAdult.setPartner(secondAdult);
							}else{
								throw new SameGenderException();
							}
						}else{
							throw new NoAvailableException();
						}
					}else{
						//when trying to make a couple when at least one member is not an adult.
						throw new NotToBeCoupledException();
					}
					break;
				case Helper.RELATIONSHIP_PARENT_CHILD:
					if( (firstPerson instanceof Adult && secondPerson instanceof Child) 
							|| (secondPerson instanceof Adult && firstPerson instanceof Child)){
						Adult adult;
						Child child;
						if(firstPerson instanceof Adult){
							adult = (Adult)firstPerson;
							child = (Child)secondPerson;
						}else{
							adult = (Adult)secondPerson;
							child = (Child)firstPerson;
						}
						if(adult.getPartner() != null ){
							if(child.getFather() == null && child.getMother() == null){
								child.setParents(adult,adult.getPartner());
							}else{
								throw new HasParentsException();
							}
						}else{
							throw new NoPartnerException();
						}
						
					}else{
						throw new AllAdultsOrAllChildrenException();
					}
					break;
				case Helper.RELATIONSHIP_FRIENDS:
					if(!firstPerson.friendList.contains(secondPerson) && !secondPerson.friendList.contains(firstPerson)){
						if(firstPerson instanceof Adult && secondPerson instanceof Adult){
							firstPerson.friendList.add(secondPerson);
							secondPerson.friendList.add(firstPerson);
						}else if(firstPerson instanceof Child && secondPerson instanceof Child){
							if(!(firstPerson instanceof YoungChild) && !(secondPerson instanceof YoungChild)){
								firstPerson.friendList.add(secondPerson);
								secondPerson.friendList.add(firstPerson);
							}else{
								throw new YoungChildFriendException();
							}
						}else{
							throw new DifferentIdentitiesException();
						}
						
					}else{
						throw new AlreadyFriendsException();
					}
					break;
				case Helper.RELATIONSHIP_COLLEAGUES:
					if(firstPerson instanceof Adult && secondPerson instanceof Adult){
						Adult firstAdult = (Adult)firstPerson;
						Adult secondAdult = (Adult)secondPerson;
						if(!firstAdult.getColleagues().contains(secondAdult) && !secondAdult.getColleagues().contains(firstAdult)){
							firstAdult.getColleagues().add(secondAdult);
							secondAdult.getColleagues().add(firstAdult);
						}else{
							throw new AlreadyColleaguesException();
						}
					}else{
						throw new NotAllAdultsException();
					}
					break;
				case Helper.RELATIONSHIP_CLASSMATES:
					if(firstPerson instanceof Adult && secondPerson instanceof Adult){
						Adult firstAdult = (Adult)firstPerson;
						Adult secondAdult = (Adult)secondPerson;
						if(!firstAdult.getColleagues().contains(secondAdult) && !secondAdult.getColleagues().contains(firstAdult)){
							firstAdult.getColleagues().add(secondAdult);
							secondAdult.getColleagues().add(firstAdult);
						}else{
							throw new AlreadyColleaguesException();
						}
					}else if(firstPerson instanceof Child && secondPerson instanceof Child){
						if(!(firstPerson instanceof YoungChild) && !(secondPerson instanceof YoungChild)){
							Child firstChild = (Child)firstPerson;
							Child secondChild = (Child)secondPerson;
							if(!firstChild.getClassmates().contains(secondChild) && !secondChild.getClassmates().contains(firstChild)){
								firstChild.getClassmates().add(secondChild);
								secondChild.getClassmates().add(firstChild);
							}else{
								throw new AlreadyColleaguesException();
							}
						}else{
							throw new YoungChildClassmatesException();
						}
					}else{
						throw new DifferentIdentitiesException();
					}
					break;
				default:
					break;
    		}
    		Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Successful!", "Building relationships ssccessfully!");
    		DataBase.getInstance().notifyDataChanged();
    		
		}catch(NotToBeCoupledException e){
			Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Exception!", "one member is not an adult.");
		}catch(NoAvailableException e){
			Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Exception!", "one of them is already connected with another adult as a couple.");
		}catch(SameGenderException e){
			Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Exception!", "The genders of them are same.");
		}catch(AllAdultsOrAllChildrenException e){
			Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Exception!", "They are all adults or children.");
		}catch(NoPartnerException e){
			Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Exception!", "The adult has no partner!");
		}catch(HasParentsException e){
			Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Exception!", "The child already has parents!");
		}catch(AlreadyFriendsException e){
			Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Exception!", "They are aldready friends!");
		}catch(DifferentIdentitiesException e){
			Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Exception!", "The one is an adult but another one is a child!");
		}catch(YoungChildFriendException e){
			Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Exception!", "Young child cannot have any friend!");
		}catch(NotAllAdultsException e){
			Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Exception!", "Only both adults can make connection as colleagues.");
		}catch(AlreadyColleaguesException e){
			Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Exception!", "They are aldready colleagues!");
		}catch(YoungChildClassmatesException e){
			Helper.showAlertDialog(BuildRelationshipDialog.this.frm, "Exception!", "Young child cannot have classmates!");
		}

    }

    
}
