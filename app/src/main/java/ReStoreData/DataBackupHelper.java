package ReStoreData;

import android.os.Build;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DataBackupHelper {

    // Phương thức sao lưu cơ sở dữ liệu
    public static void backupDatabase(String databaseName, File backupDir) {
        File databaseFile = new File("/data/data/com.example.android12/databases/" + databaseName);
        File backupFile = new File(backupDir, databaseName);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Files.copy(databaseFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức sao lưu SharedPreferences
    public static void backupSharedPreferences(String prefsName, File backupDir) {
        File prefsFile = new File("/data/data/com.example.android12/shared_prefs/" + prefsName + ".xml");
        File backupFile = new File(backupDir, prefsName + ".xml");

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Files.copy(prefsFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}