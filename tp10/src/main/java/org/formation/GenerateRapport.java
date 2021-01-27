/**
 * 
 */
package org.formation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * @author David
 *
 */
public class GenerateRapport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class.forName("org.hsqldb.jdbcDriver"); // Or any other driver
		} catch (Exception x) {
			System.out.println("Unable to load the driver class!");
		}

		//
		if (args.length == 0) {
			System.out.println("Usage : run <jrxml> [<country_parameter> <locale>]");
			System.exit(0);
		}
		String file = args[0];
		String country = "France";
		String language = "FR";
		if (args.length > 1) {
			country = args[1];
		}
		if (args.length > 2) {
			language = args[2];
		}

		try {
			java.io.InputStream inputStream = GenerateRapport.class.getResourceAsStream("/"+file);
			
			JasperDesign design = JRXmlLoader.load(inputStream);
			JasperReport report = JasperCompileManager.compileReport(design);

			Connection connection = getConnection();
			Map<String, Object> parametersMap = new HashMap<String, Object>();
			parametersMap.put("SHIPCOUNTRY", country);
			parametersMap.put("REPORT_LOCALE",new Locale(language));

			JasperPrint print = JasperFillManager.fillReport(report, parametersMap, connection);

			JasperExportManager.exportReportToPdfFile(print, print.getName() + ".pdf");

			JRPptxExporter exporter = new JRPptxExporter();

			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(print.getName() + ".pptx"));

			exporter.exportReport();

		} catch (Exception e) {
			e.printStackTrace();
		}
		// A compl�ter //
		/////////////////

	}

	private static Connection getConnection() {
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "sa", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

}
