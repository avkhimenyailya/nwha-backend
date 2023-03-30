package io.grayproject.nwha.api.util;

/**
 * @author Ilya Avkhimenya
 */
public class ControllerPaths {
    private static final String API = "/api";
    private static final String VERSION = "/v1";
    private static final String MAIN = API + VERSION;

    public static class ProfileControllerPaths {
        public static final String CONTROLLER_PATH = MAIN + "/profile";

        public static final String GET_PROFILE = "";
        public static final String GET_PROFILE_BY_ID = "/{id}";
        public static final String GET_ALL_THINGS = "/things";
        public static final String GET_ALL_COLLECTIONS = "/collection";
        public static final String GET_ALL_THINGS_BY_PROFILE_ID = "/{id}/things";
        public static final String GET_ALL_COLLECTIONS_BY_PROFILE_ID = "/{id}/collections";
        public static final String PUT_UPDATE_PROFILE_BY_DTO = "";
    }


}
