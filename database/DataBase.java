package com.socialnetwork.assign2.database;
/**
 * @author Anqi Li s3633399
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.socialnetwork.assign2.dao.Adult;
import com.socialnetwork.assign2.dao.Child;
import com.socialnetwork.assign2.dao.Person;
import com.socialnetwork.assign2.dao.Relation;
import com.socialnetwork.assign2.dao.YoungChild;

public class DataBase {

	private static String peopleFileName="people.txt";
	private static String relationsFileName="relations.txt";
	
	private static DataBase instance;
	private ArrayList<Person> personList = new ArrayList<Person>(); // all users in the social network
	private ArrayList<Adult> adultList = new ArrayList<Adult>();		// all adults in the social network
	private ArrayList<Child> childList = new ArrayList<Child>();// all children in the social network
	private ArrayList<YoungChild> youngChildList = new ArrayList<YoungChild>();// all youngchildren in the social network
	
	private LinkedList<DataBaseListener> dbListeners = new LinkedList<DataBaseListener>();
	
	public static DataBase getInstance(){
		if(instance == null){
			instance = new DataBase();
		}
		return instance;
	}
	
	private DataBase(){
		
		loadData();
	}
	
	
	/**
	 * initialize data when startup.
	 */
	private void loadData(){
		
		loadPeopleFile();
		loadRelationsFile();
		
	}
	
	
	/**
	 * load data from people file
	 */
	public void loadPeopleFile(){
		
	     try {
	            // read document id and number from lexicon file
	           
	            FileReader reader = new FileReader(peopleFileName);
	            BufferedReader br = new BufferedReader(reader);
	           
	            String temp = null;
	            String info[] = null;
	            while((temp = br.readLine()) != null) {
	            	info = temp.split(", ");
	            	int i = 0;
	            	String name = info[i++];
	            	String photoPath = info[i++];
	            	String status = info[i++];
	            	String gender = info[i++];
	            	Integer age = Integer.parseInt(info[i++].trim());
	            	String state = info[i];
	            	
	            	if(age > 16){
	            		Adult adult = new Adult(name,photoPath,status,gender,age,state);
	            		this.adultList.add(adult);
	            		this.personList.add(adult);
	            	}else if( age <=16 && age >=3){
	            		Child teen = new Child(name,photoPath,status,gender,age,state);
	            		this.childList.add(teen);
	            		this.personList.add(teen);
	            	}else{
	              		YoungChild teen = new YoungChild(name,photoPath,status,gender,age,state);
	              		this.youngChildList.add(teen);
	            		this.childList.add(teen);
	            		this.personList.add(teen);
	            	}

	            }
	           
	            br.close();
	            reader.close();
	      }
	      catch(FileNotFoundException e) {
	                  e.printStackTrace();
	      }
	     catch(IOException e) {
              e.printStackTrace();
	     }
	}
	
	public void loadRelationsFile(){
		
		try {
            // read document id and number from lexicon file
           
            FileReader reader = new FileReader(relationsFileName);
            BufferedReader br = new BufferedReader(reader);
           
            String temp = null;
            String info[] = null;
            while((temp = br.readLine()) != null) {
            	info = temp.split(", ");
            	int i = 0;
            	String name1 = info[i++];
            	String name2 = info[i++];
            	String relation = info[i].toLowerCase().trim();
            	
            	if(relation.equals("friends")){
            		linkFriendRelation(name1,name2);
            	}else if(relation.equals("couple")){
            		linkCoupleRelation(name1,name2);
            	}else if(relation.equals("parent")){
            		linkParentRelation(name1,name2);
            	}

            }
           
            br.close();
            reader.close();
      }
      catch(FileNotFoundException e) {
                  e.printStackTrace();
      }
     catch(IOException e) {
          e.printStackTrace();
     }
	}
	
	
	private void linkFriendRelation(String name1,String name2){
		
		Person person1 = getUserByName(name1);
		Person person2 = getUserByName(name2);
		if(person1 != null && person2 != null){
			Person.buildFrendsRelation(person1,person2);
		}
	}
	
	private void linkCoupleRelation(String name1,String name2){
		
		Adult adult1 = this.getAdultUserByName(name1);
		Adult adult2 = this.getAdultUserByName(name2);
		if(adult1 != null && adult2 != null){
			Adult.buildPartnerRelationship(adult1, adult2);
		}
		
	}
	
	private void linkParentRelation(String name1,String name2){
		
		Person person1 = getUserByName(name1);
		Person person2 = getUserByName(name2);
		Adult parent = null;
		Child child = null;
		if(person1 instanceof Adult && person2 instanceof Child){
			
			parent = (Adult)person1;
			child = (Child)person2;
			
		}else if(person2 instanceof Adult && person1 instanceof Child){
			
			parent = (Adult)person2;
			child = (Child)person1;
		}
		if(parent != null && child != null){
			Child.BuildParentRelation(child,parent);
		}
	}
	
	/**
	 *  search a user by name
	 * @param name
	 * @return
	 */
	public Person getUserByName(String name){
		
		Person user = null;
		
		for(Person cur : personList){
			if(cur.getName().equals(name)){
				user = cur;
				break;
			}
		}
		
		return user;
	}
	
	/**
	 * search a adult user by name
	 * @param name
	 * @return
	 */
	public Adult getAdultUserByName(String name){
	
		Adult user = null;
		
		for(Adult cur : adultList){
			if(cur.getName().equals(name)){
				user = cur;
				break;
			}
		}
		
		return user;
	}
	
	/**
	 * search a teenager user by name
	 * @param name
	 * @return
	 */
	public Child getTeenagerUserByName(String name){
		
		Child user = null;
		
		for(Child cur : childList){
			if(cur.getName().equals(name)){
				user = cur;
				break;
			}
		}
		
		return user;
	}
	
	/**
	 * add new user
	 */
	public boolean addNewUser(Person newUser){
		boolean flag = false;
		if(newUser != null && !this.personList.contains(newUser)){
			personList.add(newUser);
			if(newUser instanceof Adult){
				this.adultList.add((Adult)newUser);
			}else{
				this.childList.add((Child)newUser);
			}
			flag = true;
			notifyDataChanged();
		}
		return flag;
	}
	
	/**
	 * delete a user from db system
	 * @param user
	 * @return
	 */
	public boolean deleteUser(Person user){
		if(user == null) return false;
		boolean flag = false;
		for(Person temp : personList){
			if(temp == user){
				
				for(Person friend : temp.friendList){
					friend.friendList.remove(temp);
				}
				personList.remove(user);
				flag = true;
			}
			break;
		}
		
		return flag;
	}

	public void addDBListener(DataBaseListener listener){
		this.dbListeners.add(listener);
	}
	
	public ArrayList<Person> getPersonList(){
		return personList;
	}
	
	
	public void notifyDataChanged(){
		for(DataBaseListener listener : dbListeners){
			listener.onPersonListChanged();
		}
	}
	
	public boolean delPersonFromSystem(Person person){
		boolean result = true;
		person.removeAllRelationships();
		this.personList.remove(person);

		
		if(person instanceof Adult){
			this.adultList.remove(person);

		}else if(person instanceof Child){
			childList.remove(person);
			if(person instanceof YoungChild){
				this.youngChildList.remove(person);
			}
		}
		this.notifyDataChanged();
		return result;
	}
	

}
