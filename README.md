# translator

Simple translator app using Java17 (Spring Boot), Vite + Svelte and MongoDB Atlas. 

### Features
- Translate various languages by sending requests to [DeepL API](https://www.deepl.com/en/docs-api). 
- See your translation History (utilizing simple sessionID method)

### Usage

```bash
$ git clone https://github.com/ta1ss/translator.git
$ cd translator/
$ docker compose up
```
- access the app on -->>  http://localhost:5173/

Beware, currently using heavyweight build images. 