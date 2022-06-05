package dokerplp.yandexbackendschool.model.httpDto;

import org.springframework.http.HttpStatus;

public enum HttpDto {
    OK(new Ok()),
    NOT_FOUND(new Error(HttpStatus.NOT_FOUND, "Item not found")),
    BAD_REQUEST(new Error(HttpStatus.BAD_REQUEST, "Validation Failed"));

    private final Response response;

    HttpDto(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
