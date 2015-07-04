package cea.stop.go;

public class CriteriaSpecies extends Species {
	
	//コンストラクタでstd1,std2を初期化
	CriteriaSpecies(double std1, double std2) {
		super(std1, std2);
	}

	//判定基準のセレクション
	void selection(){
		StochasticUniversalSampling sus = new StochasticUniversalSampling(fitness);
		sus.sortFitnessTable();		//ary_fitnessの作成
		sus.selectionSUS();			//StochasticUniversalSampling
		
		for(int i=0;i<gene.length;i++){
			int o0 = sus.ary_fitness[i][1];
			gene_next[i] = gene[o0];
		}
		gene = gene_next;
	}
	
	//判定基準の遺伝的操作
	void operation(){
		for(int i=0;i<gene.length;i++){
			if(r.nextDouble() < mut){
				gene_next[i] = gene[i] + r.nextGaussian()*std;
			
			
				//突然変異をさせて、判定基準 < 0 のとき 0にする。
				if(gene_next[i] < 0){
					gene_next[i] = 0;
				}
			}
			else{
				gene_next[i] = gene[i];
			}
		}
		gene = gene_next;
	}
}
