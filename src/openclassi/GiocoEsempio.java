package openclassi;

public class GiocoEsempio {

	public static void main(String[] args) {
		int righe=4;
		int colonne=4;
		Scacchiera s=new Scacchiera(righe,colonne);
		Giocatore g=new Giocatore("tizio");
		Pedina Pedone1=new Pedina("Pedone");
		g.posizionaPedina(Pedone1, s);
		s.stampaScacchiera(s.getScacchiera());
		g.fineTurno();
	}

}