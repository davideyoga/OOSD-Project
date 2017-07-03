package gamingplatform.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import freemarker.template.*;

/**
 * classe utilizzata per processare le template freemarker
 */
public class FreemarkerHelper {

    /**
     * prepara l'ambiente di Freemarker e processa della template
     * @param template_name nome della template
     * @param data Map contenente i dati da iniettare nella template
     * @param response risposta servlet
     * @param servlet_context contesto della servlet
     */
    public static void process(String template_name, Map<String, Object> data, HttpServletResponse response, ServletContext servlet_context) {

        //path alla cartella contenente i template
        String path = "/template";
        //aggiungo path come prefisso per l'importazione di css e js
        data.put("context", path);

        //setto il tipo del contenuto di ritorno
        response.setContentType("text/html; charset=UTF-8");

        //ottengo oggetto cfg dal singleton
        Configuration cfg = SingletonFreemarkerCfg.INSTANCE.init(servlet_context, path);

        //creo oggetto template
        Template template;

        PrintWriter out = null;

        try {

            template = cfg.getTemplate(template_name);

            //ottengo lo stream della risposta
            out = response.getWriter();

            //processo la template con la Map
            template.process(data, out);

            //esplicito pulizia da garbage collection
            data=null;

        } catch (TemplateException | IOException ex) {

            //log dei dettagli dell'eccezione
            Logger.getAnonymousLogger().log(Level.SEVERE, "Templating exception: " + ex.getMessage());

        } finally {

            //provo a comletare il processamento a prescindere
            assert out != null;

            out.flush();
            out.close();
        }

    }

}


