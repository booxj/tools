package com.booxj.tools.core.io;

import com.booxj.tools.core.io.file.DefaultFileVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtil {

    public Path create(Path path) throws IOException {
        return Files.createFile(path);
    }

    public void copy(String source, String destination) throws IOException {
        Path sourcePath = Paths.get(source);
        Path destinationPath = Paths.get(destination);
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public void move(String source, String destination) throws IOException {
        Path sourcePath = Paths.get(source);
        Path destinationPath = Paths.get(destination);
        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public void delete(String source) throws IOException {
        Files.deleteIfExists(Paths.get(source));
    }

    public static Path[] ls(String source) throws IOException {
        return Files.walk(Paths.get(source)).toArray(Path[]::new);
    }

    /**
     * 使用Files.walkFileTree 遍历目录下所有文件，通过 {@link DefaultFileVisitor}干预遍历过程
     *
     * @param source
     * @param fileVisitor
     * @return
     * @throws IOException
     */
    public static Path ls(String source, DefaultFileVisitor fileVisitor) throws IOException {
        return Files.walkFileTree(Paths.get(source), fileVisitor);
    }

}
