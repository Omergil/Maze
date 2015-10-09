package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import algorithms.mazeGenerators.Position;

/**
 * Canvas to display a 3d maze game.
 */
public class GUIMazeDisplayer3d extends GUIMazeDisplayer {

	Image wall = new Image(getShell().getDisplay(), "Resources\\brick.png");
    Image character = new Image(getShell().getDisplay(), "Resources\\character.png");
    Image goal = new Image(getShell().getDisplay(), "Resources\\goal.png");
    Image finish = new Image(getShell().getDisplay(), "Resources\\finish.png");
    Image lead = new Image(getShell().getDisplay(), "Resources\\lead.png");
	final Color path = new Color(null, 255, 255, 255);
	final Color background = new Color(null, 191, 191, 191);
	ArrayList<Position> solution = new ArrayList<Position>();
	Position hint = new Position(-1, -1, -1);
	
	/**
	 * Constructor to set parent composite and style, and paint the maze.
	 * <p>
	 * If no maze was set, the character image will be shown on the canvas.<br>
	 * If a maze was set, it will paint 3 floors of it using paintMaze method.
	 * @param parent
	 * @param style
	 */
	GUIMazeDisplayer3d(Composite parent, int style)
	{
		super(parent, style);
		setBackground(background);
		
		addPaintListener(new PaintListener()
		{
			@Override
			public void paintControl(PaintEvent e)
			{

				if (maze == null)
				{
					e.gc.drawImage(character, 0, 0, character.getBounds().width, character.getBounds().height, getSize().x/3, getSize().y/3, getSize().x/3, getSize().y/3);
					return;
				}
				
				e.gc.setForeground(new Color(null, 0,0,0));
				e.gc.setBackground(new Color(null, 0,0,0));
				
				paintMaze(e, characterPosition.getZ() + 1, getSize().y/12);
				paintMaze(e, characterPosition.getZ(), (getSize().y/4)+(getSize().y/6));
				paintMaze(e, characterPosition.getZ() - 1, (getSize().y/4)*3);
			}
		});
	}

