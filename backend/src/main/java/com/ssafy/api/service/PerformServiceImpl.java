package com.ssafy.api.service;

import com.ssafy.api.response.PerformRes;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

@Service("performService")
public class PerformServiceImpl implements PerformService {
    @Override
    public List<PerformRes> getPerformAllList() {
        String serviceKey = "0706bcb651424a1aacad7bb9f3564895"; //나중에 다른 곳에 빼두기~

        try{

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

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("db");

            for(int temp = 0; temp < nList.getLength(); temp++){
                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                System.out.println("공연명 : " + getTagValue("prfnm", eElement));
                System.out.println("공연장르명 : " + getTagValue("genrenm", eElement));
                System.out.println("공연시작일 : " + getTagValue("prfpdfrom", eElement));
                System.out.println("공연종료일 : " + getTagValue("prfpdto", eElement));
                System.out.println("공연포스터경로 : " + getTagValue("poster", eElement));
                System.out.println("공연시설명 : " + getTagValue("fcltynm", eElement));
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // tag값의 정보를 가져오는 함수
    public static String getTagValue(String tag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        result = nlList.item(0).getTextContent();

        return result;
    }

    // tag값의 정보를 가져오는 함수
    public static String getTagValue(String tag, String childTag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        for(int i = 0; i < eElement.getElementsByTagName(childTag).getLength(); i++) {

            //result += nlList.item(i).getFirstChild().getTextContent() + " ";
            result += nlList.item(i).getChildNodes().item(0).getTextContent() + " ";
        }

        return result;
    }
}
