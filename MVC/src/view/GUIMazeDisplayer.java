package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

/**
 * Canvas to display a maze game.
 * <p>
 * To be extended by other canvas-classes. 
 */
public abstract class GUIMazeDisplayer extends Canvas {

	Maze3d maze = null;
	String mazeName = null;
	Position characterPosition;
	boolean hasWon = false;
	boolean displaySolution = false;
	
	/**
	 * Constructor to set parent composite and style.
	 * @param parent
	 * @param style
	 */
	GUIMazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}
	
	/**
	 * Set the maze to be used on the canvas.
	 * @param maze
	 */
	public void setMaze(Maze3d maze)
	{
		this.maze = maze;
		setCharacterPosition(this.maze.getStartPosition());
		hasWon = false;
	}
	
	/**
	 * Gets the maze name.
	 * @return String the maze name.
	 */
	public String getMazeName() {
		return mazeName;
	}

	/**
	 * Sets the maze name.
	 * @param mazeName
	 */
	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}

	/**
	 * Gets hasWon
	 * @return hasWon
	 */
	public boolean isHasWon() {
		return hasWon;
	}

	/**
	 * Sets hasWon
	 * @param hasWon
	 */
	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
	}
	
	/**
	 * Gets isDisplaySolution
	 * @return isDisplaySolution
	 */
	public boolean isDisplaySolution() {
		return displaySolution;
	}

	/**
	 * Sets displaySolution
	 * @param isDisplaySolution
	 */
	public void setDisplaySolution(boolean displaySolution) {
		this.displaySolution = displaySolution;
	}

	/**
	 * Gets the character position.
	 * @return Character position.
	 */
	public Position getCharacterPosition() {
		return characterPosition;
	}
	
	/**
	 * Sets the character position.
	 * @param position
	 */
	public abstract void setCharacterPosition(Position position);
	
	/**
	 * Moves the character to the left.
	 */
	public abstract void moveLeft();
	
	/**
	 * Moves the character to the right.
	 */
	public abstract void moveRight();
	
	
	/**
	 * Moves the character forwards.
	 */
	public abstract void moveForwards();
	
	
	/**
	 * Moves the character backwards.
	 */
	public abstract void moveBackwards();
	
}
