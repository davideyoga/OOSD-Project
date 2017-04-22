package gamingplatform.view;


import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;


public class FreemarkerHelper {

    public static void process(String template_name, Map data, HttpServletResponse response, ServletContext servlet_context) throws IOException{

        response.setContentType("text/html;charset=ISO-8859-1");

        String path="/template";

        Configuration cfg = new Configuration(new Version("2.3.26"));

        cfg.setServletContextForTemplateLoading(servlet_context, path);

        Template template = cfg.getTemplate(template_name);

        PrintWriter out = response.getWriter();

        data.put("context",path);

        try{
            template.process(data, out);

        } catch (TemplateException ex) {
            System.out.println("Templating exception: "+ex.getMessage());
        } finally{
            out.flush();
            out.close();
        }

    }

}