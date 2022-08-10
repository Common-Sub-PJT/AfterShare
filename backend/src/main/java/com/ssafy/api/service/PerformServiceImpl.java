package com.ssafy.api.service;

import com.ssafy.api.request.PerformGetReq;
import com.ssafy.api.response.PerformCarouselRes;
import com.ssafy.api.response.PerformInfoRes;
import com.ssafy.api.response.PerformRes;
import com.ssafy.db.entity.Gugun;
import com.ssafy.db.repository.PerformRepository;
import com.ssafy.db.repository.PerformRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *	공연 API 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("PerformService")
public class PerformServiceImpl implements PerformService{

    @Autowired(required = false)
    PerformRepository performRepository;

    @Autowired
    PerformRepositorySupport performRepositorySupport;

    //-------------조다연 시작-------------
    //-------------공연 전체 목록
    @Override
    public List<PerformRes> getPerformAllList() {
        //공연 전체 목록 결과값 담을 List
        List<PerformRes> list = new ArrayList<>();
        // 공연 데이터 필수값 stdate, eddate 구하기 (3개월)
        List<String> startEndDate = getDataPeriod();

        //나중에 다른 곳에 빼두기~
        String serviceKey = "0706bcb651424a1aacad7bb9f3564895";

        try{
            //원래의 주소
            //http://www.kopis.or.kr/openApi/restful/pblprfr?service={서비스키}&stdate=20220628&eddate=20220828&rows=10&cpage=1
            String path = "http://www.kopis.or.kr/openApi/restful/pblprfr?service=";
            String stdate =startEndDate.get(1);
            String eddate=startEndDate.get(0);
            String cpage = "&cpage=1";
            String rows = "&rows=999";

            String url = path + serviceKey + stdate + eddate + cpage + rows ;

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 파싱할 tag = db
            NodeList nList = doc.getElementsByTagName("db");

            for(int temp = 0; temp < nList.getLength(); temp++){
                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;

                //tag에 맞는 값을 res에 set
                PerformRes res = PerformRes.of(getTagValue("prfnm", eElement),
                        getTagValue("genrenm", eElement),
                        getTagValue("prfpdfrom", eElement),
                        getTagValue("prfpdto", eElement),
                        getTagValue("poster", eElement),
                        getTagValue("fcltynm", eElement),
                        getTagValue("mt20id", eElement));
                //list에 res 객체 추가
                list.add(res);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        //공연 전체 목록 반환
        return list;
    }

    //-------------공연 상세정보(상세+시설상세)
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

    //공연 상세정보 API
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

    //공연시설상세정보 API
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

    public static String getTagValue(String tag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        result = nlList.item(0).getTextContent();

        return result;
    }
    //-------------조다연 끝----------------

    // ----------차송희 공연 API 파싱, 검색-----------------------------
    // 시도 이름으로 시도 코드를 가져오는 메소드
    public int searchSido(PerformGetReq searchInfo){
        int sido=performRepositorySupport.findBySidoName(searchInfo.getSidoname());
        return sido;
    }

    // 시도 코드로 구군 이름 목록을 가져오는 메소드
    @Override
    public List<Gugun> searchGugunList(int sidocode) {
        List<Gugun> gugunList = new ArrayList<>();
        gugunList = performRepositorySupport.findByGugunnameContaining(sidocode);
        return gugunList;
    }

    // 구군 코드로 구군 이름을 가져오는 메소드
    public int searchGugun(PerformGetReq searchInfo){
        int gugun=performRepositorySupport.findByGugunName(searchInfo.getSidoname(), searchInfo.getGugunname());
        return gugun;
    }

    //api xml to json 파싱
    @Override
    public List<PerformRes> searchPerform(int sidocode, int guguncode, PerformGetReq searchInfo) {

        // 공연 결과값을 담을 LIST
        List<PerformRes> res = new ArrayList<>();
        // 공연 데이터 필수값 stdate, eddate 구하기 (3개월)
        List<String> startEndDate = getDataPeriod();

        //검색 단어에 공백이 포함되어 있을 경우 공백제거
        String searchWord = searchInfo.getSearch_word();
        if(searchWord.contains(" ")){
            searchWord = removeBlank(searchInfo.getSearch_word());
        }
        try{
                // parsing할 url 지정
                String service_key = "52392218e86844f5a555bb533fb6150d";
                String stdate =startEndDate.get(1);
                String eddate=startEndDate.get(0);
                String shprfnm = searchWord;

                String shcate = searchInfo.getGenre();
                int cpage =1;
                int rows=999;
                String url ="";
                //시군구 검색없이 장르만 검색할 경우
                if(sidocode==0){
                    url = "https://www.kopis.or.kr/openApi/restful/pblprfr?service=" +service_key
                            +"&stdate="+stdate
                            +"&eddate="+eddate
                            +"&cpage="+cpage
                            +"&rows="+rows
                            +"&shprfnm="+shprfnm
                            +"&shcate="+shcate;
                }else if(guguncode==0){//시도만 검색할 경우
                    url = "https://www.kopis.or.kr/openApi/restful/pblprfr?service=" +service_key
                            +"&stdate="+stdate
                            +"&eddate="+eddate
                            +"&cpage="+cpage
                            +"&rows="+rows
                            +"&signgucode="+sidocode
                            +"&shprfnm="+shprfnm
                            +"&shcate="+shcate;
                }else{
                    url = "https://www.kopis.or.kr/openApi/restful/pblprfr?service=" +service_key
                            +"&stdate="+stdate
                            +"&eddate="+eddate
                            +"&cpage="+cpage
                            +"&rows="+rows
                            +"&signgucode="+sidocode
                            +"&signgucodesub="+guguncode
                            +"&shprfnm="+shprfnm
                            +"&shcate="+shcate;
                }
                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                Document doc = dBuilder.parse(url);

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("db");

                for(int temp = 0; temp < nList.getLength(); temp++){
                    Node nNode = nList.item(temp);
                    if(nNode.getNodeType() == Node.ELEMENT_NODE){

                        Element eElement = (Element) nNode;

                        res.add(PerformRes.of(
                                getTagValue("prfnm", eElement),
                                getTagValue("genrenm", eElement),
                                getTagValue("prfpdfrom", eElement),
                                getTagValue("prfpdto", eElement),
                                getTagValue("poster", eElement),
                                getTagValue("fcltynm", eElement),
                                getTagValue("mt20id", eElement)));


                    }	// if end
                }	// for end
        } catch (Exception e){
            e.printStackTrace();
        }	// try~catch end
        return res;
    }	// searchPerform end

    //예매상황판 전체/장르필터링
    @Override
    public List<PerformCarouselRes> getCarouselList(String catecode) {
        // 공연 결과값을 담을 LIST
        List<PerformCarouselRes> res = new ArrayList<>();
        try{
            // parsing할 url 지정
            String service_key = "52392218e86844f5a555bb533fb6150d";
            String url ="";

            // 현재 날짜 구하기
            LocalDate now = LocalDate.now();
            // 포맷 정의
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            // 포맷 적용
            String today = now.format(formatter);

            //https://kopis.or.kr/openApi/restful/boxoffice?service=52392218e86844f5a555bb533fb6150d&ststype=month&date=20220808
            url = "http://kopis.or.kr/openApi/restful/boxoffice?service="+service_key+"&ststype=month&date="+today+"&catecode="+catecode;

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("boxof");

            for(int temp = 0; temp < 5; temp++){
                Node nNode = nList.item(temp);
                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    res.add(PerformCarouselRes.of(
                            getTagValue("mt20id", eElement),
                            getTagValue("poster", eElement),
                            Integer.parseInt(getTagValue("rnum", eElement))));
                }	// if end
            }	// for end
        } catch (Exception e){
            e.printStackTrace();
        }	// try~catch end
        return res;
    }

    //현재날짜로부터 3개월전 날짜 가져오는 함수 + format (공연api필수값 시작날짜, 끝날짜)
    private List<String> getDataPeriod() {
        List<String> startEnd = new ArrayList<>();
        //현재 날짜 구하기
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyyMMdd");

        String Today = df.format(cal.getTime());

        startEnd.add(Today);

        //오늘날짜로부터 3개월전
        cal.add(Calendar.MONTH, -3);
        String ThreeMonthAgo = df.format(cal.getTime());

        startEnd.add(ThreeMonthAgo);
        return startEnd;
    }

    // 사용자가 검색한 단어의 공백 제거
    private String removeBlank(String search_word) {
        char[] searchWordTmp = search_word.toCharArray();
        String searchWord="";


            for (char c: searchWordTmp){
                if(c!=' '){
                    searchWord+=c;
                }
            }

        return searchWord;
    }
    //------차송희 끝-------------------------------------
}
