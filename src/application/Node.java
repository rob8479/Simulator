package application;
/**
 * @author Robert Sadler
 * Each node of the terrain has a height, and a texture type.
 */
public class Node {

	private int terrainheight;
	private String terrainType;
	
	//Constructor
	public Node(int newTerrainHeight , String newTerrainType) {
		this.terrainheight = newTerrainHeight;
		this.terrainType = newTerrainType; 
	}
	/**
	 * 0 is low, 5 is high
	 * @return 	Return the terrain height of this node (an Int from 0 up 5)
	 */
	public int getTerrainheight() {
		return this.terrainheight;
	}
	
	// WILL MOST LIKLEY CHANGE TO A TYPE SYSTEM, BUT FOR NOW, IT'S STRING BASE
	/**
	 * 
	 * @return Returns a String, Either "Sand", "Soil", "Carpet", "Concrete" or "Tile"
	 */
	public String terrainType() {
		return this.terrainType();
	}
}
