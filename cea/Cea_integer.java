

import java.util.Random;

/*
 * 実数値型の表現を用いて、一次元関数x+axsin(bx)を最適化する
 * 通常のGAを用いている
 */

public class Cea_integer {
	//個体数
	int N = 30;
	//現在の世代数（1からスタート）
	int gen= 1;
	//最終世代数
	int gen_last= 1000;
	//解候補集団
	double geneA[] = new double [N];
	//判定基準集団
	double geneB[] = new double [N];
	//次の世代の遺伝子を一時的にいれるために使う配列
	double gene_nextA[]= new double[N];
	double gene_nextB[]= new double[N];
	//int parent[] = new int[5];
	double valueA[] = new double[N];
	double valueB[] = new double[N];
	static Random r= new Random();
	//i番目の個体の適応度を格納するための配列
	double fitnessA[]= new double[N];
	double fitnessB[]= new double[N];
	//double avg_fitA;
	//double avg_fitB;
	int parent[] = new int[5];
	//関数の値
	double a = 0.75;
	double b = 3.0;
	//突然変異率
	double mut = 0.1;
	double std = 0.25; // 標準偏差
	double threshold = 10.0;	//閾値
	
	public static void main(String[] args) {
		Cea_integer cea = new Cea_integer();
		cea.go();
	}
	
	void go() {
		for(gen=1;gen<=gen_last;gen++){
			evaluate();
			evolve();
			printStatus();
		}
	}
	

	//コンストラクタ
	//オブジェクトを作ったときに呼ばれる関数．この場合init()関数を実行する．
	//つまりプログラムを実行する時にはいつでもinit()関数が一度だけ実行されるということ．
	//なので，変数を初期化する作業は，init()関数の中に書けばよいことになる．
	Cea_integer(){
		init();
	}
	
	void init(){
		//解候補集団の初期化
		for(int i=0;i<N;i++){
			geneA[i] = 0.0;
		}
		//gene_nextを初期化．
		gene_nextA= new double[N];
		//次世代に必要な個体数分だけ選択の作業を繰り返す．
		for(int i=0;i<N;i++){
			//突然変異を加える
			if(r.nextDouble()<mut){
				gene_nextA[i] = geneA[i] + r.nextGaussian()*std;
				
				//加える値が0以上になるまでルーレットを回す
				if(gene_nextA[i] < 0){
					while(gene_nextA[i] < 0){
						gene_nextA[i] = geneA[i] + r.nextGaussian()*std;
					}	
				}				
			}
		}
		//geneの参照先をgene_nextの中身として，世代交代させる
		//(ここでは，配列gene_next[][]の値をすべて配列gene[][]にコピーするのと同等の処理だと思ってください．)
		geneA= gene_nextA;
		
		//判定基準集団の初期化
		double add = 0;
		geneB[0] = getMinValue();
		for(int i=1;i<N;i++){
			add += (getMaxValue() - getMinValue())/(N-1);
			geneB[i] = getMinValue() + add;
			
		}
		
		//適応度の初期化
		//Calc_fitness();
		
		/*
		//適応度の初期化
		for(int i=0;i<N;i++){
			fitnessA[i] = getReachParamNumbers(i);
			fitnessB[i] = getReachSolNumbers(i);
			//System.out.println(function(geneA[i])+" "+fitnessA[i]+"\t"+geneB[i]+" "+fitnessB[i]);//test
		}
		*/		
		
	}
	
