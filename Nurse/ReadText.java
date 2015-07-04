import java.io.*;

public class ReadText {
	public static void main(String[] args) {
	    double grade[] = new double[10];
		try {
			FileReader in = new FileReader("grade.txt");
			StreamTokenizer st = new StreamTokenizer(in);
		
			int i=0;
			while( st.nextToken() != StreamTokenizer.TT_EOF){
			    System.out.print(st.nval);
			    grade[i] = st.nval;
			    i++;
			    
			}
			
			
			//System.out.print(grade[0]);
			in.close();
		}catch (IOException ie){
			System.out.println("ファイルがありません");
		}catch (Exception e){
			System.out.println("ファイル指定がありません");
		}
	}

}