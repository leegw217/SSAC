import requests
from bs4 import BeautifulSoup
import json

def rankFootC():
    url = "https://sports.news.naver.com/kfootball/record/index.nhn"
    response = requests.get(url).text
    data = BeautifulSoup(response,'html.parser')

    res = []

    now = data.select('body > #wrap > #container > #content > .rr_wrap > .league_choice_panel > #splitGroupA > table > tbody > tr')
    idx = [2,3,4,5,8]

    for i in range(len(now)):
        end = str(now[i]).split("<td>")
        name = str(end[0]).split("<span>")[-1].split("</span>")[0]
        want = []
        for i in range(len(end)):
            if i in idx:
                want.append(end[i].lstrip("<strong>").split("<")[0])

        row = {}
        row["name"] = name
        row["score"] = want[0]
        row["win"] = want[1]
        row["draw"] = want[2]
        row["lose"] = want[3]
        row["diff"] = want[4]
        res.append(row)

    with open('rankFootA.json', 'w', encoding="utf-8") as make_file:
        json.dump(res, make_file, ensure_ascii=False, indent="\t")


    res = []

    now = data.select('body > #wrap > #container > #content > .rr_wrap > .league_choice_panel > #splitGroupB > table > tbody > tr')
    idx = [2,3,4,5,8]

    for i in range(len(now)):
        end = str(now[i]).split("<td>")
        name = str(end[0]).split("<span>")[-1].split("</span>")[0]
        want = []
        for i in range(len(end)):
            if i in idx:
                want.append(end[i].lstrip("<strong>").split("<")[0])

        row = {}
        row["name"] = name
        row["score"] = want[0]
        row["win"] = want[1]
        row["draw"] = want[2]
        row["lose"] = want[3]
        row["diff"] = want[4]
        res.append(row)

    with open('rankFootB.json', 'w', encoding="utf-8") as make_file:
        json.dump(res, make_file, ensure_ascii=False, indent="\t")

