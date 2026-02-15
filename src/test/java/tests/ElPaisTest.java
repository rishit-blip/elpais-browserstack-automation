package tests;

import base.BaseTest;
import pages.OpinionPage;
import pages.ArticlePage;
import utils.ImageDownloader;
import utils.LoggerUtil;
import api.TranslateAPI;
import analysis.WordAnalyzer;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class ElPaisTest extends BaseTest {

    @Test
    public void scrapeElPaisOpinion() {

        OpinionPage opinionPage = new OpinionPage(driver);
        ArticlePage articlePage = new ArticlePage(driver);

        opinionPage.openOpinionPage();
        List<String> articleLinks = opinionPage.getFirstFiveArticleLinks();

        List<String> translatedTitles = new ArrayList<>();

        int count = 1;

        
        for (String link : articleLinks) {

            String spanishTitle = articlePage.openArticleAndGetTitle(link);
            articlePage.getArticleContent();

            String imageUrl = articlePage.getCoverImageUrl();
            ImageDownloader.downloadImage(imageUrl, "article" + count);

            String englishTitle = TranslateAPI.translateToEnglish(spanishTitle);
            LoggerUtil.log("Translated Title: " + englishTitle);

            translatedTitles.add(englishTitle);

            
            try {
                Thread.sleep(1500);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            count++;
        }


        WordAnalyzer.printRepeatedWords(translatedTitles);

        LoggerUtil.log("\n===== SCRAPING COMPLETED SUCCESSFULLY =====");
    }
}
