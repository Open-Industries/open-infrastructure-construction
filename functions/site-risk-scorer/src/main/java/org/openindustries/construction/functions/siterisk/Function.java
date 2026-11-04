package org.openindustries.construction.functions.siterisk;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores project risk signals (incidents, delay, weather exposure) with an auditable rationale.
 * Transparent, auditable rules: NORMAL (< 35), WARNING (35-69), CRITICAL (>= 70);
 * the rationale is returned with the score for audit (ISO/IEC 27002 A.8.15).
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Event(String projectId, int incidentIndex, int delayIndex, int weatherIndex) {
    }

    public record Score(String projectId, int score, String decision, String rationale) {
    }

    @Funq
    public Score siteRiskScorer(Event event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.incidentIndex() >= 70) {
            score += 40;
            why.append("incidentIndex >= 70; ");
        }
        if (event.delayIndex() >= 60) {
            score += 30;
            why.append("delayIndex >= 60; ");
        }
        if (event.weatherIndex() >= 50) {
            score += 30;
            why.append("weatherIndex >= 50; ");
        }
        String decision = score >= 70 ? "CRITICAL" : score >= 35 ? "WARNING" : "NORMAL";
        return new Score(event.projectId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}
