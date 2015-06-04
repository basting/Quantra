/*
 * Created on Dec 28, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.semantic;

import java.util.EventObject;

/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SyncEventObject extends EventObject {
	private SyncCommand syncCmd;
	
	public SyncEventObject(Object source) {
		super(source);
	}
	public void setSyncCommand(SyncCommand param){
		this.syncCmd = param;
	}
	public SyncCommand getSyncCommand(){
		return syncCmd;
	}
	
}
