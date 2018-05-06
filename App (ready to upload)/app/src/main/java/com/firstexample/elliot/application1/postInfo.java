package com.firstexample.elliot.application1;

public class postInfo {

    private int postID;
    private String replyString;
    private String replyDate;
    private String replyUsername;

    public int getPostID() {return postID;}

    public String getReplyString() {return replyString;}

    public String getReplyDate() {return replyDate;}

    public String getReplyUsername() {return replyUsername;}

    postInfo(int postID, String replyString, String replyDate, String replyUsername){
        this.postID = postID;
        this.replyString = replyString;
        this.replyDate = replyDate;
        this.replyUsername = replyUsername;
    }
}
