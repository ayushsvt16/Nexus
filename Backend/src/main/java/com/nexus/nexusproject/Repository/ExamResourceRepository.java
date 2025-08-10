package com.nexus.nexusproject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexus.nexusproject.model.ExamResource;

@Repository
public interface ExamResourceRepository extends JpaRepository<ExamResource, Long> {
    List<ExamResource> findBySemesterAndBranchAndType(Integer semester, String branch, String type);
}