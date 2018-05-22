package com.socialnetwork.assign2.dao;

import java.util.ArrayList;

/**
 * Teenager(dependent)
 * Description:
 * 
 * Other than “friendships”, there are other types of connections between people. 
 * Some persons on the network are under 16 years of age. They are considered as dependents to adults. 
 * No isolated dependent can exist on the network meaning a dependent must have some connections. 
 * They always connect to exactly two adults who are the parents. The parents of a dependent must connect. 
 * You can assume there are no orphans, no single parents in this scenario.
 *  All couples are mutually exclusive to other couples. Situations like “A and B are the parents of X, 
 *  A and C are the parent of Y” do not exist in this exercise. However an adult may have no connections.
 *  A dependent can be a friend ONLY to another dependent who is also younger than 16 and from a different family. 
 *  The age difference between these two young friends cannot be more than 3 years. 
 *  A person who is 2 years old or younger does not have any friends. 
 *  For example a 4-year-old cannot be friend with a 2-year-old although their age difference is only 2.
 *
 */
public class Child extends Person {


	//a teenager who is under 16  must have parents in the social network 
	private Adult father;
	private Adult mother;
	private ArrayList<Child> classmates = new ArrayList<Child>();
	
	public Child(String name, String photoPath, String status, String gender, int age, String state) {
		super(name, photoPath, status, gender, age, state);
	}
	
	public void setFather(Adult father){
		this.father = father;
	}
	
	public void setMother(Adult mother){
		this.mother = mother;
	}
	
	public void setParent(Adult parent){
		
		if(parent.getGender().toUpperCase().equals("M")){
			this.father = parent;
		}else{
			this.mother = parent;
		}
	}

	public Adult getFather() {
		return father;
	}
	public Adult getMother() {
		return mother;
	}
	
	
	public static void BuildParentRelation(Child child,Adult parent){
		
		if(parent.getGender().equals("M")){
			child.setFather(parent);
		}else{
			child.setMother(parent);
		}
		parent.addChild(child);
	}
	
	public void setParents(Adult parent1,Adult parent2){
		
		if(parent1.getGender().equals("M")){
			this.setFather(parent1);
			this.setMother(parent2);
		}else{
			this.setFather(parent2);
			this.setMother(parent1);
		}
		parent1.addChild(this);
		parent2.addChild(this);
	}
	
	public String[] toRelationshipStringArray(){
		String[] basicInfo = new String[5];
		int i = 0;
		basicInfo[i++] = super.getName();
		basicInfo[i++] = "Child";
		basicInfo[i++] = father==null ? "" : father.getName();
		basicInfo[i++] = mother==null ? "" : mother.getName();
		basicInfo[i++] = "";
		
		return basicInfo;
	}
	
	public ArrayList<Child> getClassmates(){
		return classmates;
	}

	public static void buildClassmateRelation(Child classmate1,Child classmate2){
		
		classmate1.classmates.add(classmate2);
		classmate2.classmates.add(classmate1);
	}

	public void removeParents(){
		this.father = null;
		this.mother = null;
	}

	@Override
	public void removeAllRelationships() {
		
		super.removeAllRelationships();
		
		removeParents();
		//remove classmate relationship
		for(Child classmate : classmates){
			classmate.classmates.remove(this);
		}
		this.classmates.clear();
		
		
	}

}
