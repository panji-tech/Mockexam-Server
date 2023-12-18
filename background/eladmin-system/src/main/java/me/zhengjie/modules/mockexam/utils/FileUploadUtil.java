package me.zhengjie.modules.mockexam.utils;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import me.zhengjie.utils.LocalUploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description:
 * @Telephone :      15135964789
 * @createDate :     2021/4/10 23:18
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/10 23:18
 * @updateRemark :   修改内容
 **/
@Component
public class FileUploadUtil {

    @Resource
    private LocalUploadUtil localUploadUtil;


    /**
     * local -> server
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/10
     */
    public StorePath uploadByLocalFile(String localPath) throws IOException {
        if (StringUtils.isBlank(localPath)) {
            return null;
        }
        StorePath path = this.upload(new CommonsMultipartFile(file2FileItem(localPath)));
        return path;

    }

    /**
     * file转换成FileItem
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/10
     */
    private FileItem file2FileItem(String localPath) {
        if (StringUtils.isBlank(localPath)) {
            return null;
        }
        File file = new File(localPath);
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem("textField", "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }


    /**
     * 上传MultipartFile
     *
     * @param file
     * @return com.github.tobato.fastdfs.domain.fdfs.StorePath
     * @throws IOException
     */
    public StorePath upload(MultipartFile file) throws IOException {
        String upload = localUploadUtil.upload(file);
        StorePath storePath = StorePath.parseFromUrl(upload);
        return storePath;
    }




}
