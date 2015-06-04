/*
 * Created on Mar 3, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.queryconstructor;

import java.util.Enumeration;
import java.util.Vector;

import com.ibm.graph.Vertex;
import com.ibm.graph.Edge;
import com.ibm.quantra.semantic.SemanticNet;
import com.ibm.quantra.semantic.tagger.DatabaseValue;
import com.ibm.quantra.semantic.tagger.QueryData;
import com.ibm.quantra.semantic.tagger.QuestionPart;
import com.ibm.quantra.semantic.tagger.SemanticTaggerConstants;
import com.ibm.quantra.utilities.UserdictConstants;
import com.ibm.research.util.KeyMissingException;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryConstructor{
	private String query;
	//returns the XPath query after construction.
	public String getQuery(QueryData queryData,SemanticNet semanticNet){
		QuestionPart qnPart = queryData.getQuestionPart();
		Vector dbValues = queryData.getDBValues();
		//get all the node in the path of the query.
		Vector qnPathNodes = findPathOfQn(qnPart,semanticNet);
		if(qnPathNodes==null) return null;
		Vector alignedDatabaseValues = null;
		if(dbValues.size()!=0){
		   //preprocess the database values
		   dbValues = new DatabaseValuePreprocessor().preprocess(dbValues);
		   DatabaseValueAligner aligner = new DatabaseValueAligner();
		   alignedDatabaseValues = new Vector();
		   for(int i=0;i<dbValues.size();i++){
		   		DatabaseValue currentValue = (DatabaseValue)dbValues.elementAt(i);
		   		AlignedDatabaseValue aDbValue = aligner.getAlignedDatabaseValue(currentValue,qnPathNodes,semanticNet);
		   		aDbValue.setConstant(currentValue.getDbValue());
				alignedDatabaseValues.add(aDbValue);			   					
		   }	
		}
		query = constructQuery(alignedDatabaseValues,qnPathNodes);
		//this contains the final query.
		return query;
	}
	private String constructQuery(Vector alignedDatabaseValues,Vector qnPathNodes){
		String str = null;
		StringBuffer strBufferResult = new StringBuffer();
		String tempStr = null;
		String repStr = null;
		for(int i=qnPathNodes.size()-1;i>=0;i--){
			strBufferResult.append(QueryConstructorConstants.F_SLASH);
			strBufferResult.append((String)qnPathNodes.elementAt(i));
		}
		tempStr = new String(strBufferResult);
		
		if(alignedDatabaseValues!=null){
			strBufferResult = new StringBuffer();
			for(int i=0;i<alignedDatabaseValues.size();i++){
				AlignedDatabaseValue alignedValue = (AlignedDatabaseValue)alignedDatabaseValues.elementAt(i);
				String condition = constructCondition(alignedValue);
				String root = alignedValue.getRoot();
				repStr = tempStr.replaceFirst(root,condition);
				strBufferResult.append(repStr);
				if(i!=alignedDatabaseValues.size()-1){
					strBufferResult.append(QueryConstructorConstants.AMPERSAND);
				}
			}	
			str = new String(strBufferResult);
		}
		else
			str = tempStr;
		return str;
	}
	private String constructCondition(AlignedDatabaseValue alignedValue){
		String root = alignedValue.getRoot();
		Vector nodes = alignedValue.getNodes();
		String constant = alignedValue.getConstant();
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(root);
		strBuffer.append(QueryConstructorConstants.OPENING_SQUARE_BRACKET);
		for(int i=0;i<nodes.size();i++){
			strBuffer.append((String)nodes.elementAt(i));
			if(i!=nodes.size()-1)
				strBuffer.append(QueryConstructorConstants.F_SLASH);
		}
		strBuffer.append(QueryConstructorConstants.EQUAL);
		strBuffer.append(QueryConstructorConstants.APOSTROPHE);
		strBuffer.append(constant);	
		strBuffer.append(QueryConstructorConstants.APOSTROPHE);
		strBuffer.append(QueryConstructorConstants.CLOSING_SQUARE_BRACKET);	
		return new String(strBuffer);
	}
	private Vector findPathOfQn(QuestionPart qnPart, SemanticNet semanticNet) {
		if(qnPart == null) return null;
		Vector pathNodes =new Vector();
		String root = qnPart.getRoot();
		String word = qnPart.getQuestionWord();
		if(root.compareTo(SemanticTaggerConstants.NIL)==0){
			pathNodes.add(word);
			Enumeration vertices = semanticNet.enumerateVerticesByUserKeySetToValue(UserdictConstants.NAME,word);
			if(vertices.hasMoreElements()){
				Vertex vertex = (Vertex)vertices.nextElement();
				tracePath(vertex,pathNodes);
			}
		}
		else {
			pathNodes.add(word);
			pathNodes.add(root);
			Enumeration vertices = semanticNet.enumerateVerticesByUserKeySetToValue(UserdictConstants.NAME,root);
			if(vertices.hasMoreElements()){
				Vertex vertex = (Vertex)vertices.nextElement();
				tracePath(vertex,pathNodes);
			}
		}					
		return pathNodes;
	}
	
	// a recursive function which traces the path till the root.
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
