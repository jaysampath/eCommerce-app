package com.services.eCommerceRestful.dao;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.services.eCommerceRestful.entity.Item;
import com.services.eCommerceRestful.entity.ItemReview;
import com.services.eCommerceRestful.service.EcommerceService;
import com.services.eCommerceRestful.service.SequenceGeneratorService;

@Repository
public class ItemsRepositoryImpl implements ItemsRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	SimpleDateFormat sdf = new SimpleDateFormat();

	Logger logger = LoggerFactory.getLogger(EcommerceService.class);

	@Override
	public Item findItemByNameAndCompany(String itemName, String itemManufacturer) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where("itemName").is(itemName),
				Criteria.where("itemManufacturer").is(itemManufacturer)));
		List<Item> item = mongoTemplate.find(query, Item.class);
		if (item.size() == 0)
			return null;
		else
			return item.get(0);
	}

	@Override
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		List<Item> items = mongoTemplate.findAll(Item.class);
		return items;
	}

	@Override
	public Item updateItem(Item item) {
		// TODO Auto-generated method stub
		Item updatedItem = mongoTemplate.save(item, "item");
		return updatedItem;
	}

	@Override
	public Item getItemById(long id) {
		// TODO Auto-generated method stub
		Item item = mongoTemplate.findById(id, Item.class);
		return item;
	}

	@Override
	public Item addNewItem(Item item) {
		// TODO Auto-generated method stub
		Item checkItem = findItemByNameAndCompany(item.getItemName(), item.getItemManufacturer());
		if (checkItem == null) {
			item.setItemId(SequenceGeneratorService.generateSequence(Item.SEQUENCE_NAME));
			mongoTemplate.insert(item, "item");
			return item;
		}
		int prevStock = checkItem.getItemStock();
		float prevRating = checkItem.getItemRating();
		int prevNumRatings = checkItem.getNumRatings();
		item.setItemId(checkItem.getItemId());
		item.setItemRating(prevRating);
		item.setItemStock((item.getItemStock() + prevStock));
		item.setNumRatings(prevNumRatings);
		mongoTemplate.save(item, "item");
		return item;
	}

	@Override
	public List<String> getItemCategoryNames() {
		// TODO Auto-generated method stub
		List<String> names = mongoTemplate.findDistinct("itemCategory", Item.class, String.class);
		return names;
	}

	@Override
	public List<String> getItemSubCategoryNames(String categoryName) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("itemCategory").is(categoryName));
		List<String> subCategoryNames = mongoTemplate.findDistinct(query, "itemSubCategory", Item.class, String.class);
		return subCategoryNames;
	}

	@Override
	public List<Item> getItemsBySubCategory(String category, String subCategory) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where("itemCategory").is(category),
				Criteria.where("itemSubCategory").is(subCategory)));
		List<Item> items = mongoTemplate.find(query, Item.class);
		return items;
	}

	@Override
	public ItemReview addItemReview(ItemReview itemReview) {
		// TODO Auto-generated method stub
		itemReview.setId(SequenceGeneratorService.generateSequence(ItemReview.SEQUENCE_NAME));
		itemReview.setPostedTime(sdf.format(System.currentTimeMillis()));
		mongoTemplate.insert(itemReview, "item_review");
		updateItemRatings(itemReview.getItemId(), itemReview.getRating());
		return itemReview;
	}

	public void updateItemRatings(int itemId, float rating) {

		Item item = mongoTemplate.findById(itemId, Item.class);
		float prevRating = item.getItemRating();
		int prevNumRatings = item.getNumRatings();
		float newRating = 0;
		newRating = (((prevRating * prevNumRatings) + rating) / (prevNumRatings + 1));
		newRating = (float) Math.round(newRating * 100) / 100 ;
		item.setItemRating(newRating);
		item.setNumRatings(prevNumRatings + 1);
		mongoTemplate.save(item, "item");
	}

	@Override
	public List<ItemReview> getItemReviews(long id) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("itemId").is(id));
		List<ItemReview> itemReviews = mongoTemplate.find(query, ItemReview.class);
		return itemReviews;
	}

}
