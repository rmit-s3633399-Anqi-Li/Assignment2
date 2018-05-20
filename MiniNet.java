package com.socialnetwork.assign2;

import java.util.ArrayList;

import com.socialnetwork.assign2.dao.Relation;
import com.socialnetwork.assign2.ui.MainFrame;

/**
 * 
 * main startup class
 *
 */
public class MiniNet {

	public MiniNet() {

	}

	public static void main(String[] args) {
		
		//test
		System.out.println("test relation arraylist");
		ArrayList<Relation> relations = new ArrayList<Relation>();
		relations.add(new Relation("aa","bb","cc"));
		Relation r1 = new Relation("aa","bb","cc");
		if(!relations.contains(r1)){
			relations.add(r1);
		}
		r1 = new Relation("bb","aa","cc");
		if(!relations.contains(r1)){
			relations.add(r1);
		}
		for(Relation r : relations){
			System.out.println(r);
		}
		
		new MainFrame();

	}

}
