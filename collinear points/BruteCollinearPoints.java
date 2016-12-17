import java.util.ArrayList; 
import java.util.Arrays;

public class BruteCollinearPoints {

  private Point[] points;
  private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();
  
   public BruteCollinearPoints(Point[] input)  {

	   // finds all line segments containing 4 points
	   
	   if (input == null) throw new NullPointerException();
	   
	   int N = input.length;
	   points = Arrays.copyOf(input, N);
	   
	   for (int i = 0; i < N; i++) 
		   if (points[i] == null)	throw new NullPointerException();
 
	   Arrays.sort(points);
	   
	   for (int i = 0; i < N - 1; i++)
		   if (points[i].slopeTo(points[i+1]) == Double.NEGATIVE_INFINITY)	throw new IllegalArgumentException();
	   
	   for (int i = 0; i < N; i++)
		   for (int j = i+1; j < N; j++)
			   for (int k = j+1; k < N; k++)
				   for (int l = k+1; l < N; l++)
					   if ( isCollinear(points[i], points[j], points[k], points[l])  ) {
						   	lineSegments.add(new LineSegment(points[i], points[l]));
					   }	   
				   
   }
   
   private static boolean isCollinear(Point p, Point q, Point r, Point s) {
	  double slope = p.slopeTo(q);
	  return slope == p.slopeTo(r) && slope == p.slopeTo(s);
	  
   }
   
   public int numberOfSegments() {
	   return lineSegments.size();
   }
   
   public LineSegment[] segments() {
	   return lineSegments.toArray(new LineSegment[lineSegments.size()]);
   }
  
}
