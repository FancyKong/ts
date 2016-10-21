/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.fastdfs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangjiaze
 * @version Id: FastFDSManager.java, v 0.1 2016/8/25 0025 下午 2:19 FancyKong Exp $$
 */
@Slf4j
//@Component
public class FastFDSManager {

    @PostConstruct
    public void init() {
        //加载配置文件的方式
        String configFileName = "fastDFS/fastDFS.conf";
        try {
            ClientGlobal.init(configFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file
     * @param uploadFileName
     * @param fileLength
     * @return
     * GROUP_NAME
     * REMOTE_FILENAME
     * @throws java.io.IOException
     * @throws org.csource.common.MyException
     */
    public Map<String,String> uploadFile(File file, String uploadFileName, long fileLength) throws IOException, MyException {
        log.info("start to uploadFile,uploadFileName = {} ", uploadFileName);
        byte[] fileBuff = getFileBuffer(new FileInputStream(file), fileLength);
        String[] files = null;
        String fileExtName;
        if (uploadFileName.contains(".")) {
            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        } else {
            log.error("Fail to upload file, because the format of filename is illegal.");
            return null;
        }
        // 建立连接
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient client = new StorageClient(trackerServer, storageServer);
        // 设置元信息
        NameValuePair[] metaList = new NameValuePair[3];
        metaList[0] = new NameValuePair("fileName", uploadFileName);
        metaList[1] = new NameValuePair("fileExtName", fileExtName);
        metaList[2] = new NameValuePair("fileLength", String.valueOf(fileLength));
        // 上传文件
        try {
            files = client.upload_file(fileBuff, fileExtName, metaList);
            Map<String,String> map = new HashMap<>();
            map.put("GROUP_NAME",files[0]);
            map.put("REMOTE_FILENAME",files[1]);
            return map;
        } catch (Exception e) {
            log.error("Fail to upload file due to {}", e);
            throw e;
        }finally {
            trackerServer.close();
        }
    }

    private byte[] getFileBuffer(InputStream inStream, long fileLength) throws IOException {

        byte[] buffer = new byte[256 * 1024];
        byte[] fileBuffer = new byte[(int) fileLength];

        int count = 0;
        int length = 0;

        while ((length = inStream.read(buffer)) != -1) {
            for (int i = 0; i < length; ++i) {
                fileBuffer[count + i] = buffer[i];
            }
            count += length;
        }
        return fileBuffer;
    }

    public String getFileBase64Steam(String groupName, String remoteFileName) throws IOException, MyException {
        log.info("start to getFileBase64Steam,File = {}/{} ", groupName, remoteFileName);
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        byte[] b = storageClient.download_file(groupName, remoteFileName);
        return Base64.encodeBase64String(b);
    }

    public byte[] getFileByteArray(String groupName,String remoteFileName) throws IOException, MyException {
        log.info("start to getFileBase64Steam,File = {}/{} ", groupName, remoteFileName);
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        return storageClient.download_file(groupName, remoteFileName);
    }

    public void downloadFileToDisk(String groupName, String remoteFileName,String destinationPath)
            throws IOException, MyException {
        log.info("start to downloadFile,File = {}/{} ", groupName, remoteFileName);
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        byte[] b = storageClient.download_file(groupName, remoteFileName);
        log.info("文件大小:{}", b.length);
        FileOutputStream out = new FileOutputStream(destinationPath);
        out.write(b);
        out.flush();
        out.close();
    }


    //查看文件信息
    public void getFileInfo(String groupName, String filepath) throws Exception {
        log.info("获取文件信息=======================");
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;

        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        FileInfo fi = storageClient.get_file_info(groupName, filepath);
        log.info("所在服务器地址: {}", fi.getSourceIpAddr());
        log.info("文件大小:{}", fi.getFileSize());
        log.info("文件创建时间:{}", fi.getCreateTimestamp());
        log.info("文件CRC32 signature:{}", fi.getCrc32());
    }

    public void getFileMate(String groupName, String filepath) throws Exception {
        log.info("获取文件Mate=======================");
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;

        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        NameValuePair nvps[] = storageClient.get_metadata(groupName, filepath);
        for (NameValuePair nvp : nvps) {
            log.info(nvp.getName() + ":{}", nvp.getValue());
        }
    }

    public void deleteFile(String groupName, String filepath) throws Exception {
        log.info("start to deleteFile , file = {}/{}", groupName, filepath);
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        int i = storageClient.delete_file(groupName, filepath);
        log.info(i == 0 ? "删除成功" : "删除失败:" + i);
    }
}
