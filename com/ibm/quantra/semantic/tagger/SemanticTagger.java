/*
 * Created on Jan 25, 2005
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.semantic.tagger;

import java.util.Enumeration;
import java.util.Vector;

import com.ibm.graph.Net;
import com.ibm.graph.Vertex;
import com.ibm.quantra.semantic.LexiconContainerInterface;
import com.ibm.quantra.semantic.SemanticNet;
import com.ibm.quantra.utilities.UserdictConstants;
import com.ibm.research.util.KeyMissingException;
/**
 * @author Bastin
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SemanticTagger {
	private SemanticTag semanticTag;
 
	
	public SemanticTagger(){
		semanticTag = new SemanticTag();
	}
	public SemanticTag getSemanticTag(Net syntaxNet,SemanticNet semanticNet,LexiconContainerInterface lexiconContainer){
		populateSemanticTag(syntaxNet,semanticNet,lexiconContainer);
		return semanticTag;
	}
	public QueryData getQueryData(SemanticTag tag,Net syntaxNet){
		QueryDataConstructor queryDataCreator = new QueryDataConstructor();
		QueryData qData = queryDataCreator.createQueryData(tag,syntaxNet);
		return qData;		
	}
	
	private void populateSemanticTag(Net syntaxNet,SemanticNet semanticNet,LexiconContainerInterface lexiconContainer){
		Vector usedWords = getUsedWords(syntaxNet,lexiconContainer);
		semanticTag.addTagKey(usedWords);
		addTagValue(lexiconContainer,semanticNet,usedWords);
	}
	private void addTagValue(LexiconContainerInterface lexiconContainer, SemanticNet semanticNet,Vector usedWords){
				
		for(int i=0;i<usedWords.size();i++){
			String primaryWord = lexiconContainer.getPrimaryWord((String)usedWords.elementAt(i));
			Vector matches = getAllMatches(primaryWord,semanticNet);
			semanticTag.addValueToSemanticTag(primaryWord,matches);
		}
	}
	private Vector getAllMatches(String primaryWord,SemanticNet semanticNet){
		Vector totalMatches = new Vector();
		Vector matchLeafWords = new Vector();
		Enumeration vertices = semanticNet.enumerateVerticesByUserKeySetToValue(UserdictConstants.NAME,primaryWord);
		while(vertices.hasMoreElements()){
			Vertex v = (Vertex)vertices.nextElement();
			if(isLeaf(v)){
				Enumeration neighbors = v.enumerateIncomingNeighbors();
				while(neighbors.hasMoreElements()){
					Vertex neighbor = (Vertex)neighbors.nextElement();
					String neighborName = null;
					try {
						neighborName = neighbor.userdict.getString(UserdictConstants.NAME);
					} catch (KeyMissingException e) {e.printStackTrace();}
					if(!matchLeafWords.contains(neighborName))
						matchLeafWords.add(neighborName);
				}
			}
			else{
				TagValueContent tvg = new TagValueContent();
				String name=null;
				try {
					name = v.userdict.getString(UserdictConstants.NAME);
				} catch (KeyMissingException e) {e.printStackTrace();}
				tvg.setType(SemanticTaggerConstants.ENTITY);
				tvg.setContent(name);
				totalMatches.add(tvg);	
			}
		}
		if(matchLeafWords.size()!=0)
			addMatchLeafWords(totalMatches,matchLeafWords);
		return totalMatches;
	}
	private void addMatchLeafWords(Vector totalWords,Vector leafWords){
		for(int i=0;i<leafWords.size();i++){
			String word = (String)leafWords.elementAt(i);
			TagValueContent tvg = new TagValueContent();
			tvg.setType(SemanticTaggerConstants.ATTRIBUTE_OF);
			tvg.setContent(word);
			totalWords.add(tvg);
		}
	}
	private boolean isLeaf(Vertex vertex){
		if(vertex.degreeOutgoing()==0)
			return true;
		return false;			
	}
	private Vector getUsedWords(Net syntaxNet,LexiconContainerInterface lexiconContainer){
		Vector v = new Vector();
		String name = null;
		Enumeration vertices = syntaxNet.enumerateVertices();
		while(vertices.hasMoreElements()){
			Vertex vertex = (Vertex)vertices.nextElement();
			try {
				name = vertex.userdict.getString(UserdictConstants.NAME);
			} catch (KeyMissingException e) 
			{	try {
					name = vertex.userdict.getString(UserdictConstants.AUG_NAME);
					} catch (KeyMissingException e1) {e1.printStackTrace();}
			}
			if(lexiconContainer.getPrimaryWord(name)!=null){
				v.add(name);
			}
		}
		return v;
	}
}