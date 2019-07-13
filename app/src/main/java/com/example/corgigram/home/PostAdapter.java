package com.example.corgigram.home;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.corgigram.R;
import com.example.corgigram.model.Post;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Clarisa Leu-Rodriguez <clarisaleu@gmail.com>
 * Description: PostAdapter for Corgi Gram
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    // Instance Fields:
    private List<Post> mPosts;
    Context context;
    public int fragNum;  // if fragNum == 0 then inflate the feed view, if 1 then prof view

    public PostAdapter(List<Post> posts, int fragNum) {
        mPosts = posts;
        this.fragNum = fragNum;
    }

    public int getFragNum() {
        return fragNum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // for each row, inflate the layout and cache references into ViewHolder
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder viewHolder = null;
        if (fragNum == 0) {
            View postView = inflater.inflate(R.layout.layout_post, parent, false);
            viewHolder = new ViewHolder(postView, fragNum);
        } else if (fragNum == 1) {
            View profView = inflater.inflate(R.layout.layout_prof_post, parent, false);
            viewHolder = new ViewHolder(profView, fragNum);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Bind the values based on the position of the element
        final Post post = mPosts.get(position);  // Get the data according to the position

        // Populate the views according to this data
        if(fragNum == 0) {
            holder.tvUserName.setText("@"+post.getUser().getUsername());
            holder.tvUserName2.setText("@"+post.getUser().getUsername());
            holder.tvDesc.setText("    "+post.getDescription());
            final String posted = getRelativeTimeAgo(post.getCreatedAt().toString());
            holder.date.setText("Posted " +posted);
            // Get Profile Image and display if it exists
            final ParseFile img = post.getUser().getParseFile("profileImg");
            if (img != null) {
                Glide.with(context).load(img.getUrl()).into(holder.ivProf);
            }
            holder.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Launch Post Details
                    Intent it = new Intent(v.getContext(), PostDetails.class);
                    it.putExtra("username", post.getUser().getUsername());
                    it.putExtra("description", post.getDescription());
                    it.putExtra("date","Posted " + posted);
                    if(img.getUrl()!=null) {
                        it.putExtra("profImg", img.getUrl());
                        it.putExtra("img", post.getImage().getUrl());
                    }
                    v.getContext().startActivity(it);
                    return;
                }
            });
        }
        if(fragNum == 1) {
            if (!post.getImage().getUrl().equals("")) {
                Glide.with(context).load(post.getImage().getUrl()).into(holder.ivImage);
            }
        } else {
            if (!post.getImage().getUrl().equals("")) {
                Glide.with(context).load(post.getImage().getUrl()).into(holder.ivImage);
            }
        }

    }

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvUserName;
        TextView tvDesc;
        ImageView ivProf;
        TextView date;
        TextView tvUserName2;

        public ViewHolder(final View itemView, int fragNum) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // Set up views according to fragment
            // Feed fragment
            if (fragNum == 0) {
                ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
                tvUserName = (TextView) itemView.findViewById(R.id.tvUser);
                tvDesc = (TextView) itemView.findViewById(R.id.tvDescriptionPost);
                ivProf = (ImageView) itemView.findViewById(R.id.userProfPostImg);
                date = (TextView) itemView.findViewById(R.id.posted);
                tvUserName2 = (TextView) itemView.findViewById(R.id.userName2);
                // Profile Fragment
            } else if (fragNum == 1) {
                ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            }
        }
    }
}
