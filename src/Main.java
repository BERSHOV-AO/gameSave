import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress gameProgress1 = new GameProgress(94, 10, 2, 254.32);
        GameProgress gameProgress2 = new GameProgress(55, 7, 4, 481.83);
        GameProgress gameProgress3 = new GameProgress(71, 5, 6, 729.45);

        saveGame("C://Games/savegames/save1.dat", gameProgress1);
        saveGame("C://Games/savegames/save2.dat", gameProgress2);
        saveGame("C://Games/savegames/save3.dat", gameProgress3);

        List<String> listOfPaths = Arrays.asList("C://Games/savegames/save1.dat", "C://Games/savegames/save2.dat",
                "C://Games/savegames/save3.dat");

        zipFiles("C://Games/savegames/zip.zip", listOfPaths);
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {

        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> listOfPaths) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            FileInputStream fis;

            for (String strPath : listOfPaths) {
                fis = new FileInputStream(strPath);
                ZipEntry entry = new ZipEntry("packed_" + strPath);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for (String filePath : listOfPaths) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Файл удален");
            } else {
                System.out.println("Что то пошло не так");
            }
        }
    }
}