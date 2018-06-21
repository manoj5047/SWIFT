package iot.hustler.io.EasyDictionary.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    String audioUrl = null;


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
    public void onBindViewHolder(@NonNull final ResultViewHolder holder, int position) {
        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
        mediaPlayer[0] = new MediaPlayer();

        final Result result = results.get(position);
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.fadein));
        String pos = result.getPartOfSpeech() != null ? result.getPartOfSpeech() : "NA";
        String ipa = result.getPronunciations() != null ? result.getPronunciations().get(0).getIpa() : "NA";
        String definition = result.getSenses() != null ?
                result.getSenses().get(0).getDefinition() != null ?
                        result.getSenses().get(0).getDefinition() != null ?
                                result.getSenses().get(0).getDefinition().get(0)
                                : "NA" : "NA" : "NA";
        holder.search_query.setText(String.format("%d. %s", position + 1, queryname));
        holder.definition.setText(definition);
        holder.pronounce.setText(ipa);
        holder.phonetic.setText(pos);

//
//        //                WORsT EVER CODE I HAVE DEALT WITH BECUASE OF COMPLEX JSON PROVIDED BY PEARSON.
//        if (result.getSenses() != null && result.getSenses().size() > 0) {
//            if (result.getSenses().get(0).getGramaticalExamples() != null && result.getSenses().get(0).getGramaticalExamples().size() > 0) {
//                if (result.getSenses().get(0).getGramaticalExamples().get(0).getExamples() != null && result.getSenses().get(0).getGramaticalExamples().get(0).getExamples().size() > 0) {
//                    if (result.getSenses().get(0).getGramaticalExamples().get(0).getExamples().get(0).getAudio() != null && result.getSenses().get(0).getGramaticalExamples().get(0).getExamples().get(0).getAudio().size() > 0) {
//                        holder.player.setVisibility(View.GONE);
////                        MAKE VISIBLE WHEN AUDIO FEATURE IS AVAILABLE
//                    } else {
//                        holder.player.setVisibility(View.GONE);
//                    }
//                } else {
//                    holder.player.setVisibility(View.GONE);
//                }
//            } else {
//                holder.player.setVisibility(View.GONE);
//            }
//        } else {
//            holder.player.setVisibility(View.GONE);
//        }
//
//
//        holder.player.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    if (holder.player.getVisibility() == View.VISIBLE) {
//                        String audioUrl = "http://api.pearson.com" + result.getSenses().get(0).getGramaticalExamples().get(0).getExamples().get(0).getAudio().get(0).getUrl();
//                        if (audioUrl.length() > 0) {
//                            if (mediaPlayer[0] == null) {
//                                mediaPlayer[0] = new MediaPlayer();
//                            }
//                            mediaPlayer[0].setAudioStreamType(AudioManager.STREAM_MUSIC);
//                            mediaPlayer[0].setDataSource(audioUrl);
//                            mediaPlayer[0].prepare();
//                            mediaPlayer[0].start();
//                            Toast.makeText(activity, "Playing", Toast.LENGTH_SHORT).show();
//                            Log.d("ADAPTER URL", result.getUrl());
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                mediaPlayer[0].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        Toast.makeText(activity, "STOPPED", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return results.size() <= 0 ? 0 : results.size();
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
