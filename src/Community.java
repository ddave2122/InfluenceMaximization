import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import util.*;

public class Community {

	private int communityID;
	private int labelID;
	private ArrayList<Node> memberList = new ArrayList<Node>();
	private ArrayList<Node> edgeList = new ArrayList<Node>();
	private ArrayList<Node> pinList = new ArrayList<Node>();
	
	
	public Community() {
		super();
		communityID = 0;
		// TODO Auto-generated constructor stub
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


	/**
	 * @return the memberList
	 */
	public ArrayList<Node> getMemberList() {
		return memberList;
	}


	/**
	 * @param memberList the memberList to set
	 */
	public void setMemberList(ArrayList<Node> memberList) {
		this.memberList = memberList;
	}


	/**
	 * @return the edgeList
	 */
	public ArrayList<Node> getEdgeList() {
		return edgeList;
	}


	/**
	 * @param edgeList the edgeList to set
	 */
	public void setEdgeList(ArrayList<Node> edgeList) {
		this.edgeList = edgeList;
	}


	/**
	 * @return the pinList
	 */
	public ArrayList<Node> getPinList() {
		return pinList;
	}


	/**
	 * @param pinList the pinList to set
	 */
	public void setPinList(ArrayList<Node> pinList) {
		this.pinList = pinList;
	}

	
	
	
	
	
}
