/*GAによるナーススケジューリング問題の一解法の追実験*/

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Scheduling {
	//init
	int M = 10;		//人数
	int N = 4;		//クール数
	int L = N*M;
	int gen_last= 2;
	//現在の世代数（1からスタート）
	int gen= 1;
	int gene[] = new int [L]; 
	//次の世代の遺伝子を一時的にいれるために使う配列
	int gene_next[]= new int[L];
	int grade[] = new int[M];
	int[] elite_gene = new int [L];
	static Random r= new Random();
	double threshold = 0.5;		//閾値
	double point[] = new double [L];
	double fitness[] = new double[L];
	//交叉率
	double crossover= 0.8;
	//突然変異率
	double mut = 0.9;
	double Gpoint[] = new double [L];

	
	public static void main(String[] args) {
		Scheduling ga = new Scheduling();
		ga.go();
	}
	
	Scheduling(){
		init();
	}
	
	void init(){
		for(int i=0;i<L;i++){
			//0〜4のパターンをランダムに生成
			gene[i] = (int)((r.nextDouble()*100.0)%5.0);
		}
	}

	void go() {
		for(gen=1;gen<=gen_last;gen++){
			evaluate();
			//evolve();
			printStatus();
		}
	}
	
	void evaluate(){
		//pointの初期化
		for(int i=0;i<L;i++){
			point[i] =0;
		}
		
		try { 
			FileReader in = new FileReader("grade.txt");
			int c;
			int i=0;
			while((c = in.read()) != -1){
			    grade[i] = c;
			    i++;
			}
			for(int j=0;j<M;j++){
			    System.out.println(grade[j]);
			}
			in.close();

		}catch (IOException ie){
			System.out.println("ファイルがありません");
		}catch (Exception e){
			System.out.println("ファイル指定がありません");
		}
		
		for(int i=0;i<M;i++)
		System.out.println(grade[i]);
	}

	/*
	void evolve(){
		//gene_nextを初期化．
		gene_next= new int[L];
		//次世代に必要な個体数分だけ選択の作業を繰り返す
		for(int i=0;i<L;i++){
			//1.選択
			//ルーレット選択で選んだ子孫を残す個体の番号を代入
			int o0= getParentNoByRouletteSelection();
			int o1= getParentNoByRouletteSelection();
			
			//2.交叉
			int cpoint= 0;
			//交叉する場所をランダムに選んでcpointに代入
			if(r.nextDouble()<crossover){
				cpoint= (int)(r.nextDouble()*(N+1));
			}
			//cpointの前までは親個体o0の遺伝子を受け継ぐ
			for(int j=0;j<cpoint;j++){
				gene_next[i]= gene[o0];
			}
			//cpoint以降は親個体o1の遺伝子を受け継ぐ
			for(int j=cpoint;j<N;j++){
				gene_next[i]= gene[o1];
			}
			//3.突然変異
			//突然変異を加える
			for(int j=0;j<N;j++){
				if(r.nextDouble()<mut){
				//新たなランダム値を代入する
					gene_next[i]= (int)((r.nextDouble()*100.0)%4.0);
					}
				}
			}
		gene = gene_next;
	}
	*/
	
	//ルーレット選択を行って，子孫を作る個体の番号を求める．
	int getParentNoByRouletteSelection(){
		//候補の個体の番号2つをランダムに選ぶ
		double total= 0;
			for(int i=0;i<L;i++){
				total+= Gpoint[i];
			}
		
		double val= r.nextDouble()*total;
		double tmp= 0;
		for(int i=0;i<L;i++){
			if((tmp<=val)&&(val<tmp+Gpoint[i])){
				return(i);
			}else{
				tmp+= Gpoint[i];
			}
		}
		//もしすべての個体の適応度が0だったら，ランダムにえらんだ個体の番号を返す．
		return (int)(r.nextDouble()*M);
	}

	void printStatus() {
		System.out.println(gen+"世代目");
		for(int i=0;i<L;i=i+4){
			System.out.print(gene[i]);
			System.out.print(gene[i+1]);
			System.out.print(gene[i+2]);
			System.out.print(gene[i+3]);
			System.out.print("\n");
		}
	}
}

