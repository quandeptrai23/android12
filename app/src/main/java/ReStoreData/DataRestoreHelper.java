package ReStoreData;

import android.os.Build;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DataRestoreHelper {

    // Phương thức khôi phục cơ sở dữ liệu
    public static void restoreDatabase(String databaseName, File backupDir) {
        File backupFile = new File(backupDir, databaseName);
        File databaseFile = new File("/data/data/com.example.android12/databases/" + databaseName);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Files.copy(backupFile.toPath(), databaseFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức khôi phục SharedPreferences
    public static void restoreSharedPreferences(String prefsName, File backupDir) {
        File backupFile = new File(backupDir, prefsName + ".xml");
        File prefsFile = new File("/data/data/com.example.android12/shared_prefs/" + prefsName + ".xml");

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Files.copy(backupFile.toPath(), prefsFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
