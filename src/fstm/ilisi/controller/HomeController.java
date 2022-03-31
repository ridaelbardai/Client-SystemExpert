package fstm.ilisi.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import fstm.ilisi.model.service.PdfImprimer;
import fstm.ilisi.model.*;

public class HomeController {
	public HomeController() {

	}

	public void envoyer(Diagnostique dia) throws IOException {
System.out.println("envoi diagnostique ...");
		try (Socket socket = new Socket("localhost",234)) {
			System.out.println("Connexion reussite !");

			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			
			// get the output stream from the socket.
			OutputStream outputStream = socket.getOutputStream();
			// create an object output stream from the output stream so we can send an
			// object through it
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

			System.out.println("Envoie des informations vers Serveur...");
			// 11
			objectOutputStream.writeObject(dia);

			br.readLine();
			Float res = Float.parseFloat(br.readLine());
			dia.setPossi_presence(res);

			System.out.println("\nResultat : [" + res*100 + "% ]");

			int response = JOptionPane.showConfirmDialog(null,
					"Possibilite de presence du covid 19 : " + res * 100 + "% \n voulez vous imprimer la fiche");
			if (response == JOptionPane.NO_OPTION) {
				System.out.println("No button clicked");
			} else if (response == JOptionPane.YES_OPTION) {
				System.out.println("Yes button clicked");
				JFileChooser chooser = new JFileChooser();

				int option = chooser.showSaveDialog(null);

				if (option == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					new PdfImprimer(file.getAbsolutePath() + ".pdf", dia).makePdf1();
				} else
					System.out.println("No file was selected.");
			} else if (response == JOptionPane.CLOSED_OPTION) {
				System.out.println("JOptionPane closed");
			}

		}

	}
}
