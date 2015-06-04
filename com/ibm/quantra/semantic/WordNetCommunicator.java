/*
 * Created on Jan 5, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.quantra.semantic;


import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.IndexWordSet;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerUtils;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.list.PointerTargetNode;
import net.didion.jwnl.data.list.PointerTargetNodeList;
import net.didion.jwnl.data.list.PointerTargetTree;
import net.didion.jwnl.dictionary.Dictionary;
/**
 * @author bastin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WordNetCommunicator implements DictionaryInterface {

		public void initialize(String propsFile) {
			try {
				// initialize JWNL (this must be done before JWNL can be used)
				JWNL.initialize(new FileInputStream(propsFile));
			} catch (Exception ex) {
				ex.printStackTrace();				
			}
		}
		//checks if a particular word is present in wordnet
		public boolean checkIfPresentInDict(String word){
			IndexWordSet wordSet = null;
			try {
				wordSet = Dictionary.getInstance().lookupAllIndexWords(word);
			} catch (JWNLException e) {	e.printStackTrace();}
			if(wordSet.size()>0){
				return true;
			}
			return false;
		}
		public Vector getTertiaryWords(String string) {
			Vector vector1 = null;
			Vector vector2 = null;
			try{
				IndexWord indexWord = Dictionary.getInstance().getIndexWord(POS.NOUN, string);
				if(indexWord != null) {
					vector1 = getRelatedWord(indexWord, WordNetConstants.HYPONYM);
				}
			
				indexWord = Dictionary.getInstance().getIndexWord(POS.VERB, string);
				if(indexWord != null) {
					vector2 = getRelatedWord(indexWord, WordNetConstants.HYPERNYM);
				}
			}catch(JWNLException e){e.printStackTrace();}				
			Vector newVector = new Vector();
			if(vector1!=null)
				newVector.addAll(vector1);
			if(vector2!=null)
				newVector.addAll(vector2);
			return newVector;
		}

		private Vector getRelatedWord(IndexWord word, int type ) throws JWNLException {
			// Get all the hyponyms (children) of the first sense of <var>word</var>
			int senseCount = word.getSenseCount();
			Vector nymVector = new Vector();
			PointerTargetTree nyms = null;
		/*	if(type == WordNetConstants.HYPONYM)
				System.out.println("Hyponyms of \"" + word.getLemma() + "\":");
			else
				System.out.println("Hypernyms of \"" + word.getLemma() + "\":");
		*/	for (int l = 1;l <= senseCount; l++) {
				if(type == WordNetConstants.HYPONYM) {
					nyms = PointerUtils.getInstance().getHyponymTree(word.getSense(l));
				} else {
					nyms = PointerUtils.getInstance().getHypernymTree(word.getSense(l));
				}
			
				List list = nyms.toList();
				if (!list.isEmpty()){
					Object pointerTargetNodeList[] = list.toArray();
				
					for(int j=0;j<pointerTargetNodeList.length;j++){
						Object items[] = ((PointerTargetNodeList)pointerTargetNodeList[j]).toArray();
						for(int i=0;i<items.length;i++){
							PointerTargetNode pointerTargetNode = (PointerTargetNode)items[i];
							Synset synset = pointerTargetNode.getSynset();
							for(int k=0;k<synset.getWordsSize();k++){
								Word presentWord = synset.getWord(k);
								if(presentWord != null)
									nymVector.add(presentWord.getLemma());
							}
						}
					}
				}
			}
			HashSet set = new HashSet();
			for(int i=0;i<nymVector.size();i++){
				set.add(nymVector.elementAt(i));
			}
			Vector shortVector = new Vector(Arrays.asList(set.toArray()));
		//	if only u wanted to see the tertiary words, u display the list below.
		// when displaying the list, u can activate the above commented area also.
			//display(nymVector);
			return shortVector;
		}
		private void display(Vector vector){
			HashSet set = new HashSet();
		
			for(int i=0;i<vector.size();i++){
				set.add(vector.elementAt(i));
			}
			Object array[] = set.toArray();	
			for(int j=0;j<array.length;j++){
				System.out.print(array[j]+"-----");
				if((j+1)%5 == 0) System.out.println();	
			}
			System.out.println();
			System.out.println();
		}	
}

