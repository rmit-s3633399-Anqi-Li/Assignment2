package com.socialnetwork.assign2.database;

/**
 * 
 * @author Anqi Li s3633399 
 *  a listener to listen any data changes from database
 *
 */
public interface DataBaseListener {
	
	/**
	 * call back this method when person list has changed.
	 */
	public void onPersonListChanged();
	
	/**
	 * call back this method when relationship has changed.
	 */
	public void onRelationshipChanged();

}
