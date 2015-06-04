/*
 * Created on Feb 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.semantic.tagger;

import java.util.Enumeration;
import java.util.Vector;

import com.ibm.graph.Edge;
import com.ibm.graph.Net;
import com.ibm.graph.Vertex;
import com.ibm.quantra.utilities.UserdictConstants;
import com.ibm.research.util.KeyMissingException;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryDataConstructor {
	
	//the subgraph is to be filled using the SemanticTag and the syntaxNet
	public QueryData createQueryData(SemanticTag tag,Net syntaxNet){
		String questionVerb = getQuestionVerb(syntaxNet);
		String queryWord = getQueryWord(questionVerb,syntaxNet,tag);
		String typeOfQueryWord = tag.getTypeOfWord(queryWord);
		QuestionPart qPart = null;
		String entity = null;
		QueryData qData = new QueryData();
		if(typeOfQueryWord.compareTo(SemanticTaggerConstants.ATTRIBUTE_OF)==0){
			Vector entities = tag.getAllEntities();
			for(int i=0;i<entities.size();i++){
				entity = (String)entities.elementAt(i);
				if(tag.hasAsEntity(queryWord,entity)){
					qPart = new QuestionPart();
					qPart.setQuestionWord(queryWord);
					qPart.setRoot(entity);
					break;	
				}
			}
		}else if(typeOfQueryWord.compareTo(SemanticTaggerConstants.ENTITY)==0){
			qPart = new QuestionPart();
			qPart.setQuestionWord(queryWord);
			qPart.setRoot(SemanticTaggerConstants.NIL);
		}
		DatabaseValueExtractorInterface extractor = new LinkTypeValueExtractor();
		Vector dbWords = extractor.getDatabaseValues(syntaxNet);
		
		qData.setQuestionPart(qPart);
		qData.setDataBaseValues(dbWords);	
		
		return qData;		
	}	
	private String getQueryWord(String qnVerb,Net syntaxNet,SemanticTag tag){
		int wordNo = getWordNo(qnVerb,syntaxNet);
		Vector primes = tag.getTagKeys();
		int nextWordNo = 999;
		for(int i=0;i<primes.size();i++){
			String element = (String)primes.elementAt(i);
			if(nextWordNo > getWordNo(element,syntaxNet)){
				nextWordNo = getWordNo(element,syntaxNet);
			}
		}
		String retValue = getString(nextWordNo,syntaxNet);
		return retValue;		
	}
	private int getWordNo(String word,Net syntaxNet){
		Enumeration vertices = syntaxNet.enumerateVerticesByUserKeySetToValue(UserdictConstants.NAME,word);
		Vertex v = (Vertex)vertices.nextElement();
		int wordNo = -1;
		try {
			wordNo = v.userdict.getInteger(UserdictConstants.WORD_NO);
		} catch (KeyMissingException e) {e.printStackTrace();}
		
		return wordNo;		
	}
	private String getString(int wordNo,Net syntaxNet){
		String name = null;
		Enumeration enum = syntaxNet.enumerateVerticesByUserKeySetToValue(UserdictConstants.WORD_NO,wordNo);
		if(enum.hasMoreElements()){
			Vertex vertex = (Vertex)enum.nextElement();
		
		try {
			name = vertex.userdict.getString(UserdictConstants.NAME);
		} catch (KeyMissingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return name;
	}
	private String getQuestionVerb(Net syntaxNet){
		String qnVerb = null;
		Enumeration edges = syntaxNet.enumerateEdges();
		while(edges.hasMoreElements()){
			Edge edge = (Edge)edges.nextElement();
			try {
				String edgeName = edge.userdict.getString(UserdictConstants.NAME);
				if(isQuestionLink(edgeName)){
					qnVerb = nameOfToVertex(edge);
					break;
				}
			} catch (KeyMissingException e) {e.printStackTrace();}
			
		}
		return qnVerb;
	}
	public boolean isQuestionLink(String edgeName){
		if(edgeName.startsWith(SemanticTaggerConstants.W)||edgeName.startsWith(SemanticTaggerConstants.Q)){
			return true;
		}
		return false;
	}
	public String nameOfToVertex(Edge edge){
		Vertex vertex = edge.getToVertex();
		String name = null;
		try {
			name = vertex.userdict.getString(UserdictConstants.NAME);
		} catch (KeyMissingException e) {
			e.printStackTrace();
		}
		return name;
	}
	
}
