import java.util.Random;

public class Cea {
	int N = 30;
	int M = 20;
	//現在の世代数（1からスタート）
	int gen= 1;
	//最終世代数
	int gen_last= 10000;
	//解候補集団
	int geneA[][] = new int [N][M];
	//判定基準集団
	int geneB[][] = new int [N][M];
	//次の世代の遺伝子を一時的にいれるために使う配列
	int gene_nextA[][]= new int[N][M];
	int gene_nextB[][]= new int[N][M];
	double valueA[] = new double[N];
	double valueB[] = new double[N];
	static Random r= new Random();
	//i番目の個体の適応度を格納するための配列
	double fitnessA[]= new double[N];
	double fitnessB[]= new double[N];
	double a = 0.75;
	double b = 3.0;
	//交叉率
	double crossover= 100.0/100.0;
	//突然変異率
	double mut=1.0/30.0;
	double fit_avgA;
	double fit_avgB;
	
	public static void main(String[] args) {
		Cea cea = new Cea();
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
	Cea(){
		init();
	}
	
	void init(){
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				//geneA[i][j] = (int)(r.nextDouble()*2.0);
				geneA[i][j] = 0;	//初期値は'000000000000000'
				geneB[i][j] = (int)(r.nextDouble()*2.0);
				valueA[i] = getValueA(i);
				valueB[i] = getValueB(i);
			}
		}
	}
	
	void evaluate(){
		//各個体の表現型value[i]と適応度fitness[i]を求めて代入する
		double total_fitnessA = 0;
		double total_fitnessB = 0;

		for(int i=0;i<N;i++){
			valueA[i]= getValueA(i);
			valueB[i]= getValueB(i);
			fitnessA[i]= function(valueA[i]);
			fitnessB[i]= function(valueB[i]);
			//fitnessの平均値を出す
			total_fitnessA += fitnessA[i];	
			total_fitnessB += fitnessB[i];
		}
		fit_avgA = total_fitnessA / N;
		fit_avgB = total_fitnessB / N;
	}
	
	double getValueA(int p){
		//遺伝子をMビットのビット列だとみなして10進数に変換し，2^M-1で割った値を返す．
		//さらに、*10.0することで、0〜10までの数字に変換する
		long value= 0;
		long cb= 1;
		
		for(int i=M-1;i>=0;i--){
			value+= geneA[p][i]*cb;
			cb*=2.0;
		}
		return((double)value/((double)Math.pow(2, M)-10.0))*10.0;
	}
	
	double getValueB(int p){
		//遺伝子をMビットのビット列だとみなして10進数に変換し，2^M-1で割った値を返す．
		long value= 0;
		long cb= 1;
		
		for(int i=M-1;i>=0;i--){
			value+= geneB[p][i]*cb;
			cb*=2.0;
		}
		return((double)value/((double)Math.pow(2, M)-10.0))*10.0;
	}
	
	double function(double x){
		return(x+a*x*Math.sin(b*x));
	}
	
	//進化操作をする関数
	void evolve(){
		//gene_nextを初期化．
		gene_nextA= new int[N][M];
		gene_nextB= new int[N][M];
		//次世代に必要な個体数分だけ選択の作業を繰り返す．
		for(int i=0;i<N;i++){
			//1.選択
			//ルーレット選択で選んだ子孫を残す個体の番号を代入
			//int o0= getParentNoByRouletteSelection();
			//int o1= getParentNoByRouletteSelection();
		
			int o0= getParentNoByTournamentSelection_geneA();
			int o1= getParentNoByTournamentSelection_geneA();
		
			//2.交叉
			int cpoint= 0;
			//交叉する場所をランダムに選んでcpointに代入
			if(r.nextDouble()<crossover){
				cpoint= (int)(r.nextDouble()*(M+1));
			}
			//cpointの前までは親個体o0の遺伝子を受け継ぐ
			for(int j=0;j<cpoint;j++){
				gene_nextA[i][j]= geneA[o0][j];
			}
			//cpoint以降は親個体o1の遺伝子を受け継ぐ
			for(int j=cpoint;j<M;j++){
				gene_nextA[i][j]= geneA[o1][j];
			}
			//3.突然変異
			//突然変異を加える
			for(int j=0;j<M;j++){
				if(r.nextDouble()<mut){
					//遺伝子の値を反転させる
					gene_nextA[i][j]= 1-gene_nextA[i][j];
				}
			}
		}
		//geneの参照先をgene_nextの中身として，世代交代させる
		//(ここでは，配列gene_next[][]の値をすべて配列gene[][]にコピーするのと同等の処理だと思ってください．)
		geneA= gene_nextA;
		//geneB= gene_nextB;

	}
	/*
	//ルーレット選択を行って，子孫を作る個体の番号を求める．
	int getParentNoByRouletteSelection(){
		//候補の個体の番号2つをランダムに選ぶ
		double total= 0;
		for(int i=0;i<N;i++){
			total+= fitness[i];
		}
		double val= r.nextDouble()*total;
		double tmp= 0;
		for(int i=0;i<N;i++){
			if((tmp<=val)&&(val<tmp+fitness[i])){
				return(i);
			}else{
				tmp+= fitness[i];
			}
		}
		//もしすべての個体の適応度が0だったら，ランダムにえらんだ個体の番号を返す．
		return((int)(r.nextDouble()*N));
	}
	*/
	
	//トーナメント選択を行って，子孫を作る個体の番号を求める．
	int getParentNoByTournamentSelection_geneA(){
		//候補の個体の番号2つをランダムに選ぶ
		int parent1= (int)(r.nextDouble()*N);
		int parent2= (int)(r.nextDouble()*N);

		//parent1の適応度の方が高かったら，
		if(fitnessA[parent1]>fitnessA[parent2]){
			//その番号を返す
			return(parent1);
		}else{
			//それ以外はparent2を返す
			return(parent2);
		}
	}
	
	//トーナメント選択を行って，子孫を作る個体の番号を求める．
	int getParentNoByTournamentSelection_geneB(){
		//候補の個体の番号2つをランダムに選ぶ
		int parent1= (int)(r.nextDouble()*N);
		int parent2= (int)(r.nextDouble()*N);

		//parent1の適応度の方が高かったら，
		if(fitnessB[parent1]>fitnessB[parent2]){
			//その番号を返す
			return(parent1);
		}else{
			//それ以外はparent2を返す
			return(parent2);
		}
	}
	
	void printStatus() {
		/*
		//適応度の平均と最高値を表示
		double fitness_average= 0;
		double fitness_best= 0;
		double value_best= 0;
		int no_best= 0;
		
		for(int i=0;i<N;i++){
			fitness_average += fitness[i];
			if(fitness[i]>fitness_best){
				fitness_best= fitness[i];
				value_best= value[i];
				no_best= i;
			}
		}
		double avg = fitness_average/N;
		
		System.out.print(avg + "\t" + value_best +"\t" + no_best +"\t" + function(value_best)+"\n");
		*/
		System.out.print(fit_avgA+"\n");
	}
}
