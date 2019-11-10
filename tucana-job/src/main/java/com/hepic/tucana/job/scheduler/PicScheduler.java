package com.hepic.tucana.job.scheduler;

import com.alibaba.fastjson.JSON;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.hepic.tucana.dal.ArticleImgDao;
import com.hepic.tucana.model.dal.ArticleImg;
import com.hepic.tucana.util.file.DownloadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class PicScheduler {

    @Value(value = "${download.basePath}")
    private String basePath;

    @Autowired
    private ArticleImgDao articleImgDao;

    //每天3：05执行
    @Scheduled(cron = "0/5 * * * * ? ")
    public void downloadPic() {
        ArticleImg query = new ArticleImg();
        query.setStatus(0);
        ArticleImg articleImg = articleImgDao.selectArticleImgOneByModel(query);
        if (articleImg == null) {
            return;
        }
        String subPath = "undefine";
        if (articleImg.getArticleId() != 0) {
            subPath = articleImg.getArticleId().toString();
        }
        String dirPath = basePath + subPath;
        String img = articleImg.getImg();
        String filePath = dirPath + "/" + articleImg.getKey() + img.substring(img.lastIndexOf("."));
        System.out.println("文件名:" + filePath);
        File file = new File(filePath);
        try {
            System.out.println("文件夹:" + dirPath);
            File dir = new File(dirPath);
            if (!dir.isDirectory()) {
                dir.mkdir();
            }
            if (file.exists()) {
                throw new Exception(articleImg.getKey() + "/" + img + " has exist file");
            }
            DownloadFileUtil.downloadFile(articleImg.getImg(), filePath);
            articleImg.setPath(filePath);
            articleImg.setStatus(1);
        } catch (Exception e) {
            articleImg.setStatus(-1);
            file.delete();
            System.out.println(e);
        } finally {
            articleImgDao.updateArticleImg(articleImg);
        }
    }

    //每天3：05执行
    @Scheduled(cron = "0/30 * * * * ? ")
    public void getExif() {
        List<ArticleImg> list = articleImgDao.selectArticleImgNoExif(100);
        for (ArticleImg item : list) {
            File file = new File(item.getPath());
            if (!file.exists()) {
                return;
            }
            try {
                List<String> info = new ArrayList<>();
                Metadata metadata = ImageMetadataReader.readMetadata(file);
                for (Directory directory : metadata.getDirectories()) {
                    for (Tag tag : directory.getTags()) {
                        info.add(String.format("[%s] - %s = %s",
                                directory.getName(), tag.getTagName(), tag.getDescription()));
                    }
                }
                item.setExif(JSON.toJSONString(info));
                articleImgDao.updateArticleImg(item);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
