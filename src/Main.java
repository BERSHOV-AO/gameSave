import java.io.*;
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


        String[] saveFilesPath = new String[3];
        saveFilesPath[0] = "C://Games/savegames/save1.dat";
        saveFilesPath[1] = "C://Games/savegames/save2.dat";
        saveFilesPath[2] = "C://Games/savegames/save3.dat";

        zipFiles("C://Games/savegames/zip.zip", saveFilesPath);
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {

        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String path, String[] saveFilesPath) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String savePath : saveFilesPath) {
                try (FileInputStream fis = new FileInputStream(savePath)) {
                    ZipEntry entry = new ZipEntry(new File(savePath).getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer, 0, buffer.length);
                    zout.closeEntry();
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                File tempFile = new File(savePath);
                tempFile.delete();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}