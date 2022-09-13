TODO
========================

1. Подключить актуатор (spring boot starter)

2. Подключить https://github.com/codecentric/spring-boot-admin (optional)

3. Liquibase (optional)

4. Подумать над реализацией когда у курса > 3 предметов для поступления

   1. Сделать нормализацию данных для Course (required Subjcets) убрать поля
      first-second-third Subject и добавить таблицу CourseRequiredSubjects
      (id, course_id - foreignkey на courseId, requiredSubjectId - foreignkey на subjectId)
