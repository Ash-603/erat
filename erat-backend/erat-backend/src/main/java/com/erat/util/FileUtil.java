package com.erat.util;

import org.springframework.stereotype.Component;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileUtil {

    // 获取目录下所有文件
    public List<String> listFiles(String directoryPath) {
        try {
            Path dir = Paths.get(directoryPath);
            if (!Files.exists(dir) || !Files.isDirectory(dir)) {
                return new ArrayList<>();
            }

            return Files.list(dir)
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 创建目录
    public boolean createDirectory(String directoryPath) {
        try {
            Path dir = Paths.get(directoryPath);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 检查文件是否存在
    public boolean fileExists(String filePath) {
        try {
            return Files.exists(Paths.get(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
