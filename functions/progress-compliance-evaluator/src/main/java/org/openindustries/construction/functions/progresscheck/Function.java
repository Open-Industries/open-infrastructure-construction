package org.openindustries.construction.functions.progresscheck;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Compares defect counts and schedule adherence against project targets; one finding per breach.
 * Compares measured values with targets and returns a verdict with one finding per
 * breach — machine-checkable assurance evidence.
 */
public class Function {

    public record Kpi(String projectId, int measuredDefectCount, int targetDefectCount,
                      double measuredScheduleAdherencePct, double targetScheduleAdherencePct) {
    }

    public record Verdict(String projectId, boolean compliant, List<String> findings) {
    }

    @Funq
    public Verdict progressComplianceEvaluator(Kpi kpi) {
        List<String> findings = new ArrayList<>();
        if (kpi.measuredDefectCount() > kpi.targetDefectCount()) {
            findings.add("defectCount " + kpi.measuredDefectCount() + " exceeds target " + kpi.targetDefectCount());
        }
        if (kpi.measuredScheduleAdherencePct() < kpi.targetScheduleAdherencePct()) {
            findings.add("scheduleAdherencePct " + kpi.measuredScheduleAdherencePct() + " below target " + kpi.targetScheduleAdherencePct());
        }
        return new Verdict(kpi.projectId(), findings.isEmpty(), findings);
    }
}
