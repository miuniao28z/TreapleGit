package main.java.tr.gts;

import com.google.common.collect.Lists;
import main.java.tr.publicutil.StaticFunction;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Treaple on 2017/8/3.
 */
public class DownTheAPIProcesser implements PageProcessor {
    

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    @Override
    public void process(Page page) {
        //page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
        //page.addTargetRequests(page.getHtml().links().regex("(https://webmagic\\.io/docs/zh/[\\w\\-])").all());
        //page.putField("title", page.getHtml().xpath("//ul[@class='articles']/li[@class='chapter ']/a/text()").all());
        List<String> listhref= Lists.newArrayList();
        page.getHtml().xpath("//ul[@class='articles']/li[@class='chapter ']/a/@href").all().forEach((x)->listhref.add("http://webmagic.io/docs/zh/"+x.replace("..","")));
        //listhref.forEach(System.out::println);
        page.addTargetRequests(listhref);
        page.putField("title", page.getHtml().xpath("//h3/text()").all());
        page.putField("content", page.getHtml().xpath("//div[@class='page-wrapper']/").all());
        if (page.getResultItems().get("content")==null){
            //skip this page
            page.setSkip(true);
        }
        //page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        String title="";
        //获取结果，然后处理
        for(Map.Entry<String, Object> entry : page.getResultItems().getAll().entrySet()){
            //System.out.println("------------[][][]"+entry.getKey() + ":\t" + entry.getValue());
            title=entry.getKey().equals("title")?entry.getValue().toString():title;
            if(entry.getKey().equals("content")) {
                try {
                    StaticFunction.ToText(entry.getValue().toString(), title + ".html");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        Spider.create(new DownTheAPIProcesser())
                .addUrl("http://webmagic.io/docs/zh")
                //.addPipeline(new FilePipeline(StaticFunction.getPwdOutPutFile()))
                .thread(5).run();
    }
}
