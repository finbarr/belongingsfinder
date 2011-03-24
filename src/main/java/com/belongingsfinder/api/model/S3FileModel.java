package com.belongingsfinder.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import com.amazonaws.services.s3.model.Region;
import com.belongingsfinder.api.modules.AWSModule.BucketName;

@Entity
public class S3FileModel implements Model<S3FileModel>, Serializable {

	private static final long serialVersionUID = -128435986775490232L;
	private static final String http = "http://";
	private static final String s3 = ".amazonaws.com/";

	@Id
	@GeneratedValue(generator = "bf-uuid")
	@GenericGenerator(name = "bf-uuid", strategy = "com.belongingsfinder.api.dao.jpa.UUIDIdentifierGenerator")
	private String id;
	@Enumerated(EnumType.STRING)
	@NotNull
	private BucketName bucket;
	@NotNull
	@NotEmpty
	private String filePath;
	@Enumerated(EnumType.STRING)
	@NotNull
	private Region region;

	public S3FileModel() {
	}

	public S3FileModel(BucketName bucket, String filePath, Region region) {
		this.bucket = bucket;
		this.filePath = filePath;
		this.region = region;
	}

	public String getAWSUrl() {
		return new StringBuilder(S3FileModel.http).append(bucket.toString()).append(".").append(region.toString())
				.append(S3FileModel.s3).append(filePath).toString();
	}

	@JsonIgnore
	public BucketName getBucket() {
		return bucket;
	}

	@JsonIgnore
	public String getFilePath() {
		return filePath;
	}

	public String getId() {
		return id;
	}

	@JsonIgnore
	public Region getRegion() {
		return region;
	}

	public void setBucket(BucketName bucket) {
		this.bucket = bucket;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}
