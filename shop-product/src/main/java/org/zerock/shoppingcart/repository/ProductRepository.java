package org.zerock.shoppingcart.repository;

import java.io.IOException;
import java.util.Date;

import java.util.List;
 
import javax.persistence.NoResultException;
 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.zerock.shoppingcart.domain.Product;
import org.zerock.shoppingcart.form.ProductForm;
import org.zerock.shoppingcart.model.ProductInfo;
import org.zerock.shoppingcart.pagination.PaginationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
 
@Transactional
@Repository
public class ProductRepository {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    public Product findProduct(String code) {
        try {
            String sql = "Select e from " + Product.class.getName() + " e Where e.code =:code ";
 
            Session session = this.sessionFactory.getCurrentSession();
            Query<Product> query = session.createQuery(sql, Product.class);
            query.setParameter("code", code);
            return (Product) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
 
    public ProductInfo findProductInfo(String code) {
        Product product = this.findProduct(code);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getCode(), product.getName(), product.getPrice());
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void delete(String code) {
    	try {
    		String sql = "delete from Product where code = :code1 ";
    		
    		Session session = this.sessionFactory.getCurrentSession();
    		Query query = session.createQuery(sql);
    		query.setParameter("code1",code);
    		
    		int rowsAffected = query.executeUpdate();
    		if (rowsAffected > 0) {
    		    System.out.println("Deleted " + rowsAffected + " rows.");
    		}

    	} catch (NoResultException e) {
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(ProductForm productForm) {
 
        Session session = this.sessionFactory.getCurrentSession();
        String code = productForm.getCode();
 
        Product product = null;
 
        boolean isNew = false;
        if (code != null) {
            product = this.findProduct(code);
        }
        if (product == null) {
            isNew = true;
            product = new Product();
            product.setCreateDate(new Date());
        }
        product.setCode(code);
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
 
        if (productForm.getFileData() != null) {
            byte[] image = null;
            try {
                image = productForm.getFileData().getBytes();
            } catch (IOException e) {
            }
            if (image != null && image.length > 0) {
                product.setImage(image);
            }
        }
        if (isNew) {
            session.persist(product);
        }
        // If error in DB, Exceptions will be thrown out immediately
        session.flush();
    }
 
    public List<ProductInfo> queryProducts(String likeName) {
        String sql = "Select new " + ProductInfo.class.getName() 
                + "(p.code, p.name, p.price) " + " from "
                + Product.class.getName() + " p ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }
        sql += " order by p.createDate desc ";
        
        Session session = this.sessionFactory.getCurrentSession();
        Query<ProductInfo> query = session.createQuery(sql, ProductInfo.class);
        
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        
        List<ProductInfo> results = query.list();
        return results;
    }
 
}