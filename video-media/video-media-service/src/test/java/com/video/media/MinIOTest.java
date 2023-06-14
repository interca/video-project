package com.video.media;

import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MinIOTest {
    // 创建MinioClient对象
    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://127.0.0.1:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

    /**
     * 上传测试方法
     */
    @Test
    public void uploadTest() {
        System.out.println(":sss");
        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("hyjtest")   //确定桶
                            .object("pic01.png")    // 同一个桶内对象名不能重复
                            .filename("C:\\Users\\waili\\Desktop\\usual\\微信截图\\MongoDB\\微信截图_20230118044805.png")
                            .build()
            );
            System.out.println("上传成功");
        } catch (Exception e) {
            System.out.println("上传失败");
        }
    }
}
