package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {

	
	private static MenjacnicaInterface menjacnica;
	private static MenjacnicaGUI glavniProzor;
	private static MenjacnicaTableModel model;
	private static DodajKursGUI dodajKurs;
	private static IzvrsiZamenuGUI izvrsiZamenu;
	private static ObrisiKursGUI obrisiKurs;
	
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menjacnica = new Menjacnica();
					glavniProzor = new MenjacnicaGUI();
					glavniProzor.setVisible(true);
					glavniProzor.setLocationRelativeTo(null);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(glavniProzor.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(glavniProzor.getContentPane(),
				"Autor: Valentina Ljubisavljevic, Verzija 2.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(glavniProzor.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(glavniProzor.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziSveValute() {
		model = (MenjacnicaTableModel)(glavniProzor.getTable().getModel());
		model.staviSveValuteUModel(menjacnica.vratiKursnuListu());

	}
	
	public static void prikaziDodajKursGUI() {
		
		dodajKurs  = new DodajKursGUI();
		dodajKurs.setLocationRelativeTo(glavniProzor.getContentPane());
		dodajKurs.setVisible(true);
	}
	
	public static void prikaziObrisiKursGUI() {
		
		if (glavniProzor.getTable().getSelectedRow() != -1) {
			model = (MenjacnicaTableModel) glavniProzor.getTable().getModel();
			obrisiKurs = new ObrisiKursGUI(model.vratiValutu(glavniProzor.getTable().getSelectedRow()));
			obrisiKurs.setLocationRelativeTo(glavniProzor.getContentPane());
			obrisiKurs.setVisible(true);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI() {
		if (glavniProzor.getTable().getSelectedRow() != -1) {
			model = (MenjacnicaTableModel)(glavniProzor.getTable().getModel());
			izvrsiZamenu = new IzvrsiZamenuGUI(model.vratiValutu(glavniProzor.getTable().getSelectedRow()));
			izvrsiZamenu.setLocationRelativeTo(glavniProzor.getContentPane());
			izvrsiZamenu.setVisible(true);
		}
	}
	
	public static void unesiKurs() {
		
		try {

			Valuta valuta = new Valuta();
			// Punjenje podataka o valuti
			valuta.setNaziv(dodajKurs.getTextFieldNaziv().getText());
			valuta.setSkraceniNaziv(dodajKurs.getTextFieldSkraceniNaziv().getText());
			valuta.setSifra((Integer)(dodajKurs.getSpinnerSifra().getValue()));
			valuta.setProdajni(Double.parseDouble(dodajKurs.getTextFieldProdajniKurs().getText()));
			valuta.setKupovni(Double.parseDouble(dodajKurs.getTextFieldKupovniKurs().getText()));
			valuta.setSrednji(Double.parseDouble(dodajKurs.getTextFieldSrednjiKurs().getText()));
			
			// Dodavanje valute u kursnu listu
			menjacnica.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			prikaziSveValute();
			
			
			
			//Zatvaranje DodajValutuGUI prozora
			dodajKurs.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(dodajKurs.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void izvrsiZamenu(Valuta valuta){
		try{
			double konacniIznos = 
					menjacnica.izvrsiTransakciju(valuta,
							izvrsiZamenu.getRdbtnProdaja().isSelected(), 
							Double.parseDouble(izvrsiZamenu.getTextFieldIznos().getText()));
		
			izvrsiZamenu.getTextFieldKonacniIznos().setText(""+konacniIznos);
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(izvrsiZamenu.getContentPane(), e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
	}
	}
	
	

	public static void obrisiValutu(Valuta valuta) {
		try{
			menjacnica.obrisiValutu(valuta);
			
			prikaziSveValute();
			obrisiKurs.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(obrisiKurs.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
}
