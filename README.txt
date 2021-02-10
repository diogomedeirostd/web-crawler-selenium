Sitemap crawler - Instructions
This app was written for this sole purpose of crawling the Hornblower websites.

1. This crawler uses a visual of the webpage to get its content.
   In that sense you need the necessary driver to allow this behavior.
   Download the necessary browser driver (I recommend using chromedriver and downloading it here https://chromedriver.chromium.org/).
   Chromedriver must be in your operating system PATH for the app to be able to execute it.

2. We're using the Selenium web driver library. I've added the current version to the folder "utility/selenium-java-3.141.59" so please add them to the project on your IDE as libraries.
   If you notice a new version has come out and want to download the Java client drivers, they're here (https://www.selenium.dev/downloads/) under the
   "Selenium Client & WebDriver Language Bindings" section;

3. This crawler gets the whole content from the website and, as such, there may be content that isn't necessary (e.g. headers from the page, information from banners)
   To solve that, you can go to the file "utility/filters.json" and following the format that you can find there already, add more information that you want to be removed that you find in
   the crawled files.

4. Just run Main replacing the current sitemap written there with the one you want to crawl. Then, you should find your files in the
   NOTE: If you want to crawl any languages besides the default english, please check the "utility/iso639-1_language-codes.json" and get the language code that you want. Then, following
   the example in Main, add the language code to the list that will be passed.

