/*　
 * おしいれ問題を解くGAのサンプル．
 * 荷物の大きさを入れる配列size[]，荷物の重要度を入れる配列importance[]，
 * 押し入れの大きさを入れる変数max_sizeを新たに定義．
 * 遺伝情報は各荷物を入れる（１）か入れない（０）かに対応し，
 * 新たに定義したfunction_oshiire()関数で適応度を計算している．
 * 適応度は，入れる荷物の大きさの合計がmax_size以下の場合は大きさの合計，
 * 超えてしまった場合は0とする．
 * ※コード中変更点と追加点については表示がしてあります．
 */

import java.util.*;

public class Ga_oshiire {
//-------------追加ここから-------------
	//M番目の荷物の大きさ
	double size[]= {11,6,31,13,10,29,30,35,31,28,15,12,38,43,20,15,45,58,23,34};
	//M番目の荷物の重要度
	double importance[]= {35,34,46,28,38,47,45,55,40,52,44,33,55,63,39,31,60,70,40,43};
	//押し入れの大きさ
	double max_size= 200;
//-------------追加ここまで-------------


	//個体数
	int N= 30;
	//遺伝子の長さ
	int M= 20;
	//最終世代数
	int gen_last= 100;
	//現在の世代数（1からスタート）
	int gen= 1;
	//突然変異率
	double mut=1.0/30.0;
	//交叉率
	double crossover= 100.0/100.0;
	//N個体×M個の遺伝子を格納するInt型の配列gene[i][j]
	//本当は各遺伝子はビットだからboolean型で十分なんだけど．
	//i番目の個体のj番目の遺伝子の値を示す．
	int gene[][]= new int[N][M];
	//次の世代の遺伝子を一時的にいれるために使う配列
	int gene_next[][]= new int[N][M];
	//i番目の個体の適応度を格納するための配列
	double fitness[]= new double[N];
	//i番目の個体のj番目の変数の値を格納するための配列
	double value[]= new double[N];
	//ランダムな数を発生させるためのオブジェクト
	//r.nextDouble()とすると，0<=x<1の範囲のランダムな実数（double型）を返してくれる．
	static Random r= new Random();

	public static void main(String[] args) {
		Ga_oshiire ga_oshiire= new Ga_oshiire();
		ga_oshiire.go();
	}
	//コンストラクタ
	//オブジェクトを作ったときに呼ばれる関数．この場合init()関数を実行する．
	//つまりプログラムを実行する時にはいつでもinit()関数が一度だけ実行されるということ．
	//なので，変数を初期化する作業は，init()関数の中に書けばよいことになる．
	Ga_oshiire(){
		init();
	}

