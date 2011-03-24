package com.belongingsfinder.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.apache.solr.analysis.CJKTokenizerFactory;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.PorterStemFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.AnalyzerDiscriminator;
import org.hibernate.search.annotations.ClassBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.validator.constraints.Email;

import com.belongingsfinder.api.annotations.ValidBelonging;
import com.belongingsfinder.api.i18n.HasLanguage;
import com.belongingsfinder.api.i18n.Language;
import com.belongingsfinder.api.search.LanguageBridge;
import com.belongingsfinder.api.search.LanguageDiscriminator;

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
@AnalyzerDiscriminator(impl = LanguageDiscriminator.class)
@ClassBridge(name = "description", index = Index.TOKENIZED, store = Store.NO, impl = LanguageBridge.class)
@ValidBelonging
public class BelongingModel implements Model<BelongingModel>, HasLanguage, Serializable {

	private static final long serialVersionUID = 6587819556855066435L;

	@Id
	@GeneratedValue(generator = "bf-uuid")
	@GenericGenerator(name = "bf-uuid", strategy = "com.belongingsfinder.api.dao.jpa.UUIDIdentifierGenerator")
	private String id;
	@OneToMany(fetch = FetchType.EAGER)
	@Valid
	private Set<S3FileModel> images;
	@Lob
	private String description;
	@IndexedEmbedded
	@Embedded
	@Valid
	private LatLon location;
	@Field(index = Index.UN_TOKENIZED)
	@Enumerated(EnumType.STRING)
	@NotNull
	private BelongingType type;
	@Email
	private String email;
	@IndexedEmbedded
	@ManyToOne
	@NotNull
	@Valid
	private CategoryModel category;
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date lastUpdated;
	@Enumerated(EnumType.STRING)
	@NotNull
	private Language language;

	@JsonIgnore
	public CategoryModel getCategory() {
		return category;
	}

	public String getCategoryId() {
		return category.getId();
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

	public Set<S3FileModel> getImages() {
		return images;
	}

	public Language getLanguage() {
		return language;
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

	@JsonIgnore
	public void setCategory(CategoryModel category) {
		this.category = category;
	}

	public void setCategoryId(String categoryId) {
		category = new CategoryModel();
		category.setId(categoryId);
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

	public void setImages(Set<S3FileModel> images) {
		this.images = images;
	}

	public void setLanguage(Language language) {
		this.language = language;
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
