package com.ityb.qugou.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ityb.qugou.base.constant.CommonConstants;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.exception.code.impl.ParamExceptionEnum;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.bo.product.ProductBean;
import com.ityb.qugou.bo.product.ProductDetailDescVo;
import com.ityb.qugou.dto.product.ProductDto;
import com.ityb.qugou.po.product.Product;
import com.ityb.qugou.po.product.ProductDesc;
import com.ityb.qugou.po.product.ProductDetail;
import com.ityb.qugou.product.constant.Constants;
import com.ityb.qugou.product.jms.JmsMessageProvideService;
import com.ityb.qugou.product.mapper.ProductMapper;
import com.ityb.qugou.product.service.ProductService;
import com.ityb.qugou.vo.product.ProductVo;
import com.ityb.qugou.vo.search.ProductSearchVo;

/**
 * 商品服务实现类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private JmsMessageProvideService jmsMessageProvideService;

	/**
	 * 得到一个商品
	 * 
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@Override
	public Product getProduct(Product product) {
		if (StringUtils.isBlank(product.getId()) && StringUtils.isBlank(product.getName())
				&& StringUtils.isBlank(product.getNumber()) && StringUtils.isBlank(product.getCreater())) {
			throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(), "商品ID，商品编号和商品名称不能全为空");
		}
		List<Product> productList = this.productMapper.queryProduct(product);
		if (CollectionUtils.isEmpty(productList)) {
			return null;
		}
		return productList.get(0);
	}

	/**
	 * 获取商品列表
	 * 
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param product
	 * @return
	 */
	@Override
	public List<Product> queryProduct(Product product) {
		return this.productMapper.queryProduct(product);
	}

	/**
	 * 根据dto获取对应的商品信息
	 * 
	 * @author yangbin
	 * @date 2018年2月8日
	 * @param productDto
	 * @return
	 */
	@Override
	public List<ProductVo> queryProductByProductDto(ProductDto productDto) {
		Assert.notNull(productDto.getCreater(), "当前用户不能为空");
		if (productDto.getPageIndex() != null && productDto.getPageIndex() <= 0) {
			productDto.setPageIndex(0);
		}
		if (productDto.getPageSize() != null && productDto.getPageSize() <= 0) {
			productDto.setPageSize(CommonConstants.DEFAULT_PAGE_SIZE);
		}
		if (productDto.getPageSize() != null && productDto.getPageIndex() != null) {
			productDto.setPageStart((productDto.getPageIndex() - 1) * productDto.getPageSize());
		}
		return this.productMapper.queryProductByProductDto(productDto);
	}

	/**
	 * 根据dto 查询商品列表的数量
	 * 
	 * @author yangbin
	 * @date 2018年2月8日
	 * @param productDto
	 * @return
	 */
	@Override
	public Integer countProductByProductDto(ProductDto productDto) {
		Assert.notNull(productDto.getCreater(), "当前用户不能为空");
		return this.productMapper.countProductByProductDto(productDto);
	}
	
	/**
	 * 添加商品信息
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productBean
	 */
	@Override
	public void addProductDeatil(ProductBean productBean){
		this.productMapper.insertProductDeatil(productBean);
	}

	/**
	 * 添加商品描述
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productBean
	 */
	@Override
	public void addProductDesc(ProductBean productBean){
		this.productMapper.insertProductDesc(productBean);
	}

	/**
	 * 查询商品细节
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productDetail
	 * @return
	 */
	@Override
	public ProductDetail getProductDetil(ProductDetail productDetail) {
		Assert.hasText(productDetail.getProductId(),"商品ID不能为空");
		return this.productMapper.getProductDetail(productDetail);
	}

	/**
	 * 获取商品描述根据商品Id
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param productDesc
	 * @return
	 */
	@Override
	public ProductDesc getProductDesc(ProductDesc productDesc) {
		Assert.hasText(productDesc.getProductId(),"商品ID不能为空");
		return this.productMapper.getProductDesc(productDesc);
	}

	/**
	 * 更新商品细节
	 * @author yangbin
	 * @date 2018年2月9日
	 * @param request
	 * @param model
	 * @param product
	 * @return
	 */
	@Override
	public void updateProductDeatil(ProductBean productBean) {
		this.productMapper.updateProductDeatil(productBean);
	}
	/**
	 * 修改商品描述
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productBean
	 */
	@Override
	public void updateProductDesc(ProductBean productBean) {
		this.productMapper.updateProductDesc(productBean);
	}

	/**
	 * 获取商品详细描述等信息
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param productId
	 * @return
	 */
	@Override
	public ProductDetailDescVo getProductDetailDesc(Product product) {
		return this.productMapper.getProductDetailDesc(product);
	}

	/**
	 * 删除商品详细
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 */
	@Override
	public void deleteProductDetail(Product product) {
		this.productMapper.deleteProductDetail(product);
	}

	/**
	 * 删除商品描述
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 */
	@Override
	public void deleteProductDesc(Product product) {
		this.productMapper.deleteProductDesc(product);
	}

	/**
	 * 更新商品状态
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 * @param i
	 */
	@Override
	public void updateProductState(Product product, int state) {
		this.productMapper.updateProductState(product,state);
	}

	/**
	 * 更新商品状态
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 * @return
	 */
	@Override
	public void updateStateByProductIds(List<String> productIdList, Integer state) {
		Assert.notEmpty(productIdList,"商品ID不能为空");
		Assert.isTrue(state!=null&&(state==1||state==0),"商品状态不正确");
		this.productMapper.updateStateByProductIds(productIdList,state);
		//3发送商品添加通知通知搜索服务导入到商品search服务
		StringBuffer idBuffer=new StringBuffer();
		for (String productId : productIdList) {
			idBuffer.append(productId).append(",");
		}
		//发送搜索通知
		if(state==1){//表示是商家商品
			new Thread(()->{
				jmsMessageProvideService.sendMessageByQueue(Constants.ADD_PRODUCT_DESTINATION_NAME, idBuffer.delete(idBuffer.length()-1,idBuffer.length()).toString());
			}).start();
		}
		if(state==0){//从搜索服务中移除该商品
			new Thread(()->{
				jmsMessageProvideService.sendMessageByQueue(Constants.DELETE_PRODUCT_DESTINATION_NAME, idBuffer.delete(idBuffer.length()-1,idBuffer.length()).toString());
			}).start();
		}
	}

	/**
	 * 得到热销商品
	 * @author yangbin
	 * @date 2018年5月2日
	 * @param productDto
	 * @return
	 */
	@Override
	public List<ProductSearchVo> queryHotProduct(ProductDto productDto) {
		Assert.hasText(productDto.getAddress(),"所选地址不能为空");
		return this.productMapper.queryHotProduct(productDto);
	}
}
