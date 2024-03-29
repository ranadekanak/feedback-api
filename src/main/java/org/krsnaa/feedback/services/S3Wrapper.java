package org.krsnaa.feedback.services;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class S3Wrapper {

	final private AmazonS3Client amazonS3Client;
	final private String bucket;

	@Autowired
	public S3Wrapper(@Value("${cloud.aws.s3.bucket}") String bucket, AmazonS3Client amazonS3Client){
		this.amazonS3Client = amazonS3Client;
		this.bucket = bucket;
	}

	public PutObjectResult upload(InputStream inputStream, String uploadKey) {
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, inputStream, new ObjectMetadata());
		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);

		IOUtils.closeQuietly(inputStream);

		return putObjectResult;
	}

	public S3ObjectInputStream download(String key, HttpServletResponse response) throws IOException {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
		S3Object s3Object = amazonS3Client.getObject(getObjectRequest);

		return s3Object.getObjectContent();
	}

	public List<S3ObjectSummary> list() {
		ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(bucket));
		List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();

		return s3ObjectSummaries;
	}

	@Deprecated
	public List<PutObjectResult> upload(MultipartFile[] multipartFiles) {
		List<PutObjectResult> putObjectResults = new ArrayList<>();

		Arrays.stream(multipartFiles)
				.filter(multipartFile -> !StringUtils.isEmpty(multipartFile.getOriginalFilename()))
				.forEach(multipartFile -> {
					try {
						putObjectResults.add(upload(multipartFile.getInputStream(), multipartFile.getOriginalFilename()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});

		return putObjectResults;
	}

	@Deprecated
	public ResponseEntity<byte[]> download(String key) throws IOException {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
		S3Object s3Object = amazonS3Client.getObject(getObjectRequest);
		S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

		byte[] bytes = IOUtils.toByteArray(objectInputStream);

		String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		httpHeaders.setContentLength(bytes.length);
		httpHeaders.setContentDispositionFormData("attachment", fileName);

		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
	}

}
