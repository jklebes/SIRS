import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class RunSIRS{
static Stats stats = new Stats();
static String filename = "infile.txt";

public static void main(String[] args) throws IOException, InterruptedException{

	String line;
	int equilsteps= 1000;
	int runsteps= 50000;
	int repeats = 1;
	int width = 100;
	int height = 100;
	SIRSFrame f;
	
	double InitialS= .33333;
	double InitialI= .33333;
	double InitialM =0;
	double p1from = 0;
	double p1interval = .01;
	double p1to = 1;
	double p3from = 0;
	double p3interval = .01;
	double p3to = 1;
	double p2=.5;
	double infectiontime=0;
	double recoverytime=0;
double Mfrom =0;
double Mto=1;
double Minterval=.05;
	//file reading
	FileReader fr = new FileReader(filename);
	BufferedReader br = new BufferedReader(fr);
	while ((line = br.readLine()) != null){
		String[] lineelements = line.split(" ");
		String name = lineelements[0];
		String value = lineelements[1];
		if (name.equals("InitialS")){
			InitialS = Double.valueOf(value);
		}
		else if (name.equals("InitialI")){
			InitialI = Double.valueOf(value);
		}
	
	else if (name.equals("InitialM")){
		InitialM = Double.valueOf(value);
	}
		else if (name.equals("p1")){
			p1from=Double.valueOf(value);
			p1interval = 1;
			p1to = 1;
		}
		else if (name.equals("p2")){
			p2=Double.valueOf(value);
		}
		else if (name.equals("p3")){
			p3from=Double.valueOf(value);
			p3interval=1;
			p3to = 1;
		}
		else if (name.equals("runsteps")){
			runsteps=Integer.valueOf(value);
		}
		else if (name.equals("repeats")){
			repeats=Integer.valueOf(value);
		}
		else if (name.equals("width")){
			width=Integer.valueOf(value);
		}
		else if (name.equals("height")){
			height=Integer.valueOf(value);
		}
	}

	//initialize and run
	Algorithm a = new Algorithm(width, height, InitialS, InitialI, InitialM, infectiontime, recoverytime, p1from, p2, p3from);
	f = new SIRSFrame(a.grid);
	f.setTitle("with initial susceptible " + InitialS + " and initial infected " + InitialI);
	runSIRS(a, f, equilsteps, runsteps, repeats, p1from, p1interval, p1to, p3from, p3interval, p3to);
}

public static void runSIRS(Algorithm a , SIRSFrame f, int equilsteps, int runsteps, int repeats, double p1from, double p1interval, double p1to, double p3from, double p3interval, double p3to) throws IOException, InterruptedException{
	File file = new File("SIRS100x100M.txt");

	if (!file.exists()) {
		file.createNewFile();
	}
	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
	String line1 = "# run for "+equilsteps + " equilsteps, " +runsteps+" steps, averages of "+ repeats + " repeats";
	String line1b;
	line1b= "#p1 p3 <I>/N";
	bw.write(line1);
	bw.newLine();
	bw.write(line1b);
	String line;
	for (int p1loop = 0; (p1from + (double)p1loop*p1interval) <= p1to; p1loop++){
		for (int p3loop = 0; (p3from + (double)p3loop*p3interval)<= p3to; p3loop++){
		double p1 = p1from + (double)p1loop*p1interval;
		double p3= p3from + (double)p3loop*p3interval;
		a.setp1(p1);
		//a.fillRandom(M);
		a.setp3(p3);
		double[] Is = new double[runsteps];
		//run simulations(s) for this p1, p3
		for (int n = 0; n < repeats; n++){
			System.out.println("in repeat "+ n);
			a.reset();
			f.setTitle("equilibrating, p1 " + p1 +",p3 " +p3);
			for (int j = 0; j < equilsteps; j++){
				if (a.getTotalI() == 0){
					Is[j]=0;
					break;
					}
				a.updateGridRandom();
				//a.singleUpdate();
				f.step();
			}
			f.setTitle("running, p1 " + p1 +",p3 " +p3);
			for (int j = 0; j < runsteps; j++){
				if (a.getTotalI() == 0){
				Is[j]=0;
				break;
				}
				a.updateGridRandom();
				f.step();

				//save I at this time in array
				Is[j]=(double)a.getTotalI();

			}
			//calculate values and write to file
			double avgIfraction = stats.avg(Is) / ((double)a.getwidth()*(double)a.getheight());
			line = p1 + " " + p3 +" " + avgIfraction + "\n";
			bw.write(line);
		}
		}
	}
bw.close();
}


}

