package project.game;

/**
 * DIRECTION ENUM:
 * Starts at NORTH then proceeds clockwise through the other compass directions
 */
public enum Direction {
	NORTH, EAST, SOUTH, WEST;
	
	/**
	 * @return The Direction 90 degrees clockwise of the current direction
	 */
	public Direction clockwise(){
		int newDir = (ordinal()+1)%4;
		return Direction.values()[newDir];
	}
	
	/**
	 * @return The Direction 90 degrees anticlockwise of the current direction
	 */
	public Direction anticlockwise(){
		int newDir = ordinal()==0?3:ordinal()-1;
		return Direction.values()[newDir];		
	}
	
	/**
	 * @return The Direction 180 degrees from the current direction
	 */
	public Direction opposite(){
		int newDir = (ordinal()+2)%4;
		return Direction.values()[newDir];		
	}
}
