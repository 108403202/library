package book_use;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice//處理程序異常
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    //ResponseEntityExceptionHandler 可以覆蓋和擴展 Spring 提供的的異常處理方法
    @Autowired
    private ObjectMapper objectMapper;//將 Java 對象轉換為 JSON 數據
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        //ConstraintViolationException 為 Bean 驗證失敗 如當字段上的注解 如 @NotNull 等被違反時
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();//從異常對象
        String defaultMessage = constraintViolation.getMessage();
        JsonNode jsonNode = objectMapper.createObjectNode().put("message", defaultMessage);
        return ResponseEntity.status(400).body(jsonNode);
    }

    protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, final Object body, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        if (ex instanceof MethodArgumentNotValidException) {//請求參數驗證失敗。如果是這種異常，則使用自定義的 handleArgumentInvalid 方法。
            return handleArgumentInvalid((MethodArgumentNotValidException) ex);
        }
        log.error("Error: ", ex);
        JsonNode jsonNode = objectMapper.createObjectNode().put("message", ex.getLocalizedMessage());
        return ResponseEntity.status(status).body(jsonNode);
    }

    private ResponseEntity<Object> handleArgumentInvalid(MethodArgumentNotValidException ex) {
        //MethodArgumentNotValidException 在處理 @Valid 時，驗證失敗的異常
        String defaultMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        //取出錯誤列表中的第一个錯誤信息
        JsonNode jsonNode = objectMapper.createObjectNode().put("message", defaultMessage);
        return ResponseEntity.status(400).body(jsonNode);
    }
}
