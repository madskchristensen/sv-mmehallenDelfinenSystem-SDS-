import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

class MedlemsListe implements ListeInterface{
	//danner liste som en attribute i MedlemsListe
	private ArrayList<Medlem> liste = new ArrayList<Medlem>();

	//etablere filen der bliver redigeret
	private String path = "./src/medlemmer.txt";
	private File f = new File(path);

	MedlemsListe(){

		try{
			// Vælger filen der skal læses fra og variablerne den læses
			ArrayList<LocalDate> betalingsHistorik = new ArrayList<LocalDate>();
			String navn, adresse, email, koen, medlemsskabstype, aktivitetstype;
			LocalDate foedselsdato;
			Scanner line = new Scanner(f);
			//Skan gennem filen, indtil der ikke er flere linjer
		while(line.hasNext()){
			Scanner sc = new Scanner(line.nextLine());
			sc.useDelimiter(";");

			//nulstil variabler
			navn = adresse = email = koen = medlemsskabstype = aktivitetstype = "";

			navn = sc.next();
			foedselsdato = LocalDate.parse(sc.next());
			adresse = sc.next();
			email = sc.next();
			koen = sc.next();
			medlemsskabstype = sc.next();
			aktivitetstype = sc.next();
			//scanner betalings historiken
			ArrayList<BetalingsHistorik> betaling = new ArrayList<>();
			if(sc.hasNext()){
				sc.useDelimiter("]");
				sc.skip(";");
				sc.skip("\\[");
				String s = sc.next();
				System.out.println(s);
				Scanner b = new Scanner(s);
				b.useDelimiter(",");

					while(b.hasNext()){
						String pris = b.next();
						double p = Double.parseDouble(pris);
						System.out.println(p);
						String date = b.next();
						LocalDate d = LocalDate.parse(date);
						System.out.println(d);
						String bank = b.next();
						System.out.println(bank);
						betaling.add(new BetalingsHistorik(p, d, bank));
					}
			}
			//lav medlem og adder til arraylisten
			liste.add(new Medlem(navn, foedselsdato, koen, adresse, email, medlemsskabstype, aktivitetstype, betaling));
		}
		}catch(FileNotFoundException e){
			System.out.println("Fejl i dannelse af medlemsliste");
			// Til debug af indlæsning af fil
			System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
			System.out.println("File absolute path: " + f.getAbsolutePath());}
	
	}
	//tilføjer et nyt medlems element til arraylisten og kalder derefter for opdateringen af data filen
	public void addMedlem(String navn,  LocalDate dato, String koen, String adresse, String email, String medlemstype,
           String aktivitetstype){
		System.out.println(navn +" "+ dato.toString() + " " + koen + " " + adresse+ " " + email+ " " + medlemstype+ " " + aktivitetstype);
		liste.add(new Medlem(navn, dato, koen, adresse, email, medlemstype, aktivitetstype));
		updateList();
	}
	
	public void deleteMedlem(int medlemsIndex){
		liste.remove(medlemsIndex);
		updateList();
	}

	public void editMedlem(int medlemsIndex, String navn,  LocalDate dato, String koen, String adresse, String email, String medlemstype,
           String aktivitetstype){
		liste.set(medlemsIndex, new Medlem(navn, dato, koen, adresse, email, medlemstype, aktivitetstype));
		updateList();

	}
	
	public Medlem getMedlem(int medlemsIndex){
		if(medlemsIndex >= 0 && medlemsIndex < liste.size()){
		return liste.get(medlemsIndex);
		}
		System.out.println("medlems index out of bounds");
		return liste.get(0);
	}

	public ArrayList<Medlem> getListe(){
		return liste;
	}


	//opdater data filen ud fra arraylisten 
	private void updateList(){
		try{
			//sletter den eksisterende fil og erstatter den med en ny tom fil med samme navn
			f.delete();
			f = new File(path);

			//skriver arraylisten ind i den nye fil
			PrintStream ps = new PrintStream(f);
			for(int i = 0; i < liste.size(); i++){
				ps.println(liste.get(i));
			}
		}catch(FileNotFoundException e){
			System.out.println("Fejl i opdatering");
		}
	}

	public ArrayList<Medlem> filtrerListe() {
		return null;
	}
}