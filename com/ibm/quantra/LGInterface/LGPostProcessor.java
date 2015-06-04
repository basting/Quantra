package com.ibm.quantra.LGInterface;

/**
* This is a Java Native Code Interface to the Link Grammar post processor type in the Link Grammar
* API.  THIS CLASS IS NOT FULLY IMPLEMENTED. We use Link Grammar 4.1 and it appears that functionality
* for this type has not yet been incorporated; comments in the code actually indicate that it is still in
* the prototype api.
* @author Chris Jordan, cjordan@cs.dal.ca
* @author Mike Atherton, atherton@cs.dal.ca
* @version 0.2
* @see <a href="http://www.link.cs.cmu.edu/link/dict/index.html">Link Grammar Documentation</a>
*/

public class LGPostProcessor {

   //load the library for testing purposes
//   static {
//      System.loadLibrary( "Link41a" );
//   }

   private int postProcessorPointer;
  

   /**
   * This method gets the pointer to the post processor in memory.
   */
   public int getPointer() {
      return postProcessorPointer;
   }

   /**
   * This methods initializes the post processor
   * @param name name
   */
   private native int postProcessOpen( String name );

   /**
   * This method releases the post processor
   */
   public native void postProcessClose  ();

   /**
   * This allows an arbitrary PostProcessor to be applied to an individual linkage
   */
   private native void linkagePostProcess(int lPtr);
   
}
