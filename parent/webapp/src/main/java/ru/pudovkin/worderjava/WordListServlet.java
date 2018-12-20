package ru.pudovkin.worderjava;


import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet class for handling /wordlist page.
 */
@WebServlet(name="WordListServlet",urlPatterns = {"/wordlist"})
public class WordListServlet extends HttpServlet {
    private static Logger logger=Logger.getLogger(WordListServlet.class);

    /**
     * Collect all rows from DB and send them as attribute to show on page.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        logger.info("Выполняется запрос на получение всех данных из БД.");
        List<WordTranslation> wordList =WordDao.readAll();
        request.setAttribute("wordList",wordList);
        getServletContext().getRequestDispatcher("/wordlist.jsp").forward(request,response);
        logger.info("Данные успешно получены.");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
       doPost(request,response);
    }
}
