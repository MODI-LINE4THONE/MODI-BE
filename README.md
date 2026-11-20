<div align="center">

🟢 MODI

모두를 위한 맞춤형 키오스크, MODI

NFC 키링 하나로 나에게 맞는 키오스크 환경을 만드는 접근성 개인화 서비스

사용자가 설정한 접근성 프로필을 NFC 키링과 연결하고, 키오스크에 태그하면 개인에게 필요한 UI 설정을 실시간으로 적용합니다.

<br/>
<!-- 메인 이미지 추가 -->
<!-- <img src="./images/modi_main.png" width="100%" /> -->
</div>

⸻

🚀 Deployment

🌐 MODI 서비스를 직접 만나보세요!

사용자 웹 서비스
MODI Web 바로가기 → https://modi-web-roan.vercel.app/

키오스크 데모
배포 주소 추가 예정

⸻

💡 서비스 소개

"MODI"는 기존 키오스크의 획일적인 사용자 인터페이스로 인해 불편함을 겪는 사람들을 위한 개인 맞춤형 키오스크 접근성 서비스입니다.

고령자, 저시력자, 디지털 기기 사용에 익숙하지 않은 사용자 등은 키오스크를 이용할 때 다른 환경을 필요로 합니다.

MODI는 사용자가 자신의 접근성 설정을 미리 등록하고, NFC 키링을 키오스크에 태그하는 것만으로 개인화 UI를 자동으로 제공합니다.

복잡한 설정 과정을 반복하지 않고도 큰 글씨, 고대비 화면, 터치 가이드, 화면 위치 조정, 터치 보정 등의 기능을 이용할 수 있습니다.

키오스크에 사용자를 맞추는 것이 아니라, 키오스크가 사용자에게 맞춰지도록!

MODI는 누구나 쉽고 편리하게 키오스크를 이용할 수 있는 환경을 목표로 합니다.

⸻

✨ 주요 기능

01. 사용자별 접근성 프로필 관리

사용자는 자신의 이용 환경에 맞는 접근성 설정을 프로필로 저장할 수 있습니다.

하나의 계정에서 여러 프로필을 생성하고 관리할 수 있으며, 각 프로필마다 서로 다른 접근성 설정을 적용할 수 있습니다.

지원하는 접근성 설정

큰 글씨 모드 — 글자 크기를 확대하여 가독성 향상
고대비 모드 — 화면의 색상 대비를 높여 정보 식별 지원
터치 가이드 — 키오스크 조작 과정 안내
화면 위치 조정 — 주요 조작 영역을 아래쪽으로 배치
터치 보정 — 터치 입력의 편의성 개선

<!-- <img src="./images/profile.png" width="32%" /> -->

02. NFC 키링 등록 및 프로필 연동

사용자는 발급받은 NFC 키링을 자신의 계정에 등록하고 원하는 접근성 프로필과 연결할 수 있습니다.

키링의 고유 UID와 등록 코드를 이용해 등록 과정을 진행하며, 키링별 연결 프로필을 관리할 수 있습니다.

이를 통해 별도의 로그인 과정 없이 키링 태그만으로 접근성 설정을 불러올 수 있습니다.

<!-- <img src="./images/keyring.png" width="32%" /> -->

03. NFC 기반 키오스크 자동 개인화

사용자가 키오스크에 NFC 키링을 태그하면 서버에서 키링과 연결된 접근성 프로필을 조회합니다.

조회된 설정은 해당 키오스크로 전달되며, 사용자에게 적합한 UI 환경을 적용합니다.

동작 과정

NFC 키링 태그
      ↓
키오스크에서 UID 인식
      ↓
Spring Boot 서버로 UID 및 kioskCode 전달
      ↓
등록된 키링 및 접근성 프로필 조회
      ↓
WebSocket으로 설정 실시간 전달
      ↓
