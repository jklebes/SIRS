import java.util.Random;


public class Algorithm {
	Grid grid;
	Grid initialgrid;
	private double stepTimeValue = 1 ;
	private double durationOfInfection;
	private double durationOfRecovery;
	double p1=1;
	double p2=1;
	double p3=1;
	private Random random = new Random();
	private double initS;
	private double initI;
	
public Algorithm(int height, int width, double initS, double initI, double recovertime, double susceptibletime, double p1, double p2, double p3){
	this.initS=initS;
	this.initI=initI;
	this.p1=p1;
	this.p2=p2;
	this.p3=p3;
	this.grid = new Grid(width, height, initS, initI);
	this.initialgrid = new Grid(width, height, initS, initI);
	this.durationOfInfection = recovertime;
	this.durationOfRecovery= susceptibletime;
}

public void makeCopy(){
	//make copy of state at beginning of step
		System.out.println("copy set up");

		for (int i = 0; i < grid.getheight(); i++){
			for (int j = 0; j < grid.getwidth(); j++){
		initialgrid.grid[i][j]=grid.getStatus(i,j);
		initialgrid.timesgrid[i][j]=grid.getTime(i,j);
			}
		}
}


//updates every scquare in one step, referencing state at beginning 
//with pi=1: deterministic but does not depend on order
public void updateGridSequential() throws InterruptedException{
	makeCopy();
	for (int i = 0; i < grid.getheight(); i++){
		for (int j = 0; j < grid.getwidth(); j++){
			//update square referencing past neighbors
			updateSquare(i,j,initialgrid);
			
		}
	}
}

public void singleUpdate(){
			int randomi = random.nextInt(grid.getheight());
			int randomj = random.nextInt(grid.getwidth());
			updateSquare(randomi,randomj,grid);
}


public void reset(){
	grid.resetAllTimes();
	grid.fillRandom(initS, initI);
}

//makes changes in this.grid referencing surrounding in given reference copy g
public void updateSquare(int i,int j, Grid reference){
	if (reference.getStatus(i, j)== 0){
		//System.out.println(reference.InfectedNeighborCount(i,j));
		if (reference.InfectedNeighborCount(i,j) >= 1){
			if (random.nextDouble() < p1){
			grid.Infect(i,j);
			grid.ResetTime(i,j);}
		}
	}
	else if (reference.getStatus(i, j)== 1){
		if (timeToRecoverCheck(i,j,reference)){
			if (random.nextDouble() < p2){
			grid.Recover(i, j);
			grid.ResetTime(i,j);
			}
		}
	}
	else if (reference.getStatus(i, j)== 2){
		if (timeToSusceptibleCheck(i,j,reference)){
			if (random.nextDouble() < p3){
			grid.Susceptible(i, j);
			grid.ResetTime(i,j);
			}
		}
	}

	grid.addTime(stepTimeValue);
}

 private boolean timeToRecoverCheck(int i,int j, Grid reference){
	 if (reference.getTime(i,j) >= this.durationOfInfection){return true;}
	 else{return false;}
}

 private boolean timeToSusceptibleCheck(int i,int j, Grid reference){
	 if (reference.getTime(i,j) >= this.durationOfRecovery){return true;}
	 else{return false;}
}


}
