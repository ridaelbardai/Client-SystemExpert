package fstm.ilisi.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.bson.types.ObjectId;

import com.itextpdf.awt.geom.Line2D;
import com.toedter.calendar.JDateChooser;

import fstm.ilisi.controller.Controller;
import fstm.ilisi.model.service.PdfImprimer;
import ma.fstm.ilisi.projet.model.service.Historique;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import javax.swing.border.LineBorder;

class notification extends Thread {

	private List<String[]> regions;

	public notification() {
		super();
		this.regions = new Controller().retreiveStatistiques();
	}

	@Override
	public void run() {

		try {
			for (String[] strings : regions) {
				System.out.println(
						strings[0] + strings[1] + strings[2] + strings[3] + strings[4] + strings[5] + strings[6]);
			}
		} catch (Exception e) {

		}

	}
}

public class Home extends JFrame {
	/**
	 * 
	 */

	private List<String[]> regions;
	private Controller ctr;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNom;
	private JTextField textFieldPrenom;
	private JTextField IdentifianttextField;
	private JTable table;
	private JTextField textFieldAdress;
	private JList<String> list_dst;

	/**
	 * cette fonction sert a transferer les elements entre les deux listes vers les
	 * deux sens
	 */
	public <E> void moveSelectedTo(JList<E> from, JList<E> to) {
		try {
			List<E> entitiesToTransition = from.getSelectedValuesList();
			if (entitiesToTransition == null || entitiesToTransition.isEmpty())
				return;

			DefaultListModel<E> fromModel = (DefaultListModel<E>) from.getModel();
			for (E entity : entitiesToTransition)
				fromModel.removeElement(entity);

			List<E> previouslyLoadedEntities = new ArrayList<>();
			ListModel<E> model = (to.getModel());
			for (int i = 0; i < model.getSize(); i++)
				previouslyLoadedEntities.add(model.getElementAt(i));
			DefaultListModel<E> dlm = new DefaultListModel<>() {
				private static final long serialVersionUID = 1L;
				{
					addAll(previouslyLoadedEntities);
					addAll(entitiesToTransition);
				}
			};
			to.setModel(dlm);
		} catch (Exception e) {
			throw new RuntimeException("Erreur lors du transfer vers une liste ", e);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new notification().start();
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					JFrame.setDefaultLookAndFeelDecorated(true);
					Home frame = new Home();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	String remplirLabel(String[] infos) {

		return infos[0] + " :\ncas positifs :" + infos[1] + "\nguérris :" + infos[2] + "\nmorts :" + infos[3];

	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Home() throws IOException {

		ctr = new Controller();
		this.regions = ctr.retreiveStatistiques();
		setTitle("System Expert Covid19");
		setForeground(Color.DARK_GRAY);
		setBackground(new Color(192, 192, 192));
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\rb99\\eclipse-workspace\\test\\recourses\\doctor.png"));
		setResizable(false);
		CardLayout CL = new CardLayout(0, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 952, 714);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(CL);

		JPanel Identification = new JPanel();
		contentPane.add(Identification, "Identification");
		Identification.setLayout(null);

		IdentifianttextField = new JTextField();
		IdentifianttextField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		IdentifianttextField.setBounds(342, 552, 258, 50);
		Identification.add(IdentifianttextField);
		IdentifianttextField.setColumns(10);

		JPanel histo_consult = new JPanel();
		contentPane.add(histo_consult, "histo_consult");
		histo_consult.setLayout(null);

		JButton voirHistorique = new JButton("");

		voirHistorique.setBorder(new EmptyBorder(0, 0, 0, 0));
		voirHistorique.setIcon(new ImageIcon("C:\\Users\\rb99\\eclipse-workspace\\test\\recourses\\history (1).png"));
		voirHistorique.setBounds(92, 47, 361, 305);
		histo_consult.add(voirHistorique);

		JButton nouveauDiagnostique = new JButton("");
		nouveauDiagnostique.setIcon(new ImageIcon("C:\\Users\\rb99\\eclipse-workspace\\test\\recourses\\add-file.png"));
		nouveauDiagnostique.setBounds(469, 47, 361, 305);
		histo_consult.add(nouveauDiagnostique);

		JLabel Consulter_historique_label = new JLabel("Consulter historique");
		Consulter_historique_label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Consulter_historique_label.setBounds(210, 11, 130, 25);
		histo_consult.add(Consulter_historique_label);

		JLabel Diagnostique_Label = new JLabel("Diagnostique");
		Diagnostique_Label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Diagnostique_Label.setBounds(606, 11, 97, 25);
		histo_consult.add(Diagnostique_Label);

		JButton btnRevenirVersIdentification = new JButton("");
		btnRevenirVersIdentification.setForeground(Color.WHITE);
		btnRevenirVersIdentification.setBorder(BorderFactory.createEmptyBorder());
		btnRevenirVersIdentification.setContentAreaFilled(false);
		btnRevenirVersIdentification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CL.show(contentPane, "Identification");
			}
		});
		btnRevenirVersIdentification
				.setIcon(new ImageIcon("C:\\Users\\rb99\\eclipse-workspace\test\recourses\\icons8-back-50.png"));
		btnRevenirVersIdentification.setBounds(10, 11, 30, 30);
		histo_consult.add(btnRevenirVersIdentification);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\rb99\\eclipse-workspace\\test\\images\\protection.jpg"));
		lblNewLabel_1.setBounds(24, 363, 892, 291);
		histo_consult.add(lblNewLabel_1);

		JButton Commencer_btn = new JButton("Commencer");
		// Verifier si patient existe ou non
		Commencer_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = IdentifianttextField.getText();
				if (id.trim().length() < 5 || id.trim().length() > 8) {
					String message = "CIN invalide !";
					JOptionPane.showMessageDialog(contentPane, message, "CIN invalide", JOptionPane.ERROR_MESSAGE);
				} else {

					if (ctr.VerifPat(id)) {
						IdentifianttextField.setText("");
						CL.show(contentPane, "histo_consult");
					} else {
						String message = "Vous n'avez pas encore un dossier ?";
						int answer = JOptionPane.showConfirmDialog(contentPane, message);
						if (answer == JOptionPane.YES_OPTION) {
							// User clicked YES.
							IdentifianttextField.setText("");
							Controller.id = id;
							CL.show(contentPane, "InfoUserPanel");
						} else if (answer == JOptionPane.NO_OPTION) {

						}
					}
				}

			}
		});
		Commencer_btn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Commencer_btn.setBounds(373, 613, 196, 41);
		Identification.add(Commencer_btn);

		JLabel lblNewLabel_3 = new JLabel("Entrez votre CIN");
		lblNewLabel_3.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(416, 530, 103, 22);
		Identification.add(lblNewLabel_3);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Covid19 au maroc en chiffres :",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(4, -2, 912, 521);
		Identification.add(panel_1);
		panel_1.setLayout(null);

		JTextPane lblNewLabeltanger = new JTextPane();
		lblNewLabeltanger.setBounds(460, 11, 112, 78);
		panel_1.add(lblNewLabeltanger);
		lblNewLabeltanger.setBackground(SystemColor.controlHighlight);
		lblNewLabeltanger.setEditable(false);

		JTextPane lblNewLabelrabat = new JTextPane();
		lblNewLabelrabat.setBounds(338, 48, 112, 65);
		panel_1.add(lblNewLabelrabat);
		lblNewLabelrabat.setBackground(SystemColor.controlHighlight);
		lblNewLabelrabat.setEditable(false);

		JTextPane lblNewLabelguelmim = new JTextPane();
		lblNewLabelguelmim.setBounds(116, 202, 112, 65);
		panel_1.add(lblNewLabelguelmim);
		lblNewLabelguelmim.setBackground(SystemColor.controlHighlight);
		lblNewLabelguelmim.setEditable(false);

		JTextPane lblNewLabelfes = new JTextPane();
		lblNewLabelfes.setBounds(744, 202, 112, 65);
		panel_1.add(lblNewLabelfes);
		lblNewLabelfes.setBackground(SystemColor.controlHighlight);
		lblNewLabelfes.setEditable(false);

		JTextPane lblNewLabelerrachidia = new JTextPane();
		lblNewLabelerrachidia.setBounds(622, 244, 112, 65);
		panel_1.add(lblNewLabelerrachidia);
		lblNewLabelerrachidia.setBackground(SystemColor.controlHighlight);
		lblNewLabelerrachidia.setEditable(false);

		JTextPane lblNewLabeldakhla = new JTextPane();
		lblNewLabeldakhla.setBounds(251, 445, 112, 65);
		panel_1.add(lblNewLabeldakhla);
		lblNewLabeldakhla.setBackground(SystemColor.controlHighlight);
		lblNewLabeldakhla.setEditable(false);

		JTextPane lblNewLabellaayoun = new JTextPane();
		lblNewLabellaayoun.setBounds(338, 370, 112, 65);
		panel_1.add(lblNewLabellaayoun);
		lblNewLabellaayoun.setBackground(SystemColor.controlHighlight);
		lblNewLabellaayoun.setEditable(false);

		JTextPane lblNewLabelagadir = new JTextPane();
		lblNewLabelagadir.setBounds(466, 340, 112, 65);
		panel_1.add(lblNewLabelagadir);
		lblNewLabelagadir.setBackground(SystemColor.controlHighlight);
		lblNewLabelagadir.setEditable(false);

		JTextPane lblNewLabelbenimellal = new JTextPane();
		lblNewLabelbenimellal.setBounds(500, 264, 112, 65);
		panel_1.add(lblNewLabelbenimellal);
		lblNewLabelbenimellal.setBackground(SystemColor.controlHighlight);
		lblNewLabelbenimellal.setEditable(false);

		JTextPane lblNewLabelcasa = new JTextPane();
		lblNewLabelcasa.setBounds(304, 124, 112, 65);
		panel_1.add(lblNewLabelcasa);
		lblNewLabelcasa.setBackground(SystemColor.controlHighlight);
		lblNewLabelcasa.setEditable(false);

		JTextPane lblNewLabeloujda = new JTextPane();
		lblNewLabeloujda.setBounds(776, 24, 112, 65);
		panel_1.add(lblNewLabeloujda);
		lblNewLabeloujda.setBackground(SystemColor.control);
		lblNewLabeloujda.setEditable(false);

		JTextPane lblNewLabelmerrakech = new JTextPane();
		lblNewLabelmerrakech.setBounds(238, 202, 112, 65);
		panel_1.add(lblNewLabelmerrakech);
		lblNewLabelmerrakech.setBackground(SystemColor.controlHighlight);
		lblNewLabelmerrakech.setEditable(false);

		JLabel carteMaroc = new JLabel("");
		carteMaroc.setBounds(28, 27, 855, 494);
		panel_1.add(carteMaroc);
		carteMaroc.setIcon(new ImageIcon("C:\\Users\\rb99\\Desktop\\marocresized.png"));

		/******************************************************************************************************************************************/

		for (String[] region : regions) {
			switch (region[0]) {
			case "Beni Mellal-Khénifra":
				lblNewLabelbenimellal.setText(remplirLabel(region));
				break;
			case "Casablanca-Settat":
				lblNewLabelcasa.setText(remplirLabel(region));
				break;
			case "Eddakhla-Oued Eddahab":
				lblNewLabeldakhla.setText(remplirLabel(region));
				break;
			case "Drâa-Tafilalet":
				lblNewLabelerrachidia.setText(remplirLabel(region));
				break;
			case "Fés-Meknés":
				lblNewLabelfes.setText(remplirLabel(region));
				break;
			case "Guelmim-Oued Noun":
				lblNewLabelguelmim.setText(remplirLabel(region));
				break;
			case "Oriental":
				lblNewLabeloujda.setText(remplirLabel(region));
				break;
			case "Laayoune-Sakia El Hamra":
				lblNewLabellaayoun.setText(remplirLabel(region));
				break;
			case "Marrakech-Safi":
				lblNewLabelmerrakech.setText(remplirLabel(region));
				break;
			case "Rabat-Salé-Kénitra":
				lblNewLabelrabat.setText(remplirLabel(region));
				break;
			case "Souss-Massa":
				lblNewLabelagadir.setText(remplirLabel(region));
				break;
			case "Tanger-Tétouan-Al Hoceima":
				lblNewLabeltanger.setText(remplirLabel(region));
				break;

			default:
				break;
			}
		}

		/******************************************************************************************************************************************/

		JPanel InfoUserPanel = new JPanel();
		contentPane.add(InfoUserPanel, "InfoUserPanel");
		InfoUserPanel.setLayout(new BorderLayout(0, 0));

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(159, 172, 205, 20);

		JPanel IUP_South = new JPanel();
		FlowLayout fl_IUP_South = (FlowLayout) IUP_South.getLayout();
		fl_IUP_South.setAlignment(FlowLayout.RIGHT);
		InfoUserPanel.add(IUP_South, BorderLayout.SOUTH);
		// pour terminer l'inscription
		JButton suivant = new JButton("Suivant");

		IUP_South.add(suivant);

		JPanel IUP_Center = new JPanel();
		InfoUserPanel.add(IUP_Center, BorderLayout.CENTER);
		IUP_Center.setLayout(null);

		JPanel panel = new JPanel();

		panel.add(dateChooser);
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Entrez vos informations : ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 382, 559);
		IUP_Center.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nom :");
		lblNewLabel.setBounds(24, 25, 93, 39);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 15));

		JLabel lblPrenom = new JLabel("Prenom :");
		lblPrenom.setBounds(24, 91, 93, 39);
		panel.add(lblPrenom);
		lblPrenom.setFont(new Font("Dialog", Font.BOLD, 15));

		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
		panel.add(datePicker);

		JLabel lblNewLabel_1_1 = new JLabel("Age :");
		lblNewLabel_1_1.setBounds(24, 158, 93, 39);
		panel.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.BOLD, 15));

		textFieldNom = new JTextField();
		textFieldNom.setBounds(159, 29, 206, 30);
		panel.add(textFieldNom);
		textFieldNom.setFont(new Font("Dialog", Font.PLAIN, 15));
		textFieldNom.setColumns(10);

		textFieldPrenom = new JTextField();
		textFieldPrenom.setBounds(158, 95, 206, 30);
		panel.add(textFieldPrenom);
		textFieldPrenom.setFont(new Font("Dialog", Font.PLAIN, 15));
		textFieldPrenom.setColumns(10);

		JComboBox comboBoxRegions = new JComboBox();
		comboBoxRegions.setBounds(160, 254, 204, 22);
		comboBoxRegions.setModel(new DefaultComboBoxModel<String>(ctr.retreiveRegions()));
		panel.add(comboBoxRegions);

		JComboBox comboBoxVilles = new JComboBox();
		comboBoxVilles.setBounds(160, 330, 204, 22);
		panel.add(comboBoxVilles);

		comboBoxRegions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String reg = comboBoxRegions.getSelectedItem().toString();
				comboBoxVilles.setModel(new DefaultComboBoxModel<String>(ctr.VilleParreg(reg)));
			}
		});

		JLabel lblNewLabel_1_1_1 = new JLabel("Region :");
		lblNewLabel_1_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_1_1_1.setBounds(24, 247, 93, 39);
		panel.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_2 = new JLabel("ville :");
		lblNewLabel_1_1_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_1_1_2.setBounds(24, 320, 93, 39);
		panel.add(lblNewLabel_1_1_2);

		JLabel lblAdress = new JLabel("Adresse :");
		lblAdress.setFont(new Font("Dialog", Font.BOLD, 15));
		lblAdress.setBounds(24, 398, 93, 39);
		panel.add(lblAdress);

		textFieldAdress = new JTextField();
		textFieldAdress.setFont(new Font("Dialog", Font.PLAIN, 15));
		textFieldAdress.setColumns(10);
		textFieldAdress.setBounds(158, 402, 206, 112);
		panel.add(textFieldAdress);

		JPanel panelImg = new JPanel();
		panelImg.setBounds(401, 36, 515, 534);
		JLabel imgLabel = new JLabel(
				new ImageIcon("C:\\Users\\rb99\\eclipse-workspace\\test\\images\\inscription.png"));
		panelImg.add(imgLabel);

		IUP_Center.add(panelImg);

		JPanel IUP_North = new JPanel();
		InfoUserPanel.add(IUP_North, BorderLayout.NORTH);
		IUP_North.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnRevenirVersIdentification_1 = new JButton("");
		btnRevenirVersIdentification_1.setHorizontalAlignment(SwingConstants.LEFT);
		IUP_North.add(btnRevenirVersIdentification_1);
		btnRevenirVersIdentification_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CL.previous(contentPane);
			}
		});
		btnRevenirVersIdentification_1
				.setIcon(new ImageIcon("C:\\Users\\rb99\\eclipse-workspace\\test\\recourses\\icons8-back-50.png"));
		btnRevenirVersIdentification_1.setForeground(Color.WHITE);
		btnRevenirVersIdentification_1.setContentAreaFilled(false);
		btnRevenirVersIdentification_1.setBorder(BorderFactory.createEmptyBorder());

		JPanel panelDesSymptomes = new JPanel();
		contentPane.add(panelDesSymptomes, "panelDesSymptomes");
		panelDesSymptomes.setLayout(new BorderLayout(0, 0));

		JPanel PDS_South = new JPanel();
		panelDesSymptomes.add(PDS_South, BorderLayout.SOUTH);
		PDS_South.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton btnNewButton = new JButton("precedent");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CL.show(contentPane, "histo_consult");
			}
		});
		PDS_South.add(btnNewButton);

		JLabel titre_PDS = new JLabel("symptomes");
		titre_PDS.setHorizontalAlignment(SwingConstants.CENTER);
		panelDesSymptomes.add(titre_PDS, BorderLayout.NORTH);

		JPanel PDS_Center = new JPanel();
		panelDesSymptomes.add(PDS_Center, BorderLayout.CENTER);
		PDS_Center.setLayout(new BorderLayout(0, 0));

		JPanel panel_temperature = new JPanel();
		PDS_Center.add(panel_temperature, BorderLayout.NORTH);
		panel_temperature.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JSpinner spinnerTemperature = new JSpinner();
		spinnerTemperature.setFont(new Font("Tahoma", Font.PLAIN, 15));
		spinnerTemperature.setModel(new SpinnerNumberModel(37, null, 45, 1));
		panel_temperature.add(spinnerTemperature);

		JLabel lblTemperature = new JLabel("Temperature ");
		lblTemperature.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_temperature.add(lblTemperature);

		JPanel panel_symptomes = new JPanel();
		PDS_Center.add(panel_symptomes, BorderLayout.CENTER);
		panel_symptomes.setLayout(null);
		// remplire liste des symptomes
		String[] values = ctr.retreiveSymptoms();
		DefaultListModel<String> listModeldst = new DefaultListModel<String>();
		DefaultListModel<String> listModelsrc = new DefaultListModel<String>();
		for (int i = 0; i < values.length - 1; i++) {
			listModelsrc.addElement(values[i]);
		}

		list_dst = new JList<String>(listModeldst);
		list_dst.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list_dst.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		list_dst.setBounds(572, 106, 305, 282);
		panel_symptomes.add(list_dst);

		JList<String> list_src = new JList<String>(listModelsrc);
		JButton btnNewButton_2 = new JButton(">>");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveSelectedTo(list_src, list_dst);
			}
		});
		btnNewButton_2.setBounds(390, 118, 130, 87);
		panel_symptomes.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("<<");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveSelectedTo(list_dst, list_src);
			}
		});
		btnNewButton_3.setBounds(390, 272, 130, 87);
		panel_symptomes.add(btnNewButton_3);

		JCheckBox chckbxContact = new JCheckBox("Vous etiez en contact avec quelequ'un");
		chckbxContact.setBounds(617, 396, 218, 23);
		panel_symptomes.add(chckbxContact);
		list_src.setBounds(38, 106, 305, 282);
		panel_symptomes.add(list_src);
		list_src.setVisibleRowCount(10);
		list_src.setFont(new Font("Tahoma", Font.PLAIN, 13));

		list_src.setToolTipText("");
		list_src.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list_src.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		JPanel panel_maladiesChroniques = new JPanel();
		PDS_Center.add(panel_maladiesChroniques, BorderLayout.SOUTH);
		panel_maladiesChroniques.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panel_titre = new JPanel();
		panel_maladiesChroniques.add(panel_titre);

		JLabel lblNewLabel_2 = new JLabel("Maladie Chroniques ?");
		panel_titre.add(lblNewLabel_2);

		JPanel panel_CheckBoxs = new JPanel();
		panel_maladiesChroniques.add(panel_CheckBoxs);

		JCheckBox cbDiabete = new JCheckBox("Diabete");
		cbDiabete.setFont(new Font("Dialog", Font.BOLD, 15));
		panel_CheckBoxs.add(cbDiabete);

		JCheckBox cbCardiaque = new JCheckBox("cardiaque");
		cbCardiaque.setFont(new Font("Dialog", Font.BOLD, 15));
		panel_CheckBoxs.add(cbCardiaque);

		JCheckBox cbHypertendu = new JCheckBox("Hypertendu");
		cbHypertendu.setHorizontalAlignment(SwingConstants.CENTER);
		cbHypertendu.setFont(new Font("Dialog", Font.BOLD, 15));
		panel_CheckBoxs.add(cbHypertendu);

		// Button d'envoi du diagnostique
		JButton Terminer = new JButton("Terminer");
		Terminer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// appel du controlleur pour executer l'action envoyerDiagnostique()
				ctr.effectuerDiagnostique((int) spinnerTemperature.getValue(), list_dst, chckbxContact, cbDiabete,
						cbCardiaque, cbHypertendu);
				//rendre tous les cases a cocher et les listes vides
				spinnerTemperature.setValue(37);
				cbHypertendu.setSelected(false);
				cbCardiaque.setSelected(false);
				cbDiabete.setSelected(false);
				chckbxContact.setSelected(false);
				
				int begn = 0;
			      int end = list_dst.getModel().getSize() - 1;
			      if (end >= 0) {
			    	  list_dst.setSelectionInterval(begn, end);
			      }
			      moveSelectedTo(list_dst, list_src);
				
				CL.show(contentPane, "histo_consult");

			}
		});
		PDS_South.add(Terminer);

		JPanel historique = new JPanel();
		contentPane.add(historique, "historique");
		historique.setLayout(new BorderLayout(0, 0));

		JPanel histoPanelCenter = new JPanel();
		historique.add(histoPanelCenter);
		histoPanelCenter.setLayout(null);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CL.show(contentPane, "histo_consult");
			}
		});
		btnNewButton_1
				.setIcon(new ImageIcon("C:\\Users\\rb99\\eclipse-workspace\\test\\recourses\\icons8-back-50.png"));
		btnNewButton_1.setBounds(10, 11, 34, 35);
		histoPanelCenter.add(btnNewButton_1);
		String col[] = { "ID", "DATE", "POSSIBILITE PRESENCE" };

		DefaultTableModel tableModel = new DefaultTableModel(null, col);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 56, 906, 321);
		histoPanelCenter.add(scrollPane_1);
		table = new JTable(tableModel);
		table.setCellSelectionEnabled(true);
		scrollPane_1.setViewportView(table);
		table.setRowHeight(40);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JButton btnNewButton_4 = new JButton("Imprimer");
		btnNewButton_4.setIcon(new ImageIcon("C:\\Users\\rb99\\eclipse-workspace\\test\\images\\icons8-pdf-48.png"));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object value = table.getModel().getValueAt(table.getSelectedRow(), 0);
				try {
					JFileChooser chooser = new JFileChooser();
					int option = chooser.showSaveDialog(null);
					if (option == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						// download(file);
//		                    new FirstPdf().doit(file.getAbsolutePath()+".pdf");
						new PdfImprimer(file.getAbsolutePath() + ".pdf", (ObjectId) value).makePdf1();
					} else
						System.out.println("No file was selected.");
					ctr.AffichageDiagnostique((ObjectId) value);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_4.setBounds(393, 444, 136, 86);
		histoPanelCenter.add(btnNewButton_4);

		JLabel lblNewLabel_4 = new JLabel(
				"Selectionez l'identifiant du diagnostique que vous voulez t\u00E9l\u00E9charger\r\n");
		lblNewLabel_4.setBounds(20, 392, 335, 14);
		histoPanelCenter.add(lblNewLabel_4);
		Button impPdf = new Button("imprimer");
		voirHistorique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CL.show(contentPane, "historique");

				// List<Diagnostic> l =
				// ctr.retreiveDiagnostiques(Controller.p.getIdentifiant());
				List<Historique> l = ctr.afficherHistorique(Controller.p.get_id());

				System.out.println(l);
				for (Historique d : l) {
					Object[] objs = { d.getIdDioagno(), d.getDateDia(), d.getPossi_covid(), };
					tableModel.addRow(objs);
				}

			}
		});
		
		
		// fair un diagnostique
		nouveauDiagnostique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listModeldst.clear();
				CL.show(contentPane, "panelDesSymptomes");
			}
		});
		
		
		suivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom = textFieldNom.getText();
				String prenom = textFieldPrenom.getText();
				String adress = textFieldAdress.getText();
				String identifiant = Controller.id;
				Date datenaiss = dateChooser.getDate();
				String ville = comboBoxVilles.getSelectedItem().toString();
				ctr.ctrInscrip(nom, prenom, identifiant, datenaiss, adress, ville);
				CL.show(contentPane, "histo_consult");
			}
		});
	}

}
