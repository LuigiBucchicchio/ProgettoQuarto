package openclassi;

public class Generatore {
	/*genera un intero FROM un numero intero TO un altro*/
	public static int generaInt(int from, int to){
		int numero=0;
		if(from==0){
			numero=(int) (Math.random()*to);
		}else{
			numero=(int) (Math.random()*(to-from)+from);
		}
		return numero;
	}
}