import java.util.ArrayList;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	
	private SET<Point2D> set;
	
	public PointSET() {
		set = new SET<Point2D>();
	}
	
	public boolean isEmpty() {
		return set.isEmpty();
	}
	
	public int size() {
		return set.size();
	}
	
	public void insert(Point2D p) {
		if (p == null)  throw new NullPointerException();
		set.add(p);
	}
	
	public boolean contains(Point2D p) {
		if (p == null)  throw new NullPointerException();
		return set.contains(p);
	}
	
	public void draw() {
		
		StdDraw.setPenRadius(0.02);
		
		for (Point2D p : set) {
			StdDraw.point(p.x(), p.y());
		}
		StdDraw.show();
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		
		for (Point2D that : set) {
			if (rect.contains(that))
				points.add(that);
		}
		return points;
	}
	
	public Point2D nearest(Point2D p) {
		
		double distSquared = Double.POSITIVE_INFINITY;
		
		Point2D nearestPoint = null;
		
		for (Point2D that : set) {
			if (distSquared > p.distanceSquaredTo(that) ) {
				distSquared = p.distanceSquaredTo(that);
				nearestPoint = that;
			}
				
		}
		return nearestPoint;
	}
	
	public static void main(String[] args) {
		Point2D p1 = new Point2D(0.5,1);
		Point2D p2 = new Point2D(0.5,0.5);
		PointSET set = new PointSET();
		set.insert(p1);
		set.insert(p2);
		System.out.println(set.size());
		set.draw();
	}
}
