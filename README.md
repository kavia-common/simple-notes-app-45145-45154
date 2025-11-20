# simple-notes-app-45145-45154

Notes Backend (Spring Boot) provides CRUD operations for notes with in-memory H2 persistence and OpenAPI docs.

How to run:
- The service listens on port 3001.
- Build and run (Gradle wrapper):
  - On Linux/Mac: `cd notes_backend && ./gradlew bootRun`
  - On Windows: `cd notes_backend && gradlew.bat bootRun`

Key endpoints:
- Swagger UI: /swagger-ui.html (or /docs redirect)
- OpenAPI JSON: /openapi.json
- Health: /health
- Notes API:
  - POST /api/notes
  - GET /api/notes/{id}
  - GET /api/notes?page=0&size=20
  - PUT /api/notes/{id}
  - DELETE /api/notes/{id}

Example NoteRequest:
{
  "title": "Shopping List",
  "content": "Buy milk, eggs, and bread"
}
