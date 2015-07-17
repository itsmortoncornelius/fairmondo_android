package de.handler.mobile.android.fairmondo.presentation.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.RestCommunicator;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.data.datasource.SearchSuggestionProvider;
import de.handler.mobile.android.fairmondo.data.interfaces.OnDetailedProductListener;
import de.handler.mobile.android.fairmondo.data.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.presentation.FragmentHelper;
import de.handler.mobile.android.fairmondo.presentation.controller.ProgressController;
import de.handler.mobile.android.fairmondo.presentation.controller.UIInformationController;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductSelectionFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductSelectionFragment_;

/**
 * Presents the Search Results.
 */
@EActivity(R.layout.activity_search)
@OptionsMenu(R.menu.search)
public class SearchableActivity extends AbstractActivity implements OnDetailedProductListener, OnSearchResultListener {
    @Bean
    ProgressController mProgressController;

    @App
    FairmondoApp mApp;

    @Bean
    RestCommunicator mRestCommunicator;

    @ViewById(R.id.activity_search_toolbar)
    Toolbar mToolbar;

    private List<Product> mProducts;
    private int mProductsCount;


    @AfterInject
    public void initRestController() {
        mRestCommunicator.setProductListener(this);
        mRestCommunicator.setDetailedProductListener(this);
    }

    @AfterViews
    void init() {
        setHomeUpEnabled(mToolbar);
        this.processSearch(getIntent());
    }

    private void processSearch(@NonNull final Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);

            // Store in Search Suggestion Provider
            final SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            this.searchProducts(query);
        }
    }

    private void searchProducts(@Nullable final String query) {
        if (mApp.isConnected()) {
            if (query != null) {
                mRestCommunicator.getProducts(query);
                mProgressController.startProgress(getSupportFragmentManager(), android.R.id.content);
            } else {
                finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.app_not_connected), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProductsSearchResponse(final List<Product> products) {
        if (products != null && !products.isEmpty()) {
            this.getDetailedProducts(products);
        } else {
            this.initSelectionFragment(null);
        }
    }

    private void initSelectionFragment(@Nullable final List<Product> products) {
        ProductSelectionFragment selectionFragment = new ProductSelectionFragment_();
        if (products != null) {
            selectionFragment = ProductSelectionFragment_.builder().mProductsParcelable(Parcels.wrap(List.class, products)).build();
        }

        try {
            FragmentHelper.replaceFragmentWithTagToBackStack(R.id.activity_search_result_container, selectionFragment, getSupportFragmentManager(), "selectionFragment");
        } catch (IllegalStateException e) {
            // TODO send exception to fairmondo server
            UIInformationController.displaySnackbarInformation(findViewById(android.R.id.content), e.getMessage());
        }
    }

    // The basic product information has been received,
    // now query more detailed information about each product
    private void getDetailedProducts(@NonNull final List<Product> products) {
        // Set product count as listener responds to each single product
        // and app needs to react when all products have finished loading
        mProductsCount = products.size();
        mProducts = new ArrayList<>();
        for (final Product product : products) {
            mRestCommunicator.getDetailedProduct(product.getSlug());
        }
    }

    @Override
    public void onDetailedProductResponse(@Nullable final Product product) {
        mProducts.add(product);
        if (mProducts.size() == mProductsCount) {
            this.mProgressController.stopProgress();
            this.initSelectionFragment(mProducts);
        }
    }
}
