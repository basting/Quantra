/*
 * Created on Apr 29, 2004 at 2:33:24 PM
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.LGInterface;

/**
 * @author abc1
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LGSentence {
//	load the library for testing purposes
//	   static {
//	      System.loadLibrary( "Link41a" );
//	   }

	   private int sentencePointer;
	   /**
	   * This is the constructor. It initializes a sentence.
	   * @param sent sentence to be parsed
	   * @param dict dictionary to be used
	   */
	   public LGSentence ( String sent, LGDictionary dict ) {
	      sentencePointer = sentenceCreate( sent, dict.getPointer() );
	   }

	   /**
	   * This method gets the pointer to the sentence in memory.
	   */
	   public int getPointer() {
	      return sentencePointer;
	   }

	   /**
	   * This methods initializes the sentence.
	   * @param sent sentence to be parsed.
	   * @param dictPtr pointer of dictionary to be used.
	   */
	   private native int sentenceCreate( String sent, int dictPtr );

	   /**
	   * This method releases the sentence.
	   */
	   public native void sentenceDelete  ();

	  /**
	  * This method parses the sentence.
	  * @param opts options to be used.
	  */
	  public int parse ( LGParseOptions opts ) {
	     return sentenceParse( opts.getPointer() );
	  }

	  /**
	  * This method parses the sentence.
	  * @param optsPtr pointer of the options to be used.
	  */
	  private native int sentenceParse(int optsPtr);

	  /**
	  * This method returns the number of words in the sentence.
	  */
	  public native int sentenceLength();

	  /**
	  * This method returns the spelling of the w-th word in the sentence.
	  * @param w index of the w-th word
	  */
	  public native String sentenceGetWord(int w);

	  /**
	  * This method returns the number of null links that were used in parsing the sentence.
	  */
	  public native int sentenceNullCount();

	  /**
	  * This method returns the number of linkages that the search found.
	  */
	  public native int sentenceNumLinkagesFound();

	  /**
	  * This method returns the number of linkages that had no post-processing violations
	  */
	  public native int sentenceNumValidLinkages();

	  /**
	  * This method returns the number of linkages that were actually post-processed.
	  */
	  public native int sentenceNumLinkagesPostProcessed();

	  /**
	  * This method returns the number of post-processing violations that the i-th linkage had during the 
	  * last call to sentence_parse.
	  * @param i i-th linkage
	  */
	  public native int sentenceNumViolations( int i );

	  /**
	  * This method returns the maximum cost of connectors used in the i-th linkage of the sentence.
	  * @param i i-th linkage
	  */
	  public native int sentenceDisjunctCost( int i );

	  
}
