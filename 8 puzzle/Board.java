import java.util.ArrayList;

public class Board {
	
	private int[][] blocks;
	private static final int BLANK = 0;
	
	public Board(int[][] blocks) {
		this.blocks = copy(blocks);
	}
	
	public int dimension() {
		return blocks.length;
	}
	
	public int[][] blocks() {
		return copy(blocks);
	}
	
	public int hamming() {
		
		int count = 0;
		
		for (int i = 0; i < blocks.length; i++)
			for (int j = 0; j < blocks.length; j++) {
				if (blocks[i][j] == BLANK) continue;
				if (blocks[i][j] != toIndex(i, j)) count++;
			}

		return count;
	}
	
	public int manhattan() {
		
		int count = 0;
		
		for (int i = 0; i < blocks.length; i++)
			for (int j = 0; j < blocks.length; j++) {
				if (blocks[i][j] == BLANK) continue;
				int n = blocks[i][j] - 1;
				int y = n / blocks.length;
				int x = n % blocks.length;
				count += abs(i-y) + abs(j-x);
			}			
		return count;
	}
	
	public boolean isGoal() {
		return hamming() == 0;
	}
	
    public Board twin() {
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length - 1; j++)
                if (blocks[i][j] != BLANK && blocks[i][j+1] != BLANK)
                    return exch(i, j, i, j+1);
		return null;
    }
	
	public boolean equals(Object y) {
		
		if (this == y) return true;
		if (y == null) return false;
		if (this.getClass() != y.getClass()) return false;
		
		Board that = (Board) y;
		
		if (this.dimension() != that.dimension()) return false;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) 
				if (that.blocks[i][j] != this.blocks[i][j])	return false;
		}
		return true;
	}
	
	public Iterable<Board> neighbors() {
		
		ArrayList<Board> neighborBoards = new ArrayList<Board>();
		
		int blankX = -1;
		int blankY = -1;

		Loop1 :for (int i = 0; i < blocks.length; i++) {
					for (int j = 0; j < blocks.length; j++) {
						if (blocks[i][j] == BLANK ) {
							blankX = j;
							blankY = i;
							break Loop1;
							
						}
					}
				}
			
			if (blankX > 0) 				neighborBoards.add(exch(blankY, blankX, blankY, blankX - 1));
			if (blankX < blocks.length - 1) neighborBoards.add(exch(blankY, blankX + 1, blankY, blankX));
			if (blankY > 0) 				neighborBoards.add(exch(blankY, blankX, blankY - 1, blankX));
			if (blankY < blocks.length - 1) neighborBoards.add(exch(blankY, blankX, blankY + 1, blankX));

		return neighborBoards;
	}
	
	public String toString() {
		
		String NEWLINE = "\n";
		StringBuilder sb = new StringBuilder();
		sb.append(dimension()).append(NEWLINE);
		
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				sb.append(blocks[i][j] + " ");
			}
			sb.append(NEWLINE);
		}
		
		return sb.toString();
	}
	
	
	/// helper funcitons
	
	private int toIndex(int i, int j) {
		return blocks.length * i + (j + 1);
	}
	
	private int abs(int n) {
		return (n >= 0 ? n :-n);
	}
	
	private int[][] copy (int[][] original) {
		
		int[][] copy = new int[original.length][];
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy[i].length; j++)
				copy[i][j] = original[i][j];
		}
		
		return copy;
	}
	
	private Board exch(int i, int j, int m, int n) {
		
		int[][] copy = copy(blocks);
		int swap = copy[i][j];
		copy[i][j] = copy[m][n];
		copy[m][n] = swap;
		
		return new Board(copy);
		
	}
	

	public static void main(String[] args) {
	    In in = new In("Input\\8puzzle\\puzzle3x3-00.txt");
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);
	    System.out.println(initial);
	    
	    Object obj = new Object();
	    System.out.println( initial.equals(obj));
	}
}
