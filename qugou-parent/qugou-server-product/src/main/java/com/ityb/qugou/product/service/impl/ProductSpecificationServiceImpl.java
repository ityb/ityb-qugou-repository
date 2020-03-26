package com.ityb.qugou.product.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ityb.qugou.base.exception.Assert;
import com.ityb.qugou.base.exception.ServiceException;
import com.ityb.qugou.base.exception.code.impl.ParamExceptionEnum;
import com.ityb.qugou.base.utils.CollectionUtils;
import com.ityb.qugou.base.utils.StringUtils;
import com.ityb.qugou.bo.product.ProductSpecificationBean;
import com.ityb.qugou.bo.product.SpecificationBean;
import com.ityb.qugou.dto.product.ProductSpecificationDto;
import com.ityb.qugou.po.product.Product;
import com.ityb.qugou.po.product.ProductSpecification;
import com.ityb.qugou.product.mapper.ProductMapper;
import com.ityb.qugou.product.mapper.ProductSpecificationMapper;
import com.ityb.qugou.product.service.ProductSpecificationService;
import com.ityb.qugou.vo.product.ProductSpecificationVo;

/**
 * 商品规格service实现类
 * 
 * @author yangbin
 * @copyright 2017-2018.yangbin.All rights reserved.
 */
@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {

	@Autowired
	private ProductSpecificationMapper productSpecificationMapper;
	@Autowired
	private ProductMapper productMapper;

	/**
	 * 获取商品规格列表
	 * 
	 * @author yangbin
	 * @date 2018年1月22日
	 * @param productSpecificationDto
	 * @return
	 */
	@Override
	public List<ProductSpecificationVo> queryProductSpecification(ProductSpecificationDto productSpecificationDto) {
		return productSpecificationMapper.queryProductSpecification(productSpecificationDto);
	}

	/**
	 * 获取产品规格数量
	 * 
	 * @author yangbin
	 * @date 2018年1月26日
	 * @param productSpecificationDto
	 * @return
	 */
	@Override
	public int countProductSpecification(ProductSpecificationDto productSpecificationDto) {
		return productSpecificationMapper.countProductSpecification(productSpecificationDto);
	}

	/**
	 * 添加商品规格列表
	 * 
	 * @author yangbin
	 * @date 2018年1月27日
	 * @param productSpecificationBean
	 * @return
	 */
	@Transactional // 开启事务
	@Override
	public void addProductSpecification(ProductSpecificationBean productSpecificationBean) {
		if (StringUtils.isBlank(productSpecificationBean.getProductName())) {
			throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(), "商品名称不能为空");
		}
		if (productSpecificationBean.getProductName().length() > 12) {
			throw new ServiceException(ParamExceptionEnum.LENGTH_EXCETION.getErrorCode(), "商品名称长度不能超过12个字符");
		}
		if (StringUtils.isBlank(productSpecificationBean.getProductNumber())) {
			throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(), "商品编号不能为空");
		}
		if (productSpecificationBean.getProductNumber().length() > 12) {
			throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(), "商品编号长度不能为空");
		}
		String productId = StringUtils.getRandomStr();
		Product product = new Product();
		product.setCreater(productSpecificationBean.getCreater());
		product.setCtime(productSpecificationBean.getCtime());
		product.setName(productSpecificationBean.getProductName());
		product.setNumber(productSpecificationBean.getProductNumber());
		product.setId(productId);
		this.productMapper.insertProduct(product);
		// 添加商品规格
		if (CollectionUtils.isEmpty(productSpecificationBean.getSpecificationList())) {
			throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(), "商品规格列表不能为空");
		}
		productSpecificationBean.getSpecificationList().forEach(specificationBean -> {
			if (specificationBean.getPrice() <= 0) {
				throw new ServiceException(ParamExceptionEnum.DATE_EXCETION.getErrorCode(), "商品规格单价不能小于等于0");
			}
			if (StringUtils.isBlank(specificationBean.getProductSpecificationName())) {
				throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(), "商品规格名称不能为空");
			}
			if (specificationBean.getProductSpecificationName().length() > 10) {
				throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(), "商品规格名称不能超过10个字符");
			}
			if (specificationBean.getStock() <= 0) {
				throw new ServiceException(ParamExceptionEnum.DATE_EXCETION.getErrorCode(), "商品库存量小于等于0");
			}
			if (specificationBean.getWeight() <= 0) {
				throw new ServiceException(ParamExceptionEnum.DATE_EXCETION.getErrorCode(), "商品净重不能小于0");
			}
			ProductSpecification productSpecification = new ProductSpecification();
			productSpecification.setId(StringUtils.getRandomStr());
			productSpecification.setPrice(specificationBean.getPrice());
			productSpecification.setStock(specificationBean.getStock());
			productSpecification.setWeight(specificationBean.getWeight());
			productSpecification.setName(specificationBean.getProductSpecificationName());
			productSpecification.setCreater(productSpecificationBean.getCreater());
			productSpecification.setCtime(productSpecificationBean.getCtime());
			productSpecification.setProductId(productId);
			this.productSpecificationMapper.insertProductSpecification(productSpecification);
		});
	}

	/**
	 * 获取商品编号下面的商品规格数量
	 * 
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param product
	 * @return
	 */
	@Override
	public int countSpecification(Product product) {
		return this.productSpecificationMapper.countSpecification(product);
	}

	/**
	 * 得到商品规格
	 * 
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param product
	 * @return
	 */
	@Override
	public ProductSpecification getProductSpecification(ProductSpecification productSpecification) {
		if(StringUtils.isBlank(productSpecification.getId())){
			if (StringUtils.isBlank(productSpecification.getCreater())
					&& StringUtils.isBlank(productSpecification.getProductId())
					&& StringUtils.isBlank(productSpecification.getName())) {
				throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(),"商品编号，规格名称不能全为空");
			}
		}
		List<ProductSpecification> list=this.productSpecificationMapper.querySpecification(productSpecification);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}

	/**
	 * 添加一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	@Override
	public void addSpecification(ProductSpecification productSpecification) {
		if(StringUtils.isBlank(productSpecification.getName())){
			throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(),"规格名称不能为空");
		}
		if(StringUtils.isBlank(productSpecification.getProductId())){
			throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(),"商品ID不能为空");
		}
		if(productSpecification.getPrice()==null||productSpecification.getPrice()<=0){
			throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(),"商品单价不能为空或者小于等于0");
		}
		if(productSpecification.getStock()==null||productSpecification.getStock()<=0){
			throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(),"商品库存量不能为空或者小于等于0");
		}
		if(productSpecification.getWeight()==null||productSpecification.getWeight()<=0){
			throw new ServiceException(ParamExceptionEnum.EMPTY_EXCETION.getErrorCode(),"商品净重不能为空或者小于等于0");
		}
		productSpecification.setId(StringUtils.getRandomStr());
		this.productSpecificationMapper.insertProductSpecification(productSpecification);
	}

	/**
	 * 根据规格ids获取对应的规格信息
	 * @author yangbin
	 * @date 2018年2月4日
	 * @param ids
	 * @return
	 */
	@Override
	public List<ProductSpecificationVo> queryProductByIds(String ids) {
		Assert.hasText(ids, "商品规格id不能为空");
		String[] idStrs = ids.split("\\s*,\\s*");
		Set<String> idSet=new HashSet<>();
		for (String id : idStrs) {
			idSet.add(id);
		}
		List<ProductSpecificationVo> list=this.productSpecificationMapper.queryProductByIds(idSet);
		return list;
	}

	/**
	 * 更新一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	@Override
	public void updateSpecification(ProductSpecification productSpecification) {
		Assert.hasText(productSpecification.getId(),"商品规格ID不能为空");
		/*Assert.NumberIsNotNull(productSpecification.getPrice(), "商品单价不能为空或者小于等于0");
		Assert.NumberIsNotNull(productSpecification.getStock(), "商品库存量不能为空或者小于等于0");
		Assert.NumberIsNotNull(productSpecification.getWeight(),"商品净重不能为空或者小于等于0");*/
		//进行更新
		this.productSpecificationMapper.updateSpecification(productSpecification);
	}

	/**
	 * 删除一个商品规格
	 * @author yangbin
	 * @date 2018年1月28日
	 * @param request
	 * @param productSpecification
	 * @return
	 */
	@Override
	public void deleteSpecification(ProductSpecification productSpecification) {
		Assert.hasText(productSpecification.getId(),"商品规格ID不能为空");
		this.productSpecificationMapper.deleteSpecification(productSpecification);
	}
	
	/**
	 * 根据商品po得到对应的规格列表
	 * @author yangbin
	 * @date 2018年2月9日
	 * @param request
	 * @param model
	 * @param product
	 * @return
	 */
	@Override
	public List<SpecificationBean> querySpecificationByPoduct(Product product) {
		Assert.notNull(product.getId(), "当前用户不能为空");
		return this.productSpecificationMapper.querySpecificationByPoduct(product);
	}


	/**
	 * 批量更新商品规格状态
	 * @author yangbin
	 * @date 2018年2月10日
	 * @param specificationList
	 * @param i
	 */
	@Override
	public void batchUpdateState(List<String> specificationList, int state) {
		this.productSpecificationMapper.batchUpdateState(specificationList, state);
	}

	/**
	 * 更新商品规格状态通过商品ID
	 * @author yangbin
	 * @date 2018年2月11日
	 * @param product
	 */
	@Override
	public void updateSpecificationStateByProduct(Product product,int state) {
		this.productSpecificationMapper.updateSpecificationStateByProduct(product,state);
	}

	/**
	 * 获取对应的商品规格信息
	 * @author yangbin
	 * @date 2018年3月19日
	 * @param productSpecification
	 * @return
	 */
	@Override
	public List<ProductSpecification> querySpecification(ProductSpecification productSpecification) {
		return this.productSpecificationMapper.querySpecification(productSpecification);
	}
}
