package es.florida.AE3_AD;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		do {
			System.out.println("Què vols fer?" + "\n1. Mostrat tots el títols de la biblioteca"
					+ "\n2. Mostrar informació detallada d'un llibre" + "\n3. Crear nou llibre"
					+ "\n4. Actualitzar llibre" + "\n5. Borrar llibre" + "\n6. Tanca la biblioteca");
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			String resposta = br.readLine();
			Scanner continuar = new Scanner(System.in);
			switch (resposta) {
			case "1": {
				Llibre llibre=new Llibre(1234,"Harry Potter","J.K Rowling",1970,"A",254);
				crearLlibre(llibre);
				break;
			}
			case "2": {

				break;
			}
			case "3": {

				break;
			}
			case "4": {

				break;
			}
			case "5": {

				break;
			}
			case "6": {

				System.exit(1);
			}
			default: {
			}
				System.out.println("Has de triar una opció de 1 a 6");
				break;
			}
			System.out.println("<Pressiona ENTER per a continuar>");
			continuar.nextLine();
		} while (true);
	}

	public static int crearLlibre(Llibre llibre) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element raiz = doc.createElement("llibres");
		doc.appendChild(raiz);

		Element libro = doc.createElement("llibre");
		String id = String.valueOf(llibre.getId());
		libro.setAttribute("id", id);
		raiz.appendChild(libro);
		Element titol = doc.createElement("titol");
		titol.appendChild(doc.createTextNode(String.valueOf(llibre.getTitol())));
		libro.appendChild(titol);
		Element autor = doc.createElement("autor");
		autor.appendChild(doc.createTextNode(String.valueOf(llibre.getAutor())));
		libro.appendChild(autor);
		Element any = doc.createElement("any");
		any.appendChild(doc.createTextNode(String.valueOf(llibre.getAnyPublicacio())));
		libro.appendChild(any);
		Element editorial = doc.createElement("editorial");
		editorial.appendChild(doc.createTextNode(String.valueOf(llibre.getEditorial())));
		libro.appendChild(editorial);
		Element pagines = doc.createElement("editorial");
		pagines.appendChild(doc.createTextNode(String.valueOf(llibre.getNombrePagines())));
		libro.appendChild(pagines);
		
		TransformerFactory tranFactory = TransformerFactory.newInstance();
		Transformer aTransformer = tranFactory.newTransformer();
		aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);

		FileWriter fw = new FileWriter("Biblioteca.xml");
		StreamResult result = new StreamResult(fw);
		aTransformer.transform(source, result);
		fw.close();

		return llibre.getId();
	}

	public Llibre recuperarLlibre(int identificador) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(new File("Biblioteca.xml"));
		Llibre llibre = new Llibre();

		Element raiz = document.getDocumentElement();
		System.out.println("Contenido XML "+raiz.getNodeName()+":");
		NodeList nodeList = document.getElementsByTagName("llibre");
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			Element eElement = (Element) node;
			if (Integer.parseInt(eElement.getAttribute("id")) == identificador) {
			llibre.setId(Integer.parseInt(eElement.getAttribute("id")));
			llibre.setTitol(eElement.getElementsByTagName("titol").item(0).getTextContent());
			llibre.setAutor(eElement.getElementsByTagName("autor").item(0).getTextContent());
			llibre.setAnyPublicacio(Integer.parseInt(eElement.getElementsByTagName("any").item(0).getTextContent()));
			llibre.setEditorial(eElement.getElementsByTagName("editorial").item(0).getTextContent());
			llibre.setNombrePagines(Integer.parseInt(eElement.getElementsByTagName("formato").item(0).getTextContent()));
			}
		}
		return llibre;

	}

	public void mostrarLlibre(Llibre llibre) {
		System.out.println("Identificador: " + llibre.getId());
		System.out.println("Títol: " + llibre.getTitol());
		System.out.println("Autor: " + llibre.getAutor());
		System.out.println("Any de publicació: " + llibre.getAnyPublicacio());
		System.out.println("Editorial: " + llibre.getEditorial());
		System.out.println("Nombre de pàgines: " + llibre.getNombrePagines());
	}

	public void borrarLlibre(int identificador) {
		
	}

	public void actualitzaLlibre(int identificador) {

	}

	public ArrayList<Llibre> recuperarTots() {
		ArrayList<Llibre> llibres = new ArrayList<Llibre>();
		return llibres;
	}

}
