package cloud.ieum.responseDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CommonResponse<T> {
    boolean success;
    T response;
    HttpStatus error = null;

    public CommonResponse(boolean success, T response, HttpStatus error) {
        this.success = success;
        this.response = response;
        this.error = error;
    }
}
