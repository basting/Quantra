/*
 * Created on Feb 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.semantic.tagger;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TagValueContent {
		//type specifies whether it is an attribute or an entity.
		//content specifies the name of the tagValue.
		private String type;
		private String content;
		
		public void setType(String type){
			this.type = type;
		}
		public void setContent(String content){
			this.content = content;
		}
		public String getType(){
			return type;
		}
		public String getContent(){
			return content;
		}		
}
