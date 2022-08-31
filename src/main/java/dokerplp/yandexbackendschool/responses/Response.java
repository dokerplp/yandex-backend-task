package dokerplp.yandexbackendschool.responses;

import org.springframework.http.HttpStatus;

public enum Response {
    OK(new Ok()),
    NOT_FOUND(new Error(HttpStatus.NOT_FOUND, "Item not found")),
    BAD_REQUEST(new Error(HttpStatus.BAD_REQUEST, "Validation Failed"));

    private final IResponse IResponse;

    Response(IResponse IResponse) {
        this.IResponse = IResponse;
    }

    public IResponse getResponse() {
        return IResponse;
    }
}
