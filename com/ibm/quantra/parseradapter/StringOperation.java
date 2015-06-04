
package com.ibm.quantra.parseradapter;

import java.util.*;


public class StringOperation {
	
	public String getPostscript(String linkagePostscript){
		String s=null;
		int startIndex,endIndex;
		startIndex=linkagePostscript.indexOf(ParserAdapterConstants.OPENING_DOUBLE_SQUARE_BRACKET);
		endIndex=linkagePostscript.indexOf(ParserAdapterConstants.CLOSING_DOUBLE_SQUARE_BRACKET);
		s=linkagePostscript.substring(startIndex,endIndex+2);
		return s;
	}
	public String removeNewLine(String para){
		StringBuffer sb=new StringBuffer(para);
		int i=sb.indexOf(ParserAdapterConstants.NEWLINE_CHARACTER);
		while(i!=-1){
			sb.setCharAt(i,ParserAdapterConstants.SINGLE_SPACE);
			i=sb.indexOf(ParserAdapterConstants.NEWLINE_CHARACTER);
		}
		return new String(sb);		
	}
	public Vector readSentences(String para){
		Vector s=new Vector();
		int i=0;
		StringTokenizer st=new StringTokenizer(para,ParserAdapterConstants.DOT);
		while(st.hasMoreTokens()){
			s.add(st.nextToken());
			
		}	
		return s;
	}
	public Vector readWords(String sentence){
		Vector s=new Vector();
		int i=0;
		StringTokenizer st=new StringTokenizer(sentence);
		while(st.hasMoreTokens()){
			s.add(st.nextToken());
		}	
		return s;
	}
}
