package week9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static String readContentFromFile(String path) throws IOException {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Đường dẫn tệp không hợp lệ");
        }

        Path filePath = Paths.get(path);
        if (!filePath.isAbsolute()) {
            filePath = Paths.get(System.getProperty("user.dir"), path);
        }

        FileReader fileReader = new FileReader(filePath.toString());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }
        bufferedReader.close();

        return content.toString();
    }

    public static void writeContentToFile(String path, String content) throws IOException {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Đường dẫn tệp không hợp lệ");
        }

        Path filePath = Paths.get(path);
        if (!filePath.isAbsolute()) {
            filePath = Paths.get(System.getProperty("user.dir"), path);
        }

        File file = filePath.toFile();
        if (file.exists()) {
            file.delete();
        }

        Files.write(filePath, content.getBytes());
    }

    public static void appendContentToFile(String path, String content) throws IOException {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Đường dẫn tệp không hợp lệ");
        }

        Path filePath = Paths.get(path);
        if (!filePath.isAbsolute()) {
            filePath = Paths.get(System.getProperty("user.dir"), path);
        }

        File file = filePath.toFile();
        if (!file.exists()) {
            Files.write(filePath, content.getBytes());
        } else {
            List<String> lines = Files.readAllLines(filePath);
            lines.add(content);
            Files.write(filePath, lines.stream().map(line -> line + "\n").collect(Collectors.toList()));
        }
    }

    public static File findFileByName(String folderPath, String fileName) {
        if (folderPath == null || folderPath.isEmpty()) {
            throw new IllegalArgumentException("Đường dẫn thư mục không hợp lệ");
        }

        // Lấy đường dẫn tuyệt đối nếu đường dẫn tương đối
        Path folderPathPath = Paths.get(folderPath);
        if (!folderPathPath.isAbsolute()) {
            folderPathPath = Paths.get(System.getProperty("user.dir"), folderPath);
        }

        File folder = folderPathPath.toFile();
        File[] files = folder.listFiles();
        if (files == null) {
            return null;
        }

        for (File file : files) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }

        return null;
    }
}