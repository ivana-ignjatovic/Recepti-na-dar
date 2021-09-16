package paket.GUIKlase;

/**
 * KLASA ZA DODAVANJE NOVIH RECEPATA
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import paket.Recept;
import paket.Sastojak;
import paket.RadSaBazomKlase.MenadzerBaza;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DodavanjeRecepta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldIme;
	private JTextField textFieldVremePripreme;
	private JTextField textFieldImeSastojka;
	private JTextField textFieldKolicinaSastojka;
	private JTextField textFieldMernaJedinica;
	
	ArrayList<Sastojak>lista=new ArrayList<>();
	private static DodavanjeRecepta frame;
	private MenadzerBaza mb;
	public static Recept r;
	private static String pathName="";
	
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
	
/**
 * dogadjaj prilikom zatvaranja forme, ukoliko je recept prazan da se izbrise iz baze
 */

	public DodavanjeRecepta() {
	addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			App.Selected(App.panelDodajRecept);
			if(r.getNaziv()==null||r.getVremePripreme()==null||r.getOpisPripreme()==null)
			{
				try {
					mb.izbrisiRecept(r);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
		}
	});
	
		setBackground(Color.BLACK);
		/**
		 * poziv metoda klase za rad sa bazom
		 */
		try {
			r=new Recept();
			mb=new MenadzerBaza("ReceptiNaDar.accdb");
			lista=mb.uzmiSastojke();
			int id=mb.dodajRecepte(r);
			r.setID(id);
			
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		setResizable(false);
		frame=this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 771, 728);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblImeRecepta = new JLabel("Ime recepta:");
		lblImeRecepta.setFont(new Font("Segoe Print", Font.BOLD, 16));
		lblImeRecepta.setBounds(10, 25, 166, 39);
		contentPane.add(lblImeRecepta);
		
		JLabel lblVremePripreme = new JLabel("Vreme pripreme:");
		lblVremePripreme.setFont(new Font("Segoe Print", Font.BOLD, 16));
		lblVremePripreme.setBounds(10, 89, 166, 39);
		contentPane.add(lblVremePripreme);
		
		JLabel lblTip = new JLabel("Tip:");
		lblTip.setFont(new Font("Segoe Print", Font.BOLD, 16));
		lblTip.setBounds(518, 0, 166, 39);
		contentPane.add(lblTip);
		
		JLabel lblSastojci = new JLabel("Sastojci:");
		lblSastojci.setFont(new Font("Segoe Print", Font.BOLD, 16));
		lblSastojci.setBounds(10, 188, 166, 39);
		contentPane.add(lblSastojci);
		
		JLabel lblOpisPripreme = new JLabel("Opis pripreme:");
		lblOpisPripreme.setFont(new Font("Segoe Print", Font.BOLD, 16));
		lblOpisPripreme.setBounds(10, 403, 166, 39);
		contentPane.add(lblOpisPripreme);
		
		textFieldIme = new JTextField();
		textFieldIme.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textFieldIme.setBackground(new Color(255, 255, 204));
		textFieldIme.setBounds(186, 31, 209, 31);
		contentPane.add(textFieldIme);
		textFieldIme.setColumns(10);
		
		textFieldVremePripreme = new JTextField();
		textFieldVremePripreme.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textFieldVremePripreme.setBackground(new Color(255, 255, 204));
		textFieldVremePripreme.setColumns(10);
		textFieldVremePripreme.setBounds(186, 95, 209, 31);
		contentPane.add(textFieldVremePripreme);
		
		JCheckBox chckbxSlatko = new JCheckBox("Slatko");
		chckbxSlatko.setBackground(new Color(255, 255, 153));
		chckbxSlatko.setFont(new Font("Segoe Script", Font.BOLD, 14));
		chckbxSlatko.setBounds(447, 57, 97, 23);
		contentPane.add(chckbxSlatko);
		
		JCheckBox chckbxSlano = new JCheckBox("Slano");
		chckbxSlano.setBackground(new Color(255, 255, 153));
		chckbxSlano.setFont(new Font("Segoe Script", Font.BOLD, 14));
		chckbxSlano.setBounds(589, 57, 97, 23);
		contentPane.add(chckbxSlano);
		
		JCheckBox chckbxPosno = new JCheckBox("Posno");
		chckbxPosno.setBackground(new Color(255, 255, 153));
		chckbxPosno.setFont(new Font("Segoe Script", Font.BOLD, 14));
		chckbxPosno.setBounds(447, 99, 97, 23);
		contentPane.add(chckbxPosno);
		
		JCheckBox chckbxBrzo = new JCheckBox("Brzo");
		chckbxBrzo.setBackground(new Color(255, 255, 153));
		chckbxBrzo.setFont(new Font("Segoe Script", Font.BOLD, 14));
		chckbxBrzo.setBounds(589, 99, 97, 23);
		contentPane.add(chckbxBrzo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(140, 393, 561, 187);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textArea.setBackground(new Color(255, 255, 204));
		scrollPane.setViewportView(textArea);
		
		textFieldImeSastojka = new JTextField();
		textFieldImeSastojka.setFont(new Font("Segoe Print", Font.PLAIN, 13));
		textFieldImeSastojka.setBackground(new Color(255, 255, 204));
		textFieldImeSastojka.setBounds(405, 213, 86, 20);
		contentPane.add(textFieldImeSastojka);
		textFieldImeSastojka.setColumns(10);
		
		textFieldKolicinaSastojka = new JTextField();
		textFieldKolicinaSastojka.setFont(new Font("Segoe Print", Font.PLAIN, 13));
		textFieldKolicinaSastojka.setBackground(new Color(255, 255, 204));
		textFieldKolicinaSastojka.setColumns(10);
		textFieldKolicinaSastojka.setBounds(501, 213, 86, 20);
		contentPane.add(textFieldKolicinaSastojka);
		
		textFieldMernaJedinica = new JTextField();
		textFieldMernaJedinica.setFont(new Font("Segoe Print", Font.PLAIN, 13));
		textFieldMernaJedinica.setBackground(new Color(255, 255, 204));
		textFieldMernaJedinica.setColumns(10);
		textFieldMernaJedinica.setBounds(602, 214, 86, 20);
		contentPane.add(textFieldMernaJedinica);
		
		JLabel lblNewLabel = new JLabel("Ime sastojka");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblNewLabel.setBounds(405, 188, 87, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblKolicina = new JLabel("koli\u010Dina");
		lblKolicina.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblKolicina.setBounds(518, 188, 70, 14);
		contentPane.add(lblKolicina);
		
		JLabel lblMernaJedinica = new JLabel("merna jedinica");
		lblMernaJedinica.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblMernaJedinica.setBounds(602, 188, 128, 14);
		contentPane.add(lblMernaJedinica);
		
		JScrollPane scrollPaneSastojak = new JScrollPane();
		scrollPaneSastojak.setBounds(140, 188, 255, 152);
		contentPane.add(scrollPaneSastojak);
		
		JTextArea textAreaSastojak = new JTextArea();
		textAreaSastojak.setEditable(false);
		textAreaSastojak.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textAreaSastojak.setBackground(new Color(255, 255, 204));
		scrollPaneSastojak.setViewportView(textAreaSastojak);
		
		JPanel panelDodajSastojak = new JPanel();
		panelDodajSastojak.setBackground(unselected);
		panelDodajSastojak.setBorder(unselectedB);
		
		/**
		 * dogadjaj za dodavanje sastojaka
		 */
		panelDodajSastojak.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Selected(panelDodajSastojak);
					Sastojak s=null;
					/**
					 * ako su polja za sastojak popunjena
					 */
					if(!textFieldImeSastojka.getText().trim().equals("") && !textFieldMernaJedinica.getText().trim().equals("") && 
							!textFieldKolicinaSastojka.getText().trim().equals("")) {	
						
						int kolicina=Integer.parseInt(textFieldKolicinaSastojka.getText());
						String ime=textFieldImeSastojka.getText();
						String mernaJedinica=textFieldMernaJedinica.getText();
						
		
												
						for(Sastojak sastojak: lista)
						/**
						 * pitamo da li ime  i merna jedincia tog sastojka su jednaka vec nekom postojecem sastojku u bazi
						 */
							if(sastojak.getNaziv().equals(ime)&&sastojak.getMernaJedinica().equals(mernaJedinica)) {
								/**
								 * u tom slucaju sastojku dodeljujemo vrednosti tog sastojka
								 */
								s=new Sastojak(sastojak.getID(),sastojak.getNaziv(),sastojak.getMernaJedinica());
								break;
							}
						if(s==null)
						{
							/**
							 * ako je sastojak null dodeljujemo mu vrednosti iz textFieldova
							 */
							s=new Sastojak(ime,mernaJedinica);
							int idSastojka=mb.dodajSastojke(s);
							s.setID(idSastojka);
						}
						textAreaSastojak.append(s.toString(kolicina)+"\n");
						mb.dodajSastojkeReceptu(r, s, kolicina);//na kraju pozivamo metodu u kojoj dodeljujemo te sastojke receptu
				
						JOptionPane.showMessageDialog(null, "Uspesno!");
						Selected(panelDodajSastojak);
						textFieldImeSastojka.setText("");
						textFieldKolicinaSastojka.setText("");
						textFieldMernaJedinica.setText("");
				}
				
				else
				{
					JOptionPane.showMessageDialog(null, "Popunite polja!");
					Selected(panelDodajSastojak);
					return;
				}
				
				
				}
				catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Doslo je do greske prilikom unosa sastojaka!");
					e1.printStackTrace();
				}
				catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Doslo je do greske prilikom unosa sastojaka!");
				
				}
			}
		});
		panelDodajSastojak.setBounds(475, 266, 156, 37);
		contentPane.add(panelDodajSastojak);
		
		JLabel lblNewLabel_1 = new JLabel("Dodaj sastojak");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Segoe Print", Font.BOLD, 14));
		panelDodajSastojak.add(lblNewLabel_1);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(unselectedB);
		panel.setBackground(unselected);
		/**
		 * dogadadjaj za update recepte
		 */
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	
				try {
					Selected(panel);
					/**
					 * na pocetku ove forme dodelili smo receptu samo id, da bi ga dodali  celog potrebno je  azurirati taj recept
					 */
			
					if(!textFieldIme.getText().equals("") && !textFieldVremePripreme.getText().equals("")&&!textArea.getText().equals(""))
					{
						String imeRecepta=textFieldIme.getText();
						String vremePripreme=textFieldVremePripreme.getText();
						String tip="";
						String opisPripreme=textArea.getText();
						if(chckbxBrzo.isSelected())
							tip+="Brzo, ";
						if(chckbxPosno.isSelected())
							tip+="Posno, ";
						if(chckbxSlano.isSelected())
							tip+="Slano, ";
						if(chckbxSlatko.isSelected())
							tip+="Slatko, ";
						if(tip.length()>2)
						tip=tip.substring(0,tip.length()-2);
	
						r.setNaziv(imeRecepta);
						r.setOpisPripreme(opisPripreme);
						r.setTip(tip);
						r.setVremePripreme(vremePripreme);
						r.setKorisnik(App.k);
						mb.updateRecepta(r,pathName);//to cinimo pozivom ove metode, pathName predstavlja putanju slike
						JOptionPane.showMessageDialog( null, "Uspesno ste uneli recept!");
						
						
						App.Selected(App.panelDodajRecept);
						App.Refresh();// da bi se nakon dodavanja recepta taj recept odmah pojavio u aplikaciji pozivamo metodu refresh
						frame.dispose();// na kraju zatvaramo ovaj frame
		

					}
					else {
						JOptionPane.showMessageDialog(null, "Popunite sva polja!");
						Selected(panel);
						return;
					}
					
				} 
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Doslo je do greske prilikom unosa recepta!");
					e1.printStackTrace();
				}
				


			}
		});
		panel.setBounds(448, 644, 183, 34);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Dodaj recept");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblNewLabel_2.setBounds(10, 0, 148, 34);
		panel.add(lblNewLabel_2);
		
		JLabel lblFilePath = new JLabel("");
		lblFilePath.setBounds(186, 644, 183, 34);
		contentPane.add(lblFilePath); 
		JLabel lblSlika = new JLabel("");
		lblSlika.setBounds(10, 532, 120, 98);
		contentPane.add(lblSlika);
		
		JPanel panelDodajSliku = new JPanel();
		panelDodajSliku.addMouseListener(new MouseAdapter() {
			/**
			 * dogadjaj za dodavanje slike receptu
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files",ImageIO.getReaderFileSuffixes());
                fc.setFileFilter(filter);
                fc.setCurrentDirectory(new File(System.getProperty("user.home")+"//Desktop"));
                int response = fc.showOpenDialog(null);
                try {
                    if (response == JFileChooser.APPROVE_OPTION) {
                        pathName = fc.getSelectedFile().getPath();
                        BufferedImage img = null;
                        img = ImageIO.read(new File(pathName)); 
                        Image dimg = img.getScaledInstance(lblSlika.getWidth(), lblSlika.getHeight(),Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(dimg);
                        lblFilePath.setText(pathName);
                        lblFilePath.setVisible(false);
                        lblSlika.setIcon(icon);
                    } else {
                        JOptionPane.showMessageDialog(null, "Feel Free to Look Later");
                    }
                } catch (Exception ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
			}
		});
		
		panelDodajSliku.setBorder(unselectedB);
		panelDodajSliku.setBackground(new Color(255, 255, 204));
		panelDodajSliku.setBounds(10, 641, 156, 37);
		contentPane.add(panelDodajSliku);
		
		JLabel lblDodajSliku = new JLabel("Dodaj sliku");
		lblDodajSliku.setHorizontalAlignment(SwingConstants.CENTER);
		lblDodajSliku.setFont(new Font("Segoe Print", Font.BOLD, 14));
		panelDodajSliku.add(lblDodajSliku);
		
	}
}
