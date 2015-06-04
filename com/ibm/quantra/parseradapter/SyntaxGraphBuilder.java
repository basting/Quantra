/*
 * Created on Jun 28, 2004
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.parseradapter;


import java.util.*;
import com.ibm.graph.*;
import com.ibm.research.util.*;
import com.ibm.quantra.utilities.QConstants;
import com.ibm.quantra.utilities.Trace;
/**
 * @author Administrator
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SyntaxGraphBuilder {
	public Net constructSyntaxGraph(String sentence,String postscriptNotation,Net net,String[] parsedWords){
		Vector words;
		GraphBuilder gb;
		Net netHere=net;
//		1.build the graph using the given sentence and the links provided as separate string.
				StringOperation sop=new StringOperation();
				gb=new GraphBuilder();
//		1.a the vertices of the graph are built using the words splitted from the sentence.  
				words=sop.readWords(sentence);
				try {
					netHere=gb.createVertices(words, netHere,parsedWords);
				} catch (KeyMissingException e) {
					Trace.Print(QConstants.FATAL,"The KeyMissing for vertices");
					e.printStackTrace();
				}
//		
				Vector individualLinks=gb.readLink(postscriptNotation);
				try {
					netHere=gb.createEdges(individualLinks,netHere,parsedWords);
				} catch (KeyMissingException e1) {
					Trace.Print(QConstants.FATAL,"The KeyMissing for Edges");
					e1.printStackTrace();					
				}				
		return netHere;
	}	
}
