package openclassi;

public class Scacchiera {
	private int righe=0;
	private int colonne=0;
	private Pedina[][] scacchiera;
	public Scacchiera(int r, int c){
		this.righe=r;
		this.colonne=c;
		this.setScacchiera(new Pedina[this.righe][this.colonne]);
	}
	public void setrighe(int r){
		this.righe=r;
		setParamScacchiera();
	}
	public void setcolonne(int c){
		this.colonne=c;
		setParamScacchiera();
	}
	public int getRighe() {
		return righe;
	}
	public int getColonne(){
		return colonne;
	}
	// viene chiamato dopo il set righe o il set colonne per modificare anche l'attributo scacchiera
	public void setParamScacchiera(){
		this.scacchiera=new Pedina[this.righe][this.colonne];
	}

	/* stampa la scacchiera con gli spazi */
	public void stampaScacchiera(Pedina[][] scacchiera){
		for(int i=0;i<righe;i++){
			for(int j=0;j<colonne;j++){
				if(scacchiera[i][j]!=null){
					System.out.print(scacchiera[i][j].getCaratteristiche()+" ");
				}else{
					System.out.print("*"+" ");
				}
			}
			System.out.println("");
		}
	}
	public Pedina[][] getScacchiera() {
		return scacchiera;
	}

	public void setScacchiera(Pedina[][] scacchiera) {
		this.scacchiera = scacchiera;
	}

	/* conta le posizioni in cui non c'è una pedina: null */
	public int contaVuote(Scacchiera s){
		int conta=0;
		for(int i=0;i<s.getRighe();i++){
			for(int j=0;j<s.getColonne();j++){
				if(s.getScacchiera()[i][j]==null)
					conta++;
			}
		}
		if(conta==0){
			System.out.println("Partita terminata. Risultato: PAREGGIO.");
			System.exit(2);
		}
		return conta;
	}
}
