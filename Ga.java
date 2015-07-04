/*　
 * 関数最大化問題を解くGAのサンプル．
 * N個体の持つ各Mビットの遺伝子を2進数だと見て，その値を2^M-1で割って0～1の値にする．
 * それを関数function(x)=x^2に代入して個体の適応度とする．
 * 選択はルーレット選択かサイズ2のトーナメント選択．突然変異率（反転）mut，交叉率crossover
 * コンパイルして実行すると，標準出力に各世代の情報（平均適応度とか）が出力される．
 * 例えば，コマンドプロンプト上で，「>java Ga >result.dat」などとすると，
 * result.datに結果が書き出されるので，それをエクセルなどにドロップすると，表形式で
 * 編集でき，グラフが作成できる．
 * ほぼCだと思って書いたものなので，クラスなどのJava特有のものはほとんど使っていません．
 */

import java.util.*;

public class Ga {
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
               //Gaクラスのオブジェクトを生成（Ga()の中身init()関数が実行される）
               Ga ga= new Ga();
               //GAを実行
               ga.go();
       }

       //決められた世代数だけ実行
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

       //コンストラクタ
       //オブジェクトを作ったときに呼ばれる関数．この場合init()関数を実行する．
       //つまりプログラムを実行する時にはいつでもinit()関数が一度だけ実行されるということ．
       //なので，変数を初期化する作業は，init()関数の中に書けばよいことになる．
       Ga(){
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

       void evaluate(){
               //各個体の表現型value[i]と適応度fitness[i]を求めて代入する
               for(int j=0;j<N;j++){
                       value[j]= getValue(j);
                       fitness[j]= function(value[j]);
               }
       }

       double getValue(int p){
               //p番目の個体の遺伝子をMビットのビット列だとみなして10進数に変換し，2^M-1で割った値を返す．
               long value= 0;
               long cb= 1;
               for(int i=M-1;i>=0;i--){
                       value+= gene[p][i]*cb;
                       cb*=2.0;
               }
               return((double)value/((double)Math.pow(2, M)-1.0));
       }

       //適応度を決める関数（0<=x<=1）
       //この場合はy=x^2のyを返す．
       double function(double x){
       //      return(x*x);
               return(Math.sin(17.0*x)*Math.cos(35.0*x)+1.5);
       }


       //実験の状況を表示する関数．ほしいデータにあわせて適宜追加したり，
       //コメントアウトしたりして使ってください．
       void printStatus(){
               if(gen==1){
                       System.out.print("gen\taverage_fitness\tbest_fitness\tbest_value\t'best_code'\n");
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
               //System.out.print(String.format("%04d\t%04.9f\t%04.9f\t%04.9f\t\'", gen, fitness_average/(double)N, fitness_best, value_best));
               for(int j=0;j<M;j++){
                       System.out.print(gene[no_best][j]);
               }
               System.out.print("'\n");
       }

       //進化操作をする関数
       void evolve(){
               //gene_nextを初期化．
               gene_next= new int[N][M];
               //次世代に必要な個体数分だけ選択の作業を繰り返す．
               for(int i=0;i<N;i++){
                       //1.選択
                       //ルーレット選択で選んだ子孫を残す個体の番号を代入
                       int o0= getParentNoByRouletteSelection();
                       int o1= getParentNoByRouletteSelection();

               //      int o0= getParentNoByTournamentSelection();
               //      int o1= getParentNoByTournamentSelection();

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