package application;

import java.util.Random;

/**
 * 
 * @author Robert Sadler
 * Terrain is the 2D grid made up of Nodes 
 */
public class Terrain {

	private Node[][] terrain;
	private int gridX;
	private int gridY;
	private float nodeDiameter;
	
	//Constructor
	public Terrain(int newGridX, int newGridY, float newnodeDiameter) {
		this.gridX = newGridX;
		this.gridY = newGridY;
		this.nodeDiameter = newnodeDiameter;
		terrain = new Node[gridX][gridY];
	}
	
	//Will change to have a noise function to create a "natural" feeling terrain
	/**
	 * Generates a random terrain (presuming Landscape)
	 */
	public void generateRandomTerrain() {
		Random rand = new Random();
		//Loop through the entire grid and create nodes
		for(int i = 0; i < gridX; i++) {
			for(int j = 0; j < gridY; j++) {
				//terrainheight is any number between 0 and 5 (inclusive)
				int terrainHeight = rand.nextInt(6);
				//Create a new terrain
				terrain[i][j] = new Node(terrainHeight,"Sand");
			}
		}
	}
	/**
	 * Returns the node for a given co-ordinate
	 * @param x co-ordinate
	 * @param y co-ordinate
	 * @return Node at the co-ordinate
	 */
	public Node getNode(int x, int y) {
		return terrain[x][y];
	}
	
	/**
	 * For Debugging purposes, prints out the terrain as a numerical grid in System.Out
	 */
	
	public void printTerrain() {
		//Loop through the terrain and print out the terrain heights to System.out
		for(int i = 0; i < gridX; i++) {
			for(int j = 0; j < gridY; j++) {
				System.out.print(terrain[i][j].getTerrainheight() + " ");
			}
			System.out.println("");
		}
	}
	
	/**
	 * 
	 * @return The max X grid size
	 */
	public int getGridX() {
		return this.gridX;
	}
	
	/**
	 * 
	 * @return The max Y grid size
	 */
	public int getGridY() {
		return this.gridY;
	}
	
	/**
	 * 
	 * @return The diameter of each node.
	 */
	public float getNodeDiameter() {
		return this.nodeDiameter;
	}
	
	/**
	 * 
	 * @param x - X Coordinate
	 * @param y - Y Coordinate
	 * @return Returns the node for which that Coordinate is inside
	 */
	public Node nodeFromCoordinate(float x, float y) {
		x = x / this.nodeDiameter;
		y = y / this.nodeDiameter;
		
		int tempx = (int)x;
		int tempy = (int)y;
		
		return this.terrain[tempx][tempy];
	}
}