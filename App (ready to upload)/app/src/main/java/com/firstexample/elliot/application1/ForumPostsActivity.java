package com.firstexample.elliot.application1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//displays the infomation of a SPECIFIC post

public class ForumPostsActivity extends AppCompatActivity {

    //posts displayed
    List<postInfo> postInfoArray = new ArrayList<postInfo>();

    ListView displayList;

    databaseHelper helper = new databaseHelper(this);

    //automatic refresh
    Handler handler = new Handler();
    int handlerDelay = 1500; //5s
    int errorCount = 0; //number of times connection has failed

    //post id with responses to be shown
    int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_posts);

        //get display list
        displayList = findViewById(R.id.displayList);

        //try refresh db
        try{
            helper.refreshDatabase();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error refreshing database! Try reloading page.", Toast.LENGTH_LONG).show();
        }

        //get post selected
        postId = Integer.parseInt(getIntent().getStringExtra("postNumber"));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
                if(!helper.checkPopulated("postInfo") && errorCount < 3){
                    handler.postDelayed(this,handlerDelay);
                }
            }
        },handlerDelay);

        findViewById(R.id.refreshButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    refresh();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error getting posts! Try reloading page.", Toast.LENGTH_LONG).show();
                }
            }
        });

        (findViewById(R.id.backButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), forumsActivity.class);
                startActivity(intent);
            }
        });

        (findViewById(R.id.newPostInfo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), addPostInfoActivity.class);
                intent.putExtra("postIdToAddTo",Integer.toString(postId));
                startActivity(intent);
            }
        });

    }

    void refresh(){
        boolean result = helper.checkPopulated("postInfo");
        if(result){
            postInfoArray.clear();

            postInfoArray = helper.getPostInfo(postId);

            ArrayAdapter<postInfo> adapter = new ForumPostsActivity.adapterClassInfo();
            displayList.setAdapter(adapter);

            //set title
            post p = helper.getPost(postId);
            TextView title = findViewById( R.id.titleText);
            title.setText(p.getPostSubject());
        }else{
            errorCount += 1;
            if(errorCount > 2){
                Toast.makeText(getApplicationContext(), "No access to post information, check network", Toast.LENGTH_LONG).show();
            }
        }

    }

    private class adapterClassInfo extends ArrayAdapter<postInfo> {
        public adapterClassInfo(){
            super(ForumPostsActivity.this, R.layout.layoutpostinfo, postInfoArray);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            //get view from activity_forms.xml
            View itemView = convertView;
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.layoutpostinfo, parent, false);
            }

            //find the postInfo to work with
            postInfo currentPostInfo = postInfoArray.get(position);

            TextView subjectText = itemView.findViewById(R.id.listItem_Text);
            subjectText.setText(currentPostInfo.getReplyString());

            TextView dateText = itemView.findViewById(R.id.listItem_Date);
            dateText.setText(currentPostInfo.getReplyDate());

            TextView usernameText = itemView.findViewById(R.id.listItem_Username);
            usernameText.setText(currentPostInfo.getReplyUsername());

            return itemView;
        }
    }
}
