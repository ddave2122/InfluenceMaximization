package util;


import java.util.ArrayList;


public class FindDisjointPaths {
	ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
	
	ArrayList<ArrayList<Integer>> disjointPaths = new ArrayList<ArrayList<Integer>>();
	
	ArrayList<Double> disjointPathsProb = new ArrayList<Double>();
	
	//every time the path cannot go further since next node is visited by the optimal one,
	// all the optimal path is removed, the path should do again
	ArrayList<ArrayList<Integer>> sleepPaths = new ArrayList<ArrayList<Integer>>();
		
	ArrayList<Double> sleepPathsProb = new ArrayList<Double>();
	
	//marked as being removed
	ArrayList<Edge> markedEdges = new ArrayList<Edge>();
	
	double[] nodeMaxProb;
	
	//double[][] contactRate;
	ArrayList<Node> nodeList = new ArrayList<Node>();
	
	
	int size;
	
	//depends on ttl
	int round;
	
	ArrayList<Double> pathsProb = new ArrayList<Double>();
	
	public FindDisjointPaths(ArrayList<Node> list, int s) {
		nodeList = list;
		nodeMaxProb = new double[s];
		//transmit by value
		for(int i = 0; i<s; i++){
			nodeMaxProb[i] = 0;
		}
		size = s;
		markedEdges.clear();
		paths.clear();
		disjointPaths.clear();
		disjointPathsProb.clear();
		pathsProb.clear();
		sleepPaths.clear();
		sleepPathsProb.clear();
	} 

	
	/**
	 * @return currentPath first called use -1
	 * find k disjoint shortest paths
	 * @throws InterruptedException 
	 */
	public  void findPaths(int source, int dest, int parentPath, int TTL, int k) {
		
		//System.out.println("paths size: " + paths.size() + " disjoint paths size: " + disjointPaths.size());
		//there is already one path to the destination
		//delete all the paths with same edge with the successful path

		//System.out.println("paths size: " + paths.size()  + " pathsProb size: " +pathsProb.size());
		
		if(disjointPaths.size()==k){
			return;
		}
		if(paths.size()==0&&parentPath!=-1)
			return;
		
		if(source==dest){
			int previous = -1;
			if(parentPath == -1){
				ArrayList<Integer> path = new ArrayList<Integer>();
				path.add(source);
				//path.add(source);
				disjointPaths.add(path);
				disjointPathsProb.add(1.0);
				return;
			}
				
			//check whether contains the overlapping edges
			for(Integer d: paths.get(parentPath)){
				if(previous == -1){
					previous = d;
				}else{
					Edge e = new Edge(previous, d);
					previous = d;
					//System.out.println(e.getNode1()+ " " +e.getNode2());
					for(Edge ed: markedEdges){
						if(ed.equals(e)){
							paths.remove(parentPath);
							pathsProb.remove(parentPath);
							return;
						}
							
					}
					
				}
			}
			
			previous = -1;
			for(Integer d:paths.get(parentPath)){
				if(previous == -1){
					previous =d;
				}else{
					Edge e = new Edge(previous, d);
					previous = d;
					//System.out.println("Added: " + e.getNode1() + " " +e.getNode2());
					boolean existedEdge = false;
					for(Edge ed: markedEdges){
						if(ed.equals(e))
							existedEdge = true;
					}
					if(existedEdge == false){
						markedEdges.add(e);
						//nodeList.get(e.getNode1()).getNeighborList().get(e.getNode2()).setWeight(0);
						//nodeList.get(e.getNode2()).getNeighborList().get(e.getNode1()).setWeight(0);

						
						
						//if we need to find more than one path, please cancel the following comments
						for(Neighbor ne: nodeList.get(e.getNode1()).getNeighborList()){
							if(ne.getNodeId() == e.getNode2()){
								ne.setWeight(0);
								break;
							}
						}
						for(Neighbor ne: nodeList.get(e.getNode2()).getNeighborList()){
							if(ne.getNodeId() == e.getNode1()){
								ne.setWeight(0);
								break;
							}
						}
						
						//contactRate[e.getNode1()][e.getNode2()] = 0;
						//contactRate[e.getNode2()][e.getNode1()] = 0;
						nodeMaxProb[e.getNode2()] = 0;
						//System.out.println("Added: " + e.getNode1() + " " +e.getNode2());
					}
				}
			}
			disjointPaths.add(paths.get(parentPath));
			disjointPathsProb.add(pathsProb.get(parentPath));
			//output test
//			System.out.println("successful path: ");
//			for(int i=0; i<paths.get(parentPath).size(); i++){
//				System.out.print(paths.get(parentPath).get(i)+" ");
//			}
//			System.out.println();
			paths.remove(parentPath);
			pathsProb.remove(parentPath);
			
			//delete paths that overlaps with the selected successful one
			ArrayList<ArrayList<Integer>> removedList = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> removedProb = new ArrayList<Integer>();
			removedList.clear();
			removedProb.clear();
			for(ArrayList<Integer> a: paths){
				previous = -1;
				for(Integer d: a){
					if(previous == -1){
						previous =d;
					}else{
						Edge e = new Edge(previous, d);
						previous = d;
						if(markedEdges.contains(e)){
							removedList.add(a);
							removedProb.add(paths.indexOf(a));
							break;
						}
					}
				}
			}
			paths.removeAll(removedList);
			for(int i=0; i<removedProb.size(); i++){
				int max = 0;
				for(Integer d:removedProb){
					if(d>removedProb.get(max)){
						max = removedProb.indexOf(d);
					}
				}
				//System.out.println("removedProb: " +removedProb.get(max));
				pathsProb.remove((int)removedProb.get(max));
				removedProb.set(max, -1);
			}
			//Sydstem.out.println("removedList size: " + removedList.size());
			paths.addAll(sleepPaths);
			pathsProb.addAll(sleepPathsProb);
			sleepPaths.clear();
			sleepPathsProb.clear();
			
			if(paths.size()==0)
				return;
		}
		else{
		
			//it expires
			if(parentPath!=-1&&paths.get(parentPath).size()-1>=TTL){
				paths.remove(parentPath);
				pathsProb.remove(parentPath);
				return;
			}
			//width first search, add all current new paths
			//first call
			if(paths.size() == 0&&parentPath==-1){
				ArrayList<Integer> path = new ArrayList<Integer>();
				path.add(source);
				paths.add(path);
				pathsProb.add((double) 1);
				parentPath = 0;
			}
			
			if(paths.size()==0)
				return;
			
			ArrayList<ArrayList<Integer>> removedList = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> removedProb = new ArrayList<Integer>();
			removedList.clear();
			removedProb.clear();
			//System.out.println("source: " +source);
			for(Neighbor e: nodeList.get(source).getNeighborList()){
				if(e.getWeight()>0){
					ArrayList<Integer> path = new ArrayList<Integer>();
					if(paths.size()!=0){
						path = (ArrayList<Integer>) paths.get(parentPath).clone();
						if(!path.contains(e.getNodeId())){
							if(e.getWeight()* pathsProb.get(parentPath).doubleValue() > nodeMaxProb[e.getNodeId()]){
								for(ArrayList<Integer> a: paths){
									if(a.contains(e.getNodeId())&&a.get(a.size()-1)!=e.getNodeId()&&!removedList.contains(a)){
										removedList.add(a);
										removedProb.add(paths.indexOf(a));
									}
								}
								path.add(e.getNodeId());
								if(!paths.contains(path)){
									paths.add(path);
									pathsProb.add(e.getWeight()* pathsProb.get(parentPath).doubleValue());
								}
								// the current maximum probability that paths reach this node
								nodeMaxProb[e.getNodeId()] = pathsProb.get(pathsProb.size()-1);
								
								
							}else{
								sleepPaths.add(path);
								sleepPathsProb.add(pathsProb.get(parentPath).doubleValue());
								for(Integer d:path){
									nodeMaxProb[d] = 0;
								}
								break;
							}
						}
						//else
							//System.out.println("contian works");
					}else { //first call
						path.add(e.getNodeId());
						paths.add(path);
						pathsProb.add(e.getWeight());
						nodeMaxProb[e.getNodeId()] = pathsProb.get(pathsProb.size()-1);
					}
				}
			}

			//if there is no child, the path is removed
			//if(childExisted == false){
				//paths.remove(parentPath);
				//pathsProb.remove(parentPath);
			//}
			
			//remove original parent path
			if(parentPath!=-1){
				//paths.remove(parentPath);
				//pathsProb.remove(parentPath);
				if(!removedList.contains(paths.get(parentPath))){
					removedList.add(paths.get(parentPath));
					removedProb.add(parentPath);
				}
			}
	
//			System.out.println("paths size: " + paths.size()  + " pathsProb size: " +pathsProb.size() + " removedList size: " + removedList.size()+ " removedProb size: " + removedProb.size());
			
			paths.removeAll(removedList);
			for(int i=0; i<removedProb.size(); i++){
				int max = 0;
				for(Integer d:removedProb){
					if(d>removedProb.get(max)){
						max = removedProb.indexOf(d);
					}
				}
//				System.out.println("removedProb: " +removedProb.get(max));
				pathsProb.remove((int)removedProb.get(max));
				removedProb.set(max, -1);
			}
			
//			System.out.println("paths size: " + paths.size()  + " pathsProb size: " +pathsProb.size());
			
			
			if(paths.size()==0)
				return;
			
		}
		//find maximum probability path
//		for(ArrayList<Integer> a: paths){
//			System.out.println("TTL: " + TTL + "paths:");
//			for(Integer d: a){
//				System.out.print(d + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
		
		while(paths.size()!=0&&disjointPaths.size()<k){
			//System.out.println(paths.size());
			int maximumPath = findMaximumPath();
			//try{
				findPaths(paths.get(maximumPath).get(paths.get(maximumPath).size() - 1), dest, maximumPath, TTL, k);
//			//}catch (java.lang.StackOverflowError e) {
//				//System.out.println(e.toString());
//				System.out.println("paths size: " + paths.size());
//				System.out.println(paths.get(paths.size()-1).size());
//				for(Integer d:paths.get(paths.size()-1)){
//					System.out.print(d + " ");
//				}
//				return;
//			}
		}
		return;
	}

	
	/**
	 * @return currentPath first called use -1
	 */
	/*
	public  void findPaths(int source, int dest, int parentPath, int TTL){
		
		//there is already one path to the destination
		//delete all the paths with same edge with the successful path

	
		
		if(source==dest){
			int previous = -1;
			if(parentPath == -1){
				ArrayList<Integer> path = new ArrayList<Integer>();
				path.add(source);
				//path.add(source);
				disjointPaths.add(path);
				disjointPathsProb.add(1.0);
				return;
			}
				
			//check whether contains the overlapping edges
			for(Integer d: paths.get(parentPath)){
				if(previous == -1){
					previous = d;
				}else{
					Edge e = new Edge(previous, d);
					previous = d;
					//System.out.println(e.getNode1()+ " " +e.getNode2());
					for(Edge ed: markedEdges){
						if(ed.equals(e)){
							paths.remove(parentPath);
							pathsProb.remove(parentPath);
							return;
						}
							
					}
					
				}
			}
			
			previous = -1;
			for(Integer d:paths.get(parentPath)){
				if(previous == -1){
					previous =d;
				}else{
					Edge e = new Edge(previous, d);
					previous = d;
					//System.out.println("Added: " + e.getNode1() + " " +e.getNode2());
					boolean existedEdge = false;
					for(Edge ed: markedEdges){
						if(ed.equals(e))
							existedEdge = true;
					}
					if(existedEdge == false){
						markedEdges.add(e);
						//System.out.println("Added: " + e.getNode1() + " " +e.getNode2());
					}
				}
			}
			disjointPaths.add(paths.get(parentPath));
			disjointPathsProb.add(pathsProb.get(parentPath));
			paths.remove(parentPath);
			pathsProb.remove(parentPath);
			
			//delete paths that overlaps with the selected successful one
			ArrayList<ArrayList<Integer>> removedList = new ArrayList<ArrayList<Integer>>();
			ArrayList<Double> removedProb = new ArrayList<Double>();
			removedList.clear();
			removedProb.clear();
			for(ArrayList<Integer> a: paths){
				previous = -1;
				for(Integer d: a){
					if(previous == -1){
						previous =d;
					}else{
						Edge e = new Edge(previous, d);
						previous = d;
						if(markedEdges.contains(e)){
							removedList.add(a);
							removedProb.add(pathsProb.get(paths.indexOf(a)));
						}
					}
				}
			}
			System.out.println("removedList size: "+removedList.size());
			paths.removeAll(removedList);
			pathsProb.removeAll(removedProb);
		}
		else{
		
			//it expires
			if(parentPath!=-1&&paths.get(parentPath).size()-1>=TTL){
				paths.remove(parentPath);
				pathsProb.remove(parentPath);
				return;
			}
			//width first search, add all current new paths
			//first call
			if(paths.size() == 0&&parentPath==-1){
				ArrayList<Integer> path = new ArrayList<Integer>();
				path.add(source);
				paths.add(path);
				pathsProb.add((double) 1);
				parentPath = 0;
			}
			
			if(paths.size()==0)
				return;
			
			for(int i=0;i<size;i++){
				if(contactRate[source][i]>0){
					ArrayList<Integer> path = new ArrayList<Integer>();
					if(paths.size()!=0){
						path = (ArrayList<Integer>) paths.get(parentPath).clone();
						if(!path.contains(i)){
							path.add(i);
							paths.add(path);
							pathsProb.add(contactRate[source][i]* pathsProb.get(parentPath).doubleValue());
						}
					}else { //first call
						path.add(i);
						paths.add(path);
						pathsProb.add(contactRate[source][i]);
					}
				}
			}
			
			//if there is no child, the path is removed
			//if(childExisted == false){
				//paths.remove(parentPath);
				//pathsProb.remove(parentPath);
			//}
			
			//remove original parent path
			if(parentPath!=-1){
				paths.remove(parentPath);
				pathsProb.remove(parentPath);
			}
	
			if(paths.size()==0)
				return;
		}
		//find maximum probability path
//		for(ArrayList<Integer> a: paths){
//			System.out.println("TTL: " + TTL + "paths:");
//			for(Integer d: a){
//				System.out.print(d + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
		
		while(paths.size()!=0){
			int maximumPath = findMaximumPath();
			findPaths(paths.get(maximumPath).get(paths.get(maximumPath).size()-1), dest, maximumPath, TTL);
		}
		return;
	}
	*/

	
	public int findMaximumPath(){
		double maximumProb = 0;
		int maximumPath = 0;
		for(Double d:pathsProb){
			if(d.doubleValue()>maximumProb){
				maximumProb = d.doubleValue();
				maximumPath = pathsProb.indexOf(d);
			}
		}
		return maximumPath;
	}
	/**
	 * @return the disjointPaths
	 */
	public ArrayList<ArrayList<Integer>> getDisjointPaths() {
		return disjointPaths;
	}

