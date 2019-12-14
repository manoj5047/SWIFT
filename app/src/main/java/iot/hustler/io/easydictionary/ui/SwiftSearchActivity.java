package iot.hustler.io.easydictionary.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import iot.hustler.io.easydictionary.R;
import iot.hustler.io.easydictionary.adapters.ResultAdapter;
import iot.hustler.io.easydictionary.model.listeners.WebResponseListener;
import iot.hustler.io.easydictionary.model.pojo.ResponseWordsDefinitionsDTO;
import iot.hustler.io.easydictionary.model.pojo.ResultsDTO;
import iot.hustler.io.easydictionary.utils.FontUtils;
import iot.hustler.io.easydictionary.utils.RestUtility;

public class SwiftSearchActivity extends Activity implements View.OnClickListener {
    RelativeLayout linearLayout;
    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.close_button)
    ImageView closeButton;
    @BindView(R.id.rate)
    ImageView rate;
    @BindView(R.id.search_view)
    EditText searchView;
    @BindView(R.id.word_name)
    TextView wordName;
    @BindView(R.id.word_pron)
    TextView wordPron;
    @BindView(R.id.voice_over)
    ImageView voiceOver;
    @BindView(R.id.s_haed)
    TextView sHaed;
    @BindView(R.id.a_head)
    TextView aHead;
    @BindView(R.id.sy_data)
    TextView syData;
    @BindView(R.id.ant_data)
    TextView antData;
    @BindView(R.id.word_data_layout)
    RelativeLayout wordDataLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.data_view)
    TextView dataView;
    @BindView(R.id.results_rv)
    RecyclerView resultsRv;
    @BindView(R.id.main)
    ScrollView main;
    @BindView(R.id.adView)
    AdView adView;
    @BindView(R.id.submit_button)
    Button submitButton;
    @BindView(R.id.root_header)
    RelativeLayout rootHeader;
    @BindView(R.id.root)
    RelativeLayout root;

    private RecyclerView results_rv;
    private ResultAdapter resultsAdapter;
    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        ButterKnife.bind(this);
        findViews();
        overridePendingTransition(R.anim.roll_up, R.anim.roll_down);
        FontUtils.findtext_and_applyTypeface(SwiftSearchActivity.this, linearLayout);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int ttsLang = textToSpeech.setLanguage(Locale.US);
                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findViews() {

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
                } catch (ActivityNotFoundException anfe) {
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
            public void onSuccess(final ResponseWordsDefinitionsDTO object) {
                progressBar.setVisibility(View.GONE);
                dataView.setVisibility(View.VISIBLE);
                if (object.getResults() != null && object.getResults().size() > 0) {
                    wordDataLayout.setVisibility(View.VISIBLE);
                    wordName.setText(searchView.getText().toString());
                    wordPron.setText(object.getPronunciation().getAll());
                    StringBuilder synonyms = new StringBuilder();
                    StringBuilder antonyms = new StringBuilder();
                    for (ResultsDTO resultsDTO : object.getResults()) {
                        if (!resultsDTO.getSynonyms().isEmpty()) {
                            for (String syn : resultsDTO.getSynonyms()) {
                                synonyms.append("\n").append(syn);
                            }
                        }
                        if (!resultsDTO.getAntonyms().isEmpty()) {
                            for (String syn : resultsDTO.getAntonyms()) {
                                antonyms.append("\n").append(syn);
                            }
                        }

                    }

                    if (synonyms.length() > 0) {
                        syData.setText(synonyms);
                    } else {
                        syData.setVisibility(View.GONE);
                        sHaed.setVisibility(View.GONE);
                    }

                    if (antonyms.length() > 0) {
                        antData.setText(antonyms);
                    } else {
                        antData.setVisibility(View.GONE);
                        aHead.setVisibility(View.GONE);
                    }
                    voiceOver.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int status = textToSpeech.speak(object.getPronunciation().getAll(), TextToSpeech.QUEUE_FLUSH, null);
                            if (status == TextToSpeech.ERROR) {
                                Log.e("TTS", "Error in converting Text to Speech!");
                                Toast.makeText(getApplicationContext(), "Sorry,Unavailable Now..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    dataView.setText(String.format("%s results are available", String.valueOf(object.getResults().size())));
                    dataView.setTextColor(ContextCompat.getColor(getApplicationContext(), (R.color.colorPrimaryDark)));

                    resultsAdapter = new ResultAdapter(SwiftSearchActivity.this, object, searchView.getText().toString());
                    results_rv.setAdapter(resultsAdapter);
                    results_rv.setVisibility(View.VISIBLE);
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
