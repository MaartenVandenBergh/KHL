package db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import exceptions.DbException;

import util.Kernel;
import util.DistinctKernel;

public class DbKernel extends Db{

	private List<DistinctKernel> kernels;
	
//	Constructor
	public DbKernel(){
		this.kernels = new ArrayList<DistinctKernel>();
	}

//Util
	public void addKernel(Kernel kernel, DistinctionType type)throws DbException{
		if(kernels.contains(kernel)){
			throw new DbException("Kernel known");
		}
		else{
			DistinctKernel dk = new DistinctKernel(kernel, type);
			this.kernels.add(dk);
		}
	}
	public void removeKernel(Kernel kernel)throws DbException{
		DistinctKernel dk = new DistinctKernel(kernel, DistinctionType.CUSTOM);
		if(!kernels.contains(dk)){
			throw new DbException("Kernel unknown");
		}
		else{
			//if(kernels.get(kernels.indexOf(dk)).getDistinctionType().equals(DistinctionType.CUSTOM)){
				this.kernels.remove(dk);
			/*}
			else{
				throw new DbException("Can't remove standard kernels");
			}*/
		}
		
	}
	public void changeTypeKernel(Kernel kernel, DistinctionType type)throws DbException{
		if(!kernels.contains(kernel)){
			throw new DbException("Kernel unknown");
		}
		else{
			int index = this.kernels.indexOf(kernel);
			this.kernels.get(index).setDistinctionType(type);
		}	
	}
	public void save(String path){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element root = doc.createElement("root");
			doc.appendChild(root);
			
			for(DistinctKernel dk : this.kernels){
				//DistinctKernel Element
				Element distinctKernel = doc.createElement("DistinctKernel");
				root.appendChild(distinctKernel);
				
				//DistinctionType Element
				Element distinctionType = doc.createElement("DistinctionType");
				String type = dk.getDistinctionType().toString();
				distinctionType.appendChild(doc.createTextNode(type));
				distinctKernel.appendChild(distinctionType);
				
				//Name Element
				Element name = doc.createElement("Name");
				name.appendChild(doc.createTextNode(dk.getKernel().getName()));
				distinctKernel.appendChild(name);
				
				//Kernel Element
				Element kernel = doc.createElement("Kernel");
				distinctKernel.appendChild(kernel);
				
				double[][] kernelElements = dk.getKernel().getElements();
				
				for(int r=0;r<kernelElements.length;r++){
					Element row = doc.createElement("row");
					kernel.appendChild(row);
					
					for(int c=0;c<kernelElements[0].length;c++){
						Element column = doc.createElement("column");
						String sElement = Double.toString(kernelElements[r][c]);
						column.appendChild(doc.createTextNode(sElement));
						row.appendChild(column);
					}
				}
				
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);
			
		}
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
	public void load(String path){
		try{
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			this.kernels.clear();
			
			NodeList listOfDistinctKernels = doc.getElementsByTagName("DistinctKernel");
			for(int d=0;d<listOfDistinctKernels.getLength();d++){
				Node distinctKernelNode = listOfDistinctKernels.item(d);
				
				if(distinctKernelNode.getNodeType() == Node.ELEMENT_NODE){
					DistinctionType type = null;
					String name = null;
					double[][] kernelElements= null;
					
					Element distinctKernelElement = (Element)distinctKernelNode;
					
					//DistinctionType
					NodeList listOfDistinctionTypes = distinctKernelElement.getElementsByTagName("DistinctionType");
					Node distinctionTypeNode = listOfDistinctionTypes.item(0);
					
					if(distinctionTypeNode.getNodeType() == Node.ELEMENT_NODE){
						String sType = distinctionTypeNode.getChildNodes().item(0).getTextContent();
						if(sType.equals(DistinctionType.STANDARD.toString())){
							type = DistinctionType.STANDARD;
						}
						if(sType.equals(DistinctionType.CUSTOM.toString())){
							type = DistinctionType.CUSTOM;
						}
						if(type == null){
							throw new DbException("Unknown DiscriptionType");
						}
					}
					
					//Name
					NodeList listOfNames = distinctKernelElement.getElementsByTagName("Name");
					Node nameNode = listOfNames.item(0);
					
					if(nameNode.getNodeType() == Node.ELEMENT_NODE){
						name = nameNode.getChildNodes().item(0).getTextContent();
					}
					
					//KernelElements
					NodeList listOfKernels = distinctKernelElement.getElementsByTagName("Kernel");
					Node kernelNode = listOfKernels.item(0);
					
					if(kernelNode.getNodeType() == Node.ELEMENT_NODE){
						Element kernelElement = (Element)kernelNode;
						NodeList rowList = kernelElement.getElementsByTagName("row");
						
						for(int r = 0; r<rowList.getLength();r++){
							Node rowNode = rowList.item(r);
							
							if(rowNode.getNodeType() == Node.ELEMENT_NODE){
								Element rowElement = (Element)rowNode;
	                    		NodeList columnList = rowElement.getElementsByTagName("column");
	                    		
	                    		for(int c=0; c<columnList.getLength() ; c++){
	                    			Node columnNode = columnList.item(c);
	                    			
			                    	if(columnNode.getNodeType() == Node.ELEMENT_NODE){
			                    		
			                    		if(kernelElements == null){
			                    			kernelElements = new double[rowList.getLength()][columnList.getLength()];
			                    			
			                    		}
			                    		kernelElements[r][c] = Double.parseDouble(columnNode.getChildNodes().item(0).getTextContent());
			                    	}
	                    		}
								
							}
						}
					}
					//Create DistinctKernel
					Kernel kernel = new Kernel(kernelElements);
					kernel.setName(name);
					DistinctKernel dKernel = new DistinctKernel(kernel,type);
					
					//Add DistinctKernel to Db
					this.kernels.add(dKernel);
				}
				
			}
			
		}
		catch (SAXParseException err) {
	        System.out.println ("** Parsing error" + ", line " 
	             + err.getLineNumber () + ", uri " + err.getSystemId ());
	        System.out.println(" " + err.getMessage ());
        }
        catch (SAXException e) {
	        Exception x = e.getException ();
	        ((x == null) ? e : x).printStackTrace ();

        }
        catch (Throwable t) {
        	t.printStackTrace ();
        }
	}
	
//	DistinctionType Dependend
	public List<Kernel> getAllStandardKernels(){
		List<Kernel> output = new ArrayList<Kernel>();
		for(DistinctKernel dk: kernels){
			if(dk.getDistinctionType().equals(DistinctionType.STANDARD)){
				output.add(dk.getKernel());
			}
		}
		return output;
	}
	public List<Kernel> getAllCustomKernels(){
		List<Kernel> output = new ArrayList<Kernel>();
		for(DistinctKernel dk: kernels){
			if(dk.getDistinctionType().equals(DistinctionType.CUSTOM)){
				output.add(dk.getKernel());
			}
		}
		return output;
	}

	public List<Kernel> getAllKernels() {
		List<Kernel> kernels = new ArrayList<Kernel>();
		
		kernels.addAll(this.getAllStandardKernels());
		kernels.addAll(this.getAllCustomKernels());
		
		return kernels;
		
	}
	
}
