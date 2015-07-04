package cea.stop.go;

import java.util.Random;

public class Main {
	public static void main(String[] args){
		Species sg = new SolutionSpecies(10,10);
		System.out.println(sg.getParentNoByTournamentSelection());
	}
}
