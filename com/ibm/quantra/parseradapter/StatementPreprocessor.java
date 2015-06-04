/*
 * Created on Jun 28, 2004
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.parseradapter;


import java.util.*;
import java.io.*;
/**
 * @author Administrator
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class StatementPreprocessor {
	private HashMap hm1;
	private HashMap hm2;
	private Vector wordHeader;
	
	public StatementPreprocessor() throws IOException{
		initProcessor();
		initCompoundWordList();
	}
	private void initProcessor() throws IOException{
		hm1=new HashMap();
		BufferedReader br=new BufferedReader(new FileReader(ParserAdapterConstants.PREPROCESS_DATA_FILE1));
		String s=br.readLine();
		while(s!=null){
			StringTokenizer st=new StringTokenizer(s,ParserAdapterConstants.EQUALS);
			String s1=st.nextToken();
			String s2=st.nextToken();
			hm1.put(s1,s2);
			s=br.readLine();			
		}		
	}
	private void initCompoundWordList() throws IOException{
		hm2=new HashMap();
		wordHeader = new Vector();
		BufferedReader br=new BufferedReader(new FileReader(ParserAdapterConstants.COMPOUND_WORDLIST_FILENAME));
		String s=br.readLine();
		while(s!=null){
			StringTokenizer st=new StringTokenizer(s,ParserAdapterConstants.EQUALS);
			String s1=st.nextToken();
			String s2=st.nextToken();
			hm2.put(s1,s2);
			wordHeader.add(s1);
			s=br.readLine();			
		}		
	}
	public String preProcess(String statement){
		statement = performWordSplitting(statement);
		statement = performWordMerging(statement);
		return statement;
	}
	private String performWordSplitting(String statement){
		StringBuffer sb=new StringBuffer();
		StringTokenizer st=new StringTokenizer(statement);
		String s;
		String s1;
		while(st.hasMoreTokens()){
			s=st.nextToken();
			if(hm1.containsKey(s)){
				s1=(String)hm1.get(s);	
			}
			else s1=s;
				sb.append(s1);
			if(st.hasMoreTokens())
				sb.append(ParserAdapterConstants.SINGLE_SPACE);			
			}
		return(new String(sb));
	}
	private String performWordMerging(String statement){
		StringBuffer sb = new StringBuffer(statement);
		int index;
		for(int i=0;i<wordHeader.size();i++){
			String word = (String)wordHeader.elementAt(i);
			index = sb.indexOf(word);
			if(index!=-1){
				int length = word.length();
				sb.replace(index,index+length,(String)hm2.get(word));		
			}
		}
		return new String(sb);
	}
}