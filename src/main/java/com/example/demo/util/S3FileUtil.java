package com.example.demo.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;


@Component
public class S3FileUtil {

	@Autowired
	AmazonS3 amazonS3;

	String bucketName = "yygpronew";

	//파일 업로드
	//매개변수 : 파일 스트림
	//반환값: 파일경로
	public String fileUpload(MultipartFile file) {
		
		//파일 원본이름
		String originFilename = file.getOriginalFilename();
		
		//파일 확장자
		int index = originFilename.lastIndexOf(".");
		String extention = originFilename.substring(index+1);
		
		//새로운 파일명 중복x
		String str = UUID.randomUUID().toString().substring(0, 10);
		String s3filename = str + originFilename;

		//s3저장소에 팡리 업로드
		InputStream is;
		
		try {
			is = file.getInputStream();
			byte[] bs =IOUtils.toByteArray(is);
			
			ObjectMetadata metadata = new ObjectMetadata();
			
			metadata.setContentType("image/" + extention.replace(".", ""));
			
			metadata.setContentLength(bs.length);
			
			ByteArrayInputStream stream = new ByteArrayInputStream(bs);
			
			PutObjectRequest request = new PutObjectRequest(bucketName, s3filename, stream, metadata)
			        .withCannedAcl(CannedAccessControlList.PublicRead); // ✅ 추가

			amazonS3.putObject(request);

			
			
		}  catch (IOException e) {
			e.printStackTrace();
		
		}
		
		String url = amazonS3.getUrl(bucketName, s3filename).toString();

		return url;
	}}


