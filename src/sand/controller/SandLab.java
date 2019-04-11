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
  
  public static int[] elements = {EMPTY, METAL, SAND};
  
  
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  //sorry Cody, I must add fields because aesthetic
  private Color[] colors;
  private Random random = new Random();
  
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
    colors = new Color[elements.length];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    
    colors[EMPTY] = new Color(5, 5, 5);
    colors[METAL] = new Color(130, 140, 140);
    colors[SAND] = new Color(200, 215, 90);
    
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
    if(grid[row][col] == SAND && grid[row + 1][col] == EMPTY)
    {
    	grid[row][col] = EMPTY;
    	grid[row + 1][col] = SAND;
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
