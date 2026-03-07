package br.com.alexandresp15.literalura.repository;

import br.com.alexandresp15.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Buscar autor pelo nome (evitar duplicados)
    Optional<Autor> findByNome(String nome);

    // Buscar autores vivos em determinado ano
    List<Autor> findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(
            Integer anoNascimento,
            Integer anoFalecimento
    );

}