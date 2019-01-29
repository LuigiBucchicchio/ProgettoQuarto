package quartoclassi;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import openclassi.Giocatore;
import openclassi.Pedina;
import openclassi.Scacchiera;

public class Controllore {
	private File filePedine=new File("default.file");
	private File fileScacchiera=new File("default.file");

	//Costr. controllore con file
	public Controllore(File fp, File fs){
		this.filePedine=fp;
		this.fileScacchiera=fs;
	}

	//Costr. controllore senza file
	public Controllore(){

	}

	public File getFilePedine() {
		return filePedine;
	}

	public void setFilePedine(File filePedine) {
		this.filePedine = filePedine;
	}

	public File getFileScacchiera() {
		return fileScacchiera;
	}

	public void setFileScacchiera(File fileScacchiera) {
		this.fileScacchiera = fileScacchiera;
	}

	public int lancioMoneta(){
		int random=(int)Math.random();
		return random;
	}

	/* controlla grandezza massima dell'ArrayList
	e controlla che non ci siano duplicati all'interno dell'ArrayList
	 */
	public void controllaArrayList(ArrayList<Pedina> listaP){
		if(listaP.size()>16){
			System.out.println("Errore nel file pedine. Numero pedine non valido.");
			System.exit(12);
		}
		for(int i=0;i<listaP.size();i++){
			Pedina p=new Pedina(listaP.get(i).getCaratteristiche());
			String s=new String();
			s=listaP.get(i).getCaratteristiche();
			p=listaP.get(i);
			listaP.remove(listaP.get(i));
			for(int j=0;j<listaP.size();j++){
				if(s.compareTo(listaP.get(j).getCaratteristiche())==0){
					System.out.println("Errore nel file pedine. Duplicato presente.");
					System.exit(12);
				}
			}
			listaP.add(i, p);
		}
	}

	//controlla che il file Pedine non sia vuoto
	public void controllaFilePedine(){
		FileReader fr;
		try {
			fr = new FileReader(this.filePedine);
			BufferedReader br= new BufferedReader(fr);
			if((br.readLine()==null)){
				System.out.println("Errore nel file pedine. File pedine vuoto.");
				System.exit(12);
			}
			br.close();
		} catch (IOException eccezioneFileInput) {
			System.out.println("Errore. File delle pedine non trovato");
			System.exit(11);
		}

	}

	//controlla che nella scacchiera per ogni posizione non vi sia un duplicato
	public void controllaScacchiera(Scacchiera s){
		Pedina p= new Pedina("default");
		int contaCopie=0;
		boolean esistePedina=true;
		for(int righe=0;righe<s.getRighe();righe++){
			for(int colonne=0;colonne<s.getColonne();colonne++){
				if(s.getScacchiera()[righe][colonne]!=null){
					p.setCaratteristiche(s.getScacchiera()[righe][colonne].getCaratteristiche());
					contaCopie=0;
				} else{
					esistePedina=false;
				}
				if(esistePedina==true){
					for(int i=0;i<s.getRighe();i++){
						for(int j=0;j<s.getColonne();j++){
							if(s.getScacchiera()[i][j]!=null){
								if(p.getCaratteristiche().equals(s.getScacchiera()[i][j].getCaratteristiche())==true){
									contaCopie++;
								}
							}
						}
					}
				}
				//se trova un copia in più rispetto a sé stessa
				if(contaCopie>1){
					System.out.println("Errore nel file scacchiera. Duplicato presente.");
					System.exit(13);
				}
				esistePedina=true;
			}
		}
	}

	/*controlla se, con l'aggiungersi di questa pedina nella scacchiera
	si sta per verificare un duplicato*/
	public void controllaDuplicato(Pedina p, Scacchiera s){
		for(int i=0;i<s.getRighe();i++){
			for(int j=0;j<s.getColonne();j++){
				if(s.getScacchiera()[i][j]!=null){
					if(p.getCaratteristiche().equals(s.getScacchiera()[i][j].getCaratteristiche())==true){
						System.out.println("Errore combinato: l'inserimento della pedina genera un duplicato nella scacchiera.");
						System.exit(13);
					}
				}
			}
		}
	}

