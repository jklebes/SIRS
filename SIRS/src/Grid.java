import java.util.Random;

public class Grid {
		int[][] grid;
		
		//keeps track of times since last status change
		double[][] timesgrid;
		
		private int width;
		private int height;
		private Random random = new Random();

		public Grid(int width, int height, double initS, double initI, double initM){
			this.width = width;
			this.height=height;
			this.setGrid(new int[height][width]);
			this.timesgrid= new double[height][width];
			fillRandom(initS, initI, initM);
			resetAllTimes();
		}
		
		//constructor to make copy
		public Grid(Grid grid){
			this.width = grid.width;
			this.height=grid.height;
			this.setGrid(grid.getGrid());
			this.timesgrid= grid.timesgrid;
		}


		void resetAllTimes() {
			for (int i=0; i < height; i++){
				for (int j=0; j < width; j++){
					timesgrid[i][j]=0;
					}}
		}


		void fillRandom(double SPercentage, double Ipercentage, double MPercentage){
			double r;
			for (int i=0; i < height; i++){
				for (int j=0; j < width; j++){
					r=random.nextDouble();
					if (r<MPercentage){
						SetStatus(i,j,3);
					}
					else if (r < (MPercentage+(1-MPercentage)*SPercentage)){
						SetStatus(i,j,0);}
					else if (r < (MPercentage+(1-MPercentage)*(SPercentage+Ipercentage))){
						SetStatus(i,j,1);
					}
					else{SetStatus(i,j,2);
					}
				}
			}
		}

		public void SetStatus(int i, int j,int status){
			if (checkValidStatus(status)){getGrid()[i][j]=status;}
			else{System.out.println("cannot set status to "+ status+" , proceeding with 0");
					getGrid()[i][j]=0;}
		}

		int getStatus(int i, int j){
			//correct for periodic boundary
			if (i==-1){i = height-1;}
			else if (i== height){ i=0;}
			if (j==-1){j= width-1;}
			else if (j== width){ j= 0;}
			
			//check valid status
			if (checkValidStatus(getGrid()[i][j])){return getGrid()[i][j];}
			else{System.out.println("tried to get invalid status "+ getGrid()[i][j] + " at "+ i+", "+j + 
					" , proceeding with susceptible");
			return 0;}
		}

		private boolean checkValidStatus(int status){
			if (status ==  0|| status == 1|| status==2 || status==3){return true;}
			else {return false;}
		}

		void Infect(int i, int j) {
			SetStatus(i,j,1);
			//System.out.println("changed value "+i+ " "+j );
		}
		void Recover(int i, int j) {
			SetStatus(i,j,2);
			//System.out.println("changed value "+i+ " "+j );
		}
		void Susceptible(int i, int j) {
			SetStatus(i,j,0);
			//System.out.println("changed value "+i+ " "+j );
		}

		int InfectedNeighborCount(int i, int j) {
			int infectedcount=0;
			if (getStatus(i, j+1)==1){
				infectedcount+=1;
			}
			if (getStatus(i, j-1)==1){
				infectedcount+=1;
			}
			if (getStatus(i+1, j)==1){
				infectedcount+=1;
			}
			if (getStatus(i-1, j)==1){
				infectedcount+=1;
			}
			return infectedcount;
		}


		public int getwidth() {
			return width;
		}

		public int getheight() {
			return height;
		}


		public void ResetTime(int i, int j) {
			this.timesgrid[i][j]=0;
		}


		public void addTime(double stepTimeValue) {
			for (int i=0; i < height; i++){
				for (int j=0; j < width; j++){
					timesgrid[i][j]+= stepTimeValue;
				}
			}
		}


		public double getTime(int i, int j) {
			return this.timesgrid[i][j];
		}

		public int[][] getGrid() {
			return grid;
		}

		public void setGrid(int[][] grid) {
			this.grid = grid;
		}

		public double[][] getTimeGrid() {
			return timesgrid;
		}
		
		public void setTimeGrid(double[][] grid) {
			this.timesgrid = grid;
		}


}
