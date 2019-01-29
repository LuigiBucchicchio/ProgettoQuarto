package quartoclassi;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections; //serve per shuffle
import openclassi.Giocatore;
import openclassi.Pedina;
import openclassi.Scacchiera;

public class Quarto {

	public static void Turno (Giocatore g, Scacchiera s, Controllore c, ArrayList<Pedina> listaPedine){
		// controllo file delle pedine se è vuoto o meno
		c.controllaFilePedine();
		//lettura del file Pedine
		listaPedine=LetturaFile.letturaPedine(c);
		//controllo di eventuali duplicati nell'arrayList di pedine
		c.controllaArrayList(listaPedine);
		// lettura del file della scacchiera
		LetturaFile.letturaScacchiera(c,s);
		//controllo duplicati nella scacchiera
		c.controllaScacchiera(s);
		//controllo della vittoria precedente al posizionamento
		c.controllaVittoria(s, g);
		//controllo della pedina che si sta per mettere è un duplicato in scacchiera
		c.controllaDuplicato(listaPedine.get(0), s);
		// inserimento pedina. Poi aggiornamento dell'arrayList e scrittura dei file.
		if(listaPedine.get(0).getCaratteristiche()!=null){
			g.posizionaPedina(listaPedine.get(0), s);
			ScritturaFile.scritturaScacchiera(c,s);
			listaPedine.remove(0);
			Collections.shuffle(listaPedine);
			ScritturaFile.scritturaPedine(c,listaPedine);
		}
		else {
			System.out.println("Errore nel file delle pedine. Inserimento impossibile.");
			System.exit(12);
		}
		//stampa del risultato dopo posizionamento in console
		s.stampaScacchiera(s.getScacchiera());
		//controlla vittoria dopo il posizionamento
		c.controllaVittoria(s, g);
		// controllo pareggio
		c.controllaPareggio(s);
		//prossimo turno
		g.fineTurno();
		System.out.println("Turno successivo!");
	}
	public static void main(String[] args) throws IOException {
		// creazione giocatori, scacchiera, file pedine ed il conseguente controllore, creazione arraylist
		Giocatore g1=new Giocatore("Luigi");
		Giocatore g2=new Giocatore("Mario");
		Scacchiera s=new Scacchiera(4,4);
		final File filePedine=new File("pedine.txt");
		final File fileScacchiera=new File("scacchiera.txt");
		Controllore c=new Controllore(filePedine,fileScacchiera);
		ArrayList<Pedina> listaPedine= new ArrayList<Pedina>();
		System.out.println("viene lanciata la moneta");
		if(c.lancioMoneta()==0){
			System.out.println("inizia Luigi");
			for(int i=0;i<(s.getRighe()*s.getColonne())/2;i++){
				Turno(g1, s, c, listaPedine);
				Turno(g2, s, c, listaPedine);
			}
		}else{
			System.out.println("inizia Mario");
			for(int i=0;i<(s.getRighe()*s.getColonne())/2;i++){
				Turno(g2, s, c, listaPedine);
				Turno(g1, s, c, listaPedine);
			}
		}
	}
}