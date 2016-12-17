import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	
	private MinPQ<Node> pq; 
	private MinPQ<Node> pq_twin;
	private Node goal = null;

	private class Node implements Comparable<Node>{
		private Board board;
		private int moves;
		private Node previous;
		
		public Node (Board board, int moves, Node previous) {
			this.board = board;
			this.moves = moves;
			this.previous = previous;
		}

		public int priority() {
			return board.manhattan() + moves;
		}
		
		public int compareTo(Node that) {
			return this.priority() - that.priority();
		}
	}
	
	public Solver(Board initial) {
		
		if (initial == null) throw new NullPointerException();
		
		pq 		= new MinPQ<Node>();
		pq_twin = new MinPQ<Node>();
		
		pq.insert(new Node(initial, 0, null));
		pq_twin.insert(new Node(initial.twin(), 0, null));
		
		while (!pq.isEmpty()) {
			
			Node top 		= pq.delMin();
			Node top_twin 	= pq_twin.delMin();
			
			if (top_twin.board.isGoal()) {
				break;
			}
			
			if (top.board.isGoal()) {
				goal = top;
				break;
			}
			
			addNeighbors(top, pq);
			addNeighbors(top_twin, pq_twin);
		}
		
	}
	
	private void addNeighbors(Node node, MinPQ<Node> pq) {
		for (Board board : node.board.neighbors())
			if (node.previous == null || !board.equals(node.previous.board) ) 
				pq.insert(new Node (board, node.moves + 1, node));
	}

	public boolean isSolvable() {
		return goal != null;
	}
	
	public int moves() {
		if (!isSolvable())
			return -1;
		return goal.moves;
	}
	
	public Iterable<Board> solution() {
		
		if (!isSolvable())	return null;
		
		Stack<Board> path = new Stack<Board>();
		Node current = goal;

		while(current.previous != null) {
			path.push(current.board);
			current = current.previous;
		}
		path.push(current.board);
		
		return path;
	}
	
	public Iterable<Node> solutionNode() {
		
		if (!isSolvable())	return null;
		
		Stack<Node> path = new Stack<Node>();
		Node current = goal;

		while(current.previous != null) {
			path.push(current);
			current = current.previous;
		}
		path.push(current);
		
		return path;
	}
}
