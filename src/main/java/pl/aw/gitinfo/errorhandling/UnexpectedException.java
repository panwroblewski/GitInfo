package pl.aw.gitinfo.errorhandling;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Unexpected Exception")
class UnexpectedException extends RuntimeException {

    UnexpectedException() {
        super("Unexpected Exception");
    }
}
