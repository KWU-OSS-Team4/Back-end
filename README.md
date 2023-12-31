# Witheat

## 소프트웨어의 기능

### 1. 로그인
> 회원가입을 통해 ID 생성한다 <br/>
> 사용자의 정보를 서버에 저장하고 불러오는 키로 사용한다 (memberId)
> 로그인 부분의 경우 JWT(Json Web Token)을 이용했다. JWT란 JSON 형식의 토큰에 대한 표준 규격이고, 사용자의 인증 또는 인가 정보를 서버와 클라이언트 간에 안전하게 주고 받기 위해서 사용된다. JWT 토큰 웹에서는 HTTP 헤더를 “Bearer 토큰”의 형태로 설정하여 클라이언트에서 서버로 전송을 한다. 그래서 로그인을 한 후, 이 토큰을 가지고 다른 기능들을 실행할 수 있게 된다. JWT는 토큰 자체에 사용자의 정보가 저장되어 있어 서버 입장에서 토큰을 검증만 해주면 된다는 장점이 있어서 사용하게 되었다.

> 앱을 실행하면 초기화면으로 로그인 화면 나타난다. 존재하지 않는 아이디로 로그인 시 앱 사용이 불가능하다. 아래에 있는 register 버튼을 클릭하면 회원가입이 가능한 화면으로 넘어간다. 순서대로 사용자 이름, 아이디, 패스워드를 입력하면 아이디가 생성되고 다시 로그인 페이지로 돌아가 로그인을 이어나간다.
<br/>

### 2 마이페이지
> 키, 몸무게, 나이 등의 사용자 정보를 표시하고, 수정할 수 있게 하였다 <br/>
> 사용자가 원하는 체형에 따른 목표를 설정할 수 있게 하였다 <br/>
> 일정 기간 동안의 성취도 통계와 (성공, 실패 횟수), 체중 변화 그래프를 표시했다.<br/>

> 사용자로부터 그 날 사용자가 섭취한 음식들을 입력 받은 뒤, 원형 그래프 형식으로 탄수화물, 단백질, 지방의 비율과 섭취한 음식들의 총 칼로리를 보기 좋게 나타낸다. 또한, 사용자가 식단을 잘 지키는데 성공한 횟수와 실패한 횟수를 중앙에 보여주고 선 그래프를 통해 일정 기간 동안 사용자의 체중 변화를 표시한다. 왼쪽 하단에는 현재 몸무게와 신장을 입력할 수 있도록 버튼이 마련되어 있고, 그 옆에 오늘의 식단을 보여준다.
<br/>

### 3. 캘린더
> 사용자가 직접 입력한 목표를 캘린더에 표시한다

> 캘린더에 날마다 메모를 작성할 수 있는 칸을 마련하여 사용자가 해당 날짜의 본인의 식사에 대한 평가나 기타 사항들을 적을 수 있도록 했다. 이를 통해 사용자가 본인의 식단을 반성하거나 지난 기록들을 보면서 동기부여를 얻어 식단 계획을 더 오래, 잘 지킬 수 있게 한다.
<br/>

### 4. 식단 관리 기능
> 사용자로부터 섭취량을 직접 입력 받아 탄단지 kcal에 update 한다
<br/>

### 5. 식단 추천 기능
> 사용자에게 키, 몸무게 등의 정보를 받고 감량/유지/증량/당뇨의 식단 선택지를 제공한다 <br/>
> 선택한 식단에 해당하는 탄수화물/단백질/지방 권장섭취량을 제공한다다

> 사용자에게 감량/유지/증량 등의 선택지를 제공해 원하는 식이요법을 선택하게 한다. 이후, 기존에 받은 키, 몸무게 등을 토대로 사용자가 선택한 선택지를 목표로 하루에 섭취해야 할 탄단지 kcal 및 총 kcal를 제공한다. <br/>
> 해당 사용자의 목표 탄단지 kcal 및 총 kcal를 구하기 위해서 해당 사용자가 하루에 필요한 열량을 구해야 한다.
<br/>

#### 하루에 필요한 열량  구하는 식
` (여자의 표준 체중) = (사용자의 height) ^ 2 * 21 ` <br/>
` (남자의 표준 체중) = (사용자의 height) ^ 2 * 22 `

` (비만도) = (사용자의 weight) / 표준 체중 * 100 ` <br/>

``` javascript
(비만도 레벨)
- 과체중 : 비만도 > 120 일 경우,
- 저체중 : 비만도 < 90 일 경우,
- 정상 : 90 ≤ 비만도 ≤ 120 일 경우,
```


```javascript
(사용자의 하루 필요한 열량) 
- 과체중인 경우: 표준 체중 * 25
- 정상인 경우 : 표준 체중 * 30
- 저체중인 경우 : 표준 체중 * 35`
 ```


해당 열량에 맞게 탄단지kcal를 구하기→ 하루에 탄단지 비울 5:3:2가 영양소적으로 좋다다
``` javascript
(일반 유지, 감량, 증량)
탄수화물 : (필요한 열량) * 0.5
단백질 : (필요한 열량) * 0.3
지방 : (필요한 열량) * 0.2

