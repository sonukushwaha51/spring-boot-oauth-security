# spring-boot-oauth-security

# Genertae JWT Secrets

`https://jwtsecrets.com/#generator`

# Create Stateless session

`.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))`

# Oauth2 authorization callback url

http://localhost:8080/v1/login/oauth2/code/{provider}

provider e.g. github, google etc.

# Encrypt client-secret

`Run LocalEncryptor class`
