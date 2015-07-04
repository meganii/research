/*��
 * ��������������GA�Υ���ץ롥
 * ��ʪ���礭�������������size[]����ʪ�ν����٤����������importance[]��
 * ����������礭����������ѿ�max_size�򿷤��������
 * ��������ϳƲ�ʪ�������ʣ��ˤ�����ʤ��ʣ��ˤ����б�����
 * �������������function_oshiire()�ؿ���Ŭ���٤�׻����Ƥ��롥
 * Ŭ���٤ϡ�������ʪ���礭���ι�פ�max_size�ʲ��ξ����礭���ι�ס�
 * Ķ���Ƥ��ޤä�����0�Ȥ��롥
 * �����������ѹ������ɲ����ˤĤ��Ƥ�ɽ�������Ƥ���ޤ���
 */

import java.util.*;

public class Ga_oshiire {
//-------------�ɲä�������-------------
	//M���ܤβ�ʪ���礭��
	double size[]= {11,6,31,13,10,29,30,35,31,28,15,12,38,43,20,15,45,58,23,34};
	//M���ܤβ�ʪ�ν�����
	double importance[]= {35,34,46,28,38,47,45,55,40,52,44,33,55,63,39,31,60,70,40,43};
	//����������礭��
	double max_size= 200;
//-------------�ɲä����ޤ�-------------


	//���ο�
	int N= 30;
	//�����Ҥ�Ĺ��
	int M= 20;
	//�ǽ������
	int gen_last= 100;
	//���ߤ��������1���饹�����ȡ�
	int gen= 1;
	//�����Ѱ�Ψ
	double mut=1.0/30.0;
	//��Ψ
	double crossover= 100.0/100.0;
	//N���Ρ�M�Ĥΰ����Ҥ��Ǽ����Int��������gene[i][j]
	//�����ϳư����ҤϥӥåȤ�����boolean���ǽ�ʬ�ʤ�����ɡ�
	//i���ܤθ��Τ�j���ܤΰ����Ҥ��ͤ򼨤���
	int gene[][]= new int[N][M];
	//��������ΰ����Ҥ���Ū�ˤ���뤿��˻Ȥ�����
	int gene_next[][]= new int[N][M];
	//i���ܤθ��Τ�Ŭ���٤��Ǽ���뤿�������
	double fitness[]= new double[N];
	//i���ܤθ��Τ�j���ܤ��ѿ����ͤ��Ǽ���뤿�������
	double value[]= new double[N];
	//������ʿ���ȯ�������뤿��Υ��֥�������
	//r.nextDouble()�Ȥ���ȡ�0<=x<1���ϰϤΥ�����ʼ¿���double���ˤ��֤��Ƥ���롥
	static Random r= new Random();

	public static void main(String[] args) {
		Ga_oshiire ga_oshiire= new Ga_oshiire();
		ga_oshiire.go();
	}
	//���󥹥ȥ饯��
	//���֥������Ȥ��ä��Ȥ��˸ƤФ��ؿ������ξ��init()�ؿ���¹Ԥ��롥
	//�Ĥޤ�ץ�����¹Ԥ�����ˤϤ��ĤǤ�init()�ؿ������٤����¹Ԥ����Ȥ������ȡ�
	//�ʤΤǡ��ѿ������������Ȥϡ�init()�ؿ�����˽񤱤Ф褤���Ȥˤʤ롥
	Ga_oshiire(){
		init();
	}

	//���������ؿ�
	void init(){
		//�����seed�������seed������Ū�����ꤹ��Ȥ����ͤ��б�����������ФƤ����
		//r.setSeed(100);
		
		//gene�ν���������줾��˥������0��1������
		//�Ĥ��Ǥ�fitness�⤹�٤�0�ˡ�
		for(int i=0;i<N;i++){
			for(int j=0;j<M;j++){
				gene[i][j]= (int)(r.nextDouble()*2.0);
			}
			fitness[i]= 0;
		}
	}

	void go(){
		for(gen=1;gen<=gen_last;gen++){
			//Ŭ����ɾ��
			evaluate();
			//���ߤν��Ĥ��ͻҤ�ɽ������
			printStatus();
			//Ŭ���٤˴�Ť��ƽ��Ĥ�ʲ�������
			evolve();
		}
	}


	void evaluate(){
		//�Ƹ��Τ�ɽ����value[i]��Ŭ����fitness[i]�������������
		for(int j=0;j<N;j++){
			//value[j]= getValue(j);
			//�ѹ���
			//�������Ĥ��ä�i���ܤθ��Τ�Ŭ���٤��֤��ؿ�function_oshiire(i)��
			//�Ȥä�Ŭ���٤�����
			fitness[j]= function_oshiire(j);
		}
	}

