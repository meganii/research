package rastringin;
import rastringin.RastringinSpecies;

public class CriteriaSpeciesRastringin extends RastringinSpecies {

	CriteriaSpeciesRastringin(double std1, double std2) {
		super(std1, std2);
		std = 1.0;		//判定基準の標準偏差
		

	}
	
	void selection(){
		for(int i=0;i<gene.length;i++){
			int o0 = getParentNoByTournamentSelection();
			gene_next[i] = gene[o0];
		}
		gene = gene_next;
	}
	
	void operation(){
		for(int i=0;i<gene.length;i++){
			if(r.nextDouble() < mut){
				gene_next[i] = gene[i] + r.nextGaussian()*std;


				if( gene_next[i] < 0){
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

/*
//判定基準集団の初期化
double add = 0;
crisg.gene[0] = getMinValue();
for(int i=1;i<N;i++){
	add += (getMaxValue() - getMinValue())/(N-1);
	crisg.gene[i] = getMinValue() + add;
	
}
*/