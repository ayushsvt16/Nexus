package com.nexus.nexusproject.Repository;
// path of the directory

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexus.nexusproject.model.ExamResource;

@Repository //ye batata hai ki spring data jpa ka repository hai
public interface ExamResourceRepository extends JpaRepository<ExamResource, Long> {
    // spring data jpa automatically implementation generate karega mtb basic CRUD operations automatically add ho jata h
    List<ExamResource> findBySemesterAndBranchAndType(Integer semester, String branch, String type);
    List<ExamResource> findBySemesterAndBranchAndTypeAndYear(Integer semester, String branch, String type, Integer year);
    List<ExamResource> findByYear(Integer year);
    List<ExamResource> findBySemester(Integer semester);
    List<ExamResource> findByBranch(String branch);
    List<ExamResource> findByType(String type);
}