	/*
	//fitnessの計算
	void Calc_fitness(){
		for(int i=0;i<N;i++){
			fitnessA[i]=0;
			fitnessB[i]=0;
		}
		for(int i=0;i<N;i++){
			double pointA[] = new double[N];
			double pointB[] = new double[N];
			for(int j=0;j<N;j++){
				if( function(geneA[i]) > geneB[j]){
					pointA[j] = 1;
				}
				else{
					pointA[j] =0;
				}
				pointB[j] = 1 - pointA[j];
				fitnessA[i] += pointA[j];
				fitnessB[i] += pointB[j];
			}

		}
	}
	*/
	
	
	/*
	double getReachParamNumbers(int x) {
		double sum = 0;
		for(int i=0;i<N;i++){
				if(function(geneA[x]) > geneB[i]){
					point[i] = 1;
				}
		}
		return sum;
	}
	*/
	
	
	//解のx番目のやつが判定基準を越えた数を返す
	double getReachParamNumbers(int x) {
		double n = 0;
		for(int i=0;i<N;i++){
			if( function(geneA[x]) > geneB[i]){
				n += 1;
			}
		}
		return n;
	}
	
	//判定基準のx番目のやつが解を越えた数を返す
	double getReachSolNumbers(int x) {
		double n = 0;
		for(int i=0;i<N;i++){
			if(geneB[x] > function(geneA[i])){
				n += 1;
			}
		}
	
		return n;
	}
	
	
	void evaluate(){
		//平均適応度の計算をしたい
		//getAvgFitness();
		
		//適応度の計算
		//Calc_fitness();
		
		//各個体の表現型value[i]と適応度fitness[i]を求めて代入する
		for(int i=0;i<N;i++){
			fitnessA[i] = getReachParamNumbers(i);
			fitnessB[i] = getReachSolNumbers(i);
		}
		
		
		
	}
	
	//平均適応度の計算
	double getAvgFitnessA(){
		double total_fitnessA = 0;
		for(int i=0;i<N;i++){
			total_fitnessA += fitnessA[i];
		}
		return total_fitnessA/N;
	}
	
	//目的関数
	double function(double x){
		return(x+a*x*Math.sin(b*x));
		//return Math.sin(x)+Math.cos(x)+0.4*x;
	}

	void evolve(){
		//解候補の突然変異フェイズ
		//gene_nextを初期化．
		gene_nextA= new double[N];
		//次世代に必要な個体数分だけ選択の作業を繰り返す．
		for(int i=0;i<N;i++){
			//突然変異を加える
			//int o0 = getParentNoByRouletteSelectionA();
			int o0 = getParentNoByTournamentSelectionA();
			if(r.nextDouble() < mut){
				gene_nextA[i] = geneA[o0] + r.nextGaussian()*std;//正規乱数N(0,0.25)を加える

				//加える値が0以上になるまでルーレットを回す
				if(gene_nextA[i] < 0){
					while(gene_nextA[i] < 0){
						gene_nextA[i] = geneA[o0] + r.nextGaussian()*std;
					}
				}
				/*
				//もしも10.0を越えてしまった場合は、mutationする前の値に戻す
				if(gene_nextA[i]>10.0){
					gene_nextA[i] = 10.0 -(gene_nextA[i]-10.0);
				}
				*/
				
				 
			}
			else{
				gene_nextA[i] = geneA[o0];
			}
		}
		//geneの参照先をgene_nextの中身として，世代交代させる
		//(ここでは，配列gene_next[][]の値をすべて配列gene[][]にコピーするのと同等の処理だと思ってください．)
		geneA = gene_nextA;
		
		//判定基準候補の選択、突然変異フェーズ
		gene_nextB= new double[N];
		for(int i=0;i<N;i++){
			//ルーレット選択で必要な個体数を選択し、ある確率で突然変異させる
			//int o0 = getParentNoByRouletteSelectionB();
			//トーナメント選択
			int o0 = getParentNoByTournamentSelectionB();
			if(r.nextDouble() < mut){
				gene_nextB[i] = geneB[o0] + r.nextGaussian()*std;//正規乱数N(0,0.25)を加える

				//判定基準候補が0以下になったら、0にする。
				if(gene_nextB[i] < 0){
					gene_nextB[i] = 0;
					/*
					while(gene_nextB[i] < 0){
						gene_nextB[i] = geneB[o0] + r.nextGaussian()*std;
					}
					*/
				}
			}
			else{
				gene_nextB[i] = geneB[o0];
			}
		}
		//geneの参照先をgene_nextの中身として，世代交代させる
		//(ここでは，配列gene_next[][]の値をすべて配列gene[][]にコピーするのと同等の処理だと思ってください．)
		geneB = gene_nextB;
		
	}


