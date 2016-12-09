import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationNoBackWashTwoWQFUF {

	  private int size;
	  private int N;
	  private boolean [] opened;
	  private int top, bottom;
	  
	  private WeightedQuickUnionUF uf;
	  private WeightedQuickUnionUF uf_onlytop;
	
	  public PercolationNoBackWashTwoWQFUF(int N) {
	    if ( N <= 0 )
	      throw new IllegalArgumentException();
	  
	    this.N = N;
	    size = N * N + 2;
	    top = 0;
	    bottom = size - 1;
	   
	    opened = new boolean[size];
	    
	    for (int i = 0; i < size; i++) {
	      opened[i] = false;
	    }
	
	    uf = new WeightedQuickUnionUF(size); 
	    uf_onlytop = new WeightedQuickUnionUF(size);
	  }
  
	  public void open(int row, int col) {
	
	    if ( !isValidCell(row, col) ) 
	      throw new IndexOutOfBoundsException();

	    int pos = toIndex(row, col);
	    if ( !opened[pos] ) {
	      opened[pos] = true;
	      if ( row == 1 ) {
	        uf.union(top, pos);
	        uf_onlytop.union(top, pos);
	      }
	      
	      if ( row == N ) {
	        uf.union(bottom, pos);  
	      }
	      
	      union(row, col, row    , col + 1);
	      union(row, col, row    , col - 1);
	      union(row, col, row + 1, col    );
	      union(row, col, row - 1, col    );
	    }
	  }
	  
	  private void union(int thisRow, int thisCol, int thatRow, int thatCol) {
		  int thisPos = toIndex(thisRow, thisCol);
		  int thatPos = toIndex(thatRow, thatCol);
		  if (isValidCell(thatRow, thatCol) && opened[thatPos]) {
			  uf.union(thisPos, thatPos);
			  uf_onlytop.union(thisPos, thatPos);
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

	    return uf_onlytop.connected(top, toIndex(row, col));
	  }
	
	  public boolean percolates() {
	    return uf.connected(top, bottom);
	  }
	
	  private int toIndex(int row, int col) {
	    return (N * (row - 1) + col);
	  }
	
	  private boolean isValidCell(int row, int col) {
	    if (row < 1 || row > N || col < 1 || col > N)  return false;
	    return true;
	  }
}