	/**
	 * Paints a maze on the canvas, according to its data.
	 * @param e
	 * @param floor
	 * @param mazeHeight
	 */
	public void paintMaze(PaintEvent e, int floor, int mazeHeight)
	{	
		e.gc.setBackground(path);
		
		int windowWidth = getSize().x;
		int windowHeight = getSize().y;
		
		int cellWidth = windowWidth/maze.getMaze3d()[0].length;
		int cellHeight = windowHeight/maze.getMaze3d().length/4;
		
		if ((floor == -1) || (floor == maze.getMaze3d()[0][0].length))
		{
			for (int i = maze.getMaze3d().length - 1; i >= 0 ;i--)
			{
				for (int j = 0; j < maze.getMaze3d()[0].length;j++)
				{
					int x = j*cellWidth;
					int y = i*cellHeight;
					e.gc.drawImage(wall, 0, 0, wall.getBounds().width, wall.getBounds().height, x, y+(mazeHeight), cellWidth, cellHeight);
				}
			}
		}
		else
		{
			for (int i = maze.getMaze3d().length - 1; i >= 0 ;i--)
			{
				for (int j = 0; j < maze.getMaze3d()[0].length;j++)
				{
					int x = j*cellWidth;
					int y = i*cellHeight;
					if(maze.getMaze3d()[i][j][floor] == 1)
					{
						e.gc.drawImage(wall, 0, 0, wall.getBounds().width, wall.getBounds().height, x, y+(mazeHeight), cellWidth, cellHeight);
					}
					if(maze.getMaze3d()[i][j][floor] == 0)
					{
						e.gc.fillRectangle(x,y+(mazeHeight),cellWidth,cellHeight);
					}
				}
			}
		}
		
		// Reset hint
		if (characterPosition.compareTo(hint) == 0)
		{
			displayedHint = false;
			hint.setX(-1);
		}
		
		// Display solution
		if (isDisplayedSolution())
		{
			for (Position p : solution)
			{
				// Paint goal position
				if (p.getZ() == characterPosition.getZ())
				{
					e.gc.drawImage(lead, 0, 0, lead.getBounds().width, lead.getBounds().height, cellWidth * p.getX(), cellHeight * p.getY()+((getSize().y/4)+(getSize().y/6)), cellWidth, cellHeight);
				}
				else if (p.getZ() == (characterPosition.getZ() + 1))
				{
					e.gc.drawImage(lead, 0, 0, lead.getBounds().width, lead.getBounds().height, cellWidth * p.getX(), cellHeight * p.getY()+(getSize().y/12), cellWidth, cellHeight);
				}
				else if (p.getZ() == (characterPosition.getZ() - 1))
				{
					e.gc.drawImage(lead, 0, 0, lead.getBounds().width, lead.getBounds().height, cellWidth * p.getX(), cellHeight * p.getY()+(getSize().y/4)*3, cellWidth, cellHeight);
				}
			}
		}
		
		// Display hint
		if (isDisplayedHint())
		{
			if (hint.getX() == -1)
			{
				for (int i = solution.size() - 1; i >= 0; i--)
				{
					hint = solution.get(i);
					if (hint.getZ() == (characterPosition.getZ() + 1))
					{
						break;
					}
					else if (solution.get(i).getZ() == characterPosition.getZ())
					{
						break;
					}
				}
			}
			else
			{
				// Paint hint position
				if (hint.getZ() == (characterPosition.getZ() + 1))
				{
					e.gc.drawImage(lead, 0, 0, lead.getBounds().width, lead.getBounds().height, cellWidth * hint.getX(), cellHeight * hint.getY()+(getSize().y/12), cellWidth, cellHeight);
				}
				else if (hint.getZ() == characterPosition.getZ())
				{
					e.gc.drawImage(lead, 0, 0, lead.getBounds().width, lead.getBounds().height, cellWidth * hint.getX(), cellHeight * hint.getY()+((getSize().y/4)+(getSize().y/6)), cellWidth, cellHeight);
				}
			}
		}		
		
		if ((characterPosition.compareTo(maze.getGoalPosition()) == 0) && (floor == characterPosition.getZ() - 1))
		{
			e.gc.drawImage(finish,0,0,finish.getBounds().width,finish.getBounds().height,cellWidth*characterPosition.getX(), cellHeight*characterPosition.getY()+(getSize().y/4)+(getSize().y/6), cellWidth, cellHeight);
		}
		
		if (hasWon)
		{
			return;
		}
		
		if ((characterPosition.compareTo(maze.getGoalPosition()) == 0) && (floor == characterPosition.getZ() - 1)
				&& (hasWon == false))
		{
			new GUIMessageBox(getShell(), "You won!");
			hasWon = true;
			return;
		}
		
		// Paint character image
	    e.gc.drawImage(character,0,0,character.getBounds().width,character.getBounds().height,cellWidth*characterPosition.getX(), cellHeight*characterPosition.getY()+(getSize().y/4)+(getSize().y/6), cellWidth, cellHeight);
	    
		// Paint goal position
		if (maze.getGoalPosition().getZ() == characterPosition.getZ())
		{
			e.gc.drawImage(goal, 0, 0, goal.getBounds().width, goal.getBounds().height, cellWidth * maze.getGoalPosition().getX(), cellHeight * maze.getGoalPosition().getY()+((getSize().y/4)+(getSize().y/6)), cellWidth, cellHeight);
		}
		else if (maze.getGoalPosition().getZ() == (characterPosition.getZ() + 1))
		{
			e.gc.drawImage(goal, 0, 0, goal.getBounds().width, goal.getBounds().height, cellWidth * maze.getGoalPosition().getX(), cellHeight * maze.getGoalPosition().getY()+(getSize().y/12), cellWidth, cellHeight);
		}
	}

	/**
	 * Sets the character position.
	 */
	@Override
	public void setCharacterPosition(Position position) {
		characterPosition = position;
	}

