package com.project.mini_trello.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Corps JSON uniforme pour toutes les réponses d'erreur.
 * Exemple :
 * {
 *   "status"    : 404,
 *   "error"     : "Not Found",
 *   "message"   : "User non trouvé(e) avec id = '99'",
 *   "timestamp" : "2024-05-01T12:00:00",
 *   "details"   : []
 * }
 */
public class ErrorResponse {

    private int status;
    private String error;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private List<String> details;   // erreurs de validation champ par champ

    public ErrorResponse(int status, String error, String message, List<String> details) {
        this.status    = status;
        this.error     = error;
        this.message   = message;
        this.timestamp = LocalDateTime.now();
        this.details   = details;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public int getStatus()              { return status; }
    public String getError()            { return error; }
    public String getMessage()          { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public List<String> getDetails()    { return details; }
}

