package rastringin;

import java.util.Random;


public class Rastringin {
	
	
	int N = 100;		//個体数
	int gen= 1;		//現在の世代数（1からスタート）
	int gen_last= 50000;	//最終世代数
	
	//double std = 0.25;		//標準偏差
	
	int dimension = 10;		//次元
	double ary[] = new double[dimension];
	
	//double xrange = 5.12;	//xの範囲
	
	static Random r= new Random();
	
	//個体の生成
	SolutionSpeciesRastringin solsg = new SolutionSpeciesRastringin(0, 0);
	CriteriaSpeciesRastringin crisg = new CriteriaSpeciesRastringin(0, 0);
	
	//目的関数(Rastringin関数最小化問題)
	double objectFunction(int x){
		double value = 0;
		for(int i=0;i<dimension;i++){
			value += solsg.gene[x][i] * solsg.gene[x][i] - 10*Math.cos(2*Math.PI*solsg.gene[x][i]);
		}
		return 10*dimension + value;
	}
	
	public static void main(String[] args) {
		Rastringin rn = new Rastringin();
		rn.go();
	}
	
	Rastringin(){
		init();
	}
	
	void init(){
		/*SolutionSpeciesRastingin.javaにて実装
		//解候補集団
		for(int i=0;i<N;i++){
			solsg.gene[i] = r.nextDouble()*2*xrange - xrange;	//-5.12〜5.12の間でランダムに与える
		}
		*/
		
		//判定基準集団の初期化
		double add = 0;
		crisg.gene[0] = getMinValue();
		for(int i=1;i<N;i++){
			add += (getMaxValue() - getMinValue())/(N-1);
			crisg.gene[i] = getMinValue() + add;
			
		}
		
		//適応度の初期化
		//Calc_fitness();
		
		//適応度の初期化
		for(int i=0;i<N;i++){
			solsg.fitness[i] = getReachParamNumbers(i);
			crisg.fitness[i] = getReachSolNumbers(i);
			/*test
			System.out.println("init()での表示");
			System.out.println(solsg.gene[i] + " " + objectFunction(solsg.gene[i])+" "+solsg.fitness[i]+"\t"+crisg.gene[i]+" "+crisg.fitness[i]);//test
			*/
		}
	}
	
	//最大値の値を求める
	double getMaxValue(){
		double max = 0;
		max = objectFunction(0);
		for(int i=0;i<N;i++){
			if(objectFunction(i)>max){
				max = objectFunction(i);
			}
		}
		return max;
	}
	
	//最小値の値を求める
	double getMinValue(){
		double min = 0;
		min = objectFunction(0);
		for(int i=0;i<N;i++){
			if(objectFunction(i)<min){
				min = objectFunction(i);
			}
		}
		return min;
	}
	
	
	void go() {
		/*
		//test
		for(int i=0;i<solsg.gene.length;i++){
				System.out.println("go()での出力");
				System.out.println(solsg.gene[i]);
		}
		*/
		for(gen=1;gen<=gen_last;gen++){
			evaluate();
			evolve();
			printStatus();
		}
	}
	


	void evaluate(){
		/*
		//test
		for(int i=0;i<solsg.gene.length;i++){
				System.out.println("evaluate()での出力");
				System.out.println(solsg.gene[i]);
		}
		*/
		//各個体の表現型value[i]と適応度fitness[i]を求めて代入する
		for(int i=0;i<N;i++){
			solsg.fitness[i] = 0;
			crisg.fitness[i] = 0;
			solsg.fitness[i] = getReachParamNumbers(i);
			crisg.fitness[i] = getReachSolNumbers(i);
		}
	}
	
	//解のx番目のやつが判定基準よりも小さかった数を返す
	int getReachParamNumbers(int x) {
		int n = 0;
		for(int i=0;i<N;i++){
			if( objectFunction(x) < crisg.gene[i] ){

				n++;
			}
		}
		return n;
	}
	
	//判定基準のx番目のやつが解よりも小さかった数を返す
	int getReachSolNumbers(int x) {
		int n = 0;
		for(int i=0;i<N;i++){
			if( objectFunction(i) > crisg.gene[x] ){

				n++;	
			}
		}
	
		return n;
	}
	
	
	void evolve() {
		/*
		//test
		for(int i=0;i<solsg.gene.length;i++){
				System.out.println("evolve()前での出力");
				System.out.println(solsg.gene[i]);
		}
		*/
		
		//System.out.println("solEvolve");
		solsg.stopandgo( crisg.getAvgFitness() );
		
		
		
		//System.out.println("criEvolve");
		crisg.stopandgo( solsg.getAvgFitness() );
		
		/*
		//test
		for(int i=0;i<solsg.gene.length;i++){
				System.out.println("evolve()後での出力");
				System.out.println(solsg.gene[i]);
		}
		*/
	}
	
	
	void printStatus(){
		//見つけたもっとも小さい目的関数値を求める
		double min = objectFunction(0);
		int	object	= 0;
		for(int i=0;i<N;i++){
			if( objectFunction(i) < min ){
				min = objectFunction(i);
				object	= i;
			}
		}
		
	
		//最大の判定基準を求める
		int max = 0;
		int	objectB	= 0;
		for(int i=0;i<N;i++){
			if( crisg.fitness[i] > max){
				max = crisg.fitness[i];
				objectB	= i;
			}
		}
		
		System.out.println( gen + " " + min + " ");
		//for(int i=0;i<dimension;i++)
			//System.out.print(solsg.gene[object][i] + " ");
		//System.out.print(crisg.gene[objectB] + " " + max + "\n");
		//System.out.println( min + " "  + solsg.fitness[object]);
		
		/*
		//すべての表現型を表示させる
		for(int i=0;i<N;i++){
			System.out.print(gen +" ");
			System.out.println(solsg.gene[i] + " " + objectFunction(solsg.gene[i]) + " " + crisg.gene[i]);
		}
		*/
	}

}
