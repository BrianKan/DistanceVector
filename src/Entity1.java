public class Entity1 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity1()
    {
    	// 0 and 2
    	int[] minCost=new int[4];
    	 for(int i = 0; i < 4; i++){
   	        for(int j = 0; j < 4; j++){
   	        	if(i==j) {
   	        		distanceTable[i][j]=0;
   	        	}
   	        	else
   	          distanceTable[i][j] = 999;
   	        }
      	 }
 	
    	minCost[0]=1;
    	minCost[1]=0;
    	minCost[2]=1;
    	minCost[3]=999;
    	
    	distanceTable[1][0] = 1;distanceTable[1][1] = 0;
        distanceTable[1][2] = 1;distanceTable[1][3] = 999;
    	printDT();
        NetworkSimulator.toLayer2(new Packet(1, 0, minCost));
		NetworkSimulator.toLayer2(new Packet(1, 2, minCost));
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
    	
    	for(int k = 0; k<4; k++){
            if(p.getMincost(k)+minCost[p.getSource()] < distanceTable[source][k]){
             
              distanceTable[source][k] = p.getMincost(k)+minCost[source];
              if(distanceTable[source][k]<minCost[k]){
                minCost[k] = distanceTable[source][k];
                changed= true;
              }
            }
    	}
   
    	if(changed) {
    		//communicates with 0 and 2
          NetworkSimulator.toLayer2(new Packet(1, 0, minCost));
          NetworkSimulator.toLayer2(new Packet(1, 2, minCost));
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