키오스크 UI 개인화 적용
<!-- <img src="./images/nfc_demo.png" width="70%" /> -->

04. WebSocket 기반 실시간 접근성 설정 적용

Spring Boot와 WebSocket(STOMP)을 활용하여 접근성 설정을 실시간으로 전달합니다.

각 키오스크는 고유한 kioskCode를 사용하며, 서버는 해당 키오스크의 전용 채널로 설정을 전달합니다.

이를 통해 여러 키오스크가 연결된 환경에서도 설정을 구분하여 전달할 수 있습니다.


05. NFC 키링 분실 및 재발급 관리

키링을 분실한 경우 기존 키링을 사용할 수 없도록 처리하고 재발급을 요청할 수 있습니다.

재발급 요청 과정에서 배송 정보를 입력하고, 발급 진행 상태를 확인할 수 있도록 구성했습니다.

재발급 상태 관리

재발급 요청
     ↓
REQUESTED
     ↓
SHIPPING
     ↓
DELIVERED

※ 배송 상태는 시연을 위한 관리 기능이며 실제 택배사 시스템과 연동된 것은 아닙니다.

06. 키오스크 주문 시연

실제 카페 키오스크 이용 상황을 가정한 React 기반 주문 화면을 통해 접근성 설정이 적용되는 과정을 시연합니다.

사용자는 NFC 태그 전후의 UI 변화를 확인하고, 개인화된 화면에서 메뉴 선택과 장바구니 기능을 이용할 수 있습니다.

<!-- <img src="./images/kiosk_before.png" width="45%" /> -->
<!-- <img src="./images/kiosk_after.png" width="45%" /> -->

⸻

🏗 시스템 아키텍처

              ┌─────────────────────┐
              │      사용자 웹       │
              │       React         │
              └──────────┬──────────┘
                         │ REST API
                         ▼
              ┌─────────────────────┐
              │    Spring Boot      │
              │     Backend         │
              └──────────┬──────────┘
                         │
                         ▼
              ┌─────────────────────┐
              │       MySQL         │
              │   사용자/프로필/키링  │
              └─────────────────────┘
   NFC 키링
      │
      ▼
┌─────────────────────┐
│      키오스크        │
│    React Kiosk      │
└──────────┬──────────┘
           │ NFC UID + kioskCode
           ▼
┌─────────────────────┐
│    Spring Boot      │
│    NFC API          │
└──────────┬──────────┘
           │ 프로필 조회
           ▼
┌─────────────────────┐
│ WebSocket (STOMP)   │
└──────────┬──────────┘
           │ 접근성 설정 전달
           ▼
┌─────────────────────┐
│     키오스크 UI      │
│   접근성 설정 적용   │
└─────────────────────┘

⸻

🛠 기술 스택

구분	기술
Backend	Java 17, Spring Boot, Spring Security, Spring Data JPA
Database	MySQL
Real-time  WebSocket, STOMP
Authentication	JWT
Cloud & Infrastructure	AWS EC2, Docker
CI/CD	GitHub Actions
API Documentation	Swagger UI
Collaboration	GitHub

⸻

📂 Backend 프로젝트 구조

MODI-BE
 ├── src
 │   └── main
 │       ├── java
 │       │   └── com.likelion
 │       │       ├── domain
 │       │       │   ├── auth
 │       │       │   ├── user
 │       │       │   ├── profile
 │       │       │   ├── keyring
 │       │       │   └── kiosk
 │       │       └── global
 │       │           ├── config
 │       │           ├── security
 │       │           ├── common
 │       │           └── exception
 │       │           
 │       └── resources
 │           └── application.yml
 │           └── application-dev.yml
 │           └── application-prod.yml
 │
 ├── Dockerfile
 ├── .github
 │   └── workflows
 └── build.gradle
⸻

👥 팀원

4호선톤 MODI 팀

역할	담당
진가연	
남궁강  

⸻

<div align="center">


</div>
