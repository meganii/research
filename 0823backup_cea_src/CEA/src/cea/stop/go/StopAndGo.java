package cea.stop.go;

import java.util.Random;

public abstract class StopAndGo {
	boolean isGo;
	double std1; double std2;
	abstract void selection();
	abstract void operation();
	
	double avgFitness(){
		Random r = new Random();
		return r.nextDouble();
	}
	
	void getisGo(){
		System.out.println(isGo);
	}
	
	StopAndGo(double std1, double std2){
		isGo = true;
		this.std1 = std1;
		this.std2 = std2;
	}
	
	void stopandgo(){
		if( isGo ){
			if( avgFitness() < std1 ){
				isGo = false;
			}
			else{
				isGo = true;
				selection();
			}
		}
		else{
			if( avgFitness() > std2 ){
				isGo = true;
				selection();
			}
			else{
				isGo = false;
			}
		}
		operation();
		getisGo();
	}
}
/*
class Solution extends StopAndGo {
	Solution(double std1, double std2) {
		super(std1, std2);
	}

	void selection(){
		System.out.println(std1);
	}
	
	void operation(){
		System.out.println(std1 + 10);
	}
	
	
}


class Criteria extends StopAndGo {
	Criteria(double std1, double std2) {
		super(std1, std2);
	}

	void selection(){
		System.out.println(std2);
	}
	
	void operation(){
		System.out.println(std2 + 100);
	}
}


class Operation {
	public static void main(String[] args) {
		StopAndGo solsg = new Solution(0.25, 0.5);
		//StopAndGo crisg = new Criteria(10, 30);
		for(int i=0;i<10;i++)
		solsg.stopandgo();
	}
}
*/