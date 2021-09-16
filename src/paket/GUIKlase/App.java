package paket.GUIKlase;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import paket.Korisnik;
import paket.Recept;
import paket.RadSaBazomKlase.MenadzerBaza;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldPretraga;
	public static JPanel panelDodajRecept;
	public static JPanel panelListaRecepata;
	public static JPanel panelCeoRecept;
	public static JFrame frame;
	
	static ArrayList<Recept>lista=new ArrayList<>();
	private static  MenadzerBaza mb;
	static ArrayList<String> napuniTipove=new ArrayList<>();
	public static Korisnik k=null;
	
	private static Color selected=new Color(51, 255, 204);
	private static Color unselected=new Color(255, 255, 153);
	private static Border unselectedB=new BevelBorder(BevelBorder.RAISED);
	private static Border selectedB=new BevelBorder(BevelBorder.LOWERED);
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {   
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * metoda koja rifresuje recepte nakon dodavanja novih recepata
	 */
	static void Refresh()
	{
		try {
			mb=new MenadzerBaza("ReceptiNaDar.accdb");
			lista=mb.uzmiRecepte();
			
		} catch (ClassNotFoundException e1) {
		
			e1.printStackTrace();
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
		panelListaRecepata.setPreferredSize(new Dimension(190,(lista.size()*90)));
		Filter(panelListaRecepata,panelCeoRecept,"");
		
	}
	/**
	 * metoda koja menja boju i border u odnosu na de/selektovanje
	 */
	static void Selected(JPanel p )
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
	 * metoda koja nakon selektovanja jednog deselektuje druge recepte
	 */
	private static void SelectedRecept(JPanel mainPanel,JPanel p)
	{
		mainPanel.getComponents();
		for (Component panel : mainPanel.getComponents()) {
			panel.setBackground(unselected);
			((JPanel) panel).setBorder(unselectedB);
			
		}
		p.setBackground(selected);
		p.setBorder(selectedB);
	

	}
	
	
	
	/**
	 * metoda za ispisivanje celog recepta
	 */
	public static void IspisCelogRecepta(Recept recept,JPanel panelCeoRecept)
	{
		panelCeoRecept.removeAll();
		/**
		 * definisanje komponenti i setovanje njihovih vrednosti 
		 */
		
		JLabel Lblnaziv=new JLabel(recept.getNaziv());
		Lblnaziv.setFont(new Font("Segoe Print", Font.BOLD, 14));
		Lblnaziv.setPreferredSize(new Dimension(450,30));
		Lblnaziv.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel LblSlika=new JLabel();
		LblSlika.setPreferredSize(new Dimension(250,250));
		Image image =recept.getSlika().getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);	
		LblSlika.setIcon(new ImageIcon(image));
		LblSlika.setBorder(unselectedB);
	
		
		JTextArea TAsastojci=new JTextArea(recept.toString());
		TAsastojci.setPreferredSize(new Dimension(178,300));
		TAsastojci.setBackground(new Color(255, 255, 204));
		TAsastojci.setFont(new Font("Segoe Print", Font.BOLD, 10));
		TAsastojci.setEditable(false);
		
		JTextArea TAopisPripreme=new JTextArea(recept.getOpisPripreme());
		TAopisPripreme.setPreferredSize(new Dimension(447,195));
		TAopisPripreme.setBackground(new Color(255, 255, 204));
		TAopisPripreme.setFont(new Font("Segoe Print", Font.BOLD, 10));
		TAopisPripreme.setEditable(false);
		TAopisPripreme.setWrapStyleWord(true);
		TAopisPripreme.setLineWrap(true);

		/**
		 * dodavanje komponenti u panel za ispis celog recepta
		 */
		panelCeoRecept.add(Lblnaziv);
		panelCeoRecept.add(LblSlika);
		
		panelCeoRecept.add(TAsastojci);
		panelCeoRecept.add(TAopisPripreme);
		panelCeoRecept.setBackground(new Color(255, 255, 204));
		panelCeoRecept.revalidate();
	}
	
	
	/**
	 * metoda za ispis liste recepata
	 * @param panel predstavlja listu recepata
	 * @param panelRecept predstavlja panel celog recepta
	 * @param i promenjiva koja predstavlja indeks recepta
	 */
	public static void IspisListeRecepata(JPanel panel,JPanel panelRecept,int i) {
		
			final Integer innerMi = i;
			JPanel panel_1 = new JPanel();
			panel_1.setLayout(new FlowLayout(FlowLayout.CENTER));
			panel_1.setPreferredSize(new Dimension(200,80));
			panel_1.setBackground(new Color(255, 255, 153));
			panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
									
			JLabel naziv=new JLabel(lista.get(i).getNaziv());
			naziv.setPreferredSize(new Dimension(180,20));
			panel_1.add(naziv);
			naziv.setFont(new Font("Segoe Print", Font.BOLD, 14));
			naziv.setHorizontalAlignment(SwingConstants.CENTER);
									
									
			JLabel sastojci=new JLabel("  "+lista.get(i).ispisiLepoSastojke());
			sastojci.setPreferredSize(new Dimension(200,15));
			panel_1.add(sastojci);
			sastojci.setFont(new Font("Segoe Print", Font.PLAIN, 12));
									
									
			JLabel vremePripreme=new JLabel("  Vreme pripreme: "+lista.get(i).getVremePripreme(),SwingConstants.LEFT);
			vremePripreme.setPreferredSize(new Dimension(200,15));
			panel_1.add(vremePripreme);
			vremePripreme.setFont(new Font("Segoe Print", Font.PLAIN, 12));
									
			panel.add(panel_1);
			/**
			 * dogadjaj prilikom klika panela sa liste da se prikaze ceo recept
			 */
			panel_1.addMouseListener(new MouseAdapter()
			{					
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					SelectedRecept(panel,panel_1);	
					IspisCelogRecepta(lista.get(innerMi),panelRecept);				
				}		
			});
	}
	/**
	 * metoda za filtiranje recepata po tipu
	 * @param panel- predstavlja panel liste recepata
	 * @param panelRecept- predstavlja panel celog recepta
	 * @param s1- predtsavlja string koji se unosi u pretrazi
	 */
