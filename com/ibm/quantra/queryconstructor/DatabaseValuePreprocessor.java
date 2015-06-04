/*
 * Created on Mar 3, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.queryconstructor;

import java.util.StringTokenizer;
import java.util.Vector;

import com.ibm.quantra.semantic.tagger.DatabaseValue;
import com.ibm.quantra.semantic.tagger.SemanticTaggerConstants;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DatabaseValuePreprocessor {
	
	public Vector preprocess(Vector dataBaseValues){
		Vector newDbValue = new Vector();
		String dbValue;
		for(int i=0;i<dataBaseValues.size();i++){
			DatabaseValue databaseValue = (DatabaseValue)dataBaseValues.elementAt(i);
			if(databaseValue.getDbWord().compareTo(SemanticTaggerConstants.DATE)==0){
				dbValue = databaseValue.getDbValue();
				dbValue = preprocessDate(dbValue);
				databaseValue.putDbValue(dbValue);
			}
			else if(databaseValue.getDbWord().compareTo(SemanticTaggerConstants.TIME)==0){
				dbValue = databaseValue.getDbValue();
				dbValue = preprocessTime(dbValue);
				databaseValue.putDbValue(dbValue);
			}
			newDbValue.add(databaseValue);
		}
		return newDbValue;
	}
	public String preprocessDate(String dbValue){
		StringTokenizer strTokenizer = null;
		if(dbValue.indexOf(QueryConstructorConstants.DASH)!=-1)
			strTokenizer = new StringTokenizer(dbValue,QueryConstructorConstants.DASH);
		else if(dbValue.indexOf(QueryConstructorConstants.F_SLASH)!=-1)
			strTokenizer = new StringTokenizer(dbValue,QueryConstructorConstants.F_SLASH);
		StringBuffer strBuffer = new StringBuffer();
		Vector vec = new Vector();
		while(strTokenizer.hasMoreTokens()){
			vec.add(strTokenizer.nextToken());
		}
		String year = (String)vec.elementAt(vec.size()-1);
		if(year.length()==2){
			year = QueryConstructorConstants.TWENTY+year;
			vec.setElementAt(year,vec.size()-1);
		}
		for(int i=0;i<vec.size()-1;i++){
			String str = (String)vec.elementAt(i);
			if(str.length()==1){
				str=QueryConstructorConstants.SINGLE_ZERO+str;
				vec.setElementAt(str,i);
			}
		}
		for(int i=vec.size()-1;i>=0;i--){
			strBuffer.append((String)vec.elementAt(i));
			if(i!=0)
				strBuffer.append(QueryConstructorConstants.DASH);
		}
		return new String(strBuffer);
	}
	public String preprocessTime(String time){
		
		StringTokenizer strTokenizer = new StringTokenizer(time,QueryConstructorConstants.COLON);
		StringBuffer strBuffer = new StringBuffer();
		Vector vec = new Vector();
		while(strTokenizer.hasMoreTokens()){
			vec.add(strTokenizer.nextToken());
		}
		if(vec.size()<3){
			for(int i=0;i<=3-vec.size();i++)
				vec.add(QueryConstructorConstants.DOUBLE_ZERO);	
		}		
		String str = (String)vec.elementAt(0);
		if(str.length()==1){
			str = QueryConstructorConstants.SINGLE_ZERO+str;
		}
		vec.setElementAt(str,0);
		for(int i=0;i<vec.size();i++){
			strBuffer.append((String)vec.elementAt(i));
			if(i!=vec.size()-1)
				strBuffer.append(QueryConstructorConstants.COLON);
		}
		return new String(strBuffer);
	}
}
