package sand.controller;
import java.awt.*;
import java.util.*;

import sand.view.SandDisplay;

public class SandLab
{
//	public Map tools = new Hashtable();
  //Step 4,6
  //add constants for particle types here	
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  public static final int DIRT = 4;
  public static final int MILK = 5;
  
  public static int[] elements = {EMPTY, METAL, SAND, WATER, DIRT, MILK};
  
  public static final int ERROR = elements.length;
  
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  //sorry Cody, I must add fields because aesthetic
  private Color[] colors;
  private Random random = new Random();
  private int operation;
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[elements.length];
    colors = new Color[elements.length + 1];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[DIRT] = "Dirt";
    names[MILK] = "Milk";
    
    colors[EMPTY] = new Color(5, 5, 5);
    colors[METAL] = new Color(130, 140, 140);
    colors[SAND] = new Color(200, 215, 90);
    colors[WATER] = new Color(15, 125, 210);
    colors[DIRT] = new Color(80, 40, 0);
    colors[MILK] = new Color(230, 230, 200);
    
    colors[ERROR] = new Color(245, 40, 35);
    
    //1. Add code to initialize the data member grid with same dimensions
    
    grid = new int[numRows][numCols];
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
   grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
  	int element;
      //Step 3
   //Hint - use a nested for loop
	  for(int row = 0; row < grid.length; row ++)
	  {
		  for(int col = 0; col < grid[0].length; col ++)
		  {
		  	element = grid[row][col];
			  switch(element)
			  {
			  case METAL: element = METAL;
			  	break;
			  case SAND: element = SAND;
			  	break;
			  case WATER: element = WATER;
			  	break;
			  case DIRT: element = DIRT;
			  	break;
			  case MILK: element = MILK;
			  	break;
			  default: element = EMPTY; // default
			  	break;
			  }
			  display.setColor(row, col, colors[element]);
		  } // end col loop
	  } // end row loop
    
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    //int someRandom = (int) (Math.random() * scalar)
    //remember that you need to watch for the edges of the array
    int row = getR(grid.length);
    int col = getR(grid[0].length);
    
    try
    {    	
    	if(grid[row][col] == SAND)
    	{
    		if(!(row == grid.length - 1))
    		{
    			if(grid[row + 1][col] == EMPTY)
    			{
    				grid[row][col] = EMPTY;
    				grid[row + 1][col] = SAND;
    			}
    			else if(grid[row + 1][col] == WATER)
    			{
    				grid[row][col] = WATER;
    				grid[row + 1][col] = SAND;
    			}
    			else 
    			{
    				if(col > 0)
    				{
    					if(grid[row + 1][col - 1] == EMPTY)
    					{
    						grid[row + 1][col - 1] = SAND;
    						grid[row][col] = EMPTY;
    					}
    					else if(grid[row + 1][col + 1] == EMPTY && col < grid[0].length)
    					{
    						grid[row + 1][col + 1] = SAND;
    						grid[row][col] = EMPTY;
    					}
    				}
    			}
    		}
    	}
    	
    	// WATER
    	liquidSpread(row, col, WATER);
    	
    	// MILK
    	liquidSpread(row, col, MILK);
    }
    
    catch(ArrayIndexOutOfBoundsException boundsExcept)
    {
    	display.setColor(row, col, colors[ERROR]);
    	System.out.println("Index out of bounds at row: " + row + ", col: " + col + "\n");
    }
  }
 
  public void liquidSpread(int row, int col, int element)
  {
  	if(grid[row][col] == element)
  	{
  		// fall down
  		if(row != 19)
  		{    			
  			if(grid[row + 1][col] == EMPTY)
  			{
  				grid[row][col] = EMPTY;
  				grid[row + 1][col] = element;
  			}
  			else
  			{
  				operation = getR(2);
  				if(operation == 1) // right
  				{
  					if(col != 19)
  					{
  						if(grid[row][col + 1] == EMPTY)
  						{
  							grid[row][col] = EMPTY;
  							grid[row][col + 1] = element;
  						}
  						else if(col != 0)
  						{
  							if(grid[row][col - 1] == EMPTY)
  							{
  								grid[row][col] = EMPTY;
  								grid[row][col - 1] = element;
  							}
  						}
  					}
  					else
  					{
  						if(grid[row][col - 1] == EMPTY)
  						{
  							grid[row][col] = EMPTY;
  							grid[row][col - 1] = element;
  						}
  					}
  				}
  				else // left
  				{
  					if(col != 0)
  					{
  						if(grid[row][col - 1] == EMPTY)
  						{
  							grid[row][col] = EMPTY;
  							grid[row][col - 1] = element;			
  						}
  					}
  					else if(grid[row][col + 1] == EMPTY)
  					{
  						grid[row][col] = EMPTY;
  						grid[row][col + 1] = element;
  					}
  				}
  			}
  		}
  	}
  	
  }
  
  //do not modify this method!
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
      }
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
  
  public int getR(int maximum)
  {
  	return random.nextInt(maximum);
  }
}
