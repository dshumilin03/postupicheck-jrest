package ru.joinmore.postupicheck.api.services;

import org.springframework.stereotype.Service;
import ru.joinmore.postupicheck.api.entities.*;

import java.util.*;

@Service
public class TestDataService {

    private final UniversityService universityService;
    private final SubjectService subjectService;
    private final StudentExamResultService studentExamResultService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final AdmissionService admissionService;

    public TestDataService(UniversityService universityService, SubjectService subjectService, StudentExamResultService studentExamResultService, StudentService studentService, CourseService courseService, AdmissionService admissionService) {
        this.universityService = universityService;
        this.subjectService = subjectService;
        this.studentExamResultService = studentExamResultService;
        this.studentService = studentService;
        this.courseService = courseService;
        this.admissionService = admissionService;
    }

    public void createTestStudents() {
        for (int i = 1; i < 10000; i++) {
            String snils = String.format("%011d", i);
            Student student = new Student("Кадыров" + i, snils);
            studentService.create(student);
        }
    }

    public void createTestUniversities() {
        for (int i = 1; i <= 100; i++) {
            University university = new University("Чеченский Государственный Университет " + i);
            universityService.create(university);
        }
    }

    public void createTestStudentExamResults() {
        List<Student> students = studentService.getAll();
        students.forEach(this::createAllResultsForStudent);
    }

    public void createAllResultsForStudent(Student student) {
        Random random = new Random();
        int ruPoints = random.nextInt(100) + 1;
        int mathPoints = random.nextInt(100) + 1;
        Subject firstSubject = subjectService.get(1);
        Subject secondSubject = subjectService.get(2);
        Set<Subject> subjects = getRandomSubjects();
        StudentExamResult studentExamResultMath = new StudentExamResult(mathPoints, student, firstSubject);
        StudentExamResult studentExamResultRu = new StudentExamResult(ruPoints, student, secondSubject);
        studentExamResultService.create(studentExamResultMath);
        studentExamResultService.create(studentExamResultRu);

        subjects.forEach(subject -> {
            int points = random.nextInt(100) + 1;
            StudentExamResult studentExamResult = new StudentExamResult(points, student, subject);
            studentExamResultService.create(studentExamResult);
        });

    }

    public void createTestCourses() {
        Random random = new Random();
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

            createTestCourse(informaticsCoursesCount,
                    "Ахматология-Инфа",
                    universityName, informatics,
                    informaticsCodeNumber,
                    university,
                    math,
                    ru,
                    curPassingPoints);

            createTestCourse(physicsCoursesCount,
                    "Ахматология-Физика",
                    universityName, physics,
                    physicsCodeNumber,
                    university,
                    math,
                    ru,
                    curPassingPoints);
            createTestCourse(socialScienceCoursesCount,
                    "Ахматология-Общага",
                    universityName, socialScience,
                    socialScienceCodeNumber,
                    university,
                    math,
                    ru,
                    curPassingPoints);
            createTestCourse(chemistryCoursesCount,
                    "Ахматология-Химия",
                    universityName, chemistry,
                    chemistryCodeNumber,
                    university,
                    math,
                    ru,
                    curPassingPoints);
            createTestCourse(biologyCoursesCount,
                    "Ахматология-Биология",
                    universityName, biology,
                    biologyCodeNumber,
                    university,
                    math,
                    ru,
                    curPassingPoints);

        });

    }

    private void createTestCourse(int coursesCount,
                             String courseName,
                             String universityName,
                             Subject thirdSubject,
                             int codeNumber,
                             University university,
                             Subject math,
                             Subject ru,
                             int curPassingPoints
                             ) {

        for (int i = 1; i <= coursesCount;i++) {
            String name = String.format("%s %d в %s", courseName, i, universityName);
            String code = "код " + codeNumber;
            Course course = new Course(name, code, university, math, ru, thirdSubject, curPassingPoints);
            courseService.create(course);
        }
    }

    public void createTestAdmissions() {
        Random random = new Random();
        List<Student> students = studentService.getAll();

        students.forEach(student -> {

            int universityAdmissionsCount = random.nextInt(5) + 1;

            for (int i=1; i <= universityAdmissionsCount; i++) {
                chooseUniversityThenCreateAdmissions(random, student);
            }

        });
    }

    private void chooseCoursesThenCreateAdmissions(Random random, University randomUniversity, Student student) {
        Subject math = subjectService.get(1);
        Subject ru = subjectService.get(2);
        int admissionsInUniversityCount = random.nextInt(5) + 1;
        // выбираем сколько студент подаст заявлений в данный уник
        // подаем столько заявлений сколько рассчитали выше
        createAdmissionsWithCourses(student, math, ru, admissionsInUniversityCount, randomUniversity);

    }

    private void createAdmissionsWithCourses(Student student, Subject math, Subject ru, int admissionsInUniversityCount, University university) {

        List<Subject> passedSubjects = new ArrayList<>();
        List<StudentExamResult> studentExamResults = studentExamResultService.
                findStudentExamResultsByStudent(student);

        String mathName = math.getName();
        String ruName = ru.getName();
        studentExamResults.forEach(studentExamResult -> {
            Subject subject = studentExamResult.getSubject();
            String subjectName = subject.getName();
            if (!mathName.equals(subjectName) && !ruName.equals(subjectName)) {
                passedSubjects.add(subject);
            }
        });

        List<Course> availableCourses = new ArrayList<>();

        passedSubjects.forEach(subject -> {
            List<Course> subjectCourses = courseService.findCoursesByUniversityAndThirdSubject(university, subject);
            availableCourses.addAll(subjectCourses);
        });

        //костыльно берем результат экзамена НЕ матеши и НЕ русского и создаем заявления
        for (int i = 1; i <= admissionsInUniversityCount; i++) {

            if (availableCourses.size() > 0) {
                Collections.shuffle(availableCourses);
                Course course = availableCourses.get(0);
                Admission admission = new Admission(student, course);
                admissionService.create(admission);
                availableCourses.remove(course);

            }

        }

    }


    private void chooseUniversityThenCreateAdmissions(Random random, Student student) {
        //Берем рандомный уник
        int id = random.nextInt(100) + 1;
        University randomUniversity = universityService.get(id);
        List<Admission> admissions = admissionService.findAdmissionsByStudentAndCourse_University(student, randomUniversity);
        //проверяем что он уже туда не подавал
        if (admissions.size() == 0) {
            chooseCoursesThenCreateAdmissions(random, randomUniversity, student);

        } else {
            chooseUniversityThenCreateAdmissions(random, student);
        }
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
