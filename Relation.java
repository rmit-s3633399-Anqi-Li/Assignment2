package com.socialnetwork.assign2.dao;

public class Relation {
	
	private String person1Name;
	private String person2Name;
	
	private String relation;
	
	public Relation(String name1,String name2,String relation){
		
		this.person1Name = name1;
		this.person2Name = name2;
		this.relation = relation;
		
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Relation)){
			return false;
		}
		Relation r = (Relation)obj;
		if(relation.equals(r.relation)){
			if((this.person1Name.equals(r.person1Name) && this.person2Name.equals(r.person2Name)) 
					|| (this.person1Name.equals(r.person2Name) && this.person2Name.equals(r.person1Name)) ){
				return true;
			}
		}
		return false;
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return person1Name + ", " + person2Name + ", " + relation;
	}
	
	

}
