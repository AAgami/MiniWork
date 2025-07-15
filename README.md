# MiniWork

경량 협업툴 웹앱 – 채팅 기반 업무관리, 인사기능, 알림까지 가능한 JANDI 스타일 MVP

---

## 🔧 프로젝트 개요

- **목표**: 1인이 1주일 내 개발 가능한 협업툴 MVP
- **기술스택**: Spring Boot + React + MySQL + Docker
- **기능요약**:
  - 사용자 인증 및 MFA(Google OTP)
  - 조직/멤버 관리, 추가 및 역할 분리
  - 채널/1:1 채팅, 파일 채널, 멘션
  - 연차 신청, 서류 제출, 실시간 알림 등

---

## 📁 디렉토리 구조

```
MiniWork/
├── backend/                  # Spring Boot API 서버
│   ├── src/main/java/com/miniwork/backend/
│   │   ├── user/             # 사용자 도메인
│   │   ├── organization/     # 조직/멤버 도메인
│   │   └── auth/             # 보안 및 인증
│   └── resources/
│       └── application.properties
├── frontend/                 # React 프론트엔드
│   ├── src/
│   └── vite.config.ts
├── docker-compose.yml        # MySQL Docker 설정
└── docs/                     # ERD, API 문서 등
```


## 🔐 개발 중 보안 설정

- Spring Security 비활성화 설정 완료
- `/api/**`만 REST로 열리어 있음
- 로그인 및 JWT 적용 예정

---

## 📌 진행 상황

- [x] User 도메인 구축
- [x] Docker + MySQL 연동
- [x] 프로젝트 구조 정리
- [ ] 회원가입 API
- [ ] JWT 인증 + 로그인
- [ ] 조직 생성/추가/역할 지정
- [ ] 채널 및 채팅
- [ ] 연차/문서 제출 + 알림

---

## ✨ 개발 목적

- Spring Boot + REST API 학습
- JWT + 권한 설계 연습
- 협업 도구 기능 이해 및 재구성
- 이후 확장 가능한 구조로 설계
