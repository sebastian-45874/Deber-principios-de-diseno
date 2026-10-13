package northwind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableCaching
public class NorthwindApplication {
    public static void main(String[] args) {
        SpringApplication.run(NorthwindApplication.class, args);
    }

    // Abrir la pagina cuando el servidor ya esta listo.
    @EventListener(ApplicationReadyEvent.class)
    public void abrirPagina() {
        try {
            new ProcessBuilder("rundll32.exe", "url.dll,FileProtocolHandler",
                    "http://127.0.0.1:8080").start();
        } catch (Exception e) {
            System.out.println("Abre http://127.0.0.1:8080 en tu navegador.");
        }
    }
}
