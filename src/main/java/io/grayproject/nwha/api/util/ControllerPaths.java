package io.grayproject.nwha.api.util;

/**
 * @author Ilya Avkhimenya
 */
public class ControllerPaths {
    private static final String API = ""; // todo
    private static final String VERSION = ""; // todo
    private static final String MAIN = API + VERSION;

    public static class AuthControllerPaths {
        public static final String CONTROLLER_PATH = MAIN + "/auth";
        public static final String POST_LOGIN = "/login";
        public static final String POST_REGISTER = "/register";
        public static final String POST_REFRESH = "/refresh";
    }

    public static class ProfileControllerPaths {
        public static final String CONTROLLER_PATH = MAIN + "/profile";
        public static final String GET_PROFILE = "";
        public static final String GET_PROFILE_BY_ID = "/id/{id}";
        public static final String GET_PROFILE_BY_USERNAME = "/usr/{username}";
        public static final String GET_ALL_THINGS = "/things";
        public static final String GET_ALL_COLLECTIONS = "/collection";
        public static final String GET_ALL_THINGS_BY_PROFILE_ID = "/{id}/things";
        public static final String GET_ALL_COLLECTIONS_BY_PROFILE_ID = "/{id}/collections";
        public static final String PUT_UPDATE_PROFILE_BY_DTO = "";
    }
}
