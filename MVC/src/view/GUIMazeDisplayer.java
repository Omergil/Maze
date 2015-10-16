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
	boolean displayedSolution = false;
	boolean displayedHint = false;
	
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
		Position p = new Position(this.maze.getStartPosition().getX(), this.maze.getStartPosition().getY(), this.maze.getStartPosition().getZ());
		setCharacterPosition(p);
		hasWon = false;
		displayedSolution = false;
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
	 * Gets isDisplayedSolution
	 * @return isDisplayedSolution
	 */
	public boolean isDisplayedSolution() {
		return displayedSolution;
	}

	/**
	 * Sets displayedSolution
	 * @param isDisplayedSolution
	 */
	public void setDisplayedSolution(boolean displaySolution) {
		this.displayedSolution = displaySolution;
	}

	/**
	 * Gets displayedHint;
	 * @return displayedHint;
	 */
	public boolean isDisplayedHint() {
		return displayedHint;
	}

	/**
	 * Sets displayedHint
	 * @param displayedHint
	 */
	public void setDisplayedHint(boolean displayedHint) {
		this.displayedHint = displayedHint;
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
