package com.ibm.quantra.parseradapter;

import com.ibm.quantra.LGInterface.*;
import com.ibm.quantra.utilities.QConstants;
import com.ibm.quantra.utilities.Trace;



public class LGParserAdapter extends ParserAdapter{
	private LGDictionary lgd;
	private LGParseOptions lgp;
	private LGLinkage lgl;
	private static String words[];
// 'initparser' initializes the LGDictionary and the LGParseOptions
	public void initParser(){
		
			LGConfigData lgc = LGConfigData.getConfigData();
			System.loadLibrary(ParserAdapterConstants.LINK_GRAMMER_DLLFileName);
			Trace.Print(QConstants.INFORMATION, "Loaded Library...");
			lgd = new LGDictionary(lgc.getDictionaryFileName(), lgc.getpostProcessFileName(),lgc.getConstituentKnowledgeName() ,lgc.getAffixName());
			Trace.Print(QConstants.INFORMATION, "Loaded Dictionary...");
			lgp = new LGParseOptions();
	}
	public String parseSentence(String sentence){
		  LGSentence lgs = new LGSentence ( sentence, lgd );
	      lgs.parse( lgp );
	      lgl = new LGLinkage( 0, lgs, lgp );

	      System.out.println( lgl.linkagePrintDiagram() );
	      String linkagePostscript=lgl.linkagePrintPostscript(0);
	      words = lgl.linkageGetWords();
	      StringOperation sop=new StringOperation();
	      String postscriptNotation=sop.getPostscript(linkagePostscript); 

	      lgs.sentenceDelete();
	      
	      return postscriptNotation;
	}	
	public static String[] getWords(){
		return words;
	}
	public void releaseParser(){
	      lgd.dictionaryDelete();
	      lgp.parseOptionsDelete();
	      lgl.linkageDelete();
	      Trace.Print(QConstants.INFORMATION, "Released Parser...");
	}
}