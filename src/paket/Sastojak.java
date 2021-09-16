package paket;

import java.sql.SQLException;

public class Sastojak {
	public int ID;
	public String naziv;
	public String mernaJedinica;
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID=ID;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getMernaJedinica() {
		return mernaJedinica;
	}
	public void setMernaJedinica(String mernaJedinica) {
		this.mernaJedinica = mernaJedinica;
	}
	
	public Sastojak(int ID,String naziv, String mernaJedinica) throws SQLException {
		
		this.ID=ID;
		this.naziv = naziv;
		this.mernaJedinica = mernaJedinica;
	}
	public Sastojak(String naziv, String mernaJedinica) throws SQLException {

		this.naziv = naziv;
		this.mernaJedinica = mernaJedinica;
	}
	
	public String toString(int k) {
		return naziv +" "+ k +mernaJedinica;
	}
	
	
	
	
}

