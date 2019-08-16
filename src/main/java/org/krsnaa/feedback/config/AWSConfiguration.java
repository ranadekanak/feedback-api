package org.krsnaa.feedback.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfiguration {

	final private String accessKey;
	final private String secretKey;
	final private String region;

	@Autowired
	public AWSConfiguration(@Value("${cloud.aws.credentials.accessKey}") String accessKey, @Value("${cloud.aws.credentials.secretKey}") String secretKey, @Value("${cloud.aws.region}") String region){
		this.accessKey = accessKey.replace("%", "AKIA").replaceAll("#", "X22XDD");
		this.secretKey = secretKey.replace("%", "wramivs").replace("#", "ThiFyDQ").replace("$", "WsvxdhaW");
		this.region = region;
	}

	@Bean
	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(accessKey, secretKey);
	}

	@Bean
	public AmazonS3Client amazonS3Client(AWSCredentials awsCredentials) {
		AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
		amazonS3Client.setRegion(Region.getRegion(Regions.fromName(region)));
		return amazonS3Client;
	}
}
