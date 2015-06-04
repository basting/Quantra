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
public class AlignedDatabaseValue {
	private String root;
	private Vector nodes;
	private String constant;
	
	public void setRoot(String root){
		this.root = root;
	}
	public void setNodes(Vector nodes){
		this.nodes = nodes;
	}
	public void setConstant(String constant){
		this.constant = constant;
	}
	public String getRoot(){
		return root;
	}
	public Vector getNodes(){
		return nodes;
	}
	public String getConstant(){
		return constant;
	}
}
