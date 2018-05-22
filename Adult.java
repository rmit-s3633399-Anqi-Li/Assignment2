package com.socialnetwork.assign2.dao;
import java.util.ArrayList;
/**
 * @author Anqi Li s3633399
public class Adult extends Person {

	private Adult partner = null;
	private ArrayList<Child> children = new ArrayList<Child>();
	private ArrayList<Adult> classmates = new ArrayList<Adult>();
	private ArrayList<Adult> colleagues = new ArrayList<Adult>();
	
	public Adult(String name, String photoPath, String status, String gender, int age, String state) {
		super(name, photoPath, status, gender, age, state);
	}

	
	public boolean addChild(Child newChild){
		boolean flag = false;
		if(newChild.getFather() != this && newChild.getMother() != this){
			flag =  false;
		}
		else if(children.contains(newChild)){
			flag =  false;
		}else{
			children.add(newChild);
			flag = true;
		}
		return flag;
	}
	
	public Adult getPartner(){
		return this.partner;
	}
	
	public boolean setPartner(Adult partner){
		if(this.partner == null && partner.getPartner() == null && !this.getGender().equals(partner.getGender())){
			this.partner = partner;
			return true;
		}else{
			return false;
		}
		
	}
	public void divorce(){
		for(Child child : this.children){
			child.removeParents();
		}
		this.children.clear();
		if(this.partner != null){
			this.partner.children.clear();
			this.partner.partner = null;
			this.partner = null;
		}


	}
	
	public static boolean buildPartnerRelationship(Adult a, Adult b){
		
		boolean flag = false;
		if(!a.getGender().equals(b.getGender()) && a.getPartner() == null && b.getPartner() == null ){
			a.setPartner(b);
			b.setPartner(a);
			flag = true;
		}
		return flag;
	}

	public ArrayList<Child> getChildren(){
		return children;
	}
	
	public ArrayList<Adult> getClassmates(){
		return classmates;
	}
	
	public ArrayList<Adult> getColleagues(){
		return colleagues;
	}
	
	public String[] toRelationshipStringArray(){
		String[] basicInfo = new String[5];
		int i = 0;
		basicInfo[i++] = super.getName();
		basicInfo[i++] = "Adult";
		basicInfo[i++] = "";
		basicInfo[i++] = "";
		basicInfo[i++] = partner == null ? "" : partner.getName();
		
		return basicInfo;
	}
	
	public static void buildClassmateRelation(Adult classmate1,Adult classmate2){
		
		classmate1.classmates.add(classmate2);
		classmate2.classmates.add(classmate1);
	}
	
	public static void buildColleagueRelation(Adult colleague1,Adult colleague2){
		
		colleague1.colleagues.add(colleague2);
		colleague2.colleagues.add(colleague1);
	}


	@Override
	public void removeAllRelationships() {
		
		super.removeAllRelationships();
		this.divorce();
		
		//remove classmate relationship
		for(Adult classmate : classmates){
			classmate.classmates.remove(this);
		}
		this.classmates.clear();
		
		//remove colleague relationship
		for(Adult colleague : colleagues){
			colleague.colleagues.remove(this);
		}
		this.colleagues.clear();
		
	}

}
