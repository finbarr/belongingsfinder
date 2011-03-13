package com.belongingsfinder.api.pager;

import java.util.List;

import com.belongingsfinder.api.model.Model;

public interface ModelPager<T extends Model<T>> {

	public long count();

	public List<T> retrieve(int number, int offset);

}
