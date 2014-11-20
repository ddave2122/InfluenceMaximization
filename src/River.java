import java.util.ArrayList;
import util.*;

public class River {

	 ArrayList<Node> nodeList;
	 int k;
	 int[] seeds;
	 
	
	 
	 //initialize
	 public River (int sizeOfSeedSet) {
		super();
		//read hep
		ReadHep readHepData = new ReadHep();
		readHepData.run();
		this.nodeList = readHepData.getNodeList();
		
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
//		ReadAmazon readAmazonData = new ReadAmazon();
//		readAmazonData.run();
//		this.nodeList = readAmazonData.getNodeList();
		
		k = sizeOfSeedSet;
		seeds = new int[k];
		for(int i=0; i<k; i++){
			seeds[i] = 0;
		}
		
	}
	 
	 public void run(){
		 ShrinkingSelectSeed();
		 InfluenceSpread();
	 }
	 
	 public void ShrinkingSelectSeed(){
		 //simple shrink, direct deleting influence
		 
		 for(int i=0; i<k; i++){
			 int tempID = (int)(Math.random()*(nodeList.size()));
			 while(nodeList.get(tempID).isSeed()){
				 tempID = (int)(Math.random()*(nodeList.size()));
			 }
			 seeds[i] = tempID;
			 nodeList.get(tempID).setSeed(true);
			 nodeList.get(tempID).setActive(true);
			 //System.out.println("Seed: " + i + " : " + tempID + " degree: " + nodeList.get(tempID).getNeighborList().size());
			 //System.out.println("seed: " +  i + " "+ tempID );
		 }
	 }
	 
	 public void shrinkProcess(){
		 for(Node n: nodeList){
			 for(Neighbor e: n.getNeighborList()){
				 
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
		 int reps = 500;
		 for(int i=0; i<reps; i++){
		 	InfluenceHeuristics g = new InfluenceHeuristics(30);
		 	g.run();
			spread += g.InfluenceSpread();
		 } 
		 spread = spread/reps;
		 System.out.println("Influence Spread: " + spread);
		
	}
	 
	 
	 
	 
}
