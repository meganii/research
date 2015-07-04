package rastringin;

import java.util.Random;

/*
 * Rastringin関数を競合型共進化を用いて最適化
 */

public abstract class RastringinSpecies {
	
	int N = 100;		//個体数
	/*
	int gen= 1;		//現在の世代数（1からスタート）
	int gen_last= 10000000;		//最終世代
	*/
	static Random r= new Random();
	double mut;		//突然変異率
	double std;		//閾値
	boolean isGo;
	double std1; double std2;	//stop&goの閾値
	int dimension = 10;
	
	double gene[] = new double[N];
	double gene_next[] = new double[N];
	int fitness[] = new int[N];
	
	double xrange = 5.12;	//xの範囲
	
	//コンストラクタによる初期化
	RastringinSpecies(double std1, double std2){
		isGo = true;	//State : GOからスタート
		this.std1 = std1;	
		this.std2 = std2;
		mut = 0.01;
		//std = 0.25;
		
		for(int i=0;i<gene.length;i++){
			for(int j=0;j<N;j++){
				gene[i] = 0;
				fitness[i] = 0;
			}
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
}
