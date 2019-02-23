package pl.aw.gitinfo.errorhandling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionJSON {
    private String url;
    private String message;
}
