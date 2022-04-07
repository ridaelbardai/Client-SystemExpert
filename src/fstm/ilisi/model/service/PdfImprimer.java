package fstm.ilisi.model.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.bson.types.ObjectId;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import fstm.ilisi.controller.Controller;
import ma.fstm.ilisi.projet.model.bo.Diagnostic;
import ma.fstm.ilisi.projet.model.bo.Symptom;

class MyFooter extends PdfPageEventHelper {
    Font ffont = new Font(Font.FontFamily.UNDEFINED, 10, Font.BOLDITALIC,BaseColor.LIGHT_GRAY);
 
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        Phrase header = new Phrase("ILISI2 2020/2023", ffont);
        Phrase footer = new Phrase("Arkaman", ffont);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                header, 20+document.left(),
                document.top() + 10, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footer,
                document.right()-50,
                document.bottom() - 10, 0);
    }
}
public class PdfImprimer {

	String text;
	Diagnostic diagnostic;
	public PdfImprimer(String t , ObjectId dia) throws IOException {
		this.text=t;
		this.diagnostic= new Controller().AffichageDiagnostique(dia);
		System.out.println(this.diagnostic);
	}
	public void makePdf() {
		Document doc = new Document(); 
		try  
		{  
		//generate a PDF at the specified location  
		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(this.text));  
		PdfDocument pdf = new PdfDocument();
		pdf.addWriter(writer);
		doc.setPageCount(1);
		System.out.println("PDF created.");  
		//opens the PDF  
		doc.open();  
		//adds paragraph to the PDF file  
		Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 22, BaseColor.BLUE);
		Font font1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
		Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.MAGENTA);
		Chunk chunk,chunk2,chunk3,chunk4;
		chunk = new Chunk(
				        "this is a pdf of your diagnostiqye { "+diagnostic.getPatient().getNom()+"  "+diagnostic.getPatient().getPrenom()+" }\n", font);
		chunk.setTextRise(7);
		//doc.add(chunk); 
		chunk2=new Chunk("				Les symptomes sont : "+diagnostic.getSymptomes()+"\n\n",font1);
		chunk2.setTextRise(7);
		//doc.add(chunk2);
		chunk3=new Chunk("				La possibilité de présence de Covid 19 est  : "+diagnostic.getPossi_presence()+"\n\n",font1);
		chunk3.setTextRise(7);
		//doc.add(chunk3);
		chunk4 = new Chunk( "merci pour votre confiance et prenez soin de vous \n",font1);
		chunk4.setTextRise(7);
		//doc.add(chunk4);
		Chunk chunk5 = new Chunk("\nVotre santé est notre  interet  LES INNOVATEURS D'ILISI 20/23", font2);
		chunk5.setTextRise(7);
		//doc.add(chunk5);
		 Paragraph first = new Paragraph();
	        first.add(chunk);
	        first.add(chunk2);
	        first.add(chunk3);
	        first.add(chunk4);
	        first.add(chunk5);
	     doc.add(first);
	     

		//close the PDF file  
		doc.close();  
		//closes the writer  
		writer.close();  
		}   
		catch (DocumentException e)  
		{  
		e.printStackTrace();  
		}   
		catch (FileNotFoundException e)  
		{  
		e.printStackTrace();  
		}  
	}
	public  void makePdf1()  
	{  
	//created PDF document instance   
	Document doc = new Document();
	try  
	{  
	//generate a PDF at the specified location  
	PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(this.text));  
	writer.setPageEvent(new MyFooter());
	System.out.println("PDF de diagnostique a ete cree .");  
	//opens the PDF  
	doc.open();  
	PdfPTable table = new PdfPTable(5); // 3 columns.
	
    Image image = Image.getInstance("images\\fst.jpeg");
    Image image1 = Image.getInstance("images\\ilisi.jpeg");
    PdfPCell cell1 = new PdfPCell(new Paragraph(""));
    PdfPCell cell2 = new PdfPCell(image, true);

  
    PdfPCell cell4 = new PdfPCell(image1, true);
    cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell4.setVerticalAlignment(Element.ALIGN_CENTER);
    
    PdfPCell cell3 = new PdfPCell(new Paragraph(""));
    cell1.setBorder(Rectangle.NO_BORDER);
    //cell2.setBorder(Rectangle.NO_BORDER);
    cell3.setBorder(Rectangle.NO_BORDER);
    cell4.setBorder(Rectangle.NO_BORDER);
    
    table.setHeaderRows(0);
    table.addCell(cell2);
    table.addCell(cell1);
    table.addCell(cell1);
    table.addCell(cell3);
    table.addCell(cell4);
    doc.add(table);
    Paragraph separateur = new Paragraph("\n\n");
    doc.add(separateur);
	/************************/
    table = new PdfPTable(3);
    table.setWidths((new float[] { 5,8,5}));
    Image image3 = Image.getInstance("images\\covid.jpeg");
    PdfPCell cell = new PdfPCell(image3, true);
    cell.enableBorderSide(3);
    table.addCell(cell3);
    table.addCell(cell);
    table.addCell(cell3);
    doc.add(table);
    separateur.add("\n\n\n");
    doc.add(separateur);
    /************************/
    table= new PdfPTable(2);
    table.setWidths((new float[] { 5,10}));
    Paragraph text = new Paragraph("Nom ");
    cell = new PdfPCell(text);cell.setPadding(15);
    table.addCell(cell);
    cell = new PdfPCell(new Paragraph(this.diagnostic.getPatient().getNom()));
    table.addCell(cell);
    text = new Paragraph("Prenom ");
    cell = new PdfPCell(text);cell.setPadding(15);
    table.addCell(cell);
    cell = new PdfPCell(new Paragraph(this.diagnostic.getPatient().getPrenom()));
    table.addCell(cell);
    text = new Paragraph("Symptomes ");
    cell = new PdfPCell(text); cell.setPadding(15);
    table.addCell(cell);
    String str="";
    for(Symptom s :this.diagnostic.getSymptomes()) {
    	str+=s.getSymName()+"  ";
    }
    Paragraph pr= new  Paragraph(str);
    cell = new PdfPCell(pr); 
    table.addCell(cell);
    text = new Paragraph("Possibilité de présence du covid19 ");
    cell = new PdfPCell(text); cell.setPadding(10);
    table.addCell(cell);
    cell = new PdfPCell(new Paragraph(this.diagnostic.getPossi_presence()*100+" %")); 
    table.addCell(cell);
    text = new Paragraph("Date et heure de diagnostique ");
    cell = new PdfPCell(text); cell.setPadding(10);
    table.addCell(cell);
    cell = new PdfPCell(new Paragraph(new CurrentDateTimeExample1().doit())); 
    table.addCell(cell);
    /************************/
    //footer
    
    doc.add(table);
    
    doc.close();  
	//closes the writer  
	writer.close();  
	}   
	catch (DocumentException e)  
	{  
	e.printStackTrace();  
	}   
	catch (FileNotFoundException e)  
	{  
	e.printStackTrace();  
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
	}  
	

}