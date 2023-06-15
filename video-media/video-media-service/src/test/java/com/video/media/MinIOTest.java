package com.video.media;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.InputStream;

@SpringBootTest
public class MinIOTest {
    // 创建MinioClient对象
    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://127.0.0.1:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();


    @Autowired
    private MinioClient client;
    /**
     * 上传测试方法
     */
    @Test
    public void uploadTest() {
        try {
            client.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("hyj")   //确定桶
                            .object("pic01.png")    // 同一个桶内对象名不能重复
                            .filename("C:\\Users\\waili\\Desktop\\usual\\微信截图\\MongoDB\\微信截图_20230118044805.png")
                            .build()
            );
            System.out.println("上传成功");
        } catch (Exception e) {
            System.out.println("上传失败");
        }
    }


    /**
     * 删除文件
     */
    @Test
    public void deleteTest() {
        try {
            client.removeObject(RemoveObjectArgs
                    .builder()
                    .bucket("hyj")
                    .object("pic01.png")
                    .build());
            System.out.println("删除成功");
        } catch (Exception e) {
            System.out.println("删除失败");
        }
    }


    /**
     * 下载文件
     */
    @Test
    public void getFileTest() {
        try {
            InputStream inputStream =client.getObject(GetObjectArgs.builder()
                    .bucket("video")
                    .object("币安上线34期 Launchpool--质押代币就能挖矿MAV加密货币，Maverick协议简单分析.mp4")
                    .build());
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\waili\\Desktop\\tmp.mp4");
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer,0,len);
            }
            inputStream.close();
            fileOutputStream.close();
            System.out.println("下载成功");
        } catch (Exception e) {
            System.out.println("下载失败");
        }
    }
}
