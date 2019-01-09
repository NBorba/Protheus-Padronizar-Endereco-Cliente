package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.json.JSONObject;

import model.Temp;
import util.Util;

public class TempController {
		   
	/**
	 * Realiza o processo total de update do endereço dos
	 * clientes baseado no CEP. Irá loopar até todos os clientes terem
	 * seus endereços atualizados.
	 * @throws IOException
	 */
	public static void buscarEndereco() throws IOException {
		Long tempoInicio = null;
		Long tempoFim = null;
		
		List dados = buscarDadosBase();
		
		HttpURLConnection con = null;
		int i;
		int qtdClientesFaltando;
		
		// Enquanto ainda existirem dados
		for (i = 0; i < dados.size(); i++) {
			tempoInicio = System.currentTimeMillis();
			
			// Busca o cliente da fila
			Temp temp = (Temp) dados.get(i);
			
			qtdClientesFaltando = dados.size() - i;
			
			// Montagem de url
			String url = "https://webmaniabr.com/api/1/cep/";
			String appKey = "?app_key=chave";
			String appSecret = "&app_secret=segredo";
			String cep = temp.getCep(); 
			
			URL urlConexao = new URL(url + cep + appKey + appSecret);
			con = (HttpURLConnection) urlConexao.openConnection();
			
			// Importante formatar caracteres para UTF-8 com abaixo
			// Podem vir caracteres especiais
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			// Recebe o endereço novo realizando o PARSE do JSON
			String enderecoNovo = realizaParseEnderecoJSON(response.toString().trim());
			String bairroNovo = realizaParseBairroJSON(response.toString().trim());
			String cidadeNova = realizaParseCidadeJSON(response.toString().trim());
			
			// Atualiza o bairro encontrado pelo programa
			// Normalizer tira os acentos das palavras
			temp.setBairroNovo(Normalizer
					.normalize(bairroNovo.toUpperCase(), Normalizer.Form.NFD)
					.replaceAll("[^\\p{ASCII}]", ""));
 			
			// Atualiza a cidade encontrado pelo programa
			// Normalizer tira os acentos das palavras
			temp.setCidadeNova(Normalizer
					.normalize(cidadeNova.toUpperCase(), Normalizer.Form.NFD)
					.replaceAll("[^\\p{ASCII}]", ""));
			
			// Atualiza os endereços novos no objeto temporário do cliente
			// Normalizer importante, pois Protheus não aceita acentos
			temp.setEndNovo(Normalizer
			        .normalize(Util.encurtarEndereco(enderecoNovo).toUpperCase(), Normalizer.Form.NFD)
			        .replaceAll("[^\\p{ASCII}]", ""));
			temp.setEndNaoAbreviado(Normalizer
			        .normalize(enderecoNovo.toUpperCase(), Normalizer.Form.NFD)
			        .replaceAll("[^\\p{ASCII}]", ""));
			
			temp.setNumFinal(Util.buscaNumFinal(temp.getEndAntigo()));
			
			// Salva cliente
			updateTemp(temp);
			
			// Travar próximo loop por alguns segundos para 
			// previnir que a API de CEP ache que algum bot está 
			// tentando atacar a rede e consequemente bloquear nossas chamadas
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Mostra que acabamos de atualizar o cliente 'i'
			System.out.println(temp.getRecno());
			System.out.println("Tempo estimado: " + Util.calcularTempoEstimado(tempoInicio, qtdClientesFaltando) + " Hora(s)" );
		}
		// Fecha conexão ao terminar 
		con.disconnect();
	}
	
	/**
	 * Realiza o update na tabela temporária com o novo endereço encontrado 
	 * @param temp
	 */
	private static void updateTemp(Temp temp) {
		Session session = util.HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.update(temp);
        t.commit();
        session.clear();
        session.close();
	}
	
	/**
	 * Busca os clientes que ainda não tiveram seus endereços buscados pelo programa
	 * @return Lista de clientes
	 */
	private static List buscarDadosBase() {
		List resultadoQuery = null;
		
		// Inicia transação
		Session session = util.HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		try{
			// Busca todos os clientes da base temporária onde o endereço
			// novo ainda se encontra vazio
			Criteria criteria = session.createCriteria(model.Temp.class);
			criteria.add(Restrictions.eq("endNovo", ""));
			resultadoQuery = criteria.list();
		} catch(Exception e) {
			throw e;
		} finally {
			// Fecha a sessão
			session.getTransaction().commit();
			session.flush();
			session.close();
		}
		return resultadoQuery;
	}
	
	/**
	 * Recebe json encontrado pelo CEP fornecido e retorna seu endereço
	 * @param json JSON encontrado ao buscar CEP
	 * @return Retorna endereço do CEP especificado
	 */
	private static String realizaParseBairroJSON(String json) {
		// Converte string em objeto JSON
		JSONObject objeto = new JSONObject(json);
		String resultado = "VAZIO";
		try {
			if(objeto.getString("bairro").equals("")) {
				resultado = "VAZIO";
			} else {
				resultado = objeto.getString("bairro");
			}
		}
		catch (JSONException e){
			resultado = "VAZIO";
		}
		return resultado;
	}

	/**
	 * Recebe json encontrado pelo CEP fornecido e retorna seu endereço
	 * @param json JSON encontrado ao buscar CEP
	 * @return Retorna endereço do CEP especificado
	 */
	private static String realizaParseCidadeJSON(String json) {
		// Converte string em objeto JSON
		JSONObject objeto = new JSONObject(json);
		String resultado = "VAZIO";
		try {
			if(objeto.getString("cidade").equals("")) {
				resultado = "VAZIO";
			} else {
				resultado = objeto.getString("cidade");
			}
		}
		catch (JSONException e){
			resultado = "VAZIO";
		}
		return resultado;
	}
	
	/**
	 * Recebe json encontrado pelo CEP fornecido e retorna seu endereço
	 * @param json JSON encontrado ao buscar CEP
	 * @return Retorna endereço do CEP especificado
	 */
	private static String realizaParseEnderecoJSON(String json) {
		// Converte string em objeto JSON
		JSONObject objeto = new JSONObject(json);
		String resultado = "VAZIO";
		try {
			if(objeto.getString("endereco").equals("")) {
				resultado = "VAZIO";
			} else {
				resultado = objeto.getString("endereco");
			}
		}
		catch (JSONException e){
			resultado = "VAZIO";
		}
		return resultado;
	}
}
