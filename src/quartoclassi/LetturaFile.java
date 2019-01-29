package quartoclassi;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import openclassi.Pedina;
import openclassi.Scacchiera;

public class LetturaFile {
	/*legge file Pedine. Ritorna un ArrayList di Pedine.
  Inoltre, effettua un controllo per la formattazione delle pedine*/
	public static ArrayList<Pedina> letturaPedine(Controllore c){
		FileReader fr;
		try {
			fr = new FileReader(c.getFilePedine());
			BufferedReader br = new BufferedReader(fr);
			String riga="default";
			ArrayList<Pedina> listaPedine= new ArrayList<>();
			while(riga!=null){
				riga=br.readLine();
				if(riga!=null){
					if(riga.length()!=4){
						System.out.println("Errore nel file pedine. Pedina non valida.");
						System.exit(12);
					}
					if((riga.charAt(0)!= 'B') && (riga.charAt(0) != 'A')){
						System.out.println("Errore nel file pedine. Pedina non valida.");
						System.exit(12);
					}
					if((riga.charAt(1)!= 'W') && (riga.charAt(1) != 'N')){
						System.out.println("Errore nel file pedine. Pedina non valida.");
						System.exit(12);
					}
					if((riga.charAt(2)!= 'T') && (riga.charAt(2) != 'Q')){
						System.out.println("Errore nel file pedine. Pedina non valida.");
						System.exit(12);
					}
					if((riga.charAt(3)!= 'F') && (riga.charAt(3) != 'P')){
						System.out.println("Errore nel file pedine. Pedina non valida.");
						System.exit(12);
					}
					Pedina k=new Pedina(riga);
					listaPedine.add(k);
				}
			}
			br.close();
			return listaPedine;
		} catch (IOException eccezioneFileInput) {
			System.out.println("Errore. File delle pedine non trovato");
			System.exit(11);
			return null;
		}
	}
	/*legge file Scacchiera. Avendo come parametro un'istanza di Controllore e di Scacchiera,
 il metodo agisce sui suoi attributi. Quindi non necessita di un ritorno.
	 */
	public static void letturaScacchiera(Controllore c, Scacchiera s){
		FileReader fr;
		try {
			fr= new FileReader(c.getFileScacchiera());
			BufferedReader br= new BufferedReader(fr);
			String riga;
			int contariga=0;
			while((riga=br.readLine())!=null){
				String[] celle;
				celle= riga.split(" ");
				if((celle[contariga].length() != 4)&&(celle[contariga].length()!=1))
					System.exit(13);
				for(int contacolonna=0; contacolonna<s.getRighe();contacolonna++){
					if((celle[contacolonna].length()!=4)&&(celle[contacolonna].length()!=1))
						System.exit(13);
					if(!celle[contacolonna].equals("*")){
						Pedina nuovaPedina=new Pedina(celle[contacolonna]);
						s.getScacchiera()[contariga][contacolonna]=nuovaPedina;
					}
					else{
						s.getScacchiera()[contariga][contacolonna]=null;
					}
				}
				contariga++;
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Errore. File della scacchiera non trovato");
			System.exit(11);
		}
	}
}