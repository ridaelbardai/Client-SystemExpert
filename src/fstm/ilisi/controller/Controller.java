package fstm.ilisi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import fstm.ilisi.model.Diagnostique;
import fstm.ilisi.model.Request;

public class Controller {

	public void envoyerDiagnostique(Diagnostique dia) throws IOException {
		System.out.println("Envoi diagnostique ...");
		try (Socket socket = new Socket("localhost", 234)) {
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

	public void envoyer2(Diagnostique dia) throws IOException {
		System.out.println("envoi diagnostique ...");
		try (Socket socket = new Socket("localhost", 234)) {
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
	
	
}
