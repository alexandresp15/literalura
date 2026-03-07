package br.com.alexandresp15.literalura;

import br.com.alexandresp15.literalura.model.DadosLivro;
import br.com.alexandresp15.literalura.model.DadosResposta;
import br.com.alexandresp15.literalura.service.ConverteDados;
import br.com.alexandresp15.literalura.service.ConsumoApi;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication {

    public static void main(String[] args) {

        ConsumoApi consumo = new ConsumoApi();
        String endereco = "https://gutendex.com/books/?search=dom+casmurro";

        String json = consumo.obterDados(endereco);

        ConverteDados conversor = new ConverteDados();
        DadosResposta dados = conversor.obterDados(json, DadosResposta.class);

        for (DadosLivro livro : dados.resultados()) {
            System.out.println("Livro: " + livro.titulo());
        }
    }
}
