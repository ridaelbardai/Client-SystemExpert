package fstm.ilisi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JList;

import org.bson.types.ObjectId;

import ma.fstm.ilisi.projet.model.bo.CronicDisease;
import ma.fstm.ilisi.projet.model.bo.Diagnostic;
import ma.fstm.ilisi.projet.model.bo.Patient;
import ma.fstm.ilisi.projet.model.bo.Region;
import ma.fstm.ilisi.projet.model.bo.Symptom;
import ma.fstm.ilisi.projet.model.bo.Ville;
import ma.fstm.ilisi.projet.model.service.Request;

public class Controller {

	String server = "localhost";
	public static Patient p;
	public static String id;

	public void effectuerDiagnostique(int temperature, JList<String> listeDesSymptomes, JCheckBox contact,
			JCheckBox cbd, JCheckBox cbc, JCheckBox cbh) {
		Diagnostic diagno = new Diagnostic();

		if (cbd.isSelected())
			diagno.addCronic(new CronicDisease(((JCheckBox) cbd).getText()));
		if (cbc.isSelected())
			diagno.addCronic(new CronicDisease(((JCheckBox) cbc).getText()));
		if (cbh.isSelected())
			diagno.addCronic(new CronicDisease(((JCheckBox) cbh).getText()));

		if (contact.isSelected()) {
			diagno.addSymptom(new Symptom("contact covid"));
			diagno.setContact(true);
		}
		for (int i = 0; i < listeDesSymptomes.getModel().getSize(); i++)
			diagno.addSymptom(new Symptom(listeDesSymptomes.getModel().getElementAt(i)));

		diagno.setDate_diagnostic(new Date());
		diagno.setTemperature(temperature);
		diagno.setPatient(p);
		// envoi vers serveur
		try {
			System.out.println(diagno);
			envoyerDiagnostique(diagno);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public String[] retreiveSymptoms() throws IOException {
		List<Symptom> sympt = null;
		System.out.println("Demande liste des symptomes");
		try (Socket socket = new Socket(server, 234)) {
			System.out.println("Connexion etablis avec systeme");
			// pour la recuperation

			// pour l'envoie
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			Request req = new Request(null, 7);

			objectOutputStream.writeObject(req);
			System.out.println(req);
			InputStream is = socket.getInputStream();
			System.out.println(is);
			ObjectInputStream objectInputStream = new ObjectInputStream(is);
			sympt = (List<Symptom>) objectInputStream.readObject();
			try {
				is.close();
				objectOutputStream.close();
				socket.close();
				System.out.println("fermeture session \n\n");

			} catch (IOException i) {
				System.out.println(i);
			}
		} catch (IOException e) {

			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
//		System.out.println(sympt);
		String[] sy = new String[sympt.size()];
		for (int i = 0; i < sympt.size(); i++) {
			sy[i] = sympt.get(i).getSymName();
		}
		return sy;
	}

	@SuppressWarnings("unchecked")
	public String[] retreiveRegions() {
		List<Region> regions = new ArrayList<Region>();
		try (Socket socket = new Socket(server, 234)) {
			// pour la recuperation

			// pour l'envoie
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			Request req = new Request(null, 6);

			objectOutputStream.writeObject(req);
			System.out.println(req);
			System.out.println("Connexion reussite 1!");
			InputStream is = socket.getInputStream();
			System.out.println(is);
			ObjectInputStream objectInputStream = new ObjectInputStream(is);
			System.out.println("Connexion reussite !");
			regions = (List<Region>) objectInputStream.readObject();
			try {
				is.close();
				objectOutputStream.close();
				socket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		} catch (IOException e) {

			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		System.out.println(regions);
		String[] sy = new String[regions.size()];
		for (int i = 0; i < regions.size(); i++) {
			sy[i] = regions.get(i).getRegionName();
		}
		return sy;
	}

	@SuppressWarnings("unchecked")
	public String[] VilleParreg(String re) {
		List<Ville> villes = new ArrayList<Ville>();
		try (Socket socket = new Socket(server, 234)) {
			// pour l'envoie
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			Request req = new Request(re, 5);

			objectOutputStream.writeObject(req);
			System.out.println(req);
			System.out.println("Connexion reussite 1!");
			InputStream is = socket.getInputStream();
			System.out.println(is);
			ObjectInputStream objectInputStream = new ObjectInputStream(is);
			System.out.println("Connexion reussite !");
			villes = (List<Ville>) objectInputStream.readObject();
			try {
				is.close();
				objectOutputStream.close();
				socket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		} catch (IOException e) {

			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		System.out.println(villes);
		String[] sy = new String[villes.size()];
		for (int i = 0; i < villes.size(); i++) {
			sy[i] = villes.get(i).getVilleName();
		}
		return sy;
	}

	public boolean VerifPat(String CNE) {
		System.out.println("Verification si patient existe");
		try (Socket socket = new Socket(server, 234)) {
			// pour l'envoie
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			// requete a envoyer
			Request req = new Request(CNE, 1);
			// envoie de l'objet
			objectOutputStream.writeObject(req);
			System.out.println(req);
			// pour la reception
			InputStream is = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(is);
			// reception de la reponse (p different de null si ce patient existe vraiment)
			p = (Patient) objectInputStream.readObject();
			// fermeture de la connexion
			try {
				is.close();
				objectOutputStream.close();
				socket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		} catch (IOException e) {

			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		if (p != null) {
			return true;
		} else {
			return false;
		}
	}

	public void Inscription(Patient newp) {
		Patient p = new Patient();
		System.out.println("envoi diagnostique ...");
		try (Socket socket = new Socket(server, 234)) {
			// pour la recuperation

			// pour l'envoie
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			Request req = new Request(newp, 4);

			objectOutputStream.writeObject(req);
			System.out.println(req);
			System.out.println("Connexion reussite 1!");
			InputStream is = socket.getInputStream();
			System.out.println(is);
			ObjectInputStream objectInputStream = new ObjectInputStream(is);
			System.out.println("Connexion reussite !");
			p = (Patient) objectInputStream.readObject();
			try {
				is.close();
				objectOutputStream.close();
				socket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		} catch (IOException e) {

			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		System.out.println(p);

	}

	public void Envo_diag(Diagnostic d) {
		System.out.println("envoi diagnostique ...");
		try (Socket socket = new Socket(server, 234)) {

			// pour l'envoie
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			InputStream is = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(is);

			Request req = new Request(d, 8);

			System.out.println(req);
			objectOutputStream.writeObject(req);

			d = (Diagnostic) objectInputStream.readObject();
			System.out.println("Connexion reussite !8");

			System.out.println("\n\n apres \n\n" + d);
			try {
				is.close();
				objectOutputStream.close();
				socket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		} catch (IOException e) {

			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		System.out.println(p);

	}

	@SuppressWarnings("unchecked")
	public List<Diagnostic> retreiveDiagnostiques(String identifier) throws IOException {
		List<Diagnostic> diagnostic = null;
		System.out.println("Demande des diagnostiques");
		try (Socket socket = new Socket("localhost", 234)) {
			System.out.println("Connexion reussite !");
			// pour l'envoie
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			// pour la reception
			InputStream is = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(is);
			// requete a envoyer
			Request req = new Request(p, 2);
			// envoi de la requete
			objectOutputStream.writeObject(req);
			// reception du resultat
			diagnostic = (List<Diagnostic>) objectInputStream.readObject();
			try {
				is.close();
				objectOutputStream.close();
				socket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(diagnostic);
		System.out.println("Connexion reussite 3!");
		return diagnostic;
	}

	public Diagnostic AffichageDiagnostique(ObjectId idDiag) throws IOException {
		Diagnostic diagnostic = null;
		System.out.println("envoi diagnostique ...");
		try (Socket socket = new Socket("localhost", 234)) {
			// pour la recuperation

			// pour l'envoie
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			Request req = new Request(idDiag, 3);
			objectOutputStream.writeObject(req);
			System.out.println(req);
			System.out.println("Connexion reussite 1!");
			InputStream is = socket.getInputStream();
			System.out.println(is);
			ObjectInputStream objectInputStream = new ObjectInputStream(is);
			System.out.println("Connexion reussite !");
			diagnostic = (Diagnostic) objectInputStream.readObject();
			try {
				is.close();
				objectOutputStream.close();
				socket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(diagnostic);
		System.out.println("Connexion reussite 3!");
		return diagnostic;
	}

//	public Ville StringToVille(String stringVille) {
//		Ville ville;
//		return ville;
//	}

	public void ctrInscrip(String nom, String prenom, String identifiant, Date dateNaissance, String adresse,
			String ville) {

		System.out.println(nom + " " + prenom + " " + identifiant);
		// Patient p = new Patient(nom, prenom, identifiant, dateNaissance, adresse,
		// ville);
	}

	public void envoyerDiagnostique(Diagnostic dia) throws IOException {
		Diagnostic p = new Diagnostic();
		System.out.println("envoi diagnostique ...");
		try (Socket socket = new Socket("localhost", 234)) {
			// pour la recuperation

			// pour l'envoie
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			Request req = new Request(dia, 8);

			objectOutputStream.writeObject(req);
			System.out.println(req);
			System.out.println("Connexion reussite 1!");
			InputStream is = socket.getInputStream();
			System.out.println(is);
			ObjectInputStream objectInputStream = new ObjectInputStream(is);
			System.out.println("Connexion reussite !");
			p = (Diagnostic) objectInputStream.readObject();
			try {
				is.close();
				objectOutputStream.close();
				socket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Patient patient=new Patient(new ObjectId("623740f074ef0d449bbe60ec"),"","", null, null, null, null);
		//patient.set_id(new ObjectId("623740f074ef0d449bbe60ec"));
		Diagnostic dia =new Diagnostic(patient, new Date());
		System.out.println(dia.getPatient().get_id());
		dia.setPatient(patient);
		dia.setTemperature(39);
		dia.getPatient().setAge(75);
		dia.setDate_diagnostic(new Date(0));
		new Controller().Envo_diag(dia);

	}

}
