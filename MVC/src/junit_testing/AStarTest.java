package junit_testing;

import static org.junit.Assert.*;
import org.junit.Test;
import algorithms.demo.Maze3dSearchable;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.search.AStar;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Solution;

public class AStarTest {

	@Test
	public void testSearchSearchableHeuristic() {
		AStar astar = new AStar();
		Solution solution = astar.search(new Maze3dSearchable(new MyMaze3dGenerator(3, 3, 3).generate()), new MazeManhattanDistance());
		assertNotNull(solution);
	}

}
