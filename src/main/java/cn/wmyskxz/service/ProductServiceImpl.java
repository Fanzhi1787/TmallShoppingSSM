package cn.wmyskxz.service;

import cn.wmyskxz.mapper.ProductMapper;
import cn.wmyskxz.pojo.Category;
import cn.wmyskxz.pojo.Product;
import cn.wmyskxz.pojo.ProductExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.ArrayList;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductMapper productMapper;

    @Autowired
   ReviewService reviewService;

    @Autowired(required = false)
    public void add(Product product) { productMapper.insert(product);}

    @Autowired(required = false)
    public void  delete(Integer id) { productMapper.deleteByPrimaryKey(id);}

    @Autowired(required = false)
    public void update(Product product) { productMapper.updateByPrimaryKey(product);}

    @Autowired(required = false)
    public  Product get(Integer id) { return productMapper.selectByPrimaryKey(id);}

    @Autowired(required = false)
    public  List<Product> list(Integer category_id) {
        ProductExample example =new ProductExample();
        example.or().andCategory_idEqualTo(category_id);
        List<Product> products =productMapper.selectByExample(example);
        return products;
    }

    @Autowired(required = false)
    public void fill(List<Category> categories){
        for (Category category:categories){
            fill(category);
        }
    }

    @Autowired(required = false)
    public void fill(Category category){
        List<Product> products = list(category.getId());
        category.setProducts(products);
    }

    @Autowired(required = false)
    public void fillByRow(List<Category> categories){
        int productNumberofEachRow=8;
        for (Category category:categories){
            List<Product> products = category.getProducts();
            List<List<Product>> productByRow = new ArrayList<List<Product>>();
            for (int i = 0 ;i < products.size();i+=productNumberofEachRow){
                int size = i+productNumberofEachRow;
                size=size>products.size() ? products.size():size;
                List<Product> productsOfEachRow=products.subList(i,size);
                productByRow.add(productsOfEachRow);
            }
            category.setProductByRow(productByRow);
        }
    }

    @Autowired(required = false)
    public  void setReviewCount(Product product){
        int reviewCount = reviewService.getCount(product.getId());
        product.setReviewCount(reviewCount);
    }

    @Autowired(required = false)
    public List<Product> search(String keyword){
        ProductExample example=new ProductExample();
        example.or().andNameLike("%"+keyword+"%");
        example.setOrderByClause("id desc");
        return productMapper.selectByExample(example);
    }
}
