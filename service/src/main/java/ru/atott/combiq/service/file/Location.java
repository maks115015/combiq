package ru.atott.combiq.service.file;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;
import ru.atott.combiq.service.ServiceException;
import ru.atott.combiq.service.ServicePropertiesHolder;

import java.io.File;
import java.util.Random;
import java.util.UUID;

public class Location {

    private static Random random = new Random(System.currentTimeMillis());

    /**
     * Полный путь к файлу на жестком диске.
     */
    private String relativePath;

    /**
     * Имя файла включая расширение, не определяет местоположение файла на диске, носит информационный характер.
     */
    private String filename;

    private Location() { }

    public static Location parse(String locationString) {
        Validate.notBlank(locationString);

        String[] parts = StringUtils.split(locationString, ":");
        Validate.isTrue(parts.length == 4);

        String storage = parts[0];
        String node = parts[1];
        String relativePath = parts[2];
        String filename = parts[3];

        Location location = new Location();
        location.relativePath = relativePath;
        location.filename = filename;
        return location;
    }

    public static Location localFile(String relativePath, String filename) {
        Location location = new Location();
        location.relativePath = relativePath;
        location.filename = filename;
        return location;
    }

    public static Location allocateLocalLocation(String filename) {
        String filesPath = ServicePropertiesHolder.getFilesPath();
        int i = 0;
        while (i++ < 100) {
            String directoryName = Integer.toString(random.nextInt(100));
            File directoryFile = new File(filesPath, directoryName);
            if (!directoryFile.exists()) {
                if (!directoryFile.mkdirs()) {
                    directoryFile.mkdirs();
                }
            }
            File relativeFile = new File(directoryName, UUID.randomUUID().toString());
            File absoluteFile = new File(filesPath, relativeFile.getPath());
            if (!absoluteFile.exists()) {
                return localFile(relativeFile.getPath(), filename);
            }
        }
        throw new ServiceException("Не удалось выделить location для файла " + filename);
    }

    public String getFilename() {
        return filename;
    }

    public File getLocalFile() {
        return new File(ServicePropertiesHolder.getFilesPath(), relativePath);
    }

    @Override
    public String toString() {
        return "local:node0:" + relativePath + ":" + filename;
    }
}
