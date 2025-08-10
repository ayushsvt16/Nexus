import React, { useState, useEffect } from 'react';
import { useDropzone } from 'react-dropzone';

// Efficient drag-and-drop file upload using react-dropzone
function FileDropZone({ onFileAccepted }) {
  const { getRootProps, getInputProps, acceptedFiles, isDragActive } = useDropzone({
    accept: {
      'application/pdf': []
    },
    maxFiles: 1,
    onDrop: (files) => {
      if (files && files[0]) {
        onFileAccepted(files[0]);
      }
    }
  });

  return (
    <div
      {...getRootProps()}
      className={`border-2 border-dashed rounded-lg px-4 py-6 flex flex-col items-center justify-center transition-all duration-200 cursor-pointer ${isDragActive ? 'border-pink-500 bg-pink-50' : 'border-orange-300 bg-orange-10'}`}
      style={{ minHeight: '80px' }}
    >
      <input {...getInputProps()} />
      {acceptedFiles.length > 0 ? (
        <div className="flex flex-col items-center">
          <span className="text-sm font-medium text-orange-700">{acceptedFiles[0].name}</span>
          <span className="text-xs text-gray-500">{(acceptedFiles[0].size / 1024).toFixed(1)} KB</span>
        </div>
      ) : (
        <>
          <svg className="w-8 h-8 text-orange-400 mb-2" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" d="M12 16v-8m0 0l-4 4m4-4l4 4" />
            <rect x="3" y="3" width="18" height="18" rx="2" stroke="currentColor" strokeWidth="2" fill="none" />
          </svg>
          <span className="text-xs text-orange-700">Drag & drop PDF here or <span className="underline text-pink-500">choose file</span></span>
        </>
      )}
    </div>
  );
// End of FileDropZone (no extra closing brace)
}

