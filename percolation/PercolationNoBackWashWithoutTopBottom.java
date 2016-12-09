import java.util.Arrays;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationNoBackWashWithoutTopBottom {
	
	  private int N;
	  private boolean [] opened;
	  private boolean [] connToTop;
	  private boolean [] connToBottom;
	  private boolean  doesPercolate = false;
	  
	  private WeightedQuickUnionUF uf;


	  public PercolationNoBackWashWithoutTopBottom(int N) {
	    if ( N <= 0 )
	      throw new IllegalArgumentException();
	    
	    this.N = N;
	    int size = N * N + 1;  // 1-base index , 0 for dummy
	
	    opened = new boolean[size];
	    connToTop = new boolean[size];
	    connToBottom = new boolean[size];
	    
	    for (int i = 0; i < size; i++) {
	      opened[i] 	= false;
	      connToTop[i] 	= false;
	      connToTop[i] 	= false;
	    }
	    
	    uf = new WeightedQuickUnionUF(size); 
	  }
  
	  public void open(int row, int col) {
	
	    if ( !isValidCell(row, col) )
	      throw new IndexOutOfBoundsException();
	    
	    boolean bool_connToTop 	= false;
	    boolean bool_connToBottom = false;
	    
	    int pos = toIndex(row, col); // between 1 ~ N * N
	    int[] neighbors = getNeighborsIndex(row, col);
	    
	    if ( !opened[pos] ) {
	      opened[pos] = true;
	      
	      for (int neighbor : neighbors) {
	    	  if (opened[neighbor]) {
    		  
	    		  if (connToTop[uf.find(neighbor)])
	    			  bool_connToTop = true;
	    		  if (connToBottom[uf.find(neighbor)])
	    			  bool_connToBottom = true;
	    		  uf.union(pos, neighbor);
	    		  
	    	  }
	      }
	      
	      // update connection status after union with neighbor cells
	      int root = uf.find(pos);
	      connToTop[root] 	 = bool_connToTop		|| row == 1 ;
	      connToBottom[root] = bool_connToBottom 	|| row == N;
	      
	      if (connToTop[root] && connToBottom[root])
	    	  doesPercolate = true;
	    }
	  }

	
	  public boolean isOpen(int row, int col) {
	    if ( !isValidCell(row, col) ) 
	      throw new IndexOutOfBoundsException();
	    
	    return opened[toIndex(row,col)];
	  }
	  
	  public boolean isFull(int row, int col) {
	    if ( !isValidCell(row, col) ) 
	      throw new IndexOutOfBoundsException();
	    
	    return connToTop[uf.find(toIndex(row, col))];
	  }
	
	  public boolean percolates() {
		  return doesPercolate;
	  }
	
	  private int toIndex(int row, int col) {
	    return (N * (row - 1) + col);
	  }
	 
	  private boolean isValidCell(int row, int col) {
	    if (row < 1 || row > N || col < 1 || col > N)	return false;
	    return true;
	  }
	  
	  private int[] getNeighborsIndex(int row, int col) {
		  
	    if ( !isValidCell(row, col) ) 
	        throw new IndexOutOfBoundsException();
	
		  int pos = toIndex(row, col);
		  int [] delta = {-1, +1, -N, +N};
		  int [] tmp = new int[delta.length];
		  int [] copy;
		  
		  int i = 0;
		  int j = 0;
		  
		  if ( col > 1 )
			  tmp[j++] = pos + delta[i];
		  i++;
	
		  if ( col < N )
			  tmp[j++] = pos + delta[i];
		  i++;
		  
		  if ( row > 1 )
			  tmp[j++] = pos + delta[i];
		  i++;
	
		  if ( row < N )
			  tmp[j++] = pos + delta[i];
		  i++;
		  
		  copy = Arrays.copyOfRange(tmp, 0, j);
		  
		  return copy;
	  }
}
