import requests
from bs4 import BeautifulSoup
import json


def rankBaseC():
    url = "https://sports.news.naver.com/kbaseball/record/index.nhn?category=kbo"
    response = requests.get(url).text
    data = BeautifulSoup(response,'html.parser')

    res = []

    now = data.select('body > #wrap > #container > #content > .tb_kbo > .rr_wrap > .tbl_box > table > #regularTeamRecordList_table > tr')


    idx = [2,3,4,5,7]
    for i in range(len(now)):
        end = str(now[i]).split("<td>")
        name = str(end[0]).split("</span>")[0].split(">")[-1]
        want = []
        for j in range(len(end)):
            if j in idx:
                want.append(end[j].split('>')[1].split('<')[0])
                
        row = {}
        row["name"] = name
        row["win"] = want[0]
        row["lose"] = want[1]
        row["draw"] = want[2]
        row["rate"] = want[3]
        row["cont"] = want[4]
        res.append(row)

    with open('rankBase.json', 'w', encoding="utf-8") as make_file:
        json.dump(res, make_file, ensure_ascii=False, indent="\t")
