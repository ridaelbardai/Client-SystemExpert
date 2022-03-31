package fstm.ilisi.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import fstm.ilisi.controller.HomeController;
import fstm.ilisi.model.*;

import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.Toolkit;

public class Home extends JFrame {
	private Diagnostique maDiagnostique;
	private JPanel contentPane;
	private JTextField textFieldNom;
	private JTextField textFieldPrenom;
	private JTextField IdentifianttextField;
	private JTable table;

	public <E> void moveSelectedTo(JList from, JList to) {

		try {
			List<E> entitiesToTransition = from.getSelectedValuesList();
			if (entitiesToTransition == null || entitiesToTransition.isEmpty())
				return;

			DefaultListModel fromModel = (DefaultListModel) from.getModel();
			for (E entity : entitiesToTransition)
				fromModel.removeElement(entity);

			List<E> previouslyLoadedEntities = new ArrayList<>();
			ListModel<E> model = (to.getModel());
			for (int i = 0; i < model.getSize(); i++)
				previouslyLoadedEntities.add(model.getElementAt(i));
			DefaultListModel<E> dlm = new DefaultListModel<>() {
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

	// pour prendre les symptomes
	void remplireSymptomes(fstm.ilisi.model.Diagnostique dgstq, JList list) {
		for (int i = 0; i < list.getModel().getSize(); i++) {
//	            String item = list.getModel().getElementAt(i).toString();
			dgstq.AjouterSymptome(new Symptome(0, "title", null, list.getModel().getElementAt(i).toString()));
		}
	}

	// pour prendre les valeurs des maladies chroniques
	public void remplirMaladiesChroniques(JCheckBox cbd, JCheckBox cbc, JCheckBox cbh) {
		if (cbd.isSelected())
			maDiagnostique.AjouterMaladieC(new MaladieCronique(0, ((JCheckBox) cbd).getText(), 0));
		if (cbc.isSelected())
			maDiagnostique.AjouterMaladieC(new MaladieCronique(0, ((JCheckBox) cbc).getText(), 0));
		if (cbh.isSelected())
			maDiagnostique.AjouterMaladieC(new MaladieCronique(0, ((JCheckBox) cbh).getText(), 0));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	public Home() {
		setTitle("System Expert Covid19");
		setForeground(Color.DARK_GRAY);
		setBackground(new Color(192, 192, 192));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\rb99\\Desktop\\doctor.png"));
		setResizable(false);
		setAlwaysOnTop(true);
		CardLayout CL = new CardLayout(0, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 812, 567);
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
		IdentifianttextField.setBounds(260, 311, 248, 50);
		Identification.add(IdentifianttextField);
		IdentifianttextField.setColumns(10);

		JPanel histo_consult = new JPanel();
		contentPane.add(histo_consult, "histo_consult");
		histo_consult.setLayout(null);

		JButton voirHistorique = new JButton("");
		voirHistorique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CL.show(contentPane, "historique");
			}
		});
		voirHistorique.setBorder(new EmptyBorder(0, 0, 0, 0));
		voirHistorique.setIcon(new ImageIcon("C:\\Users\\rb99\\Desktop\\history (1).png"));
		voirHistorique.setBounds(92, 134, 233, 186);
		histo_consult.add(voirHistorique);

		JButton nouveauDiagnostique = new JButton("");
		nouveauDiagnostique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CL.show(contentPane, "InfoUserPanel");
			}
		});
		nouveauDiagnostique.setIcon(new ImageIcon("C:\\Users\\rb99\\Desktop\\add-file.png"));
		nouveauDiagnostique.setBounds(469, 134, 233, 186);
		histo_consult.add(nouveauDiagnostique);

		JLabel Consulter_historique_label = new JLabel("Consulter historique");
		Consulter_historique_label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Consulter_historique_label.setBounds(150, 98, 130, 25);
		histo_consult.add(Consulter_historique_label);

		JLabel Diagnostique_Label = new JLabel("Diagnostique");
		Diagnostique_Label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Diagnostique_Label.setBounds(549, 98, 97, 25);
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
		btnRevenirVersIdentification.setIcon(new ImageIcon("C:\\Users\\rb99\\Desktop\\icons8-back-50.png"));
		btnRevenirVersIdentification.setBounds(10, 11, 30, 30);
		histo_consult.add(btnRevenirVersIdentification);

		JButton Commencer_btn = new JButton("Commencer");
		Commencer_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = IdentifianttextField.getText();
				if (id.equals("123")) {
					CL.show(contentPane, "histo_consult");
				} else {
					String message = "Vous n'avez pas encore un dossier ?";
				    int answer = JOptionPane.showConfirmDialog(contentPane, message);
				    if (answer == JOptionPane.YES_OPTION) {
				      // User clicked YES.
				    	CL.show(contentPane, "InfoUserPanel");
				    } else if (answer == JOptionPane.NO_OPTION) {
				     
				    }
				}

			}
		});
		Commencer_btn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Commencer_btn.setBounds(288, 384, 196, 41);
		Identification.add(Commencer_btn);
		
		JPanel image = new JPanel();
		image.setBounds(172, 11, 423, 325);
		Identification.add(image);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\rb99\\Desktop\\medical-record.png"));
		image.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Entrez le numero de votre dossier medical");
		lblNewLabel_3.setBounds(288, 458, 200, 22);
		Identification.add(lblNewLabel_3);

		JPanel InfoUserPanel = new JPanel();
		contentPane.add(InfoUserPanel, "InfoUserPanel");
		InfoUserPanel.setLayout(new BorderLayout(0, 0));

		JPanel IUP_South = new JPanel();
		FlowLayout fl_IUP_South = (FlowLayout) IUP_South.getLayout();
		fl_IUP_South.setAlignment(FlowLayout.RIGHT);
		InfoUserPanel.add(IUP_South, BorderLayout.SOUTH);

		JButton suivant = new JButton("Suivant");
		suivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CL.next(contentPane);
			}
		});
		IUP_South.add(suivant);

		JPanel IUP_Center = new JPanel();
		InfoUserPanel.add(IUP_Center, BorderLayout.CENTER);
		IUP_Center.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Entrez vos informations : ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(32, 100, 335, 272);
		IUP_Center.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nom :");
		lblNewLabel.setBounds(24, 25, 93, 39);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 15));

		JLabel lblPrenom = new JLabel("Prenom :");
		lblPrenom.setBounds(24, 109, 93, 39);
		panel.add(lblPrenom);
		lblPrenom.setFont(new Font("Dialog", Font.BOLD, 15));

		JLabel lblNewLabel_1_1 = new JLabel("Age :");
		lblNewLabel_1_1.setBounds(24, 195, 93, 39);
		panel.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.BOLD, 15));

		textFieldNom = new JTextField();
		textFieldNom.setBounds(158, 25, 114, 30);
		panel.add(textFieldNom);
		textFieldNom.setFont(new Font("Dialog", Font.PLAIN, 15));
		textFieldNom.setColumns(10);

		textFieldPrenom = new JTextField();
		textFieldPrenom.setBounds(158, 113, 114, 30);
		panel.add(textFieldPrenom);
		textFieldPrenom.setFont(new Font("Dialog", Font.PLAIN, 15));
		textFieldPrenom.setColumns(10);

		JSpinner spinnerAge = new JSpinner();
		spinnerAge.setBounds(158, 197, 47, 20);
		panel.add(spinnerAge);
		spinnerAge.setModel(new SpinnerNumberModel(20, 1, 100, 1));

		JPanel panelImg = new JPanel();
		panelImg.setBounds(401, 36, 375, 384);
		JLabel imgLabel = new JLabel(new ImageIcon("C:\\Users\\rb99\\Desktop\\doctor.png"));
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
		btnRevenirVersIdentification_1.setIcon(new ImageIcon("C:\\Users\\rb99\\Desktop\\icons8-back-50.png"));
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
				CL.previous(contentPane);
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
		spinnerTemperature.setModel(new SpinnerNumberModel(37, null, 45, 1));
		panel_temperature.add(spinnerTemperature);

		JLabel label_temperature = new JLabel("Temperature : ");
		label_temperature.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_temperature.add(label_temperature);

		JPanel panel_symptomes = new JPanel();
		PDS_Center.add(panel_symptomes, BorderLayout.CENTER);
		panel_symptomes.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 85, 305, 149);
		panel_symptomes.add(scrollPane);
		String[] values = new String[] { "fievre", "fatigue", "toux seche", "diarrhe", "essouflement", "chute",
				"frisson", "congestion nasal", "gorge seche", "ecoulement nasal", "dyspnee", "douleurs musculaires",
				"maux de tete", "perte de gout", "perte de l'odorat", "confusion", "nause", "vomissement",
				"conjonctivite" };
		DefaultListModel listModelsrc = new DefaultListModel();
		for (int i = 0; i < values.length; i++) {
			listModelsrc.addElement(values[i]);
		}
		JList list_src = new JList(listModelsrc);
		list_src.setVisibleRowCount(10);
		scrollPane.setViewportView(list_src);
		list_src.setFont(new Font("Tahoma", Font.PLAIN, 13));

		list_src.setToolTipText("");
		list_src.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list_src.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		DefaultListModel listModeldst = new DefaultListModel();
		JList list_dst = new JList();
		list_dst.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list_dst.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		list_dst.setBounds(451, 85, 305, 149);
		panel_symptomes.add(list_dst);

		JButton btnNewButton_2 = new JButton(">>");
		btnNewButton_2.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				moveSelectedTo(list_src, list_dst);
			}
		});
		btnNewButton_2.setBounds(343, 117, 98, 26);
		panel_symptomes.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("<<");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveSelectedTo(list_dst, list_src);
			}
		});
		btnNewButton_3.setBounds(343, 154, 98, 26);
		panel_symptomes.add(btnNewButton_3);

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

		/////////////////////////////////////////////////////////////////////// FIRE
		JButton Terminer = new JButton("Terminer");
		Terminer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Patient p = new Patient(0, textFieldNom.getText(), textFieldPrenom.getText(), "khadija21", "1234", null,
						"hay mohammadi",
						new Ville(0, "title", new Region(0, getName())));
				p.setAge((int) spinnerAge.getValue());
				p.setTemperature((int) spinnerTemperature.getValue());
				maDiagnostique = new Diagnostique(p, null, 0);
				remplirMaladiesChroniques(cbDiabete, cbCardiaque, cbHypertendu);
				remplireSymptomes(maDiagnostique, list_dst);
//				System.out.println(maDiagnostique);
				try {
					new HomeController().envoyer(maDiagnostique);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

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
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\rb99\\Desktop\\icons8-back-50.png"));
		btnNewButton_1.setBounds(10, 11, 34, 35);
		histoPanelCenter.add(btnNewButton_1);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setEnabled(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id", "Date", "Symptomes", "Resultat", "Commentaire"
			}
		));
		table.setBorder(UIManager.getBorder("PasswordField.border"));
		table.setBounds(10, 121, 766, 231);
		histoPanelCenter.add(table);

		//////////////////////////////////////////////////////////////////////////
	}
}
