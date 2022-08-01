package com.ssafy.api.service;

import com.ssafy.api.response.PerformInfoRes;
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

            // 파싱할 tag = db
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

    @Override
    public PerformInfoRes getPerformInfo(String mt20id) {

        PerformInfoRes res = null;
        //나중에 다른 곳에 빼두기~
        String serviceKey = "0706bcb651424a1aacad7bb9f3564895";

        //공연상세정보 (API 호출 및 xml 파싱)
        res = getPerformDetail(res, serviceKey, mt20id);

        //공연시설상세정보 (상세정보에서 시설ID 받아 API 호출 및 xml 파싱)
        res = getPerformPlc(res, serviceKey, res.getMt10id());
        
        //공연 상세정보+시설상세정보 반환
        return res;
    }

    private PerformInfoRes getPerformDetail(PerformInfoRes res, String serviceKey, String mt20id) {
        try{
            //ex) http://www.kopis.or.kr/openApi/restful/pblprfr/PF132236?service=0706bcb651424a1aacad7bb9f3564895
            //요청메세지 주소 형식
            //http://kopis.or.kr/openApi/restful/pblprfr/{공연아이디}?service={서비스키}
            String path = "http://www.kopis.or.kr/openApi/restful/pblprfr/" + mt20id + "?service=";
            String url = path + serviceKey;

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            //파싱할 tag = db
            NodeList nListDb = doc.getElementsByTagName("db");

            //db tag에 있는 값들을 PerformInfoRes에 set
            for(int i = 0; i < nListDb.getLength(); i++){
                Node nNode = nListDb.item(i);

                Element eElement = (Element) nNode;

                res = PerformInfoRes.of(getTagValue("mt10id", eElement), getTagValue("prfnm", eElement),
                        getTagValue("prfpdfrom", eElement), getTagValue("prfpdto", eElement),
                        getTagValue("fcltynm", eElement), getTagValue("prfcast", eElement),
                        getTagValue("prfcrew", eElement), getTagValue("prfruntime", eElement),
                        getTagValue("prfage", eElement), getTagValue("entrpsnm", eElement),
                        getTagValue("pcseguidance", eElement), getTagValue("poster", eElement),
                        getTagValue("sty", eElement), getTagValue("genrenm", eElement),
                        getTagValue("prfstate", eElement), getTagValue("openrun", eElement),
                        getTagValue("dtguidance", eElement));

                //-----styurls 속 자식태그들을 모두 담기 위한 부분
                //styurl을 하나로 담기 위한 String 변수
                String result = "";

                //styurl 태그의 값을 가져오기 위해 List 만들어주기
                //파싱할 tag = styurl
                NodeList nListStyurl = doc.getElementsByTagName("styurl");

                //styurl 정해진 개수 x, 해당 개수만큼 반복문 돌아 담아서 총 한 줄로 넣어주기 -> (,) 구분자로 값 잘라서 프론트에서 사용하기
                for(int j = 0; j < nListStyurl.getLength(); j++){
                    result += nListStyurl.item(j).getTextContent() +", ";
                }

                res = PerformInfoRes.of(res, result);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        //공연 상세정보 set 반환
        return res;
    }

    private PerformInfoRes getPerformPlc(PerformInfoRes res, String serviceKey, String mt10id) {
        try{
            //요청메세지 주소 형식
            //http://www.kopis.or.kr/openApi/restful/prfplc/FC001247?service={서비스키}
            String path = "http://www.kopis.or.kr/openApi/restful/prfplc/" + mt10id + "?service=";
            String url = path + serviceKey;

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            NodeList nListDbPlc = doc.getElementsByTagName("db");

            //db tag에 있는 값들을 PerformInfoRes에 set
            for(int i = 0; i < nListDbPlc.getLength(); i++){
                Node nNode = nListDbPlc.item(i);

                Element eElement = (Element) nNode;

                res = PerformInfoRes.of(res, getTagValue("telno", eElement), getTagValue("relateurl", eElement),
                        getTagValue("adres", eElement), getTagValue("la", eElement), getTagValue("lo", eElement));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        //공연 시설상세정보 set 반환
        return res;
    }



    // tag값의 정보를 가져오는 함수 --> 송과 겹칠 듯 확인하고 merge 하기
    public static String getTagValue(String tag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        result = nlList.item(0).getTextContent();

        return result;
    }
}
