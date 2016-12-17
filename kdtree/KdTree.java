import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	
	private Node root;
	
	private class Node {
		private Point2D point;
		private RectHV rect;
		private Node left, right;
		private int depth;
		private int size;
		public Node(Point2D point, RectHV rect, int depth) {
			this.point = point;
			this.rect = rect;
			this.left = null;
			this.right = null;
			this.depth = depth;
			this.size = 1;
		}
	}
	
	public KdTree() {
		root = null;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public int size() {
		return size(root);
	}
	
	private int size(Node x) {
		if (x == null) return 0;
		else return x.size;
	}
	
	public void insert(Point2D p) {
		if (p == null)  throw new NullPointerException();
		if (contains(p)) return;
		if (isEmpty()) {
			root = new Node(p, new RectHV(0,0,1,1), 0); 
		} else
			insert(root, null, p);
	}
	
	private Node insert(Node x, Node previous, Point2D p) {

		if (x == null) return new Node(p, rect(previous, p), previous.depth + 1);
		int cmp = compare(x, p);
		if		(cmp < 0) 	x.left	 = insert(x.left,  x, p);
		else if (cmp >= 0) 	x.right	 = insert(x.right, x, p);
		
		x.size = 1 + size(x.left) + size(x.right);
		
		return x;
	}
	
	private int compare(Node x, Point2D p) {
		Comparator<Point2D> comparator = divideX(x) ? Point2D.X_ORDER : Point2D.Y_ORDER ;
		return comparator.compare(p, x.point);
	}
	
	private boolean divideX(Node x) {
		return (x.depth & 1) == 0;
	}
	
	private RectHV rect(Node x, Point2D p) {
		int cmp = compare(x, p);
		if (!divideX(x) && cmp <  0 )	return new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.point.y());
		if (!divideX(x) && cmp >= 0 )	return new RectHV(x.rect.xmin(), x.point.y(),   x.rect.xmax(), x.rect.ymax());
		if ( divideX(x) && cmp <  0 ) 	return new RectHV(x.rect.xmin(), x.rect.ymin(), x.point.x(),   x.rect.ymax());
		if ( divideX(x) && cmp >= 0 ) 	return new RectHV(x.point.x()  , x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
		return null;
	}
	
	public boolean contains(Point2D p) {
		if (p == null)  throw new NullPointerException();
		return contains(root, p);
	}
	
	private boolean contains(Node x, Point2D p) {
		if (x == null) 			return false;
		if (x.point.equals(p)) 	return true;
		int cmp = compare(x, p);
		if		(cmp < 0 ) 	return contains(x.left, p);
		else if (cmp >= 0) 	return contains(x.right, p);
		else 			   	return false;
	}
	
	public void draw() {

		for (Node node : collectNode(root)) {
			StdDraw.setPenRadius(0.005);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.point(node.point.x(), node.point.y());
			StdDraw.setPenRadius(0.001);
			if ( divideX(node) ) {
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
			} else {

				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
			}
			
		}
	}
	
	private Iterable<Node> collectNode(Node x) {
		Queue<Node> queue = new Queue<Node>(); 
        collectNode(x, queue);
        return queue;
	}
	
	private void collectNode(Node x, Queue<Node> queue) {
        if (x == null) return; 
        queue.enqueue(x); 
        collectNode(x.left,  queue);
        collectNode(x.right, queue); 
	}

	public Iterable<Point2D> range(RectHV rect) {
		
		if (rect == null) throw new IllegalArgumentException();
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		if (isEmpty()) return points;
		Queue<Node> q = new Queue<Node>();
		q.enqueue(root);
		while (!q.isEmpty()) {
			Node node = q.dequeue();
			if (rect.contains(node.point)) points.add(node.point);
			enqueueIntersectdNode(node.left, rect, q);
			enqueueIntersectdNode(node.right, rect, q);
		}
		
		return points;
	}
	
	private void enqueueIntersectdNode(Node x, RectHV rect, Queue<Node> q) {
		if (x == null) return;
		if (rect.intersects(x.rect)) q.enqueue(x);
	}
	
	
	
	public Point2D nearest(Point2D point) {
		
		if (point == null) throw new IllegalArgumentException();
		if (isEmpty()) return null;
		
		double distSquared = Double.POSITIVE_INFINITY;
		Point2D nearestPoint = null;
		Queue<Node> q = new Queue<Node>();
		
		q.enqueue(root);
		
		while (!q.isEmpty()) {
			Node node = q.dequeue();
			double newDistSquared = node.point.distanceSquaredTo(point);
			if (newDistSquared < distSquared ) {
				nearestPoint = node.point;
				distSquared = newDistSquared;
			}
			
			enqueueNearestNode(node.left, point, distSquared, q);
			enqueueNearestNode(node.right, point, distSquared, q);
			
		}
		
		return nearestPoint;
	}

	private void enqueueNearestNode(Node x, Point2D point, double dist,  Queue<Node> q) {
		if (x == null) return;
		if (x.rect.distanceSquaredTo(point) < dist) q.enqueue(x);
	}
	
}
