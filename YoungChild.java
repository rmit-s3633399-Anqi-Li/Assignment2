package com.socialnetwork.assign2.dao;
/**
 * @author Anqi Li s3633399
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
public class YoungChild extends Child { 


	//a teenager who is under 16  must have parents in the social network 
	
	public YoungChild(String name, String photoPath, String status, String gender, int age, String state) {
		super(name, photoPath, status, gender, age, state);
	}

	
	public String[] toRelationshipStringArray(){
		String[] basicInfo = new String[5];
		int i = 0;
		basicInfo[i++] = super.getName();
		basicInfo[i++] = "Young Child";
		basicInfo[i++] = this.getFather()==null ? "" : this.getFather().getName();
		basicInfo[i++] = this.getMother()==null ? "" : this.getMother().getName();
		basicInfo[i++] = "";
		
		return basicInfo;
	}




}
