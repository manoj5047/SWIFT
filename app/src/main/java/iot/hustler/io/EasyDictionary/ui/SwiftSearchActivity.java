package iot.hustler.io.EasyDictionary.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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
    private ImageView closeButton;
    private Button submitButton;
    private ProgressBar progressBar;
    private RecyclerView results_rv;
    private ResultAdapter resultsAdapter;
    private AdView adView;
    private ImageView rate;


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
        rate = findViewById(R.id.rate);
        results_rv.setNestedScrollingEnabled(true);
        results_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        closeButton.setOnClickListener(SwiftSearchActivity.this);
        submitButton.setOnClickListener(SwiftSearchActivity.this);

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("A5B1E467FD401973F9F69AD2CCC13C30").build();
        adView.loadAd(adRequest);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity  object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    callApi();
                    handled = true;
                }
                return handled;
            }
        });
//        FontUtils.setMoonFont(this,header);
    }


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
