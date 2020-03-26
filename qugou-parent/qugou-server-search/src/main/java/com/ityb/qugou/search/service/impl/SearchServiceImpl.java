package com.ityb.qugou.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.DateUtils;
import com.ityb.qugou.base.utils.NumberUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.bo.search.ProductSearchBean;
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.dto.search.ProductSearchDto;
import com.ityb.qugou.po.product.BrowseHistory;
import com.ityb.qugou.search.constant.Constants;
import com.ityb.qugou.search.dao.SearchDao;
import com.ityb.qugou.search.service.SearchService;
import com.ityb.qugou.vo.search.ProductSearchVo;

/**
 * 商品搜索服务serviceImpl
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SolrClient solrClient;
	@Autowired
	private SearchDao searchDao;
	private final static Logger LOGGER = Logger.getLogger(SearchServiceImpl.class);

	/**
	 * 导入商品进入solr服务器
	 * 
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param list
	 */
	@Override
	public void importProduct(List<ProductSearchBean> list) {
		Assert.notEmpty(list, "导入的商品为空");
		SolrInputDocument solrInputDocument = null;
		List<SolrInputDocument> solrInputDocumentList = new ArrayList<>();
		for (ProductSearchBean productSearchBean : list) {
			solrInputDocument = new SolrInputDocument();
			solrInputDocument.setField("id", productSearchBean.getProductId());
			solrInputDocument.setField("product_add_time", DateUtils.date2Str(productSearchBean.getAddTime()));
			solrInputDocument.setField("product_title", productSearchBean.getTitle());
			solrInputDocument.setField("product_sell_point", productSearchBean.getSellPoint());
			solrInputDocument.setField("product_min_price", productSearchBean.getMinPrice());
			solrInputDocument.setField("product_max_price", productSearchBean.getMaxPrice());
			solrInputDocument.setField("product_category_bg", productSearchBean.getShopCategoryName());
			solrInputDocument.setField("product_category_merchant", productSearchBean.getMerchantCategoryName());
			solrInputDocument.setField("product_address", productSearchBean.getProductAddress());
			solrInputDocument.setField("product_number", productSearchBean.getProductNumber());
			solrInputDocument.setField("product_name", productSearchBean.getProductName());
			solrInputDocument.setField("product_shop_name", productSearchBean.getShopName());
			solrInputDocument.setField("product_x", productSearchBean.getX().toEngineeringString());
			solrInputDocument.setField("product_y", productSearchBean.getY().toEngineeringString());
			solrInputDocument.setField("product_shop_id", productSearchBean.getShopId());
			solrInputDocument.setField("product_specification_id", productSearchBean.getSpecificationId());
			//导入是第一张图片
			solrInputDocument.setField("product_pic",productSearchBean.getPicUrl());
			solrInputDocumentList.add(solrInputDocument);
		}
		try {
			solrClient.add(solrInputDocumentList);
			// 必须进行提交
			solrClient.commit();
			LOGGER.info("importProduct...导入商品信息到solr服务器成功，共导入" + list.size() + "条记录");
		} catch (SolrServerException | IOException e) {
			LOGGER.error("importProduct...导入商品信息到solr服务器失败", e);
		}
	}

	/**
	 * 添加一条记录到solr服务器
	 * 
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 */
	@Override
	public void addProductToSearch(List<ProductSearchBean> list) {
		this.importProduct(list);
	}

	/**
	 * 从solr服务器中删除一条信息
	 * 
	 * @author yangbin
	 * @date 2018年2月12日
	 * @param text
	 */
	@Override
	public void deleteProductToSearchByProductId(List<String> productIdList) {
		Assert.notEmpty(productIdList,"商品ID不能为空");
		try {
			for (String productId:productIdList) {
				this.solrClient.deleteById(productId);
			}
			solrClient.commit();
		} catch (SolrServerException | IOException e) {
			LOGGER.error("importProduct...商品信息从solr服务器删除失败", e);
		}
	}

	/**
	 * 商品搜索
	 * @author yangbin
	 * @date 2018年3月17日
	 * @param productSearchDto
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Override
	public List<ProductSearchVo> search(ProductSearchDto productSearchDto){
		if(productSearchDto.getPageNow()==null||productSearchDto.getPageNow()<=0){
			productSearchDto.setPageNow(Constants.DEFAULT_PAGE_NOW);
		}
		if(productSearchDto.getPageSize()==null||productSearchDto.getPageSize()<=0){
			productSearchDto.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		}
		productSearchDto.setPageStart((productSearchDto.getPageNow()-1)*productSearchDto.getPageSize());
		SolrQuery solrQuery=new SolrQuery();
		if(StringUtils.isNoneBlank(productSearchDto.getKeyWord())){
			//设置查询条件
			solrQuery.setQuery(productSearchDto.getKeyWord());
		}else{
			solrQuery.setQuery("*:*");
		}
		//设置查询的起始位置
		solrQuery.setStart(productSearchDto.getPageStart());
		//设置一页显示多少条数据
		solrQuery.setRows(productSearchDto.getPageSize());
		/*
		 * 设置过滤条件
		 */
		if(StringUtils.isNotBlank(productSearchDto.getAddress())){
			solrQuery.addFilterQuery("product_address:"+productSearchDto.getAddress()+"*");
		}
		if(StringUtils.isNotBlank(productSearchDto.getShopName())){
			solrQuery.addFilterQuery("product_shop_name:"+productSearchDto.getShopName());
		}
		if(StringUtils.isNotBlank(productSearchDto.getMerchantCategoryName())){
			solrQuery.addFilterQuery("product_category_merchant:"+productSearchDto.getMerchantCategoryName());
		}
		if(StringUtils.isNotBlank(productSearchDto.getShopCategoryName())){
			solrQuery.addFilterQuery("product_category_bg:"+productSearchDto.getShopCategoryName());
		}
		if(StringUtils.isNotBlank(productSearchDto.getShopId())){
			solrQuery.addFilterQuery("product_shop_id:"+productSearchDto.getShopId());
		}
		//价格区间
		StringBuffer priceCondition=new StringBuffer();
		if(NumberUtils.isNotNull(productSearchDto.getPriceLeft())){
			priceCondition.append("[").append(productSearchDto.getPriceLeft()).append(" TO ");
		}else{
			priceCondition.append("[").append("*").append(" TO ");
		}
		if(NumberUtils.isNotNull(productSearchDto.getPriceRight())){
			priceCondition.append(productSearchDto.getPriceRight()).append("]");
		}else{
			priceCondition.append("*").append("]");
		}
		//进行范围筛选
		solrQuery.addFilterQuery("product_min_price:"+priceCondition.toString());
		//根据价格升降序
		if(NumberUtils.isNotNull(productSearchDto.getPriceState())&&productSearchDto.getPriceState().intValue()==1){
			solrQuery.addSort("product_min_price", ORDER.asc);
		}else if(NumberUtils.isNotNull(productSearchDto.getPriceState())&&productSearchDto.getPriceState().intValue()==2){
			solrQuery.addSort("product_min_price", ORDER.desc);
		}
		if(NumberUtils.isNotNull(productSearchDto.getAddTimeOrder())&&productSearchDto.getAddTimeOrder().intValue()==1){
			solrQuery.addSort("product_add_time", ORDER.asc);
		}else if(NumberUtils.isNotNull(productSearchDto.getAddTimeOrder())&&productSearchDto.getAddTimeOrder().intValue()==2){
			solrQuery.addSort("product_add_time", ORDER.desc);
		}
		// 设置默认搜索域
		solrQuery.set("df", "product_keywords");
		// 设置高亮显示
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("product_title");
		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");
		List<ProductSearchVo> searchResultList=null;
		try {
			searchResultList=this.searchDao.search(solrQuery);
		} catch (SolrServerException | IOException e) {
			LOGGER.error("search...",e);
			throw new ServiceException("搜索发生错误");
		}
		return searchResultList;
	}

	/**
	 * 商品搜索通过分类（包括商城分类，商家分类）
	 * @author yangbin
	 * @date 2018年3月18日
	 * @param productSearchDto
	 * @return
	 */
	@Override
	public List<ProductSearchVo> searchByCategory(ProductSearchDto productSearchDto) {
		Assert.isTrue(StringUtils.isBlank(productSearchDto.getMerchantCategoryName())&&StringUtils.isBlank(productSearchDto.getShopCategoryName()),"搜索的内容不能为空");
		if(productSearchDto.getPageNow()==null||productSearchDto.getPageNow()<=0){
			productSearchDto.setPageNow(Constants.DEFAULT_PAGE_NOW);
		}
		if(productSearchDto.getPageSize()==null||productSearchDto.getPageSize()<=0){
			productSearchDto.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		}
		productSearchDto.setPageStart((productSearchDto.getPageNow()-1)*productSearchDto.getPageSize());
		SolrQuery solrQuery=new SolrQuery();
		//设置查询的起始位置
		solrQuery.setStart(productSearchDto.getPageStart());
		//设置一页显示多少条数据
		solrQuery.setRows(productSearchDto.getPageSize());
		if(StringUtils.isNotBlank(productSearchDto.getMerchantCategoryName())){
			solrQuery.addFilterQuery("product_category_merchant",productSearchDto.getMerchantCategoryName());
			//设置查询的域
			solrQuery.addField("product_category_merchant");
		}
		if(StringUtils.isNotBlank(productSearchDto.getShopCategoryName())){
			solrQuery.addFilterQuery("product_category_bg",productSearchDto.getShopCategoryName());
			//设置查询的域
			solrQuery.addField("product_category_bg");
		}
		/*
		 * 设置过滤条件
		 */
		if(StringUtils.isNotBlank(productSearchDto.getAddress())){
			solrQuery.addFilterQuery("product_address:"+productSearchDto.getAddress()+"*");
		}
		//价格区间
		StringBuffer priceCondition=new StringBuffer();
		if(NumberUtils.isNotNull(productSearchDto.getPriceLeft())){
			priceCondition.append("[").append(productSearchDto.getPriceLeft()).append(" TO ");
		}else{
			priceCondition.append("[").append("*").append(" TO ");
		}
		if(NumberUtils.isNotNull(productSearchDto.getPriceRight())){
			priceCondition.append(productSearchDto.getPriceRight()).append("]");
		}else{
			priceCondition.append("*").append("]");
		}
		//进行范围筛选
		solrQuery.addFilterQuery("product_min_price:"+priceCondition.toString());
		//根据价格升降序
		if(NumberUtils.isNotNull(productSearchDto.getPriceState())&&productSearchDto.getPriceState().intValue()==1){
			solrQuery.addSort("product_min_price", ORDER.asc);
		}else if(NumberUtils.isNotNull(productSearchDto.getPriceState())&&productSearchDto.getPriceState().intValue()==2){
			solrQuery.addSort("product_min_price", ORDER.desc);
		}
		// 设置高亮显示
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("product_title");
		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");
		List<ProductSearchVo> searchResultList=null;
		try {
			searchResultList=this.searchDao.search(solrQuery);
		} catch (SolrServerException | IOException e) {
			LOGGER.error("search...",e);
			throw new ServiceException("搜索发生错误");
		}
		return searchResultList;
	}

	/**
	 * 根据商品ID得到对应的商品信息
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param productId
	 * @return
	 */
	@Override
	public ProductSearchVo searchByProductId(String productId) {
		Assert.hasText(productId,"商品ID不能为空");
		ProductSearchVo productSearchVo=null;
		try {
			productSearchVo=this.searchDao.searchByProductId(productId);
		} catch (Exception e) {
			LOGGER.error("searchByProductId....根据商品ID得到对应的商品信息失败",e);
			throw new ServiceException("商品搜索失败");
		}
		return productSearchVo;
	}

	
	/**
	 * 计算搜索的条数
	 * @author yangbin
	 * @date 2018年4月1日
	 * @param productSearchDto
	 * @return
	 */
	@Override
	public Integer countSearch(ProductSearchDto productSearchDto) {
		SolrQuery solrQuery=new SolrQuery();
		if(StringUtils.isNoneBlank(productSearchDto.getKeyWord())){
			//设置查询条件
			solrQuery.setQuery(productSearchDto.getKeyWord());
		}else{
			solrQuery.setQuery("*:*");
		}
		//设置查询的起始位置
		solrQuery.setStart(productSearchDto.getPageStart());
		//设置一页显示多少条数据
		solrQuery.setRows(productSearchDto.getPageSize());
		/*
		 * 设置过滤条件
		 */
		if(StringUtils.isNotBlank(productSearchDto.getAddress())){
			solrQuery.addFilterQuery("product_address:"+productSearchDto.getAddress()+"*");
		}
		if(StringUtils.isNotBlank(productSearchDto.getShopName())){
			solrQuery.addFilterQuery("product_shop_name:"+productSearchDto.getShopName());
		}
		if(StringUtils.isNotBlank(productSearchDto.getMerchantCategoryName())){
			solrQuery.addFilterQuery("product_category_merchant",productSearchDto.getMerchantCategoryName());
			//设置查询的域
			solrQuery.addField("product_category_merchant");
		}
		if(StringUtils.isNotBlank(productSearchDto.getShopCategoryName())){
			solrQuery.addFilterQuery("product_category_bg",productSearchDto.getShopCategoryName());
			//设置查询的域
			solrQuery.addField("product_category_bg");
		}
		//价格区间
		StringBuffer priceCondition=new StringBuffer();
		if(NumberUtils.isNotNull(productSearchDto.getPriceLeft())){
			priceCondition.append("[").append(productSearchDto.getPriceLeft()).append(" TO ");
		}else{
			priceCondition.append("[").append("*").append(" TO ");
		}
		if(NumberUtils.isNotNull(productSearchDto.getPriceRight())){
			priceCondition.append(productSearchDto.getPriceRight()).append("]");
		}else{
			priceCondition.append("*").append("]");
		}
		//进行范围筛选
		solrQuery.addFilterQuery("product_min_price:"+priceCondition.toString());
		//根据价格升降序
		if(NumberUtils.isNotNull(productSearchDto.getPriceState())&&productSearchDto.getPriceState().intValue()==1){
			solrQuery.addSort("product_min_price", ORDER.asc);
		}else if(NumberUtils.isNotNull(productSearchDto.getPriceState())&&productSearchDto.getPriceState().intValue()==2){
			solrQuery.addSort("product_min_price", ORDER.desc);
		}
		// 设置默认搜索域
		solrQuery.set("df", "product_keywords");
		Integer count=0;
		try {
			count=this.searchDao.countSearch(solrQuery);
		} catch (SolrServerException | IOException e) {
			LOGGER.error("countSearch...",e);
			throw new ServiceException("搜索发生错误");
		}
		return count;
	}

	/**
	 * 获取推荐商品
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param productSearchDto
	 * @return
	 */
	@Override
	public List<ProductSearchVo> queryRecommendProduct(ProductDto productDto) {
		if(productDto.getPageIndex()==null||productDto.getPageIndex()<=0){
			productDto.setPageIndex(Constants.DEFAULT_PAGE_NOW);
		}
		if(productDto.getPageSize()==null||productDto.getPageSize()<=0){
			productDto.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		}
		List<ProductSearchVo> result=new ArrayList<>();
		//1.判断是否存在商城分类
		if(CollectionUtils.isNotEmpty(productDto.getBrowseHistoryList())){
			//2.划分推荐商品比列
			Map<String, Integer> categoryCountMap = getRecommendProductByShopCategory(productDto.getBrowseHistoryList(), productDto.getPageSize());
			ProductSearchDto productSearchDto=null;
			for (BrowseHistory browseHistory : productDto.getBrowseHistoryList()) {
				productSearchDto=new ProductSearchDto();
				productSearchDto.setAddress(productDto.getAddress());
				productSearchDto.setShopCategoryName(browseHistory.getShopCategoryName());
				productSearchDto.setPageNow(productDto.getPageIndex());
				productSearchDto.setPageSize(categoryCountMap.get(browseHistory.getShopCategoryName()));
				List<ProductSearchVo> list = this.search(productSearchDto);
				result.addAll(list);
			}
		}else{
			//3.随机推荐商品
			ProductSearchDto productSearchDto=new ProductSearchDto();
			productSearchDto.setAddress(productDto.getAddress());
			productSearchDto.setPageNow(productDto.getPageIndex());
			productSearchDto.setPageSize(productDto.getPageSize());
			List<ProductSearchVo> list = this.search(productSearchDto);
			result.addAll(list);
		}
		return result;
	}
	
	/**
	 * 获取推荐商品各个分类需要推荐的数量
	 * @author yangbin
	 * @date 2018年5月10日
	 * @param list
	 * @param pageSize
	 * @return
	 */
	private Map<String,Integer> getRecommendProductByShopCategory(List<BrowseHistory> list,Integer pageSize){
		Map<String,Integer> resultMap=new LinkedHashMap<>();
		if(CollectionUtils.isNotEmpty(list)){
			int totalCount=0;
			for (BrowseHistory browseHistory : list) {
				totalCount+=browseHistory.getBrowseCount();
			}
			int total=0;
			for (BrowseHistory browseHistory : list) {
				int count=(int)((float)(browseHistory.getBrowseCount())/(float)totalCount)*pageSize;
				total+=count;
				resultMap.put(browseHistory.getShopCategoryName(),count);
			}
			if(total<pageSize){//如果推荐的商品数量少于预计要显示的商品则将剩余的数量补充到浏览程度较高的商品
				BrowseHistory browseHistory = list.get(0);
				resultMap.put(browseHistory.getShopCategoryName(), resultMap.get(browseHistory.getShopCategoryName())+(pageSize-total));
			}
		}
		return resultMap;
	}
}
