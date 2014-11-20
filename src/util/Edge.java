package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;


public class Edge {

	int EdgeID;
	int Node1;
	int Node2;
	
	
	public Edge(int node1, int node2) {
		super();
		Node1 = node1;
		Node2 = node2;
	}
	
	public boolean equals(Edge e){
		if(e.getNode1() == this.Node1&&e.getNode2() == this.Node2||e.getNode1() == this.Node2&&e.getNode2() == this.Node1)
			return true;
		else
			return false;
	}
	
	 @Override public boolean equals(Object other) {
	        boolean result = false;
	        if (other instanceof Edge) {
	            Edge that = (Edge) other;
	            result = (this.getNode1() == that.getNode1() && this.getNode2() == that.getNode2()||this.getNode1() == that.getNode2() && this.getNode2() == that.getNode1() );
	        }
	        return result;
	    }
	    
	/**
	 * @return the edgeID
	 */
	public int getEdgeID() {
		return EdgeID;
	}
	/**
	 * @param edgeID the edgeID to set
	 */
	public void setEdgeID(int edgeID) {
		EdgeID = edgeID;
	}
	/**
	 * @return the node1
	 */
	public int getNode1() {
		return Node1;
	}
	/**
	 * @param node1 the node1 to set
	 */
	public void setNode1(int node1) {
		Node1 = node1;
	}
	/**
	 * @return the node2
	 */
	public int getNode2() {
		return Node2;
	}
	/**
	 * @param node2 the node2 to set
	 */
	public void setNode2(int node2) {
		Node2 = node2;
	}
	
	
	
	
	
}
