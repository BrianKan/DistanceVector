public class Entity2 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity2()
    {
    	int[] minCost=new int[4];
    	// 0, 1,3 
    	 for(int i = 0; i < 4; i++){
   	        for(int j = 0; j < 4; j++){
   	        	if(i==j) {
   	        		distanceTable[i][j]=0;
   	        	}
   	        	else
   	          distanceTable[i][j] = 999;
   	        }
      	 }
    	minCost[0]=3;
    	minCost[1]=1;
    	minCost[2]=0;
    	minCost[3]=2;
    	
    	
    	distanceTable[2][0] = 3;distanceTable[2][1] = 1;
        distanceTable[2][2] = 0;distanceTable[2][3] = 2;
    	printDT();
        NetworkSimulator.toLayer2(new Packet(2, 0, minCost));
        NetworkSimulator.toLayer2(new Packet(2, 1, minCost));
		NetworkSimulator.toLayer2(new Packet(2, 3, minCost));
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
            int zeroone = Math.min(distanceTable[i][0], distanceTable[i][1]);
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
    		//communicates with 0,1,3
          NetworkSimulator.toLayer2(new Packet(2, 0, minCost));
          NetworkSimulator.toLayer2(new Packet(2, 1, minCost));
          NetworkSimulator.toLayer2(new Packet(2, 3, minCost));
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
          System.out.println(" D2 |  0   1   2   3");
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
