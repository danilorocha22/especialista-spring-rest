package com.dan.esr;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.repositories.CozinhaRepository;
import com.dan.esr.util.DatabaseCleaner;
import com.dan.esr.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestPropertySource("/application-test.properties")//Configurando um banco de testes
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {
    /*INÍCIO DOS TESTES DA API utilizando a lib RestAssured*/
    public static final String COZINHA_JSON = "/json/correto/cozinhaChinesa.json";
    public static final Long ID_COZINHA_INEXISTENTE = 1000L;
    public static final String COZINHA_AMERICANA = "Americana";

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner cleaner;

    @Autowired
    private CozinhaRepository cadastroCozinha;

    private int totalCozinhas;

    private Cozinha cozinhaAmericana;

    //Método de Callback para auxiliar os métodos de testes
    @BeforeEach // Código executado antes de cada teste
    public void setUp() {
        //Verifica o que foi enviado na requisição e o que foi retornado na resposta
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";
        this.cleaner.clearTables();
        this.totalCozinhas = this.prepararDados();
    }

    /*Métodos de Testes*/

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        given() //dado cenário
            .accept(ContentType.JSON)
        .when() //quando fazer get
            .get()
        .then() //então resultado
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConterTotalCozinhas_QuandoConsultarCozinhas() {
        given() //dado cenário
            .accept(ContentType.JSON)
        .when() //quando fizer get
            .get()
        .then() //então resultado
            .body("tipo", hasSize(this.totalCozinhas));
            //.body("tipo", hasItems("Brasileira"));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        given()
            .body(ResourceUtils.getContentFromResource(COZINHA_JSON))
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarStatus200_QuandoBuscarCozinhaExistente() {
        // GET /cozinhas/{cozinhaId}, ou seja, /cozinhas/2
        given() //dado cenário
            .pathParams("cozinhaId", this.cozinhaAmericana.getId())
            .accept(ContentType.JSON)
        .when() //quando fazer get
            .get("/{cozinhaId}")
        .then() //então resultado
            .statusCode(HttpStatus.OK.value())
                .body("tipo", equalTo(this.cozinhaAmericana.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoBuscarCozinhaInexistente() {
        // GET /cozinhas/{cozinhaId}, ou seja, /cozinhas/2
        given() //dado cenário
            .pathParams("cozinhaId", ID_COZINHA_INEXISTENTE)
            .accept(ContentType.JSON)
        .when() //quando fazer get
            .get("/{cozinhaId}")
        .then() //então resultado
            .statusCode(HttpStatus.NOT_FOUND.value());
    }


    private int prepararDados() {
        Cozinha coz1 = new Cozinha();
        coz1.setNome("Brasileira");

        cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome(COZINHA_AMERICANA);

        return this.cadastroCozinha.saveAll(List.of(coz1, cozinhaAmericana)).size();
    }


    /* FIM DOS TESTES DA API*/

/*
    */
/*INÍCIO DOS TESTES DE INTEGRAÇÃO*/
    /*
    //Os testes são divididos em três fases: Cenário, Ação e Validação
    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Test
    public void sucesso_CadastroCozinhaCorretamente() {
        //cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        //ação
        novaCozinha = cadastroCozinha.salvarOuAtualizar(novaCozinha);

        //validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test()
    public void falha_CadastroCozinha_ComNomeNulo() {
        //validação quando erro
        assertThrows(ConstraintViolationException.class, () -> {

            //cenário
            Cozinha novaCozinha = new Cozinha();
            novaCozinha.setNome(null);

            //ação
            cadastroCozinha.salvarOuAtualizar(novaCozinha);
        });
    }

    @Test
    public void falha_CadastroCozinha_ComNomeVazio() {
        //validação quando erro
        assertThrows(ConstraintViolationException.class, () -> {
            //cenário
            Cozinha novaCozinha = new Cozinha();
            novaCozinha.setNome(" ");

            //ação
            cadastroCozinha.salvarOuAtualizar(novaCozinha);
        });
    }

    @Test
    public void falha_QuandoExcluirCozinhaEmUso() {
        assertThrows(EntidadeEmUsoException.class, () -> {
            Cozinha novaCozinha = new Cozinha();
            novaCozinha.setNome("Havaiana");
            novaCozinha = this.cadastroCozinha.salvarOuAtualizar(novaCozinha);
            System.out.println("ID da nova cozinha" + novaCozinha.getId());

            Restaurante restaurante = new Restaurante();
            restaurante.setNome("Restaurante Bom de Prato");
            restaurante.setTaxaFrete(new BigDecimal(15));
            restaurante.setCozinha(novaCozinha);
            this.cadastroRestaurante.salvarOuAtualizar(restaurante);

            this.cadastroCozinha.remover(novaCozinha.getId());
        });
    }

    @Test
    public void falha_QuandoExcluirCozinhaInexistente() {
        assertThrows(CozinhaNaoEncontradaException.class, () ->
                this.cadastroCozinha.remover(10L)
        );
    }

*/

}
