package de.projekteanderschule.weinert.ltbcontrol;

import android.app.Activity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static android.os.Environment.isExternalStorageEmulated;
import static android.os.Environment.isExternalStorageRemovable;

/**
 * Created by ralf on 21.05.17.
 */

public class SaveDataToFile extends Activity {
    private final static String KlassenName = "SaveDataToFile";
    private boolean deleteOldFile = true;


    /**
     * Created by ralf on 21.05.17.
     *
     * Speicher Data to File
     *
     *
     * Vorraussetzungen:
     * @param fileName			FileName unter dme Gespeichert werden soll
     * @param sData	            Data die gespeichet werden soll
     *
     * @return kein
     *
     *  @author ralf weinert - ralf.weinert@gmx.de
     *  @version 0.8.2-21052017
     *
     */
    public void SaveData(String fileName, String sData) {
        String methodenName = KlassenName + ".SaveData";
        String logText = "versuche sData in ein File zu schreiben.";
        Logger.log( "info", methodenName, logText);

        //Logger.log( "info", methodenName, "isExternalStorageEmulated = " + isExternalStorageEmulated());
        //Logger.log( "info", methodenName, "isExternalStorageRemovable = " + isExternalStorageRemovable());
//
        try
        {
//            File path = new File(context.getExternalFilesDir(null), MainActivity.getVersion());
            File path = MainActivity.getLTBAPPPATH();
            //Logger.log( "info", methodenName, "Path = " + path);
            if ( !path.exists() ) {
                path.mkdirs();
            }
            File fullFileName = new File(path, fileName);
            Logger.log( "info", methodenName, "Path zu SaveFile = " + fullFileName);
            if( fullFileName.exists() && deleteOldFile == true ) {
                Logger.log( "info", methodenName, "Das File existiert schon! Die alte Version wird gelöscht!");
                fullFileName.delete();
            }
            FileWriter writer = new FileWriter(fullFileName,true);
           // Nicht anhängen, Löschen!
            writer.write(sData);
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            logText = "ERROR Write to Disk.";
            Logger.log( "error", methodenName, logText);

        }
    }
    public void setDeleteOldFile( boolean delete){
        deleteOldFile = delete;
    }
}
