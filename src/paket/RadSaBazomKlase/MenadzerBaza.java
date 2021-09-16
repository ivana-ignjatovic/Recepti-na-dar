package paket.RadSaBazomKlase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import paket.Korisnik;
import paket.Recept;
import paket.Sastojak;

public class MenadzerBaza {

	private static Baza baza;
	
	public MenadzerBaza(String fileName)throws  ClassNotFoundException,SQLException{
		baza=new Baza(fileName);
		
	}
	
	public MenadzerBaza() {
		
	}
	/**
	 *metoda koja vracu listu korisnika iz baze
	 */
	public ArrayList<Korisnik>uzmiKorisnike() throws SQLException
	{
		ArrayList<Korisnik>lista=new ArrayList<Korisnik>();
		String sql="SELECT * FROM Korisnik";
		ResultSet result=baza.query(sql);
		Korisnik korisnik=new Korisnik();
		while(result.next())
		{
			String username=result.getString("KorisnickoIme");
			String sifra=result.getString("Sifra");
			String ime=result.getString("Ime");
			String prezime=result.getString("Prezime");
			String email=result.getString("Email");
			korisnik= new Korisnik(username,sifra,ime,prezime,email);
			lista.add(korisnik);
		}
		return lista;
	}
	
	
	/**
	 * metoda koja vraca listu sastojaka iz baze
	 */
	public ArrayList<Sastojak>uzmiSastojke() throws SQLException
	{
		ArrayList<Sastojak>lista=new ArrayList<Sastojak>();
		String sql="SELECT * FROM Sastojak";
		ResultSet result= baza.query(sql);
		while(result.next())
		{
			
			int idSastojak=result.getInt("ID");
			String nazivSastojak=result.getString("Naziv");
			String mernaJedinica=result.getString("MernaJedinica");
			Sastojak sastojak=new Sastojak(idSastojak,nazivSastojak,mernaJedinica);
			lista.add(sastojak);
			
		}
		return lista;
		
	}
	
	
	/**
	 * metoda koja vraca listu recepata
	 */
	public ArrayList<Recept> uzmiRecepte() throws SQLException
	{
		ArrayList<Recept>lista=new ArrayList<Recept>();
		String sql="SELECT * FROM Recept";
		ResultSet result=baza.query(sql);
		while(result.next())
		{
			int idRecept=result.getInt("ID");
			String nazivRecept=result.getString("Naziv");
			String vremePripreme=result.getString("VremePripreme");
			String  tip=result.getString("Tip");
			String opisPripreme=result.getString("OpisPripreme");
			Hashtable<Sastojak, Integer>sastojci=new Hashtable<Sastojak, Integer>();
			String k=result.getString("Korisnik");
			Korisnik korisnik=null;
			InputStream input = result.getBinaryStream("Images");
			byte buffer[]=getByteImgFromOLEInputStream(input, "JPEG");
			ImageIcon icon = new ImageIcon(buffer);
			
			//uzimanje sastojaka koji pripadaju tom receptu
			String sql1="SELECT Kolicina, IdSastojka FROM SastojciRecepta WHERE IDRecepta="+idRecept;
			ResultSet result1=baza.query(sql1);
				while(result1.next())
				{
					int kolicina=result1.getInt("Kolicina");
					int idSastojka=result1.getInt("IDSastojka");
					String sql2="SELECT * FROM Sastojak WHERE ID= "+idSastojka;
					ResultSet result2=baza.query(sql2);
						while(result2.next())
						{
							int idSastojak=result2.getInt("ID");
							String naziv=result2.getString("Naziv");
							String mernaJedinica=result2.getString("MernaJedinica");
							Sastojak s=new Sastojak(idSastojak,naziv,mernaJedinica);
							sastojci.put(s,kolicina);
						}			
				}
				
			//uzimanje username korisnika koji je autor recepta
			String sql3="SELECT * FROM Korisnik WHERE KorisnickoIme='"+k+"'";
			ResultSet result3=baza.query(sql3);
				while(result3.next())
				{
					String username=result3.getString("KorisnickoIme");
					String sifra=result3.getString("Sifra");
					String ime=result3.getString("Ime");
					String prezime=result3.getString("Prezime");
					String email=result3.getString("Email");
					korisnik= new Korisnik(username,sifra,ime,prezime,email);
					
				}
			Recept recept=new Recept(idRecept,nazivRecept,vremePripreme,tip,opisPripreme,sastojci,korisnik,icon);
			lista.add(recept);
		}
			return lista;
			
			
	}
	
