package baseAPI.API.Swagger.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private String devUrl = "http://localhost:8080";

    private String prodUrl = "http://localhost:8080";

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Link de desenvolvimento");
        /*
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");
        */
        Contact contact = new Contact();
        contact.setEmail("devquinteiro@gmail.com");
        contact.setName("Mquinteiro");
        contact.setUrl("");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("baseAPI")
                .version("1.0")
                .contact(contact)
                .description("Base api, com login e swagger funcionais").termsOfService("")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }


}
