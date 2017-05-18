
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class FastCollinearPoints {
	
	private static final int minPoints = 4;
	private Point[] points, original;
	private ArrayList<LineSegment> collinearPoints = new ArrayList<LineSegment>();
	private TreeMap<Point, HashSet<Double>> pointAndSlope = new TreeMap<Point, HashSet<Double>>();
	
	public FastCollinearPoints(Point[] input) {
		
	   if (input == null) throw new NullPointerException();
	   
	   int N = input.length;
	   
	   original = Arrays.copyOf(input, N);
	   Arrays.sort(original);
	   points = Arrays.copyOf(original, N);
	  
	   for (int i = 0; i < N; i++) 
		   if (points[i] == null)	throw new NullPointerException();
	   
	   for (int i = 0; i < N - 1; i++)
		   if (points[i].slopeTo(points[i+1]) == Double.NEGATIVE_INFINITY)	throw new IllegalArgumentException();
	   
	   findCollinearPoints();
			
	}
	
	private void findCollinearPoints() {
		
		int numOfPoints = 0;
		int N = points.length;
		
		ArrayList<Point[]> unfilteredLines = new ArrayList<Point[]>();

		for (int i = 0; i < N; i++) {

			Arrays.sort(points, original[i].slopeOrder());

			double[] slope = new double[N];

			for (int j = 0; j < N; j++) 
				slope[j] = original[i].slopeTo(points[j]);


			for (int first = 0, last = 0; last < N; first = last) {
				
				numOfPoints = 0;
				
				while (last < N && slope[first] == slope[last]) {
					last++;
				}
	
				numOfPoints = last - first + 1;
				
				if (numOfPoints >= minPoints) {
					
					Point[] pointsInLineSegment = new Point[numOfPoints];
					
					pointsInLineSegment[0] = original[i];
					
					for (int k = 0; k < numOfPoints - 1; k++) 
						pointsInLineSegment[k + 1] = points[first + k];

					Arrays.sort(pointsInLineSegment);
					
					Point startPoint = pointsInLineSegment[0];
					Point endPoint 	 = pointsInLineSegment[pointsInLineSegment.length - 1];
					
					unfilteredLines.add(new Point[] {startPoint, endPoint});

				}
			}
		}
		
		//  eliminate duplicated linesegments
		
		for (Point[] points : unfilteredLines) {
			
			Point startPoint = points[0];
			Point endPoint 	 = points[1];
			double slope = startPoint.slopeTo(endPoint);
			
			HashSet<Double> allSlopes = pointAndSlope.get(startPoint);
			
			if (allSlopes == null)
				allSlopes = new HashSet<Double>();
			
			if (!allSlopes.contains(slope)) {			
				allSlopes.add(slope);
				pointAndSlope.put(startPoint, allSlopes);
				collinearPoints.add(new LineSegment(startPoint, endPoint));
			} 
		}

		
	}
	

	public int numberOfSegments() {

		return segments().length;
	}
	
	public LineSegment[] segments() {
		
		return collinearPoints.toArray(new LineSegment[collinearPoints.size()]);
	}

	private void test() {
		
	}
	
}