	//初期化する関数
	void init(){
		//乱数のseedの設定（seedを明示的に設定するとその値に対応した乱数が出てくる）
		//r.setSeed(100);
		
		//geneの初期化．それぞれにランダムに0か1を代入
		//ついでにfitnessもすべて0に．
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				gene[i][j]= (int)(r.nextDouble()*2.0);
			}
			fitness[i]= 0;
		}
	}

	void go(){
		for(gen=1;gen<=gen_last;gen++){
			//適応度評価
			evaluate();
			//現在の集団の様子を表示する
			printStatus();
			//適応度に基づいて集団を進化させる
			evolve();
		}
	}


	void evaluate(){
		//各個体の表現型value[i]と適応度fitness[i]を求めて代入する
		for(int j=0;j<N;j++){
			//value[j]= getValue(j);
			//変更点
			//新しくつくったi番目の個体の適応度を返す関数function_oshiire(i)を
			//使って適応度を代入
			fitness[j]= function_oshiire(j);
		}
	}

	//------追加ここから-------
	double function_oshiire(int p){
		//押し入れに入れる荷物の大きさの合計
		int sum_size= 0;
		//入れた荷物の重要度の合計
		int sum_importance= 0;
		
		//0からM-1番目の荷物それぞれについて．．．
		for(int i=0;i<M;i++){
			//遺伝情報を調べて1だったら．．．
			if(gene[p][i]==1){
				//そのおおきさと重要度を合計に加算
				sum_size+= size[i];
				sum_importance+= importance[i];
			}
		}
		//もし大きさが押し入れの最大サイズを上回っていたら
		//適応度は0（なにも入れられなかった）にする
		if(sum_size>max_size){
			//結果表示用にvaluep]に大きさの合計0を入れておく
			value[p]= 0;
			//適応度0を返す
			return(0);
		}else{
			//結果表示用にvaluep]に大きさの合計を入れておく
			value[p]= sum_size;
			//それ以外は重要度の合計を返す
			return(sum_importance);
		}

	}
	//------追加ここまで-------


	//実験の状況を表示する関数．ほしいデータにあわせて適宜追加したり，
	//コメントアウトしたりして使ってください．
	void printStatus(){
		if(gen==1){
			System.out.print("gen\taverage_fitness\tbest_fitness\tbest_size\t'best_code'\n");
		}
		//現在の世代の様子を表示
		//\tはタブ記号，\nは改行を意味する文字（エスケープシーケンスと言います．）
		//適応度の平均と最高値を表示
		double fitness_average= 0;
		double fitness_best= 0;
		double value_best= 0;
		int no_best= 0;
		for(int i=0;i<N;i++){
			fitness_average+= fitness[i];
			if(fitness[i]>fitness_best){
				fitness_best= fitness[i];
				value_best= value[i];
				no_best= i;
			}
		}
		System.out.print(String.format("%04d\t%04.9f\t%04.9f\t%04.9f\t\'", gen, fitness_average/(double)N, fitness_best, value_best));
		for(int j=0;j<M;j++){
			System.out.print(gene[no_best][j]);
		}
		System.out.print("'\n");
	}

	void evolve(){
		//gene_nextを初期化．
		gene_next= new int[N][M];
		//次世代に必要な個体数分だけ選択の作業を繰り返す．
		for(int i=0;i<N;i++){
			//1.選択
			//ルーレット選択で選んだ子孫を残す個体の番号を代入
			int o0= getParentNoByRouletteSelection();
			int o1= getParentNoByRouletteSelection();
		
		//	int o0= getParentNoByTournamentSelection();
		//	int o1= getParentNoByTournamentSelection();
		
			//2.交叉
			int cpoint= 0;
			//交叉する場所をランダムに選んでcpointに代入
			if(r.nextDouble()<crossover){
				cpoint= (int)(r.nextDouble()*(M+1));
			}
			//cpointの前までは親個体o0の遺伝子を受け継ぐ
			for(int j=0;j<cpoint;j++){
				gene_next[i][j]= gene[o0][j];
			}
			//cpoint以降は親個体o1の遺伝子を受け継ぐ
			for(int j=cpoint;j<M;j++){
				gene_next[i][j]= gene[o1][j];
			}
			//3.突然変異
			//突然変異を加える
			for(int j=0;j<M;j++){
				if(r.nextDouble()<mut){
					//遺伝子の値を反転させる
					//変更点
					gene_next[i][j]= 1-gene_next[i][j];					
				}
			}
		}
		//geneの参照先をgene_nextの中身として，世代交代させる
		//(ここでは，配列gene_next[][]の値をすべて配列gene[][]にコピーするのと同等の処理だと思ってください．)
		gene= gene_next;
	}

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

	//トーナメント選択を行って，子孫を作る個体の番号を求める．
	int getParentNoByTournamentSelection(){
		//候補の個体の番号2つをランダムに選ぶ
		int parent1= (int)(r.nextDouble()*N);
		int parent2= (int)(r.nextDouble()*N);

		//parent1の適応度の方が高かったら，
		if(fitness[parent1]>fitness[parent2]){
			//その番号を返す
			return(parent1);
		}else{
			//それ以外はparent2を返す
			return(parent2);
		}
	}

}
