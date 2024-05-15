package com.dan.esr;

import com.dan.esr.domain.entities.Cozinha;
import com.dan.esr.domain.entities.Restaurante;
import com.dan.esr.domain.repositories.CozinhaRepository;
import com.dan.esr.domain.repositories.RestauranteRepository;
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

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


@TestPropertySource("/application-test.properties")//Configurando um banco de testes
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroRestauranteIT {
    /*INÍCIO DOS TESTES DA API utilizando a lib RestAssured*/
    public static final String RESTAURANTE_CORRETO_JSON = "/json/correto/restauranteNewYorkComCozinha.json";
    public static final String RESTAURANTE_COM_COZINHA_INEXISTENTE_JSON = "/json/incorreto/restauranteNewYorkComCozinhaInexistente.json";
    public static final String RESTAURANTE_SEM_COZINHA_JSON = "/json/incorreto/restauranteNewYorkSemCozinha.json";
    public static final String RESTAURANTE_SEM_FRETE_JSON = "/json/incorreto/restauranteNewYorkSemFrete.json";
    public static final Long RESTAURANTE_ID_INEXISTENTE = 1000L;
    public static final String RESTAURANTE_ID = "restauranteId";
    private static final Object DADOS_INVALIDOS_PROBLEM_TITLE = "Propriedade Inválida";
    private static final Object VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Erro na Requisição";

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner cleaner;

    @Autowired
    private RestauranteRepository cadastroRestaurante;

    @Autowired
    private CozinhaRepository cadastroCozinha;

    private Restaurante restauranteTexas;

    private int totalRestaurantes;

    //private Cozinha cozinhaAmericana;

    //Método de Callback para auxiliar os métodos de testes
    @BeforeEach // Código executado antes de cada teste
    public void setUp() {
        //Verifica o que foi enviado na requisição e o que foi retornado na resposta
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        this.cleaner.clearTables();
        this.totalRestaurantes = this.prepararDados();
    }

    /*Métodos de Testes*/

    @Test
    public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given() //dado cenário
            .accept(ContentType.JSON)
        .when() //quando fazer get
            .get()
        .then() //então resultado
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConterTotalRestaurantes_QuandoConsultarRestaurantes() {
        given() //dado cenário
            .accept(ContentType.JSON)
        .when() //quando fizer get
            .get()
        .then() //então resultado
            .body("nome", hasSize(this.totalRestaurantes));
            //.body("tipo", hasItems("Brasileira"));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
        given()
            .body(ResourceUtils.getContentFromResource(RESTAURANTE_CORRETO_JSON))
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }
    
    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
        given()
            .body(ResourceUtils.getContentFromResource(RESTAURANTE_SEM_FRETE_JSON))
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
        given()
            .body(ResourceUtils.getContentFromResource(RESTAURANTE_SEM_COZINHA_JSON))
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }
    
    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
        given()
            .body(ResourceUtils.getContentFromResource(RESTAURANTE_COM_COZINHA_INEXISTENTE_JSON))
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }

    @Test
    public void deveRetornarStatus200_QuandoBuscarRestauranteExistente() {
        // GET /cozinhas/{cozinhaId}, ou seja, /cozinhas/2
        given() //dado cenário
            .pathParams(RESTAURANTE_ID, this.restauranteTexas.getId())
            .accept(ContentType.JSON)
        .when() //quando fazer get
            .get("/{" + RESTAURANTE_ID + "}")
        .then() //então resultado
            .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(this.restauranteTexas.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoBuscarRestauranteInexistente() {
        // GET /cozinhas/{cozinhaId}, ou seja, /cozinhas/2
        given() //dado cenário
            .pathParams(RESTAURANTE_ID, RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when() //quando fazer get
            .get("/{"+ RESTAURANTE_ID +"}")
        .then() //então resultado
            .statusCode(HttpStatus.NOT_FOUND.value());
    }


    private int prepararDados() {
        Restaurante restaurantePratoFeito = new Restaurante();
        restaurantePratoFeito.setNome("Restaurante Prato Feito");
        restaurantePratoFeito.setTaxaFrete(new BigDecimal("8"));
        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        restaurantePratoFeito.setCozinha(cozinhaBrasileira);

        restauranteTexas = new Restaurante();
        restauranteTexas.setNome("Restaurante do Tio Sam");
        restauranteTexas.setTaxaFrete(new BigDecimal("7.5"));
        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        restauranteTexas.setCozinha(cozinhaAmericana);

        this.cadastroCozinha.saveAll(List.of(cozinhaBrasileira, cozinhaAmericana));

        return this.cadastroRestaurante.saveAll(List.of(restaurantePratoFeito, restauranteTexas)).size();
    }


    /* FIM DOS TESTES DA API*/

/*
    */
/*INÍCIO DOS TESTES DE INTEGRAÇÃO*//*

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
