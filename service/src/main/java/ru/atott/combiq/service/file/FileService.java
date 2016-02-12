package ru.atott.combiq.service.file;

import java.io.OutputStream;

public interface FileService {

    OutputStream getOutputStream(Location location);

    FileDescriptor getFileDescriptor(Location location);
}
