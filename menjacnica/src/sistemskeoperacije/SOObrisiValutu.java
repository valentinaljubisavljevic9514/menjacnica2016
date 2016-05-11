package sistemskeoperacije;

import java.util.List;

import menjacnica.Valuta;

public class SOObrisiValutu {

	public static void izvrsi(Valuta valuta, List<Valuta> kursnaLista){
		if (!kursnaLista.contains(valuta))
			throw new RuntimeException("Valuta ne postoji u kursnoj listi");
		
		kursnaLista.remove(valuta);
	}
	
}
