package cea.stop.go;

import java.util.Random;

import processing.core.*;

public class DrowCea extends PApplet {

	//個体数
	int N = 30;
	//現在の世代数（1からスタート）
	int gen= 1;
	//最終世代数
	int gen_last= 100000;

	//double valueA[] = new double[N];
	//double valueB[] = new double[N];

	static Random r= new Random();

	//関数の値
	double a = 0.75;
	double b = 3.0;

	double std = 0.25;

	SolutionSpecies solsg = new SolutionSpecies(0, 0);
	CriteriaSpecies crisg = new CriteriaSpecies(0, 0);

	public void setup() {
		size(200, 200);
		stroke(255);
		frameRate(30);
	}

	public void draw() {
		int i=0;
		background(51);
		for(i=0;i<30;i++)
			ellipse(0,i*2,100,100);
		
	}


}