package com.belongingsfinder.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.solr.analysis.CJKTokenizerFactory;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.PorterStemFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.analyzer.Discriminator;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.AnalyzerDiscriminator;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import com.belongingsfinder.api.model.BelongingModel.BelongingModelLanguageDiscriminator;
import com.sun.istack.internal.NotNull;

/**
 * @author finbarr
 * 
 */
@Entity
@Indexed
@AnalyzerDefs({
		@AnalyzerDef(name = "en", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				@TokenFilterDef(factory = PorterStemFilterFactory.class) }),
		@AnalyzerDef(name = "jp", tokenizer = @TokenizerDef(factory = CJKTokenizerFactory.class)) })
@AnalyzerDiscriminator(impl = BelongingModelLanguageDiscriminator.class)
public class BelongingModel implements Model<BelongingModel>, Serializable {

	private static final long serialVersionUID = 6587819556855066435L;

	@Id
	@GeneratedValue(generator = "bf-uuid")
	@GenericGenerator(name = "bf-uuid", strategy = "com.belongingsfinder.api.dao.jpa.UUIDIdentifierGenerator")
	private String id;
	@OneToMany
	private List<S3FileModel> images;
	@Field
	@Lob
	private String description;
	@Embedded
	@IndexedEmbedded
	private LatLon location;
	@Field(index = Index.UN_TOKENIZED)
	@Enumerated(EnumType.STRING)
	@NotNull
	private BelongingType type;
	@NotNull
	private String email;
	@NotNull
	@IndexedEmbedded
	@ManyToOne
	private CategoryModel category;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;
	@Enumerated(EnumType.STRING)
	private BelongingLang lang;

	public CategoryModel getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public String getEmail() {
		return email;
	}

	public String getId() {
		return id;
	}

	public List<S3FileModel> getImages() {
		return images;
	}

	public BelongingLang getLang() {
		return lang;
	}

	@JsonIgnore
	public Date getLastUpdated() {
		return lastUpdated;
	}

	public LatLon getLocation() {
		return location;
	}

	public BelongingType getType() {
		return type;
	}

	public void setCategory(CategoryModel category) {
		this.category = category;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImages(List<S3FileModel> images) {
		this.images = images;
	}

	public void setLang(BelongingLang lang) {
		this.lang = lang;
	}

	@JsonIgnore
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setLocation(LatLon location) {
		this.location = location;
	}

	public void setType(BelongingType type) {
		this.type = type;
	}

	public enum BelongingField {

		DESCRIPTION("description"), LOCATION("location"), TYPE("type"), EMAIL("email"), IMAGE("image"), CATEGORY(
				"category");

		private final String name;

		private BelongingField(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	public enum BelongingLang {

		EN("en"), JP("jp");

		private final String lang;

		private BelongingLang(String lang) {
			this.lang = lang;
		}

		@Override
		public String toString() {
			return lang;
		}

	}

	public static class BelongingModelLanguageDiscriminator implements Discriminator {

		@Override
		public String getAnalyzerDefinitionName(Object value, Object entity, String field) {
			if (entity instanceof BelongingModel) {
				BelongingModel bm = (BelongingModel) entity;
				return bm.getLang().toString();
			}
			return null;
		}

	}

	public static class BelongingModelVerifier implements ModelVerifier<BelongingModel> {

		@Override
		public boolean verify(BelongingModel model) {
			return model != null
					&& model.getCategory() != null
					&& model.getType() != null
					&& model.getEmail() != null
					&& (model.getType() == BelongingModel.BelongingType.FOUND && model.getImages() != null
							&& !model.getImages().isEmpty() || model.getType() == BelongingModel.BelongingType.LOST
							&& model.getDescription() != null);
		}

	}

	public enum BelongingType {

		LOST("lost"), FOUND("found"), ALL("all");

		private final String name;

		private BelongingType(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

}
