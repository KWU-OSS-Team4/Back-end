package com.witheat.WithEatServer.Service;

import com.witheat.WithEatServer.Domain.entity.Height;
import com.witheat.WithEatServer.Domain.entity.Member;
import com.witheat.WithEatServer.Domain.entity.Suggest;
import com.witheat.WithEatServer.Domain.entity.Weight;
import com.witheat.WithEatServer.Exception.BaseException;
import com.witheat.WithEatServer.Repository.MemberRepository;
import com.witheat.WithEatServer.Repository.SuggestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SuggestService {
    private final MemberRepository memberRepository;
    private final SuggestRepository suggestRepository;
    private final MemberService memberService;

    //사용자의 selecet 보기
    public Long createSuggestId(Long memberId, String plan_name)
    {
        //1.User id get 해오기 -> 이러기 위해 userId를 인자로 받아야함
        Member member = memberRepository.findById(memberId).orElseThrow(()
        -> new BaseException(404,"member not found"));

        //엔티티 생성 이후, suggestId 생성 및 사용자의 plan_name 저장
        //Suggest 엔티티를 메모리에 생성
        Suggest suggest = Suggest.builder()
                .plan_name(plan_name)
                .build();

        //해당 엔티티의 planName 필드에 값을 설정
        return suggestRepository.save(suggest).getSuggest_id();
    }

    //사람의 필요한 열량 구하는 메서드
    public int calKcal(Long memberId)
    {
        //최신 정보를 받아오는 메서드
        //Member member = memberRepository.findById(memberId).orElseThrow(()
        //-> new BaseException(404, "member not found"));
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new BaseException(HttpStatus.NO_CONTENT.value(), "Member not found"));
        //List<Suggest> suggests = suggestRepository.findByMemberId(memberId);

        List<Suggest> suggests = member.getSuggests();

        if (suggests.isEmpty()){
            return -1;
        }

        Suggest suggest = suggests.get(0);

        String plan_name = suggest.getPlan_name();
        //최종 키와 몸무게 받아오기
        Height latestHeight = memberService.getLatestHeight(member);
        Weight latestWeight = memberService.getLatestWeight(member);

        //사용자의 필요한 열량 구하기
        int memberKcal;
        //표준체중 구하기
        int standardWeight = latestHeight.getHeight() * latestHeight.getHeight();

        //남자
        if(member.getGender() == true)
        {
            standardWeight = standardWeight * 22;
        } else if (member.getGender() == false) {
            standardWeight = standardWeight * 21;
        }else{
            //예외 처리 하기
            // BaseException("")
        }

        //비만도 체크하기
        String Obesity;
        int ObesityLevel;

        ObesityLevel = latestWeight.getWeight() / standardWeight;
        ObesityLevel = ObesityLevel * 100;

        if(ObesityLevel < 90){
            Obesity = "underbody";
        } else if (ObesityLevel > 120){
            Obesity = "overbody";
        }else{
            Obesity = "normal";
        }

        if(Obesity == "underbody"){
            memberKcal = standardWeight * 35;
        } else if (Obesity == "normal") {
            memberKcal = standardWeight * 30;
        } else{
            memberKcal = standardWeight * 25;
        }

        switch (plan_name){
            case "유지" :
            case "당뇨 환자들을 위한 식단":
                return  memberKcal;
            case "감량":
                return memberKcal - 500;
            case "증량" :
                return memberKcal + 500;

        }
        return memberKcal;
    }

    //탄단지 칼로리 비율 사용자에게 보여주기
    //<**>
    public Suggest calNutuient(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new BaseException(HttpStatus.NO_CONTENT.value(), "Member not found"));
        //List<Suggest> suggests = suggestRepository.findByMemberId(memberId);

        List<Suggest> suggests = member.getSuggests();

        if (suggests.isEmpty()){
            return null;
        }

        Suggest suggest = suggests.get(0);

        String plan_name = suggest.getPlan_name();

        int memberKcal = calKcal(memberId);

        if(plan_name == "당뇨 환자들을 위한 식단"){
            double doublecar = memberKcal * 0.4;
            int carbohydrate = (int) doublecar;

            double doublepro = memberKcal * 0.4;
            int protein = (int) doublepro;

            double doublefat = memberKcal * 0.2;
            int fat = (int) doublefat;

            suggest.setCarbohydrate(carbohydrate);
            suggest.setProtein(protein);
            suggest.setFat(fat);
            suggest.setTotalKcal(memberKcal);

        } else{
            double doublecar = memberKcal * 0.5;
            int carbohydrate = (int) doublecar;

            double doublepro = memberKcal * 0.3;
            int protein = (int) doublepro;

            double doublefat = memberKcal * 0.2;
            int fat = (int) doublefat;

            suggest.setCarbohydrate(carbohydrate);
            suggest.setProtein(protein);
            suggest.setFat(fat);
            suggest.setTotalKcal(memberKcal);
        }

        return suggestRepository.save(suggest);
    }

    //식단 보여주기
    public Suggest getMealPlan(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()
        -> new BaseException(HttpStatus.NO_CONTENT.value(), "Member not found"));
        //List<Suggest> suggests = suggestRepository.findByMemberId(memberId);

        List<Suggest> suggests = member.getSuggests();

        if (suggests.isEmpty()){
            return null;
        }

        Suggest suggest = suggests.get(0);

        String plan_name = suggest.getPlan_name();


        switch (plan_name){
            case "감량":
            case "유지":
                suggest = lowAndKeepPlan(suggest);
            case "증량":
                suggest = bigPlan(suggest);
            case "당뇨 환자들을 위한 식단":
                suggest = diabetesPlan(suggest);
        }
        return suggest;
    }

    public Suggest lowAndKeepPlan(Suggest suggest){
        //각 칼로의 carborn을 먹어야함
        int carbon = suggest.getCarbohydrate();
        int protein = suggest.getProtein();
        int fat = suggest.getFat();

        //총 섭취량 구하기
        int carIntake = (int) Math.round(carbon / 4);
        int proIntake = (int) Math.round(protein / 4);
        int fatIntake = (int) Math.round(fat / 4);

        //문자열을 추가하기
        //총 탄수화물의 86퍼는 고구마가 가져가야함
        int totalCarSweet = (int) Math.round(carIntake * 0.86);
        //총 탄수화물의 11퍼는 야채
        int totalCarVeg = (int) Math.round(carIntake * 0.11);
        //총 탄수화물의 3퍼는 하루 견과
        int totalCarNut = (int) Math.round(carIntake * 0.03);
        //총 단백질의 89퍼 닭가슴살
        int totalProChi = (int) Math.round(proIntake * 0.89);

        int IntakeSweet = (int) Math.round(totalCarSweet/2*280);
        int IntakeChi = (int) Math.round(totalProChi/3*92);
        int IntakeVeg = (int) Math.round(totalCarVeg/3*49);
        int IntakeNut = (int) Math.round(totalCarNut/1*56);

        String breakfast = "고구마 " + IntakeSweet + "개 (1개당 288kcal), 닭가슴살"
                + IntakeChi +"00g (100g당 103kcal), 야채샐러드 " + IntakeVeg +"인분 (1인분 당 154kcal)";

        String lunch = "고구마 " + IntakeSweet + "개 (1개당 288kcal), 닭가슴살"
                + (IntakeChi - 1) +"00g (100g당 103kcal), 야채샐러드 " + IntakeVeg +"인분 (1인분 당 154kcal)";

        String dinner = "닭가슴살" + IntakeChi +"00g (100g당 103kcal), 야채샐러드 " + IntakeVeg +"인분 (1인분 당 154kcal)";

        String snack = "한끼 견과" + IntakeNut +"개 (1개당 104kcal)";

        suggest.setBreakfast(breakfast);
        suggest.setLunch(lunch);
        suggest.setDinner(dinner);
        suggest.setSnack(snack);

        return suggestRepository.save(suggest);
    }

    public Suggest bigPlan(Suggest suggest){
        //각 칼로의 carborn을 먹어야함
        int carbon = suggest.getCarbohydrate();
        int protein = suggest.getProtein();
        int fat = suggest.getFat();

        //총 섭취량 구하기
        int carIntake = (int) Math.round(carbon / 4);
        int proIntake = (int) Math.round(protein / 4);
        int fatIntake = (int) Math.round(fat / 4);

        //문자열을 추가하기
        int totalCarSweet = (int) Math.round(carIntake * 0.7);
        int totalCarVeg = (int) Math.round(carIntake * 0.09);
        int totalCarApp = (int) Math.round(carIntake * 0.18);
        int totalCarYor = (int) Math.round(carIntake * 0.03);
        int totalProChi = (int) Math.round(proIntake * 0.89);


        int IntakeSweet = (int) Math.round(totalCarSweet/2*280);
        int IntakeChi = (int) Math.round(totalProChi/3*92);
        int IntakeVeg = (int) Math.round(totalCarVeg/3*49);
        int IntakeApp = (int) Math.round(totalCarApp/2*141);
        int IntakeYor = (int) Math.round(totalCarYor/2*20);

        String breakfast = "고구마 " + IntakeSweet + "개 (1개당 288kcal), 닭가슴살"
                + IntakeChi +"00g (100g당 103kcal), 야채샐러드 " + IntakeVeg +"인분 (1인분 당 154kcal)";

        String lunch = "고구마 " + IntakeSweet + "개 (1개당 288kcal), 닭가슴살 "
                + (IntakeChi - 1) +"00g (100g당 103kcal), 야채샐러드 " + IntakeVeg +"인분 (1인분 당 154kcal)";

        String dinner = "닭가슴살 " + IntakeChi +"00g (100g당 103kcal), 야채샐러드 " + IntakeVeg +"인분 (1인분당 154kcal)";

        String snack = "사과 " + IntakeApp +"개 (1개당 158kcal), 그릭 요거트 " + IntakeYor + "개 (1개당 119kcal)";

        suggest.setBreakfast(breakfast);
        suggest.setLunch(lunch);
        suggest.setDinner(dinner);
        suggest.setSnack(snack);

        return suggestRepository.save(suggest);
    }

    public Suggest diabetesPlan(Suggest suggest) {
        //대한 당뇨병학회
        String breakfast = "흑미 차조밥 1/2공기, 재첩 맑은 국, 꽈리고추찜, 아삭이고추 양파무침, " +
                "김 실파무침, 배추김치";
        String lunch = "현미밥 1/2공기, 소고기 샤브샤브(소고기 40g), 박고지 묵은 김치 만두," +
                "무 볶음(70g), 양배추 무침, 갓김치";
        String dinner = "수수밥 1/2공기, 비지찌개, 갈치구이(50g), 우엉조림, 미나리 무침, 파김치";

        String snack = "우유(200g), 연시(80g)";
        suggest.setBreakfast(breakfast);
        suggest.setLunch(lunch);
        suggest.setDinner(dinner);
        suggest.setSnack(snack);

        return suggestRepository.save(suggest);
    }

}


