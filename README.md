<p align="center">
  <img src="./images/modi_main.jpeg" width="100%" alt="MODI 서비스 메인 이미지" />
</p>

# 🔵 MODI

> **NFC 키링을 태그하면 나에게 맞는 화면으로 바뀌는 개인 맞춤형 키오스크 접근성 서비스입니다.**

## 🚀 Deployment

> 🌐 **MODI 서비스를 직접 만나보세요!**  
> [**MODI 바로가기 →**](https://modi-web-roan.vercel.app/onboarding/login)

## 💡 서비스 소개

**MODI**는 획일적인 키오스크 화면으로 불편함을 겪는 **고령자, 저시력자, 디지털 기기 이용이 익숙하지 않은 사용자** 등을 위한 **개인 맞춤형 키오스크 접근성 서비스**입니다.

사용자는 웹에서 **큰 글씨, 고대비, 터치 가이드, 화면 위치 조정, 터치 보정** 등의 접근성 설정을 프로필로 저장하고 NFC 키링과 연결할 수 있습니다. 키오스크에서 키링을 태그하면 서버가 연결된 프로필을 조회하고, **WebSocket으로 해당 키오스크에 설정을 실시간 전달**합니다.

매번 키오스크에서 복잡한 설정을 반복하지 않아도, 자신에게 편한 환경으로 서비스를 이용할 수 있도록 돕습니다.

> **키오스크에 사용자를 맞추는 것이 아니라, 키오스크가 사용자에게 맞춰지도록!**

## ✨ 주요 기능

### 01. 사용자별 접근성 프로필 관리
사용자는 자신의 필요에 맞게 **접근성 프로필을 생성·수정·조회**할 수 있습니다. 하나의 계정에서 여러 프로필을 관리하고 프로필마다 다른 설정을 저장할 수 있습니다.

- **큰 글씨** — 글자 크기 확대
- **고대비** — 화면 대비 강화
- **터치 가이드** — 조작 과정 안내
- **화면 위치 조정** — 주요 조작 영역을 아래쪽으로 배치
- **터치 보정** — 터치 입력 편의성 개선

<!-- 실제 이미지 업로드 후 주석을 해제하세요.
<p align="left">
  <img src="./images/profile.png" width="32%" />
</p>
-->

### 02. NFC 키링 등록 및 프로필 연동
발급된 NFC 키링의 **등록 코드**를 이용해 키링을 계정에 등록하고, 원하는 접근성 프로필에 연결할 수 있습니다. 등록된 키링은 고유 UID를 통해 식별됩니다.

<!--
<p align="left">
  <img src="./images/keyring.png" width="32%" />
</p>
-->

### 03. NFC 기반 키오스크 자동 개인화
사용자가 키오스크에 NFC 키링을 태그하면 **키링 UID와 키오스크 코드**가 서버로 전달됩니다. 서버는 연결된 프로필을 조회한 뒤 해당 키오스크에 접근성 설정을 전송합니다.

**NFC 태그 → 키링·프로필 조회 → WebSocket 전송 → 키오스크 UI 적용**

<!--
<p align="left">
  <img src="./images/nfc_demo.png" width="70%" />
</p>
-->

### 04. WebSocket 기반 실시간 설정 적용
**Spring Boot와 WebSocket(STOMP)**을 사용해 접근성 설정을 실시간으로 전달합니다. 키오스크마다 고유한 `kioskCode`를 사용하여 각 키오스크의 구독 채널을 구분합니다.

### 05. NFC 키링 분실 및 재발급 관리
키링 분실 시 기존 키링을 비활성화하고 재발급을 요청할 수 있습니다. 시연 환경에서는 **요청 → 배송 중 → 배송 완료** 상태를 관리하며, 실제 택배사 배송 시스템과는 연동하지 않습니다.

### 06. 키오스크 주문 시연
카페 주문 상황을 가정한 **React 기반 키오스크 화면**에서 NFC 태그 전후의 접근성 UI 변화를 시연합니다. 사용자는 개인화된 화면에서 메뉴 선택 및 장바구니 흐름을 확인할 수 있습니다.

<!--
<p align="left">
  <img src="./images/kiosk_before.png" width="45%" />
  <img src="./images/kiosk_after.png" width="45%" />
</p>
-->

## 🛠 기술 스택

| 구분 | 기술 |
| --- | --- |
| **Backend** | Java 17, Spring Boot, Spring Security, Spring Data JPA |
| **Database** | MySQL |
| **Real-time Communication** | WebSocket, STOMP |
| **Authentication** | JWT |
| **Cloud & Infrastructure** | AWS EC2, Docker |
| **CI/CD** | GitHub Actions |
| **API Documentation** | Swagger UI, springdoc-openapi |
| **Collaboration** | GitHub |

## 👥 팀원

![BE](https://img.shields.io/badge/BE-남궁강-3B68ED) &nbsp;
![BE](https://img.shields.io/badge/BE-진가연-3B68ED)

## 📂 Repository

### ⚙️ Backend
> 사용자 인증, 접근성 프로필 및 NFC 키링 관리, WebSocket 통신과 서버 기능을 담당합니다.  
> [**Backend Repository →**](https://github.com/MODI-LINE4THONE/MODI-BE)

---

### 🦁 4호선톤 5팀 | MODI
