package cea.stop.go;

import cea.stop.go.Species;

public class SolutionSpecies extends Species {
	
	//コンストラクタで引数 std1,std2を参照
	SolutionSpecies(double std1, double std2) {
		super(std1, std2);
	}

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
	
	void operation(){
		for(int i=0;i<gene.length;i++){
			if(r.nextDouble() < mut){
				gene_next[i] = gene[i] + r.nextGaussian()*std;
				
				//加える値が0以上になるまでルーレットを回す
				if(gene_next[i] < 0){
					while(gene_next[i] < 0){
						gene_next[i] = gene[i] + r.nextGaussian()*std;
					}
				}
			}
			else{
				gene_next[i] = gene[i];
			}
					
		}
		gene = gene_next;
	}
}