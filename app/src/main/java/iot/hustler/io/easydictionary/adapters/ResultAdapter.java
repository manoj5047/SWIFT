package iot.hustler.io.easydictionary.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import iot.hustler.io.easydictionary.R;
import iot.hustler.io.easydictionary.model.pojo.ResponseWordsDefinitionsDTO;
import iot.hustler.io.easydictionary.model.pojo.ResultsDTO;
import iot.hustler.io.easydictionary.ui.SwiftSearchActivity;
import iot.hustler.io.easydictionary.utils.FontUtils;

/**
 * Created by Sayi on 06-06-2018.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    private Activity activity;
    private String queryName;
    private ResponseWordsDefinitionsDTO dataObject;

    public ResultAdapter(SwiftSearchActivity swiftSearchActivity, ResponseWordsDefinitionsDTO results, String queryName) {
        this.activity = swiftSearchActivity;
        this.dataObject = results;
        this.queryName = queryName;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(activity.getLayoutInflater().inflate(R.layout.result_activity_item, parent, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull final ResultViewHolder holder, int position) {
        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
        mediaPlayer[0] = new MediaPlayer();

        final ResultsDTO result = dataObject.getResults().get(position);
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.fadein));
        String pos = result.getPartOfSpeech() != null ? result.getPartOfSpeech() : "NA";
        String ipa = dataObject.getPronunciation() != null ? dataObject.getPronunciation().getAll() : "NA";
        String definition = result.getDefinition() == null ? "NA" : result.getDefinition();
        holder.search_query.setText(String.format("%d. %s", position + 1, queryName));
        holder.definition.setText(definition);
        holder.pronounce.setText(ipa);
        holder.phonetic.setText(pos);


    }

    @Override
    public int getItemCount() {
        return dataObject.getResults().size() <= 0 ? 0 : dataObject.getResults().size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView search_query;
        private TextView definition;
        private TextView pronounce;
        private TextView phonetic;
        private LinearLayout root;
        private ImageView player;

        public ResultViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            search_query = itemView.findViewById(R.id.serach_query);
            definition = itemView.findViewById(R.id.Meaning);
            pronounce = itemView.findViewById(R.id.grammetical_name);
            phonetic = itemView.findViewById(R.id.phonetical_spell);
//            player = (ImageView) itemView.findViewById(R.id.player);
//            player.setOnClickListener(this);
            FontUtils.setMoonFont(activity, definition);
            FontUtils.setMoonFont(activity, search_query);

//            FontUtils.findtext_and_applyTypeface(activity, root);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
