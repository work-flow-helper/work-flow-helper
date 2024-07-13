package com.sparta.workflowhelper.domain.mapping.repository;

import com.sparta.workflowhelper.domain.mapping.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

}
