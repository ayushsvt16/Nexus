import React, { useState, useEffect } from 'react';

const ExamVault = () => {
  const [selectedSemester, setSelectedSemester] = useState('I');
  const [selectedBranch, setBranch] = useState('CSE');
  const [selectedYear, setSelectedYear] = useState('2023');
  const [selectedExamType, setSelectedExamType] = useState('Mid Sem');
  const [examPapers, setExamPapers] = useState([]);
  const [loading, setLoading] = useState(false);

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
      id: 3,
      courseCode: 'CS203',
      courseName: 'Programming Fundamentals',
      professor: 'Dr. Rajesh Kumar',
      semester: 'I',
      branch: 'CSE',
      year: '2023',
      examType: 'Class Test',
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
  const years = ['2023', '2022', '2021', '2020'];
  const examTypes = ['Mid Sem', 'End Sem', 'Class Test'];

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
            <button className="w-full bg-gradient-to-r from-orange-500 to-red-500 hover:from-orange-600 hover:to-red-600 text-white px-4 py-2.5 rounded-lg text-sm font-medium transition-all duration-300 transform hover:scale-105 shadow-lg">
              Request any resource
            </button>
          </div>

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
