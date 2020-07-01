package br.com.fapen.estoque.services;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component	
public class ArquivoService {
	
	@Autowired
	private ResourceLoader resourceLoader;

	public static final String DIRETORIO_BASE = "/home/rodrigo/dev/media/";
	public static final String DIRETORIO_TEMPLATE = "classpath:/templates/modeloEmail/";

	public String salvarEmDisco(MultipartFile arquivo) {
		String realPath = DIRETORIO_BASE;

		File diretorio = new File(realPath);
		if (!diretorio.exists()) {
			diretorio.mkdir();
		}
		diretorio = null;

		try {
			String path = realPath + arquivo.getOriginalFilename();
			arquivo.transferTo(new File(path));
			return "media/" + arquivo.getOriginalFilename();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return "media/padrao.jpg";
		}
	}
	
	public String loadTemplateEmail(String templateName) throws IOException {
		File arquivo = resourceLoader.getResource(DIRETORIO_TEMPLATE + templateName).getFile();
		Scanner leitor = new Scanner(arquivo);
		StringBuilder sb = new StringBuilder();
		
		while (leitor.hasNextLine()) {
			String linha = leitor.nextLine();
			sb.append(linha);
		}
		leitor.close();		
		return sb.toString();
	}	
}