	private void moveCharacter(Position p){
		setCharacterPosition(p);
		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				redraw();
			}
		});
	}

	/**
	 * Method to be ran when a player presses the left key.<br>
	 * Moves the character to the left.
	 */
	@Override
	public void moveLeft() {
		String[] moves;
		boolean isPossible = false;
		Position p = getCharacterPosition();
		int x, y, z;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if left is possible
			if (moves[i] == "Left")
				isPossible = true;
		}
		if (isPossible){
			y = p.getY();
			x = p.getX()-1;
			z = p.getZ();
			p.setY(y);
			p.setX(x);
			p.setZ(z);
			moveCharacter(p);
		}
	}
	
	/**
	 * Method to be ran when a player presses the right key.<br>
	 * Moves the character to the right.
	 */
	@Override
	public void moveRight() {
		String[] moves;
		boolean isPossible = false;
		Position p = getCharacterPosition();
		int x, y, z;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if right is possible
			if (moves[i] == "Right")
				isPossible = true;
		}
		if (isPossible){
			y = p.getY();
			x = p.getX()+1;
			z = p.getZ();
			p.setY(y);
			p.setX(x);
			p.setZ(z);
			moveCharacter(p);
		}

	}

	/**
	 * Method to be ran when a player presses the up key.<br>
	 * Moves the character forwards.
	 */
	@Override
	public void moveForwards() {
		String[] moves;
		boolean isPossible = false;
		Position p = getCharacterPosition();
		int x, y, z;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if forwards is possible
			if (moves[i] == "Forwards")
				isPossible = true;
		}
		if (isPossible){
			y = p.getY()+1;
			x = p.getX();
			z = p.getZ();
			p.setY(y);
			p.setX(x);
			p.setZ(z);
			moveCharacter(p);
		}

	}

	/**
	 * Method to be ran when a player presses the down key.<br>
	 * Moves the character backwards.
	 */
	@Override
	public void moveBackwards() {
		String[] moves;
		boolean isPossible = false;
		Position p = getCharacterPosition();
		int x, y, z;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if backwards is possible
			if (moves[i] == "Backwards")
				isPossible = true;
		}
		if (isPossible){
			y = p.getY()-1;
			x = p.getX();
			z = p.getZ();
			p.setY(y);
			p.setX(x);
			p.setZ(z);
			moveCharacter(p);
		}

	}
	
	/**
	 * Method to be ran when a player presses the pageUp key.<br>
	 * Moves the character to the upper floor.
	 */
	public void moveUp() {
		String[] moves;
		boolean isPossible = false;
		Position p = getCharacterPosition();
		int x, y, z;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if up is possible
			if (moves[i] == "Up")
				isPossible = true;
		}
		if (isPossible){
			y = p.getY();
			x = p.getX();
			z = p.getZ()+1;
			p.setY(y);
			p.setX(x);
			p.setZ(z);
			moveCharacter(p);
		}
	}
	
	/**
	 * Method to be ran when a player presses the pageDown key.<br>
	 * Moves the character to the floor beneath.
	 */
	public void moveDown() {
		String[] moves;
		boolean isPossible = false;
		Position p = getCharacterPosition();
		int x, y, z;
		
		//get position possible moves
		moves = maze.getPossibleMoves(characterPosition);
		for (int i=0;i < moves.length; i++)
		{
			//check if down is possible
			if (moves[i] == "Down")
				isPossible = true;
		}
		if (isPossible){
			y = p.getY();
			x = p.getX();
			z = p.getZ()-1;
			p.setY(y);
			p.setX(x);
			p.setZ(z);
			moveCharacter(p);
		}
	}
	
	public void displaySolution(ArrayList<String> solutionStrings)
	{
		for (String step : solutionStrings)
		{
			Position tempPosition = new Position(0,0,0);
			List<String> coordinates = new ArrayList<String>(Arrays.asList(step.split(",")));
			tempPosition.setX((Integer.parseInt(coordinates.get(0))));
			tempPosition.setY((Integer.parseInt(coordinates.get(1))));
			tempPosition.setZ((Integer.parseInt(coordinates.get(2))));
			solution.add(tempPosition);
		}
		redraw();
	}
}
