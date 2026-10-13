package northwind;

import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class PedidoController {
    private final PedidoService servicio;
    private final CacheManager cacheManager;

    public PedidoController(PedidoService servicio, CacheManager cacheManager) {
        this.servicio = servicio;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/pedidos")
    public Map<String, Object> consultar(@RequestParam(defaultValue = "false") boolean cache) {
        boolean enMemoria = cache && cacheManager.getCache("pedidos").get("todos") != null;
        long inicio = System.nanoTime();
        List<Map<String, Object>> datos = cache ? servicio.conCache() : servicio.sinCache();
        double tiempo = (System.nanoTime() - inicio) / 1_000_000.0;

        return Map.of("datos", datos, "total", datos.size(), "tiempo", tiempo,
                "origen", enMemoria ? "Memoria" : "Base de datos");
    }

    @DeleteMapping("/cache")
    public Map<String, String> limpiar() {
        cacheManager.getCache("pedidos").clear();
        return Map.of("mensaje", "Cache vaciada");
    }
}
