public class Entity0 extends Entity
{    
    // Perform any necessary initialization in the constructor
	static int[] distVector = new int[4];
	static final int ENTITY_ID=0;
    public Entity0()
    {	
    	
    	// Connections : 1,2,3
    	//Each entity has a distancetable
    	//nested for loop to intialize all variables to infinity
    	 for(int i = 0; i < 4; i++){
   	        for(int j = 0; j < 4; j++){
   	        	if(i==j) {
   	        	   distanceTable[i][j] = 0;
   	        	}
   	        	else
   	        		distanceTable[i][j] = 999;
   	        }
      	 }
 	

    	distVector[0]=0;
    	distVector[1]=1;
    	distVector[2]=3;
    	distVector[3]=7;
    	
    	//Setting the table values to the costs
    	distanceTable[0][0]=0;
    	distanceTable[0][1]=1;
    	distanceTable[0][2]=3;
    	distanceTable[0][3]=7;
    	
    	System.out.println("Initialized");
    	printDT();
    
    	
    	//source dest
    	NetworkSimulator.toLayer2(new Packet(0, 1, distVector));
		NetworkSimulator.toLayer2(new Packet(0, 2, distVector));
		NetworkSimulator.toLayer2(new Packet(0, 3, distVector));
    }
    	
    
//    Representation of Distance Table
//    [0,0][0,1][0,2][0,3]
//    [1,0][1,1][1,2][1,3]
//    [2,0][2,1][2,2][2,3]
//    [3,0][3,1][3,2][3,3]
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {      
    	System.out.println("-----------------------------");
    	System.out.println("Before updating node 0");
    	System.out.println("-----------------------------");
    	printDT();
    	int source=p.getSource();
    	int min;
    	int[] maxCost=new int[4];
    	boolean changed=false;
    	
    	
    	//need to update mincost vector
//    	distVector[0]=0;
//    	distVector[1]=0;
//    	distVector[2]=0
//    	distVector[3]=Math.min(a, b)
    	    	
    	
    	//Loop through to compare the minimums

    	for(int k = 0; k<4; k++){
    		// If mincost of packet +distVector of source < current value
    		//set current value to mincost+cost to source
    		//Update the current row with the new distance vector
    		if(p.getMincost(k)< distanceTable[source][k]){
                distanceTable[source][k] =p.getMincost(k);
    		}
            if(p.getMincost(k)+distVector[source] < distanceTable[0][k]){
              distanceTable[0][k] = p.getMincost(k)+distVector[source];
              
              
              // if current value less than mincost k
              // update the mincost and send it to the other tables
              if(distanceTable[0][k]<distVector[k]){
                distVector[k] = distanceTable[0][k];
                changed= true;
              }
            }
    	}
    		
    	System.out.println("-----------------------------");
    	System.out.println("After updating node 0");
    	System.out.println("-----------------------------");
    	printDT();
    	if(changed) {
    		System.out.println("Distance Vector Changed");
    		//Communicates with 1,2,3
    		NetworkSimulator.toLayer2(new Packet(0, 1, distVector));
    		NetworkSimulator.toLayer2(new Packet(0, 2, distVector));
    		NetworkSimulator.toLayer2(new Packet(0, 3, distVector));
    		printDT();
    	}
    	printDT();
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
    	System.out.println();
        System.out.println("           via");
        System.out.println(" D0 |  0   1   2   3");
        System.out.println("----+-----------------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++)
            {
                if (distanceTable[i][j] < 10)
                {
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else
                {
                    System.out.print(" ");
                }

                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
