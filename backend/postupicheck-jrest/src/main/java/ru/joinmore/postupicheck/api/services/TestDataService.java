package ru.joinmore.postupicheck.api.services;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.entities.*;
import ru.joinmore.postupicheck.api.repositories.*;

import java.util.*;

@Service
public class TestDataService {

    private final UniversityService universityService;
    private final SubjectService subjectService;
    private final StudentExamResultService studentExamResultService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final AdmissionService admissionService;
    private final StudentAdmissionService studentAdmissionService;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final UniversityRepository universityRepository;
    private final CourseRepository courseRepository;
    private final StudentExamResultRepository studentExamResultRepository;
    private final AdmissionRepository admissionRepository;

    public TestDataService(
            UniversityService universityService,
            SubjectService subjectService,
            StudentExamResultService studentExamResultService,
            StudentService studentService,
            CourseService courseService,
            AdmissionService admissionService,
            StudentAdmissionService studentAdmissionService,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository,
            UniversityRepository universityRepository,
            CourseRepository courseRepository,
            StudentExamResultRepository studentExamResultRepository,
            AdmissionRepository admissionRepository) {
        this.universityService = universityService;
        this.subjectService = subjectService;
        this.studentExamResultService = studentExamResultService;
        this.studentService = studentService;
        this.courseService = courseService;
        this.admissionService = admissionService;
        this.studentAdmissionService = studentAdmissionService;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.universityRepository = universityRepository;
        this.courseRepository = courseRepository;
        this.studentExamResultRepository = studentExamResultRepository;
        this.admissionRepository = admissionRepository;
    }

    public void createTestStudents() {
        List<Student> students = new ArrayList<>();

        for (int i = 1; i <= 10000; i++) {
            String snils = String.format("%011d", i);
            Student student = new Student("Иванов " + i, snils);
            students.add(student);
        }

        studentRepository.saveAll(students);
    }

    public void createTestUniversities() {
        List<University> universities = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            University university = new University("Государственный Университет " + i);
            universities.add(university);
        }

