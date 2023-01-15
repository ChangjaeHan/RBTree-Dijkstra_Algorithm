// --== CS400 File Header Information ==--
// Name: <Changjae Han>
// Email: <chan82@wisc.edu>
// Team: <AN>
// TA: <Yelun>
// Lecturer: <Gary>
// Notes to Grader: 

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the implementation of CS400Graph for the individual component of
 * Project Three: the implementation of Dijsktra's Shortest Path algorithm.
 */
public class GraphTest {

    private CS400Graph<String> graph;
    
    /**
     * Instantiate graph from last week's shortest path activity.
     */
    @BeforeEach
    public void createGraph() {
        graph = new CS400Graph<>();
        // insert vertices A-F
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");
        // insert edges from Week 11. Shortest Path Activity
        graph.insertEdge("A","B",6);
        graph.insertEdge("A","C",2);
        graph.insertEdge("A","D",5);
        graph.insertEdge("B","E",1);
        graph.insertEdge("B","C",2);
        graph.insertEdge("C","B",3);
        graph.insertEdge("C","F",1);
        graph.insertEdge("D","E",3);
        graph.insertEdge("E","A",4);
        graph.insertEdge("F","A",1);
        graph.insertEdge("F","D",1);
    }

    /**
     * Checks the distance/total weight cost from the vertex A to F.
     */
    @Test
    public void testPathCostAtoF() {
        assertTrue(graph.getPathCost("A", "F") == 3);
    }

    /**
     * Checks the distance/total weight cost from the vertex A to D.
     */
    @Test
    public void testPathCostAtoD() {
        assertTrue(graph.getPathCost("A", "D") == 4);
    }

    /**
     * Checks the ordered sequence of data within vertices from the vertex 
     * A to D.
     */
    @Test
    public void testPathAtoD() {
        assertTrue(graph.shortestPath("A", "D").toString().equals(
            "[A, C, F, D]"
        ));
    }

    /**
     * Checks the ordered sequence of data within vertices from the vertex 
     * A to F.
     */
    @Test
    public void testPathAtoF() {
        assertTrue(graph.shortestPath("A", "F").toString().equals(
            "[A, C, F]"
        ));
    }
 
    /**
     * Create extra 4 test regarding last week activity 
     *  and 1 test for correctness 
     * 
     * 1.testDistancePath()
     * 2.testSequencePath()
     * 3.testTotalPathCost()
     * 4.testPredecessor()
     * 
     * 5.testAll() //additional test in current graph + altered graph 
     */
    @Test
    public void testDistancePath() {
    	assertTrue(graph.getPathCost("B", "F") == 3);
    }
    
    @Test
    public void testSequencePath() {
    	 assertTrue(graph.shortestPath("B", "F").toString().equals(
    			 "[B, C, F]"
    	 ));
    }
    
    @Test
    public void testTotalPathCost() {
    	 assertTrue(graph.getPathCost("E", "D") == 8);
    }
    
    @Test
    public void testPredecessor() {
    	 assertTrue(graph.shortestPath("F", "B").get(2).toString().equals(
    			 "C"
    	 ));
    }
    
    @Test
    public void testAll() {
    	
    	 //additional test for current graph
    	 assertTrue(graph.shortestPath("E", "B").toString().equals(
    			 "[E, A, C, B]"
    	 ));
    	 assertTrue(graph.getPathCost("E", "B") == 9);
    	 
    	 //insert and delete extra edges from the graph and test
    	 //if DB weight3 is added and CD weight3 is deleted
    	 graph.insertEdge("D","B", 3);
    	 graph.removeEdge("C","B");
    	 assertTrue(graph.shortestPath("E", "B").toString().equals(
    			 "[E, A, B]"
    	 ));
    	 assertTrue(graph.getPathCost("E", "B") == 10);
    	 
    	 graph.removeEdge("D","B");
    	 graph.insertEdge("D","B", 1);
    	 
    	 assertTrue(graph.shortestPath("E", "B").toString().equals(
    			 "[E, A, C, F, D, B]"
    	 ));
    	 assertTrue(graph.getPathCost("E", "B") == 9);
 
    }  
}