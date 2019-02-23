package pl.aw.gitinfo.repositoryinformation;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.aw.gitinfo.errorhandling.GlobalExceptionHandler;
import pl.aw.gitinfo.errorhandling.RestTemplateResponseErrorHandler;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryInformationResourceTest {

    private static final String EXISTING_OWNER = "allegro";
    private static final String EXISTING_REPOSITORY = "restapi-guideline";
    private static final String NOT_EXISTING_REPOSITORY = "gibberish123";

    private static final String VALID_URI_API = "/repositories/" + EXISTING_OWNER + "/" + EXISTING_REPOSITORY + "";
    private static final String INVALID_URI_API = "/repositories/" + EXISTING_OWNER + "/" + NOT_EXISTING_REPOSITORY + "";

    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

    @Spy
    private GitHubApiService gitHubApiService = new GitHubApiService(restTemplateBuilder);

    @InjectMocks
    private RepositoryInformationResource testee;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(testee)
                .setControllerAdvice(new RestTemplateResponseErrorHandler(), new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void should_return_ok() {
        try {
            //given
            stubServiceToReturnValidInformation();

            //when
            //then
            mockMvc.perform(get(VALID_URI_API)).andExpect(status().isOk());
        } catch (Exception e) {
            fail();
        }
    }

    @Test()
    public void should_return_not_found() {
        try {
            //given
            stubServiceToReturnEmptyInformation();

            //when
            //then
            mockMvc.perform(get(INVALID_URI_API)).andExpect(status().isNotFound());
        } catch (Exception e) {
            //then
            fail();
        }
    }

    @Test
    public void should_return_repository_information() {
        //given
        stubServiceToReturnValidInformation();
        ResponseEntity result;

        //when
        result = testee.getInformation(EXISTING_OWNER, EXISTING_REPOSITORY);

        //then
        assertNotNull(result.getBody());
    }

    @Test
    public void should_throw_RepositoryNotFoundException_when_no_repositories_found() {
        //given
        ResponseEntity result;
        stubServiceToReturnEmptyInformation();

        //when
        result = testee.getInformation(EXISTING_OWNER, NOT_EXISTING_REPOSITORY);

        //then
        assertNull(result.getBody());
    }

    private Optional<RepositoryInformation> getValidRepositoryInformation() {
        return Optional.of(new RepositoryInformation("test", "test", "test", 2, LocalDateTime.now()));
    }

    private void stubServiceToReturnEmptyInformation() {
        when(gitHubApiService.getGitHubRepository(EXISTING_OWNER, NOT_EXISTING_REPOSITORY)).thenReturn(Optional.empty());
    }

    private void stubServiceToReturnValidInformation() {
        when(gitHubApiService.getGitHubRepository(EXISTING_OWNER, EXISTING_REPOSITORY)).thenReturn(getValidRepositoryInformation());
    }
}