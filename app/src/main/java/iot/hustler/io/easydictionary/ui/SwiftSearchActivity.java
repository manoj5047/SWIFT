package iot.hustler.io.easydictionary.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import iot.hustler.io.easydictionary.R;
import iot.hustler.io.easydictionary.adapters.ResultAdapter;
import iot.hustler.io.easydictionary.model.listeners.WebResponseListener;
import iot.hustler.io.easydictionary.model.pojo.ResponseWordsDefinitionsDTO;
import iot.hustler.io.easydictionary.model.pojo.ResultsDTO;
import iot.hustler.io.easydictionary.utils.FontUtils;
import iot.hustler.io.easydictionary.utils.RestUtility;

public class SwiftSearchActivity extends Activity implements View.OnClickListener {
    RelativeLayout linearLayout;
    TextView header;
    ImageView closeButton;
    ImageView rate;
    EditText searchView;
    TextView wordName;
    TextView wordPron;
    ImageView voiceOver;
    TextView sHaed;
    TextView aHead;
    TextView syData;
    TextView antData;
    RelativeLayout wordDataLayout;
    ProgressBar progressBar;
    TextView dataView;
    ScrollView main;
    AdView adView;
    Button submitButton;
    RelativeLayout rootHeader;
    RelativeLayout root;

    private RecyclerView results_rv;
    private ResultAdapter resultsAdapter;
    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        root = findViewById(R.id.root);
        rootHeader = findViewById(R.id.root_header);
        header = findViewById(R.id.header);
        closeButton = findViewById(R.id.close_button);
        rate = findViewById(R.id.rate);
        searchView = findViewById(R.id.search_view);
        wordName = findViewById(R.id.word_name);
        wordPron = findViewById(R.id.word_pron);
        voiceOver = findViewById(R.id.voice_over);
        sHaed = findViewById(R.id.s_haed);
        aHead = findViewById(R.id.a_head);
        syData = findViewById(R.id.sy_data);
        antData = findViewById(R.id.ant_data);
        wordDataLayout = findViewById(R.id.word_data_layout);
        progressBar = findViewById(R.id.progress_bar);
        dataView = findViewById(R.id.data_view);
        results_rv = findViewById(R.id.results_rv);
        main = findViewById(R.id.main);
        submitButton = findViewById(R.id.submit_button);
        linearLayout = root;
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
        AdRequest adRequest = new AdRequest.Builder().build();
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
