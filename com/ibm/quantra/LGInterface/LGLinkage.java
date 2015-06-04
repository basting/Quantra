/*
 * Created on Apr 29, 2004 at 2:28:46 PM
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.ibm.quantra.LGInterface;

import com.ibm.quantra.parseradapter.ParserAdapterConstants;

/**
 * @author abc1
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LGLinkage {
	 //load the library for testing purposes
//	    static {
//	       System.loadLibrary( "Link41a" );
//	    }

	   private int linkagePointer;
	   /**
	   * This is the constructor. It initializes the linkage and has linkagePointer point to it.
	   * @param index the index of the linkage that is to be accessed
	   * @param sent the sentence to be used
	   * @param opts the parse options to be used
	   */
	   public LGLinkage ( int index, LGSentence sent, LGParseOptions opts ){
	      	System.loadLibrary(ParserAdapterConstants.LINK_GRAMMER_DLLFileName);
		linkagePointer = linkageCreate( index, sent.getPointer(), opts.getPointer() );
	   }

	   /**
	   * This method gets the pointer to the linkage in memory.
	   */
	   public int getPointer() {
	      return linkagePointer;
	   }

	   /**
	   * This methods initializes the linkage
	   * @param index the index of the linkage that is to be accessed
	   * @param sPtr pointer to the sentence to be used
	   * @param oPtr pointer to the parse options to be used
	   */
	   private native int linkageCreate( int index, int sPtr, int oPtr );

	   /**
	   * This method releases the linkage
	   */
	   public native void linkageDelete();

	   /**
	   * This method returns the number of sublinkages for a linkage with conjunctions.
	   */
	   public native int linkageGetNumSublinkages();

	   /**
	   * After this call, all operations on the linkage will refer to the index-th sublinkage.
	   * @param index index of the sublinkage
	   */
	   public native int linkageSetCurrentSublinkage( int index);

	   /**
	   * If the linkage has a conjunction, what this does is to combine all of the links occurring in all sublinkages together
	   */
	   public native int linkageComputeUnion();

	   /**
	   * The number of words in the sentence for which this is a linkage.
	   */
	   public native int linkageGetNumWords();

	   /**
	   * The number of links used in the current sublinkage.
	   */
	   public native int  linkageGetNumLinks();

	   /**
	   * The number of words spanned by the index-th link of the current sublinkage.
	   * @param index index of the sublinkage
	   */
	   public native int linkageGetLinkLength( int index);

	   /**
	   * The number of the word on the left end of the index-th link of the current sublinkage.
	   * @param index index of the sublinkage
	   */
	   public native int linkageGetLinkLword(int index);

	   /**
	   * The number of the word on the right end of the index-th link of the current sublinkage.
	   */
	   public native int linkageGetLinkRword( int index);

	   /**
	   * Returns a pointer to a string containing the familiar graphical linkage display.
	   */
	   public native String linkagePrintDiagram();

	   /**
	   * Returns the macros needed to print out the linkage in a postscript file.
	   * @param mode postscript mode
	   */
	   public native String linkagePrintPostscript(int mode);

	   /**
	   * Returns a string that lists all of the links and domain names for the current sublinkage.
	   */
	   public native String linkagePrintLinksAndDomains();

	   /**
	   * Used in returning the label for a link.
	   * @param index index
	   */
	   public native String linkageGetLinkLabel(int index);

	   /**
	   * Used in returning the label for a link.
	   * @param index index
	   */
	   public native String linkageGetLinkLlabel(int index);

	   /**
	   * Used in returning the label for a link.
	   * @param index index
	   */
	   public native String linkageGetLinkRlabel(int index);

	   /**
	   * Returns the number of domains in sublinkage
	   * @param index index of sublinkage
	   */
	   public native int linkageGetLinkNumDomains(int index);

	   /**
	   * Returns the domains names in sublinkage
	   * @param index index of sublinkage
	   */
	   public native String[] linkageGetLinkDomainNames(int index);

	   /**
	   * If the linkage violated any post-processing rules, the name of the violated rule in the post-process knowledge file
	   * is returned.
	   */
	   public native String  linkageGetViolationName();

	   /**
	   * Return the array of word spellings for the current sublinkage
	   */
	   public native String [] linkageGetWords();

	   /**
	   * Return the word spelling for an individual word spelling for the current sublinkage
	   * @param w w-th word
	   */
	   public native String linkageGetWord( int w);

	   /**
	   *
	   */
	   public native int linkageUnusedWordCost();

	   /**
	   *
	   */
	   public native int linkageDisjunctCost();

	   /**
	   *
	   */
	   public native int linkageAndCost();

	   /**
	   *
	   */
	   public native int linkageLinkCost();
	  
}
