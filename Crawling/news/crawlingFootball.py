from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.wait import WebDriverWait
from bs4 import BeautifulSoup
from selenium import webdriver
import time
import json
import requests

def newsFootC():
    options = webdriver.ChromeOptions()
    options.add_experimental_option('excludeSwitches', ['enable-logging']) #selenium 메시지 팝업 제거
    browser = webdriver.Chrome(executable_path="chromedriver.exe", options=options)
    browser.get(url='https://sports.news.naver.com/kfootball/news/index.nhn?isphoto=N&type=popular')
    time.sleep(1)

    html = browser.page_source
    soup = BeautifulSoup(html, 'html.parser')

    titles = soup.select('body > #wrap > #container > #content > .news_wrap > .content > .content_area > .news_list > ul > li > .text > .title > span')
    contents = soup.select('body > #wrap > #container > #content > .news_wrap > .content > .content_area > .news_list > ul > li > .text > .desc')
    thmbs = soup.select('body > #wrap > #container > #content > .news_wrap > .content > .content_area > .news_list > ul > li > .thmb > img')
    links = soup.select('body > #wrap > #container > #content > .news_wrap > .content > .content_area > .news_list > ul > li > a')
    distributers = soup.select('body > #wrap > #container > #content > .news_wrap > .content > .content_area > .news_list > ul > li > .text > .source > .press')
    date = soup.select('body > #wrap > #container > #content > .news_wrap > .content > .content_area > .news_list > ul > li > .text > .source > .time')

    # print(distributers)
    # print(date)

    dist = []
    for i in distributers:
        i = str(i).split(">")[1].split("<")[0]
        dist.append(i)

    # tim = []
    # for i in date:
    #     i = str(i).split("<")[-2].split(">")[-1]
    #     tim.append(i)

    lnk = []
    for i in links:
        i = str(i)
        x,y = i.split('href="')
        l = y.split()
        li = l[0].split("read.")
        x,y = li[1].split("amp;")
        url = "https://sports.news.naver.com/news."+x+y.rstrip('"')
        # print(ln)

        response = requests.get(url).text
        data = BeautifulSoup(response,'html.parser')
        link = str(data.select('body > #wrap > #container > #content > .news_wrap > .content > .content_area > .news_headline > .info > .press_link')).split('href="')[1].split('"')[0].replace("amp;","")
        # print(link)
        lnk.append(link)

    img = []
    for i in thmbs:
        i = str(i)
        x,y,z = i.split("src=")
        x,y = z.split()
        img.append(x.strip('"'))

    cont = []
    for i in contents:
        i = str(i)
        x,y,z = i.split(">")
        x,y = y.split("<")
        cont.append(x)

    # print(thmbs)
    # print(day.text, tiktok.text, '기준 실시간 급상승 검색어')
    news = []
    for i in range(20):
        new = {}
        new["title"] = str(titles[i]).lstrip("<span>").rstrip("</span>")
        new["content"] = cont[i]
        new["imgsrc"] = img[i]
        new["link"] = lnk[i]
        new["distributer"] = dist[i]
        # new["time"] = tim[i]
        news.append(new)

    with open('newsFoot.json', 'w', encoding="utf-8") as make_file:
        json.dump(news, make_file, ensure_ascii=False, indent="\t")


    browser.quit()