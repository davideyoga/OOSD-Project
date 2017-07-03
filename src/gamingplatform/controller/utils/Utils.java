package gamingplatform.controller.utils;

import gamingplatform.dao.implementation.LevelDaoImpl;
import gamingplatform.dao.implementation.UserDaoImpl;
import gamingplatform.dao.implementation.UserLevelDaoImpl;
import gamingplatform.dao.interfaces.LevelDao;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.dao.interfaces.UserLevelDao;
import gamingplatform.model.Level;
import gamingplatform.model.User;
import gamingplatform.model.UserLevel;
import org.apache.commons.io.FileUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static gamingplatform.controller.utils.SecurityLayer.addSlashes;
import static java.util.Objects.*;

/**
 * contiene metodi utility di appoggio all'applicativo
 */
public class Utils {

    @Resource(name = "jdbc/gamingplatform")
    private static DataSource ds;

    /**
     * dato un filePart (file proveniente da una form), controlla se il file è un .jpg o .png
     * se si lo copia nella directory specificata e torna il nome del file salvato
     *
     * @param filePart  oggetto Part contenente dati del file
     * @param directory directory in cui verrà salvto il file
     * @return il nome del file salvato, oppure null su errore
     */
    public static String fileUpload(Part filePart, String directory, ServletContext svc) {

        String fileName = addSlashes(Paths.get(filePart.getSubmittedFileName()).getFileName().toString());

        //se il file è null o vuoto imposto il file di default
        if (isNull(fileName) || fileName.equals("")) {
            return "default.png";
        }

        //se non è un .jpg oppure un .png
        if (!(fileName.substring(fileName.length() - 4).equals(".jpg") ||
                fileName.substring(fileName.length() - 4).equals(".png"))) {

            Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[Utils] fileUpload fallito, formato non corretto: " + fileName + " - formato: " + fileName.substring(fileName.length() - 4));
            return null;
        }

        try {
            //aggiungo il tempo in millisecondi prima del nome (così mantengo il form) per evitare conflitti
            fileName = System.currentTimeMillis() + fileName;
            InputStream fileContent = filePart.getInputStream();
            //salvo il file nella directory specificata
            File targetFile = new File(svc.getRealPath("template/" + directory + "/" + fileName));
            FileUtils.copyInputStreamToFile(fileContent, targetFile);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[Utils] fileUpload IOException " + e.getMessage());
            return null;
        }

        return fileName;
    }


