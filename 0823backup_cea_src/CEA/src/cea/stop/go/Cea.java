package cea.stop.go;

import java.util.Random;


/*
 * 実数値型の表現を用いて、一次元関数x+axsin(bx)を最大化する
 * 競合型共進化を用いている（stop＆go導入）
 */

public class Cea {
	
	//個体数
	int N = 30;
	//現在の世代数（1からスタート）
	int gen= 1;
	//最終世代数
	int gen_last= 100000;
	static Random r= new Random();
	//関数の値
	double a = 0.75;
	double b = 3.0;
	
	double std = 0.25;
	
	SolutionSpecies solsg = new SolutionSpecies(0, 0);
	CriteriaSpecies crisg = new CriteriaSpecies(0, 0);
	
	
	public static void main(String[] args) {
		Cea cea = new Cea();
		cea.go();
		
	}
	
	void go() {
		for(gen=1;gen<=gen_last;gen++){
			/*
			System.out.println("evaluate");
			for(int i=0;i<N;i++){
				System.out.println(objectFunction(solsg.gene[i])+" "+solsg.fitness[i]+"\t"+crisg.gene[i]+" "+crisg.fitness[i]);//test
			}
			*/
			evaluate();
			/*
			System.out.println("after evaluate");
			System.out.println(solsg.getAvgFitness() + " " + crisg.getAvgFitness());
			System.out.println(solsg.isGo + " " + crisg.isGo);
			for(int i=0;i<N;i++){
				System.out.println(objectFunction(solsg.gene[i])+" "+solsg.fitness[i]+"\t"+crisg.gene[i]+" "+crisg.fitness[i]);//test
			}
			*/
			printStatus();
			evolve();
			/*
			evaluate();//test
			System.out.println("after evolve");
			System.out.println(solsg.isGo + " " + crisg.isGo);
			for(int i=0;i<N;i++){
				System.out.println(objectFunction(solsg.gene[i])+" "+solsg.fitness[i]+"\t"+crisg.gene[i]+" "+crisg.fitness[i]);//test
			}
			*/
			
		}
	}
	

	//コンストラクタ
	Cea(){
		init();
	}
	
