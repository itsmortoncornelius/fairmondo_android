package de.handler.mobile.android.fairmondo.datasource.database;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig categoryDaoConfig;
    private final DaoConfig productDaoConfig;
    private final DaoConfig productCategoryDaoConfig;
    private final DaoConfig searchSuggestionDaoConfig;

    private final CategoryDao categoryDao;
    private final ProductDao productDao;
    private final ProductCategoryDao productCategoryDao;
    private final SearchSuggestionDao searchSuggestionDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        categoryDaoConfig = daoConfigMap.get(CategoryDao.class).clone();
        categoryDaoConfig.initIdentityScope(type);

        productDaoConfig = daoConfigMap.get(ProductDao.class).clone();
        productDaoConfig.initIdentityScope(type);

        productCategoryDaoConfig = daoConfigMap.get(ProductCategoryDao.class).clone();
        productCategoryDaoConfig.initIdentityScope(type);

        searchSuggestionDaoConfig = daoConfigMap.get(SearchSuggestionDao.class).clone();
        searchSuggestionDaoConfig.initIdentityScope(type);

        categoryDao = new CategoryDao(categoryDaoConfig, this);
        productDao = new ProductDao(productDaoConfig, this);
        productCategoryDao = new ProductCategoryDao(productCategoryDaoConfig, this);
        searchSuggestionDao = new SearchSuggestionDao(searchSuggestionDaoConfig, this);

        registerDao(Category.class, categoryDao);
        registerDao(Product.class, productDao);
        registerDao(ProductCategory.class, productCategoryDao);
        registerDao(SearchSuggestion.class, searchSuggestionDao);
    }
    
    public void clear() {
        categoryDaoConfig.getIdentityScope().clear();
        productDaoConfig.getIdentityScope().clear();
        productCategoryDaoConfig.getIdentityScope().clear();
        searchSuggestionDaoConfig.getIdentityScope().clear();
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public ProductCategoryDao getProductCategoryDao() {
        return productCategoryDao;
    }

    public SearchSuggestionDao getSearchSuggestionDao() {
        return searchSuggestionDao;
    }

}