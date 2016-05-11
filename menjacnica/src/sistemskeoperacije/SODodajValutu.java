package sistemskeoperacije;

import java.util.List;

import menjacnica.Valuta;

public class SODodajValutu {

	public static void izvrsi(Valuta valuta, List<Valuta> kursnaLista){
		if (valuta==null)
			throw new RuntimeException("Valuta ne sme biti null");
		
		if (kursnaLista.contains(valuta))
			throw new RuntimeException("Valuta je vec uneta u kursnu listu");
		
		kursnaLista.add(valuta);	
	}
	
}
