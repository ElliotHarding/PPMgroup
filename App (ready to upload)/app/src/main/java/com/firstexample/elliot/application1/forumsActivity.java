package com.firstexample.elliot.application1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//displays all posts, with a little infomation for each

public class forumsActivity extends AppCompatActivity {

    //posts displayed
    List<post> posts = new ArrayList<post>();

    ListView displayList;

    databaseHelper helper = new databaseHelper(this);

    Handler handler = new Handler();
    int handlerDelay = 1500; //2s
    int errorCount = 0; //number of times connection has failed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forums);

        //get display list
        displayList = findViewById(R.id.displayList);

        //try refresh db
        try{
            helper.refreshDatabase();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error refreshing database! Try reloading page.", Toast.LENGTH_LONG).show();
        }


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

        (findViewById(R.id.homeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        (findViewById(R.id.newPost)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), addPostActivity.class);
                startActivity(intent);
            }
        });

        displayList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ForumPostsActivity.class);
                intent.putExtra("postNumber",Integer.toString(position));
                startActivity(intent);
            }
        });
    }

    void refresh(){
        boolean result = helper.checkPopulated("posts");
        if(result){
            posts.clear();

            for(int x = 0; x <  helper.numberOfPosts(); x++){
                post p = helper.getPost(x);
                posts.add(new post(p.getUsername(),"Null",p.getPostDate(),p.getPostSubject(),x));
            }

            ArrayAdapter<post> adapter = new adapterClass();
            displayList.setAdapter(adapter);
        }else{
            errorCount += 1;
            if(errorCount > 2){
                Toast.makeText(getApplicationContext(), "No access to posts, check network!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class adapterClass extends ArrayAdapter<post>{
        public adapterClass(){
            super(forumsActivity.this, R.layout.layoutpost, posts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            //get view from activity_forms.xml
            View itemView = convertView;
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.layoutpost, parent, false);
            }

            //find the post to work with
            post currentPost = posts.get(position);

            TextView subjectText = itemView.findViewById(R.id.listItem_Subject);
            subjectText.setText(currentPost.getPostSubject());

            TextView dateText = itemView.findViewById(R.id.listItem_Date);
            dateText.setText(currentPost.getPostDate());

            TextView usernameText = itemView.findViewById(R.id.listItem_Username);
            usernameText.setText(currentPost.getUsername());

           return itemView;
        }
    }
}