	//metodo per resettare i file ai valori di default grazie ai rispettivi file
	public void ResetFile(File fp, File fs){
		File filePedineDefault=new File("pedineDefault.txt");
		File fileScacchieraDefault=new File("scacchieraDefault.txt");
		try {
			FileWriter fw1 = new FileWriter(fp);
			BufferedWriter bw1=new BufferedWriter(fw1);
			FileReader fr1 = new FileReader(filePedineDefault);
			BufferedReader br1 = new BufferedReader(fr1);
			while(br1.ready()){
				bw1.write(br1.readLine());
				bw1.newLine();
			}
			br1.close();
			bw1.close();
			FileWriter fw2 = new FileWriter(fs);
			BufferedWriter bw2=new BufferedWriter(fw2);
			FileReader fr2 = new FileReader(fileScacchieraDefault);
			BufferedReader br2 = new BufferedReader(fr2);
			while(br2.ready()){
				bw2.write(br2.readLine());
				bw2.newLine();
			}
			br2.close();
			bw2.close();
		}catch (IOException eccezioneFileInput){
			System.out.println("Errore. File di default o di gioco mancanti");
			System.exit(11);
		}
	}
	/*controlla che la scacchiera sia piena e siccome il controllo lo fa dopo la vittoria
	è sicuramente un pareggio*/
	public void controllaPareggio( Scacchiera s){
		boolean pieno=true;
		for(int i=0;i<s.getRighe();i++){
			for(int j=0;j<s.getColonne();j++){
				if(s.getScacchiera()[i][j]==null)
					pieno=false;
			}
		}
		if(pieno==true){
			System.out.println("Partita terminata. Risultato: PAREGGIO!");
			ResetFile(this.filePedine,this.fileScacchiera);
			System.exit(2);
		}
	}

	//controlla la vittoria per ogni combinazione
	public void controllaVittoria(Scacchiera s, Giocatore g){

		boolean a=this.controllaEsistenzaVittoria(s);
		if(a==true){
			//controllo per Alto-Basso
			this.controllaVittoriaRighe(s, 0, g);
			this.controllaVittoriaColonne(s, 0, g);
			this.controllaVittoriaDiagonale(s, 0, g);
			// Controllo per Bianco-Nero
			this.controllaVittoriaRighe(s, 1, g);
			this.controllaVittoriaColonne(s, 1, g);
			this.controllaVittoriaDiagonale(s, 1, g);  
			// Controllo per Tondo-Quadrato	
			this.controllaVittoriaRighe(s,2, g);
			this.controllaVittoriaColonne(s, 2, g);
			this.controllaVittoriaDiagonale(s, 2, g);
			// Controllo per Bucato-Pieno
			this.controllaVittoriaRighe(s, 3, g);
			this.controllaVittoriaColonne(s,3, g);
			this.controllaVittoriaDiagonale(s, 3, g);
		}
	}

	/* controlla l'esistenza della vittoria(Righe, Colonne e Diagonale in un OR
   per aumentare l'efficenza nei primi turni*/
	public boolean controllaEsistenzaVittoria(Scacchiera s){
		boolean esisteVrighe=true;
		boolean esisteVcolonne=true;
		boolean esisteVdiagonale=true;
		for(int i=0;i<s.getRighe();i++){
			if(s.getScacchiera()[i][0]==null)
				esisteVrighe=false;
		}
		for(int j=0;j<s.getColonne();j++){
			if(s.getScacchiera()[0][j]==null)
				esisteVcolonne=false;
		}
		if((s.getScacchiera()[0][0]==null)||(s.getScacchiera()[(s.getRighe()-1)][(s.getColonne()-1)]==null))
			esisteVdiagonale=false;
		if((esisteVrighe==true)||(esisteVcolonne==true)||(esisteVdiagonale==true))
			return true;
		else
			return false;
	}

