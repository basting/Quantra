/*
 * Created on Mar 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.queryconstructor;

import java.util.Enumeration;
import java.util.Vector;

import com.ibm.graph.Edge;
import com.ibm.graph.Vertex;
import com.ibm.quantra.semantic.SemanticNet;
import com.ibm.quantra.semantic.tagger.DatabaseValue;
import com.ibm.quantra.utilities.UserdictConstants;
import com.ibm.research.util.KeyMissingException;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DatabaseValueAligner {
	
	public AlignedDatabaseValue getAlignedDatabaseValue(DatabaseValue databaseValue,Vector qnPathNodes,SemanticNet semanticNet){
		AlignedDatabaseValue aDbValue = new AlignedDatabaseValue();
		String word = databaseValue.getDbWord(); 
		Vector dbValuePathNodes = getDbValuePath(word,qnPathNodes,semanticNet);
		constructAlignedDbValue(aDbValue,qnPathNodes,dbValuePathNodes);
		return aDbValue; 
	}	
	private void constructAlignedDbValue(AlignedDatabaseValue aDbValue, Vector qnPathNodes, Vector dbValuePathNodes) {
		String dbValuePathNode = null;
		String qnPathNode = null;
		for(int i=dbValuePathNodes.size()-1,j=qnPathNodes.size()-1;i>=0;i--){
			dbValuePathNode = (String)dbValuePathNodes.elementAt(i);
			if(j>=0)
				qnPathNode = (String)qnPathNodes.elementAt(j);
			if(dbValuePathNode.compareTo(qnPathNode)==0){
				j--;	
			}
			else{
				aDbValue.setRoot((String)dbValuePathNodes.elementAt(i+1));
				Vector values = getRemainingElements(dbValuePathNodes,i);
				aDbValue.setNodes(values);
			}
		}
	}
	private Vector reverse(Vector list){
		Vector newVector = new Vector();
		for(int i=list.size()-1;i>=0;i--){
			newVector.add(list.elementAt(i));
		}
		return newVector;
	}
	private Vector getRemainingElements(Vector dbValuePathNodes, int k) {
		Vector nodes = new Vector();
		for(int i=k;i>=0;i--){
			nodes.add(dbValuePathNodes.elementAt(i));
		}
		return nodes;	
	}
	public Vector getDbValuePath(String word,Vector qnPathNodes,SemanticNet semanticNet){
		Enumeration vertices = semanticNet.enumerateVerticesByUserKeySetToValue(UserdictConstants.NAME,word);
		Vector dbPaths = new Vector();
		while(vertices.hasMoreElements()){
			Vector pathNodes = new Vector();
			Vertex vertex = (Vertex)vertices.nextElement();
			DatabaseValuePath dbPath = new DatabaseValuePath();
			pathNodes.add(word);
			tracePath(vertex,pathNodes);
			int score = Math.abs(pathNodes.size()-qnPathNodes.size());
			dbPath.setScore(score);
			dbPath.setNode(pathNodes);
			dbPaths.add(dbPath);
		}
		DatabaseValuePath opPath = getMinimumScorePath(dbPaths);
		return opPath.getPathNodes();
	}
	private DatabaseValuePath getMinimumScorePath(Vector dbPaths){
		DatabaseValuePath dbPath = null;
		int score = 9999; 
		for(int i=0;i<dbPaths.size();i++){
			if(((DatabaseValuePath)dbPaths.elementAt(i)).getScore()<score){
				dbPath = (DatabaseValuePath)dbPaths.elementAt(i);
				score = dbPath.getScore();
			}			
		}
		return dbPath;
	}
	private void tracePath(Vertex vertex,Vector pathNodes){
		Enumeration incomingEdges = vertex.enumerateIncomingEdges();
		if(incomingEdges.hasMoreElements()){
			Edge edge = (Edge)incomingEdges.nextElement();
			Vertex v = edge.getFromVertex();
			String name = null;
			try {
				name = v.userdict.getString(UserdictConstants.NAME);
			} catch (KeyMissingException e) {e.printStackTrace();}
			pathNodes.add(name);
			tracePath(v,pathNodes);
		}
		else 
			return;	
		 
	}	
}
