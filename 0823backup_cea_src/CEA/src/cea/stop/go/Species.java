package cea.stop.go;

/**
 * 
 * 
 */

import java.lang.reflect.Array;
import java.util.Random;
import java.util.Arrays;

public abstract class Species {
	
	int N = 30;		//個体数
	/*
	int gen= 1;		//現在の世代数（1からスタート）
	int gen_last= 10000000;		//最終世代
	*/
	static Random r= new Random();
	double mut;		//突然変異率
	double std;		//閾値
	boolean isGo;
	double std1; double std2;	//stop&goの閾値
	
	double gene[] = new double [N];
	double gene_next[]= new double[N];
	int fitness[] = new int[N];
	
	int size = 5;	//トーナメントサイズ
	
	//double xrange = 5.12;	//xの範囲
	
	//コンストラクタによる初期化
	Species(double std1, double std2){
		isGo = true;	//State : GOからスタート
		this.std1 = std1;	
		this.std2 = std2;
		mut = 0.1;
		std = 0.25;
		
		for(int i=0;i<gene.length;i++){
			gene[i] = 0;
			fitness[i] = 0;
		}
	}
	
	//抽象メソッド　SolutionSpecies.java, CriteriaSpecies.javaで実装
	abstract void selection();
	abstract void operation();
	
	//平均適応度を求める。
	double getAvgFitness(){
		double sum =0;
		double avg =0;
		for(int i=0;i<fitness.length;i++){
			sum += fitness[i];
		}
		avg = sum /fitness.length;
		return avg;
	}
	
	//ステートがどうなってるかを調べる。
	void getisGo(){
		System.out.println(isGo);
	}
	
	/*
	//昔のStop$Go
	//Stop&Goのフレームワーク
	void stopandgo(double avg){
		//getisGo();					//test
		//System.out.println(avg);	//test
		if( isGo ){
			if( avg < std1 ){
				isGo = false;
			}
			else{
				isGo = true;
				selection();
			}
		}
		else{
			if( avg > std2 ){
				isGo = true;
				selection();
			}
			else{
				isGo = false;
			}
		}
		operation();
		
	}
	*/
	
	
	
	//改良中 stop&go
	void stopandgo(double avg){
		//getisGo();					//test
		//System.out.println(avg);	//test
		if( isGo ){
			if( avg <= std1 ){
				isGo = false;
			}
			else{
				isGo = true;
				selection();
				operation();
			}
			
			
			
		}
		else{
			if( avg > std2 ){
				isGo = true;
				selection();
				operation();
			}
			else{
				isGo = false;
			}
		}

		//operation();
	}
	
	
	
	//トーナメント選択を行って，子孫を作る個体の番号を返す．
	int getParentNoByTournamentSelection(){
		int size = 5;	//トーナメントサイズ;
		int parent[] = new int[size];
		//候補の個体の番号5つをランダムに選び、その中で一番よいものの番号を返す
		for(int i=0;i<parent.length;i++){		
			parent[i]= (int)(r.nextDouble()*gene.length);
		}
		double max = 0;
		int index = 0;
		max = fitness[parent[0]];
		for(int j=0;j<5;j++){
			if( fitness[parent[j]] > max ){
				max = fitness[parent[j]]; 
				index = j;
			}
		}
		return parent[index];
	}
	
	
	//降順に並び替え
	void sortFitnessTable(){
		int tmp[] = new int[fitness.length];
		Arrays.sort(fitness);
		int index =0;
		for(int i=fitness.length-1;i>=0;i--){
			tmp[index] = fitness[i];
			index++;
		}
		fitness = tmp;
	}
	
	/*
	//Stochastic universal sampling
	void StochasticUniversalSampling(){	
		double ptrs[] = new double[size];
		int elite[] = new int[size];
		double start = r.nextDouble()*(getTotalFitness()/N);
		for(int i=0;i<size;i++){
			ptrs[i] = start + i*getTotalFitness()/N;
		}
		double tmp=0;
		for(int j=0;j<size;j++){
			for(int i=0;i<fitness.length;i++){
				if( (tmp<=ptrs[j]) && (ptrs[j]<tmp+fitness[i]) ){
					elite[j] = i;
				}
				else{
					tmp += fitness[i];
				}
			}
			//もしすべての個体の適応度が0だったら，ランダムにえらんだ個体の番号を返す．
			elite[j] = (int) (r.nextDouble()*(getTotalFitness()/N) );
		}		
	}
	*/
	
	double getTotalFitness(){
		double total =0;
		for(int i=0;i<fitness.length;i++){
			total += fitness[i];
		}
		return total;
	}
	
}
