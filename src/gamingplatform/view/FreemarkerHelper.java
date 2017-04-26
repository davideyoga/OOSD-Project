package gamingplatform.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import freemarker.template.*;


public class FreemarkerHelper {


    public static void process(String template_name, Map<String, Object> data, HttpServletResponse response, ServletContext servlet_context) {

        //path alla cartella contenente i template
        String path = "/template";
        //aggiungo path come prefisso per l'importazione di css e js
        data.put("context", path);

        //setto il tipo del contenuto di ritorno
        response.setContentType("text/html; charset=UTF-8");

        //ottengo oggetto cfg dal singleton
        Configuration cfg = FreemarkerCfg.INSTANCE.init(servlet_context, path);

        //creo oggetto template
        Template template;

        PrintWriter out = null;

        try {

            template = cfg.getTemplate(template_name);

            //ottengo lo stream della risposta
            out = response.getWriter();

            //processo la template con la Map
            template.process(data, out);

        } catch (TemplateException | IOException ex) {

            //log dei dettagli dell'eccezione
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "Templating exception: " + ex.getMessage(), ex);

        } finally {

            //provo a comletare il processamento a prescindere
            assert out != null;

            out.flush();
            out.close();
        }

    }

}


