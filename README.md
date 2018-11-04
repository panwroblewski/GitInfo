---

## Overview

This is a demo project for hiring process. Requirements are listed in `Software Engineer - zadanie rekrutacyjne.pdf` file.

It is a production ready web service for communicating with GitHub API on basic level.
1. Maven **verify** phase includes an end-to-end performance test plan.
2. Maven **package** phase will also include a docker process to the build.  


---

## Usage

  * Execute `mvn verify` to run test
    * 2 maven profiles are configured: `local` and `jenkins` in case of performance tests
     would be ran on jenkins  
  * Execute `mvn package` to build the application and execute docker build process
  * Execute `mvn spring-boot:run` to run the application
  
    * The server is available on port 8080
    * There is one endpoint available i.e:
    
        GET /repositories/{owner}/{repository-name}
    
        With API response:
    
    ```json
    
    {
      "fullName": "...",
      "description": "...",
      "cloneUrl": "...",
      "stars": 0,
      "createdAt": "..."
    }
    ```
    This API will return empty 404 response in case of repository not found. This is due to GitHub API
    returning 404 for private repositories without providing credentials for security reasons. 