        universityRepository.saveAll(universities);
    }

    public void createTestStudentExamResults() {
        List<Student> students = studentService.getAll();
        List<StudentExamResult> allStudentExamResults = new ArrayList<>();

        students.forEach(student -> {
            List<StudentExamResult> createdStudentExamResults = createAllResultsForStudent(student);
            allStudentExamResults.addAll(createdStudentExamResults);
        });

        studentExamResultRepository.saveAll(allStudentExamResults);
    }

    public List<StudentExamResult> createAllResultsForStudent(Student student) {
        Random random = new Random();

        int ruPoints = random.nextInt(100) + 1;
        int mathPoints = random.nextInt(100) + 1;

        Subject firstSubject = subjectService.get(1);
        Subject secondSubject = subjectService.get(2);

        StudentExamResult studentExamResultMath = new StudentExamResult(mathPoints, student, firstSubject);
        StudentExamResult studentExamResultRu = new StudentExamResult(ruPoints, student, secondSubject);

        List<StudentExamResult> currentStudentExamResults = new ArrayList<>();
        currentStudentExamResults.add(studentExamResultMath);
        currentStudentExamResults.add(studentExamResultRu);

        Set<Subject> subjects = getRandomSubjects();

        subjects.forEach(subject -> {
            int points = random.nextInt(100) + 1;
            StudentExamResult studentExamResult = new StudentExamResult(points, student, subject);
            currentStudentExamResults.add(studentExamResult);
        });

        return currentStudentExamResults;
    }

    public void createTestCourses() {
        Random random = new Random();
        List<Course> allCourses = new ArrayList<>();
        List<University> universities = universityService.getAll();

        universities.forEach(university -> {
            int informaticsCoursesCount = random.nextInt(10) + 1;
            int physicsCoursesCount = random.nextInt(5) + 1;
            int socialScienceCoursesCount = random.nextInt(5) + 1;
            int biologyCoursesCount = random.nextInt(3) + 1;
            int chemistryCoursesCount = random.nextInt(3) + 1;

            int curPassingPoints = 0;

            Subject math = subjectService.findByName("Математика");
            Subject ru = subjectService.findByName("Русский язык");
            Subject informatics = subjectService.findByName("Информатика");
            Subject physics = subjectService.findByName("Физика");
            Subject socialScience = subjectService.findByName("Обществознание");
            Subject chemistry = subjectService.findByName("Химия");
            Subject biology = subjectService.findByName("Биология");

            int informaticsCodeNumber = 1;
            int physicsCodeNumber = 2;
            int socialScienceCodeNumber = 3;
            int chemistryCodeNumber = 6;
            int biologyCodeNumber = 5;

            String universityName = university.getName();

            int budgetPlaces1 = random.nextInt(100) + 1;
            List<Course> infCourses =
                    createTestCourse(
                            informaticsCoursesCount,
                    "Направление-Информатика",
                    universityName, informatics,
                    informaticsCodeNumber,
                    university,
                    math,
                    ru,
                    curPassingPoints,
                    budgetPlaces1);

            int budgetPlaces2 = random.nextInt(100) + 1;
            List<Course> physCourses =
                    createTestCourse(
                            physicsCoursesCount,
                    "Направление-Физика",
                    universityName, physics,
                    physicsCodeNumber,
                    university,
                    math,
                    ru,
                    curPassingPoints,
                    budgetPlaces2);

            int budgetPlaces3 = random.nextInt(100) + 1;
            List<Course> ssCourses =
                    createTestCourse(
                            socialScienceCoursesCount,
                    "Направление-Обществознание",
                    universityName, socialScience,
                    socialScienceCodeNumber,
                    university,
                    math,
                    ru,
                    curPassingPoints,
                    budgetPlaces3);

            int budgetPlaces4 = random.nextInt(100) + 1;
            List<Course> chemistryCourses =
            createTestCourse(
                    chemistryCoursesCount,
                    "Направление-Химия",
                    universityName, chemistry,
                    chemistryCodeNumber,
                    university,
                    math,
                    ru,
                    curPassingPoints,
                    budgetPlaces4);

            int budgetPlaces5 = random.nextInt(100) + 1;
            List<Course> biologyCourses =
            createTestCourse(
                    biologyCoursesCount,
                    "Направление-Биология",
                    universityName, biology,
                    biologyCodeNumber,
                    university,
                    math,
                    ru,
                    curPassingPoints,
                    budgetPlaces5);

            allCourses.addAll(infCourses);
            allCourses.addAll(physCourses);
            allCourses.addAll(ssCourses);
            allCourses.addAll(chemistryCourses);
            allCourses.addAll(biologyCourses);
        });

        courseRepository.saveAll(allCourses);
    }

    private List<Course> createTestCourse(
            int coursesCount,
            String courseName,
            String universityName,
            Subject thirdSubject,
            int codeNumber,
            University university,
            Subject math,
            Subject ru,
            int curPassingPoints,
            int budgetPlaces) {
        List<Course> allCourses = new ArrayList<>();

        for (int i = 1; i <= coursesCount;i++) {
            String name = String.format("%s %d в %s", courseName, i, universityName);
            String code = "код " + codeNumber;
            Course course = new Course(name, code, university, math, ru, thirdSubject, curPassingPoints, budgetPlaces);
            allCourses.add(course);
        }

        return allCourses;
    }

    public void createTestAdmissions() {
        Random random = new Random();
        List<Student> students = studentService.getAll();
        List<Admission> allAdmissions = new ArrayList<>();

        students.forEach(student -> {
            int universityAdmissionsCount = random.nextInt(5) + 1;

            for (int i=1; i <= universityAdmissionsCount; i++) {
                allAdmissions.addAll(chooseUniversityThenCreateAdmissions(random, student));
            }

        });

        admissionRepository.saveAll(allAdmissions);
    }

    private List<Admission> chooseCoursesThenCreateAdmissions(Random random, University randomUniversity, Student student) {
        Subject math = subjectService.get(1);
        Subject ru = subjectService.get(2);
        int admissionsInUniversityCount = random.nextInt(5) + 1;
        // Выбираем сколько студент подаст заявлений в данный уник
        // подаем столько заявлений сколько рассчитали выше

        return new ArrayList<>(createAdmissionsWithCourses(student, math, ru, admissionsInUniversityCount, randomUniversity));
    }

    public void createSubjects() {
        List<Subject> allSubjects = new ArrayList<>();
        Subject math = new Subject("Математика");
        Subject ru = new Subject("Русский язык");
        Subject inf = new Subject("Информатика");
        Subject phys = new Subject("Физика");
        Subject biology = new Subject("Биология");
        Subject chemistry = new Subject("Химия");
        Subject socialScience = new Subject("Обществознание");

        allSubjects.add(math);
        allSubjects.add(ru);
        allSubjects.add(inf);
        allSubjects.add(phys);
        allSubjects.add(biology);
        allSubjects.add(chemistry);
        allSubjects.add(socialScience);

        subjectRepository.saveAll(allSubjects);
    }

    private List<Admission> createAdmissionsWithCourses(
            Student student,
            Subject math,
            Subject ru,
            int admissionsInUniversityCount,
            University university) {
        String mathName = math.getName();
        String ruName = ru.getName();

        List<Subject> passedSubjects = new ArrayList<>();
        List<StudentExamResult> studentExamResults = studentExamResultService.getAllStudentResults(student);

        studentExamResults.forEach(studentExamResult -> {
            Subject subject = studentExamResult.getSubject();
            String subjectName = subject.getName();
            if (!mathName.equals(subjectName) && !ruName.equals(subjectName)) {
                passedSubjects.add(subject);
            }
        });

        List<Course> availableCourses = new ArrayList<>();

        passedSubjects
                .forEach(subject -> {
                    List<Course> subjectCourses =
                            courseService
                                    .findCoursesByUniversityAndThirdSubject(university, subject);
                    availableCourses.addAll(subjectCourses)
                    ;
                });

        List<Admission> allAdmissions = new ArrayList<>();
        // Костыльно берем результат экзамена НЕ математики и НЕ русского и создаем заявления
        for (int i = 1; i <= admissionsInUniversityCount; i++) {
            if (availableCourses.size() > 0) {
                Collections.shuffle(availableCourses);
                Course course = availableCourses.get(0);
                int points = 0;
                Admission admission = new Admission(student, course, points);
                allAdmissions.add(admission);
                availableCourses.remove(course);
            }
        }

        return allAdmissions;
    }

    public void setRandomBudgetPlacesForAllCourses() {
        Random random = new Random();
        List<Course> allCourses = courseService.getAll();

        allCourses
                .forEach(course -> {
                    long id = course.getId();
                    int budgetPlaces = random.nextInt(100) + 1;
                    course.setBudgetPlaces(budgetPlaces);
                    courseService.replace(course, id);
                });
    }

    public void setPointsForAllAdmissions() {
        List<Admission> allAdmissions = admissionService.getAll();

        allAdmissions.forEach(admission -> {
            Course admissionCourse = admission.getCourse();
            Student admissionStudent = admission.getStudent();
            int points = studentAdmissionService.getStudentAdmissionPoints(admissionStudent, admissionCourse);
            admission.setPoints(points);
        });

        admissionService.saveAll(allAdmissions);
    }

    private List<Admission> chooseUniversityThenCreateAdmissions(Random random, Student student) {
        // Берем рандомный уник
        int id = random.nextInt(100) + 1;
        University randomUniversity = universityService.get(id);
        // Берем все заявления из данного уника
        List<Admission> admissions = admissionService.findAdmissionsByStudentAndCourseUniversity(student, randomUniversity);
        // Проверяем что он уже туда не подавал
        List<Admission> allAdmissions = new ArrayList<>();

        if (admissions.size() == 0) {
            allAdmissions.addAll(chooseCoursesThenCreateAdmissions(random, randomUniversity, student));
        } else {
            chooseUniversityThenCreateAdmissions(random, student);
        }

        return allAdmissions;
    }

    private Set<Subject> getRandomSubjects() {
        Set<Subject> subjects = new HashSet<>();
        Random random = new Random();

        int informaticsChance = random.nextInt(100) + 1;
        if (informaticsChance <= 60) {
            subjects.add(subjectService.get(3));
        }

        int physicsChance = random.nextInt(100) + 1;
        if (physicsChance <= 30) {
            subjects.add(subjectService.get(4));
        }

        int biologyChance = random.nextInt(100) + 1;
        if (biologyChance <= 10) {
            subjects.add(subjectService.get(5));
        }

        int chemistryChance = random.nextInt(100) + 1;
        if (chemistryChance <= 10) {
            subjects.add(subjectService.get(6));
        }

        int socialScienceChance = random.nextInt(100) + 1;
        if (socialScienceChance <= 30) {
            subjects.add(subjectService.get(7));
        }

        return subjects;
    }
}
