package iot.hustler.io.EasyDictionary.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import iot.hustler.io.EasyDictionary.R;
import iot.hustler.io.EasyDictionary.model.pojo.Result;
import iot.hustler.io.EasyDictionary.ui.SwiftSearchActivity;
import iot.hustler.io.EasyDictionary.utils.FontUtils;

/**
 * Created by Sayi on 06-06-2018.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    Activity activity;
    ArrayList<Result> results;
    String queryname;

    public ResultAdapter(SwiftSearchActivity swiftSearchActivity, ArrayList<Result> results, String queryname) {
        this.activity = swiftSearchActivity;
        this.results = results;
        this.queryname = queryname;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(activity.getLayoutInflater().inflate(R.layout.result_activity_item, parent, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        Result result = results.get(position);
holder.itemView.setAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(),R.anim.fadein));
        String pos = result.getPartOfSpeech() != null ? result.getPartOfSpeech() : "NA";
        String ipa = result.getPronunciations() != null ? result.getPronunciations().get(0).getIpa() : "NA";
        String definition = result.getSenses() != null ?
                result.getSenses().get(0).getDefinition() != null ?
                        result.getSenses().get(0).getDefinition() != null ?
                                result.getSenses().get(0).getDefinition().get(0)
                                : "NA" : "NA" : "NA";
        holder.search_query.setText(String.format("%d. %s", position+1, queryname));
        holder.definition.setText(definition);
        holder.pronounce.setText(ipa);
        holder.phonetic.setText(pos);

    }

    @Override
    public int getItemCount() {
        return results.size() <= 0 ? 0 : results.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView search_query;
        TextView definition;
        TextView pronounce;
        TextView phonetic;
        LinearLayout root;

        public ResultViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            search_query = itemView.findViewById(R.id.serach_query);
            definition = itemView.findViewById(R.id.Meaning);
            pronounce = itemView.findViewById(R.id.grammetical_name);
            phonetic = itemView.findViewById(R.id.phonetical_spell);
            FontUtils.setMoonFont(activity,definition);
            FontUtils.setMoonFont(activity,search_query);
//            FontUtils.findtext_and_applyTypeface(activity, root);
        }
    }
}
