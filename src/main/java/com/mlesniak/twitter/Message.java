package com.mlesniak.twitter;

/**
 * POJO for deserialization with Gson.
 */
public class Message {
    public class User {
        private String screenName;

        public String getScreenName() {
            return screenName;
        }
    }

    private String id;
    private String text;
    private User user;

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
