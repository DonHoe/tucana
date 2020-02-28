package com.hepic.tucana.util;

import org.eclipse.bittorrent.TorrentFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TorrentUtil {

    /**
     * 获取文件信息
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> getFileNames(String path) throws IOException {
        TorrentFile file = new TorrentFile(new File(path));
        String[] names = file.getFilenames();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            result.add(new String(names[i].getBytes("ISO-8859-1"), "UTF-8"));
        }
        return result;
    }
}