	/**
	 * @param disjointPaths the disjointPaths to set
	 */
	public void setDisjointPaths(ArrayList<ArrayList<Integer>> disjointPaths) {
		this.disjointPaths = disjointPaths;
	}

	/**
	 * @return the pathsProb
	 */
	public ArrayList<Double> getPathsProb() {
		return pathsProb;
	}

	/**
	 * @param pathsProb the pathsProb to set
	 */
	public void setPathsProb(ArrayList<Double> pathsProb) {
		this.pathsProb = pathsProb;
	}

	/**
	 * @return the paths
	 */
	public ArrayList<ArrayList<Integer>> getPaths() {
		return paths;
	}

	/**
	 * @param paths the paths to set
	 */
	public void setPaths(ArrayList<ArrayList<Integer>> paths) {
		this.paths = paths;
	}

	/**
	 * @return the disjointPathsProb
	 */
	public ArrayList<Double> getDisjointPathsProb() {
		return disjointPathsProb;
	}

	/**
	 * @param disjointPathsProb the disjointPathsProb to set
	 */
	public void setDisjointPathsProb(ArrayList<Double> disjointPathsProb) {
		this.disjointPathsProb = disjointPathsProb;
	}
	
	
	public static void main(String args[]){
		ArrayList<Node> test = new ArrayList<Node>();
		for(int i=0;i<5;i++){
			Node n = new Node();
			n.setNodeID(i);
			test.add(n);
		}
		Neighbor e = new Neighbor();
		e.setNodeId(1);
		e.setWeight(0.8);
		test.get(0).getNeighborList().add(e);
		
		Neighbor e1 = new Neighbor();
		e1.setNodeId(2);
		e1.setWeight(0.8);
		test.get(1).getNeighborList().add(e1);
		
		Neighbor e2 = new Neighbor();
		e2.setNodeId(4);
		e2.setWeight(0.8);
		test.get(2).getNeighborList().add(e2);
		
		Neighbor e4 = new Neighbor();
		e4.setNodeId(4);
		e4.setWeight(0.3);
		test.get(0).getNeighborList().add(e4);
		
		Neighbor e5 = new Neighbor();
		e5.setNodeId(3);
		e5.setWeight(0.6);
		test.get(0).getNeighborList().add(e5);
		
		Neighbor e6 = new Neighbor();
		e6.setNodeId(4);
		e6.setWeight(0.6);
		test.get(3).getNeighborList().add(e6);
		
		for(Node n: test){
			for(Neighbor ee: n.getNeighborList()){
				if(ee.getNodeId() > n.getNodeID()){
					Neighbor ne = new Neighbor();
					ne.setNodeId(n.getNodeID());
					ne.setWeight(ee.getWeight());
					test.get(ee.getNodeId()).getNeighborList().add(ne);
				}
			}
			
		}
		
		
		FindDisjointPaths findpaths = new FindDisjointPaths(test, 5);
		System.out.println(test.get(0).getNeighborList().size());

		findpaths.findPaths(0, 4, -1, 3, 5);
		
		
		ArrayList<ArrayList<Integer>> paths = findpaths.getDisjointPaths();
		for(ArrayList<Integer> a: paths){
			System.out.println( paths.indexOf(a) + " path:");
			for(Integer d:a){
				System.out.print(d + " ");
			}
			System.out.println("prob: " + findpaths.getDisjointPathsProb().get(findpaths.getDisjointPaths().indexOf(a)));
		}
		
		System.out.println("remaining paths's number: " + findpaths.getPaths().size());
	}
	
}
