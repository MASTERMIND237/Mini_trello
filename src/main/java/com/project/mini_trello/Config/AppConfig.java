package com.project.mini_trello.Config;

 
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
 
/**
 * Configuration globale de ModelMapper.
 * Déclaré en @Bean pour être injecté dans tous les Services via @Autowired / constructeur.
 */
@Configuration
public class AppConfig {
 
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
 
        // STRICT : évite les mappings ambigus entre champs de noms similaires
        // ex : userId dans ProjetDTO ne sera pas mappé sur id_User de User par erreur
        mapper.getConfiguration()
              .setMatchingStrategy(MatchingStrategies.STRICT);
 
        return mapper;
    }


    // ── Configuration CORS globale ────────────────────────────────────────────
    /**
     * Autorise React (localhost:3000) à consommer l'API Spring (localhost:8080)
     * sans être bloqué par la politique Same-Origin du navigateur.
     *
     * En production, remplace "http://localhost:3000"
     * par l'URL réelle de ton frontend hébergé.
     */
    //@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")          // tous les endpoints /api/...
                .allowedOrigins(
                    "http://localhost:3000",     // React dev server
                    "http://localhost:5173"      // Vite dev server (si utilisé)
                )
                .allowedMethods(
                    "GET",
                    "POST",
                    "PUT",
                    "DELETE",
                    "OPTIONS"                   // preflight Axios
                )
                .allowedHeaders("*")            // Authorization, Content-Type, etc.
                .allowCredentials(true)         // cookies / headers d'auth futurs
                .maxAge(3600);                  // cache preflight 1h (réduit les requêtes OPTIONS)
    }
}
