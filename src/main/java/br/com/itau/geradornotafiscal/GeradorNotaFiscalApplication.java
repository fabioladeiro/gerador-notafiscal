package br.com.itau.geradornotafiscal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeradorNotaFiscalApplication {

	private static final Logger logger = LoggerFactory.getLogger(GeradorNotaFiscalApplication.class);

	public static void main(String[] args) {

		try {
			logger.info("Iniciando a aplicação GeradorNotaFiscal...");
			SpringApplication.run(GeradorNotaFiscalApplication.class, args);
			logger.info("Aplicação GeradorNotaFiscal iniciada com sucesso.");
		} catch (Exception e) {
			logger.error("Erro ao iniciar a aplicação GeradorNotaFiscal: ", e);
		}
		
	}

}
