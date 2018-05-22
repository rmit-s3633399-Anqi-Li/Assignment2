package com.socialnetwork.assign2.dao;
import java.util.ArrayList;

/**
 * 
 * @author Yinan Jin s3548049
 * Person class
 * which actually is the user class of the social network.
 *
 */
public abstract class Person {

	private String name;
	private String photoPath;
	private int age;
	private String gender;
	private String state;
	private String status;
	
	
	public ArrayList<Person> friendList = new ArrayList<Person>();
	
	public Person(String name,String photoPath,String status,String gender, int age ,String state) {
		this.name = name;
		this.photoPath = photoPath;
		this.status = status;
		this.gender = gender;
		this.age = age;
		this.state = state;
	}

	public String getName(){
		return name;
	}
	
	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getGender(){
		return gender;
	}
	
	public int getAge(){
		return age;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String hobbies) {
		this.state = hobbies;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public static void buildFrendsRelation(Person person1,Person person2){
		
		person1.friendList.add(person2);
		person2.friendList.add(person1);
	}
	
	public String[] toBasicInfoStringArray(){
		String[] basicInfo = new String[5];
		int i = 0;
		basicInfo[i++] = this.getName();
		basicInfo[i++] = ""+this.getAge();
		basicInfo[i++] = this.getGender();
		basicInfo[i++] = this.getState();
		basicInfo[i++] = this.getStatus();
		
		return basicInfo;
	}
	
	public abstract String[] toRelationshipStringArray();
	
	public void removeAllRelationships(){
		
		//delete him from all his friends's friendlist
		for(Person friend : friendList){
			friend.friendList.remove(this);
		}
		this.friendList.clear();
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}
	
	
}
