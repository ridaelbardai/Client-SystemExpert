package fstm.ilisi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import ma.fstm.ilisi.projet.model.bo.Diagnostic;
import ma.fstm.ilisi.projet.model.bo.Patient;
import ma.fstm.ilisi.projet.model.bo.Region;
import ma.fstm.ilisi.projet.model.bo.Symptom;
import ma.fstm.ilisi.projet.model.bo.Ville;
import ma.fstm.ilisi.projet.model.service.Request;

public class Controller {

	public void envoyerDiagnostique(Diagnostic dia) throws IOException {
		System.out.println("Envoi diagnostique ...");
		try (Socket socket = new Socket("172.17.36.144", 234)) {
			System.out.println("Connexion reussite !");

			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			Request req = new Request(dia, 1);

			objectOutputStream.writeObject(req);

			try {
				is.close();
				objectOutputStream.close();
				socket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		}
	}

	public void envoyer2(Diagnostic dia) throws IOException {
		System.out.println("envoi diagnostique ...");
		try (Socket socket = new Socket("172.17.36.144", 234)) {
			System.out.println("Connexion reussite !");

			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			Request req = new Request(dia, 2);
			objectOutputStream.writeObject(req);
			try {
				is.close();
				objectOutputStream.close();
				socket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		}

	}
	
	public String[] retreiveSymptoms() throws IOException {
		List<Symptom> sympt = null;
		System.out.println("Demande liste des symptomes");
		try (Socket socket = new Socket("172.17.36.144", 234)) {
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
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		System.out.println(sympt);
		String[] sy = new String[sympt.size()];
		for (int i=0; i < sympt.size(); i++) {
			sy[i]=sympt.get(i).getSymName();
		}
		return sy;
	}
	
	public String[] retreiveRegions()
	{
		List<Region> regions = new ArrayList<Region>();
		try (Socket socket = new Socket("172.17.36.144", 234)) {
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
			regions = (List<Region>)objectInputStream.readObject();
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
		System.out.println(regions);
		String[] sy = new String[regions.size()];
		for (int i=0; i < regions.size(); i++) {
			sy[i]=regions.get(i).getRegionName();
		}
		return sy;
	}
	

	
	
	public String[] VilleParreg(String re)
	{
		List<Ville> villes= new ArrayList<Ville>();
		try (Socket socket = new Socket("172.17.36.144", 234)) {
			// pour la recuperation
			

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
			villes = (List<Ville>)objectInputStream.readObject();
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
		System.out.println(villes);
		String[] sy = new String[villes.size()];
		for (int i=0; i < villes.size(); i++) {
			sy[i]=villes.get(i).getVilleName();
		}
		return sy;
	}
	
	public Patient VerifPat(String CNE)
	{ 
		Patient p = new Patient() ;
		System.out.println("envoi diagnostique ...");
		try (Socket socket = new Socket("172.17.36.144", 234)) {
			// pour la recuperation
			

			// pour l'envoie
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			Request req = new Request(CNE, 1);

			objectOutputStream.writeObject(req);
			System.out.println(req);
			System.out.println("Connexion reussite 1!");
			InputStream is = socket.getInputStream();
			System.out.println(is);
			ObjectInputStream objectInputStream = new ObjectInputStream(is);
			System.out.println("Connexion reussite !");
			p = (Patient)objectInputStream.readObject();
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
		System.out.println(p);
		return p;
	}
	
	public void Inscription(Patient newp)
	{ 
		Patient p = new Patient() ;
		System.out.println("envoi diagnostique ...");
		try (Socket socket = new Socket("172.17.36.144", 234)) {
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
			p = (Patient)objectInputStream.readObject();
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
		System.out.println(p);
		
	}
	
	public void Envo_diag(Diagnostic d)
	{ 
		Diagnostic p = new Diagnostic() ;
		System.out.println("envoi diagnostique ...");
		try (Socket socket = new Socket("172.17.36.144", 234)) {
			// pour la recuperation
			

			// pour l'envoie
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			Request req = new Request(d, 9);

			objectOutputStream.writeObject(req);
			System.out.println(req);
			System.out.println("Connexion reussite 1!");
			InputStream is = socket.getInputStream();
			System.out.println(is);
			ObjectInputStream objectInputStream = new ObjectInputStream(is);
			System.out.println("Connexion reussite !");
			p = (Diagnostic)objectInputStream.readObject();
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
		System.out.println(p);
		
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
