public class Entity1 extends Entity
{    
    // Perform any necessary initialization in the constructor
	static int[] distVector = new int[4];
	static final int ENTITY_ID=1;
    public Entity1()
    {
    	// 0 and 2
    	 for(int i = 0; i < 4; i++){
    	        for(int j = 0; j < 4; j++){
    	        	if(i==j) {
    	        	   distanceTable[i][j] = 0;
    	        	}
    	        	else
    	        		distanceTable[i][j] = 999;
    	        }
       	 }
 	
    	distVector[0]=1;
    	distVector[1]=0;
    	distVector[2]=1;
    	distVector[3]=999;
    	
    	distanceTable[1][0] = 1;
    	distanceTable[1][1] = 0;
        distanceTable[1][2] = 1;
        distanceTable[1][3] = 999;
        
        System.out.println("Initialized");
    	printDT();
    	
 
    	
        NetworkSimulator.toLayer2(new Packet(1, 0, distVector));
		NetworkSimulator.toLayer2(new Packet(1, 2, distVector));
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
    	int source=p.getSource();
    	int min;
    	int[] maxCost=new int[4];
    	boolean changed=false;
    	System.out.println("-----------------------------");
    	System.out.println("Before updating node 1");
    	System.out.println("-----------------------------");
    	printDT();
    	
    	

    	for(int k = 0; k<4; k++){
    		// If mincost of packet +distVector of source < current value
    		//set current value to mincost+cost to source
    		//Update the current row with the new distance vector
    		if(p.getMincost(k)< distanceTable[source][k]){
                distanceTable[source][k] =p.getMincost(k);
    		}
            if(p.getMincost(k)+distVector[source] < distanceTable[1][k]){
              distanceTable[1][k] = p.getMincost(k)+distVector[source];
              
              
              // if current value less than mincost k
              // update the mincost and send it to the other tables
              if(distanceTable[1][k]<distVector[k]){
                distVector[k] = distanceTable[1][k];
                changed= true;
              }
            }
    	}
    	System.out.println("-----------------------------");
    	System.out.println("After updating node 1");
    	System.out.println("-----------------------------");
    	printDT();
    	if(changed) {
    		System.out.println("Distance Vector Changed");
    		//communicates with 0 and 2
          NetworkSimulator.toLayer2(new Packet(1, 0, distVector));
          NetworkSimulator.toLayer2(new Packet(1, 2, distVector));
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
		        System.out.println(" D1 |  0   1   2   3");
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
