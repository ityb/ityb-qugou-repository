package com.ityb.qugou.product.facade.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.bo.product.ProductBean;
import com.ityb.qugou.bo.product.ProductDetailDescVo;
import com.ityb.qugou.bo.product.SpecificationBean;
import com.ityb.qugou.po.product.Product;
import com.ityb.qugou.product.constant.Constants;
import com.ityb.qugou.product.facade.productServiceFacade;
import com.ityb.qugou.product.jms.JmsMessageProvideService;
import com.ityb.qugou.product.service.ProductService;
import com.ityb.qugou.product.service.ProductSpecificationService;

@Service
public class ProductServiceFacadeImpl implements productServiceFacade {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductSpecificationService productSpecificationService;
	@Autowired
	private JmsMessageProvideService jmsMessageProvideService;

	/**
	 * 添加商品信息
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productBean
	 * @return
	 */
	@Override
	@Transactional
	public void addProduct(ProductBean productBean) {
		Assert.hasText(productBean.getCreater(), "当前用户不能为空");
		Assert.hasText(productBean.getProductId(),"商品ID不能为空");
		Assert.hasText(productBean.getSpecifications(),"商品规格列表不能为空");
		if(productBean.getAddDate()==null){
			productBean.setAddDate(new Date());
		}
		//1添加商品信息
		//1.1添加商品详细信息
		productBean.setId(StringUtils.getRandomStr());
		this.productService.addProductDeatil(productBean);
		//1.2添加商品描述
		productBean.setId(StringUtils.getRandomStr());
		this.productService.addProductDesc(productBean);
		//2修改商品规则状态
		List<String> specificationList=Arrays.asList(productBean.getSpecifications().split("\\s*,\\s*"));
		productSpecificationService.batchUpdateState(specificationList,1);//1表示未即将上架的规格
	}
	/**
	 * 修改商品信息
	 * 
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productBean
	 * @return
	 */
	@Override
	public void updateProduct(ProductBean productBean) {
		Assert.hasText(productBean.getUpdater(), "当前用户不能为空");
		Assert.hasText(productBean.getProductId(), "商品ID不能为空");
		Assert.hasText(productBean.getSpecifications(), "商品规格列表不能为空");
		if (productBean.getModifyDate() == null) {
			productBean.setModifyDate(new Date());
		}
		// 1修改商品信息
		// 1.1修改商品详细信息
		this.productService.updateProductDeatil(productBean);
		// 1.2修改商品描述
		productBean.setId(StringUtils.getRandomStr());
		this.productService.updateProductDesc(productBean);
		// 2修改商品规则状态
		List<String> specificationList = Arrays.asList(productBean.getSpecifications().split("\\s*,\\s*"));
		/**
		 * 求出所有的商品规格
		 */
		Product product = new Product();
		product.setId(productBean.getProductId());
		List<SpecificationBean> specificationBeanList = this.productSpecificationService
				.querySpecificationByPoduct(product);
		List<String> specificationPoList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(specificationBeanList)) {
			specificationBeanList.forEach(specificationBean -> {
				specificationPoList.add(specificationBean.getProductSpecificationId());
			});
		}
		// 求差集
		specificationPoList.removeAll(specificationList);
		productSpecificationService.batchUpdateState(specificationList, 1);// 1表示即将上架的规格
		if (CollectionUtils.isNotEmpty(specificationPoList)) {
			productSpecificationService.batchUpdateState(specificationPoList, 0);// 1表示取消上架的商品规格
		}
		// 3发送商品修改通知通知搜索服务导入到商品search服务
		new Thread(()->{
			jmsMessageProvideService.sendMessageByQueue(Constants.UPDATE_PRODUCT_DESTINATION_NAME, productBean.getProductId());
		}).start();
	}

	/**
	 * 获取商品详细描述等信息
	 * 
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 * @return
	 */
	@Override
	public ProductDetailDescVo getProductDetailDesc(Product product) {
		Assert.hasText(product.getId(), "商品ID不能为空");
		ProductDetailDescVo productDetailDescVo = this.productService.getProductDetailDesc(product);
		product.setState(1);
		List<SpecificationBean> specificationList = this.productSpecificationService
				.querySpecificationByPoduct(product);
		productDetailDescVo.setSpecificationList(specificationList);
		return productDetailDescVo;
	}

	/**
	 * 删除一个商品信息
	 * 
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 * @return
	 */
	@Override
	@Transactional
	public void deleteProduct(Product product) {
		Assert.hasText(product.getId(), "商品ID不能为空");
		Assert.hasText(product.getDeleter(), "操作人不能为空");
		if (product.getDtime() == null) {
			product.setDtime(new Date());
		}
		product.setMtime(product.getDtime());
		// 更新商品状态
		this.productService.updateProductState(product, 0);
		// 删除商品信息细节
		this.productService.deleteProductDetail(product);
		// 删除商品描述
		this.productService.deleteProductDesc(product);
		// 修改商品规格状态
		this.productSpecificationService.updateSpecificationStateByProduct(product, 0);
		new Thread(()->{
			jmsMessageProvideService.sendMessageByQueue(Constants.DELETE_PRODUCT_DESTINATION_NAME, product.getId());
		}).start();
	}
}
