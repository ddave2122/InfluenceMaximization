import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import util.*;

public class RabbitHole {

	 ArrayList<Node> nodeList;
	 int k;
	 int[] seeds;
	 ArrayList<Community> communityList = new ArrayList<Community>();
	 int threshold = 200; // limit the number of communities
	 int numOfCommunities = 0;

	 //initialize
	 public RabbitHole(int sizeOfSeedSet) {
		super();
		//read hep
//		ReadHep readHepData = new ReadHep();
//		readHepData.run();
//		this.nodeList = readHepData.getNodeList();
		
		//read phy
//		ReadPhy readPhyData = new ReadPhy();
//		readPhyData.run();
//		this.nodeList = readPhyData.getNodeList();
		
		//read dblp
//		ReadDblp readDblpData = new ReadDblp();
//		readDblpData.run();
//		this.nodeList = readDblpData.getNodeList();
		
		//read Epinions
//		ReadEpinions readEpinionsData = new ReadEpinions();
//		readEpinionsData.run();
//		this.nodeList = readEpinionsData.getNodeList();
		
		//read Amazon
		ReadAmazon readAmazonData = new ReadAmazon();
		readAmazonData.run();
		this.nodeList = readAmazonData.getNodeList();
		
		k = sizeOfSeedSet;
		seeds = new int[k];
		for(int i=0; i<k; i++){
			seeds[i] = 0;
		}
	}
	 
	 public void run(){
		 RabbitHoleSelectSeed();
		 InfluenceSpread();
	 }
	 
	 public void RabbitHoleSelectSeed(){
		 //Community detection
		 //use fast unfolding algorithm, then change it according to our needs
		 CommunityDetection();
		 
		 
		 //Edge analysis
		 EdgeAnalysis();
		 
		 //Cross points
		 CrossPointsAnalysis();
		 
		 
		 
		 //select seeds
		 int numOfSeeds = 0;
		 for(Community c: communityList){
			 for(Node n: c.getPinList()){
				 seeds[numOfSeeds] = n.getNodeID();
				 nodeList.get(n.getNodeID()).setSeed(true);
				 nodeList.get(n.getNodeID()).setActive(true);
				 numOfSeeds++;
				 if(numOfSeeds==k)
					 break;
			 }
			 if(numOfSeeds==k)
				 break;
		 }
	 }
	 
	 
	 public void CommunityDetection(){
		 //initialize
		 //every node is initialized
		 for(Node n:nodeList){
			 n.setLabelID(n.getNodeID());
		 }
		 
		 
		 boolean IsUpdated = true;
		 int rounds = 0;
		 while(IsUpdated){
			 rounds++;
			 System.out.println("rounds: " + rounds);
			 IsUpdated = false;
			 for(Node n: nodeList){
				 Map <Integer, Integer> labelMap = new HashMap<Integer, Integer>();
				 labelMap.clear();
				 //calculation of labels of neighbors
				 for(Neighbor e: n.getNeighborList()){
					 if(labelMap.containsKey(nodeList.get(e.getNodeId()).getLabelID())){
						 labelMap.put(nodeList.get(e.getNodeId()).getLabelID(), labelMap.get(nodeList.get(e.getNodeId()).getLabelID())+1);
//						 System.out.println("yes " + labelMap.keySet());
					 }else{
						 labelMap.put(nodeList.get(e.getNodeId()).getLabelID(), 1);
//						 System.out.println("No" + labelMap.keySet());
					 }
				 }
				 
				 //label the current node
				 int maxLabel = n.getLabelID();
				 int labelFrequency = 1;
				 int originalLabel = n.getLabelID();
				 
				 for (Integer i: labelMap.keySet()){
					 if(labelMap.get(i)>labelFrequency){
						 maxLabel = i;
						 labelFrequency = labelMap.get(i);
					 }
				 }
				 //this means there is no dominant label, so do a random process
				 if(maxLabel == originalLabel&&labelFrequency==1){
					 int r = (int)(Math.random()*labelMap.keySet().size());
					 Iterator ite = labelMap.keySet().iterator();
					 for(int i=0; i<r-1; i++){
						 maxLabel = (int)ite.next();
					 }
					 
//					 System.out.println("r: " + r + "  " + maxLabel );
				 }
				 //label the current node using max label
				 if(n.getLabelID() != maxLabel){
					 n.setLabelID(maxLabel);
					 if(labelFrequency != 1){
						 IsUpdated = true;
					 }
//					 System.out.println( n.getNodeID() + " : " + maxLabel + " " + labelFrequency);
				 }
			 }
		 }
		 //see how many communities we have
		 Map <Integer, Integer> labelMap = new HashMap<Integer, Integer>();
		 labelMap.clear();
		 for(Node n:nodeList){
			if(labelMap.containsKey(n.getLabelID())){
				labelMap.put(n.getLabelID(), labelMap.get(n.getLabelID())+1);
			}else{
				labelMap.put(n.getLabelID(), 1);
			}
		 }
		 System.out.println("Community number: " + labelMap.keySet().size());
		
		 
		 int communityID = 0;
		 //create communities
		 for (Integer i: labelMap.keySet()){
			 if(labelMap.get(i)>threshold){
				 Community c = new Community();
				 c.setLabelID(i);
				 c.setCommunityID(communityID);
				 communityID++;
				 communityList.add(c);
				 numOfCommunities++;
			 }
		 }
		 //add members for each communities
		 for(Node n: nodeList){
			 for(Community c: communityList){
				 if(n.getLabelID() == c.getLabelID()){
					 c.getMemberList().add(n);
					 break;
				 }
			 }
		 }
		 System.out.println("Community size>" + threshold + " number: " + numOfCommunities);
	 }

