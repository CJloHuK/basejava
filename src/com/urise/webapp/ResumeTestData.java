package com.urise.webapp;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;

import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = getTestResume();
        System.out.println(resume.getFullName());
        System.out.println();
        Map<ContactType, String> contacts = resume.getContacts();
        for (ContactType contactType : ContactType.values()) {
            String str = contacts.get(contactType);
            if (str != null) {
                System.out.println(contactType.getTitle() + ": " + str);
            }
        }
        System.out.println();
        Map<SectionType, Section> sections = resume.getSections();
        for (SectionType sectionType : SectionType.values()) {
            Section section = sections.get(sectionType);
            if (section != null) {
                System.out.println(sectionType.getTitle());
                System.out.println(section);
                System.out.println();
            }
        }
    }

    public static Resume getTestResume() {
        Resume resume = new Resume("uuid1", "Григорий Кислин");

//        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
//        resume.addContact(ContactType.SKYPE, "grigory.kislin");
//        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
//        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
//        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
//        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
//        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");
//
//        TextSection textSection = new TextSection();
//        textSection.setDescription("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
//        resume.addSection(SectionType.OBJECTIVE, textSection);
//        textSection = new TextSection();
//        textSection.setDescription("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
//        resume.addSection(SectionType.PERSONAL, textSection);
//        TextListSection textListSection = new TextListSection();
//        List<String> descriptionList = textListSection.getDescriptionList();
//        descriptionList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
//        descriptionList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
//        descriptionList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
//        descriptionList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
//        descriptionList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
//        descriptionList.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
//        resume.addSection(SectionType.ACHIEVEMENT, textListSection);
//        textListSection = new TextListSection();
//        descriptionList = textListSection.getDescriptionList();
//        descriptionList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
//        descriptionList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
//        descriptionList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
//        descriptionList.add("MySQL, SQLite, MS SQL, HSQLDB");
//        descriptionList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
//        descriptionList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
//        descriptionList.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
//        descriptionList.add("Python: Django.");
//        descriptionList.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
//        descriptionList.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
//        descriptionList.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
//        descriptionList.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
//        descriptionList.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
//        descriptionList.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
//        descriptionList.add("Родной русский, английский \"upper intermediate\"");
//        resume.addSection(SectionType.QUALIFICATIONS, textListSection);
//        WorkSection workSection = new WorkSection();
//        List<Work> workList = workSection.getWorkList();
//        Work work = new Work("Java Online Projects");
//        work.addDescription(new Work.WorkDescription(2013, Month.OCTOBER, "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
//        workList.add(work);
//        work = new Work("Wrike");
//        work.addDescription(new Work.WorkDescription(2014, Month.OCTOBER, 2016, Month.JANUARY, "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
//        workList.add(work);
//        work = new Work("RIT Center");
//        work.addDescription(new Work.WorkDescription(2012, Month.APRIL, 2014, Month.OCTOBER, "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
//        workList.add(work);
//        work = new Work("Luxoft (Deutsche Bank)");
//        work.addDescription(new Work.WorkDescription(2010, Month.DECEMBER, 2012, Month.APRIL, "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
//        workList.add(work);
//        work = new Work("Yota");
//        work.addDescription(new Work.WorkDescription(2008, Month.JUNE, 2010, Month.DECEMBER, "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
//        workList.add(work);
//        work = new Work("Enkata");
//        work.addDescription(new Work.WorkDescription(2007, Month.MARCH, 2008, Month.JUNE, "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
//        workList.add(work);
//        work = new Work("Siemens AG");
//        work.addDescription(new Work.WorkDescription(2005, Month.JANUARY, 2007, Month.FEBRUARY, "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
//        workList.add(work);
//        work = new Work("Alcatel");
//        work.addDescription(new Work.WorkDescription(1997, Month.SEPTEMBER, 2005, Month.JANUARY, "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
//        workList.add(work);
//        resume.addSection(SectionType.EXPERIENCE, workSection);
//        workSection = new WorkSection();
//        workList = workSection.getWorkList();
//        work = new Work("Coursera");
//        work.addDescription(new Work.WorkDescription(2013, Month.MARCH, 2013, Month.MAY, "\"Functional Programming Principles in Scala\" by Martin Odersky", null));
//        workList.add(work);
//        work = new Work("Luxoft");
//        work.addDescription(new Work.WorkDescription(2011, Month.MARCH, 2011, Month.APRIL, "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null));
//        workList.add(work);
//        work = new Work("Siemens AG");
//        work.addDescription(new Work.WorkDescription(2005, Month.JANUARY, 2005, Month.APRIL, "3 месяца обучения мобильным IN сетям (Берлин)", null));
//        workList.add(work);
//        work = new Work("Alcatel");
//        work.addDescription(new Work.WorkDescription(1997, Month.SEPTEMBER, 1998, Month.MARCH, "6 месяцев обучения цифровым телефонным сетям (Москва)", null));
//        workList.add(work);
//        work = new Work("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики");
//        work.addDescription(new Work.WorkDescription(1993, Month.SEPTEMBER, 1996, Month.JULY, "Аспирантура (программист С, С++)", null));
//        work.addDescription(new Work.WorkDescription(1987, Month.SEPTEMBER, 1993, Month.JULY, "Инженер (программист Fortran, C)", null));
//        workList.add(work);
//        work = new Work("Заочная физико-техническая школа при МФТИ");
//        work.addDescription(new Work.WorkDescription(1984, Month.SEPTEMBER, 1987, Month.JUNE, "Закончил с отличием", null));
//        workList.add(work);
//        resume.addSection(SectionType.EDUCATION, workSection);
        return resume;
    }
}
