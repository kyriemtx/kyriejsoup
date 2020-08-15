package com.kyriemtx.jsoup.project.service;

import com.kyriemtx.jsoup.common.AjaxResult;
import com.kyriemtx.jsoup.common.HttpStatusCommon;
import com.kyriemtx.jsoup.project.entity.GoodInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TBService
 * @Description
 * @Author tengxiao.ma
 * @Date 2020/8/15 10:54
 **/
@Service
@Slf4j
public class TBService {

    public AjaxResult downTBProduct(String url) throws IOException {
        // 动态模拟请求数据
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 模拟浏览器浏览（user-agent的值可以通过浏览器浏览，查看发出请求的头文件获取）
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        // 获取响应状态码
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        // 如果状态响应码为200，则获取html实体内容或者json文件
        Elements liList = null;
        if (HttpStatusCommon.SUCCESS == statusCode) {
            String html = EntityUtils.toString(entity, Consts.UTF_8);
            Document doc  = Jsoup.parse(html);
            Elements ulList = (Elements) doc.select("div[class='view grid-nosku view-noCom']");
            liList = ulList.select("div[class='product']");
        }else {
            // 消耗掉实体
            EntityUtils.consume(response.getEntity());
        }
        return new AjaxResult(htmlToGoodInfo(liList));
    }


    /**
     * 从HTML页面抽取出商品信息
     * @param liList
     * @return
     */
    public List<GoodInfo> htmlToGoodInfo(Elements liList){
        log.info("【从爬取的页面数据中抽取出商品详细信息】请求参数：{}",liList);
        List<GoodInfo> goodInfos = new ArrayList<>();
        for (Element item : liList) {
            GoodInfo goodInfo = new GoodInfo();
            // 商品ID
            goodInfo.setId(item.select("div[class='product']").attr("data-id"));
            // 商品名称
            goodInfo.setName(item.select("div[class='productTitle productTitle-spu']").select("a").select("span").text());
            // 商品价格
            goodInfo.setPrice(item.select("p[class='productPrice']").select("em").attr("title"));
            // 商品网址
            goodInfo.setGoodsUrl(item.select("p[class='productTitle']").select("a").attr("href"));
            // 商品图片网址
            goodInfo.setImgUrl(item.select("div[class='productImg-wrap']").select("a").select("img").text());
            // 商品店铺
            goodInfo.setGoodsShop(item.select("div[class='productShop']").select("a").attr("href"));
            //月销售量
            goodInfo.setMonthNumber(item.select("p[class='productStatus']").select("span").select("em").text());

            goodInfos.add(goodInfo);
        }
        return goodInfos;
    }





}
