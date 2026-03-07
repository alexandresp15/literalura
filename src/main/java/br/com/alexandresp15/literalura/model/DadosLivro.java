package br.com.alexandresp15.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(

        @JsonProperty("title")
        String titulo,

        @JsonProperty("authors")
        List<DadosAutor> autores,

        @JsonProperty("languages")
        List<String> idiomas,

        @JsonProperty("download_count")
        Integer numeroDownloads
) {}