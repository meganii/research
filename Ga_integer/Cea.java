import java.util.Random;

/*実数値型の一次元関数最適化の問題
*/

public class Cea {
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
	int parent[] = new int[5];
	double a = 0.75;
	double b = 3.0;
	//交叉率
	double crossover= 100.0/100.0;
	//突然変異率
	double mut=0.5;
	double fit_avgA;
	double fit_avgB;
	double std = 0.25; // 標準偏差
	
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
			geneA[i] = 0.0;
			geneB[i] = 0.0;
		}
	}
	
	void evaluate(){
		//各個体の表現型value[i]と適応度fitness[i]を求めて代入する
		for(int i=0;i<N;i++){
			fitnessA[i]= function(geneA[i]);
		}
		
	}
	
	double function(double x){
		return(x+a*x*Math.sin(b*x));
	}

	void evolve(){
		//gene_nextを初期化．
		gene_nextA= new double[N];
		//次世代に必要な個体数分だけ選択の作業を繰り返す．
		for(int i=0;i<N;i++){
			//1.選択
			int o0= getParentNoByTournamentSelection();
		
			//3.突然変異
			//突然変異を加える
			if(r.nextDouble()<mut){
				//加える値が0以上になるまでルーレットを回す
				while(r.nextGaussian()*std > 0){
					gene_nextA[o0] = geneA[o0] + r.nextGaussian()*std;
				}//0以下になったときは、0にする
				
			}
		}
		//geneの参照先をgene_nextの中身として，世代交代させる
		//(ここでは，配列gene_next[][]の値をすべて配列gene[][]にコピーするのと同等の処理だと思ってください．)
		geneA= gene_nextA;
		

	}
	
	//トーナメント選択を行って，子孫を作る個体の番号を求める．
	int getParentNoByTournamentSelection(){
		//候補の個体の番号5つをランダムに選ぶ
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
	
	double getMaxValue(){
		double max = 0;
		max = function(geneA[0]);
		for(int i=0;i<N;i++){
		    if(function(geneA[i]) > max){
			max = function(geneA[i]);
			}
		}
		return max;
	}
		
	
	private void printStatus() {
		System.out.println(gen+"世代目");
		//System.out.println(getMaxValue());
		
		for(int i=0;i<N;i++){
			System.out.println(geneA[i]);
		}
			
	}
}