    /**
     * torna la lista degli attributi di una classe
     *
     * @param o classe
     * @return lista degli attributi della classe
     */
    public static List<String> getClassFields(Object o) {
        Class<?> clazz = o.getClass();
        List<String> list = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals("password")) {
                continue;
            }
            list.add(field.getName());
        }
        return list;
    }

    /**
     * ritorna l'ultimo segmento della url passata
     *
     * @param url url da analizzare
     * @return ultimo segmento
     */
    public static String getLastBitFromUrl(final String url) {
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }


    /**
     * ritorna l'n-ultimo segmento della url passata
     * es. /terzultimo/penultimo/ultimo con n=1 torna "penultimo"
     *
     * @param url url da analizzare
     * @param n   indica l'elemento da tornare
     * @return n-ultimo segmento
     */
    public static String getNlastBitFromUrl(final String url, int n) {

        String segment = "";
        int length = 0;

        for (int i = 0; i <= n; i++) {
            //System.out.println("passata "+i+", segment: "+segment+", length: "+length);
            segment = getLastBitFromUrl(url.substring(0, url.length() - length));
            length += segment.length() + 1; //+1 per il "/"
        }

        //System.out.println("return: "+segment);
        return segment;
    }


    /**
     * dato un user controlla che sia al livello giusto rispetto ai punti exp che ha, in caso in cui il
     * livello attuale dell'utente non sia quello giusto (a seguito di una retrocessione o promozione)
     * viene identificato il livello a cui dovrebbe essere (X) e vengono aggiunti allo storico dell'utente
     * tutti i livelli dal livello attuale a cui si trova l'utente fino ad X
     *
     * @param user
     * @return 1 se vengono aggiunti livelli in promozione, -1 se vengono aggiunti livelli in retrocessione,
     *         0 se non vengono aggiunti livelli, 500 se c'è un errore
     */
    public static int checkLevel(User user) {

        LevelDao ld = new LevelDaoImpl(ds);
        UserDao ud = new UserDaoImpl(ds);


        List<Level> livelliDaInserire = new ArrayList<>(); //lista dei livelli che l'user ha perso o ha guadagnato
        int result = 0; //valore che varra' restituito

        try {
            ld.init();
            ud.init();

            gamingplatform.model.Level livelloAttuale = ud.getLevelByUserId(user.getId());

            //dati attuali
            int esperienzaPrecedente = (ud.getLevelByUserId(user.getId())).getExp(); // livello a cui si trovava prima l'utente
            int esperienzaAttuale = user.getExp(); // esperienza a cui si trova attualmente l'utente
            List<Level> levelList = ld.getLevelsOrdered(); // lista contenente TUTTI i livelli

            System.out.println("espPrec: " + esperienzaPrecedente);
            System.out.println("espAtt: " + esperienzaAttuale);


            //l'utente ha meno esperienza di prima, ha perso livelli
            if (esperienzaAttuale < esperienzaPrecedente) {

                for (int i = levelList.size() - 1; i >= 0; i--) { // ciclo sulla lista dei livelli

                    if (levelList.get(i).getExp() >= livelloAttuale.getExp())
                        continue;

                    if (esperienzaAttuale <= levelList.get(i).getExp()) {
                        /* se l'esperieza del livello e' maggiore a quella precedente dell'utente e
                           minore o uguale a quella dell'esperienza successiva
                        */

                        livelliDaInserire.add(levelList.get(i)); // aggiungo il livello alla lista di livelli persi
                        System.out.println("livello da inserire(" + i + ") " + levelList.get(i).getName() + " " + levelList.get(i).getExp());

                    } else {
                        livelliDaInserire.add(levelList.get(i)); // aggiungo il livello effettivo a cui sono arrivato alla lista di livelli persi
                        System.out.println("livello da inserire(" + i + ") " + levelList.get(i).getName() + " " + levelList.get(i).getExp());
                        break;
                    }
                }


                result = -1;

            } else if (esperienzaAttuale > esperienzaPrecedente) {
                //l'utente ha piu' esperienza di prima

                for (int i = 0; i < levelList.size(); i++) {// ciclo sulla lista dei livelli

                    if (levelList.get(i).getExp() <= livelloAttuale.getExp())
                        continue;

                    if (esperienzaAttuale >= levelList.get(i).getExp()) {
                            /* se l'esperieza del livello e' maggiore o uguale a quella precedente dell'utente e
                            minore o uguale a quella dell'esperienza successiva
                            */

                        livelliDaInserire.add(levelList.get(i)); //aggiungo l alla lista dei livelli guadagnati

                    }
                }
                result = 1;
            } else {
                System.out.println("pippo");
                ld.destroy();
                ud.destroy();
                return 0; // se non ha ne guadagnato livelli ne ne ha persi torno 0 e non faccio alcun inserimento in userlevel
            }


            UserLevelDao uld = new UserLevelDaoImpl(ds); // inizializzo un UserlevelDao per accedere al database
            uld.init();


            UserLevel ul = uld.getUserLevel(); // user level da inserire


            if (livelliDaInserire.size() == 0) {
                return 0;
            }


            for (Level l : livelliDaInserire) { // scorro i livelli raggiunti dall'utente

                System.out.println("livello che inserisco in user level: " + l.getName() + " " + l.getExp());

                ul.setUserId(user.getId()); // inserisco l'utente dentro userLevel
                ul.setLevelId(l.getId()); // inserisco il livello in userLevel
                ul.setDate(new Timestamp(System.currentTimeMillis()));

                java.util.concurrent.TimeUnit.SECONDS.sleep(1);//aspetto almeno 1 secondo

                uld.insertUserlevel(ul); // inserisco lo userLevel precedentemente creato

            }//fine for di scorrimento di livelliDaInserire

            ld.destroy();
            ud.destroy();
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(java.util.logging.Level.WARNING, "[upDateLevel] Eccezione: " + e.getMessage());
            //torno KO alla chiamata servlet
            return 500;
        }

        return result;
    }

}
