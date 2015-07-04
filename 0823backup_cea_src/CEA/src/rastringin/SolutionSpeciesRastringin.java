package rastringin;

import rastringin.RastringinSpecies;

public class SolutionSpeciesRastringin extends RastringinSpecies{
	
	double gene[][] = new double[N][dimension];
	double gene_next[][] = new double[N][dimension];
	
	//コンストラクタで引数 std1,std2を参照
	SolutionSpeciesRastringin(double std1, double std2) {
		super(std1, std2);
		std = 0.25;		//解候補の標準偏差

		
		//解候補集団
		for(int i=0;i<gene.length;i++){
			for(int j=0;j<dimension;j++){
				gene[i][j] = r.nextDouble()*2*xrange - xrange;	//-5.12〜5.12の間でランダムに与える
			}
			
		}

	}

	void selection(){
		/*
		//test
		for(int i=0;i<gene.length;i++){
			System.out.print("selection前" + " " );
			System.out.println(gene[i]);
		}
		*/
		
		//double gene_next[] = new double[N];
		for(int i=0;i<gene.length;i++){
			for(int j=0;j<dimension;j++){
				int o0 = getParentNoByTournamentSelection();

				//System.out.println(o0);		//test
				
				gene_next[i][j] = gene[o0][j];
			}

		}
		gene = gene_next;
		
		/*
		//test
		for(int i=0;i<gene.length;i++){
			System.out.print("selection後" + " " );
			System.out.println(gene[i]);
		}
		*/
	}
	
	void operation(){
		/*
		//test
		for(int i=0;i<gene.length;i++){
			System.out.print("opelaton前" + " " );
			System.out.println(gene[i] + " " + gene_next[i]	);
		}
		*/
		
		
		//double gene_next[] = new double[N];
		for(int i=0;i<gene.length;i++){
			for(int j=0;j<dimension;j++){
				if(r.nextDouble() < mut){
					gene_next[i][j] = gene[i][j] + r.nextGaussian()*std;
				}
				//-5.12<x<5.12になるまでルーレットを回す
				if(gene_next[i][j] < -5.12){
					while(gene_next[i][j] < -5.12){
						gene_next[i][j] = gene[i][j] + r.nextGaussian()*std;
					}
				}
				else if(gene_next[i][j] > 5.12){
					while(gene_next[i][j] > 5.12){
						gene_next[i][j] = gene[i][j] + r.nextGaussian()*std;
					}
				}
				else{
					gene_next[i][j] = gene[i][j];
				}
			}
		}
		/*
		//test
		for(int i=0;i<gene.length;i++){
			System.out.print("opelaton後" + " " );
			System.out.println("gene_next" + gene_next[i]);
		}
		*/
		
		gene = gene_next;
		
		/*
		//test
		for(int i=0;i<gene.length;i++){
			System.out.print("opelaton後" + " " );
			System.out.println(gene[i]);
		}
		*/
	}
	
}

/*
//解候補集団
for(int i=0;i<N;i++){
	solsg.gene[i] = r.nextDouble()*2*xrange - xrange;	//-5.12〜5.12の間でランダムに与える
}
*/