/*
 * Created on Oct 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.testapplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Enumeration;


import com.ibm.graph.Edge;
import com.ibm.graph.Vertex;
import com.ibm.quantra.semantic.SemanticNet;
import com.ibm.quantra.semantic.XmlSchemaParser;
import com.ibm.quantra.utilities.UserdictConstants;
import com.ibm.research.util.KeyMissingException;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XmlSchemaParserTester {

	public static void main(String[] args) throws KeyMissingException {
		XmlSchemaParser parser = new XmlSchemaParser();
		// Change the File Name of the Schema below
		String fileName = "xsdFiles\\graph.xsd";
		FileInputStream fis=null;
		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SemanticNet myGraph = parser.giveGraph(fis,new SemanticNet());
		try {
			printGraph(myGraph);
		} catch (KeyMissingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//to get the lexicon from the semanticNet.
		/*Vector lexicon = myGraph.getLexicon();
		for(int i=0;i<lexicon.size();i++){
			System.out.println(lexicon.elementAt(i));
		}*/			
	}
	public static void printGraph(SemanticNet myGraph) throws KeyMissingException{
		Enumeration edgeSet=myGraph.enumerateEdges();
		
		System.out.println("The Output is in the Order:");
		System.out.println("childVertex.Name, edge.name, edge.OrderIndicator, edge.type, ParentVertex.name");
		System.out.println("------------------------------------------------------------------------------");
			
		while(edgeSet.hasMoreElements()){
			Edge edge=(Edge)edgeSet.nextElement();
			Vertex v1=edge.getFromVertex();
			Vertex v2=edge.getToVertex();
			
			System.out.print(v1.userdict.getString(UserdictConstants.NAME)+"<----");
			System.out.print(edge.userdict.getString(UserdictConstants.NAME)+"-"+edge.userdict.getString(UserdictConstants.ORDER_INDICATOR)+"-"+edge.userdict.getString(UserdictConstants.TYPE)+"---->");
			System.out.print(v2.userdict.getString(UserdictConstants.NAME));
			
			if(v2.userdict.containsKey(UserdictConstants.TYPE))
				System.out.print("------------"+v2.userdict.getString(UserdictConstants.TYPE));
			System.out.println();
		}
	/*
		Enumeration vertexEnumeration = myGraph.enumerateVertices();

		while(vertexEnumeration.hasMoreElements()){	
			Vertex vertex= (Vertex)vertexEnumeration.nextElement();
			System.out.print(vertex.userdict.getString(UserdictConstants.NAME));
			Enumeration edgeEnumeration = vertex.enumerateEdges();
			int degreeOut = 0,degreeIn = 0;
			while(edgeEnumeration.hasMoreElements()){
				Edge edge = (Edge) edgeEnumeration.nextElement();
				if(edge.getFromVertex().equals(vertex))
					degreeOut++;
				else if(edge.getToVertex().equals(vertex))
					degreeIn++;
			}
			System.out.println(degreeOut+" "+degreeIn);
		}*/	
	}
}