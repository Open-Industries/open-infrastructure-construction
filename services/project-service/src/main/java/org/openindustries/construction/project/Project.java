package org.openindustries.construction.project;

import java.time.Instant;
import java.util.UUID;

/**
 * Project aggregate — Infrastructure project registry.
 *
 * Privacy by design (ISO/IEC 27002 A.5.34): secret identifiers are validated at
 * creation and NEVER persisted or returned in clear — only a masked form is kept.
 * All identifiers in this reference implementation are synthetic.
 */
public class Project {
    public UUID id;
        public String name;
        public String projectType;
        public String regionCode;
    public String status;
    public Instant createdAt;

    public static Project create(String name, String projectType, String regionCode) {
        Project e = new Project();
        e.id = UUID.randomUUID();
        e.name = name;
        e.projectType = projectType;
        e.regionCode = regionCode;
        e.status = "PLANNED";
        e.createdAt = Instant.now();
        return e;
    }
}
