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
 * @ClassName JDService
 * @Description
 * @Author tengxiao.ma
 * @Date 2020/8/15 10:54
 **/
@Service
@Slf4j
public class JDService {


    public AjaxResult downJDProduct(String url) throws IOException {
        // 动态模拟请求数据
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
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
            // 提取HTML得到商品信息结果
            Document doc = Jsoup.parse(html);
            // 通过浏览器查看商品页面的源代码，找到信息所在的div标签，再对其进行一步一步地解析,这都需要对html代码进行分析了
            Elements ulList = (Elements) doc.select("#J_goodsList");
            liList = ulList.select(".gl-item");
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
            goodInfo.setId(item.attr("data-sku"));
            // 商品名称
            goodInfo.setName(item.select(".p-name").select("em").text());
            // 商品价格
            goodInfo.setPrice(item.select(".p-price").select("i").text());
            // 商品网址
            goodInfo.setGoodsUrl(item.select(".p-name").select("a").attr("href"));
            // 商品图片网址
            goodInfo.setImgUrl(item.select(".p-img").select("a").select("img").attr("src"));
            // 商品店铺
            goodInfo.setGoodsShop(item.select(".p-shop").select("span").select("a").attr("title"));
            //评论数量
            goodInfo.setCommit(item.select("p-commit").select("a").text());

            goodInfos.add(goodInfo);
        }
        return goodInfos;
    }

}
