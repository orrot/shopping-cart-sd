package com.orrot.store.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    public static final Gson GSON = new Gson();

    public static <T> List<T> extractListFrom(String json, Class<T> type) {
        var listType = TypeToken.getParameterized(ArrayList.class, type)
                .getType();
        return GSON.fromJson(json, listType);
    }

    public static <T> List<T> extractListFrom(MvcResult mvcResult, Class<T> type) throws UnsupportedEncodingException {
        var json = mvcResult.getResponse().getContentAsString();
        return extractListFrom(json, type);
    }

    public static <T> List<T> extractListFrom(MvcResult mvcResult, String jsonPath, Class<T> type) throws UnsupportedEncodingException {
        var json = JsonPath.read(mvcResult.getResponse().getContentAsString(), jsonPath).toString();;
        return extractListFrom(json, type);
    }

}
