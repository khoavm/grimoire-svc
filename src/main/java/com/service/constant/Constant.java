package com.service.constant;

import java.time.ZoneId;

public class Constant {

    public static class API {
        private static final String API_VERSION = "/api/v1";
        public static final String QUEST = API_VERSION + "/quest";

    }

    public static final ZoneId HO_CHI_MINH_TIME_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");
}
