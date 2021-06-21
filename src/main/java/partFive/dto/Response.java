package partFive.dto;

import com.sun.net.httpserver.HttpExchange;
import lombok.Data;

@Data
public class Response {
    Object data;
    String template = "";
    Integer statusCode;
    HttpExchange httpExchange;
}