	//トーナメント選択を行って，子孫を作る個体の番号を求める．
	int getParentNoByTournamentSelectionA(){
		//候補の個体の番号5つをランダムに選び、その中で一番よいものの番号を返す
		for(int i=0;i<5;i++){		
			parent[i]= (int)(r.nextDouble()*N);
		}
		double max = 0;
		int index = 0;
		max = fitnessA[parent[0]];
		for(int j=0;j<5;j++){
			if(fitnessA[parent[j]]>max){
				max = fitnessA[parent[j]]; 
				index = j;
			}
		}
		return parent[index];
	}
	
	//トーナメント選択を行って，子孫を作る個体の番号を求める．
	int getParentNoByTournamentSelectionB(){
		//候補の個体の番号5つをランダムに選び、その中で一番よいものの番号を返す
		for(int i=0;i<5;i++){		
			parent[i]= (int)(r.nextDouble()*N);
		}
		double max = 0;
		int index = 0;
		max = fitnessB[parent[0]];
		for(int j=0;j<5;j++){
			if(fitnessB[parent[j]]>max){
				max = fitnessB[parent[j]]; 
				index = j;
			}
		}
		return parent[index];
	}
	
	//ルーレット選択を行って，子孫を作る個体の番号を求める．
	int getParentNoByRouletteSelectionB(){
		//候補の個体の番号2つをランダムに選ぶ
		double total= 0;
		for(int i=0;i<N;i++){
			total+= fitnessB[i];
		}
		double val= r.nextDouble()*total;
		double tmp= 0;
		for(int i=0;i<N;i++){
			if((tmp<=val)&&(val<tmp+fitnessB[i])){
				return(i);
			}else{
				tmp+= fitnessB[i];
			}
		}
		//もしすべての個体の適応度が0だったら，ランダムにえらんだ個体の番号を返す．
		return((int)(r.nextDouble()*N));
	}
	
	//ルーレット選択A
	int getParentNoByRouletteSelectionA(){
		//候補の個体の番号2つをランダムに選ぶ
		double total= 0;
		for(int i=0;i<N;i++){
			total+= fitnessA[i];
		}
		double val= r.nextDouble()*total;
		double tmp= 0;
		for(int i=0;i<N;i++){
			if((tmp<=val)&&(val<tmp+fitnessA[i])){
				return(i);
			}else{
				tmp+= fitnessA[i];
			}
		}
		//もしすべての個体の適応度が0だったら，ランダムにえらんだ個体の番号を返す．
		return((int)(r.nextDouble()*N));
	}
	
	//最大値の値を求める
	double getMaxValue(){
		double max = 0;
		max = function(geneA[0]);
		for(int i=0;i<N;i++){
			if(function(geneA[i])>max){
				max = function(geneA[i]);
			}
		}
		return max;
	}
	
	//最小値の値を求める
	double getMinValue(){
		double min = 0;
		min = function(geneA[0]);
		for(int i=0;i<N;i++){
			if(function(geneA[i])<min){
				min = function(geneA[i]);
			}
		}
		return min;
	}
		
	
	private void printStatus() {
	    //System.out.println(gen+"世代目"+"\t"+getMaxValue());
		System.out.println(gen+"世代目");
	    //System.out.println(getMaxValue());
		
		for(int i=0;i<N;i++){
			//System.out.println(geneA[i]+" "+geneB[i]);
			System.out.println(function(geneA[i])+" "+fitnessA[i]+"\t"+geneB[i]+" "+fitnessB[i]);//test
		}
		
		
	}
}

