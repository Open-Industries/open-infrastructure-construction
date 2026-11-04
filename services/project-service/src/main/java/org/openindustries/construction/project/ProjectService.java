package org.openindustries.construction.project;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProjectService {

    @Inject
    ProjectRepository repository;

    public Project create(CreateProjectRequest request) {
        return repository.save(Project.create(request.name(), request.projectType(), request.regionCode()));
    }

    public Project get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Project not found: " + id));
    }

    public List<Project> list() {
        return repository.findAll();
    }

    public Project start(UUID id) {
        Project entity = get(id);
        if ("COMPLETED".equals(entity.status)) {
            throw new DomainException(409, "A completed project is terminal");
        }
        if ("IN_PROGRESS".equals(entity.status)) {
            throw new DomainException(409, "Project already IN_PROGRESS");
        }
        entity.status = "IN_PROGRESS";
        return repository.save(entity);
    }

    public Project complete(UUID id) {
        Project entity = get(id);
        if ("COMPLETED".equals(entity.status)) {
            throw new DomainException(409, "A completed project is terminal");
        }
        if ("COMPLETED".equals(entity.status)) {
            throw new DomainException(409, "Project already COMPLETED");
        }
        entity.status = "COMPLETED";
        return repository.save(entity);
    }
}
