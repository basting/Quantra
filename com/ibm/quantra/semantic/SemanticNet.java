/*
 * Created on Aug 27, 2004 at 8:07:29 PM
 *
 * @todo To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 * 
 */
package com.ibm.quantra.semantic;

import java.util.Vector;

import com.ibm.graph.Net;
import com.ibm.graph.Vertex;


/**
 * @author abc1
 *
 * @todo To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SemanticNet extends Net{
	private Vector listeners;
	
	public SemanticNet(){
		listeners = new Vector();
	}
	
	public boolean add(Vertex vertex){
		boolean retValue = super.add(vertex);
		SyncCommand cmd = new SyncCommand();
		cmd.command = SemanticProcessorConstants.ADD;
		cmd.value = vertex;
		SyncEventObject event = new SyncEventObject(this);
		event.setSyncCommand(cmd);
		fireSyncEvent(event);
		return retValue;		
	}
	public boolean remove(Vertex vertex){
		boolean retValue = super.remove(vertex);
		SyncCommand cmd = new SyncCommand();
		cmd.command = SemanticProcessorConstants.REMOVE;
		cmd.value = vertex;
		SyncEventObject event = new SyncEventObject(this);
		event.setSyncCommand(cmd);
		fireSyncEvent(event);
		return retValue;		
	}
	public void addSyncListener(SyncListener syncListener){
		listeners.add(syncListener);
	}
	public void removeSyncListener(SyncListener syncListener){
		listeners.remove(syncListener);
	}
	public void fireSyncEvent(SyncEventObject event){
		for(int i=0;i<listeners.size();i++){
			SyncListener listener = (SyncListener) listeners.elementAt(i);
			listener.update(event);
		}		
	}
	
	
}