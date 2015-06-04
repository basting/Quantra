/*
 * Created on Apr 29, 2004 at 2:32:46 PM
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
public class LGParseOptions {
	 //load the library for testing purposes
//	   static {
//	      System.loadLibrary( "Link41a" );
// }

	   private int optionsPointer;
	   /**
	   * This is the constructor. It initializes the parse option and sets the optionPointer to point to it.
	   */
	   public LGParseOptions ( ){
	      optionsPointer = parseOptionsCreate();
	   }

	   /**
	   * This method gets the pointer to the parse option in memory.
	   */
	   public int getPointer() {
	      return optionsPointer;
	   }

	   /**
	   * This methods initializes the parse option.
	   */
	   private native int parseOptionsCreate();

	   /**
	   * This method releases the parse option.
	   */
	   public native int parseOptionsDelete  ();

	   /**
	   * This sets the level of description printed to stderr/stdout about the parsing process.
	   * @param verbosity the level of description to be printed.
	   */
	   public native void parseOptionsSetVerbosity( int verbosity);

	   /**
	   * This gets the level of description printed to stderr/stdout about the parsing process
	   */
	   public native int  parseOptionsGetVerbosity();

	   /**
	   * This method sets the maximum number of linkages that can be found in post processing.
	   * @param linkage_limit the maximum number of linkages that can be found.
	   */
	   public native void parseOptionsSetLinkageLimit(int linkage_limit);

	   /**
	   * This method gets the maximum number of linkages that can be found in post processing.
	   */
	   public native int parseOptionsGetLinkageLimit();

	   /**
	   * Sets the maximum disjunct cost used during parsing.
	   * @param disjunct_cost the maximum disjunct cost.
	   */
	   public native void parseOptionsSetDisjunctCost( int disjunct_cost);

	   /**
	   * Gets the maximum disjunct cost used during parsing.
	   */
	   public native int parseOptionsGetDisjunctCost();

	   /**
	   * Set the minimum number of null links that a parse might have.
	   * @param null_count the minimum number of null links.
	   */
	   public native void parseOptionsSetMinNullCount(int null_count);

	   /**
	   * Get the minimum number of null links that a parse might have.
	   */
	   public native int parseOptionsGetMinNullCount();

	   /**
	   * Set the maximum number of null links that a parse might have.
	   * @param null_count the maximum number of null links.
	   */
	   public native void parseOptionsSetMaxNullCount(int null_count);

	   /**
	   * Get the maximum number of null links that a parse might have.
	   */
	   public native int parseOptionsGetMaxNullCount();

	   /**
	   * Set the size of the null block.
	   * @param null_block the size of the null block.
	   */
	   public native void parseOptionsSetNullBlock(int null_block);

	   /**
	   * Get the size of the null block.
	   */
	   public native int parseOptionsGetNullBlock();

	   /**
	   * Set how long linkages are allowed to be.
	   * @param short_length the maximum length of linkage.
	   */
	   public native void parseOptionsSetShortLength(int short_length);

	   /**
	   * Get how long linkages are allowed to be.
	   */
	   public native int  parseOptionsGetShortLength();

	   /**
	   * Set whether islands are allowed or not.
	   * @param islands_ok allow islands or not.
	   */
	   public native void parseOptionsSetIslandsOk(int islands_ok);

	   /**
	   * Get whether islands are allowed or not.
	   */
	   public native int parseOptionsGetIslandsOk();

	   /**
	   * Set maximum amount of time allowed for parsing.
	   * @param secs amount of time in seconds.
	   */
	   public native void parseOptionsSetMaxParseTime(int secs);

	   /**
	   * Get maximum amount of time allowed for parsing.
	   */
	   public native int parseOptionsGetMaxParseTime();

	   /**
	   * Set the amount of memory allowed for parsing.
	   * @param mem amount of memory allowed.
	   */
	   public native void parseOptionsSetMaxMemory(int mem);

	   /**
	   * Get the amount of memory allowed for parsing.
	   */
	   public native int parseOptionsGetMaxMemory();

	   /**
	   * Determines if the timer expired during parsing.
	   */
	   public native int  parseOptionsTimerExpired();

	   /**
	   * Determines if the memory was exhausted during parsing.
	   */
	   public native int  parseOptionsMemoryExhausted();
	   
	   /**
	   * Determines if the resources were exhausted during parsing.
	   */
	   public native int  parseOptionsResourcesExhausted();

	   /**
	   * Resets the constraints set on the parser. This has to be done everytime before parsing.
	   */
	   public native void parseOptionsResetResources();

	   /**
	   * Set cost model type.
	   * @param cm the cost model type.
	   */
	   public native void parseOptionsSetCostModelType(int cm);

	   /**
	   * Get cost model type.
	   */
	   public native int parseOptionsGetCostModelType();

	   /**
	   * Set screen width.
	   * @param val width in characters.
	   */
	   public native void parseOptionsSetScreenWidth(int val);

	   /**
	   * Get screen width.
	   */
	   public native int parseOptionsGetScreenWidth();

	   /**
	   * Set allow null linkages or not.
	   * @param val allow null linkages or not.
	   */
	   public native void parseOptionsSetAllowNull(int val);

	   /**
	   * Get allow null linkages or not.
	   */
	   public native int parseOptionsGetAllowNull();

	   /**
	   * Set display walls or not.
	   * @param val display walls or not
	   */
	   public native void parseOptionsSetDisplayWalls( int val);

	   /**
	   * Get display walls or not.
	   */
	   public native int parseOptionsGetDisplayWalls( );

	   /**
	   * Set whether to have length restriction imposed on connectors or not.
	   * @param val impose restriction or not.
	   */
	   public native void parseOptionsSetAllShortConnectors(int val);

	   /**
	   * Get whether length restriction is imposed on connectors or not.
	   */
	   public native int parseOptionsGetAllShortConnectors();
	  
}
