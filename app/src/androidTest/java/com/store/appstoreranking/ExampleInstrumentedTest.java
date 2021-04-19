package com.store.appstoreranking;

import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.store.appstoreranking.constact.Constact;
import com.store.appstoreranking.manger.DBHelper;
import com.store.appstoreranking.model.AppDetailEntry;
import com.store.appstoreranking.model.AppEntry;
import com.store.appstoreranking.model.AppsModel;
import com.store.appstoreranking.model.AppsRankingEntry;
import com.store.appstoreranking.net.ApiService;
import com.store.appstoreranking.net.NetUtils;
import com.store.appstoreranking.net.retrofit.ApiException;
import com.store.appstoreranking.net.retrofit.ResponseResultListener;
import com.store.appstoreranking.net.retrofit.RetrofitClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = "ExampleInstrumentedTest";

    private static final int ROW_LIMIT = 10;
    private static final int ROW_RANKING_LIMIT = 100;
    private static final int THREAD_COUNT = 100;
    private static final int SINGLE_COUNT = 1;


    private ApiService apiService;
    private DBHelper dbHelper;

    @Before
    public void setUp() {
        apiService = creatApiService();
        dbHelper = new DBHelper(InstrumentationRegistry.getTargetContext());
    }

    /**
     * 测试场景：调用接口，获取10条数据
     * 前置条件：联网
     * 预期结果：获取到10条结果
     */
    @Test
    public void testGetRecommendApi() {
        countDownLatch = new CountDownLatch(SINGLE_COUNT);
        NetUtils.subscribe(apiService.getTopGrossingApplications(String.valueOf(ROW_LIMIT)), null,
                new ResponseResultListener<AppsRankingEntry>() {
                    @Override
                    public void success(AppsRankingEntry appRankingInfoModel) {
                        Log.e(TAG,"testGetRecommendApi");
                        List<AppEntry> appEntries = null;
                        if (appRankingInfoModel != null) {
                            appEntries = appRankingInfoModel.getFeed().getEntry();
                        }
                        if (appEntries != null && appEntries.size() == ROW_LIMIT) {
                            assertTrue(true);
                        } else {
                            fail();
                        }
                        countDownLatch.countDown();
                    }

                    @Override
                    public void failure(ApiException e) {
                        Log.e(TAG, e.getMessage());
                        fail();
                    }
                });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(SINGLE_COUNT, 1);
    }


    private CountDownLatch countDownLatch = null;

    /**
     * 测试场景：多线程并发调用获取app列表的接口，每次调用获取10条数据
     * 前置条件：联网，多线程并发调用
     * 预期结果：能正确获取数据，每次获取10条
     */
    @Test
    public void testGetRecommendApiMuiltThread() {
        countDownLatch = new CountDownLatch(THREAD_COUNT);
        List<List<AppEntry>> datas = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NetUtils.subscribe(apiService.getTopGrossingApplications(String.valueOf(ROW_LIMIT)), null,
                            new ResponseResultListener<AppsRankingEntry>() {
                                @Override
                                public void success(AppsRankingEntry appRankingInfoModel) {
                                    Log.e(TAG,"testGetRecommendApiMuiltThread");
                                    List<AppEntry> appEntries = null;
                                    if (appRankingInfoModel != null) {
                                        appEntries = appRankingInfoModel.getFeed().getEntry();
                                    }
                                    if (appEntries == null || appEntries.size() != ROW_LIMIT) {
                                        fail();
                                    }
                                    datas.add(appEntries);
                                    countDownLatch.countDown();
                                }

                                @Override
                                public void failure(ApiException e) {
                                    fail();
                                    countDownLatch.countDown();
                                }
                            });
                }
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(THREAD_COUNT, datas.size());
    }

    /**
     * 测试场景：调用排行接口之后调用app详情，获取100条数据,并将数据存储到数据库
     * 前置条件：联网
     * 预期结果：获取100条数据,并将数据存储到数据库
     */
    @Test
    public void testGetRankingApi() {
        countDownLatch = new CountDownLatch(SINGLE_COUNT);
        NetUtils.subscribe(apiService.getTopFreeApplications(String.valueOf(ROW_RANKING_LIMIT)), null,
                new ResponseResultListener<AppsRankingEntry>() {
                    @Override
                    public void success(AppsRankingEntry appRankingInfoModel) {
                        getAppDetail(appRankingInfoModel.getFeed().getEntry());
                    }

                    @Override
                    public void failure(ApiException e) {
                        Log.e(TAG, e.getMessage());
                        fail();
                    }
                });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void getAppDetail(List<AppEntry> appsModel) {
        List<List<AppsModel>> appArrayList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < appsModel.size(); i++) {
            if (i < appsModel.size() - 1) {
                stringBuilder.append(appsModel.get(i).getId().getAttributes().getId() + ",");
            } else {
                stringBuilder.append(appsModel.get(i).getId().getAttributes().getId());
            }
        }
        HashMap<String, String> hashMap = new HashMap<>(1);
        hashMap.put("id", String.valueOf(stringBuilder));
        NetUtils.subscribe(NetUtils.getApiService().getLookup(hashMap),
                null,
                new ResponseResultListener<AppDetailEntry>() {
                    @Override
                    public void success(AppDetailEntry appDetailEntry) {
                        appArrayList.clear();
                        // 将数据分成每页10条
                        int pageNum = (appsModel.size() - 1) / ROW_LIMIT+1;
                        for (int i = 0; i < pageNum; i++) {
                            ArrayList<AppsModel> appEntries = new ArrayList<>();
                            for (int i1 = 0; i1 < ROW_LIMIT; i1++) {
                                AppEntry appEntry = appsModel.get(i1 + ROW_LIMIT * i);
                                AppDetailEntry.ResultsModel resultsModel = appDetailEntry.getResults().get(i1 + ROW_LIMIT * i);
                                AppsModel appsModel1 = new AppsModel(appEntry, resultsModel);
                                appEntries.add(appsModel1);
                            }
                            appArrayList.add(appEntries);
                        }
                        if (dbHelper.addApps(appArrayList.get(0), DBHelper.TABLE_RANKING)) {
                            assertTrue(true);
                        } else {
                            fail("数据插入失败");
                        }
                        countDownLatch.countDown();
                    }

                    @Override
                    public void failure(ApiException e) {
                        Log.e(TAG, e.getMessage());
                        fail();
                    }
                });

    }


    @After
    public void tearDown() {
        apiService = null;
        if (countDownLatch != null) {
            countDownLatch = null;
        }
    }

    public ApiService creatApiService() {
        return RetrofitClient.getInstance().init(Constact.BASE_API).createService(ApiService.class);
    }
}
