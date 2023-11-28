package com.teamc6.chatSystem.api;

public class UserAPI {
    public static final String PATH = "/api/v1/users";
    public static final String ADD = PATH + "/add";
    public static final String ALL = "";
    public static final String GET = PATH + "/{id}";
    public static final String SEARCH = PATH + "/search";
    public static final String FILTER_USERNAME = PATH + "/filter/username";
    public static final String DELETE = PATH + "/delete/{id}";
    public static final String UPDATE = PATH + "/update/{id}";
    public static final String GROUPS = PATH + "/{id}/groups";
    public static final String FRIENDS = PATH + "/{id}/friends";
    public static final String SESSIONS = PATH + "/{id}/sessions";
    public static final String ADD_FRIENDS = PATH + "/{id1}/friends/add/{id2}";
}
