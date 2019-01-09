package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

public class Util {
	/**
     * Recebe e encurta um endereço.
     *
     * @param endereco endereço que se quer encurtar
     * @return endereço reduzido
     */
    public static String encurtarEndereco(String endereco) {
        String enderecoNovo = "";
        List<String> palavrasList = new ArrayList<>();

        // Separa endereço por espaço
        StringTokenizer st = new StringTokenizer(endereco);

        // Busca palavra a palavra e adiciona na lista
        while (st.hasMoreElements()) {
            palavrasList.add(st.nextElement().toString());
        }

        // Define palavras e sua versão encurtada
        HashMap<String, String> palavrasHash = new HashMap<>();
        
        // Tipo de lugar
        palavrasHash.put("Industrial", "IND");
        palavrasHash.put("Município", "MUN");
        palavrasHash.put("República", "REP");
        
        // Tipos de rua
        palavrasHash.put("Alameda", "AL");
        palavrasHash.put("Avenida", "AV");
        palavrasHash.put("Conjunto", "CJ");
        palavrasHash.put("Esplanada", "ESP");
        palavrasHash.put("Estrada", "EST");
        palavrasHash.put("Ferrovia", "FER");
        palavrasHash.put("Habitacional", "HAB");
        palavrasHash.put("Jardim", "JD");
        palavrasHash.put("Loteamento", "LOT");
        palavrasHash.put("Parque", "PRQ");
        palavrasHash.put("Passarela", "PSA");
        palavrasHash.put("Praça", "PC");
        palavrasHash.put("Recanto","REC");
        palavrasHash.put("Rodovia", "ROD");
        palavrasHash.put("Rua", "R");
        palavrasHash.put("Servidão","SRV");
        palavrasHash.put("Travessa", "TV");
        palavrasHash.put("Trevo", "TRV");
        palavrasHash.put("Viaduto", "VD");
        palavrasHash.put("Vila", "VL");

        // Tratamentos pessoais
        palavrasHash.put("Senhor", "SR");
        palavrasHash.put("Senhora", "SRA");
        
        // Religioso 
        palavrasHash.put("Santa", "STA");
        palavrasHash.put("Reverendo", "REV");

        // Profissões
        palavrasHash.put("Arquiteta", "ARQ");
        palavrasHash.put("Arquiteto", "ARQ");
        palavrasHash.put("Advogada", "ADV");
        palavrasHash.put("Advogado", "ADV");
        palavrasHash.put("Capitão", "CAP");
        palavrasHash.put("Comendadora", "COM");
        palavrasHash.put("Comendador", "COM");
        palavrasHash.put("Coronel", "CEL");
        palavrasHash.put("Deputado", "DEP");
        palavrasHash.put("Desembargador", "DES");
        palavrasHash.put("Desembargadora", "DES");
        palavrasHash.put("Doutora", "DRA");
        palavrasHash.put("Doutor", "DR");
        palavrasHash.put("Engenheira", "ENG");
        palavrasHash.put("Engenheiro", "ENG");
        palavrasHash.put("Expedicionário", "EXP");
        palavrasHash.put("General", "GEN");
        palavrasHash.put("Governador", "GOV");
        palavrasHash.put("Jornalista", "JOR");
        palavrasHash.put("Major", "MAJ");
        palavrasHash.put("Marechal", "MAL");
        palavrasHash.put("Prefeito", "PREF");
        palavrasHash.put("Presidente", "PRES");
        palavrasHash.put("Professora", "PROF");
        palavrasHash.put("Professor", "PROF");
        palavrasHash.put("Sargento", "SARG");
        palavrasHash.put("Senador", "SEN");
        palavrasHash.put("Senadora", "SEN");
        palavrasHash.put("Soldado", "SD");
        palavrasHash.put("Tenente", "TEN");
        palavrasHash.put("Vereadora", "VER");
        palavrasHash.put("Vereador", "VER");

        // Para cada chave existente no HashMap, verifica se existe alguma palavra do endereço que pode ser encurtada e substitui
        // ela pela sua versão encurtada
        for (String chave : palavrasHash.keySet()) {
            for (int i = 0; i < palavrasList.size(); i++) {
                if (palavrasList.get(i).contains(chave)) {
                    palavrasList.set(i, palavrasHash.get(chave));
                }
            }
        }

        // Percorre a lista de palavras do endereço e remonta a string de endereço.
        for (int i = 0; i < palavrasList.size(); i++) {
            enderecoNovo += palavrasList.get(i) + " ";
        }

        return enderecoNovo;
    }
    
    public static String buscaNumFinal(String endereco){
    	// Separa endereço por espaço
        StringTokenizer st = new StringTokenizer(endereco);
  
        List<String> palavrasList = new ArrayList<>();
        
        String resultado = "";
       
        // Busca palavra a palavra e adiciona na lista
        while (st.hasMoreElements()) {
            palavrasList.add(st.nextElement().toString());
        }
        
        ListIterator li = palavrasList.listIterator(palavrasList.size());
        
        while (li.hasPrevious()) {
        	String palavra = li.previous().toString();
        	if (Character.isDigit(palavra.trim().charAt(0))) {
        		if (palavra.equals("0")) {
        			String palavra2 = li.previous().toString();
        			if (Character.isDigit(palavra2.charAt(0))) {
        				return palavra2;
        			} else {
        				return "0";
        			}
        		}
        		return palavra;
        	} else {
        		resultado = "";
        	}	
        }
        return resultado;
    }
    
    public static long calcularTempoEstimado(long tempoInicio, int qtdClientesFaltando) {
    	Long tempoFim = System.currentTimeMillis();
		Long tempoTotalSec = ((tempoFim - tempoInicio) / 1000) * qtdClientesFaltando;
		Long tempoTotalMin = tempoTotalSec / 60;
		Long tempoTotalHr = tempoTotalMin / 60;
		
		return tempoTotalHr;
    }
}
