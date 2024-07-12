package com.sparta.workflowhelper.domain.mapping.repository;

import com.sparta.workflowhelper.domain.mapping.entity.ProjectMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    List<Long> findUserIdsByProjectId(Long projectId);

}
