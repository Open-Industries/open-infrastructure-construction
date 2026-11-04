package org.openindustries.construction.safety;

import java.util.UUID;

/**
 * SafetyStandard aggregate — a decision rule of this core: for one activityType, it guarantees a
 * capacity (maxCrewSize) and a threshold (maxIncidentRate), with a precedence priority.
 * Only ACTIVE rules decide; every decision names its rule and its reason (audit).
 */
public class SafetyStandard {
    public UUID id;
    public String name;
    public String activityType;
    public long maxCrewSize;
    public int maxIncidentRate;
    public int priority;
    public String status;

    public static SafetyStandard create(String name, String activityType, long maxCrewSize, int maxIncidentRate, int priority) {
        SafetyStandard r = new SafetyStandard();
        r.id = UUID.randomUUID();
        r.name = name;
        r.activityType = activityType;
        r.maxCrewSize = maxCrewSize;
        r.maxIncidentRate = maxIncidentRate;
        r.priority = priority;
        r.status = "DRAFT";
        return r;
    }
}
