package com.sparta.workflowhelper.domain.mapping.repository;

import com.sparta.workflowhelper.domain.mapping.entity.ProjectMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    Optional<ProjectMember> findByIdAndProjectId(Long workerId, Long projectId);

    boolean existsByIdAndProjectId(Long workerId, Long projectId);
}
