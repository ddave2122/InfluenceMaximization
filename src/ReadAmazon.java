import java.io.*;
import java.util.ArrayList;
import util.*;

public class ReadAmazon {

    int numOfNodes = 262111;
 
    //Adjacent lists
    ArrayList<Node> nodeList = new ArrayList<Node>();

	 
	public void run(){ 
	    try  
	    {  
	        FileInputStream fstream = new FileInputStream("data/Amazon0302.txt");  
	       
	        DataInputStream in = new DataInputStream(fstream);  
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));  
	        String strLine;

	        
	        //int average = 0;
	        //int num = 0;
			
//			int previous0 = 0;
//        	int previous1 = 0;
        	
	        while ((strLine = br.readLine()) != null)  
		    {
	        
	        	String[] str = strLine.split("	");
	        	int[] values = new int[str.length];
	        	for(int i=0;i<str.length;i++){
	        		values[i] = Integer.valueOf(str[i]);
	        	}
//	        	System.out.println("length: " +values.length);
//	        	System.out.println(values[0] +  " " + values[1]);
	        	
	        	
	        	//first line show how many nodes there are
	        	if(values[0]==262111&&values[1]==1234877){
	        		//create the list
	        		for(int i=0; i<values[0]; i++){
	        			Node node = new Node();
	        			node.setNodeID(i);
	        			nodeList.add(node);
	        		}
	        		continue;
	        	}
	        		
//	        	//delete the repeating pair according to the directory
//	        	if(values[0]==previous0&&values[1]==previous1){
//	        		previous0 = values[0];
//	        		previous1 = values[1];
//	        		continue;
//	        	}
//	        	
//	        	//update the previous
//	        	previous0 = values[0];
//        		previous1 = values[1];
	        	
	        	
	        	if(values[0]>=0&&values[0]<=numOfNodes&&values[1]>=0&&values[1]<=numOfNodes){
	        		Neighbor neighbor = new Neighbor();
	        		neighbor.setNodeId(values[1]);
	        		boolean isNewPair = true;
	        		for(Neighbor e: nodeList.get(values[0]).getNeighborList()){
	        			if(neighbor.equals(e)){
	        				isNewPair = false;
	        			}
	        		}
	        		if(isNewPair){
	        			neighbor.setWeight(trivalencyModel());
	        			nodeList.get(values[0]).getNeighborList().add(neighbor);
	        			Neighbor neighbor1 = new Neighbor();
	        			neighbor1.setNodeId(values[0]);
	        			neighbor1.setWeight(trivalencyModel());
	        			nodeList.get(values[1]).getNeighborList().add(neighbor1);
	        		}
	        	}
		    }
	        
	        
	        in.close();  
	    }  
	    catch(Exception e)  
	    {  
	        System.err.println("Error: " + e.getMessage());  
	    }  
	  }

	
	//return a random value from 0.1, 0.01, 0.001.
	public double trivalencyModel(){
		//double p = 1 + (int)(Math.random()*3);
		//System.out.println(1/(Math.pow(10, p)));
		
		//double p = Math.random();
		//TRIVALENCY model
		//return 1/(Math.pow(10, p));
		
		//TRIVALENCY model 2
		int p = (int)(Math.random()*3);
		double[] choice = {0.2,0.04,0.008};
		return choice[p];
				
		
		//uniform IC model
		//return (double)1/100;
	}
	
	
	/**
	 * @return the numOfNodes
	 */
	public int getNumOfNodes() {
		return numOfNodes;
	}



	/**
	 * @param numOfNodes the numOfNodes to set
	 */
	public void setNumOfNodes(int numOfNodes) {
		this.numOfNodes = numOfNodes;
	}





	/**
	 * @return the nodeList
	 */
	public ArrayList<Node> getNodeList() {
		return nodeList;
	}



	/**
	 * @param nodeList the nodeList to set
	 */
	public void setNodeList(ArrayList<Node> nodeList) {
		this.nodeList = nodeList;
	}



	public static void main(String args[]){

		ReadAmazon readAmazonData = new ReadAmazon();
		readAmazonData.run();
		ArrayList<Node> list = readAmazonData.getNodeList();
		for(Node n: list){
			System.out.print(n.getNodeID() + " neighbors: ");
			for(Neighbor e: n.getNeighborList()){
				System.out.print(e.getNodeId() + " " + e.getWeight() + " ");
			}
			System.out.println();
		}
	}

}
