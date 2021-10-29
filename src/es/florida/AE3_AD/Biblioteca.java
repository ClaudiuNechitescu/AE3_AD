package es.florida.AE3_AD;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
				ArrayList<Llibre> llibres = recuperarTots();
				for (Llibre li : llibres) {
					System.out.println(li.getTitol());
				}
				break;
			}
			case "2": {
				ArrayList<Llibre> llibres = recuperarTots();
				String missatge = "";
				for (int i = 0; i < llibres.size(); i++) {
					missatge += "\n" + (i + 1) + ". " + llibres.get(i).getTitol();
				}
				System.out.println("De què llibre vols ver l'informació?" + missatge);
				int numero = Integer.parseInt(br.readLine());
				// Jo haria directament mostrarLlibre(llibres.get(numero.1)), pero bé, hi ha que
				// utilitzar recuperarLlibre
				mostrarLlibre(recuperarLlibre(llibres.get(numero - 1).getId()));
				break;
			}
			case "3": {

				System.out.println("Introdueix la id del llibre: ");
				int id = Integer.parseInt(br.readLine());
				System.out.println("Introdueix el titol: ");
				String titol = br.readLine();
				System.out.println("Introdueix el autor: ");
				String autor = br.readLine();
				System.out.println("Introdueix l'any de publicació: ");
				int any = Integer.parseInt(br.readLine());
				System.out.println("Introdueix la editorial: ");
				String edi = br.readLine();
				System.out.println("Introdueix el nom de pàgines: ");
				int pag = Integer.parseInt(br.readLine());
				Llibre llibre = new Llibre(id, titol, autor, any, edi, pag);
				crearLlibre(llibre);
				break;
			}
			case "4": {
				ArrayList<Llibre> llibres = recuperarTots();
				String missatge = "";
				for (int i = 0; i < llibres.size(); i++) {
					missatge += "\n" + (i + 1) + ". " + llibres.get(i).getTitol();
				}
				System.out.println("A què llibre vols actualitzar l'informació?" + missatge);
				int numero = Integer.parseInt(br.readLine());
				actualitzaLlibre(llibres.get(numero - 1).getId());
				break;
			}
			case "5": {
				System.out.println("Introdueix l'identificador del llibre a borrar: ");
				borrarLlibre(Integer.parseInt(br.readLine()));
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
		ArrayList<Llibre> llibres = recuperarTots();
		llibres.add(llibre);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element raiz = doc.createElement("llibres");
		doc.appendChild(raiz);
		for(Llibre li:llibres) {
			Element libro = doc.createElement("llibre");
			String id = String.valueOf(llibre.getId());
			libro.setAttribute("id", id);
			raiz.appendChild(libro);
			Element titol = doc.createElement("titol");
			titol.appendChild(doc.createTextNode(String.valueOf(li.getTitol())));
			libro.appendChild(titol);
			Element autor = doc.createElement("autor");
			autor.appendChild(doc.createTextNode(String.valueOf(li.getAutor())));
			libro.appendChild(autor);
			Element any = doc.createElement("any");
			any.appendChild(doc.createTextNode(String.valueOf(li.getAnyPublicacio())));
			libro.appendChild(any);
			Element editorial = doc.createElement("editorial");
			editorial.appendChild(doc.createTextNode(String.valueOf(li.getEditorial())));
			libro.appendChild(editorial);
			Element pagines = doc.createElement("numpagines");
			pagines.appendChild(doc.createTextNode(String.valueOf(li.getNombrePagines())));
			libro.appendChild(pagines);

		}
		

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

	public static Llibre recuperarLlibre(int identificador) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(new File("Biblioteca.xml"));
		Llibre llibre = new Llibre();

		Element raiz = document.getDocumentElement();
		NodeList nodeList = document.getElementsByTagName("llibre");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			Element eElement = (Element) node;
			if (Integer.parseInt(eElement.getAttribute("id")) == identificador) {
				llibre.setId(Integer.parseInt(eElement.getAttribute("id")));
				llibre.setTitol(eElement.getElementsByTagName("titol").item(0).getTextContent());
				llibre.setAutor(eElement.getElementsByTagName("autor").item(0).getTextContent());
				llibre.setAnyPublicacio(
						Integer.parseInt(eElement.getElementsByTagName("any").item(0).getTextContent()));
				llibre.setEditorial(eElement.getElementsByTagName("editorial").item(0).getTextContent());
				llibre.setNombrePagines(
						Integer.parseInt(eElement.getElementsByTagName("numpagines").item(0).getTextContent()));
			}
		}
		return llibre;

	}

	public static void mostrarLlibre(Llibre llibre) {
		System.out.println("Identificador: " + llibre.getId());
		System.out.println("Títol: " + llibre.getTitol());
		System.out.println("Autor: " + llibre.getAutor());
		System.out.println("Any de publicació: " + llibre.getAnyPublicacio());
		System.out.println("Editorial: " + llibre.getEditorial());
		System.out.println("Nombre de pàgines: " + llibre.getNombrePagines());
	}

	public static void borrarLlibre(int identificador) throws Exception {
		ArrayList<Llibre> llibres = recuperarTots();
		for (int i = 0; i < llibres.size(); i++) {
			if (llibres.get(i).getId() == identificador) {
				llibres.remove(i);
			} 
				if(!llibres.isEmpty()) {
					crearLlibre(llibres.get(i));
				}
				else {
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = db.newDocument();
					Element raiz = doc.createElement("llibres");
					doc.appendChild(raiz);
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

				}
			
		}

	}

	public static void actualitzaLlibre(int identificador) throws Exception {
		ArrayList<Llibre> llibres = recuperarTots();
		for (Llibre li : llibres) {
			if (li.getId() == identificador) {
				InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(isr);
				String resposta;
				
					System.out.println("Què vols canviar?" + "\n1. Títol" + "\n2. Autor" + "\n3. Any de publicació"
							+ "\n4. Editorial" + "\n5. Nombre de pàgines" + "\n6. Res");
					resposta = br.readLine();
					switch (resposta) {
					case "1": {
						System.out.println("Introdueix el nou titol: ");
						String nou = br.readLine();
						li.setTitol(nou);
						break;
					}
					case "2": {
						System.out.println("Introdueix el nou autor: ");
						String nou = br.readLine();
						li.setAutor(nou);
						break;

					}
					case "3": {
						System.out.println("Introdueix el nou any: ");
						int nou = Integer.parseInt(br.readLine());
						li.setAnyPublicacio(nou);
						break;

					}
					case "4": {
						System.out.println("Introdueix la nou editorial: ");
						String nou = br.readLine();
						li.setEditorial(nou);
						break;
					}
					case "5": {
						System.out.println("Introdueix el nou nom de pàgines: ");
						int nou = Integer.parseInt(br.readLine());
						li.setNombrePagines(nou);
						break;

					}
					case "6": {
						System.exit(1);
						break;
					}
					default: {
						System.out.println("Has d'introduir un numero de 1 a 6");
					}
					}
					
				
			}
			borrarLlibre(li.getId());
			crearLlibre(li);
		}

	}

	public static ArrayList<Llibre> recuperarTots() throws Exception {
		ArrayList<Llibre> llibres = new ArrayList<Llibre>();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(new File("Biblioteca.xml"));
		Llibre llibre = new Llibre();

		Element raiz = document.getDocumentElement();
		NodeList nodeList = document.getElementsByTagName("llibre");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
				Element eElement = (Element) node;
				int id = (Integer.parseInt(eElement.getAttribute("id")));
				String titol = (eElement.getElementsByTagName("titol").item(0).getTextContent());
				String autor = (eElement.getElementsByTagName("autor").item(0).getTextContent());
				int anypublicacio = (Integer.parseInt(eElement.getElementsByTagName("any").item(0).getTextContent()));
				String editorial = (eElement.getElementsByTagName("editorial").item(0).getTextContent());
				int nompagines = (Integer
						.parseInt(eElement.getElementsByTagName("numpagines").item(0).getTextContent()));
				llibres.add(new Llibre(id, titol, autor, anypublicacio, editorial, nompagines));
			
		}

		return llibres;
	}

}
