import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class RunSIRS{
	
static String filename = "infile.txt";

public static void main(String[] args) throws IOException, InterruptedException{

	String line;
	int runsteps= 500000;
	int repeats = 10;
	int width = 100;
	int height = 100;
	Stats stats = new Stats();
	SIRSFrame f;
	
	double InitialS= .6;
	double InitialI= .1;
	double InitialM =0;
	double p1=.2;
	double p2=.3;
	double p3=.7;
	double infectiontime=0;
	double recoverytime=0;

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
			p1=Double.valueOf(value);
		}
		else if (name.equals("p2")){
			p2=Double.valueOf(value);
		}
		else if (name.equals("p3")){
			p3=Double.valueOf(value);
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
	Algorithm a = new Algorithm(width, height, InitialS, InitialI, InitialM, infectiontime, recoverytime, p1, p2, p3);
	f = new SIRSFrame(a.grid);
	f.setTitle("with initial susceptible " + InitialS + " and initial infected " + InitialI);
	runSIRS(a, f, runsteps, repeats);
}

public static void runSIRS(Algorithm a , SIRSFrame f, int runsteps, int repeats) throws IOException, InterruptedException{
	File file = new File("SIRS.txt");

	if (!file.exists()) {
		file.createNewFile();
	}
	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
	String line1 = "# run for "+ runsteps+" steps, averages of "+ repeats + "repeats";
	String line1b;
	line1b= "#T M S [sterror in M over repeats] [sterror in S over repeats] [sterror in M(t), avg over repeats] ";
	bw.write(line1);
	bw.newLine();
	bw.write(line1b);
	for (int n = 0; n < repeats; n++){
		System.out.println("in repeat "+ n);
		a.reset();
		for (int j = 0; j < runsteps; j++){
			a.updateGridSequential();
			//a.singleUpdate();
			f.step();
		}
	}
	bw.close();
}

}

