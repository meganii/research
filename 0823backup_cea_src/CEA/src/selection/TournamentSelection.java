package selection;

public class TournamentSelection {
	public int getParentNoByTournamentSelection(){
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
}

/*
 * 	//トーナメント選択を行って，子孫を作る個体の番号を求める．
	int getParentNoByTournamentSelectionA(){
		//候補の個体の番号5つをランダムに選び、その中で一番よいものの番号を返す
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
 */