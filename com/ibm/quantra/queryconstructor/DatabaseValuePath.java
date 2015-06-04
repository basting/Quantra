/*
 * Created on Mar 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.queryconstructor;

import java.util.Vector;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DatabaseValuePath {
	private int score;
	private Vector nodes;
	
	public DatabaseValuePath(){
		nodes = new Vector();
	}
	public int getScore(){
		return score;
	}
	public Vector getPathNodes(){
		return nodes;
	}
	public void setScore(int score){
		this.score = score;
	}
	public void setNode(Vector nodes){
		this.nodes.addAll(nodes);
	}
	public void setNode(String node){
		nodes.add(node);
	}
}
