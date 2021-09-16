package paket;

import java.util.Hashtable;

import javax.swing.ImageIcon;


public class Recept {
	private int ID;
	private String naziv;
	private String vremePripreme;
	private String tip;
	private String opisPripreme;
	private Hashtable<Sastojak,Integer>sastojci;
	private Korisnik korisnik;
	private ImageIcon slika;
	
	
	
	public Recept(int iD, String naziv, String vremePripreme, String tip,String opisPripreme, Hashtable<Sastojak, Integer> sastojci,Korisnik korisnik,ImageIcon slika) {
		ID = iD;
		this.naziv = naziv;
		this.vremePripreme = vremePripreme;
		this.tip = tip;
		this.opisPripreme=opisPripreme;
		this.sastojci = sastojci;
		this.korisnik=korisnik;
		this.slika=slika;
	}


	public ImageIcon getSlika() {
		return slika;
	}


	public void setSlika(ImageIcon slika) {
		this.slika = slika;
	}


	public Recept() {
		super();
	}
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getVremePripreme() {
		return vremePripreme;
	}

	public void setVremePripreme(String vremePripreme) {
		this.vremePripreme  =vremePripreme;
	}
	public String getTip() {
		return tip;
	}
	public String getOpisPripreme() {
		return opisPripreme;
	}
	public void setOpisPripreme(String opisPripreme) {
		this.opisPripreme=opisPripreme;
	}
	
	public void setTip(String tip) {
		this.tip = tip;
	}

	public Hashtable<Sastojak, Integer> getSastojci() {
		return sastojci;
	}

	public void setSastojci(Hashtable<Sastojak, Integer> sastojci) {
		this.sastojci = sastojci;
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik=korisnik;
	}

	String sa;
	
	public String toString() {
		sa="";
		sastojci.forEach((key,value)->{
			sa=sa+key.toString(value)+"\n";
			
		});
			return  "\nSastojci:\n"+sa;
		}

	public String ispisiLepoSastojke()
	{
		sa="";
		sastojci.forEach((key,value)->{
			sa=sa+key.getNaziv() +", ";
		});
		if(sa.length()>0) {
			return "Sastojci: "+ sa.substring(0,sa.length()-1);
		}
		else {
			return "Sastojci:";
		}
			
		}
	

}


