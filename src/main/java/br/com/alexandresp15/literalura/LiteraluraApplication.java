package br.com.alexandresp15.literalura;

import br.com.alexandresp15.literalura.service.ConsumoApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication {

    public static void main(String[] args) {

        ConsumoApi consumo = new ConsumoApi();

        String endereco = "https://gutendex.com/books/?search=dom+casmurro";

        String json = consumo.obterDados(endereco);

        System.out.println(json);
    }
}
