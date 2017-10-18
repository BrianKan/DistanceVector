public class Entity0 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity0()
    {	
    	int[] minCost=new int[4];
    	// Connections : 1,2,3
    	//Each entity has a distancetable
    	//nested for loop to intialize all variables to infinity
    	 for(int i = 0; i < 4; i++){
   	        for(int j = 0; j < 4; j++){
   	        	if(i==j) {
   	        		distanceTable[i][j]=0;
   	        	}
   	        	else
   	          distanceTable[i][j] = 999;
   	        }
      	 }
 	
    	
    	minCost[0]=0;
    	minCost[1]=1;
    	minCost[2]=3;
    	minCost[3]=7;
    	
    	//Setting the table values to the costs
    	distanceTable[0][0]=0;distanceTable[0][1]=1;
    	distanceTable[0][2]=3;distanceTable[0][3]=7;
    	printDT();
    	//source dest
    	NetworkSimulator.toLayer2(new Packet(0, 1, minCost));
		NetworkSimulator.toLayer2(new Packet(0, 2, minCost));
		NetworkSimulator.toLayer2(new Packet(0, 3, minCost));
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
    	int[] minCost=new int[4];
    	boolean changed=false;
    	
    	for(int i = 0; i < 4; i++){
    		int zeroone=Math.min(distanceTable[i][0], distanceTable[i][1]);
            int twothree= Math.min(distanceTable[i][2], distanceTable[i][3]);
            minCost[i] = Math.min(zeroone, twothree);
        }
    	
    	//Loop through to compare the minimums

    	for(int k = 0; k<4; k++){
    		// If mincost of packet +minCost of source < current value
            if(p.getMincost(k)+minCost[source] < distanceTable[source][k]){
            	//set current value to mincost+cost to source
              distanceTable[source][k] = p.getMincost(k)+minCost[source];
              
              // if current value less than mincost k
              // update the mincost and send it to the other tables
              if(distanceTable[source][k]<minCost[k]){
                minCost[k] = distanceTable[source][k];
                changed= true;
              }
            }
    	}
    	  
    	if(changed) {
    		//Communicates with 1,2,3
    		NetworkSimulator.toLayer2(new Packet(0, 1, minCost));
    		NetworkSimulator.toLayer2(new Packet(0, 2, minCost));
    		NetworkSimulator.toLayer2(new Packet(0, 3, minCost));
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
