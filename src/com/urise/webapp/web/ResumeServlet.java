package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private Storage sqlStorage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        //response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');
        Resume r = null;
        if (uuid != null) {
            r = sqlStorage.get(uuid);
        }
        String tittle = uuid == null ? "List of resumes" : "Resume of " + r.getFullName();
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>" + tittle + "</title>");
        writer.println("</head>");
        writer.println("<body>");
        if (uuid == null) {
            printResumeList(writer, sqlStorage);
        } else {
            printResume(writer, r);
        }
        writer.println("</body>");
        writer.println("</html>");
    }

    private void printResumeList(PrintWriter pw, Storage storage) {
        pw.println("<table border>");
        pw.println("<tr>");
        pw.println("<th>UUID</th>");
        pw.println("<th>Name</th>");
        pw.println("</tr>");
        List<Resume> list = storage.getAllSorted();
        for (Resume r : list) {
            pw.println("<tr>");
            pw.println("<td><a href =resume?uuid=" + r.getUuid() + ">" + r.getUuid() + "</td>");
            pw.println("<td>" + r.getFullName() + "</td>");
            pw.println("</tr>");
        }
        pw.println("</table>");
    }

    private void printResume(PrintWriter pw, Resume r) {
        pw.println("<h1>" + r.getFullName() + "</h1>");
        pw.println("<br>");
        Map<ContactType, String> map = r.getContacts();
        if(!map.isEmpty()) {
            pw.println("<h31>Contacts</h3>");

            pw.println("<table border>");
            pw.println("<tr>");
            pw.println("<th>Contact type</th>");
            pw.println("<th>Contact</th>");
            pw.println("</tr>");

            for (Map.Entry<ContactType, String> entry : map.entrySet()) {
                pw.println("<tr>");
                pw.println("<td>" + entry.getKey().getTitle() + "</td>");
                pw.println("<td>" + entry.getValue() + "</td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
            pw.println("<br>");
        }
        Map<SectionType, Section> sectionMap = r.getSections();
        if(!map.isEmpty()) {
            pw.println("<h31>Sections</h3>");

            pw.println("<table border>");
            pw.println("<tr>");
            pw.println("<th>Section type</th>");
            pw.println("<th>Section</th>");
            pw.println("</tr>");

            for (Map.Entry<SectionType, Section> entry : sectionMap.entrySet()) {
                pw.println("<tr>");
                pw.println("<td>" + entry.getKey().getTitle() + "</td>");
                pw.println("<td>" + entry.getValue().toString().replaceAll(System.lineSeparator(), "<br>") + "</td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
            pw.println("<br>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
