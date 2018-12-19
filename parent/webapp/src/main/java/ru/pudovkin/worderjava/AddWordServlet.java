package ru.pudovkin.worderjava;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "AddWordServlet",urlPatterns ="/addword")
public class AddWordServlet extends HttpServlet {

    /**
     * Determines post request.
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

            // get parameters from request
            String word = request.getParameter("word");
            String translation = request.getParameter("translation");
            System.out.println(translation);

            // store data in wordTranslation obj
            WordTranslation wordTranslation= new WordTranslation(word,translation);

            // validate parameters
            String message;
            if (word==null||translation==null||word.isEmpty()||translation.isEmpty()){

                message= "Please fill out all text boxes";
                url="/addword.jsp";
            }
            else {
                if (WordDao.wordExists(word) ){
                    message="This word already in list. Please try another.";
                    url="/addword.jsp";
                }
                else {
                    message = "Thanks for adding word, try more!";
                    url="/index.jsp";
                    WordDao.create(wordTranslation);
                }


            }

            request.setAttribute("wordTranslation",wordTranslation);
            request.setAttribute("message",message);
        }


        getServletContext().getRequestDispatcher(url).forward(request,response);
    }


}