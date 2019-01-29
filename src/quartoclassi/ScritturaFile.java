package quartoclassi;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import openclassi.Pedina;
import openclassi.Scacchiera;

public class ScritturaFile {
	/* scrive la scacchiera. Esegue il trim di ogni riga*/
	public static void scritturaScacchiera(Controllore c, Scacchiera s){
		FileWriter fw;
		try {
			fw = new FileWriter(c.getFileScacchiera());
			BufferedWriter bw= new BufferedWriter(fw);
			for(int i=0;i<s.getRighe();i++){
				String riga=new String();
				for(int j=0;j<s.getColonne();j++){
					if(s.getScacchiera()[i][j]==null)
						riga += "* ";
					else 
						riga+= s.getScacchiera()[i][j].getCaratteristiche() + " ";
				}
				bw.write(riga.trim());
				if(i!=3)
					bw.newLine();
			}
			bw.close();
		} catch (IOException eccezioneFileInput) {
			System.out.println("Errore. File della scacchiera non trovato");
			System.exit(11);
		}
	}
	/*scrive il file Pedine. Esegue il trim di ogni riga*/
	public static void scritturaPedine(Controllore c, ArrayList<Pedina> listaP){
		FileWriter fw;
		try {
			fw = new FileWriter(c.getFilePedine());
			BufferedWriter bw=new BufferedWriter(fw);
			for(int i=0; i<listaP.size();i++){
				String riga=new String();
				if(listaP.get(i)!=null){
					riga += (listaP.get(i)).getCaratteristiche();
					bw.write(riga.trim());
					if(i+1!=listaP.size())
						bw.newLine();
				}
			}
			bw.close();
		} catch (IOException eccezioneFileInput) {
			System.out.println("Errore. File delle pedine non trovato");
			System.exit(11);
		}
	}
}