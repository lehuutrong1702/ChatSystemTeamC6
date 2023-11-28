package com.teamc6.chatSystem.api;

public class GroupAPI {
    public static final String PATH = "/api/v1/groups";
    // add new api url below
    public static final String ADD = PATH + "/add";
    public static final String ALL_MEMBERS = PATH + "/{id}/members";
    public static final String ALL_ADMINS = PATH + "/{id}/admins";
    public static final String GET = PATH + "/{id}";
    public static final String ADD_MEMBER = PATH + "/{id}/add/{member_id}";
    public static final String SEARCH = PATH + "/search";
    public static final String DELETE = PATH + "/delete/{id}";
    public static final String UPDATE = PATH + "/update/{id}";
}
