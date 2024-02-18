package br.com.efirminov.webframeword.web;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import br.com.efirminov.webframeword.annotations.WebframeworkGetMethod;
import br.com.efirminov.webframeword.annotations.WebframeworkPostMethod;
import br.com.efirminov.webframeword.datastructures.ControllerMap;
import br.com.efirminov.webframeword.datastructures.RequestControllerData;
import br.com.efirminov.webframeword.explorer.ClassExplorer;
import br.com.efirminov.webframeword.util.WebFrameworkLogger;

public class WebFrameworkWebApplication {

	public static void run(Class<?> sourceClass) {
		// desligar todos os logs do apache tomcat
		java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.OFF);
		long ini, fim;

		WebFrameworkLogger.showBanner();

		try {

			// class explorer
			// começar a criar um método de extração de metadados:
			/*
			 * List<String> allClasses = ClassExplorer.retrieveAllCalsses(sourceClass);
			 * allClasses.forEach(p -> { WebFrameworkLogger.log("Class Explorer",
			 * "Class found: " + p); });
			 */
			extractMetadata(sourceClass);

			ini = System.currentTimeMillis();

			WebFrameworkLogger.log("Embeded Web Container", "Iniciando WebframeworkWebApplication");
			Tomcat tomcat = new Tomcat();
			Connector connector = new Connector();
			connector.setPort(8080);
			tomcat.setConnector(connector);
			WebFrameworkLogger.log("Embeded Web Container", "Iniciando na porta 8080");

			// contexto olhando a raiz da aplicação:

			// procurando classes na raiz da app
			Context context = tomcat.addContext("", new File(".").getAbsolutePath());
			Tomcat.addServlet(context, "WebFrameworkDispatcherServlet", new WebFrameworkDispatcherServlet());

			// tudo que digitar na URL vai cair neste ponto:
			context.addServletMappingDecoded("/*", "WebFrameworkDispatcherServlet");

			fim = System.currentTimeMillis();
			WebFrameworkLogger.log("Embeded Web Container", "Tomcat iniciado em " + (double) ((fim - ini)) + "ms");

			// start:
			tomcat.start();
			tomcat.getServer().await();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void extractMetadata(Class<?> sourceClass) {
		try {
			List<String> allClasses = ClassExplorer.retrieveAllCalsses(sourceClass);
			for (String classe : allClasses) {
				// recuperar as anotações da classe
				Annotation annotations[] = Class.forName(classe).getAnnotations();
				for (Annotation classAnnotation : annotations) {
					if (classAnnotation.annotationType().getName()
							.equals("br.com.ehmf.webframework.annotations.WebframeworkController")) {
						WebFrameworkLogger.log("Metadata Explorer", "Found a Controller: " + classe);
						extractMethods(classe);
					}
				}
			}
			for (RequestControllerData item : ControllerMap.values.values()) {
				WebFrameworkLogger.log("", "     " + item.getHttpMethod() + ":" + item.getUrl() + " ["
						+ item.getControllerClass() + "." + item.getControllerMethod() + "]");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void extractMethods(String className) throws Exception {
		String httpMethod = "";
		String path = "";

		// recuperar todos os métodos da classe
		for (Method method : Class.forName(className).getDeclaredMethods()) {
			// WebFrameworkLogger.log(" - ", method.getName());
			for (Annotation annotation : method.getAnnotations()) {
				if (annotation.annotationType().getName()
						.equals("br.com.ehmf.webframework.annotations.WebframeworkGetMethod")) {
					httpMethod = "GET";
					path = ((WebframeworkGetMethod) annotation).value();
				} else if (annotation.annotationType().getName()
						.equals("br.com.ehmf.webframework.annotations.WebframeworkPostMethod")) {
					httpMethod = "POST";
					path = ((WebframeworkPostMethod) annotation).value();
				}
			}
			// WebFrameworkLogger.log(" - ", httpMethod + " " + path);
			RequestControllerData getData = new RequestControllerData(httpMethod, path, className, method.getName());
			ControllerMap.values.put(httpMethod + path, getData);
		}

	}

}
