package pl.aw.gitinfo.repositoryinformation;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.aw.gitinfo.errorhandling.RestTemplateResponseErrorHandler;

import javax.ws.rs.NotFoundException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class GitHubApiService {

    private static final Duration TIMEOUT = Duration.ofSeconds(1000);
    private static final String PARAM_REPOSITORY_NAME = "repository-name";
    private static final String PARAM_OWNER = "owner";
    private static final String URI_GITHUB_API = "https://api.github.com/repos/{" + PARAM_OWNER + "}/{" + PARAM_REPOSITORY_NAME + "}";
    private final RestTemplate restTemplate;

    @Autowired
    public GitHubApiService(final RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .setConnectTimeout(TIMEOUT)
                .setReadTimeout(TIMEOUT)
                .build();
    }

    Optional<RepositoryInformation> getGitHubRepository(String ownerName, String repositoryName) {
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_OWNER, ownerName);
        params.put(PARAM_REPOSITORY_NAME, repositoryName);

        Optional<RepositoryInformation> result;
        try {
            result = Optional.ofNullable(restTemplate.getForObject(URI_GITHUB_API, RepositoryInformation.class, params));
        } catch (NotFoundException e) {
            result = Optional.empty();
        }

        log.info("Response from GitHub: {}", result);

        return result;
    }
}
