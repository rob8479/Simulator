package application;

import java.util.ArrayList;
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
	 * Sets the specified gridReference as the givenNode
	 * @param newNode - The newNode for the Grid
	 * @param x - x co-ord in the grid
	 * @param y - y co-ord in the grid
	 * 
	 */
	public void setNode(Node newNode, int x, int y) {
		terrain[x][y] = newNode;
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
	
	
	public ArrayList<Wall> getWallsOfNode(double x, double y){
		
		x = x / this.nodeDiameter;
		y = y / this.nodeDiameter;
		int tempx = (int)x;
		int tempy = (int)y;
		
		ArrayList<Wall> tempWalls = new ArrayList<Wall>();		
		
		//Make sure the point given is still within the grid
		if(tempx < 0 || tempx > this.gridX || tempy < 0 || tempy > this.gridY) {
			return tempWalls;
		}
		
		//TODO - North and South don't work
		
		Node selectedNode = this.terrain[tempx][tempy];
		//Check North
		if(tempy != 0) {
			Node temp = this.terrain[tempx][tempy - 1];
			//If the difference between the north node and the current node is greater than 1, then it's a wall
			if(selectedNode.getTerrainheight()  - temp.getTerrainheight() > 1) {
				Wall newWall = new Wall(tempx * this.nodeDiameter, tempy * this.nodeDiameter, this.nodeDiameter, 2);
				tempWalls.add(newWall);
			}
		}
		//Check South
		if(tempy != this.gridY) {
			Node temp = this.terrain[tempx][tempy + 1];
			//If the difference between the south node and the current node is greater than 1, then it's a wall
			if(selectedNode.getTerrainheight()  - temp.getTerrainheight() > 1) {
				Wall newWall = new Wall((tempx * this.nodeDiameter), (tempy * this.nodeDiameter) + this.nodeDiameter, this.nodeDiameter, 2);
				tempWalls.add(newWall);
			}
		}
		
		//Check West
		if(tempx != 0) {
			Node temp = this.terrain[tempx - 1][tempy];
			//If the difference between the west node and the current node is greater than 1, then it's a wall
			if(selectedNode.getTerrainheight()  - temp.getTerrainheight() > 1) {
				Wall newWall = new Wall((tempx * this.nodeDiameter), (tempy * this.nodeDiameter), 2, this.nodeDiameter);
				tempWalls.add(newWall);
			}
		}
		
		//Check East
		if(tempx != this.gridX) {
			Node temp = this.terrain[tempx + 1][tempy];
			//If the difference between the west node and the current node is greater than 1, then it's a wall
			if(selectedNode.getTerrainheight() - temp.getTerrainheight() > 1) {
				Wall newWall = new Wall((tempx * this.nodeDiameter) + this.nodeDiameter, (tempy * this.nodeDiameter), 2, this.nodeDiameter);
				tempWalls.add(newWall);
			}
		}
		return tempWalls;
	}
		
}
