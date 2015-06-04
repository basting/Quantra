/*
 * Created on Mar 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.queryconstructor;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.Node;

import org.dom4j.io.SAXReader;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.XPathSyntaxException;
import org.jaxen.dom4j.Dom4jXPath;
/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryExecutor {
	private String fileName;
	public void loadXMLFile(String fileName){
		this.fileName = fileName;
	}
	public Vector executeQuery(String Query){
		Vector queryResults = null;
		try{
			SAXReader reader = new SAXReader();
            
		    Document doc = reader.read( fileName );
    
	   	    XPath xpath = new Dom4jXPath( Query );
    
		    List results = xpath.selectNodes( doc );

		    Iterator resultIter = results.iterator();
    
		    queryResults = new Vector();
		    while ( resultIter.hasNext() )
		    {
			    Object object = resultIter.next();
			    if ( object instanceof Node ) 
			    {
			  	   Node node = (Node) object;
			 	   queryResults.add(node.asXML());
			    }
			    else 
			    {
				    queryResults.add(object);
			    }
		   }
	   }
	   catch (XPathSyntaxException e)
	   {
		   System.err.println( e.getMultilineMessage() );
	   }
	   catch (JaxenException e)
	   {
		   e.printStackTrace();
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
	   }
	   return queryResults;
	}
		   	
}
