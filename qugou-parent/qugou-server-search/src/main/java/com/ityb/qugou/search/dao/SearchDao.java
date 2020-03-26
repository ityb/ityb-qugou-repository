package com.ityb.qugou.search.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.vo.search.ProductSearchVo;

@Repository
public class SearchDao {

	@Autowired
	private SolrClient solrClient;

	/**
	 * 商品搜索
	 * 
	 * @author yangbin
	 * @date 2018年3月17日
	 * @param solrQuery
	 * @return
	 * @throws IOException
	 * @throws SolrServerException
	 */
	public List<ProductSearchVo> search(SolrQuery solrQuery) throws SolrServerException, IOException {
		// 根据query对象进行查询
		QueryResponse response = solrClient.query(solrQuery);
		// 取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		// 搜索结果的遍历
		ProductSearchVo productSearchVo = null;
		List<ProductSearchVo> resultList = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			productSearchVo = new ProductSearchVo();
			// 取商品价格
			productSearchVo.setPrice((Float) solrDocument.get("product_min_price"));
			// 取商品卖点
			productSearchVo.setSellPoint((String) solrDocument.get("product_sell_point"));
			// 取商品名称
			productSearchVo.setShopName((String) solrDocument.get("product_shop_name"));
			// 取商品id
			productSearchVo.setProductId((String) solrDocument.get("id"));
			productSearchVo.setShopId((String) solrDocument.get("product_shop_id"));
			productSearchVo.setSpecificationId((String) solrDocument.get("product_specification_id"));
			String pic = (String) solrDocument.get("product_pic");
			// 取图片的第一张
			productSearchVo.setPic(StringUtils.isBlank(pic) ? "" : pic.split("\\s*,\\s*")[0]);
			// 取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("product_title");
			String title = "";
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("product_title");
			}
			// 设置标题
			productSearchVo.setTitle(title);

			// 添加到集合中
			resultList.add(productSearchVo);
		}
		return resultList;
	}

	/**
	 * 根据商品ID得到对应的商品信息
	 * 
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param productId
	 * @return
	 * @throws IOException
	 * @throws SolrServerException
	 */
	public ProductSearchVo searchByProductId(String productId) throws SolrServerException, IOException {
		SolrDocument solrDocument = this.solrClient.getById(productId);
		ProductSearchVo productSearchVo = new ProductSearchVo();
		// 取商品价格
		productSearchVo.setPrice((Float) solrDocument.get("product_min_price"));
		// 取最大价格
		productSearchVo.setMaxPrice((Float) solrDocument.get("product_max_price"));
		// 取商品卖点
		productSearchVo.setSellPoint((String) solrDocument.get("product_sell_point"));
		// 取商品名称
		productSearchVo.setShopName((String) solrDocument.get("product_shop_name"));
		// 取商品id
		productSearchVo.setProductId((String) solrDocument.get("id"));
		productSearchVo.setShopId((String) solrDocument.get("product_shop_id"));
		// 取图片的第一张
		productSearchVo.setPic((String) solrDocument.get("product_pic"));
		// 设置标题
		productSearchVo.setTitle((String) solrDocument.get("product_title"));
		productSearchVo.setShopCategory((String) solrDocument.get("product_category_bg"));
		productSearchVo.setMerchantCategory((String) solrDocument.get("product_category_merchant"));
		// 设置商品地址
		productSearchVo.setProductAddress((String) solrDocument.get("product_address"));
		return productSearchVo;
	}

	/**
	 * 计算搜索的数量
	 * 
	 * @author yangbin
	 * @date 2018年4月1日
	 * @param solrQuery
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public Integer countSearch(SolrQuery solrQuery) throws SolrServerException, IOException {
		// 根据query对象进行查询
		QueryResponse response = solrClient.query(solrQuery);
		// 取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		return solrDocumentList.size();
	}
}
