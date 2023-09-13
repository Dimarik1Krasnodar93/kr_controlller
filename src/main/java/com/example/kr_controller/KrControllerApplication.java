package com.example.kr_controller;

import io.github.cdimascio.dotenv.Dotenv;
import liquibase.integration.commandline.Main;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.InputStreamSource;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
//@EnableConfigurationProperties(JwtProperties.class)
public class KrControllerApplication extends SpringBootServletInitializer {

	public static String POSTGRES_USER;
	public static String POSTGRES_PASSWORD;
	public static String POSTGRES_DB;
	public static String POSTGRES_PATH;



	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(KrControllerApplication.class);
	}

	public static void main(String[] args) {
		loadFromEnv();
		changeHibernateCfgXml();
		changeApplicationProperties();
		SpringApplication.run(KrControllerApplication.class, args);
	}

	private static void loadFromEnv() {
		Dotenv dotenv = Dotenv.load();
		POSTGRES_USER = dotenv.get("POSTGRES_USER");
		POSTGRES_PASSWORD = dotenv.get("POSTGRES_PASSWORD");
		POSTGRES_DB = dotenv.get("POSTGRES_DB");
		POSTGRES_PATH = dotenv.get("POSTGRES_PATH");
	}

	private static void changeHibernateCfgXml() {
		try {
			// Load the Hibernate configuration XML file
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File("src/main/resources/hibernate.cfg.xml"));

			NodeList properties = doc.getElementsByTagName("property");
			for (int i = 0; i < properties.getLength(); i++) {
				Element property = (Element) properties.item(i);
				String propertyName = property.getAttribute("name");
				if ("hibernate.connection.username".equals(propertyName)) {
					property.setTextContent(POSTGRES_USER);
				}
				if ("hibernate.connection.password".equals(propertyName)) {
					property.setTextContent(POSTGRES_PASSWORD);
				}
				if ("hibernate.connection.url".equals(propertyName)) {
					property.setTextContent(POSTGRES_PATH);
				}
			}

			// Save the updated document back to the file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("src/main/resources/hibernate.cfg.xml"));
			transformer.transform(source, result);

			System.out.println("Updated hibernate.connection.username property to '123'.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void changeApplicationProperties() {
		Properties properties = new Properties();
		try (InputStream inputStream = new FileInputStream("src/main/resources/db.properties")
		) {
			properties.load(inputStream);
			properties.setProperty("url", POSTGRES_PATH);
			properties.setProperty("username", POSTGRES_USER);
			properties.setProperty("password", POSTGRES_PASSWORD);
			try (Writer writer = new FileWriter("src/main/resources/db.properties")){
				for (String key : properties.stringPropertyNames()) {
					String value = properties.getProperty(key);
					writer.write(key + "=" + value + "\n");
				}
			}


		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private static void changeAttribute(Document document, String attributeName, String value) {
		NodeList nodes = document.getElementsByTagName("property");
		Element element = (Element) nodes.item(0);
		element.setAttribute(attributeName, value);
	}
}
