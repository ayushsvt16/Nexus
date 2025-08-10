package com.nexus.nexusproject.DTO;

public class ResourceResponse {
    private Long id;
    private String subjectCode;
    private String subjectName;
    private String professorName;
    private String type;        // "Mid Sem" or "End Sem"
    private Integer semester;   // 1-8
    private Integer year;       // e.g., 2023
    private String branch;      // CSE, ECE, ...
    private String fileUrl;
    private String fileName;
    private String fileType;    // e.g., "pdf"
    private String uploader;    // uploader username/email (optional)
    private String notes;       // any extra info
    private String uploadedAt;  // ISO string (for serialization)

    // --- Getter and Setter Methods ---
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getProfessorName() {
        return professorName;
    }
    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Integer getSemester() {
        return semester;
    }
    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }

    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFileUrl() {
        return fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUploader() {
        return uploader;
    }
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUploadedAt() {
        return uploadedAt;
    }
    public void setUploadedAt(String uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
