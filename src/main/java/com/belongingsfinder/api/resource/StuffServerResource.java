package com.belongingsfinder.api.resource;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.restlet.resource.Get;

import com.amazonaws.services.s3.model.Region;
import com.belongingsfinder.api.dao.ModelDAO;
import com.belongingsfinder.api.i18n.Language;
import com.belongingsfinder.api.model.BelongingModel;
import com.belongingsfinder.api.model.BelongingModel.BelongingType;
import com.belongingsfinder.api.model.CategoryModel;
import com.belongingsfinder.api.model.S3FileModel;
import com.belongingsfinder.api.modules.AWSModule.BucketName;
import com.google.inject.Inject;

/**
 * @author finbarr
 * 
 */
public class StuffServerResource extends ValidatedServerResource {

	@Inject
	private ModelDAO<BelongingModel> dao;

	@Inject
	private ModelDAO<CategoryModel> cdao;

	@Inject
	private ModelDAO<S3FileModel> sdao;

	@Get("json")
	public BelongingModel getModel() {
		CategoryModel cm = new CategoryModel();
		cm.setName("parent");
		Set<CategoryModel> children = new HashSet<CategoryModel>();
		children.add(c("a", cm));
		children.add(c("b", cm));
		children.add(c("c", cm));
		children.add(c("d", cm));
		cm.setChildren(children);
		S3FileModel s3 = new S3FileModel();
		s3.setBucket(BucketName.DEV);
		s3.setFilePath("hello");
		s3.setRegion(Region.US_Standard);
		sdao.create(s3);
		s3.setBucket(BucketName.DEV);
		s3.setRegion(Region.US_West);
		s3.setFilePath("hey-ho");
		Set<S3FileModel> images = new HashSet<S3FileModel>();
		images.add(s3);
		cdao.create(cm);
		BelongingModel b = new BelongingModel();
		b.setCategory(cm);
		b.setEmail("f@fbar.org");
		b.setLanguage(Language.EN);
		b.setDescription("hello, world");
		b.setLastUpdated(new Date());
		b.setType(BelongingType.LOST);
		b.setImages(images);
		dao.create(b);
		b.getCategory().setChildren(null);
		return b;
	}

	private CategoryModel c(String name, CategoryModel parent) {
		CategoryModel cm = new CategoryModel();
		cm.setName(name);
		cm.setParent(parent);
		return cm;
	}
}
