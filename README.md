# translator

Simple translator app using Java17 (Spring Boot), Vite + Svelte and MongoDB Atlas. 

### Features
- Translate various languages by sending requests to [DeepL API](https://www.deepl.com/en/docs-api). 
- See your translation History (utilizing simple sessionID method)

### Usage

```bash
$ docker pull viktorvt/translator
$ docker run -p 8080:8080 viktorvt/translator:latest 
```
- access the app on -->>  http://localhost:8080/