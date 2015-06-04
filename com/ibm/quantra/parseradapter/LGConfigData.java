package com.ibm.quantra.parseradapter;

import java.io.*;
import java.util.*;

import com.ibm.quantra.utilities.QConstants;
import com.ibm.quantra.utilities.Trace;


public class LGConfigData {
	private String dictionaryFileName = null;
	private String postProcessFileName = null;
	private String constituentKnowledgeName = null;
    private String affixName = null;
    private static LGConfigData configData; 
	private LGConfigData(){}
	private LGConfigData(String configFile) throws IOException{
		// Read the file configFile and initialize the private variables
		FileInputStream in = new FileInputStream(configFile);
		Properties property=new Properties();
		property.load(in);
		dictionaryFileName=property.getProperty(ParserAdapterConstants.DICTIONARY_FILENAME);		
		postProcessFileName=property.getProperty(ParserAdapterConstants.POSTPROCESS_FILENAME);
		constituentKnowledgeName=property.getProperty(ParserAdapterConstants.CONSTITUENTKNOWLEDGE_NAME);
		affixName = property.getProperty(ParserAdapterConstants.AFFIX_NAME);
	}
	
	public String getDictionaryFileName(){
		return dictionaryFileName;
	}
	
	public String getpostProcessFileName(){
		return postProcessFileName;
	}
	
	public String getConstituentKnowledgeName(){
		return constituentKnowledgeName;
	}
	
	public String getAffixName(){
		return affixName;
	}
	
	public static LGConfigData getConfigData(){
		if(configData == null){
			try {
				configData = new LGConfigData(ParserAdapterConstants.FILECONFIG_PROPERTIES);
			} catch (IOException e) {
				Trace.Print(QConstants.FATAL, "Error in fileconfig.properties");
				e.printStackTrace();
			}
		}
		return configData;
	}
}