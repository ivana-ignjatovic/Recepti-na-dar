package paket;

public class Korisnik {

	private String username, sifra, ime,prezime,email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Korisnik() {
		super();
	}

	public Korisnik(String username, String sifra, String ime, String prezime, String email) {
		super();
		this.username = username;
		this.sifra = sifra;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
	}

	@Override
	public String toString() {
		return ime+ " " + username ;
	}
	
	
	
}
