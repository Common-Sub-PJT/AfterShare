package com.ssafy.api.service;

import com.ssafy.api.response.PerformRes;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

@Service("performService")
public class PerformServiceImpl implements PerformService {
    @Override
    public List<PerformRes> getPerformAllList() {
        List<PerformRes> list = new ArrayList<>();

        //나중에 다른 곳에 빼두기~
        String serviceKey = "0706bcb651424a1aacad7bb9f3564895";

        try{

            //원래의 주소
            //http://www.kopis.or.kr/openApi/restful/pblprfr?service={서비스키}&stdate=20160101&eddate=20160630&rows=10&cpage=1
            String path = "http://www.kopis.or.kr/openApi/restful/pblprfr?service=";
            String stdate = "&stdate=20220628";
            String eddate = "&eddate=00220828";
            String cpage = "&cpage=1";
            String rows = "&rows=999";
            String url = path + serviceKey + stdate + eddate + cpage + rows ;

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 제일 첫번째 태그 (dbs)
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("db");
            System.out.println(nList.getLength());

            for(int temp = 0; temp < nList.getLength(); temp++){
                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                //tag에 맞는 값을 res에 set
                PerformRes res = PerformRes.of(getTagValue("prfnm", eElement), getTagValue("genrenm", eElement),
                        getTagValue("prfpdfrom", eElement), getTagValue("prfpdto", eElement),
                        getTagValue("poster", eElement), getTagValue("fcltynm", eElement));

                //list에 res 객체 추가
                list.add(res);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        
        //공연 전체 목록 반환
        return list;
    }

    // tag값의 정보를 가져오는 함수
    public static String getTagValue(String tag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        result = nlList.item(0).getTextContent();

        return result;
    }
}
