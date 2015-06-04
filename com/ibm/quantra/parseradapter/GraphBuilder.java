package com.ibm.quantra.parseradapter;

import com.ibm.graph.*;
import com.ibm.quantra.utilities.UserdictConstants;
import com.ibm.research.util.*;
import java.util.*;


public class GraphBuilder{
//1.  the vertex are created using the method 'createVertex'
	public Net createVertices(Vector words, Net net,String []parsedWords)throws KeyMissingException{
		Vertex newV;
		Vector augmentedWords=new Vector(Arrays.asList(parsedWords));
		for(int i=0, j=0;i<augmentedWords.size();i++){
			String currentWord=(String)augmentedWords.elementAt(i);
			
			Enumeration vertices=net.enumerateVerticesByUserKeySetToValue(UserdictConstants.AUG_NAME,currentWord); 
			if(!vertices.hasMoreElements()){
				/* The assumption is that 
						(1) the size of the 'augmentedWords' is larger than 'words'
						(2) the order of words in 'augmentedWords' is similar to 'words' 
				*/ 
				newV=new Vertex();
				newV.userdict.def(UserdictConstants.AUG_NAME,currentWord);
				newV.userdict.def(UserdictConstants.WORD_NO,i);
				String originalWord = (String)words.elementAt(j);
				if(j==0)
					originalWord = originalWord.toLowerCase();
				if(currentWord.startsWith(originalWord)) {  
					newV.userdict.def(UserdictConstants.NAME,originalWord);
					j++;
				}
				if(j==words.size()){
					j--;
				}
				net.add(newV);
			}
			else j++;
		}
		return net;
	}
//2.  the individual links are read
	public Vector readLink(String presentLinks){
	 Vector links=new Vector();
	 StringTokenizer st=new StringTokenizer(presentLinks,ParserAdapterConstants.CLOSING_SQUARE_BRACKET);
	 while(st.hasMoreTokens()){
	 	String s=st.nextToken(); 
	 	s=s.replace(ParserAdapterConstants.SQUARE_BRACKET_OPENING,ParserAdapterConstants.SINGLE_SPACE);
	 	s=s.replace(ParserAdapterConstants.SQUARE_BRACKET_CLOSING,ParserAdapterConstants.SINGLE_SPACE);
	 	s=s.trim();
	 	links.add(s);
	 }
	 return links;
	}
//3.  the edges are created using the method 'createEdges'
 public Net createEdges(Vector links,Net net,String[] augmentedWords) throws KeyMissingException{
		int flag=0,tag=0;
		Vertex v1=null,v2=null;
		Vector parsedWords=new Vector(Arrays.asList(augmentedWords));
		for(int j=0;j<links.size();j++){
			String s=(String)links.elementAt(j);
			StringTokenizer st=new StringTokenizer(s);
			String wor1=st.nextToken();
			String wor2=st.nextToken();
			st.nextToken();
			String lin=st.nextToken();
			lin = processLink(lin);
			
			int word1_no=Integer.parseInt(wor1);
			int word2_no=Integer.parseInt(wor2);
			
			String word1 = (String)parsedWords.elementAt(word1_no); 
			String word2 = (String)parsedWords.elementAt(word2_no);
			
			Enumeration e1=net.enumerateVerticesByUserKeySetToValue(UserdictConstants.AUG_NAME,word1);
			Enumeration e2=net.enumerateVerticesByUserKeySetToValue(UserdictConstants.AUG_NAME,word2);
			
			v1=(Vertex)e1.nextElement();
			v2=(Vertex)e2.nextElement();
			
			Edge edge=new Edge(v1,v2,true);
			edge.userdict.def(UserdictConstants.NAME,lin);
			net.add(edge);
		}
		return net;
	}
	private String processLink(String link){
		link = link.replace(ParserAdapterConstants.OPENING_BRACKET,ParserAdapterConstants.SINGLE_SPACE);
		link = link.replace(ParserAdapterConstants.CLOSING_BRACKET,ParserAdapterConstants.SINGLE_SPACE);
		link = link.trim();
		return link;
	} 		
 	public static void printGraph(Net net) throws KeyMissingException{
 		/*Enumeration edgeSet=net.enumerateEdges();
 		while(edgeSet.hasMoreElements()){
 			Edge edge=(Edge)edgeSet.nextElement();
 			Vertex v1=edge.getFromVertex();
 			Vertex v2=edge.getToVertex();
 			System.out.print(v1.userdict.getString(UserdictConstants.AUG_NAME)+"<----");
 			System.out.print(edge.userdict.getString(UserdictConstants.NAME)+"---->");
 			System.out.println(v2.userdict.getString(UserdictConstants.AUG_NAME));
 		}*/
 		Enumeration vertexSet = net.enumerateVertices();
 		while(vertexSet.hasMoreElements()){
 			Vertex vertex = (Vertex)vertexSet.nextElement();
 			if(vertex.userdict.getBoolean(UserdictConstants.IS_KEYWORD)){
 				System.out.print(vertex.userdict.getString(UserdictConstants.AUG_NAME));
 				System.out.print("  ");
 				System.out.println(vertex.userdict.getString(UserdictConstants.NAME));				
 			}
 			
 		}
	}	
}
