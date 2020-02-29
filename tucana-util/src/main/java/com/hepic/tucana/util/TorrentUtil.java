package com.hepic.tucana.util;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.bittorrent.TorrentFile;
import org.eclipse.bittorrent.internal.encode.BEncodedDictionary;
import org.eclipse.bittorrent.internal.encode.Decode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TorrentUtil {

    /**
     * 获取文件信息
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> getFileNames(String path) throws IOException {
        return getFileNames(new File(path));
    }

    /**
     * 获取文件信息
     *
     * @param torrentFile
     * @return
     * @throws IOException
     */
    public static List<String> getFileNames(File torrentFile) throws IOException {
        TorrentFile file = new TorrentFile(torrentFile);

        String[] names = file.getFilenames();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            result.add(new String(names[i].getBytes("ISO-8859-1"), "UTF-8"));
        }
        return result;
    }

    /**
     * 获取字典，自行处理
     *
     * @param torrentFile
     * @return
     * @throws IOException
     */
    public static BEncodedDictionary getDictionary(File torrentFile) throws IOException {
        if (torrentFile == null) {
            throw new IllegalArgumentException("The file cannot be null");
        } else if (torrentFile.isDirectory()) {
            throw new IllegalArgumentException("The provided file is a directory");
        }
        return Decode.bDecode(new FileInputStream(torrentFile));
    }

    /**
     * 获取字典，自行处理
     *
     * @param torrentFile
     * @return
     * @throws IOException
     */
    public static Map<String, Object> getNameAndFiles(File torrentFile) throws IOException {
        BEncodedDictionary bEncodedDictionary = getDictionary(torrentFile);
        BEncodedDictionary info = (BEncodedDictionary) bEncodedDictionary.get("info");
        List<String> fileNames = new ArrayList<>();
        List list = (List) info.get("files");
        String name = (String) info.get("name");
        name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
        if (list == null) {
            fileNames.add(name);
        } else {
            for (Object item : list) {
                BEncodedDictionary aDictionary = (BEncodedDictionary) item;
                List path = (List) aDictionary.get("path");
                String fullPath = StringUtils.join(path, File.separator);
                fileNames.add(new String(fullPath.getBytes("ISO-8859-1"), "UTF-8"));
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("files", fileNames);
        return result;
    }
}
