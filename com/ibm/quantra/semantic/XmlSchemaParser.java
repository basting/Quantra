/*
 * Created on Oct 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.semantic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.ibm.graph.EdgeSharedException;
import com.ibm.graph.Edge;
import com.ibm.graph.Vertex;
import com.ibm.quantra.utilities.XSDKeyConstants;
import com.ibm.quantra.utilities.UserdictConstants;
import com.ibm.research.util.Dict;
import com.ibm.research.util.KeyMissingException;


/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XmlSchemaParser implements XmlSchemaParserInterface{
		private Document doc;
		private DocumentBuilder myDB;
		private Vector simpleTypes;
		
			
		public XmlSchemaParser(){
		
			DocumentBuilderFactory myDBFactory = DocumentBuilderFactory.newInstance();
			try{
					myDB  =  myDBFactory.newDocumentBuilder();
			}
			catch(Exception e){}
		}
		
		public SemanticNet giveGraph(InputStream fileStream,SemanticNet myGraph){
				try{
					doc = myDB.parse(fileStream);
				}
				catch(Exception e){
					e.printStackTrace();
				}	
				constructGraph(doc, null,null,myGraph);
				try {
					initializeSimpleTypeList();
					connectGraphVertices(myGraph);
				} catch (KeyMissingException e1) {
					e1.printStackTrace();
				} catch (EdgeSharedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return myGraph;
		}
		

		private void constructGraph(Node node,Vertex parent,String typeOfEdge,SemanticNet myGraph){
			if(node==null){
				return;
			}
			
			short nodeType = node.getNodeType();
			switch(nodeType){
			
				case Node.DOCUMENT_NODE: 
						
						Document document = (Document)node;
						constructGraph(document.getDocumentElement(),null,null,myGraph);
						break;
				
				case Node.ELEMENT_NODE:
						handleElementNode(node,parent,typeOfEdge,myGraph);
			}//switch(nodeType)...				
		}
		
		private void handleElementNode(Node node,Vertex parent,String typeOfEdge,SemanticNet myGraph){
			//the type is an element node with name ending with 'schema' for eg: xs:schema
			if(node.getNodeName().compareTo(XSDKeyConstants.SCHEMA)==0){
				handleXS_SchemaNode(node,parent,typeOfEdge,myGraph);
			}
			// the type is an element node. 
			else if(node.getNodeName().compareTo(XSDKeyConstants.ELEMENT)==0){
				try {
					handleXS_ElementNode(node,parent,typeOfEdge,myGraph);
				} catch (KeyMissingException e) {
					e.printStackTrace();
				}
			}	
		    else if (node.getNodeName().compareTo(XSDKeyConstants.COMPLEX_TYPE)==0 ||  node.getNodeName().compareTo(XSDKeyConstants.SIMPLE_TYPE)==0){
				try {
					handleXS_ComplexType_SimpleType_Nodes(node,parent,typeOfEdge,myGraph);
				} catch (KeyMissingException e) {
					e.printStackTrace();
				}
			}//else if(node.getNodeName().compareTo(KeyConstants.COMPLEX_TYPE.......
			else if(isOrderIndicator(node.getNodeName())){
				handleOrderIndicator(node,parent,typeOfEdge,myGraph);
			}//else if(isOrderIndicator.......	
			else if(node.getNodeName().compareTo(XSDKeyConstants.COMPLEX_CONTENT)==0||node.getNodeName().compareTo(XSDKeyConstants.SIMPLE_CONTENT)==0){
				handleXS_ComplexContent_SimpleContent_Nodes(node,parent,typeOfEdge,myGraph);
			}//else if(node.getNodeName().compareTo(KeyConstants.COMPLEX_CONTENT..........	
			else if(node.getNodeName().compareTo(XSDKeyConstants.EXTENSION)==0){
				handleXS_ExtensionNode(node,parent,typeOfEdge,myGraph);
			}//else if(node.getNodeName().compareTo(KeyConstants.EXTENSION...
			else if(isCommentLike(node.getNodeName())){
				return;
			}
			else{
				NamedNodeMap attrs = node.getAttributes();
				for(int i = 0; i < attrs.getLength(); i++){
					Attr attr = (Attr)attrs.item(i);
					parent.userdict.def(node.getNodeName()+" "+attr.getName(), attr.getValue());
				}
			}	 
		}
		private void handleXS_SchemaNode(Node node,Vertex parent,String typeOfEdge,SemanticNet myGraph){
			Vertex vertex = new Vertex();
			vertex.userdict.def(UserdictConstants.NAME,UserdictConstants.ROOT_NODE);
			vertex.userdict.def(UserdictConstants.KIND,XSDKeyConstants.SCHEMA);
			Node childNode = node.getFirstChild();
			while (childNode != null){
				constructGraph(childNode, vertex,null,myGraph);
				childNode = childNode.getNextSibling();
			}	
		}
		private void handleXS_ElementNode(Node node,Vertex parent,String typeOfEdge,SemanticNet myGraph) throws KeyMissingException{
			Vertex vertex = new Vertex();
			vertex.userdict.def(UserdictConstants.KIND, XSDKeyConstants.ELEMENT);
			if (parent!=null){
				Edge edge = new Edge(parent,vertex,true);
				edge.userdict.def(UserdictConstants.NAME, UserdictConstants.CHILD_OF);
				if(typeOfEdge!=null){
					if(!isOrderIndicator(typeOfEdge)){
						edge.userdict.def(UserdictConstants.TYPE,typeOfEdge);
						edge.userdict.def(UserdictConstants.ORDER_INDICATOR,UserdictConstants.NO);
					}
					else{
						edge.userdict.def(UserdictConstants.TYPE,XSDKeyConstants.COMPLEX_TYPE);
						edge.userdict.def(UserdictConstants.ORDER_INDICATOR,typeOfEdge);
					}
				}
				else{
					edge.userdict.def(UserdictConstants.TYPE,UserdictConstants.NO);
					edge.userdict.def(UserdictConstants.ORDER_INDICATOR,UserdictConstants.NO);
				}
				if(parent.userdict.getString(UserdictConstants.NAME).compareTo(UserdictConstants.ROOT_NODE)!=0)
					myGraph.add(edge);
			}
			NamedNodeMap attrs = node.getAttributes();
			for(int i = 0; i < attrs.getLength(); i++){
				Attr attr = (Attr)attrs.item(i);
				vertex.userdict.def(attr.getName(), attr.getValue());								
			}
			myGraph.add(vertex);
			Node childNode = node.getFirstChild();
			while (childNode != null){
				constructGraph(childNode, vertex,null,myGraph);
				childNode = childNode.getNextSibling();
			}		
		}
		private void handleXS_ComplexType_SimpleType_Nodes(Node node,Vertex parent,String typeOfEdge,SemanticNet myGraph) throws KeyMissingException{
			NamedNodeMap attrs = node.getAttributes();
			Attr nameAttr = (Attr)attrs.getNamedItem(UserdictConstants.NAME);
			if (nameAttr == null){
				 parent.userdict.def(UserdictConstants.TYPE_OF_ELEMENT,node.getNodeName());
				 Node childNode = node.getFirstChild();
				 while(childNode != null){
					constructGraph(childNode,parent,node.getNodeName(),myGraph);
					childNode = childNode.getNextSibling();						   	
				}					
			}				
			else{
				Vertex vertex = new Vertex();
				attrs = node.getAttributes();
				for(int i = 0; i < attrs.getLength(); i++){
					Attr attr = (Attr)attrs.item(i);
					vertex.userdict.def(attr.getName(), attr.getValue());
				}
				vertex.userdict.def(UserdictConstants.TYPE_OF_ELEMENT,node.getNodeName());
				Edge edge = new Edge(parent,vertex,true);
				edge.userdict.def(UserdictConstants.NAME, UserdictConstants.CHILD_OF);
				if(typeOfEdge!=null){
					if(!isOrderIndicator(typeOfEdge)){
						edge.userdict.def(UserdictConstants.TYPE,typeOfEdge);
						edge.userdict.def(UserdictConstants.ORDER_INDICATOR,UserdictConstants.NO);
					}
					else{
						edge.userdict.def(UserdictConstants.TYPE,XSDKeyConstants.COMPLEX_TYPE);
						edge.userdict.def(UserdictConstants.ORDER_INDICATOR,typeOfEdge);
					}
				}
				else{
				   edge.userdict.def(UserdictConstants.TYPE,UserdictConstants.NO);
				   edge.userdict.def(UserdictConstants.ORDER_INDICATOR,UserdictConstants.NO);
				}
				if(parent.userdict.getString(UserdictConstants.NAME).compareTo(UserdictConstants.ROOT_NODE)!=0)
									myGraph.add(edge);
				myGraph.add(vertex);
				Node childNode = node.getFirstChild();
				while(childNode != null){
					constructGraph(childNode,vertex,node.getNodeName(),myGraph);
					childNode = childNode.getNextSibling();						   	
				}							
			}	
		}
		private void handleOrderIndicator(Node node,Vertex parent,String typeOfEdge,SemanticNet myGraph){
			Node childNode = node.getFirstChild();
			while(childNode != null){
				constructGraph(childNode,parent,node.getNodeName(),myGraph);
				childNode = childNode.getNextSibling();
			}
		}
		private void handleXS_ComplexContent_SimpleContent_Nodes(Node node,Vertex parent,String typeOfEdge,SemanticNet myGraph){
			Node childNode = node.getFirstChild();
			while(childNode != null){
				constructGraph(childNode,parent,typeOfEdge,myGraph);
				childNode = childNode.getNextSibling();
			}
		}
		private void handleXS_ExtensionNode(Node node,Vertex parent,String typeOfEdge,SemanticNet myGraph){
			NamedNodeMap attrs = node.getAttributes();
			for(int i = 0; i < attrs.getLength(); i++){
				Attr attr = (Attr)attrs.item(i);
				parent.userdict.def(node.getNodeName()+" "+attr.getName(), attr.getValue());								
			}
			Node childNode = node.getFirstChild();
			while(childNode != null){
				constructGraph(childNode,parent,typeOfEdge,myGraph);
				childNode = childNode.getNextSibling();
			}
		}
		private boolean isCommentLike(String string){
			if(string.compareTo(XSDKeyConstants.APP_INFO)==0||string.compareTo(XSDKeyConstants.ANNOTATION)==0||string.compareTo(XSDKeyConstants.DOCUMENTATION)==0){
				return true;
			}
			return false;
		}		
		private boolean isOrderIndicator(String string){
			if(string.compareTo(XSDKeyConstants.SEQUENCE)==0||string.compareTo(XSDKeyConstants.CHOICE)==0||string.compareTo(XSDKeyConstants.ALL)==0){
				return true;
			}
			return false;
		}
		private void initializeSimpleTypeList(){
			FileReader fr = null;
			String word = null;
			simpleTypes = new Vector();
			try {
				fr = new FileReader(SemanticProcessorConstants.SIMPLE_TYPES_FILENAME);
			} catch (FileNotFoundException e) {e.printStackTrace();}
			
			BufferedReader bis = new BufferedReader(fr);
			
			try {
				word = bis.readLine();
				while(word!=null){
					simpleTypes.add(word);
					word = bis.readLine();
				}
			} catch (IOException e1) {e1.printStackTrace();}
		}
		private void connectGraphVertices(SemanticNet myGraph) throws KeyMissingException, EdgeSharedException{
			Enumeration typeVertices = myGraph.enumerateVerticesByUserKey(UserdictConstants.TYPE);
			Vector vertexVector = new Vector();
			while(typeVertices.hasMoreElements()){
				Vertex v = (Vertex)typeVertices.nextElement();
				String string = v.userdict.getString(UserdictConstants.TYPE);
				int index = string.indexOf(':');
				String subString = string.substring(index+1);
				if(!isPrimitiveType(subString)){
					Enumeration reqVertices = myGraph.enumerateVerticesByUserKeySetToValue(UserdictConstants.NAME,string);
					while(reqVertices.hasMoreElements()){
						Vertex v2 = (Vertex)reqVertices.nextElement();
						if(!vertexVector.contains(v2))
							vertexVector.add(v2);
						Edge edge = new Edge(v,v2,true);
						defineDictEdge(edge);
						myGraph.add(edge);									
					}					
				}
			}
			removeVertexVector(vertexVector,myGraph);
		}
		private void removeVertexVector(Vector vertVector,SemanticNet myGraph){
			for(int i=0;i<vertVector.size();i++){
				Vertex vertex = (Vertex)vertVector.elementAt(i);
				if(vertex.degreeIncoming()==1){
					Enumeration enum = vertex.enumerateIncomingEdges();
					Edge edge = (Edge)enum.nextElement();
					Vertex vParent = edge.getFromVertex();
					myGraph.remove(edge);
					transferChildrenTo(vertex,vParent);	
					myGraph.remove(vertex);				
				}
				else{
					transferChildrenTo(vertex,myGraph);
				}				
			}			
		}		
		private boolean isPrimitiveType(String type){
			
			if(simpleTypes.contains(type))
				return true;
			return false;
		}
		private void transferChildrenTo(Vertex vertex,Vertex vParent){
			Enumeration outEdges = vertex.enumerateOutgoingEdges();
			while(outEdges.hasMoreElements()){
				Edge edge = (Edge)outEdges.nextElement();
				try {
					edge.setFromVertex(vParent);
					} catch (EdgeSharedException e) {e.printStackTrace();}								
			}
		}
		private void transferChildrenTo(Vertex vertex,SemanticNet myGraph){
			Enumeration enumEdges = vertex.enumerateIncomingEdges();
			Enumeration toEdgeEnum = null;
			while(enumEdges.hasMoreElements()){
				Edge edge = (Edge)enumEdges.nextElement();
				Vertex ver = edge.getFromVertex();
				toEdgeEnum = vertex.enumerateOutgoingEdges();
				while(toEdgeEnum.hasMoreElements()){
					Edge ed = (Edge)toEdgeEnum.nextElement();
					Vertex newVer = ed.getToVertex();
					Edge newEdge = new Edge(ver,newVer,true);
					newEdge.userdict = (Dict)ed.userdict.clone();
					myGraph.add(newEdge);					
				}				
			}
			enumEdges = vertex.enumerateIncomingEdges();
			toEdgeEnum = vertex.enumerateOutgoingEdges();
			while(enumEdges.hasMoreElements()){
				myGraph.remove((Edge)enumEdges.nextElement());
			}
			while(toEdgeEnum.hasMoreElements()){
				myGraph.remove((Edge)toEdgeEnum.nextElement());
			}
			myGraph.remove(vertex);
		}
		private void defineDictEdge(Edge edge){
			edge.userdict.def(UserdictConstants.NAME,UserdictConstants.CHILD_OF);
			edge.userdict.def(UserdictConstants.TYPE,UserdictConstants.BLANK);
			edge.userdict.def(UserdictConstants.NAME,UserdictConstants.BLANK);
		}
}

