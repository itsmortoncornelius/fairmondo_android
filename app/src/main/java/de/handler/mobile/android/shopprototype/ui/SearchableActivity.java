package de.handler.mobile.android.shopprototype.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.interfaces.OnSearchResultListener;
import de.handler.mobile.android.shopprototype.rest.RestController;
import de.handler.mobile.android.shopprototype.rest.json.Article;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductSelectionFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductSelectionFragment_;

/**
 * Presents the Search Results
 */
@EActivity(R.layout.activity_search)
public class SearchableActivity extends AbstractActivity implements OnSearchResultListener {

    public static final String QUERY_STRING_EXTRA = "query_string_extra";


    @App
    ShopApp app;

    @Bean
    RestController restController;


    @AfterInject
    public void initRestController() {
        restController.setProductListener(this);
    }



    @AfterViews
    public void init() {
        ActionBar actionBar = this.setupActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String query;
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        } else {
            query = getIntent().getStringExtra(QUERY_STRING_EXTRA);
        }

        this.searchProducts(query);
    }


    private void searchProducts(String query) {
        if (app.isConnected()) {
            restController.getProduct(query);
        } else {
            Toast.makeText(this, getString(R.string.app_not_connected), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProductsSearchResponse(ArrayList<Article> articles) {
        ProductSelectionFragment searchResultFragment = new ProductSelectionFragment_();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ProductSelectionFragment.SELECTION_ARRAY_LIST_EXTRA, articles);
        searchResultFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_search_result_container, searchResultFragment)
                .commit();
    }

    /**
     * ActionBar settings
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                this.openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettings() {

    }
}
