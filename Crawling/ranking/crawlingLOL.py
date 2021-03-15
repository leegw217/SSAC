import requests
from bs4 import BeautifulSoup
import json

def rankLOLC():
    url = "https://sports.news.naver.com/esports/index.nhn"

    response = requests.get(url).text
    data = BeautifulSoup(response,'html.parser')

    res = []

    now = data.select('body > #wrap > #container > #content > .home_wrap > .home_grid > .aside > #rank_template1 > .home_mn > .hmb_tbl > table > tbody > tr')
    # print(now)
    # end = str(now[0]).split("<td>")
    # for i in end:
    #     print(i)
    #     print("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq")

    for i in range(len(now)):
        end = str(now[i]).split("<td>")
        name = str(end[1]).split("</span>")[0].split(">")[-1]
        want = []
        for j in range(2,6):
            want.append(end[j].split('>')[1].split('<')[0])
            
        row = {}
        row["name"] = name
        row["win"] = want[0]
        row["lose"] = want[1]
        row["rate"] = want[2]
        row["diff"] = want[3]
        res.append(row)

    with open('rankLOL.json', 'w', encoding="utf-8") as make_file:
        json.dump(res, make_file, ensure_ascii=False, indent="\t")