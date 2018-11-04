package pl.aw.gitinfo.repositoryinformation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.aw.gitinfo.errorhandling.ExceptionJSON;

import javax.servlet.http.HttpServletRequest;
import java.rmi.UnexpectedException;
import java.util.Optional;

@Slf4j
@RestController
public class RepositoryInformationResource {

    private final GitHubApiService gitHubApiService;

    public RepositoryInformationResource(final GitHubApiService gitHubApiService) {
        this.gitHubApiService = gitHubApiService;
    }

    @GetMapping("/repositories/{owner}/{repository-name}")
    public ResponseEntity getInformation(@PathVariable("owner") String owner,
                                         @PathVariable("repository-name") String repositoryName) {

        log.info("Requested information about owner: {}, repository: {}", owner, repositoryName);

        Optional<RepositoryInformation> repositoryInformation = gitHubApiService.getGitHubRepository(owner, repositoryName);

        return ResponseEntity.of(repositoryInformation);
    }

    @ExceptionHandler(UnexpectedException.class)
    public @ResponseBody
    ExceptionJSON handleUnexpectedException(HttpServletRequest request, Exception ex) {

        ExceptionJSON response = new ExceptionJSON();
        response.setUrl(request.getRequestURL().toString());
        response.setMessage(ex.getMessage());

        return response;
    }
}
