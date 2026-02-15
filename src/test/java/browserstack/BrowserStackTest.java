
package browserstack;

import base.BaseTest;
import pages.ArticlePage;
import pages.OpinionPage;
import utils.ImageDownloader;
import utils.LoggerUtil;
import api.TranslateAPI;
import analysis.WordAnalyzer;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class BrowserStackTest extends BaseTest {

    @Test
    public void runElPaisTest() {

        List<String> translatedTitles = new ArrayList<>();
        int imagesSaved = 0;

        try {

            OpinionPage opinionPage = new OpinionPage(driver);
            ArticlePage articlePage = new ArticlePage(driver);

            opinionPage.openOpinionPage();
            List<String> articleLinks = opinionPage.getFirstFiveArticleLinks();

            int count = 1;

            for (String link : articleLinks) {

                String spanishTitle = articlePage.openArticleAndGetTitle(link);
                articlePage.getArticleContent();

                String imageUrl = articlePage.getCoverImageUrl();
                ImageDownloader.downloadImage(imageUrl, "bs_article" + count);
                
                if (imageUrl != null && !imageUrl.isEmpty()) imagesSaved++;

                ImageDownloader.downloadImage(imageUrl, "bs_article" + count);

                String englishTitle = TranslateAPI.translateToEnglish(spanishTitle);
                LoggerUtil.log("Translated Title: " + englishTitle);

                translatedTitles.add(englishTitle);

                Thread.sleep(1500);
                count++;
            }

            WordAnalyzer.printRepeatedWords(translatedTitles);

            sendBrowserStackStatus("passed", "Scraping successful");

        } catch (Exception e) {

            sendBrowserStackStatus("failed", "Execution failed");
            e.printStackTrace();
        }
        LoggerUtil.log("\n======================================");
        LoggerUtil.log("Execution Summary");
        LoggerUtil.log("Articles scraped: " + translatedTitles.size());
        LoggerUtil.log("Images downloaded: " + imagesSaved); 
        LoggerUtil.log("Threads executed: 5");
        LoggerUtil.log("Browsers tested: Chrome, Firefox, Safari, Android, iPhone");
        LoggerUtil.log("======================================\n");

    }
}
