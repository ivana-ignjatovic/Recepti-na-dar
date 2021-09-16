package paket.GUIKlase;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

public class Prijava extends JFrame {
	/**
	 * KLASA ZA PRIJAVU KORISNIKA
	 */
	private static final long serialVersionUID = 1L;
	private MenadzerBaza mb;
	private JPanel contentPane;
	private JTextField textFieldKorisnickoIme;
	private JTextField textFieldLozinka;
	private JLabel lblPrijava;
	
	private static Prijava frame;
	ArrayList<Korisnik>lista=new ArrayList<>();
	Registracija r =new Registracija();
	public static JPanel panel_1;
	
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
	
	public Prijava() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(App.k!=null)
					App.panelDodajRecept.setVisible(true);
			}
		});
		/**
		 * metoda koja uzima korisnika iz baze podataka
		 */
		try {
			mb=new MenadzerBaza("ReceptiNaDar.accdb");
			lista=mb.uzmiKorisnike();
			
		} catch (ClassNotFoundException e1) {
		
			e1.printStackTrace();
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
		
		setResizable(false);
		frame=this;
		setBackground(new Color(0, 0, 0));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 413, 251);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblKorisnickoIme = new JLabel("Korisni\u010Dko ime:");
		lblKorisnickoIme.setForeground(new Color(0, 0, 0));
		lblKorisnickoIme.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblKorisnickoIme.setBounds(10, 47, 206, 41);
		contentPane.add(lblKorisnickoIme);
		
		JLabel lblLozinka = new JLabel("Lozinka");
		lblLozinka.setForeground(new Color(0, 0, 0));
		lblLozinka.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblLozinka.setBounds(10, 99, 114, 41);
		contentPane.add(lblLozinka);
		
		textFieldKorisnickoIme = new JTextField();
		textFieldKorisnickoIme.setForeground(Color.BLACK);
		textFieldKorisnickoIme.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		textFieldKorisnickoIme.setBounds(143, 57, 127, 20);
		contentPane.add(textFieldKorisnickoIme);
		textFieldKorisnickoIme.setColumns(10);
		
		textFieldLozinka = new JPasswordField();
		textFieldLozinka.setFont(new Font("Segoe UI Historic", Font.PLAIN, 12));
		textFieldLozinka.setColumns(10);
		textFieldLozinka.setBounds(143, 109, 127, 20);
		contentPane.add(textFieldLozinka);
		
		lblPrijava = new JLabel("Prijava korisnika");
		lblPrijava.setForeground(new Color(0, 0, 0));
		lblPrijava.setFont(new Font("Segoe Script", Font.BOLD, 20));
		lblPrijava.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrijava.setBounds(47, 11, 307, 23);
		contentPane.add(lblPrijava);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 204));
		panel.setBorder(unselectedB);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Selected(panel);
				//ukoliko su polja popunjena
				 if(!textFieldKorisnickoIme.getText().equals("") && !textFieldLozinka.getText().equals("")) 
				  {
					  String sifra=textFieldLozinka.getText();
					  String ime = textFieldKorisnickoIme.getText();
					  for(int i = 0; i < lista.size(); i++) 
					  {
						  //proveravamo da li username i sifra odgovaraju nekom objektu u listi korisnika
						if(ime.equals(lista.get(i).getUsername()) && sifra.equals(lista.get(i).getSifra()))
						{
							JOptionPane.showMessageDialog(null, "USPEŠNO PRIJAVLJIVANJE");
							App.k=new Korisnik(ime,sifra,lista.get(i).getIme(),lista.get(i).getPrezime(),lista.get(i).getEmail());
							frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
							return;

						}
						
					  }
						JOptionPane.showMessageDialog(null, "POGRESNI PODACI!");
						Selected(panel);
				  }
				  else 
				  {	
					  JOptionPane.showMessageDialog(null, "Popunite polja!");
					  Selected(panel);
				  }
			}
		});
		panel.setBounds(47, 167, 114, 34);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Prijava");
		lblNewLabel.setFont(new Font("Segoe Print", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(1, 0, 113, 34);
		panel.add(lblNewLabel);
	
		panel_1 = new JPanel();
		Selected(panel_1);
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Selected(panel_1);
				r.setVisible(true);
			}
		});
		
		panel_1.setBounds(230, 167, 127, 34);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Registruj se");
		lblNewLabel_1.setFont(new Font("Segoe Print", Font.BOLD, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(3, 0, 123, 33);
		panel_1.add(lblNewLabel_1);
	}
}
