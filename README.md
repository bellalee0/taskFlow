# 🚀 TaskFlow - 기업용 태스크 관리 시스템

## 📌 프로젝트 소개

- 팀스파르타 내일배움캠프의 실무형 Kotlin & Spring 개발자 양성과정 중 진행된 팀 프로젝트

- 기간 : 2025년 12월 08일(월) ~ 15일(월) (총 6일)
  
- 주제 : 이미 완성된 프론트엔드 개발 내용에 맞게 백엔드 서버를 구축하는 아웃소싱 프로젝트

- 프로젝트 회고 : [✍️ velog](https://velog.io/@bella0/%EC%95%84%EC%9B%83%EC%86%8C%EC%8B%B1-%ED%8C%80-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%9A%8C%EA%B3%A0)

## 🎯 프로젝트 목표

- REST API 기반의 안정적인 백엔드 서버 구축
- Spring Boot와 JPA를 활용한 효율적인 데이터 처리
- JWT를 활용한 안전한 인증/인가 시스템 구현
- AOP 기반의 활동 로그 시스템 제공


## 🛠 기술 스택

- **Language**: Java 17
- **Framework**: Spring Boot 3.5.8
- **Database**: MySQL 8.4
- **ORM**: JPA/Hibernate
- **Security**: Spring Security, JWT
- **Build Tool**: Gradle
- **IDE**: IntelliJ IDEA 2025.2.2


## ✨ 주요 기능

<details>
  <summary><strong>1️⃣ 사용자 관리 (User Management)</strong></summary>
  
  - 회원가입 및 로그인 (JWT 기반 인증)
  - 사용자 정보 조회, 수정
  - 비밀번호 확인 및 변경
  - 사용자 목록 조회
  - 팀에 추가 가능한 사용자 조회
</details>
<details>
  <summary><strong>2️⃣ 작업 관리 (Task Management)</strong></summary>

  - 작업 생성, 조회, 수정, 삭제 (CRUD)
  - 작업 상태 변경 (TODO, IN_PROGRESS, COMPLETED)
  - 작업 우선순위 설정 (LOW, MEDIUM, HIGH)
  - 담당자 지정 및 변경
  - 마감일 설정 및 관리
</details>
<details>
  <summary><strong>3️⃣ 팀 관리 (Team Management)</strong></summary>
  
  - 팀 생성, 조회, 수정, 삭제 (CRUD)
  - 팀 멤버 추가 및 제거
  - 팀 멤버 목록 조회
  - 팀 상세 정보 조회
</details>
<details>
  <summary><strong>4️⃣ 댓글 시스템 (Comment Management)</strong></summary>
  
  - 작업별 댓글 생성, 조회, 수정, 삭제
  - 대댓글 지원(계층형 구조)
</details>
<details>
  <summary><strong>5️⃣ 대시보드 (Dashboard)</strong></summary>
  
  - 내 작업 요약 통계
  - 대시보드 통계 데이터 제공
  - 주간 작업 추세 분석
</details>
<details>
  <summary><strong>6️⃣ 통합 검색 (Search)</strong></summary>
  
  - 작업, 팀, 사용자 통합 검색
  - 키워드 기반 검색
</details>
<details>
  <summary><strong>7️⃣ 활동 로그 (Activity Log)</strong></summary>
  
  - AOP 기반 자동 로깅
  - 내 활동 로그 조회
  - 전체 활동 로그 조회(필터링 지원)
  - 작업, 댓글 관련 활동 추적
</details>

## 👩🏻‍💻 담당 기능

### ① 댓글 CRUD 구현

기능:
- 작업별 댓글 생성, 조회, 수정, 삭제
- 대댓글 지원 (계층형 구조)
- 댓글 작성자 본인만 수정/삭제 가능
- 논리 삭제(Soft Delete) 적용

구현 포인트:
- parentComment 필드를 활용한 계층 구조 설계
- 댓글 조회 시 대댓글을 포함한 트리 구조 반환

### ② 활동 로그 기능 구현 (AOP 기반)

기능 설명:
- 사용자의 작업/댓글 관련 활동 자동 추적
- 전체 활동 로그 조회 (필터링 지원)
- 내 활동 로그 조회

구현 포인트:
- @Loggable 커스텀 어노테이션 생성
- AOP를 활용한 자동 로깅 시스템
- @Around 어드바이스로 메서드 실행 전후 처리
- 로그 타입별 분류 (TASK_CREATE, TASK_UPDATE, COMMENT_CREATE 등)

### ③ 기타

- Spring Security 오작동 트러블슈팅
- User, Task 도메인 일부 API 구현
- CORS 설정
- 테스트코드 작성(커버리지 98% 달성)

## 🗂 데이터베이스 ERD

<img width="600" height="591" alt="image" src="https://github.com/user-attachments/assets/45a7fdaa-9dac-4d99-941d-eeebd96592c0" />

<details>
  <summary>User (사용자)</summary>
  
  - 사용자 기본 정보 (username, email, password, name)
  - 논리 삭제(Soft Delete) 지원
  - 생성일/수정일 자동 관리
</details>
<details>
  <summary>Team (팀)</summary>
  
  - 팀 정보 (name, description)
  - 논리 삭제 지원
</details>
<details>
  <summary>TeamUser (팀-사용자 연결)</summary>
  
  - 팀과 사용자 간의 다대다 관계 관리
</details>
<details>
  <summary>Tasks (작업)</summary>
  
  - 작업 정보 (title, description, status, priority)
  - 담당자(assigneeId) 지정
  - 마감일(dueDateTime) 관리
  - 완료 여부 및 완료 시간 추적
  - 논리 삭제 지원
</details>
<details>
  <summary>Comments (댓글)</summary>
  
  - 작업별 댓글 관리
  - 대댓글 지원 (parentComment, depth)
  - 논리 삭제 지원
</details>
<details>
  <summary>Logs (활동 로그)</summary>
  
  - 사용자 활동 추적
- 작업/댓글 관련 로그 기록
- 로그 타입별 분류
</details>


## 📡 API 명세

<details>
  <summary>인증 (Authentication)</summary>
  
  - `POST /api/auth/login` - 로그인
  - `POST /api/users/verify-password` - 비밀번호 확인
</details>
<details>
  <summary>사용자 (Users)</summary>
  
  - `POST /api/users` - 회원가입
  - `GET /api/users/{id}` - 사용자 정보 조회
  - `GET /api/users` - 사용자 목록 조회
  - `PUT /api/users/{id}` - 사용자 정보 수정
  - `DELETE /api/users/{id}` - 회원 탈퇴
  - `GET /api/users/available` - 팀에 추가 가능한 사용자 조회
</details>
<details>
  <summary>작업 (Tasks)</summary>
  
  - `GET /api/tasks` - 작업 목록 조회
  - `GET /api/tasks/{id}` - 작업 상세 조회
  - `POST /api/tasks` - 작업 생성
  - `PUT /api/tasks/{id}` - 작업 수정
  - `DELETE /api/tasks/{id}` - 작업 삭제
  - `PATCH /api/tasks/{id}/status` - 작업 상태 변경
</details>
<details>
  <summary>팀 (Teams)</summary>
  
  - `GET /api/teams` - 팀 목록 조회
  - `GET /api/teams/{id}` - 팀 상세 조회
  - `GET /api/teams/{teamId}/members` - 팀 멤버 조회
  - `POST /api/teams` - 팀 생성
  - `PUT /api/teams/{id}` - 팀 수정
  - `DELETE /api/teams/{id}` - 팀 삭제
  - `POST /api/teams/{teamId}/members` - 팀 멤버 추가
  - `DELETE /api/teams/{teamId}/members/{userId}` - 팀 멤버 제거
</details>
<details>
  <summary>댓글 (Comments)</summary>
  
  - `GET /api/tasks/{taskId}/comments` - 댓글 조회
  - `POST /api/tasks/{taskId}/comments` - 댓글 생성
  - `PUT /api/tasks/{taskId}/comments/{commentId}` - 댓글 수정
  - `DELETE /api/tasks/{taskId}/comments/{commentId}` - 댓글 삭제
</details>
<details>
  <summary>대시보드 (Dashboard)</summary>
  
  - `GET /api/dashboard/stats` - 대시보드 통계
  - `GET /api/dashboard/tasks` - 내 작업 요약
  - `GET /api/dashboard/weekly-trend` - 주간 작업 추세
</details>
<details>
  <summary>활동 로그 (Activities)</summary>
  
  - `GET /api/activities` - 전체 활동 로그 조회
  - `GET /api/activities/me` - 내 활동 로그 조회
</details>
<details>
  <summary>검색 (Search)</summary>
  
  - `GET /api/search` - 통합 검색
</details>


## 🚀 실행 방법

### 1. 백엔드 서버 실행

```bash
# 저장소 클론
git clone https://github.com/dandylsj/taskFlow.git
cd taskFlow

# dev-v2 브랜치로 체크아웃
git checkout dev-v2

# Gradle 빌드 및 실행
./gradlew bootRun

# 서버는 기본적으로 http://localhost:8080에서 실행됩니다
```

### 2. 프론트엔드 연동 : Docker 이미지 실행

```bash
# 이미지 다운로드
docker pull sparta7tutor/taskflow-frontend:latest

# 컨테이너 실행
docker run -d -p 3000:3000 --name taskflow-frontend sparta7tutor/taskflow-frontend:latest

# 접속
# http://localhost:3000

```


## 🏗 프로젝트 구조

```
taskFlow
	├── common
	│   ├── annotation      # 커스텀 어노테이션(@Loggable)
	│   ├── aspect          # AOP 로깅
	│   ├── config          # 설정 (Security, CORS 등)
	│   ├── entity          # 전체 엔티티 관리
	│   ├── exception       # 전역 예외 처리
	│   ├── filter          # JWT Filter
	│   ├── model
	│   │   ├── enums       # 공통 상수 관리
	│   │   └── response    # 공통 응답 포맷
	│   └── utils           # JWT Util, Password Encoder
	└── domain
	    ├── user            # 사용자 도메인
	    ├── auth            # 인증 도메인
	    ├── team            # 팀 도메인
	    ├── task            # 작업 도메인
	    ├── comment         # 댓글 도메인
	    ├── search          # 검색 도메인
	    ├── dashboard       # 대시보드 도메인
	    └── activities      # 활동 로그 도메인
			※ 각 도메인은 controller, service, repository 계층으로 분리되어 있고,
			  model 패키지에서 dto, request, response 객체들을 관리합니다
```


## 🧪 테스트 커버리지

<img width="917" height="249" alt="스크린샷_2025-12-15_오전_10 56 54" src="https://github.com/user-attachments/assets/e2bdf465-97be-4fc5-9c8c-0d13a9473363" />


## 👥 팀 구성

### 팀명: **2🦀 되네**

| 이름 | 담당 내용 |
| --- | --- |
| 이세진(팀장) | 유저 CRUD 구현, 유효성 검증 고도화 |
| 고아람 | 인증/인가 구현(JWT, Spring Security) |
| 성종민 | 작업 CRUD 구현, 검색 기능 구현 |
| 원태준 | 팀 CRUD 구현, 대시보드 기능 구현 |
| 이서연 | 댓글 CRUD 구현, 활동 로그 기능 구현(AOP 기반) |


## 📄 라이선스

본 프로젝트는 팀스파르타 내일배움캠프의 실무형 Kotlin & Spring 개발자 양성과정 중 팀 ‘2게 되네’의 학습 및 포트폴리오 목적으로 제작되었습니다.
