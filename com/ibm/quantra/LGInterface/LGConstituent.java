/*
 * Created on Apr 29, 2004 at 2:30:46 PM
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
public class LGConstituent {
//	load the library for testing purposes
//	   static {
//	      System.loadLibrary( "Link41a" );
//	   }

	   private int constituentPointer;
	   /**
	   * This is the constructor. It initializes the constituent
	   * @param linkage linkage to be analyzed
	   */
	   public LGConstituent ( LGLinkage linkage ){
	      constituentPointer = linkageConstituentTree(linkage.getPointer());
	   }

	   /**
	   * This method gets the pointer to the constituent in memory.
	   */
	   public int getPointer() {
	      return constituentPointer;
	   }

	   /**
	   * This methods initializes the constituent
	   * @param lPtr pointer to a linkage in memory
	   */
	   private native int linkageConstituentTree( int lPtr );

	   /**
	   * This method releases the constituent
	   */
	   public native void linkageFreeConstituentTree( );

	   /**
	   * This method prints the constituent tree
	   */
	   public String printConstituentTree( LGLinkage linkage, int mode ) {
	      return linkagePrintConstituentTree( linkage.getPointer(), mode );
	   }
	   /**
	   * This method prints the constituent tree
	   */
	   private native String linkagePrintConstituentTree(int lPtr, int mode);
	   
}