	public void controllaVittoriaRighe(Scacchiera s, int indexchar, Giocatore g){
		ArrayList<String> stringhe=new ArrayList<String>();
		for(int i=0;i<s.getRighe();i++){
			boolean check=true;
			for(int j=0;j<s.getColonne();j++){
				if(s.getScacchiera()[i][j]==null){
					check=false;
					break;
				}
				stringhe.add(j, s.getScacchiera()[i][j].getCaratteristiche());
			}
			if((check==true)&&(stringhe.get(0).charAt(indexchar)==stringhe.get(1).charAt(indexchar))&&(stringhe.get(0).charAt(indexchar)==stringhe.get(2).charAt(indexchar))&&(stringhe.get(0).charAt(indexchar)==stringhe.get(3).charAt(indexchar))){
				System.out.println("Partita terminata. Risultato: VITTORIA DI "+g.getNome()+"! Allineamento vincente in una riga.");
				ResetFile(this.filePedine,this.fileScacchiera);
				System.exit(1);
			}
		}
	}

	public void controllaVittoriaColonne(Scacchiera s, int indexchar, Giocatore g){
		ArrayList<String> stringhe=new ArrayList<String>();
		for(int j=0;j<s.getColonne();j++){
			boolean check=true;
			for(int i=0;i<s.getRighe();i++){
				if(s.getScacchiera()[i][j]==null){
					check=false;
					break;
				}
				stringhe.add(i, s.getScacchiera()[i][j].getCaratteristiche());
			}
			if((check==true)&&(stringhe.get(0).charAt(indexchar)==stringhe.get(1).charAt(indexchar))&&(stringhe.get(0).charAt(indexchar)==stringhe.get(2).charAt(indexchar))&&(stringhe.get(0).charAt(indexchar)==stringhe.get(3).charAt(indexchar))){
				System.out.println("Partita terminata. Risultato: VITTORIA DI "+g.getNome()+"! Allineamento vincente in una colonna.");
				ResetFile(this.filePedine,this.fileScacchiera);
				System.exit(1);
			}
		}
	}

	public void controllaVittoriaDiagonale(Scacchiera s, int indexchar, Giocatore g){
		ArrayList<String> stringhe=new ArrayList<String>();
		boolean check=true;
		for(int i=0;i<Math.min(s.getRighe(), s.getColonne());i++){
			if(s.getScacchiera()[i][i]==null){
				check=false;
				break;
			}
			stringhe.add(i, s.getScacchiera()[i][i].getCaratteristiche());
		}
		if((check==true)&&(stringhe.get(0).charAt(indexchar)==stringhe.get(1).charAt(indexchar))&&(stringhe.get(0).charAt(indexchar)==stringhe.get(2).charAt(indexchar))&&(stringhe.get(0).charAt(indexchar)==stringhe.get(3).charAt(indexchar))){
			System.out.println("Partita terminata. Risultato: VITTORIA DI "+g.getNome()+"! Allineamento vincente sulla diagonale principale.");
			ResetFile(this.filePedine,this.fileScacchiera);
			System.exit(1);
		}
		check=true;
		int j=(Math.min(s.getRighe(), s.getColonne()))-1;
		for(int i=0;i<Math.min(s.getRighe(), s.getColonne());i++){
			if(s.getScacchiera()[j][i]==null){
				check=false;
				break;
			}
			stringhe.add(i, s.getScacchiera()[j][i].getCaratteristiche());
			j--;
		}
		if((check==true)&&(stringhe.get(0).charAt(indexchar)==stringhe.get(1).charAt(indexchar))&&(stringhe.get(0).charAt(indexchar)==stringhe.get(2).charAt(indexchar))&&(stringhe.get(0).charAt(indexchar)==stringhe.get(3).charAt(indexchar))){
			System.out.println("Partita terminata. Risultato: VITTORIA DI "+g.getNome()+"! Allineamento vincente sulla antidiagonale.");
			ResetFile(this.filePedine,this.fileScacchiera);
			System.exit(1);
		}
	}
}