	/**
	 *metoda koja upisuje korisnika
	 */
	public void upisiKorisnika(Korisnik k)throws SQLException{
		try {
			String SQL = String.format("INSERT INTO Korisnik(KorisnickoIme, Sifra, Ime, Prezime, Email) VALUES ('%s','%s','%s','%s','%s');",k.getUsername(),k.getSifra(),k.getIme(),k.getPrezime(),k.getEmail());
			baza.update(SQL);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "GRESKA PRI UVOZU PODATAKA KORISNIKA U BAZU!\n" + e.getMessage());
			System.exit(0);
		}
	}
	
	
	/**
	 *metoda za brisanje recepta
	 */
	public void izbrisiRecept(Recept r)throws SQLException{
		try {
			String SQL = "DELETE FROM Recept WHERE ID="+r.getID()+";";
			baza.update(SQL);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "GRESKA PRI BRISANJU RECEPTE U BAZI\n" + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 *dodavanje sastojaka u bazu
	 */
	public int dodajSastojke(Sastojak s) throws SQLException
	{
		try {
			String sql=String.format("INSERT INTO Sastojak( Naziv, MernaJedinica)VALUES ('%s', '%s');",s.getNaziv(),s.getMernaJedinica());
			return baza.updateReturnID(sql);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "GRESKA PRI UVOZU PODATAKA SASTOJAKA U BAZU!\n" + e.getMessage());
			System.exit(0);
		}
		return 0;
	}
	
	/**
	 * dodavanje recepata u bazu
	 */
	public int dodajRecepte(Recept r) throws SQLException
	{
		try {
		String sql=String.format("INSERT INTO Recept(Naziv) VALUES ('%s');",r.getNaziv());
		return baza.updateReturnID(sql);
		}
		catch (Exception e) {
		JOptionPane.showMessageDialog(null, "GRESKA PRI UVOZU RECEPATA U BAZU!\n" + e.getMessage());
		}
		return 0;
	
	}
	
	/**
	 * dodavanje odgovarajucih sastojaka receptu
	 */
	public void dodajSastojkeReceptu(Recept r,Sastojak s,int k) throws SQLException
	{
		try {
			String sql1=String.format("INSERT INTO SastojciRecepta(IDRecepta,IDSastojka,Kolicina)VALUES('%s','%s','%s');",r.getID(),s.getID(),k);
			baza.update(sql1);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "GRESKA PRI UVOZU SASTOJAKA RECEPTA U BAZU!\n" + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * azuriranje recepta
	 */
	public void updateRecepta(Recept r,String pathName)throws SQLException
	{
		PreparedStatement ps=null;
		try {
			File file;
			String sql="UPDATE Recept SET  Naziv=?, VremePripreme=?,Tip=?,OpisPripreme=?,Korisnik=?,Images=? WHERE ID =?";
			ps=baza.prepared(sql);
			ps.setString(1, r.getNaziv());
			ps.setString(2, r.getVremePripreme());
			ps.setString(3, r.getTip());
			ps.setString(4, r.getOpisPripreme());
			ps.setString(5, r.getKorisnik().getUsername());
			if(pathName.length()>0) {
				file=new File(pathName);
			}
			else
			{
				file=new File("images\\slika.JPG");
			}
				
			FileInputStream fis=new FileInputStream(file);
			ps.setBinaryStream(6, fis,(int) file.length());
			ps.setInt(7, r.getID());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	    
	    public byte[] getByteImgFromOLEInputStream(InputStream input, String imageFormat) {
	    	Map<String, String> imagesBeginBlock;
	   	  	imagesBeginBlock = new HashMap<String, String>();
	         imagesBeginBlock.put("JPEG", "\u00FF\u00D8\u00FF"); // JPEG
	         imagesBeginBlock.put("PNG", "\u0089PNG\r\n\u001a\n"); // PNG
	         imagesBeginBlock.put("GIF", "GIF8"); // GIF
	         imagesBeginBlock.put("TIFF", "II*\u0000"); // TIFF
	         imagesBeginBlock.put("BMP", "BM"); // BMP
	        // get begin block identifier using imageFormat parameter
	        String beginBlock = imagesBeginBlock.get(imageFormat);
	        if(beginBlock == null) {
	            throw new RuntimeException("Unsupported image format parameter value.");
	        }
	         
	        try {
	            byte[] b = toByteArray(input);
	            String str = new String(b, "ISO-8859-1");
	             
	    
	            int index = str.indexOf(beginBlock);
	            if(index == -1) {
	                throw new RuntimeException("Unable to determine image format.");
	            }
	             
	            byte[] buffer = new byte[b.length - index];
	            for(int i = 0, a = index; a < b.length; i++, a++) {
	                buffer[i] = b[a];
	            }
	            return buffer;
	             
	        } catch(IOException e) {
	            e.printStackTrace();
	        } 
	         
	        return null;
	    }
	     
	    /**
	     * Convert InputStream object to array of bytes
	     * @throws IOException 
	     */
	    public byte[] toByteArray(InputStream is) throws IOException {
	        int len;
	        int size = 1024;
	        byte[] buf;
	 
	        if (is instanceof ByteArrayInputStream) {
	            size = is.available();
	            buf = new byte[size];
	            len = is.read(buf, 0, size);
	        } else {
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            buf = new byte[size];
	            while ((len = is.read(buf, 0, size)) != -1) {
	                bos.write(buf, 0, len);
	            }
	            buf = bos.toByteArray();
	        }
	        return buf;
	    }
}