	void init(){
		//解候補集団
		//gene_nextを初期化．
		solsg.gene_next= new double[N];
		//次世代に必要な個体数分だけ選択の作業を繰り返す．
		for(int i=0;i<N;i++){
			//突然変異を加える
			if(r.nextDouble()<solsg.mut){
				solsg.gene_next[i] = solsg.gene[i] + r.nextGaussian()*std;
				
				//加える値が0以上になるまでルーレットを回す
				if(solsg.gene_next[i] < 0){
					while(solsg.gene_next[i] < 0){
						solsg.gene_next[i] = solsg.gene[i] + r.nextGaussian()*std;
					}	
				}				
			}
		}
		//geneの参照先をgene_nextの中身として，世代交代させる
		//(ここでは，配列gene_next[][]の値をすべて配列gene[][]にコピーするのと同等の処理だと思ってください．)
		solsg.gene= solsg.gene_next;
		
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
			//System.out.println(objectFunction(solsg.gene[i])+" "+solsg.fitness[i]+"\t"+crisg.gene[i]+" "+crisg.fitness[i]);//test
		}
		
		
		
	}
	
	//解のx番目のやつが判定基準を越えた数を返す
	int getReachParamNumbers(int x) {
		int n = 0;
		for(int i=0;i<N;i++){
			if( objectFunction(solsg.gene[x]) > crisg.gene[i] ){

				n++;
			}
		}
		return n;
	}
	
	//判定基準のx番目のやつが解を越えた数を返す
	int getReachSolNumbers(int x) {
		int n = 0;
		for(int i=0;i<N;i++){
			if( objectFunction(solsg.gene[i]) < crisg.gene[x] ){

				n++;	
			}
		}
	
		return n;
	}
	
	
	void evaluate(){
		//各個体の表現型value[i]と適応度fitness[i]を求めて代入する
		for(int i=0;i<N;i++){
			solsg.fitness[i]=0;
			crisg.fitness[i]=0;
			solsg.fitness[i] = getReachParamNumbers(i);
			crisg.fitness[i] = getReachSolNumbers(i);
		}
		
		
		/*
		 * まとめて適応度を計算しようと試みたが失敗。
        for(int i=0;i<N;i++){
        	solsg.fitness[i]=0;
        	crisg.fitness[i]=0;
            for(int j=0;j<N;j++){
                if( objectFunction(solsg.gene[i]) > crisg.gene[j] ){
                    solsg.fitness[i]++;
                }else{
                    crisg.fitness[j]++;
                }
            }
        }
        */
		
		
		
	}
	
	
	//目的関数
	double objectFunction(double x){
		return(x+a*x*Math.sin(b*x));
		//return Math.sin(x)+Math.cos(x)+0.4*x;
	}

	void evolve(){
		//System.out.println(solsg.getAvgFitness()+ " " + crisg.getAvgFitness());
		solsg.stopandgo(crisg.getAvgFitness());
		crisg.stopandgo(solsg.getAvgFitness());
	}

	
	//最大値の値を求める
	double getMaxValue(){
		double max = 0;
		max = objectFunction(solsg.gene[0]);
		for(int i=0;i<N;i++){
			if(objectFunction(solsg.gene[i])>max){
				max = objectFunction(solsg.gene[i]);
			}
		}
		return max;
	}
	
	//最小値の値を求める
	double getMinValue(){
		double min = 0;
		min = objectFunction(solsg.gene[0]);
		for(int i=0;i<N;i++){
			if(objectFunction(solsg.gene[i])<min){
				min = objectFunction(solsg.gene[i]);
			}
		}
		return min;
	}
		
	
	private void printStatus() {
	    //System.out.println(gen+"世代目"+"\t"+getMaxValue());
		//System.out.println(gen+"世代目");
	    //System.out.println(getMaxValue());
		
		//解候補の中での最大のxを求める
		double max = 0;
		int	object	= 0;
		for(int i=0;i<N;i++){
			if(solsg.gene[i]>max){
				max = solsg.gene[i];
				object	= i;
			}
		}
		
		//最小の判定基準を求める
		double min = 1000;
		int index_min =0;
		for(int i=0;i<N;i++){
			if(crisg.gene[i] < min){
				min = crisg.gene[i];
				index_min = i;
			}
		}
		
		/* よくわからない過去の遺産
		//最小の判定基準を求める
		double maxB = 1000;
		int	objectB	= 0;
		for(int i=0;i<N;i++){
			if(crisg.fitness[i]<max){
				maxB = crisg.fitness[i];
				objectB	= i;
			}
		}
		*/
		
		//目的関数を最大にするxを求める
		double max_function =0;
		int index = 0;
		for(int i=0;i<N;i++){
			if( objectFunction(solsg.gene[i]) > max_function ){
				max_function = 	objectFunction(solsg.gene[i]);
				index = i;
			}
		}
		
		System.out.println(gen + " " + max_function + " " + solsg.gene[index] + " " + solsg.fitness[index] + " " + min + " " + crisg.fitness[index_min]);
		
		//System.out.println(gen + " " + max + " " + objectFunction(solsg.gene[object]) + " " + solsg.fitness[object] + " " + solsg.gene[object] + " " +  crisg.gene[objectB] + " " + maxB + " " + crisg.fitness[objectB]);
		//System.out.println(gen + " " + max + " " + getMaxValue() + " " + solsg.fitness[object] + " " + solsg.gene[object] + " " +  crisg.gene[objectB] + " " + maxB + " " + crisg.fitness[objectB]);
		
		/*
		System.out.println(gen);
		for(int i=0;i<N;i++){
			//System.out.println(solsg.gene[i]+" "+crisg.gene[i]);
			System.out.println(solsg.gene[i]+" "+ objectFunction(solsg.gene[i]) + " " + solsg.fitness[i]+"\t"+crisg.gene[i]+" "+crisg.fitness[i]);//test
			//System.out.println(solsg.gene[i]);
		}
		*/
		
	}
}
