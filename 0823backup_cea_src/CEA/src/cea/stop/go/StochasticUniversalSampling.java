package cea.stop.go;

import java.util.Random;

public class StochasticUniversalSampling {
	
	int N =30;
	int fitness[] = new int[N];
	int ary_fitness[][] = new int[fitness.length][2];
	int next_ary[][] = new int[N][2];
	static Random r = new Random();
	
	StochasticUniversalSampling(int[] fitness) {
		this.fitness = fitness;
	}

	//順番を有するFitnessTableの作成
	void sortFitnessTable(){
		for(int i=0;i<fitness.length;i++){
			ary_fitness[i][0] = fitness[i];
			ary_fitness[i][1] = i;
		}
		
		//配列の上下を入れ替える。そのときに、順番も対応させる。
		for(int i=0;i<fitness.length-1;i++){
			for(int j=fitness.length-1;j>i;j--){
				int tmp[] = new int[2];
				if( ary_fitness[j][0] < ary_fitness[j-1][0] ){
					for(int k=0;k<2;k++)
						tmp[k] = ary_fitness[j][k];
					for(int k=0;k<2;k++)
						ary_fitness[j][k] = ary_fitness[j-1][k];
					for(int k=0;k<2;k++)
						ary_fitness[j-1][k] = tmp[k];
				}
			}
		}
		
		//降順に並べ替え
		int index =0;
		int tmp[][] = new int[fitness.length][2];
		for(int i=fitness.length-1;i>=0;i--){
			for(int k=0;k<2;k++)
				tmp[index][k] = ary_fitness[i][k];
			index++;
		}
		ary_fitness = tmp;
	}
	
	
	//Stochastic universal sampling
	//
	void selectionSUS(){	
		double ptrs[] = new double[N];
		double start = r.nextDouble()*(getTotalFitness()/N);
		//System.out.println("total=" + getTotalFitness());
		for(int i=0;i<N;i++){
			ptrs[i] = start + i*getTotalFitness()/N;
			//System.out.print("ptrs=" + ptrs[i] + " ");
		}
		//System.out.println();
		
		if(fitness[0]==0 && fitness[N-1]==0){
			for (int i = fitness.length - 1; i >= 0; i--)
				ptrs[i] = i + 1;
		}
		
		for(int j=0;j<N;j++){
			double tmp=0;
			for(int i=0;i<fitness.length;i++){		
				
				if( (tmp<=ptrs[j]) && (ptrs[j]<tmp+fitness[i]) ){
					for(int k=0;k<2;k++)
						next_ary[j][k] = ary_fitness[i][k];
					break;
				}
				else{
					tmp += fitness[i];
				}
			}
		}
		
		ary_fitness = next_ary;
	}
	
	//fitnessの合計を得る
	double getTotalFitness(){
		double total =0;
		for(int i=0;i<fitness.length;i++){
			total += fitness[i];
		}
		return total;
	}

}
