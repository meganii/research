package cea.stop.go;

import cea.stop.go.Species;

public class SolutionSpeciesRastringin extends Species{
	
	//コンストラクタで引数 std1,std2を参照
	SolutionSpeciesRastringin(double std1, double std2) {
		super(std1, std2);
		
		//解候補集団
		for(int i=0;i<N;i++){
			gene[i] = r.nextDouble()*2*xrange - xrange;	//-5.12〜5.12の間でランダムに与える
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
			int o0 = getParentNoByTournamentSelection();
			
			//test
			//System.out.println(o0);
			
			gene_next[i] = gene[o0];
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
			if(r.nextDouble() < mut){
				gene_next[i] = gene[i] + r.nextGaussian()*std;
			
				//-5.12<x<5.12になるまでルーレットを回す
				if(gene_next[i] < -5.12){
					while(gene_next[i] < -5.12){
						gene_next[i] = gene[i] + r.nextGaussian()*std;
					}
				}
				else if(gene_next[i] > 5.12){
					while(gene_next[i] > 5.12){
						gene_next[i] = gene[i] + r.nextGaussian()*std;
					}
				}
			}
			else{
				gene_next[i] = gene[i];
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