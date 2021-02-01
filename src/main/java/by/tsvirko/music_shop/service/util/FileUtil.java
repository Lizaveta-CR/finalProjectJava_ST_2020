package by.tsvirko.music_shop.service.util;

import by.tsvirko.music_shop.service.util.exception.FileUtilException;

import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Reads file information
 */
public class FileUtil {
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * Reads file text
     *
     * @param filePart represents a part or form item that was received within a multipart/form-data POST request
     * @return file information in String
     * @throws FileUtilException if file can not be processed
     */
    public String readFile(Part filePart) throws FileUtilException {
        try {
            InputStream inputStream = filePart.getInputStream();
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(CHARSET_NAME);
        } catch (IOException e) {
            throw new FileUtilException("File can not be processed");
        }
    }
}
