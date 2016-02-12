package ru.atott.combiq.service.file;

import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;
import ru.atott.combiq.service.ServiceException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public OutputStream getOutputStream(Location location) {
        Validate.notNull(location);

        try {
            File localFile = location.getLocalFile();

            if (!localFile.exists()) {
                localFile.createNewFile();
            }

            return new FileOutputStream(localFile);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public FileDescriptor getFileDescriptor(Location location) {
        try {
            File file = location.getLocalFile();

            FileDescriptor descriptor = new FileDescriptor();
            descriptor.setSize(file.length());
            descriptor.setInputStream(new FileInputStream(file));
            descriptor.setLocation(location);
            return descriptor;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
