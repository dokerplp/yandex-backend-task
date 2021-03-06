package dokerplp.yandexbackendschool.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Error implements IResponse {
    private long code;
    private String message;

    public Error(HttpStatus status, String msg) {
        this.code = status.value();
        this.message = msg;
    }
}
