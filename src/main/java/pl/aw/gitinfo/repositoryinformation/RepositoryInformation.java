package pl.aw.gitinfo.repositoryinformation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.LocalDateTime;

@Value
class RepositoryInformation {
    private final String fullName;
    private final String description;
    private final String cloneUrl;
    private final int stars;
    private final LocalDateTime createdAt;

    @JsonCreator
    RepositoryInformation(@JsonProperty("full_name") String fullName,
                          @JsonProperty("description") String description,
                          @JsonProperty("clone_url") String cloneUrl,
                          @JsonProperty("stargazers_count") int stars,
                          @JsonProperty("created_at") LocalDateTime createdAt) {
        this.fullName = fullName;
        this.description = description;
        this.cloneUrl = cloneUrl;
        this.stars = stars;
        this.createdAt = createdAt;
    }
}
