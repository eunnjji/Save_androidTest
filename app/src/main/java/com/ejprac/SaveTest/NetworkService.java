package com.ejprac.SaveTest;

import com.google.gson.JsonArray;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Call interface 생성
 * 동기 또는 비동기하는 HTTP 요청을 원격 웹서버로 전송하게 함
 * HTTP 요청은 어노테이션을 사용하여 명시합니다.
 * URL 파라미터 치환과 쿼리 파라미터가 지원됨
 * 객체를 요청 body로 변환 ( JSON 형태)
 * 멀티파트 요청 body와 파일 업로드가 가능함
 *
 * */
public interface NetworkService {

    @GET("/test/test")
    Call<List<TestItem>> get_test();

    /*@POST("/test/")
    Call<TestItem> post_test(@Body TestItem post);*/


}
