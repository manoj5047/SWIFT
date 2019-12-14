package iot.hustler.io.easydictionary.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import iot.hustler.io.easydictionary.R;
import iot.hustler.io.easydictionary.utils.FontUtils;

public class ResultActivity extends Activity implements View.OnClickListener {
    RelativeLayout linearLayout;
    private EditText searchView;
    private TextView dataView;
    private ImageView closeButton;
    private Button submitButton;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);
        findViews();
        overridePendingTransition(R.anim.roll_up, R.anim.roll_down);
        FontUtils.findtext_and_applyTypeface(ResultActivity.this, linearLayout);
    }

    private void findViews() {
        linearLayout = findViewById(R.id.root_header);
        searchView = findViewById(R.id.search_view);
        dataView = findViewById(R.id.data_view);
        closeButton = findViewById(R.id.close_button);
        submitButton = findViewById(R.id.submit_button);
        progressBar = findViewById(R.id.progress_bar);

        closeButton.setOnClickListener(ResultActivity.this);
        submitButton.setOnClickListener(ResultActivity.this);
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
            ResultActivity.this.finish();
        } else if (v == submitButton) {
            // Handle clicks for submitButton
        }
    }


}
