package openclassi;

public class Giocatore {
	private String nome="default";
	public Giocatore(String nome){
		this.nome=nome;
	}
	public String getNome(){
		return nome;
	}
	public void setNome(String nome){
		this.nome=nome;
	}
	/* posiziona la Pedina contando le posizioni vuote e mettendola in una di esse.
	   il Boolean fa terminare il ciclo appena posizionata */
	public void posizionaPedina(Pedina p, Scacchiera s){
		int n=s.contaVuote(s);
		int posizione=Generatore.generaInt(1, n);
		int conta=0;
		boolean posizionata=false;
		for(int i=0;((i<s.getRighe())&&(posizionata==false));i++){
			for(int j=0;((j<s.getColonne())&&(posizionata==false));j++){
				if(s.getScacchiera()[i][j]==null)
					conta++;
				if(posizione==conta){
					s.getScacchiera()[i][j]=p;
					posizionata=true;
				}
			}
		}
	}
	public void fineTurno(){
		System.out.println(this.getNome()+" dice: <<Ho finito il mio turno!>>");
	}
}
