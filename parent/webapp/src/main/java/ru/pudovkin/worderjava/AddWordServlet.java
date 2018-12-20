package ru.pudovkin.worderjava;



import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet class for handling "/addword" page
 */
@WebServlet(name = "AddWordServlet",urlPatterns ="/addword")
public class AddWordServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(AddWordServlet.class);

     /**
     * Process data, received from the input boxes.
     * If data is correct, creates new row in database with this data.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url="/addword.jsp";

        // get current action
        String action = request.getParameter("action");
        if (action==null){
            action="join"; // default action
        }

        // perform action and set URL
        if (action.equals("join")){
            url = "/addword.jsp";
        }
        else if (action.equals("add")){
            logger.info("Выполняется POST запрос на странице /addword.");
            // get parameters from request
            String word = request.getParameter("word");
            String translation = request.getParameter("translation");

            // store data in wordTranslation obj
            WordTranslation wordTranslation= new WordTranslation(word,translation);

            // validate parameters
            String message;
            logger.info("Выполняется проверка параметров запроса.");
            if (word==null||translation==null||word.isEmpty()||translation.isEmpty()){
                logger.info("Какое то из полей оказалось пустым.");
                message= "Please fill out all text boxes";
                url="/addword.jsp";
            }
            else {
                if (WordDao.wordExists(word) ){
                    message="This word already in list. Please try another.";
                    url="/addword.jsp";
                    logger.info("Слово - "+word+" уже есть в словаре.");
                }
                else {
                    message = "Thanks for adding word, try more!";
                    url="/index.jsp";
                    WordDao.create(wordTranslation);
                    logger.info("Перевод - "+wordTranslation+" успещно добавлен");
                }


            }

            request.setAttribute("wordTranslation",wordTranslation);
            request.setAttribute("message",message);
        }


        getServletContext().getRequestDispatcher(url).forward(request,response);
    }



}