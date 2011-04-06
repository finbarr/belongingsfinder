package com.belongingsfinder.api.resource;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.restlet.data.Status;
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
	private ModelDAO<CategoryModel> cdao;

	@Inject
	private ModelDAO<BelongingModel> bdao;

	@Inject
	private ModelDAO<S3FileModel> fdao;

	// totally not in line with REST conventions
	@Get
	public void createCategories() throws IOException {
		List<CategoryModel> categories = new LinkedList<CategoryModel>();
		categories.add(setChildren(createCategory("Clothing"), "Clothes", "Shoes", "Accessories", "Other"));
		categories.add(setChildren(createCategory("Jewellery"), "Rings", "Watches", "Necklaces", "Other"));
		categories.add(createCategory("Cars/Vehicles"));
		categories.add(createCategory("Bicycles"));
		categories.add(createCategory("Artwork/Sculpture"));
		categories.add(createCategory("Personal/Photographs"));
		categories.add(setChildren(createCategory("Electricals"), "Laptops/Desktops", "Mobile phones", "Cameras",
				"Other"));
		categories.add(createCategory("Furniture"));
		categories.add(createCategory("Dolls/Bears"));
		categories.add(createCategory("Books/Diaries"));
		categories.add(createCategory("Musical Instruments"));
		categories.add(createCategory("Other"));

		for (CategoryModel c : categories) {
			cdao.create(c);
		}

		// File file = new File("/home/finbarr/bd.txt");
		// BufferedReader br = new BufferedReader(new FileReader(file));
		// String line;
		// while ((line = br.readLine()) != null) {
		// createBelonging(line, createImage(), categories);
		// }
		getResponse().setStatus(Status.SUCCESS_NO_CONTENT);
	}

	private void createBelonging(String description, S3FileModel s3, List<CategoryModel> categories) {
		BelongingModel b = new BelongingModel();
		Random rand = new Random();
		b.setCategory(categories.get(rand.nextInt(categories.size())));
		b.setDescription(description);
		b.setEmail("f@fbar.org");
		b.setLanguage(Language.EN);
		b.setType(BelongingType.FOUND);
		Set<S3FileModel> images = new HashSet<S3FileModel>();
		images.add(s3);
		b.setImages(images);
		bdao.create(b);
	}

	private CategoryModel createCategory(String name) {
		CategoryModel c = new CategoryModel();
		c.setName(name);
		return c;
	}

	private S3FileModel createImage() {
		S3FileModel s3 = new S3FileModel();
		s3.setBucket(BucketName.DEV);
		s3.setFilePath("example.png");
		s3.setRegion(Region.US_West);
		fdao.create(s3);
		return s3;
	}

	private CategoryModel setChildren(CategoryModel c, String... children) {
		Set<CategoryModel> kids = new HashSet<CategoryModel>();
		for (String s : children) {
			CategoryModel child = new CategoryModel();
			child.setName(s);
			child.setParent(c);
			kids.add(child);
		}
		c.setChildren(kids);
		return c;
	}
}