const ExamVault = () => {
  const [showSemesterDropdown, setShowSemesterDropdown] = useState(false);
  const [showYearDropdown, setShowYearDropdown] = useState(false);
  const [showBranchDropdown, setShowBranchDropdown] = useState(false);
  const [showTypeDropdown, setShowTypeDropdown] = useState(false);

  // Refs for dropdown containers
  const semesterDropdownRef = React.useRef(null);
  const yearDropdownRef = React.useRef(null);
  const branchDropdownRef = React.useRef(null);
  const typeDropdownRef = React.useRef(null);
  const [selectedSemester, setSelectedSemester] = useState('I');
  const [selectedBranch, setBranch] = useState('CSE');
  const [selectedYear, setSelectedYear] = useState('2023');
  const [selectedExamType, setSelectedExamType] = useState('Mid Sem');
  const [examPapers, setExamPapers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showUploadForm, setShowUploadForm] = useState(false);
  const [uploadForm, setUploadForm] = useState({
    subjectCode: '',
    subjectName: '',
    professorName: '',
    type: 'Mid Sem',
    semester: 'I',
    year: '2023',
    branch: 'CSE',
  });
  const [uploadFile, setUploadFile] = useState(null);
  // ...existing code...
  // Close dropdowns when clicking outside
  useEffect(() => {
    function handleClickOutside(event) {
      if (showSemesterDropdown && semesterDropdownRef.current && !semesterDropdownRef.current.contains(event.target)) {
        setShowSemesterDropdown(false);
      }
      if (showYearDropdown && yearDropdownRef.current && !yearDropdownRef.current.contains(event.target)) {
        setShowYearDropdown(false);
      }
      if (showBranchDropdown && branchDropdownRef.current && !branchDropdownRef.current.contains(event.target)) {
        setShowBranchDropdown(false);
      }
      if (showTypeDropdown && typeDropdownRef.current && !typeDropdownRef.current.contains(event.target)) {
        setShowTypeDropdown(false);
      }
    }
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [showSemesterDropdown, showYearDropdown, showBranchDropdown, showTypeDropdown]);

  // Mock data for demonstration
  const mockPapers = [
    {
      id: 1,
      courseCode: 'CS201',
      courseName: 'Discrete Mathematics',
      professor: 'Dr. Himadri Nayak',
      semester: 'I',
      branch: 'CSE',
      year: '2023',
      examType: 'Mid Sem',
      fileUrl: '/path/to/pdf'
    },
    {
      id: 2,
      courseCode: 'CS202',
      courseName: 'Data Structures',
      professor: 'Dr. Anita Sharma',
      semester: 'I',
      branch: 'CSE',
      year: '2023',
      examType: 'End Sem',
      fileUrl: '/path/to/pdf'
    },
    {
      id: 4,
      courseCode: 'EC201',
      courseName: 'Circuit Analysis',
      professor: 'Dr. Priya Singh',
      semester: 'I',
      branch: 'ECE',
      year: '2023',
      examType: 'Mid Sem',
      fileUrl: '/path/to/pdf'
    },
    {
      id: 5,
      courseCode: 'ME201',
      courseName: 'Engineering Mechanics',
      professor: 'Dr. Suresh Patel',
      semester: 'I',
      branch: 'MAE',
      year: '2023',
      examType: 'End Sem',
      fileUrl: '/path/to/pdf'
    },
    // Add papers for different semesters/branches
    {
      id: 6,
      courseCode: 'CS301',
      courseName: 'Database Systems',
      professor: 'Dr. Neha Gupta',
      semester: 'II',
      branch: 'CSE',
      year: '2023',
      examType: 'Mid Sem',
      fileUrl: '/path/to/pdf'
    }
  ];

  // Fetch exam papers based on selection (using mock data)
  const fetchExamPapers = async () => {
    setLoading(true);
    try {
      // Simulate API delay
      await new Promise(resolve => setTimeout(resolve, 500));
      
      // Filter mock data based on selection
      const filteredPapers = mockPapers.filter(paper => 
        paper.semester === selectedSemester && 
        paper.branch === selectedBranch && 
        paper.year === selectedYear &&
        paper.examType === selectedExamType
      );
      setExamPapers(filteredPapers);
    } catch (error) {
      console.error('Error fetching exam papers:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchExamPapers();
  }, [selectedSemester, selectedBranch, selectedYear, selectedExamType]);

  const semesters = ['I', 'II', 'III', 'IV', 'V', 'VI', 'VII', 'VIII'];
  const branches = ['CSE', 'ECE', 'MAE', 'MNC'];
  const years = ['2023', '2024', '2025'];
  const examTypes = ['Mid Sem', 'End Sem'];

  const PaperCard = ({ paper }) => {
    const handleDownload = () => {
      // Simulate download - in real app, this would download the actual file
      alert(`Downloading ${paper.courseCode} - ${paper.courseName}`);
      // In a real app, you would use:
      // window.open(paper.fileUrl, '_blank');
    };

    return (
      <div 
        className="bg-gradient-to-br from-red-50 to-red-100 rounded-xl p-3 md:p-4 hover:shadow-lg transition-all duration-300 cursor-pointer hover:from-red-100 hover:to-red-200 border-2 border-red-300 hover:border-red-400 
                   flex sm:flex-row md:flex-col items-center md:justify-center gap-3 md:gap-0"
        onClick={handleDownload}
      >
        <div className="w-12 h-12 md:w-16 md:h-16 flex-shrink-0 md:mb-2 md:mb-3 bg-red-200 rounded-full flex items-center justify-center">
          {/* PDF Icon */}
          <svg className="w-6 h-6 md:w-8 md:h-8 text-red-600" fill="currentColor" viewBox="0 0 20 20">
            <path fillRule="evenodd" d="M4 4a2 2 0 012-2h4.586A2 2 0 0112 2.586L15.414 6A2 2 0 0116 7.414V16a2 2 0 01-2 2H6a2 2 0 01-2-2V4zm2 6a1 1 0 011-1h6a1 1 0 110 2H7a1 1 0 01-1-1zm1 3a1 1 0 100 2h6a1 1 0 100-2H7z" clipRule="evenodd" />
          </svg>
        </div>
        <div className="flex-1 md:text-center">
          <div className="flex items-center gap-2 mb-1 justify-start md:justify-center">
            <h3 className="font-bold text-sm md:text-base text-gray-800">{paper.courseCode}</h3>
          </div>
          <p className="text-xs md:text-sm text-gray-700 text-left md:text-center line-clamp-2 font-medium">{paper.courseName}</p>
          <p className="text-xs text-red-600 text-left md:text-center truncate font-medium mt-1">{paper.professor}</p>
        </div>
      </div>
    );
  };

  return (
    <main className="container mx-auto py-8 md:px-10 px-4">
      <div className="space-y-8 mr-0 md:mr-8">
        {/* Header and Selection Section */}
        <div className="flex flex-col lg:flex-row gap-8">
          {/* Header Card */}
          <div className="bg-gradient-to-br from-gray-900 via-gray-800 to-gray-700 text-white rounded-xl p-6 flex-shrink-0 lg:max-w-sm shadow-xl">
            <h1 className="text-3xl font-bold mb-4 bg-gradient-to-r from-orange-400 to-red-500 bg-clip-text text-transparent">
              Exam Vault
            </h1>
            <div className="border-t border-gray-600 pt-4 mb-4">
              <p className="text-sm text-gray-300 leading-relaxed">Browse all PYQs by choosing your</p>
              <p className="text-sm text-gray-300 leading-relaxed">Semester and Branch. Download</p>
              <p className="text-sm text-gray-300 leading-relaxed">in One click. One place for all.</p>
            </div>
            <button
              className="w-full bg-gradient-to-r from-orange-500 to-red-500 hover:from-orange-600 hover:to-red-600 text-white px-4 py-2.5 rounded-lg text-sm font-medium transition-all duration-300 transform hover:scale-105 shadow-lg"
              onClick={() => setShowUploadForm(true)}
            >
              Upload a resource
            </button>
          </div>
        {/* Upload Resource Popup Form */}
        {showUploadForm && (
          <div className="fixed inset-0 bg-black/70 flex items-center justify-center z-50 p-4">
            <div className="bg-white rounded-lg shadow-xl p-4 w-full max-w-md relative mx-2 sm:mx-0 overflow-y-auto" style={{ maxHeight: '95vh', minWidth: '0', width: '100%' }}>
              <button
                className="absolute top-3 right-3 text-gray-400 hover:text-gray-600"
                onClick={() => setShowUploadForm(false)}
                aria-label="Close"
              >
                <svg className="w-5 h-5" viewBox="0 0 20 20" fill="currentColor">
                  <path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd" />
                </svg>
              </button>
              <h2 className="text-lg font-semibold mb-4 text-gray-800">Upload Resource</h2>
              <form className="space-y-3">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <label className="block text-xs font-medium text-gray-600 mb-1">Subject Code</label>
                    <input
                      type="text"
                      className="w-full border border-gray-200 rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-100 focus:border-orange-400 min-w-0 mb-2"
                      value={uploadForm.subjectCode}
                      onChange={e => setUploadForm({ ...uploadForm, subjectCode: e.target.value })}
                    />
                  </div>
                  <div>
                    <label className="block text-xs font-medium text-gray-600 mb-1">Subject Name</label>
                    <input
                      type="text"
                      className="w-full border border-gray-200 rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-100 focus:border-orange-400 min-w-0 mb-2"
                      value={uploadForm.subjectName}
                      onChange={e => setUploadForm({ ...uploadForm, subjectName: e.target.value })}
                    />
                  </div>
                </div>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
                  <div>
                    <label className="block text-xs font-medium text-gray-600 mb-1">Professor Name</label>
                    <input
                      type="text"
                      className="w-full border border-gray-200 rounded-md px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-orange-100 focus:border-orange-400 min-w-0 mb-2"
                      value={uploadForm.professorName}
                      onChange={e => setUploadForm({ ...uploadForm, professorName: e.target.value })}
                    />
                  </div>
                  <div>
                    <label className="block text-xs font-medium text-gray-600 mb-1">Type</label>
                    <div className="relative" ref={typeDropdownRef}>
                      <button
                        type="button"
                        className="w-full flex items-center justify-between rounded-md border border-gray-200 px-3 py-2 text-sm bg-white focus:outline-none focus:ring-2 focus:ring-orange-100 focus:border-orange-400 mb-2"
                        onClick={() => setShowTypeDropdown((prev) => !prev)}
                        tabIndex={0}
                      >
                        <span>{uploadForm.type}</span>
                        <span className="flex items-center justify-center h-full">
                          <img src="/down.svg" alt="Dropdown arrow" className="w-2 h-2 text-gray-200" />
                        </span>
                      </button>
                      {showTypeDropdown && (
                        <div className="absolute left-0 right-0 mt-1 z-10 bg-white border border-gray-200 rounded-md shadow-lg">
                          {["Mid Sem", "End Sem"].map((type) => (
                            <button
                              key={type}
                              type="button"
                              className={`w-full text-left px-3 text-sm hover:bg-orange-100 rounded-md transition-colors ${uploadForm.type === type ? "bg-orange-50 font-semibold text-orange-700" : "text-gray-700"}`}
                              onClick={() => {
                                setUploadForm({ ...uploadForm, type });
                                setShowTypeDropdown(false);
                              }}
                            >
                              {type}
                            </button>
                          ))}
                        </div>
                      )}
                    </div>
                  </div>
                </div>
                <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
                  <div>
                    <label className="block text-xs font-medium text-gray-600 mb-1">Semester</label>
                    <div className="relative" ref={semesterDropdownRef}>
                      <button
                        type="button"
                        className="w-full flex items-center justify-between rounded-md border border-gray-200 px-3 py-1 text-sm bg-white focus:outline-none focus:ring-2 focus:ring-orange-100 focus:border-orange-400 mb-2"
                        onClick={() => setShowSemesterDropdown((prev) => !prev)}
                        tabIndex={0}
                      >
                        <span>{uploadForm.semester}</span>
                        <span className="flex items-center justify-center h-full">
                           <img src="/down.svg" alt="Dropdown arrow" className="w-2 h-2 text-gray-200" />
                        </span>
                      </button>
                      {showSemesterDropdown && (
                        <div className="absolute left-0 right-0 z-10 bg-white border border-gray-200 rounded-md shadow-lg">
                          {semesters.map((sem) => (
                            <button
                              key={sem}
                              type="button"
                              className={`w-full text-left px-3 text-sm hover:bg-orange-100 rounded-md transition-colors ${uploadForm.semester === sem ? "bg-orange-50 font-semibold text-orange-700" : "text-gray-700"}`}
                              onClick={() => {
                                setUploadForm({ ...uploadForm, semester: sem });
                                setShowSemesterDropdown(false);
                              }}
                            >
                              {sem}
                            </button>
                          ))}
                        </div>
                      )}
                    </div>
                  </div>
                  <div>
                    <label className="block text-xs font-medium text-gray-600 mb-1">Year</label>
                    <div className="relative" ref={yearDropdownRef}>
                      <button
                        type="button"
                        className="w-full flex items-center justify-between rounded-md border border-gray-200 px-3 py-1 text-sm bg-white focus:outline-none focus:ring-2 focus:ring-orange-100 focus:border-orange-400 mb-2"
                        onClick={() => setShowYearDropdown((prev) => !prev)}
                        tabIndex={0}
                      >
                        <span>{uploadForm.year}</span>
                        <span className="flex items-center justify-center h-full">
                           <img src="/down.svg" alt="Dropdown arrow" className="w-2 h-2 text-gray-200" />
                        </span>
                      </button>
                      {showYearDropdown && (
                        <div className="absolute left-0 right-0 z-10 bg-white border border-gray-200 rounded-md shadow-lg">
                          {["2021","2022","2023","2024"].map((year) => (
                            <button
                              key={year}
                              type="button"
                              className={`w-full text-left px-3 text-sm hover:bg-orange-100 rounded-md transition-colors ${uploadForm.year === year ? "bg-orange-50 font-semibold text-orange-700" : "text-gray-700"}`}
                              onClick={() => {
                                setUploadForm({ ...uploadForm, year });
                                setShowYearDropdown(false);
                              }}
                            >
                              {year}
                            </button>
                          ))}
                        </div>
                      )}
                    </div>
                  </div>
                  <div>
                    <label className="block text-xs font-medium text-gray-600 mb-1">Branch</label>
                    <div className="relative" ref={branchDropdownRef}>
                      <button
                        type="button"
                        className="w-full flex items-center justify-between rounded-md border border-gray-200 px-3 py-1 text-sm bg-white focus:outline-none focus:ring-2 focus:ring-orange-100 focus:border-orange-400 mb-2"
                        onClick={() => setShowBranchDropdown((prev) => !prev)}
                        tabIndex={0}
                      >
                        <span>{uploadForm.branch}</span>
                        <span className="flex items-center justify-center h-full">
                          <img src="/down.svg" alt="Dropdown arrow" className="w-2 h-2 text-gray-200" />
                        </span>
                      </button>
                      {showBranchDropdown && (
                        <div className="absolute left-0 right-0 z-10 bg-white border border-gray-200 rounded-md shadow-lg">
                          {["CSE","ECE","ME","CE","EE"].map((branch) => (
                            <button
                              key={branch}
                              type="button"
                              className={`w-full text-left px-3 text-sm hover:bg-orange-100 rounded-md transition-colors ${uploadForm.branch === branch ? "bg-orange-50 font-semibold text-orange-700" : "text-gray-700"}`}
                              onClick={() => {
                                setUploadForm({ ...uploadForm, branch });
                                setShowBranchDropdown(false);
                              }}
                            >
                              {branch}
                            </button>
                          ))}
                        </div>
                      )}
                    </div>
                  </div>
                </div>
                {/* File Upload Section */}
                <div className="mt-3">
                  <label className="block text-xs font-medium text-gray-600 mb-1">Upload File (PDF)</label>
                  <div className="w-full min-w-0 mb-2">
                    <FileDropZone onFileAccepted={setUploadFile} />
                  </div>
                </div>
                <button
                  type="button"
                  className="w-full bg-gradient-to-r from-orange-500 to-red-500 hover:from-orange-600 hover:to-red-600 text-white px-4 py-2.5 rounded-lg text-sm font-medium mt-2 transition-all duration-300 transform hover:scale-[1.02] shadow-lg"
                  onClick={() => {
                    if (!uploadFile) {
                      alert('Please upload a PDF file.');
                      return;
                    }
                    alert('Resource uploaded (mock)!');
                    setShowUploadForm(false);
                  }}
                >
                  Upload
                </button>
              </form>
            </div>
          </div>
        )}

          {/* Selection Interface */}
          <div className="flex-1 space-y-6">
            {/* Semester Selection */}
            <div>
              <div className="bg-gradient-to-r from-blue-400 to-purple-600 text-white px-4 py-2 rounded-lg inline-block mb-4">
                <h2 className="text-sm font-medium">Select Your Semester</h2>
              </div>
              <div className="flex flex-wrap gap-2">
                {semesters.map((sem) => (
                  <button
                    key={sem}
                    onClick={() => setSelectedSemester(sem)}
                    className={`px-3 py-2 md:px-4 md:py-2 border rounded transition-colors text-sm ${
                      selectedSemester === sem
                        ? 'bg-orange-500 text-white border-orange-500'
                        : 'bg-white text-gray-700 border-gray-300 hover:bg-orange-100'
                    }`}
                  >
                    {sem}
                  </button>
                ))}
              </div>
            </div>

            {/* Branch Selection */}
            <div>
              <div className="bg-gradient-to-r from-purple-500 to-pink-600 text-white px-4 py-2 rounded-lg inline-block mb-4">
                <h2 className="text-sm font-medium">Select Your Branch</h2>
              </div>
              <div className="flex flex-wrap gap-2">
                {branches.map((branch) => (
                  <button
                    key={branch}
                    onClick={() => setBranch(branch)}
                    className={`px-3 py-2 md:px-4 md:py-2 border rounded transition-colors text-sm ${
                      selectedBranch === branch
                        ? 'bg-orange-500 text-white border-orange-500'
                        : 'bg-white text-gray-700 border-gray-300 hover:bg-orange-100'
                    }`}
                  >
                    {branch}
                  </button>
                ))}
              </div>
            </div>

            {/* Exam Type Selection */}
            <div>
              <div className="bg-gradient-to-r from-green-500 to-blue-600 text-white px-4 py-2 rounded-lg inline-block mb-4">
                <h2 className="text-sm font-medium">Select Exam Type</h2>
              </div>
              <div className="flex flex-wrap gap-2">
                {examTypes.map((examType) => (
                  <button
                    key={examType}
                    onClick={() => setSelectedExamType(examType)}
                    className={`px-3 py-2 md:px-4 md:py-2 border rounded transition-colors text-sm ${
                      selectedExamType === examType
                        ? 'bg-orange-500 text-white border-orange-500'
                        : 'bg-white text-gray-700 border-gray-300 hover:bg-orange-100'
                    }`}
                  >
                    {examType}
                  </button>
                ))}
              </div>
            </div>
          </div>
        </div>

        {/* Current Selection Display */}
        <div className="flex flex-col sm:flex-row sm:justify-between sm:items-center gap-4">
          <div className="flex flex-col sm:flex-row gap-2 sm:gap-8 text-sm">
            <span><strong>Semester:</strong> {selectedSemester}</span>
            <span><strong>Branch:</strong> {selectedBranch}</span>
            <span><strong>Exam Type:</strong> {selectedExamType}</span>
          </div>
          <div className="flex items-center gap-2">
            <span className="text-sm"><strong>Year:</strong></span>
            <select 
              value={selectedYear}
              onChange={(e) => setSelectedYear(e.target.value)}
              className="border border-gray-300 rounded-lg px-3 py-1 text-sm "
            >
              {years.map((year) => (
                <option key={year} value={year}>{year}</option>
              ))}
            </select>
          </div>
        </div>

        {/* Exam Papers Grid */}
        {loading ? (
          <div className="flex justify-center items-center py-8">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-orange-500"></div>
          </div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 2xl:grid-cols-6 gap-3 md:gap-4">
            {examPapers.map((paper) => (
              <PaperCard key={paper.id} paper={paper} />
            ))}
            {/* Add more mock papers for demonstration */}
            {Array.from({ length: 6 }).map((_, index) => (
              <PaperCard 
                key={`mock-${index}`} 
                paper={{
                  id: `mock-${index}`,
                  courseCode: 'CS201',
                  courseName: 'Discrete Mathematics',
                  professor: 'Dr. Himadri Nayak'
                }} 
              />
            ))}
          </div>
        )}
      </div>
    </main>
  );
};

export default ExamVault;
