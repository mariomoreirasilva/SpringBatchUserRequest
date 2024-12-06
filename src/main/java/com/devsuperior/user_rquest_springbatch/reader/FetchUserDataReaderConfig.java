package com.devsuperior.user_rquest_springbatch.reader;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.devsuperior.user_rquest_springbatch.domain.ResponseUser;
import com.devsuperior.user_rquest_springbatch.dto.UserDTO;

@Configuration
public class FetchUserDataReaderConfig implements ItemReader<UserDTO> {
	
	//somente para visualizar a execução
	private static Logger logger = LoggerFactory.getLogger(FetchUserDataReaderConfig.class);
	
	private final String BASE_URL = "http://localhost:8081";
	//variavel que usa como cliente api(consumir api externa)
	private RestTemplate restTemplete = new RestTemplate();
	
	private int page =0;
	
	//criar uma lista de userDTO para armazenar a busca da api
	private List<UserDTO> users = new ArrayList<>();
	//controle para percorrer a lista
	private int userIndex = 0;
	
	//os parametros abaixo servem para definir a quantidade de leitura(chunk) e o tamanho de registros da pagina.
	//importantes para a chamada da api externa para fazer o carregamento
	@Value("${chunkSize}")
	private int chunkSize;
	
	@Value("${pageSize}")
	private int pageSize;

	@Override
	public UserDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		UserDTO user;
		//rotina para controlar as leituras. Assim que ler tudo, retorna nulo
		if (userIndex < users.size())
			user = users.get(userIndex);
		else
			user = null;
		
		userIndex ++;
		return null;
	}
	
	//criar um metodo que conecta na api remota
	private List<UserDTO> fetchUserDataFromAPI(){
		//String uri = "http://localhost:8081/clients/pagedData?page=0&size=10";
		String uri = BASE_URL + "/clients/pagedData?page=%d&size=%d";
		
		logger.info("[READER STEP] Buscando dados...");
		logger.info("[READER STEP] Request da URI: " + String.format(uri, getPage(), pageSize));
		
		//chamada da api usando o restTamplate
		ResponseEntity<ResponseUser> response = restTemplete.exchange(String.format(uri, getPage(), pageSize), HttpMethod.GET, null,new ParameterizedTypeReference<ResponseUser>() {
			
										});
		List<UserDTO> result = response.getBody().getContent();
		return result;
	}
	
	public int getPage() {
		return page;
	}
	
	public void incrementaPage() {
		this.page++;
	}
	
	//vai carregando os valores do chunk na lista. Se tamonho da pagina igual a 10 e chunk = 5, carrega 5 e depois mais 5
	@BeforeChunk
	public void beforeChunk(ChunkContext context) {
		for(int i = 0; i < chunkSize; i += pageSize) {
			users.addAll(fetchUserDataFromAPI());
		}
	}
	
	//depois que executou o chunk, vai incrementado a pagina e carregando a lista ate acabar as paginas
	@AfterChunk
	public void afterChunk(ChunkContext context) { 
		logger.info("Fim do chunk.");
		incrementaPage();
		//a proxima pagina volta para o indice = 0 para começar a incremento da proxima pagina
		userIndex = 0;
		users = new ArrayList<>();
	}
}
