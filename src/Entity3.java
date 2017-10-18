public class Entity3 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity3()
    {
    	int[] minCost=new int[4];
    	//0,2
    	for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
              distanceTable[i][j] = 999;
            }
    	}
    	
    	minCost[0]=7;
    	minCost[1]=999;
    	minCost[2]=2;
    	minCost[3]=0;
    	
    	distanceTable[0][0] = 7; distanceTable[1][1] = 999;
    	distanceTable[2][2] = 2;  distanceTable[3][3] = 0;
    	
    	NetworkSimulator.toLayer2(new Packet(3, 0, minCost));
 	 	NetworkSimulator.toLayer2(new Packet(3, 2, minCost));
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
    	
    	//0,2
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
    	
    	
    	   for(int i = 0; i < 4; i++){
    	        if(p.getMincost(i)+distanceTable[source][source] < distanceTable[i][source]){
    	          distanceTable[i][source] = p.getMincost(i)+distanceTable[source][source];
    	          if(distanceTable[i][p.getSource()]<minCost[i]){
    	            minCost[i] = distanceTable[i][p.getSource()];
    	            changed = true;
    	          }
    	}
	}
    	printDT();
    	if(changed) {
    		//communicates with 0,2
          NetworkSimulator.toLayer2(new Packet(3, 0, minCost));
          NetworkSimulator.toLayer2(new Packet(3, 2, minCost));
          printDT();
    	}	
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
 public void printDT()
       {
           System.out.println();
           System.out.println("           via");
           System.out.println(" D3 |  0   1   2   3");
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