(당뇨 식단)
탄수화물 : (필요한 열량) * 0.4
단백질 : (필요한 열량) * 0.4
지방 : (필요한 열량) * 0.2
```

> 사용자가 식단 추천을 원할 경우, 식단 추천 받기 버튼을 누르면 위에서 구한 탄수화물, 단백질, 지방 kcal 를 통해 사용자에 맞는 용량에 따른 식단을 제공한다.


#### 해당 용량 구하는 방법
- 이용할 식단
    - 감량 및 유지 식단
    ```javascript
    아침 : 고구마 2개 + 닭가슴살 100g + 야채샐러드
    점심 : 고구마 2개 + 닭가슴살 100g + 야채 샐러드
    간식 : 한끼 견과
    저녁 : 닭가슴살 200g + 야채 샐러드
    ```

    - 증량
    ```javascript
    아침 : 고구마 2개 + 닭가슴살 100g + 야채샐러드
    아침 간식 : 사과 1개, 그릭 요거트 
    점심 : 고구마 2개 + 닭가슴살 100g + 야채 샐러드
    점심 간식 : 사과 1개 + 그릭 요거트
    저녁 : 닭가슴살 200g + 야채 샐러드
    ```
    
    - 당뇨
    ```javascript
    아침 : 흑미 차조밥 1/2공기, 재첩 맑은 국, 꽈리고추찜, 아삭이고추 양파무침, 김 실파무침, 배추김치
    점심 : 현미밥 1/2공기, 소고기 샤브샤브(소고기 40g), 박고지 묵은 김치 만두, 무 볶음(70g), 양배추 무침, 갓김치
    간식 : 우유(200g), 연시(80g)
    저녁 : 수수밥 1/2공기, 비지찌개, 갈치구이(50g), 우엉조림, 미나리 무침, 파김치
    ```

    ##### 감량 및 유지 식단 : 영양사의 추천 식단, 벌크업: 벌크업 정보가 나와있는 블로그 및 ‘하이닥’과 같은 건강페이지, 당뇨 식단은: 당뇨관리협회 페이지에서 정보를 찾았다.

  - 해당 식단이 총 kcal에 몇 퍼센트를 차지하는 지 구하고 이를 통해 해당 음식들이 차지하는 비중 정하기
    - 유지나 감량 식단일 경우
      1. 현재 음식들이 탄수화물 또는 단백질에 차지하는 비중 정하기 
      ``` javascript
      i) 고구마가 가지는 총 kcal(totalSweet) = 탄수화물 칼로리(totalCar) * 0.86
      ii) 닭가슴살이 가지는 총 kcal(totalChi) = 단백질 칼로리 (totalPro) * 0.86
      iii) 하루 견과가 가지는 총 kcal(totalNut) = 탄수화물 칼로리 * 0.03
      iv) 야채 샐로드가 가지는 총 kcal(totalVeg) =탄수화물 칼로리 * 0.11
      ```
      <br/>
      
      2. 한 번 식사의 음식 양을 구하기
      ``` javascript
      i) 고구마 : 한 번 당 280kcal를 섭취, 총 2번(아침, 점심)을 나눠서 먹음 -> 고구마 한 끼 양(intakeSweet) = 총 고구마의 kcal /2*280
      ii) 닭가슴살(intake : 한 번 당 92kcal 섭취, 총 3번(아,점,저)을 나눠서 먹음 -> 닭가슴살 한 끼 양(intakeChi) = 총 닭가슴살의 kcal / 3*92
      iii) 야채샐러드 : 한 번 당 49kcal 섭취, 총 3번 나눠서 먹음 -> 샐러드 한 끼 양(intakeVeg) = 총 샐러드의 kcal/ 3*49
      iv) 하루 견과 : 한 번 당 56kal를 섭취, 총 1번 -> 하루 견과 한 끼 양(intakeNut) = 하루견과 kcal /1*56
      ```
      <br/>
      
      3. 아침 식사, 점심 식사, 저녁 식사, 간식 출력
      ``` javascript
      ex) String breakfast = "고구마 " + intakeSweet + "개 (1개당 288kcal), 닭가슴살" + intakeChi + "00g (100당 103kcal)
      ```
      
      ##### 고구마 1개당 288kcal, 닭가슴살 100g당 103kcal, 야채샐러드 1인분 당 154kcal, 한끼 견과 1개당 104kcal


    - 벌크업일 경우
       1. 현재 음식들이 탄수화물 또는 단백질에서 차지하는 비중 정하기(위 식단과 다른 점 : 간식의 종류가 다름)
        ```javascript
        i) 고구마가 가지는 총 kcal(totalSweet) = 탄수화물 칼로리(totalCar) * 0.7
        ii) 닭가슴살이 가지는 총 kcal(totalChi) = 단백질 칼로리 (totalPro) * 0.81
        iii) 사과가 가지는 총 kcal(totalApp) = 탄수화물 칼로리 * 0.18
        iv) 야채 샐로드가 가지는 총 kcal(totalVeg) =탄수화물 칼로리 * 0.09
        v) 그릭 요거트가 가지는 총 kcal(totalYor) =탄수화물 칼로리 * 0.03
        ```
        <br/>
        
       2. 한 번 식사의 음식 양을 구하기
        ``` javascript
        i) 고구마 : 한 번 당 280kcal를 섭취, 총 2번(아침, 점심)을 나눠서 먹음 -> 고구마 한 끼 양(intakeSweet) = 총 고구마의 kcal /2*280
        ii) 닭가슴살(intake : 한 번 당 92kcal 섭취, 총 3번(아,점,저)을 나눠서 먹음 -> 닭가슴살 한 끼 양(intakeChi) = 총 닭가슴살의 kcal / 3*92
        iii) 야채샐러드 : 한 번 당 49kcal 섭취, 총 3번 나눠서 먹음 -> 샐러드 한 끼 양(intakeVeg) = 총 샐러드의 kcal/ 3*49
        iv) 사과 : 한 번 당 141kal를 섭취, 총 2번 -> 사과 한 끼 양(intakeApp) = 하루견과 kcal /2*141
        v) 그릭 요거트 : 한 번 당 20kal를 섭취, 총 2번 -> 그릭 한 끼 양(intakeYor) = 그릭 요거트 kcal /2*20
        ```
        <br/>
      
      3. 아침 식사, 점심 식사, 저녁 식사, 간식 출력
      ``` javascript
      ex) String breakfast = "고구마 " + intakeSweet + "개 (1개당 288kcal), 닭가슴살" + intakeChi + "00g (100당 103kcal)
      ```

      ##### 사과 1개당 158kcal, 그릭 요거트 1개당 119kcal

    - 당뇨 식단 : 위의 식단을 그대로 아침 점심 저녁 간식으로 보여주기
<br/>

### 6. 알람기능
> 간헐적 단식, 공복, 물 마시기에 대한 알람 기능을 각각 제공한다 <br/>
> 지정된 시간에 사용자에게 알려 사용자의 계획을 보조할 수 있게 한다.

>초기값으로 물,공복,간헐적단식 주기를 제공한다. 기본적으로 각각 2시간, 4시간,14시간의 기본값을 가진다. 제일 위의 재생버튼을 누르면 각각 타이머가 작동한다. 이후 같은위치의 일시정지 버튼으로 바뀐 버튼을 다시 누르면 정지한다. 왼쪽 버튼을 누르면 초기값으로 reset되고 오른쪽 버튼을 누르면 각각의 타이머의 시간을timepicker로 선택해 타이머를 원하는 시간으로 조정할 수 있다. 
<br/>

## 설치 방법 및 사용 방법
> #### 설치 방법
> 없음
> #### 사용 방법
1. 로그인 & 로그아웃 (설정 아이콘)
   앱 최초실행 회원가입 , 이름, 이메일, 비밀번호, 성별, 키, 몸무게 정보를 입력받는다
   앱 실행 시, 맨 오른쪽 하단 설정 부분을 누르면 로그아웃 및 뒤로 돌아갈 버튼인 cancel 버튼이 존재한다.
   
2. 마이페이지 (홈)
   앱 맨 위 상단의 사용자에게 입력받은 키와 몸무게가 나옴. 이를 클릭하면 키와 몸무게를 수정할 수 있다(식단 관리 기능)
   중간에 오늘 하루 섭취한 탄단지 비율을 동그라미로 한 눈에 정보를 볼 수 있게 하였으며 원 그래프 옆엔 수치로 탄단지의 섭취 kcal를 볼 수 있다
   해당 원 그래프를 클릭하면 사용자가 섭취한 탄단지 정보를 입력할 수 있다

3. 캘린더(달력 아이콘)
   캘린더에 날마다 메모를 작성할 수 있는 칸을 마련하여 사용자가 해당 날짜의 본인의 식사에 대한 평가나 기타 사항들을 적을 수 있도록 했다
   이를 통해 사용자가 본인의 식단을 반성하거나 지난 기록들을 보면서 동기부여를 얻어 식단 계획을 더 오래, 잘 지킬 수 있게 한다
   
5. 식단 추천(밥 아이콘)
   해당 페이지에 들어가면 '감량', '유지', '증량', '당뇨' 총 4가지의 식단을 선택할 수 있다. 사용자가 원하는 식단을 하나 고르면 다음 페이지에 해당 식단에 맞는 총 kcal를 계산해주어 하루의 탄단지의 각각의 총 kcal를 알려    준다. 옆에 식단 추천 버튼을 누르면 사용자에 맞는 용량으로 식단을 추천해주었다.
    
