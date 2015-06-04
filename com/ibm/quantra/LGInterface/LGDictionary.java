/*
 * Created on Apr 29, 2004 at 2:31:45 PM
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
public class LGDictionary {

	   //load the library for testing purposes
//	   static {
//	      System.loadLibrary( "Link41a" );
//	   }

	   private int dictionaryPointer;
	   /**
	   * This is the constructor. It loads the dictionary and sets the dictionaryPointer to point to it.
	   * @param dict_name the dictionary to be used
	   * @param post_process_file_name the post process file name to be used
	   * @param constituent_knowledge_name the constituent knowledge to be used
	   * @param affix_name the affix file to be used
	   */
	   public LGDictionary ( String dict_name, String post_process_file_name, String constituent_knowledge_name,
	      String affix_name ){
	      dictionaryPointer = dictionaryCreate( dict_name, post_process_file_name, constituent_knowledge_name, affix_name );
	   }

	   /**
	   * This method gets the pointer to the dictionary in memory.
	   */
	   public int getPointer() {
	      return dictionaryPointer;
	   }

	   /**
	   * This methods loads the dictionary.
	   * @param dict_name the dictionary to be used
	   * @param post_process_file_name the post process file name to be used
	   * @param constituent_knowledge_name the constituent knowledge to be used
	   * @param affix_name the affix file to be used
	   */
	   private native int dictionaryCreate( String dict_name, String post_process_file_name, String constituent_knowledge_name,
	      String affix_name );

	   /**
	   * This method releases the dictionary.
	   */
	   public native int dictionaryDelete  ();

	   /**
	   * This method returns the maximum cost (number of brackets []) that is placed on any connector in the dictionary.
	   */
	   public native int dictionaryGetMaxCost();
	  
}