	//------�ɲä�������-------
	double function_oshiire(int p){
		//���������������ʪ���礭���ι��
		int sum_size= 0;
		//���줿��ʪ�ν����٤ι��
		int sum_importance= 0;
		
		//0����M-1���ܤβ�ʪ���줾��ˤĤ��ơ�����
		for(int i=0;i<M;i++){
			//���������Ĵ�٤�1���ä��顥����
			if(gene[p][i]==1){
				//���Τ��������Ƚ����٤��פ˲û�
				sum_size+= size[i];
				sum_importance+= importance[i];
			}
		}
		//�⤷�礭������������κ��祵��������äƤ�����
		//Ŭ���٤�0�ʤʤˤ�������ʤ��ä��ˤˤ���
		if(sum_size>max_size){
			//���ɽ���Ѥ�valuep]���礭���ι��0������Ƥ���
			value[p]= 0;
			//Ŭ����0���֤�
			return(0);
		}else{
			//���ɽ���Ѥ�valuep]���礭���ι�פ�����Ƥ���
			value[p]= sum_size;
			//����ʳ��Ͻ����٤ι�פ��֤�
			return(sum_importance);
		}

	}
	//------�ɲä����ޤ�-------


	//�¸��ξ�����ɽ������ؿ����ۤ����ǡ����ˤ��碌��Ŭ���ɲä����ꡤ
	//�����ȥ����Ȥ����ꤷ�ƻȤäƤ���������
	void printStatus(){
		if(gen==1){
			System.out.print("gen\taverage_fitness\tbest_fitness\tbest_size\t'best_code'\n");
		}
		//���ߤ�������ͻҤ�ɽ��
		//\t�ϥ��ֵ��桤\n�ϲ��Ԥ��̣����ʸ���ʥ��������ץ������󥹤ȸ����ޤ�����
		//Ŭ���٤�ʿ�ѤȺǹ��ͤ�ɽ��
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
		//gene_next��������
		gene_next= new int[N][M];
		//�������ɬ�פʸ��ο�ʬ��������κ�Ȥ򷫤��֤���
		for(int i=0;i<N;i++){
			//1.����
			//�롼��å�������������¹��Ĥ����Τ��ֹ������
			int o0= getParentNoByRouletteSelection();
			int o1= getParentNoByRouletteSelection();
		
		//	int o0= getParentNoByTournamentSelection();
		//	int o1= getParentNoByTournamentSelection();
		
			//2.��
			int cpoint= 0;
			//�򺵤�����������������cpoint������
			if(r.nextDouble()<crossover){
				cpoint= (int)(r.nextDouble()*(M+1));
			}
			//cpoint�����ޤǤϿƸ���o0�ΰ����Ҥ�����Ѥ�
			for(int j=0;j<cpoint;j++){
				gene_next[i][j]= gene[o0][j];
			}
			//cpoint�ʹߤϿƸ���o1�ΰ����Ҥ�����Ѥ�
			for(int j=cpoint;j<M;j++){
				gene_next[i][j]= gene[o1][j];
			}
			//3.�����Ѱ�
			//�����Ѱۤ�ä���
			for(int j=0;j<M;j++){
				if(r.nextDouble()<mut){
					//�����Ҥ��ͤ�ȿž������
					//�ѹ���
					gene_next[i][j]= 1-gene_next[i][j];					
				}
			}
		}
		//gene�λ������gene_next����ȤȤ��ơ�������夵����
		//(�����Ǥϡ�����gene_next[][]���ͤ򤹤٤�����gene[][]�˥��ԡ�����Τ�Ʊ���ν������ȻפäƤ���������)
		gene= gene_next;
	}

	//�롼��å������Ԥäơ���¹������Τ��ֹ����롥
	int getParentNoByRouletteSelection(){
		//����θ��Τ��ֹ�2�Ĥ�����������
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
		//�⤷���٤Ƥθ��Τ�Ŭ���٤�0���ä��顤������ˤ��������Τ��ֹ���֤���
		return((int)(r.nextDouble()*N));
	}

	//�ȡ��ʥ��������Ԥäơ���¹������Τ��ֹ����롥
	int getParentNoByTournamentSelection(){
		//����θ��Τ��ֹ�2�Ĥ�����������
		int parent1= (int)(r.nextDouble()*N);
		int parent2= (int)(r.nextDouble()*N);

		//parent1��Ŭ���٤������⤫�ä��顤
		if(fitness[parent1]>fitness[parent2]){
			//�����ֹ���֤�
			return(parent1);
		}else{
			//����ʳ���parent2���֤�
			return(parent2);
		}
	}

}
