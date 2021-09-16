package paket.GUIKlase;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import paket.Korisnik;
import paket.RadSaBazomKlase.MenadzerBaza;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Registracija extends JFrame {
	
	/**
	 * KLASA ZA REGISTRACIJU KORISNIKA
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField textFieldIme;
	private JTextField textFieldPrezime;
	private JTextField textFieldEmail;
	private JTextField textFieldKorisnickoIme;
	private JTextField textFieldLozinka;

	
	private MenadzerBaza mb;
	private Registracija frame;
	ArrayList<Korisnik>lista=new ArrayList<>();
	
	private static Color unselected=new Color(255, 255, 204);
	private static Color selected=new Color(51, 255, 204);
	private static Border unselectedB=new BevelBorder(BevelBorder.RAISED);
	private static Border selectedB=new BevelBorder(BevelBorder.LOWERED);
	
	
	private static void Selected(JPanel p )
	{
		if(p.getBackground().equals(unselected))
		{
			p.setBackground(selected);
			p.setBorder(selectedB);
		}
		else 
		{
			p.setBackground(unselected);
			p.setBorder(unselectedB);
		}
	}

	public Registracija() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Selected(Prijava.panel_1);
			}
		});
		//uzimamo korisnike iz baze
		try {
			mb=new MenadzerBaza("ReceptiNaDar.accdb");
			lista=mb.uzmiKorisnike();
		} catch (ClassNotFoundException e1) {
		
			e1.printStackTrace();
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
		
		setResizable(false);
		frame =this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 441, 557);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIme = new JLabel("Ime:");
		lblIme.setHorizontalAlignment(SwingConstants.LEFT);
		lblIme.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblIme.setBounds(21, 73, 109, 30);
		contentPane.add(lblIme);
		
		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setHorizontalAlignment(SwingConstants.LEFT);
		lblPrezime.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblPrezime.setBounds(21, 149, 109, 30);
		contentPane.add(lblPrezime);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmail.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblEmail.setBounds(21, 227, 109, 30);
		contentPane.add(lblEmail);
		
		JLabel lblKorisnickoIme = new JLabel("Korisni\u010Dko ime:");
		lblKorisnickoIme.setHorizontalAlignment(SwingConstants.LEFT);
		lblKorisnickoIme.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblKorisnickoIme.setBounds(21, 300, 109, 30);
		contentPane.add(lblKorisnickoIme);
		
		JLabel lblLozinka = new JLabel("Lozinka:");
		lblLozinka.setHorizontalAlignment(SwingConstants.LEFT);
		lblLozinka.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblLozinka.setBounds(21, 369, 109, 30);
		contentPane.add(lblLozinka);
		
		textFieldIme = new JTextField();
		textFieldIme.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		textFieldIme.setBackground(new Color(255, 255, 204));
		textFieldIme.setBounds(166, 71, 180, 36);
		contentPane.add(textFieldIme);
		textFieldIme.setColumns(10);
		
		textFieldPrezime = new JTextField();
		textFieldPrezime.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		textFieldPrezime.setBackground(new Color(255, 255, 204));
		textFieldPrezime.setColumns(10);
		textFieldPrezime.setBounds(166, 147, 180, 36);
		contentPane.add(textFieldPrezime);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		textFieldEmail.setBackground(new Color(255, 255, 204));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(166, 229, 180, 36);
		contentPane.add(textFieldEmail);
		
		textFieldKorisnickoIme = new JTextField();
		textFieldKorisnickoIme.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		textFieldKorisnickoIme.setBackground(new Color(255, 255, 204));
		textFieldKorisnickoIme.setColumns(10);
		textFieldKorisnickoIme.setBounds(167, 295, 180, 36);
		contentPane.add(textFieldKorisnickoIme);
		
		textFieldLozinka =new JPasswordField();
		textFieldLozinka.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		textFieldLozinka.setBackground(new Color(255, 255, 204));
		textFieldLozinka.setColumns(10);
		textFieldLozinka.setBounds(167, 368, 180, 36);
		contentPane.add(textFieldLozinka);
		
		JLabel lblNewLabel = new JLabel("Registracija korisnika");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe Script", Font.BOLD, 25));
		lblNewLabel.setBounds(10, 11, 392, 49);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(unselected);
		panel.setBorder(unselectedB);
		
		/*DOGADJAJ ZA PRIJAVU*/
		
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Selected(panel);
					String ime=textFieldIme.getText();
					String prezime=textFieldPrezime.getText();
					String username=textFieldKorisnickoIme.getText();
					String sifra=textFieldLozinka.getText();
					String email=textFieldEmail.getText();
					//ukoliko su polja popunjena
					if(!ime.equals("") && !prezime.equals("")&&!username.equals("")&&!sifra.equals("")&&!email.equals("")) { 
					for (Korisnik korisnik : lista) {
						//prvo proveravamo da li je korisnik uneo postojeci username
						if(korisnik.getUsername().equals(username))
						
						{	textFieldKorisnickoIme.setText("");
							throw new Exception ("Ovo korisnicko ime vec postoji, pokusajte sa drugim!");
						}
						
					}
					Korisnik k=new Korisnik(username,sifra,ime,prezime,email);
					//ukoliko je sve u redu registrujemo korisnika
					mb.upisiKorisnika(k);
					JOptionPane.showMessageDialog(null, "Registracija uspesna!");
					frame.dispose();
					
					}
					  else 
					  {	
						  JOptionPane.showMessageDialog(null, "Popunite polja!");
						  Selected(panel);
						  return;
					  }
					
					
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "GRESKA PRI UPISU KORISNIKA U BAZU!");
						
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
						
					}
			
			}
		});
		panel.setBounds(119, 451, 189, 49);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Registruj se");
		lblNewLabel_1.setBounds(47, 11, 94, 26);
		lblNewLabel_1.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
	}
}