	 public void EdgeAnalysis(){
		 //detect edges for communities.
		 for(Community c: communityList){
			 //System.out.println("communityID: " +c.getCommunityID() + " size: " +c.getMemberList().size());
			 for(Node n: c.getMemberList()){
				 int previousLabel = nodeList.get(n.getNeighborList().get(0).getNodeId()).getLabelID();
				 boolean isEdge = false;
				 for(Neighbor e: n.getNeighborList()){
					 if(nodeList.get(e.getNodeId()).getLabelID()!=previousLabel){
						 isEdge = true;
						 break;
					 }
				 }
				 if(isEdge){
					 c.getEdgeList().add(n);
				 }
			 }
			 //output test
			 System.out.println("Community id: " + c.getCommunityID() + " size: " + c.getMemberList().size() + " edge size: " + c.getEdgeList().size());
		 }		 
		 
		 //edge analysis
		 for(Community c: communityList){
			 //double totalIn = 0;
			 //double totalOut = 0;
			 double maxInfluence = 0;
			 Node maxPin = null;
			 for(Node n: c.getEdgeList()){
				 
				 double inInfluence = 1.0;
				 double outInfluence = 1.0;
				 for(Neighbor e: n.getNeighborList()){
					 if(nodeList.get(e.getNodeId()).getLabelID()==c.getLabelID()){
						 inInfluence *= 1 - e.getWeight();
					 }else {
						 outInfluence *= 1 - e.getWeight();
					 }
				 }
				 inInfluence = 1 - inInfluence;
				 outInfluence = 1- outInfluence;
				 if(inInfluence*outInfluence > maxInfluence){
					 maxInfluence = inInfluence*outInfluence;
					 maxPin = n;
				 }
				 //totalIn +=inInfluence;
				 //totalOut +=outInfluence;
			 }
			 c.getPinList().add(maxPin);
			 
			 //System.out.println("Community id: " + c.getCommunityID() + " inInflu: " +totalIn/c.getEdgeList().size() + " outInflu: " + totalOut/c.getEdgeList().size() );
		 }
	 }
	 
	 
	 public void CrossPointsAnalysis(){
		 //input process
		
		 for(int i=0; i<numOfCommunities; i++){
			 for(int j=i+1; j<numOfCommunities; j++){
				 FindDisjointPaths findpaths = new FindDisjointPaths(nodeList, nodeList.size());
				 findpaths.findPaths(communityList.get(i).getPinList().get(0).getNodeID(), communityList.get(j).getPinList().get(0).getNodeID(), -1, 5, 1);
				 ArrayList<ArrayList<Integer>> paths = findpaths.getDisjointPaths();
				 System.out.println("Communities: " + i + " " + j);
				 for(ArrayList<Integer> a: paths){
					 System.out.println( paths.indexOf(a) + " path:");
					 for(Integer d:a){
						 System.out.print(d + " ");
					 }
					 System.out.println("prob: " + findpaths.getDisjointPathsProb().get(findpaths.getDisjointPaths().indexOf(a)));
				 }
			 }
		 }
	 }
	 
	 public int InfluenceSpread(){
		 for(int i=0; i<k; i++){
			 Spread(seeds[i]);
		 }
		 
		 //Statistics
		 int activeNum = 0;
		 int inactiveNum = 0;
		 for(Node n: nodeList){
			 if(n.isActive()){
				 activeNum++;
			 }else{
				 inactiveNum++;
			 }
		 }
		 
		 //double ratio = (double)activeNum/(activeNum + inactiveNum);
		 // System.out.println("Spread: " + activeNum);
		 return activeNum;
	 }
	 
	 
	 public void Spread(int nodeId){
		 for(Neighbor e: nodeList.get(nodeId).getNeighborList()){
			 double r = Math.random();
			 //if the neighbor is not active and r<e then activate it.
			 if(!nodeList.get(e.getNodeId()).isActive()&&r<=e.getWeight()){
				 nodeList.get(e.getNodeId()).setActive(true);
				 Spread(e.getNodeId());
			 }
		 }
	 }
	 
	 public static void main(String args[]){

		 int spread = 0;
		 int reps = 1;
		 for(int i=0; i<reps; i++){
			RabbitHole g = new RabbitHole(30);
		 	g.run();
			spread += g.InfluenceSpread();
		 } 
		 spread = spread/reps;
		 System.out.println("Influence Spread: " + spread);
		
	}
}



