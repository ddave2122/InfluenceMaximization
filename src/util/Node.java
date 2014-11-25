package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;


public class Node {

	private int nodeID;
	private boolean isActive; // true means active, false means inactive
	private boolean isTempActive; // true means temporarily active
	private boolean isSeed; // true means it is seclected as a seed
	private int degree;
	private int activeNeighbors;
	private int communityID;
	private int labelID;
	
	private int gainedProbability;
	
	private ArrayList<Neighbor> neighborList = new ArrayList<Neighbor>();
	
	
	public Node() {
		super();
		nodeID = 0;
		isActive = false;
		isSeed = false;
		isTempActive = false;
		degree = 0;
		activeNeighbors = 0;
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the nodeID
	 */
	public int getNodeID() {
		return nodeID;
	}


	/**
	 * @param nodeID the nodeID to set
	 */
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}


	/**
	 * @return the neighborList
	 */
	public ArrayList<Neighbor> getNeighborList() {
		return neighborList;
	}


	/**
	 * @param neighborList the neighborList to set
	 */
	public void setNeighborList(ArrayList<Neighbor> neighborList) {
		this.neighborList = neighborList;
	}


	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}


	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	/**
	 * @return the isSeed
	 */
	public boolean isSeed() {
		return isSeed;
	}


	/**
	 * @param isSeed the isSeed to set
	 */
	public void setSeed(boolean isSeed) {
		this.isSeed = isSeed;
	}


	/**
	 * @return the isTempActive
	 */
	public boolean isTempActive() {
		return isTempActive;
	}


	/**
	 * @param isTempActive the isTempActive to set
	 */
	public void setTempActive(boolean isTempActive) {
		this.isTempActive = isTempActive;
	}


	/**
	 * @return the degree
	 */
	public int getDegree() {
		return degree;
	}


	/**
	 * @param degree the degree to set
	 */
	public void setDegree(int degree) {
		this.degree = degree;
	}


	/**
	 * @return the activeNeighbors
	 */
	public int getActiveNeighbors() {
		return activeNeighbors;
	}


	/**
	 * @param activeNeighbors the activeNeighbors to set
	 */
	public void setActiveNeighbors(int activeNeighbors) {
		this.activeNeighbors = activeNeighbors;
	}


	/**
	 * @return the communityID
	 */
	public int getCommunityID() {
		return communityID;
	}


	/**
	 * @param communityID the communityID to set
	 */
	public void setCommunityID(int communityID) {
		this.communityID = communityID;
	}


	/**
	 * @return the gainedProbability
	 */
	public int getGainedProbability() {
		return gainedProbability;
	}


	/**
	 * @param gainedProbability the gainedProbability to set
	 */
	public void setGainedProbability(int gainedProbability) {
		this.gainedProbability = gainedProbability;
	}


	/**
	 * @return the labelID
	 */
	public int getLabelID() {
		return labelID;
	}


	/**
	 * @param labelID the labelID to set
	 */
	public void setLabelID(int labelID) {
		this.labelID = labelID;
	}

	
}
