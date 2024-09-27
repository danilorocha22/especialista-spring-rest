package com.dan.esr.core.openapi;

import com.dan.esr.api.exceptionhandler.Problem;
import com.dan.esr.api.v1.links.Links;
import com.dan.esr.api.v1.models.output.cozinha.CozinhaOutput;
import com.dan.esr.api.v1.models.output.pedido.PedidoOutput;
import com.dan.esr.api.v1.models.output.pedido.PedidoResumoOutput;
import com.dan.esr.api.v1.openapi.models.LinksModelOpenApi;
import com.dan.esr.api.v1.openapi.models.PageModelOpenApi;
import com.dan.esr.api.v1.openapi.models.PageableModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.List;
import java.util.function.Consumer;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static springfox.documentation.schema.AlternateTypeRules.newRule;
import static springfox.documentation.schema.ScalarType.STRING;
import static springfox.documentation.service.ParameterType.QUERY;
import static springfox.documentation.spi.DocumentationType.OAS_30;

//@EnableSwagger2 // Com o SpringFox 3.0, esta anotação não é necessária
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig /*implements WebMvcConfigurer*/ {
    TypeResolver typeResolver = new TypeResolver();

    @Bean
    public Docket apiDocketV1() {
        return new Docket(OAS_30)
                .groupName("V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dan.esr.api"))
                //.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/v1/**"))
                //.paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(GET, globalGetResponseMessages())
                .globalResponses(POST, globalPostPutResponseMessages())
                .globalResponses(PUT, globalPostPutResponseMessages())
                .globalResponses(DELETE, globalDeleteResponseMessages())
                //.globalRequestParameters(getParametrosOperacao())
                .ignoredParameterTypes(getModelosIgnorados())
                .additionalModels(typeResolver.resolve(Problem.class))
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(getClasseAlternativa(CozinhaOutput.class))
                .alternateTypeRules(getClasseAlternativa(PedidoOutput.class))
                .alternateTypeRules(getClasseAlternativa(PedidoResumoOutput.class))
                .alternateTypeRules()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .apiInfo(apiInfoV1())
                .tags(tags()[0], tags());
    }

    private List<SecurityContext> securityContexts() {
        var securityReference = SecurityReference.builder()
                .reference("Danfood")
                .scopes(scopes().toArray(new AuthorizationScope[0]))
                .build();

        return List.of(
                SecurityContext.builder()
                        .securityReferences(List.of(securityReference))
                        //.forPaths(PathSelectors.any())
                        .operationSelector(operationContext -> true)
                        .build()
        );
    }

    private List<SecurityScheme> securitySchemes() {
        return List.of(oauth());
    }

    private SecurityScheme oauth() {
        return new OAuthBuilder()
                .name("Danfood")
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    private List<GrantType> grantTypes() {
        return List.of(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
    }

    private List<AuthorizationScope> scopes() {
        return List.of(new AuthorizationScope("READ", "Acesso de leitura"),
                new AuthorizationScope("WRITE", "Acesso de escrita"));
    }

    //@Bean
    public Docket apiDocketV2() {
        return new Docket(OAS_30)
                .groupName("V2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dan.esr.api"))
                //.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/v2/**"))
                //.paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(GET, globalGetResponseMessages())
                .globalResponses(POST, globalPostPutResponseMessages())
                .globalResponses(PUT, globalPostPutResponseMessages())
                .globalResponses(DELETE, globalDeleteResponseMessages())
                //.globalRequestParameters(getParametrosOperacao())
                .ignoredParameterTypes(getModelosIgnorados())
                .additionalModels(typeResolver.resolve(Problem.class))
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                /*.alternateTypeRules(getClasseAlternativa(CozinhaOutput.class))
                .alternateTypeRules(getClasseAlternativa(PedidoOutput.class))
                .alternateTypeRules(getClasseAlternativa(PedidoResumoOutput.class))*/
                .alternateTypeRules()
                .apiInfo(apiInfoV2())
                /*.tags(tags()[0], tags())*/;
    }

    private Class<?>[] getModelosIgnorados() {
        return new Class<?>[]{
                ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class,
                Resource.class, File.class, InputStream.class
        };
    }

    private List<Response> globalDeleteResponseMessages() {
        return List.of(
                new ResponseBuilder()
                        .code(comoString(INTERNAL_SERVER_ERROR))
                        .description("Erro interno do Servidor")
                        .representation(APPLICATION_JSON)
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(BAD_REQUEST))
                        .description("Requisição inválida (erro do cliente)")
                        .representation(APPLICATION_JSON)
                        .apply(problemBuilder())
                        .build()
        );
    }

    private List<Response> globalPostPutResponseMessages() {
        return List.of(
                new ResponseBuilder()
                        .code(comoString(INTERNAL_SERVER_ERROR))
                        .description("Erro interno do Servidor")
                        .representation(APPLICATION_JSON)
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(NOT_ACCEPTABLE))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(comoString(BAD_REQUEST))
                        .description("Requisição inválida (erro do cliente)")
                        .representation(APPLICATION_JSON)
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(UNSUPPORTED_MEDIA_TYPE))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .representation(APPLICATION_JSON)
                        .apply(problemBuilder())
                        .build()
        );
    }

    private List<Response> globalGetResponseMessages() {
        return List.of(
                new ResponseBuilder()
                        .code(comoString(INTERNAL_SERVER_ERROR))
                        .description("Erro interno do Servidor")
                        .representation(APPLICATION_JSON)
                        .apply(problemBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(comoString(NOT_ACCEPTABLE))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build()
        );
    }

    private String comoString(HttpStatus httpStatus) {
        return String.valueOf(httpStatus.value());
    }

    private Consumer<RepresentationBuilder> problemBuilder() {
        return r -> r.model(m -> m.name("Problema")
                .referenceModel(
                        ref -> ref.key(
                                k -> k.qualifiedModelName(
                                        q -> q.name("Problema")
                                                .namespace("com.dan.esr.api.exceptionhandler")
                                ))));
    }

    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("DanFood API (Depreciada)")
                .description("API aberta para clientes e restaurantes. </br></strong> Essa versão da API está depreciada " +
                        "e deixará de existir a partir de 01/01/2025. Use a versão atual da API.</strong>")
                .version("1")
                .contact(new Contact("Danilo Rocha", "https://www.danfood.com", "danfood@danfood.com"))
                .build();
    }

    private ApiInfo apiInfoV2() {
        return new ApiInfoBuilder()
                .title("DanFood API")
                .description("API aberta para clientes e restaurantes")
                .version("2")
                .contact(new Contact("Danilo Rocha", "https://www.danfood.com", "danfood@danfood.com"))
                .build();
    }

    private Tag[] tags() {
        return new Tag[]{
                new Tag("Cidades", "Gerencia as cidades"),
                new Tag("Grupos", "Gerencia os grupos"),
                new Tag("Cozinhas", "Gerencia as cozinhas"),
                new Tag("Pedidos", "Gerencia os pedidos"),
                new Tag("Formas de Pagamento", "Gerencia a forma de pagamento"),
                new Tag("Restaurantes", "Gerencia os restaurantes"),
                new Tag("Produtos", "Gerencia os produtos de um restaurante"),
                new Tag("Foto Produtos", "Gerencia a foto dos produtos de um restaurante"),
                new Tag("Estados", "Gerencia os estados"),
                new Tag("Usuários", "Gerencia os usuários"),
                new Tag("Estatísticas", "Gerencia as estatísticas"),
        };
    }

    private <T> AlternateTypeRule getClasseAlternativa(Class<T> classModel) {
        return newRule(
                typeResolver.resolve(Page.class, classModel),
                typeResolver.resolve(PageModelOpenApi.class, classModel)
        );
    }

    private static List<RequestParameter> getParametrosOperacao() {
        return List.of(new RequestParameterBuilder()
                .name("campos")
                .description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
                .in(QUERY)
                .required(false)
                .query(q -> q.model(m -> m.scalarModel(STRING)))
                .build()
        );
    }

    /*@Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build();
    }*/

    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }*/

    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("index.html")
                .addResourceLocations("classpath:/META-INF/resources/");
    }*/
}