public static void Filter(JPanel panel,JPanel panelRecept,String s1) 
{	
	panel.removeAll();
	int br=0;
	int dim=0;
	int dim2=0;
	if(napuniTipove.isEmpty()&&s1.isBlank())
	{
		for(int i=0;i<lista.size();i++)
			IspisListeRecepata(panel,panelRecept,i);
		
	}
	  //ukoliko filter nije definisan ali pretraga jeste ispisuje se lista koja odgovara pretrazi
	else if(napuniTipove.isEmpty()&&!s1.isBlank())
	{
		for (int i = 0;i<lista.size(); i++)
		{
			
			if(lista.get(i).getNaziv().toLowerCase().contains(s1.toLowerCase()))
			{
			IspisListeRecepata(panel,panelRecept,i);
			dim++;
			}		
		}
		panel.setPreferredSize(new Dimension(190,dim*90));
	}
	// u slucaju da su definisani tipovi
	else 
	{
		
		for (int i = 0;i<lista.size(); i++)
		{
			ArrayList<String> tipovi =new ArrayList<String>(Arrays.asList(lista.get(i).getTip().split(", ")));
			for(int k=0;k<napuniTipove.size();k++)
			{ 
				if(tipovi.contains(napuniTipove.get(k)))
					br++;
				if(br==napuniTipove.size())
				{
					if(lista.get(i).getNaziv().toLowerCase().contains(s1.toLowerCase()))
					{
						IspisListeRecepata(panel,panelRecept,i);
						dim2++;
					}
					
				}
			}
			br=0;
		}
		panel.setPreferredSize(new Dimension(190,dim2*90));
	}
	panel.revalidate();	
	panel.repaint();
}
public App() {
		

	setResizable(false);
	addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			if(DodavanjeRecepta.r!=null)
			{
				if(DodavanjeRecepta.r.getNaziv()==null||DodavanjeRecepta.r.getOpisPripreme()==null&&DodavanjeRecepta.r.getVremePripreme()==null)
				{
					try {
						mb.izbrisiRecept(DodavanjeRecepta.r);
					} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
		}
			
		}
			}
		
	});
		try {
			/**
			 * poziv klase za rad sa bazom podataka
			 */
			
			mb=new MenadzerBaza("ReceptiNaDar.accdb");
			lista=mb.uzmiRecepte();
		
			
			
			
		} catch (ClassNotFoundException e1) {
		
			e1.printStackTrace();
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
	
		setTitle("Recepti na dar");
		setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		setForeground(new Color(204, 102, 153));
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setBackground(new Color(255, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 660);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 153));
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Prijava");
		/**
		 * dogadjaj za prijavu korisnika
		 */
		mnNewMenu.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				Prijava p =new Prijava();
				p.setVisible(true);
			}
		});
		
		mnNewMenu.setBackground(new Color(255, 255, 153));
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("Registracija");
		
		/**
		 * dogadjaj za registraciju korisnika
		 */
		mnNewMenu_1.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				Registracija r=new Registracija();
				r.setVisible(true);
		
			}
		});
		menuBar.add(mnNewMenu_1);
		/**
		 * dogadjaj za odjavu korisnika
		 */
		JMenu mnNewMenu_2 = new JMenu("Odjava");
		mnNewMenu_2.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(k==null)
					JOptionPane.showMessageDialog(null, "Niste se prijavili!");
				else
				{
					k=null;
					panelDodajRecept.setVisible(false);
					JOptionPane.showMessageDialog(null, "Uspesno ste se odjavili!");
				}
					
					
			}
		});
		
		menuBar.add(mnNewMenu_2);
		contentPane = new JPanel();
		contentPane.setBorder(UIManager.getBorder("Tree.editorBorder"));
		contentPane.setBackground(new Color(255, 255, 153));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		 panelListaRecepata = new JPanel();
		panelListaRecepata.setBackground(new Color(255, 255, 204));
		panelListaRecepata.setBounds(261, 89, 214, 488);
		panelListaRecepata.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		panelCeoRecept = new JPanel();
		panelCeoRecept.setBorder(UIManager.getBorder("PopupMenu.border"));
		panelCeoRecept.setBackground(new Color(255, 255, 204));
		panelCeoRecept.setBounds(1, 1, 477, 576);
		panelCeoRecept.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			

		JScrollPane scrollPane = new JScrollPane(panelListaRecepata);
		scrollPane.setBounds(251, 91, 227, 486);
		panelListaRecepata.setPreferredSize(new Dimension(190,(lista.size()*90)));
		contentPane.add(scrollPane);

		JScrollPane scrollPane2 = new JScrollPane(panelCeoRecept);
		scrollPane2.setBounds(488, 91, 478, 486);
		panelCeoRecept.setPreferredSize(new Dimension(450, 560));
		contentPane.add(scrollPane2);
		
		JTextPane txtpnNaslov = new JTextPane();
		txtpnNaslov.setEditable(false);
		txtpnNaslov.setFont(new Font("Segoe Script", Font.BOLD | Font.ITALIC, 50));
		txtpnNaslov.setBackground(new Color(255, 255, 153));
		txtpnNaslov.setText("Recepti na dar");
		txtpnNaslov.setBounds(251, 5, 454, 75);
		contentPane.add(txtpnNaslov);
		
		JPanel panelTip = new JPanel();
		panelTip.setBorder(UIManager.getBorder("PopupMenu.border"));
		panelTip.setBackground(new Color(255, 255, 204));
		panelTip.setBounds(10, 91, 231, 486);
		contentPane.add(panelTip);
		panelTip.setLayout(null);
		
		JPanel panelSlanaJela = new JPanel();
		
		panelSlanaJela.setBackground(new Color(255, 255, 153));
		panelSlanaJela.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelSlanaJela.setBounds(33, 123, 151, 54);
		panelTip.add(panelSlanaJela);
		panelSlanaJela.setLayout(null);
		
		JLabel lblSlanaJela = new JLabel("Slana jela");
		lblSlanaJela.setHorizontalAlignment(SwingConstants.CENTER);
		lblSlanaJela.setFont(new Font("Tempus Sans ITC", Font.BOLD, 23));
		lblSlanaJela.setBounds(10, 11, 131, 32);
		panelSlanaJela.add(lblSlanaJela);
		
		JPanel panelSlatkaJela = new JPanel();
		
		panelSlatkaJela.setLayout(null);
		panelSlatkaJela.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelSlatkaJela.setBackground(new Color(255, 255, 153));
		panelSlatkaJela.setBounds(33, 209, 151, 54);
		panelTip.add(panelSlatkaJela);
		
		JLabel lblSlatkaJela = new JLabel("Slatka jela");
		lblSlatkaJela.setHorizontalAlignment(SwingConstants.CENTER);
		lblSlatkaJela.setFont(new Font("Tempus Sans ITC", Font.BOLD, 23));
		lblSlatkaJela.setBounds(10, 11, 131, 32);
		panelSlatkaJela.add(lblSlatkaJela);
		
		JPanel panelPosnaJela = new JPanel();
		
		panelPosnaJela.setLayout(null);
		panelPosnaJela.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelPosnaJela.setBackground(new Color(255, 255, 153));
		panelPosnaJela.setBounds(33, 297, 151, 54);
		panelTip.add(panelPosnaJela);
		
		JLabel lblPosnaSlanaJela = new JLabel("Posna jela");
		lblPosnaSlanaJela.setHorizontalAlignment(SwingConstants.CENTER);
		lblPosnaSlanaJela.setFont(new Font("Tempus Sans ITC", Font.BOLD, 23));
		lblPosnaSlanaJela.setBounds(10, 11, 131, 32);
		panelPosnaJela.add(lblPosnaSlanaJela);
		
		JPanel panelBrzaJela = new JPanel();
	
		panelBrzaJela.setLayout(null);
		panelBrzaJela.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelBrzaJela.setBackground(new Color(255, 255, 153));
		panelBrzaJela.setBounds(33, 387, 151, 54);
		panelTip.add(panelBrzaJela);
		
		JLabel lblBrzaJela = new JLabel("Brza jela");
		lblBrzaJela.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrzaJela.setFont(new Font("Tempus Sans ITC", Font.BOLD, 23));
		lblBrzaJela.setBounds(10, 11, 131, 32);
		panelBrzaJela.add(lblBrzaJela);
		
	
		/**
		 * dogadjaj za pretragu
		 */
		textFieldPretraga = new JTextField();
		textFieldPretraga.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				String pretraga=textFieldPretraga.getText();
				Filter(panelListaRecepata,panelCeoRecept,pretraga);
			}
		});
		
		Filter(panelListaRecepata,panelCeoRecept,textFieldPretraga.getText());
	
		textFieldPretraga.setBounds(10, 32, 211, 20);
		panelTip.add(textFieldPretraga);
		textFieldPretraga.setColumns(10);
		
		JLabel lblPretraga = new JLabel("Pretraga");
		lblPretraga.setFont(new Font("Tempus Sans ITC", Font.BOLD | Font.ITALIC, 18));
		lblPretraga.setBounds(10, 7, 107, 25);
		panelTip.add(lblPretraga);
		
		/**
		 *  otvaranje forme za unos recepata
		 */
		panelDodajRecept = new JPanel();
		panelDodajRecept.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
		 		Selected(panelDodajRecept);
		 		DodavanjeRecepta dodavanjeRecepta = new DodavanjeRecepta();
		 		dodavanjeRecepta.setVisible(true); 		
			}
		});
		
		/**
		 * ukoliko korisnik nije prijavljen ulazak u formu nije dozvoljen
		 */
 		if(k==null)
 		panelDodajRecept.setVisible(false);
		panelDodajRecept.setLayout(null);
		panelDodajRecept.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelDodajRecept.setBackground(new Color(255, 255, 153));
		panelDodajRecept.setBounds(750, 11, 201, 54);
		contentPane.add(panelDodajRecept);
			
		JLabel lblDodajRecept = new JLabel("Dodaj recept");
		lblDodajRecept.setHorizontalAlignment(SwingConstants.CENTER);
		lblDodajRecept.setFont(new Font("Tempus Sans ITC", Font.BOLD, 23));
		lblDodajRecept.setBounds(27, 11, 131, 32);
		panelDodajRecept.add(lblDodajRecept);
		
		/**
		 * dogadjaji prilikom klika tipa recepta
		 */
		
		panelSlanaJela.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Selected(panelSlanaJela);
				if(panelSlanaJela.getBackground()==selected)
				napuniTipove.add("Slano");	
				else
				napuniTipove.remove("Slano");
				Filter(panelListaRecepata,panelCeoRecept,textFieldPretraga.getText());	
			}
		});
				
		panelSlatkaJela.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Selected(panelSlatkaJela);
				if(panelSlatkaJela.getBackground()==selected)
					napuniTipove.add("Slatko");
				else 
					napuniTipove.remove("Slatko");	
				Filter(panelListaRecepata,panelCeoRecept,textFieldPretraga.getText());
					
			}
		});
				
		panelPosnaJela.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				Selected(panelPosnaJela);
				if(panelPosnaJela.getBackground()==selected)
					napuniTipove.add("Posno");
				else
					napuniTipove.remove("Posno");
				Filter(panelListaRecepata,panelCeoRecept,textFieldPretraga.getText());
			}
		});
			
		panelBrzaJela.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				Selected(panelBrzaJela);
				if(panelBrzaJela.getBackground()==selected)
					napuniTipove.add("Brzo");
				else
					napuniTipove.remove("Brzo");
				Filter(panelListaRecepata,panelCeoRecept,textFieldPretraga.getText());
					
			}
		});
				
		
	}
}
