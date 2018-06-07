package iot.hustler.io.EasyDictionary.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import iot.hustler.io.EasyDictionary.R;
import iot.hustler.io.EasyDictionary.adapters.ResultAdapter;
import iot.hustler.io.EasyDictionary.model.listeners.WebResponseListener;
import iot.hustler.io.EasyDictionary.model.pojo.RootObject;
import iot.hustler.io.EasyDictionary.utils.FontUtils;
import iot.hustler.io.EasyDictionary.utils.RestUtility;

public class SwiftSearchActivity extends Activity implements View.OnClickListener {
    RelativeLayout linearLayout;
    private EditText searchView;
    private TextView dataView;
    private Button closeButton;
    private Button submitButton;
    private ProgressBar progressBar;
    private RecyclerView results_rv;
    private ResultAdapter resultsAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        findViews();
        overridePendingTransition(R.anim.roll_up, R.anim.roll_down);
        FontUtils.findtext_and_applyTypeface(SwiftSearchActivity.this, linearLayout);
    }

    private void findViews() {
        linearLayout = findViewById(R.id.root_header);
        searchView = findViewById(R.id.search_view);
        dataView = findViewById(R.id.data_view);
        closeButton = findViewById(R.id.close_button);
        submitButton = findViewById(R.id.submit_button);
        progressBar = findViewById(R.id.progress_bar);
        results_rv = findViewById(R.id.results_rv);
        results_rv.setNestedScrollingEnabled(true);
        results_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        closeButton.setOnClickListener(SwiftSearchActivity.this);
        submitButton.setOnClickListener(SwiftSearchActivity.this);
//        FontUtils.setMoonFont(this,header);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-05-31 17:58:36 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == closeButton) {
            // Handle clicks for closeButton
            SwiftSearchActivity.this.finish();
        } else if (v == submitButton) {
            // Handle clicks for submitButton
            callApi();
        }
    }

    private void callApi() {
        dataView.setVisibility(View.GONE);
        dataView.setText("");
        results_rv.setVisibility(View.GONE);
        results_rv.setAdapter(null);
        progressBar.setVisibility(View.VISIBLE);
        new RestUtility(SwiftSearchActivity.this).getMeaning(SwiftSearchActivity.this, searchView.getText().toString(), new WebResponseListener() {
            @Override
            public void onSuccess(RootObject object) {
                progressBar.setVisibility(View.GONE);
                dataView.setVisibility(View.VISIBLE);
                if (object.getResults() != null && object.getResults().size() > 0) {
                    dataView.setText(String.format("%s results are available", String.valueOf(object.getResults().size())));
                    dataView.setTextColor(ContextCompat.getColor(getApplicationContext(), (R.color.colorPrimaryDark)));
//                    dataView.setTextColor(ContextCompat.getColor(SwiftSearchActivity.this, R.color.textColorPrimary));
                    resultsAdapter = new ResultAdapter(SwiftSearchActivity.this, object.getResults(), searchView.getText().toString());
                    results_rv.setAdapter(resultsAdapter);
                    results_rv.setVisibility(View.VISIBLE);
//                    if (object.getResults().size() > 0) {
//                        dataView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorPrimary));
//                        String pos = object.getResults().get(0).getPartOfSpeech() != null ? object.getResults().get(0).getPartOfSpeech() : "NA";
//                        String ipa = object.getResults().get(0).getPronunciations() != null ? object.getResults().get(0).getPronunciations().get(0).getIpa() : "NA";
//                        String definition = object.getResults().get(0).getSenses() != null ?
//                                (object.getResults().get(0).getSenses().get(0).getDefinition() != null ?
//                                        object.getResults().get(0).getSenses().get(0).getDefinition() != null ?
//                                                object.getResults().get(0).getSenses().get(0).getDefinition().get(0)
//                                                : "NA"
//                                        : "NA")
//                                : "NA";
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                            dataView.setText(String.format("%s%s%s%s%s", pos,
//                                    System.lineSeparator(),
//                                    System.lineSeparator(),
//                                    ipa,
//                                    System.lineSeparator(),
//                                    System.lineSeparator(),
//                                    definition));
//                        } else {
//                            dataView.setText(String.format("%s\n\n%s\n\n%s", pos, ipa, definition));
//
                } else {
                    results_rv.setAdapter(null);
                    results_rv.setVisibility(View.GONE);
                    dataView.setTextColor(Color.RED);
                    dataView.setText(getString(R.string.no_word_found));
                }
            }


            @Override
            public void onError(String errormessage) {
                progressBar.setVisibility(View.GONE);
                dataView.setVisibility(View.VISIBLE);
                dataView.setText(errormessage);
                dataView.setTextColor(Color.RED);

            }
        });
    }
}
