package northwind;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class PedidoService {
    private final JdbcTemplate jdbc;

    public PedidoService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> sinCache() {
        return jdbc.queryForList("""
            SELECT o.OrderID AS pedido, c.CompanyName AS cliente,
                   CAST(CAST(o.OrderDate AS date) AS varchar(10)) AS fecha,
                   p.ProductName AS producto, d.Quantity AS cantidad,
                   CAST(d.UnitPrice * d.Quantity * (1-d.Discount) AS decimal(18,2)) AS importe
            FROM Orders o
            JOIN Customers c ON c.CustomerID = o.CustomerID
            JOIN OrderDetails d ON d.OrderID = o.OrderID
            JOIN Products p ON p.ProductID = d.ProductID
            ORDER BY o.OrderID, d.ProductID
            """);
    }

    // Spring guarda el resultado; las llamadas siguientes lo leen de memoria.
    @Cacheable(value = "pedidos", key = "'todos'")
    public List<Map<String, Object>> conCache() {
        return sinCache();
    }
}
