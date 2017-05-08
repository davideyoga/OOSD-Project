package gamingplatform.view;

import javax.servlet.ServletContext;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;


public enum SingletonFreemarkerCfg {

    INSTANCE;

    private Configuration cfg = null;

    SingletonFreemarkerCfg() {}

    public Configuration init(ServletContext servlet_context, String path) {

        if (cfg == null) {
            //istanzio oggetto configuration
            cfg = new Configuration(new Version("2.3.26"));

            //default encoding
            cfg.setDefaultEncoding("UTF-8");

            //fornisco il contesto della servlet
            cfg.setServletContextForTemplateLoading(servlet_context, path);

            // definisce come gli errori devono essere mostrati
            // durante il  *development* è consigliato TemplateExceptionHandler.HTML_DEBUG_HANDLER,
            // in fase di deploy usare RETHROW_HANDLER
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

            // non logga eccezioni dentro FreeMarker che sarebbero comunque rilanciate
            cfg.setLogTemplateExceptions(false);

        }

        return cfg;
    }
}
